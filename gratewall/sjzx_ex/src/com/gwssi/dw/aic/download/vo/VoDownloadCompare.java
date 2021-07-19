package com.gwssi.dw.aic.download.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[download_compare]的数据对象类
 * @author Administrator
 *
 */
public class VoDownloadCompare extends VoBase
{
	private static final long serialVersionUID = 200902201127580002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_DOWNLOAD_COMPARE_ID = "download_compare_id" ;	/* 比对下载ID */
	public static final String ITEM_TABLE_NO = "table_no" ;			/* 临时表编号 */
	public static final String ITEM_TABLE_NAME = "table_name" ;		/* 临时表名 */
	public static final String ITEM_TABLE_NAME_CN = "table_name_cn" ;	/* 临时表中文名 */
	public static final String ITEM_CREATE_USER = "create_user" ;	/* 创建者 */
	public static final String ITEM_CREATE_DATE = "create_date" ;	/* 创建日期 */
	public static final String ITEM_CREATE_TIME = "create_time" ;	/* 创建时间 */
	public static final String ITEM_GEN_TABLE_NAME = "gen_table_name" ;	/* 生成表名 */
	public static final String ITEM_REMARK = "remark" ;				/* 备注 */
	public static final String ITEM_DOWNLOAD_CN_LIST = "download_cn_list" ;	/* 下载字段中文串 */
	public static final String ITEM_DOWNLOAD_EN_LIST = "download_en_list" ;	/* 下载字段英文串 */
	public static final String ITEM_BAK1 = "bak1" ;					/* 预留1 */
	public static final String ITEM_BAK2 = "bak2" ;					/* 预留2 */
	public static final String ITEM_BAK3 = "bak3" ;					/* 预留3 */
	public static final String ITEM_BAK4 = "bak4" ;					/* 预留4 */
	public static final String ITEM_BAK5 = "bak5" ;					/* 预留5 */
	
	/**
	 * 构造函数
	 */
	public VoDownloadCompare()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoDownloadCompare(DataBus value)
	{
		super(value);
	}
	
	/* 比对下载ID : String */
	public String getDownload_compare_id()
	{
		return getValue( ITEM_DOWNLOAD_COMPARE_ID );
	}

	public void setDownload_compare_id( String download_compare_id1 )
	{
		setValue( ITEM_DOWNLOAD_COMPARE_ID, download_compare_id1 );
	}

	/* 临时表编号 : String */
	public String getTable_no()
	{
		return getValue( ITEM_TABLE_NO );
	}

	public void setTable_no( String table_no1 )
	{
		setValue( ITEM_TABLE_NO, table_no1 );
	}

	/* 临时表名 : String */
	public String getTable_name()
	{
		return getValue( ITEM_TABLE_NAME );
	}

	public void setTable_name( String table_name1 )
	{
		setValue( ITEM_TABLE_NAME, table_name1 );
	}

	/* 临时表中文名 : String */
	public String getTable_name_cn()
	{
		return getValue( ITEM_TABLE_NAME_CN );
	}

	public void setTable_name_cn( String table_name_cn1 )
	{
		setValue( ITEM_TABLE_NAME_CN, table_name_cn1 );
	}

	/* 创建者 : String */
	public String getCreate_user()
	{
		return getValue( ITEM_CREATE_USER );
	}

	public void setCreate_user( String create_user1 )
	{
		setValue( ITEM_CREATE_USER, create_user1 );
	}

	/* 创建日期 : String */
	public String getCreate_date()
	{
		return getValue( ITEM_CREATE_DATE );
	}

	public void setCreate_date( String create_date1 )
	{
		setValue( ITEM_CREATE_DATE, create_date1 );
	}

	/* 创建时间 : String */
	public String getCreate_time()
	{
		return getValue( ITEM_CREATE_TIME );
	}

	public void setCreate_time( String create_time1 )
	{
		setValue( ITEM_CREATE_TIME, create_time1 );
	}

	/* 生成表名 : String */
	public String getGen_table_name()
	{
		return getValue( ITEM_GEN_TABLE_NAME );
	}

	public void setGen_table_name( String gen_table_name1 )
	{
		setValue( ITEM_GEN_TABLE_NAME, gen_table_name1 );
	}

	/* 备注 : String */
	public String getRemark()
	{
		return getValue( ITEM_REMARK );
	}

	public void setRemark( String remark1 )
	{
		setValue( ITEM_REMARK, remark1 );
	}

	/* 下载字段中文串 : String */
	public String getDownload_cn_list()
	{
		return getValue( ITEM_DOWNLOAD_CN_LIST );
	}

	public void setDownload_cn_list( String download_cn_list1 )
	{
		setValue( ITEM_DOWNLOAD_CN_LIST, download_cn_list1 );
	}

	/* 下载字段英文串 : String */
	public String getDownload_en_list()
	{
		return getValue( ITEM_DOWNLOAD_EN_LIST );
	}

	public void setDownload_en_list( String download_en_list1 )
	{
		setValue( ITEM_DOWNLOAD_EN_LIST, download_en_list1 );
	}

	/* 预留1 : String */
	public String getBak1()
	{
		return getValue( ITEM_BAK1 );
	}

	public void setBak1( String bak11 )
	{
		setValue( ITEM_BAK1, bak11 );
	}

	/* 预留2 : String */
	public String getBak2()
	{
		return getValue( ITEM_BAK2 );
	}

	public void setBak2( String bak21 )
	{
		setValue( ITEM_BAK2, bak21 );
	}

	/* 预留3 : String */
	public String getBak3()
	{
		return getValue( ITEM_BAK3 );
	}

	public void setBak3( String bak31 )
	{
		setValue( ITEM_BAK3, bak31 );
	}

	/* 预留4 : String */
	public String getBak4()
	{
		return getValue( ITEM_BAK4 );
	}

	public void setBak4( String bak41 )
	{
		setValue( ITEM_BAK4, bak41 );
	}

	/* 预留5 : String */
	public String getBak5()
	{
		return getValue( ITEM_BAK5 );
	}

	public void setBak5( String bak51 )
	{
		setValue( ITEM_BAK5, bak51 );
	}

}

