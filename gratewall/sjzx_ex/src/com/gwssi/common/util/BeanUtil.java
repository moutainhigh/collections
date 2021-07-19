package com.gwssi.common.util;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 对类的方法和变量进行控制处理
 * @author lifx
 *
 */
public class BeanUtil {
	/**
	 * 复制Bean1对象的属性到Bean2对象 复制所有Null与非Null所有存在的属性
	 * 不复制set方法中多值属性
	 * @param obj1 复制源对象
	 * @param obj2 复制目标对象
	 * @throws Exception
	 */
	public static void copyProperties(Object obj1, Object obj2)
		throws Exception {
		copyProperties(obj1,obj2,0,null);
	}
	
	/**
	 * 复制Bean1对象的属性到Bean2对象 根据Empty参数条件复制所有属性
	 * 不复制set方法中多值属性
	 * @param obj1 复制源对象
	 * @param obj2 复制目标对象
	 * @param emtpy 0 Null与非Null全部复制，1 不复制返回值为Null的属性 2 不复制返回值为Null、""、0、0.0、空Map、空List的属性 
	 * @throws Exception
	 */
	public static void copyProperties(Object obj1, Object obj2,int empty)
		throws Exception {
		copyProperties(obj1,obj2,empty,null);
	}
	
	/**
	 * 复制Bean1对象的属性到Bean2对象 只复制Methods存在的名称属性
	 * 不复制set方法中多值属性
	 * @param obj1 复制源对象
	 * @param obj2 复制目标对象
	 * @param emtpy 0 Null与非Null全部复制，1 不复制返回值为Null的属性 2 不复制返回值为Null、""、0、0.0的属性 
	 * @param methods 需要复制的属性名称数组，不在此对象其中的属性则不被复制，如果此对象为空那么全部复制
	 * @throws Exception
	 */
	public static void copyProperties(Object obj1, Object obj2,int empty,List methods)
		throws Exception {
		if(obj1 == null || obj2 == null){
			throw new IllegalArgumentException("parameter is null!");
		}
		Method[] method1 = obj1.getClass().getMethods();
		Method[] method2 = obj2.getClass().getMethods();
		String methodName1;
		String methodFix1;
		String methodName2;
		String methodFix2;
		for (int i = 0; i < method1.length; i++) {
			methodName1 = method1[i].getName();
			methodFix1 = methodName1.substring(3, methodName1.length());
			if (methodName1.startsWith("get") && (methods == null || methods.isEmpty() 
					|| methods.contains(methodName1))) {
				if (method1[i].getParameterTypes().length > 0){
					continue;
				}
				for (int j = 0; j < method2.length; j++) {
					methodName2 = method2[j].getName();
					methodFix2 = methodName2.substring(3, methodName2.length());
					if (methodName2.startsWith("set")) {
						if (methodFix2.equals(methodFix1)) {
							if (method2[j].getParameterTypes().length > 1){
								continue;
							}
							Object[] args1 = new Object[0];
							Object[] args2 = new Object[1];
							args2[0] = method1[i].invoke(obj1, args1);
							if(empty == 1 && args2[0] == null){
								continue;
							}
							if(empty == 2 && (args2[0] == null || args2[0].equals("") || args2[0].equals(new Integer(0))
									|| args2[0].equals(new Long(0)) || args2[0].equals(new Double(0))
									|| args2[0].equals(new Float(0)) || args2[0].equals(new HashMap()) || args2[0].equals(new ArrayList()))){
								continue;
							}
							method2[j].invoke(obj2, args2);
						}
					}
				}
			}
		}
	}
	
	/**
	 * set属性，根据方法名称和值保存到对象中
	 * @param obj
	 * @param methodName
	 * @param value
	 * @throws Exception
	 */
	public static void setValue(Object obj,String methodName, Object value)
			throws Exception {
		if(obj == null || methodName == null){
			throw new IllegalArgumentException("parameter is null!");
		}
		Method[] method = obj.getClass().getMethods();
		for (int i = 0; i < method.length; i++) {
			if (method[i].getName().startsWith("set") 
					&& method[i].getName().endsWith(methodName)) {
				method[i].invoke(obj, new Object[]{formatValue(value,method[i].getParameterTypes()[0])});
				break;
			}
		}
	}
	
