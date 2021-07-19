package com.gwssi.common.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * 
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�ParamUtil �������������������� �����ˣ�lizheng ����ʱ�䣺Apr 9, 2013
 * 11:00:43 AM �޸��ˣ�lizheng �޸�ʱ�䣺Apr 9, 2013 11:00:43 AM �޸ı�ע��
 * 
 * @version
 * 
 */
public class ParamUtil
{

	public static HashMap beanToMap(final Object beanObj)
	{
		HashMap map = new HashMap();
		return beanToMap(beanObj, map, false, 0);
	}

	/**
	 * ��bean��get����ȡ�õ��������ֺ�ֵ���õ�map���У�Ĭ������ģʽ"yyyy-MM-dd"
	 * 
	 * @param beanObj
	 *            bean����
	 * @param map
	 *            map����
	 * @param processNull
	 *            �Ƿ���bean���صĿ�ֵ
	 * @return ���ú�ֵ��map����
	 */
	public static HashMap beanToMap(final Object beanObj, final HashMap map,
			final boolean processNull)
	{
		return beanToMap(beanObj, map, processNull, 0);
	}

	/**
	 * ��bean��get����ȡ�õ��������ֺ�ֵ���õ�map����
	 * 
	 * @param beanObj
	 *            bean����
	 * @param map
	 *            map����
	 * @param processNull
	 *            �Ƿ���bean���صĿ�ֵ
	 * @param datePattern
	 *            ���ڸ�ʽ��ģʽ���μ�DateUtil�е�dateFormat�����ȡֵ��Χ��
	 * @return ���ú�ֵ��map����
	 */
	public static HashMap beanToMap(final Object beanObj, final HashMap map,
			final boolean processNull, final int datePattern)
	{
		return beanToMap(beanObj, map, processNull, datePattern, -1);
	}

	/**
	 * ��bean��get����ȡ�õ��������ֺ�ֵ���õ�map����
	 * 
	 * @param beanObj
	 *            bean����
	 * @param map
	 *            map����
	 * @param processNull
	 *            �Ƿ���bean���صĿ�ֵ
	 * @param datePattern
	 *            ���ڸ�ʽ��ģʽ���μ�DateUtil�е�dateFormat�����ȡֵ��Χ��
	 * @param scale
	 *            ������ֵ����С��λ��
	 * @return ���ú�ֵ��map����
	 */
	public static HashMap beanToMap(final Object beanObj, final HashMap map,
			final boolean processNull, final int datePattern, final int scale)
	{
		HashMap result = map;
		Method[] methods = beanObj.getClass().getDeclaredMethods();
		Method method;
		for (int i = 0; i < methods.length; i++) {
			method = methods[i];
			String methodName = method.getName();
			fillFieldToMap(method, beanObj, processNull, datePattern, scale,
					result);
		}
		return result;
	}

