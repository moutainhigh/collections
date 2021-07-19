package com.gwssi.dw.aic.bj.xb.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[xb_report_base]的数据对象类
 * @author Administrator
 *
 */
public class VoXbReportBase extends VoBase
{
	private static final long serialVersionUID = 200809170932520002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_XB_REPORT_BASE_ID = "xb_report_base_id" ;	/* 举报基本信息ID */
	public static final String ITEM_REGI_NO = "regi_no" ;			/* 受理编号 */
	public static final String ITEM_ACC_REG_PER = "acc_reg_per" ;	/* 登记人 */
	public static final String ITEM_REG_DEP = "reg_dep" ;			/* 登记单位 */
	public static final String ITEM_RPT_REG_DATE = "rpt_reg_date" ;	/* 登记日期 */
	public static final String ITEM_NAME = "name" ;					/* 申诉方姓名 */
	public static final String ITEM_SEX = "sex" ;					/* 性别 */
	public static final String ITEM_PER_IDE = "per_ide" ;			/* 申诉方分类1（城镇...） */
	public static final String ITEM_RPT_PER_LINK = "rpt_per_link" ;	/* 举报方联系方式 */
	public static final String ITEM_INFO_ORI = "info_ori" ;			/* 申诉信息来源 */
	public static final String ITEM_RPT_REQ_CLO_DATE = "rpt_req_clo_date" ;	/* 要求反馈时间 */
	public static final String ITEM_ENT_NAME = "ent_name" ;			/* 被诉方名称 */
	public static final String ITEM_REG_NO = "reg_no" ;				/* 被诉方注册号 */
	public static final String ITEM_RPT_ADDR = "rpt_addr" ;			/* 注册地址 */
	public static final String ITEM_RPT_BUSI_ADDR = "rpt_busi_addr" ;	/* 经营地址 */
	public static final String ITEM_MDSE_NAME = "mdse_name" ;		/* 商品/服务名称 */
	public static final String ITEM_INV_OB_TYPE_LARGE = "inv_ob_type_large" ;	/* 申诉标的大类 */
	public static final String ITEM_INV_OB_TYPE_MID = "inv_ob_type_mid" ;	/* 申诉标的中类 */
	public static final String ITEM_INV_OB_TYPE_SMALL = "inv_ob_type_small" ;	/* 申诉标的小类 */
	public static final String ITEM_INV_OB_TYPE_MOST_SMALL = "inv_ob_type_most_small" ;	/* 申诉标的小小类 */
	public static final String ITEM_RPT_PER_ACCQ = "rpt_per_accq" ;	/* 举报方要求 */
	public static final String ITEM_RPT_INVOVLED_QUE = "rpt_invovled_que" ;	/* 涉及问题 */
	public static final String ITEM_BRI_ST = "bri_st" ;				/* 问题陈述 */
	public static final String ITEM_RPT_REC_DEPT2 = "rpt_rec_dept2" ;	/* 接收单位二级 */
	public static final String ITEM_ACCI_LEV = "acci_lev" ;			/* 级别 */
	public static final String ITEM_ACCI_LEV_DETAIL = "acci_lev_detail" ;	/* 级别细分 */
	public static final String ITEM_ETL_ID = "etl_id" ;				/* ETL序列号 */
	public static final String ITEM_ETL_FLAG = "etl_flag" ;			/* ETL数据状态 */
	public static final String ITEM_ETL_TIMESTAMP = "etl_timestamp" ;	/* ETL时间戳 */
	
	/**
	 * 构造函数
	 */
	public VoXbReportBase()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoXbReportBase(DataBus value)
	{
		super(value);
	}
	
	/* 举报基本信息ID : String */
	public String getXb_report_base_id()
	{
		return getValue( ITEM_XB_REPORT_BASE_ID );
	}

	public void setXb_report_base_id( String xb_report_base_id1 )
	{
		setValue( ITEM_XB_REPORT_BASE_ID, xb_report_base_id1 );
	}

	/* 受理编号 : String */
	public String getRegi_no()
	{
		return getValue( ITEM_REGI_NO );
	}

