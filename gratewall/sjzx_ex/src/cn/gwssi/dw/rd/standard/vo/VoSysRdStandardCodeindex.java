package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_rd_standard_codeindex]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysRdStandardCodeindex extends VoBase
{
	private static final long serialVersionUID = 201205031030510006L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_RD_STANDAR_CODEINDEX = "sys_rd_standar_codeindex" ;	/* ���뼯��ʶ�� */
	public static final String ITEM_CODEINDEX_NAME = "codeindex_name" ;	/* ���뼯���� */
	public static final String ITEM_CODEINDEX_CATEGORY = "codeindex_category" ;	/* ���뼯���� */
	public static final String ITEM_REPRESENTATION = "representation" ;	/* ��ʾ */
	public static final String ITEM_STANDARD_CODEINDEX_VERSION = "standard_codeindex_version" ;	/* �汾 */
	public static final String ITEM_CODE_TABLE = "code_table" ;		/* ����� */
	public static final String ITEM_CODING_METHODS = "coding_methods" ;	/* ���뷽�� */
	public static final String ITEM_DESCRIPTION = "description" ;	/* ˵�� */
	
	/**
	 * ���캯��
	 */
	public VoSysRdStandardCodeindex()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysRdStandardCodeindex(DataBus value)
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

	/* ���뼯���� : String */
	public String getCodeindex_name()
	{
		return getValue( ITEM_CODEINDEX_NAME );
	}

	public void setCodeindex_name( String codeindex_name1 )
	{
		setValue( ITEM_CODEINDEX_NAME, codeindex_name1 );
	}

	/* ���뼯���� : String */
	public String getCodeindex_category()
	{
		return getValue( ITEM_CODEINDEX_CATEGORY );
	}

	public void setCodeindex_category( String codeindex_category1 )
	{
		setValue( ITEM_CODEINDEX_CATEGORY, codeindex_category1 );
	}

	/* ��ʾ : String */
	public String getRepresentation()
	{
		return getValue( ITEM_REPRESENTATION );
	}

	public void setRepresentation( String representation1 )
	{
		setValue( ITEM_REPRESENTATION, representation1 );
	}

	/* �汾 : String */
	public String getStandard_codeindex_version()
	{
		return getValue( ITEM_STANDARD_CODEINDEX_VERSION );
	}

	public void setStandard_codeindex_version( String standard_codeindex_version1 )
	{
		setValue( ITEM_STANDARD_CODEINDEX_VERSION, standard_codeindex_version1 );
	}

	/* ����� : String */
	public String getCode_table()
	{
		return getValue( ITEM_CODE_TABLE );
	}

	public void setCode_table( String code_table1 )
	{
		setValue( ITEM_CODE_TABLE, code_table1 );
	}

	/* ���뷽�� : String */
	public String getCoding_methods()
	{
		return getValue( ITEM_CODING_METHODS );
	}

	public void setCoding_methods( String coding_methods1 )
	{
		setValue( ITEM_CODING_METHODS, coding_methods1 );
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

