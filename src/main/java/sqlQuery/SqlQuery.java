package sqlQuery;

import java.lang.reflect.Array;

public class SqlQuery {
	
	private String location;
	private String dataType;
	private String startDate;
	private String endDate;	
	private Array stationArray;
	
	// конструктор
	public SqlQuery(String inlocation,
					String indataType,
					String instartDate,
					String inendDate){
		location = inlocation;
		dataType = indataType;
		startDate = instartDate;
		endDate = inendDate;
	}
	
//	private Array GetStationList(){
//		Array stationList;
//		
//		
//		
//		return stationList;
//	}
	
	
	//расчет основного блока статистики
	public void MainCalc(){
		String q;
		q = "SELECT sum_cldh_void('30791', 24, "+startDate+","+endDate+", )";
		
	}
	
	

}
