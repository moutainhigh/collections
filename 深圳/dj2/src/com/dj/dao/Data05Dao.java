package com.dj.dao;

import java.util.List;
import java.util.Map;

import com.dj.pojo.Data01;
import com.dj.pojo.Data02;
import com.dj.pojo.Data05;

public interface Data05Dao {

	public List<Data05> getEntInfoByUnifAndEntName(Map map);
}
