package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_notice_info]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysNoticeInfoSelectKey extends VoBase
{
	private static final long serialVersionUID = 200810151621200003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_NOTICE_MATTER = "sys_notice_matter" ;	/* ���� */
	public static final String ITEM_SYS_NOTICE_DATE = "sys_notice_date" ;	/* ����ʱ�� */
	public static final String ITEM_SYS_NOTICE_ORG = "sys_notice_org" ;	/* ������λ */
	public static final String ITEM_SYS_NOTICE_PROMULGATOR = "sys_notice_promulgator" ;	/* ������ */
	public static final String ITEM_SYS_NOTICE_STATE = "sys_notice_state" ;	/* ����״̬ */
	
	/**
	 * ���캯��
	 */
	public VoSysNoticeInfoSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysNoticeInfoSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* ���� : String */
	public String getSys_notice_matter()
	{
		return getValue( ITEM_SYS_NOTICE_MATTER );
	}

	public void setSys_notice_matter( String sys_notice_matter1 )
	{
		setValue( ITEM_SYS_NOTICE_MATTER, sys_notice_matter1 );
	}

	/* ����ʱ�� : String */
	public String getSys_notice_date()
	{
		return getValue( ITEM_SYS_NOTICE_DATE );
	}

	public void setSys_notice_date( String sys_notice_date1 )
	{
		setValue( ITEM_SYS_NOTICE_DATE, sys_notice_date1 );
	}

	/* ������λ : String */
	public String getSys_notice_org()
	{
		return getValue( ITEM_SYS_NOTICE_ORG );
	}

	public void setSys_notice_org( String sys_notice_org1 )
	{
		setValue( ITEM_SYS_NOTICE_ORG, sys_notice_org1 );
	}

	/* ������ : String */
	public String getSys_notice_promulgator()
	{
		return getValue( ITEM_SYS_NOTICE_PROMULGATOR );
	}

	public void setSys_notice_promulgator( String sys_notice_promulgator1 )
	{
		setValue( ITEM_SYS_NOTICE_PROMULGATOR, sys_notice_promulgator1 );
	}

	/* ����״̬ : String */
	public String getSys_notice_state()
	{
		return getValue( ITEM_SYS_NOTICE_STATE );
	}

	public void setSys_notice_state( String sys_notice_state1 )
	{
		setValue( ITEM_SYS_NOTICE_STATE, sys_notice_state1 );
	}

}

