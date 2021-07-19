package com.gwssi.rodimus.exception;
/**
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class RuleException extends RuntimeException{
	public RuleException(String msg, Exception e) {
		super(msg,e);
	}

	public RuleException(String msg) {
		super(msg);
	}

	private static final long serialVersionUID = -2734511880240603739L;

}
