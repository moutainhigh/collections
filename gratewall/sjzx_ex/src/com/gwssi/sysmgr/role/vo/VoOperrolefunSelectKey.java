package com.gwssi.sysmgr.role.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[operrolefun]�����ݶ�����
 * @author Administrator
 *
 */
public class VoOperrolefunSelectKey extends VoBase
{
	private static final long serialVersionUID = 200709111111510003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_ROLEID = "roleid" ;				/* ��ɫ��� */
	
	/**
	 * ���캯��
	 */
	public VoOperrolefunSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoOperrolefunSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* ��ɫ��� : String */
	public String getRoleid()
	{
		return getValue( ITEM_ROLEID );
	}

	public void setRoleid( String roleid1 )
	{
		setValue( ITEM_ROLEID, roleid1 );
	}

}

