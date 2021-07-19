package com.gwssi.log.collectlog.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[collect_joumal]�����ݶ�����
 * @author Administrator
 *
 */
public class VoCollectJoumalSelectKey extends VoBase
{
	private static final long serialVersionUID = 201304101519320003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SERVICE_TARGETS_NAME = "service_targets_name" ;	/* ����������� */
	public static final String ITEM_COLLECT_TYPE = "collect_type" ;	/* ����� ftp */
	public static final String ITEM_TASK_STATUS = "task_status" ;	/* ����� ����ͣ�ù鵵 */
	
	/**
	 * ���캯��
	 */
	public VoCollectJoumalSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoCollectJoumalSelectKey(DataBus value)
	{
		super(value);
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

	/* ����� ftp : String */
	public String getCollect_type()
	{
		return getValue( ITEM_COLLECT_TYPE );
	}

	public void setCollect_type( String collect_type1 )
	{
		setValue( ITEM_COLLECT_TYPE, collect_type1 );
	}

	/* ����� ����ͣ�ù鵵 : String */
	public String getTask_status()
	{
		return getValue( ITEM_TASK_STATUS );
	}

	public void setTask_status( String task_status1 )
	{
		setValue( ITEM_TASK_STATUS, task_status1 );
	}

}

