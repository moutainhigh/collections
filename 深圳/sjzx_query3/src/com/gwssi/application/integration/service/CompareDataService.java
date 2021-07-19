package com.gwssi.application.integration.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Service;

import com.gwssi.application.integration.model.EntBo;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service
public class CompareDataService extends BaseService {

	/* 拷贝企业信息库 */
	/*private static String getDetail_datasourcekey() {
		Properties properties = ConfigManager.getProperties("optimus");
		String key = properties.getProperty("dc_query.datasourcekey");
		return key;
	}*/

	public Map query(String tid) throws OptimusException {
		if (StringUtils.isEmpty(tid)) {
			return null;
		}
		List<Map> resultList = null;
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		params.add(tid);
		StringBuffer sql = new StringBuffer();
		// sql.append("select
		// t.tid,t.regno,t.entname,t.enttype,e.regno,e.entname,e.enttype from
		// t_uploadExcel t left join ent_select e on t.regno != e.regno where
		// t.tid=?");

		sql.append("select  count(1) count_sum, nvl(sum(case when recid is not null then 1 end),0) count_match from ( "
				+ " select t.tid, t.regno, t.entname, t.enttype,e.recid, e.regno,e.entname,e.enttype  from t_uploadExcel1 t  left join V_DC_RA_MER_BASE_compare e"
				+ "   on t.regno = e.regno and t.entname = e.entname and t.enttype = e.enttype where t.tid = ?)");

		resultList = dao.queryForList(sql.toString(), params);
		if (resultList != null && resultList.size() == 0) {
			return null;// 数据库与用户上传的一致
		}
		return resultList.get(0);
	}

	public String save(List lists) throws OptimusException {
		if (lists != null && lists.size() > 0) {
			IPersistenceDAO dao = getPersistenceDAO("dc_dc");
			dao.insert(lists);
			return ((EntBo) lists.get(0)).getTid();
		} else {
			return "";
		}

	}

	
	/**
	 * jazz里面匹配成功是1，不成功是2 
	 * @param tid
	 * @param resultType
	 * @return
	 * @throws OptimusException
	 */
	public List getList(String tid,int resultType) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		params.add(tid);
		StringBuffer sql = new StringBuffer();
		sql.append("select t.tid,	t.regno, t.entname, t.enttype, case when e.regno is ")
			.append(" null then '比对失败' else '比对成功' end as result ")
			.append(" from t_uploadExcel1 t ")
			.append(" left join V_DC_RA_MER_BASE_compare e  on t.regno = e.regno  and t.entname = e.entname and t.enttype = e.enttype where t.tid = ? ");
		
		if(resultType==1){
			sql.append(" and e.regno is not null");
		}
		if(resultType==2){
			sql.append(" and e.regno is null");
		}
		
		List list = dao.pageQueryForList(sql.toString(), params);
		return list;
	}

}
