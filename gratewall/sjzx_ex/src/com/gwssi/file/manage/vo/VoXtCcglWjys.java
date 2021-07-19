package com.gwssi.file.manage.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[xt_ccgl_wjys]的数据对象类
 * @author Administrator
 *
 */
public class VoXtCcglWjys extends VoBase
{
	private static final long serialVersionUID = 201303271612500006L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_YSBH_PK = "ysbh_pk" ;			/* 映射编号 */
	public static final String ITEM_CCLBBH_PK = "cclbbh_pk" ;		/* 类别编号ID */
	public static final String ITEM_WYBS = "wybs" ;					/* 唯一标识 */
	public static final String ITEM_WJMC = "wjmc" ;					/* 文件名称 */
	public static final String ITEM_WJZT = "wjzt" ;					/* 文件状态 */
	public static final String ITEM_CCLJ = "cclj" ;					/* 存储路径 */
	public static final String ITEM_CJSJ = "cjsj" ;					/* 创建时间 */
	public static final String ITEM_SCXGSJ = "scxgsj" ;				/* 上次修改时间 */
	public static final String ITEM_BZ = "bz" ;						/* 备注 */
	public static final String ITEM_YWBZ = "ywbz" ;					/* 业务备注 */
	public static final String ITEM_XM_FK = "xm_fk" ;				/* 项目fk */
	
	/**
	 * 构造函数
	 */
	public VoXtCcglWjys()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoXtCcglWjys(DataBus value)
	{
		super(value);
	}
	
	/* 映射编号 : String */
	public String getYsbh_pk()
	{
		return getValue( ITEM_YSBH_PK );
	}

	public void setYsbh_pk( String ysbh_pk1 )
	{
		setValue( ITEM_YSBH_PK, ysbh_pk1 );
	}

	/* 类别编号ID : String */
	public String getCclbbh_pk()
	{
		return getValue( ITEM_CCLBBH_PK );
	}

	public void setCclbbh_pk( String cclbbh_pk1 )
	{
		setValue( ITEM_CCLBBH_PK, cclbbh_pk1 );
	}

	/* 唯一标识 : String */
	public String getWybs()
	{
		return getValue( ITEM_WYBS );
	}

	public void setWybs( String wybs1 )
	{
		setValue( ITEM_WYBS, wybs1 );
	}

	/* 文件名称 : String */
	public String getWjmc()
	{
		return getValue( ITEM_WJMC );
	}

	public void setWjmc( String wjmc1 )
	{
		setValue( ITEM_WJMC, wjmc1 );
	}

	/* 文件状态 : String */
	public String getWjzt()
	{
		return getValue( ITEM_WJZT );
	}

	public void setWjzt( String wjzt1 )
	{
		setValue( ITEM_WJZT, wjzt1 );
	}

	/* 存储路径 : String */
	public String getCclj()
	{
		return getValue( ITEM_CCLJ );
	}

	public void setCclj( String cclj1 )
	{
		setValue( ITEM_CCLJ, cclj1 );
	}

	/* 创建时间 : String */
	public String getCjsj()
	{
		return getValue( ITEM_CJSJ );
	}

	public void setCjsj( String cjsj1 )
	{
		setValue( ITEM_CJSJ, cjsj1 );
	}

	/* 上次修改时间 : String */
	public String getScxgsj()
	{
		return getValue( ITEM_SCXGSJ );
	}

	public void setScxgsj( String scxgsj1 )
	{
		setValue( ITEM_SCXGSJ, scxgsj1 );
	}

	/* 备注 : String */
	public String getBz()
	{
		return getValue( ITEM_BZ );
	}

	public void setBz( String bz1 )
	{
		setValue( ITEM_BZ, bz1 );
	}

	/* 业务备注 : String */
	public String getYwbz()
	{
		return getValue( ITEM_YWBZ );
	}

	public void setYwbz( String ywbz1 )
	{
		setValue( ITEM_YWBZ, ywbz1 );
	}

	/* 项目fk : String */
	public String getXm_fk()
	{
		return getValue( ITEM_XM_FK );
	}

	public void setXm_fk( String xm_fk1 )
	{
		setValue( ITEM_XM_FK, xm_fk1 );
	}

}

