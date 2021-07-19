package com.gwssi.file.manage.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[xt_ccgl_wjlb]的数据对象类
 * @author Administrator
 *
 */
public class VoXtCcglWjlb extends VoBase
{
	private static final long serialVersionUID = 201303271601110002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_CCLBBH_PK = "cclbbh_pk" ;		/* 文件类别编号 */
	public static final String ITEM_CCLBMC = "cclbmc" ;				/* 处处类别名称 */
	public static final String ITEM_LBMCBB = "lbmcbb" ;				/* 类别名称版本 */
	public static final String ITEM_CCGML = "ccgml" ;				/* 存储根目录 */
	public static final String ITEM_EJMLGZ = "ejmlgz" ;				/* 二级目录规则 */
	public static final String ITEM_GZFZZD = "gzfzzd" ;				/* 规则辅助字段 */
	public static final String ITEM_ZT = "zt" ;						/* 状态 */
	public static final String ITEM_BZ = "bz" ;						/* 备注 */
	
	/**
	 * 构造函数
	 */
	public VoXtCcglWjlb()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoXtCcglWjlb(DataBus value)
	{
		super(value);
	}
	
	/* 文件类别编号 : String */
	public String getCclbbh_pk()
	{
		return getValue( ITEM_CCLBBH_PK );
	}

	public void setCclbbh_pk( String cclbbh_pk1 )
	{
		setValue( ITEM_CCLBBH_PK, cclbbh_pk1 );
	}

	/* 处处类别名称 : String */
	public String getCclbmc()
	{
		return getValue( ITEM_CCLBMC );
	}

	public void setCclbmc( String cclbmc1 )
	{
		setValue( ITEM_CCLBMC, cclbmc1 );
	}

	/* 类别名称版本 : String */
	public String getLbmcbb()
	{
		return getValue( ITEM_LBMCBB );
	}

	public void setLbmcbb( String lbmcbb1 )
	{
		setValue( ITEM_LBMCBB, lbmcbb1 );
	}

	/* 存储根目录 : String */
	public String getCcgml()
	{
		return getValue( ITEM_CCGML );
	}

	public void setCcgml( String ccgml1 )
	{
		setValue( ITEM_CCGML, ccgml1 );
	}

	/* 二级目录规则 : String */
	public String getEjmlgz()
	{
		return getValue( ITEM_EJMLGZ );
	}

	public void setEjmlgz( String ejmlgz1 )
	{
		setValue( ITEM_EJMLGZ, ejmlgz1 );
	}

	/* 规则辅助字段 : String */
	public String getGzfzzd()
	{
		return getValue( ITEM_GZFZZD );
	}

	public void setGzfzzd( String gzfzzd1 )
	{
		setValue( ITEM_GZFZZD, gzfzzd1 );
	}

	/* 状态 : String */
	public String getZt()
	{
		return getValue( ITEM_ZT );
	}

	public void setZt( String zt1 )
	{
		setValue( ITEM_ZT, zt1 );
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

}

