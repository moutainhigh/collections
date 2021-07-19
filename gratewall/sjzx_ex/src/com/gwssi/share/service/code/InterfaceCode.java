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
 * ��Ŀ���ƣ�bjgs_exchange    
 * �����ƣ�InterfaceCode    
 * ��������    
 * �����ˣ�lvhao    
 * ����ʱ�䣺2013-3-27 ����02:26:46    
 * �޸��ˣ�lvhao    
 * �޸�ʱ�䣺2013-3-27 ����02:26:46    
 * �޸ı�ע��    
 * @version     
 *
 */
public class InterfaceCode extends ParamHelp
{

	/**
	 * ��ȡ�ӿ�����
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
			log.error( "ȡ���ݿ��ʱ����", e );
			return null;
		}

		return getParamList(context.getRecordset("selected-code-listed"), "interface_name", "interface_id");

	}
}
