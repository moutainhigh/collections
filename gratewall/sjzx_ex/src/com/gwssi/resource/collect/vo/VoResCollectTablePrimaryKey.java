package com.gwssi.resource.collect.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[res_collect_table]�����ݶ�����
 * @author Administrator
 *
 */
public class VoResCollectTablePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201303221045510004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_COLLECT_TABLE_ID = "collect_table_id" ;	/* �ɼ����ݱ�ID */
	
	/**
	 * ���캯��
	 */
	public VoResCollectTablePrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoResCollectTablePrimaryKey(DataBus value)
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

}

