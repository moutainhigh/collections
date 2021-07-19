package com.gwssi.dw.dq.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.genersoft.frame.base.database.DBException;
import com.genersoft.frame.base.database.DbUtils;

public class CheckDao
{
	private String connType;
	
	public CheckDao(String connType) {
		this.connType = connType;
	}
	
	/**
	 * 检验实体页面两种关联条件是否合法,返回有误的id或者name的集合
	 * @param jsonStr
	 * @return
	 */
	public String checkEntityCond(String jsonStr) {
		Map map = JSONObject.fromObject(jsonStr);
		String table = (String)map.get("table");
		List fList = (List)map.get("foreignEntity");
		List sList = (List)map.get("subjoinTable");
		List newFList = new ArrayList();
		List newSList = new ArrayList();
		StringBuffer sql = new StringBuffer();
		for (int i=0;i < fList.size();i++) {
			Map temp = (Map)fList.get(i);
			sql.append("explain plan for (");
			sql.append("select null from ");
			sql.append(table + ",");
			sql.append((String)temp.get("table") + " ");
			sql.append("where rownum=1 and ");
			sql.append((String)temp.get("cond"));
			sql.append(" )");
			try {
//				System.out.println("checkSQL:" + sql.toString());
				DbUtils.execute(sql.toString(), this.connType);
			} catch (DBException e) {
//				e.printStackTrace();
				Map map1 = new HashMap();
				map1.put("id", temp.get("id"));
				newFList.add(map1);
			}
			finally {
				sql = new StringBuffer();
			}
		}
		for (int i=0;i < sList.size();i++) {
			Map temp = (Map)sList.get(i);
			sql.append("explain plan for (");
			sql.append("select null from ");
			sql.append(table + ",");
			sql.append((String)temp.get("name") + " ");
			sql.append("where rownum=1 and ");
			sql.append((String)temp.get("cond"));
			sql.append(" )");
			try {
//				System.out.println("checkSQL:" + sql.toString());
				DbUtils.execute(sql.toString(), this.connType);
			} catch (DBException e) {
//				e.printStackTrace();
				Map map1 = new HashMap();
				map1.put("name", temp.get("name"));
				newSList.add(map1);
			}
			finally {
				sql = new StringBuffer();
			}
		}
		Map newMap = new HashMap();
		newMap.put("foreignEntity", newFList);
		newMap.put("subjoinTable", newSList);
		return pack2Json(newMap);
	}
	
	/**
	 * 检验分组页面输入的预定义SQL是否合法
	 * @param jsonStr
	 * @return
	 */
	public String checkGroupPrev(String jsonStr) {
		Map map = JSONObject.fromObject(jsonStr);
		String groupSql = (String)map.get("sql");
		StringBuffer sql = new StringBuffer();
		sql.append("explain plan for (");
		sql.append("select id,text,pid from (");
		sql.append(groupSql);
		sql.append(")");
		sql.append(")");
		String flag = "true";
		try {
			DbUtils.execute(sql.toString(), this.connType);
		} catch (DBException e) {
//			e.printStackTrace();
			flag = "false";
		}
		return flag;
	}
	
	public String checkModelName(String name, String id) {
		String sql = "select * from DQ_TEMPLATE where TEMPLATE_NAME='" +
				name.trim() +
				"'";
		if (id != null && !id.equals("")) {
			sql += " and TEMPLATE_ID <> '" + id + "' ";
		}
		String flag = "true";
		try {
			List list = DbUtils.select(sql, this.connType);
			if (list.size() > 0) 
				flag = "false";
		} catch (DBException e) {
			e.printStackTrace();
			flag = "error";
		}
		return flag;
	}
	
//	/**
//	 * 检验分组页面的自定义分组项条件是否合法
//	 * @param jsonStr
//	 * @return
//	 */
//	public String checkGroupCustom(String jsonStr) {
//		String flag = "true";
//		Map map = JSONObject.fromObject(jsonStr);
//		List params = (List)map.get("params");
//		List items = (List)map.get("items");
//		String pattern = "(\\{|\\[)[^(\\}|\\])]*(\\}|\\])"; //替换如['']或者{''}型的值
//		PreparedStatement ps = null;
//		for (int i=0;i < items.size();i++) {
//			boolean tempFlag = false;
//			Map itemMap = (Map)items.get(i);
//			Matcher matcher =Pattern.compile(pattern).matcher((String)itemMap.get("cond"));
//			String sql = matcher.replaceAll("?");
//			try {
//				ps = conn.prepareStatement(sql);
//			} catch (SQLException e) {
////				e.printStackTrace();
//			}
//			int j = 0;
//			while (matcher.find()) {
//				String temp = matcher.group(); //['xxxx'] or {xxxx}
//				String value = "";
//				String type = "常量";
//				if (temp.indexOf("[") < 0) { //为{}型,是参数
//					temp = temp.substring(1,temp.length()-1); //'xxxx' or xxxx
//					type = getParamType(temp, params);
//					if (type.equals("字符")) {
//						value = "test";
//					}
//					else if (type.equals("数字")) {
//						value = "1";
//					}
//					else if (type.equals("日期")) {
//						value = "2008-01-01";
//					}
//				}
//				else { //为[]型,是常量
//					value = temp.substring(1,temp.length()-1);
//				}
//				try {
//					if (!type.equals("数字")) {
//						ps.setString(j, value);
//					}
//					else {
//						ps.setInt(j, Integer.parseInt(value));
//					}
//				} catch (Exception e) {
//					
//				}
//			}
//		}
//		return flag;
//	}
//	
//	private String getParamType(String name, List paramsList) {
//		for (int i=0;i < paramsList.size();i++) {
//			Map map = (Map)paramsList.get(i);
//			if (((String)map.get("name")).equals(name)) {
//				return (String)map.get("dataType");
//			}
//		}
//		return "";
//	}
	
	private String pack2Json(Map map) {
		return JSONObject.fromObject(map).toString();
	}

}
