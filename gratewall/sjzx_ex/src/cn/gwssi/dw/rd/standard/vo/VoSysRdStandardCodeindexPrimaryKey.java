package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_rd_standard_codeindex]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysRdStandardCodeindexPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201205031030510008L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_RD_STANDAR_CODEINDEX = "sys_rd_standar_codeindex" ;	/* ���뼯��ʶ�� */
	
	/**
	 * ���캯��
	 */
	public VoSysRdStandardCodeindexPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysRdStandardCodeindexPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ���뼯��ʶ�� : String */
	public String getSys_rd_standar_codeindex()
	{
		return getValue( ITEM_SYS_RD_STANDAR_CODEINDEX );
	}

	public void setSys_rd_standar_codeindex( String sys_rd_standar_codeindex1 )
	{
		setValue( ITEM_SYS_RD_STANDAR_CODEINDEX, sys_rd_standar_codeindex1 );
	}

}

