package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.txn.Constants;

/**
 * 数据表[sys_etl_wf]的数据对象类
 * @author Administrator
 *
 */
public class SysEtlWfContext extends TxnContext
{
	private static final long serialVersionUID = 200805081507300001L;
	
	/**
	 * 构造函数
	 */
	public SysEtlWfContext()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public SysEtlWfContext(TxnContext value)
	{
		super(value);
	}
	
	/**
	 * 取主键
	 * @return
	 */
	public VoSysEtlWfPrimaryKey getPrimaryKey()
	{
		return getPrimaryKey( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * 取主键
	 * @param nodeName 保存主键的节点名称
	 * @return
	 */
	public VoSysEtlWfPrimaryKey getPrimaryKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoSysEtlWfPrimaryKey( data );
	}
	
	/**
	 * 取主键
	 * @return
	 */
	public VoSysEtlWfPrimaryKey[] getPrimaryKeys()
	{
		return getPrimaryKeys( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * 取主键
	 * @param nodeName 保存主键的节点名称
	 * @return
	 */
	public VoSysEtlWfPrimaryKey[] getPrimaryKeys( String nodeName )
	{
		try{
			Recordset data = getRecordset( nodeName );
			VoSysEtlWfPrimaryKey keys[] = new VoSysEtlWfPrimaryKey[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoSysEtlWfPrimaryKey( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoSysEtlWfPrimaryKey[0];
		}
	}
	
	/**
	 * 取查询条件
	 * @return
	 */
	public VoSysEtlWfSelectKey getSelectKey()
	{
		return getSelectKey( Constants.NODE_SELECTKEY );
	}
	
	/**
	 * 取主键
	 * @param nodeName 保存主键的节点名称
	 * @return
	 */
	public VoSysEtlWfSelectKey getSelectKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoSysEtlWfSelectKey( data );
	}
	
	/**
	 * 取记录集
	 * @param nodeName
	 * @return
	 */
	public VoSysEtlWf getSysEtlWf( String nodeName )
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

		return new VoSysEtlWf( data );
	}
	
	/**
	 * 取记录集
	 * @param nodeName
	 * @return
	 */
	public VoSysEtlWf[] getSysEtlWfs( String nodeName )
	{
		if( nodeName == null ){
			nodeName = Constants.NODE_RECORD;
		}

		try{
			Recordset data = getRecordset( nodeName );
			VoSysEtlWf keys[] = new VoSysEtlWf[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoSysEtlWf( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoSysEtlWf[0];
		}
	}
}

