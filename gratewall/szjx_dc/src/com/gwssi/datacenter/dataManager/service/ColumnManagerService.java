/**
 * 
 */
package com.gwssi.datacenter.dataManager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.application.common.AppConstants;
import com.gwssi.datacenter.model.DcColumnBO;
import com.gwssi.datacenter.model.DcDataSourceBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

/**
 * @author xiaohan
 *
 */
@Service
public class ColumnManagerService extends BaseService {

	/**
	 * 字段管理-根据id查询
	 * 
	 * @param pkDcColumn
	 *            id主键
	 * @return DcColumnBO
	 * @throws OptimusException
	 */
	public DcColumnBO findColumnById(String pkDcColumn) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		DcColumnBO colunmbo = dao.queryByKey(DcColumnBO.class, pkDcColumn);
		return colunmbo;
	}

	/**
	 * 字段管理-更新
	 * 
	 * @param colunmbo
	 *            bo对象
	 * @return
	 * @throws OptimusException
	 */
	public void updateColumn(DcColumnBO colunmbo) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		dao.update(colunmbo);
	}

	/**
	 * 字段管理-查询
	 * 
	 * @param parmas
	 *            查询字段对象集合
	 * @param pkDcDataSource
	 *            数据源主键
	 * @param pkDcTable
	 *            表主键
	 * @return List<Map>
	 * @throws OptimusException
	 */
	public List<Map> findColumnByList(Map<String, String> parmas,
			String pkDcDataSource, String pkDcTable) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from DC_COLUMN where EFFECTIVE_MARKER = ? and PK_DC_DATA_SOURCE = ? and PK_DC_TABLE = ? ");
		List listParam = new ArrayList();
		listParam.add(AppConstants.EFFECTIVE_Y);
		listParam.add(pkDcDataSource);
		listParam.add(pkDcTable);
		if (parmas != null) {
			String columnNameCn = parmas.get("columnNameCn").trim();
			if (!"".equals(columnNameCn)) {
				sql.append(" and  COLUMN_NAME_CN like ? ");
				listParam.add("%" + columnNameCn + "%");
			}

			String columnNameEn = parmas.get("columnNameEn").trim();
			if (!"".equals(columnNameEn)) {
				sql.append(" and UPPER(COLUMN_NAME_EN) LIKE  ? ");
				listParam.add("%" +columnNameEn.toUpperCase()+ "%");
			}

//			String columnType = parmas.get("columnType").trim();
//			if (!"".equals(columnType)) {
//				sql.append(" and UPPER(COLUMN_TYPE) LIKE ? ");
//				listParam.add("%" +columnType.toUpperCase()+ "%");
//			}

		}
		List<Map> ColumnList = dao.pageQueryForList(sql.toString(), listParam);
		return ColumnList;
	}

	/**
	 * 字段管理-查询主键字段
	 * 
	 * @param pkDcDataSource
	 *            数据源主键
	 * @param pkDcTable
	 *            表主键
	 * @return List<Map>
	 * @throws OptimusException
	 */
	public List<Map> findColumnByList(String pkDcDataSource, String pkDcTable)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append(" select COLUMN_NAME_EN from DC_COLUMN where EFFECTIVE_MARKER = ? and PK_DC_DATA_SOURCE = ? and PK_DC_TABLE = ? and IS_PRIMARY_KEY = ? ");
		List listParam = new ArrayList();
		listParam.add(AppConstants.EFFECTIVE_Y);
		listParam.add(pkDcDataSource);
		listParam.add(pkDcTable);
		listParam.add(AppConstants.EFFECTIVE_Y);
		List<Map> ColumnList = dao.queryForList(sql.toString(), listParam);
		return ColumnList;
	}
	
	
	/**
	 * 字段管理-查询主键字段
	 * 
	 * @param pkDcDataSource
	 *            数据源主键
	 * @param pkDcTable
	 *            表主键
	 * @return List<Map>
	 * @throws OptimusException
	 */
	public List<Map> findRelationColumnByList(String pkDcTable)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append(" select PK_DC_COLUMN as value , COLUMN_NAME_EN as text from DC_COLUMN where EFFECTIVE_MARKER = ? and PK_DC_TABLE = ? ");
		List listParam = new ArrayList();
		listParam.add(AppConstants.EFFECTIVE_Y);
		//listParam.add(pkDcDataSource);
		listParam.add(pkDcTable);
		List<Map> ColumnList = dao.queryForList(sql.toString(), listParam);
		return ColumnList;
	}
	/**
	 * 获取标准数据元
	 * @return
	 * @throws OptimusException
	 */
	public List doqueryStand() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		
		//编写sql语句
        String sql = "select t.identifier as value ,t.column_nane||'('||t.column_nane_cn||')' as text from DC_STANDARD_DATAELEMENT t where t.effective_marker =?";
		List listParam = new ArrayList();
		listParam.add(AppConstants.EFFECTIVE_Y);
        //封装结果集
        List systemList = dao.queryForList(sql, listParam);
        
        return systemList;
	}
	public List doqueryCode()throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		
		//编写sql语句
        String sql = "select t.standard_codeindex as value, t.standard_codeindex||'('||t.codeindex_name||')' as text from DC_STANDARD_CODEINDEX  t where t.effective_marker =?";
		List listParam = new ArrayList();
		listParam.add(AppConstants.EFFECTIVE_Y);
        //封装结果集
        List systemList = dao.queryForList(sql, listParam);
        
        return systemList;
	}

	public List getALLCodeOrStand() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		
		//编写sql语句
        String sql = "select t.standard_codeindex as value, t.standard_codeindex||'('||t.codeindex_name||')' as text from DC_STANDARD_CODEINDEX  t where t.effective_marker =? union  ";
        String sql2 = "select t.identifier as value ,t.column_nane||'('||t.column_nane_cn||')' as text from DC_STANDARD_DATAELEMENT t where t.effective_marker =?";
        sql =sql +sql2;
		List listParam = new ArrayList();
		listParam.add(AppConstants.EFFECTIVE_Y);
		listParam.add(AppConstants.EFFECTIVE_Y);
        //封装结果集
        List systemList = dao.queryForList(sql, listParam);
        
        return systemList;
	}
	/**
	 * 获取数据源
	 * @param pkDcDataSource
	 * @return
	 * @throws OptimusException
	 */
	public DcDataSourceBO doGetDcDataSource(String pkDcDataSource) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		
		return dao.queryByKey(DcDataSourceBO.class,pkDcDataSource);
	}

	public List doquerySysCodeSet(String pkDcBusiObject) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		
		//编写sql语句
        String sql = "select t.dc_dm_id as value, t.dc_dm_dm || '(' || t.dc_dm_mc || ')' as text   from dc_dm_jcdm t  where t.effective_marker = ? and t.pk_dc_busi_object =?  ";
		List listParam = new ArrayList();
		listParam.add(AppConstants.EFFECTIVE_Y);
		listParam.add(pkDcBusiObject);
        //封装结果集
        List systemList = dao.queryForList(sql, listParam);
        
        return systemList;
	}

}
