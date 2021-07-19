package com.gwssi.sysmgr.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_popwindow]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysPopwindow extends VoBase
{
	private static final long serialVersionUID = 200907271007430002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_POPWINDOW_ID = "sys_popwindow_id" ;	/* ϵͳ����ID */
	public static final String ITEM_CONTENT = "content" ;			/* ���� */
	public static final String ITEM_PUBLISH_DATE = "publish_date" ;	/* �������� */
	public static final String ITEM_EXPIRE_DATE = "expire_date" ;	/* �������� */
	public static final String ITEM_ROLES = "roles" ;				/* ��ɫ�б� */
	
	/**
	 * ���캯��
	 */
	public VoSysPopwindow()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysPopwindow(DataBus value)
	{
		super(value);
	}
	
	/* ϵͳ����ID : String */
	public String getSys_popwindow_id()
	{
		return getValue( ITEM_SYS_POPWINDOW_ID );
	}

	public void setSys_popwindow_id( String sys_popwindow_id1 )
	{
		setValue( ITEM_SYS_POPWINDOW_ID, sys_popwindow_id1 );
	}

	/* ���� : String */
	public String getContent()
	{
		return getValue( ITEM_CONTENT );
	}

	public void setContent( String content1 )
	{
		setValue( ITEM_CONTENT, content1 );
	}

	/* �������� : String */
	public String getPublish_date()
	{
		return getValue( ITEM_PUBLISH_DATE );
	}

	public void setPublish_date( String publish_date1 )
	{
		setValue( ITEM_PUBLISH_DATE, publish_date1 );
	}

	/* �������� : String */
	public String getExpire_date()
	{
		return getValue( ITEM_EXPIRE_DATE );
	}

	public void setExpire_date( String expire_date1 )
	{
		setValue( ITEM_EXPIRE_DATE, expire_date1 );
	}

	/* ��ɫ�б� : String */
	public String getRoles()
	{
		return getValue( ITEM_ROLES );
	}

	public void setRoles( String roles1 )
	{
		setValue( ITEM_ROLES, roles1 );
	}

}

