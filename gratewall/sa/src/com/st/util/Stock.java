package com.st.util;

import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//http://blog.csdn.net/fatowen/article/details/7683029
//http://blog.sina.com.cn/s/blog_510844b70102wrvf.html
@Component
public class Stock {

	@Value("${stock}")
	private String stock;
	
	@Scheduled(cron = "0/4 * *  * * ? ") // 每1秒执行一次
	public void monitor() throws IOException {
		System.out.println(stock);
	
		
		URL url = new URL("http://hq.sinajs.cn/list="+stock);
		System.out.println(url);
		//Toolkit.getDefaultToolkit().beep();
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		InputStream ins = conn.getInputStream();
		BufferedInputStream bs = new BufferedInputStream(ins);
		BufferedReader ir = new BufferedReader(new InputStreamReader(bs, Charset.forName("GBK")));
		
		System.out.println(ir.readLine());
		
		/*String str = ir.readLine().split("=")[1];
		System.out.println(str.split(",")[0] + " == " + str.split(",")[3]);*/
	}
}
