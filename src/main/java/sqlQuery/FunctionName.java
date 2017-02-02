package sqlQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FunctionName {
	
	String typeFunc;
	String queryName;
	ArrayList<String> typeName = new ArrayList<String>(Arrays.asList( "main","extend","status","fogStart")); 
	Map<String, String> mainFuncName;
	
	/**
	 * ����������� �� ���������
	 */
	public FunctionName(){
			
		}
	
	/**
	 * ����������� � ���������� ����
	 * @param inType String ���������
	 */
	public FunctionName(String inType){
		setType(inType);
	}
		
	//������������� ��� 
	public void setType(String inType) {

		if (typeName.indexOf(inType) != -1){
			typeFunc = inType;
			fillNameList();
		}
	}

	/**
	 * �������� ��� ������� �� ���������, ������� ��������
	 * @param inParamName String �������������
	 * @return String ��� ������
	 */
	public String getName(String inParamName) {
		
		String result = "";
		
		if (typeFunc != null){
				result = mainFuncName.get(inParamName);
		}else{
				result = "error type";
		}
		return result;
	}
		
	//��������� ��������� ���������� �������
	private void fillNameList(){
		
		mainFuncName = new HashMap<String, String>();
		
		if (typeFunc == typeName.get(0)){
			mainFuncName.put("ta","sum_ta_void");
			mainFuncName.put("cldh","sum_cldh_void");
			mainFuncName.put("rh","sum_rh_void");
			mainFuncName.put("tdew","sum_tdew_void");
			mainFuncName.put("vis","sum_vis_void");
			mainFuncName.put("wd","sum_wd_void");
			mainFuncName.put("ws","sum_ws_void");
		}else if (typeFunc == typeName.get(1)){
			mainFuncName.put("extend","f_stat_sb");
		}else if (typeFunc == typeName.get(2)){	
			mainFuncName.put("status","f_stat_status");
		}else if (typeFunc == typeName.get(3)){	
			mainFuncName.put("fogStart","f_stat_start_fog");
		}
	
	
	}

}
