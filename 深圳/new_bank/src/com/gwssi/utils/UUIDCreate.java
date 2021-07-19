package com.gwssi.utils;

import java.util.UUID;


/*
 * 用于UUID生成。将此UUID作为银行的key,直接用 main方法生成一个UUID之后拷贝到t_ent_bank_userinfo中的key字段即可
 * 
 * */
public class UUIDCreate {
	public static String getUUID32() {
		String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
		return uuid;
		//   return UUID.randomUUID().toString().replace("-", "").toLowerCase();
	}
	
	
	public static void main(String[] args) {
		String str = UUIDCreate.getUUID32();
		System.out.println(str);
	}
}
