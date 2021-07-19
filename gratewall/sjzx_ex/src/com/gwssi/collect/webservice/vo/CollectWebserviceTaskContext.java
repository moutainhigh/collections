package com.gwssi.collect.webservice.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.txn.Constants;

/**
 * ���ݱ�[collect_webservice_task]�����ݶ�����
 * @author Administrator
 *
 */
public class CollectWebserviceTaskContext extends TxnContext
{
	private static final long serialVersionUID = 201304101334340001L;
	
	/**
	 * ���캯��
	 */
	public CollectWebserviceTaskContext()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public CollectWebserviceTaskContext(TxnContext value)
	{
		super(value);
	}
	
	/**
	 * ȡ����
	 * @return
	 */
	public VoCollectWebserviceTaskPrimaryKey getPrimaryKey()
	{
		return getPrimaryKey( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * ȡ����
	 * @param nodeName ���������Ľڵ�����
	 * @return
	 */
	public VoCollectWebserviceTaskPrimaryKey getPrimaryKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoCollectWebserviceTaskPrimaryKey( data );
	}
	
	/**
	 * ȡ����
	 * @return
	 */
	public VoCollectWebserviceTaskPrimaryKey[] getPrimaryKeys()
	{
		return getPrimaryKeys( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * ȡ����
	 * @param nodeName ���������Ľڵ�����
	 * @return
	 */
	public VoCollectWebserviceTaskPrimaryKey[] getPrimaryKeys( String nodeName )
	{
		try{
			Recordset data = getRecordset( nodeName );
			VoCollectWebserviceTaskPrimaryKey keys[] = new VoCollectWebserviceTaskPrimaryKey[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoCollectWebserviceTaskPrimaryKey( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoCollectWebserviceTaskPrimaryKey[0];
		}
	}
	
	/**
	 * ȡ��ѯ����
	 * @return
	 */
	public VoCollectWebserviceTaskSelectKey getSelectKey()
	{
		return getSelectKey( Constants.NODE_SELECTKEY );
	}
	
	/**
	 * ȡ����
	 * @param nodeName ���������Ľڵ�����
	 * @return
	 */
	public VoCollectWebserviceTaskSelectKey getSelectKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoCollectWebserviceTaskSelectKey( data );
	}
	
	/**
	 * ȡ��¼��
	 * @param nodeName
	 * @return
	 */
	public VoCollectWebserviceTask getCollectWebserviceTask( String nodeName )
	{
		if( nodeName == null ){
			nodeName = Constants.NODE_RECORD;
		}

		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoCollectWebserviceTask( data );
	}
	
	/**
	 * ȡ��¼��
	 * @param nodeName
	 * @return
	 */
	public VoCollectWebserviceTask[] getCollectWebserviceTasks( String nodeName )
	{
		if( nodeName == null ){
			nodeName = Constants.NODE_RECORD;
		}

		try{
			Recordset data = getRecordset( nodeName );
			VoCollectWebserviceTask keys[] = new VoCollectWebserviceTask[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoCollectWebserviceTask( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoCollectWebserviceTask[0];
		}
	}
}

