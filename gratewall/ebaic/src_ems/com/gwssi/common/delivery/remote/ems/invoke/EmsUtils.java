package com.gwssi.common.delivery.remote.ems.invoke;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import com.gwssi.optimus.core.common.ConfigManager;

/**
 * EMS 工具类。
 * @author liuhailong
 */
public class EmsUtils {
	
	protected final static Logger logger = Logger.getLogger(EmsUtils.class);
	
	public static final String XOR_KEY; 
	public static final String SIGN_FIX; 
	
	static{
		Properties props = ConfigManager.getProperties("ems");
		XOR_KEY = props.getProperty("ems.xorkey");
		SIGN_FIX = props.getProperty("ems.signfix"); 
	}
	/**
	 * 压缩。
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static byte[] compress(String data) throws IOException {
		byte[] normalBytes = data.toString().getBytes("UTF-8");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		gzip.write(normalBytes);
		gzip.finish();
		gzip.close();
		out.close();
		byte[] ret = out.toByteArray();
		
		if(logger.isDebugEnabled()){
			//logger.debug(String.format("“%s”压缩为“%s”", data, toString(ret)));
		}
		
		return ret;
	}

	/**
	 * 解压缩。 
	 * @param compressBytes
	 * @return
	 * @throws IOException
	 */
	public static String uncompress(byte[] compressBytes) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(compressBytes);
		GZIPInputStream gunzip = new GZIPInputStream(in);
		byte[] buffer = new byte[256];
		int n;
		while ((n = gunzip.read(buffer)) >= 0) {
			out.write(buffer, 0, n);
		}
		gunzip.close();
		in.close();
		out.close();
		
		String ret = out.toString();
		
		if(logger.isDebugEnabled()){
			//logger.debug(String.format("“%s”解压缩为“%s”", toString(compressBytes), ret));
		}
		
		return ret;
	}
	
	/**
	 * 加密/解密。
	 * @param compressBytes
	 * @return
	 */
	public static byte[] encrypt(byte[] data){
		byte[] ret = XOR.encode(data, XOR_KEY);// 加密处理
		
		if(logger.isDebugEnabled()){
			//logger.debug(String.format("“%s”加密/解密“%s”",toString(data),	toString(ret)));
		}
		
		return ret;
	}
	
	/**
	 * 签名。
	 * 使用摘要算法获得签名，用于校验是否被非法修改。
	 * @param encodeRequestBytes
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String sign(byte[] data) throws UnsupportedEncodingException{
		// 将加密后的报文与固定签名串进行组合计算签名
		byte[] signBytes = ArrayUtils.addAll(data, SIGN_FIX.getBytes("utf-8"));
		String sign = Md5.md5(signBytes);
		
		if(logger.isDebugEnabled()){
			//logger.debug(String.format("“%s”签名为“%s”", toString(data), sign));
		}
		
		return sign;
	}
	
	
	/**
	 * byte[] 转为  String。
	 * @param data
	 * @return
	 */
	public static String toString(byte[] data){
		String retString = "";
		try {
			if(data!=null){
				retString = new String(data,"UTF-8");
			}
		} catch (UnsupportedEncodingException e) {}
		return retString;
	}
	
}
