package cn.gwssi.dw.aic.bj.ecomm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[eb_site_illeg_trail]的数据对象类
 * @author Administrator
 *
 */
public class VoEbSiteIllegTrailPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201209241647280016L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_CHR_ID = "chr_id" ;				/* 主键ID(网站ID) */
	
	/**
	 * 构造函数
	 */
	public VoEbSiteIllegTrailPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoEbSiteIllegTrailPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 主键ID(网站ID) : String */
	public String getChr_id()
	{
		return getValue( ITEM_CHR_ID );
	}

	public void setChr_id( String chr_id1 )
	{
		setValue( ITEM_CHR_ID, chr_id1 );
	}

}

