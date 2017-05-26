package sqlQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * �������� � �������� view ��� ������������ ������ �� ��������
 * @author ����� �
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
	 * ����������� � �����������
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
	 * �������� ������ �������� view ��� ������� ���������� ������ �� ��������
	 * @param inType String - ��� view (���� �������� � ���������������)
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
			+ " AND status.mainmessage = '��������� �����' ";
			break;
		}
	
		return query;
	}
	
	/**
	 * ������� view
	 * @param inType final int - ��� view
	 */
	public void makeView(int inType){
		
		String query;
		boolean isError;
		
		switch(inType){
		case MAIN:
			
			if (this.checkView(MAIN)){	
				if (this.checkView(ADDITION)){
					this.delView(ADDITION);
					isAdditionCons = false;
				}
				this.delView(MAIN);
				isMainCons = false;
			}
			
			if (!isMainCons){
				query = this.getQuery(MAIN);
				
				isError = this.updateQ(query);
				if (!isError){
					isMainCons = true;
				}
			}
			break;
		case ADDITION:
			
			if (this.checkView(ADDITION)){
				this.delView(ADDITION);
			}
			
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
	 * ������� view
	 * @param inType int final ����� view �������
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
	
	private boolean checkView(int inType){
		String query= "";
		ArrayList<String> res;
		boolean checked = false;
		
		switch(inType){
		case MAIN:
			
			query = "SELECT relname FROM pg_class WHERE relname='status'";
			res = this.makeSimpleQ(query);
			
			if ( !res.isEmpty() && res.get(0).equals("status")){
				checked = true;
			}else{
				checked =  false;
			}
			break;
		case ADDITION:
			
			query = "SELECT relname FROM pg_class WHERE relname = 'status_a'";
			res = this.makeSimpleQ(query);

			if ( !res.isEmpty() && res.get(0).equals("status_a")){
				checked = true;
			}else{
				checked = false;
			}
			break;
		}
		
		return checked;
	}
	
	/**
	 * ���������� �������, ��� ��������� ��������� ���� void
	 * @param q ������ � ��������
	 * @return boolean ������� �� ������ ������
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
 	
 	/**
	 * ���������� ������� � ������������ ���������� � ������
	 * @param q - ������ � ��������
	 * @return - ArrayList<String> - ������ ���������
	 */
	private ArrayList<String> makeSimpleQ(String q){
		Statement stmt = null;

		ArrayList<String> stringData = new ArrayList<String>();
		
		try {
			stmt = myConn.createStatement();
			
			ResultSet rs = stmt.executeQuery(q);
				
			ResultSetMetaData rsmd;
			rsmd = rs.getMetaData();
			rsmd.getColumnCount();
						
			int totalColumn = rsmd.getColumnCount();			
			
			while (rs.next()){
				int colId = 1;
				
				StringBuilder sb = new StringBuilder();
				
				while ( colId <= totalColumn ){

					Object colVal = rs.getObject(colId);
					
					sb.append( colVal.toString() );
					
					colId++;
				}
				stringData.add(sb.toString());
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
		
		return stringData;
	}
	
}

