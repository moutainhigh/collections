package com.gwssi.report.balance.api;


/**
 * 平衡表达式拆分出来每个"项"所对应实体,包含运算符与数值
 * 在实际解析中，中括号所包含的数据为一个DataItem实体
 
 * @author wuyincheng ,Nov 2, 2016
 */
public class DataItem {
	
	public String item;
	public boolean isConstant;//是否为常量,常量不需要填充数据
	public boolean isOperChar;//是否为运算符
	
	public DataItem(String item) {
		this.item = item;
	}
	
	public DataItem(String item, boolean isConstant) {
		this.item = item;
		this.isConstant = isConstant;
	}
	
	@Override
	public String toString() {
		return item + (isConstant ? "(c)" : "");
	}
	
	public static DataItem operChar(String operChar) {
		DataItem o = new DataItem(operChar);
		o.isOperChar = true;
		return o;
	}

}
