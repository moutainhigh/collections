package com.dj.dao;

import java.util.List;
import java.util.Map;

import com.dj.pojo.Data01;
import com.dj.pojo.Data02;
import com.dj.pojo.Data06;

public interface Data06Dao {

	public List<Data06> getEntInfoByUnifAndEntName(Map map);
}
