package com.ly.sms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.ly.dao.SMSSenderDao;
import com.ly.pojo.SMSSender;

@Component
public class MyTencentSMSUtils {

	@Autowired
	private SMSSenderDao smssenderdao;

	private static final Logger logger = Logger.getLogger("sts");

	public SMSSender getBuyRemaind(List<String> phoneNumbers) {
		SMSSender smsSender = null;
		List<SMSSender> list = smssenderdao.getList();
		if(list!=null){
			smsSender = list.get(0);
			String ctime = smsSender.getTimes();
			Integer time = Integer.valueOf(ctime) - phoneNumbers.size();
			smsSender.setTimes(time + "");
			smssenderdao.save(smsSender);
			return smsSender;
		}else{
			return null;
		}
	}

	public void sendBuy(ArrayList<String> phoneNumbers, String[] params) {
		SMSSender sms = getBuyRemaind(phoneNumbers);
		if (sms!=null) {
			String appidStr = sms.getAppId(); // 1400开头
			String appkey = sms.getAppKey();
			String templateIdStr = sms.getTemplateIdForBuy();
			String smsSign = sms.getSign();
			int appid = Integer.valueOf(appidStr);
			int templateId = Integer.valueOf(templateIdStr);
			SmsSingleSenderResult result = null;
			logger.info("使用的签名  【" + smsSign + "】");
			try {
				SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
				for (int i = 0; i < phoneNumbers.size(); i++) {
					result = ssender.sendWithParam("86", phoneNumbers.get(i), templateId, params, smsSign, "", ""); // 签名参数未提供或者为空时，会使用默认签名发送短信
					logger.info("系统返回===>" + result + "  , 发送的号码为 " + phoneNumbers.get(i));
					//System.out.println(result);
				}

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

	public void sendSell(ArrayList<String> phoneNumbers, String[] params) {
		SMSSender sms = getBuyRemaind(phoneNumbers);
		if (sms!=null) {
			String appidStr = sms.getAppId(); // 1400开头
			String appkey = sms.getAppKey();
			String templateIdStr = sms.getTemplateIdForSell();
			String smsSign = sms.getSign();
			int appid = Integer.valueOf(appidStr);
			int templateId = Integer.valueOf(templateIdStr);
			SmsSingleSenderResult result = null;
			logger.info("使用的签名  【" + smsSign + "】");
			try {
				SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
				for (int i = 0; i < phoneNumbers.size(); i++) {
					result = ssender.sendWithParam("86", phoneNumbers.get(i), templateId, params, smsSign, "", ""); // 签名参数未提供或者为空时，会使用默认签名发送短信
					logger.info("系统返回===>" + result);
					//System.out.println(result);
				}

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

}