package com.gwssi.optimus.core.reflect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import com.esotericsoftware.reflectasm.MethodAccess;
import com.gwssi.optimus.util.StringUtil;

/**
 * 第三方接口发布中需包含。
 * 
 * 反射工具类。该类利用reflectasm实现，可反射调用对象的指定方法，或者获取对象的成员变量值。
 * 在使用ReflectUtil对某类第一次进行反射时，会将该类进行解析并缓存，因此第一次会比较耗时，
 * 但之后再对该类进行反射时会很快。
 */
public class ReflectUtil {

    @SuppressWarnings("rawtypes")
	private static ConcurrentHashMap<Class, BeanMeta> beanMetaCache = 
            new ConcurrentHashMap<Class, BeanMeta>();
    
    @SuppressWarnings("rawtypes")
	public static void addBeanMeta2Cache(Class type, BeanMeta beanMeta){
        beanMetaCache.put(type, beanMeta);
    }
    
    /**
     * 从缓存中获取指定类的结构信息。若没有，则对该类进行解析并将得到的结构信息缓存。
     * @param type 指定的类
     * @return 类结构信息
     */
    @SuppressWarnings("rawtypes")
	public static BeanMeta getBeanMeta(Class type){
        BeanMeta beanMeta = beanMetaCache.get(type);
        if(beanMeta==null){
            beanMeta = parseBeanMeta(type);
            beanMetaCache.put(type, beanMeta);
        }
        return beanMeta;
    }
    
    /**
     * 反射触发一个对象的指定方法。
     * @param object 目标对象
     * @param methodName 要触发的方法名
     * @param args 传入的参数
     * @return 方法触发后的返回值
     */
    public static Object invoke(Object object, String methodName, Object[] args) {
        BeanMeta beanMeta = getBeanMeta(object.getClass());
        MethodAccess methodAccess = beanMeta.getMethodAccess();
        return methodAccess.invoke(object, methodName, args);
    }
    
    /**
     * 反射获取一个对象的指定成员变量值。
     * @param object 目标对象
     * @param propName 成员变量名称
     * @return 成员变量值
     */
    @SuppressWarnings("rawtypes")
	public static Object getProperty(Object object, String propName){
        if(object instanceof Map){
            Map map = (Map)object;
            return map.get(propName);
        }
        BeanMeta beanMeta = getBeanMeta(object.getClass());
        MethodAccess methodAccess = beanMeta.getMethodAccess();
        FieldMeta fieldMeta = beanMeta.getFieldMeta(propName);
        if(fieldMeta==null)
            return null;
        return methodAccess.invoke(object, fieldMeta.getGetMethodIndex());
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object setProperty(Object object, String propName, Object value){
        if(object instanceof Map){
            Map map = (Map)object;
            return map.put(propName, value);
        }
        BeanMeta beanMeta = getBeanMeta(object.getClass());
        MethodAccess methodAccess = beanMeta.getMethodAccess();
        FieldMeta fieldMeta = beanMeta.getFieldMeta(propName);
        if(fieldMeta==null)
            return null;
        return methodAccess.invoke(object, fieldMeta.getSetMethodIndex(), value);
    }
    
    @SuppressWarnings("rawtypes")
	public static boolean contains(Class type){
        return beanMetaCache.containsKey(type);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static BeanMeta parseBeanMeta(Class type){
        BeanMeta beanMeta = new BeanMeta(); 
        beanMeta.setConstructorAccess(ConstructorAccess.get(type));
        MethodAccess methodAccess = MethodAccess.get(type);
        beanMeta.setMethodAccess(methodAccess);
        List<FieldMeta> fieldMetaList = new ArrayList<FieldMeta>();  
        Method[] methods = type.getDeclaredMethods();
        for(Method method : methods){
            String methodName = method.getName();
            if(!methodName.startsWith("get")){
                continue;
            }
            String getMethodName = methodName;
            String temp = methodName.substring(3);
            String setMethodName = "set" + temp;
            String fieldName = StringUtil.firstLetterLow(temp);
            FieldMeta fieldMeta = new FieldMeta();
            fieldMeta.setFieldName(fieldName);
            fieldMeta.setGetMethodName(getMethodName);
            fieldMeta.setSetMethodName(setMethodName);
            fieldMeta.setType(method.getReturnType());
            fieldMeta.setGetMethodIndex(methodAccess.getIndex(getMethodName));
            fieldMeta.setSetMethodIndex(methodAccess.getIndex(setMethodName));
            fieldMetaList.add(fieldMeta);
            beanMeta.fieldMetaMap.put(fieldName, fieldMeta);
        }
        beanMeta.setFieldMetaList(fieldMetaList);
        return beanMeta;
    }

    @SuppressWarnings("rawtypes")
	public static ConcurrentHashMap<Class, BeanMeta> getBeanMetaCache() {
        return beanMetaCache;
    }

    @SuppressWarnings("rawtypes")
	public static void setBeanMetaCache(ConcurrentHashMap<Class, BeanMeta> beanMetaCache) {
        ReflectUtil.beanMetaCache = beanMetaCache;
    }
    
//    @SuppressWarnings("static-access")
//	public static void main(String[] args) {
//    	List<CpWkStagespayBO> list = new ArrayList<CpWkStagespayBO>();
//    	CpWkStagespayBO stagespayBO = new CpWkStagespayBO();
//    	stagespayBO.setConForm("conFrom...111......");
//    	list.add(stagespayBO);
//    	stagespayBO = new CpWkStagespayBO();
//    	stagespayBO.setConForm("conFrom..222.......");
//    	list.add(stagespayBO);
//    	
//    	
//		CpApiInvestorBO investorBO = new CpApiInvestorBO();
//		ReflectUtil util = new ReflectUtil();
//		util.setProperty(investorBO, "stagePay",list);
//		System.out.println(investorBO.getStagePay());
//	}

}
