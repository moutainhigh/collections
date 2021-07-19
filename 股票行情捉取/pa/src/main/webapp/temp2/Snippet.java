package com.ly.task;

import java.util.HashMap;
import java.util.Map;


import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.ly.HttpUtils;


//https://market.aliyun.com/products/57126001/cmapi031752.html?spm=5176.2020520132.101.23.4d707218zmlDCi#sku=yuncode2575200009
public class Snippet {
	public static void main(String[] args) {
		    String host = "https://exempt.market.alicloudapi.com";
		    String path = "/exemptCode";
		    String method = "GET";
		    String appcode = "你自己的AppCode";
		    Map<String, String> headers = new HashMap<String, String>();
		    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		    headers.put("Authorization", "APPCODE " + appcode);
		    Map<String, String> querys = new HashMap<String, String>();
		    querys.put("content", "【涪擎】您的验证码是：{123456}，请在5分钟内使用。请勿泄露。");
		    querys.put("phone", "18269215167");
	
	
		    try {
		    	/**
		    	* 重要提示如下:
		    	* HttpUtils请从
		    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
		    	* 下载
		    	*
		    	* 相应的依赖请参照
		    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
		    	*/
		    	HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
		    	System.out.println(response);
		    	//System.out.println(response.toString());如不输出json, 请打开这行代码，打印调试头部状态码。
	                //状态码: 200 正常；400 URL无效；401 appCode错误； 403 次数用完； 500 API网管错误
		    	//获取response的body
		    	System.out.println(EntityUtils.toString(response.getEntity()));
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
		}
}

