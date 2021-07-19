package com.ly.dao;

import java.util.List;
import java.util.Map;

import com.ly.pojo.Buy;

public interface BuyDao {

	public List<Buy> getBuyByParam(Map map);

	public Integer getBuyTotalByParam(Map map);
	
	public List<Buy> getBuyList();
	
	
	public List<Buy> getNonNameList();
	
	public void save(Buy buy);
	
	
	public void del(Buy buy);
	
}
