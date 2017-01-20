package protocol;

import java.sql.Connection;
import sqlQuery.SqlQuery;
import sqlQuery.TableWorker;

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
		
		//�.1 ������ ��������, ����������� ���������� � ������ �� �������
		
		String calcType = null;
		SqlQuery sqlQuery = new SqlQuery(myConn, calcType,startDate, endDate);
		
		//������ �������� ����������
		sqlQuery.makeCalc(mainType);
		
		//������ ��� �� �����������
		sqlQuery.makeCalc(extendType);
		
		//������ ������
		sqlQuery.makeCalc(statusType);
		
		//�������� ������ � �����
		
		//�2 ��������� ������� ������ ������ �� ������ ������ � ������� fog_analyze
		sqlQuery.makeCalc(fogStartType);
		
		//�3 ��������� ������ �� ������� lt_fog_start_stat � ��������� ���� ��� ����������� ������� � xml
		
		//�4 ������� ������ �� ������ lt_estim_xxxxx � ���������� ����/����
		TableWorker tw = new TableWorker(myConn, startDate, endDate);
		tw.sep(mainType);
		
		//�5 ������� ������ �� �������� ������
		tw.del(mainType);
		
		//�6 ��������� � �������� ������� ������ �� ����
		tw.load(mainType, day);
		
		//�7 ��������� ������ �������� � ����������� ����������
		sqlQuery.makeCalc(mainType);
		sqlQuery.makeCalc(extendType);
		
		//�8 ������� �������� � ����������� ���������� � ����
		
		//�9 ������� ������ �� �������� ������
		tw.del(mainType);
		
		//�10 ��������� � �������� ������� ������ �� ����
		tw.load(mainType, night);
		
		//�11 ��������� ������ �������� � ����������� ����������
		sqlQuery.makeCalc(mainType);
		sqlQuery.makeCalc(extendType);
		
		//�12 ������� �������� � ����������� ���������� � ����
		
		//�13 � �������� ������� ��������� ������ �� ����, ����� �������� ������ ����� ������ �� �����
		tw.load(mainType, day);
		
		//�14.1 ��������� ������ �� ������� ��������
		tw.sep(statusType);
		
		//�14.2 ������� ������ �� ������� ��������
		tw.del(statusType);
		
		//�14.3 ��������� ������ �� ���� � ������� ��������
		tw.load(statusType, day);
		
		//�14.4 ��������� ������ ������� 
		sqlQuery.makeCalc(statusType);
				
		//�14.5 ������� �������� � ����������� ���������� � ����
		
		//�14.6 ������� ������ �� ������� �� ���������
		tw.del(statusType);
		
		//�14.7 ��������� � ������� �������� ������ �� ����
		tw.load(statusType, night);
		
		//�14.8 ������ ������� ���������� �������
		sqlQuery.makeCalc(statusType);
		
		//�14.9 ������� ���������� ������� � ����
		
		//�14.10 ��������� � ������ ������ �� ����, ����� �������� ������ �����
		tw.load(statusType, day);
		
		//�15.1 ��������� ������ � ������� ������ ������
		tw.sep(fogStartType);
		
		//�15.2 ������� ������ �� �������
		tw.del(fogStartType);
		
		//�15.3 ��������� ������ �� ����
		tw.load(fogStartType, day);
		
		//�15.4 ��������� ������ ����������
		sqlQuery.makeCalc(fogStartType);
		
		//�15.5 ������� ���������� � ����
		
		//�15.6 ������� ������ �� �������
		tw.del(fogStartType);
		
		//�15.7 ��������� ������ �� ����
		tw.load(fogStartType, night);
		
		//�15.8 ������ ������� ����������
		sqlQuery.makeCalc(fogStartType);
		
		//�15.9 ������� ���������� 
		
		//�15.10 �������� ������ �� ���� ����� �������� ������ �����
		tw.load(fogStartType, day);
		
		//�16 ������������ ������ �� �������� � ������� � ����
		
		//�17 ������ �������� ������� ��������� �������������� � ������
		
		
		
		
	}
	
}
