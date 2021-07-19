package com.gwssi.common.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JSONUtils
{
	/**
	 * 
	 * @author wangwei JSON工具类
	 * @param <T>
	 * 
	 */

	/***************************************************************************
	 * 将List对象序列化为JSON文本
	 */
	public static String toJSONString(List list)
	{
		JSONArray jsonArray = JSONArray.fromObject(list);

		return jsonArray.toString();
	}


	/***************************************************************************
	 * 将对象转换为List对象
	 * 
	 * @param object
	 * @return
	 */
	public static List toArrayList(Object object)
	{
		List arrayList = new ArrayList();

		JSONArray jsonArray = JSONArray.fromObject(object);

		Iterator it = jsonArray.iterator();
		while (it.hasNext()) {
			JSONObject jsonObject = (JSONObject) it.next();

			Iterator keys = jsonObject.keys();
			while (keys.hasNext()) {
				Object key = keys.next();
				Object value = jsonObject.get(key);
				arrayList.add(value);
			}
		}

		return arrayList;
	}
	
	public static String toJsonStringByObj(Object obj){
		JSONObject jsonObject=JSONObject.fromObject(obj);
		return jsonObject.toString();
	}
	
	public static void main(String[] args)
	{		
		

	}

}
