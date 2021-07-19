package com.st.util;

import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


//http://walsece.iteye.com/blog/169514
//http://blog.csdn.net/caesardadi/article/details/8622266
//http://javaeedevelop.iteye.com/blog/1242031
//http://bbs.csdn.net/topics/370137885
public class Test {

	public static void main(String[] args) throws IOException {
		
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:app.xml");
		
		System.out.println(ac);
		Stock st = (Stock) ac.getBean("stock");
		st.monitor();
	}

}
