package protocol;

import java.sql.Connection;

import dataSaver.ReportMaker;
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
		
//		//������ ����� ������� ������ ���������
		DateBuilder db = new DateBuilder();
//		System.out.println(db.getCurrTime());
//		
//		//�.1 ������ ��������, ����������� ���������� � ������ �� �������
		SqlQuery sqlQuery = new SqlQuery(myConn, startDate, endDate);
//		
//		//������ �������� ����������
//		sqlQuery.makeCalc(mainType);
//		System.out.println("complete mainCalc");
//		
//		//������ ��� �� �����������
		sqlQuery.makeCalc(extendType);
		System.out.println("complete extendCalc");
//		
//		//������ ������
//		sqlQuery.makeCalc(statusType);
//		System.out.println("complete statusCalc");
		
		//�������� ������ � �����
		ReportMaker rm = new ReportMaker(myConn,allDay, startDate, endDate);
		rm.writeStatisticFile();
		System.out.println("complete report ALL");
		
		//�4 ������� ������ �� ������ lt_estim_xxxxx � ���������� ����/����
		TableWorker tw = new TableWorker(myConn, startDate, endDate);
		tw.sep(mainType);
		System.out.println("complete separate main");
		
		//�5 ������� ������ �� �������� ������
		tw.del(mainType);
		System.out.println("complete del main");
		
		//�6 ��������� � �������� ������� ������ �� ����
		tw.load(mainType, day);
		System.out.println("complete load day main");
		
		//�7 ��������� ������ �������� � ����������� ����������
		sqlQuery.makeCalc(mainType);
		System.out.println("complete mainCalc Day");
		sqlQuery.makeCalc(extendType);
		System.out.println("complete extendCalc Day");
		
		//�14.1 ��������� ������ �� ������� ��������
		tw.sep(statusType);
		System.out.println("complete separate status");
		
		//�14.2 ������� ������ �� ������� ��������
		tw.del(statusType);
		System.out.println("complete del status");
		
		//�14.3 ��������� ������ �� ���� � ������� ��������
		tw.load(statusType, day);
		System.out.println("complete load day status");
		
		//�13 � �������� ������� ��������� ������ �� ����, ����� �������� ������ ����� ������ �� �����
		//tw.load(mainType, night);
		//System.out.println("complete load night main");
		
		//�14.4 ��������� ������ ������� 
		sqlQuery.makeCalc(statusType);
		System.out.println("complete mainCalc");
		
		//�8 ������� ���� ���������� � ����
		rm.setPathOfDay(day);
		rm.writeStatisticFile();
		System.out.println("complete report day");
		
		//�9 ������� ������ �� �������� ������
		tw.del(mainType);
		System.out.println("complete del main");
		
		//�10 ��������� � �������� ������� ������ �� ����
		tw.load(mainType, night);
		System.out.println("complete load night main");
		
		//�11 ��������� ������ �������� � ����������� ����������
		sqlQuery.makeCalc(mainType);
		System.out.println("complete mainCalc night");
		sqlQuery.makeCalc(extendType);
		System.out.println("complete extendCalc night");
		
		//�13 � �������� ������� ��������� ������ �� ����, ����� �������� ������ ����� ������ �� �����
		tw.load(mainType, day);
		System.out.println("complete load day main");
		
		//�14.6 ������� ������ �� ������� �� ���������
		tw.del(statusType);
		System.out.println("complete del status");
		
		//�14.7 ��������� � ������� �������� ������ �� ����
		tw.load(statusType, night);
		System.out.println("complete load night status");
		
		//�14.8 ������ ������� ���������� �������
		sqlQuery.makeCalc(statusType);
		System.out.println("complete statusCalc");
		
		//�14.9 ������� ���������� ������� � ����
		rm.setPathOfDay(night);
		rm.writeStatisticFile();
		System.out.println("complete report night");
		
		//�14.10 ��������� � ������ ������ �� ����, ����� �������� ������ �����
		tw.load(statusType, day);
		System.out.println("complete load day status");
		
		//�2 ��������� ������� ������ ������ �� ������ ������ � ������� fog_analyze
		sqlQuery.makeCalc(fogStartType);
		System.out.println("complete foganalyzeCalc");
		
		//�3 ��������� ������ �� ������� lt_fog_start_stat � ��������� ���� ��� ����������� ������� � xml
		rm.writeStatisticFile("FOGSTART", allDay);
		System.out.println("complete report foganalyze all");
		
		//�15.1 ��������� ������ � ������� ������ ������
		tw.sep(fogStartType);
		System.out.println("complete separate foganalyze");
		
		//�15.2 ������� ������ �� �������
		tw.del(fogStartType);
		System.out.println("complete del foganalyze");
		
		//�15.3 ��������� ������ �� ����
		tw.load(fogStartType, day);
		System.out.println("complete load day foganalyze");
		
		//�15.4 ��������� ������ ����������
		sqlQuery.makeCalc(fogStartType);
		System.out.println("complete foganalyzeCalc day");
		
		//�15.5 ������� ���������� � ����
		rm.writeStatisticFile("FOGSTART", day);
		System.out.println("complete report foganalyze day");
		
		//�15.6 ������� ������ �� �������
		tw.del(fogStartType);
		System.out.println("complete del foganalyze");
		
		//�15.7 ��������� ������ �� ����
		tw.load(fogStartType, night);
		System.out.println("complete load night foganalyze");
		
		//�15.8 ������ ������� ����������
		sqlQuery.makeCalc(fogStartType);
		System.out.println("complete foganalyzeCalc night");
		
		//�15.9 ������� ���������� 
		rm.writeStatisticFile("FOGSTART", night);
		System.out.println("complete report foganalyze night");
		
		//�15.10 �������� ������ �� ���� ����� �������� ������ �����
		tw.load(fogStartType, day);
		System.out.println("complete load day foganalyze");
		
		//�16 ������������ ������ �� �������� � ������� � ����
		
		
		//�17 ������ �������� ������� ��������� �������������� � ������
		
		
		System.out.println(db.getCurrTime());
		
	}
	
}
