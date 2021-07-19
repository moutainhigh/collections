package com.gwssi.dw.dq.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;

import com.genersoft.frame.base.database.DBException;
import com.genersoft.frame.base.database.DbUtils;

public class TreeQueryDAO {
	
	private String		connType;
	
	public TreeQueryDAO(String connType) {
		this.connType = connType;
	}
	
	public Document getTree(String name) throws DBException {
		Document doc = DocumentFactory.getInstance().createDocument();
		Element rootElement = doc.addElement("ul");
		Element rootLiElement = rootElement.addElement("li");
		Element rootAElement = rootLiElement.addElement("a");
		Element rootSpanElement = rootAElement.addElement("span");
		if (name.equals("target")) {
			rootSpanElement.addText("指标");
		}
		else {
			rootSpanElement.addText("分组");
		}
		rootLiElement.addAttribute("expanded", "true");
		Element rootUlElement = rootLiElement.addElement("ul");
		List subjectList = getSubjects();
		for (int i=0;i < subjectList.size();i++) {
			Map subjectMap = (HashMap)subjectList.get(i);
			List entityList = getEntities((String)subjectMap.get("SUBJECT_ID"),name);
			
			Element subjectLiElement = rootUlElement.addElement("li");
			subjectLiElement.addAttribute("id", (String)subjectMap.get("SUBJECT_ID"));
			Element subjectAElement = subjectLiElement.addElement("a");
			Element subjectSpanElement = subjectAElement.addElement("span");
			subjectSpanElement.addText((String)subjectMap.get("SUBJECT_NAME")); //span中的名称
			
			int j = 0;
			Element entityUlElement = null;
			if (entityList.size() != 0) {
				entityUlElement = subjectLiElement.addElement("ul");
			}
			for (;j < entityList.size();j++) {
				Map entityMap = (HashMap)entityList.get(j);
//				List itemList = null;
//				if (name.equals("target"))
//					itemList = getTargets((String)entityMap.get("ENTITY_ID"), conn);
//				else
//					itemList = getGroups((String)entityMap.get("ENTITY_ID"), conn);
//				if (itemList.size() == 0) {
//					continue;
//				}
				Element entityLiElement = entityUlElement.addElement("li");
				entityLiElement.addAttribute("id", (String)entityMap.get("ENTITY_ID"));
				entityLiElement.addAttribute("table", (String)entityMap.get("ENTITY_TABLE"));
				entityLiElement.addAttribute("src", "treeList.action?method=getChildTree&type="+name+"&entityId="+(String)entityMap.get("ENTITY_ID"));
				Element entityAElement = entityLiElement.addElement("a");
				Element entitySpanElement = entityAElement.addElement("span");
				entitySpanElement.addText((String)entityMap.get("ENTITY_NAME"));
				
//				int k = 0;
//				Element itemUlElement = null;
//				if (itemList.size() != 0) {
//					itemUlElement = entityLiElement.addElement("ul");
//				}
//				for (;k < itemList.size();k++) {
//					if (name.equals("target")) {
//						Map targetMap = (HashMap)itemList.get(k);
//						Element targetLiElement = itemUlElement.addElement("li");
//						targetLiElement.addAttribute("id", (String)targetMap.get("TARGET_ID"));
//						targetLiElement.addAttribute("entityID", (String)targetMap.get("ENTITY_ID"));
//						targetLiElement.addAttribute("allEntityID", getAllEntityID((String)targetMap.get("ENTITY_ID"), conn));
//						targetLiElement.addAttribute("class", "target");
//						targetLiElement.addAttribute("draggable", "true");
//						Element targetAElement = targetLiElement.addElement("a");
//						Element targetSpanElement = targetAElement.addElement("span");
//						targetSpanElement.addText((String)targetMap.get("TARGET_NAME"));
//					}
//					else {
//						Map groupMap = (HashMap)itemList.get(k);
//						Element groupLiElement = itemUlElement.addElement("li");
//						String groupId = (String)groupMap.get("GROUP_ID");
//						groupLiElement.addAttribute("id", groupId);
//						groupLiElement.addAttribute("entityID", (String)groupMap.get("ENTITY_ID"));
//						groupLiElement.addAttribute("show", (String)groupMap.get("ISSHOWID"));
//						groupLiElement.addAttribute("fileIcon", ((String)groupMap.get("INPUTTYPE")).equals("input")?"/bjaic/images/showReport/textfield.gif":"/bjaic/images/showReport/list.gif");
//						groupLiElement.addAttribute("allEntityID", getAllEntityID((String)groupMap.get("ENTITY_ID"), conn));
//						groupLiElement.addAttribute("class", "group " + (String)groupMap.get("INPUTTYPE"));
//						groupLiElement.addAttribute("draggable", "true");
//						groupLiElement.addAttribute("condLength", (String)groupMap.get("MAXLENGTH"));
//						Element groupAElement = groupLiElement.addElement("a");
//						Element groupSpanElement = groupAElement.addElement("span");
//						groupSpanElement.addText((String)groupMap.get("GROUP_NAME"));
//					}
//				}
			}
			if (j != 0)
				subjectLiElement.addAttribute("expanded", "false");
		}
		doc.setXMLEncoding("UTF-8");
		return doc;
	}
	
