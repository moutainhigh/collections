package com.gwssi.dw.aic.bj.newmon.buss.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoMonBussQus extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * 变量列表
	 */
	public static final String ITEM_MON_BUSS_QUS_ID = "mon_buss_qus_id";			/* 问题处理表ID */
	public static final String ITEM_QUS_ID = "qus_id";			/* 问题id */
	public static final String ITEM_ITEM_ID = "item_id";			/* 项目ID */
	public static final String ITEM_ITEM_NAME = "item_name";			/* 项目名称 */
	public static final String ITEM_QUS_NO = "qus_no";			/* 问题编号 */
	public static final String ITEM_SET_INS = "set_ins";			/* 处理状态(dm) */
	public static final String ITEM_QUS_TO_DATE = "qus_to_date";			/* 责改到期日期 */
	public static final String ITEM_QUS_DESC = "qus_desc";			/* 问题描述 */
	public static final String ITEM_QUS_CRT_DATE = "qus_crt_date";			/* 问题生成日期 */
	public static final String ITEM_QUS_ACC_DATE = "qus_acc_date";			/* 问题处理日期 */
	public static final String ITEM_DEP_ID = "dep_id";			/* 部门代码 */
	public static final String ITEM_QUS_RET_RES = "qus_ret_res";			/* 反馈结果ID */
	public static final String ITEM_RES_PERSN_ID = "res_persn_id";			/* 检查负责人ID */
	public static final String ITEM_UPPER_USER_ID = "upper_user_id";			/* 上级用户ID */
	public static final String ITEM_FEED_BACK_VALUE = "feed_back_value";			/* 反馈值 */

	public VoMonBussQus(DataBus value)
	{
		super(value);
	}

	public VoMonBussQus()
	{
		super();
	}

	/* 问题处理表ID */
	public String getMon_buss_qus_id()
	{
		return getValue( ITEM_MON_BUSS_QUS_ID );
	}

	public void setMon_buss_qus_id( String mon_buss_qus_id1 )
	{
		setValue( ITEM_MON_BUSS_QUS_ID, mon_buss_qus_id1 );
	}

	/* 问题id */
	public String getQus_id()
	{
		return getValue( ITEM_QUS_ID );
	}

	public void setQus_id( String qus_id1 )
	{
		setValue( ITEM_QUS_ID, qus_id1 );
	}

	/* 项目ID */
	public String getItem_id()
	{
		return getValue( ITEM_ITEM_ID );
	}

	public void setItem_id( String item_id1 )
	{
		setValue( ITEM_ITEM_ID, item_id1 );
	}

	/* 项目名称 */
	public String getItem_name()
	{
		return getValue( ITEM_ITEM_NAME );
	}

	public void setItem_name( String item_name1 )
	{
		setValue( ITEM_ITEM_NAME, item_name1 );
	}

	/* 问题编号 */
	public String getQus_no()
	{
		return getValue( ITEM_QUS_NO );
	}

	public void setQus_no( String qus_no1 )
	{
		setValue( ITEM_QUS_NO, qus_no1 );
	}

	/* 处理状态(dm) */
	public String getSet_ins()
	{
		return getValue( ITEM_SET_INS );
	}

	public void setSet_ins( String set_ins1 )
	{
		setValue( ITEM_SET_INS, set_ins1 );
	}

	/* 责改到期日期 */
	public String getQus_to_date()
	{
		return getValue( ITEM_QUS_TO_DATE );
	}

	public void setQus_to_date( String qus_to_date1 )
	{
		setValue( ITEM_QUS_TO_DATE, qus_to_date1 );
	}

	/* 问题描述 */
	public String getQus_desc()
	{
		return getValue( ITEM_QUS_DESC );
	}

	public void setQus_desc( String qus_desc1 )
	{
		setValue( ITEM_QUS_DESC, qus_desc1 );
	}

	/* 问题生成日期 */
	public String getQus_crt_date()
	{
		return getValue( ITEM_QUS_CRT_DATE );
	}

	public void setQus_crt_date( String qus_crt_date1 )
	{
		setValue( ITEM_QUS_CRT_DATE, qus_crt_date1 );
	}

	/* 问题处理日期 */
	public String getQus_acc_date()
	{
		return getValue( ITEM_QUS_ACC_DATE );
	}

	public void setQus_acc_date( String qus_acc_date1 )
	{
		setValue( ITEM_QUS_ACC_DATE, qus_acc_date1 );
	}

	/* 部门代码 */
	public String getDep_id()
	{
		return getValue( ITEM_DEP_ID );
	}

	public void setDep_id( String dep_id1 )
	{
		setValue( ITEM_DEP_ID, dep_id1 );
	}

	/* 反馈结果ID */
	public String getQus_ret_res()
	{
		return getValue( ITEM_QUS_RET_RES );
	}

	public void setQus_ret_res( String qus_ret_res1 )
	{
		setValue( ITEM_QUS_RET_RES, qus_ret_res1 );
	}

	/* 检查负责人ID */
	public String getRes_persn_id()
	{
		return getValue( ITEM_RES_PERSN_ID );
	}

	public void setRes_persn_id( String res_persn_id1 )
	{
		setValue( ITEM_RES_PERSN_ID, res_persn_id1 );
	}

	/* 上级用户ID */
	public String getUpper_user_id()
	{
		return getValue( ITEM_UPPER_USER_ID );
	}

	public void setUpper_user_id( String upper_user_id1 )
	{
		setValue( ITEM_UPPER_USER_ID, upper_user_id1 );
	}

	/* 反馈值 */
	public String getFeed_back_value()
	{
		return getValue( ITEM_FEED_BACK_VALUE );
	}

	public void setFeed_back_value( String feed_back_value1 )
	{
		setValue( ITEM_FEED_BACK_VALUE, feed_back_value1 );
	}

}

