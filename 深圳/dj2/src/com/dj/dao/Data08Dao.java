package com.dj.dao;

import java.util.List;
import java.util.Map;

import com.dj.pojo.Data01;
import com.dj.pojo.Data02;
import com.dj.pojo.Data08;

public interface Data08Dao {

	public List<Data08> getEntInfoByUnifAndEntName(Map map);
}
