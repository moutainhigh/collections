//package com.gwssi.rodimus.protocolstack.core;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.commons.lang.StringUtils;
//
//import com.gwssi.rodimus.exception.RodimusException;
//import com.gwssi.rodimus.util.UUIDUtil;
//
///**
// * @author liuhailong(liuhailong2008#foxmail.com)
// */
//public class Request {
//	
//	/**
//	 * 唯一编号。
//	 */
//	public final String requestId = UUIDUtil.getUUID();
//	/**
//	 * 请求头。
//	 */
//	private Map<String,String> header = null;
//	/**
//	 * 请求体。
//	 */
//	private byte[] body = null;
//	/**
//	 * 请求体。
//	 */
//	private String bodyStr = "";
//	/**
//	 * 上下文。
//	 */
//	private Map<String,Object> context = null;
//	
//	
//	/**
//	 * 构造函数。
//	 * 
//	 * @param header
//	 * @param body
//	 */
//	public Request(Map<String, String> header, byte[] body){
//		if((header==null || header.isEmpty()) && (body==null || body.length==0)){
//			throw new RodimusException("请求为空。");
//		}
//		this.header = header ;
//		this.body = body ;
//		this.context = new HashMap<String,Object>();
//		this.context.put("DATA", this.body);
//	}
//
//	/**
//	 * 构造函数。
//	 * 
//	 * @param header
//	 * @param body
//	 */
//	public Request(Map<String, String> header, String body){
//		if((header==null || header.isEmpty()) && StringUtils.isBlank(body)){
//			throw new RodimusException("请求为空。");
//		}
//		this.header = header ;
//		this.bodyStr = body ;
//		this.context = new HashMap<String,Object>();
//		this.context.put("DATA", this.bodyStr);
//	}
//	
//	public Map<String, String> getHeader() {
//		return header;
//	}
//	public void setHeader(Map<String, String> header) {
//		this.header = header;
//	}
//	public byte[] getBody() {
//		return body;
//	}
//	public void setBody(byte[] body) {
//		this.body = body;
//	}
//	public Map<String, Object> getContext() {
//		return context;
//	}
//
//	public String getBodyStr() {
//		return bodyStr;
//	}
//
//	public void setBodyStr(String bodyStr) {
//		this.bodyStr = bodyStr;
//	}
//
//}
