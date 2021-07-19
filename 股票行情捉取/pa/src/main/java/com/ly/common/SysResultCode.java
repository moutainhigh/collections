package com.ly.common;

import java.util.HashMap;
import java.util.Map;

//https://blog.csdn.net/u013979547/article/details/53449788
//https://blog.csdn.net/panchang199266/article/details/80385353
//https://blog.csdn.net/babylove_BaLe/article/details/76258904
//https://www.cnblogs.com/pxuan/p/8058299.html
public class SysResultCode {

	static Map<String, String> map = new HashMap<String, String>();

	
	public static Map<String, String> codeStatus00(String code,String content) {
		map.put(code, content);
		return map;
	}
	
	// 未登录
	public static Map<String, String> codeStatus0() {
		map.put("code", "0000");
		map.put("msg", "未登录");
		return map;
	}

	// 登录成功
	public static Map<String, String> codeStatus1() {
		map.put("code", "0001");
		map.put("msg", "登录成功");
		return map;
	}

	// 输入错误次数过多，请过一段时间再试
	public static Map<String, String> codeStatus2() {
		map.put("code", "0002");
		map.put("msg", "输入错误次数过多，请过一段时间再试");
		return map;
	}

	// 用户名或密码错误
	public static Map<String, String> codeStatus3() {
		map.put("code", "0003");
		map.put("msg", "用户名或密码错误");
		return map;
	}

	// 用户输入信息不能为空
	public static Map<String, String> codeStatu4() {
		map.put("code", "0004");
		map.put("msg", "用户输入信息不能为空");
		return map;
	}

	// 退出成功
	public static Map<String, String> codeStatu5() {
		map.put("code", "0005");
		map.put("msg", "退出成功");
		return map;
	}

	// 插入或更新成功
	public static Map<String, String> codeStatu6() {
		map.put("code", "0006");
		map.put("msg", " 插入或更新成功");
		return map;
	}

	// 插入或更新失败
	public static Map<String, String> codeStatu7() {
		map.put("code", "0007");
		map.put("msg", "插入或更新失败");
		return map;
	}

	// 用户不存在
	public static Map<String, String> codeStatus8() {
		map.put("code", "0008");
		map.put("msg", "用户不存在");
		return map;
	}

	public static Map<String, String> getMap() {
		return map;
	}

	public static void setMap(Map<String, String> map) {
		SysResultCode.map = map;
	}
	
	
	
}
