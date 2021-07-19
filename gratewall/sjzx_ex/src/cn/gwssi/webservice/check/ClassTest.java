package cn.gwssi.webservice.check;

import java.util.HashMap;

public class ClassTest
{

	public static void main(String[] args)
	{
        String className = "cn.gwssi.webservice.check.Check_W_JRJ";   
        try {
			Class c = Class.forName(className);
			ICheckRule iTest=(ICheckRule)c.newInstance();
			HashMap map=new HashMap();
			iTest.checkRule(map);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	
}
