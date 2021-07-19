package com.gwssi.collect.webservice.param.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[collect_ws_param_value]的数据对象类
 * @author Administrator
 *
 */
public class VoCollectWsParamValue extends VoBase
{
	private static final long serialVersionUID = 201307231043580002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_WS_PARAM_VALUE_ID = "ws_param_value_id" ;	/* 参数值ID */
	public static final String ITEM_WEBSERVICE_PATAMETER_ID = "webservice_patameter_id" ;	/* 参数ID */
	public static final String ITEM_PARAM_VALUE_TYPE = "param_value_type" ;	/* 参数类型 */
	public static final String ITEM_PATAMETER_NAME = "patameter_name" ;	/* 参数名 */
	public static final String ITEM_PATAMETER_VALUE = "patameter_value" ;	/* 参数值 */
	public static final String ITEM_STYLE = "style" ;				/* 格式 */
	public static final String ITEM_CONNECTOR = "connector" ;		/* 拼接符 */
	
	/**
	 * 构造函数
	 */
	public VoCollectWsParamValue()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoCollectWsParamValue(DataBus value)
	{
		super(value);
	}
	
	/* 参数值ID : String */
	public String getWs_param_value_id()
	{
		return getValue( ITEM_WS_PARAM_VALUE_ID );
	}

	public void setWs_param_value_id( String ws_param_value_id1 )
	{
		setValue( ITEM_WS_PARAM_VALUE_ID, ws_param_value_id1 );
	}

	/* 参数ID : String */
	public String getWebservice_patameter_id()
	{
		return getValue( ITEM_WEBSERVICE_PATAMETER_ID );
	}

	public void setWebservice_patameter_id( String webservice_patameter_id1 )
	{
		setValue( ITEM_WEBSERVICE_PATAMETER_ID, webservice_patameter_id1 );
	}

	/* 参数类型 : String */
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

	/* 拼接符 : String */
	public String getConnector()
	{
		return getValue( ITEM_CONNECTOR );
	}

	public void setConnector( String connector1 )
	{
		setValue( ITEM_CONNECTOR, connector1 );
	}

}

