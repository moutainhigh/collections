package com.ly.http.tencent;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;


@Component
public class TenCentHttp {
private static Logger logger = Logger.getLogger(TenCentHttp.class);
	
	
	public String httpGet(String url,Map<String,String> headers) {
		
		 RequestConfig requestConfig = RequestConfig.custom()
	                .setConnectTimeout(5000)   //设置连接超时时间
	                .setConnectionRequestTimeout(5000) // 设置请求超时时间
	                .setSocketTimeout(5000)
	                .setRedirectsEnabled(true)//默认允许自动重定向
	                .build();
		
		//CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpClient httpclient = null;
		String srtResult = null;
		try {
			  CookieStore cookieStore = new BasicCookieStore();
			  httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
			
			// 创建httpget
			HttpGet httpget = new HttpGet(url);
			httpget.setConfig(requestConfig);
			
			if (headers != null) {
				for (String key : headers.keySet()) {
					httpget.setHeader(key, headers.get(key));
				}
			}
				
			
			logger.debug("请求头====> " +httpget);
			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);
			
			 List<Cookie> cookies = cookieStore.getCookies();
			
			 logger.debug("获取最新cookie ===> ");
			 for (Cookie cookie : cookies) {
				// System.out.println(cookie.getName());
				// System.out.println(cookie.getValue());
				 
				 logger.debug(cookie.getName()  + " 【最新的】" + cookie.getValue());
			}
			 
			 
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				// 打印响应状态
			  /*	System.out.println(response.getStatusLine());
				if (entity != null) {
					// 打印响应内容长度
					System.out.println("Response content length: " + entity.getContentLength());
					// 打印响应内容
					System.out.println("Response content: " + EntityUtils.toString(entity,"UTF-8"));
				}*/
				
				 if(response.getStatusLine().getStatusCode() == 200){
		                srtResult = EntityUtils.toString(response.getEntity());//获得返回的结果
		               // logger.debug("响应内容====> " +srtResult);
		               // System.out.println(srtResult);
		            }else if(response.getStatusLine().getStatusCode() == 400){
		                //..........
		            }else if(response.getStatusLine().getStatusCode() == 500){
		                //.............
		            }
				
			} finally {
				if (null != response) response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return srtResult;
	}
	
}
