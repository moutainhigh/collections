package com.gwssi.dw.aic.bj.exc.que.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[exc_que_auth]�����ݶ�����
 * @author Administrator
 *
 */
public class VoExcQueAuthPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200808291334510008L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_EXC_QUE_AUTH_ID = "exc_que_auth_id" ;	/* �����չ��ϢID */
	
	/**
	 * ���캯��
	 */
	public VoExcQueAuthPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoExcQueAuthPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* �����չ��ϢID : String */
	public String getExc_que_auth_id()
	{
		return getValue( ITEM_EXC_QUE_AUTH_ID );
	}

	public void setExc_que_auth_id( String exc_que_auth_id1 )
	{
		setValue( ITEM_EXC_QUE_AUTH_ID, exc_que_auth_id1 );
	}

}

