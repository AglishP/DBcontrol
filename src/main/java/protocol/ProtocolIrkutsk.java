package protocol;

import java.sql.Connection;
import sqlQuery.SqlQuery;

//реализация протокола расчета статистики для иркутстка от октября 2016года
public class ProtocolIrkutsk {

	private Connection myConn;
	private String startDate;
	private String endDate;
	
	public ProtocolIrkutsk(Connection inconnection,
							String instartDate,
							String inendDate){
		myConn = inconnection;
		startDate = instartDate;
		endDate = inendDate;
	}

	public void RunProtocol(){
		//п.1 Расчет основной, расширенной статистики и расчет по статусу
		
		String calcType = null;
		SqlQuery sqlQuery = new SqlQuery(myConn, calcType,startDate, endDate);
		calcType = "main";
		sqlQuery.setCalcType(calcType);
		sqlQuery.makeCalc();
		
		//меняем тип на расширенный
		calcType = "extend";
		sqlQuery.setCalcType(calcType);
		sqlQuery.makeCalc();
		
		//теперь статус
		calcType = "status";
		sqlQuery.setCalcType(calcType);
		sqlQuery.makeCalc();
		
		//собираем данные в отчет
		
		//п.2 запускаем функцию сборки данных по началу тумана в таблицу fog_analyze
		calcType = "fogStart";
		sqlQuery.setCalcType(calcType);
		sqlQuery.makeCalc();
		
		//п3 выгружаем данные из таблицы lt_fog_start_stat в текстовый файл для последующей вставки в xml
		
		//п4 разбить данные из таблиц lt_estim_xxxxx в подтаблицы день/ночь
		
		
	}
	
}
