package com.gwssi.share.query.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.txn.Constants;

/**
 * 数据表[sys_advanced_query]的数据对象类
 * @author Administrator
 *
 */
public class SysAdvancedQueryContext extends TxnContext
{
	private static final long serialVersionUID = 200806261658150001L;
	
	/**
	 * 构造函数
	 */
	public SysAdvancedQueryContext()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public SysAdvancedQueryContext(TxnContext value)
	{
		super(value);
	}
	
	/**
	 * 取主键
	 * @return
	 */
	public VoSysAdvancedQueryPrimaryKey getPrimaryKey()
	{
		return getPrimaryKey( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * 取主键
	 * @param nodeName 保存主键的节点名称
	 * @return
	 */
	public VoSysAdvancedQueryPrimaryKey getPrimaryKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoSysAdvancedQueryPrimaryKey( data );
	}
	
	/**
	 * 取主键
	 * @return
	 */
	public VoSysAdvancedQueryPrimaryKey[] getPrimaryKeys()
	{
		return getPrimaryKeys( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * 取主键
	 * @param nodeName 保存主键的节点名称
	 * @return
	 */
	public VoSysAdvancedQueryPrimaryKey[] getPrimaryKeys( String nodeName )
	{
		try{
			Recordset data = getRecordset( nodeName );
			VoSysAdvancedQueryPrimaryKey keys[] = new VoSysAdvancedQueryPrimaryKey[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoSysAdvancedQueryPrimaryKey( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoSysAdvancedQueryPrimaryKey[0];
		}
	}
	
	/**
	 * 取查询条件
	 * @return
	 */
	public VoSysAdvancedQuerySelectKey getSelectKey()
	{
		return getSelectKey( Constants.NODE_SELECTKEY );
	}
	
	/**
	 * 取主键
	 * @param nodeName 保存主键的节点名称
	 * @return
	 */
	public VoSysAdvancedQuerySelectKey getSelectKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoSysAdvancedQuerySelectKey( data );
	}
	
	/**
	 * 取记录集
	 * @param nodeName
	 * @return
	 */
	public VoSysAdvancedQuery getSysAdvancedQuery( String nodeName )
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

		return new VoSysAdvancedQuery( data );
	}
	
	/**
	 * 取记录集
	 * @param nodeName
	 * @return
	 */
	public VoSysAdvancedQuery[] getSysAdvancedQuerys( String nodeName )
	{
		if( nodeName == null ){
			nodeName = Constants.NODE_RECORD;
		}

		try{
			Recordset data = getRecordset( nodeName );
			VoSysAdvancedQuery keys[] = new VoSysAdvancedQuery[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoSysAdvancedQuery( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoSysAdvancedQuery[0];
		}
	}
}

