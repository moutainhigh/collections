package cn.gwssi.dw.rd.metadata.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_rd_table_relation]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysRdTableRelationSelectKey extends VoBase
{
	private static final long serialVersionUID = 201205260127140003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_TABLE_NAME = "table_name" ;		/* ������������ */
	public static final String ITEM_RELATION_TABLE_NAME = "relation_table_name" ;	/* �������������� */
	
	/**
	 * ���캯��
	 */
	public VoSysRdTableRelationSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysRdTableRelationSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* ������������ : String */
	public String getTable_name()
	{
		return getValue( ITEM_TABLE_NAME );
	}

	public void setTable_name( String table_name1 )
	{
		setValue( ITEM_TABLE_NAME, table_name1 );
	}

	/* �������������� : String */
	public String getRelation_table_name()
	{
		return getValue( ITEM_RELATION_TABLE_NAME );
	}

	public void setRelation_table_name( String relation_table_name1 )
	{
		setValue( ITEM_RELATION_TABLE_NAME, relation_table_name1 );
	}

}

