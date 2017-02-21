package sqlQuery;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Создание и удаление view для раскидывания данных по корзинам
 * @author Павел А
 *
 */
public class BasketView {

	public final int MAIN = 1;
	public final int ADDITION = 2;
	
	private boolean isMainCons = false;
	private boolean isAdditionCons = false;
	
	private Connection myConn;
	private String startDate;
	private String endDate;
	
	/**
	 * Конструктор с параметрами
	 * @param inConn Connection
	 * @param inStartDate String
	 * @param inEndDate String
	 */
	public BasketView(Connection inConn,
			String inStartDate,
			String inEndDate){
		myConn = inConn;
		startDate = inStartDate;
		endDate = inEndDate;
	}
	
	/**
	 * Получаем запрос создания view для простой фильтрации данных по корзинам
	 * @param inType String - тип view (есть основная и вспомогательная)
	 */
	private String getQuery(int inType){
		
		String query = "";
		
		switch(inType){
		case MAIN:
			query = "CREATE VIEW status AS "
			+ "SELECT lt_estim_status.timemessage,lt_estim_status.station, lt_estim_30791.tmstart, lt_estim_30791.factvalue,lt_estim_status.mainmessage "
			+ "FROM lt_estim_status "
			+ "LEFT JOIN lt_estim_30791 ON " 
			+ " lt_estim_status.station = lt_estim_30791.station "
			+ " WHERE " 
			+ " lt_estim_30791.station = '30791' " 
			+ " AND lt_estim_30791.param = 'Vis' "
			+ " AND lt_estim_30791.tmstart >= lt_estim_status.timemessage " 
			+ " AND (lt_estim_30791.tmstart - lt_estim_status.timemessage <= '06:00:00') " 
			+ " AND DATE(lt_estim_status.timemessage) >= '"+ startDate +"' " 
			+ " AND DATE(lt_estim_status.timemessage) <= '"+ endDate +"' "
			+ " ORDER BY lt_estim_status.timemessage,lt_estim_30791.tmstart";
			break;
		case ADDITION:
			query = "CREATE VIEW status_a AS " 
			+ " SELECT distinct(timemessage) "
			+ " FROM status "
			+ " WHERE " 
			+ " status.tmstart >= status.timemessage " 
			+ " AND (status.tmstart - status.timemessage <= '06:00:00') " 
			+ " AND status.factvalue <= 1000 "
			+ " AND status.mainmessage = 'ожидается туман' ";
			break;
		}
	
		return query;
	}
	
	/**
	 * Создаем view
	 * @param inType final int - тип view
	 */
	public void makeView(int inType){
		
		String query;
		boolean isError;
		
//		this.delView(ADDITION);
//		this.delView(MAIN);
		
		switch(inType){
		case MAIN:
			
			if (!isMainCons){
				query = this.getQuery(MAIN);
				
				isError = this.updateQ(query);
				if (!isError){
					isMainCons = true;
				}
			}
			break;
		case ADDITION:
			
			if(!isAdditionCons){
				query = this.getQuery(ADDITION);
				isError = this.updateQ(query);
				if (!isError){
					isAdditionCons = true;
				}
			}
			break;
		}
	}
	
	/**
	 * Удаляем view
	 * @param inType int final какой view удаляем
	 */
	public void delView(int inType){
		String query;
		boolean isError;
		
		switch(inType){
		case MAIN:
			
			query = "DROP VIEW status";
			
			isError = this.updateQ(query);
			if (!isError){
				isMainCons = false;
			}
			break;
		case ADDITION:
			
			query = "DROP VIEW status_a";
			isError = this.updateQ(query);
			if (!isError){
				isAdditionCons = false;
			}
			
			break;
		}
	}
	
	/**
	 * Выполнения запроса, где ожидается результат типа void
	 * @param q строка с запросом
	 * @return boolean успешно ли прошел запрос
	 */
 	private boolean updateQ(String q){
 		//System.out.println(q);
		boolean isError = false;
		Statement stmt = null;
		
		try {
			stmt = myConn.createStatement();
			stmt.executeUpdate(q);
		} catch (SQLException e) {
			e.printStackTrace();
			String status = e.getSQLState();
			if (status.indexOf("42P01") == 0 ){
					System.out.println("dont have such table");
			}else{
				System.out.println(e.getSQLState());
				System.out.println("we have sql exception");
			}
		}finally{
			if (stmt != null){
				try {
				
				stmt.close();
				isError = true;
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("we have sql/stmt exception");
			}}
		}
		return isError;
	}
	
}

