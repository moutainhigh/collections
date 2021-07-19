package com.ly.placeholder;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DESUtils {
	private static Key key;
	private static String KEY_STR = "ynrtml";
	static {
		try {
			KeyGenerator generator = KeyGenerator.getInstance("DES");
			generator.init(new SecureRandom(KEY_STR.getBytes()));
			key = generator.generateKey();
			generator = null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 对str进行DES加密
	 * 
	 * @param str
	 * @return
	 */
	public static String getEncryptString(String str) {
		BASE64Encoder base64en = new BASE64Encoder();
		try {
			byte[] strBytes = str.getBytes("UTF8");
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encryptStrBytes = cipher.doFinal(strBytes);
			return base64en.encode(encryptStrBytes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 对str进行DES解密
	 * 
	 * @param str
	 * @return
	 */
	public static String getDecryptString(String str) {
		BASE64Decoder base64De = new BASE64Decoder();
		try {
			byte[] strBytes = base64De.decodeBuffer(str);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] decryptStrBytes = cipher.doFinal(strBytes);
			return new String(decryptStrBytes, "UTF8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public static void main(String[] args) throws Exception {
		/*if (args == null || args.length < 1) {
			System.out.println("请输入要加密的字符，用空格分隔.");
		} else {
			for (String arg : args) {
				System.out.println(arg + ":" + getEncryptString(arg));
			}    
		}*/
		//System.out.println(getDecryptString("gJQ9O+q34qk="));
		System.out.println(getEncryptString("N_C2FE34B7E178CAA5DFE2E300D929BDEF80732D8E05AF75E4525479B127FFE33FCBE3A2DD21541F845285F7EFFEDC7688047651AE4E1BD27B341F59F094C249817133B7A343DB5AFA"));
		System.out.println();
		//System.out.println(getDecryptString("Wu+cCC1EFh6bXtr2v0rfQXX6hOzuiDV5wHlfRM74GiIbytGi/bEdwCgHud9QT0dHa76DT2xKcqBB
	}
}
