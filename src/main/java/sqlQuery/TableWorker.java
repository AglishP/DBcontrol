package sqlQuery;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Облаботка данных в таблицах: удаление, разделение, загрузка
 * @author Павел А.
 *
 */
public class TableWorker {

	private Connection myConn;
	private Statement stmt = null;
	private String queryString;
	private String startDate;
	private String endDate;
	private ArrayList<String> stationList = new ArrayList<String>(Arrays.asList( "30791","30001","30002","30003")); 
	private ArrayList<String> dayHourList = new ArrayList<String>(Arrays.asList("22","23","0","1","2","3","4","5","6","7","8"));
	private ArrayList<String> nightHourList = new ArrayList<String>(Arrays.asList("9","10","11","12","13","14","15","16","17","18","19","20","21"));
	
	final static public String MAIN = "main";
	final static public String STATUS = "status";
	final static public String FOGSTART = "fogstart";
	final static public String DAY = "day";
	final static public String NIGHT = "night";
	final static public String ALLDAY = "ALL";
	
	//Конструкторы
	public TableWorker(){}
	
	public TableWorker(Connection inConnect){
		myConn = inConnect;
	}

	public TableWorker(Connection inConnect,
						String inStartDate,
						String inEndDate){
		myConn = inConnect;
		startDate = inStartDate;
		endDate = inEndDate;
	}
	
