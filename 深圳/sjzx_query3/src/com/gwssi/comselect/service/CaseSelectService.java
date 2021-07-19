package com.gwssi.comselect.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.application.log.aspect.LogUtil;
import com.gwssi.comselect.model.CaseSelectQueryBo;
import com.gwssi.comselect.model.EntSelectQueryBo;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.util.QueryCodeSql;

@Service("caseSelectService")
public class CaseSelectService extends BaseService {
	
	/**
	 * 该数据源目前没确定 再改
	 * @return
	 */
	private String getDetail_datasourcekey(){
		Properties properties = ConfigManager.getProperties("optimus");
		
		String key= properties.getProperty("regDetail.datasourcekey");

		return key;
	}

	public List<?> queryPageQuery(CaseSelectQueryBo bo, HttpServletRequest httpServletRequest) throws OptimusException {
		String querytype="page";
		List<Map> list=queryCase(bo, querytype,httpServletRequest);
		return list;
	}
	
	public List<Map> queryCase(CaseSelectQueryBo bo, String querytype,HttpServletRequest req) throws OptimusException{
		
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * "
//		sql.append("select b.ID,b.CASENO,b.CASENAME,b.LITIGANTCERNO, "
//				+ "b.CASEREGISTERTIME,b.CASEENDTIME,b.LITIGANTTYPE,b.LITIGANTNAME "
//				+ "p.PARTYTYPE,p.UNITNAME,p.REGNO,p.UNISCID, "
//				+ "p.CERNO "
				+ "from DC_CASE_CASE b "
//				+ "left join  "
//				+ "CASE_CF_PARTYINFO p "
//				+ "on b.CASEID = p.CASEID  "
				+ " where 1=1  ");
		
		if(StringUtils.isNotBlank(bo.getCaseNo())){
			sql.append("  and CASENO = ?     ");
			params.add(bo.getCaseNo());
		}
//		if(StringUtils.isNotBlank(bo.getCaseName())){
//			sql.append("  and CASENAME=?     ");
//			params.add(bo.getCaseName());
//		}
//		if(StringUtils.isNotBlank(bo.getCaseNo())){
//			sql.append("  and CASESTATE = ?    ");
//			params.add(bo.getCaseNo());
//		}
		if(StringUtils.isNotBlank(bo.getCaseFiauth())){
			sql.append("  and CASEFIAUTH = ?    ");
			params.add(bo.getCaseFiauth());
		}
		if(StringUtils.isNotBlank(bo.getCaseFiDate())){
			sql.append("  and CASEFIDATE = to_date(?,'yyyy-MM-dd')    ");
			params.add(bo.getCaseFiDate());
		}if(StringUtils.isNotBlank(bo.getCloCaseDate())){
			sql.append("   and CLOCASEDATE = to_date(?,'yyyy-MM-dd')    ");
			params.add(bo.getCloCaseDate());
		}if(StringUtils.isNotBlank(bo.getLITIGANTTYPE())){
			sql.append("  and LITIGANTTYPE = ?    ");
			params.add(bo.getLITIGANTTYPE());
		}if(StringUtils.isNotBlank(bo.getUnitName())){
			sql.append("  and UNITNAME = ?   ");
			params.add(bo.getUnitName());
		}if(StringUtils.isNotBlank(bo.getRegNo())){
			sql.append("  and REGNO = ?   ");
			params.add(bo.getRegNo());
		}if(StringUtils.isNotBlank(bo.getUniScid())){
			sql.append("  and UNISCID = ?   ");
			params.add(bo.getUniScid());
		}if(StringUtils.isNotBlank(bo.getCerNo())){
			sql.append("  and CERNO = ?   ");
			params.add(bo.getCerNo());
		}
		
		
		System.out.println(sql);
		System.out.println(params);
		if("page".equals(querytype)){
			List<Map> pageQueryForList = dao.pageQueryForList(sql.toString(), params);
			LogUtil.insertLog("案件查询", sql.toString(), params.toString(), req, dao);
			return pageQueryForList;
		}else if("excel".equals(querytype)){//这里需要修改  改为视图
			String sql33="select * from (  select t_.*, rownum as rownum_ from (";
			sql33=sql33+sql.toString()+") t_ where rownum <= 5000 ) where rownum_ > 0";
			//这处需要优化
			List<Map> pageQueryForList = ComSelectService.typechage(dao.queryForList(sql33.toString(), params));
			LogUtil.insertLog("excel导出_case", sql33, params.toString(), req, dao);
			return pageQueryForList;
		}else{
			String sql33="select * from (  select t_.*, rownum as rownum_ from (";
			sql33=sql33+sql.toString()+") t_ where rownum <= 100 ) where rownum_ > 0";
			//这处需要优化
			List<Map> pageQueryForList = ComSelectService.typechage(dao.queryForList(sql33.toString(), params));
			LogUtil.insertLog("案件查询", sql33, params.toString(), req, dao);
			return pageQueryForList;
		}
	}
	
	
	/**
	 * 获取代码值
	 * @param type
	 * @param parm
	 * @return
	 * @throws OptimusException 
	 */
	public List<Map<String, Object>> queryCode_value(String...params) throws OptimusException {
		IPersistenceDAO dao=getPersistenceDAO(ComSelectService.getDc_code_KEY());
		List list = dao.queryForList(QueryCodeSql.QueryCodeSql(params),null);
		return list;
	}
	
	/**
	 * excel导出数据的查/**
	 * excel导出查询
	 * @param bo
	 * @param request 
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryCaseExcel(CaseSelectQueryBo bo, HttpServletRequest request) throws OptimusException{
		String querytype="excel";
		List<Map> list=queryCase(bo, querytype,request);
		return list;
	}
	
}
