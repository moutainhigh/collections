package com.gwssi.dw.runmgr.db.code;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.param.ParamHelp;

public class DbViewCode extends ParamHelp
{
	private static final String TABLE_NAME="sys_db_view";
	public Recordset getViewList (TxnContext data) throws TxnException{
		BaseTable table = TableFactory.getInstance().getTableObject(this, TABLE_NAME);
		String sql = "select sys_db_view_id as jcsjfx_dm, view_name as jcsjfx_mc from sys_db_view";
        table.executeRowset(sql,data,"selected-view-listed");
		Recordset rs = getParamList(data.getRecordset("selected-view-listed"), "jcsjfx_mc", "jcsjfx_dm");	
		return rs;
		
		
	}
}
