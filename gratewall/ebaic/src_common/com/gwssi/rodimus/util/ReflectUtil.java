package com.gwssi.rodimus.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.gwssi.optimus.core.reflect.BeanMeta;
import com.gwssi.optimus.core.reflect.FieldMeta;
import com.gwssi.rodimus.exception.RodimusException;


//反射实体工具类，里面将对像的所用的字段全部得到
public class ReflectUtil {
	
	protected static Logger log = Logger.getLogger(ReflectUtil.class);
	
	/**
	 * BeanMeta Cache
	 */
	private static ConcurrentHashMap<Class<?>, BeanMeta> beanMetaCache = 
            new ConcurrentHashMap<Class<?>, BeanMeta>();
    
	/**
	 * 
	 * @param object
	 * @param propName
	 * @param value
	 * @return
	 */
	public static Object setProperty(Object object, String propName,Object value) {
		if(StringUtil.isBlank(propName)){
			throw new RodimusException("propName cannot be null.");
		}
		if (object instanceof Map) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			Map<String, Object> map = (Map) object;
			return map.put(propName, value);
		}
		BeanMeta beanMeta = getBeanMeta(object.getClass());
		MethodAccess methodAccess = beanMeta.getMethodAccess();
		FieldMeta fieldMeta = beanMeta.getFieldMeta(propName);
		if (fieldMeta == null){
			return null;
		}
		Object ret = methodAccess.invoke(object, fieldMeta.getSetMethodIndex(), value);
		return ret;
	}
	/**
	 * 
	 * @param clz
	 * @return
	 */
	private static BeanMeta getBeanMeta(Class<?> clz) {
		BeanMeta beanMeta = beanMetaCache.get(clz);
		if (beanMeta == null) {
			beanMeta = com.gwssi.optimus.core.reflect.ReflectUtil.parseBeanMeta(clz);
			beanMetaCache.put(clz, beanMeta);
		}
		return beanMeta;
	}
	
	private static Object operate(Object obj, String fieldName, Object fieldVal, String type) {
		Object ret = null;
		try {
			// 获得对象类型
			Class<? extends Object> classType = obj.getClass();
			// 获得对象的所有属性
			Field fields[] = classType.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				if (field.getName().equals(fieldName)) {
					
					String firstLetter = fieldName.substring(0, 1).toUpperCase(); // 获得和属性对应的getXXX()方法的名字
					if ("set".equals(type)) {
						String setMethodName = "set" + firstLetter + fieldName.substring(1); // 获得和属性对应的getXXX()方法
						Method setMethod = classType.getMethod(setMethodName, new Class[] { field.getType() }); // 调用原对象的getXXX()方法
						ret = setMethod.invoke(obj, new Object[] { fieldVal });
					}
					if ("get".equals(type)) {
						String getMethodName = "get" + firstLetter + fieldName.substring(1); // 获得和属性对应的setXXX()方法的名字
						Method getMethod = classType.getMethod(getMethodName, new Class[] {});
						ret = getMethod.invoke(obj, new Object[] {});
					}
					return ret;
				}
			}
		} catch (Exception e) {
//			log.warn("reflect error:" + fieldName, e);
		}
		return ret;
	}

	public static Object getVal(Object obj, String fieldName) {
		return operate(obj, fieldName, null, "get");
	}

	public static void setVal(Object obj, String fieldName, Object fieldVal) {
		operate(obj, fieldName, fieldVal, "set");
	}

	private static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes) {
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				// superClass.getMethod(methodName, parameterTypes);
				return superClass.getDeclaredMethod(methodName, parameterTypes);
			} catch (NoSuchMethodException e) {
				// Method 不在当前类定义, 继续向上转型
			}
		}

		return null;
	}

	private static void makeAccessible(Field field) {
		if (!Modifier.isPublic(field.getModifiers())) {
			field.setAccessible(true);
		}
	}

	private static Field getDeclaredField(Object object, String filedName) {
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(filedName);
			} catch (NoSuchFieldException e) {
				// Field 不在当前类定义, 继续向上转型
			}
		}
		return null;
	}

	public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes, Object[] parameters)
			throws InvocationTargetException {
		Method method = getDeclaredMethod(object, methodName, parameterTypes);

		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + object + "]");
		}

		method.setAccessible(true);

		try {
			return method.invoke(object, parameters);
		} catch (IllegalAccessException e) {

		}

		return null;
	}

	public static void setFieldValue(Object object, String fieldName, Object value) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null)
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");

		makeAccessible(field);

		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static Object getFieldValue(Object object, String fieldName) {
		Field field = getDeclaredField(object, fieldName);
		if (field == null)
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");

		makeAccessible(field);

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return result;
	}

}