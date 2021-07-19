package com.ly.dao;

import java.util.List;
import java.util.Map;

import com.ly.pojo.Sell;

public interface SellDao {

	public List<Sell> getSellList();

	public void save(Sell sell);

	public void del(Sell sell);

	public List getSellByParam(Map map);

	public int getSellTotalByParam(Map map);

}
