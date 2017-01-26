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
		pathOfDay = inType;
		startDate = inStartDate;
		endDate = inEndDate;
		fd = new FetchData(myConn, startDate, endDate);
	}
	
	/**
	 * Конструктор по умолчанию
	 */
	public ReportMaker(){
		fd = new FetchData(myConn);
	}

	/**
	 * По сути тут мы изменяем имя файла, в который будем писать данные
	 * @param inPath идентификатор времени суток в имени файла
	 */
	public void setPathOfDay(String inPath){
		
		pathOfDay = inPath;
		ds = this.prepareFile(pathOfDay);
	}
	
	/**
	 * метод для записи всего файла с данными сразу изнутри тела класса
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
	 * Метод для записи файла отчета с параметрами
	 * @param inParamOfCalc тип отчета
	 * @param inPathOfDay время суток для записи
	 */
	public void writeStatisticFile(String inParamOfCalc, String inPathOfDay){
		
		if (inParamOfCalc == "FOGSTART"){
			
			//объединяем строки
			String buff = new StringBuilder().append(inPathOfDay).append("-").append(inParamOfCalc).toString();

			this.setPathOfDay(buff);
			
			//запуск записи
			this.writeFogStatisticFile();
			
		}else if (inParamOfCalc == "BASKET"){
			
			this.setPathOfDay(inParamOfCalc);
			
			this.writeBasketFile();
		}
		
	}
	
	/**
	 * Запись данных распределения ко корзинам видимости ниже 1000м
	 */
	private void writeBasketFile() {
			
		this.writeBasketA();
		this.writeBasketB();
		this.writeBasketC();
		this.writeFactFog();
		this.close();
	}
	
	/**
	 * Пишем данные из корзины А
	 */
	private void writeBasketA() {
		
		//формируем строки общих данных
		ArrayList<String> headTemplate = new TemplateData().getBasketAHead();
		
		Map<String, Object> m = new HashMap<String, Object>();
		
		//пишем заголовок
		this.composeAndWrite(m, headTemplate);
		
		//получаем данные по корзине a
		ArrayList<LinkedHashMap<String, Object>> basketData = this.getBasketAData();
		
		//коллекция для записи в файл
		ArrayList<ArrayList<String>> arrToWrite = new ArrayList<ArrayList<String>>(); 
		
		//ципл по строкам
		for (LinkedHashMap<String, Object> item: basketData){
			
			ArrayList<String> itemToWrite = new ArrayList<String>();
			
			//пересобираем данные в простой list 
			for (Object val: item.values()){
				itemToWrite.add(val.toString());
			}
			
			arrToWrite.add(itemToWrite);	
		}
		
		this.linearWrite(arrToWrite);	
	}
	
	/**
	 * Пишем данные из корзины B
	 */
	private void writeBasketB() {
		
		//формируем строки общих данных
		ArrayList<String> headTemplate = new TemplateData().getBasketBHead();
		
		Map<String, Object> m = new HashMap<String, Object>();
		
		//пишем заголовок
		this.composeAndWrite(m, headTemplate);
		
		//получаем данные по корзине a
		ArrayList<LinkedHashMap<String, Object>> basketData = this.getBasketBData();
		
		//коллекция для записи в файл
		ArrayList<ArrayList<String>> arrToWrite = new ArrayList<ArrayList<String>>(); 
		
		//ципл по строкам
		for (LinkedHashMap<String, Object> item: basketData){
			
			ArrayList<String> itemToWrite = new ArrayList<String>();
			
			//пересобираем данные в простой list 
			for (Object val: item.values()){
				itemToWrite.add(val.toString());
			}
			
			arrToWrite.add(itemToWrite);	
		}
		
		this.linearWrite(arrToWrite);	
	}
	
	/**
	 * Пишем данные из корзины B
	 */
	private void writeBasketC() {
		
		//формируем строки общих данных
		ArrayList<String> headTemplate = new TemplateData().getBasketCHead();
		
		Map<String, Object> m = new HashMap<String, Object>();
		
		//пишем заголовок
		this.composeAndWrite(m, headTemplate);
		
		//получаем данные по корзине a
		ArrayList<LinkedHashMap<String, Object>> basketData = this.getBasketCData();
		
		//коллекция для записи в файл
		ArrayList<ArrayList<String>> arrToWrite = new ArrayList<ArrayList<String>>(); 
		
		//ципл по строкам
		for (LinkedHashMap<String, Object> item: basketData){
			
			ArrayList<String> itemToWrite = new ArrayList<String>();
			
			//пересобираем данные в простой list 
			for (Object val: item.values()){
				itemToWrite.add(val.toString());
			}
			
			arrToWrite.add(itemToWrite);	
		}
		
		this.linearWrite(arrToWrite);	
	}
	
	/**
	 * Пишем данные по фактической видимости ниже 1000м
	 */
	private void writeFactFog() {
		
		//формируем строки общих данных
		ArrayList<String> headTemplate = new TemplateData().getFactFogHeadTemplate();
		
		Map<String, Object> m = new HashMap<String, Object>();
		
		//пишем заголовок
		this.composeAndWrite(m, headTemplate);
		
		//получаем данные по корзине a
		ArrayList<LinkedHashMap<String, Object>> basketData = this.getFactFogData();
		
		//коллекция для записи в файл
		ArrayList<ArrayList<String>> arrToWrite = new ArrayList<ArrayList<String>>(); 
		
		//ципл по строкам
		for (LinkedHashMap<String, Object> item: basketData){
			
			ArrayList<String> itemToWrite = new ArrayList<String>();
			
			//пересобираем данные в простой list 
			for (Object val: item.values()){
				itemToWrite.add(val.toString());
			}
			
			arrToWrite.add(itemToWrite);	
		}
		
		this.linearWrite(arrToWrite);	
	}
	
	/**
	 * Метод записи данных по времени начала тумана
	 */
	private void writeFogStatisticFile() {
		
		this.writeFogStartData();
		this.close();
	}

	/**
	 * Пишем данные по времени начала тумана
	 */
	private void writeFogStartData() {
		
		//получаем данные из таблицы
		ArrayList<LinkedHashMap<String, Object>> fogData = this.getFogStartData();
		
		//коллекция для записи в файл
		ArrayList<ArrayList<String>> arrToWrite = new ArrayList<ArrayList<String>>(); 
		
		//ципл по строкам
		for (LinkedHashMap<String, Object> item: fogData){
			
			ArrayList<String> itemToWrite = new ArrayList<String>();
			
			//пересобираем данные в простой list 
			for (Object val: item.values()){
				itemToWrite.add(val.toString());
			}
			
			arrToWrite.add(itemToWrite);	
		}
		
		this.linearWrite(arrToWrite);	
	}

	/**
	 * Получаем данные отчета по времени начала тумана
	 * @return List c данными
	 */
	public ArrayList<LinkedHashMap<String, Object>> getFogStartData() {
		
		 return fd.getStartFogStat();
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
	public ArrayList<ArrayList<LinkedHashMap<String, Object>>> getMainData(){
		
		return fd.getMainStat();
	}
	
	/**
	 * Получаем данные основной статистики
	 */
	public ArrayList<ArrayList<LinkedHashMap<String, Object>>> getExtendData(){
		
		return fd.getExtendStat();
	}
	
	/**
	 * Получить данные о статусе
	 * @return упакованые данные
	 */
	public ArrayList<LinkedHashMap<String, Object>> getStatusData(){
		
		return fd.getStatusStat();
	}
	
	/**
	 * Получить данные по корзине а
	 * @return
	 */
	public ArrayList<LinkedHashMap<String, Object>> getBasketAData(){
		
		return fd.getBasketA();
	}
	
	/**
	 * Получить данные по корзине b
	 * @return
	 */
	public ArrayList<LinkedHashMap<String, Object>> getBasketBData(){
		
		return fd.getBasketB();
	}
	
	/**
	 * Получить данные по корзине c
	 * @return
	 */
	public ArrayList<LinkedHashMap<String, Object>> getBasketCData(){
		
		return fd.getBasketC();
	}
	
	/**
	 * Получить данные по количеству значений видимости менее 1000м
	 * @return
	 */
	public ArrayList<LinkedHashMap<String, Object>> getFactFogData(){
		
		return fd.getFactFogTime();
	}
	
	/**
	 * Получить данные о статистике времени начала тумана
	 * @return упакованые данные
	 */
	public ArrayList<LinkedHashMap<String, Object>> getStartFogData(){
		
		return fd.getStartFogStat();
	}

	/**
	 * Создаем имя для файла
	 * @param inPathOfDay String время суток, как метка в имени файла
	 * @return строка с полным именем файла
	 */
	private String createFileName(String inPathOfDay){
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
		sb.append(" 4ч-BD-JAVA_TEST-");
		sb.append(inPathOfDay);
		sb.append(".txt");
		
		return sb.toString();
	}

	/**
	 * Инициализация файла для записи
	 * @return DataSaverClass - через него пишем в файл
	 */
	private DataSaver prepareFile(String inPathOfDay){
		
		//проверка что файл еще не закрыт
		if (ds != null && ds.isOpen ){
			ds.closeAndFlush();
		}
		
		String fileName = this.createFileName(inPathOfDay);
		
		DataSaver ds = new DataSaver(fileName);
		
		return ds;	
	}
	
	/**
	 * Пишем основные данные заголовка
	 */
	private void writeCommonData(){
		
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
	private void writeTshData(){
		
		//получаем общие данные
		Map<String, Object> headData = this.getTsh();
		
		//формируем строки общих данных
		ArrayList<String> headTemplate = new TemplateData().getTshTemplate();
		
		this.composeAndWrite(headData, headTemplate);
	}
	
	/**
	 * Пишем основные данные статистики
	 */
	private void writeMainData(){
		
		//получаем общие данные
		ArrayList<ArrayList<LinkedHashMap<String, Object>>> maindData = this.getMainData();
		
		//формируем строки общих данных
		ArrayList<String> mainTemplate = new TemplateData().getMainTemplate();
		
		//формируем строки общих данных
		ArrayList<String> headTemplate = new TemplateData().getMainHeadTemplate();
		
		//цикл по стацниям
		for (ArrayList<LinkedHashMap<String, Object>> station: maindData){

			//получаем имя станции
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("station", station.get(0).get("station"));
			//пишем заголовок с индексом станции
			this.composeAndWrite(m, headTemplate);
			
			////цикл по параметрам
			for (Map<String, Object> param: station){
				//System.out.println(param);
				this.composeAndWrite(param, mainTemplate);
			}
		}
	}
	
	/**
	 * Пишем данные расширенной статистики
	 */
	private void writeExtendData(){
		//получаем общие данные
		ArrayList<ArrayList<LinkedHashMap<String, Object>>> extendindData = this.getExtendData();
		
		//формируем строки общих данных
		ArrayList<String> extendTemplate = new TemplateData().getExtendTemplate();
		
		//формируем строки общих данных
		ArrayList<String> headTemplate = new TemplateData().getExtendHeadTemplate();
		
		//получаем имя станции
		Map<String, Object> m = new HashMap<String, Object>();
		//m.put("station", extendindData.get(0).get("station"));
		//пишем заголовок с индексом станции
		this.composeAndWrite(m, headTemplate);
		
		//цикл по стацниям
		for (ArrayList<LinkedHashMap<String, Object>> station: extendindData){

			////цикл по параметрам
			for (Map<String, Object> param: station){
				//System.out.println(param);
				this.composeAndWrite(param, extendTemplate);
			}
		}
		
	}
	
	/**
	 * Пишем данные по статусу
	 */
	private void writeStatusData(){
		
		//получаем общие данные
		ArrayList<LinkedHashMap<String, Object>> headData = this.getStatusData();
		
		//формируем строки общих данных
		ArrayList<String> headTemplate = new TemplateData().getStatusHeadTemplate();
		
		//формируем строки общих данных
		ArrayList<String> statusTemplate = new TemplateData().getStatusTemplate();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("station", headData.get(0).get("station"));
		//пишем заголовок с индексом станции
		this.composeAndWrite(m, headTemplate);
				
		//цикл по параметрам
		for (Map<String, Object> param: headData){
			//System.out.println(param);
			this.composeAndWrite(param, statusTemplate);
		}
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
	 * Пишем в строчку данные из массива с разделителем ;
	 * @param arrToWrite
	 */
	private void linearWrite(ArrayList<ArrayList<String>> arrToWrite){
		
		//цикл по строкам
		for (ArrayList<String> row: arrToWrite){
			
			//собираем данные в строку
			StringBuilder sb = new StringBuilder();
			
			for (String item: row){
			
				sb.append(item);
				sb.append(";");
			}
			ds.writeString(sb.toString());
		}
		
	}
	
	/**
	 * закрываем запись в файл
	 */
	private void close(){
		//построчная запись общих данных
		ds.closeAndFlush();
	}
}
