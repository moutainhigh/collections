package com.gwssi.resource.collect.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[res_collect_table]�����ݶ�����
 * @author Administrator
 *
 */
public class VoResCollectTable extends VoBase
{
	private static final long serialVersionUID = 201303221045510002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_COLLECT_TABLE_ID = "collect_table_id" ;	/* �ɼ����ݱ�ID */
	public static final String ITEM_SERVICE_TARGETS_ID = "service_targets_id" ;	/* �������ID */
	public static final String ITEM_TABLE_NAME_EN = "table_name_en" ;	/* ������ */
	public static final String ITEM_TABLE_NAME_CN = "table_name_cn" ;	/* ���������� */
	public static final String ITEM_TABLE_TYPE = "table_type" ;		/* ������ */
	public static final String ITEM_TABLE_DESC = "table_desc" ;		/* ������ */
	public static final String ITEM_TABLE_STATUS = "table_status" ;	/* ��״̬ */
	public static final String ITEM_IS_MARKUP = "is_markup" ;		/* ��Ч��� */
	public static final String ITEM_CREATOR_ID = "creator_id" ;		/* ������ID */
	public static final String ITEM_CREATED_TIME = "created_time" ;	/* ����ʱ�� */
	public static final String ITEM_LAST_MODIFY_ID = "last_modify_id" ;	/* ����޸���ID */
	public static final String ITEM_LAST_MODIFY_TIME = "last_modify_time" ;	/* ����޸�ʱ�� */
	public static final String ITEM_FJ_FK = "fj_fk" ;				/* ����id */
	public static final String ITEM_FJMC = "fjmc" ;					/* �������� */
	public static final String ITEM_DELNAMES = "delNAMEs";			/* delNAMEs */
	public static final String ITEM_DELIDS = "delIDs";			/* delIDs */
	public static final String ITEM_CJ_LY = "cj_ly";			/* cj_ly */
	public static final String ITEM_IF_CREAT = "if_creat";			/* if_creat */
	
	/**
	 * ���캯��
	 */
	public VoResCollectTable()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoResCollectTable(DataBus value)
	{
		super(value);
	}
	
	/* �ɼ����ݱ�ID : String */
	public String getCollect_table_id()
	{
		return getValue( ITEM_COLLECT_TABLE_ID );
	}

	public void setCollect_table_id( String collect_table_id1 )
	{
		setValue( ITEM_COLLECT_TABLE_ID, collect_table_id1 );
	}

	/* �������ID : String */
	public String getService_targets_id()
	{
		return getValue( ITEM_SERVICE_TARGETS_ID );
	}

	public void setService_targets_id( String service_targets_id1 )
	{
		setValue( ITEM_SERVICE_TARGETS_ID, service_targets_id1 );
	}

	/* ������ : String */
	public String getTable_name_en()
	{
		return getValue( ITEM_TABLE_NAME_EN );
	}

	public void setTable_name_en( String table_name_en1 )
	{
		setValue( ITEM_TABLE_NAME_EN, table_name_en1 );
	}

	/* ���������� : String */
	public String getTable_name_cn()
	{
		return getValue( ITEM_TABLE_NAME_CN );
	}

	public void setTable_name_cn( String table_name_cn1 )
	{
		setValue( ITEM_TABLE_NAME_CN, table_name_cn1 );
	}

	/* ������ : String */
	public String getTable_type()
	{
		return getValue( ITEM_TABLE_TYPE );
	}

	public void setTable_type( String table_type1 )
	{
		setValue( ITEM_TABLE_TYPE, table_type1 );
	}

	/* ������ : String */
	public String getTable_desc()
	{
		return getValue( ITEM_TABLE_DESC );
	}

	public void setTable_desc( String table_desc1 )
	{
		setValue( ITEM_TABLE_DESC, table_desc1 );
	}

	/* ��״̬ : String */
	public String getTable_status()
	{
		return getValue( ITEM_TABLE_STATUS );
	}

	public void setTable_status( String table_status1 )
	{
		setValue( ITEM_TABLE_STATUS, table_status1 );
	}

	/* ��Ч��� : String */
	public String getIs_markup()
	{
		return getValue( ITEM_IS_MARKUP );
	}

	public void setIs_markup( String is_markup1 )
	{
		setValue( ITEM_IS_MARKUP, is_markup1 );
	}

	/* ������ID : String */
	public String getCreator_id()
	{
		return getValue( ITEM_CREATOR_ID );
	}

	public void setCreator_id( String creator_id1 )
	{
		setValue( ITEM_CREATOR_ID, creator_id1 );
	}

	/* ����ʱ�� : String */
	public String getCreated_time()
	{
		return getValue( ITEM_CREATED_TIME );
	}

	public void setCreated_time( String created_time1 )
	{
		setValue( ITEM_CREATED_TIME, created_time1 );
	}

	/* ����޸���ID : String */
	public String getLast_modify_id()
	{
		return getValue( ITEM_LAST_MODIFY_ID );
	}

	public void setLast_modify_id( String last_modify_id1 )
	{
		setValue( ITEM_LAST_MODIFY_ID, last_modify_id1 );
	}

	/* ����޸�ʱ�� : String */
	public String getLast_modify_time()
	{
		return getValue( ITEM_LAST_MODIFY_TIME );
	}

	public void setLast_modify_time( String last_modify_time1 )
	{
		setValue( ITEM_LAST_MODIFY_TIME, last_modify_time1 );
	}
	
	/* �ɼ���Դ : String */
	public String getCj_ly()
	{
		return getValue( ITEM_CJ_LY );
	}

	public void setCj_ly(String cj_ly)
	{
		setValue( ITEM_CJ_LY, cj_ly );
	}
	
	/* �Ƿ����ɲɼ��� : String */
	public String getIf_creat()
	{
		return getValue( ITEM_IF_CREAT );
	}

	public void setIf_creat(String if_creat)
	{
		setValue( ITEM_IF_CREAT, if_creat);
	}

}

