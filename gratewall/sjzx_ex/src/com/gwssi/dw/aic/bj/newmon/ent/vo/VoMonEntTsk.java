package com.gwssi.dw.aic.bj.newmon.ent.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[mon_ent_tsk]�����ݶ�����
 * @author Administrator
 *
 */
public class VoMonEntTsk extends VoBase
{
	private static final long serialVersionUID = 200811191653120002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_MON_ENT_TSK_ID = "mon_ent_tsk_id" ;	/* �����������id */
	public static final String ITEM_MON_MIS_ID = "mon_mis_id" ;		/* �������id */
	public static final String ITEM_RES_CHE = "res_che" ;			/* ��������� */
	public static final String ITEM_CHE_MIS_STA_TIME = "che_mis_sta_time" ;	/* �������ʼʱ�� */
	public static final String ITEM_CHE_MIS_FIN_TIME = "che_mis_fin_time" ;	/* ����������ʱ�� */
	public static final String ITEM_CHE_TYPE = "che_type" ;			/* ������ */
	public static final String ITEM_CHE_STATE = "che_state" ;		/* ����״̬(dm) */
	public static final String ITEM_CHE_RES = "che_res" ;			/* �����(dm) */
	public static final String ITEM_CHE_COM_TIME = "che_com_time" ;	/* ������ʱ�� */
	public static final String ITEM_DEP_NO = "dep_no" ;				/* ���Ŵ���(dm) */
	public static final String ITEM_DATA_FLAG = "data_flag" ;		/* ���ݱ�־ */
	public static final String ITEM_GRID_ID = "grid_id" ;			/* ����ID */
	public static final String ITEM_MAIN_ID = "main_id" ;			/* ����ID */
	
	/**
	 * ���캯��
	 */
	public VoMonEntTsk()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoMonEntTsk(DataBus value)
	{
		super(value);
	}
	
	/* �����������id : String */
	public String getMon_ent_tsk_id()
	{
		return getValue( ITEM_MON_ENT_TSK_ID );
	}

	public void setMon_ent_tsk_id( String mon_ent_tsk_id1 )
	{
		setValue( ITEM_MON_ENT_TSK_ID, mon_ent_tsk_id1 );
	}

	/* �������id : String */
	public String getMon_mis_id()
	{
		return getValue( ITEM_MON_MIS_ID );
	}

	public void setMon_mis_id( String mon_mis_id1 )
	{
		setValue( ITEM_MON_MIS_ID, mon_mis_id1 );
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

	/* �������ʼʱ�� : String */
	public String getChe_mis_sta_time()
	{
		return getValue( ITEM_CHE_MIS_STA_TIME );
	}

	public void setChe_mis_sta_time( String che_mis_sta_time1 )
	{
		setValue( ITEM_CHE_MIS_STA_TIME, che_mis_sta_time1 );
	}

	/* ����������ʱ�� : String */
	public String getChe_mis_fin_time()
	{
		return getValue( ITEM_CHE_MIS_FIN_TIME );
	}

	public void setChe_mis_fin_time( String che_mis_fin_time1 )
	{
		setValue( ITEM_CHE_MIS_FIN_TIME, che_mis_fin_time1 );
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

	/* ����״̬(dm) : String */
	public String getChe_state()
	{
		return getValue( ITEM_CHE_STATE );
	}

	public void setChe_state( String che_state1 )
	{
		setValue( ITEM_CHE_STATE, che_state1 );
	}

	/* �����(dm) : String */
	public String getChe_res()
	{
		return getValue( ITEM_CHE_RES );
	}

	public void setChe_res( String che_res1 )
	{
		setValue( ITEM_CHE_RES, che_res1 );
	}

	/* ������ʱ�� : String */
	public String getChe_com_time()
	{
		return getValue( ITEM_CHE_COM_TIME );
	}

	public void setChe_com_time( String che_com_time1 )
	{
		setValue( ITEM_CHE_COM_TIME, che_com_time1 );
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

	/* ���ݱ�־ : String */
	public String getData_flag()
	{
		return getValue( ITEM_DATA_FLAG );
	}

	public void setData_flag( String data_flag1 )
	{
		setValue( ITEM_DATA_FLAG, data_flag1 );
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

	/* ����ID : String */
	public String getMain_id()
	{
		return getValue( ITEM_MAIN_ID );
	}

	public void setMain_id( String main_id1 )
	{
		setValue( ITEM_MAIN_ID, main_id1 );
	}

}

