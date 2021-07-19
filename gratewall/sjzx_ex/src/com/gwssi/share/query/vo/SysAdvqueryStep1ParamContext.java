package com.gwssi.share.query.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.txn.Constants;

/**
 * 数据表[sys_advquery_step1_param]的数据对象类
 * @author Administrator
 *
 */
public class SysAdvqueryStep1ParamContext extends TxnContext
{
	private static final long serialVersionUID = 200809261021030009L;
	
	/**
	 * 构造函数
	 */
	public SysAdvqueryStep1ParamContext()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public SysAdvqueryStep1ParamContext(TxnContext value)
	{
		super(value);
	}
	
	/**
	 * 取主键
	 * @return
	 */
	public VoSysAdvqueryStep1ParamPrimaryKey getPrimaryKey()
	{
		return getPrimaryKey( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * 取主键
	 * @param nodeName 保存主键的节点名称
	 * @return
	 */
	public VoSysAdvqueryStep1ParamPrimaryKey getPrimaryKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoSysAdvqueryStep1ParamPrimaryKey( data );
	}
	
	/**
	 * 取主键
	 * @return
	 */
	public VoSysAdvqueryStep1ParamPrimaryKey[] getPrimaryKeys()
	{
		return getPrimaryKeys( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * 取主键
	 * @param nodeName 保存主键的节点名称
	 * @return
	 */
	public VoSysAdvqueryStep1ParamPrimaryKey[] getPrimaryKeys( String nodeName )
	{
		try{
			Recordset data = getRecordset( nodeName );
			VoSysAdvqueryStep1ParamPrimaryKey keys[] = new VoSysAdvqueryStep1ParamPrimaryKey[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoSysAdvqueryStep1ParamPrimaryKey( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoSysAdvqueryStep1ParamPrimaryKey[0];
		}
	}
	
	/**
	 * 取查询条件
	 * @return
	 */
	public VoSysAdvqueryStep1ParamSelectKey getSelectKey()
	{
		return getSelectKey( Constants.NODE_SELECTKEY );
	}
	
	/**
	 * 取主键
	 * @param nodeName 保存主键的节点名称
	 * @return
	 */
	public VoSysAdvqueryStep1ParamSelectKey getSelectKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoSysAdvqueryStep1ParamSelectKey( data );
	}
	
	/**
	 * 取记录集
	 * @param nodeName
	 * @return
	 */
	public VoSysAdvqueryStep1Param getSysAdvqueryStep1Param( String nodeName )
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

		return new VoSysAdvqueryStep1Param( data );
	}
	
	/**
	 * 取记录集
	 * @param nodeName
	 * @return
	 */
	public VoSysAdvqueryStep1Param[] getSysAdvqueryStep1Params( String nodeName )
	{
		if( nodeName == null ){
			nodeName = Constants.NODE_RECORD;
		}

		try{
			Recordset data = getRecordset( nodeName );
			VoSysAdvqueryStep1Param keys[] = new VoSysAdvqueryStep1Param[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoSysAdvqueryStep1Param( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoSysAdvqueryStep1Param[0];
		}
	}
}

