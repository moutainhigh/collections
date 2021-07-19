package cn.gwssi.dw.aic.bj.ecomm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[eb_site_relat_type]的数据对象类
 * @author Administrator
 *
 */
public class VoEbSiteRelatTypePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201209241557420008L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_CHR_ID = "chr_id" ;				/* 主键id */
	
	/**
	 * 构造函数
	 */
	public VoEbSiteRelatTypePrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoEbSiteRelatTypePrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 主键id : String */
	public String getChr_id()
	{
		return getValue( ITEM_CHR_ID );
	}

	public void setChr_id( String chr_id1 )
	{
		setValue( ITEM_CHR_ID, chr_id1 );
	}

}

