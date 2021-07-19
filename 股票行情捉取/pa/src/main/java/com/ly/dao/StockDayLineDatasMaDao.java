package com.ly.dao;

import java.util.List;
import java.util.Map;

import com.ly.pojo.StockDayLineDatasMa;
import com.ly.pojo.StockMonthLineByAutoDataList;
import com.ly.pojo.StockMonthLineUpByAuto;
import com.ly.pojo.StockWeekLineByAutoDataList;

public interface StockDayLineDatasMaDao {

	public List<StockDayLineDatasMa> getAll(Map map);

	public void save(StockDayLineDatasMa stockBasic);

	public void insertBatch(List stockBasicLists);

	public void deleteByBatch(List stockBasicLists);

	public void truncateTableData();

}
