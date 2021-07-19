package com.ly.dao;

import java.util.List;
import java.util.Map;

import com.ly.pojo.StockDayLineUpByMa;
import com.ly.pojo.StockMonthLineUpByAuto;
import com.ly.pojo.StockWeekLineUpByAuto;

public interface StockDayLineUpByMaDao {

	public List<StockDayLineUpByMa> getAllByParam(Map map);

	public StockDayLineUpByMa getSingleByParam(Map map);

	public void save(StockDayLineUpByMa user);

	public int getCountByParam(Map map);

	public void insertBatch(List lists);

	public void truncateTable();
}
