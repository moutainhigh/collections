package com.gwssi.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;

public class JsonDataUtil
{

	public static JSONArray getJsonArray(String jsonString, String key)
	{
		return JSONObject.fromObject(jsonString).getJSONArray(key);
	}

	public static String toJSONString(Object object)
	{
		JSONArray jsonArray = JSONArray.fromObject(object);
		return jsonArray.toString();
	}

	public static String getJsonByRecordSet(Recordset rs)
	{
		List<Map<String, String>> stateList = new ArrayList<Map<String, String>>();
		for (int ii = 0; ii < rs.size(); ii++) {
			DataBus db = rs.get(ii);
			Map<String, String> stateMap = new HashMap<String, String>();
			stateMap.put("key", db.getValue("key"));
			stateMap.put("title", db.getValue("title"));
			if (StringUtils.isNotBlank(db.getValue("amount"))) {
				stateMap.put("amount", db.getValue("amount"));
			}
			stateList.add(stateMap);
		}
		return JsonDataUtil.toJSONString(stateList);
	}
	
	/**
	 * 组织接口分组数组
	 * 按照拼音首字母分组并排序
	 * @throws TxnException 
	 */
	public static String getPYJsonByRecordSet(Recordset rs) throws TxnException
	{
		Map<String, List<Map<String, String>>> all  = new HashMap<String, List<Map<String, String>>>();
		List<Map<String, String>> fline = new ArrayList<Map<String, String>>();
		List<Map<String, String>> stateList = new ArrayList<Map<String, String>>();
		for (int ii = 0; ii < rs.size(); ii++) {
			DataBus db = rs.get(ii);
			Map<String, String> stateMap = new HashMap<String, String>();
			stateMap.put("key", db.getValue("key"));
			stateMap.put("title", db.getValue("title"));
			if (StringUtils.isNotBlank(db.getValue("amount"))) {
				stateMap.put("amount", db.getValue("amount"));
			}
			stateList.add(stateMap);
			if(ii < 10){
				fline.add(stateMap);
			}
		}
		all.put("data", stateList);
		all.put("hot", fline);
		
		List ll = new ArrayList();
		ll.add(all);
		return JsonDataUtil.toJSONString(ll);
	}
	
	public static String getJsonByRecordSet(Recordset rs,String keyName,String valueName)
	{
		List<Map<String, String>> stateList = new ArrayList<Map<String, String>>();
		for (int ii = 0; ii < rs.size(); ii++) {
			DataBus db = rs.get(ii);
			Map<String, String> stateMap = new HashMap<String, String>();
			stateMap.put("key", db.getValue(keyName));
			stateMap.put("title", db.getValue(valueName));
			if (StringUtils.isNotBlank(db.getValue("amount"))) {
				stateMap.put("amount", db.getValue("amount"));
			}
			stateList.add(stateMap);
		}
		return JsonDataUtil.toJSONString(stateList);
	}
	/**
	 * 
	 * @param rs
	 *            查询的结果集
	 * @param groupName
	 *            需要分组的字段
	 * @param keys
	 *            分组的要显示名字数组
	 * @param values
	 *            分组字段具体的值数组
	 * @return
	 */
	@SuppressWarnings( { "rawtypes", "unchecked" })
	public static String getJsonGroupByRecordSet(Recordset rs,
			String groupName, String[] keys, String[] values)
	{
		List<Map<String, String>> stateList = new ArrayList<Map<String, String>>();
		Map groupMap = new HashMap();
		for (int ii = 0; ii < rs.size(); ii++) {
			DataBus db = rs.get(ii);
			Map<String, String> stateMap = new HashMap<String, String>();
			stateMap.put("key", db.getValue("key"));
			stateMap.put("title", db.getValue("title"));
			if (StringUtils.isNotBlank(db.getValue("amount"))) {
				stateMap.put("amount", db.getValue("amount"));
			}
			stateList.add(stateMap);

			if (null == groupMap.get(db.getValue(groupName))) {
				List list = new ArrayList();
				list.add(stateMap);
				groupMap.put(db.getValue(groupName), list);
			} else {
				List list = (List) groupMap.get(db.getValue(groupName));
				list.add(stateMap);
				groupMap.put(db.getValue(groupName), list);
			}
		}
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		for (int i = 0; i < keys.length; i++) {
			if (groupMap.containsKey(values[i])) {
				Map data = new HashMap();
				data.put("key", values[i]);
				data.put("name", keys[i]);
				data.put("data", groupMap.get(values[i]));
				List list = (List) groupMap.get(values[i]);
				int amount = 0;
				if (!list.isEmpty()) {
					for (int j = 0; j < list.size(); j++) {
						Map map = (Map) list.get(j);
						if (null != map.get("amount")) {
							if (!map.get("amount").equals("0")) {
								amount += Integer.parseInt(map.get("amount")
										.toString());
							}
						}

					}
				}
				data.put("amount", amount);
				dataList.add(data);
			}
		}
		return JsonDataUtil.toJSONString(dataList);
	}
	
