package com.gwssi.dw.runmgr.services.code;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.param.ParamHelp;

public class CltUserCode extends ParamHelp
{
	private static final String TABLE_NAME="sys_clt_user";
	public Recordset getUserList (TxnContext data) throws TxnException{
		BaseTable table = TableFactory.getInstance().getTableObject(this, TABLE_NAME);
		String sql = "select sys_clt_user_id as jcsjfx_dm, name as jcsjfx_mc from sys_clt_user";
        table.executeRowset(sql,data,"selected-code-listed");
		Recordset rs = getParamList(data.getRecordset("selected-code-listed"), "jcsjfx_mc", "jcsjfx_dm");	
		return rs;
		
		
	}
}
