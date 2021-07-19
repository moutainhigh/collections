package cn.gwssi.dw.aic.bj.ecomm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[eb_site_illeg_trail]�����ݶ�����
 * @author Administrator
 *
 */
public class VoEbSiteIllegTrailSelectKey extends VoBase
{
	private static final long serialVersionUID = 201209241647280015L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_DOM_SUBST = "dom_subst" ;		/* ��Ͻ�־� */
	public static final String ITEM_CHR_ID = "chr_id" ;				/* ����ID(��վID) */
	public static final String ITEM_REG_BUS_ENT_ID = "reg_bus_ent_id" ;	/* ����id */
	
	/**
	 * ���캯��
	 */
	public VoEbSiteIllegTrailSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoEbSiteIllegTrailSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* ��Ͻ�־� : String */
	public String getDom_subst()
	{
		return getValue( ITEM_DOM_SUBST );
	}

	public void setDom_subst( String dom_subst1 )
	{
		setValue( ITEM_DOM_SUBST, dom_subst1 );
	}

	/* ����ID(��վID) : String */
	public String getChr_id()
	{
		return getValue( ITEM_CHR_ID );
	}

	public void setChr_id( String chr_id1 )
	{
		setValue( ITEM_CHR_ID, chr_id1 );
	}

	/* ����id : String */
	public String getReg_bus_ent_id()
	{
		return getValue( ITEM_REG_BUS_ENT_ID );
	}

	public void setReg_bus_ent_id( String reg_bus_ent_id1 )
	{
		setValue( ITEM_REG_BUS_ENT_ID, reg_bus_ent_id1 );
	}

}

