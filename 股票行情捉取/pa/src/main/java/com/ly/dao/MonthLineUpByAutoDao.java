package com.ly.dao;

import java.util.List;
import java.util.Map;

import com.ly.pojo.StockMonthLineUpByAuto;
import com.ly.pojo.StockWeekLineUpByAuto;

public interface MonthLineUpByAutoDao {

	public List<StockMonthLineUpByAuto> getAllByParam(Map map);

	public StockMonthLineUpByAuto getSingleByParam(Map map);

	public void save(StockMonthLineUpByAuto user);

	public int getCountByParam(Map map);

	public void insertBatch(List lists);

	public void truncateTable();
}
