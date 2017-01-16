package sqlQuery;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class SqlQuery {
	
	private String location;
	private String dataType;
	private String startDate;
	private String endDate;	
	private Array stationArray;
	private Connection myconn; 
	private ArrayList<String> typeName = new ArrayList<String>(Arrays.asList( "vis","cldh","ta","tdew","rh","ws","wd")); 
	
	// конструктор
	public SqlQuery(Connection inconnection,
					String inlocation,
					String indataType,
					String instartDate,
					String inendDate){
		myconn = inconnection;
		location = inlocation;
		dataType = indataType;
		startDate = instartDate;
		endDate = inendDate;
	}
	
	//расчет основного блока статистики
	public void MainCalc() throws SQLException{
		String queryString;
		
		FunctionName fn = new FunctionName();
		Statement stmt = null;
		try {
			stmt = myconn.createStatement();
			for (String meteoParam: typeName){
				queryString = "SELECT "+fn.getName(meteoParam)+"('30791', 24, "+startDate+","+endDate+", )";
				
				ResultSet rs = stmt.executeQuery(queryString);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if (stmt != null){stmt.close();}
		}
		
		
		
		
	}
	
	

}
