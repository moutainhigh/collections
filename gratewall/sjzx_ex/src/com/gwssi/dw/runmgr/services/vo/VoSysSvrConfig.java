package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_svr_config]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysSvrConfig extends VoBase
{
	private static final long serialVersionUID = 200809081645150002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_SVR_CONFIG_ID = "sys_svr_config_id" ;	/* ����������ñ��� */
	public static final String ITEM_SYS_SVR_SERVICE_ID = "sys_svr_service_id" ;	/* ���������� */
	public static final String ITEM_SYS_SVR_USER_ID = "sys_svr_user_id" ;	/* ���������� */
	public static final String ITEM_PERMIT_COLUMN = "permit_column" ;	/* ������ʵ��ֶ� */
	public static final String ITEM_CREATE_DATE = "create_date" ;	/* �������� */
	public static final String ITEM_CREATE_BY = "create_by" ;		/* ������ */
	public static final String ITEM_CONFIG_ORDER = "config_order" ;	/* �����ֶ� */
	
	/**
	 * ���캯��
	 */
	public VoSysSvrConfig()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysSvrConfig(DataBus value)
	{
		super(value);
	}
	
	/* ����������ñ��� : String */
	public String getSys_svr_config_id()
	{
		return getValue( ITEM_SYS_SVR_CONFIG_ID );
	}

	public void setSys_svr_config_id( String sys_svr_config_id1 )
	{
		setValue( ITEM_SYS_SVR_CONFIG_ID, sys_svr_config_id1 );
	}

	/* ���������� : String */
	public String getSys_svr_service_id()
	{
		return getValue( ITEM_SYS_SVR_SERVICE_ID );
	}

	public void setSys_svr_service_id( String sys_svr_service_id1 )
	{
		setValue( ITEM_SYS_SVR_SERVICE_ID, sys_svr_service_id1 );
	}

	/* ���������� : String */
	public String getSys_svr_user_id()
	{
		return getValue( ITEM_SYS_SVR_USER_ID );
	}

	public void setSys_svr_user_id( String sys_svr_user_id1 )
	{
		setValue( ITEM_SYS_SVR_USER_ID, sys_svr_user_id1 );
	}

	/* ������ʵ��ֶ� : String */
	public String getPermit_column()
	{
		return getValue( ITEM_PERMIT_COLUMN );
	}

	public void setPermit_column( String permit_column1 )
	{
		setValue( ITEM_PERMIT_COLUMN, permit_column1 );
	}

	/* �������� : String */
	public String getCreate_date()
	{
		return getValue( ITEM_CREATE_DATE );
	}

	public void setCreate_date( String create_date1 )
	{
		setValue( ITEM_CREATE_DATE, create_date1 );
	}

	/* ������ : String */
	public String getCreate_by()
	{
		return getValue( ITEM_CREATE_BY );
	}

	public void setCreate_by( String create_by1 )
	{
		setValue( ITEM_CREATE_BY, create_by1 );
	}

	/* �����ֶ� : String */
	public String getConfig_order()
	{
		return getValue( ITEM_CONFIG_ORDER );
	}

	public void setConfig_order( String config_order1 )
	{
		setValue( ITEM_CONFIG_ORDER, config_order1 );
	}

}

