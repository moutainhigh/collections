package com.ly.http.pingan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ly.dao.MyExcepitonLogDao;
import com.ly.pojo.MyExcepitonLog;

//https://stock.pingan.com/omm/http/common/getServiceDatas?callback=jQuery183001228701700355228_1545816067258
//https://blog.csdn.net/zhangbinu/article/details/72777620
//https://blog.csdn.net/wisdom_maxl/article/details/65631825 cookie 保持登录
@Component
public class PingAnHttp {
	private static Logger logger = Logger.getLogger("myhttp");
	
	@Autowired
	private MyExcepitonLogDao dao;
	
	public String httpGet(String url,Map<String,String> headers) {
		MyExcepitonLog mylog  = new MyExcepitonLog();
		mylog.setUrl(url);
		
		String srtResult = null;
		
		 RequestConfig requestConfig = RequestConfig.custom()
	                .setConnectTimeout(5000)   //设置连接超时时间
	                .setConnectionRequestTimeout(5000) // 设置请求超时时间
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
				
			
			/*httpget.setHeader("accept", "application/json");
			httpget.addHeader("Accept-Charset", "utf-8");
			httpget.addHeader("Accept-Language", "zh-CN,zh;q=0.9");
			httpget.addHeader("Accept-Encoding", "gzip, deflate, br");
			httpget.setHeader("Connection", "keep-alive"); 
			httpget.setHeader("Cache-Control", "no-cache"); 
	        // 传输的类型
			httpget.addHeader("Cookie", "WEBTRENDS_ID=4.0.4.20-3912647088.30704422; pa_stock_client_id=cnsz045162|19451640254239497|94dab926-e74a-400a-a54f-d3308fe67e05; ps_login_app_name=AYLCH5; ps_login_union_id=aae15dba1c2b4d5faceaf303e92428a9; __utma=33891555.1296176584.1543019349.1543019349.1543019349.1; __utmz=33891555.1543019349.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); WT-FPC=id=4.0.4.20-3912647088.30704422:lv=1543019717916:ss=1543019348628:fs=1542975627051:pn=1:vn=1; BIGipServersmt-hs-html5_30074_PrdPool=1124471212.31349.0000; BIGipServersis-stock-frontend-m-static_30075_PrdPool=554045868.31605.0000; paid_test=56ca049e-75fe-880a-4a66-05f056030f53; ps_login_token_id=N_C2FEDB427F4809E19EB217B2E1A3196398939B319977F8C3026E95958E39BFD24865DAECC0997CBB386EE60C7B089F7546F3EA25F4318489AB2EB2D1E86CDB06417B58BB7A0FA4F9; WT-FPC=id=4.0.4.20-3912647088.30704422:lv=1543049829115:ss=1543049694585:fs=1542975627051:pn=3:vn=2");
			httpget.setHeader("Host", "m.stock.pingan.com"); 
			httpget.setHeader("Pragma", "no-cache"); 
			httpget.setHeader("Referer", "https://m.stock.pingan.com/html/h5security/wechat/trade/index.html"); 
			httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
			httpget.setHeader("X-Requested-With", "XMLHttpRequest"); */
			
			
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
		} catch (Exception e) {
			e.printStackTrace();
			mylog.setLogContent(e.getMessage());
			mylog.setLogType("0");
			dao.save(mylog);
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				dao.save(mylog);
				mylog.setLogContent(e.getMessage());
				mylog.setLogType("0");
				e.printStackTrace();
			}
		}
		
		return srtResult;
	}
	
	
	
	
	
	
     public String httpPost(String url,Map<String,String> headers,Map<String,String> map){
    	 MyExcepitonLog mylog  = new MyExcepitonLog();
 		mylog.setUrl(url);
    	 
    	 String strResult = "";
		//获取可关闭的 httpCilent
       // CloseableHttpClient httpClient = HttpClients.createDefault();
    	 
    	  CookieStore cookieStore = new BasicCookieStore();
    	  CloseableHttpClient httpClient  = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
    	 
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
       /* httpPost.setHeader("accept", "application/json");
        httpPost.addHeader("Accept-Charset", "utf-8");
        httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.9");
        httpPost.addHeader("Accept-Encoding", "gzip, deflate, br");
        httpPost.setHeader("Connection", "keep-alive"); 
		httpPost.setHeader("Cache-Control", "no-cache"); 
		//httpPost.setHeader("Content-Length", "15"); 
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded"); 
        // 传输的类型
		httpPost.addHeader("Cookie", "WEBTRENDS_ID=4.0.4.20-3912647088.30704422; pa_stock_client_id=cnsz045162|19451640254239497|94dab926-e74a-400a-a54f-d3308fe67e05; ps_login_app_name=AYLCH5; ps_login_union_id=aae15dba1c2b4d5faceaf303e92428a9; __utma=33891555.1296176584.1543019349.1543019349.1543019349.1; __utmz=33891555.1543019349.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); WT-FPC=id=4.0.4.20-3912647088.30704422:lv=1543019717916:ss=1543019348628:fs=1542975627051:pn=1:vn=1; BIGipServersmt-hs-html5_30074_PrdPool=1124471212.31349.0000; BIGipServersis-stock-frontend-m-static_30075_PrdPool=554045868.31605.0000; paid_test=56ca049e-75fe-880a-4a66-05f056030f53; ps_login_token_id=N_C2FEDB427F4809E19EB217B2E1A3196398939B319977F8C3026E95958E39BFD24865DAECC0997CBB386EE60C7B089F7546F3EA25F4318489AB2EB2D1E86CDB06417B58BB7A0FA4F9; WT-FPC=id=4.0.4.20-3912647088.30704422:lv=1543049829115:ss=1543049694585:fs=1542975627051:pn=3:vn=2");
		httpPost.setHeader("Host", "m.stock.pingan.com"); 
		httpPost.setHeader("Pragma", "no-cache"); 
		httpPost.setHeader("Referer", "https://m.stock.pingan.com/html/h5security/wechat/trade/index.html"); 
		httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
		httpPost.setHeader("X-Requested-With", "XMLHttpRequest"); */
        
        
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
			mylog.setLogContent(e.getMessage());
			mylog.setLogType("0");
			dao.save(mylog);
        }finally {
            try {
                if(httpClient != null){
                    httpClient.close(); //释放资源
                }
            } catch (IOException e) {
            	e.printStackTrace();
    			mylog.setLogContent(e.getMessage());
    			mylog.setLogType("0");
    			dao.save(mylog);
            }
        }
		return strResult;
	}

     
     
     
     
     
     
     
     
     
     //https://blog.csdn.net/happymff/article/details/78785956
     //https://www.cnblogs.com/proli/p/8630590.html
     //https://blog.csdn.net/rise511/article/details/79234539
     public String httpPostDoLogin(String url,Map<String,String> headers,Map<String,String> map) {
    	 String strResult = "";
		//获取可关闭的 httpCilent
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //配置超时时间
        RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(1000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(1000).setRedirectsEnabled(true).build();
         
        HttpPost httpPost = new HttpPost(url);
        //设置超时时间
        httpPost.setConfig(requestConfig);
        String strPayloadDataJson = "{\"appChannel\":\"H5\"}";
        System.out.println(strPayloadDataJson);
        StringEntity stringEntity;
        if (headers != null) {
			for (String key : headers.keySet()) {
				httpPost.setHeader(key, headers.get(key));
			}
		}
        
        try {
        	stringEntity = new StringEntity((strPayloadDataJson), "application/json", "utf-8");
			httpPost.setEntity(stringEntity);
			
            HttpResponse httpResponse = httpClient.execute(httpPost);
            System.out.println(httpResponse);
            if(httpResponse != null){ 
                //System.out.println(httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    strResult = EntityUtils.toString(httpResponse.getEntity());
                    System.out.println(strResult);
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
     
     
     
     
     public String httpPostDoLoginRedirect(String url,Map<String,String> headers,Map<String,String> map) {
    	 String strResult = "";
		//获取可关闭的 httpCilent
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //配置超时时间
        RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(1000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(1000).setRedirectsEnabled(true).build();
         
        HttpPost httpPost = new HttpPost(url);
        //设置超时时间
        httpPost.setConfig(requestConfig);
        String  strs="{'body':'zg4XMXrLyE55vLav0lnPpI1jQE0MczPCjNKf4lWc2MmydAF%2FrOURhKnmu7lEkLfsPvWYc5aPfWUKysLPF3sZOHrq0HUYRJvx4krWGdSO6fgeSx4DF1W52hl3ElznU%2BnzYjFqHChruxq74CBWz0jLRSa8VTLZ7Iu5t5EWeuUuMWYMdcHEqcsiDl%2FOO3c%2F0ds%2ByFvEOp5u9a%2B20n2qdrgj5cjoTN240JfcPR%2BFRT9rS6RtQj0YNeKk6HcaVu%2BuRQPE96oNXEwzVbk0ncbdE3gMm5JxOu%2Fjduf6d6l86e5Q8%2F0dC5g52o3njwZDW8BUDC1AXvhXB0SDfoKQlEsvKGU70b65ZYKgc1nvTfz7TTZhWKk%3D','spartamsg':'eyJzZGt2ZXJzaW9uIjoiMS4zLjAiLCJkaWQiOiI3NDc5MTZjNzM3MDFlMjY2MDY5ZTUyNDdhMjBhYTdiOCIsInVzZXJfYWdlbnQiOiJNb3ppbGxhLzUuMCAoV2luZG93cyBOVCA2LjE7IFdpbjY0OyB4NjQpIEFwcGxlV2ViS2l0LzUzNy4zNiAoS0hUTUwsIGxpa2UgR2Vja28pIENocm9tZS83MC4wLjM1MzguMTAyIFNhZmFyaS81MzcuMzYiLCJyZWZlcmVyIjoiaHR0cHM6Ly9zdG9jay5waW5nYW4uY29tLyIsImd1aWQiOiJkMGM4Mjk2Ni1hYjcyLTM0YWUtNTlhYi1jYWM5Y2IxNzk0ZWUiLCJvcyI6IlcifQ==','encrypt':true}";
        JSONObject mapObject=JSONObject.parseObject(strs);
         String strPayloadDataJson =mapObject.toString();
        StringEntity stringEntity;
        if (headers != null) {
			for (String key : headers.keySet()) {
				httpPost.setHeader(key, headers.get(key));
			}
		}
        
        try {
        	stringEntity =new StringEntity((strPayloadDataJson), "application/json", "utf-8");
			httpPost.setEntity(stringEntity);
			
            HttpResponse httpResponse = httpClient.execute(httpPost);
            System.out.println(httpResponse);
            if(httpResponse != null){ 
                //System.out.println(httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    strResult = EntityUtils.toString(httpResponse.getEntity());
                    System.out.println(strResult);
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
     
     
     
     //https://www.cnblogs.com/qiaoyeye/p/4741579.html
     //https://blog.csdn.net/zhangbinu/article/details/72777620
     public String httpPostLoginGetCookie(String url,Map<String,String> headers,Map<String,String> map){
    	 String strResult = "";
		//获取可关闭的 httpCilent
        CloseableHttpClient httpClient = HttpClients.createDefault();
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
        
        
        //装配post请求参数
        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>(); 
        for(Map.Entry<String,String> entry : map.entrySet()){
        	list.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
        }
        
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,"UTF-8"); 
            //设置post求情参数
            httpPost.setEntity(entity);
            CookieStore cookieStore = new BasicCookieStore();
            HttpResponse httpResponse = httpClient.execute(httpPost);
            System.out.println(httpResponse);
            
            List<Cookie> cookies = cookieStore.getCookies();
            for (int i = 0; i < cookies.size(); i++) {
            	System.out.println(cookies.get(i).getValue());
            }
            if(httpResponse != null){ 
                //System.out.println(httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    strResult = EntityUtils.toString(httpResponse.getEntity());
                    System.out.println(strResult);
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
     
}
