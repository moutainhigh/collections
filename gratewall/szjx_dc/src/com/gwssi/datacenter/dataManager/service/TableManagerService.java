/**
 * 
 */
package com.gwssi.datacenter.dataManager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.application.common.AppConstants;
import com.gwssi.datacenter.model.DcBusiObjectBO;
import com.gwssi.datacenter.model.DcTableBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

/**
 * @author xiaohan
 *
 */
@Service
public class TableManagerService extends BaseService {

	/**
	 * 表管理-根据id查询
	 * 
	 * @param pkDcTableid主键
	 * @return DcTableBO
	 * @throws OptimusException
	 */
	public DcTableBO findTableById(String pkDcTable)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		DcTableBO tablebo = dao.queryByKey(DcTableBO.class, pkDcTable);
		return tablebo;
	}

	/**
	 * 表管理-更新
	 * 
	 * @param tablebo bo对象
	 * @return
	 * @throws OptimusException
	 */
	public void updateTable(DcTableBO tablebo) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		dao.update(tablebo);
	}

	/**
	 * 表管理-查询
	 * 
	 * @param paramObject 查询字段对象集合 
	 * @return List<Map> 
	 * @throws OptimusException
	 */
	public List<Map> findTableByList(Map<String, String> parmas,String pkDcBusiObject) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from ( select  m.* , ROW_NUMBER()  OVER(PARTITION BY  PK_DC_TABLE, PK_DC_DATA_SOURCE  ORDER BY  CREATER_TIME ) AS rownumber from ( ");
		sql.append(" select   c.TABLE_NAME_EN , c.TABLE_NAME_CN , c.TABLE_TYPE , c.IS_CHECK , c.IS_SHARE , c.IS_QUERY, c.DATA_SOURCE_NAME , c.PK_DC_BUSI_OBJECT ,c.BUSI_OBJECT_NAME ,c.EFFECTIVE_MARKER , c.COLUMN_NAME_EN ,c.COLUMN_NAME_CN , c.CREATER_NAME,  c.CREATER_TIME , c.PK_DC_TABLE, c.PK_DC_DATA_SOURCE  from ( select  a.TABLE_NAME_EN , a.TABLE_NAME_CN ,  a.TABLE_TYPE  , a. IS_CHECK , a.IS_SHARE , a.IS_QUERY, b.DATA_SOURCE_NAME , b.PK_DC_BUSI_OBJECT ,(select BUSI_OBJECT_NAME from DC_BUSI_OBJECT where PK_DC_BUSI_OBJECT = b.PK_DC_BUSI_OBJECT ) as  BUSI_OBJECT_NAME , a. EFFECTIVE_MARKER , a.CREATER_NAME,d.COLUMN_NAME_EN ,d.COLUMN_NAME_CN,a.PK_DC_TABLE, a.PK_DC_DATA_SOURCE , a.CREATER_TIME from DC_Table a  left join  DC_DATA_SOURCE b on a.PK_DC_DATA_SOURCE = b.PK_DC_DATA_SOURCE  right join DC_COLUMN d on d.PK_DC_TABLE  =  a.PK_DC_TABLE  and d.PK_DC_DATA_SOURCE =  a.PK_DC_DATA_SOURCE  ) c where c.EFFECTIVE_MARKER = ? ");
		sql.append(" ) m where 1 = 1  ");
		List listParam = new ArrayList();
		listParam.add(AppConstants.EFFECTIVE_Y);
		//String pkDcBusiObject = parmas.get("pkDcBusiObject").trim();
		if (!"undefined".equals(pkDcBusiObject)) {
			sql.append(" and m.PK_DC_BUSI_OBJECT = ? ");
			listParam.add(pkDcBusiObject);
		}
		else{
			//sql.append(" and m.PK_DC_BUSI_OBJECT is null ");
		}
		if (parmas != null) {
//			String dataSourceName = parmas.get("dataSourceName").trim();
//			if (!"".equals(dataSourceName)) {
//				sql.append(" and  m.DATA_SOURCE_NAME like ? ");
//				listParam.add("%" + dataSourceName + "%");
//			}
			
			String pkDcBusi = parmas.get("pkDcBusiObject").trim();
			if (!"".equals(pkDcBusi)) {
				sql.append(" and  m.PK_DC_BUSI_OBJECT = ? ");
				listParam.add(pkDcBusi);
			}
			
			String tableType = parmas.get("tableType").trim();
			if (!"".equals(tableType)) {
				sql.append(" and m.TABLE_TYPE = ? ");
				listParam.add(tableType);
			}
			
			String tableNameCn = parmas.get("tableNameCn").trim();
			if (!"".equals(tableNameCn)) {
				sql.append(" and  m.TABLE_NAME_CN like ? ");
				listParam.add("%" + tableNameCn + "%");
			}
			
			String tableNameEn = parmas.get("tableNameEn").trim();
			if (!"".equals(tableNameEn)) {
				sql.append(" and ( lower(m.TABLE_NAME_EN) like ? or upper(m.TABLE_NAME_EN) like ?  ) ");
				listParam.add("%" + tableNameEn + "%");
				listParam.add("%" + tableNameEn + "%");
			}
			
		}
		
		sql.append(" ) where rownumber = 1 ");
		List<Map> TableList = dao.pageQueryForList(sql.toString(),
				listParam);
		return TableList;
	}
	
	/**
	 * 业务对象-根据id查询
	 * 
	 * @param pkDcBusiObject主键
	 * @return DcBusiObjectBO
	 * @throws OptimusException
	 */
	public DcBusiObjectBO findBusiById(String pkDcBusiObject)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		DcBusiObjectBO busibo = dao.queryByKey(DcBusiObjectBO.class, pkDcBusiObject);
		return busibo;
	}
	
	/**
	 * 获取数据源代码集
	 * @return
	 * @throws OptimusException 
	 */
	public List<Map<String, Object>> findKeyValueTableBO(String pkDcDataSource) throws OptimusException {
		IPersistenceDAO dao=getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		List<String> str = new ArrayList<String>();
		sql.append("select PK_DC_TABLE as value, TABLE_NAME_EN  as text from DC_TABLE where EFFECTIVE_MARKER= ? and DC_TOPIC = ? ");
		str.add(AppConstants.EFFECTIVE_Y);
		str.add(pkDcDataSource);
		List list1 =dao.queryForList(sql.toString(),str);
		return list1;
	}

}
