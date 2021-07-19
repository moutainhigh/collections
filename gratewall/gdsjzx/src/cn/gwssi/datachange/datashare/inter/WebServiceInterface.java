package cn.gwssi.datachange.datashare.inter;

import java.util.Map;

public interface WebServiceInterface {
	
	public Map callWebService(String serviceId,String ip,String username,String password, String mode,Map mapCondition,Map requiredItems );
	
}
