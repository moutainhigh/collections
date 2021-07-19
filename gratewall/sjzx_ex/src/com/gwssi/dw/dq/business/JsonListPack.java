package com.gwssi.dw.dq.business;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.dw.dq.DqContants;
import com.gwssi.dw.dq.dao.JsonListDao;

public class JsonListPack
{
	/**
	 * @param method
	 * @param args 0Ϊid,1ΪtableName,2ΪsubjectId
	 * @return
	 * @throws DBException
	 */
	public String get(String method, String[] args) throws DBException {
		if (method == null) {
			return null;
		}
		List result = null;
		JsonListDao jld = new JsonListDao(DqContants.CON_TYPE);
		if (method.equals("getSubjectList")) {
			result = jld.subjectList();
		}
		else if (method.equals("getTableList")) {
			result = jld.tableList(args[0]);
		}
		else if (method.equals("getColumnList")) {
			result = jld.columnList(args[0]);
		}
		else if (method.equals("queryColumnList")) {
			result = jld.queryColumn(args[1], args[2]);
		}
		else if (method.equals("getEntityList")) {
			result = jld.entityList(args[0]);
		}
		else if (method.equals("getTargetList")) {
			result = jld.targetList(args[0]);
		}
		else if (method.equals("queryTargetList")) {
			result = jld.queryTargetList(args[0]);
		}
		else if (method.equals("getGroupList")) {
			result = jld.groupList(args[0], args[1]);
		}
		else if (method.equals("queryGroupList")) {
			result = jld.queryGroupList(args[0]);
		}
		else if (method.equals("foreignEntityList")) {
			result = jld.foreignEntityList(args[0]);
		}
		else if (method.equals("subjoinEntityList")) {
			result = jld.subjoinEntityList(args[0]);
		}
		else if (method.equals("allParamList")) {
			result = jld.allParamList();
		}
		else if (method.equals("getTable")){
			result = jld.getTable(args[0]);
		}
		else if (method.equals("globalForeignEntityList")) {
			result = jld.globalForeignEntityList(args[0]);
		}
		else if (method.equals("getGroupTempList")) {
			result = jld.getGroupTempList(args[0]);
		}
		return pack2Json(result);
	}

	public String paramXML(InputStream is) {
		ParamXMLAnalyze analyzeParam = new ParamXMLAnalyze(is);
		return analyzeParam.trans2Json();
	}
	private String pack2Json(List dataList) {
		Map map = new HashMap();
		map.put("totalCount", dataList.size() + "");
		map.put("records", dataList);
		return JSONObject.fromObject(map).toString();
	}

}
