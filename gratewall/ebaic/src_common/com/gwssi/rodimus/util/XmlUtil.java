package com.gwssi.rodimus.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gwssi.rodimus.exception.RodimusException;

/**
 * XML工具类。
 * 
 * @author 刘海龙
 */
public class XmlUtil {
	
	public static void main(String[] args){
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> ent = new HashMap<String,Object>();
		Map<String,Object> inv = new HashMap<String,Object>();
		ent.put("admCode", "110100");
		inv.put("name", "liu");
		map.put("basicInfo", ent);
		map.put("invInfo", inv);
		list.add(map);
		String ret = list2Xml("ArchiveInfo", list, 1);
		//String ret = map2Xml("ArchiveInfo",map,1);
		System.out.println(ret);
	}

	private final static String BR = "\r\n";
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * Map对象转换为XML字符串。
	 * 
	 * @param map
	 * @return
	 */
	public static String map2Xml(String elementName, Map<String,Object> map,int level){
		if(level<1){
			level = 1;
		}
		if(StringUtil.isBlank(elementName)){
			throw new RodimusException("标签名不能为空。");
		}
		
		StringBuffer ret = new StringBuffer();
		elementName = formatKey(elementName);
		if(level<=1){
			ret.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append(BR) ;
		}
		
		if(map==null || map.isEmpty()){
			if(level>1){
				ret.append(StringUtil.repeat("\t", level));
			}
			ret.append("<").append(elementName).append(" />");
			return ret.toString();
		}
		ret.append(StringUtil.repeat("\t", level-1));
		ret.append("<").append(elementName).append(">").append(BR);
		
		String key;	Object value;
		for(Map.Entry<String, Object> entry : map.entrySet()){
			key = entry.getKey() ;
			key = formatKey(key);
			value = entry.getValue();
			if(value == null){
				ret.append(StringUtil.repeat("\t", level));
				ret.append("<").append(key).append(" />").append(BR);
				continue ;
			}
			if(value instanceof String){
				ret.append(StringUtil.repeat("\t", level));
				ret.append("<").append(key).append(">")
				//.append("<![CDATA[")
				.append(value)
				//.append("]]>")
				.append("</").append(key).append(">").append(BR);
				continue ;
			}
			if(value instanceof BigDecimal){
				ret.append(StringUtil.repeat("\t", level));
				long v = ((BigDecimal)value).longValue();
				ret.append("<").append(key).append(">")
				//.append("<![CDATA[")
				.append(v)
				//.append("]]>")
				.append("</").append(key).append(">").append(BR);
				continue ;
			}
			if(value instanceof Integer){
				ret.append(StringUtil.repeat("\t", level));
				long v = ((Integer)value).longValue();
				ret.append("<").append(key).append(">")
				//.append("<![CDATA[")
				.append(v)
				//.append("]]>")
				.append("</").append(key).append(">").append(BR);
				continue ;
			}
			if(value instanceof List){
				@SuppressWarnings("unchecked")
				List<Map<String,Object>> list = (List<Map<String,Object>>)value;
				ret.append("<").append(key+"List").append(">");
				for(Map<String,Object> item : list){
					String itemString = map2Xml(key,item,level+1);
					ret.append(itemString);
				}
				ret.append("</").append(key+"List").append(">").append(BR);
				continue ;
			}
			if(value instanceof Map){
				@SuppressWarnings("unchecked")
				Map<String,Object> child = (Map<String,Object>)value;
				if(child.isEmpty()){
					ret.append(StringUtil.repeat("\t", level));
					ret.append("<").append(key).append(" />").append(BR);
					continue ;
				}
				String itemString = map2Xml(key,child,level+1);
				ret.append(itemString);
				continue ;
			}
			if(value instanceof Calendar){
				Calendar c = (Calendar)value;
				String dateString = sdf.format(c.getTime());
				ret.append("<").append(key).append(">")
				//.append("<![CDATA[")
				.append(dateString)
				//.append("]]>")
				.append("</").append(key).append(">").append(BR);
				continue ;
			}
			throw new RodimusException("不支持的数据类型：" + value.getClass().getName());
		}
		
		ret.append(BR).append(StringUtil.repeat("\t", level-1));
		ret.append("</").append(elementName).append(">");
		return ret.toString();
	}
	/**
	 * Map对象转换为XML字符串。
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings({ "unchecked"})
	public static String list2Xml(String elementName, List<Map<String,Object>> list,int level){
		if(level<1){
			level = 1;
		}
		if(StringUtil.isBlank(elementName)){
			throw new RodimusException("标签名不能为空。");
		}
		elementName = formatKey(elementName);
		StringBuffer ret = new StringBuffer();
		if(level<=1){
			ret.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append(BR) ;
		}
		if(list==null || list.isEmpty()){
			if(level>1){
				ret.append(StringUtil.repeat("\t", level));
			}
			ret.append("<").append(elementName).append(" />");
			return ret.toString();
		}
		ret.append(StringUtil.repeat("\t", level-1));
		ret.append("<").append(elementName).append(">").append(BR);
		int mapLevel = level+1;
		String key;
		Object value;
		for(Map<String,Object> map:list){
			for(Map.Entry<String, Object> entry:map.entrySet()){
				key = entry.getKey();
				key = formatKey(key);
				value = entry.getValue();
				if(value == null){
					ret.append(StringUtil.repeat("\t", level));
					ret.append("<").append(key).append(" />").append(BR);
					continue ;
				}
				if(value instanceof Map){
					Map<String,Object> mapValue = (Map<String,Object>)value;
					if(mapValue.isEmpty()){
						ret.append(StringUtil.repeat("\t", level));
						ret.append("<").append(key).append(" />").append(BR);
						continue ;
					}
					ret.append(map2Xml(key,mapValue,mapLevel)).append(BR);
					continue ;
				}
				if(value instanceof String){
					ret.append(StringUtil.repeat("\t", level));
					ret.append("<").append(key).append(">")
					//.append("<![CDATA[")
					.append(value)
					//.append("]]>")
					.append("</").append(key).append(">").append(BR);
					continue ;
				}
				if(value instanceof BigDecimal){
					ret.append(StringUtil.repeat("\t", level));
					long v = ((BigDecimal)value).longValue();
					ret.append("<").append(key).append(">")
					//.append("<![CDATA[")
					.append(v)
					//.append("]]>")
					.append("</").append(key).append(">").append(BR);
					continue ;
				}
				if(value instanceof List){
					String itemString = list2Xml(key,(List<Map<String,Object>>)value,mapLevel);
					ret.append(itemString);
					continue ;
				}
				if(value instanceof Calendar){
					Calendar c = (Calendar)value;
					String dateString = sdf.format(c.getTime());
					ret.append("<").append(key).append(">")
					//.append("<![CDATA[")
					.append(dateString)
					//.append("]]>")
					.append("</").append(key).append(">").append(BR);
					continue ;
				}
				throw new RodimusException("不支持的数据类型：" + value.getClass().getName());
			}
		}
		ret.append(BR).append(StringUtil.repeat("\t", level-1));
		ret.append("</").append(elementName).append(">");
		return ret.toString();
	}
	
	
	private static String formatKey(String key){
		if(StringUtil.isBlank(key)){
			return "Null";
		}
		key = key.replaceAll(" ", "");
		key = key.substring(0, 1).toUpperCase() + key.substring(1);
		return key;
	}
}
