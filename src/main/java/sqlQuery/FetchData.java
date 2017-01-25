package sqlQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FetchData {

	Connection myConn = null;
	Statement stmt = null;
	String startDate = null;
	String endDate = null;
	ArrayList<String> stationList = new ArrayList<String>(Arrays.asList("30791","30001","30002","30003"));
	
	/**
	 * �����������
	 * @param inConn ������������� ����������� � ��
	 */
	public FetchData(Connection inConn){
		myConn = inConn;
	}

	/**
	 * �����������
	 * @param inConn ������������� ����������� � ��
	 * @param inStartDate ���� ������ �������
	 * @param inEndDate ���� ��������� �������
	 */
	public FetchData(Connection inConn,
						String inStartDate,
						String inEndDate){
		myConn = inConn;
		startDate = inStartDate;
		endDate = inEndDate;
	}
	
	/**
	 * �������� ������ � ��������� ��������� ��������
	 */
	public Map<String, Object> getTsh(){
		
		String queryString = null;
		
		Map<String, Object> responseData = new HashMap<String, Object>();
		
		try {
			stmt = myConn.createStatement();
			
			queryString = "SELECT * FROM tsh ";
			
			ResultSet rs = stmt.executeQuery(queryString);
				
			while (rs.next()){
				String currName = null;
				Integer currValue = null;
				
				currName = rs.getString(1);
				currValue = rs.getInt(2);
				
				//������������ ���� ���
				int ind = 0;
				ind = currName.indexOf(" ");
				if (ind > 0){
					currName = currName.substring(0,ind);
				}
				
				responseData.put(currName, currValue);
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
	 * �������� ������ ��������� �������
	 */
	public ArrayList<ArrayList<Map<String, Object>>> getMainStat(){
		
		ArrayList<ArrayList<Map<String, Object>>> responseData = new ArrayList<ArrayList<Map<String, Object>>>();
		
		for(String station: stationList){
		
			String queryString = "SELECT * FROM lt_estim_res "
						+ "WHERE indate = '"+startDate+"' AND outdate = '"+endDate+"' AND station= '"+station+"'"
								+ "AND period = 24 ";
			
			responseData.add(this.makeSimpleQ(queryString));	
		}
		
		return responseData;
	}

	/**
	 * �������� ������ ����������� ����������
	 * @return ���������� ������ 
	 */
	public ArrayList<ArrayList<Map<String, Object>>> getExtendStat(){
		
		ArrayList<ArrayList<Map<String, Object>>> responseData = new ArrayList<ArrayList<Map<String, Object>>>();
		
		for(String station: stationList){
		
			String queryString = "SELECT * FROM lt_estim_res_stat "
				+ "WHERE indate = '"+startDate+"' AND outdate = '"+endDate+"'AND station= '"+station+"'"
						+ " AND period = 24 ";
			
			responseData.add(this.makeSimpleQ(queryString));
		}
		
		return responseData;
	}
		
	/**
	 * 	�������� ������ ���������� �� �������
	 */
	public ArrayList<Map<String, Object>> getStatusStat(){
		
		String queryString = "SELECT * FROM lt_estim_res_stat_status "
			+ "WHERE indate = '"+startDate+"' AND outdate = '"+endDate+"' "
					+ " AND period = 36 ";
		
		return this.makeSimpleQ(queryString);		
	}
	
	/**
	 * �������� ������ ���������� �� ������� ������� ������ ������
	 * @return ����������� ������
	 */
	public ArrayList<Map<String, Object>> getStartFogStat(){
		
		String queryString = "SELECT * FROM lt_fog_start_stat "
				+ "WHERE indate = '"+startDate+"' AND outdate = '"+endDate+"' ";
		
		return this.makeSimpleQ(queryString);
	}
		
	/**
	 * ���������� ������� � ������������ ���������� � ������
	 * @param q - ������ � ��������
	 * @return - ArrayList<Map<String, Object>> - ������ ���������
	 */
	private ArrayList<Map<String, Object>> makeSimpleQ(String q){
		
		ArrayList<Map<String, Object>> responseData = new ArrayList<Map<String, Object>>();
		
		try {
			stmt = myConn.createStatement();
			Map<String, Object> m;
			
			ResultSet rs = stmt.executeQuery(q);
				
			ResultSetMetaData rsmd;
			rsmd = rs.getMetaData();
			rsmd.getColumnCount();
						
			int totalColumn = rsmd.getColumnCount();			
			
			while (rs.next()){
				int colId = 1;
				
				m = new HashMap<String, Object>();
				
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