	/**
	 * 根据Class类型转换value对象，
	 * @param value
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static Object formatValue(Object value,Class type)
		throws Exception{
		if (value == null){
			return null;
		}
		Object arg = null;
		if (type.equals(Integer.class)){
			arg = new Integer(String.valueOf(value));
    	}else if (type.equals(Long.class)){
    		arg = new Long(String.valueOf(value));
    	}else if (type.equals(Double.class)){
    		arg = new Double(String.valueOf(value));
    	}else if (type.equals(Float.class)){
    		arg = new Float(String.valueOf(value));
    	}else if(type.equals(Date.class)){
    		try{
    			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.m");
    			arg = df.parse(String.valueOf(value));
    		}catch(Exception e1){
    			try{
    				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    				arg = df.parse(String.valueOf(value));
    			}catch(Exception e2){
    				try{
        				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        				arg = df.parse(String.valueOf(value));
        			}catch(Exception e3){
        				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        				arg = df.parse(String.valueOf(value));
        			}
    			}
    		}
    	}else if (type.isArray() && type.equals(Integer[].class)){
    		Object[] intValues = (Object[])value;
    		Integer[] ints = new Integer[intValues.length];
    		for (int j = 0 ; j < intValues.length ; j ++){
    			ints[j] = new Integer(String.valueOf(intValues[j]));
    		}
    		arg = intValues;
    	}else if (type.isArray() && type.equals(String[].class)){
    		Object[] strValues = (Object[])value;
    		String[] ints = new String[strValues.length];
    		for (int j = 0 ; j < strValues.length ; j ++){
    			ints[j] = String.valueOf(strValues[j]);
    		}
    		arg = strValues;
    	}else{
    		arg = value;
    	}
		return arg;
	}
	
	/**
	 * get属性，根据方法名称取得到对象中数据
	 * @param obj
	 * @param methodName
	 * @param value
	 * @throws Exception
	 */
	public static Object getValue(Object obj,String methodName)
			throws Exception {
		if(obj == null || methodName == null){
			throw new IllegalArgumentException("parameter is null!");
		}
		Method[] method = obj.getClass().getMethods();
		for (int i = 0; i < method.length; i++) {
			if (method[i].getName().startsWith("get") 
					&& method[i].getName().endsWith(methodName)) {
				return method[i].invoke(obj, new Object[0]);
			}
		}
		return null;
	}
	
	/**
	 * 取得一个Class的所有参数信息
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public static Map getParams(Class cls)
			throws Exception{
		Method[] methods = cls.getMethods();
		Map setMethods = new HashMap();
		Map getMethods = new HashMap();
		for (int i = 0; i < methods.length;i++){
			if (methods[i].getName().startsWith("set")){
				setMethods.put(methods[i].getName().substring(3),methods[i].getParameterTypes()[0]);
			}else if (methods[i].getName().startsWith("get")){
				getMethods.put(methods[i].getName().substring(3),methods[i].getReturnType());
			}
		}
		List list = new ArrayList();
		for (Iterator iter = getMethods.keySet().iterator();iter.hasNext();){
			String setKey = (String)iter.next();
			if (setMethods.get(setKey) == null){
				list.add(setKey);
			}
		}
		for (Iterator iter = list.iterator();iter.hasNext();){
			getMethods.remove(iter.next());
		}
		return getMethods;
	}
	
	/**
	 * 调用Object的set属性，如果object == null,根据objectPath得到一个类的实例
	 * @param object
	 * @param objectPath
	 * @param paramName
	 * @param value
	 * @throws Exception
	 */
	public static void setBoValue(Object object,String objectPath,String paramName,Object value)
			throws Exception{
		if (paramName != null && !paramName.equals("")){
			if (object == null){
				object = SysClassLoader.newInstance(objectPath);
			}
			setValue(object,"set"+paramName,value);
		}
	}
	
	/**
	 * 调用一个方法，根据方法名称，传入参数
	 * @param object
	 * @param methodName
	 * @param params
	 * @return
	 */
	public static Object invoke(Object object,String methodName,Object[] params)
			throws Exception{
		if (object == null || methodName == null || methodName.equals("")){
			throw new IllegalArgumentException("parameter is null!");
		}
		Method[] method = object.getClass().getMethods();
		for (int i = 0; i < method.length; i++) {
			if (method[i].getName().equals(methodName) && method[i].getParameterTypes().length == params.length){
				boolean pass = false;
				for (int j = 0 ; j < params.length ; j ++){
					if (params[j] != null){
						Class obj = params[j].getClass();
						while (obj != null){
						   if (obj.getName().equals(method[i].getParameterTypes()[j].getName())){
							   pass = true;
							   break;
						   }
						   Class[] ifs = obj.getInterfaces();
						   for (int x = 0 ; x < ifs.length ; x ++){
							   if (ifs[x].getName().equals(method[i].getParameterTypes()[j].getName())){
								   pass = true;
								   break;
							   }
						   }
						   
						   obj = obj.getSuperclass();
						}
					}
				}
				if (pass){
					return method[i].invoke(object, params);
				}
			}
		}
		return null;
	}
	
}
