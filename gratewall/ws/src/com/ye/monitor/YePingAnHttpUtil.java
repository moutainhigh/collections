package com.ye.monitor;


import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
//HTTP �������֣�


//http://blog.csdn.net/gplihf/article/details/52790847
//http://www.iteye.com/problems/20999
//https://zhidao.baidu.com/question/169109435.html
//ͷ����Ϣhttp://blog.csdn.net/gplihf/article/details/52790847
//http://mercymessi.iteye.com/blog/2250161
//http://blog.csdn.net/u010726042/article/details/51198715\

//https://www.cnblogs.com/nihaorz/p/6952050.html
//http://blog.csdn.net/u010726042/article/details/51198715
//ϸ��http://mercymessi.iteye.com/blog/2250161
//����л���http://bbs.csdn.net/topics/390422664?page=1

//����ֻ���http://blog.csdn.net/ccclll1990/article/details/17006159
@Component
public class YePingAnHttpUtil {
	public static Logger log = LogManager.getLogger(YePingAnHttpUtil.class);
	
	public  String httpGet(String url){
		String str = null;
		//CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpClient httpclient = HttpClients.custom().build();  //Ϊ�˸�ͷ�������Ϣ
		try{
			//����httpget
			HttpGet httpget = new HttpGet(url);
			httpget.setHeader("accept", "*/*");
			httpget.setHeader("Connection", "keep-alive");  
			//httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.93 Safari/537.36");  
			//httpget.setHeader("User-Agent", "Mozilla/5.0 (iPad; CPU OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1"); 
			httpget.setHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 6_1_3 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Mobile/10B329 MicroMessenger/5.0.1"); 
			
			httpget.setURI(new URI(httpget.getURI().toString()));
			
			//ִ��get����
			CloseableHttpResponse response = httpclient.execute(httpget);
			try{
				//��ȡ��Ӧʵ��
				HttpEntity entity = response.getEntity();
				//��Ӧ״̬
				//System.out.println(response.getStatusLine());
				//log.log(Level.INFO,"HTTP����״̬ ===>>>>"+ response.getStatusLine()+",����ĵ�ַ ====> " + url);
				if(entity != null){
					//���ݳ���
					//System.out.println("Response content length: " + entity.getContentLength());
					//��Ӧ����
					//System.out.println("Response content: " + EntityUtils.toString(entity));
					str = EntityUtils.toString(entity);
					//System.out.println(str);
				}
			}finally{
				response.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				httpclient.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return str;
	}
	
}
