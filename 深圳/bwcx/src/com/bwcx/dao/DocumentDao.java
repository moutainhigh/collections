package com.bwcx.dao;

import java.util.List;

import com.bwcx.pojo.DocumentEntity;

public interface DocumentDao {

	public DocumentEntity getDocumentDetail(String docId);

	public DocumentEntity save(DocumentEntity doc);
	
	public List<DocumentEntity> getDocumentEntitys(String chanelId);
	
	
}
