package com.gwssi.dw.aic.bj.newmon.mov.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[mon_mov_dom]�����ݶ�����
 * @author Administrator
 *
 */
public class VoMonMovDom extends VoBase
{
	private static final long serialVersionUID = 200811201508540002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_MON_MOV_DOM_ID = "mon_mov_dom_id" ;	/* ��Ǩס����ID */
	public static final String ITEM_RES_CHE = "res_che" ;			/* ��������� */
	public static final String ITEM_REG_DATE = "reg_date" ;			/* �Ǽ�ʱ�� */
	public static final String ITEM_NOW_OP_LOC = "now_op_loc" ;		/* �־�Ӫ��ַ */
	public static final String ITEM_MOV_DATE = "mov_date" ;			/* Ǩ��/��ʱ�� */
	public static final String ITEM_PRM_SET_DATE = "prm_set_date" ;	/* ����ʱ�� */
	public static final String ITEM_PRM_SET_FORM = "prm_set_form" ;	/* ����ʽ(dm) */
	public static final String ITEM_REG_DOM_GRID = "reg_dom_grid" ;	/* ע������� */
	public static final String ITEM_DEP_NO = "dep_no" ;				/* ���Ŵ���(dm) */
	public static final String ITEM_CRT_TSK_ID = "crt_tsk_id" ;		/* ��������ID */
	public static final String ITEM_PRM_QUS_TYPE = "prm_qus_type" ;	/* ��������(dm) */
	public static final String ITEM_REINSP_RES = "reinsp_res" ;		/* �ز���(dm) */
	public static final String ITEM_PRM_MOV_DOM_ID = "prm_mov_dom_id" ;	/* ��Ǩס��id */
	public static final String ITEM_GRID_ID = "grid_id" ;			/* ����ID */
	public static final String ITEM_ASS_DEP = "ass_dep" ;			/* ָ�ɲ��� */
	public static final String ITEM_REG_NO = "reg_no" ;				/* ע��� */
	public static final String ITEM_QRSJ = "qrsj" ;					/* Ǩ��/��ʱ�� */
	public static final String ITEM_CLFS = "clfs" ;					/* ����ʽ */
	public static final String ITEM_WTLX = "wtlx" ;					/* �������� */
	public static final String ITEM_REINSP_CASE = "reinsp_case" ;	/* �ز���� */
	public static final String ITEM_IS_IN_BUILD = "is_in_build" ;	/* �Ƿ�д��¥�� */
	public static final String ITEM_MOV_BUILD_ID = "mov_build_id" ;	/* д��¥id */
	public static final String ITEM_MOV_PROP_ID = "mov_prop_id" ;	/* ��ҵid */
	public static final String ITEM_MOV_CASE_ID = "mov_case_id" ;	/* ����id */
	public static final String ITEM_GRID_RES_IS_MOD = "grid_res_is_mod" ;	/* �����������Ƿ��޸Ĺ� */
	public static final String ITEM_CHE_TYPE = "che_type" ;			/* ������ */
	public static final String ITEM_INPUT_MAN = "input_man" ;		/* ¼����Ա */
	public static final String ITEM_LINK_TELE = "link_tele" ;		/* ��ϵ�绰 */
	public static final String ITEM_MAIN_ID = "main_id" ;			/* ����id */
	
	/**
	 * ���캯��
	 */
	public VoMonMovDom()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoMonMovDom(DataBus value)
	{
		super(value);
	}
	
	/* ��Ǩס����ID : String */
	public String getMon_mov_dom_id()
	{
		return getValue( ITEM_MON_MOV_DOM_ID );
	}

	public void setMon_mov_dom_id( String mon_mov_dom_id1 )
	{
		setValue( ITEM_MON_MOV_DOM_ID, mon_mov_dom_id1 );
	}

	/* ��������� : String */
	public String getRes_che()
	{
		return getValue( ITEM_RES_CHE );
	}

	public void setRes_che( String res_che1 )
	{
		setValue( ITEM_RES_CHE, res_che1 );
	}

	/* �Ǽ�ʱ�� : String */
	public String getReg_date()
	{
		return getValue( ITEM_REG_DATE );
	}

	public void setReg_date( String reg_date1 )
	{
		setValue( ITEM_REG_DATE, reg_date1 );
	}

	/* �־�Ӫ��ַ : String */
	public String getNow_op_loc()
	{
		return getValue( ITEM_NOW_OP_LOC );
	}

	public void setNow_op_loc( String now_op_loc1 )
	{
		setValue( ITEM_NOW_OP_LOC, now_op_loc1 );
	}

	/* Ǩ��/��ʱ�� : String */
	public String getMov_date()
	{
		return getValue( ITEM_MOV_DATE );
	}

	public void setMov_date( String mov_date1 )
	{
		setValue( ITEM_MOV_DATE, mov_date1 );
	}

	/* ����ʱ�� : String */
	public String getPrm_set_date()
	{
		return getValue( ITEM_PRM_SET_DATE );
	}

	public void setPrm_set_date( String prm_set_date1 )
	{
		setValue( ITEM_PRM_SET_DATE, prm_set_date1 );
	}

	/* ����ʽ(dm) : String */
	public String getPrm_set_form()
	{
		return getValue( ITEM_PRM_SET_FORM );
	}

	public void setPrm_set_form( String prm_set_form1 )
	{
		setValue( ITEM_PRM_SET_FORM, prm_set_form1 );
	}

	/* ע������� : String */
	public String getReg_dom_grid()
	{
		return getValue( ITEM_REG_DOM_GRID );
	}

	public void setReg_dom_grid( String reg_dom_grid1 )
	{
		setValue( ITEM_REG_DOM_GRID, reg_dom_grid1 );
	}

