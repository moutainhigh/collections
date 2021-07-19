package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_etl_dbsource]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysEtlDbsourcePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200805091051550004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_ETL_DBSOURCE_ID = "sys_etl_dbsource_id" ;	/* ID */
	
	/**
	 * ���캯��
	 */
	public VoSysEtlDbsourcePrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysEtlDbsourcePrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ID : String */
	public String getSys_etl_dbsource_id()
	{
		return getValue( ITEM_SYS_ETL_DBSOURCE_ID );
	}

	public void setSys_etl_dbsource_id( String sys_etl_dbsource_id1 )
	{
		setValue( ITEM_SYS_ETL_DBSOURCE_ID, sys_etl_dbsource_id1 );
	}

}

