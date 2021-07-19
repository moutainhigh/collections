package com.gwssi.sysmgr.role.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[operrolefun]�����ݶ�����
 * @author Administrator
 *
 */
public class VoOperrolefun extends VoBase
{
	private static final long serialVersionUID = 200709111111510002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_ROLEACCID = "roleaccid" ;		/* ��ɫȨ�޴��� */
	public static final String ITEM_ROLEID = "roleid" ;				/* ��ɫ��� */
	public static final String ITEM_TXNCODE = "txncode" ;			/* ���״��� */
	public static final String ITEM_DATAACCRULE = "dataaccrule" ;	/* ����Ȩ����֤���� */
	
	/**
	 * ���캯��
	 */
	public VoOperrolefun()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoOperrolefun(DataBus value)
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

	/* ��ɫ��� : String */
	public String getRoleid()
	{
		return getValue( ITEM_ROLEID );
	}

	public void setRoleid( String roleid1 )
	{
		setValue( ITEM_ROLEID, roleid1 );
	}

	/* ���״��� : String */
	public String getTxncode()
	{
		return getValue( ITEM_TXNCODE );
	}

	public void setTxncode( String txncode1 )
	{
		setValue( ITEM_TXNCODE, txncode1 );
	}

	/* ����Ȩ����֤���� : String */
	public String getDataaccrule()
	{
		return getValue( ITEM_DATAACCRULE );
	}

	public void setDataaccrule( String dataaccrule1 )
	{
		setValue( ITEM_DATAACCRULE, dataaccrule1 );
	}

}

