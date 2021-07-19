package com.gwssi.dw.runmgr.services.code;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.param.ParamHelp;

public class SysExUserCode extends ParamHelp
{
	private static final String TABLE_NAME="sys_db_user";
	public Recordset getUserList (TxnContext data) throws TxnException{
		BaseTable table = TableFactory.getInstance().getTableObject(this, TABLE_NAME);
		//String sql = "select user_name as jcsjfx_dm, user_name as jcsjfx_mc from sys_svr_user union all select sys_db_user_id as jcsjfx_dm, user_name as jcsjfx_mc from sys_db_user";
		String sql = "select user_name as jcsjfx_dm, user_name as jcsjfx_mc from sys_svr_user";
        table.executeRowset(sql,data,"selected-code-listed");
        DataBus db = new DataBus();
//        db.setValue("jcsjfx_dm", "北京市地税局");
//        db.setValue("jcsjfx_mc", "北京市地税局");
//        data.addRecord("selected-code-listed", db);
//        db = new DataBus();
        db.setValue("jcsjfx_dm", "北京市质监局");
        db.setValue("jcsjfx_mc", "北京市质监局");
        data.addRecord("selected-code-listed", db);
//        db = new DataBus();
//        db.setValue("jcsjfx_dm", "北京市质检局");
//        db.setValue("jcsjfx_mc", "北京市质检局");
//        data.addRecord("selected-code-listed", db);
		Recordset rs = getParamList(data.getRecordset("selected-code-listed"), "jcsjfx_mc", "jcsjfx_dm");	
		return rs;
		
		
	}
}
