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
public class DqGroupItem extends DqMetadata
{
	private String cond;
	
	public DqGroupItem(String id,String text){
		this.id = id;
		this.text = text;
	}
	
	public DqGroupItem(String id,String text,String cond){
		this(id,text);
		this.cond = cond;
	}

	/**
	 * @return the cond
	 */
	public String getCond() {
		return cond;
	}

	/**
	 * @param cond the cond to set
	 */
	public void setCond(String cond) {
		this.cond = cond;
	}
}
