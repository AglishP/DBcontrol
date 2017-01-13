package sqlTest;

import org.junit.Assert;
import org.junit.Test;

import sqlQuery.FunctionName;

public class TestSqlPackage extends Assert {

	@Test
	public void testGetMainFunctionName(){
		
		FunctionName fn = new FunctionName();
		
		fn.setType("main");
		assertEquals("sum_ta_void", fn.getName("ta"));
		assertEquals("sum_vis_void", fn.getName("vis"));
		//System.out.println(fn.getName("ta"));
		
	}
	
	@Test
	public void testWrongType(){
		
		FunctionName fn = new FunctionName();
		fn.setType("ggg");
		assertEquals("error type", fn.getName("ta"));
	}
	
	@Test
	public void testWrongParam(){
		
		FunctionName fn = new FunctionName();
		fn.setType("main");
		assertEquals(null,fn.getName("rrr"));		
	}
	
}
