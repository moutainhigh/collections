package com.gwssi.rodimus.exception;
/**
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class FileException extends RuntimeException{
	public FileException(String msg, Exception e) {
		super(msg,e);
	}

	public FileException(String msg) {
		super(msg);
	}

	private static final long serialVersionUID = -2734511880240603739L;

}
