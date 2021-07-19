package com.gwssi.log.collectlog.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[collect_joumal]�����ݶ�����
 * @author Administrator
 *
 */
public class VoCollectJoumalPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201304101519320004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_COLLECT_JOUMAL_ID = "collect_joumal_id" ;	/* �ɼ���־ID */
	
	/**
	 * ���캯��
	 */
	public VoCollectJoumalPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoCollectJoumalPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* �ɼ���־ID : String */
	public String getCollect_joumal_id()
	{
		return getValue( ITEM_COLLECT_JOUMAL_ID );
	}

	public void setCollect_joumal_id( String collect_joumal_id1 )
	{
		setValue( ITEM_COLLECT_JOUMAL_ID, collect_joumal_id1 );
	}

}

