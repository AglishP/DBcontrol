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
	 * Конструктор
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
		ds = this.createInitFile("DAY");
	}
	
	/**
	 * Конструктор по умолчанию
	 */
	public ReportMaker(){
		fd = new FetchData(myConn);
	}
	
	/**
	 * Получение общих данных отчета
	 * @return list map c данными
	 */
	public Map<String, Object> getTsh(){
				
		//получаем данные о пороговых значениях
		return fd.getTsh();		
	}
	
	/**
	 * Получаем данные для заголовка в текстовом файле
	 * @return list map с данными
	 */
	public HashMap<String, String> getHeadData(){
				
		//дополняем остальными данными для заголовка
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
	 * Получаем данные основной статистики
	 */
	public ArrayList<ArrayList<Map<String, Object>>> getMainData(){
		
		return fd.getMainStat();
	}
	
	/**
	 * Получаем данные основной статистики
	 */
	public ArrayList<ArrayList<Map<String, Object>>> getExtendData(){
		
		return fd.getExtendStat();
	}
	
	/**
	 * Получить данные о статусе
	 * @return упакованые данные
	 */
	public ArrayList<Map<String, Object>> getStatusData(){
		
		return fd.getStatusStat();
	}
	
	/**
	 * Получить данные о статистике времени начала тумана
	 * @return упакованые данные
	 */
	public ArrayList<Map<String, Object>> getStartFogData(){
		
		return fd.getStartFogStat();
	}

	/**
	 * Создаем имя для файла
	 * @param inPathOfDay String время суток, как метка в имени файла
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
		sb.append(" 4ч-BD-JAVA_TEST");
		sb.append(inPathOfDay);
		sb.append(".txt");
		
		return sb.toString();
	}

	public void writeHeadFile(){
		//получаем имя файла для записи
		
		//инициируем класс записи
		
		//создаем файл и пишем в него общие данные
		
		
		
		//получаем пороговые данные
		
		
		
		//формируем строки с пороговыми данными
		
		
		
		//построчная запись пороговых данных
		
		//закрытие файла
		
		
	}
	
	/**
	 * Инициализация файла для записи
	 * @return DataSaverClass - через него пишем в файл
	 */
	public DataSaver createInitFile(String inPathOfDay){
		
		String fileName = this.nameFileCreator(inPathOfDay);
		
		DataSaver ds = new DataSaver(fileName);
		
		return ds;	
	}
	
	/**
	 * Пишем основные данные заголовка
	 */
	public void writeCommonData(){
		
		//получаем общие данные
		HashMap<String, String> headData = this.getHeadData();
		
		//формируем строки общих данных
		ArrayList<String> headTemplate = new TemplateData().getHeadTemplate();
					
		//Объединяем данные с шаблоном
		for (String oneString: headTemplate){
			
			int indStart = oneString.indexOf("<")+1;
			int indEnd = oneString.indexOf(">");
			if (indStart > 0 || indEnd > 0 ){
				
				//получаем значение из Map
				String item = oneString.substring(indStart, indEnd);
				String inputVal = headData.get(item);
				
				StringBuilder newString = new StringBuilder();
				
				//склеиваем строку для записи в файл
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
	 * Пишем данные пороговых значений
	 */
	public void writeTshData(){
		
		//получаем общие данные
		Map<String, Object> headData = this.getTsh();
		
		//формируем строки общих данных
		ArrayList<String> headTemplate = new TemplateData().getTshTemplate();
		
		this.composeAndWrite(headData, headTemplate);
	}
	
	/**
	 * Пишем основные данные статистики
	 */
	public void writeMainData(){
		
		//получаем общие данные
		ArrayList<ArrayList<Map<String, Object>>> maindData = this.getMainData();
		
		//формируем строки общих данных
		ArrayList<String> mainTemplate = new TemplateData().getMainTemplate();
		
		//формируем строки общих данных
		ArrayList<String> headTemplate = new TemplateData().getMainHeadTemplate();
		
		//цикл по стацниям
		for (ArrayList<Map<String, Object>> station: maindData){

			//получаем имя станции
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("station", station.get(1).get("station"));
			//пишем заголовок с индексом станции
			this.composeAndWrite(m, headTemplate);
			
			////цикл по параметрам
			for (Map<String, Object> param: station){
				//System.out.println(param);
				this.composeAndWrite(param, mainTemplate);
			}
		}
	}
	
	public void writeExtendData(){
		//получаем общие данные
		ArrayList<ArrayList<Map<String, Object>>> extendindData = this.getExtendData();
		
		//формируем строки общих данных
		ArrayList<String> extendTemplate = new TemplateData().getExtendTemplate();
		
		//формируем строки общих данных
		ArrayList<String> headTemplate = new TemplateData().getExtendHeadTemplate();
		
		//цикл по стацниям
		for (ArrayList<Map<String, Object>> station: extendindData){

			//получаем имя станции
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("station", station.get(1).get("station"));
			//пишем заголовок с индексом станции
			this.composeAndWrite(m, headTemplate);
			
			////цикл по параметрам
			for (Map<String, Object> param: station){
				//System.out.println(param);
				this.composeAndWrite(param, extendTemplate);
			}
		}
		
	}
	
	public void writeStatusData(){
		
	}
	
	/**
	 * Пересобираем данные на основе шаблона и переданных значений и пишем в файл
	 * @param headData данные для вставки в шаблон
	 * @param headTemplate шаблон строк для вставки в файл отчета
	 */
	private void composeAndWrite(Map<String, Object> headData, ArrayList<String> headTemplate){
		
		//Объединяем данные с шаблоном
		for (String oneString: headTemplate){
			
			String currString = oneString;
			
			boolean hasNext = true;
			StringBuilder newString = new StringBuilder();
			
			while(hasNext){
				
				int indStart = currString.indexOf("<")+1;
				int indEnd = currString.indexOf(">");
				if (indStart > 0 || indEnd > 0 ){
					
					//получаем значение из Map
					String item = currString.substring(indStart, indEnd);
					
					String inputVal =  headData.get(item).toString();
					
					//склеиваем строку для записи в файл
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
	 * закрываем запись в файл
	 */
	public void close(){
		//построчная запись общих данных
		ds.closeAndFlush();
	}
}
