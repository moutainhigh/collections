package com.gwssi.rodimus.exception;
/**
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class RodimusException extends RuntimeException{
	public RodimusException(String msg, Throwable e) {
		super(msg,e);
	}

	public RodimusException(String msg) {
		super(msg);
	}

	private static final long serialVersionUID = -2734511880240603739L;

}
