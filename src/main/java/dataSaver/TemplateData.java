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
			"���� ������ �������:?",
			"���� ��������� �������:?",
			"������ ���������� ��� ��������� ��:4�",
			"���� ���������� �������:?",
			"����� ���������� �������:?",
			"������ �������� �� ������� ��"			
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



}
