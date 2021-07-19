/**
 * 
 */
package com.gwssi.application.integration.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.application.common.AppConstants;
import com.gwssi.application.model.SmRoleBO;
import com.gwssi.application.model.SmSysIntegrationBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

/**
 * @author xiaohan
 *
 */
@Service
public class IntegrationService extends BaseService {

	/**
	 * 应用集成-系统新增
	 * 
	 * @param integrationbo 系统bo对象
	 * @return
	 * @throws OptimusException
	 */
	public void saveIntegration(SmSysIntegrationBO integrationbo) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		dao.insert(integrationbo);
	}
	
	/**
	 * 应用集成-根据id系统查询
	 * 
	 * @param pkSysIntegration 系统id主键
	 * @return
	 * @throws OptimusException
	 */
	public SmSysIntegrationBO findIntegrationById(String pkSysIntegration) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		SmSysIntegrationBO integrationbo = dao.queryByKey(SmSysIntegrationBO.class, pkSysIntegration);
		return integrationbo;
	}
	
	/**
	 * 应用集成-系统更新
	 * 
	 * @param integrationbo 系统bo对象
	 * @return
	 * @throws OptimusException
	 */
	public void updateIntegration(SmSysIntegrationBO integrationbo) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		dao.update(integrationbo);
	}
	
	/**
	 * 应用集成-系统查询
	 * 
	 * @param paramObject 系统查询字段对象集合
	 * @return
	 * @throws OptimusException
	 */
	public List<SmSysIntegrationBO> findByIdIntegration(List paramObject) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		List<SmSysIntegrationBO> integrationList = dao.queryByKey(SmSysIntegrationBO.class, paramObject);
		return integrationList;
	}
	
	/**
	 * 应用集成-系统查询
	 * 
	 * @param paramObject 系统查询字段对象集合  flag标识位，当前用户是否为超级管理员
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findIntegration(Map<String,String> parmas ,String flag ,List listCode) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.*, "+flag+" as ISSUPER ,(select FIRM_NAME_SHORT from SM_FIRM where PK_SM_FIRM = a.PK_SM_FIRM) as FIRMNAME , (select SM_LIKEMAN from SM_LIKEMAN where PK_SM_LIKEMAN = a.PK_SM_LIKEMAN) as LINKMAN from SM_SYS_INTEGRATION a where a.EFFECTIVE_MARKER = ? ");
		List listParam = new ArrayList();
		listParam.add(AppConstants.EFFECTIVE_Y);
		if("0".equals(flag)){
			sql.append(" and a.PK_SYS_INTEGRATION in ( " );
			for(Object code:listCode){
				sql.append(" ? , ");
				listParam.add(code.toString());
			}
			sql.append(" ? ) " );
			//0占位符
			listParam.add("0");
		}
		
		if(parmas != null){
			String systemName = parmas.get("systemName").trim();
			if(!"".equals(systemName)){
				sql.append(" and  a.SYSTEM_NAME like ? ");
				listParam.add("%"+systemName+"%");
			}
			String systemCode = parmas.get("systemCode").trim();
			if(!"".equals(systemCode)){
				sql.append(" and ( lower(a.SYSTEM_CODE) like ? or upper(a.SYSTEM_CODE) like ?  ) ");
				listParam.add("%"+systemCode+"%");
				listParam.add("%"+systemCode+"%");
			}
		}
		sql.append(" order by a.ORDER_NO ASC"); 
		List<Map> integrationList = dao.pageQueryForList(sql.toString(), listParam);
		return integrationList;
	}
	
	/**
	 * 应用集成-系统删除
	 * 
	 * @param pkSysIntegrations 系统Id编号集合
	 * @return
	 * @throws OptimusException
	 */
	public void deleteIntegration(String pkSysIntegrations) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		dao.deleteByKey(SmSysIntegrationBO.class, pkSysIntegrations);
	}
	
	/**
	 * 应用集成-系统查询总个数
	 * 
	 * @return int
	 * @throws OptimusException
	 */
	public int getNumIntegration() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		String sql = "select count(1) from SM_SYS_INTEGRATION"; 
		return dao.queryForInt(sql, null);
	}

}
