/**
 * 
 */
package com.gwssi.datacenter.dataManager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.application.common.AppConstants;
import com.gwssi.datacenter.model.DcRelationBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

/**
 * @author xiaohan
 *
 */
@Service
public class RelationService extends BaseService {
	
	/**
	 * 表管理-查询
	 * 
	 * @param paramObject 查询字段对象集合 
	 * @return List<Map> 
	 * @throws OptimusException
	 */
	public List<Map> findTableByList(Map<String, String> parmas) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from dc_table t where t.dc_topic is not null and t.effective_marker = ?");
		List listParam = new ArrayList();
		listParam.add(AppConstants.EFFECTIVE_Y);
		if (parmas != null) {
			
			String dcTopic = parmas.get("dcTopic").trim();
			if (!"".equals(dcTopic)) {
				sql.append(" and t.dc_topic = ? ");
				listParam.add(dcTopic);
			}
			
			String tableType = parmas.get("tableType").trim();
			if (!"".equals(tableType)) {
				sql.append(" and t.TABLE_TYPE = ? ");
				listParam.add(tableType);
			}
			
			String tableNameCn = parmas.get("tableNameCn").trim();
			if (!"".equals(tableNameCn)) {
				sql.append(" and  t.TABLE_NAME_CN like ? ");
				listParam.add("%" + tableNameCn + "%");
			}
			
			String tableNameEn = parmas.get("tableNameEn").trim();
			if (!"".equals(tableNameEn)) {
				sql.append(" and ( lower(t.TABLE_NAME_EN) like ? or upper(t.TABLE_NAME_EN) like ?  ) ");
				listParam.add("%" + tableNameEn + "%");
				listParam.add("%" + tableNameEn + "%");
			}
			
		}
		
		//sql.append(" ) where rownumber = 1 ");
		List<Map> TableList = dao.pageQueryForList(sql.toString(),
				listParam);
		return TableList;
	}

	/**
	 * 表变更-根据id查询
	 * 
	 * @param pkDcRelationid主键
	 * @return DcRelationBO
	 * @throws OptimusException
	 */
	public DcRelationBO findRelationById(String pkDcRelation)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		DcRelationBO relationbo = dao.queryByKey(DcRelationBO.class,
				pkDcRelation);
		return relationbo;
	}

	/**
	 * 表变更- 保存
	 * 
	 * @param relationbo
	 *            变更对象
	 * @throws OptimusException
	 */
	public void saveRelation(DcRelationBO relationbo) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		dao.insert(relationbo);
	}
	
	/**
	 * 表变更- 更新
	 * 
	 * @param relationbo
	 *            变更对象
	 * @throws OptimusException
	 */
	public void updateRelation(DcRelationBO relationbo) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		dao.update(relationbo);
	}

	/**
	 * 表变更-查询
	 * 
	 * @param paramObject
	 *            查询字段对象集合
	 * @return List<Map>
	 * @throws OptimusException
	 */
	public List<Map> findTableRelationByList(String pkDcDataSource,String pkDcTable)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append(" select n.* from ( select d.DATA_SOURCE_NAME,( select b.BUSI_OBJECT_NAME from DC_BUSI_OBJECT b where b.PK_DC_BUSI_OBJECT = d.PK_DC_BUSI_OBJECT  ) as BUSI_OBJECT_NAME , e.DATA_SOURCE_NAME as DATA_SOURCE_NAME_RELATION , m.*  from DC_RELATION m  ");
		sql.append(" left join DC_DATA_SOURCE d on d.PK_DC_DATA_SOURCE = m.PK_DC_DATA_SOURCE ");
		sql.append(" left join DC_DATA_SOURCE e on e.PK_DC_DATA_SOURCE = m.PK_DC_DATA_SOURCE_RELATION  ) n ");
		sql.append(" where 1 = 1  and EFFECTIVE_MARKER = ? and PK_DC_DATA_SOURCE = ? and PK_DC_TABLE = ? ");
		sql.append(" ORDER BY  CREATER_TIME  DESC  ");
		List listParam = new ArrayList();
		listParam.add(AppConstants.EFFECTIVE_Y);
		listParam.add(pkDcDataSource);
		listParam.add(pkDcTable);
		List<Map> relationList = dao.pageQueryForList(sql.toString(), listParam);
		return relationList;
	}
}
