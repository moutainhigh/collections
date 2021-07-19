package com.ye.monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * Hello world!
 * 
 */
// http://mercymessi.iteye.com/blog/2250161
public class CopyOfApp {
	static String requestUrl = "http://api.feige.ee/SmsService/Send";

	public static void main(String[] args) {
		System.out.println("Hello World!");
		try {
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			formparams.add(new BasicNameValuePair("Account", "账号"));
			formparams.add(new BasicNameValuePair("Pwd", "接口秘钥"));
			formparams.add(new BasicNameValuePair("Content", "您的验证码是1234"));
			formparams.add(new BasicNameValuePair("Mobile", "138****1234"));
			formparams.add(new BasicNameValuePair("SignId", "签名id"));
			Post(formparams);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void Post(List<NameValuePair> formparams) throws Exception {
		CloseableHttpClient httpclient = HttpClients.custom().build(); // 为了给头部添加信息

		HttpPost requestPost = new HttpPost(requestUrl);
		requestPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.93 Safari/537.36");
		requestPost.setHeader("Connection", "keep-alive");
		
		HttpEntity entity = new UrlEncodedFormEntity(formparams, "utf-8");  
		//requestPost.setEntity(new UrlEncodedFormEntity(formparams, "utf-8"));
		requestPost.setEntity(entity);  
		
		System.out.println(requestUrl);
		HttpResponse response = httpclient.execute(requestPost);
		String responseHtml = EntityUtils.toString(response.getEntity());
		System.out.println(responseHtml);
	}

}
