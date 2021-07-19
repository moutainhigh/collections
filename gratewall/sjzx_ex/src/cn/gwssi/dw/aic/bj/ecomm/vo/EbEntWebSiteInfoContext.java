package cn.gwssi.dw.aic.bj.ecomm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.txn.Constants;

/**
 * 数据表[eb_ent_web_site_info]的数据对象类
 * @author Administrator
 *
 */
public class EbEntWebSiteInfoContext extends TxnContext
{
	private static final long serialVersionUID = 201209241434510001L;
	
	/**
	 * 构造函数
	 */
	public EbEntWebSiteInfoContext()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public EbEntWebSiteInfoContext(TxnContext value)
	{
		super(value);
	}
	
	/**
	 * 取主键
	 * @return
	 */
	public VoEbEntWebSiteInfoPrimaryKey getPrimaryKey()
	{
		return getPrimaryKey( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * 取主键
	 * @param nodeName 保存主键的节点名称
	 * @return
	 */
	public VoEbEntWebSiteInfoPrimaryKey getPrimaryKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoEbEntWebSiteInfoPrimaryKey( data );
	}
	
	/**
	 * 取主键
	 * @return
	 */
	public VoEbEntWebSiteInfoPrimaryKey[] getPrimaryKeys()
	{
		return getPrimaryKeys( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * 取主键
	 * @param nodeName 保存主键的节点名称
	 * @return
	 */
	public VoEbEntWebSiteInfoPrimaryKey[] getPrimaryKeys( String nodeName )
	{
		try{
			Recordset data = getRecordset( nodeName );
			VoEbEntWebSiteInfoPrimaryKey keys[] = new VoEbEntWebSiteInfoPrimaryKey[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoEbEntWebSiteInfoPrimaryKey( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoEbEntWebSiteInfoPrimaryKey[0];
		}
	}
	
	/**
	 * 取查询条件
	 * @return
	 */
	public VoEbEntWebSiteInfoSelectKey getSelectKey()
	{
		return getSelectKey( Constants.NODE_SELECTKEY );
	}
	
	/**
	 * 取主键
	 * @param nodeName 保存主键的节点名称
	 * @return
	 */
	public VoEbEntWebSiteInfoSelectKey getSelectKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoEbEntWebSiteInfoSelectKey( data );
	}
	
	/**
	 * 取记录集
	 * @param nodeName
	 * @return
	 */
	public VoEbEntWebSiteInfo getEbEntWebSiteInfo( String nodeName )
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

		return new VoEbEntWebSiteInfo( data );
	}
	
	/**
	 * 取记录集
	 * @param nodeName
	 * @return
	 */
	public VoEbEntWebSiteInfo[] getEbEntWebSiteInfos( String nodeName )
	{
		if( nodeName == null ){
			nodeName = Constants.NODE_RECORD;
		}

		try{
			Recordset data = getRecordset( nodeName );
			VoEbEntWebSiteInfo keys[] = new VoEbEntWebSiteInfo[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoEbEntWebSiteInfo( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoEbEntWebSiteInfo[0];
		}
	}
}

