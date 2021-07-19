package com.gwssi.dw.runmgr.db.code;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.param.ParamHelp;

public class DbUserCode extends ParamHelp
{
	private static final String TABLE_NAME="sys_db_user";
	public Recordset getUserList (TxnContext data) throws TxnException{
		BaseTable table = TableFactory.getInstance().getTableObject(this, TABLE_NAME);
		String sql = "select sys_db_user_id as jcsjfx_dm, user_name as jcsjfx_mc from sys_db_user";
        table.executeRowset(sql,data,"selected-code-listed");
		Recordset rs = getParamList(data.getRecordset("selected-code-listed"), "jcsjfx_mc", "jcsjfx_dm");	
		return rs;
		
		
	}
}
