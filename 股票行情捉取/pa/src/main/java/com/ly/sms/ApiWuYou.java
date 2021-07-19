package com.ly.sms;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ly.http.CommHttpUtil;

@Component
public class ApiWuYou {

	@Autowired
	private CommHttpUtil httpUtil;
	//https://www.kancloud.cn/wangjikeji/wangjiapi/435493
	
	//https://www.kancloud.cn/wangjikeji/wangjiapi/428133
	public void send(String phone,String params){
		String url ="http://www.api51.cn/api/smsApi/sendcode?token=6716320f9befe47f6963d5db0bbe6230&mobile="+phone+"&sign=技术撸公众号&tpl_id=246437&params="+params;
		
		Map<String, String> headers = new HashMap<String, String>();
		
		headers.put("accept", "*/*");
		headers.put("connection", "Keep-Alive");
		headers.put("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		
		httpUtil.httpGet(url, headers);
	}
}
