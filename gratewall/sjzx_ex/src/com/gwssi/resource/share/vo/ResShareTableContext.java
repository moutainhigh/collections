package com.gwssi.resource.share.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.txn.Constants;

/**
 * ���ݱ�[res_share_table]�����ݶ�����
 * @author Administrator
 *
 */
public class ResShareTableContext extends TxnContext
{
	private static final long serialVersionUID = 201303191807570001L;
	
	/**
	 * ���캯��
	 */
	public ResShareTableContext()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public ResShareTableContext(TxnContext value)
	{
		super(value);
	}
	
	/**
	 * ȡ����
	 * @return
	 */
	public VoResShareTablePrimaryKey getPrimaryKey()
	{
		return getPrimaryKey( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * ȡ����
	 * @param nodeName ���������Ľڵ�����
	 * @return
	 */
	public VoResShareTablePrimaryKey getPrimaryKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoResShareTablePrimaryKey( data );
	}
	
	/**
	 * ȡ����
	 * @return
	 */
	public VoResShareTablePrimaryKey[] getPrimaryKeys()
	{
		return getPrimaryKeys( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * ȡ����
	 * @param nodeName ���������Ľڵ�����
	 * @return
	 */
	public VoResShareTablePrimaryKey[] getPrimaryKeys( String nodeName )
	{
		try{
			Recordset data = getRecordset( nodeName );
			VoResShareTablePrimaryKey keys[] = new VoResShareTablePrimaryKey[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoResShareTablePrimaryKey( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoResShareTablePrimaryKey[0];
		}
	}
	
	/**
	 * ȡ��ѯ����
	 * @return
	 */
	public VoResShareTableSelectKey getSelectKey()
	{
		return getSelectKey( Constants.NODE_SELECTKEY );
	}
	
	/**
	 * ȡ����
	 * @param nodeName ���������Ľڵ�����
	 * @return
	 */
	public VoResShareTableSelectKey getSelectKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoResShareTableSelectKey( data );
	}
	
	/**
	 * ȡ��¼��
	 * @param nodeName
	 * @return
	 */
	public VoResShareTable getResShareTable( String nodeName )
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

		return new VoResShareTable( data );
	}
	
	/**
	 * ȡ��¼��
	 * @param nodeName
	 * @return
	 */
	public VoResShareTable[] getResShareTables( String nodeName )
	{
		if( nodeName == null ){
			nodeName = Constants.NODE_RECORD;
		}

		try{
			Recordset data = getRecordset( nodeName );
			VoResShareTable keys[] = new VoResShareTable[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoResShareTable( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoResShareTable[0];
		}
	}
}

