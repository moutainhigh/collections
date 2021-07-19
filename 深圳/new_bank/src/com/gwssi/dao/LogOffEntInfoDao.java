package com.gwssi.dao;

import java.util.List;
import java.util.Map;

import com.gwssi.pojo.LogOff;

public interface LogOffEntInfoDao {
	
	//获取注销企业信息
	public List<LogOff> queryLogOffEntInfo(Map params);
	
	public Integer queryLogOffEntInfoTotals(Map params);

}
