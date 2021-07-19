package cn.gwssi.dw.aic.bj.ecomm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[eb_ent_check_task]的数据对象类
 * @author Administrator
 *
 */
public class VoEbEntCheckTask extends VoBase
{
	private static final long serialVersionUID = 201209241710040018L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_CHR_ID = "chr_id" ;				/* 检查任务ID */
	public static final String ITEM_CHECK_PERSION_ID = "check_persion_id" ;	/* 检查责任人 */
	public static final String ITEM_CHECK_PERSION_NAME = "check_persion_name" ;	/* 检查责任人名称 */
	public static final String ITEM_BEGIN_CHECK_DATE = "begin_check_date" ;	/* 检查任务开始时间 */
	public static final String ITEM_END_CHECK_DATE = "end_check_date" ;	/* 检查任务结束时间 */
	public static final String ITEM_CHECK_TYPE = "check_type" ;		/* 检查类别(1周期;2其他) */
	public static final String ITEM_TASK_STATE = "task_state" ;		/* 任务状态(0 */
	public static final String ITEM_CHECK_RESULT = "check_result" ;	/* 检查结果(0无问题;1有问题) */
	public static final String ITEM_CHECK_COMPLETE_DATE = "check_complete_date" ;	/* 检查完成时间 */
	public static final String ITEM_ENT_WEB_SITE_ID = "ent_web_site_id" ;	/* 主体网站ID */
	public static final String ITEM_DATEXC = "datexc" ;				/* 数据交换位 */
	public static final String ITEM_LAST_DATE = "last_date" ;		/* 时间戳 */
	public static final String ITEM_JUDGE_SIGN = "judge_sign" ;		/* 判定标识（3无效链接） */
	
	/**
	 * 构造函数
	 */
	public VoEbEntCheckTask()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoEbEntCheckTask(DataBus value)
	{
		super(value);
	}
	
	/* 检查任务ID : String */
	public String getChr_id()
	{
		return getValue( ITEM_CHR_ID );
	}

	public void setChr_id( String chr_id1 )
	{
		setValue( ITEM_CHR_ID, chr_id1 );
	}

	/* 检查责任人 : String */
	public String getCheck_persion_id()
	{
		return getValue( ITEM_CHECK_PERSION_ID );
	}

	public void setCheck_persion_id( String check_persion_id1 )
	{
		setValue( ITEM_CHECK_PERSION_ID, check_persion_id1 );
	}

	/* 检查责任人名称 : String */
	public String getCheck_persion_name()
	{
		return getValue( ITEM_CHECK_PERSION_NAME );
	}

	public void setCheck_persion_name( String check_persion_name1 )
	{
		setValue( ITEM_CHECK_PERSION_NAME, check_persion_name1 );
	}

	/* 检查任务开始时间 : String */
	public String getBegin_check_date()
	{
		return getValue( ITEM_BEGIN_CHECK_DATE );
	}

	public void setBegin_check_date( String begin_check_date1 )
	{
		setValue( ITEM_BEGIN_CHECK_DATE, begin_check_date1 );
	}

	/* 检查任务结束时间 : String */
	public String getEnd_check_date()
	{
		return getValue( ITEM_END_CHECK_DATE );
	}

	public void setEnd_check_date( String end_check_date1 )
	{
		setValue( ITEM_END_CHECK_DATE, end_check_date1 );
	}

	/* 检查类别(1周期;2其他) : String */
	public String getCheck_type()
	{
		return getValue( ITEM_CHECK_TYPE );
	}

	public void setCheck_type( String check_type1 )
	{
		setValue( ITEM_CHECK_TYPE, check_type1 );
	}

	/* 任务状态(0 : String */
	public String getTask_state()
	{
		return getValue( ITEM_TASK_STATE );
	}

	public void setTask_state( String task_state1 )
	{
		setValue( ITEM_TASK_STATE, task_state1 );
	}

	/* 检查结果(0无问题;1有问题) : String */
	public String getCheck_result()
	{
		return getValue( ITEM_CHECK_RESULT );
	}

	public void setCheck_result( String check_result1 )
	{
		setValue( ITEM_CHECK_RESULT, check_result1 );
	}

	/* 检查完成时间 : String */
	public String getCheck_complete_date()
	{
		return getValue( ITEM_CHECK_COMPLETE_DATE );
	}

	public void setCheck_complete_date( String check_complete_date1 )
	{
		setValue( ITEM_CHECK_COMPLETE_DATE, check_complete_date1 );
	}

	/* 主体网站ID : String */
	public String getEnt_web_site_id()
	{
		return getValue( ITEM_ENT_WEB_SITE_ID );
	}

	public void setEnt_web_site_id( String ent_web_site_id1 )
	{
		setValue( ITEM_ENT_WEB_SITE_ID, ent_web_site_id1 );
	}

	/* 数据交换位 : String */
	public String getDatexc()
	{
		return getValue( ITEM_DATEXC );
	}

	public void setDatexc( String datexc1 )
	{
		setValue( ITEM_DATEXC, datexc1 );
	}

	/* 时间戳 : String */
	public String getLast_date()
	{
		return getValue( ITEM_LAST_DATE );
	}

	public void setLast_date( String last_date1 )
	{
		setValue( ITEM_LAST_DATE, last_date1 );
	}

	/* 判定标识（3无效链接） : String */
	public String getJudge_sign()
	{
		return getValue( ITEM_JUDGE_SIGN );
	}

	public void setJudge_sign( String judge_sign1 )
	{
		setValue( ITEM_JUDGE_SIGN, judge_sign1 );
	}

}

