package com.gwssi.common.rodimus.report.util;

import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class StringUtil {
	
	public static String safe2String(Object obj) {
		return (obj == null) ? "" : obj.toString();
	}
	
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 将返回结果转成json格式并已ajax的形式返回
	 * @param response
	 * @param obj
	 */
	
	public static void printJson(HttpServletResponse response,Object obj){
		PrintWriter out=null;
		String jsonString=JSON.toJSONString(obj,SerializerFeature.DisableCircularReferenceDetect);
		//System.out.println("---->"+jsonString);
		try {
			response.setCharacterEncoding("GBK");
			response.setContentType("text/json");
			out=response.getWriter();
			out.write(jsonString);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (out!=null) {
				out.close();
			}
		}
	}
}
