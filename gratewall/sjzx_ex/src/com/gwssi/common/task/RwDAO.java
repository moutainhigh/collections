package com.gwssi.common.task;

import java.util.List;
import java.util.Map;

import com.genersoft.frame.base.database.DBException;

public interface RwDAO
{
	Map queryDataSource(String sql) throws DBException;
	
	List queryMethodList(String sql) throws DBException;
}
