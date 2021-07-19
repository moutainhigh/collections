package com.gwssi.dw.aic.bj.homepage.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[work_status]�����ݶ�����
 * @author Administrator
 *
 */
public class VoWorkStatusPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200812041106360004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_WORK_STATUS_ID = "work_status_id" ;	/* ����״̬ID */
	
	/**
	 * ���캯��
	 */
	public VoWorkStatusPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoWorkStatusPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ����״̬ID : String */
	public String getWork_status_id()
	{
		return getValue( ITEM_WORK_STATUS_ID );
	}

	public void setWork_status_id( String work_status_id1 )
	{
		setValue( ITEM_WORK_STATUS_ID, work_status_id1 );
	}

}

