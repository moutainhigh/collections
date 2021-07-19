package com.ly.dao;

import java.util.List;
import java.util.Map;

import com.ly.pojo.StockWeekLineUpByAuto;

public interface WeekLineUpByAutoDao {

	public List<StockWeekLineUpByAuto> getAllByParam(Map map);

	public StockWeekLineUpByAuto getSingleByParam(Map map);

	public void save(StockWeekLineUpByAuto user);

	public int getCountByParam(Map map);

	public void insertBatch(List lists);

	public void truncateTable();
}
