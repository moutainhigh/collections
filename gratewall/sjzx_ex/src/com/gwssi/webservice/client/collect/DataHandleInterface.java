package com.gwssi.webservice.client.collect;

import java.util.List;
import java.util.Map;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.log.collectlog.dao.CollectLogVo;

public interface DataHandleInterface
{
	/**
	 * 
	 * insertData 将采集的数据插入采集表
	 * 
	 * @param tableId
	 * @param collectMode
	 * @param domMap
	 * @param collectLogVo
	 * @return
	 * @throws DBException
	 *             int
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public int insertData(String tableId, String collectMode, Map domMap,
			CollectLogVo collectLogVo) throws DBException;

	public int insertDataForQs(String tableId, String collectMode,
			List dataList, CollectLogVo collectLogVo) throws DBException;
}
