package com.ly.dao;

import java.util.List;
import java.util.Map;

import com.ly.pojo.RecentStock;

public interface RecentStockDao {

	public List<RecentStock> getListByParam(Map map);

	public Integer getTotalByParam(Map map);

	public void save(RecentStock recentPrice);
}
