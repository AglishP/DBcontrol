package protocol;

import java.sql.Connection;

import dataSaver.ReportMaker;
import sqlQuery.BasketDisribiution;
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
		
		//ставим метку времени начала протогола
		DateBuilder db = new DateBuilder();
		System.out.println(db.getCurrTime());
		
		SqlQuery sqlQuery = new SqlQuery(myConn, startDate, endDate);
		
		//п. расчет основной статистики
		sqlQuery.makeCalc(mainType);
		System.out.println("complete mainCalc");
		
		//п. меняем тип на расширенный
		sqlQuery.makeCalc(extendType);
		System.out.println("complete extendCalc");
		
		//п. теперь статус
		sqlQuery.makeCalc(statusType);
		System.out.println("complete statusCalc");
		
		//п. собираем данные в отчет
		ReportMaker rm = new ReportMaker(myConn,allDay, startDate, endDate);
		rm.writeStatisticFile();
		System.out.println("complete report ALL");
		
		//п. разбить данные из таблиц lt_estim_xxxxx в подтаблицы день/ночь
		TableWorker tw = new TableWorker(myConn, startDate, endDate);
		tw.sep(mainType);
		System.out.println("complete separate main");
		
		//п. удаляем данные из основных таблиц
		tw.del(mainType);
		System.out.println("complete del main");
		
		//п. вставляем в основную таблицу данные за день
		tw.load(mainType, day);
		System.out.println("complete load day main");
		
		//п. запускаем оценку основной и расширенной статиситки
		sqlQuery.makeCalc(mainType);
		System.out.println("complete mainCalc Day");
		sqlQuery.makeCalc(extendType);
		System.out.println("complete extendCalc Day");
		
		//п. вставляем в основную таблицу данные за ночь, чтобы получить данные за целый день
		tw.load(mainType, night);
		System.out.println("complete load day main");
		
		//п разбиваем данные из таблицы статусов
		tw.sep(statusType);
		System.out.println("complete separate status");
		
		//п. удаляем данные из таблицы статусов
		tw.del(statusType);
		System.out.println("complete del status");
		
		//п. загружаем данные за день в таблицу статусов
		tw.load(statusType, day);
		System.out.println("complete load day status");
		
		//п. запускаем расчет статуса 
		sqlQuery.makeCalc(statusType);
		System.out.println("complete mainCalc");
		
		//п. экспорт всей статиситки в файл
		rm.setPathOfDay(day);
		rm.writeStatisticFile();
		System.out.println("complete report day");
		
		//п. удаляем данные из основных таблиц
		tw.del(mainType);
		System.out.println("complete del main");
		
		//п. вставляем в основную таблицу данные за ночь
		tw.load(mainType, night);
		System.out.println("complete load night main");
		
		//п. запускаем оценку основной и расширенной статистики
		sqlQuery.makeCalc(mainType);
		System.out.println("complete mainCalc night");
		sqlQuery.makeCalc(extendType);
		System.out.println("complete extendCalc night");
		
		//п. в основную таблицу вставляем данные за день, чтобы получить полный набор данных за сутки
		tw.load(mainType, day);
		System.out.println("complete load day main");
		
		//п. удаляем данные из таблицы со статусами
		tw.del(statusType);
		System.out.println("complete del status");
		
		//п. загружаем в таблицу статусов данные за ночь
		tw.load(statusType, night);
		System.out.println("complete load night status");
		
		//п. запуск расчета статистики статуса
		sqlQuery.makeCalc(statusType);
		System.out.println("complete statusCalc");
		
		//п. экспорт статистики статуса в файл
		rm.setPathOfDay(night);
		rm.writeStatisticFile();
		System.out.println("complete report night");
		
		//п. загружаем в статус данные за день, чтобы получить полные сутки
		tw.load(statusType, day);
		System.out.println("complete load day status");
		
		//п. запускаем функцию сборки данных по началу тумана в таблицу fog_analyze
		sqlQuery.makeCalc(fogStartType);
		System.out.println("complete foganalyzeCalc");
		
		//п. выгружаем данные из таблицы lt_fog_start_stat в текстовый файл для последующей вставки в xml
		rm.writeStatisticFile("FOGSTART", allDay);
		System.out.println("complete report foganalyze all");
		
		//п. рапределение данных по корзинам и экспорт в файл
		BasketDisribiution bd = new BasketDisribiution(myConn, startDate, endDate);
		bd.makeBasketReport();
		System.out.println("complete basket");
		
		//п. расчет среднего времени появления предупреждения о тумане
		
		
		System.out.println(db.getCurrTime());
		
	}
	
}
