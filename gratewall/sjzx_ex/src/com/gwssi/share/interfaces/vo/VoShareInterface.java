package com.gwssi.share.interfaces.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[share_interface]�����ݶ�����
 * @author Administrator
 *
 */
public class VoShareInterface extends VoBase
{
	private static final long serialVersionUID = 201303121022120002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_INTERFACE_ID = "interface_id" ;	/* �ӿ�ID */
	public static final String ITEM_INTERFACE_NAME = "interface_name" ;	/* �ӿ����� */
	public static final String ITEM_TABLE_ID = "table_id" ;			/* ����봮 */
	public static final String ITEM_TABLE_NAME_CN = "table_name_cn" ;	/* ���������� */
	public static final String ITEM_SQL = "sql" ;					/* sql��� */
	public static final String ITEM_INTERFACE_DESCRIPTION = "interface_description" ;	/* �ӿ����� */
	public static final String ITEM_INTERFACE_STATE = "interface_state" ;	/* �ӿ�״̬ */
	public static final String ITEM_IS_MARKUP = "is_markup" ;		/* ����� */
	public static final String ITEM_CREATOR_ID = "creator_id" ;		/* ������ID */
	public static final String ITEM_CREATED_TIME = "created_time" ;	/* ����ʱ�� */
	public static final String ITEM_LAST_MODIFY_ID = "last_modify_id" ;	/* ����޸���ID */
	public static final String ITEM_LAST_MODIFY_TIME = "last_modify_time" ;	/* ����޸�ʱ�� */
	
	/**
	 * ���캯��
	 */
	public VoShareInterface()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoShareInterface(DataBus value)
	{
		super(value);
	}
	
	/* �ӿ�ID : String */
	public String getInterface_id()
	{
		return getValue( ITEM_INTERFACE_ID );
	}

	public void setInterface_id( String interface_id1 )
	{
		setValue( ITEM_INTERFACE_ID, interface_id1 );
	}

	/* �ӿ����� : String */
	public String getInterface_name()
	{
		return getValue( ITEM_INTERFACE_NAME );
	}

	public void setInterface_name( String interface_name1 )
	{
		setValue( ITEM_INTERFACE_NAME, interface_name1 );
	}

	/* ����봮 : String */
	public String getTable_id()
	{
		return getValue( ITEM_TABLE_ID );
	}

	public void setTable_id( String table_id1 )
	{
		setValue( ITEM_TABLE_ID, table_id1 );
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

	/* sql��� : String */
	public String getSql()
	{
		return getValue( ITEM_SQL );
	}

	public void setSql( String sql1 )
	{
		setValue( ITEM_SQL, sql1 );
	}

	/* �ӿ����� : String */
	public String getInterface_description()
	{
		return getValue( ITEM_INTERFACE_DESCRIPTION );
	}

	public void setInterface_description( String interface_description1 )
	{
		setValue( ITEM_INTERFACE_DESCRIPTION, interface_description1 );
	}

	/* �ӿ�״̬ : String */
	public String getInterface_state()
	{
		return getValue( ITEM_INTERFACE_STATE );
	}

	public void setInterface_state( String interface_state1 )
	{
		setValue( ITEM_INTERFACE_STATE, interface_state1 );
	}

	/* ����� : String */
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

}

