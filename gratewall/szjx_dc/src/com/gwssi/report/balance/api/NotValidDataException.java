package com.gwssi.report.balance.api;


/**
 * 报表无效填充数据时异常类，暂时没起太多作用
 * @author wuyincheng ,Dec 2, 2016
 */
public class NotValidDataException extends RuntimeException{

	private static final long serialVersionUID = -8730905021891844518L;
	
	public NotValidDataException() {
		super();
	}
	
	public NotValidDataException(String msg) {
		super(msg);
	}
	
}