	private static void fillFieldToMap(final Method m, final Object obj,
			final boolean processNull, final int datePattern, final int scale,
			final HashMap result)
	{
		String methodName = m.getName();
		try {
			if (methodName.startsWith("get") && methodName.length() > 3
					&& m.getParameterTypes().length == 0) {
				String key = String.valueOf(Character.toLowerCase(methodName
						.charAt(3)));
				if (methodName.length() > 4)
					key += methodName.substring(4);
				Object valueObj = m.invoke(obj, new Object[0]);
				if (methodName.equals("getCompositeId")) {
					if (valueObj != null) {
						// ����������ֵ
						Method[] pkMethods = valueObj.getClass()
								.getDeclaredMethods();
						Method pkMethod;
						// String pkMethodName;
						for (int j = 0; j < pkMethods.length; j++) {
							pkMethod = pkMethods[j];
							if (pkMethod.getModifiers() == Modifier.PUBLIC)
								fillFieldToMap(pkMethod, valueObj, processNull,
										datePattern, scale, result);
						}
					}
				} else {
					if (valueObj != null) {
						// ����һ������ֵ
						String value = null;
						if (valueObj instanceof Date) {
							value = DateUtil.dateToString((Date) valueObj,
									datePattern);
						} else if (valueObj instanceof Timestamp) {
							Calendar calendar = DateUtil
									.convSqlTimestampToUtilCalendar((Timestamp) valueObj);
							value = DateUtil.toDateStr(calendar, datePattern);
						} else if (valueObj instanceof Calendar) {
							value = DateUtil.toDateStr((Calendar) valueObj,
									datePattern);
						} else if (valueObj instanceof BigDecimal && scale > 0) {
							BigDecimal bd = (BigDecimal) valueObj;
							bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);
							value = bd.toString();
						} else {
							value = valueObj.toString();
						}
						if (processNull || value != null)
							result.put(key, value);
					} else if (processNull) {
						result.put(key, null);
					}
				}
			}
		} catch (Exception e) {
		}
	}

	/**
	 * ��Map�е����ݸ�ֵ��JavaBean����
	 * 
	 * @param request
	 *            �������
	 * @param bean
	 *            Ҫ��ֵ��JavaBean����
	 * @param processNull
	 *            �Ƿ���null��true:����false:������,add by GM
	 * @return ��ֵ���JavaBean����ͬ�����bean
	 */
	public static Object mapToBean(Map map, Object bean, boolean processNull)
	{
		Method[] methods = bean.getClass().getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			String methodName = method.getName();
			fillField(map, method, bean, processNull);
		}
		return bean;
	}

	/**
	 * 
	 * @param map
	 * @param method
	 * @param bean
	 * @param processNull
	 *            �Ƿ���null��true:����false:������,add by GM
	 */
	private static void fillField(Map map, Method method, Object bean,
			boolean processNull)

	{
		String methodName = method.getName();
		if (methodName.startsWith("set")) {
			String fName = methodName.substring(3);
			Object valueObj = null;
			Iterator keyIte = map.keySet().iterator();
			while (keyIte.hasNext()) {
				Object key = keyIte.next();
				if (key instanceof String
						&& fName.equalsIgnoreCase((String) key)) {
					valueObj = map.get(key);
				}
			}
			// remove by liaoxl ����Ҫ�������ж�,������ַ����᲻����
			// if (valueObj != null && valueObj.equals("")) {
			// valueObj = null;
			// }
			boolean porcess = true;
			if (valueObj == null && !processNull) {
				porcess = false;
			}
			if (porcess)
				setMethodValue(bean, method, valueObj, processNull);
		}
	}

	/**
	 * 
	 * @param obj
	 * @param method
	 * @param valueObj
	 * @param processNull
	 * �Ƿ���null��true:����false:������,add by GM @
	 */
	private static void setMethodValue(Object obj, Method method,
			Object valueObj, boolean processNull)
	{
		// add
		// para
		// GM���Ƿ����
		try {
			boolean update = true;
			Object setValue = null;
			if (valueObj != null) {
				Class cls = method.getParameterTypes()[0];
				if (cls.equals(valueObj.getClass())) {
					setValue = valueObj;
				} else {
					String valueStr = objectToString(valueObj);
					if (!valueStr.trim().equals("")) {
						if (cls == String.class) {
							setValue = (String) valueStr;
						} else if (cls == Integer.class) {
							try {
								setValue = Integer.valueOf(valueStr);
							} catch (NumberFormatException e) {
								setValue = null;
							}
						} else if (cls == Float.class) { // ֧��Float���� by
							// zhangyu
							try {
								setValue = Float.valueOf(valueStr);
							} catch (NumberFormatException e) {
								setValue = null;
							}
						} else if (cls == BigDecimal.class) {
							try {
								setValue = new BigDecimal(valueStr);
							} catch (NumberFormatException e) {
								setValue = null;
							}
						} else if (cls == Calendar.class) {
							setValue = DateUtil.parseDate(valueStr);
						} else if (cls == Date.class) {
							setValue = DateUtil.parseDate(valueStr);
						} else if (cls == Timestamp.class) {
							// modify by liaoxl ֧�����ָ�ʽ��ʱ��yyyy-MM-dd��yyyy-MM-dd
							// HH:mm:ss
							setValue = DateUtil.parseTimestamp(valueStr);
						} else if (cls == Long.class) {
							try {
								setValue = Long.valueOf(valueStr);
							} catch (NumberFormatException e) {
								setValue = null;
							}
						} else if (cls == Double.class) {
							try {
								setValue = Double.valueOf(valueStr);
							} catch (NumberFormatException e) {
								setValue = null;
							}
							// ���Ӷ�Map�Ĵ��� add by zhangyj
						} else if (cls == Map.class) {
							setValue = valueObj;
						} else {
							update = false;
						}
					} else
						setValue = null;
				}
			}

			if (update) {
				if (valueObj != null || processNull) {
					// ��Ϊ����գ���д���ֵ
					method.invoke(obj, new Object[] { setValue });
				}
			}
		} catch (Exception e) {
		}
	}

	private static String objectToString(Object valueObj)
	{
		if (valueObj instanceof String[]) {
			String[] array = (String[]) valueObj;
			if (array.length == 0) {
				return "";
			} else {
				/**
				 * �˴�ȡ�����һ��Ԫ����Ҫ��֧��requestToBean������
				 * requestToBean����ͨ��request.getParameterMap()������ȡ��Map�ڱ���Ķ����ַ�������
				 * һ�������������Ӧ��ֻ��һ��ֵ�������Ҷ����ͬ����field����1��Ҳ���ܳ���һ����
				 * ��ʱ����ҳ���ϵ�ͬ��fieldʵ������ͬһ������ȡ����һ�����ɣ�������checkbox�Լ���ѡselect���������ݴ���
				 * ��˲���ʹ��requestToBean�Լ�requestToVO��requestToDAO������ȡcheckbox�Լ���ѡselect��ֵ��
				 * �����Ҫ����Ҫ��ǰ̨ʹ��javascriptת��Ϊ�����ַ����������������ύ��
				 */
				return array[0];
			}
		} else {
			return valueObj.toString();
		}
	}

	/**
	 * �ж��ַ����Ƿ�Ϊ��
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str)
	{
		return (str == null || "".equals(str));
	}

	/**
	 * �ж��ַ����Ƿ�JSON��ʽ
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isJSON(String str)
	{
		if ("".equals(str) || str == null)
			return false;
		if (str.indexOf("{") != -1 && str.indexOf("}") != -1)
			return true;
		return false;
	}

	/**
	 * objת�ַ�����Ϊnullʱ���ء���
	 * 
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj)
	{
		return (obj == null) ? "" : obj.toString();
	}

	/**
	 * ��map��ȡkey��Ӧ���ַ�����Ϊnullʱ���ء���
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	public static String getMapStr(Map map, String key)
	{
		String value = "";
		if (map != null && key != null && !"".equals(key)) {
			if (map.containsKey(key) && map.get(key) != null)
				value = map.get(key).toString();
		}
		return value;
	}
}
