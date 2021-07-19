package com.gwssi.dw.dq.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;

import com.genersoft.frame.base.database.DBException;
import com.genersoft.frame.base.database.DbUtils;

public class FzxDao
{
	private String connType = "2";
	
	public FzxDao(String connType)
	{
		this.connType = connType;
	}
	
	public Document getRootTree(String connType,String groupId) throws DBException {
		Document doc = DocumentFactory.getInstance().createDocument();
		Element rootElement = doc.addElement("ul");
		List groupList = getFzx1(groupId);
		for (int i=0;i < groupList.size();i++) {
			Map dataMap = (HashMap)groupList.get(i);
			String groupSql = (String)dataMap.get("GROUP_SQL");
			String pattern = "\\'(.[^\\']*)\\'"; //替换如['']的常量
			Matcher matcher = Pattern.compile(pattern).matcher(groupSql);
			List dataId = new ArrayList();
			StringBuffer ids = new StringBuffer();
			while (matcher.find()) {
				dataId.add(matcher.group(1)); 
			}
			for (int m=0,len=dataId.size();m < len;m++) {
				ids.append((String)dataId.get(m));
				if (m != len-1) 
					ids.append(",");
			}
			List fzx2List = getFzx2(groupId, groupSql);
			
			Element fzx1LiElement = rootElement.addElement("li");
			fzx1LiElement.addAttribute("id", (String)dataMap.get("GROUP_ID"));
			fzx1LiElement.addAttribute("youiChecked", "false");
			fzx1LiElement.addAttribute("dataId", ids.toString());
			//fzx1LiElement.addAttribute("src", "fzxList.action?method=getChildTree&groupId="+groupId+"&pId="+groupId);
			Element fzx1AElement = fzx1LiElement.addElement("a");
			Element fzx1SpanElement = fzx1AElement.addElement("span");
			fzx1SpanElement.addText((String)dataMap.get("GROUP_NAME")); 
			int j = 0;
			Element fzx2UlElement = null;
			if (fzx2List.size() != 0) {
				fzx2UlElement = fzx1LiElement.addElement("ul");
			}
			for (;j < fzx2List.size();j++) {
				Map fzx2Map = (HashMap)fzx2List.get(j);
				Element fzx2LiElement = fzx2UlElement.addElement("li");
				fzx2LiElement.addAttribute("id", (String)fzx2Map.get("ID"));
				fzx2LiElement.addAttribute("youiChecked", "false");
				if (checkChild(groupSql, (String)fzx2Map.get("ID"))) 
					fzx2LiElement.addAttribute("src", "fzxList.action?method=getChildTree&groupId="+groupId+"&pId="+(String)fzx2Map.get("ID"));
				Element fzx2AElement = fzx2LiElement.addElement("a");
				Element fzx2SpanElement = fzx2AElement.addElement("span");
				fzx2SpanElement.addText((String)fzx2Map.get("TEXT"));
				fzx2LiElement.addAttribute("expanded", checkChild(groupSql, (String)fzx2Map.get("ID")) + "");
			}
		}
		doc.setXMLEncoding("UTF-8");
		return doc;
	}
	
	public String getChildTree(String groupId, String pId) throws DBException {
		List list = getFzx1(groupId);
		Map map = (Map)list.get(0);
		String groupSql = (String)map.get("GROUP_SQL");
		List cList = getFzx3(pId, groupSql);
		List newList = new ArrayList();
		Map newMap = new HashMap();
		newMap.put("totalCount", cList.size() + "");
		for (int i=0;i < cList.size();i++) {
			Map temp = (Map)cList.get(i);
			Map newTempMap = new HashMap();
			newTempMap.put("id", temp.get("ID"));
			newTempMap.put("text", temp.get("TEXT"));
			String src = "fzxList.action?method=getChildTree&groupId=" 
				+ groupId + "&pId=" + (String)temp.get("ID");
			if (checkChild(groupSql, (String)temp.get("ID"))) 
				newTempMap.put("src", src);
			newList.add(newTempMap);
		}
		newMap.put("records", newList);
		return JSONObject.fromObject(newMap).toString();
	}
	
	private boolean checkChild(String groupSql, String pId) throws DBException {
		List list = getFzx3(pId, groupSql);
		if (list.size() > 0)
			return true;
		else
			return false;
	}
	
	public Document getGroupTree(String groupId) throws DBException {
		Document back = null;
		if (isCustomGroup(groupId)) {
			back = getCustomTree(groupId);
		}
		else {
			back = getRootTree(this.connType, groupId);
		}
		return back;
	}
	
	private boolean isCustomGroup(String groupId) throws DBException {
		String sql = "select ISCUSTOM from DQ_GROUP " +
				"where GROUP_ID='" +
				groupId +
				"' ";
		Map map = DbUtils.selectOne(sql, this.connType);
		if (((String)map.get("ISCUSTOM")).equals("0"))
			return false;
		else
			return true;
	}
	
