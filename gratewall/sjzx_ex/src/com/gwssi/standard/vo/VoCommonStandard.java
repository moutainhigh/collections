package com.gwssi.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[common_standard]的数据对象类
 * @author Administrator
 *
 */
public class VoCommonStandard extends VoBase
{
	private static final long serialVersionUID = 201304121530000002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_STANDARD_ID = "standard_id" ;	/* 标准ID */
	public static final String ITEM_STANDARD_NAME = "standard_name" ;	/* 标准名称 */
	public static final String ITEM_STANDARD_TYPE = "standard_type" ;	/* 标准类型 */
	public static final String ITEM_SPECIFICATE_TYPE = "specificate_type" ;	/* 规则类型 */
	public static final String ITEM_ISSUING_UNIT = "issuing_unit" ;	/* 发布单位 */
	public static final String ITEM_SPECIFICATE_NO = "specificate_no" ;	/* 类型号 */
	public static final String ITEM_SPECIFICATE_DESC = "specificate_desc" ;	/* 类型描述 */
	public static final String ITEM_SPECIFICATE_STATUS = "specificate_status" ;	/* 类型状态 */
	public static final String ITEM_IS_MARKUP = "is_markup" ;		/* 代码集 无效 有效 */
	public static final String ITEM_CREATOR_ID = "creator_id" ;		/* 创建人ID */
	public static final String ITEM_CREATED_TIME = "created_time" ;	/* 创建时间 */
	public static final String ITEM_LAST_MODIFY_ID = "last_modify_id" ;	/* 最后修改人ID */
	public static final String ITEM_LAST_MODIFY_TIME = "last_modify_time" ;	/* 最后修改时间 */
	public static final String ITEM_FJ_FK = "fj_fk" ;				/* 附件id */
	public static final String ITEM_DELNAMES = "delNAMEs";			/* delNAMEs */
	public static final String ITEM_DELIDS = "delIDs";			/* delIDs */
	public static final String ITEM_FJMC = "fjmc" ;					/* 附件名称 */
	/**
	 * 构造函数
	 */
	public VoCommonStandard()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoCommonStandard(DataBus value)
	{
		super(value);
	}
	
	/* 标准ID : String */
	public String getStandard_id()
	{
		return getValue( ITEM_STANDARD_ID );
	}

	public void setStandard_id( String standard_id1 )
	{
		setValue( ITEM_STANDARD_ID, standard_id1 );
	}

	/* 标准名称 : String */
	public String getStandard_name()
	{
		return getValue( ITEM_STANDARD_NAME );
	}

	public void setStandard_name( String standard_name1 )
	{
		setValue( ITEM_STANDARD_NAME, standard_name1 );
	}

	/* 标准类型 : String */
	public String getStandard_type()
	{
		return getValue( ITEM_STANDARD_TYPE );
	}

	public void setStandard_type( String standard_type1 )
	{
		setValue( ITEM_STANDARD_TYPE, standard_type1 );
	}

	/* 规则类型 : String */
	public String getSpecificate_type()
	{
		return getValue( ITEM_SPECIFICATE_TYPE );
	}

	public void setSpecificate_type( String specificate_type1 )
	{
		setValue( ITEM_SPECIFICATE_TYPE, specificate_type1 );
	}

	/* 发布单位 : String */
	public String getIssuing_unit()
	{
		return getValue( ITEM_ISSUING_UNIT );
	}

	public void setIssuing_unit( String issuing_unit1 )
	{
		setValue( ITEM_ISSUING_UNIT, issuing_unit1 );
	}

	/* 类型号 : String */
	public String getSpecificate_no()
	{
		return getValue( ITEM_SPECIFICATE_NO );
	}

	public void setSpecificate_no( String specificate_no1 )
	{
		setValue( ITEM_SPECIFICATE_NO, specificate_no1 );
	}

	/* 类型描述 : String */
	public String getSpecificate_desc()
	{
		return getValue( ITEM_SPECIFICATE_DESC );
	}

	public void setSpecificate_desc( String specificate_desc1 )
	{
		setValue( ITEM_SPECIFICATE_DESC, specificate_desc1 );
	}

	/* 类型状态 : String */
	public String getSpecificate_status()
	{
		return getValue( ITEM_SPECIFICATE_STATUS );
	}

	public void setSpecificate_status( String specificate_status1 )
	{
		setValue( ITEM_SPECIFICATE_STATUS, specificate_status1 );
	}

	/* 代码集 无效 有效 : String */
	public String getIs_markup()
	{
		return getValue( ITEM_IS_MARKUP );
	}

	public void setIs_markup( String is_markup1 )
	{
		setValue( ITEM_IS_MARKUP, is_markup1 );
	}

	/* 创建人ID : String */
	public String getCreator_id()
	{
		return getValue( ITEM_CREATOR_ID );
	}

	public void setCreator_id( String creator_id1 )
	{
		setValue( ITEM_CREATOR_ID, creator_id1 );
	}

	/* 创建时间 : String */
	public String getCreated_time()
	{
		return getValue( ITEM_CREATED_TIME );
	}

	public void setCreated_time( String created_time1 )
	{
		setValue( ITEM_CREATED_TIME, created_time1 );
	}

	/* 最后修改人ID : String */
	public String getLast_modify_id()
	{
		return getValue( ITEM_LAST_MODIFY_ID );
	}

	public void setLast_modify_id( String last_modify_id1 )
	{
		setValue( ITEM_LAST_MODIFY_ID, last_modify_id1 );
	}

	/* 最后修改时间 : String */
	public String getLast_modify_time()
	{
		return getValue( ITEM_LAST_MODIFY_TIME );
	}

	public void setLast_modify_time( String last_modify_time1 )
	{
		setValue( ITEM_LAST_MODIFY_TIME, last_modify_time1 );
	}
	/* 附件id : String */
	public String getFj_fk()
	{
		return getValue( ITEM_FJ_FK );
	}

	public void setFj_fk( String fj_fk1 )
	{
		setValue( ITEM_FJ_FK, fj_fk1 );
	}

	/* 附件名称 : String */
	public String getFjmc()
	{
		return getValue( ITEM_FJMC );
	}

	public void setFjmc( String fjmc1 )
	{
		setValue( ITEM_FJMC, fjmc1 );
	}

}