	/**
	 * Выставляем значения периода
	 * @param inStartDate String
	 * @param inEndDate String
	 */
	public void setStartEndDate(String inStartDate, String inEndDate){
		this.startDate = inStartDate;
		this.endDate = inEndDate;
	}
	
	
	/**
	 * Загружаем данные в основную таблицу из подтаблиц день/ночь
	 * @param inTableType String
	 * @param inPathOfDay String
	 */
	public void load(String inTableType, String inPathOfDay){
		
		String tableName = null;
		String tableSource = null;
		
		try {
			stmt = myConn.createStatement();
			
			if ( TableWorker.DAY.contentEquals(inPathOfDay) ){
				tableSource = "_"+inPathOfDay;
			}else if ( TableWorker.NIGHT.contentEquals(inPathOfDay) ){
				tableSource = "_"+inPathOfDay;
			}
			
			
			if ( inTableType.contentEquals(MAIN) ){
				
				tableName = "lt_estim_";
				
				for (String stationId: stationList){
				
					queryString = "INSERT INTO "+tableName+stationId+" SELECT * FROM "+tableName+stationId+tableSource+" "
							+ "WHERE DATE(tmstart)>='"+startDate+"' AND DATE(tmstart)<='"+endDate+"'";
					System.out.println(queryString);
					stmt.executeUpdate(queryString);
				}
				
			}else if( inTableType.contentEquals(STATUS) ){
				
				tableName = "lt_estim_status";
				
				queryString = "INSERT INTO "+tableName+" SELECT * FROM "+tableName+tableSource+" "
						+ "WHERE DATE(timemessage)>='"+startDate+"' AND DATE(timemessage)<='"+endDate+"'";
				System.out.println(queryString);
				stmt.executeUpdate(queryString);
				
			}else if( inTableType.contentEquals(FOGSTART) ){
				
				tableName = "fog_analyze";
				
				queryString = "INSERT INTO "+tableName+" SELECT * FROM "+tableName+tableSource+""
						+ " WHERE DATE(tmstart)>='"+startDate+"' AND DATE(tmstart)<='"+endDate+"'";
				System.out.println(queryString);
				stmt.executeUpdate(queryString);
			}
			
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
	 * Удаляем данные из таблицы
	 * @param inTableType String
	 */
	public void del(String inTableType){
		
		String tableName = null;
		
		try {
			stmt = myConn.createStatement();
			
			if ( inTableType.contentEquals(MAIN) ){
				
				tableName = "lt_estim_";
				
				for (String stationId: stationList){
				
					queryString = "DELETE FROM "+tableName+stationId+" "
							+ "WHERE DATE(tmstart)>='"+startDate+"' AND DATE(tmstart)<='"+endDate+"'";
					System.out.println(queryString);
					stmt.executeUpdate(queryString);
				}
				
			}else if( inTableType.contentEquals(STATUS) ){
				
				tableName = "lt_estim_status";
				
				queryString = "DELETE FROM "+tableName+" "
						+ "WHERE DATE(timemessage)>='"+startDate+"' AND DATE(timemessage)<='"+endDate+"'";
				System.out.println(queryString);
				stmt.executeUpdate(queryString);
				
			}else if( inTableType.contentEquals(FOGSTART) ){
				
				tableName = "fog_analyze";
				
				queryString = "DELETE FROM "+tableName+" "
						+ "WHERE DATE(tmstart)>='"+startDate+"' AND DATE(tmstart)<='"+endDate+"'";
				System.out.println(queryString);
				stmt.executeUpdate(queryString);
			}
			
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
	 * разделяем таблицу на подтаблицы с отдельными данными день/ночь. 
	 * все делается в фиклах, сначала в таблицу день с соответствующими часами, потом ночь
	 * @param inTableType String
	 */
	public void sep(String inTableType){
		
		String tableName = null;
		
		String[] listPathOfDay = {DAY,NIGHT};
		
		try {
			stmt = myConn.createStatement();
		
				if ( inTableType.contentEquals(MAIN) ){
					
					for (String typePathOfDay: listPathOfDay){
					
						tableName = "lt_estim_";
						
						for (String stationId: stationList){
							
							//удаляем значения из таблицы, в которую делаем insert
							queryString = "DELETE FROM "+tableName+stationId+"_"+typePathOfDay+" "
									+ "WHERE DATE(tmstart)>='"+startDate+"' AND DATE(tmstart)<='"+endDate+"'";
							stmt.executeUpdate(queryString);
							
							ArrayList<String> hourList;
							if (typePathOfDay.contentEquals(listPathOfDay[0])){
								hourList = dayHourList;
							}else{
								hourList = nightHourList;
							}
							
							for (String currHour: hourList){
								
								queryString = "INSERT INTO "+tableName+stationId+"_"+typePathOfDay+" SELECT * FROM "+tableName+stationId+" "
											+ "WHERE extract(hour from tmstart) = "+currHour+" "
											+ "AND DATE(tmstart)>='"+startDate+"' AND DATE(tmstart)<='"+endDate+"'";
								System.out.println(queryString);
								stmt.executeUpdate(queryString);
							}
						}
					}
					
				}else if( inTableType.contentEquals(STATUS)){
					
					tableName = "lt_estim_status";
					
					for (String typePathOfDay: listPathOfDay){
						
						//удаляем значения из таблицы, в которую делаем insert
						queryString = "DELETE FROM "+tableName+"_"+typePathOfDay+" "
								+ "WHERE DATE(timemessage)>='"+startDate+"' AND DATE(timemessage)<='"+endDate+"'";
						stmt.executeUpdate(queryString);
						
						ArrayList<String> hourList;
						if (typePathOfDay.contentEquals(listPathOfDay[0])){
							hourList = dayHourList;
						}else{
							hourList = nightHourList;
						}
					
						for (String currHour: hourList){
							
							queryString = "INSERT INTO "+tableName+"_"+typePathOfDay+" SELECT * FROM "+tableName+" "
									+ "WHERE extract(hour from timemessage) = "+currHour+" AND"
									+ " DATE(timemessage)>='"+startDate+"' AND DATE(timemessage)<='"+endDate+"'";
							System.out.println(queryString);
							stmt.executeUpdate(queryString);
						}
					}
					
				}else if( inTableType.contentEquals(FOGSTART) ){
					//вообще время начала тумана пока не разделяется
					tableName = "fog_analyze";
					
					for (String typePathOfDay: listPathOfDay){
						
						ArrayList<String> hourList;
						if (typePathOfDay.contentEquals(listPathOfDay[0])){
							hourList = dayHourList;
						}else{
							hourList = nightHourList;
						}
					
						for (String currHour: hourList){
						
							queryString = "INSERT INTO "+tableName+"_"+typePathOfDay+" SELECT * FROM "+tableName+" "
									+ "WHERE extract(hour from tmstart) = "+currHour+" "
									+ "AND DATE(tmstart)>='"+startDate+"' AND DATE(tmstart)<='"+endDate+"'";
							System.out.println(queryString);
							stmt.executeUpdate(queryString);
						}
					}
				}
			
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
	
}
