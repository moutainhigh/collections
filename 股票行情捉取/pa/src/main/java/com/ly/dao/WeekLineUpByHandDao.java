package com.ly.dao;

import java.util.List;
import java.util.Map;

import com.ly.pojo.StockWeekLineUpByHand;

public interface WeekLineUpByHandDao {

	public List<StockWeekLineUpByHand> getAllByParam(Map map);

	public int getCountByParam(Map map);

	public StockWeekLineUpByHand getSingleByParam(Map map);

	public void save(StockWeekLineUpByHand hand);

	public void insertBatch(List lists);

	public void deleteByBatch(List lists);

	public void truncateTable();
}
