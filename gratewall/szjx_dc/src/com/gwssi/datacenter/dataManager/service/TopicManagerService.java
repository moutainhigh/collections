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
public class TopicManagerService extends BaseService {

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
	public List<Map> findTableByList(String pkDcTopic) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from dc_table t where t.EFFECTIVE_MARKER = ? and t.DC_TOPIC = ?");
		List listParam = new ArrayList();
		listParam.add(AppConstants.EFFECTIVE_Y);
		listParam.add(pkDcTopic);

		List<Map> TableList = dao.queryForList(sql.toString(),
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
		sql.append("select PK_DC_TABLE as value, TABLE_NAME_EN  as text from DC_TABLE where EFFECTIVE_MARKER= ? and PK_DC_DATA_SOURCE = ? ");
		str.add(AppConstants.EFFECTIVE_Y);
		str.add(pkDcDataSource);
		List list1 =dao.queryForList(sql.toString(),str);
		return list1;
	}

}
