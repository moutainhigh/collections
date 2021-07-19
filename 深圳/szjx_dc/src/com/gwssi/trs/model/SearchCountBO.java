package com.gwssi.trs.model;

import java.math.BigDecimal;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * SEARCH_COUNT表对应的实体类
 */
@Entity
@Table(name = "SEARCH_COUNT")
public class SearchCountBO extends AbsDaoBussinessObject {
	
	public SearchCountBO(){}

	private String searchText;	
	private BigDecimal searchCount;	
	private String searchId;	
	
	@Column(name = "SEARCH_TEXT")
	public String getSearchText(){
		return searchText;
	}
	public void setSearchText(String searchText){
		this.searchText = searchText;
		markChange("searchText", searchText);
	}
	@Column(name = "SEARCH_COUNT")
	public BigDecimal getSearchCount(){
		return searchCount;
	}
	public void setSearchCount(BigDecimal searchCount){
		this.searchCount = searchCount;
		markChange("searchCount", searchCount);
	}
	@Id
	@Column(name = "SEARCH_ID")
	public String getSearchId(){
		return searchId;
	}
	public void setSearchId(String searchId){
		this.searchId = searchId;
		markChange("searchId", searchId);
	}
}