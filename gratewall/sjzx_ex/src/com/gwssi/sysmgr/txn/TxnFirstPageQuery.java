package com.gwssi.sysmgr.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.sysmgr.vo.FirstPageQueryContext;

public class TxnFirstPageQuery extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnFirstPageQuery.class, FirstPageQueryContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "first_page_query_tj";
	/**
	 * ���캯��
	 */
	public TxnFirstPageQuery()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ��Ա��¼�б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn61000001( FirstPageQueryContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String date_start = context.getRecord(inputNode).getValue("query_date_start");
		String date_end = context.getRecord(inputNode).getValue("query_date_end");
		table.executeFunction( "queryXtjgYh", context, inputNode, outputNode );
		Recordset rs= context.getRecordset(outputNode);
		int m,n;
		m=rs.size();
		DataBus databus = null;
		String username;
		String count = "";
		String sfyx = "";
		for(n=0;n<m;n++){
			
			databus=rs.get(n);			
			username=databus.getValue("username");
			sfyx=databus.getValue("sfyx");
			FirstPageQueryContext countContext = new FirstPageQueryContext();
			DataBus countDb = countContext.getRecord("record");
			countDb.setValue("username", username);
			countDb.setValue("date_start", date_start);
			countDb.setValue("date_end", date_end);
			table.executeFunction( "queryCountByYh", countContext, "record", "record" );
			DataBus reDb= countContext.getRecord("record");
			count = reDb.getValue("count");
			if(sfyx==null||!sfyx.equals("0")){
				if(count!=null&&count.equals("0")){
					databus.clear();
				}else{
					databus.setValue("sfyx", "ͣ��");
				}
			}else{
				databus.setValue("sfyx", "����");
			}
			databus.setValue("count", count);					
		}	
	}
	
	/** ��ϸ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn61000002( FirstPageQueryContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "queryDetailYhdlcs", context, inputNode, outputNode );
	}
	/** ��ѯ�־��б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn61000003( FirstPageQueryContext context ) throws TxnException{
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String date_start = context.getRecord(inputNode).getValue("query_date_start");
		String date_end = context.getRecord(inputNode).getValue("query_date_end");
		table.executeFunction( "queryXtjgFj", context, inputNode, outputNode );
		Recordset rs= context.getRecordset(outputNode);
		int m,n;
		m=rs.size();
		DataBus databus = null;
		String sjjgid;
		String count = "";
		for(n=0;n<m;n++){
			
			databus=rs.get(n);			
			sjjgid=databus.getValue("sjjgid");
			FirstPageQueryContext countContext = new FirstPageQueryContext();
			DataBus countDb = countContext.getRecord("record");
			countDb.setValue("sjjgid", sjjgid);
			countDb.setValue("date_start", date_start);
			countDb.setValue("date_end", date_end);
			table.executeFunction( "queryCountByFj", countContext, "record", "record" );
			DataBus reDb= countContext.getRecord("record");
			count = reDb.getValue("count");
			databus.setValue("count", count);					
		}		
	}
	/** ��ѯ�����б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn61000004( FirstPageQueryContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String date_start = context.getRecord(inputNode).getValue("query_date_start");
		String date_end = context.getRecord(inputNode).getValue("query_date_end");
		table.executeFunction( "queryXtjgKs", context, inputNode, outputNode );
		Recordset rs= context.getRecordset(outputNode);
		int m,n;
		m=rs.size();
		DataBus databus = null;
		String orgid;
		String count = "";
		for(n=0;n<m;n++){
			
			databus=rs.get(n);			
			orgid=databus.getValue("orgid");
			FirstPageQueryContext countContext = new FirstPageQueryContext();
			DataBus countDb = countContext.getRecord("record");
			countDb.setValue("orgid", orgid);
			countDb.setValue("date_start", date_start);
			countDb.setValue("date_end", date_end);
			table.executeFunction( "queryCountByKs", countContext, "record", "record" );
			DataBus reDb= countContext.getRecord("record");
			count = reDb.getValue("count");
			databus.setValue("count", count);					
		}	
	}
				
	/**
	 * ���ظ���ķ����������滻���׽ӿڵ��������
	 * ���ú���
	 * @param funcName ��������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void doService( String funcName,
			TxnContext context ) throws TxnException
	{
		Method method = (Method)txnMethods.get( funcName );
		if( method == null ){
			funcName = this.getClass().getName() + "#" + funcName;
			throw new TxnErrorException( ErrorConstant.JAVA_METHOD_NOTFOUND,
					"û���ҵ�������[" + txnCode + ":" + funcName + "]�Ĵ�����"
			);
		}
		
		// ִ��
		FirstPageQueryContext appContext = new FirstPageQueryContext( context );
		invoke( method, appContext );
	}
}
