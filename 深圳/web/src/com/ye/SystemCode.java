package com.ye;

import java.util.HashMap;
import java.util.Map;

public class SystemCode {

	public  static Map CODE_0000000() {
		Map map = new HashMap();
		map.put("code", "0000000");
		map.put("msg", "请求成功");
		return map;
	}
	
	public  static Map CODE_0000001() {
		Map map = new HashMap();
		map.put("code", "0000001");
		map.put("msg", "请求失败，系统繁忙");
		return map;
	}
	
	public  static Map CODE_0000002() {
		Map map = new HashMap();
		map.put("code", "0000002");
		map.put("msg", "请求失败，参数不能为空");
		return map;
	}
}
