package com.gwssi.common.util;

public class XmlUrlReaderException extends Throwable
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -540677185710083843L;
	private String message;
	
	
	public XmlUrlReaderException(String message){
		this.message = message;
	}
	
	public XmlUrlReaderException(String message,Throwable e){
		this.message = message;
		if(e!=null){
			this.message+=" ";
			this.message+=e.getMessage();
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	public String getMessage()
	{
		return this.message;
	}

	
}
