package com.gwssi.dw.aic.bj.homepage.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[work_status]�����ݶ�����
 * @author Administrator
 *
 */
public class VoWorkStatus extends VoBase
{
	private static final long serialVersionUID = 200812041106360002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_WORK_STATUS_ID = "work_status_id" ;	/* ����״̬ID */
	public static final String ITEM_WORK_STATUS_NAME = "work_status_name" ;	/* ������������ */
	public static final String ITEM_WORK_STATUS_CONTENT = "work_status_content" ;	/* ������������ */
	public static final String ITEM_INITIATOR_ID = "initiator_id" ;	/* ������ID */
	public static final String ITEM_INITIATOR_NAME = "initiator_name" ;	/* ���������� */
	public static final String ITEM_INITIATOR_AGEN_ID = "initiator_agen_id" ;	/* �����˻���ID */
	public static final String ITEM_INITIATOR_AGEN_NAME = "initiator_agen_name" ;	/* �����˻������� */
	public static final String ITEM_INITIATOR_DATE = "initiator_date" ;	/* �������� */
	public static final String ITEM_INITIATOR_TIME = "initiator_time" ;	/* ����ʱ�� */
	public static final String ITEM_LINK_URL = "link_url" ;			/* ���ӵ�ַ */
	public static final String ITEM_TXNCODE = "txncode" ;			/* ���״��� */
	public static final String ITEM_ISVALID = "isvalid" ;			/* �Ƿ���Ч */
	public static final String ITEM_SCOUR_TABLE_NAME = "scour_table_name" ;	/* Դ���� */
	public static final String ITEM_SCOUR_TABLE_KEY_COL = "scour_table_key_col" ;	/* Դ������ID */
	public static final String ITEM_BAK_COL_1 = "bak_col_1" ;		/* Ԥ��1 */
	public static final String ITEM_BAK_COL_2 = "bak_col_2" ;		/* Ԥ��2 */
	public static final String ITEM_BAK_COL_3 = "bak_col_3" ;		/* Ԥ��3 */
	public static final String ITEM_BAK_COL_4 = "bak_col_4" ;		/* Ԥ��4 */
	public static final String ITEM_BAK_COL_5 = "bak_col_5" ;		/* Ԥ��5 */
	
	/**
	 * ���캯��
	 */
	public VoWorkStatus()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoWorkStatus(DataBus value)
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

	/* ������������ : String */
	public String getWork_status_name()
	{
		return getValue( ITEM_WORK_STATUS_NAME );
	}

	public void setWork_status_name( String work_status_name1 )
	{
		setValue( ITEM_WORK_STATUS_NAME, work_status_name1 );
	}

	/* ������������ : String */
	public String getWork_status_content()
	{
		return getValue( ITEM_WORK_STATUS_CONTENT );
	}

	public void setWork_status_content( String work_status_content1 )
	{
		setValue( ITEM_WORK_STATUS_CONTENT, work_status_content1 );
	}

	/* ������ID : String */
	public String getInitiator_id()
	{
		return getValue( ITEM_INITIATOR_ID );
	}

	public void setInitiator_id( String initiator_id1 )
	{
		setValue( ITEM_INITIATOR_ID, initiator_id1 );
	}

	/* ���������� : String */
	public String getInitiator_name()
	{
		return getValue( ITEM_INITIATOR_NAME );
	}

	public void setInitiator_name( String initiator_name1 )
	{
		setValue( ITEM_INITIATOR_NAME, initiator_name1 );
	}

	/* �����˻���ID : String */
	public String getInitiator_agen_id()
	{
		return getValue( ITEM_INITIATOR_AGEN_ID );
	}

	public void setInitiator_agen_id( String initiator_agen_id1 )
	{
		setValue( ITEM_INITIATOR_AGEN_ID, initiator_agen_id1 );
	}

	/* �����˻������� : String */
	public String getInitiator_agen_name()
	{
		return getValue( ITEM_INITIATOR_AGEN_NAME );
	}

	public void setInitiator_agen_name( String initiator_agen_name1 )
	{
		setValue( ITEM_INITIATOR_AGEN_NAME, initiator_agen_name1 );
	}

	/* �������� : String */
	public String getInitiator_date()
	{
		return getValue( ITEM_INITIATOR_DATE );
	}

	public void setInitiator_date( String initiator_date1 )
	{
		setValue( ITEM_INITIATOR_DATE, initiator_date1 );
	}

	/* ����ʱ�� : String */
	public String getInitiator_time()
	{
		return getValue( ITEM_INITIATOR_TIME );
	}

	public void setInitiator_time( String initiator_time1 )
	{
		setValue( ITEM_INITIATOR_TIME, initiator_time1 );
	}

	/* ���ӵ�ַ : String */
	public String getLink_url()
	{
		return getValue( ITEM_LINK_URL );
	}

	public void setLink_url( String link_url1 )
	{
		setValue( ITEM_LINK_URL, link_url1 );
	}

	/* ���״��� : String */
	public String getTxncode()
	{
		return getValue( ITEM_TXNCODE );
	}

	public void setTxncode( String txncode1 )
	{
		setValue( ITEM_TXNCODE, txncode1 );
	}

	/* �Ƿ���Ч : String */
	public String getIsvalid()
	{
		return getValue( ITEM_ISVALID );
	}

	public void setIsvalid( String isvalid1 )
	{
		setValue( ITEM_ISVALID, isvalid1 );
	}

	/* Դ���� : String */
	public String getScour_table_name()
	{
		return getValue( ITEM_SCOUR_TABLE_NAME );
	}

	public void setScour_table_name( String scour_table_name1 )
	{
		setValue( ITEM_SCOUR_TABLE_NAME, scour_table_name1 );
	}

	/* Դ������ID : String */
	public String getScour_table_key_col()
	{
		return getValue( ITEM_SCOUR_TABLE_KEY_COL );
	}

	public void setScour_table_key_col( String scour_table_key_col1 )
	{
		setValue( ITEM_SCOUR_TABLE_KEY_COL, scour_table_key_col1 );
	}

	/* Ԥ��1 : String */
	public String getBak_col_1()
	{
		return getValue( ITEM_BAK_COL_1 );
	}

	public void setBak_col_1( String bak_col_11 )
	{
		setValue( ITEM_BAK_COL_1, bak_col_11 );
	}

	/* Ԥ��2 : String */
	public String getBak_col_2()
	{
		return getValue( ITEM_BAK_COL_2 );
	}

	public void setBak_col_2( String bak_col_21 )
	{
		setValue( ITEM_BAK_COL_2, bak_col_21 );
	}

	/* Ԥ��3 : String */
	public String getBak_col_3()
	{
		return getValue( ITEM_BAK_COL_3 );
	}

	public void setBak_col_3( String bak_col_31 )
	{
		setValue( ITEM_BAK_COL_3, bak_col_31 );
	}

	/* Ԥ��4 : String */
	public String getBak_col_4()
	{
		return getValue( ITEM_BAK_COL_4 );
	}

	public void setBak_col_4( String bak_col_41 )
	{
		setValue( ITEM_BAK_COL_4, bak_col_41 );
	}

	/* Ԥ��5 : String */
	public String getBak_col_5()
	{
		return getValue( ITEM_BAK_COL_5 );
	}

	public void setBak_col_5( String bak_col_51 )
	{
		setValue( ITEM_BAK_COL_5, bak_col_51 );
	}

}

