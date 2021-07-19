package com.bwcx.dao;

import java.util.List;
import java.util.Map;

import com.bwcx.pojo.DocumentEntity;

public interface UpdateDao {
	

	public void save(DocumentEntity doc);
	public void insert(String sql );

	public void update(String sql);
	
	public List<Map> select(String sql);
	
	public  List<DocumentEntity> getPublishFromWcm();
}
