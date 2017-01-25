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
			"Rh. �����������:<Rh>(%), ��� �������� ��<RhLvl>(%)"
			));
		
	final ArrayList<String> mainTemplate = new ArrayList<String>(Arrays.asList(
			"		�������� <param>",
			"			VIS. ������� ���������:<successforecast> ( <successforecastpercent>%)",
			"			VIS. ����� ���������/���.������: <sumforecstdata>",
			"			VIS. ��� ������ �����:<noforecastdata>",
			"			VIS. ���: <sko>",
			"			VIS. ������� ����������: <mean>",
			"			VIS. ������� �� ������:  <absmean>"
			));
	
	final ArrayList<String> extendTemplate = new ArrayList<String>(Arrays.asList(
			"	���� ������ �������:<indate>",
			"	���� ��������� �������:<outdate>",
			"		��������:<param>",
			"		��������� �������� ��� ���������� �� ��������:<tsh_in>",
			"		������� �:<a_basket>",
			"		������� �:<b_basket>",
			"		������� �:<c_basket>",
			"		������� D:<d_basket>",
			"		�������� PC:<pc>",
			"		�������� PC plus:<pc_plus>",
			"		�������� PC minus:<pc_minus>",
			"		�������� P plus:<p_plus>",
			"		�������� P minus:<p_minus>",
			"		�������� F:<far>",
			"		�������� H:<hit>",
			"		�������� MISS:<miss>",
			"		�������� ORSS:<orss>",
			"		�������� EDI:<edi>",
			"		�������� SEDI:<sedi>"
			));
	
	final ArrayList<String> mainHeadTemplate = new ArrayList<String>(Arrays.asList(
			"----------------------------------",
			"	�������-<station>"
			));
	
	final ArrayList<String> extendHeadTemplate = new ArrayList<String>(Arrays.asList(
			"----------------------------------",
			"����� ����������� ���������� �� �������� �� ��������� ������",
			"	�������-30791"
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
	 * ���������� ������ ������ � �������� ��� ����������� ����������
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getExtendTemplate(){
		
		return extendTemplate;
	}
	
	
	/**
	 * ���������� ��������� ��� ������ ��������
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getMainHeadTemplate(){
		
		return mainHeadTemplate;
	}
	
	/**
	 * ���������� ��������� ��� ������������ ��������
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getExtendHeadTemplate(){
		
		return extendHeadTemplate;
	}
}
