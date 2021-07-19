package com.gwssi.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils; 

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gwssi.comselect.model.EntSelectQueryBo;


public class BeanUtilTest {  
  
    public static void main(String[] args) {  
  
    	EntSelectQueryBo bo = new EntSelectQueryBo();
		bo.setEstdate_start("2000-01-01");
		bo.setEstdate_end("2015-01-01");
		String[] entname_term  = new String[2];
		entname_term[0]="include";
		entname_term[1]="include";
		bo.setEntname_term(entname_term);
		 String[] entname= new String[2] ;//企业名称
		 entname[0]="深圳";
		 entname[1]="食品";	 
		 bo.setEntname(entname);
	     JSONObject json = (JSONObject) JSON.toJSON(bo);  
	       
			System.out.println(json.toJSONString());
        // 将javaBean 转换为map  
        Map<String, Object> map = transBean2Map(bo);  
  
        System.out.println("--- transBean2Map Map Info: ");  
        for (Map.Entry<String, Object> entry : map.entrySet()) {  
            System.out.println(entry.getKey() + ": " + entry.getValue());  
        }  
  
    }  
  
    // Map --> Bean 2: 利用org.apache.commons.beanutils 工具类实�?Map --> Bean  
    public static void transMap2Bean2(Map<String, Object> map, Object obj) {  
        if (map == null || obj == null) {  
            return;  
        }  
        try {  
            BeanUtils.populate(obj, map);  
        } catch (Exception e) {  
            System.out.println("transMap2Bean2 Error " + e);  
        }  
    }  
  
    // Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean  
    public static void transMap2Bean(Map<String, Object> map, Object obj) {  
  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
  
                if (map.containsKey(key)) {  
                    Object value = map.get(key);  
                    // 得到property对应的setter方法  
                    Method setter = property.getWriteMethod();  
                    setter.invoke(obj, value);  
                }  
  
            }  
  
        } catch (Exception e) {  
            System.out.println("transMap2Bean Error " + e);  
        }  
  
        return;  
  
    }  
  
    // Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map  
    public static Map<String, Object> transBean2Map(Object obj) {  
  
        if(obj == null){  
            return null;  
        }          
        Map<String, Object> map = new HashMap<String, Object>();  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
  
                // 过滤class属�?  
                if (!key.equals("class")) {  
                    // 得到property对应的getter方法  
                    Method getter = property.getReadMethod();  
                    Object value = getter.invoke(obj);  
  
                    map.put(key, value);  
                }  
  
            }  
        } catch (Exception e) {  
            System.out.println("transBean2Map Error " + e);  
        }  
  
        return map;  
  
    } 
    

}  