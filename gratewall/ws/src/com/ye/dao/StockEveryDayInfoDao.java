package com.ye.dao;

import java.util.List;

import com.bo.domain.StockEveryDayInfo;

public interface StockEveryDayInfoDao {

	public List<StockEveryDayInfo> selectEveryDayPrice();

	public int save(StockEveryDayInfo stock);
}
