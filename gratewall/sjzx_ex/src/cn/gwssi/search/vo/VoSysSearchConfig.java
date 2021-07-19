package cn.gwssi.search.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_search_config]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysSearchConfig extends VoBase
{
	private static final long serialVersionUID = 201208211741000002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_SEARCH_CONFIG_ID = "sys_search_config_id" ;	/* ��������ID */
	public static final String ITEM_SYS_SVR_USER_ID = "sys_svr_user_id" ;	/* �û�ID */
	public static final String ITEM_PERMIT_SUBJECT = "permit_subject" ;	/* ��Ȩ���� */
	public static final String ITEM_CREATE_BY = "create_by" ;		/* ������ */
	public static final String ITEM_CREATE_DATE = "create_date" ;	/* �������� */
	public static final String ITEM_CONFIG_ORDER = "config_order" ;	/* ����˳�� */
	public static final String ITEM_IS_PAUSE = "is_pause" ;			/* �Ƿ���ͣ */
	
	/**
	 * ���캯��
	 */
	public VoSysSearchConfig()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysSearchConfig(DataBus value)
	{
		super(value);
	}
	
	/* ��������ID : String */
	public String getSys_search_config_id()
	{
		return getValue( ITEM_SYS_SEARCH_CONFIG_ID );
	}

	public void setSys_search_config_id( String sys_search_config_id1 )
	{
		setValue( ITEM_SYS_SEARCH_CONFIG_ID, sys_search_config_id1 );
	}

	/* �û�ID : String */
	public String getSys_svr_user_id()
	{
		return getValue( ITEM_SYS_SVR_USER_ID );
	}

	public void setSys_svr_user_id( String sys_svr_user_id1 )
	{
		setValue( ITEM_SYS_SVR_USER_ID, sys_svr_user_id1 );
	}

	/* ��Ȩ���� : String */
	public String getPermit_subject()
	{
		return getValue( ITEM_PERMIT_SUBJECT );
	}

	public void setPermit_subject( String permit_subject1 )
	{
		setValue( ITEM_PERMIT_SUBJECT, permit_subject1 );
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

	/* �������� : String */
	public String getCreate_date()
	{
		return getValue( ITEM_CREATE_DATE );
	}

	public void setCreate_date( String create_date1 )
	{
		setValue( ITEM_CREATE_DATE, create_date1 );
	}

	/* ����˳�� : String */
	public String getConfig_order()
	{
		return getValue( ITEM_CONFIG_ORDER );
	}

	public void setConfig_order( String config_order1 )
	{
		setValue( ITEM_CONFIG_ORDER, config_order1 );
	}

	/* �Ƿ���ͣ : String */
	public String getIs_pause()
	{
		return getValue( ITEM_IS_PAUSE );
	}

	public void setIs_pause( String is_pause1 )
	{
		setValue( ITEM_IS_PAUSE, is_pause1 );
	}

}

