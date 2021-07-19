package com.gwssi.dw.aic.bj.homepage.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[work_status]的数据对象类
 * @author Administrator
 *
 */
public class VoWorkStatus extends VoBase
{
	private static final long serialVersionUID = 200812041106360002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_WORK_STATUS_ID = "work_status_id" ;	/* 工作状态ID */
	public static final String ITEM_WORK_STATUS_NAME = "work_status_name" ;	/* 待办任务名称 */
	public static final String ITEM_WORK_STATUS_CONTENT = "work_status_content" ;	/* 待办任务内容 */
	public static final String ITEM_INITIATOR_ID = "initiator_id" ;	/* 发起人ID */
	public static final String ITEM_INITIATOR_NAME = "initiator_name" ;	/* 发起人名称 */
	public static final String ITEM_INITIATOR_AGEN_ID = "initiator_agen_id" ;	/* 发起人机构ID */
	public static final String ITEM_INITIATOR_AGEN_NAME = "initiator_agen_name" ;	/* 发起人机构名称 */
	public static final String ITEM_INITIATOR_DATE = "initiator_date" ;	/* 发起日期 */
	public static final String ITEM_INITIATOR_TIME = "initiator_time" ;	/* 发起时间 */
	public static final String ITEM_LINK_URL = "link_url" ;			/* 链接地址 */
	public static final String ITEM_TXNCODE = "txncode" ;			/* 交易代码 */
	public static final String ITEM_ISVALID = "isvalid" ;			/* 是否有效 */
	public static final String ITEM_SCOUR_TABLE_NAME = "scour_table_name" ;	/* 源表名 */
	public static final String ITEM_SCOUR_TABLE_KEY_COL = "scour_table_key_col" ;	/* 源表主键ID */
	public static final String ITEM_BAK_COL_1 = "bak_col_1" ;		/* 预留1 */
	public static final String ITEM_BAK_COL_2 = "bak_col_2" ;		/* 预留2 */
	public static final String ITEM_BAK_COL_3 = "bak_col_3" ;		/* 预留3 */
	public static final String ITEM_BAK_COL_4 = "bak_col_4" ;		/* 预留4 */
	public static final String ITEM_BAK_COL_5 = "bak_col_5" ;		/* 预留5 */
	
	/**
	 * 构造函数
	 */
	public VoWorkStatus()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoWorkStatus(DataBus value)
	{
		super(value);
	}
	
	/* 工作状态ID : String */
	public String getWork_status_id()
	{
		return getValue( ITEM_WORK_STATUS_ID );
	}

	public void setWork_status_id( String work_status_id1 )
	{
		setValue( ITEM_WORK_STATUS_ID, work_status_id1 );
	}

	/* 待办任务名称 : String */
	public String getWork_status_name()
	{
		return getValue( ITEM_WORK_STATUS_NAME );
	}

	public void setWork_status_name( String work_status_name1 )
	{
		setValue( ITEM_WORK_STATUS_NAME, work_status_name1 );
	}

	/* 待办任务内容 : String */
	public String getWork_status_content()
	{
		return getValue( ITEM_WORK_STATUS_CONTENT );
	}

	public void setWork_status_content( String work_status_content1 )
	{
		setValue( ITEM_WORK_STATUS_CONTENT, work_status_content1 );
	}

	/* 发起人ID : String */
	public String getInitiator_id()
	{
		return getValue( ITEM_INITIATOR_ID );
	}

	public void setInitiator_id( String initiator_id1 )
	{
		setValue( ITEM_INITIATOR_ID, initiator_id1 );
	}

	/* 发起人名称 : String */
	public String getInitiator_name()
	{
		return getValue( ITEM_INITIATOR_NAME );
	}

	public void setInitiator_name( String initiator_name1 )
	{
		setValue( ITEM_INITIATOR_NAME, initiator_name1 );
	}

