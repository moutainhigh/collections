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
			// ȡ���ݱ�getTableObject�ڶ��������Ƿ��ʵı�����dao�ļ�����
		    table = TableFactory.getInstance().getTableObject(this, "sys_table_semantic");
		}
			catch( TxnException e ){
			log.error( "ȡ���ݿ��ʱ����", e );
			return null;
		}
			
		String sys_id = null;
		try{
			// ȡ���ݹ����Ĳ�����Ĭ�Ϸ���input-data�ϣ������ж������
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
			// ִ�в�ѯ��������ѯ�����ŵ���ʱ�ڵ�"selected-code-list"��
		    table.executeRowset(sql,data,"selected-code-listed");
		}
		catch( TxnException e ){
		    log.error( e );
		}
			Recordset rs = getParamList(data.getRecordset("selected-code-listed"), "table_name_cn", "table_no");	
		    return rs;
		}	  
}
