package com.gwssi.dw.aic.bj.newmon.mov.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.dw.aic.bj.newmon.mov.vo.MonMovDomContext;

public class TxnMonMovDom extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnMonMovDom.class, MonMovDomContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "mon_mov_dom";
	
	
	/**
	 * ���캯��
	 */
	public TxnMonMovDom()
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
     * ��ؾ�Ӫ��ҵ��Ϣ�б�
     * TxnMonMovDom:txn81120001 
     * @creater - caiwd
     * @creatertime - Nov 20, 2008
     * @param context
     * @throws TxnException
     * @returnType void
     */
	public void txn81120001( MonMovDomContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "queryMonMovDom_List", context, inputNode, outputNode );
	}
	
	/**
	 * �鿴��ؾ�Ӫ��ϸ��Ϣ
	 * TxnMonMovDom:txn81120002 
	 * @creater - caiwd
	 * @creatertime - Nov 20, 2008
	 * @param context
	 * @throws TxnException
	 * @returnType void
	 */
	public void txn81120002( MonMovDomContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "viewMonMovDom_Detail", context, inputNode, outputNode );
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
		MonMovDomContext appContext = new MonMovDomContext( context );
		invoke( method, appContext );
	}
}
