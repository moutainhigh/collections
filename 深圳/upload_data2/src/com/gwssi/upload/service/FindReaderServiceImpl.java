package com.gwssi.upload.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwssi.upload.mapper.BaseEntByTimeMapper;
import com.gwssi.upload.pojo.BaseInfo;

/**
 * 读id
 * @author Administrator
 *
 */
@Service
public class FindReaderServiceImpl implements FindReaderService {
	
	@Autowired
	private BaseEntByTimeMapper baseEntByTimeMapper;
	
	//根据时间戳获取基表中的数据
	public List<BaseInfo> findEntInfoByLastDate(String time) {
		return baseEntByTimeMapper.queryEntInfoByTime(time);
	}

	
	
}
