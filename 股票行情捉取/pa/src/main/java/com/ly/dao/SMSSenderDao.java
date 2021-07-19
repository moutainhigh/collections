package com.ly.dao;

import java.util.List;

import com.ly.pojo.SMSSender;

public interface SMSSenderDao {

	public List<SMSSender> getList();

	public void save(SMSSender buy);

	public void updateSms();

}
