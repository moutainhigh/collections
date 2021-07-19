package com.gwssi.report.tzsb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service
public class RegService extends BaseService {
	private static Logger log = Logger.getLogger(RegService.class);

	// 查询局
	public List<Map<String, Object>> queryCode_value(String... params) throws OptimusException {
		IPersistenceDAO dao_dc = getPersistenceDAO("dc_dc");
		StringBuffer sql = new StringBuffer();
		//sql.append("select distinct fjdm  as value, fjmc as text  from v_jg_ent ");
		sql.append("SELECT c.code_value,c.old_code_value as value,code_name as text FROM CODEDATA C WHERE C.CODE_INDEX = 'CA11' order by value");
		List list = dao_dc.queryForList(sql.toString(), null);
		return list;
	}

	// 所属每个局下面对应的所有工商所
	public List<Map<String, Object>> getAdminbrancode(String adminbrancode) throws OptimusException {
		IPersistenceDAO dao_dc = getPersistenceDAO("dc_dc");
		String param = "";
		StringBuffer sql = new StringBuffer();
		List list = null;
		List<String> str = new ArrayList<String>();// 参数准备
		param = adminbrancode;
		//sql.append("select distinct gssdm as value,gssmc as text from v_jg_ent where gssdm= ? order by gssdm ");
		sql.append("select distinct gssmc as text,gssdm as value from v_jg_ent where fjdm= ?");
		
		str.add(param);
		list = dao_dc.queryForList(sql.toString(), str);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}
}
