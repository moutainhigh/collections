package com.gwssi.common.util;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.iface.DaoColumn;
import cn.gwssi.common.dao.iface.DaoTable;

/**
 * 继承beacon生成器，生成UUID
 * @author Administrator
 *
 */
public class GenerateValue extends cn.gwssi.common.dao.util.GenerateValue {
	/**
	 * 获取32位String型uuid内码
	 * @param request
	 * @param inpputData
	 * @param table
	 * @param column
	 * @return String(32)
	 */
	public String getUUID(TxnContext request, DataBus inpputData, DaoTable table, DaoColumn column)
	{		 	  
  	    return UuidGenerator.getUUID();
	}
	/**
	 * 获取long型内码
	 * @param request
	 * @param inpputData
	 * @param table
	 * @param column
	 * @return long
	 */
	public long getLongId (TxnContext request, DataBus inpputData, DaoTable table, DaoColumn column)
	{
		return UuidGenerator.getLongId();
	}
	
}
