package sqlQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import dataSaver.DataSaver;

/**
 * Класс которых формирует и запрашивает данных по корзинам и контролирует выгрузку в один файл
 * @author Павел А.
 *
 */
public class BasketDisribiution {
	
	private BasketView bv = null;
	private Connection myConn;
	private DataSaver ds = null;
	private String startDate;
	private String endDate;
	
	private boolean isViewCons = false;
	
	private ArrayList<String> basketAData;
	private ArrayList<String> basketBData;
	private ArrayList<String> basketCData;
	private ArrayList<String> lowVisData;
	
	/**
	 * Конструктор с параметрами
	 * @param inConn Connection
	 * @param inStartDate String
	 * @param inEndDate String
	 */
	public BasketDisribiution(Connection inConn, String inStartDate, String inEndDate){
		myConn = inConn;
		startDate = inStartDate;
		endDate = inEndDate;
	}
	
	/**
	 * головной класс запуска обработки и печати отчета
	 */
	public void makeBasketReport(){
		//создаем view
		 this.makeView();
		 
		 if(isViewCons){
			 //корзина a
			 this.fetchBasketA();
			 //корзина b
			 this.fetchBasketB();
			 //корзина с
			 this.fetchBasketC();
			 //низкая видмость
			 this.fetchLowVis();
		 }
		 
		 //пишем отчет в файл
		 this.writeReport();
		 
		 //удаляем view
		 this.dropView();
	}
	
	/**
	 * Пишем отчет в файл
	 */
	private void writeReport() {
		
		ds = this.prepareFile();
		ds.writeString("корзина a, только срок, за который прогноз оправдался");
		ds.writeString(" ");
		
		for ( String itemLine : basketAData){
			ds.writeString(itemLine.toString());		
		}
		ds.writeString(" ");
		ds.writeString(" ");
		ds.writeString("********************************************************************");
		ds.writeString("********************************************************************");
		ds.writeString("********************************************************************");
		ds.writeString(" ");
		ds.writeString(" ");
		ds.writeString(" ");
		ds.writeString("корзина b");
		ds.writeString(" ");
		ds.writeString(" ");
		
		
		for (String itemLine : basketBData){
			ds.writeString(itemLine.toString());
		}
		ds.writeString(" ");
		ds.writeString(" ");
		ds.writeString("********************************************************************");
		ds.writeString("********************************************************************");
		ds.writeString("********************************************************************");
		ds.writeString(" ");
		ds.writeString(" ");
		ds.writeString(" ");
		ds.writeString("корзина c");
		ds.writeString(" ");
		ds.writeString(" ");
		
		for (String itemLine : basketCData){
			ds.writeString(itemLine.toString());
		}
		ds.writeString(" ");
		ds.writeString(" ");
		ds.writeString("********************************************************************");
		ds.writeString("********************************************************************");
		ds.writeString("********************************************************************");
		ds.writeString(" ");
		ds.writeString(" ");
		ds.writeString(" ");
		ds.writeString("срок, когда видимость была ниже 1000м");
		ds.writeString(" ");
		ds.writeString(" ");
		
		for (String itemLine : lowVisData){
			ds.writeString(itemLine.toString());
		}
		
		ds.closeAndFlush();
	}
	
	/**
	 * Инициализация файла для записи
	 * @return DataSaverClass - через него пишем в файл
	 */
	private DataSaver prepareFile(){
		
		//проверка что файл еще не закрыт
		if (ds != null && ds.isOpen ){
			ds.closeAndFlush();
		}
		
		String fileName = this.createFileName();
		
		DataSaver ds = new DataSaver(fileName);
		
		return ds;	
	}
	
	/**
	 * Создаем имя для файла
	 * @return строка с полным именем файла
	 */
	private String createFileName(){
		//маска имени: неоправдавшиеся прогнозы 2016-01-01 2016-12-31 
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("неоправдавшиеся прогнозы ");
		sb.append(startDate);
		sb.append(" ");
		sb.append( endDate);		
		sb.append(".txt");
		
		return sb.toString();
	}
	

	/**
	 * для начала необходимо создать view из которых будем брать данные
	 */
	public void makeView(){
		
		bv = new BasketView(myConn, startDate, endDate);
		bv.makeView(bv.MAIN);
		bv.makeView(bv.ADDITION);
		
		isViewCons = true;
	}
	
	/**
	 * Получаем даные по корзине а
	 */
	public void fetchBasketA(){
		
		String query = "SELECT DISTINCT status.timemessage  FROM status " 
				+ " WHERE status.factvalue <= 1000 "
				+ " AND status.mainmessage = 'ожидается туман' "
				+ " AND status.tmstart >= status.timemessage " 
				+ " AND (status.tmstart - status.timemessage <= '06:00:00') ";
		
		basketAData = this.makeSimpleQ(query);
	}
	
	/**
	 * Получаем данные по корзине b
	 */
	public void fetchBasketB(){
		
		String query = "SELECT DISTINCT(timemessage) FROM status "
				+ " WHERE status.mainmessage = 'ожидается туман' "
				+ " AND status.tmstart >= status.timemessage " 
				+ " AND (status.tmstart - status.timemessage <= '06:00:00') "
				+ " AND status.timemessage NOT IN (SELECT timemessage FROM status_a) "
				+ " ORDER BY timemessage ";
		
		basketBData = this.makeSimpleQ(query);
	}

	/**
	 * Получаем данные по корзине с
	 */
	public void fetchBasketC(){
		
		String query = "SELECT *  FROM status "
				+ " WHERE status.factvalue <= 1000 " 
				+ " AND status.mainmessage = 'без тумана' "
				+ " AND status.tmstart >= status.timemessage " 
				+ " AND (status.tmstart - status.timemessage <= '06:00:00')  ";
		
		basketCData = this.makeSimpleQ(query);
	}
	
	/**
	 * Получаем данные по низкой видимости
	 */
	public void fetchLowVis(){
		
		String query = "SELECT tmstart,factvalue FROM "
				+ " lt_estim_30791 WHERE param = 'Vis' AND factvalue <= 1000 AND " 
				+ " DATE(tmstart) >= '"+startDate+"' AND DATE(tmstart) <= '"+endDate+"' ORDER by tmstart ";
		
		lowVisData = this.makeSimpleQ(query);
	}
	
	/**
	 * удаляем view
	 */
	public void dropView(){
		
		bv.delView(bv.ADDITION);
		bv.delView(bv.MAIN);
		
		
		isViewCons = false;
	}
	
	/**
	 * Выполнение запроса и сворачивание результата в список
	 * @param q - строка с запросом
	 * @return - ArrayList<String> - данные упакованы
	 */
	private ArrayList<String> makeSimpleQ(String q){
		System.out.println(q);
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
					
					//если пришла метра времени то переформатируем ее в понятный вывод
					if ( colVal.getClass().getName() == "java.sql.Timestamp"){
						java.sql.Timestamp cDateObj = (Timestamp) colVal;
						
						String outVal = cDateObj.toLocaleString();
						sb.append(outVal);
					}else{
					sb.append( colVal.toString() );
					}
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
