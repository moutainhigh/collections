package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_rd_standard_codedata]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysRdStandardCodedataSelectKey extends VoBase
{
	private static final long serialVersionUID = 201205031401170003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_RD_STANDAR_CODEINDEX = "sys_rd_standar_codeindex" ;	/* �����ʶ�� */
	
	/**
	 * ���캯��
	 */
	public VoSysRdStandardCodedataSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysRdStandardCodedataSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* �����ʶ�� : String */
	public String getSys_rd_standar_codeindex()
	{
		return getValue( ITEM_SYS_RD_STANDAR_CODEINDEX );
	}

	public void setSys_rd_standar_codeindex( String sys_rd_standar_codeindex1 )
	{
		setValue( ITEM_SYS_RD_STANDAR_CODEINDEX, sys_rd_standar_codeindex1 );
	}

}

