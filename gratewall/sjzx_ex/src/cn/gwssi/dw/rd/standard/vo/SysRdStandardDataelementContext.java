package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.txn.Constants;

/**
 * ���ݱ�[sys_rd_standard_dataelement]�����ݶ�����
 * @author Administrator
 *
 */
public class SysRdStandardDataelementContext extends TxnContext
{
	private static final long serialVersionUID = 201205022055520005L;
	
	/**
	 * ���캯��
	 */
	public SysRdStandardDataelementContext()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public SysRdStandardDataelementContext(TxnContext value)
	{
		super(value);
	}
	
	/**
	 * ȡ����
	 * @return
	 */
	public VoSysRdStandardDataelementPrimaryKey getPrimaryKey()
	{
		return getPrimaryKey( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * ȡ����
	 * @param nodeName ���������Ľڵ�����
	 * @return
	 */
	public VoSysRdStandardDataelementPrimaryKey getPrimaryKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoSysRdStandardDataelementPrimaryKey( data );
	}
	
	/**
	 * ȡ����
	 * @return
	 */
	public VoSysRdStandardDataelementPrimaryKey[] getPrimaryKeys()
	{
		return getPrimaryKeys( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * ȡ����
	 * @param nodeName ���������Ľڵ�����
	 * @return
	 */
	public VoSysRdStandardDataelementPrimaryKey[] getPrimaryKeys( String nodeName )
	{
		try{
			Recordset data = getRecordset( nodeName );
			VoSysRdStandardDataelementPrimaryKey keys[] = new VoSysRdStandardDataelementPrimaryKey[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoSysRdStandardDataelementPrimaryKey( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoSysRdStandardDataelementPrimaryKey[0];
		}
	}
	
	/**
	 * ȡ��ѯ����
	 * @return
	 */
	public VoSysRdStandardDataelementSelectKey getSelectKey()
	{
		return getSelectKey( Constants.NODE_SELECTKEY );
	}
	
	/**
	 * ȡ����
	 * @param nodeName ���������Ľڵ�����
	 * @return
	 */
	public VoSysRdStandardDataelementSelectKey getSelectKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoSysRdStandardDataelementSelectKey( data );
	}
	
	/**
	 * ȡ��¼��
	 * @param nodeName
	 * @return
	 */
	public VoSysRdStandardDataelement getSysRdStandardDataelement( String nodeName )
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

		return new VoSysRdStandardDataelement( data );
	}
	
	/**
	 * ȡ��¼��
	 * @param nodeName
	 * @return
	 */
	public VoSysRdStandardDataelement[] getSysRdStandardDataelements( String nodeName )
	{
		if( nodeName == null ){
			nodeName = Constants.NODE_RECORD;
		}

		try{
			Recordset data = getRecordset( nodeName );
			VoSysRdStandardDataelement keys[] = new VoSysRdStandardDataelement[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoSysRdStandardDataelement( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoSysRdStandardDataelement[0];
		}
	}
}

