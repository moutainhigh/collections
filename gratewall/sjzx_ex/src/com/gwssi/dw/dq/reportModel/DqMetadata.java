/**
 * 
 */
package com.gwssi.dw.dq.reportModel;

/**
 * <p>Title: </p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (c) Sep 25, 2008</p>
 * <p>Company: �������</p>
 * @author zhouyi
 * @version 1.0
 */
public abstract class DqMetadata
{
	protected String id;
	
	protected String text;

	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return the text
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text)
	{
		this.text = text;
	}

	
}
