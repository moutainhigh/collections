package com.gwssi.application.home.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PageResult {
	public PageResult(){}
	//总记录数
	private long totalCount;
	//当前页号
	private int pageNo=1;
	//总页数
	private int totalPageCount;
	//页大小
	private int pageSize=10;
	//记录列表
	private List items;
	
	private String freeMReturnString;
	
	private List<Map> querylist;
	
	public List<Map> getQuerylist() {
		return querylist;
	}
	public void setQuerylist(List<Map> querylist) {
		this.querylist = querylist;
	}
	//构造分页对象；主要目的计算总页数
	public PageResult(long totalCount, int pageSize) {
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		if (totalCount != 0) {
			int tem = (int)totalCount/pageSize;
			this.totalPageCount = (totalCount%pageSize==0)?tem:(tem+1);
		} else {
			this.pageNo = 0;
		}
		
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getTotalPageCount() {
		return totalPageCount;
	}
	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List getItems() {
		return items;
	}
	public void setItems(List items) {
		this.items = items!=null?items:new ArrayList();
	}
	public String getFreeMReturnString() {
		return freeMReturnString;
	}
	public void setFreeMReturnString(String freeMReturnString) {
		this.freeMReturnString = freeMReturnString;
	}
	
	
}
