package com.gwssi.rodimus.exception;

/**
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class ValidateException extends RuntimeException{
	public ValidateException(String msg) {
		super(msg);
	}

	public ValidateException(String message, Exception e) {
		super(message,e);
	}

	private static final long serialVersionUID = -2734511880240603739L;

}
