package com.gwssi.dw.runmgr.db;

import java.util.List;
import java.util.Map;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.database.DBOperation;
import com.gwssi.common.database.DBOperationFactory;

import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.txn.TxnService;

public class TxnBaseDbMgr extends TxnService
{

	protected void prepare(TxnContext arg0) throws TxnException
	{
		// TODO Auto-generated method stub

	}
	protected boolean hasConfigView(BaseTable table, String user_id, String view_id){
		DataBus db = new DataBus();
		StringBuffer sql = new StringBuffer("select sys_db_config_id from sys_db_config where 1=1");
		if(user_id!=null&&!"".equals(user_id)){
			sql.append(" and sys_db_user_id='").append(user_id).append("'");
		}
		if(view_id!=null&&!"".equals(view_id)){
			sql.append(" and sys_db_view_id='").append(view_id).append("'");
		}
		int j;
		try {
			j = table.executeRowset( sql.toString(), db, "ifExsit" );
			log.debug("----------------");
			log.debug(db);
			log.debug("----------------");
			if (j > 0) {
				if (db.getRecord("ifExsit")!=null&&db.getRecord("ifExsit").getValue("sys_db_config_id") != null
						&& !"".equals(db.getRecord("ifExsit").getValue(
								"sys_db_config_id"))){
					return true;
				}
			}
		} catch (TxnException e) {
			
		}
		return false;
	}
	
	protected void executeDBSql(String user_type, List sqls, String ope) throws TxnErrorException
	{
		try {
			DBOperation operation = DBOperationFactory.createOperation();
			if("0".equals(user_type)){
				operation.execute(sqls, true, "2");
			}else if("1".equals(user_type)){
				operation.execute(sqls, true, "3");
			}
			
		} catch (DBException e) {
			e.printStackTrace();
			throw new TxnErrorException("99999", ope+"数据库用户失败！");
		}
	}
	
	protected Map executeDBSql(String user_type, String sql, String ope) throws TxnErrorException
	{
		try {
			DBOperation operation = DBOperationFactory.createOperation();
			if("0".equals(user_type)){
				return operation.selectOne(sql, "2");
			}else if("1".equals(user_type)){
				return operation.selectOne(sql, "3");
			}
			
		} catch (DBException e) {
			e.printStackTrace();
			throw new TxnErrorException("99999", ope);
		}
		return null;
	}
}
