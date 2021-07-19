package com.gwssi.dw.aic.bj.exc.que.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[exc_que_civ]�����ݶ�����
 * @author Administrator
 *
 */
public class VoExcQueCivPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200808291334510012L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_EXC_QUE_CIV_ID = "exc_que_civ_id" ;	/* ��������չ��ϢID */
	
	/**
	 * ���캯��
	 */
	public VoExcQueCivPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoExcQueCivPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ��������չ��ϢID : String */
	public String getExc_que_civ_id()
	{
		return getValue( ITEM_EXC_QUE_CIV_ID );
	}

	public void setExc_que_civ_id( String exc_que_civ_id1 )
	{
		setValue( ITEM_EXC_QUE_CIV_ID, exc_que_civ_id1 );
	}

}

