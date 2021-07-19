package com.gwssi.help.txn;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

import com.gwssi.help.vo.HelpContext;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;

public class TxnHelp extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnHelp.class, HelpContext.class );
	

	
	/**
	 * ���캯��
	 */
	public TxnHelp()
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
	 * ƽ̨����
	 */
	public void txn7000001( HelpContext context) throws TxnException, IOException
	{

	}
	/** ʹ��˵��
	 */
	public void txn7000002( HelpContext context) throws TxnException, IOException
	{

	}
	
	/** ��������ְ�ܡ���ɫ����
	 */
	public void txn7000004( HelpContext context) throws TxnException, IOException
	{

	}
	/** �ɼ���������
	 */
	public void txn7000005( HelpContext context) throws TxnException, IOException
	{

	}
	/** �����������
	 */
	public void txn7000006( HelpContext context) throws TxnException, IOException
	{

	}
	/** ����ƽ̨����
	 */
	public void txn7000007( HelpContext context) throws TxnException, IOException
	{

	}
	/** ��������
	 */
	public void txn7000008( HelpContext context) throws TxnException, IOException
	{

	}
	/** ϵͳ����
	 */
	public void txn9000001( HelpContext context) throws TxnException, IOException
	{

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
		HelpContext appContext = new HelpContext( context );
		invoke( method, appContext );
	}
}
