package com.gwssi.dw.dq.business;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.dw.dq.DqContants;
import com.gwssi.dw.dq.dao.DefinitionDao;

public class Definition
{
	private DefinitionDao dao;
	
	public Definition() {
		dao = new DefinitionDao(DqContants.CON_TYPE);
	}
	
	public String addTarget(String entityId, String targetName, String targetFormula) {
		boolean flag = false;
		try {
			flag = dao.insertTarget(entityId, targetName, targetFormula);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag + "";
	}
	
	public String getTarget(String id) {
		if (id == null)
			return "error";
		List list = null;
		try {
			list = dao.queryTarget(id);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list == null)
			return "error";
		Map map = (Map)list.get(0);
		return pack2Json(map);
	}
	
	public String updateTarget(String targetId, String targetName, String targetFormula) {
		boolean flag = false;
		try {
			flag = dao.updateTarget(targetId, targetName, targetFormula);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag + "";
	}
	
	public String delTarget(String targetId) {
		boolean flag = false;
		try {
			flag = dao.deleteTarget(targetId);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag + "";
	}
	
	public String addGroup(String jsonStr) {
		String msg = "true";
		try {
			dao.insertGroup(json2Map(jsonStr),true,null);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg = "false";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg = "false";
		}
		return msg;
	}
	
	public String getGroup(String id) {
		if (id == null)
			return "error";
		Map map = new HashMap();
		try {
			map = dao.queryGroup(id);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pack2Json(map);
	}
	
	public String updateGroup(String jsonStr) {
		String msg = "true";
		try {
			dao.updateGroup(json2Map(jsonStr));
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg = "false";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg = "false";
		}
		return msg;
	}
	
	public String delGroup(String id) {
		String msg = "true";
		try {
			dao.delGroup(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg = "false";
		}
		return msg;
	}
	public String insertEntity(String jsonStr){
		String msg = "true";
		try {
			dao.insertEntity(json2Map(jsonStr),true,null);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg="false";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg="false";
		}
		
		return msg;
		
		
	}
	public String updateEntity(String jsonStr){
		String msg = "true";
		try {
			dao.updateEntity(json2Map(jsonStr));
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg="false";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg="false";
		}
		
		return msg;

	}
	public String getEntity(String entityId){
		if (entityId == null)
			return "error";
		Map map = new HashMap();
	
		try {
			map = dao.queryEntity(entityId);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pack2Json(map);
	}
	
	public String addDetail(String jsonStr) {
		String msg = "true";
		try {
			dao.addDetail(json2Map(jsonStr));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg = "false";
		}
		return msg;
	}
	
	public String updateDetail(String jsonStr) {
		String msg = "true";
		try {
			dao.updateDetail(json2Map(jsonStr));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg = "false";
		}
		return msg;
	}
	
	public String getDetailsList(String entityId){
		if (entityId==null)
			return "error";
		Map map = new HashMap();
		try {
			map=dao.detailsList(entityId);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pack2Json(map);
		
	}
	
	public String getDetails(String detailId){
		if (detailId==null)
			return "error";
		Map map = new HashMap();
		try {
			map=dao.getDetails(detailId);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pack2Json(map);
		
	}
	
	public String delDetails(String detailIdStr) {
		String msg = "true";
		try {
			dao.delDetails(detailIdStr);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg = "false";
		}
		return msg;
	}
	
	public String delEntity(String entityId){
		String msg = "true";
		try {
			dao.delEntity(entityId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg="false";
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg = "false";
		}
		return msg;
	}
	
	public String checkTargetCondition(String tableName, String condition) {
		String msg = dao.checkTargetCondition(tableName, condition);
		return msg;
	}
	
	private String pack2Json(Object obj) {
		return JSONObject.fromObject(obj).toString();
	}
	
	private Map json2Map(String jsonStr) {
		JSONObject a = JSONObject.fromObject(jsonStr);
		Map map = (Map)a;
		return map;
	}
	
	public void free() {
		try {
			dao.close();
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
