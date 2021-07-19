package com.gwssi.share.ftp.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.common.util.UuidGenerator;
import com.gwssi.share.ftp.vo.ShareFtpSrvParamContext;

public class TxnShareFtpSrvParam extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnShareFtpSrvParam.class, ShareFtpSrvParamContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "share_ftp_srv_param";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select share_ftp_srv_param list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one share_ftp_srv_param";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one share_ftp_srv_param";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one share_ftp_srv_param";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one share_ftp_srv_param";
	
	/**
	 * ���캯��
	 */
	public TxnShareFtpSrvParam()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯftp��������б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn40402001( ShareFtpSrvParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoShareFtpSrvParamSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoShareFtpSrvParam result[] = context.getShareFtpSrvParams( outputNode );
	}
	
	/** �޸�ftp���������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn40402002( ShareFtpSrvParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoShareFtpSrvParam share_ftp_srv_param = context.getShareFtpSrvParam( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
		String service_id=context.getRecord(inputNode).getValue("service_id");
		System.out.println("service_id======="+service_id);
		String service_targets_id=context.getRecord(inputNode).getValue("service_targets_id");
		System.out.println("service_targets_id======="+service_targets_id);
		context.getRecord("record1").setValue("service_id",service_id);
		context.getRecord("record1").setValue("service_targets_id",service_targets_id);
		//this.callService("40401004", context);
	}
	
	/** ����ftp���������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn40402003( ShareFtpSrvParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoShareFtpSrvParam share_ftp_srv_param = context.getShareFtpSrvParam( inputNode );
		String srv_param_id = context.getRecord("record").getValue("srv_param_id");
			if (srv_param_id == null || "".equals(srv_param_id)) {
				String id = UuidGenerator.getUUID();
				context.getRecord("record").setValue("srv_param_id", id);// ����ֵID
				table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
			}else{
				table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
			}
			
	}
	
	/** ��ѯftp������������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn40402004( ShareFtpSrvParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoShareFtpSrvParamPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoShareFtpSrvParam result = context.getShareFtpSrvParam( outputNode );
	}
	
	/** ɾ��ftp���������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn40402005( ShareFtpSrvParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoShareFtpSrvParamPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
		
	/**
	 * ���ظ���ķ����������滻���׽ӿڵ��������
	 * ���ú���
	 * @param funcName ��������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void doService( String funcName,
			TxnContext context ) throws TxnException
	{
		Method method = (Method)txnMethods.get( funcName );
		if( method == null ){
			funcName = this.getClass().getName() + "#" + funcName;
			throw new TxnErrorException( ErrorConstant.JAVA_METHOD_NOTFOUND,
					"û���ҵ�������[" + txnCode + ":" + funcName + "]�Ĵ�����"
			);
		}
		
		// ִ��
		ShareFtpSrvParamContext appContext = new ShareFtpSrvParamContext( context );
		invoke( method, appContext );
	}
}
