package com.gwssi.common.delivery.remote.ems.invoke;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {
	/**
	 * MD5算法,来源于JForum
	 * 
	 * @ption
	 */
	public static String md5(byte[] signBytes) {

		StringBuilder hexString = new StringBuilder();

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(signBytes);
			byte[] hash = md.digest();

			for (byte element : hash) {
				if ((0xff & element) < 0x10) {
					hexString.append('0').append(Integer.toHexString((0xFF & element)));
				} else {
					hexString.append(Integer.toHexString(0xFF & element));
				}
			}
		} catch (NoSuchAlgorithmException e) {
			return null;
		}

		return hexString.toString();
	}
}
