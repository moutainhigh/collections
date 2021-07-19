package cn.gwssi.dw.rd.log.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[view_sys_app_log_query]�����ݶ�����
 * @author Administrator
 *
 */
public class VoViewSysAppLogQuery extends VoBase
{
	private static final long serialVersionUID = 201208010743380002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_USERNAME = "username" ;			/* �û����� */
	public static final String ITEM_QUERY_TIME = "query_time" ;		/* ��ѯʱ�� */
	public static final String ITEM_ORGID = "orgid" ;				/* ����id */
	public static final String ITEM_IPADDRESS = "ipaddress" ;		/* ip��ַ */
	public static final String ITEM_TYPE = "type" ;					/* ���� */
	
	/**
	 * ���캯��
	 */
	public VoViewSysAppLogQuery()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoViewSysAppLogQuery(DataBus value)
	{
		super(value);
	}
	
	/* �û����� : String */
	public String getUsername()
	{
		return getValue( ITEM_USERNAME );
	}

	public void setUsername( String username1 )
	{
		setValue( ITEM_USERNAME, username1 );
	}

	/* ��ѯʱ�� : String */
	public String getQuery_time()
	{
		return getValue( ITEM_QUERY_TIME );
	}

	public void setQuery_time( String query_time1 )
	{
		setValue( ITEM_QUERY_TIME, query_time1 );
	}

	/* ����id : String */
	public String getOrgid()
	{
		return getValue( ITEM_ORGID );
	}

	public void setOrgid( String orgid1 )
	{
		setValue( ITEM_ORGID, orgid1 );
	}

	/* ip��ַ : String */
	public String getIpaddress()
	{
		return getValue( ITEM_IPADDRESS );
	}

	public void setIpaddress( String ipaddress1 )
	{
		setValue( ITEM_IPADDRESS, ipaddress1 );
	}

	/* ���� : String */
	public String getType()
	{
		return getValue( ITEM_TYPE );
	}

	public void setType( String type1 )
	{
		setValue( ITEM_TYPE, type1 );
	}

}

