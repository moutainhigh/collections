package com.gwssi.rodimus.exception;

/**
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class DaoException extends RuntimeException{
	public DaoException(String msg) {
		super(msg);
	}

	public DaoException(String message, Exception e) {
		super(message,e);
	}

	private static final long serialVersionUID = -2734511880240603739L;

}
