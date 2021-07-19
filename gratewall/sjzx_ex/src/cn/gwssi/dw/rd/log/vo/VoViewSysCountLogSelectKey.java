package cn.gwssi.dw.rd.log.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[view_sys_count_log]�����ݶ�����
 * @author Administrator
 *
 */
public class VoViewSysCountLogSelectKey extends VoBase
{
	private static final long serialVersionUID = 201208031540190003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_NAME = "sys_name" ;			/* �������� */
	public static final String ITEM_TABLE_NAME_CN = "table_name_cn" ;	/* �������� */
	public static final String ITEM_COUNT_DATE = "count_date" ;		/* ͳ������ */
	public static final String ITEM_CLASS_STATE = "class_state" ;	/* ����״̬ */
	
	/**
	 * ���캯��
	 */
	public VoViewSysCountLogSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoViewSysCountLogSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* �������� : String */
	public String getSys_name()
	{
		return getValue( ITEM_SYS_NAME );
	}

	public void setSys_name( String sys_name1 )
	{
		setValue( ITEM_SYS_NAME, sys_name1 );
	}

	/* �������� : String */
	public String getTable_name_cn()
	{
		return getValue( ITEM_TABLE_NAME_CN );
	}

	public void setTable_name_cn( String table_name_cn1 )
	{
		setValue( ITEM_TABLE_NAME_CN, table_name_cn1 );
	}

	/* ͳ������ : String */
	public String getCount_date()
	{
		return getValue( ITEM_COUNT_DATE );
	}

	public void setCount_date( String count_date1 )
	{
		setValue( ITEM_COUNT_DATE, count_date1 );
	}

	/* ����״̬ : String */
	public String getClass_state()
	{
		return getValue( ITEM_CLASS_STATE );
	}

	public void setClass_state( String class_state1 )
	{
		setValue( ITEM_CLASS_STATE, class_state1 );
	}

}

