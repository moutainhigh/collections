//package com.gwssi.rodimus.sms.service;
//
//import java.util.Map;
//
//import com.gwssi.optimus.core.exception.OptimusException;
//
///**
// * 实际执行短信发送的接口。
// * 
// * @author liuhailong
// */
//public interface SmsSendService {
//
//	/**
//	 * 发送单条短信。
//	 * 
//	 * @param mobile
//	 * @param content
//	 */
//	void send(String mobile, String content)  throws OptimusException;
//	
//	/**
//	 * 发送多条短信。
//	 * 
//	 * Map的Key为移动电话码，Value为短信内容。
//	 * @param smsList
//	 */
//	void send(Map<String, String> smsList)  throws OptimusException;
//}
