package com.gwssi.dw.aic.download.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[download_compare]�����ݶ�����
 * @author Administrator
 *
 */
public class VoDownloadCompare extends VoBase
{
	private static final long serialVersionUID = 200902201127580002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_DOWNLOAD_COMPARE_ID = "download_compare_id" ;	/* �ȶ�����ID */
	public static final String ITEM_TABLE_NO = "table_no" ;			/* ��ʱ���� */
	public static final String ITEM_TABLE_NAME = "table_name" ;		/* ��ʱ���� */
	public static final String ITEM_TABLE_NAME_CN = "table_name_cn" ;	/* ��ʱ�������� */
	public static final String ITEM_CREATE_USER = "create_user" ;	/* ������ */
	public static final String ITEM_CREATE_DATE = "create_date" ;	/* �������� */
	public static final String ITEM_CREATE_TIME = "create_time" ;	/* ����ʱ�� */
	public static final String ITEM_GEN_TABLE_NAME = "gen_table_name" ;	/* ���ɱ��� */
	public static final String ITEM_REMARK = "remark" ;				/* ��ע */
	public static final String ITEM_DOWNLOAD_CN_LIST = "download_cn_list" ;	/* �����ֶ����Ĵ� */
	public static final String ITEM_DOWNLOAD_EN_LIST = "download_en_list" ;	/* �����ֶ�Ӣ�Ĵ� */
	public static final String ITEM_BAK1 = "bak1" ;					/* Ԥ��1 */
	public static final String ITEM_BAK2 = "bak2" ;					/* Ԥ��2 */
	public static final String ITEM_BAK3 = "bak3" ;					/* Ԥ��3 */
	public static final String ITEM_BAK4 = "bak4" ;					/* Ԥ��4 */
	public static final String ITEM_BAK5 = "bak5" ;					/* Ԥ��5 */
	
	/**
	 * ���캯��
	 */
	public VoDownloadCompare()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoDownloadCompare(DataBus value)
	{
		super(value);
	}
	
	/* �ȶ�����ID : String */
	public String getDownload_compare_id()
	{
		return getValue( ITEM_DOWNLOAD_COMPARE_ID );
	}

	public void setDownload_compare_id( String download_compare_id1 )
	{
		setValue( ITEM_DOWNLOAD_COMPARE_ID, download_compare_id1 );
	}

	/* ��ʱ���� : String */
	public String getTable_no()
	{
		return getValue( ITEM_TABLE_NO );
	}

	public void setTable_no( String table_no1 )
	{
		setValue( ITEM_TABLE_NO, table_no1 );
	}

	/* ��ʱ���� : String */
	public String getTable_name()
	{
		return getValue( ITEM_TABLE_NAME );
	}

	public void setTable_name( String table_name1 )
	{
		setValue( ITEM_TABLE_NAME, table_name1 );
	}

	/* ��ʱ�������� : String */
	public String getTable_name_cn()
	{
		return getValue( ITEM_TABLE_NAME_CN );
	}

	public void setTable_name_cn( String table_name_cn1 )
	{
		setValue( ITEM_TABLE_NAME_CN, table_name_cn1 );
	}

	/* ������ : String */
	public String getCreate_user()
	{
		return getValue( ITEM_CREATE_USER );
	}

	public void setCreate_user( String create_user1 )
	{
		setValue( ITEM_CREATE_USER, create_user1 );
	}

	/* �������� : String */
	public String getCreate_date()
	{
		return getValue( ITEM_CREATE_DATE );
	}

	public void setCreate_date( String create_date1 )
	{
		setValue( ITEM_CREATE_DATE, create_date1 );
	}

	/* ����ʱ�� : String */
	public String getCreate_time()
	{
		return getValue( ITEM_CREATE_TIME );
	}

	public void setCreate_time( String create_time1 )
	{
		setValue( ITEM_CREATE_TIME, create_time1 );
	}

	/* ���ɱ��� : String */
	public String getGen_table_name()
	{
		return getValue( ITEM_GEN_TABLE_NAME );
	}

	public void setGen_table_name( String gen_table_name1 )
	{
		setValue( ITEM_GEN_TABLE_NAME, gen_table_name1 );
	}

	/* ��ע : String */
	public String getRemark()
	{
		return getValue( ITEM_REMARK );
	}

	public void setRemark( String remark1 )
	{
		setValue( ITEM_REMARK, remark1 );
	}

	/* �����ֶ����Ĵ� : String */
	public String getDownload_cn_list()
	{
		return getValue( ITEM_DOWNLOAD_CN_LIST );
	}

	public void setDownload_cn_list( String download_cn_list1 )
	{
		setValue( ITEM_DOWNLOAD_CN_LIST, download_cn_list1 );
	}

	/* �����ֶ�Ӣ�Ĵ� : String */
	public String getDownload_en_list()
	{
		return getValue( ITEM_DOWNLOAD_EN_LIST );
	}

	public void setDownload_en_list( String download_en_list1 )
	{
		setValue( ITEM_DOWNLOAD_EN_LIST, download_en_list1 );
	}

	/* Ԥ��1 : String */
	public String getBak1()
	{
		return getValue( ITEM_BAK1 );
	}

	public void setBak1( String bak11 )
	{
		setValue( ITEM_BAK1, bak11 );
	}

	/* Ԥ��2 : String */
	public String getBak2()
	{
		return getValue( ITEM_BAK2 );
	}

	public void setBak2( String bak21 )
	{
		setValue( ITEM_BAK2, bak21 );
	}

	/* Ԥ��3 : String */
	public String getBak3()
	{
		return getValue( ITEM_BAK3 );
	}

	public void setBak3( String bak31 )
	{
		setValue( ITEM_BAK3, bak31 );
	}

	/* Ԥ��4 : String */
	public String getBak4()
	{
		return getValue( ITEM_BAK4 );
	}

	public void setBak4( String bak41 )
	{
		setValue( ITEM_BAK4, bak41 );
	}

	/* Ԥ��5 : String */
	public String getBak5()
	{
		return getValue( ITEM_BAK5 );
	}

	public void setBak5( String bak51 )
	{
		setValue( ITEM_BAK5, bak51 );
	}

}