	public void setRegi_no( String regi_no1 )
	{
		setValue( ITEM_REGI_NO, regi_no1 );
	}

	/* 登记人 : String */
	public String getAcc_reg_per()
	{
		return getValue( ITEM_ACC_REG_PER );
	}

	public void setAcc_reg_per( String acc_reg_per1 )
	{
		setValue( ITEM_ACC_REG_PER, acc_reg_per1 );
	}

	/* 登记单位 : String */
	public String getReg_dep()
	{
		return getValue( ITEM_REG_DEP );
	}

	public void setReg_dep( String reg_dep1 )
	{
		setValue( ITEM_REG_DEP, reg_dep1 );
	}

	/* 登记日期 : String */
	public String getRpt_reg_date()
	{
		return getValue( ITEM_RPT_REG_DATE );
	}

	public void setRpt_reg_date( String rpt_reg_date1 )
	{
		setValue( ITEM_RPT_REG_DATE, rpt_reg_date1 );
	}

	/* 申诉方姓名 : String */
	public String getName()
	{
		return getValue( ITEM_NAME );
	}

	public void setName( String name1 )
	{
		setValue( ITEM_NAME, name1 );
	}

	/* 性别 : String */
	public String getSex()
	{
		return getValue( ITEM_SEX );
	}

	public void setSex( String sex1 )
	{
		setValue( ITEM_SEX, sex1 );
	}

	/* 申诉方分类1（城镇...） : String */
	public String getPer_ide()
	{
		return getValue( ITEM_PER_IDE );
	}

	public void setPer_ide( String per_ide1 )
	{
		setValue( ITEM_PER_IDE, per_ide1 );
	}

	/* 举报方联系方式 : String */
	public String getRpt_per_link()
	{
		return getValue( ITEM_RPT_PER_LINK );
	}

	public void setRpt_per_link( String rpt_per_link1 )
	{
		setValue( ITEM_RPT_PER_LINK, rpt_per_link1 );
	}

	/* 申诉信息来源 : String */
	public String getInfo_ori()
	{
		return getValue( ITEM_INFO_ORI );
	}

	public void setInfo_ori( String info_ori1 )
	{
		setValue( ITEM_INFO_ORI, info_ori1 );
	}

	/* 要求反馈时间 : String */
	public String getRpt_req_clo_date()
	{
		return getValue( ITEM_RPT_REQ_CLO_DATE );
	}

	public void setRpt_req_clo_date( String rpt_req_clo_date1 )
	{
		setValue( ITEM_RPT_REQ_CLO_DATE, rpt_req_clo_date1 );
	}

	/* 被诉方名称 : String */
	public String getEnt_name()
	{
		return getValue( ITEM_ENT_NAME );
	}

	public void setEnt_name( String ent_name1 )
	{
		setValue( ITEM_ENT_NAME, ent_name1 );
	}

	/* 被诉方注册号 : String */
	public String getReg_no()
	{
		return getValue( ITEM_REG_NO );
	}

	public void setReg_no( String reg_no1 )
	{
		setValue( ITEM_REG_NO, reg_no1 );
	}

	/* 注册地址 : String */
	public String getRpt_addr()
	{
		return getValue( ITEM_RPT_ADDR );
	}

	public void setRpt_addr( String rpt_addr1 )
	{
		setValue( ITEM_RPT_ADDR, rpt_addr1 );
	}

	/* 经营地址 : String */
	public String getRpt_busi_addr()
	{
		return getValue( ITEM_RPT_BUSI_ADDR );
	}

	public void setRpt_busi_addr( String rpt_busi_addr1 )
	{
		setValue( ITEM_RPT_BUSI_ADDR, rpt_busi_addr1 );
	}

	/* 商品/服务名称 : String */
	public String getMdse_name()
	{
		return getValue( ITEM_MDSE_NAME );
	}

	public void setMdse_name( String mdse_name1 )
	{
		setValue( ITEM_MDSE_NAME, mdse_name1 );
	}

