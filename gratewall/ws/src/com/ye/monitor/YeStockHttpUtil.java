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


@Component
public class YeStockHttpUtil {
	public static Logger log = LogManager.getLogger(YeStockHttpUtil.class);
	
	public  String httpGet(String url){
		String str = null;
		//CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpClient httpclient = HttpClients.custom().build();  //Ϊ�˸�ͷ�������Ϣ
		try{
			//����httpget
			//HttpGet httpget = new HttpGet("http://rs.xidian.edu.cn/forum.php");
			//HttpGet httpget = new HttpGet("http://club.xdnice.com/thread-1400344-1-1.html");
			//HttpGet httpget = new HttpGet("https://xueqiu.com/stock/pankou.json?symbol=SZ000983");
			//HttpGet httpget = new HttpGet("http://hq.sinajs.cn/list=sh601006");
			HttpGet httpget = new HttpGet(url);
			httpget.setHeader("accept", "*/*");
			httpget.setHeader("Connection", "keep-alive");  
			//httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.93 Safari/537.36");  
			httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.82 Safari/537.36"); 
			
			//HttpGet httpget = new HttpGet("http://api.money.126.net/data/feed/1000983");
			//System.out.println("executing request " + httpget.getURI());
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
