package sqlQuery;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class SqlQuery {
	
	private String calcType;
	private String startDate;
	private String endDate;	
	//private Array stationArray;
	private Connection myconn; 
	private String queryString;
	private ArrayList<String> typeName = new ArrayList<String>(Arrays.asList( "vis","cldh","ta","tdew","rh","ws","wd"));
	private ArrayList<String> stationList = new ArrayList<String>(Arrays.asList("30791", "30001", "30002", "30003"));
	private ArrayList<String> allowedCalcType = new ArrayList<String>(Arrays.asList( "main","extend","status","fogStart")); 
	private Statement stmt = null;
	private FunctionName fn;
		
	/**
	 * Конструктор по умолчанию
	 */
	public SqlQuery(){};
	
	/**
	 * Конструктор с параметрами
	 * @param inconnection Connection
	 * @param incalcType String тип расчета
	 * @param instartDate String начальная дата
	 * @param inendDate String конечная дата
	 */
	public SqlQuery(Connection inconnection,
					String incalcType,
					String instartDate,
					String inendDate){
		myconn = inconnection;
		this.setCalcType(incalcType);
		startDate = instartDate;
		endDate = inendDate;
		
		fn = new FunctionName(calcType);
	}

	/**
	 * Конструктор с параметрами
	 * @param inconnection Connection соединение
	 * @param instartDate String начальная дата
	 * @param inendDate String конечная дата
	 */
	public SqlQuery(Connection inconnection,
					String instartDate,
					String inendDate) {
		
		myconn = inconnection;
		startDate = instartDate;
		endDate = inendDate;
	}
	
	/**
	 * Установка типа расчета
	 * @param incalcType String тип расчета
	 */
	public void setCalcType(String incalcType){
		if (allowedCalcType.indexOf(incalcType) != -1){
			calcType = incalcType;
			
			fn = new FunctionName(calcType);
		}else{
			calcType = null;
		}
	}
		
	/**
	 * Запуск расчета с одновременной установкой типа
	 * @param inType String тип расчета
	 */
	public void makeCalc(String inType){
	
		this.setCalcType(inType);
		this.makeCalc();
	}
	
	/**
	 * Запуск расчета статистики
	 */
	public void makeCalc(){
		
		if (calcType == allowedCalcType.get(0)){
			this.mainCalc();
		}else if (calcType == allowedCalcType.get(1)){
			this.extendCalc();
		}else if (calcType == allowedCalcType.get(2)){
			this.statusCalc();
		}else if (calcType == allowedCalcType.get(3)){
			this.fogStartCalc();
		}
	}
	
	private void fogStartCalc() {
		
		try {
			stmt = myconn.createStatement();
			
				queryString = "SELECT "+fn.getName("fogStart")+"('status','30791', 36,1000,'"+startDate+"','"+endDate+"')";
				System.out.println(queryString);
				stmt.executeQuery(queryString);
			
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

	private void mainCalc() {
		
		try {
			stmt = myconn.createStatement();
			for (String station: stationList){ 
				for (String meteoParam: typeName){
					
					queryString = "SELECT "+fn.getName(meteoParam)+"('"+station+"', 24, '"+startDate+"','"+endDate+"' )";
					System.out.println(queryString);
					stmt.executeQuery(queryString);
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
	
	private void extendCalc(){
		
		try {
			//TASK: переделать под циклы
			for(String station: stationList){
				stmt = myconn.createStatement();
				queryString = "SELECT "+fn.getName(calcType)+"('"+station+"','vis',3000, 24, '"+startDate+"','"+endDate+"')";
				System.out.println(queryString);
				stmt.executeQuery(queryString);
				queryString = "SELECT "+fn.getName(calcType)+"('"+station+"','vis',1000, 24, '"+startDate+"','"+endDate+"' )";
				System.out.println(queryString);
				stmt.executeQuery(queryString);
				queryString = "SELECT "+fn.getName(calcType)+"('"+station+"','vis',800, 24, '"+startDate+"','"+endDate+"' )";
				System.out.println(queryString);
				stmt.executeQuery(queryString);
				queryString = "SELECT "+fn.getName(calcType)+"('"+station+"','cldh',60, 24, '"+startDate+"','"+endDate+"' )";
				System.out.println(queryString);
				stmt.executeQuery(queryString);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if (stmt != null){try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}}
		}	
	}
	
	private void statusCalc(){
 
		try {
			//TASK: переделать под циклы
			stmt = myconn.createStatement();
			queryString = "SELECT "+fn.getName(calcType)+"('30791','vis',1000, 36, '"+startDate+"','"+endDate+"' )";
			System.out.println(queryString);
			stmt.executeQuery(queryString);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if (stmt != null){try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}}
		}	
	}

}
