package com.ly.http;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;


//https://blog.csdn.net/gg_yangliyang/article/details/80856270
@Component
public class CommHttpWithCookieUtil {
	private static Logger logger = Logger.getLogger(CommHttpWithCookieUtil.class);
	
	
	public String httpGet(String url,Map<String,String> headers) {
		
		 RequestConfig requestConfig = RequestConfig.custom()
	                .setConnectTimeout(5000)   //设置连接超时时间
	                .setConnectionRequestTimeout(5000) // 设置请求超时时间
	                .setSocketTimeout(5000)
	                .setRedirectsEnabled(true)//默认允许自动重定向
	                .build();
		 
		   //https://blog.csdn.net/ly6cyh/article/details/77141346
		 	//设置代理IP、端口、协议（请分别替换）
	        HttpHost proxy = new HttpHost("你的代理的IP", 8080, "http");

	        //把代理设置到请求配置
	       /* RequestConfig defaultRequestConfig = RequestConfig.custom()
	                .setProxy(proxy)
	                .build();*/

		
		//CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpClient httpclient = null;
		String srtResult = null;
		try {
			
			
			CookieStore cookieStore = new BasicCookieStore();
			//httpclient.setDefaultCookieStore(cookieStore);
			 httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
			
			// 创建httpget
			HttpGet httpget = new HttpGet(url);
			httpget.setConfig(requestConfig);
			
			//https://zhidao.baidu.com/question/106937907.html
			if (headers != null) {
				for (String key : headers.keySet()) {
					httpget.setHeader(key, headers.get(key));
				}
			}
				
			
			
			logger.debug("请求头====> " +httpget);
			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);
			
			 
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
		                
		                List<Cookie> cookies= cookieStore.getCookies();  //响应成功时，cookieStore里面已经存好cookies了
		                logger.debug("获取刚才最新响应的cookie信息 ===> ");
		                String str = this.getValueByName(cookies, "");
		                
		                logger.debug("响应内容====> " +srtResult);
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
	
	
	
	
	
	
     public String httpPost(String url,Map<String,String> headers,Map<String,String> map){
    	 String strResult = "";
		//获取可关闭的 httpCilent
      // CloseableHttpClient httpClient = HttpClients.createDefault();
    	 
    	 
    	 CloseableHttpClient httpClient  = null;
    	 
    	
    	 
    	 
    	  RequestConfig requestConfig = RequestConfig.custom()
	                .setConnectTimeout(5000)   //设置连接超时时间
	                .setConnectionRequestTimeout(5000) // 设置请求超时时间
	                .setSocketTimeout(5000)
	                .setRedirectsEnabled(true)//默认允许自动重定向
	                .build();
         
    	  CookieStore cookieStore = new BasicCookieStore();
			//httpclient.setDefaultCookieStore(cookieStore);
  	      httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
    	  
    	  
        HttpPost httpPost = new HttpPost(url);
        //设置超时时间
        httpPost.setConfig(requestConfig);
        
        
        if (headers != null) {
			for (String key : headers.keySet()) {
				httpPost.setHeader(key, headers.get(key));
			}
		}
        
        
        //装配post请求参数
        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>(); 
       /* list.add(new BasicNameValuePair("age", "20"));  //请求参数
        list.add(new BasicNameValuePair("name", "zhangsan")); //请求参数
        
*/      
        for(Map.Entry<String,String> entry : map.entrySet()){
        	list.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
        }
        
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,"UTF-8"); 
            //设置post求情参数
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            
          //  System.out.println(httpResponse);
            if(httpResponse != null){ 
                //System.out.println(httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    strResult = EntityUtils.toString(httpResponse.getEntity());
                    logger.debug(strResult);
                    
                    List<Cookie> cookies= cookieStore.getCookies();  //响应成功时，cookieStore里面已经存好cookies了
	                logger.debug("获取刚才最新响应的cookie信息 ===> ");
	                String str = this.getValueByName(cookies, "");
                    
                    
                } else if (httpResponse.getStatusLine().getStatusCode() == 400) {
                   // strResult = "Error Response: " + response.getStatusLine().toString();
                } else if (httpResponse.getStatusLine().getStatusCode() == 500) {
                   // strResult = "Error Response: " + response.getStatusLine().toString();
                } else {
                  //  strResult = "Error Response: " + response.getStatusLine().toString();
                } 
            }else{
                 
            }
           // System.out.println(strResult);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(httpClient != null){
                    httpClient.close(); //释放资源
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		return strResult;
	}

     
     //根据cookie的名字，从所有的cookie中找到值。
 	private static String getValueByName(List<Cookie> cookies, String name){
 		String value = null;
 		for(Cookie c:cookies){
 			if(name.equals(c.getName())){
 				value = c.getValue();
 				break;
 			}
 		}
 		return value;
 	}
     
}
