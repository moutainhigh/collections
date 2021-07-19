package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_rd_standard]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysRdStandardSelectKey extends VoBase
{
	private static final long serialVersionUID = 201205020221520007L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_STANDARD_NAME = "standard_name" ;	/* ���� */
	
	/**
	 * ���캯��
	 */
	public VoSysRdStandardSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysRdStandardSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* ���� : String */
	public String getStandard_name()
	{
		return getValue( ITEM_STANDARD_NAME );
	}

	public void setStandard_name( String standard_name1 )
	{
		setValue( ITEM_STANDARD_NAME, standard_name1 );
	}

}

