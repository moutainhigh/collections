package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_notice_info]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysNoticeInfo extends VoBase
{
	private static final long serialVersionUID = 200810151621200002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_NOTICE_ID = "sys_notice_id" ;	/* ֪ͨͨ��ID */
	public static final String ITEM_SYS_NOTICE_TITLE = "sys_notice_title" ;	/* ���� */
	public static final String ITEM_SYS_NOTICE_MATTER = "sys_notice_matter" ;	/* ���� */
	public static final String ITEM_SYS_NOTICE_PROMULGATOR = "sys_notice_promulgator" ;	/* ������ */
	public static final String ITEM_SYS_NOTICE_ORG = "sys_notice_org" ;	/* ������λ */
	public static final String ITEM_SYS_NOTICE_DATE = "sys_notice_date" ;	/* ����ʱ�� */
	public static final String ITEM_SYS_NOTICE_STATE = "sys_notice_state" ;	/* ����״̬ */
	public static final String ITEM_SYS_NOTICE_FILEPATH = "sys_notice_filepath" ;	/* ���� */
	
	/**
	 * ���캯��
	 */
	public VoSysNoticeInfo()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysNoticeInfo(DataBus value)
	{
		super(value);
	}
	
	/* ֪ͨͨ��ID : String */
	public String getSys_notice_id()
	{
		return getValue( ITEM_SYS_NOTICE_ID );
	}

	public void setSys_notice_id( String sys_notice_id1 )
	{
		setValue( ITEM_SYS_NOTICE_ID, sys_notice_id1 );
	}

	/* ���� : String */
	public String getSys_notice_title()
	{
		return getValue( ITEM_SYS_NOTICE_TITLE );
	}

	public void setSys_notice_title( String sys_notice_title1 )
	{
		setValue( ITEM_SYS_NOTICE_TITLE, sys_notice_title1 );
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

	/* ������ : String */
	public String getSys_notice_promulgator()
	{
		return getValue( ITEM_SYS_NOTICE_PROMULGATOR );
	}

	public void setSys_notice_promulgator( String sys_notice_promulgator1 )
	{
		setValue( ITEM_SYS_NOTICE_PROMULGATOR, sys_notice_promulgator1 );
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

	/* ����ʱ�� : String */
	public String getSys_notice_date()
	{
		return getValue( ITEM_SYS_NOTICE_DATE );
	}

	public void setSys_notice_date( String sys_notice_date1 )
	{
		setValue( ITEM_SYS_NOTICE_DATE, sys_notice_date1 );
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

	/* ���� : String */
	public String getSys_notice_filepath()
	{
		return getValue( ITEM_SYS_NOTICE_FILEPATH );
	}

	public void setSys_notice_filepath( String sys_notice_filepath1 )
	{
		setValue( ITEM_SYS_NOTICE_FILEPATH, sys_notice_filepath1 );
	}

}

