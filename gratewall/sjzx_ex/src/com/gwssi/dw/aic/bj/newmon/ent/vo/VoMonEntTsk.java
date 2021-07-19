package com.gwssi.dw.aic.bj.newmon.ent.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[mon_ent_tsk]的数据对象类
 * @author Administrator
 *
 */
public class VoMonEntTsk extends VoBase
{
	private static final long serialVersionUID = 200811191653120002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_MON_ENT_TSK_ID = "mon_ent_tsk_id" ;	/* 主体检查任务表id */
	public static final String ITEM_MON_MIS_ID = "mon_mis_id" ;		/* 检查任务id */
	public static final String ITEM_RES_CHE = "res_che" ;			/* 检查责任人 */
	public static final String ITEM_CHE_MIS_STA_TIME = "che_mis_sta_time" ;	/* 检查任务开始时间 */
	public static final String ITEM_CHE_MIS_FIN_TIME = "che_mis_fin_time" ;	/* 检查任务结束时间 */
	public static final String ITEM_CHE_TYPE = "che_type" ;			/* 检查类别 */
	public static final String ITEM_CHE_STATE = "che_state" ;		/* 任务状态(dm) */
	public static final String ITEM_CHE_RES = "che_res" ;			/* 检查结果(dm) */
	public static final String ITEM_CHE_COM_TIME = "che_com_time" ;	/* 检查完成时间 */
	public static final String ITEM_DEP_NO = "dep_no" ;				/* 部门代码(dm) */
	public static final String ITEM_DATA_FLAG = "data_flag" ;		/* 数据标志 */
	public static final String ITEM_GRID_ID = "grid_id" ;			/* 网格ID */
	public static final String ITEM_MAIN_ID = "main_id" ;			/* 主体ID */
	
	/**
	 * 构造函数
	 */
	public VoMonEntTsk()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoMonEntTsk(DataBus value)
	{
		super(value);
	}
	
	/* 主体检查任务表id : String */
	public String getMon_ent_tsk_id()
	{
		return getValue( ITEM_MON_ENT_TSK_ID );
	}

	public void setMon_ent_tsk_id( String mon_ent_tsk_id1 )
	{
		setValue( ITEM_MON_ENT_TSK_ID, mon_ent_tsk_id1 );
	}

	/* 检查任务id : String */
	public String getMon_mis_id()
	{
		return getValue( ITEM_MON_MIS_ID );
	}

	public void setMon_mis_id( String mon_mis_id1 )
	{
		setValue( ITEM_MON_MIS_ID, mon_mis_id1 );
	}

	/* 检查责任人 : String */
	public String getRes_che()
	{
		return getValue( ITEM_RES_CHE );
	}

	public void setRes_che( String res_che1 )
	{
		setValue( ITEM_RES_CHE, res_che1 );
	}

	/* 检查任务开始时间 : String */
	public String getChe_mis_sta_time()
	{
		return getValue( ITEM_CHE_MIS_STA_TIME );
	}

	public void setChe_mis_sta_time( String che_mis_sta_time1 )
	{
		setValue( ITEM_CHE_MIS_STA_TIME, che_mis_sta_time1 );
	}

	/* 检查任务结束时间 : String */
	public String getChe_mis_fin_time()
	{
		return getValue( ITEM_CHE_MIS_FIN_TIME );
	}

	public void setChe_mis_fin_time( String che_mis_fin_time1 )
	{
		setValue( ITEM_CHE_MIS_FIN_TIME, che_mis_fin_time1 );
	}

	/* 检查类别 : String */
	public String getChe_type()
	{
		return getValue( ITEM_CHE_TYPE );
	}

	public void setChe_type( String che_type1 )
	{
		setValue( ITEM_CHE_TYPE, che_type1 );
	}

	/* 任务状态(dm) : String */
	public String getChe_state()
	{
		return getValue( ITEM_CHE_STATE );
	}

	public void setChe_state( String che_state1 )
	{
		setValue( ITEM_CHE_STATE, che_state1 );
	}

	/* 检查结果(dm) : String */
	public String getChe_res()
	{
		return getValue( ITEM_CHE_RES );
	}

	public void setChe_res( String che_res1 )
	{
		setValue( ITEM_CHE_RES, che_res1 );
	}

	/* 检查完成时间 : String */
	public String getChe_com_time()
	{
		return getValue( ITEM_CHE_COM_TIME );
	}

	public void setChe_com_time( String che_com_time1 )
	{
		setValue( ITEM_CHE_COM_TIME, che_com_time1 );
	}

	/* 部门代码(dm) : String */
	public String getDep_no()
	{
		return getValue( ITEM_DEP_NO );
	}

	public void setDep_no( String dep_no1 )
	{
		setValue( ITEM_DEP_NO, dep_no1 );
	}

	/* 数据标志 : String */
	public String getData_flag()
	{
		return getValue( ITEM_DATA_FLAG );
	}

	public void setData_flag( String data_flag1 )
	{
		setValue( ITEM_DATA_FLAG, data_flag1 );
	}

	/* 网格ID : String */
	public String getGrid_id()
	{
		return getValue( ITEM_GRID_ID );
	}

	public void setGrid_id( String grid_id1 )
	{
		setValue( ITEM_GRID_ID, grid_id1 );
	}

	/* 主体ID : String */
	public String getMain_id()
	{
		return getValue( ITEM_MAIN_ID );
	}

	public void setMain_id( String main_id1 )
	{
		setValue( ITEM_MAIN_ID, main_id1 );
	}

}

