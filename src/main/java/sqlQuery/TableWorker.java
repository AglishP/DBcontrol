package sqlQuery;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class TableWorker {

	private Connection myConn;
	private Statement stmt = null;
	private String queryString;
	private String startDate;
	private String endDate;
	private ArrayList<String> stationList = new ArrayList<String>(Arrays.asList( "30791","30001","30002","30003")); 
	private ArrayList<String> dayHourList = new ArrayList<String>(Arrays.asList("22","23","0","1","2","3","4","5","6","7","8"));
	private ArrayList<String> nightHourList = new ArrayList<String>(Arrays.asList("9","10","11","12","13","14","15","16","17","18","19","20","21"));
			
	
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
	
	public void load(String inTableType, String inPathOfDay){
		
		String tableName = null;
		String tableSource = null;
		
		try {
			stmt = myConn.createStatement();
			
			if (inPathOfDay == "day"){
				tableSource = "_"+inPathOfDay;
			}else if (inPathOfDay == "night"){
				tableSource = "_"+inPathOfDay;
			}
			
			
			if (inTableType == "main"){
				
				tableName = "lt_estim_";
				
				for (String stationId: stationList){
				
					queryString = "INSERT INTO "+tableName+stationId+" SELECT * FROM "+tableName+stationId+tableSource+" "
							+ "WHERE DATE(tmstart)>='"+startDate+"' AND DATE(tmstart)<='"+endDate+"'";
					System.out.println(queryString);
					stmt.executeQuery(queryString);
				}
				
			}else if(inTableType == "status"){
				
				tableName = "lt_estim_status";
				
				queryString = "INSERT INTO "+tableName+" SELECT * FROM "+tableName+tableSource+""
						+ "WHERE DATE(tmstart)>='"+startDate+"' AND DATE(tmstart)<='"+endDate+"'";
				System.out.println(queryString);
				stmt.executeQuery(queryString);
				
			}else if(inTableType == "fogStart"){
				
				tableName = "fog_analyze";
				
				queryString = "INSERT INTO "+tableName+" SELECT * FROM "+tableName+tableSource+""
						+ "WHERE DATE(tmstart)>='"+startDate+"' AND DATE(tmstart)<='"+endDate+"'";
				System.out.println(queryString);
				stmt.executeQuery(queryString);
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
	
	public void del(String inTableType){
		
		String tableName = null;
		
		try {
			stmt = myConn.createStatement();
			
			if (inTableType == "main"){
				
				tableName = "lt_estim_";
				
				for (String stationId: stationList){
				
					queryString = "DELETE FROM "+tableName+stationId+" "
							+ "WHERE DATE(tmstart)>='"+startDate+"' AND DATE(tmstart)<='"+endDate+"'";
					System.out.println(queryString);
					stmt.executeQuery(queryString);
				}
				
			}else if(inTableType == "status"){
				
				tableName = "lt_estim_status";
				
				queryString = "DELETE FROM "+tableName+" "
						+ "WHERE DATE(tmstart)>='"+startDate+"' AND DATE(tmstart)<='"+endDate+"'";
				System.out.println(queryString);
				stmt.executeQuery(queryString);
				
			}else if(inTableType == "fogStart"){
				
				tableName = "fog_analyze";
				
				queryString = "DELETE FROM "+tableName+" "
						+ "WHERE DATE(tmstart)>='"+startDate+"' AND DATE(tmstart)<='"+endDate+"'";
				System.out.println(queryString);
				stmt.executeQuery(queryString);
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
	
	//разделяем таблицу на подтаблицы с отдельными данными день/ночь. 
	//все делается в фиклах, сначала в таблицу день с соответствующими часами, потом ночь
	public void sep(String inTableType){
		
		String tableName = null;
		
		String[] listPathOfDay = {"day","night"};
		
		try {
			stmt = myConn.createStatement();
		
				if (inTableType == "main"){
					
					for (String typePathOfDay: listPathOfDay){
					
						tableName = "lt_estim_";
						
						for (String stationId: stationList){
							
							ArrayList<String> hourList;
							if (typePathOfDay == listPathOfDay[0]){
								hourList = dayHourList;
							}else{
								hourList = nightHourList;
							}
							
							for (String currHour: hourList){
						
								queryString = "INSERT INTO "+tableName+stationId+typePathOfDay+" SELECT * FROM "+tableName+stationId+" "
											+ "AND execute(hour from tmstart) = "+currHour+" "
											+ "WHERE DATE(tmstart)>='"+startDate+"' AND DATE(tmstart)<='"+endDate+"'";
								System.out.println(queryString);
								stmt.executeQuery(queryString);
							}
						}
					}
					
				}else if(inTableType == "status"){
					
					tableName = "lt_estim_status";
					
					for (String typePathOfDay: listPathOfDay){
						
						ArrayList<String> hourList;
						if (typePathOfDay == listPathOfDay[0]){
							hourList = dayHourList;
						}else{
							hourList = nightHourList;
						}
					
						for (String currHour: hourList){
						
							queryString = "INSERT INTO "+tableName+typePathOfDay+" SELECT * FROM "+tableName+" "
									+ "AND execute(hour from tmstart) = "+currHour+" "
									+ "WHERE DATE(tmstart)>='"+startDate+"' AND DATE(tmstart)<='"+endDate+"'";
							System.out.println(queryString);
							stmt.executeQuery(queryString);
						}
					}
					
				}else if(inTableType == "fogStart"){
					
					tableName = "fog_analyze";
					
					for (String typePathOfDay: listPathOfDay){
						
						ArrayList<String> hourList;
						if (typePathOfDay == listPathOfDay[0]){
							hourList = dayHourList;
						}else{
							hourList = nightHourList;
						}
					
						for (String currHour: hourList){
						
							queryString = "INSERT INTO "+tableName+typePathOfDay+" SELECT * FROM "+tableName+" "
									+ "AND execute(hour from tmstart) = "+currHour+" "
									+ "WHERE DATE(tmstart)>='"+startDate+"' AND DATE(tmstart)<='"+endDate+"'";
							System.out.println(queryString);
							stmt.executeQuery(queryString);
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
