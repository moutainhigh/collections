package com.gwssi.share.query.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.txn.Constants;

/**
 * 数据表[sys_advquery_step2_param]的数据对象类
 * @author Administrator
 *
 */
public class SysAdvqueryStep2ParamContext extends TxnContext
{
	private static final long serialVersionUID = 200809261020110005L;
	
	/**
	 * 构造函数
	 */
	public SysAdvqueryStep2ParamContext()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public SysAdvqueryStep2ParamContext(TxnContext value)
	{
		super(value);
	}
	
	/**
	 * 取主键
	 * @return
	 */
	public VoSysAdvqueryStep2ParamPrimaryKey getPrimaryKey()
	{
		return getPrimaryKey( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * 取主键
	 * @param nodeName 保存主键的节点名称
	 * @return
	 */
	public VoSysAdvqueryStep2ParamPrimaryKey getPrimaryKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoSysAdvqueryStep2ParamPrimaryKey( data );
	}
	
	/**
	 * 取主键
	 * @return
	 */
	public VoSysAdvqueryStep2ParamPrimaryKey[] getPrimaryKeys()
	{
		return getPrimaryKeys( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * 取主键
	 * @param nodeName 保存主键的节点名称
	 * @return
	 */
	public VoSysAdvqueryStep2ParamPrimaryKey[] getPrimaryKeys( String nodeName )
	{
		try{
			Recordset data = getRecordset( nodeName );
			VoSysAdvqueryStep2ParamPrimaryKey keys[] = new VoSysAdvqueryStep2ParamPrimaryKey[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoSysAdvqueryStep2ParamPrimaryKey( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoSysAdvqueryStep2ParamPrimaryKey[0];
		}
	}
	
	/**
	 * 取查询条件
	 * @return
	 */
	public VoSysAdvqueryStep2ParamSelectKey getSelectKey()
	{
		return getSelectKey( Constants.NODE_SELECTKEY );
	}
	
	/**
	 * 取主键
	 * @param nodeName 保存主键的节点名称
	 * @return
	 */
	public VoSysAdvqueryStep2ParamSelectKey getSelectKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoSysAdvqueryStep2ParamSelectKey( data );
	}
	
	/**
	 * 取记录集
	 * @param nodeName
	 * @return
	 */
	public VoSysAdvqueryStep2Param getSysAdvqueryStep2Param( String nodeName )
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

		return new VoSysAdvqueryStep2Param( data );
	}
	
	/**
	 * 取记录集
	 * @param nodeName
	 * @return
	 */
	public VoSysAdvqueryStep2Param[] getSysAdvqueryStep2Params( String nodeName )
	{
		if( nodeName == null ){
			nodeName = Constants.NODE_RECORD;
		}

		try{
			Recordset data = getRecordset( nodeName );
			VoSysAdvqueryStep2Param keys[] = new VoSysAdvqueryStep2Param[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoSysAdvqueryStep2Param( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoSysAdvqueryStep2Param[0];
		}
	}
}

