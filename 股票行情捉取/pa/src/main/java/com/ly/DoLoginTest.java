package com.ly;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ly.common.utils.CookieUtil;
import com.ly.http.pingan.PingAnHttp;

public class DoLoginTest {
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:app.xml");
		PingAnHttp  pa = ac.getBean(PingAnHttp.class);
		String url ="https://login.stock.pingan.com/loginservice/captcha";
		
		
		Map<String,String> headers = new HashMap<String,String> ();
		headers.put("accept", "application/json, text/javascript, */*; q=0.01");
		headers.put("Accept-Encoding", "gzip, deflate, br");
		headers.put("Accept-Language", "zh-CN,zh;q=0.9");
		headers.put("Accept-Charset", "utf-8");
		headers.put("Connection", "keep-alive");
		headers.put("Cache-Control", "no-cache");
		headers.put("Content-Type", "application/json;charset=UTF-8");
		
		// 传输的类型
		headers.put("Cookie", "BIGipServersis-tradesso-login-com_30075_PrdPool=!RbqMozikoEQdBfC3WPIL4ff4Z7fFeZJ+SxuYjkA5tp9mNiCuwDiXiSMAJTnxdtK/LovA1FQniAgje+0=; WT-FPC=id=4.0.4.21-2489478384.30715421:lv=1547699531678:ss=1547699531678:fs=1547699531678:pn=1:vn=1; WEBTRENDS_ID=4.0.4.21-2489478384.30715421; paid_test=4c8d8c18-b1af-4c78-6a25-7a77a8f8a77c");
		headers.put("Host", "login.stock.pingan.com");
		headers.put("Origin", "login.stock.pingan.com");
		headers.put("Pragma", "no-cache");
		headers.put("Referer", "https://login.stock.pingan.com/login/index_pc.html?referUrl=https%3A%2F%2Fstock.pingan.com%2F&kbChannel=J&accountType=1|2|3|4");
		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
		headers.put("X-Requested-With", "XMLHttpRequest");
		
		Map<String,String> map = new HashMap<String,String> ();
		
		String str = pa.httpPostDoLogin(url, headers, map);
		System.out.println(str);
	}
}
