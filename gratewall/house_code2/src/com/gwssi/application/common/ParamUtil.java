package com.gwssi.application.common;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ParamUtil {

	public static HashMap beanToMap(final Object beanObj) {
		HashMap map = new HashMap();
		return beanToMap(beanObj, map, false, 0);
	}

	/**
	 * 将bean中get方法取得的属性名字和值设置到map当中，默认日期模式"yyyy-MM-dd"
	 * 
	 * @param beanObj
	 *            bean对象
	 * @param map
	 *            map对象
	 * @param processNull
	 *            是否处理bean返回的空值
	 * @return 设置好值的map对象
	 */
	public static HashMap beanToMap(final Object beanObj, final HashMap map,
			final boolean processNull) {
		return beanToMap(beanObj, map, processNull, 0);
	}

	/**
	 * 将bean中get方法取得的属性名字和值设置到map当中
	 * 
	 * @param beanObj
	 *            bean对象
	 * @param map
	 *            map对象
	 * @param processNull
	 *            是否处理bean返回的空值
	 * @param datePattern
	 *            日期格式化模式（参见DateUtil中的dateFormat定义的取值范围）
	 * @return 设置好值的map对象
	 */
	public static HashMap beanToMap(final Object beanObj, final HashMap map,
			final boolean processNull, final int datePattern) {
		return beanToMap(beanObj, map, processNull, datePattern, -1);
	}

	/**
	 * 将bean中get方法取得的属性名字和值设置到map当中
	 * 
	 * @param beanObj
	 *            bean对象
	 * @param map
	 *            map对象
	 * @param processNull
	 *            是否处理bean返回的空值
	 * @param datePattern
	 *            日期格式化模式（参见DateUtil中的dateFormat定义的取值范围）
	 * @param scale
	 *            浮点数值保留小数位数
	 * @return 设置好值的map对象
	 */
	public static HashMap beanToMap(final Object beanObj, final HashMap map,
			final boolean processNull, final int datePattern, final int scale) {
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
			final HashMap result) {
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
						// 处理复合主键值
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
						// 处理一般属性值
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
	 * 把Map中的数据赋值到JavaBean对象
	 * 
	 * @param request
	 *            请求参数
	 * @param bean
	 *            要赋值的JavaBean对象
	 * @param processNull
	 *            是否处理null，true:处理，false:不处理,add by GM
	 * @return 赋值后的JavaBean对象，同传入的bean
	 */
	public static Object mapToBean(Map map, Object bean, boolean processNull) {
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
	 *            是否处理null，true:处理，false:不处理,add by GM
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
			// remove by liaoxl 不需要在这里判断,否则空字符串会不更新
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
	 *            是否处理null，true:处理，false:不处理,add by GM @
	 */
	private static void setMethodValue(Object obj, Method method,
			Object valueObj, boolean processNull) {
		// add
		// para
		// GM，是否处理空
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
						} else if (cls == Float.class) { // 支持Float类型 by
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
							// modify by liaoxl 支持两种格式的时间yyyy-MM-dd和yyyy-MM-dd
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
							// 增加对Map的处理 add by zhangyj
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
					// 若为处理空，则写入空值
					method.invoke(obj, new Object[] { setValue });
				}
			}
		} catch (Exception e) {
		}
	}

	private static String objectToString(Object valueObj) {
		if (valueObj instanceof String[]) {
			String[] array = (String[]) valueObj;
			if (array.length == 0) {
				return "";
			} else {
				/**
				 * 此处取数组第一个元素主要是支持requestToBean方法，
				 * requestToBean方法通过request.getParameterMap()方法获取的Map内保存的都是字符串数组
				 * 一般情况下数组内应该只有一个值，但如果叶面上同名的field超过1个也可能超过一个，
				 * 此时假设页面上的同名field实际上是同一个，仅取其中一个即可，但对于checkbox以及多选select则会出现数据错误
				 * 因此不能使用requestToBean以及requestToVO
				 * ，requestToDAO方法获取checkbox以及多选select的值，
				 * 如果需要则需要在前台使用javascript转换为单个字符串，并用隐藏域提交。
				 */
				return array[0];
			}
		} else {
			return valueObj.toString();
		}
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {
		return (str == null || "".equals(str));
	}

	/**
	 * 判断字符串是否JSON格式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isJSON(String str) {
		if ("".equals(str) || str == null)
			return false;
		if (str.indexOf("{") != -1 && str.indexOf("}") != -1)
			return true;
		return false;
	}

	/**
	 * obj转字符串，为null时返回“”
	 * 
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		return (obj == null) ? "" : obj.toString();
	}

	/**
	 * 从map中取key对应的字符串，为null时返回“”
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	public static String getMapStr(Map map, String key) {
		String value = "";
		if (map != null && key != null && !"".equals(key)) {
			if (map.containsKey(key) && map.get(key) != null)
				value = map.get(key).toString();
		}
		return value;
	}
}
