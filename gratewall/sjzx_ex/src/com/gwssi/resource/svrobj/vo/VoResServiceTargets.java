package com.gwssi.resource.svrobj.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[res_service_targets]�����ݶ�����
 * @author Administrator
 *
 */
public class VoResServiceTargets extends VoBase
{
	private static final long serialVersionUID = 201303131040540002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SERVICE_TARGETS_ID = "service_targets_id" ;	/* �������ID */
	public static final String ITEM_SERVICE_TARGETS_NO = "service_targets_no" ;	/* ���������� */
	public static final String ITEM_SERVICE_TARGETS_NAME = "service_targets_name" ;	/* ����������� */
	public static final String ITEM_SERVICE_TARGETS_TYPE = "service_targets_type" ;	/* ����������� */
	public static final String ITEM_IS_BIND_IP = "is_bind_ip" ;		/* �Ƿ��IP */
	public static final String ITEM_IP = "ip" ;						/* IP */
	public static final String ITEM_SERVICE_PASSWORD = "service_password" ;	/* ������� */
	public static final String ITEM_SERVICE_STATUS = "service_status" ;	/* ����״̬ */
	public static final String ITEM_SERVICE_DESC = "service_desc" ;	/* ����������� */
	public static final String ITEM_IS_MARKUP = "is_markup" ;		/* ��Ч��� */
	public static final String ITEM_CREATOR_ID = "creator_id" ;		/* ������ID */
	public static final String ITEM_CREATED_TIME = "created_time" ;	/* ����ʱ�� */
	public static final String ITEM_LAST_MODIFY_ID = "last_modify_id" ;	/* ����޸���ID */
	public static final String ITEM_LAST_MODIFY_TIME = "last_modify_time" ;	/* ����޸�ʱ�� */
	public static final String ITEM_IS_FORMAL = "is_formal" ;	/* �Ƿ������� */
	/**
	 * ���캯��
	 */
	public VoResServiceTargets()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoResServiceTargets(DataBus value)
	{
		super(value);
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

	/* ���������� : String */
	public String getService_targets_no()
	{
		return getValue( ITEM_SERVICE_TARGETS_NO );
	}

	public void setService_targets_no( String service_targets_no1 )
	{
		setValue( ITEM_SERVICE_TARGETS_NO, service_targets_no1 );
	}

	/* ����������� : String */
	public String getService_targets_name()
	{
		return getValue( ITEM_SERVICE_TARGETS_NAME );
	}

	public void setService_targets_name( String service_targets_name1 )
	{
		setValue( ITEM_SERVICE_TARGETS_NAME, service_targets_name1 );
	}

	/* ����������� : String */
	public String getService_targets_type()
	{
		return getValue( ITEM_SERVICE_TARGETS_TYPE );
	}

	public void setService_targets_type( String service_targets_type1 )
	{
		setValue( ITEM_SERVICE_TARGETS_TYPE, service_targets_type1 );
	}

	/* �Ƿ��IP : String */
	public String getIs_bind_ip()
	{
		return getValue( ITEM_IS_BIND_IP );
	}

	public void setIs_bind_ip( String is_bind_ip1 )
	{
		setValue( ITEM_IS_BIND_IP, is_bind_ip1 );
	}

	/* IP : String */
	public String getIp()
	{
		return getValue( ITEM_IP );
	}

	public void setIp( String ip1 )
	{
		setValue( ITEM_IP, ip1 );
	}

	/* ������� : String */
	public String getService_password()
	{
		return getValue( ITEM_SERVICE_PASSWORD );
	}

	public void setService_password( String service_password1 )
	{
		setValue( ITEM_SERVICE_PASSWORD, service_password1 );
	}

	/* ����״̬ : String */
	public String getService_status()
	{
		return getValue( ITEM_SERVICE_STATUS );
	}

	public void setService_status( String service_status1 )
	{
		setValue( ITEM_SERVICE_STATUS, service_status1 );
	}

	/* ����������� : String */
	public String getService_desc()
	{
		return getValue( ITEM_SERVICE_DESC );
	}

	public void setService_desc( String service_desc1 )
	{
		setValue( ITEM_SERVICE_DESC, service_desc1 );
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
	/* �Ƿ������� : String */
	public String getIs_formal()
	{
		return getValue( ITEM_IS_FORMAL );
	}

	public void setIs_formal( String is_foraml1 )
	{
		setValue( ITEM_IS_FORMAL, is_foraml1 );
	}
}