	public String getChildTree(String contextPath, String type, String entityId) throws DBException {
		if (type == null) {
			return "";
		}
//		Connection conn = DbUtils.getConnection(DqContants.CON_TYPE);
		List itemList = null;
		List dataList = new ArrayList();
		Map dataMap = new HashMap();
		String allEntityId = getAllEntityID(entityId,type);
		if (type.equals("target")) {
			itemList = getTargets(entityId);
			for (int i=0;i < itemList.size();i++) {
				Map targetMap = (HashMap)itemList.get(i);
				Map map = new HashMap();
				map.put("id", (String)targetMap.get("TARGET_ID"));
				map.put("text", (String)targetMap.get("TARGET_NAME"));
				map.put("entityID", (String)targetMap.get("ENTITY_ID"));
				map.put("allEntityID", allEntityId);
				map.put("nType", "target");
				map.put("draggable", "true");
				dataList.add(map);
			}
		}
		else {
			itemList = getGroups(entityId);
			for (int i=0;i < itemList.size();i++) {
				Map groupMap = (HashMap)itemList.get(i);
				String groupId = (String)groupMap.get("GROUP_ID");
				Map map = new HashMap();
				map.put("id", groupId);
				map.put("text", (String)groupMap.get("GROUP_NAME"));
				map.put("entityID", (String)groupMap.get("ENTITY_ID"));
				map.put("show", (String)groupMap.get("ISSHOWID"));
				map.put("fileIcon", ((String)groupMap.get("INPUTTYPE")).equals("input")?contextPath + "/images/showReport/textfield.gif":contextPath + "/images/showReport/list.gif");
				map.put("allEntityID", allEntityId);
				map.put("nType", "group " + (String)groupMap.get("INPUTTYPE"));
				map.put("draggable", "true");
				map.put("condLength", (String)groupMap.get("MAXLENGTH"));
				dataList.add(map);
			}
		}
		dataMap.put("records", dataList);
		dataMap.put("totalCount", dataList.size() + "");
		return JSONObject.fromObject(dataMap).toString();
	}
	
	private List getSubjects() throws DBException {
		List dataList;
		String sql = "select sub.SUBJECT_ID, sub.SUBJECT_NAME " +
				"from DQ_SUBJECT sub order by sub.SUBJECT_NAME asc ";
		dataList = DbUtils.select(sql, this.connType);
		return dataList;
	}
	
