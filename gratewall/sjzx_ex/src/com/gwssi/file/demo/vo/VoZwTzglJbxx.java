package com.gwssi.file.demo.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[zw_tzgl_jbxx]�����ݶ�����
 * @author Administrator
 *
 */
public class VoZwTzglJbxx extends VoBase
{
	private static final long serialVersionUID = 201303271357410002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_JBXX_PK = "jbxx_pk" ;			/* ֪ͨ���-���� */
	public static final String ITEM_TZMC = "tzmc" ;					/* ֪ͨ���� */
	public static final String ITEM_FBRID = "fbrid" ;				/* ������ID */
	public static final String ITEM_FBRMC = "fbrmc" ;				/* ���������� */
	public static final String ITEM_FBKSID = "fbksid" ;				/* �������� */
	public static final String ITEM_FBKSMC = "fbksmc" ;				/* �������� */
	public static final String ITEM_FBSJ = "fbsj" ;					/* ����ʱ�� */
	public static final String ITEM_TZNR = "tznr" ;					/* ֪ͨ���� */
	public static final String ITEM_TZZT = "tzzt" ;					/* ֪ͨ״̬ */
	public static final String ITEM_JSRIDS = "jsrids" ;				/* ������ids */
	public static final String ITEM_JSRMCS = "jsrmcs" ;				/* ���������� */
	public static final String ITEM_FJ_FK = "fj_fk" ;				/* ����id */
	public static final String ITEM_FJMC = "fjmc" ;					/* �������� */
	public static final String ITEM_DELNAMES = "delNAMEs";			/* delNAMEs */
	public static final String ITEM_DELIDS = "delIDs";			/* delIDs */
	
	/**
	 * ���캯��
	 */
	public VoZwTzglJbxx()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoZwTzglJbxx(DataBus value)
	{
		super(value);
	}
	
	/* ֪ͨ���-���� : String */
	public String getJbxx_pk()
	{
		return getValue( ITEM_JBXX_PK );
	}

	public void setJbxx_pk( String jbxx_pk1 )
	{
		setValue( ITEM_JBXX_PK, jbxx_pk1 );
	}

	/* ֪ͨ���� : String */
	public String getTzmc()
	{
		return getValue( ITEM_TZMC );
	}

	public void setTzmc( String tzmc1 )
	{
		setValue( ITEM_TZMC, tzmc1 );
	}

	/* ������ID : String */
	public String getFbrid()
	{
		return getValue( ITEM_FBRID );
	}

	public void setFbrid( String fbrid1 )
	{
		setValue( ITEM_FBRID, fbrid1 );
	}

	/* ���������� : String */
	public String getFbrmc()
	{
		return getValue( ITEM_FBRMC );
	}

	public void setFbrmc( String fbrmc1 )
	{
		setValue( ITEM_FBRMC, fbrmc1 );
	}

	/* �������� : String */
	public String getFbksid()
	{
		return getValue( ITEM_FBKSID );
	}

	public void setFbksid( String fbksid1 )
	{
		setValue( ITEM_FBKSID, fbksid1 );
	}

	/* �������� : String */
	public String getFbksmc()
	{
		return getValue( ITEM_FBKSMC );
	}

	public void setFbksmc( String fbksmc1 )
	{
		setValue( ITEM_FBKSMC, fbksmc1 );
	}

	/* ����ʱ�� : String */
	public String getFbsj()
	{
		return getValue( ITEM_FBSJ );
	}

	public void setFbsj( String fbsj1 )
	{
		setValue( ITEM_FBSJ, fbsj1 );
	}

	/* ֪ͨ���� : String */
	public String getTznr()
	{
		return getValue( ITEM_TZNR );
	}

	public void setTznr( String tznr1 )
	{
		setValue( ITEM_TZNR, tznr1 );
	}

	/* ֪ͨ״̬ : String */
	public String getTzzt()
	{
		return getValue( ITEM_TZZT );
	}

	public void setTzzt( String tzzt1 )
	{
		setValue( ITEM_TZZT, tzzt1 );
	}

	/* ������ids : String */
	public String getJsrids()
	{
		return getValue( ITEM_JSRIDS );
	}

	public void setJsrids( String jsrids1 )
	{
		setValue( ITEM_JSRIDS, jsrids1 );
	}

	/* ���������� : String */
	public String getJsrmcs()
	{
		return getValue( ITEM_JSRMCS );
	}

	public void setJsrmcs( String jsrmcs1 )
	{
		setValue( ITEM_JSRMCS, jsrmcs1 );
	}

	/* ����id : String */
	public String getFj_fk()
	{
		return getValue( ITEM_FJ_FK );
	}

	public void setFj_fk( String fj_fk1 )
	{
		setValue( ITEM_FJ_FK, fj_fk1 );
	}

	/* �������� : String */
	public String getFjmc()
	{
		return getValue( ITEM_FJMC );
	}

	public void setFjmc( String fjmc1 )
	{
		setValue( ITEM_FJMC, fjmc1 );
	}

}

