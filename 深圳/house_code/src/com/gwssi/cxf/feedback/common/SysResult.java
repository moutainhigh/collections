package com.gwssi.cxf.feedback.common;

public class SysResult {
	
	/**
	 * 状态代码0，获取数据成功
	 * @param arg
	 * @return
	 */
	public static String codeStatus_0(String arg) {
		return new String("{\"code\":\"0\",\"msg\":\"获取数据成功\",\"data\":"+arg+"}");
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

}
