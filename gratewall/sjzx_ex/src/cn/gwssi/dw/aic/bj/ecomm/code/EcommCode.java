package cn.gwssi.dw.aic.bj.ecomm.code;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.param.ParamHelp;

public class EcommCode extends ParamHelp
{
	/**
	 * 获取用户名称
	 * @param context
	 * @return
	 * @throws TxnException
	 */
	public Recordset getUserName(TxnContext context) throws TxnException {
		BaseTable table = null;
		
		StringBuffer sql = new StringBuffer("select eb_sys_right_user_id,user_name from eb_sys_right_user");
		
		try{
			table = TableFactory.getInstance().getTableObject(this, "eb_sys_right_user");
			table.executeRowset(sql.toString(),context,"selected-code-listed");
		}
		catch( TxnException e ){
			log.error( "取数据库表时错误", e );
			return null;
		}

		return getParamList(context.getRecordset("selected-code-listed"), "user_name", "eb_sys_right_user_id");

	}
}
