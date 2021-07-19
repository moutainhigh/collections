package com.gwssi.rodimus.util;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.ParamException;

/**
 * <p>
 * 功能描述:参数处理工具类
 * </p>
 */
public class ParamUtil {

	protected final static Logger logger = Logger.getLogger(ParamUtil.class);
	
	/**
	 * @param pname
	 * @param isRequired
	 * @return
	 * @throws OptimusException 
	 */
	public static String get(String pname,boolean isRequired){
		OptimusRequest request = RequestUtil.getOptimusRequest();
		String ret = request.getParameter(pname);
		if(StringUtils.isEmpty(ret)){
			ret = StringUtil.safe2String(request.getAttr(pname));
		}
		if(isRequired && StringUtil.isBlank(ret)){
			throw new ParamException("参数"+pname+"不能为空。");
		}
		return ret;
	}
	
	public static String get(OptimusRequest request, String pname,boolean isRequired){
		String ret = request.getParameter(pname);
		if(StringUtils.isEmpty(ret)){
			ret = StringUtil.safe2String(request.getAttr(pname));
		}
		if(isRequired && StringUtil.isBlank(ret)){
			throw new ParamException("参数"+pname+"不能为空。");
		}
		return ret;
	}
	/**
	 * 
	 * @param pname
	 * @return
	 * @throws OptimusException 
	 */
	public static String get(String pname){
		String ret = get(pname,true); 
		return ret;
	}
	
//	
//	public static Map<String,String> dictTransBo(Object data,String...valueKeys) throws OptimusException{
//		Map<String,String> ret = BeanUtil.beanToMap(data);
//		String[] arrStrings = null;
//		String mapKey = null,dictKey = null;
//		String mapValue = null;
//		DicData dicData = null;
//		for(String valueKey : valueKeys){
//			if(valueKey==null){
//				continue ;
//			}
//			arrStrings = valueKey.split(",") ;
//			
//			
//			if(arrStrings==null || arrStrings.length<2){
//				continue ;
//			}
//			
//			if(arrStrings.length==2){
//				mapKey =  arrStrings[0];
//				dictKey = arrStrings[1];
//				if(data!=null){
//					mapValue = ParamUtil.toString(ReflectUtil.getProperty(data, mapKey));
//				}else{
//					mapValue = null;
//				}
//				if(mapValue==null){
//					//ret.put(mapKey,null);
//				}else{
//					dicData = DictionaryManager.getData(dictKey);
//					ret.put(mapKey, dicData.getText(mapValue));
//				}
//			}else if(arrStrings.length>2){
//				// 对于码表中不存在的字段
//				mapKey =  arrStrings[0];
//				if(data!=null){
//					mapValue = ParamUtil.toString(ReflectUtil.getProperty(data, mapKey));
//				}else{
//					mapValue = null;
//				}
//				if (!StringUtils.isEmpty(mapValue)){
//					for(int i=1,length=arrStrings.length;i<length-1;i++){
//						if(mapValue.equals(arrStrings[i])){
//							ret.put(mapKey, arrStrings[i+1]);
//							break;
//						}
//					}
//				}
//			}
//		}
//		return ret;
//	}
//	/**
//	 * 对Map中的值根据码表转码。
//	 * 
//	 * <pre>
//	 * Example： 
//	 * dataMap = dictTrans(dataMap,"positonCode,CA16","state,WDDJ01")
//	 * * dataMap = dictTrans(dataMap,"positonCode,0,是,1否")
//	 * 
//	 * </pre>
//	 * 
//	 * @param data
//	 * @param valueKeys
//	 * @return
//	 * @throws OptimusException
//	 */
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	public static Map<Object,Object> dictTrans(Map<Object,Object> data,String...valueKeys) throws OptimusException{
//		if(valueKeys!=null && valueKeys.length>0){
//			
//			String[] arrStrings = null ;
//			
//			String mapKey = null;
//			Object mapValue = null;
//			Pair mapPairValue = null;
//			
//			DicData dicData = null;
//			String dictKey = null;
//			String dictValue = null;
//			
//			for(String valueKey : valueKeys){
//				
//				if(valueKey==null){
//					continue ;
//				}
//				
//				arrStrings = valueKey.split(",") ;
//				
//				
//				if(arrStrings==null || arrStrings.length<2){
//					continue ;
//				}
//				
//				if(arrStrings.length==2){
//					mapKey =  arrStrings[0];
//					dictKey = arrStrings[1];
//					
//					mapValue = data.get(mapKey);
//					
//					if (mapValue!=null&&mapValue instanceof Pair){
//						mapPairValue = (Pair)mapValue;
//						
//						dicData = DictionaryManager.getData(dictKey);
//						mapPairValue.first = dictValue = dicData.getText(ParamUtil.toString(mapPairValue.first));
//						mapPairValue.second = dictValue = dicData.getText(ParamUtil.toString(mapPairValue.second));
//						
//						data.put(mapKey, mapPairValue);
//					}else if(mapValue!=null){
//						dictValue = DictionaryManager.getData(dictKey).getText(mapValue.toString());
//						data.put(mapKey, dictValue);
//					}
//					
//				}else if(arrStrings.length>2){
//					// 对于码表中不存在的字段
//					mapKey =  arrStrings[0];
//					
//					
//					mapValue = data.get(mapKey);
//					
//					if (mapValue!=null&& mapValue instanceof Pair){
//						mapPairValue = (Pair)mapValue;
//						String fir=ParamUtil.toString(mapPairValue.first);
//						String sec=ParamUtil.toString(mapPairValue.second);
//						for(int i=1,length=arrStrings.length;i<length-1;i++){
//							
//							if(!StringUtils.isEmpty(fir)){
//								if(fir.equals(arrStrings[i])){
//									mapPairValue.first=arrStrings[i+1];
//								}
//							}
//							if(!StringUtils.isEmpty(sec)){
//								if(sec.equals(arrStrings[i])){
//									mapPairValue.second=arrStrings[i+1];
//								}
//							}
//						}
//						
//						data.put(mapKey, mapPairValue);
//					}else if(mapValue!=null){
//						String beforevalue=ParamUtil.toString(mapValue);
//						for(int i=1,length=arrStrings.length;i<length;i++){
//							if(!StringUtils.isEmpty(beforevalue)){
//								if(beforevalue.equals(arrStrings[i])){
//									dictValue=arrStrings[i+1];
//									break;
//								}
//							}
//						}
//						
//						data.put(mapKey, dictValue);
//					}
//				}
//			} 
//		}
//		return data;
//	}
//	
//	/**
//	 * 
//	 * liuhailong
//	 * @param o
//	 * @return
//	 */
//	public static int objectToInt(Object o){
//		int ret = objectToInt(o, 0);
//		return ret;
//	}
//	/**
//	 * liuhailong
//	 * @param o
//	 * @param defaultValue
//	 * @return
//	 */
//	public static int objectToInt(Object o,int defaultValue){
//		if(o==null){
//			return defaultValue;
//		}
//		try{
//			String string = o.toString();
//			int ret = Integer.parseInt(string);
//			return ret;
//		}catch(Throwable e){
//			return defaultValue;
//		}
//	}
//	
//	
//	public static HashMap<Object, Object> beanToMap(final Object beanObj)
//			throws Exception {
//		HashMap<Object, Object> map = new HashMap<Object, Object>();
//		return beanToMap(beanObj, map, false, 0);
//	}
//
//	public static HashMap<Object, Object> beanToMap(final Object beanObj,
//			int dataFormatType) throws Exception {
//		HashMap<Object, Object> map = new HashMap<Object, Object>();
//		return beanToMap(beanObj, map, false, dataFormatType);
//	}
//
//	/**
//	 * 将bean中get方法取得的属性名字和值设置到map当中，默认日期模式"yyyy-MM-dd"
//	 * 
//	 * @param beanObj
//	 *            bean对象
//	 * @param map
//	 *            map对象
//	 * @param processNull
//	 *            是否处理bean返回的空值
//	 * @return 设置好值的map对象
//	 */
//	public static HashMap<Object, Object> beanToMap(final Object beanObj,
//			final HashMap<Object, Object> map, final boolean processNull)
//			throws Exception {
//		return beanToMap(beanObj, map, processNull, 0);
//	}
//
//	/**
//	 * 将bean中get方法取得的属性名字和值设置到map当中
//	 * 
//	 * @param beanObj
//	 *            bean对象
//	 * @param map
//	 *            map对象
//	 * @param processNull
//	 *            是否处理bean返回的空值
//	 * @param datePattern
//	 *            日期格式化模式（参见DateUtil中的dateFormat定义的取值范围）
//	 * @return 设置好值的map对象
//	 */
//	public static HashMap<Object, Object> beanToMap(final Object beanObj,
//			final HashMap<Object, Object> map, final boolean processNull,
//			final int datePattern) throws Exception {
//		return beanToMap(beanObj, map, processNull, datePattern, -1);
//	}

//	
//
//	private static void fillFieldToMap(final Method m, final Object obj,
//			final boolean processNull, final int datePattern, final int scale,
//			final HashMap<Object, Object> result) throws Exception {
//		String methodName = m.getName();
//		try {
//			if (methodName.startsWith("get") && methodName.length() > 3
//					&& m.getParameterTypes().length == 0) {
//				String key = String.valueOf(Character.toLowerCase(methodName
//						.charAt(3)));
//				if (methodName.length() > 4)
//					key += methodName.substring(4);
//				Object valueObj = m.invoke(obj, new Object[0]);
//				if (methodName.equals("getCompositeId")) {
//					if (valueObj != null) {
//						// 处理复合主键值
//						Method[] pkMethods = valueObj.getClass()
//								.getDeclaredMethods();
//						Method pkMethod;
//						// String pkMethodName;
//						for (int j = 0; j < pkMethods.length; j++) {
//							pkMethod = pkMethods[j];
//							if (pkMethod.getModifiers() == Modifier.PUBLIC)
//								fillFieldToMap(pkMethod, valueObj, processNull,
//										datePattern, scale, result);
//						}
//					}
//				} else {
//					if (valueObj != null) {
//						// 处理一般属性值
//						String value = null;
//						if (valueObj instanceof Date) {
//							value = DateUtil.dateToString((Date) valueObj,
//									datePattern);
//						} else if (valueObj instanceof Timestamp) {
//							Calendar calendar = DateUtil
//									.convSqlTimestampToUtilCalendar((Timestamp) valueObj);
//							value = DateUtil.toDateStr(calendar, datePattern);
//						} else if (valueObj instanceof Calendar) {
//							value = DateUtil.toDateStr((Calendar) valueObj,
//									datePattern);
//						} else if (valueObj instanceof BigDecimal && scale > 0) {
//							BigDecimal bd = (BigDecimal) valueObj;
//							bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);
//							value = bd.toString();
//						} else {
//							value = valueObj.toString();
//						}
//						if (processNull || value != null)
//							result.put(key, value);
//					} else if (processNull) {
//						result.put(key, null);
//					}
//				}
//			}
//		} catch (Exception e) {
//			throw new Exception("");
//		}
//	}
//
//
//	/**
//	 * 
//	 * @param map
//	 * @param method
//	 * @param bean
//	 * @param processNull
//	 *            是否处理null，true:处理，false:不处理,add by GM
//	 */
//	private static void fillField(Map<Object, Object> map, Method method,
//			Object bean, boolean processNull) throws Exception {
//		String methodName = method.getName();
//		if (methodName.startsWith("set")) {
//			String fName = methodName.substring(3);
//			Object valueObj = null;
//			Iterator<Object> keyIte = map.keySet().iterator();
//			boolean mapHasThisKey = false;
//			while (keyIte.hasNext()) {
//				Object key = keyIte.next();
//				if (key instanceof String
//						&& fName.equalsIgnoreCase((String) key)) {
//					valueObj = map.get(key);
//					mapHasThisKey = true;
//					break;
//				}
//			}
//			boolean porccess = false;
//			if (mapHasThisKey && (valueObj != null || processNull)) {
//				porccess = true;
//			}
//			if (porccess)
//				setMethodValue(bean, method, valueObj, processNull);
//		}
//	}
//
//	/**
//	 * 
//	 * @param obj
//	 * @param method
//	 * @param valueObj
//	 * @param processNull
//	 *            是否处理null，true:处理，false:不处理,add by GM
//	 * @throws Exception
//	 */
//	private static void setMethodValue(Object obj, Method method,
//			Object valueObj, boolean processNull) throws Exception {
//		// add
//		// para
//		// GM，是否处理空
//		try {
//			boolean update = true;
//			Object setValue = null;
//			if (valueObj != null) {
//				Class<?> cls = method.getParameterTypes()[0];
//				if (cls.equals(valueObj.getClass())) {
//					setValue = valueObj;
//				} else {
//					String valueStr = objectToString(valueObj);
//					if (!valueStr.trim().equals("")) {
//						if (cls == String.class) {
//							setValue = (String) valueStr;
//						} else if (cls == Integer.class) {
//							try {
//								setValue = Integer.valueOf(valueStr);
//							} catch (NumberFormatException e) {
//								setValue = null;
//							}
//						} else if (cls == Float.class) { // 支持Float类型 by
//							// zhangyu
//							try {
//								setValue = Float.valueOf(valueStr);
//							} catch (NumberFormatException e) {
//								setValue = null;
//							}
//						} else if (cls == BigDecimal.class) {
//							try {
//								setValue = new BigDecimal(valueStr);
//							} catch (NumberFormatException e) {
//								setValue = null;
//							}
//						} else if (cls == Calendar.class) {
//							setValue = DateUtil.parseDate(valueStr);
//						} else if (cls == Date.class) {
//							setValue = DateUtil.parseDate(valueStr);
//						} else if (cls == Timestamp.class) {
//							// modify by liaoxl 支持两种格式的时间yyyy-MM-dd和yyyy-MM-dd
//							// HH:mm:ss
//							setValue = DateUtil.parseTimestamp(valueStr);
//						} else if (cls == Long.class) {
//							try {
//								setValue = Long.valueOf(valueStr);
//							} catch (NumberFormatException e) {
//								setValue = null;
//							}
//						} else if (cls == Double.class) {
//							try {
//								setValue = Double.valueOf(valueStr);
//							} catch (NumberFormatException e) {
//								setValue = null;
//							}
//							// 增加对Map的处理 add by zhangyj
//						} else if (cls == Map.class) {
//							setValue = valueObj;
//						} else {
//							update = false;
//						}
//					} else
//						setValue = null;
//				}
//			}
//
//			if (update) {
//				if (valueObj != null || processNull) {
//					// 若为处理空，则写入空值
//					method.invoke(obj, new Object[] { setValue });
//				}
//			}
//		} catch (Exception e) {
//			throw new Exception("Error occured when invoke method '"
//					+ method.getName() + "' of '" + obj.getClass().getName()
//					+ "'", e);
//		}
//	}

//
//	/**
//	 * 判断字符串是否JSON格式
//	 * 
//	 * @param str
//	 * @return
//	 */
//	public static boolean isJSON(String str) {
//		if ("".equals(str) || str == null)
//			return false;
//		if (str.indexOf("{") != -1 && str.indexOf("}") != -1)
//			return true;
//		return false;
//	}


	/**
	 * 将字符串转成UTF-8编码(用来解决get请求参数中文乱码)
	 * 
	 * @param str
	 * @return
	 * @throws OptimusException
	 * @throws UnsupportedEncodingException
	 */
	public static String transcoding(String str) throws OptimusException {
		if (str == null) {
			return null;
		}
		try {
			str = new String(str.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new OptimusException(e.getMessage());
		}
		return str;
	}

	/**
	 * 将字符串转成UTF-8编码(用来解决get请求参数中文乱码)
	 * 
	 * @param str
	 * @return
	 * @throws OptimusException
	 * @throws UnsupportedEncodingException
	 */
	public static String transEncoding(String str, HttpServletRequest request)
			throws OptimusException, UnsupportedEncodingException {
		String agent = request.getHeader("user-agent");
		String return_str = "";
		// 设置文件名称
		if (agent.indexOf("Firefox") != -1 || agent.indexOf("Chrome") != -1) {
			return_str = new String(str.getBytes("utf-8"), "ISO8859-1");
		} else {
			return_str = ParamUtil.toUtfString(str);
		}
		return return_str;
	}

	public static String toUtfString(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					//System.out.println(ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 准备参数。
	 * 
	 * @param gid
	 * @return
	 */
	public static Map<String, Object> prepareParams(String gid) {
		String sql = "select r.requisition_id,r.ent_id,r.cp_ent_id,r.name_app_id,r.operation_type,r.app_form,r.app_date,r.agent_reg_no,nvl(r.agent_name,' ') as agent_name, "
				+ " r.censor_result,r.cert_receive_form,r.submit_user_id,r.submit_date,r.state,r.cat_id,r.ent_name,r.timestamp,r.gid,r.version,r.copy_no,r.name_id,r.auth_type,t.* "
				+ " from be_wk_requisition r left join be_wk_ent t on r.ent_id=t.ent_id "
				+ " where r.gid = ?";
		Map<String, Object> ret = DaoUtil.getInstance().queryForRow(sql, gid);
		if(ret==null){
			ret = new HashMap<String, Object>();
		}
		//是否有自然人股东
		sql = " select count(1) from be_wk_investor i where i.inv_type in ('20','35','36','91') and (i.modify_sign is null or i.modify_sign not like '3%') and i.gid=? ";
		String haveInv = StringUtil.safe2String(DaoUtil.getInstance().queryForOneLong(sql, gid));
		ret.put("haveInv", haveInv);
		//是否有非自然人股东
		sql = " select count(1) from be_wk_investor i where i.inv_type not in ('20','35','36','91') and (i.modify_sign is null or i.modify_sign not like '3%') and i.gid=? ";
		String haveCpInv = StringUtil.safe2String(DaoUtil.getInstance().queryForOneLong(sql, gid));
		ret.put("haveCpInv", haveCpInv);
		//住所证明
		sql = " select count(1) from be_wk_upload_file u left join be_wk_file f on u.file_id = f.file_id where u.gid = ? and u.category_id = '5' and u.ref_id = 'dom' and u.state <> '2' ";
		String domUpload = StringUtil.safe2String(DaoUtil.getInstance().queryForOneLong(sql, gid));
		ret.put("domUpload", domUpload);
		//股东确认书
		sql = " select count(1) from be_wk_upload_file u left join be_wk_file f on u.file_id = f.file_id where u.gid = ? and u.category_id = '5' and u.ref_id = 'inv' and u.state <> '2' ";
		String invUpload = StringUtil.safe2String(DaoUtil.getInstance().queryForOneLong(sql, gid));
		ret.put("invUpload", invUpload);
		//法定代表人承诺书
		sql = " select count(1) from be_wk_upload_file u left join be_wk_file f on u.file_id = f.file_id where u.gid = ? and u.category_id = '5' and u.ref_id = 'leg' and u.state <> '2' ";
		String legUpload = StringUtil.safe2String(DaoUtil.getInstance().queryForOneLong(sql, gid));
		ret.put("legUpload", legUpload);
		//代理人承诺书
		sql = " select count(1) from be_wk_upload_file u left join be_wk_file f on u.file_id = f.file_id where u.gid = ? and u.category_id = '5' and u.ref_id = 'apply_name' and u.state <> '2' ";
		String applyUpload = StringUtil.safe2String(DaoUtil.getInstance().queryForOneLong(sql, gid));
		ret.put("applyUpload", applyUpload);
		//是否是代理机构
		sql="select decode(r.agent_name,null,'0','1') as num from be_wk_requisition r where r.gid=?";
		String isAgent=	StringUtil.safe2String(DaoUtil.getInstance().queryForOneString(sql, gid));
		ret.put("isAgent", isAgent);
		
		//是否上传过其他对应的图片，如没有上传则不去调用模板
		sql="select count(*) from be_wk_upload_file u where u.category_id='7' and u.state<>'2' and u.gid=? ";
		String isUpLoadOther =	StringUtil.safe2String(DaoUtil.getInstance().queryForOneLong(sql, gid));
		ret.put("isUpLoadOther", isUpLoadOther);
		//名称是否是自主预查，自主预查没有名称预先核准通知书0--不是
		sql="select count(*) from nm_rs_name n  where n.self_name_sign = '1' and n.name_id=( select name_id from be_wk_requisition r where r.gid=? ) ";
		String isSelfName =	StringUtil.safe2String(DaoUtil.getInstance().queryForOneLong(sql, gid));
		ret.put("isSelfName", isSelfName);

		return ret;
		
	}
	
	/**
	 * 获取请求中所有提交的参数（不包括form中的内容）
	 * 包括： 1、url中?后拼接的所有参数
	 * 		2、ajax请求中data:{}提交的参数对象内容
	 * 		3、DataAdapter提交时的参数对象内容param:{}
	 * @return
	 */
	public static Map<String, Object> getParams(OptimusRequest request){
		Map<String, Object> ret = new HashMap<String, Object>();
		Enumeration<?> pNames=request.getHttpRequest().getParameterNames();
		//获取参数列表
		while(pNames.hasMoreElements()){
			String key = (String)pNames.nextElement();
			ret.put(key, request.getParameter(key));
		}
		//获取DataAdapter提交的post参数
		@SuppressWarnings("rawtypes")
		Map<String, Map> attrSet = request.getAttrSet();
		if(attrSet !=null){
			ret.remove("postData");//去掉原来的postMap
			for(String key:attrSet.keySet()){
				ret.put(key, request.getAttr(key));
			}
			
		}
		
		return ret;
	}
	
//	/**
//	 * 字符串转成int。
//	 * @param str
//	 * @param defaultValue
//	 * @return
//	 */
//	public static int String2Int(String str,int defaultValue){
//		if(StringUtils.isEmpty(str)){
//			return defaultValue;
//		}
//		int ret = defaultValue; 
//		try{
//			ret = Integer.parseInt(str);
//		}catch(Exception e){
//			ret = defaultValue ; 
//		}
//		return ret;
//	}
//	
//	/**
//	 * 字符串转换为BigDecimal。
//	 * 
//	 * @param str
//	 * @param defaultValue
//	 * @return
//	 * @throws OptimusException 
//	 */
//	public static BigDecimal String2BigDecimal(String str,String fieldName) throws OptimusException{
//		if(StringUtils.isEmpty(str)){
//			throw new OptimusException(fieldName+"不能为空。");
//		}	
//		BigDecimal ret = null;	
//		try{
//			ret = new BigDecimal(str.trim());
//		}catch(Exception e){
//			throw new OptimusException(fieldName+"需输入合法的数字。"); 
//		}
//		return ret;
//	}
//	/**
//	 * 字符串转换为BigDecimal。
//	 * 
//	 * @param str
//	 * @param defaultValue
//	 * @return
//	 * @throws OptimusException 
//	 */
//	public static BigDecimal String2BigDecimal(String str,BigDecimal defaultValue) throws OptimusException{
//		if(StringUtils.isEmpty(str)){
//			return defaultValue;
//		}	
//		BigDecimal ret = null;	
//		try{
//			ret = new BigDecimal(str.trim());
//		}catch(Exception e){
//			return defaultValue; 
//		}
//		return ret;
//	}
//	
//	public static BigDecimal String2BigDecimal(String str) throws OptimusException{
//		if(StringUtils.isEmpty(str)){
//			return null;
//		}	
//		BigDecimal ret = null;	
//		try{
//			ret = new BigDecimal(str.trim());
//		}catch(Exception e){
//			return null; 
//		}
//		return ret;
//	}
//	
//	/**
//	 * 将逗号分隔的字符串转成List<String>。
//	 * @param str
//	 * @return
//	 */
//	public static List<String> String2List(String str) {
//		List<String> ret = new ArrayList<String>();
//		if(!StringUtils.isEmpty(str)){
//			String[] arr = str.split(",");
//			if(arr!=null && arr.length>0){
//				for(String reprintId : arr){
//					ret.add(reprintId) ;
//				}
//			}
//		}
//		return ret;
//	}
//	public static String list2String(List<String> list,String spr){
//		if(spr==null){
//			spr = ",";
//		}
//		if(list==null || list.isEmpty()){
//			return "";
//		}
//		StringBuffer sb = new StringBuffer();
//		String ret = null;
//		for(String item : list){
//			if(StringUtils.isEmpty(item)){
//				continue;
//			}
//			sb.append(item).append(spr);
//		}
//		ret= sb.substring(0, sb.length()-1);
//		return ret;
//	}
//	/**
//	 * 将json字符串转换为Map格式。
//	 * 
//	 * @param jsonStr
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public static Map<String, String> jsonStr2Map(String jsonStr) {
//		try{
//			Map<String, String> map = (Map<String, String>)JSON.parse(jsonStr);
//			return map;
//		}catch(Exception e){
//			logger.error("=====Json2Map异常！"+jsonStr);
//			return null;
//		}
//	}
//	
//	
//	/**
//	 *  把List<String>转成用指定分隔符分隔的字符串，默认","
//	 * 
//	 * @param List<String>
//	 * @param String 分隔符，例如","
//	 * @return str1,str2,....strn
//	 */
//	public String list2StrForIn(List<String> list, String separator){
//		if(separator == null){
//			separator = ",";
//		}
//		StringBuffer result = new StringBuffer();
//		if( list != null && list.size()>0 ){
//			result.append(list.get(0));
//			for (int i = 1; i < list.size(); i++) {
//				result.append(separator+list.get(i));
//			}
//		}
//		return result.toString();
//	}
	
	
}
