package com.gwssi.share.trs.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.txn.Constants;

/**
 * ���ݱ�[trs_share_service]�����ݶ�����
 * @author Administrator
 *
 */
public class TrsShareServiceContext extends TxnContext
{
	private static final long serialVersionUID = 201308051642360001L;
	
	/**
	 * ���캯��
	 */
	public TrsShareServiceContext()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public TrsShareServiceContext(TxnContext value)
	{
		super(value);
	}
	
	/**
	 * ȡ����
	 * @return
	 */
	public VoTrsShareServicePrimaryKey getPrimaryKey()
	{
		return getPrimaryKey( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * ȡ����
	 * @param nodeName ���������Ľڵ�����
	 * @return
	 */
	public VoTrsShareServicePrimaryKey getPrimaryKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoTrsShareServicePrimaryKey( data );
	}
	
	/**
	 * ȡ����
	 * @return
	 */
	public VoTrsShareServicePrimaryKey[] getPrimaryKeys()
	{
		return getPrimaryKeys( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * ȡ����
	 * @param nodeName ���������Ľڵ�����
	 * @return
	 */
	public VoTrsShareServicePrimaryKey[] getPrimaryKeys( String nodeName )
	{
		try{
			Recordset data = getRecordset( nodeName );
			VoTrsShareServicePrimaryKey keys[] = new VoTrsShareServicePrimaryKey[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoTrsShareServicePrimaryKey( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoTrsShareServicePrimaryKey[0];
		}
	}
	
	/**
	 * ȡ��ѯ����
	 * @return
	 */
	public VoTrsShareServiceSelectKey getSelectKey()
	{
		return getSelectKey( Constants.NODE_SELECTKEY );
	}
	
	/**
	 * ȡ����
	 * @param nodeName ���������Ľڵ�����
	 * @return
	 */
	public VoTrsShareServiceSelectKey getSelectKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoTrsShareServiceSelectKey( data );
	}
	
	/**
	 * ȡ��¼��
	 * @param nodeName
	 * @return
	 */
	public VoTrsShareService getTrsShareService( String nodeName )
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

		return new VoTrsShareService( data );
	}
	
	/**
	 * ȡ��¼��
	 * @param nodeName
	 * @return
	 */
	public VoTrsShareService[] getTrsShareServices( String nodeName )
	{
		if( nodeName == null ){
			nodeName = Constants.NODE_RECORD;
		}

		try{
			Recordset data = getRecordset( nodeName );
			VoTrsShareService keys[] = new VoTrsShareService[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoTrsShareService( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoTrsShareService[0];
		}
	}
}

