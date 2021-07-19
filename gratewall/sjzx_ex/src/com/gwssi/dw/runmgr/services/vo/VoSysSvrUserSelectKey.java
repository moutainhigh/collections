package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_svr_user]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysSvrUserSelectKey extends VoBase
{
	private static final long serialVersionUID = 200805050829180003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_LOGIN_NAME = "login_name" ;		/* �û��� */
	public static final String ITEM_USER_TYPE = "user_type" ;		/* �û����� */
	
	/**
	 * ���캯��
	 */
	public VoSysSvrUserSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysSvrUserSelectKey(DataBus value)
	{
		super(value);
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

