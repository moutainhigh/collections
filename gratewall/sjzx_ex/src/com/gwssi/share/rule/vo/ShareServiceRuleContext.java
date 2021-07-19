package com.gwssi.share.rule.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.txn.Constants;

/**
 * ���ݱ�[share_service_rule]�����ݶ�����
 * @author Administrator
 *
 */
public class ShareServiceRuleContext extends TxnContext
{
	private static final long serialVersionUID = 201304081757160001L;
	
	/**
	 * ���캯��
	 */
	public ShareServiceRuleContext()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public ShareServiceRuleContext(TxnContext value)
	{
		super(value);
	}
	
	/**
	 * ȡ����
	 * @return
	 */
	public VoShareServiceRulePrimaryKey getPrimaryKey()
	{
		return getPrimaryKey( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * ȡ����
	 * @param nodeName ���������Ľڵ�����
	 * @return
	 */
	public VoShareServiceRulePrimaryKey getPrimaryKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoShareServiceRulePrimaryKey( data );
	}
	
	/**
	 * ȡ����
	 * @return
	 */
	public VoShareServiceRulePrimaryKey[] getPrimaryKeys()
	{
		return getPrimaryKeys( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * ȡ����
	 * @param nodeName ���������Ľڵ�����
	 * @return
	 */
	public VoShareServiceRulePrimaryKey[] getPrimaryKeys( String nodeName )
	{
		try{
			Recordset data = getRecordset( nodeName );
			VoShareServiceRulePrimaryKey keys[] = new VoShareServiceRulePrimaryKey[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoShareServiceRulePrimaryKey( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoShareServiceRulePrimaryKey[0];
		}
	}
	
	/**
	 * ȡ��ѯ����
	 * @return
	 */
	public VoShareServiceRuleSelectKey getSelectKey()
	{
		return getSelectKey( Constants.NODE_SELECTKEY );
	}
	
	/**
	 * ȡ����
	 * @param nodeName ���������Ľڵ�����
	 * @return
	 */
	public VoShareServiceRuleSelectKey getSelectKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoShareServiceRuleSelectKey( data );
	}
	
	/**
	 * ȡ��¼��
	 * @param nodeName
	 * @return
	 */
	public VoShareServiceRule getShareServiceRule( String nodeName )
	{
		if( nodeName == null ){
			nodeName = Constants.NODE_RECORD;
		}

		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoShareServiceRule( data );
	}
	
	/**
	 * ȡ��¼��
	 * @param nodeName
	 * @return
	 */
	public VoShareServiceRule[] getShareServiceRules( String nodeName )
	{
		if( nodeName == null ){
			nodeName = Constants.NODE_RECORD;
		}

		try{
			Recordset data = getRecordset( nodeName );
			VoShareServiceRule keys[] = new VoShareServiceRule[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoShareServiceRule( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoShareServiceRule[0];
		}
	}
}

