package com.ly.dao;

import java.util.List;

import com.ly.pojo.SMSPhone;

public interface SMSDao {

	
	public List<SMSPhone> getSMSListByParam();
	
	public void save(SMSPhone buy);
	
}
