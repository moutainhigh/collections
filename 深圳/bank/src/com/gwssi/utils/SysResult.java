package com.gwssi.utils;

public class SysResult {
	
	
	public static String codeStatus_Key(String arg) {
		return new String("{\"code\":\"-1\",\"msg\":\"获取数据失败\",\"data\":"+arg+"}");
	}
	
	
	/**
	 * 状态代码0，获取数据成功
	 * @param arg
	 * @return
	 */
	public static String codeStatus_0(String arg,int totals) {
		return new String("{\"code\":\"0\",\"totals\":"+totals+",\"msg\":\"获取数据成功\",\"data\":"+arg+"}");
	}
	
	
	/**
	 * 状态代码1，暂无数据
	 * @return
	 */
	public static String codeStatus_1() {
		return new String("{\"code\":\"1\",\"msg\":\"暂无数据\",\"data\":\"\"}");
	}
	
	/**
	 * 状态代码2，无效请求参数
	 * @return
	 */
	public static String codeStatus_2() {
		return new String("{\"code\":\"2\",\"msg\":\"无效请求参数\",\"data\":\"\"}");
	}
	
	/**
	 * 状态代码9，其他
	 * @return
	 */
	public static String codeStatus_9() {
		return new String("{\"code\":\"9\",\"msg\":\"其他\",\"data\":\"\"}");
	}

	
	public static String codeStatus_91(String msg) {
		return new String("{\"code\":\"9\",\"msg\":\""+msg+"\",\"data\":\"\"}");
	}
	
	
	public static String codeStatus_92() {
		return new String("{\"code\":\"404\",\"msg\":\"查询时间标识不为1时，开始时间和结束时间必须传入\",\"data\":\"\"}");
	}
}
