package com.ye.monitor;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
//HTTP 请求三种：

//http://www.jb51.net/article/106908.htm
//http://blog.csdn.net/a511718727/article/details/28483909
//http://bbs.csdn.net/topics/391887692
//http://bbs.csdn.net/topics/391887692
//https://hq.cmschina.com/hq/views/hq/zxgList.html
@Component
public class YeZhaoShangUtil {
	public static Logger log = LogManager.getLogger(YeZhaoShangUtil.class);
	
	public  String httpPost(String code)  {
		String str = null;
		//CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpClient httpclient = HttpClients.custom().build();  //为了给头部添加信息
			//创建httpget
		//HttpPost httppost = new HttpPost(url);
		HttpPost httppost = new HttpPost("https://hq.cmschina.com/market/json");
		httppost.setHeader("accept", "*/*");
		httppost.setHeader("Connection", "keep-alive");  
		//httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.93 Safari/537.36");  
		//httpget.setHeader("User-Agent", "Mozilla/5.0 (iPad; CPU OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1"); 
		httppost.setHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 6_1_3 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Mobile/10B329 MicroMessenger/5.0.1"); 
		//HttpResponse response = null;
		CloseableHttpResponse  response = null;
		//https://www.cnblogs.com/kobe8/p/4030396.html
		try {
			httppost.setURI(new URI(httppost.getURI().toString()));
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			formparams.add(new BasicNameValuePair("funcno", "20000"));
			formparams.add(new BasicNameValuePair("version", "1"));
			formparams.add(new BasicNameValuePair("stock_list", code)); // :  https://wenku.baidu.com/view/ff8cd627f111f18583d05a43.html
			//formparams.add(new BasicNameValuePair("stock_list", "SZ%3A000983"));
			//formparams.add(new BasicNameValuePair("stock_list", "SH%3A603991"));
			formparams.add(new BasicNameValuePair("field", "1%3A2%3A3%3A6%3A9%3A10%3A11%3A12%3A14%3A16%3A21%3A22%3A48%3A8%3A13%3A18%3A19%3A27%3A31%3A68%3A58%3A78%3A79%3A7%3A15"));
			
			
			httppost.setEntity(new UrlEncodedFormEntity(formparams, "utf-8"));
			
			
			//System.out.println(httppost);
			response  = httpclient.execute(httppost);
			 str = EntityUtils.toString(response.getEntity());
			//System.out.println(responseHtml);
			
			
		} catch (URISyntaxException | ParseException | IOException e) {
			e.printStackTrace();
		}finally{
			try {
				httpclient.close();
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return str;
	}
	
}
