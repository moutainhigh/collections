package com.dj.dao;

import java.util.List;
import java.util.Map;

import com.dj.pojo.Data01;
import com.dj.pojo.Data02;
import com.dj.pojo.Data03;

public interface Data03Dao {

	public List<Data03> getEntInfoByUnifAndEntName(Map map);
}
