package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_rd_standard_term]的数据对象类
 * @author Administrator
 *
 */
public class VoSysRdStandardTerm extends VoBase
{
	private static final long serialVersionUID = 201205021622440002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_RD_STANDAR_TERM_ID = "sys_rd_standar_term_id" ;	/* 术语ID */
	public static final String ITEM_STANDARD_TERM_CN = "standard_term_cn" ;	/* 术语名称 */
	public static final String ITEM_STANDARD_TERM_EN = "standard_term_en" ;	/* 英文名称 */
	public static final String ITEM_STANDARD_TERM_DEFINITION = "standard_term_definition" ;	/* 术语定义 */
	public static final String ITEM_MEMO = "memo" ;					/* 备注 */
	
	/**
	 * 构造函数
	 */
	public VoSysRdStandardTerm()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysRdStandardTerm(DataBus value)
	{
		super(value);
	}
	
	/* 术语ID : String */
	public String getSys_rd_standar_term_id()
	{
		return getValue( ITEM_SYS_RD_STANDAR_TERM_ID );
	}

	public void setSys_rd_standar_term_id( String sys_rd_standar_term_id1 )
	{
		setValue( ITEM_SYS_RD_STANDAR_TERM_ID, sys_rd_standar_term_id1 );
	}

	/* 术语名称 : String */
	public String getStandard_term_cn()
	{
		return getValue( ITEM_STANDARD_TERM_CN );
	}

	public void setStandard_term_cn( String standard_term_cn1 )
	{
		setValue( ITEM_STANDARD_TERM_CN, standard_term_cn1 );
	}

	/* 英文名称 : String */
	public String getStandard_term_en()
	{
		return getValue( ITEM_STANDARD_TERM_EN );
	}

	public void setStandard_term_en( String standard_term_en1 )
	{
		setValue( ITEM_STANDARD_TERM_EN, standard_term_en1 );
	}

	/* 术语定义 : String */
	public String getStandard_term_definition()
	{
		return getValue( ITEM_STANDARD_TERM_DEFINITION );
	}

	public void setStandard_term_definition( String standard_term_definition1 )
	{
		setValue( ITEM_STANDARD_TERM_DEFINITION, standard_term_definition1 );
	}

	/* 备注 : String */
	public String getMemo()
	{
		return getValue( ITEM_MEMO );
	}

	public void setMemo( String memo1 )
	{
		setValue( ITEM_MEMO, memo1 );
	}

}

