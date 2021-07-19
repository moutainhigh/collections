package com.gwssi.sms.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.gwssi.cxf.validatecode.manager.MyX509TrustManager;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;


@Controller
public class SMSController {

	
	@RequestMapping("/phone")
	public void senderSMS(OptimusRequest req,OptimusResponse res) throws Exception{
		HttpServletRequest request = req.getHttpRequest();
		
		String strJson = null;
		String key = "27C31DC6A30042BCA2020B41A8D38326";
		String type="0";
		String phone ="18269215167 ";
		int a = (int)(Math.random()*(9999-1000+1))+1000;//产生1000-9999的随机数
		String mess ;
		try {
			mess = URLEncoder.encode("您本次反馈请求的验证码:【"+a+"】,有效期为10分钟，如不是本人操作请忽略。", "utf-8");
			String url = "https://api.szaic.gov.cn/sms/"+key+"/"+type+"/"+phone+"/"+mess;
			strJson = MyX509TrustManager.getClient(url);
			request.setAttribute("codes", a);//存入session
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject json = JSONObject.parseObject(strJson);
		System.out.println(json);
	}
	
	
	public static void main(String[] args) {
		String strJson = null;
		String key = "27C31DC6A30042BCA2020B41A8D38326";
		String type="0";
		String phone ="18269215167 ";
		int a = (int)(Math.random()*(9999-1000+1))+1000;//产生1000-9999的随机数
		String mess ;
		try {
			mess = URLEncoder.encode("您本次反馈请求的验证码:【"+a+"】,有效期为10分钟，如不是本人操作请忽略。", "utf-8");
			String url = "https://api.szaic.gov.cn/sms/"+key+"/"+type+"/"+phone+"/"+mess;
			strJson = MyX509TrustManager.getClient(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject json = JSONObject.parseObject(strJson);
		System.out.println(json);
}

}
