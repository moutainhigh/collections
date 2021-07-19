package com.gwssi.datacenter.dataResource.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.application.common.AppConstants;
import com.gwssi.datacenter.model.DcViewBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.util.StringUtil;

@Service(value = "viewService")
public class ViewService extends BaseService{
	
	/**
	 * 通过条件对视图进行查询
	 * @param param
	 * @return 视图集合
	 * @throws OptimusException 
	 */
	public List queryViewList(Map param) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//获取数据
		String dataSourceName = StringUtil.getMapStr(param, "dataSourceName").trim();
		String viewName = StringUtil.getMapStr(param, "viewName").trim();
		String pkDcBusiObject = StringUtil.getMapStr(param, "pkDcBusiObject").trim();
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		sql.append("select t.*,o.BUSI_OBJECT_NAME  from (select v.*,s.PK_DC_BUSI_OBJECT,s.DATA_SOURCE_NAME from DC_VIEW v ,DC_DATA_SOURCE s WHERE s.PK_DC_DATA_SOURCE=v.PK_DC_DATA_SOURCE )t ")
			.append(" LEFT JOIN DC_BUSI_OBJECT o on o.PK_DC_BUSI_OBJECT = t.PK_DC_BUSI_OBJECT where t.EFFECTIVE_MARKER=?");
			
		listParam.add(AppConstants.EFFECTIVE_Y);
		
		//当dataSourceName不为空时
		if(StringUtils.isNotEmpty(dataSourceName)){
			sql.append(" and t.DATA_SOURCE_NAME like ?");
			listParam.add("%"+dataSourceName+"%");
		}
		
		//当viewName不为空时
		if(StringUtils.isNotEmpty(viewName)){
			sql.append(" and ( lower(t.VIEW_NAME) like ? or upper(t.VIEW_NAME) like ? )");
			listParam.add("%"+viewName+"%");
			listParam.add("%"+viewName+"%");
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
	 * 查询业务系统
	 * @return 业务系统集合
	 * @throws OptimusException
	 */
	public List queryBusiObjectName() throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		sql.append("select PK_DC_BUSI_OBJECT as value, BUSI_OBJECT_NAME as text from DC_BUSI_OBJECT");
		
		return dao.queryForList(sql.toString(), null);
	}
	
	/**
	 * 通过主键查询已有的视图信息(回显)
	 * @param pkDcView
	 * @return 视图信息集合
	 * @throws OptimusException
	 */
	public List<Map> queryViewListById(String pkDcView) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		/*sql.append("select v.*,o.BUSI_OBJECT_NAME from DC_VIEW v,DC_DATA_SOURCE s,DC_BUSI_OBJECT o where v.PK_DC_DATA_SOURCE=s.PK_DC_DATA_SOURCE")
			.append(" and s.PK_DC_BUSI_OBJECT=o.PK_DC_BUSI_OBJECT and PK_DC_VIEW=?");*/
		
		sql.append("select t.*,o.BUSI_OBJECT_NAME  from (select v.*,s.PK_DC_BUSI_OBJECT,s.DATA_SOURCE_NAME from DC_VIEW v ,DC_DATA_SOURCE s WHERE s.PK_DC_DATA_SOURCE=v.PK_DC_DATA_SOURCE )t ")
			.append(" LEFT JOIN DC_BUSI_OBJECT o  on o.PK_DC_BUSI_OBJECT = t.PK_DC_BUSI_OBJECT where t.PK_DC_VIEW=?");
		listParam.add(pkDcView);
		
		//封装结果集
		return dao.queryForList(sql.toString(), listParam);
		
		
	}
	
	/**
	 * 保存修改的视图信息
	 * @param dcViewBO
	 * @throws OptimusException
	 */
	public void saveUpdate(DcViewBO dcViewBO) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		dao.update(dcViewBO);
		
		
	}

}
