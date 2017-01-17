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
	private ArrayList<String> allowedCalcType = new ArrayList<String>(Arrays.asList( "main","extend","status","fogStart")); 
	private Statement stmt = null;
	private FunctionName fn;
		
	// �����������
	public SqlQuery(){};
	
	// �����������
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

	public void setCalcType(String incalcType){
		if (allowedCalcType.indexOf(incalcType) != -1){
			calcType = incalcType;
		}else{
			calcType = null;
		}
	}
		
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
			for (String meteoParam: typeName){
				queryString = "SELECT "+fn.getName(meteoParam)+"('status','30791', 36,1000,"+startDate+","+endDate+", )";
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

	private void mainCalc() {
		
		try {
			stmt = myconn.createStatement();
			for (String meteoParam: typeName){
				queryString = "SELECT "+fn.getName(meteoParam)+"('30791', 24, "+startDate+","+endDate+", )";
				
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
	
	private void extendCalc(){
		
		try {
			//TASK: ���������� ��� �����
			stmt = myconn.createStatement();
			queryString = "SELECT "+fn.getName(calcType)+"('30791','vis',3000, 24, "+startDate+","+endDate+", )";
			stmt.executeQuery(queryString);
			queryString = "SELECT "+fn.getName(calcType)+"('30791','vis',1000, 24, "+startDate+","+endDate+", )";
			stmt.executeQuery(queryString);
			queryString = "SELECT "+fn.getName(calcType)+"('30791','vis',800, 24, "+startDate+","+endDate+", )";
			stmt.executeQuery(queryString);
			queryString = "SELECT "+fn.getName(calcType)+"('30791','cldh',60, 24, "+startDate+","+endDate+", )";
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
	
	private void statusCalc(){
 
		try {
			//TASK: ���������� ��� �����
			stmt = myconn.createStatement();
			queryString = "SELECT "+fn.getName(calcType)+"('30791','vis',800, 36, "+startDate+","+endDate+", )";
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
