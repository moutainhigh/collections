package com.gwssi.dw.aic.bj.exc.que.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[exc_que_reg]�����ݶ�����
 * @author Administrator
 *
 */
public class VoExcQueRegSelectKey extends VoBase
{
	private static final long serialVersionUID = 200808291334510003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_EXC_QUE_REG_ID = "exc_que_reg_id" ;	/* �ʼ취�˿�ID */
	
	/**
	 * ���캯��
	 */
	public VoExcQueRegSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoExcQueRegSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* �ʼ취�˿�ID : String */
	public String getExc_que_reg_id()
	{
		return getValue( ITEM_EXC_QUE_REG_ID );
	}

	public void setExc_que_reg_id( String exc_que_reg_id1 )
	{
		setValue( ITEM_EXC_QUE_REG_ID, exc_que_reg_id1 );
	}

}

