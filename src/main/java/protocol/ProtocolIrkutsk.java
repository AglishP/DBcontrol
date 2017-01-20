package protocol;

import java.sql.Connection;
import sqlQuery.SqlQuery;
import sqlQuery.TableWorker;

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
		
		//п.1 Расчет основной, расширенной статистики и расчет по статусу
		
		String calcType = null;
		SqlQuery sqlQuery = new SqlQuery(myConn, calcType,startDate, endDate);
		
		//расчет основной статистики
		sqlQuery.makeCalc(mainType);
		
		//меняем тип на расширенный
		sqlQuery.makeCalc(extendType);
		
		//теперь статус
		sqlQuery.makeCalc(statusType);
		
		//собираем данные в отчет
		
		//п2 запускаем функцию сборки данных по началу тумана в таблицу fog_analyze
		sqlQuery.makeCalc(fogStartType);
		
		//п3 выгружаем данные из таблицы lt_fog_start_stat в текстовый файл для последующей вставки в xml
		
		//п4 разбить данные из таблиц lt_estim_xxxxx в подтаблицы день/ночь
		TableWorker tw = new TableWorker(myConn, startDate, endDate);
		tw.sep(mainType);
		
		//п5 удаляем данные из основных таблиц
		tw.del(mainType);
		
		//п6 вставляем в основную таблицу данные за день
		tw.load(mainType, day);
		
		//п7 запускаем оценку основной и расширенной статиситки
		sqlQuery.makeCalc(mainType);
		sqlQuery.makeCalc(extendType);
		
		//п8 экспорт основной и расширенной статиситки в файл
		
		//п9 удаляем данные из основных таблиц
		tw.del(mainType);
		
		//п10 вставляем в основную таблицу данные за ночь
		tw.load(mainType, night);
		
		//п11 запускаем оценку основной и расширенной статистики
		sqlQuery.makeCalc(mainType);
		sqlQuery.makeCalc(extendType);
		
		//п12 экспорт основной и расширенной статиситки в файл
		
		//п13 в основную таблицу вставляем данные за день, чтобы получить полный набор данных за сутки
		tw.load(mainType, day);
		
		//п14.1 разбиваем данные из таблицы статусов
		tw.sep(statusType);
		
		//п14.2 удаляем данные из таблицы статусов
		tw.del(statusType);
		
		//п14.3 загружаем данные за день в таблицу статусов
		tw.load(statusType, day);
		
		//п14.4 запускаем расчет статуса 
		sqlQuery.makeCalc(statusType);
				
		//п14.5 экспорт основной и расширенной статиситки в файл
		
		//п14.6 удаляем данные из таблицы со статусами
		tw.del(statusType);
		
		//п14.7 загружаем в таблицу статусов данные за ночь
		tw.load(statusType, night);
		
		//п14.8 запуск расчета статистики статуса
		sqlQuery.makeCalc(statusType);
		
		//п14.9 экспорт статистики статуса в файл
		
		//п14.10 загружаем в статус данные за день, чтобы получить полные сутки
		tw.load(statusType, day);
		
		//п15.1 разбиваем данные в таблице начала тумана
		tw.sep(fogStartType);
		
		//п15.2 удаляем данные из таблицы
		tw.del(fogStartType);
		
		//п15.3 загружаем данные за день
		tw.load(fogStartType, day);
		
		//п15.4 запускаем расчет статистики
		sqlQuery.makeCalc(fogStartType);
		
		//п15.5 экспорт статистики в файл
		
		//п15.6 удаляем данные из таблицы
		tw.del(fogStartType);
		
		//п15.7 загружаем данные за ночь
		tw.load(fogStartType, night);
		
		//п15.8 запуск расчета статиситки
		sqlQuery.makeCalc(fogStartType);
		
		//п15.9 экспорт статистики 
		
		//п15.10 загрузка данных за день чтобы получить полные сутки
		tw.load(fogStartType, day);
		
		//п16 рапределение данных по корзинам и экспорт в файл
		
		//п17 расчет среднего времени появления предупреждения о тумане
		
		
		
		
	}
	
}
