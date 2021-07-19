package com.gwssi.share.ftp.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[share_ftp_srv_param]的数据对象类
 * @author Administrator
 *
 */
public class VoShareFtpSrvParam extends VoBase
{
	private static final long serialVersionUID = 201308211700020010L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SRV_PARAM_ID = "srv_param_id" ;	/* 参数值ID */
	public static final String ITEM_FTP_SERVICE_ID = "ftp_service_id" ;	/* FTP服务ID */
	public static final String ITEM_PARAM_VALUE_TYPE = "param_value_type" ;	/* 代码表String INT BOOLEAN */
	public static final String ITEM_PATAMETER_NAME = "patameter_name" ;	/* 参数名 */
	public static final String ITEM_PATAMETER_VALUE = "patameter_value" ;	/* 参数值 */
	public static final String ITEM_STYLE = "style" ;				/* 格式 */
	public static final String ITEM_SHOWORDER = "showorder" ;		/* 顺序字段 */
	
	/**
	 * 构造函数
	 */
	public VoShareFtpSrvParam()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoShareFtpSrvParam(DataBus value)
	{
		super(value);
	}
	
	/* 参数值ID : String */
	public String getSrv_param_id()
	{
		return getValue( ITEM_SRV_PARAM_ID );
	}

	public void setSrv_param_id( String srv_param_id1 )
	{
		setValue( ITEM_SRV_PARAM_ID, srv_param_id1 );
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

	/* 代码表String INT BOOLEAN : String */
	public String getParam_value_type()
	{
		return getValue( ITEM_PARAM_VALUE_TYPE );
	}

	public void setParam_value_type( String param_value_type1 )
	{
		setValue( ITEM_PARAM_VALUE_TYPE, param_value_type1 );
	}

	/* 参数名 : String */
	public String getPatameter_name()
	{
		return getValue( ITEM_PATAMETER_NAME );
	}

	public void setPatameter_name( String patameter_name1 )
	{
		setValue( ITEM_PATAMETER_NAME, patameter_name1 );
	}

	/* 参数值 : String */
	public String getPatameter_value()
	{
		return getValue( ITEM_PATAMETER_VALUE );
	}

	public void setPatameter_value( String patameter_value1 )
	{
		setValue( ITEM_PATAMETER_VALUE, patameter_value1 );
	}

	/* 格式 : String */
	public String getStyle()
	{
		return getValue( ITEM_STYLE );
	}

	public void setStyle( String style1 )
	{
		setValue( ITEM_STYLE, style1 );
	}

	/* 顺序字段 : String */
	public String getShoworder()
	{
		return getValue( ITEM_SHOWORDER );
	}

	public void setShoworder( String showorder1 )
	{
		setValue( ITEM_SHOWORDER, showorder1 );
	}

}

