package com.gwssi.entSelect.dao;

import java.util.List;

import com.gwssi.entSelect.pojo.AltInfoBo;

public interface AltInfoDao {

	public void insertBatch(List list);
	
	public void save(AltInfoBo altinfo);
	
}
