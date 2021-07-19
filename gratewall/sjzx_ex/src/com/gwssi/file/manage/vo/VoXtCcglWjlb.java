package com.gwssi.file.manage.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[xt_ccgl_wjlb]�����ݶ�����
 * @author Administrator
 *
 */
public class VoXtCcglWjlb extends VoBase
{
	private static final long serialVersionUID = 201303271601110002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_CCLBBH_PK = "cclbbh_pk" ;		/* �ļ������ */
	public static final String ITEM_CCLBMC = "cclbmc" ;				/* ����������� */
	public static final String ITEM_LBMCBB = "lbmcbb" ;				/* ������ư汾 */
	public static final String ITEM_CCGML = "ccgml" ;				/* �洢��Ŀ¼ */
	public static final String ITEM_EJMLGZ = "ejmlgz" ;				/* ����Ŀ¼���� */
	public static final String ITEM_GZFZZD = "gzfzzd" ;				/* �������ֶ� */
	public static final String ITEM_ZT = "zt" ;						/* ״̬ */
	public static final String ITEM_BZ = "bz" ;						/* ��ע */
	
	/**
	 * ���캯��
	 */
	public VoXtCcglWjlb()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoXtCcglWjlb(DataBus value)
	{
		super(value);
	}
	
	/* �ļ������ : String */
	public String getCclbbh_pk()
	{
		return getValue( ITEM_CCLBBH_PK );
	}

	public void setCclbbh_pk( String cclbbh_pk1 )
	{
		setValue( ITEM_CCLBBH_PK, cclbbh_pk1 );
	}

	/* ����������� : String */
	public String getCclbmc()
	{
		return getValue( ITEM_CCLBMC );
	}

	public void setCclbmc( String cclbmc1 )
	{
		setValue( ITEM_CCLBMC, cclbmc1 );
	}

	/* ������ư汾 : String */
	public String getLbmcbb()
	{
		return getValue( ITEM_LBMCBB );
	}

	public void setLbmcbb( String lbmcbb1 )
	{
		setValue( ITEM_LBMCBB, lbmcbb1 );
	}

	/* �洢��Ŀ¼ : String */
	public String getCcgml()
	{
		return getValue( ITEM_CCGML );
	}

	public void setCcgml( String ccgml1 )
	{
		setValue( ITEM_CCGML, ccgml1 );
	}

	/* ����Ŀ¼���� : String */
	public String getEjmlgz()
	{
		return getValue( ITEM_EJMLGZ );
	}

	public void setEjmlgz( String ejmlgz1 )
	{
		setValue( ITEM_EJMLGZ, ejmlgz1 );
	}

	/* �������ֶ� : String */
	public String getGzfzzd()
	{
		return getValue( ITEM_GZFZZD );
	}

	public void setGzfzzd( String gzfzzd1 )
	{
		setValue( ITEM_GZFZZD, gzfzzd1 );
	}

	/* ״̬ : String */
	public String getZt()
	{
		return getValue( ITEM_ZT );
	}

	public void setZt( String zt1 )
	{
		setValue( ITEM_ZT, zt1 );
	}

	/* ��ע : String */
	public String getBz()
	{
		return getValue( ITEM_BZ );
	}

	public void setBz( String bz1 )
	{
		setValue( ITEM_BZ, bz1 );
	}

}

