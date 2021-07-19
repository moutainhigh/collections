package com.gwssi.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ExcelUrlReaderUtil
{
	public static void writeExcel(URL url,OutputStream out){
		URLConnection urlConnection = null;
		try {
			urlConnection = url.openConnection();
		} catch (IOException e) {
			new XmlUrlReaderException("URL打开失败：",e);
		}   
		urlConnection.setRequestProperty("method", "POST");
		urlConnection.setRequestProperty("content-type",   
			"application/x-www-form-urlencoded;charset=utf-8");//设置http的mime   
		urlConnection.setDoOutput(true);   
		
		InputStream stream = null;
		try {
			stream = urlConnection.getInputStream();
			int bytesRead = 0;
	        byte[] buffer = new byte[1024];
	        while ( (bytesRead = stream.read(buffer, 0, 1024)) != -1) {
	        	out.write(buffer, 0, bytesRead);
	        }
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			 try {
				stream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void  writeExcel(URL url){
		URLConnection urlConnection = null;
		try {
			urlConnection = url.openConnection();
		} catch (IOException e) {
			new XmlUrlReaderException("URL打开失败：",e);
		}   
		urlConnection.setRequestProperty("method", "POST");
		urlConnection.setRequestProperty("content-type",   
			"application/x-www-form-urlencoded;charset=utf-8");//设置http的mime   
		urlConnection.setDoOutput(true);   
		
		try {
			InputStream stream = urlConnection.getInputStream();
			File objFile = new File("D:/test.xls");
			FileOutputStream fileOutputStream = new FileOutputStream(objFile);
			
			int bytesRead = 0;
	        byte[] buffer = new byte[1024];
	        while ( (bytesRead = stream.read(buffer, 0, 1024)) != -1) {
	        	fileOutputStream.write(buffer, 0, bytesRead);
	        	//objFile.write(buffer, 0, bytesRead);//将文件写入服务器
	        }
	        stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void writeExcel(String urlString,OutputStream out) throws XmlUrlReaderException{
		//urlString = "http://127.0.0.1:7001/beacon/txn202011a.ajax";
		URL  url = null;
		try {
			url  =  new URL(urlString);
		} catch (MalformedURLException e) {
			throw new XmlUrlReaderException("URL错误：",e);
		}
		writeExcel(url,out);
	}
	
	public static void main(String args[]){
		String urlString ="http://127.0.0.1:7001/beacon/getWjgWj";
		try {
			File objFile = new File("D:/test.xls");
			try {
				FileOutputStream fileOutputStream = new FileOutputStream(objFile);
				ExcelUrlReaderUtil.writeExcel(urlString,fileOutputStream);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (XmlUrlReaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
