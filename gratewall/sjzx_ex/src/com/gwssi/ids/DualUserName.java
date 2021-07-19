package com.gwssi.ids;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.gwssi.ids.PropertiesUtil;



public class DualUserName {

/*	public static void main(String[] args){
		System.out.println(UserNameDual("dd"));
	}*/
	/**
	 * 处理用户登录看是否用加后缀
	 * @param username
	 * @return
	 */
	public static  String UserNameDual(String username){
		String t1=username;
		username=username.toUpperCase();
		if(username==null|| username.length()<1){
			return null;
		}
		
		
		PropertiesUtil properties = new PropertiesUtil("/usernamedual.properties");
		String nosuffixuser = properties.getProperty("ids.login.no.suffix");
		String suffix= properties.getProperty("ids.login.suffix");
		if(nosuffixuser!=null&&nosuffixuser.length()>0){
			nosuffixuser=nosuffixuser.toUpperCase();
			String nosuffu[] =nosuffixuser.split(",");
			List list =Arrays.asList(nosuffu);
			if(list.contains(username)){
				return t1;
			}else{
				if(suffix!=null&&suffix.length()>0){
					t1=t1+suffix;
				}
					return t1;
				
			}
		}else{
			return null;
		}
	}
}
