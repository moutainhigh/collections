package com.bwcx.dao;

import java.util.List;
import java.util.Map;

import com.bwcx.pojo.DocumentEntity;

public interface DocumentDao {

	public DocumentEntity getDocumentDetail(Map map);

	public void save(DocumentEntity doc);
	
	public void saveThumb(Map map);
	
	
	public void saveFile(Map map);

	public List<DocumentEntity> getDocumentsByChannel(Map map);

	public List<DocumentEntity> getPubDocumentsByChannel(Map map);

	public List<DocumentEntity> getDocusWithThumb(Map map);

	public List<DocumentEntity> getPubDocusWithThumb(Map cid);

	public void deleteByBatch(List<String> lists);

	public Integer getCount(Map docId);
	
	public Integer getPubCount(Map docId);
	
	
	public Integer queryPubCount(Map map);
	
	
	public  List<DocumentEntity> getPublishFromWcm();

}
