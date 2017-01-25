package dataSaver;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * ������ ������ � ��������� �����
 * @author ����� �����
 *
 */
public class TemplateData {

	final ArrayList<String> headTemplate = new ArrayList<String>(Arrays.asList(
			"���� ������ �������:<StartDate>",
			"���� ��������� �������:<EndDate>",
			"������ ���������� ��� ��������� ��:4�",
			"���� ���������� �������:<CurrDate>",
			"����� ���������� �������:<CurrTime>",
			"������ �������� �� ������� ��"			
			));
	
	final ArrayList<String> tshTemplate = new ArrayList<String>(Arrays.asList(
			"���������, ��� ������� ����������� ������:",
			"Vis. �����������:<Vis1Tsh>(�) ��� ���������  ��:<Vis1Lvl>(�)",
			"Vis. �����������:<Vis2Tsh>(�) ��� ���������  ��:<Vis2Lvl>(�)",
			"Cldh. �����������:<Cldh1Tsh>(�) �� ������  ��:<Cldh1Lvl>(�)",
			"Cldh. �����������:<Cldh2Tsh>(%) �� ������  ��:<Cldh2Lvl>(�)",
			"Tair. �����������:<Ta>(����.�)",
			"Tdew. �����������:<Tdew>(����.�)",
			"Ws. �����������:<Ws>(����.�) ��� �������� ����� �����:<Ws_LowLvl>(�/�)",
			"Wd. �����������:<Wd>(����.�) ��� �������� ����� �����:<Wd_LowLvl>(�/�)",
			"Rh. �����������:<Rh>(%), ��� �������� ��<RhLvl>(%)",
			"----------------------------------"
			));
		
	final ArrayList<String> mainTemplate = new ArrayList<String>(Arrays.asList(
			"		�������� <param>",
			"			VIS. ������� ���������:<successforecast> ( <successforecastpercent>%)",
			"			VIS. ����� ���������/���.������: <sumforecstdata>",
			"			VIS. ��� ������ �����:<noforecastdata>",
			"			VIS. ���: <sko>",
			"			VIS. ������� ����������: <mean>",
			"			VIS. ������� �� ������:  <absmean>",
			"----------------------------------"
			));
	
	final ArrayList<String> mainHeadTemplate = new ArrayList<String>(Arrays.asList(
			"	�������-<station>"
			));
	
	/**
	 * ����������� �� ���������
	 */
	public TemplateData(){
		
	}

	/**
	 * ���������� ������ ������ � �������� �������� ������
	 * @return ArrayList<String> 
	 */
	public ArrayList<String> getHeadTemplate(){
		
		return headTemplate;		
	}

	/**
	 * ���������� ������ ������ � �������� ��� ��������� ��������
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getTshTemplate(){
		
		return tshTemplate;
	}

	/**
	 * ���������� ������ ������ � �������� ��� �������� ����������
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getMainTemplate(){
		
		return mainTemplate;
	}
	
	/**
	 * ���������� ��������� ��� ������ ��������
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getMainHeadTemplate(){
		
		return mainHeadTemplate;
	}
}
