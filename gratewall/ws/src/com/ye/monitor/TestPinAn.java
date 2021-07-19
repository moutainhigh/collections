package com.ye.monitor;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class TestPinAn {
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.custom().build(); // 为了给头部添加信息

		HttpGet requestGet = new HttpGet("https://m.stock.pingan.com/h5quote/quote/getRealTimeData?random=0.08151615015075153&stockCode=603848&codeType=4353&type=shsz");
		requestGet.setHeader("accept", "*/*");
		requestGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.82 Safari/537.36"); 
		requestGet.setHeader("Connection", "keep-alive");
		
		HttpResponse response = httpclient.execute(requestGet);
		String responseHtml = EntityUtils.toString(response.getEntity());
		System.out.println(responseHtml);
	}
	
}