	public static void main(String[] args)
	{
		// //String s = "{\"field0\":\"NAME\"}";
		String s = "{data:[{\"leftParen\": \"(\",\"leftTable\": {\"id\":\"QYDJT001\",\"name_en\":\"REG_BUS_ENT\",\"name_cn\":\"企业(机构)\"},\"leftColumn\" : {\"id\":\"QYDJT001C1001\",\"name_en\":\"REG_BUS_ENT_ID\",\"name_cn\":\"企业(机构)ID\"}, \"middleParen\": \"=\",\"rightTable\": {\"id\":\"QYDJT002\",\"name_en\":\"REG_BUS_ENT_APP\",\"name_cn\":\"企业(机构)附表\"},\"rightColumn\" : {\"id\":\"QYDJT002C1002\",\"name_en\":\"REG_BUS_ENT_ID\",\"name_cn\":\"企业(机构)ID\"},\"rightParen\": \")\" }]}";
		// s+=",{\"leftParen\":\"(\",\"leftTable\":{\"id\":\"QYDJT001\",\"name_en\":
		// \"REG_BUS_EN\",\"name_cn\":\"企业(机构)\"}}]}";

		System.out.println(s);
		JSONObject jb = JSONObject.fromObject(s);
		System.out.println(jb.toString());
		// System.out.println(map.size());
		// JSONArray jArray=jb.getJSONArray("data");
		// for (int i = 0; i < jArray.size(); i++) {
		// JSONObject object=(JSONObject)jArray.get(i);
		// System.out.println(object.getString("leftParen"));
		// }
		// Map dataMap = new HashMap();
		// Map left_table = new HashMap();
		// left_table.put("id", "111");
		// left_table.put("name_en", "aaa");
		// left_table.put("name_cn", "关联");
		// dataMap.put("leftTable", left_table);
		// Map left_column = new HashMap();
		// left_column.put("id", "222");
		// left_column.put("name_en", "bbb");
		// left_column.put("name_cn", "关联");
		// dataMap.put("leftColumn", left_column);
		// dataMap.put("middleParen", "=");
		// Map right_table = new HashMap();
		// right_table.put("id", "333");
		// right_table.put("name_en","ccc");
		// right_table.put("name_cn", "关联");
		// dataMap.put("rightTable", right_table);
		// Map right_column = new HashMap();
		// right_column.put("id", "444");
		// right_column.put("name_en", "ddd");
		// right_column.put("name_cn", "关联");
		// dataMap.put("rightColumn", right_column);
		// JSONArray jsonArray = JSONArray.fromObject(dataMap);
		// for (int i = 0; i < jsonArray.size(); i++) {
		// System.out.println(jsonArray.get(i));
		// }
		// sysoutjsonArray.toString();
	}
}
