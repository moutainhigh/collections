package com.ye.dao;

import java.util.List;

import com.bo.domain.StockEveryDayDeal;

public interface StockEveryDayDealDao {

	public List<StockEveryDayDeal> selectRateList();

	public List<StockEveryDayDeal> selectEveryDayPrice();

	public int save(StockEveryDayDeal stock);

	public int update(StockEveryDayDeal stock);
}
