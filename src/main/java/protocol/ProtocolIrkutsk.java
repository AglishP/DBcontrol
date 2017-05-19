package protocol;

import java.sql.Connection;

import dataSaver.ReportMaker;
import sqlQuery.BasketDisribiution;
import sqlQuery.SqlQuery;
import sqlQuery.TableWorker;
import starter.DateBuilder;

//���������� ��������� ������� ���������� ��� ��������� �� ������� 2016����
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
		
		//������ ����� ������� ������ ���������
		DateBuilder db = new DateBuilder();
		System.out.println(db.getCurrTime());
		
		SqlQuery sqlQuery = new SqlQuery(myConn, startDate, endDate);
		
		//�. ������ �������� ����������
		sqlQuery.makeCalc(mainType);
		System.out.println("complete mainCalc");
		
		//�. ������ ��� �� �����������
		sqlQuery.makeCalc(extendType);
		System.out.println("complete extendCalc");
		
		//�. ������ ������
		sqlQuery.makeCalc(statusType);
		System.out.println("complete statusCalc");
		
		//�. �������� ������ � �����
		ReportMaker rm = new ReportMaker(myConn,allDay, startDate, endDate);
		rm.writeStatisticFile();
		System.out.println("complete report ALL");
		
		//�. ������� ������ �� ������ lt_estim_xxxxx � ���������� ����/����
		TableWorker tw = new TableWorker(myConn, startDate, endDate);
		tw.sep(mainType);
		System.out.println("complete separate main");
		
		//�. ������� ������ �� �������� ������
		tw.del(mainType);
		System.out.println("complete del main");
		
		//�. ��������� � �������� ������� ������ �� ����
		tw.load(mainType, day);
		System.out.println("complete load day main");
		
		//�. ��������� ������ �������� � ����������� ����������
		sqlQuery.makeCalc(mainType);
		System.out.println("complete mainCalc Day");
		sqlQuery.makeCalc(extendType);
		System.out.println("complete extendCalc Day");
		
		//�. ��������� � �������� ������� ������ �� ����, ����� �������� ������ �� ����� ����
		tw.load(mainType, night);
		System.out.println("complete load day main");
		
		//� ��������� ������ �� ������� ��������
		tw.sep(statusType);
		System.out.println("complete separate status");
		
		//�. ������� ������ �� ������� ��������
		tw.del(statusType);
		System.out.println("complete del status");
		
		//�. ��������� ������ �� ���� � ������� ��������
		tw.load(statusType, day);
		System.out.println("complete load day status");
		
		//�. ��������� ������ ������� 
		sqlQuery.makeCalc(statusType);
		System.out.println("complete mainCalc");
		
		//�. ������� ���� ���������� � ����
		rm.setPathOfDay(day);
		rm.writeStatisticFile();
		System.out.println("complete report day");
		
		//�. ������� ������ �� �������� ������
		tw.del(mainType);
		System.out.println("complete del main");
		
		//�. ��������� � �������� ������� ������ �� ����
		tw.load(mainType, night);
		System.out.println("complete load night main");
		
		//�. ��������� ������ �������� � ����������� ����������
		sqlQuery.makeCalc(mainType);
		System.out.println("complete mainCalc night");
		sqlQuery.makeCalc(extendType);
		System.out.println("complete extendCalc night");
		
		//�. � �������� ������� ��������� ������ �� ����, ����� �������� ������ ����� ������ �� �����
		tw.load(mainType, day);
		System.out.println("complete load day main");
		
		//�. ������� ������ �� ������� �� ���������
		tw.del(statusType);
		System.out.println("complete del status");
		
		//�. ��������� � ������� �������� ������ �� ����
		tw.load(statusType, night);
		System.out.println("complete load night status");
		
		//�. ������ ������� ���������� �������
		sqlQuery.makeCalc(statusType);
		System.out.println("complete statusCalc");
		
		//�. ������� ���������� ������� � ����
		rm.setPathOfDay(night);
		rm.writeStatisticFile();
		System.out.println("complete report night");
		
		//�. ��������� � ������ ������ �� ����, ����� �������� ������ �����
		tw.load(statusType, day);
		System.out.println("complete load day status");
		
		//�. ��������� ������� ������ ������ �� ������ ������ � ������� fog_analyze
		sqlQuery.makeCalc(fogStartType);
		System.out.println("complete foganalyzeCalc");
		
		//�. ��������� ������ �� ������� lt_fog_start_stat � ��������� ���� ��� ����������� ������� � xml
		rm.writeStatisticFile("FOGSTART", allDay);
		System.out.println("complete report foganalyze all");
		
		//�. ������������ ������ �� �������� � ������� � ����
		BasketDisribiution bd = new BasketDisribiution(myConn, startDate, endDate);
		bd.makeBasketReport();
		System.out.println("complete basket");
		
		//�. ������ �������� ������� ��������� �������������� � ������
		
		
		System.out.println(db.getCurrTime());
		
	}
	
}
