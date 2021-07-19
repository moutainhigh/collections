package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_rd_standard_codeindex]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysRdStandardCodeindexSelectKey extends VoBase
{
	private static final long serialVersionUID = 201205031030510007L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_CODEINDEX_NAME = "codeindex_name" ;	/* ���뼯���� */
	
	/**
	 * ���캯��
	 */
	public VoSysRdStandardCodeindexSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysRdStandardCodeindexSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* ���뼯���� : String */
	public String getCodeindex_name()
	{
		return getValue( ITEM_CODEINDEX_NAME );
	}

	public void setCodeindex_name( String codeindex_name1 )
	{
		setValue( ITEM_CODEINDEX_NAME, codeindex_name1 );
	}

}

