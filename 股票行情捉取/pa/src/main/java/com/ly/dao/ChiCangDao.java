package com.ly.dao;

import java.util.List;

import com.ly.pojo.ChiCang;

public interface ChiCangDao {
	public List<ChiCang> getAll();
	public void save(ChiCang weiTuo);
}