	/* ���Ŵ���(dm) : String */
	public String getDep_no()
	{
		return getValue( ITEM_DEP_NO );
	}

	public void setDep_no( String dep_no1 )
	{
		setValue( ITEM_DEP_NO, dep_no1 );
	}

	/* ��������ID : String */
	public String getCrt_tsk_id()
	{
		return getValue( ITEM_CRT_TSK_ID );
	}

	public void setCrt_tsk_id( String crt_tsk_id1 )
	{
		setValue( ITEM_CRT_TSK_ID, crt_tsk_id1 );
	}

	/* ��������(dm) : String */
	public String getPrm_qus_type()
	{
		return getValue( ITEM_PRM_QUS_TYPE );
	}

	public void setPrm_qus_type( String prm_qus_type1 )
	{
		setValue( ITEM_PRM_QUS_TYPE, prm_qus_type1 );
	}

	/* �ز���(dm) : String */
	public String getReinsp_res()
	{
		return getValue( ITEM_REINSP_RES );
	}

	public void setReinsp_res( String reinsp_res1 )
	{
		setValue( ITEM_REINSP_RES, reinsp_res1 );
	}

	/* ��Ǩס��id : String */
	public String getPrm_mov_dom_id()
	{
		return getValue( ITEM_PRM_MOV_DOM_ID );
	}

	public void setPrm_mov_dom_id( String prm_mov_dom_id1 )
	{
		setValue( ITEM_PRM_MOV_DOM_ID, prm_mov_dom_id1 );
	}

	/* ����ID : String */
	public String getGrid_id()
	{
		return getValue( ITEM_GRID_ID );
	}

	public void setGrid_id( String grid_id1 )
	{
		setValue( ITEM_GRID_ID, grid_id1 );
	}

	/* ָ�ɲ��� : String */
	public String getAss_dep()
	{
		return getValue( ITEM_ASS_DEP );
	}

	public void setAss_dep( String ass_dep1 )
	{
		setValue( ITEM_ASS_DEP, ass_dep1 );
	}

	/* ע��� : String */
	public String getReg_no()
	{
		return getValue( ITEM_REG_NO );
	}

	public void setReg_no( String reg_no1 )
	{
		setValue( ITEM_REG_NO, reg_no1 );
	}

	/* Ǩ��/��ʱ�� : String */
	public String getQrsj()
	{
		return getValue( ITEM_QRSJ );
	}

	public void setQrsj( String qrsj1 )
	{
		setValue( ITEM_QRSJ, qrsj1 );
	}

	/* ����ʽ : String */
	public String getClfs()
	{
		return getValue( ITEM_CLFS );
	}

	public void setClfs( String clfs1 )
	{
		setValue( ITEM_CLFS, clfs1 );
	}

	/* �������� : String */
	public String getWtlx()
	{
		return getValue( ITEM_WTLX );
	}

	public void setWtlx( String wtlx1 )
	{
		setValue( ITEM_WTLX, wtlx1 );
	}

	/* �ز���� : String */
	public String getReinsp_case()
	{
		return getValue( ITEM_REINSP_CASE );
	}

	public void setReinsp_case( String reinsp_case1 )
	{
		setValue( ITEM_REINSP_CASE, reinsp_case1 );
	}

	/* �Ƿ�д��¥�� : String */
	public String getIs_in_build()
	{
		return getValue( ITEM_IS_IN_BUILD );
	}

	public void setIs_in_build( String is_in_build1 )
	{
		setValue( ITEM_IS_IN_BUILD, is_in_build1 );
	}

	/* д��¥id : String */
	public String getMov_build_id()
	{
		return getValue( ITEM_MOV_BUILD_ID );
	}

	public void setMov_build_id( String mov_build_id1 )
	{
		setValue( ITEM_MOV_BUILD_ID, mov_build_id1 );
	}

	/* ��ҵid : String */
	public String getMov_prop_id()
	{
		return getValue( ITEM_MOV_PROP_ID );
	}

	public void setMov_prop_id( String mov_prop_id1 )
	{
		setValue( ITEM_MOV_PROP_ID, mov_prop_id1 );
	}

	/* ����id : String */
	public String getMov_case_id()
	{
		return getValue( ITEM_MOV_CASE_ID );
	}

	public void setMov_case_id( String mov_case_id1 )
	{
		setValue( ITEM_MOV_CASE_ID, mov_case_id1 );
	}

	/* �����������Ƿ��޸Ĺ� : String */
	public String getGrid_res_is_mod()
	{
		return getValue( ITEM_GRID_RES_IS_MOD );
	}

	public void setGrid_res_is_mod( String grid_res_is_mod1 )
	{
		setValue( ITEM_GRID_RES_IS_MOD, grid_res_is_mod1 );
	}

	/* ������ : String */
	public String getChe_type()
	{
		return getValue( ITEM_CHE_TYPE );
	}

	public void setChe_type( String che_type1 )
	{
		setValue( ITEM_CHE_TYPE, che_type1 );
	}

	/* ¼����Ա : String */
	public String getInput_man()
	{
		return getValue( ITEM_INPUT_MAN );
	}

	public void setInput_man( String input_man1 )
	{
		setValue( ITEM_INPUT_MAN, input_man1 );
	}

	/* ��ϵ�绰 : String */
	public String getLink_tele()
	{
		return getValue( ITEM_LINK_TELE );
	}

	public void setLink_tele( String link_tele1 )
	{
		setValue( ITEM_LINK_TELE, link_tele1 );
	}

	/* ����id : String */
	public String getMain_id()
	{
		return getValue( ITEM_MAIN_ID );
	}

	public void setMain_id( String main_id1 )
	{
		setValue( ITEM_MAIN_ID, main_id1 );
	}

}

