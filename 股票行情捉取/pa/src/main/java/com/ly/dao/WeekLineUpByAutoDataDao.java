package com.ly.dao;

import java.util.List;
import java.util.Map;

import com.ly.pojo.StockWeekLineByAutoDataList;

public interface WeekLineUpByAutoDataDao {

	public List<StockWeekLineByAutoDataList> getAll(Map map);

	public void save(StockWeekLineByAutoDataList stockBasic);

	public void insertBatch(List stockBasicLists);

	public void deleteByBatch(List stockBasicLists);
	
	public void truncateTableData();

}
