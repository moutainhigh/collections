package com.gwssi.dw.runmgr.services.code;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.param.ParamHelp;

public class SysSvrUserCode extends ParamHelp
{
	private static final String TABLE_NAME="sys_Svr_user";
	public Recordset getSvrUserList (TxnContext data) throws TxnException{
		BaseTable table = TableFactory.getInstance().getTableObject(this, TABLE_NAME);
		String sql = "select sys_svr_user_id as code, user_name as name from sys_svr_user order by user_order";
        table.executeRowset(sql,data,"selected-code-listed");
		Recordset rs = getParamList(data.getRecordset("selected-code-listed"), "name", "code");	
		return rs;
		
		
	}
}
