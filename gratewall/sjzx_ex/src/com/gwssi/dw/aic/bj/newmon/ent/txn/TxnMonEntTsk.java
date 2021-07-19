package com.gwssi.dw.aic.bj.newmon.ent.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.dw.aic.bj.newmon.ent.vo.MonEntTskContext;

public class TxnMonEntTsk extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnMonEntTsk.class, MonEntTskContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "mon_ent_tsk";
		
	/**
	 * ���캯��
	 */
	public TxnMonEntTsk()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/**
	 * �����������Ʋ�ѯ��������б�
	 * TxnMonEntTsk:txn81119001 
	 * @creater - caiwd
	 * @creatertime - Nov 19, 2008
	 * @param context
	 * @throws TxnException
	 * @returnType void
	 */
	public void txn81119001( MonEntTskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "queryMonEntTsk_List", context, inputNode, outputNode );
		
	}
	
	/**
	 * �鿴���������ϸ��Ϣ
	 * TxnMonEntTsk:txn81119002 
	 * @creater - caiwd
	 * @creatertime - Nov 20, 2008
	 * @param context
	 * @throws TxnException
	 * @returnType void
	 */
	public void txn81119002( MonEntTskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "viewMonEntTsk_Detail", context, inputNode, outputNode );
		
		String mon_mis_id = context.getString("record:mon_mis_id");
		if(null!= mon_mis_id && !"".equals(mon_mis_id)){
			table.executeFunction("queryMonBussQusAndType_List", context, "record", "que-type");
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
		MonEntTskContext appContext = new MonEntTskContext( context );
		invoke( method, appContext );
	}
}
