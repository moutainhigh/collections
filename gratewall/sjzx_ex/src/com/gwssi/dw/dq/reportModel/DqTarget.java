/**
 * 
 */
package com.gwssi.dw.dq.reportModel;

/**
 * <p>Title: </p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (c) Sep 25, 2008</p>
 * <p>Company: ³¤³ÇÈí¼þ</p>
 * @author zhouyi
 * @version 1.0
 */
public class DqTarget extends DqMetadata
{
	private String query;
	private String from;

	public DqTarget(String id,String text){
		this.id = id;
		this.text = text;
	}
	
	public DqTarget(String id,String text,String query,String from){
		this(id,text);
		this.query = query;
		this.from = from;
	}

	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @param query the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}
}
