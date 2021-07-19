package com.gwssi.share.trs.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[trs_share_service]�����ݶ�����
 * @author Administrator
 *
 */
public class VoTrsShareService extends VoBase
{
	private static final long serialVersionUID = 201308051642360002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_TRS_SERVICE_ID = "trs_service_id" ;	/* ����ID */
	public static final String ITEM_SERVICE_TARGETS_ID = "service_targets_id" ;	/* �����������ID */
	public static final String ITEM_TRS_SERVICE_NAME = "trs_service_name" ;	/* �������� */
	public static final String ITEM_TRS_SERVICE_NO = "trs_service_no" ;	/* ������ */
	public static final String ITEM_TRS_DATA_BASE = "trs_data_base" ;	/* ����� */
	public static final String ITEM_TRS_COLUMN = "trs_column" ;		/* չʾ�ֶ� */
	public static final String ITEM_SERVICE_STATE = "service_state" ;	/* �Ƿ����� */
	public static final String ITEM_IS_MARKUP = "is_markup" ;		/* �Ƿ���Ч */
	public static final String ITEM_CREATOR_ID = "creator_id" ;		/* ������ID */
	public static final String ITEM_CREATED_TIME = "created_time" ;	/* ����ʱ�� */
	public static final String ITEM_LAST_MODIFY_ID = "last_modify_id" ;	/* ����޸���ID */
	public static final String ITEM_LAST_MODIFY_TIME = "last_modify_time" ;	/* ����޸�ʱ�� */
	public static final String ITEM_SERVICE_DESCRIPTION = "service_description" ;	/* ����˵�� */
	public static final String ITEM_TRS_SEARCH_COLUMN = "trs_search_column" ;		/* �����ֶ� */
	public static final String ITEM_USE_TEMPLATE = "use_template" ;		/* �Ƿ�ʹ��ģ�� */
	public static final String ITEM_TRS_TEMPLATE = "trs_template" ;		/* ģ���ļ� */
	public static final String ITEM_TRS_TEMPLATE_EX = "trs_template_ex" ;		/* ����ģ���ļ���չ */
	
	/**
	 * ���캯��
	 */
	public VoTrsShareService()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoTrsShareService(DataBus value)
	{
		super(value);
	}
	
	/* ����ID : String */
	public String getTrs_service_id()
	{
		return getValue( ITEM_TRS_SERVICE_ID );
	}

	public void setTrs_service_id( String trs_service_id1 )
	{
		setValue( ITEM_TRS_SERVICE_ID, trs_service_id1 );
	}

	/* �����������ID : String */
	public String getService_targets_id()
	{
		return getValue( ITEM_SERVICE_TARGETS_ID );
	}

	public void setService_targets_id( String service_targets_id1 )
	{
		setValue( ITEM_SERVICE_TARGETS_ID, service_targets_id1 );
	}

	/* �������� : String */
	public String getTrs_service_name()
	{
		return getValue( ITEM_TRS_SERVICE_NAME );
	}

	public void setTrs_service_name( String trs_service_name1 )
	{
		setValue( ITEM_TRS_SERVICE_NAME, trs_service_name1 );
	}

	/* ������ : String */
	public String getTrs_service_no()
	{
		return getValue( ITEM_TRS_SERVICE_NO );
	}

	public void setTrs_service_no( String trs_service_no1 )
	{
		setValue( ITEM_TRS_SERVICE_NO, trs_service_no1 );
	}

	/* ����� : String */
	public String getTrs_data_base()
	{
		return getValue( ITEM_TRS_DATA_BASE );
	}

	public void setTrs_data_base( String trs_data_base1 )
	{
		setValue( ITEM_TRS_DATA_BASE, trs_data_base1 );
	}

	/* չʾ�ֶ� : String */
	public String getTrs_column()
	{
		return getValue( ITEM_TRS_COLUMN );
	}

	public void setTrs_column( String trs_column1 )
	{
		setValue( ITEM_TRS_COLUMN, trs_column1 );
	}

	/* �Ƿ����� : String */
	public String getService_state()
	{
		return getValue( ITEM_SERVICE_STATE );
	}

	public void setService_state( String service_state1 )
	{
		setValue( ITEM_SERVICE_STATE, service_state1 );
	}

	/* �Ƿ���Ч : String */
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

	/* ����˵�� : String */
	public String getService_description()
	{
		return getValue( ITEM_SERVICE_DESCRIPTION );
	}

	public void setService_description( String service_description1 )
	{
		setValue( ITEM_SERVICE_DESCRIPTION, service_description1 );
	}
	
	/* �����ֶ� : String */
	public String getTrs_search_column()
	{
		return getValue( ITEM_TRS_SEARCH_COLUMN );
	}

	public void setTrs_search_column( String trs_search_column1 )
	{
		setValue( ITEM_TRS_SEARCH_COLUMN, trs_search_column1 );
	}

	/* �Ƿ�ʹ��ģ�� : String */
	public String getUse_template()
	{
		return getValue( ITEM_USE_TEMPLATE );
	}

	public void setUse_template( String use_template )
	{
		setValue( ITEM_USE_TEMPLATE, use_template );
	}

	
	/* ģ���ļ� : String */
	public String getTrs_template()
	{
		return getValue( ITEM_TRS_TEMPLATE );
	}

	public void setTrs_template( String trs_template )
	{
		setValue( ITEM_TRS_TEMPLATE, trs_template );
	}
	
	
	/* ģ���ļ���չ : String */
	public String getTrs_template_ex()
	{
		return getValue( ITEM_TRS_TEMPLATE_EX );
	}

	public void setTrs_template_ex( String trs_template_ex )
	{
		setValue( ITEM_TRS_TEMPLATE_EX, trs_template_ex );
	}
}

