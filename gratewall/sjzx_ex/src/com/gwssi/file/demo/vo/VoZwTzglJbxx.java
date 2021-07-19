package com.gwssi.file.demo.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[zw_tzgl_jbxx]的数据对象类
 * @author Administrator
 *
 */
public class VoZwTzglJbxx extends VoBase
{
	private static final long serialVersionUID = 201303271357410002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_JBXX_PK = "jbxx_pk" ;			/* 通知编号-主键 */
	public static final String ITEM_TZMC = "tzmc" ;					/* 通知名称 */
	public static final String ITEM_FBRID = "fbrid" ;				/* 发布人ID */
	public static final String ITEM_FBRMC = "fbrmc" ;				/* 发布人名称 */
	public static final String ITEM_FBKSID = "fbksid" ;				/* 发布科室 */
	public static final String ITEM_FBKSMC = "fbksmc" ;				/* 发布名称 */
	public static final String ITEM_FBSJ = "fbsj" ;					/* 发布时间 */
	public static final String ITEM_TZNR = "tznr" ;					/* 通知内容 */
	public static final String ITEM_TZZT = "tzzt" ;					/* 通知状态 */
	public static final String ITEM_JSRIDS = "jsrids" ;				/* 接收人ids */
	public static final String ITEM_JSRMCS = "jsrmcs" ;				/* 接收人名称 */
	public static final String ITEM_FJ_FK = "fj_fk" ;				/* 附件id */
	public static final String ITEM_FJMC = "fjmc" ;					/* 附件名称 */
	public static final String ITEM_DELNAMES = "delNAMEs";			/* delNAMEs */
	public static final String ITEM_DELIDS = "delIDs";			/* delIDs */
	
	/**
	 * 构造函数
	 */
	public VoZwTzglJbxx()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoZwTzglJbxx(DataBus value)
	{
		super(value);
	}
	
	/* 通知编号-主键 : String */
	public String getJbxx_pk()
	{
		return getValue( ITEM_JBXX_PK );
	}

	public void setJbxx_pk( String jbxx_pk1 )
	{
		setValue( ITEM_JBXX_PK, jbxx_pk1 );
	}

	/* 通知名称 : String */
	public String getTzmc()
	{
		return getValue( ITEM_TZMC );
	}

	public void setTzmc( String tzmc1 )
	{
		setValue( ITEM_TZMC, tzmc1 );
	}

	/* 发布人ID : String */
	public String getFbrid()
	{
		return getValue( ITEM_FBRID );
	}

	public void setFbrid( String fbrid1 )
	{
		setValue( ITEM_FBRID, fbrid1 );
	}

	/* 发布人名称 : String */
	public String getFbrmc()
	{
		return getValue( ITEM_FBRMC );
	}

	public void setFbrmc( String fbrmc1 )
	{
		setValue( ITEM_FBRMC, fbrmc1 );
	}

	/* 发布科室 : String */
	public String getFbksid()
	{
		return getValue( ITEM_FBKSID );
	}

	public void setFbksid( String fbksid1 )
	{
		setValue( ITEM_FBKSID, fbksid1 );
	}

	/* 发布名称 : String */
	public String getFbksmc()
	{
		return getValue( ITEM_FBKSMC );
	}

	public void setFbksmc( String fbksmc1 )
	{
		setValue( ITEM_FBKSMC, fbksmc1 );
	}

	/* 发布时间 : String */
	public String getFbsj()
	{
		return getValue( ITEM_FBSJ );
	}

	public void setFbsj( String fbsj1 )
	{
		setValue( ITEM_FBSJ, fbsj1 );
	}

	/* 通知内容 : String */
	public String getTznr()
	{
		return getValue( ITEM_TZNR );
	}

	public void setTznr( String tznr1 )
	{
		setValue( ITEM_TZNR, tznr1 );
	}

	/* 通知状态 : String */
	public String getTzzt()
	{
		return getValue( ITEM_TZZT );
	}

	public void setTzzt( String tzzt1 )
	{
		setValue( ITEM_TZZT, tzzt1 );
	}

	/* 接收人ids : String */
	public String getJsrids()
	{
		return getValue( ITEM_JSRIDS );
	}

	public void setJsrids( String jsrids1 )
	{
		setValue( ITEM_JSRIDS, jsrids1 );
	}

	/* 接收人名称 : String */
	public String getJsrmcs()
	{
		return getValue( ITEM_JSRMCS );
	}

	public void setJsrmcs( String jsrmcs1 )
	{
		setValue( ITEM_JSRMCS, jsrmcs1 );
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

