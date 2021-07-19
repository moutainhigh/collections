package cn.gwssi.webservice.check;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;



public interface ICheckRule
{
	
	@SuppressWarnings("rawtypes")
	public boolean checkRule(Map map) throws FileNotFoundException, IOException;
	
}
