package cn.gwssi.dw.rd.metadata.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_rd_unclaim_column]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysRdUnclaimColumnSelectKey extends VoBase
{
	private static final long serialVersionUID = 201205071126260003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_RD_UNCLAIM_TABLE_ID = "sys_rd_unclaim_table_id" ;	/* �����ID */
	public static final String ITEM_UNCLAIM_COLUMN_CODE = "unclaim_column_code" ;	/* �ֶδ��� */
	public static final String ITEM_UNCLAIM_COLUMN_TYPE = "unclaim_column_type" ;	/* �ֶ����� */
	
	/**
	 * ���캯��
	 */
	public VoSysRdUnclaimColumnSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysRdUnclaimColumnSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* �����ID : String */
	public String getSys_rd_unclaim_table_id()
	{
		return getValue( ITEM_SYS_RD_UNCLAIM_TABLE_ID );
	}

	public void setSys_rd_unclaim_table_id( String sys_rd_unclaim_table_id1 )
	{
		setValue( ITEM_SYS_RD_UNCLAIM_TABLE_ID, sys_rd_unclaim_table_id1 );
	}

	/* �ֶδ��� : String */
	public String getUnclaim_column_code()
	{
		return getValue( ITEM_UNCLAIM_COLUMN_CODE );
	}

	public void setUnclaim_column_code( String unclaim_column_code1 )
	{
		setValue( ITEM_UNCLAIM_COLUMN_CODE, unclaim_column_code1 );
	}

	/* �ֶ����� : String */
	public String getUnclaim_column_type()
	{
		return getValue( ITEM_UNCLAIM_COLUMN_TYPE );
	}

	public void setUnclaim_column_type( String unclaim_column_type1 )
	{
		setValue( ITEM_UNCLAIM_COLUMN_TYPE, unclaim_column_type1 );
	}

}

