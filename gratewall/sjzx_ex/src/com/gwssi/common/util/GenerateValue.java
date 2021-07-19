package com.gwssi.common.util;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.iface.DaoColumn;
import cn.gwssi.common.dao.iface.DaoTable;

/**
 * �̳�beacon������������UUID
 * @author Administrator
 *
 */
public class GenerateValue extends cn.gwssi.common.dao.util.GenerateValue {
	/**
	 * ��ȡ32λString��uuid����
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
	 * ��ȡlong������
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
