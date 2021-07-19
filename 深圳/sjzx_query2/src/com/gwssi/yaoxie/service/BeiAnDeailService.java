package com.gwssi.yaoxie.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.yaoxie.util.Util;

@Service
public class BeiAnDeailService extends BaseService {
	private static Logger log = Logger.getLogger(BeiAnDeailService.class);

	public List<Map> queryDetail(String id) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		String sql = "select  * from V_DC_YAOXIE_QUERY  t where t.id = ? ";
		List list = new ArrayList();
		//list.add(pripid);
		//list.add(certno);
		list.add(id);
		//return dao.queryForList(sql, list);
		return Util.typechage(dao.pageQueryForList(sql.toString(), list));
	}
	
	
	public List<Map> queryYaoJianZhuTiDetail(String id) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sb = new StringBuffer();
		sb.append("select  * from v_dc_yaoxie_yj_zhu_ti  t where 1 = 1  ");
		List list = new ArrayList();
		if(StringUtils.isNotEmpty(id)) {
			sb.append(" and t.id = ?");
			
		}
		list.add(id);
		//return dao.queryForList(sql, list);
		return Util.typechage(dao.pageQueryForList(sb.toString(), list));
	}
}
