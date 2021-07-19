package com.ly.dao;

import java.util.List;
import java.util.Map;

import com.ly.pojo.Stocks;

public interface StockDao {

	public List<Stocks> getAll();

	public List<Stocks> findSingleStockPinYin(Map map);

	public List<Stocks> findSingleStockByCode(Map map);

	public List<Stocks> findSingleStockByCodeForBuy(Map map);

	public List<Stocks> getListForDay(Map map);

	public int getTotalsForDay();

	public void insertBatch(List lists);

	public void save(Stocks stockBasic);

	public void updatePinyin(Stocks stockBasic);

	public void deleteByBatch(List<Stocks> lists);

	public void truncateTable();
}
