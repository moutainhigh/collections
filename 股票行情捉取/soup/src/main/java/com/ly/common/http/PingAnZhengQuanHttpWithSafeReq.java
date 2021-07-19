package com.ly.common.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

//https://stock.pingan.com/omm/http/common/getServiceDatas?callback=jQuery183001228701700355228_1545816067258
//https://blog.csdn.net/zhangbinu/article/details/72777620
//https://blog.csdn.net/wisdom_maxl/article/details/65631825 cookie 保持登录
@Component
public class PingAnZhengQuanHttpWithSafeReq {
	private static Logger logger = Logger.getLogger("myhttp");
	
	
	public String httpGet(String url,Map<String,String> headers) {
		
		String srtResult = null;
		
		 RequestConfig requestConfig = RequestConfig.custom()
	                .setConnectTimeout(5000)   //设置连接超时时间
	                .setConnectionRequestTimeout(5000) // 设置请求超时时间,单位毫秒。
	                .setSocketTimeout(5000)
	                .setRedirectsEnabled(true)//默认允许自动重定向
	                .build();
		
		//CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpClient httpclient = null;
		try {
			  //https://blog.csdn.net/gg_yangliyang/article/details/80856270设置cookie
			   //https://www.cnblogs.com/zeze/p/4953414.html
			  CookieStore cookieStore = new BasicCookieStore();
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
			//读取响应的cookie
			List<Cookie> cookies = cookieStore.getCookies();
			
			 logger.debug("获取最新cookie ===> ");
			 for (Cookie cookie : cookies) {
				// System.out.println(cookie.getName());
				// System.out.println(cookie.getValue());
				 logger.info(cookie.getName()  + " 【最新的cookies】" + cookie.getValue());
			}
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				// 打印响应状态
				 if(response.getStatusLine().getStatusCode() == 200){
		                srtResult = EntityUtils.toString(response.getEntity());//获得返回的结果
		                logger.debug("响应内容====> " +srtResult);
		               // System.out.println(srtResult);
		            }else if(response.getStatusLine().getStatusCode() == 400){
		                //..........
		            }else if(response.getStatusLine().getStatusCode() == 500){
		                //.............
		            }
				
			} finally {
				if (null != response){
					response.close();
				}
				if (null != httpclient){
					httpclient.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return srtResult;
	}
	
	
	
	
	
	
     public String httpPost(String url,Map<String,String> headers,String jsonStr){
    	 String strResult = "";
		//获取可关闭的 httpCilent
       // CloseableHttpClient httpClient = HttpClients.createDefault();
    	 
    	  CookieStore cookieStore = new BasicCookieStore();
    	  CloseableHttpClient httpClient  = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
    	  CloseableHttpResponse httpResponse  = null;
        //配置超时时间
        RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(1000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(1000).setRedirectsEnabled(true).build();
         
        HttpPost httpPost = new HttpPost(url);
        //设置超时时间
        httpPost.setConfig(requestConfig);
        
        
        if (headers != null) {
			for (String key : headers.keySet()) {
				httpPost.setHeader(key, headers.get(key));
			}
		}
      
        
        try {
        	StringEntity stringEntity = new StringEntity(jsonStr, "application/json", "utf-8");

        	//设置post求情参数
        	httpPost.setEntity(stringEntity);
            
             httpResponse = httpClient.execute(httpPost);
            List<Cookie> cookies = cookieStore.getCookies();
            logger.debug("获取最新cookie ===> ");
			 for (Cookie cookie : cookies) {
				// System.out.println(cookie.getName());
				// System.out.println(cookie.getValue());
				 logger.debug(cookie.getName()  + " 【最新的】" + cookie.getValue());
			}
            
          //  System.out.println(httpResponse);
            if(httpResponse != null){ 
                //System.out.println(httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    strResult = EntityUtils.toString(httpResponse.getEntity());
                    logger.debug(strResult);
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
                if(httpResponse != null){
                	httpResponse.close(); //释放资源
                }
            } catch (IOException e) {
            	e.printStackTrace();
            }
        }
		return strResult;
	}

     
     
     
     
     
 	
 	
     public String httpPost(String url,Map<String,String> headers,Map<String,String> map){
    	 String strResult = "";
		//获取可关闭的 httpCilent
       // CloseableHttpClient httpClient = HttpClients.createDefault();
    	 
    	 //https://blog.csdn.net/hellozpc/article/details/98082895
    	 
    	  CookieStore cookieStore = new BasicCookieStore();
    	  CloseableHttpClient httpClient  = HttpClients.custom()
    			  .setDefaultCookieStore(cookieStore)
    			   .evictIdleConnections(1, TimeUnit.MINUTES)// 定期回收空闲连接
                   .evictExpiredConnections()// 定期回收过期连接
    			  .setConnectionTimeToLive(5, TimeUnit.SECONDS) //// 连接存活时间，如果不设置，则根据长连接信息决定
    			  .build();
    	  CloseableHttpResponse httpResponse  = null;
    	  
    	  //https://www.jianshu.com/p/3b6d7aa2043a
    	  //UrlEncodedFormEntity entity = null;
    	  HttpEntity  entity = null;
    	  
    	  
    	 // HttpHost proxy = new HttpHost("117.65.47.142", 9999, "http");
    	  
        //配置超时时间
        RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(1000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(1000).setRedirectsEnabled(true).build();//.setProxy(proxy).
         
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
        	entity = new UrlEncodedFormEntity(list,"UTF-8"); 
            //设置post求情参数
             httpPost.setEntity(entity);
             httpResponse = httpClient.execute(httpPost);
            List<Cookie> cookies = cookieStore.getCookies();
            logger.debug("获取最新cookie ===> ");
			 for (Cookie cookie : cookies) {
				// System.out.println(cookie.getName());
				// System.out.println(cookie.getValue());
				 logger.debug(cookie.getName()  + " 【最新的】" + cookie.getValue());
			}
            
          //  System.out.println(httpResponse);
            if(httpResponse != null){ 
                //System.out.println(httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    strResult = EntityUtils.toString(httpResponse.getEntity());
                    
                    
                    logger.debug(strResult);
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
                if(httpResponse != null){
                	httpResponse.close(); //释放资源
                }
            } catch (IOException e) {
            	e.printStackTrace();
            }
            
            try {
				entity.getContent().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
		return strResult;
	}

     
     
     
     
     
     
     
}
