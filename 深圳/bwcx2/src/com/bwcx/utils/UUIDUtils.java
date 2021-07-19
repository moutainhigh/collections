package com.bwcx.utils;

import java.util.UUID;

public class UUIDUtils {

	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		// 去掉“-”符号
		return uuid.replaceAll("-", "");

	}
}
