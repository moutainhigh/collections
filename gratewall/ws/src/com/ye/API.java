package com.ye;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//http://apistore.baidu.com/astore/serviceinfo/28642.html
//http://apistore.baidu.com/astore/serviceinfo/28585.html
//http://apistore.baidu.com/apiworks/servicedetail/1646.html
//https://www.showapi.com/api/lookPoint/131/49
public class API {
	

	/**
	 * @param urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 */
	public static String request(String httpUrl, String httpArg) {
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();
	    httpUrl = httpUrl + "?" + httpArg;

	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("GET");
	        // 填入apikey到HTTP header
	        connection.setRequestProperty("apikey",  "您自己的apikey");
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}
	
	public static void main(String[] args) {
		String httpUrl = "http://apis.baidu.com/showapi_open_bus/stock_his/sh_stock_his";
		String httpArg = "begin=2015-09-01&end=2015-09-02&code=600004";
		String jsonResult = request(httpUrl, httpArg);
		System.out.println(jsonResult);
	}

	
	
	
	//https://www.showapi.com/api/lookPoint/131/49
	public void shows () throws Exception {
        URL u=new URL("http://route.showapi.com/131-49?showapi_appid=myappid&code=&day=&showapi_sign=mysecret");
        InputStream in=u.openStream();
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        try {
            byte buf[]=new byte[1024];
            int read = 0;
            while ((read = in.read(buf)) > 0) {
                out.write(buf, 0, read);
            }
        }  finally {
            if (in != null) {
                in.close();
            }
        }
        byte b[]=out.toByteArray( );
        System.out.println(new String(b,"utf-8"));
	}

}
