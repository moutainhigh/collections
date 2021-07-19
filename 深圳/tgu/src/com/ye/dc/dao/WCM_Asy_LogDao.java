package com.ye.dc.dao;

import java.util.List;
import java.util.Map;

import com.ye.dc.pojo.WCM_Asy_Log;

public interface WCM_Asy_LogDao {

	public void save(WCM_Asy_Log log);	
	
	public List<WCM_Asy_Log> getRecordList();
	
	public List<WCM_Asy_Log> getHasAddList();
	
	public WCM_Asy_Log findById(Map  map);
}
