package com.gwssi.upload.service;


import java.util.List;

import com.gwssi.upload.pojo.BaseInfo;

public interface FindReaderService {
	
	public List<BaseInfo> findEntInfoByLastDate(String date);
	
}
