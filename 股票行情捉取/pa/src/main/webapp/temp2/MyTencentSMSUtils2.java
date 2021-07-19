package com.ly.sms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsMultiSenderResult;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.ly.dao.SMSSenderDao;
import com.ly.pojo.SMSSender;

@Component
public class MyTencentSMSUtils2 {

	@Autowired
	private SMSSenderDao smssenderdao;

	private static final Logger logger = Logger.getLogger("sts");

	public SMSSender getBuyRemaind(List<String> phoneNumbers, List<String> params) {
		SMSSender sms = null;
		List<SMSSender> list = smssenderdao.getList();
		if (list != null && list.size() > 0) {
			for (SMSSender smsSender : list) {
				String ctime = smsSender.getTimes();
				Integer time = Integer.valueOf(ctime) - phoneNumbers.size();
				smsSender.setTimes(time+"");
				smssenderdao.save(smsSender);
				return smsSender;
			}
		}
		return sms;
	}

	public void sendBuy(ArrayList<String> phoneNumbers, ArrayList<String> params) {
		SMSSender sms = getBuyRemaind(phoneNumbers,params);
		// 短信应用SDK AppID
		int appid = 1400050751; // 1400开头

		// 短信应用SDK AppKey
		String appkey = "fbb3ffb5c46c08b53f87ccbb7647a0ee";

		// 需要发送短信的手机号码
		// String[] phoneNumbers = {"18269215167","15677590371"};

		// 短信模板ID，需要在短信应用中申请
		int templateId = 248549; // NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请
		// templateId7839对应的内容是"您的验证码是: {1}"
		// 签名
		String smsSign = "技术撸公众号"; // NOTE://
									// 这里的签名"腾讯云"只是一个示例，真实的签名需要在短信控制台中申请，另外签名参数使用的是`签名内容`，而不是`签名ID`

		try {
			// String[] params = { "603991","6.23" };//
			// 数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
			SmsMultiSender msender = new SmsMultiSender(appid, appkey);
			SmsMultiSenderResult result = msender.sendWithParam("86", phoneNumbers, templateId, params, smsSign, "", "");// 签名参数未提供或者为空时，会使用默认签名发送短信
			logger.debug("发送成功，系统返回信息==》 " + result);
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

	public void sendSell(ArrayList<String> phoneNumbers, ArrayList<String> params) {
		// 短信应用SDK AppID
		int appid = 1400050751; // 1400开头

		// 短信应用SDK AppKey
		String appkey = "fbb3ffb5c46c08b53f87ccbb7647a0ee";

		// 需要发送短信的手机号码
		// String[] phoneNumbers = {"18269215167","15677590371"};

		// 短信模板ID，需要在短信应用中申请
		int templateId = 247496; // NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请
		// templateId7839对应的内容是"您的验证码是: {1}"
		// 签名
		String smsSign = "技术撸公众号"; // NOTE:
									// 这里的签名"腾讯云"只是一个示例，真实的签名需要在短信控制台中申请，另外签名参数使用的是`签名内容`，而不是`签名ID`
		try {
			// String[] params = { "603991","6.23" };//
			// 数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
			SmsMultiSender msender = new SmsMultiSender(appid, appkey);
			SmsMultiSenderResult result = msender.sendWithParam("86", phoneNumbers, templateId, params, smsSign, "", ""); // 签名参数未提供或者为空时，会使用默认签名发送短信
			logger.debug("发送成功，系统返回信息==》 " + result);
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