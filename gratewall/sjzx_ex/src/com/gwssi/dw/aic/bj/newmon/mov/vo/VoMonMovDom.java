package com.gwssi.dw.aic.bj.newmon.mov.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[mon_mov_dom]的数据对象类
 * @author Administrator
 *
 */
public class VoMonMovDom extends VoBase
{
	private static final long serialVersionUID = 200811201508540002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_MON_MOV_DOM_ID = "mon_mov_dom_id" ;	/* 擅迁住所表ID */
	public static final String ITEM_RES_CHE = "res_che" ;			/* 检查责任人 */
	public static final String ITEM_REG_DATE = "reg_date" ;			/* 登记时间 */
	public static final String ITEM_NOW_OP_LOC = "now_op_loc" ;		/* 现经营地址 */
	public static final String ITEM_MOV_DATE = "mov_date" ;			/* 迁入/出时间 */
	public static final String ITEM_PRM_SET_DATE = "prm_set_date" ;	/* 处理时间 */
	public static final String ITEM_PRM_SET_FORM = "prm_set_form" ;	/* 处理方式(dm) */
	public static final String ITEM_REG_DOM_GRID = "reg_dom_grid" ;	/* 注册地网格 */
	public static final String ITEM_DEP_NO = "dep_no" ;				/* 部门代码(dm) */
	public static final String ITEM_CRT_TSK_ID = "crt_tsk_id" ;		/* 生成任务ID */
	public static final String ITEM_PRM_QUS_TYPE = "prm_qus_type" ;	/* 问题类型(dm) */
	public static final String ITEM_REINSP_RES = "reinsp_res" ;		/* 回查结果(dm) */
	public static final String ITEM_PRM_MOV_DOM_ID = "prm_mov_dom_id" ;	/* 擅迁住所id */
	public static final String ITEM_GRID_ID = "grid_id" ;			/* 网格ID */
	public static final String ITEM_ASS_DEP = "ass_dep" ;			/* 指派部门 */
	public static final String ITEM_REG_NO = "reg_no" ;				/* 注册号 */
	public static final String ITEM_QRSJ = "qrsj" ;					/* 迁入/出时间 */
	public static final String ITEM_CLFS = "clfs" ;					/* 处理方式 */
	public static final String ITEM_WTLX = "wtlx" ;					/* 问题类型 */
	public static final String ITEM_REINSP_CASE = "reinsp_case" ;	/* 回查情况 */
	public static final String ITEM_IS_IN_BUILD = "is_in_build" ;	/* 是否写字楼内 */
	public static final String ITEM_MOV_BUILD_ID = "mov_build_id" ;	/* 写字楼id */
	public static final String ITEM_MOV_PROP_ID = "mov_prop_id" ;	/* 物业id */
	public static final String ITEM_MOV_CASE_ID = "mov_case_id" ;	/* 案件id */
	public static final String ITEM_GRID_RES_IS_MOD = "grid_res_is_mod" ;	/* 网格责任人是否修改过 */
	public static final String ITEM_CHE_TYPE = "che_type" ;			/* 检查类别 */
	public static final String ITEM_INPUT_MAN = "input_man" ;		/* 录入人员 */
	public static final String ITEM_LINK_TELE = "link_tele" ;		/* 联系电话 */
	public static final String ITEM_MAIN_ID = "main_id" ;			/* 主体id */
	
	/**
	 * 构造函数
	 */
	public VoMonMovDom()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoMonMovDom(DataBus value)
	{
		super(value);
	}
	
	/* 擅迁住所表ID : String */
	public String getMon_mov_dom_id()
	{
		return getValue( ITEM_MON_MOV_DOM_ID );
	}

