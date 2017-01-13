package starter;

import java.sql.*;

public class ConnectionClass {
	
	Connection connection = null;
	
	public Connection letsConnect(){

		try {
			Class.forName("org.postgresql.Driver");
		}catch (ClassNotFoundException e){
			System.out.println("NO postgresql driver");
			return connection;
		}
		
		
		try {
			connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/LiteTable", "postgres", "MASTER");
		} catch (SQLException e) {
			System.out.println("No Connection");
			return connection;
		}
		
		return connection;
		
	}
	
}
