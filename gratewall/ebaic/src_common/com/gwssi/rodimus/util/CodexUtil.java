package com.gwssi.rodimus.util;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;


/**
 * 加解密工具类。
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class CodexUtil {
	
	public static void main(String[] args) throws Exception{
		
		String s = "中国  ABC %20 {\"姓名\":\"刘海龙\"}";
		
		// Base64测试
		System.out.println("Base64\r原文："+s+"。");
		String base64 = CodexUtil.enBase64(s);
		System.out.println("编码："+base64+"。");
		String deBase64 = CodexUtil.deBase64(base64);
		System.out.println("解码："+deBase64+"。");
		
		System.out.println("\rAES\r原文：" + s);
		String key = "BMH8i6VxMO0uTK1qWRHymQ==";
		System.out.println("密钥：" + key);
		String encryptData = enAES(s, key);
		System.out.println("加密：" + encryptData);
		String decryptData = deAES(encryptData, key);
		System.out.println("解密: " + decryptData);
		
		System.out.println("\rSHA-1\r原文：" + s);
		String sha1 = sha(s);
		System.out.println("摘要：" + sha1);
	}
	
	/**
	 * Base64编码。
	 * 
	 * @param plainText
	 * @return
	 */
	public static String enBase64(String s){  
        byte[] bytes = string2Bytes(s);
        bytes = Base64.encodeBase64(bytes);  
        String ret = bytes2String(bytes);  
        return ret;  
    }
	
	/**
	 * Base64解码。
	 * @param s
	 * @return
	 */
	public static String deBase64(String s){  
		byte[] bytes = string2Bytes(s);  
		bytes = Base64.decodeBase64(bytes);  
        String ret = bytes2String(bytes);
        return ret;  
    } 
	
	/**
	  * AES加密。
	  * 
	  * @param data 待加密数据
	  * @param key  密钥
	  * @return 加密后的数据
	  * */
	 public static String enAES(String data, String key) {
		try{
			  Key k = toKey(Base64.decodeBase64(key));                          //还原密钥
			  //使用PKCS7Padding填充方式(即调用BouncyCastle组件实现)
			  Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");       //实例化Cipher对象，它用于完成实际的加密操作
			  cipher.init(Cipher.ENCRYPT_MODE, k);                              //初始化Cipher对象，设置为加密模式
			  byte[] result = cipher.doFinal(string2Bytes(data));				//执行加密操作。
			  String base64Result = Base64.encodeBase64String(result);
			  return base64Result; //加密后的结果通常都会用Base64编码进行传输
		 }catch(Exception e){
			  return null;
		 }
	 }
	 
	 /**
	  * AES解密。
	  * 
	  * @param data 待解密数据
	  * @param key  密钥
	  * @return 解密后的数据
	  * */
	 public static String deAES(String data, String key){
		 try{
			  Key k = toKey(Base64.decodeBase64(key));
			  Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			  cipher.init(Cipher.DECRYPT_MODE, k);                          //初始化Cipher对象，设置为解密模式
			  byte[] result = cipher.doFinal(Base64.decodeBase64(data));	 //执行解密操作
			  String resultString = bytes2String(result);
			  return resultString;
		 }catch(Exception e){
			  return null;
		 }
	 }
	 /**
	  * 
	  * @param s
	  * @return
	  */
	 public static String sha(String s){
		if (s == null) {
			return null;
		}
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			byte[] bytes = string2Bytes(s);
			sha.update(bytes);
			byte[] result = sha.digest();
			String base64Result = Base64.encodeBase64String(result);
			if(base64Result!=null){
				base64Result = base64Result.trim();
			}
			return base64Result;
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	 }
	 
	 
	 
	 
	 
	 
	
	
	
	
	
	
	
	
	
	/**
	  * 生成密钥
	  */
	public static String initkey() throws Exception {
		KeyGenerator kg = KeyGenerator.getInstance("AES"); // 实例化密钥生成器
		kg.init(128); // 初始化密钥生成器:AES要求密钥长度为128,192,256位
		SecretKey secretKey = kg.generateKey(); // 生成密钥
		return Base64.encodeBase64String(secretKey.getEncoded()); // 获取二进制密钥编码形式
	}
	
	/**
	  * 转换密钥
	  */
	 private static Key toKey(byte[] key) throws Exception{
		 return new SecretKeySpec(key, "AES");
	 }
	 
	/** 
	 * 字符串转为byte数组。
	 * 
	 * @param s
	 * @return
	 */
	private static byte[] string2Bytes(String s){
		if(s==null){
			return null;
		}
		 try {
			byte[] bytes = s.getBytes("utf-8");
			return bytes;
		} catch (UnsupportedEncodingException e) {
			return null;
		}  
	}
	
	/** 
	 * 字符串转为byte数组。
	 * 
	 * @param b
	 * @return
	 */
	private static String bytes2String(byte[] b){
		if(b==null){
			return null;
		}
		try {
			String s = new String(b,"utf-8");  
			return s;
		} catch (UnsupportedEncodingException e) {
			return null;
		}  
	}
}