	public void setMon_mov_dom_id( String mon_mov_dom_id1 )
	{
		setValue( ITEM_MON_MOV_DOM_ID, mon_mov_dom_id1 );
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

	/* 登记时间 : String */
	public String getReg_date()
	{
		return getValue( ITEM_REG_DATE );
	}

	public void setReg_date( String reg_date1 )
	{
		setValue( ITEM_REG_DATE, reg_date1 );
	}

	/* 现经营地址 : String */
	public String getNow_op_loc()
	{
		return getValue( ITEM_NOW_OP_LOC );
	}

	public void setNow_op_loc( String now_op_loc1 )
	{
		setValue( ITEM_NOW_OP_LOC, now_op_loc1 );
	}

	/* 迁入/出时间 : String */
	public String getMov_date()
	{
		return getValue( ITEM_MOV_DATE );
	}

	public void setMov_date( String mov_date1 )
	{
		setValue( ITEM_MOV_DATE, mov_date1 );
	}

	/* 处理时间 : String */
	public String getPrm_set_date()
	{
		return getValue( ITEM_PRM_SET_DATE );
	}

	public void setPrm_set_date( String prm_set_date1 )
	{
		setValue( ITEM_PRM_SET_DATE, prm_set_date1 );
	}

	/* 处理方式(dm) : String */
	public String getPrm_set_form()
	{
		return getValue( ITEM_PRM_SET_FORM );
	}

	public void setPrm_set_form( String prm_set_form1 )
	{
		setValue( ITEM_PRM_SET_FORM, prm_set_form1 );
	}

	/* 注册地网格 : String */
	public String getReg_dom_grid()
	{
		return getValue( ITEM_REG_DOM_GRID );
	}

	public void setReg_dom_grid( String reg_dom_grid1 )
	{
		setValue( ITEM_REG_DOM_GRID, reg_dom_grid1 );
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

	/* 生成任务ID : String */
	public String getCrt_tsk_id()
	{
		return getValue( ITEM_CRT_TSK_ID );
	}

	public void setCrt_tsk_id( String crt_tsk_id1 )
	{
		setValue( ITEM_CRT_TSK_ID, crt_tsk_id1 );
	}

	/* 问题类型(dm) : String */
	public String getPrm_qus_type()
	{
		return getValue( ITEM_PRM_QUS_TYPE );
	}

	public void setPrm_qus_type( String prm_qus_type1 )
	{
		setValue( ITEM_PRM_QUS_TYPE, prm_qus_type1 );
	}

	/* 回查结果(dm) : String */
	public String getReinsp_res()
	{
		return getValue( ITEM_REINSP_RES );
	}

	public void setReinsp_res( String reinsp_res1 )
	{
		setValue( ITEM_REINSP_RES, reinsp_res1 );
	}

	/* 擅迁住所id : String */
	public String getPrm_mov_dom_id()
	{
		return getValue( ITEM_PRM_MOV_DOM_ID );
	}

	public void setPrm_mov_dom_id( String prm_mov_dom_id1 )
	{
		setValue( ITEM_PRM_MOV_DOM_ID, prm_mov_dom_id1 );
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

	/* 指派部门 : String */
	public String getAss_dep()
	{
		return getValue( ITEM_ASS_DEP );
	}

	public void setAss_dep( String ass_dep1 )
	{
		setValue( ITEM_ASS_DEP, ass_dep1 );
	}

	/* 注册号 : String */
	public String getReg_no()
	{
		return getValue( ITEM_REG_NO );
	}

	public void setReg_no( String reg_no1 )
	{
		setValue( ITEM_REG_NO, reg_no1 );
	}

	/* 迁入/出时间 : String */
	public String getQrsj()
	{
		return getValue( ITEM_QRSJ );
	}

	public void setQrsj( String qrsj1 )
	{
		setValue( ITEM_QRSJ, qrsj1 );
	}

	/* 处理方式 : String */
	public String getClfs()
	{
		return getValue( ITEM_CLFS );
	}

	public void setClfs( String clfs1 )
	{
		setValue( ITEM_CLFS, clfs1 );
	}

	/* 问题类型 : String */
	public String getWtlx()
	{
		return getValue( ITEM_WTLX );
	}

	public void setWtlx( String wtlx1 )
	{
		setValue( ITEM_WTLX, wtlx1 );
	}

	/* 回查情况 : String */
	public String getReinsp_case()
	{
		return getValue( ITEM_REINSP_CASE );
	}

	public void setReinsp_case( String reinsp_case1 )
	{
		setValue( ITEM_REINSP_CASE, reinsp_case1 );
	}

	/* 是否写字楼内 : String */
	public String getIs_in_build()
	{
		return getValue( ITEM_IS_IN_BUILD );
	}

	public void setIs_in_build( String is_in_build1 )
	{
		setValue( ITEM_IS_IN_BUILD, is_in_build1 );
	}

	/* 写字楼id : String */
	public String getMov_build_id()
	{
		return getValue( ITEM_MOV_BUILD_ID );
	}

	public void setMov_build_id( String mov_build_id1 )
	{
		setValue( ITEM_MOV_BUILD_ID, mov_build_id1 );
	}

	/* 物业id : String */
	public String getMov_prop_id()
	{
		return getValue( ITEM_MOV_PROP_ID );
	}

	public void setMov_prop_id( String mov_prop_id1 )
	{
		setValue( ITEM_MOV_PROP_ID, mov_prop_id1 );
	}

	/* 案件id : String */
	public String getMov_case_id()
	{
		return getValue( ITEM_MOV_CASE_ID );
	}

	public void setMov_case_id( String mov_case_id1 )
	{
		setValue( ITEM_MOV_CASE_ID, mov_case_id1 );
	}

	/* 网格责任人是否修改过 : String */
	public String getGrid_res_is_mod()
	{
		return getValue( ITEM_GRID_RES_IS_MOD );
	}

	public void setGrid_res_is_mod( String grid_res_is_mod1 )
	{
		setValue( ITEM_GRID_RES_IS_MOD, grid_res_is_mod1 );
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

	/* 录入人员 : String */
	public String getInput_man()
	{
		return getValue( ITEM_INPUT_MAN );
	}

	public void setInput_man( String input_man1 )
	{
		setValue( ITEM_INPUT_MAN, input_man1 );
	}

	/* 联系电话 : String */
	public String getLink_tele()
	{
		return getValue( ITEM_LINK_TELE );
	}

	public void setLink_tele( String link_tele1 )
	{
		setValue( ITEM_LINK_TELE, link_tele1 );
	}

	/* 主体id : String */
	public String getMain_id()
	{
		return getValue( ITEM_MAIN_ID );
	}

	public void setMain_id( String main_id1 )
	{
		setValue( ITEM_MAIN_ID, main_id1 );
	}

}

