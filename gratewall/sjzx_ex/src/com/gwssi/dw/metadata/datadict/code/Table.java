package com.gwssi.dw.metadata.datadict.code;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.param.ParamHelp;

public class Table extends ParamHelp
{
	public Recordset getTable( TxnContext data ) throws TxnException{
		BaseTable table = null;
		
		
		try{
			// 取数据表，getTableObject第二个参数是访问的表名（dao文件名）
		    table = TableFactory.getInstance().getTableObject(this, "sys_table_semantic");
		}
			catch( TxnException e ){
			log.error( "取数据库表时错误", e );
			return null;
		}
			
		String sys_id = null;
		try{
			// 取传递过来的参数，默认放在input-data上，可以有多个参数
			sys_id = data.getString("input-data:sys_id");
		}
		catch(Exception e){
			log.error( e );
		}
		String sql = "";
		if(sys_id != null && !sys_id.equals(""))
		{
			sql = "select table_no, table_name_cn from sys_table_semantic where sys_id='"+sys_id+"' order by table_order"; 
		}else
		{
			sql = "select table_no, table_name_cn from sys_table_semantic order by table_order";
		}
		
		try{
			// 执行查询函数，查询结果存放到临时节点"selected-code-list"上
		    table.executeRowset(sql,data,"selected-code-listed");
		}
		catch( TxnException e ){
		    log.error( e );
		}
			Recordset rs = getParamList(data.getRecordset("selected-code-listed"), "table_name_cn", "table_no");	
		    return rs;
		}	  
}
