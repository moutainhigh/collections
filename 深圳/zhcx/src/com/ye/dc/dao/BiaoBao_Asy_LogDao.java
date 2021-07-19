package com.ye.dc.dao;

import java.util.List;

import com.ye.dc.pojo.BaoBiao_Asy_Log;

public interface BiaoBao_Asy_LogDao {

	public void save(BaoBiao_Asy_Log log);	
	
	public List<BaoBiao_Asy_Log> getRecordList();
}
