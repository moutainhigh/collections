package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_rd_standard_term]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysRdStandardTermSelectKey extends VoBase
{
	private static final long serialVersionUID = 201205021622440003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_RD_STANDAR_TERM_ID = "sys_rd_standar_term_id" ;	/* ����ID */
	
	/**
	 * ���캯��
	 */
	public VoSysRdStandardTermSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysRdStandardTermSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* ����ID : String */
	public String getSys_rd_standar_term_id()
	{
		return getValue( ITEM_SYS_RD_STANDAR_TERM_ID );
	}

	public void setSys_rd_standar_term_id( String sys_rd_standar_term_id1 )
	{
		setValue( ITEM_SYS_RD_STANDAR_TERM_ID, sys_rd_standar_term_id1 );
	}

}

