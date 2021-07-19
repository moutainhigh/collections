package com.gwssi.share.ftp.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[share_ftp_service]的数据对象类
 * @author Administrator
 *
 */
public class VoShareFtpService extends VoBase
{
	private static final long serialVersionUID = 201308211659250006L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_FTP_SERVICE_ID = "ftp_service_id" ;	/* FTP服务ID */
	public static final String ITEM_SERVICE_ID = "service_id" ;		/* 服务ID */
	public static final String ITEM_DATASOURCE_ID = "datasource_id" ;	/* 数据源ID */
	public static final String ITEM_SRV_SCHEDULING_ID = "srv_scheduling_id" ;	/* 服务调度ID */
	public static final String ITEM_FILE_NAME = "file_name" ;		/* 文件名称 */
	public static final String ITEM_FILE_TYPE = "file_type" ;		/* 文件类型 */
	
	/**
	 * 构造函数
	 */
	public VoShareFtpService()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoShareFtpService(DataBus value)
	{
		super(value);
	}
	
	/* FTP服务ID : String */
	public String getFtp_service_id()
	{
		return getValue( ITEM_FTP_SERVICE_ID );
	}

	public void setFtp_service_id( String ftp_service_id1 )
	{
		setValue( ITEM_FTP_SERVICE_ID, ftp_service_id1 );
	}

	/* 服务ID : String */
	public String getService_id()
	{
		return getValue( ITEM_SERVICE_ID );
	}

	public void setService_id( String service_id1 )
	{
		setValue( ITEM_SERVICE_ID, service_id1 );
	}

	/* 数据源ID : String */
	public String getDatasource_id()
	{
		return getValue( ITEM_DATASOURCE_ID );
	}

	public void setDatasource_id( String datasource_id1 )
	{
		setValue( ITEM_DATASOURCE_ID, datasource_id1 );
	}

	/* 服务调度ID : String */
	public String getSrv_scheduling_id()
	{
		return getValue( ITEM_SRV_SCHEDULING_ID );
	}

	public void setSrv_scheduling_id( String srv_scheduling_id1 )
	{
		setValue( ITEM_SRV_SCHEDULING_ID, srv_scheduling_id1 );
	}

	/* 文件名称 : String */
	public String getFile_name()
	{
		return getValue( ITEM_FILE_NAME );
	}

	public void setFile_name( String file_name1 )
	{
		setValue( ITEM_FILE_NAME, file_name1 );
	}

	/* 文件类型 : String */
	public String getFile_type()
	{
		return getValue( ITEM_FILE_TYPE );
	}

	public void setFile_type( String file_type1 )
	{
		setValue( ITEM_FILE_TYPE, file_type1 );
	}

}