	/* 发起人机构ID : String */
	public String getInitiator_agen_id()
	{
		return getValue( ITEM_INITIATOR_AGEN_ID );
	}

	public void setInitiator_agen_id( String initiator_agen_id1 )
	{
		setValue( ITEM_INITIATOR_AGEN_ID, initiator_agen_id1 );
	}

	/* 发起人机构名称 : String */
	public String getInitiator_agen_name()
	{
		return getValue( ITEM_INITIATOR_AGEN_NAME );
	}

	public void setInitiator_agen_name( String initiator_agen_name1 )
	{
		setValue( ITEM_INITIATOR_AGEN_NAME, initiator_agen_name1 );
	}

	/* 发起日期 : String */
	public String getInitiator_date()
	{
		return getValue( ITEM_INITIATOR_DATE );
	}

	public void setInitiator_date( String initiator_date1 )
	{
		setValue( ITEM_INITIATOR_DATE, initiator_date1 );
	}

	/* 发起时间 : String */
	public String getInitiator_time()
	{
		return getValue( ITEM_INITIATOR_TIME );
	}

	public void setInitiator_time( String initiator_time1 )
	{
		setValue( ITEM_INITIATOR_TIME, initiator_time1 );
	}

	/* 链接地址 : String */
	public String getLink_url()
	{
		return getValue( ITEM_LINK_URL );
	}

	public void setLink_url( String link_url1 )
	{
		setValue( ITEM_LINK_URL, link_url1 );
	}

	/* 交易代码 : String */
	public String getTxncode()
	{
		return getValue( ITEM_TXNCODE );
	}

	public void setTxncode( String txncode1 )
	{
		setValue( ITEM_TXNCODE, txncode1 );
	}

	/* 是否有效 : String */
	public String getIsvalid()
	{
		return getValue( ITEM_ISVALID );
	}

	public void setIsvalid( String isvalid1 )
	{
		setValue( ITEM_ISVALID, isvalid1 );
	}

	/* 源表名 : String */
	public String getScour_table_name()
	{
		return getValue( ITEM_SCOUR_TABLE_NAME );
	}

	public void setScour_table_name( String scour_table_name1 )
	{
		setValue( ITEM_SCOUR_TABLE_NAME, scour_table_name1 );
	}

	/* 源表主键ID : String */
	public String getScour_table_key_col()
	{
		return getValue( ITEM_SCOUR_TABLE_KEY_COL );
	}

	public void setScour_table_key_col( String scour_table_key_col1 )
	{
		setValue( ITEM_SCOUR_TABLE_KEY_COL, scour_table_key_col1 );
	}

	/* 预留1 : String */
	public String getBak_col_1()
	{
		return getValue( ITEM_BAK_COL_1 );
	}

	public void setBak_col_1( String bak_col_11 )
	{
		setValue( ITEM_BAK_COL_1, bak_col_11 );
	}

	/* 预留2 : String */
	public String getBak_col_2()
	{
		return getValue( ITEM_BAK_COL_2 );
	}

	public void setBak_col_2( String bak_col_21 )
	{
		setValue( ITEM_BAK_COL_2, bak_col_21 );
	}

	/* 预留3 : String */
	public String getBak_col_3()
	{
		return getValue( ITEM_BAK_COL_3 );
	}

	public void setBak_col_3( String bak_col_31 )
	{
		setValue( ITEM_BAK_COL_3, bak_col_31 );
	}

	/* 预留4 : String */
	public String getBak_col_4()
	{
		return getValue( ITEM_BAK_COL_4 );
	}

	public void setBak_col_4( String bak_col_41 )
	{
		setValue( ITEM_BAK_COL_4, bak_col_41 );
	}

	/* 预留5 : String */
	public String getBak_col_5()
	{
		return getValue( ITEM_BAK_COL_5 );
	}

	public void setBak_col_5( String bak_col_51 )
	{
		setValue( ITEM_BAK_COL_5, bak_col_51 );
	}

}

