package com.gwssi.monitor.txn;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.monitor.vo.MonitorContext;

public class TxnMonitor extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnMonitor.class, MonitorContext.class );
	

	
	/**
	 * ���캯��
	 */
	public TxnMonitor()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}

	/** ��ת��ʵʱ���
	 * @param context ����������
	 * @throws TxnException
	 * @throws IOException 
	 */
	public void txn5100000( MonitorContext context) throws TxnException, IOException
	{
		//http://172.30.7.179:8080/exmon/index.do?method=rtime
	}
	/** ��ת�����ָ�����
	 * @param context ����������
	 * @throws TxnException
	 * @throws IOException 
	 */
	public void txn5200000( MonitorContext context) throws TxnException, IOException
	{
		//http://172.30.7.179:8080/exmon/index.do?method=manage
	}
	/** ��ת�����鷢�������
	 * @param context ����������
	 * @throws TxnException
	 * @throws IOException 
	 */
	public void txn5300000( MonitorContext context) throws TxnException, IOException
	{
		//http://172.30.7.179:8080/exmon/event.do
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
		MonitorContext appContext = new MonitorContext( context );
		invoke( method, appContext );
	}
}
