package com.gwssi.log.sharelog.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.txn.Constants;

/**
 * 数据表[share_log]的数据对象类
 * @author Administrator
 *
 */
public class ShareLogContext extends TxnContext
{
	private static final long serialVersionUID = 201304031121530001L;
	
	/**
	 * 构造函数
	 */
	public ShareLogContext()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public ShareLogContext(TxnContext value)
	{
		super(value);
	}
	
	/**
	 * 取主键
	 * @return
	 */
	public VoShareLogPrimaryKey getPrimaryKey()
	{
		return getPrimaryKey( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * 取主键
	 * @param nodeName 保存主键的节点名称
	 * @return
	 */
	public VoShareLogPrimaryKey getPrimaryKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoShareLogPrimaryKey( data );
	}
	
	/**
	 * 取主键
	 * @return
	 */
	public VoShareLogPrimaryKey[] getPrimaryKeys()
	{
		return getPrimaryKeys( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * 取主键
	 * @param nodeName 保存主键的节点名称
	 * @return
	 */
	public VoShareLogPrimaryKey[] getPrimaryKeys( String nodeName )
	{
		try{
			Recordset data = getRecordset( nodeName );
			VoShareLogPrimaryKey keys[] = new VoShareLogPrimaryKey[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoShareLogPrimaryKey( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoShareLogPrimaryKey[0];
		}
	}
	
	/**
	 * 取查询条件
	 * @return
	 */
	public VoShareLogSelectKey getSelectKey()
	{
		return getSelectKey( Constants.NODE_SELECTKEY );
	}
	
	/**
	 * 取主键
	 * @param nodeName 保存主键的节点名称
	 * @return
	 */
	public VoShareLogSelectKey getSelectKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoShareLogSelectKey( data );
	}
	
	/**
	 * 取记录集
	 * @param nodeName
	 * @return
	 */
	public VoShareLog getShareLog( String nodeName )
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

		return new VoShareLog( data );
	}
	
	/**
	 * 取记录集
	 * @param nodeName
	 * @return
	 */
	public VoShareLog[] getShareLogs( String nodeName )
	{
		if( nodeName == null ){
			nodeName = Constants.NODE_RECORD;
		}

		try{
			Recordset data = getRecordset( nodeName );
			VoShareLog keys[] = new VoShareLog[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoShareLog( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoShareLog[0];
		}
	}
}

