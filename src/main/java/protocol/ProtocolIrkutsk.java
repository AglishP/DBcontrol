package protocol;

import java.sql.Connection;
import sqlQuery.SqlQuery;

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
		//�.1 ������ ��������, ����������� ���������� � ������ �� �������
		
		String calcType = null;
		SqlQuery sqlQuery = new SqlQuery(myConn, calcType,startDate, endDate);
		calcType = "main";
		sqlQuery.setCalcType(calcType);
		sqlQuery.makeCalc();
		
		//������ ��� �� �����������
		calcType = "extend";
		sqlQuery.setCalcType(calcType);
		sqlQuery.makeCalc();
		
		//������ ������
		calcType = "status";
		sqlQuery.setCalcType(calcType);
		sqlQuery.makeCalc();
		
		//�������� ������ � �����
		
		//�.2 ��������� ������� ������ ������ �� ������ ������ � ������� fog_analyze
		calcType = "fogStart";
		sqlQuery.setCalcType(calcType);
		sqlQuery.makeCalc();
		
		//�3 ��������� ������ �� ������� lt_fog_start_stat � ��������� ���� ��� ����������� ������� � xml
		
		//�4 ������� ������ �� ������ lt_estim_xxxxx � ���������� ����/����
		
		
	}
	
}
