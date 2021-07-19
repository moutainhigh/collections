package com.gwssi.common.delivery.remote.ems.invoke;

import java.io.UnsupportedEncodingException;

public class XOR {

	/**
	 * 加密字符串
	 * @param pass 需要加密的字符串
	 * @param key 密钥
	 * @return
	 */
	public static byte[] encode(final byte[] pass, final String key){
		byte[] keybytes = null;
		try {
			keybytes = key.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] newpass = new byte[pass.length];
		int n1, n2;
		if (key.length() == 0)
			return pass;

		for (n1 = 0, n2 = 0; n1 < pass.length; ++n1) {
			newpass[n1] = (byte)(pass[n1] ^ keybytes[n2++]);
			if (n2 >= keybytes.length)
				n2 = 0;
		}
		return newpass;
	}

	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		byte[] org = "chinapost".getBytes("utf-8");
		
		byte[] xorbyte = XOR.encode(org, "rd2f_xor_key");
		System.out.println(xorbyte.length);
	}

}
