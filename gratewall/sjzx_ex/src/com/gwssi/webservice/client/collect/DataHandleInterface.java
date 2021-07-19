package com.gwssi.webservice.client.collect;

import java.util.List;
import java.util.Map;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.log.collectlog.dao.CollectLogVo;

public interface DataHandleInterface
{
	/**
	 * 
	 * insertData ���ɼ������ݲ���ɼ���
	 * 
	 * @param tableId
	 * @param collectMode
	 * @param domMap
	 * @param collectLogVo
	 * @return
	 * @throws DBException
	 *             int
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public int insertData(String tableId, String collectMode, Map domMap,
			CollectLogVo collectLogVo) throws DBException;

	public int insertDataForQs(String tableId, String collectMode,
			List dataList, CollectLogVo collectLogVo) throws DBException;
}
