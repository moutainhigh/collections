package com.gwssi.dao;

import java.util.List;
import java.util.Map;

import com.gwssi.pojo.DC_upload_record;

public interface DCUploadRecordDao {

	public List getRecord(Map map);
	
	public void save(DC_upload_record record);
}
