package com.gwssi.file.manage.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[xt_ccgl_wjys]�����ݶ�����
 * @author Administrator
 *
 */
public class VoXtCcglWjys extends VoBase
{
	private static final long serialVersionUID = 201303271612500006L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_YSBH_PK = "ysbh_pk" ;			/* ӳ���� */
	public static final String ITEM_CCLBBH_PK = "cclbbh_pk" ;		/* �����ID */
	public static final String ITEM_WYBS = "wybs" ;					/* Ψһ��ʶ */
	public static final String ITEM_WJMC = "wjmc" ;					/* �ļ����� */
	public static final String ITEM_WJZT = "wjzt" ;					/* �ļ�״̬ */
	public static final String ITEM_CCLJ = "cclj" ;					/* �洢·�� */
	public static final String ITEM_CJSJ = "cjsj" ;					/* ����ʱ�� */
	public static final String ITEM_SCXGSJ = "scxgsj" ;				/* �ϴ��޸�ʱ�� */
	public static final String ITEM_BZ = "bz" ;						/* ��ע */
	public static final String ITEM_YWBZ = "ywbz" ;					/* ҵ��ע */
	public static final String ITEM_XM_FK = "xm_fk" ;				/* ��Ŀfk */
	
	/**
	 * ���캯��
	 */
	public VoXtCcglWjys()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoXtCcglWjys(DataBus value)
	{
		super(value);
	}
	
	/* ӳ���� : String */
	public String getYsbh_pk()
	{
		return getValue( ITEM_YSBH_PK );
	}

	public void setYsbh_pk( String ysbh_pk1 )
	{
		setValue( ITEM_YSBH_PK, ysbh_pk1 );
	}

	/* �����ID : String */
	public String getCclbbh_pk()
	{
		return getValue( ITEM_CCLBBH_PK );
	}

	public void setCclbbh_pk( String cclbbh_pk1 )
	{
		setValue( ITEM_CCLBBH_PK, cclbbh_pk1 );
	}

	/* Ψһ��ʶ : String */
	public String getWybs()
	{
		return getValue( ITEM_WYBS );
	}

	public void setWybs( String wybs1 )
	{
		setValue( ITEM_WYBS, wybs1 );
	}

	/* �ļ����� : String */
	public String getWjmc()
	{
		return getValue( ITEM_WJMC );
	}

	public void setWjmc( String wjmc1 )
	{
		setValue( ITEM_WJMC, wjmc1 );
	}

	/* �ļ�״̬ : String */
	public String getWjzt()
	{
		return getValue( ITEM_WJZT );
	}

	public void setWjzt( String wjzt1 )
	{
		setValue( ITEM_WJZT, wjzt1 );
	}

	/* �洢·�� : String */
	public String getCclj()
	{
		return getValue( ITEM_CCLJ );
	}

	public void setCclj( String cclj1 )
	{
		setValue( ITEM_CCLJ, cclj1 );
	}

	/* ����ʱ�� : String */
	public String getCjsj()
	{
		return getValue( ITEM_CJSJ );
	}

	public void setCjsj( String cjsj1 )
	{
		setValue( ITEM_CJSJ, cjsj1 );
	}

	/* �ϴ��޸�ʱ�� : String */
	public String getScxgsj()
	{
		return getValue( ITEM_SCXGSJ );
	}

	public void setScxgsj( String scxgsj1 )
	{
		setValue( ITEM_SCXGSJ, scxgsj1 );
	}

	/* ��ע : String */
	public String getBz()
	{
		return getValue( ITEM_BZ );
	}

	public void setBz( String bz1 )
	{
		setValue( ITEM_BZ, bz1 );
	}

	/* ҵ��ע : String */
	public String getYwbz()
	{
		return getValue( ITEM_YWBZ );
	}

	public void setYwbz( String ywbz1 )
	{
		setValue( ITEM_YWBZ, ywbz1 );
	}

	/* ��Ŀfk : String */
	public String getXm_fk()
	{
		return getValue( ITEM_XM_FK );
	}

	public void setXm_fk( String xm_fk1 )
	{
		setValue( ITEM_XM_FK, xm_fk1 );
	}

}

