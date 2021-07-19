package com.ly.dao;

import java.util.List;

import com.ly.pojo.RestingOrders;

public interface RestingOrdersDao {
	
	public List<RestingOrders> getAll();

	public void save(RestingOrders weiTuo);
}
