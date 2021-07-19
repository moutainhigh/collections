package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_svr_user]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysSvrUser extends VoBase
{
	private static final long serialVersionUID = 200805050829180002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_SVR_USER_ID = "sys_svr_user_id" ;	/* ��������� */
	public static final String ITEM_LOGIN_NAME = "login_name" ;		/* �û��� */
	public static final String ITEM_PASSWORD = "password" ;			/* ���� */
	public static final String ITEM_CREATE_DATE = "create_date" ;	/* �������� */
	public static final String ITEM_CREATE_BY = "create_by" ;		/* ������ */
	public static final String ITEM_STATE = "state" ;				/* ״̬ */
	public static final String ITEM_DESC = "desc" ;					/* ���� */
	public static final String ITEM_USER_TYPE = "user_type" ;		/* �û����� */
	
	/**
	 * ���캯��
	 */
	public VoSysSvrUser()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysSvrUser(DataBus value)
	{
		super(value);
	}
	
	/* ��������� : String */
	public String getSys_svr_user_id()
	{
		return getValue( ITEM_SYS_SVR_USER_ID );
	}

	public void setSys_svr_user_id( String sys_svr_user_id1 )
	{
		setValue( ITEM_SYS_SVR_USER_ID, sys_svr_user_id1 );
	}

	/* �û��� : String */
	public String getLogin_name()
	{
		return getValue( ITEM_LOGIN_NAME );
	}

	public void setLogin_name( String login_name1 )
	{
		setValue( ITEM_LOGIN_NAME, login_name1 );
	}

	/* ���� : String */
	public String getPassword()
	{
		return getValue( ITEM_PASSWORD );
	}

	public void setPassword( String password1 )
	{
		setValue( ITEM_PASSWORD, password1 );
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

	/* ״̬ : String */
	public String getState()
	{
		return getValue( ITEM_STATE );
	}

	public void setState( String state1 )
	{
		setValue( ITEM_STATE, state1 );
	}

	/* ���� : String */
	public String getDesc()
	{
		return getValue( ITEM_DESC );
	}

	public void setDesc( String desc1 )
	{
		setValue( ITEM_DESC, desc1 );
	}

	/* �û����� : String */
	public String getUser_type()
	{
		return getValue( ITEM_USER_TYPE );
	}

	public void setUser_type( String user_type1 )
	{
		setValue( ITEM_USER_TYPE, user_type1 );
	}

}

