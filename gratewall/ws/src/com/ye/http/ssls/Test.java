package com.ye.http.ssls;

import java.net.URLEncoder;



public class Test {

	
	public static void main(String[] args) throws Exception {
		String str = MyX509TrustManager.getClient("http://www.baidu.com");
		System.out.println(str);
		/*String key = "27C31DC6A30042BCA2020B41A8D38326";
		String type="0";
		String phone = "18269215167";
		
		int a = (int) (Math.random() * (9999 - 1000 + 1)) + 1000 ;// 产生1000-9999的随机数
		
		String mess = URLEncoder.encode("002552,000983,002547", "utf-8");
		String url = "https://api.szaic.gov.cn/sms/" + key.trim() + "/" + type.trim()+ "/" + phone.trim() + "/" + mess.trim();
		String strJson = MyX509TrustManager.getClient(url);
		System.out.println(strJson);*/
	}
}
