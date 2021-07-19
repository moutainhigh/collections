package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_rd_standard_term]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysRdStandardTerm extends VoBase
{
	private static final long serialVersionUID = 201205021622440002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_RD_STANDAR_TERM_ID = "sys_rd_standar_term_id" ;	/* ����ID */
	public static final String ITEM_STANDARD_TERM_CN = "standard_term_cn" ;	/* �������� */
	public static final String ITEM_STANDARD_TERM_EN = "standard_term_en" ;	/* Ӣ������ */
	public static final String ITEM_STANDARD_TERM_DEFINITION = "standard_term_definition" ;	/* ���ﶨ�� */
	public static final String ITEM_MEMO = "memo" ;					/* ��ע */
	
	/**
	 * ���캯��
	 */
	public VoSysRdStandardTerm()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysRdStandardTerm(DataBus value)
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

	/* �������� : String */
	public String getStandard_term_cn()
	{
		return getValue( ITEM_STANDARD_TERM_CN );
	}

	public void setStandard_term_cn( String standard_term_cn1 )
	{
		setValue( ITEM_STANDARD_TERM_CN, standard_term_cn1 );
	}

	/* Ӣ������ : String */
	public String getStandard_term_en()
	{
		return getValue( ITEM_STANDARD_TERM_EN );
	}

	public void setStandard_term_en( String standard_term_en1 )
	{
		setValue( ITEM_STANDARD_TERM_EN, standard_term_en1 );
	}

	/* ���ﶨ�� : String */
	public String getStandard_term_definition()
	{
		return getValue( ITEM_STANDARD_TERM_DEFINITION );
	}

	public void setStandard_term_definition( String standard_term_definition1 )
	{
		setValue( ITEM_STANDARD_TERM_DEFINITION, standard_term_definition1 );
	}

	/* ��ע : String */
	public String getMemo()
	{
		return getValue( ITEM_MEMO );
	}

	public void setMemo( String memo1 )
	{
		setValue( ITEM_MEMO, memo1 );
	}

}

