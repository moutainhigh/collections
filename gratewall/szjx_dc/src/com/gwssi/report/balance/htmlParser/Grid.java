package com.gwssi.report.balance.htmlParser;



/**
 * @author wuyincheng ,Oct 31, 2016
 * 解析HTML报表成程序模型所用到的中间实体
 */
public class Grid{
	
	String content = ""; 
	boolean isFill = false;
	public Grid() {	}
	public Grid(boolean isFill) { this.isFill = isFill;}
	@Override
	public String toString() {return content;}
}