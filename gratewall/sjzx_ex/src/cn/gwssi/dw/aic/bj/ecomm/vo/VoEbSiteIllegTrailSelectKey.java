package cn.gwssi.dw.aic.bj.ecomm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[eb_site_illeg_trail]的数据对象类
 * @author Administrator
 *
 */
public class VoEbSiteIllegTrailSelectKey extends VoBase
{
	private static final long serialVersionUID = 201209241647280015L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_DOM_SUBST = "dom_subst" ;		/* 管辖分局 */
	public static final String ITEM_CHR_ID = "chr_id" ;				/* 主键ID(网站ID) */
	public static final String ITEM_REG_BUS_ENT_ID = "reg_bus_ent_id" ;	/* 主体id */
	
	/**
	 * 构造函数
	 */
	public VoEbSiteIllegTrailSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoEbSiteIllegTrailSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 管辖分局 : String */
	public String getDom_subst()
	{
		return getValue( ITEM_DOM_SUBST );
	}

	public void setDom_subst( String dom_subst1 )
	{
		setValue( ITEM_DOM_SUBST, dom_subst1 );
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

	/* 主体id : String */
	public String getReg_bus_ent_id()
	{
		return getValue( ITEM_REG_BUS_ENT_ID );
	}

	public void setReg_bus_ent_id( String reg_bus_ent_id1 )
	{
		setValue( ITEM_REG_BUS_ENT_ID, reg_bus_ent_id1 );
	}

}

