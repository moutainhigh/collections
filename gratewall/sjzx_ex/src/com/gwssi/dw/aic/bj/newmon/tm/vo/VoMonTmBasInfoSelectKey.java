package com.gwssi.dw.aic.bj.newmon.tm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[mon_tm_bas_info]�����ݶ�����
 * @author Administrator
 *
 */
public class VoMonTmBasInfoSelectKey extends VoBase
{
	private static final long serialVersionUID = 200811171515250003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_TM_NAME = "tm_name" ;			/* �̱����� */
	public static final String ITEM_TM_TYPE = "tm_type" ;			/* �̱����(dm) */
	public static final String ITEM_TM_REG_ID = "tm_reg_id" ;		/* �̱�ע��֤�� */
	
	/**
	 * ���캯��
	 */
	public VoMonTmBasInfoSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoMonTmBasInfoSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* �̱����� : String */
	public String getTm_name()
	{
		return getValue( ITEM_TM_NAME );
	}

	public void setTm_name( String tm_name1 )
	{
		setValue( ITEM_TM_NAME, tm_name1 );
	}

	/* �̱����(dm) : String */
	public String getTm_type()
	{
		return getValue( ITEM_TM_TYPE );
	}

	public void setTm_type( String tm_type1 )
	{
		setValue( ITEM_TM_TYPE, tm_type1 );
	}

	/* �̱�ע��֤�� : String */
	public String getTm_reg_id()
	{
		return getValue( ITEM_TM_REG_ID );
	}

	public void setTm_reg_id( String tm_reg_id1 )
	{
		setValue( ITEM_TM_REG_ID, tm_reg_id1 );
	}

}

