package com.gwssi.share.service.code;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.param.ParamHelp;

/**
 * 
 *     
 * 项目名称：bjgs_exchange    
 * 类名称：InterfaceCode    
 * 类描述：    
 * 创建人：lvhao    
 * 创建时间：2013-3-27 下午02:26:46    
 * 修改人：lvhao    
 * 修改时间：2013-3-27 下午02:26:46    
 * 修改备注：    
 * @version     
 *
 */
public class InterfaceCode extends ParamHelp
{

	/**
	 * 获取接口名称
	 * @param context
	 * @return
	 * @throws TxnException
	 */
	public Recordset getInterfaceName(TxnContext context) throws TxnException {
		BaseTable table = null;
		
		StringBuffer sql = new StringBuffer("select interface_id,interface_name from share_interface where is_markup='Y' order by interface_name ");
		
		try{
			table = TableFactory.getInstance().getTableObject(this, "share_interface");
			table.executeRowset(sql.toString(),context,"selected-code-listed");
		}
		catch( TxnException e ){
			log.error( "取数据库表时错误", e );
			return null;
		}

		return getParamList(context.getRecordset("selected-code-listed"), "interface_name", "interface_id");

	}
}
