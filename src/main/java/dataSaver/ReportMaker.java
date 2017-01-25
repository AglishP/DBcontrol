package dataSaver;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sqlQuery.FetchData;
import starter.DateBuilder;

public class ReportMaker {
	
	Connection myConn;
	String calcType;
	String startDate;
	String endDate;
	FetchData fd = null;
	DataSaver ds = null;
	
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
		ds = this.createInitFile("DAY");
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
	public Map<String, Object> getTsh(){
				
		//�������� ������ � ��������� ���������
		return fd.getTsh();		
	}
	
	/**
	 * �������� ������ ��� ��������� � ��������� �����
	 * @return list map � �������
	 */
	public HashMap<String, String> getHeadData(){
				
		//��������� ���������� ������� ��� ���������
		HashMap<String, String> m = new HashMap<String, String>();
		
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
	public ArrayList<ArrayList<Map<String, Object>>> getMainData(){
		
		return fd.getMainStat();
	}
	
	/**
	 * �������� ������ �������� ����������
	 */
	public ArrayList<ArrayList<Map<String, Object>>> getExtendData(){
		
		return fd.getExtendStat();
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
		sb.append(" 4�-BD-JAVA_TEST");
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
		
		DataSaver ds = new DataSaver(fileName);
		
		return ds;	
	}
	
	/**
	 * ����� �������� ������ ���������
	 */
	public void writeCommonData(){
		
		//�������� ����� ������
		HashMap<String, String> headData = this.getHeadData();
		
		//��������� ������ ����� ������
		ArrayList<String> headTemplate = new TemplateData().getHeadTemplate();
					
		//���������� ������ � ��������
		for (String oneString: headTemplate){
			
			int indStart = oneString.indexOf("<")+1;
			int indEnd = oneString.indexOf(">");
			if (indStart > 0 || indEnd > 0 ){
				
				//�������� �������� �� Map
				String item = oneString.substring(indStart, indEnd);
				String inputVal = headData.get(item);
				
				StringBuilder newString = new StringBuilder();
				
				//��������� ������ ��� ������ � ����
				newString.append(oneString.substring(0,indStart-1));
				newString.append(inputVal);
				newString.append(oneString.substring(indEnd+1));
								
				ds.writeString(newString.toString());				
			}else{
				ds.writeString(oneString);
			}
		}
	}

	/**
	 * ����� ������ ��������� ��������
	 */
	public void writeTshData(){
		
		//�������� ����� ������
		Map<String, Object> headData = this.getTsh();
		
		//��������� ������ ����� ������
		ArrayList<String> headTemplate = new TemplateData().getTshTemplate();
		
		this.composeAndWrite(headData, headTemplate);
	}
	
	/**
	 * ����� �������� ������ ����������
	 */
	public void writeMainData(){
		
		//�������� ����� ������
		ArrayList<ArrayList<Map<String, Object>>> maindData = this.getMainData();
		
		//��������� ������ ����� ������
		ArrayList<String> mainTemplate = new TemplateData().getMainTemplate();
		
		//��������� ������ ����� ������
		ArrayList<String> headTemplate = new TemplateData().getMainHeadTemplate();
		
		//���� �� ��������
		for (ArrayList<Map<String, Object>> station: maindData){

			//�������� ��� �������
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("station", station.get(1).get("station"));
			//����� ��������� � �������� �������
			this.composeAndWrite(m, headTemplate);
			
			////���� �� ����������
			for (Map<String, Object> param: station){
				//System.out.println(param);
				this.composeAndWrite(param, mainTemplate);
			}
		}
	}
	
	public void writeExtendData(){
		//�������� ����� ������
		ArrayList<ArrayList<Map<String, Object>>> extendindData = this.getExtendData();
		
		//��������� ������ ����� ������
		ArrayList<String> extendTemplate = new TemplateData().getExtendTemplate();
		
		//��������� ������ ����� ������
		ArrayList<String> headTemplate = new TemplateData().getExtendHeadTemplate();
		
		//���� �� ��������
		for (ArrayList<Map<String, Object>> station: extendindData){

			//�������� ��� �������
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("station", station.get(1).get("station"));
			//����� ��������� � �������� �������
			this.composeAndWrite(m, headTemplate);
			
			////���� �� ����������
			for (Map<String, Object> param: station){
				//System.out.println(param);
				this.composeAndWrite(param, extendTemplate);
			}
		}
		
	}
	
	public void writeStatusData(){
		
	}
	
	/**
	 * ������������ ������ �� ������ ������� � ���������� �������� � ����� � ����
	 * @param headData ������ ��� ������� � ������
	 * @param headTemplate ������ ����� ��� ������� � ���� ������
	 */
	private void composeAndWrite(Map<String, Object> headData, ArrayList<String> headTemplate){
		
		//���������� ������ � ��������
		for (String oneString: headTemplate){
			
			String currString = oneString;
			
			boolean hasNext = true;
			StringBuilder newString = new StringBuilder();
			
			while(hasNext){
				
				int indStart = currString.indexOf("<")+1;
				int indEnd = currString.indexOf(">");
				if (indStart > 0 || indEnd > 0 ){
					
					//�������� �������� �� Map
					String item = currString.substring(indStart, indEnd);
					
					String inputVal =  headData.get(item).toString();
					
					//��������� ������ ��� ������ � ����
					newString.append(currString.substring(0,indStart-1));
					newString.append(inputVal);
									
					currString = currString.substring(indEnd+1);
				}else{
					newString.append(currString);
					hasNext = false;
				}
			}
			ds.writeString(newString.toString());				
		}
	}
	
	/**
	 * ��������� ������ � ����
	 */
	public void close(){
		//���������� ������ ����� ������
		ds.closeAndFlush();
	}
}
