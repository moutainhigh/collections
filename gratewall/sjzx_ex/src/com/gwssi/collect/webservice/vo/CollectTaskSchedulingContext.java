package com.gwssi.collect.webservice.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.txn.Constants;

/**
 * 数据表[collect_task_scheduling]的数据对象类
 * @author Administrator
 *
 */
public class CollectTaskSchedulingContext extends TxnContext
{
	private static final long serialVersionUID = 201304181055590001L;
	
	/**
	 * 构造函数
	 */
	public CollectTaskSchedulingContext()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public CollectTaskSchedulingContext(TxnContext value)
	{
		super(value);
	}
	
	/**
	 * 取主键
	 * @return
	 */
	public VoCollectTaskSchedulingPrimaryKey getPrimaryKey()
	{
		return getPrimaryKey( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * 取主键
	 * @param nodeName 保存主键的节点名称
	 * @return
	 */
	public VoCollectTaskSchedulingPrimaryKey getPrimaryKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoCollectTaskSchedulingPrimaryKey( data );
	}
	
	/**
	 * 取主键
	 * @return
	 */
	public VoCollectTaskSchedulingPrimaryKey[] getPrimaryKeys()
	{
		return getPrimaryKeys( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * 取主键
	 * @param nodeName 保存主键的节点名称
	 * @return
	 */
	public VoCollectTaskSchedulingPrimaryKey[] getPrimaryKeys( String nodeName )
	{
		try{
			Recordset data = getRecordset( nodeName );
			VoCollectTaskSchedulingPrimaryKey keys[] = new VoCollectTaskSchedulingPrimaryKey[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoCollectTaskSchedulingPrimaryKey( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoCollectTaskSchedulingPrimaryKey[0];
		}
	}
	
	/**
	 * 取查询条件
	 * @return
	 */
	public VoCollectTaskSchedulingSelectKey getSelectKey()
	{
		return getSelectKey( Constants.NODE_SELECTKEY );
	}
	
	/**
	 * 取主键
	 * @param nodeName 保存主键的节点名称
	 * @return
	 */
	public VoCollectTaskSchedulingSelectKey getSelectKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoCollectTaskSchedulingSelectKey( data );
	}
	
	/**
	 * 取记录集
	 * @param nodeName
	 * @return
	 */
	public VoCollectTaskScheduling getCollectTaskScheduling( String nodeName )
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

		return new VoCollectTaskScheduling( data );
	}
	
	/**
	 * 取记录集
	 * @param nodeName
	 * @return
	 */
	public VoCollectTaskScheduling[] getCollectTaskSchedulings( String nodeName )
	{
		if( nodeName == null ){
			nodeName = Constants.NODE_RECORD;
		}

		try{
			Recordset data = getRecordset( nodeName );
			VoCollectTaskScheduling keys[] = new VoCollectTaskScheduling[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoCollectTaskScheduling( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoCollectTaskScheduling[0];
		}
	}
}

