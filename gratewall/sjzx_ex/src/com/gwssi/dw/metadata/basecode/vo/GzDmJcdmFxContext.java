package com.gwssi.dw.metadata.basecode.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.txn.Constants;

/**
 * 数据表[gz_dm_jcdm_fx]的数据对象类
 * @author Administrator
 *
 */
public class GzDmJcdmFxContext extends TxnContext
{
	private static final long serialVersionUID = 200708271316220004L;
	
	/**
	 * 构造函数
	 */
	public GzDmJcdmFxContext()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public GzDmJcdmFxContext(TxnContext value)
	{
		super(value);
	}
	
	/**
	 * 取主键
	 * @return
	 */
	public VoGzDmJcdmFxPrimaryKey getPrimaryKey()
	{
		return getPrimaryKey( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * 取主键
	 * @param nodeName 保存主键的节点名称
	 * @return
	 */
	public VoGzDmJcdmFxPrimaryKey getPrimaryKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoGzDmJcdmFxPrimaryKey( data );
	}
	
	/**
	 * 取主键
	 * @return
	 */
	public VoGzDmJcdmFxPrimaryKey[] getPrimaryKeys()
	{
		return getPrimaryKeys( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * 取主键
	 * @param nodeName 保存主键的节点名称
	 * @return
	 */
	public VoGzDmJcdmFxPrimaryKey[] getPrimaryKeys( String nodeName )
	{
		try{
			Recordset data = getRecordset( nodeName );
			VoGzDmJcdmFxPrimaryKey keys[] = new VoGzDmJcdmFxPrimaryKey[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoGzDmJcdmFxPrimaryKey( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoGzDmJcdmFxPrimaryKey[0];
		}
	}
	
	/**
	 * 取查询条件
	 * @return
	 */
	public VoGzDmJcdmFxSelectKey getSelectKey()
	{
		return getSelectKey( Constants.NODE_SELECTKEY );
	}
	
	/**
	 * 取主键
	 * @param nodeName 保存主键的节点名称
	 * @return
	 */
	public VoGzDmJcdmFxSelectKey getSelectKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoGzDmJcdmFxSelectKey( data );
	}
	
	/**
	 * 取记录集
	 * @param nodeName
	 * @return
	 */
	public VoGzDmJcdmFx getGzDmJcdmFx( String nodeName )
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

		return new VoGzDmJcdmFx( data );
	}
	
	/**
	 * 取记录集
	 * @param nodeName
	 * @return
	 */
	public VoGzDmJcdmFx[] getGzDmJcdmFxs( String nodeName )
	{
		if( nodeName == null ){
			nodeName = Constants.NODE_RECORD;
		}

		try{
			Recordset data = getRecordset( nodeName );
			VoGzDmJcdmFx keys[] = new VoGzDmJcdmFx[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoGzDmJcdmFx( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoGzDmJcdmFx[0];
		}
	}
}

