package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.txn.Constants;

/**
 * 数据表[sys_rd_standard_codedata]的数据对象类
 * @author Administrator
 *
 */
public class SysRdStandardCodedataContext extends TxnContext
{
	private static final long serialVersionUID = 201205031401170001L;
	
	/**
	 * 构造函数
	 */
	public SysRdStandardCodedataContext()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public SysRdStandardCodedataContext(TxnContext value)
	{
		super(value);
	}
	
	/**
	 * 取主键
	 * @return
	 */
	public VoSysRdStandardCodedataPrimaryKey getPrimaryKey()
	{
		return getPrimaryKey( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * 取主键
	 * @param nodeName 保存主键的节点名称
	 * @return
	 */
	public VoSysRdStandardCodedataPrimaryKey getPrimaryKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoSysRdStandardCodedataPrimaryKey( data );
	}
	
	/**
	 * 取主键
	 * @return
	 */
	public VoSysRdStandardCodedataPrimaryKey[] getPrimaryKeys()
	{
		return getPrimaryKeys( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * 取主键
	 * @param nodeName 保存主键的节点名称
	 * @return
	 */
	public VoSysRdStandardCodedataPrimaryKey[] getPrimaryKeys( String nodeName )
	{
		try{
			Recordset data = getRecordset( nodeName );
			VoSysRdStandardCodedataPrimaryKey keys[] = new VoSysRdStandardCodedataPrimaryKey[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoSysRdStandardCodedataPrimaryKey( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoSysRdStandardCodedataPrimaryKey[0];
		}
	}
	
	/**
	 * 取查询条件
	 * @return
	 */
	public VoSysRdStandardCodedataSelectKey getSelectKey()
	{
		return getSelectKey( Constants.NODE_SELECTKEY );
	}
	
	/**
	 * 取主键
	 * @param nodeName 保存主键的节点名称
	 * @return
	 */
	public VoSysRdStandardCodedataSelectKey getSelectKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoSysRdStandardCodedataSelectKey( data );
	}
	
	/**
	 * 取记录集
	 * @param nodeName
	 * @return
	 */
	public VoSysRdStandardCodedata getSysRdStandardCodedata( String nodeName )
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

		return new VoSysRdStandardCodedata( data );
	}
	
	/**
	 * 取记录集
	 * @param nodeName
	 * @return
	 */
	public VoSysRdStandardCodedata[] getSysRdStandardCodedatas( String nodeName )
	{
		if( nodeName == null ){
			nodeName = Constants.NODE_RECORD;
		}

		try{
			Recordset data = getRecordset( nodeName );
			VoSysRdStandardCodedata keys[] = new VoSysRdStandardCodedata[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoSysRdStandardCodedata( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoSysRdStandardCodedata[0];
		}
	}
}

