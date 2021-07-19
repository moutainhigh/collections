package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_rd_standard_codedata]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysRdStandardCodedata extends VoBase
{
	private static final long serialVersionUID = 201205031401170002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_ID = "id" ;						/* ����ֵID */
	public static final String ITEM_SYS_RD_STANDAR_CODEINDEX = "sys_rd_standar_codeindex" ;	/* �����ʶ�� */
	public static final String ITEM_SYS_RD_STANDARD_CODEVALUE = "sys_rd_standard_codevalue" ;	/* ����ֵ */
	public static final String ITEM_SYS_RD_STANDARD_CODENAME = "sys_rd_standard_codename" ;	/* �������� */
	public static final String ITEM_DESCRIPTION = "description" ;	/* ˵�� */
	
	/**
	 * ���캯��
	 */
	public VoSysRdStandardCodedata()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysRdStandardCodedata(DataBus value)
	{
		super(value);
	}
	
	/* ����ֵID : String */
	public String getId()
	{
		return getValue( ITEM_ID );
	}

	public void setId( String id1 )
	{
		setValue( ITEM_ID, id1 );
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

	/* ����ֵ : String */
	public String getSys_rd_standard_codevalue()
	{
		return getValue( ITEM_SYS_RD_STANDARD_CODEVALUE );
	}

	public void setSys_rd_standard_codevalue( String sys_rd_standard_codevalue1 )
	{
		setValue( ITEM_SYS_RD_STANDARD_CODEVALUE, sys_rd_standard_codevalue1 );
	}

	/* �������� : String */
	public String getSys_rd_standard_codename()
	{
		return getValue( ITEM_SYS_RD_STANDARD_CODENAME );
	}

	public void setSys_rd_standard_codename( String sys_rd_standard_codename1 )
	{
		setValue( ITEM_SYS_RD_STANDARD_CODENAME, sys_rd_standard_codename1 );
	}

	/* ˵�� : String */
	public String getDescription()
	{
		return getValue( ITEM_DESCRIPTION );
	}

	public void setDescription( String description1 )
	{
		setValue( ITEM_DESCRIPTION, description1 );
	}

}

