package com.ye.dao;

import java.util.List;

import com.bo.domain.SMSStockEveryDayDeal;

public interface SMSStockEveryDayDealDao {

	public List<SMSStockEveryDayDeal> selectRateList();
	
	public List<SMSStockEveryDayDeal> selectEveryDayPrice();
	
	public int save(SMSStockEveryDayDeal dailyStockEveryDayDeal);
}
