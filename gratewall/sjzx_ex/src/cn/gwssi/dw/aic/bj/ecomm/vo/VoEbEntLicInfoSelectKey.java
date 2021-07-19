package cn.gwssi.dw.aic.bj.ecomm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[eb_ent_lic_info]�����ݶ�����
 * @author Administrator
 *
 */
public class VoEbEntLicInfoSelectKey extends VoBase
{
	private static final long serialVersionUID = 201209241612200011L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_LIC_NAME = "lic_name" ;			/* ���֤���� */
	public static final String ITEM_REG_BUS_ENT_ID = "reg_bus_ent_id" ;	/* ��ҵID */
	public static final String ITEM_CHR_ID = "chr_id" ;				/* ����ID */
	
	/**
	 * ���캯��
	 */
	public VoEbEntLicInfoSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoEbEntLicInfoSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* ���֤���� : String */
	public String getLic_name()
	{
		return getValue( ITEM_LIC_NAME );
	}

	public void setLic_name( String lic_name1 )
	{
		setValue( ITEM_LIC_NAME, lic_name1 );
	}

	/* ��ҵID : String */
	public String getReg_bus_ent_id()
	{
		return getValue( ITEM_REG_BUS_ENT_ID );
	}

	public void setReg_bus_ent_id( String reg_bus_ent_id1 )
	{
		setValue( ITEM_REG_BUS_ENT_ID, reg_bus_ent_id1 );
	}

	/* ����ID : String */
	public String getChr_id()
	{
		return getValue( ITEM_CHR_ID );
	}

	public void setChr_id( String chr_id1 )
	{
		setValue( ITEM_CHR_ID, chr_id1 );
	}

}

