/**
 * 
 */
package com.gwssi.datacenter.dataManager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.datacenter.model.DcChangeBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

/**
 * @author xiaohan
 *
 */
@Service
public class ChangeService extends BaseService {

	/**
	 * 表变更-根据id查询
	 * 
	 * @param pkDcChangeid主键
	 * @return DcChangeBO
	 * @throws OptimusException
	 */
	public DcChangeBO findChangeById(String pkDcChange)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		DcChangeBO changebo = dao.queryByKey(DcChangeBO.class, pkDcChange);
		return changebo;
	}
	
	/**
	 * 表变更- 保存
	 * 
	 * @param changebo 变更对象
	 * @throws OptimusException
	 */
	public void saveChange(DcChangeBO changebo)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		dao.insert(changebo);
	}

	/**
	 * 表变更-查询
	 * 
	 * @param paramObject 查询字段对象集合 
	 * @return List<Map> 
	 * @throws OptimusException
	 */
	public List<Map> findChangeByList(Map<String, String> parmas) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append(" select n.* from ( select d.DATA_SOURCE_NAME,( select b.BUSI_OBJECT_NAME from DC_BUSI_OBJECT b where b.PK_DC_BUSI_OBJECT = d.PK_DC_BUSI_OBJECT  ) as BUSI_OBJECT_NAME , ( select c.NAME_SHORT from DC_CHANGE_TYPE c where c.CODE = m.CHANGE_ITEM ) as CHANGE_ITEM_NAME , m.* from DC_CHANGE m  ");
		sql.append(" left join DC_DATA_SOURCE d on d.PK_DC_DATA_SOURCE = m.PK_DC_DATA_SOURCE ) n ");
		sql.append(" where 1 = 1  ");
		List listParam = new ArrayList();
		if (parmas != null) {
			
			String changeItem = parmas.get("changeItem").trim();
			if (!"".equals(changeItem)) {
				sql.append(" and  n.CHANGE_ITEM = ? ");
				listParam.add(changeItem);
			}
			
			String tableNameEn = parmas.get("tableNameEn").trim();
			if (!"".equals(tableNameEn)) {
				sql.append(" and ( lower(n.TABLE_NAME_EN) like ? or upper(n.TABLE_NAME_EN) like ?  ) ");
				listParam.add("%" + tableNameEn + "%");
				listParam.add("%" + tableNameEn + "%");
			}
			
			String columnNameEn = parmas.get("columnNameEn").trim();
			if (!"".equals(columnNameEn)) {
				sql.append(" and ( lower(n.COLUMN_NAME_EN) like ? or upper(n.COLUMN_NAME_EN) like ?  ) ");
				listParam.add("%" + columnNameEn + "%");
				listParam.add("%" + columnNameEn + "%");
			}
		}
		
		sql.append(" ORDER BY  CREATER_TIME  DESC  ");
		List<Map> TableList = dao.pageQueryForList(sql.toString(),
				listParam);
		return TableList;
	}
	
	/**
	 * 获取数据源代码集
	 * @return
	 * @throws OptimusException 
	 */
	public List<Map<String, Object>> getChangeType() throws OptimusException {
		IPersistenceDAO dao=getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		List<String> str = new ArrayList<String>();
		sql.append("select CODE as value, NAME_SHORT  as text from DC_CHANGE_TYPE ");
		List list1 =dao.queryForList(sql.toString(),str);
		return list1;
	}
}
