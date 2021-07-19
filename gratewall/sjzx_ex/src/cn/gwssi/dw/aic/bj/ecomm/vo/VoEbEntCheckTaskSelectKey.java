package cn.gwssi.dw.aic.bj.ecomm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[eb_ent_check_task]的数据对象类
 * @author Administrator
 *
 */
public class VoEbEntCheckTaskSelectKey extends VoBase
{
	private static final long serialVersionUID = 201209241710040019L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_CHR_ID = "chr_id" ;				/* 检查任务ID */
	public static final String ITEM_ENT_WEB_SITE_ID = "ent_web_site_id" ;	/* 主体网站ID */
	
	/**
	 * 构造函数
	 */
	public VoEbEntCheckTaskSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoEbEntCheckTaskSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 检查任务ID : String */
	public String getChr_id()
	{
		return getValue( ITEM_CHR_ID );
	}

	public void setChr_id( String chr_id1 )
	{
		setValue( ITEM_CHR_ID, chr_id1 );
	}

	/* 主体网站ID : String */
	public String getEnt_web_site_id()
	{
		return getValue( ITEM_ENT_WEB_SITE_ID );
	}

	public void setEnt_web_site_id( String ent_web_site_id1 )
	{
		setValue( ITEM_ENT_WEB_SITE_ID, ent_web_site_id1 );
	}

}

