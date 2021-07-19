package com.gwssi.dw.aic.bj.exc.que.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[exc_que_civ]�����ݶ�����
 * @author Administrator
 *
 */
public class VoExcQueCiv extends VoBase
{
	private static final long serialVersionUID = 200808291334510010L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_EXC_QUE_CIV_ID = "exc_que_civ_id" ;	/* ��������չ��ϢID */
	public static final String ITEM_ORGAN_CODE = "organ_code" ;		/* ��֯��������(��) */
	public static final String ITEM_SUPE_ORG = "supe_org" ;			/* ���쵥λ */
	public static final String ITEM_REG_CAP = "reg_cap" ;			/* ע���ʱ� */
	public static final String ITEM_CAP_CUR = "cap_cur" ;			/* ���� */
	public static final String ITEM_CARD_ORG = "card_org" ;			/* �Ǽ�֤��֤���� */
	public static final String ITEM_CARD_DATE = "card_date" ;		/* �Ǽ�֤��Ч���� */
	public static final String ITEM_REVOK_DATE = "revok_date" ;		/* ����ʱ�� */
	public static final String ITEM_CHAN_DATE = "chan_date" ;		/* ���ʱ�� */
	
	/**
	 * ���캯��
	 */
	public VoExcQueCiv()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoExcQueCiv(DataBus value)
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

	/* ��֯��������(��) : String */
	public String getOrgan_code()
	{
		return getValue( ITEM_ORGAN_CODE );
	}

	public void setOrgan_code( String organ_code1 )
	{
		setValue( ITEM_ORGAN_CODE, organ_code1 );
	}

	/* ���쵥λ : String */
	public String getSupe_org()
	{
		return getValue( ITEM_SUPE_ORG );
	}

	public void setSupe_org( String supe_org1 )
	{
		setValue( ITEM_SUPE_ORG, supe_org1 );
	}

	/* ע���ʱ� : String */
	public String getReg_cap()
	{
		return getValue( ITEM_REG_CAP );
	}

	public void setReg_cap( String reg_cap1 )
	{
		setValue( ITEM_REG_CAP, reg_cap1 );
	}

	/* ���� : String */
	public String getCap_cur()
	{
		return getValue( ITEM_CAP_CUR );
	}

	public void setCap_cur( String cap_cur1 )
	{
		setValue( ITEM_CAP_CUR, cap_cur1 );
	}

	/* �Ǽ�֤��֤���� : String */
	public String getCard_org()
	{
		return getValue( ITEM_CARD_ORG );
	}

	public void setCard_org( String card_org1 )
	{
		setValue( ITEM_CARD_ORG, card_org1 );
	}

	/* �Ǽ�֤��Ч���� : String */
	public String getCard_date()
	{
		return getValue( ITEM_CARD_DATE );
	}

	public void setCard_date( String card_date1 )
	{
		setValue( ITEM_CARD_DATE, card_date1 );
	}

	/* ����ʱ�� : String */
	public String getRevok_date()
	{
		return getValue( ITEM_REVOK_DATE );
	}

	public void setRevok_date( String revok_date1 )
	{
		setValue( ITEM_REVOK_DATE, revok_date1 );
	}

	/* ���ʱ�� : String */
	public String getChan_date()
	{
		return getValue( ITEM_CHAN_DATE );
	}

	public void setChan_date( String chan_date1 )
	{
		setValue( ITEM_CHAN_DATE, chan_date1 );
	}

}

