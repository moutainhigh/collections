package com.gwssi.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;



public class Codetype extends BaseService {
	public String trsTypeChange(String code, String str) throws OptimusException{
		if(str == null || str.length() == 0){
			return "";
		}
		IPersistenceDAO dao = getPersistenceDAO("dc_code");
		StringBuffer sql = new StringBuffer();
		List<String> params = new ArrayList<String>();
		sql.append("select t.codeindex_value as text from DC_STANDARD_CODEDATA t where t.standard_codeindex = ? and t.codeindex_code = ? ");
		params.add(code);
		params.add(str);
		List<Map> res = dao.queryForList(sql.toString(), params);
		if(res != null){
			return (String)res.get(0).get("text");
		}
		else{
			return "";
		}
	}
}
