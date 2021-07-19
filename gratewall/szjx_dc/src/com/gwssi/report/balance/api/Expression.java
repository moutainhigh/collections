package com.gwssi.report.balance.api;
/**
 * @author wuyincheng ,Nov 2, 2016
 * 解析式接口
 */
public interface Expression {
	
	public static final Boolean TRUE = new Boolean(true);    //校验表达式结果为真
	
	public static final Boolean FALSE = new Boolean(false); //校验表达式结果为假

	public Object execute(Object expression);
	
}
