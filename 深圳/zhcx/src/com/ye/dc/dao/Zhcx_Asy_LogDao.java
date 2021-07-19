package com.ye.dc.dao;

import java.util.List;

import com.ye.dc.pojo.Zhcx_Asy_Log;

public interface Zhcx_Asy_LogDao {

	public void save(Zhcx_Asy_Log log);	
	
	public List<Zhcx_Asy_Log> getRecordList();
}
