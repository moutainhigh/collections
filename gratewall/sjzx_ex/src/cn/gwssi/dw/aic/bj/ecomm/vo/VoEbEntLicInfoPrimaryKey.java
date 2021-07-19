package cn.gwssi.dw.aic.bj.ecomm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[eb_ent_lic_info]的数据对象类
 * @author Administrator
 *
 */
public class VoEbEntLicInfoPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201209241612200012L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_CHR_ID = "chr_id" ;				/* 主键ID */
	
	/**
	 * 构造函数
	 */
	public VoEbEntLicInfoPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoEbEntLicInfoPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 主键ID : String */
	public String getChr_id()
	{
		return getValue( ITEM_CHR_ID );
	}

	public void setChr_id( String chr_id1 )
	{
		setValue( ITEM_CHR_ID, chr_id1 );
	}

}

