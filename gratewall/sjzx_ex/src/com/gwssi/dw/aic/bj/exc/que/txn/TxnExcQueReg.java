package com.gwssi.dw.aic.bj.exc.que.txn;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnMasterTable;

import com.gwssi.common.util.PlainSequence;
import com.gwssi.common.util.StringUtil;
import com.gwssi.dw.aic.bj.exc.que.vo.ExcQueRegContext;

public class TxnExcQueReg extends TxnMasterTable
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnExcQueReg.class, ExcQueRegContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "exc_que_reg";
	


	// ��ϸ��
	private static final String detailTables[][] = {
		{ "exc_que_auth", "60114011" },
		{ "exc_que_civ", "60114021" },
		
		// ������Ϊ�˴������һ�� [,]������ɾ��
		{ null, null }
	};
	
	/**
	 * ���캯��
	 */
	public TxnExcQueReg()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ����ҵ�б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60114001( ExcQueRegContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "viewExcQueReg_Detail", context, inputNode, outputNode );
		Recordset rs = context.getRecordset(outputNode);
		for(int i=0;i<rs.size();i++){
			DataBus db = rs.get(i);
			formatDate("est_date","yyyy-MM-dd", db);
		}
	}

	/**
	 * ��ѯ��������Ϣ
	 * @creator caiwd
	 * @createtime 2008-8-29
	 *             ����02:29:52
	 * @param context
	 * @throws TxnException
	 *
	 */
	public void txn60114002( ExcQueRegContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, "exc_que_auth" );
		table.executeFunction( "queryExcQueAuthBase_detail", context, inputNode, outputNode );
	}

	/**
	 * ���ҳ��(��ѯ��������Exc_Que_Reg_ID��)
	 * @creator caiwd
	 * @createtime 2008-8-31
	 *             ����10:11:49
	 * @param context
	 * @throws TxnException
	 *
	 */
	public void txn60114003( ExcQueRegContext context ) throws TxnException
	{
		/*
		 * select
		 * t.exc_que_reg_id,t.ent_name,t.organ_code,t.civ_id,t.corp_rpt,t.data_sou
		 * from exc_que_reg t where
		 * t.exc_que_reg_id='F0000000000000100000000004719273'
		 */
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "viewExcQueReg_DetailAndParameters", context, inputNode, outputNode );    
		context.getRecord("select-key").setValue("nodes", "FQY");
		this.callService("60110108", context);
	}

	/**
	 * �����ҵ�����Ϣ
	 * @creator caiwd
	 * @createtime 2008-8-31
	 *             ����09:26:19
	 * @param context
	 * @throws TxnException
	 *
	 */
	public void txn60114004( ExcQueRegContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, "exc_que_auth_ann" );
		table.executeFunction( "queryExcQueAuthAnn_list", context, inputNode, outputNode );
		PlainSequence.addIndex(context, outputNode);
	}

	/**
	 * ������ҵ��λ������Ϣ
	 * @creator caiwd
	 * @createtime 2008-8-31
	 *             ����11:35:45
	 * @param context
	 * @throws TxnException
	 *
	 */
	public void txn60114005( ExcQueRegContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, "exc_que_civ" );
		table.executeFunction( "queryExcQueCivBase_detail", context, inputNode, outputNode );
		Recordset rs = context.getRecordset(outputNode);
		for(int i=0;i<rs.size();i++){
			DataBus db = rs.get(i);
			formatDate("est_date","yyyy-MM-dd", db);
			formatDate("chan_date","yyyy-MM-dd", db);
			formatDate("revok_date","yyyy-MM-dd", db);
		}
	}
	
	/**
	 * ������ҵ��λ�����Ϣ
	 * @creator caiwd
	 * @createtime 2008-8-31
	 *             ����12:01:36
	 * @param context
	 * @throws TxnException
	 *
	 */
	public void txn60114006( ExcQueRegContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, "exc_que_civ_ann" );
		//queryExcQueCivAnn_list
		table.executeFunction( "queryExcQueCivAnn_list", context, inputNode, outputNode );
		PlainSequence.addIndex(context, outputNode);
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
		ExcQueRegContext appContext = new ExcQueRegContext( context );
		invoke( method, appContext );
	}
	
	/**
	 * ���ڸ�ʽ��
	 * @creator caiwd
	 * @createtime 2008-9-17
	 *             ����01:36:35
	 * @param record
	 * @param fromPattern
	 * @param context
	 *
	 */
	protected void formatDate(String record,String fromPattern, DataBus db){
		String date2Format = db.getValue(record);
		try {
			db.setValue(record,StringUtil.format2Date(date2Format,fromPattern, "yyyy��MM��dd��"));
		} catch (ParseException e) {
			//System.out.println("����ת��ʧ�ܣ�"+e.getMessage());
		}
	}
}
