package com.ye.dao;

import java.util.List;

import com.bo.domain.SMSStockEveryDayInfo;
//���Ź�
public interface SMSStockEveryDayInfoDao {
	
	public List<SMSStockEveryDayInfo> selectEveryDayPrice();
	
	public int save(SMSStockEveryDayInfo stock);
}
