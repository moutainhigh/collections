package com.ye.dc.dao;

import java.util.Map;

import com.ye.dc.pojo.BaoBiaoUser;
import com.ye.dc.pojo.User;

public interface BaoBiaoDao {
	public void save(BaoBiaoUser user);

	public BaoBiaoUser getUserIsExit(Map map);
}
