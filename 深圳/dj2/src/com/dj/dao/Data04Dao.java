package com.dj.dao;

import java.util.List;
import java.util.Map;

import com.dj.pojo.Data01;
import com.dj.pojo.Data02;
import com.dj.pojo.Data04;

public interface Data04Dao {

	public List<Data04> getEntInfoByUnifAndEntName(Map map);
}
