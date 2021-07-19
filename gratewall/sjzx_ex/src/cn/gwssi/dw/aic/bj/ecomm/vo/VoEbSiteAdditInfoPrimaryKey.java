package cn.gwssi.dw.aic.bj.ecomm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[eb_site_addit_info]�����ݶ�����
 * @author Administrator
 *
 */
public class VoEbSiteAdditInfoPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201209241538470004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_REG_BUS_ENT_ID = "reg_bus_ent_id" ;	/* ��ҵ����ID */
	
	/**
	 * ���캯��
	 */
	public VoEbSiteAdditInfoPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoEbSiteAdditInfoPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ��ҵ����ID : String */
	public String getReg_bus_ent_id()
	{
		return getValue( ITEM_REG_BUS_ENT_ID );
	}

	public void setReg_bus_ent_id( String reg_bus_ent_id1 )
	{
		setValue( ITEM_REG_BUS_ENT_ID, reg_bus_ent_id1 );
	}

}

