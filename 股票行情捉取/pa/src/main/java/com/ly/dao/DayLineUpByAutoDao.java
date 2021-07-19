package com.ly.dao;

import java.util.List;
import java.util.Map;

import com.ly.pojo.StockDayLineUpByAuto;
import com.ly.pojo.StockWeekLineUpByAuto;

public interface DayLineUpByAutoDao {
	public List<StockDayLineUpByAuto> getAllByParam(Map map);

	public StockWeekLineUpByAuto getSingleByParam(Map map);

	public void save(StockDayLineUpByAuto dayInfo);

	public int getCountByParam(Map map);

	public void insertBatch(List lists);

	public void truncateTable();
}
