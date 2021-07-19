package com.gwssi.datacenter.dataResource.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.application.common.AppConstants;
import com.gwssi.datacenter.model.DcTriggerBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.util.StringUtil;

@Service(value = "functionService")
public class FunctionService extends BaseService{

	/**
	 * 查询业务系统
	 * @return 业务系统集合
	 * @throws OptimusException
	 */
	public List queryBusiObjectName() throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		sql.append("select PK_DC_BUSI_OBJECT as value, BUSI_OBJECT_NAME as text from DC_BUSI_OBJECT");
		
		//封装结果集
		return dao.queryForList(sql.toString(), null);
	}
	
	
	/**
	 * 通过条件查询函数
	 * @param param
	 * @return 函数集合
	 * @throws OptimusException 
	 */
	public List queryFunctionList(Map param) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//获取数据
		String dataSourceName = StringUtil.getMapStr(param, "dataSourceName").trim();
		String triggerName = StringUtil.getMapStr(param, "triggerName").trim();
		String pkDcBusiObject = StringUtil.getMapStr(param, "pkDcBusiObject").trim();
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		sql.append("select t.*,o.BUSI_OBJECT_NAME  from (select v.*,s.PK_DC_BUSI_OBJECT,s.DATA_SOURCE_NAME from DC_TRIGGER v ,DC_DATA_SOURCE s WHERE s.PK_DC_DATA_SOURCE=v.PK_DC_DATA_SOURCE )t ")
			.append("LEFT JOIN DC_BUSI_OBJECT o on o.PK_DC_BUSI_OBJECT = t.PK_DC_BUSI_OBJECT where t.EFFECTIVE_MARKER=?");
			
		listParam.add(AppConstants.EFFECTIVE_Y);
		
		//当dataSourceName不为空时
		if(StringUtils.isNotEmpty(dataSourceName)){
			sql.append(" and t.DATA_SOURCE_NAME like ?");
			listParam.add("%"+dataSourceName+"%");
		}
		
		//当triggerName不为空时
		if(StringUtils.isNotEmpty(triggerName)){
			sql.append(" and ( lower(t.TRIGGER_NAME) like ? or upper(t.TRIGGER_NAME) like ? )");
			listParam.add("%"+triggerName+"%");
			listParam.add("%"+triggerName+"%");
		}
		
		//当pkDcBusiObject不为空时
		if(StringUtils.isNotEmpty(pkDcBusiObject)){
			sql.append(" and t.PK_DC_BUSI_OBJECT like ?");
			listParam.add("%"+pkDcBusiObject+"%");
		}
		
		//封装结果集
		return dao.pageQueryForList(sql.toString(), listParam);
	}

	
	/**
	 * 通过主键查询函数信息(回显)
	 * @param pkDcTrigger
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryFunctionListById(String pkDcTrigger) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		/*sql.append("select t.*,o.BUSI_OBJECT_NAME from DC_TRIGGER t,DC_DATA_SOURCE s,DC_BUSI_OBJECT o where t.PK_DC_DATA_SOURCE=s.PK_DC_DATA_SOURCE")
			.append(" and s.PK_DC_BUSI_OBJECT=o.PK_DC_BUSI_OBJECT and t.PK_DC_TRIGGER=?");*/
		sql.append("select t.*,o.BUSI_OBJECT_NAME  from (select v.*,s.PK_DC_BUSI_OBJECT,s.DATA_SOURCE_NAME from DC_TRIGGER v ,DC_DATA_SOURCE s WHERE s.PK_DC_DATA_SOURCE=v.PK_DC_DATA_SOURCE )t ")
		.append("LEFT JOIN DC_BUSI_OBJECT o on o.PK_DC_BUSI_OBJECT = t.PK_DC_BUSI_OBJECT where t.PK_DC_TRIGGER=?");
		listParam.add(pkDcTrigger);
		
		//封装结果集
		return dao.queryForList(sql.toString(), listParam);
	}

	
	/**
	 * 保存修改的数据
	 * @param dcTriggerBO
	 * @throws OptimusException
	 */
	public void saveUpdate(DcTriggerBO dcTriggerBO) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		dao.update(dcTriggerBO);
		
	}
	
	
	
	
}
