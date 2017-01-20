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
	 *  онструктор
	 * @param inConn Connection
	 * @param inType String тип данных, которые пишем
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
	 *  онструктор по умолчанию
	 */
	public ReportMaker(){
		fd = new FetchData(myConn);
	}
	
	/**
	 * ѕолучение общих данных отчета
	 * @return list map c данными
	 */
	public ArrayList<Map <String, Integer>> getTsh(){
				
		//получаем данные о пороговых значени€х
		return fd.getTsh();		
	}
	
	/**
	 * ѕолучаем данные дл€ заголовка в текстовом файле
	 * @return list map с данными
	 */
	public Map<String, String> getHeadData(){
				
		//дополн€ем остальными данными дл€ заголовка
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
	 * ѕолучаем данные основной статистики
	 */
	public ArrayList<Map<String, Object>> getMainData(){
		
		return fd.getMainStat();
	}
	
	/**
	 * ѕолучить данные о статусе
	 * @return упакованые данные
	 */
	public ArrayList<Map<String, Object>> getStatusData(){
		
		return fd.getStatusStat();
	}
	
	/**
	 * ѕолучить данные о статистике времени начала тумана
	 * @return упакованые данные
	 */
	public ArrayList<Map<String, Object>> getStartFogData(){
		
		return fd.getStartFogStat();
	}

	/**
	 * —оздаем им€ дл€ файла
	 * @param inPathOfDay String врем€ суток, как метка в имени файла
	 * @return строка с полным именем файла
	 */
	public String nameFileCreator(String inPathOfDay){
		//маска имени: 2016-01-01 2016-12-31 build-2017-01-20 10-18 4ч-BD
		
		StringBuilder sb = new StringBuilder();
		
		//получаем общие данные
		Map<String, String> commonData = new HashMap<String, String>(); 
		commonData = this.getHeadData();
		
		sb.append( commonData.get("StartDate"));
		sb.append(" ");
		sb.append( commonData.get("EndDate"));
		sb.append(" build-");
		sb.append( commonData.get("CurrDate") );
		sb.append(" ");
		sb.append( commonData.get("CurrTime"));
		sb.append(" 4ч-BD-");
		sb.append(inPathOfDay);
		sb.append(".txt");
		
		return sb.toString();
	}

	
	public void writeHeadFile(){
		//получаем им€ файла дл€ записи
		
		//инициируем класс записи
		
		//создаем файл и пишем в него общие данные
		
		
		
		//получаем пороговые данные
		
		
		
		//формируем строки с пороговыми данными
		
		
		
		//построчна€ запись пороговых данных
		
		//закрытие файла
		
		
	}
	
	/**
	 * »нициализаци€ файла дл€ записи
	 * @return DataSaverClass - через него пишем в файл
	 */
	public DataSaver createInitFile(String inPathOfDay){
		
		String fileName = this.nameFileCreator(inPathOfDay);
		
		DataSaver dsc = new DataSaver(fileName);
		
		return dsc;	
	}
	
	
	public void writeCommonData(DataSaver inDsc){
		
		//получаем общие данные
		Map<String, String> headData = this.getHeadData();
		
		//формируем строки общих данных
		ArrayList<String> headTemplate = new TemplateData().getHeadTemplate();
		
		//ќбъедин€ем данные с шаблоном
		for (String oneString: headTemplate){
			if ( oneString.contains("?") ){
				System.out.println( headData );
				headData.remove(1);
			}
		}
		
		//построчна€ запись общих данных
		
	}

}
