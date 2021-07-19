package com.gwssi.collect.webservice.param.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[collect_ws_param_value]�����ݶ�����
 * @author Administrator
 *
 */
public class VoCollectWsParamValue extends VoBase
{
	private static final long serialVersionUID = 201307231043580002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_WS_PARAM_VALUE_ID = "ws_param_value_id" ;	/* ����ֵID */
	public static final String ITEM_WEBSERVICE_PATAMETER_ID = "webservice_patameter_id" ;	/* ����ID */
	public static final String ITEM_PARAM_VALUE_TYPE = "param_value_type" ;	/* �������� */
	public static final String ITEM_PATAMETER_NAME = "patameter_name" ;	/* ������ */
	public static final String ITEM_PATAMETER_VALUE = "patameter_value" ;	/* ����ֵ */
	public static final String ITEM_STYLE = "style" ;				/* ��ʽ */
	public static final String ITEM_CONNECTOR = "connector" ;		/* ƴ�ӷ� */
	
	/**
	 * ���캯��
	 */
	public VoCollectWsParamValue()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoCollectWsParamValue(DataBus value)
	{
		super(value);
	}
	
	/* ����ֵID : String */
	public String getWs_param_value_id()
	{
		return getValue( ITEM_WS_PARAM_VALUE_ID );
	}

	public void setWs_param_value_id( String ws_param_value_id1 )
	{
		setValue( ITEM_WS_PARAM_VALUE_ID, ws_param_value_id1 );
	}

	/* ����ID : String */
	public String getWebservice_patameter_id()
	{
		return getValue( ITEM_WEBSERVICE_PATAMETER_ID );
	}

	public void setWebservice_patameter_id( String webservice_patameter_id1 )
	{
		setValue( ITEM_WEBSERVICE_PATAMETER_ID, webservice_patameter_id1 );
	}

	/* �������� : String */
	public String getParam_value_type()
	{
		return getValue( ITEM_PARAM_VALUE_TYPE );
	}

	public void setParam_value_type( String param_value_type1 )
	{
		setValue( ITEM_PARAM_VALUE_TYPE, param_value_type1 );
	}

	/* ������ : String */
	public String getPatameter_name()
	{
		return getValue( ITEM_PATAMETER_NAME );
	}

	public void setPatameter_name( String patameter_name1 )
	{
		setValue( ITEM_PATAMETER_NAME, patameter_name1 );
	}

	/* ����ֵ : String */
	public String getPatameter_value()
	{
		return getValue( ITEM_PATAMETER_VALUE );
	}

	public void setPatameter_value( String patameter_value1 )
	{
		setValue( ITEM_PATAMETER_VALUE, patameter_value1 );
	}

	/* ��ʽ : String */
	public String getStyle()
	{
		return getValue( ITEM_STYLE );
	}

	public void setStyle( String style1 )
	{
		setValue( ITEM_STYLE, style1 );
	}

	/* ƴ�ӷ� : String */
	public String getConnector()
	{
		return getValue( ITEM_CONNECTOR );
	}

	public void setConnector( String connector1 )
	{
		setValue( ITEM_CONNECTOR, connector1 );
	}

}

