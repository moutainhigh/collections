package com.ly.dao;

import java.util.List;
import java.util.Map;

import com.ly.pojo.StockMonthLineByAutoDataList;
import com.ly.pojo.StockMonthLineUpByAuto;
import com.ly.pojo.StockWeekLineByAutoDataList;

public interface MonthLineUpByAutoDataDao {

	public List<StockMonthLineByAutoDataList> getAll(Map map);

	public void save(StockMonthLineByAutoDataList stockBasic);

	public void insertBatch(List stockBasicLists);

	public void deleteByBatch(List stockBasicLists);

	public void truncateTableData();

}
