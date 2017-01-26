package sqlQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FetchData {

	Connection myConn = null;
	Statement stmt = null;
	String startDate = null;
	String endDate = null;
	ArrayList<String> stationList = new ArrayList<String>(Arrays.asList("30791","30001","30002","30003"));
	
	/**
	 * Конструктор
	 * @param inConn установленное подключение к БД
	 */
	public FetchData(Connection inConn){
		myConn = inConn;
	}

	/**
	 * Конструктор
	 * @param inConn установленное подключение к БД
	 * @param inStartDate дата начала расчета
	 * @param inEndDate дата окончания расчета
	 */
	public FetchData(Connection inConn,
						String inStartDate,
						String inEndDate){
		myConn = inConn;
		startDate = inStartDate;
		endDate = inEndDate;
	}
	
	/**
	 * Получить данные о пороговых значениях расчетов
	 */
	public Map<String, Object> getTsh(){
		
		String queryString = null;
		
		Map<String, Object> responseData = new HashMap<String, Object>();
		
		try {
			stmt = myConn.createStatement();
			
			queryString = "SELECT * FROM tsh ";
			
			ResultSet rs = stmt.executeQuery(queryString);
				
			while (rs.next()){
				String currName = null;
				Integer currValue = null;
				
				currName = rs.getString(1);
				currValue = rs.getInt(2);
				
				//модифицируем поле имя
				int ind = 0;
				ind = currName.indexOf(" ");
				if (ind > 0){
					currName = currName.substring(0,ind);
				}
				
				responseData.put(currName, currValue);
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("we have sql exception");
		}finally{
			if (stmt != null){try {
				
				stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("we have sql exception");
			}}
		}
		
		return responseData;
		
	}
	
	/**
	 * Получить данные основного расчета
	 */
	public ArrayList<ArrayList<LinkedHashMap<String, Object>>> getMainStat(){
		
		ArrayList<ArrayList<LinkedHashMap<String, Object>>> responseData = new ArrayList<ArrayList<LinkedHashMap<String, Object>>>();
		
		for(String station: stationList){
		
			String queryString = "SELECT * FROM lt_estim_res "
						+ "WHERE indate = '"+startDate+"' AND outdate = '"+endDate+"' AND station= '"+station+"'"
								+ "AND period = 24 ";
			
			responseData.add(this.makeSimpleQ(queryString));	
		}
		
		return responseData;
	}

	/**
	 * Получить данные расширенной статистики
	 * @return упакованые данные 
	 */
	public ArrayList<ArrayList<LinkedHashMap<String, Object>>> getExtendStat(){
		
		ArrayList<ArrayList<LinkedHashMap<String, Object>>> responseData = new ArrayList<ArrayList<LinkedHashMap<String, Object>>>();
		
		for(String station: stationList){
		
			String queryString = "SELECT * FROM lt_estim_res_stat "
				+ "WHERE indate = '"+startDate+"' AND outdate = '"+endDate+"'AND station= '"+station+"'"
						+ " AND period = 24 ";
			
			responseData.add(this.makeSimpleQ(queryString));
		}
		
		return responseData;
	}
		
	/**
	 * 	Получить данные статистики по статусу
	 */
	public ArrayList<LinkedHashMap<String, Object>> getStatusStat(){
		
		String queryString = "SELECT * FROM lt_estim_res_stat_status "
			+ "WHERE indate = '"+startDate+"' AND outdate = '"+endDate+"' "
					+ " AND period = 36 ";
		
		return this.makeSimpleQ(queryString);		
	}
	
	/**
	 * Получить данные статистики по расчету времени начала тумана
	 * @return упакованные данные
	 */
	public ArrayList<LinkedHashMap<String, Object>> getStartFogStat(){
		
				
		String queryString = "SELECT * FROM lt_fog_start_stat "
				+ "WHERE indate = '"+startDate+"' AND outdate = '"+endDate+"' ";
		
		return this.makeSimpleQ(queryString);
	}
	
	/**
	 * Получить список сроков, которые попали в корзину а
	 * @return
	 */
	public ArrayList<LinkedHashMap<String, Object>> getBasketA(){
		
		//запрос на создание VIEW
		String queryString = "CREATE VIEW status AS "
			+ "SELECT lt_estim_status.timemessage,lt_estim_status.station, lt_estim_30791.tmstart, lt_estim_30791.factvalue,lt_estim_status.mainmessage "
			+ "FROM lt_estim_status "
			+ "LEFT JOIN lt_estim_30791 ON " 
			+ "lt_estim_status.station = lt_estim_30791.station "
			+ "WHERE " 
			+ "lt_estim_30791.station = '30791' " 
			+ "AND lt_estim_30791.param = 'Vis' "
			+ "AND lt_estim_30791.tmstart >= lt_estim_status.timemessage " 
			+ "AND (lt_estim_30791.tmstart - lt_estim_status.timemessage <= '06:00:00') " 
			+ "AND DATE(lt_estim_status.timemessage) >= '"+startDate+"' "  
			+ "AND DATE(lt_estim_status.timemessage) <= '"+endDate+"' " 
			+ "ORDER BY lt_estim_status.timemessage,lt_estim_30791.tmstart";
	
		this.updateQ(queryString);
		
		queryString = " SELECT DISTINCT status.timemessage  FROM status " 
			+ "	WHERE status.factvalue <= 1000 "
			+ " AND status.mainmessage = 'ожидается туман' "
			+ " AND status.tmstart >= status.timemessage " 
			+ "AND (status.tmstart - status.timemessage <= '06:00:00')";
		
		
		return this.makeSimpleQ(queryString);
		
	}
	
	/**
	 * Получить список сроков, которые попали в корзину b
	 * @return
	 */
	public ArrayList<LinkedHashMap<String, Object>> getBasketB(){
		
		//создание дополнительного view
		String queryString = " CREATE VIEW status_a AS " 
				+ "SELECT distinct(timemessage) "
				+ "FROM status "
				+ "WHERE " 
				+ "status.tmstart >= status.timemessage " 
				+ "AND (status.tmstart - status.timemessage <= '06:00:00') " 
				+ "AND status.factvalue <= 1000 "
				+ "AND status.mainmessage = 'ожидается туман'";
		
		this.updateQ(queryString);
		
		queryString = " SELECT DISTINCT(timemessage) FROM status "
				+ "WHERE status.mainmessage = 'ожидается туман' "
				+ "AND status.tmstart >= status.timemessage " 
				+ "AND (status.tmstart - status.timemessage <= '06:00:00') "
				+ "AND status.timemessage NOT IN (SELECT timemessage FROM status_a) "
				+ "ORDER BY timemessage";
		
		return this.makeSimpleQ(queryString);
	}

	/**
	 * Получить список сроков, которые попали в корзину c
	 * @return
	 */
	public ArrayList<LinkedHashMap<String, Object>> getBasketC(){
		
		String queryString = " SELECT *  FROM status " 
				+ "WHERE status.factvalue <= 1000 " 
				+ "AND status.mainmessage = 'без тумана' "
				+ "AND status.tmstart >= status.timemessage " 
				+ "AND (status.tmstart - status.timemessage <= '06:00:00') ";
		
		return this.makeSimpleQ(queryString);
	}

	/**
	 * Получить список с сроками, когда видимость меньше 1000м
	 * @return
	 */
	public ArrayList<LinkedHashMap<String, Object>> getFactFogTime(){
	
		String queryString = "DROP VIEW status_a";
		
		this.updateQ(queryString);
		
		queryString = "DROP VIEW status";
		
		this.updateQ(queryString);
		
		queryString = " SELECT tmstart,factvalue FROM lt_estim_30791 WHERE param = 'Vis' AND factvalue <= 1000 AND " 
				+ "date(tmstart) >= '"+startDate+"' and date(tmstart) <= '"+endDate+"' ORDER by tmstart";
		
		return this.makeSimpleQ(queryString);
		
	}
	
	/**
	 * Выполнения запроса, где ожидается результат типа void
	 * @param q строка с запросом
	 */
	private void updateQ(String q){
		
		try {
			stmt = myConn.createStatement();
			stmt.executeUpdate(q);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("we have sql exception");
		}finally{
			if (stmt != null){try {
				
				stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("we have sql exception");
			}}
		}
	}
	
	/**
	 * Выполнение запроса и сворачивание результата в список
	 * @param q - строка с запросом
	 * @return - ArrayList<Map<String, Object>> - данные упакованы
	 */
	private ArrayList<LinkedHashMap<String, Object>> makeSimpleQ(String q){
		
		ArrayList<LinkedHashMap<String, Object>> responseData = new ArrayList<LinkedHashMap<String, Object>>();
		
		try {
			stmt = myConn.createStatement();
			LinkedHashMap<String, Object> m;
			
			ResultSet rs = stmt.executeQuery(q);
				
			ResultSetMetaData rsmd;
			rsmd = rs.getMetaData();
			rsmd.getColumnCount();
						
			int totalColumn = rsmd.getColumnCount();			
			
			while (rs.next()){
				int colId = 1;
				
				m = new LinkedHashMap<String, Object>();
				
				while ( colId <= totalColumn ){
					String colName = rsmd.getColumnName(colId);
					Object colVal = rs.getObject(colId);
					m.put(colName, colVal);
					colId++;
				}
				responseData.add(m);
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("we have sql exception");
		}finally{
			if (stmt != null){try {
				
				stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("we have sql exception");
			}}
		}
		
		return responseData;
	}
	
}
