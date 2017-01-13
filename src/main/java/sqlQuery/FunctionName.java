package sqlQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FunctionName {
	
	String typeFunc;
	String queryName;
	
	//допустимые наборы функций
	ArrayList<String> typeName = new ArrayList<String>(Arrays.asList( "main","extend","status")); 
	Map<String, String> mainFucnName;
	

	public void setType(String inType) {
		// TODO Auto-generated method stub
		if (typeName.indexOf(inType) != -1){
			typeFunc = inType;
			fillNameList();
		}
	}

	public String getName(String inParamName) {
		// TODO Auto-generated method stub
		String result = "";
		
		if (typeFunc == "main"){
				result = getMainFunc(inParamName);
		}else{
				result = "error type";
		}
		return result;
	}
	
	private String getMainFunc(String inParam){
		
		String result = "";
		
		result = mainFucnName.get(inParam);
						
		return result;
	}
	
	private void fillNameList(){
		
		if (typeFunc == "main"){
			mainFucnName = new HashMap<String, String>();
			mainFucnName.put("ta","sum_ta_void");
			mainFucnName.put("cldh","sum_cldh_void");
			mainFucnName.put("rh","sum_rh_void");
			mainFucnName.put("tdew","sum_tdew_void");
			mainFucnName.put("vis","sum_vis_void");
			mainFucnName.put("wd","sum_wd_void");
			mainFucnName.put("ws","sum_ws_void");
		}else{
			
		}
	
	
	}

}
