package com.gwssi.dao;

import java.util.List;
import java.util.Map;

import com.gwssi.pojo.EntInfo;

public interface EntInfoDao {
	
	//获取企业信息
	public List<EntInfo> queryEntInfo(Map params);
	
	public Integer queryEntInfoTotals(Map params);

}
