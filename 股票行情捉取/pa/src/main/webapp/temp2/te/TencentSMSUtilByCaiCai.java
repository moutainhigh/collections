package com.ly.sms;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.ly.task.forbuy.ForbuyTask;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class TencentSMSUtilByCaiCai {
//	private static Logger logger = Logger.getLogger(TencentSMSUtil2.class);
	
	private static final Logger logger = Logger.getLogger("sts");
	
	
	public void sendBuy(String phoneNum,String [] params) {
		// 短信应用SDK AppID
		int appid = 1400169476; // 1400开头

		// 短信应用SDK AppKey
		String appkey = "15148b8d07d72138bc7420ecfa819b4f";

		// 需要发送短信的手机号码
		//String[] phoneNumbers = {"18269215167","15677590371"};

		// 短信模板ID，需要在短信应用中申请
		int templateId = 248069; // NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请
		//templateId7839对应的内容是"您的验证码是: {1}"
		// 签名
		String smsSign = "菜菜科技"; // NOTE: 这里的签名"腾讯云"只是一个示例，真实的签名需要在短信控制台中申请，另外签名参数使用的是`签名内容`，而不是`签名ID`
		try {
			//String[] params = { "603991","6.23" };// 数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
			SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
			SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNum, templateId, params, smsSign, "", ""); // 签名参数未提供或者为空时，会使用默认签名发送短信
			logger.debug("发送成功，系统返回信息==》 " +result);
		} catch (HTTPException e) {
			// HTTP响应码错误
			e.printStackTrace();
		} catch (JSONException e) {
			// json解析错误
			e.printStackTrace();
		} catch (IOException e) {
			// 网络IO错误
			e.printStackTrace();
		}
	}
	
	
	
	
	public void sendSell(String phoneNum,String [] params) {
		// 短信应用SDK AppID
		int appid = 1400169476; // 1400开头

		// 短信应用SDK AppKey
		String appkey = "15148b8d07d72138bc7420ecfa819b4f";

		// 需要发送短信的手机号码
		//String[] phoneNumbers = {"18269215167","15677590371"};

		// 短信模板ID，需要在短信应用中申请
		int templateId = 253471; // NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请
		//templateId7839对应的内容是"您的验证码是: {1}"
		// 签名
		String smsSign = "菜菜科技"; // NOTE: 这里的签名"腾讯云"只是一个示例，真实的签名需要在短信控制台中申请，另外签名参数使用的是`签名内容`，而不是`签名ID`
		try {
			//String[] params = { "603991","6.23" };// 数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
			SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
			SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNum, templateId, params, smsSign, "", ""); // 签名参数未提供或者为空时，会使用默认签名发送短信
			logger.debug("发送成功，系统返回信息==》 " +result);
		} catch (HTTPException e) {
			// HTTP响应码错误
			e.printStackTrace();
		} catch (JSONException e) {
			// json解析错误
			e.printStackTrace();
		} catch (IOException e) {
			// 网络IO错误
			e.printStackTrace();
		}
	}

}