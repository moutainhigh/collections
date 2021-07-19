package cn.gwssi.dw.aic.bj.ecomm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.txn.Constants;

/**
 * ���ݱ�[eb_sys_right_user]�����ݶ�����
 * @author Administrator
 *
 */
public class EbSysRightUserContext extends TxnContext
{
	private static final long serialVersionUID = 201209261622180001L;
	
	/**
	 * ���캯��
	 */
	public EbSysRightUserContext()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public EbSysRightUserContext(TxnContext value)
	{
		super(value);
	}
	
	/**
	 * ȡ����
	 * @return
	 */
	public VoEbSysRightUserPrimaryKey getPrimaryKey()
	{
		return getPrimaryKey( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * ȡ����
	 * @param nodeName ���������Ľڵ�����
	 * @return
	 */
	public VoEbSysRightUserPrimaryKey getPrimaryKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoEbSysRightUserPrimaryKey( data );
	}
	
	/**
	 * ȡ����
	 * @return
	 */
	public VoEbSysRightUserPrimaryKey[] getPrimaryKeys()
	{
		return getPrimaryKeys( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * ȡ����
	 * @param nodeName ���������Ľڵ�����
	 * @return
	 */
	public VoEbSysRightUserPrimaryKey[] getPrimaryKeys( String nodeName )
	{
		try{
			Recordset data = getRecordset( nodeName );
			VoEbSysRightUserPrimaryKey keys[] = new VoEbSysRightUserPrimaryKey[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoEbSysRightUserPrimaryKey( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoEbSysRightUserPrimaryKey[0];
		}
	}
	
	/**
	 * ȡ��ѯ����
	 * @return
	 */
	public VoEbSysRightUserSelectKey getSelectKey()
	{
		return getSelectKey( Constants.NODE_SELECTKEY );
	}
	
	/**
	 * ȡ����
	 * @param nodeName ���������Ľڵ�����
	 * @return
	 */
	public VoEbSysRightUserSelectKey getSelectKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoEbSysRightUserSelectKey( data );
	}
	
	/**
	 * ȡ��¼��
	 * @param nodeName
	 * @return
	 */
	public VoEbSysRightUser getEbSysRightUser( String nodeName )
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

		return new VoEbSysRightUser( data );
	}
	
	/**
	 * ȡ��¼��
	 * @param nodeName
	 * @return
	 */
	public VoEbSysRightUser[] getEbSysRightUsers( String nodeName )
	{
		if( nodeName == null ){
			nodeName = Constants.NODE_RECORD;
		}

		try{
			Recordset data = getRecordset( nodeName );
			VoEbSysRightUser keys[] = new VoEbSysRightUser[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoEbSysRightUser( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoEbSysRightUser[0];
		}
	}
}

