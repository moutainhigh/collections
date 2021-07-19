/**
 * 
 */
package com.gwssi.dw.dq.action;

/**
 * <p>Title: </p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (c) Sep 22, 2008</p>
 * <p>Company: ³¤³ÇÈí¼þ</p>
 * @author zhouyi
 * @version 1.0
 */
public class ActionException extends Throwable
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 959941238684841072L;
	
	private String message = "";
	
	public ActionException(String message){
		this.message = message;
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	public String getMessage()
	{
		return this.message;
	}
	
	

}
