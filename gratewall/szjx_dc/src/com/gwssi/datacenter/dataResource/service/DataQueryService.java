package com.gwssi.datacenter.dataResource.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.application.common.AppConstants;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.util.StringUtil;

@Service(value = "dataQueryService")
public class DataQueryService extends BaseService{
	
	/**
	 * 通过条件查询元数据
	 * @param param
	 * @return
	 * @throws OptimusException
	 */
	public List queryData(Map param) throws OptimusException  {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//获取数据
		String tableNameEn =  StringUtil.getMapStr(param, "tableNameEn").trim();
		String tableNameCn =  StringUtil.getMapStr(param, "tableNameCn").trim();
		String columnNameEn =  StringUtil.getMapStr(param, "columnNameEn").trim();
		String columnNameCn =  StringUtil.getMapStr(param, "columnNameCn").trim();
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		/*sql.append("select c.*,t.TABLE_NAME_EN,o.BUSI_OBJECT_NAME from DC_COLUMN c,DC_TABLE t,DC_DATA_SOURCE s,DC_BUSI_OBJECT o where c.PK_DC_TABLE=t.PK_DC_TABLE and c.PK_DC_DATA_SOURCE=s.PK_DC_DATA_SOURCE and s.PK_DC_BUSI_OBJECT=o.PK_DC_BUSI_OBJECT and c.EFFECTIVE_MARKER=? and c.IS_CHECK=?");*/
		/*sql.append("select m.*,o.BUSI_OBJECT_NAME from  (select c.*,t.TABLE_NAME_EN,s.PK_DC_BUSI_OBJECT from DC_COLUMN c,DC_TABLE t,DC_DATA_SOURCE s ")
			.append("where c.PK_DC_DATA_SOURCE=t.PK_DC_DATA_SOURCE and s.PK_DC_DATA_SOURCE=c.PK_DC_DATA_SOURCE)m ")
			.append("LEFT JOIN DC_BUSI_OBJECT o on o.PK_DC_BUSI_OBJECT=m.PK_DC_BUSI_OBJECT where  m.EFFECTIVE_MARKER=?");
		
		listParam.add(AppConstants.EFFECTIVE_Y);*/
		
		sql.append("select t.table_name_en, t.table_name_cn, t.dc_topic, c.column_name_en, c.column_name_cn from dc_table t, dc_column c where t.pk_dc_table = c.pk_dc_table and t.dc_topic is not null and t.effective_marker= ? and  c.effective_marker = ?");
		listParam.add(AppConstants.EFFECTIVE_Y);
		listParam.add(AppConstants.EFFECTIVE_Y);
		
		//当tableNameEn不为空时
		if(StringUtils.isNotEmpty(tableNameEn)){
			sql.append(" and ( lower(t.TABLE_NAME_EN) like ? or upper(t.TABLE_NAME_EN) like ?  )");
			listParam.add("%"+tableNameEn+"%");
			listParam.add("%"+tableNameEn+"%");
		}
		
		//当tableNameCn不为空时
		if(StringUtils.isNotEmpty(tableNameCn)){
			sql.append(" and t.TABLE_NAME_CN like ?");
			listParam.add("%"+tableNameCn+"%");
		}
		
		//当columnNameEn不为空时
		if(StringUtils.isNotEmpty(columnNameEn)){
			sql.append(" and ( lower(c.COLUMN_NAME_EN) like ? or upper(c.COLUMN_NAME_EN) like ?  )");
			listParam.add("%"+columnNameEn+"%");
			listParam.add("%"+columnNameEn+"%");
		}
		
		//当columnNameCn不为空时
		if(StringUtils.isNotEmpty(columnNameCn)){
			sql.append(" and c.COLUMN_NAME_CN like ?");
			listParam.add("%"+columnNameCn+"%");
		}
		sql.append(" order by t.dc_topic, t.table_name_en");
		return dao.pageQueryForList(sql.toString(), listParam);
	}
	
	/**
	 * 获取业务对象表的主键和 名称
	 * @return
	 * @throws OptimusException 
	 */
	public List<Map<String, Object>> findKeyValueDcBusiObjectBO() throws OptimusException {
		IPersistenceDAO dao=getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		List<String> str = new ArrayList<String>();

		 sql.append("select PK_DC_BUSI_OBJECT as value, BUSI_OBJECT_NAME  as text from DC_BUSI_OBJECT where EFFECTIVE_MARKER= ? ");
		 str.add(AppConstants.EFFECTIVE_Y);

		
		List list1 =dao.queryForList(sql.toString(),str);
		return list1;
	}
	
	/**
	 * 获取表信息表的主键和 表名
	 * @return
	 * @throws OptimusException 
	 */
	public List<Map<String, Object>> findKeyValueDcTableBO() throws OptimusException {
		IPersistenceDAO dao=getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		List<String> str = new ArrayList<String>();

		 sql.append("select PK_DC_Table as value, TABLE_NAME_EN  as text from DC_TABLE where EFFECTIVE_MARKER= ? ");
		 str.add(AppConstants.EFFECTIVE_Y);

		
		List list1 =dao.queryForList(sql.toString(),str);
		return list1;
	}

}
