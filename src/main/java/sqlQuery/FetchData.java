package sqlQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FetchData {

	Connection myConn = null;
	Statement stmt = null;
	String startDate = null;
	String endDate = null;
	
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
	public ArrayList<Map<String, Integer>> getTsh(){
		
		String queryString = null;
		
		ArrayList<Map<String, Integer>> responseData = new ArrayList<Map<String, Integer>>();
		
		try {
			stmt = myConn.createStatement();
			Map<String, Integer> m =  new HashMap<String, Integer>();
			
			queryString = "SELECT * FROM tsh ";
			System.out.println(queryString);
			ResultSet rs = stmt.executeQuery(queryString);
				
			while (rs.next()){
				String currName = null;
				Integer currValue = null;
				
				currName = rs.getString(1);
				currValue = rs.getInt(2);
				
				m.put(currName, currValue);
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
	
	/**
	 * Получить данные основного расчета
	 */
	public ArrayList<Map<String, Object>> getMainStat(){
		
		String queryString = "SELECT * FROM lt_estim_res "
					+ "WHERE indate = '"+startDate+"' AND outdate = '"+endDate+"' ";
			
		return this.makeSimpleQ(queryString);
	}

	/**
	 * Получить данные расширенной статистики
	 * @return упакованые данные 
	 */
	public ArrayList<Map<String, Object>> getExtendStat(){
		
		String queryString = "SELECT * FROM lt_estim_res_stat "
			+ "WHERE indate = '"+startDate+"' AND outdate = '"+endDate+"' ";
		
		return this.makeSimpleQ(queryString);
	}
	
	/**
	 * 	Получить данные статистики по статусу
	 */
	public ArrayList<Map<String, Object>> getStatusStat(){
		
		String queryString = "SELECT * FROM lt_estim_res_stat_status "
				+ "WHERE indate = '"+startDate+"' AND outdate = '"+endDate+"' ";
		
		return this.makeSimpleQ(queryString);
	}
	
	/**
	 * Получить данные статистики по расчету времени начала тумана
	 * @return упакованные данные
	 */
	public ArrayList<Map<String, Object>> getStartFogStat(){
		
		String queryString = "SELECT * FROM lt_fog_start_stat "
				+ "WHERE indate = '"+startDate+"' AND outdate = '"+endDate+"' ";
		
		return this.makeSimpleQ(queryString);
	}
		
	/**
	 * Выполнение запроса и сворачивание результата в список
	 * @param q - строка с запросом
	 * @return - ArrayList<Map<String, Object>> - данные упакованы
	 */
	private ArrayList<Map<String, Object>> makeSimpleQ(String q){
		
		ArrayList<Map<String, Object>> responseData = new ArrayList<Map<String, Object>>();
		
		try {
			stmt = myConn.createStatement();
			Map<String, Object> m =  new HashMap<String, Object>();
			
			ResultSet rs = stmt.executeQuery(q);
				
			ResultSetMetaData rsmd;
			rsmd = rs.getMetaData();
			rsmd.getColumnCount();
						
			int totalColumn = rsmd.getColumnCount();			
			
			while (rs.next()){
				int colId = 1;
				
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
