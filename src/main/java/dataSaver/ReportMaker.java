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
	String pathOfDay;
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
		pathOfDay = inType;
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
	 * �� ���� ��� �� �������� ��� �����, � ������� ����� ������ ������
	 * @param inPath ������������� ������� ����� � ����� �����
	 */
	public void setPathOfDay(String inPath){
		
		pathOfDay = inPath;
		ds = this.prepareFile(pathOfDay);
	}
	
	/**
	 * ����� ��� ������ ����� ����� � ������� ����� ������� ���� ������
	 */
	public void writeStatisticFile(){
		
		this.setPathOfDay(pathOfDay);
		
		this.writeCommonData();
		this.writeTshData();
		this.writeMainData();
		this.writeExtendData();
		this.writeStatusData();
		this.close();
	}
	
	/**
	 * ����� ��� ������ ����� ������ � �����������
	 * @param inParamOfCalc ��� ������
	 * @param inPathOfDay ����� ����� ��� ������
	 */
	public void writeStatisticFile(String inParamOfCalc, String inPathOfDay){
		
		if (inParamOfCalc == "FOGSTART"){
			
			//���������� ������
			String buff = new StringBuilder().append(inPathOfDay).append("-").append(inParamOfCalc).toString();

			this.setPathOfDay(buff);
			
			//������ ������
			this.writeFogStatisticFile();
			
		}else if (inParamOfCalc == "BASKET"){
			
			this.setPathOfDay(inParamOfCalc);
			
			this.writeBasketFile();
		}
		
	}
	
	/**
	 * ������ ������ ������������� �� �������� ��������� ���� 1000�
	 */
	private void writeBasketFile() {
			
		this.writeBasketA();
		this.writeBasketB();
		this.writeBasketC();
		this.writeFactFog();
		this.close();
	}
	
	/**
	 * ����� ������ �� ������� �
	 */
	private void writeBasketA() {
		
		//��������� ������ ����� ������
		ArrayList<String> headTemplate = new TemplateData().getBasketAHead();
		
		Map<String, Object> m = new HashMap<String, Object>();
		
		//����� ���������
		this.composeAndWrite(m, headTemplate);
		
		//�������� ������ �� ������� a
		ArrayList<LinkedHashMap<String, Object>> basketData = this.getBasketAData();
		
		//��������� ��� ������ � ����
		ArrayList<ArrayList<String>> arrToWrite = new ArrayList<ArrayList<String>>(); 
		
		//���� �� �������
		for (LinkedHashMap<String, Object> item: basketData){
			
			ArrayList<String> itemToWrite = new ArrayList<String>();
			
			//������������ ������ � ������� list 
			for (Object val: item.values()){
				itemToWrite.add(val.toString());
			}
			
			arrToWrite.add(itemToWrite);	
		}
		
		this.linearWrite(arrToWrite);	
	}
	
	/**
	 * ����� ������ �� ������� B
	 */
	private void writeBasketB() {
		
		//��������� ������ ����� ������
		ArrayList<String> headTemplate = new TemplateData().getBasketBHead();
		
		Map<String, Object> m = new HashMap<String, Object>();
		
		//����� ���������
		this.composeAndWrite(m, headTemplate);
		
		//�������� ������ �� ������� a
		ArrayList<LinkedHashMap<String, Object>> basketData = this.getBasketBData();
		
		//��������� ��� ������ � ����
		ArrayList<ArrayList<String>> arrToWrite = new ArrayList<ArrayList<String>>(); 
		
		//���� �� �������
		for (LinkedHashMap<String, Object> item: basketData){
			
			ArrayList<String> itemToWrite = new ArrayList<String>();
			
			//������������ ������ � ������� list 
			for (Object val: item.values()){
				itemToWrite.add(val.toString());
			}
			
			arrToWrite.add(itemToWrite);	
		}
		
		this.linearWrite(arrToWrite);	
	}
	
	/**
	 * ����� ������ �� ������� B
	 */
	private void writeBasketC() {
		
		//��������� ������ ����� ������
		ArrayList<String> headTemplate = new TemplateData().getBasketCHead();
		
		Map<String, Object> m = new HashMap<String, Object>();
		
		//����� ���������
		this.composeAndWrite(m, headTemplate);
		
		//�������� ������ �� ������� a
		ArrayList<LinkedHashMap<String, Object>> basketData = this.getBasketCData();
		
		//��������� ��� ������ � ����
		ArrayList<ArrayList<String>> arrToWrite = new ArrayList<ArrayList<String>>(); 
		
		//���� �� �������
		for (LinkedHashMap<String, Object> item: basketData){
			
			ArrayList<String> itemToWrite = new ArrayList<String>();
			
			//������������ ������ � ������� list 
			for (Object val: item.values()){
				itemToWrite.add(val.toString());
			}
			
			arrToWrite.add(itemToWrite);	
		}
		
		this.linearWrite(arrToWrite);	
	}
	
	/**
	 * ����� ������ �� ����������� ��������� ���� 1000�
	 */
	private void writeFactFog() {
		
		//��������� ������ ����� ������
		ArrayList<String> headTemplate = new TemplateData().getFactFogHeadTemplate();
		
		Map<String, Object> m = new HashMap<String, Object>();
		
		//����� ���������
		this.composeAndWrite(m, headTemplate);
		
		//�������� ������ �� ������� a
		ArrayList<LinkedHashMap<String, Object>> basketData = this.getFactFogData();
		
		//��������� ��� ������ � ����
		ArrayList<ArrayList<String>> arrToWrite = new ArrayList<ArrayList<String>>(); 
		
		//���� �� �������
		for (LinkedHashMap<String, Object> item: basketData){
			
			ArrayList<String> itemToWrite = new ArrayList<String>();
			
			//������������ ������ � ������� list 
			for (Object val: item.values()){
				itemToWrite.add(val.toString());
			}
			
			arrToWrite.add(itemToWrite);	
		}
		
		this.linearWrite(arrToWrite);	
	}
	
	/**
	 * ����� ������ ������ �� ������� ������ ������
	 */
	private void writeFogStatisticFile() {
		
		this.writeFogStartData();
		this.close();
	}

	/**
	 * ����� ������ �� ������� ������ ������
	 */
	private void writeFogStartData() {
		
		//�������� ������ �� �������
		ArrayList<LinkedHashMap<String, Object>> fogData = this.getFogStartData();
		
		//��������� ��� ������ � ����
		ArrayList<ArrayList<String>> arrToWrite = new ArrayList<ArrayList<String>>(); 
		
		//���� �� �������
		for (LinkedHashMap<String, Object> item: fogData){
			
			ArrayList<String> itemToWrite = new ArrayList<String>();
			
			//������������ ������ � ������� list 
			for (Object val: item.values()){
				itemToWrite.add(val.toString());
			}
			
			arrToWrite.add(itemToWrite);	
		}
		
		this.linearWrite(arrToWrite);	
	}

	/**
	 * �������� ������ ������ �� ������� ������ ������
	 * @return List c �������
	 */
	public ArrayList<LinkedHashMap<String, Object>> getFogStartData() {
		
		 return fd.getStartFogStat();
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
	public ArrayList<ArrayList<LinkedHashMap<String, Object>>> getMainData(){
		
		return fd.getMainStat();
	}
	
	/**
	 * �������� ������ �������� ����������
	 */
	public ArrayList<ArrayList<LinkedHashMap<String, Object>>> getExtendData(){
		
		return fd.getExtendStat();
	}
	
	/**
	 * �������� ������ � �������
	 * @return ���������� ������
	 */
	public ArrayList<LinkedHashMap<String, Object>> getStatusData(){
		
		return fd.getStatusStat();
	}
	
	/**
	 * �������� ������ �� ������� �
	 * @return
	 */
	public ArrayList<LinkedHashMap<String, Object>> getBasketAData(){
		
		return fd.getBasketA();
	}
	
	/**
	 * �������� ������ �� ������� b
	 * @return
	 */
	public ArrayList<LinkedHashMap<String, Object>> getBasketBData(){
		
		return fd.getBasketB();
	}
	
	/**
	 * �������� ������ �� ������� c
	 * @return
	 */
	public ArrayList<LinkedHashMap<String, Object>> getBasketCData(){
		
		return fd.getBasketC();
	}
	
	/**
	 * �������� ������ �� ���������� �������� ��������� ����� 1000�
	 * @return
	 */
	public ArrayList<LinkedHashMap<String, Object>> getFactFogData(){
		
		return fd.getFactFogTime();
	}
	
	/**
	 * �������� ������ � ���������� ������� ������ ������
	 * @return ���������� ������
	 */
	public ArrayList<LinkedHashMap<String, Object>> getStartFogData(){
		
		return fd.getStartFogStat();
	}

	/**
	 * ������� ��� ��� �����
	 * @param inPathOfDay String ����� �����, ��� ����� � ����� �����
	 * @return ������ � ������ ������ �����
	 */
	private String createFileName(String inPathOfDay){
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
		sb.append(" 4�-BD-JAVA_TEST-");
		sb.append(inPathOfDay);
		sb.append(".txt");
		
		return sb.toString();
	}

	/**
	 * ������������� ����� ��� ������
	 * @return DataSaverClass - ����� ���� ����� � ����
	 */
	private DataSaver prepareFile(String inPathOfDay){
		
		//�������� ��� ���� ��� �� ������
		if (ds != null && ds.isOpen ){
			ds.closeAndFlush();
		}
		
		String fileName = this.createFileName(inPathOfDay);
		
		DataSaver ds = new DataSaver(fileName);
		
		return ds;	
	}
	
	/**
	 * ����� �������� ������ ���������
	 */
	private void writeCommonData(){
		
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
	private void writeTshData(){
		
		//�������� ����� ������
		Map<String, Object> headData = this.getTsh();
		
		//��������� ������ ����� ������
		ArrayList<String> headTemplate = new TemplateData().getTshTemplate();
		
		this.composeAndWrite(headData, headTemplate);
	}
	
	/**
	 * ����� �������� ������ ����������
	 */
	private void writeMainData(){
		
		//�������� ����� ������
		ArrayList<ArrayList<LinkedHashMap<String, Object>>> maindData = this.getMainData();
		
		//��������� ������ ����� ������
		ArrayList<String> mainTemplate = new TemplateData().getMainTemplate();
		
		//��������� ������ ����� ������
		ArrayList<String> headTemplate = new TemplateData().getMainHeadTemplate();
		
		//���� �� ��������
		for (ArrayList<LinkedHashMap<String, Object>> station: maindData){

			//�������� ��� �������
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("station", station.get(0).get("station"));
			//����� ��������� � �������� �������
			this.composeAndWrite(m, headTemplate);
			
			////���� �� ����������
			for (Map<String, Object> param: station){
				//System.out.println(param);
				this.composeAndWrite(param, mainTemplate);
			}
		}
	}
	
	/**
	 * ����� ������ ����������� ����������
	 */
	private void writeExtendData(){
		//�������� ����� ������
		ArrayList<ArrayList<LinkedHashMap<String, Object>>> extendindData = this.getExtendData();
		
		//��������� ������ ����� ������
		ArrayList<String> extendTemplate = new TemplateData().getExtendTemplate();
		
		//��������� ������ ����� ������
		ArrayList<String> headTemplate = new TemplateData().getExtendHeadTemplate();
		
		//�������� ��� �������
		Map<String, Object> m = new HashMap<String, Object>();
		//m.put("station", extendindData.get(0).get("station"));
		//����� ��������� � �������� �������
		this.composeAndWrite(m, headTemplate);
		
		//���� �� ��������
		for (ArrayList<LinkedHashMap<String, Object>> station: extendindData){

			////���� �� ����������
			for (Map<String, Object> param: station){
				//System.out.println(param);
				this.composeAndWrite(param, extendTemplate);
			}
		}
		
	}
	
	/**
	 * ����� ������ �� �������
	 */
	private void writeStatusData(){
		
		//�������� ����� ������
		ArrayList<LinkedHashMap<String, Object>> headData = this.getStatusData();
		
		//��������� ������ ����� ������
		ArrayList<String> headTemplate = new TemplateData().getStatusHeadTemplate();
		
		//��������� ������ ����� ������
		ArrayList<String> statusTemplate = new TemplateData().getStatusTemplate();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("station", headData.get(0).get("station"));
		//����� ��������� � �������� �������
		this.composeAndWrite(m, headTemplate);
				
		//���� �� ����������
		for (Map<String, Object> param: headData){
			//System.out.println(param);
			this.composeAndWrite(param, statusTemplate);
		}
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
	 * ����� � ������� ������ �� ������� � ������������ ;
	 * @param arrToWrite
	 */
	private void linearWrite(ArrayList<ArrayList<String>> arrToWrite){
		
		//���� �� �������
		for (ArrayList<String> row: arrToWrite){
			
			//�������� ������ � ������
			StringBuilder sb = new StringBuilder();
			
			for (String item: row){
			
				sb.append(item);
				sb.append(";");
			}
			ds.writeString(sb.toString());
		}
		
	}
	
	/**
	 * ��������� ������ � ����
	 */
	private void close(){
		//���������� ������ ����� ������
		ds.closeAndFlush();
	}
}