	public Document getCustomTree(String groupId) throws DBException {
		String sql = "select CUSTOM_ITEM_ID, CUSTOM_ITEM_NAME, CUSTOM_ITEM_COND from DQ_CUSTOM_ITEM " +
				"where GROUP_ID='" +
				groupId +
				"' ";
		Document doc = DocumentFactory.getInstance().createDocument();
		Element rootElement = doc.addElement("ul");
		Element liElement = rootElement.addElement("li");
		
		List paramsList = getParams(groupId);
		liElement.addAttribute("id", groupId);
		liElement.addAttribute("youiChecked", "false");
		Element aElement = liElement.addElement("a");
		Element spanElement = aElement.addElement("span");
		List groupList = getFzx1(groupId);
		Map groupMap = (Map)groupList.get(0);
		spanElement.addText((String)groupMap.get("GROUP_NAME"));
		Element ulElement = liElement.addElement("ul");
		List list = DbUtils.select(sql, this.connType);
		for (int i=0;i < list.size();i++) {
			Map dataMap = (HashMap)list.get(i);
			
			Element fzx1LiElement = ulElement.addElement("li");
			fzx1LiElement.addAttribute("id", (String)dataMap.get("CUSTOM_ITEM_ID"));
			fzx1LiElement.addAttribute("youiChecked", "false");
//			fzx1LiElement.addAttribute("isCustom", "1");
			String cond = (String)dataMap.get("CUSTOM_ITEM_COND");
//			fzx1LiElement.addAttribute("cond", cond);
			fzx1LiElement.addAttribute("params", getItemParam(cond, paramsList));
			Element fzx1AElement = fzx1LiElement.addElement("a");
			Element fzx1SpanElement = fzx1AElement.addElement("span");
			fzx1SpanElement.addText((String)dataMap.get("CUSTOM_ITEM_NAME")); 
		}
		doc.setXMLEncoding("UTF-8");
		return doc;
	}
	
	private List getParams(String groupId) throws DBException {
		List paramList;
		String sql = "select * from DQ_PARAMETER where PARAMETER_ID in " +
				"(select PARAMETER_ID from DQ_CUSTOM_PARAMETER where GROUP_ID='" +
				groupId +
				"') ";
		paramList = DbUtils.select(sql, this.connType);
		return paramList;
	}

	private String getItemParam(String itemCond, List paramList)
	{
		String pattern = "\\{[^\\}]*\\}"; // 替换如{''}型的值
		List addedList = new ArrayList();
		if (itemCond == null)
			itemCond = "";
		Matcher matcher = Pattern.compile(pattern).matcher(itemCond);
		while (matcher.find()) {
			String temp = matcher.group(); // {'xxxx'}
			temp = temp.substring(1, temp.length() - 1); // 'xxxx'
			for (int i = 0; i < paramList.size(); i++) {
				Map map = (Map) paramList.get(i);
				String parameterName = (String) map.get("PARAMETER_NAME");
				if (((String)map.get("PARAMETER_EXP")).equals("null")) {
					map.put("PARAMETER_EXP", "");
				}
				if (temp.equals(parameterName)) {
					if (!addedList.contains(map)) {
						addedList.add(map);
					}
				}
			}
		}
		Map map = new HashMap();
		map.put("params", addedList);
		return JSONObject.fromObject(map).toString();
	}
	
	public String getFzModel(String DlId) throws DBException {
		List doc;
		String obj = "";
		doc = getAll(DlId);
		for (int i = 0; i < doc.size(); i++) {
			Map map = (HashMap) doc.get(i);
			obj += (String) map.get("GROUP_TEMP_ID");
			obj += ",";
			obj += (String) map.get("GROUP_TEMP_NAME");

			if (i != doc.size() - 1)
				obj += ";";
		}
		obj += "";
		return obj;
	}
	
	public String getFzModelTree(String tempId) throws DBException
	{
		List doc;
		String obj = "";
		doc = getTree(tempId);
		for (int i = 0; i < doc.size(); i++) {
			Map map = (HashMap) doc.get(i);
			obj += (String) map.get("TEMP_ITEMS_ID");
			if (i != doc.size() - 1)
				obj += ",";
		}
		obj += "";
		return obj;
	}
	
	private List getFzx3(String pid, String groupSql)throws DBException {
		List dataList;
		String sql = "select id,text from (" + groupSql + ") where pid='" +
				pid +
				"' ";
		 dataList = DbUtils.select(sql, this.connType);
		 return dataList;

}
	private List getFzx2(String pid, String groupSql) throws DBException {
		List dataList;
		if (groupSql == null) {
			return new ArrayList();
		}
		String sql = "select id,text from (" + groupSql + ") where pid is null";
		dataList = DbUtils.select(sql, this.connType);
		return dataList;
	}

	private List getFzx1(String fzxTree) throws DBException {//获得行业代码；
		List dataList;
		String sql = " select a.GROUP_ID,a.GROUP_NAME,a.GROUP_SQL " +
				" from DQ_GROUP a "+" where a.GROUP_ID='" +
				fzxTree +
				"' " ;
		dataList = DbUtils.select(sql, this.connType);
		return dataList;
	}
	
	private List getAll(String DlId)throws DBException {
		List dataList;
		String sql = " select GROUP_TEMP_ID,GROUP_TEMP_NAME "
			+" from DQ_GROUP_TEMP "
			+" where GROUP_ID='"
		    +DlId
		    +"' ";
		dataList = DbUtils.select(sql, this.connType);
		return dataList;
	}
	
    private List getTree(String tempId)throws DBException{
    	List dataList;
    	String sql=" select TEMP_ITEMS_ID "+" from DQ_GROUP_TEMP_ITEMS "+" where GROUP_TEMP_ID='"+tempId+"'";
    	dataList = DbUtils.select(sql, this.connType);
		return dataList;
    }
    

}