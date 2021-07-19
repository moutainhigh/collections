package com.ly.dao;

import java.util.List;
import java.util.Map;

import com.ly.pojo.MyExcepitonLog;

public interface MyExcepitonLogDao {

	public List<MyExcepitonLog> getExceptionByParam(Map map);

	public void save(MyExcepitonLog ex);

}
