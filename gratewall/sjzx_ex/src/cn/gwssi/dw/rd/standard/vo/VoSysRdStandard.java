package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_rd_standard]的数据对象类
 * @author Administrator
 *
 */
public class VoSysRdStandard extends VoBase
{
	private static final long serialVersionUID = 201205020221520006L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_RD_STANDARD_ID = "sys_rd_standard_id" ;	/* 案件序列号 */
	public static final String ITEM_STANDARD_NAME = "standard_name" ;	/* 姓名 */
	public static final String ITEM_STANDARD_ISSUED_UNIT = "standard_issued_unit" ;	/* 企业注册号 */
	public static final String ITEM_STANDARD_CATEGORY_NO = "standard_category_no" ;	/* 当事人类型 */
	public static final String ITEM_STANDARD_ISSUED_TIME = "standard_issued_time" ;	/* 当事人住所 */
	public static final String ITEM_FILE_NAME = "file_name" ;		/* 法定代表人(负责人) */
	public static final String ITEM_FILE_PATH = "file_path" ;		/* 法定代表人(负责人) */
	public static final String ITEM_STANDARD_RANGE = "standard_range" ;	/* 工作单位 */
	public static final String ITEM_STANDARD_CATEGORY = "standard_category" ;	/* 现地址 */
	public static final String ITEM_MEMO = "memo" ;					/* 联系电话 */
	public static final String ITEM_SORT = "sort" ;					/* 籍贯 */
	public static final String ITEM_TIMESTAMP = "timestamp" ;		/* 案件登记编号 */
	
	/**
	 * 构造函数
	 */
	public VoSysRdStandard()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysRdStandard(DataBus value)
	{
		super(value);
	}
	
	/* 案件序列号 : String */
	public String getSys_rd_standard_id()
	{
		return getValue( ITEM_SYS_RD_STANDARD_ID );
	}

	public void setSys_rd_standard_id( String sys_rd_standard_id1 )
	{
		setValue( ITEM_SYS_RD_STANDARD_ID, sys_rd_standard_id1 );
	}

	/* 姓名 : String */
	public String getStandard_name()
	{
		return getValue( ITEM_STANDARD_NAME );
	}

	public void setStandard_name( String standard_name1 )
	{
		setValue( ITEM_STANDARD_NAME, standard_name1 );
	}

	/* 企业注册号 : String */
	public String getStandard_issued_unit()
	{
		return getValue( ITEM_STANDARD_ISSUED_UNIT );
	}

	public void setStandard_issued_unit( String standard_issued_unit1 )
	{
		setValue( ITEM_STANDARD_ISSUED_UNIT, standard_issued_unit1 );
	}

	/* 当事人类型 : String */
	public String getStandard_category_no()
	{
		return getValue( ITEM_STANDARD_CATEGORY_NO );
	}

	public void setStandard_category_no( String standard_category_no1 )
	{
		setValue( ITEM_STANDARD_CATEGORY_NO, standard_category_no1 );
	}

	/* 当事人住所 : String */
	public String getStandard_issued_time()
	{
		return getValue( ITEM_STANDARD_ISSUED_TIME );
	}

	public void setStandard_issued_time( String standard_issued_time1 )
	{
		setValue( ITEM_STANDARD_ISSUED_TIME, standard_issued_time1 );
	}

	/* 法定代表人(负责人) : String */
	public String getFile_name()
	{
		return getValue( ITEM_FILE_NAME );
	}

	public void setFile_name( String file_name1 )
	{
		setValue( ITEM_FILE_NAME, file_name1 );
	}

	/* 法定代表人(负责人) : String */
	public String getFile_path()
	{
		return getValue( ITEM_FILE_PATH );
	}

	public void setFile_path( String file_path1 )
	{
		setValue( ITEM_FILE_PATH, file_path1 );
	}

	/* 工作单位 : String */
	public String getStandard_range()
	{
		return getValue( ITEM_STANDARD_RANGE );
	}

	public void setStandard_range( String standard_range1 )
	{
		setValue( ITEM_STANDARD_RANGE, standard_range1 );
	}

	/* 现地址 : String */
	public String getStandard_category()
	{
		return getValue( ITEM_STANDARD_CATEGORY );
	}

	public void setStandard_category( String standard_category1 )
	{
		setValue( ITEM_STANDARD_CATEGORY, standard_category1 );
	}

	/* 联系电话 : String */
	public String getMemo()
	{
		return getValue( ITEM_MEMO );
	}

	public void setMemo( String memo1 )
	{
		setValue( ITEM_MEMO, memo1 );
	}

	/* 籍贯 : String */
	public String getSort()
	{
		return getValue( ITEM_SORT );
	}

	public void setSort( String sort1 )
	{
		setValue( ITEM_SORT, sort1 );
	}

	/* 案件登记编号 : String */
	public String getTimestamp()
	{
		return getValue( ITEM_TIMESTAMP );
	}

	public void setTimestamp( String timestamp1 )
	{
		setValue( ITEM_TIMESTAMP, timestamp1 );
	}

}