	/* 申诉标的大类 : String */
	public String getInv_ob_type_large()
	{
		return getValue( ITEM_INV_OB_TYPE_LARGE );
	}

	public void setInv_ob_type_large( String inv_ob_type_large1 )
	{
		setValue( ITEM_INV_OB_TYPE_LARGE, inv_ob_type_large1 );
	}

	/* 申诉标的中类 : String */
	public String getInv_ob_type_mid()
	{
		return getValue( ITEM_INV_OB_TYPE_MID );
	}

	public void setInv_ob_type_mid( String inv_ob_type_mid1 )
	{
		setValue( ITEM_INV_OB_TYPE_MID, inv_ob_type_mid1 );
	}

	/* 申诉标的小类 : String */
	public String getInv_ob_type_small()
	{
		return getValue( ITEM_INV_OB_TYPE_SMALL );
	}

	public void setInv_ob_type_small( String inv_ob_type_small1 )
	{
		setValue( ITEM_INV_OB_TYPE_SMALL, inv_ob_type_small1 );
	}

	/* 申诉标的小小类 : String */
	public String getInv_ob_type_most_small()
	{
		return getValue( ITEM_INV_OB_TYPE_MOST_SMALL );
	}

	public void setInv_ob_type_most_small( String inv_ob_type_most_small1 )
	{
		setValue( ITEM_INV_OB_TYPE_MOST_SMALL, inv_ob_type_most_small1 );
	}

	/* 举报方要求 : String */
	public String getRpt_per_accq()
	{
		return getValue( ITEM_RPT_PER_ACCQ );
	}

	public void setRpt_per_accq( String rpt_per_accq1 )
	{
		setValue( ITEM_RPT_PER_ACCQ, rpt_per_accq1 );
	}

	/* 涉及问题 : String */
	public String getRpt_invovled_que()
	{
		return getValue( ITEM_RPT_INVOVLED_QUE );
	}

	public void setRpt_invovled_que( String rpt_invovled_que1 )
	{
		setValue( ITEM_RPT_INVOVLED_QUE, rpt_invovled_que1 );
	}

	/* 问题陈述 : String */
	public String getBri_st()
	{
		return getValue( ITEM_BRI_ST );
	}

	public void setBri_st( String bri_st1 )
	{
		setValue( ITEM_BRI_ST, bri_st1 );
	}

	/* 接收单位二级 : String */
	public String getRpt_rec_dept2()
	{
		return getValue( ITEM_RPT_REC_DEPT2 );
	}

	public void setRpt_rec_dept2( String rpt_rec_dept21 )
	{
		setValue( ITEM_RPT_REC_DEPT2, rpt_rec_dept21 );
	}

	/* 级别 : String */
	public String getAcci_lev()
	{
		return getValue( ITEM_ACCI_LEV );
	}

	public void setAcci_lev( String acci_lev1 )
	{
		setValue( ITEM_ACCI_LEV, acci_lev1 );
	}

	/* 级别细分 : String */
	public String getAcci_lev_detail()
	{
		return getValue( ITEM_ACCI_LEV_DETAIL );
	}

	public void setAcci_lev_detail( String acci_lev_detail1 )
	{
		setValue( ITEM_ACCI_LEV_DETAIL, acci_lev_detail1 );
	}

	/* ETL序列号 : String */
	public String getEtl_id()
	{
		return getValue( ITEM_ETL_ID );
	}

	public void setEtl_id( String etl_id1 )
	{
		setValue( ITEM_ETL_ID, etl_id1 );
	}

	/* ETL数据状态 : String */
	public String getEtl_flag()
	{
		return getValue( ITEM_ETL_FLAG );
	}

	public void setEtl_flag( String etl_flag1 )
	{
		setValue( ITEM_ETL_FLAG, etl_flag1 );
	}

	/* ETL时间戳 : String */
	public String getEtl_timestamp()
	{
		return getValue( ITEM_ETL_TIMESTAMP );
	}

	public void setEtl_timestamp( String etl_timestamp1 )
	{
		setValue( ITEM_ETL_TIMESTAMP, etl_timestamp1 );
	}

}

