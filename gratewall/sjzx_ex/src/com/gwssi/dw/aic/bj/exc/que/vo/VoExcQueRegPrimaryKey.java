package com.gwssi.dw.aic.bj.exc.que.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[exc_que_auth]�����ݶ�����
 * @author Administrator
 *
 */
public class VoExcQueRegPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200808291334510018L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_EXC_QUE_AUTH_ID = "exc_que_reg_id" ;	/* ��ϢID */
	
	/**
	 * ���캯��
	 */
	public VoExcQueRegPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoExcQueRegPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* �����չ��ϢID : String */
	public String getExc_que_reg_id()
	{
		return getValue( ITEM_EXC_QUE_AUTH_ID );
	}

	public void setExc_que_reg_id( String exc_que_reg_id1 )
	{
		setValue( ITEM_EXC_QUE_AUTH_ID, exc_que_reg_id1 );
	}

}

