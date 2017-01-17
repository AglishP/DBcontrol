package sqlQuery;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableWorker {

	private Connection myConn;
	private Statement stmt = null;
	private String queryString;
	
	public TableWorker(){}
	
	public TableWorker(Connection inConnect){
		myConn = inConnect;
	}
	
	public void load(){
		
		
		try {
			stmt = myConn.createStatement();
			
				queryString = "";
				
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
	
	public void del(){
		
	}
	
	public void sep(){
		
	}
	
}
