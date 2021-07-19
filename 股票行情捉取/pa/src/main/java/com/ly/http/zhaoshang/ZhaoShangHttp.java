package com.ly.http.zhaoshang;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class ZhaoShangHttp {
	private static Logger logger = Logger.getLogger(ZhaoShangHttp.class);
	
	
	public String httpGet(String url,Map<String,String> headers) {
		logger.debug(url);
		 RequestConfig requestConfig = RequestConfig.custom()
	                .setConnectTimeout(5000)   //设置连接超时时间
	                .setConnectionRequestTimeout(5000) // 设置请求超时时间
	                .setSocketTimeout(5000)
	                .setRedirectsEnabled(true)//默认允许自动重定向
	                .build();
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String srtResult = null;
		try {
			
			
			// 创建httpget.
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
   	 
	 RequestConfig requestConfig = RequestConfig.custom()
             .setConnectTimeout(5000)   //设置连接超时时间
             .setConnectionRequestTimeout(5000) // 设置请求超时时间
             .setSocketTimeout(5000)
             .setRedirectsEnabled(true)//默认允许自动重定向
             .build();
	
	 	CloseableHttpClient httpclient = HttpClients.createDefault();
   	 
        
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
           HttpResponse httpResponse = httpclient.execute(httpPost);
           
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
               if(httpclient != null){
            	   httpclient.close(); //释放资源
               }
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
		return strResult;
	}

}