	/**
	 * 根据给定的主题ID,得到相应的所有实体列表
	 * @param subjectId 主题ID
	 * @param conn 数据库连接
	 * @return HashMap为元素的List
	 * @throws DBException
	 */
	private List getEntities(String subjectId, String type) throws DBException {
		List dataList;
		String sql = "";
		if (type.equals("target")) {
			sql = "select distinct entity.ENTITY_ID, entity.SUBJECT_ID, entity.ENTITY_NAME, entity.ENTITY_TABLE " +
					"from DQ_ENTITY entity,DQ_TARGET tar " +
					"where entity.SUBJECT_ID='" +
					subjectId +
					"' and entity.ENTITY_ID=tar.ENTITY_ID order by entity.ENTITY_NAME asc";
		}
		else {
			sql = "select distinct entity.ENTITY_ID, entity.SUBJECT_ID, entity.ENTITY_NAME, entity.ENTITY_TABLE " +
					"from DQ_ENTITY entity,DQ_GROUP gr " +
					"where entity.SUBJECT_ID='" +
					subjectId +
					"' and entity.ENTITY_ID=gr.ENTITY_ID order by entity.ENTITY_NAME asc";
		}
		dataList = DbUtils.select(sql, this.connType);
		return dataList;
	}
	
	/**
	 * 根据实体ID获得相应的分组列表
	 * @param entityId 实体ID
	 * @param conn 数据库连接
	 * @return HashMap为元素的List
	 * @throws DBException
	 */
	private List getGroups(String entityId) throws DBException {
		List dataList;
		String sql = "select gr.GROUP_ID, gr.ENTITY_ID, gr.GROUP_NAME, gr.INPUTTYPE, gr.MAXLENGTH, gr.ISSHOWID " +
				"from DQ_GROUP gr " +
				"where gr.ENTITY_ID='" +
				entityId +
				"' and gr.ISACTIVE='1' order by gr.INPUTTYPE,gr.GROUP_NAME asc";
		dataList = DbUtils.select(sql, this.connType);
		return dataList;
	}
	
	/**
	 * 根据实体ID获得相应的指标列表
	 * @param entityId 实体ID
	 * @param conn 数据库连接
	 * @return HashMap为元素的List
	 * @throws DBException
	 */
	private List getTargets(String entityId) throws DBException {
		List dataList;
		String sql = "select tg.TARGET_ID, tg.ENTITY_ID, tg.TARGET_NAME " +
				"from DQ_TARGET tg " +
				"where tg.ENTITY_ID='" +
				entityId +
				"' order by tg.TARGET_NAME asc";
		dataList = DbUtils.select(sql, this.connType);
		return dataList;
	}
	
	/**
	 * 根据实体ID获得所有实体ID,返回字符串
	 * 如果是target,则将所有对应它的外键实体ID串返回,否则将它所对应的实体ID串返回
	 * @param entityId
	 * @param conn
	 * @param type 为target或group
	 * @return id,id,id……
	 * @throws DBException
	 */
	private String getAllEntityID(String entityId,String type) throws DBException {
		StringBuffer back = new StringBuffer();
		back.append(entityId);
		String sql = "";
		List temp = null;
		if (type.equals("group")) {
			sql = "select ent.ENTITY_ID,ent.FOREIGN_ENTITY_ID from DQ_ENTITY_FOREIGN ent " +
				"where ent.FOREIGN_ENTITY_ID='" +
				entityId +
				"' ";
			temp = DbUtils.select(sql, this.connType);
			for (int i=0;i < temp.size();i++) {
				Map map = (Map)temp.get(i);
				back.append("," + (String)map.get("ENTITY_ID"));
			}
//			sql = "select ent.ENTITY_ID,ent.FOREIGN_ENTITY_ID from DQ_ENTITY_FOREIGN ent " +
//				"where ent.ENTITY_ID='" +
//				entityId +
//				"' ";
//			temp = DbUtils.select(sql, this.connType);
//			for (int i=0;i < temp.size();i++) {
//				Map map = (Map)temp.get(i);
//				back.append("," + (String)map.get("FOREIGN_ENTITY_ID"));
//			}
		}
		return back.toString();
	}
	
}
