package protocol;

import java.sql.Connection;

import dataSaver.ReportMaker;
import sqlQuery.SqlQuery;
import sqlQuery.TableWorker;
import starter.DateBuilder;

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
		
		final String mainType = "main";
		final String extendType = "extend";
		final String statusType = "status";
		final String fogStartType = "fogStart";
		final String day = "day";
		final String night = "night";
		final String allDay = "ALL";
		
//		//ставим метку времени начала протогола
		DateBuilder db = new DateBuilder();
//		System.out.println(db.getCurrTime());
//		
//		//п.1 Расчет основной, расширенной статистики и расчет по статусу
		SqlQuery sqlQuery = new SqlQuery(myConn, startDate, endDate);
//		
//		//расчет основной статистики
//		sqlQuery.makeCalc(mainType);
//		System.out.println("complete mainCalc");
//		
//		//меняем тип на расширенный
		sqlQuery.makeCalc(extendType);
		System.out.println("complete extendCalc");
//		
//		//теперь статус
//		sqlQuery.makeCalc(statusType);
//		System.out.println("complete statusCalc");
		
		//собираем данные в отчет
		ReportMaker rm = new ReportMaker(myConn,allDay, startDate, endDate);
		rm.writeStatisticFile();
		System.out.println("complete report ALL");
		
		//п4 разбить данные из таблиц lt_estim_xxxxx в подтаблицы день/ночь
		TableWorker tw = new TableWorker(myConn, startDate, endDate);
		tw.sep(mainType);
		System.out.println("complete separate main");
		
		//п5 удаляем данные из основных таблиц
		tw.del(mainType);
		System.out.println("complete del main");
		
		//п6 вставляем в основную таблицу данные за день
		tw.load(mainType, day);
		System.out.println("complete load day main");
		
		//п7 запускаем оценку основной и расширенной статиситки
		sqlQuery.makeCalc(mainType);
		System.out.println("complete mainCalc Day");
		sqlQuery.makeCalc(extendType);
		System.out.println("complete extendCalc Day");
		
		//п14.1 разбиваем данные из таблицы статусов
		tw.sep(statusType);
		System.out.println("complete separate status");
		
		//п14.2 удаляем данные из таблицы статусов
		tw.del(statusType);
		System.out.println("complete del status");
		
		//п14.3 загружаем данные за день в таблицу статусов
		tw.load(statusType, day);
		System.out.println("complete load day status");
		
		//п13 в основную таблицу вставляем данные за день, чтобы получить полный набор данных за сутки
		//tw.load(mainType, night);
		//System.out.println("complete load night main");
		
		//п14.4 запускаем расчет статуса 
		sqlQuery.makeCalc(statusType);
		System.out.println("complete mainCalc");
		
		//п8 экспорт всей статиситки в файл
		rm.setPathOfDay(day);
		rm.writeStatisticFile();
		System.out.println("complete report day");
		
		//п9 удаляем данные из основных таблиц
		tw.del(mainType);
		System.out.println("complete del main");
		
		//п10 вставляем в основную таблицу данные за ночь
		tw.load(mainType, night);
		System.out.println("complete load night main");
		
		//п11 запускаем оценку основной и расширенной статистики
		sqlQuery.makeCalc(mainType);
		System.out.println("complete mainCalc night");
		sqlQuery.makeCalc(extendType);
		System.out.println("complete extendCalc night");
		
		//п13 в основную таблицу вставляем данные за день, чтобы получить полный набор данных за сутки
		tw.load(mainType, day);
		System.out.println("complete load day main");
		
		//п14.6 удаляем данные из таблицы со статусами
		tw.del(statusType);
		System.out.println("complete del status");
		
		//п14.7 загружаем в таблицу статусов данные за ночь
		tw.load(statusType, night);
		System.out.println("complete load night status");
		
		//п14.8 запуск расчета статистики статуса
		sqlQuery.makeCalc(statusType);
		System.out.println("complete statusCalc");
		
		//п14.9 экспорт статистики статуса в файл
		rm.setPathOfDay(night);
		rm.writeStatisticFile();
		System.out.println("complete report night");
		
		//п14.10 загружаем в статус данные за день, чтобы получить полные сутки
		tw.load(statusType, day);
		System.out.println("complete load day status");
		
		//п2 запускаем функцию сборки данных по началу тумана в таблицу fog_analyze
		sqlQuery.makeCalc(fogStartType);
		System.out.println("complete foganalyzeCalc");
		
		//п3 выгружаем данные из таблицы lt_fog_start_stat в текстовый файл для последующей вставки в xml
		rm.writeStatisticFile("FOGSTART", allDay);
		System.out.println("complete report foganalyze all");
		
		//п15.1 разбиваем данные в таблице начала тумана
		tw.sep(fogStartType);
		System.out.println("complete separate foganalyze");
		
		//п15.2 удаляем данные из таблицы
		tw.del(fogStartType);
		System.out.println("complete del foganalyze");
		
		//п15.3 загружаем данные за день
		tw.load(fogStartType, day);
		System.out.println("complete load day foganalyze");
		
		//п15.4 запускаем расчет статистики
		sqlQuery.makeCalc(fogStartType);
		System.out.println("complete foganalyzeCalc day");
		
		//п15.5 экспорт статистики в файл
		rm.writeStatisticFile("FOGSTART", day);
		System.out.println("complete report foganalyze day");
		
		//п15.6 удаляем данные из таблицы
		tw.del(fogStartType);
		System.out.println("complete del foganalyze");
		
		//п15.7 загружаем данные за ночь
		tw.load(fogStartType, night);
		System.out.println("complete load night foganalyze");
		
		//п15.8 запуск расчета статиситки
		sqlQuery.makeCalc(fogStartType);
		System.out.println("complete foganalyzeCalc night");
		
		//п15.9 экспорт статистики 
		rm.writeStatisticFile("FOGSTART", night);
		System.out.println("complete report foganalyze night");
		
		//п15.10 загрузка данных за день чтобы получить полные сутки
		tw.load(fogStartType, day);
		System.out.println("complete load day foganalyze");
		
		//п16 рапределение данных по корзинам и экспорт в файл
		
		
		//п17 расчет среднего времени появления предупреждения о тумане
		
		
		System.out.println(db.getCurrTime());
		
	}
	
}
