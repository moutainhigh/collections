package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_etl_dbsource]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysEtlDbsourceSelectKey extends VoBase
{
	private static final long serialVersionUID = 200805091051540003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_DBSOURCE_LB = "dbsource_lb" ;	/* ������Դ��� */
	public static final String ITEM_DB_NAME = "db_name" ;			/* ���ݿ����� */
	
	/**
	 * ���캯��
	 */
	public VoSysEtlDbsourceSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysEtlDbsourceSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* ������Դ��� : String */
	public String getDbsource_lb()
	{
		return getValue( ITEM_DBSOURCE_LB );
	}

	public void setDbsource_lb( String dbsource_lb1 )
	{
		setValue( ITEM_DBSOURCE_LB, dbsource_lb1 );
	}

	/* ���ݿ����� : String */
	public String getDb_name()
	{
		return getValue( ITEM_DB_NAME );
	}

	public void setDb_name( String db_name1 )
	{
		setValue( ITEM_DB_NAME, db_name1 );
	}

}

