package com.gwssi.sysmgr.role.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[operrolefun]�����ݶ�����
 * @author Administrator
 *
 */
public class VoOperrolefunPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200709111111520004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_ROLEACCID = "roleaccid" ;		/* ��ɫȨ�޴��� */
	
	/**
	 * ���캯��
	 */
	public VoOperrolefunPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoOperrolefunPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ��ɫȨ�޴��� : String */
	public String getRoleaccid()
	{
		return getValue( ITEM_ROLEACCID );
	}

	public void setRoleaccid( String roleaccid1 )
	{
		setValue( ITEM_ROLEACCID, roleaccid1 );
	}

}

