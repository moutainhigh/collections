package com.gwssi.upload.mapper;

import java.util.List;

import com.gwssi.upload.pojo.BaseInfo;

/**
 * 根据时间戳 读取数据库中的数据
 * @author Administrator
 *
 */
public interface BaseEntByTimeMapper {
	
	//根据时间戳获取信息
	public List<BaseInfo> queryEntInfoByTime(String time);
	
}
