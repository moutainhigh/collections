package com.gwssi.dw.dq.action;

public class Pager
{
	public final static int QUERY_TYPE_ALL = 0; 
	public final static int QUERY_TYPE_LIST = 1; 
	public final static int QUERY_TYPE_COUNT = 2;
	
	private final static int DEFALUT_PAGESIZE = 10;
	
	private final static int DEFALUT_PAGEINDEX = 1;
	
	private int pageIndex;//页定位
	
	private int pageSize;//每页记录条数
	
	private int queryType;
	
	public  Pager(int pageIndex,int pageSize,int queryType){
		this.pageIndex = pageIndex;
		this.pageSize = pageIndex;
		this.queryType = queryType;
	}
	
	public Pager(String pageIndex,String pageSize,String queryType){
		try {
			this.pageSize = new Integer(pageSize).intValue();
		} catch (NumberFormatException e) {
			this.pageSize = DEFALUT_PAGESIZE;
		}
		
		try {
			this.pageIndex = new Integer(pageIndex).intValue();
		} catch (NumberFormatException e) {
			this.pageIndex = DEFALUT_PAGEINDEX;
		}
		
		try {
			this.queryType = new Integer(queryType).intValue();
		} catch (NumberFormatException e) {
			this.queryType = QUERY_TYPE_ALL;
		}
	}

	/**
	 * @return the pageIndex
	 */
	public int getPageIndex()
	{
		return pageIndex;
	}

	/**
	 * @param pageIndex the pageIndex to set
	 */
	public void setPageIndex(int pageIndex)
	{
		this.pageIndex = pageIndex;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize()
	{
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}
	
	public int getStartIndex() {
		pageIndex = (pageIndex==0)?1:pageIndex;
		return (this.pageIndex-1)*this.pageSize; //
	}
	public int getEndIndex() {
		return getStartIndex()+this.pageSize;
	}

	/**
	 * @return the queryType
	 */
	public int getQueryType()
	{
		return queryType;
	}

	/**
	 * @param queryType the queryType to set
	 */
	public void setQueryType(int queryType)
	{
		this.queryType = queryType;
	}
	
}
