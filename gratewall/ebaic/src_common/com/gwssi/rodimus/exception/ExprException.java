package com.gwssi.rodimus.exception;

/**
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class ExprException extends RuntimeException{
	public ExprException(String msg) {
		super(msg);
	}

	public ExprException(String message, Exception e) {
		super(message,e);
	}

	private static final long serialVersionUID = -2734511880240603739L;

}
