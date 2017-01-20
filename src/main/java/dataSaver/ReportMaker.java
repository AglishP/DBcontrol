package dataSaver;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import sqlQuery.FetchData;
import starter.DateBuilder;

public class ReportMaker {
	
	Connection myConn;
	String calcType;
	String startDate;
	String endDate;
	FetchData fd = null;
	
//	final ArrayList<String> tshParam = new ArrayList<String>(Arrays.asList("Cldh1Lvl","Cldh1Tsh","Cldh2Lvl","Cldh2Tsh", ""
//			+ "Rh","RhLvl","Ta","Tdew","Vis1Lvl","Vis1Tsh","Vis2Lvl","Vis2Tsh","Wd","Wd_LowLvl","Ws","Ws_LowLvl")); 
	
	/**
	 * �����������
	 * @param inConn Connection
	 * @param inType String ��� ������, ������� �����
	 * @param inStartDate String
	 * @param inEndDate String
	 */
	public ReportMaker(Connection inConn,
					String inType,
					String inStartDate,
					String inEndDate){
		myConn = inConn;
		calcType = inType;
		startDate = inStartDate;
		endDate = inEndDate;
		fd = new FetchData(myConn, startDate, endDate);
	}
	
	/**
	 * ����������� �� ���������
	 */
	public ReportMaker(){
		fd = new FetchData(myConn);
	}
	
	/**
	 * ��������� ����� ������ ������
	 * @return list map c �������
	 */
	public ArrayList<Map <String, Integer>> getTsh(){
				
		//�������� ������ � ��������� ���������
		return fd.getTsh();		
	}
	
	/**
	 * �������� ������ ��� ��������� � ��������� �����
	 * @return list map � �������
	 */
	public Map<String, String> getHeadData(){
				
		//��������� ���������� ������� ��� ���������
		Map<String, String> m = new HashMap<String, String>();
		
		m.put("StartDate", startDate);
		//commonHeadData.add(m);
		//m.clear();
		m.put("EndDate", endDate);
		//commonHeadData.add(m);
		//m.clear();
		DateBuilder db = new DateBuilder("curr");
		String currDate = db.getCurrDate();
		m.put("CurrDate", currDate);
		//commonHeadData.add(m);
		//m.clear();
		String currTime = db.getCurrTime();
		m.put("CurrTime", currTime);
			
		return m;
	}
	
	/**
	 * �������� ������ �������� ����������
	 */
	public ArrayList<Map<String, Object>> getMainData(){
		
		return fd.getMainStat();
	}
	
	/**
	 * �������� ������ � �������
	 * @return ���������� ������
	 */
	public ArrayList<Map<String, Object>> getStatusData(){
		
		return fd.getStatusStat();
	}
	
	/**
	 * �������� ������ � ���������� ������� ������ ������
	 * @return ���������� ������
	 */
	public ArrayList<Map<String, Object>> getStartFogData(){
		
		return fd.getStartFogStat();
	}

	/**
	 * ������� ��� ��� �����
	 * @param inPathOfDay String ����� �����, ��� ����� � ����� �����
	 * @return ������ � ������ ������ �����
	 */
	public String nameFileCreator(String inPathOfDay){
		//����� �����: 2016-01-01 2016-12-31 build-2017-01-20 10-18 4�-BD
		
		StringBuilder sb = new StringBuilder();
		
		//�������� ����� ������
		Map<String, String> commonData = new HashMap<String, String>(); 
		commonData = this.getHeadData();
		
		sb.append( commonData.get("StartDate"));
		sb.append(" ");
		sb.append( commonData.get("EndDate"));
		sb.append(" build-");
		sb.append( commonData.get("CurrDate") );
		sb.append(" ");
		sb.append( commonData.get("CurrTime"));
		sb.append(" 4�-BD-");
		sb.append(inPathOfDay);
		sb.append(".txt");
		
		return sb.toString();
	}

	
	public void writeHeadFile(){
		//�������� ��� ����� ��� ������
		
		//���������� ����� ������
		
		//������� ���� � ����� � ���� ����� ������
		
		
		
		//�������� ��������� ������
		
		
		
		//��������� ������ � ���������� �������
		
		
		
		//���������� ������ ��������� ������
		
		//�������� �����
		
		
	}
	
	/**
	 * ������������� ����� ��� ������
	 * @return DataSaverClass - ����� ���� ����� � ����
	 */
	public DataSaver createInitFile(String inPathOfDay){
		
		String fileName = this.nameFileCreator(inPathOfDay);
		
		DataSaver dsc = new DataSaver(fileName);
		
		return dsc;	
	}
	
	
	public void writeCommonData(DataSaver inDsc){
		
		//�������� ����� ������
		Map<String, String> headData = this.getHeadData();
		
		//��������� ������ ����� ������
		ArrayList<String> headTemplate = new TemplateData().getHeadTemplate();
		
		//���������� ������ � ��������
		for (String oneString: headTemplate){
			if ( oneString.contains("?") ){
				System.out.println( headData );
				headData.remove(1);
			}
		}
		
		//���������� ������ ����� ������
		
	}

}
