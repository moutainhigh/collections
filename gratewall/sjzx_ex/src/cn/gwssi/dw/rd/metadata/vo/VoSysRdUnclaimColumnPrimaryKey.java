package cn.gwssi.dw.rd.metadata.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_rd_unclaim_column]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysRdUnclaimColumnPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201205071126260004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_RD_UNCLAIM_COLUMN_ID = "sys_rd_unclaim_column_id" ;	/* ������ */
	
	/**
	 * ���캯��
	 */
	public VoSysRdUnclaimColumnPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysRdUnclaimColumnPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ������ : String */
	public String getSys_rd_unclaim_column_id()
	{
		return getValue( ITEM_SYS_RD_UNCLAIM_COLUMN_ID );
	}

	public void setSys_rd_unclaim_column_id( String sys_rd_unclaim_column_id1 )
	{
		setValue( ITEM_SYS_RD_UNCLAIM_COLUMN_ID, sys_rd_unclaim_column_id1 );
	}

}

