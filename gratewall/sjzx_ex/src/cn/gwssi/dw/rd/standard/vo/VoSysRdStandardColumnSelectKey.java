package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_rd_standard_column]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysRdStandardColumnSelectKey extends VoBase
{
	private static final long serialVersionUID = 201205031749450007L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_CN_NAME = "cn_name" ;			/* ָ�������� */
	public static final String ITEM_COLUMN_NAME = "column_name" ;	/* �ֶ����� */
	
	/**
	 * ���캯��
	 */
	public VoSysRdStandardColumnSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysRdStandardColumnSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* ָ�������� : String */
	public String getCn_name()
	{
		return getValue( ITEM_CN_NAME );
	}

	public void setCn_name( String cn_name1 )
	{
		setValue( ITEM_CN_NAME, cn_name1 );
	}

	/* �ֶ����� : String */
	public String getColumn_name()
	{
		return getValue( ITEM_COLUMN_NAME );
	}

	public void setColumn_name( String column_name1 )
	{
		setValue( ITEM_COLUMN_NAME, column_name1 );
	}

}

