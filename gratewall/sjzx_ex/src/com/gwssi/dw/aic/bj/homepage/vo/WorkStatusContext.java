package com.gwssi.dw.aic.bj.homepage.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.txn.Constants;

/**
 * 数据表[work_status]的数据对象类
 * @author Administrator
 *
 */
public class WorkStatusContext extends TxnContext
{
	private static final long serialVersionUID = 200812041106350001L;
	
	/**
	 * 构造函数
	 */
	public WorkStatusContext()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public WorkStatusContext(TxnContext value)
	{
		super(value);
	}
	
	/**
	 * 取主键
	 * @return
	 */
	public VoWorkStatusPrimaryKey getPrimaryKey()
	{
		return getPrimaryKey( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * 取主键
	 * @param nodeName 保存主键的节点名称
	 * @return
	 */
	public VoWorkStatusPrimaryKey getPrimaryKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoWorkStatusPrimaryKey( data );
	}
	
	/**
	 * 取主键
	 * @return
	 */
	public VoWorkStatusPrimaryKey[] getPrimaryKeys()
	{
		return getPrimaryKeys( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * 取主键
	 * @param nodeName 保存主键的节点名称
	 * @return
	 */
	public VoWorkStatusPrimaryKey[] getPrimaryKeys( String nodeName )
	{
		try{
			Recordset data = getRecordset( nodeName );
			VoWorkStatusPrimaryKey keys[] = new VoWorkStatusPrimaryKey[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoWorkStatusPrimaryKey( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoWorkStatusPrimaryKey[0];
		}
	}
	
	/**
	 * 取查询条件
	 * @return
	 */
	public VoWorkStatusSelectKey getSelectKey()
	{
		return getSelectKey( Constants.NODE_SELECTKEY );
	}
	
	/**
	 * 取主键
	 * @param nodeName 保存主键的节点名称
	 * @return
	 */
	public VoWorkStatusSelectKey getSelectKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoWorkStatusSelectKey( data );
	}
	
	/**
	 * 取记录集
	 * @param nodeName
	 * @return
	 */
	public VoWorkStatus getWorkStatus( String nodeName )
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

		return new VoWorkStatus( data );
	}
	
	/**
	 * 取记录集
	 * @param nodeName
	 * @return
	 */
	public VoWorkStatus[] getWorkStatuss( String nodeName )
	{
		if( nodeName == null ){
			nodeName = Constants.NODE_RECORD;
		}

		try{
			Recordset data = getRecordset( nodeName );
			VoWorkStatus keys[] = new VoWorkStatus[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoWorkStatus( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoWorkStatus[0];
		}
	}
}

