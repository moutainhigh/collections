package cn.gwssi.dw.rd.log.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.txn.Constants;

/**
 * 数据表[view_sys_app_log_query]的数据对象类
 * @author Administrator
 *
 */
public class ViewSysAppLogQueryContext extends TxnContext
{
	private static final long serialVersionUID = 201208010743350001L;
	
	/**
	 * 构造函数
	 */
	public ViewSysAppLogQueryContext()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public ViewSysAppLogQueryContext(TxnContext value)
	{
		super(value);
	}
	
	/**
	 * 取主键
	 * @return
	 */
	public VoViewSysAppLogQueryPrimaryKey getPrimaryKey()
	{
		return getPrimaryKey( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * 取主键
	 * @param nodeName 保存主键的节点名称
	 * @return
	 */
	public VoViewSysAppLogQueryPrimaryKey getPrimaryKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoViewSysAppLogQueryPrimaryKey( data );
	}
	
	/**
	 * 取主键
	 * @return
	 */
	public VoViewSysAppLogQueryPrimaryKey[] getPrimaryKeys()
	{
		return getPrimaryKeys( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * 取主键
	 * @param nodeName 保存主键的节点名称
	 * @return
	 */
	public VoViewSysAppLogQueryPrimaryKey[] getPrimaryKeys( String nodeName )
	{
		try{
			Recordset data = getRecordset( nodeName );
			VoViewSysAppLogQueryPrimaryKey keys[] = new VoViewSysAppLogQueryPrimaryKey[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoViewSysAppLogQueryPrimaryKey( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoViewSysAppLogQueryPrimaryKey[0];
		}
	}
	
	/**
	 * 取查询条件
	 * @return
	 */
	public VoViewSysAppLogQuerySelectKey getSelectKey()
	{
		return getSelectKey( Constants.NODE_SELECTKEY );
	}
	
	/**
	 * 取主键
	 * @param nodeName 保存主键的节点名称
	 * @return
	 */
	public VoViewSysAppLogQuerySelectKey getSelectKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoViewSysAppLogQuerySelectKey( data );
	}
	
	/**
	 * 取记录集
	 * @param nodeName
	 * @return
	 */
	public VoViewSysAppLogQuery getViewSysAppLogQuery( String nodeName )
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

		return new VoViewSysAppLogQuery( data );
	}
	
	/**
	 * 取记录集
	 * @param nodeName
	 * @return
	 */
	public VoViewSysAppLogQuery[] getViewSysAppLogQuerys( String nodeName )
	{
		if( nodeName == null ){
			nodeName = Constants.NODE_RECORD;
		}

		try{
			Recordset data = getRecordset( nodeName );
			VoViewSysAppLogQuery keys[] = new VoViewSysAppLogQuery[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoViewSysAppLogQuery( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoViewSysAppLogQuery[0];
		}
	}
}

