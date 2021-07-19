package cn.gwssi.dw.rd.metadata.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.txn.Constants;

/**
 * ���ݱ�[sys_rd_unclaim_column]�����ݶ�����
 * @author Administrator
 *
 */
public class SysRdUnclaimColumnContext extends TxnContext
{
	private static final long serialVersionUID = 201205071126250001L;
	
	/**
	 * ���캯��
	 */
	public SysRdUnclaimColumnContext()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public SysRdUnclaimColumnContext(TxnContext value)
	{
		super(value);
	}
	
	/**
	 * ȡ����
	 * @return
	 */
	public VoSysRdUnclaimColumnPrimaryKey getPrimaryKey()
	{
		return getPrimaryKey( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * ȡ����
	 * @param nodeName ���������Ľڵ�����
	 * @return
	 */
	public VoSysRdUnclaimColumnPrimaryKey getPrimaryKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoSysRdUnclaimColumnPrimaryKey( data );
	}
	
	/**
	 * ȡ����
	 * @return
	 */
	public VoSysRdUnclaimColumnPrimaryKey[] getPrimaryKeys()
	{
		return getPrimaryKeys( Constants.NODE_PRIMARYKEY );
	}
	
	/**
	 * ȡ����
	 * @param nodeName ���������Ľڵ�����
	 * @return
	 */
	public VoSysRdUnclaimColumnPrimaryKey[] getPrimaryKeys( String nodeName )
	{
		try{
			Recordset data = getRecordset( nodeName );
			VoSysRdUnclaimColumnPrimaryKey keys[] = new VoSysRdUnclaimColumnPrimaryKey[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoSysRdUnclaimColumnPrimaryKey( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoSysRdUnclaimColumnPrimaryKey[0];
		}
	}
	
	/**
	 * ȡ��ѯ����
	 * @return
	 */
	public VoSysRdUnclaimColumnSelectKey getSelectKey()
	{
		return getSelectKey( Constants.NODE_SELECTKEY );
	}
	
	/**
	 * ȡ����
	 * @param nodeName ���������Ľڵ�����
	 * @return
	 */
	public VoSysRdUnclaimColumnSelectKey getSelectKey( String nodeName )
	{
		DataBus data;
		
		try{
			data = getRecord( nodeName );
		}
		catch( Exception e ){
			data = new DataBus();
			setValue( nodeName, data );
		}

		return new VoSysRdUnclaimColumnSelectKey( data );
	}
	
	/**
	 * ȡ��¼��
	 * @param nodeName
	 * @return
	 */
	public VoSysRdUnclaimColumn getSysRdUnclaimColumn( String nodeName )
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

		return new VoSysRdUnclaimColumn( data );
	}
	
	/**
	 * ȡ��¼��
	 * @param nodeName
	 * @return
	 */
	public VoSysRdUnclaimColumn[] getSysRdUnclaimColumns( String nodeName )
	{
		if( nodeName == null ){
			nodeName = Constants.NODE_RECORD;
		}

		try{
			Recordset data = getRecordset( nodeName );
			VoSysRdUnclaimColumn keys[] = new VoSysRdUnclaimColumn[data.size()];
			for( int ii=0; ii<data.size(); ii++ ){
				keys[ii] = new VoSysRdUnclaimColumn( data.get(ii) );
			}
			
			return keys;
		}
		catch( Exception e ){
			return new VoSysRdUnclaimColumn[0];
		}
	}
}

