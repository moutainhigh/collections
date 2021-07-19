package com.gwssi.dw.runmgr.services.txn;

import java.util.HashMap;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.dw.runmgr.services.vo.SysCltUserContext;

public class TxnSysCltLog extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap		txnMethods		= getAllMethod(
														TxnSysCltLog.class,
														SysCltUserContext.class);

	// 数据表名称
	private static final String	LOG_TABLE_NAME	= "sys_clt_log";
	// 数据表名称
	private static final String	LOGDETAIL_TABLE_NAME	= "sys_clt_log_detail";
	
	private static final String	LOG_ROWSET_FUNCTION	= "select sys_clt_log list";

	private static final String	LOGDETAIL_ROWSET_FUNCTION	= "select sys_clt_log_detail list";

	protected void prepare(TxnContext arg0) throws TxnException
	{
	}

	public void txn50212001(TxnContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				LOG_TABLE_NAME);
		// 此方法在user的dao的配置文件中配置
		table.executeFunction(LOG_ROWSET_FUNCTION, context, inputNode, outputNode);

	}

	public void txn50212002(TxnContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				LOGDETAIL_TABLE_NAME);
		// 此方法在user的dao的配置文件中配置
		table.executeFunction(LOGDETAIL_ROWSET_FUNCTION, context, inputNode, outputNode);

	}


}
