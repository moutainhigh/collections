package com.gwssi.comselect.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.comselect.model.CaseSelectQueryBo;
import com.gwssi.comselect.model.ResumeQueryBo;
import com.gwssi.comselect.service.ComSelectService;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.util.QueryCodeSql;

@Service("resumeProService")
public class ResumeProService extends BaseService {
	
	/**
	 * 该数据源目前没确定 再改
	 * @return
	 */
	private String getDetail_datasourcekey(){
		Properties properties = ConfigManager.getProperties("optimus");
		
		String key= properties.getProperty("regDetail.datasourcekey");

		return key;
	}

	public List<?> queryPageQuery(ResumeQueryBo bo) throws OptimusException {
		String querytype="page";
		List<Map> list=queryResumePro(bo, querytype);
		return list;
	}
	
	/**
	 * 12315消保列表查询
	 * @param bo
	 * @param querytype
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryResumePro(ResumeQueryBo bo, String querytype) throws OptimusException{
		
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		
		ArrayList<String> params = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		sql.append("select regino,"
				+ "infoori,"
				+ "regdep,"
				+ "accregper,"
				+ "regtime "
				+ "from AI_12315_REG_INFO where 1=1 ");
		
		if(StringUtils.isNotBlank(bo.getRegIno())){
			sql.append("  and regino = ?     ");
			params.add(bo.getRegIno());
		}
		if(StringUtils.isNotBlank(bo.getInfoOri())){
			sql.append("  and infoori = ?     ");
			params.add(bo.getInfoOri());
		}
		if(StringUtils.isNotBlank(bo.getRegDep())){
			sql.append("  and regdep = ?     ");
			params.add(bo.getRegDep());
		}
		if(StringUtils.isNotBlank(bo.getAccRegper())){
			sql.append("  and accregper = ?     ");
			params.add(bo.getAccRegper());
		}
		if(StringUtils.isNotBlank(bo.getRegTime())){
			sql.append("  and regtime = ?     ");
			params.add(bo.getRegTime());
		}
		if(StringUtils.isNotBlank(bo.getInfoType())){
			sql.append("  and infotype = ?     ");
			params.add(bo.getInfoType());
		}
		
		System.out.println(sql);
		System.out.println(params);
		if("page".equals(querytype)){
			return dao.pageQueryForList(sql.toString(), params);
		}else if("excel".equals(querytype)){//这里需要修改  改为视图
			String sql33="select * from (  select t_.*, rownum as rownum_ from (";
			sql33=sql33+sql.toString()+") t_ where rownum <= 5000 ) where rownum_ > 0";
			//这处需要优化
			return ComSelectService.typechage(dao.queryForList(sql33.toString(), params));
		}else{
			String sql33="select * from (  select t_.*, rownum as rownum_ from (";
			sql33=sql33+sql.toString()+") t_ where rownum <= 100 ) where rownum_ > 0";
			//这处需要优化
			return ComSelectService.typechage(dao.queryForList(sql33.toString(), params));
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
	 * excel导出数据查询
	 * @param bo
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryCaseExcel(ResumeQueryBo bo) throws OptimusException{
		String querytype="excel";
		List<Map> list=queryResumePro(bo, querytype);
		return list;
	}
}
