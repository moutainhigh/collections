package com.gwssi.share.ftp.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.common.util.UuidGenerator;
import com.gwssi.share.ftp.vo.ShareFtpServiceContext;

public class TxnShareFtpService extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap		txnMethods		= getAllMethod(
														TxnShareFtpService.class,
														ShareFtpServiceContext.class);

	// ���ݱ�����
	private static final String	TABLE_NAME		= "share_ftp_service";

	// ��ѯ�б�
	private static final String	ROWSET_FUNCTION	= "select share_ftp_service list";

	// ��ѯ��¼
	private static final String	SELECT_FUNCTION	= "select one share_ftp_service";

	// �޸ļ�¼
	private static final String	UPDATE_FUNCTION	= "update one share_ftp_service";

	// ���Ӽ�¼
	private static final String	INSERT_FUNCTION	= "insert one share_ftp_service";

	// ɾ����¼
	private static final String	DELETE_FUNCTION	= "delete one share_ftp_service";

	/**
	 * ���캯��
	 */
	public TxnShareFtpService()
	{

	}

	/**
	 * ��ʼ������
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{

	}

	/**
	 * ��ѯ����ftp�����б�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn40401001(ShareFtpServiceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoShareFtpServiceSelectKey selectKey = context.getSelectKey(
		// inputNode );
		table.executeFunction(ROWSET_FUNCTION, context, inputNode, outputNode);
		// ��ѯ���ļ�¼�� VoShareFtpService result[] = context.getShareFtpServices(
		// outputNode );
	}

	/**
	 * �޸Ĺ���ftp������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn40401002(ShareFtpServiceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

	
		String ftp_service_id = context.getRecord(inputNode).getValue("ftp_service_id");
		String service_id=context.getRecord(inputNode).getValue("service_id");
		if(service_id!=null&&!"".equals(service_id)){
		
			context.getRecord("record1").setValue("service_id", service_id);// ftp����ID
			if (ftp_service_id == null || "".equals(ftp_service_id)) {
				String id = UuidGenerator.getUUID();
				context.getRecord("record1").setValue("ftp_service_id", id);// ftp����ID
				table.executeFunction(INSERT_FUNCTION, context, inputNode,outputNode);
			}// ����
			else {
				table.executeFunction(UPDATE_FUNCTION, context, inputNode,outputNode);
			}
			}
			this.callService("40401004", context);
		
	}
	
	/**
	 * ���ӹ���ftp������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn40401003(ShareFtpServiceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ���Ӽ�¼������ VoShareFtpService share_ftp_service =
		// context.getShareFtpService( inputNode );
		table.executeFunction(INSERT_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * ��ѯ����ftp���������޸�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn40401004(ShareFtpServiceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		ShareFtpServiceContext context1= new ShareFtpServiceContext();
		// ��ѯ��¼������ VoShareFtpServicePrimaryKey primaryKey =
		// context.getPrimaryKey( inputNode );
		String service_id = context.getRecord("primary-key").getValue("service_id");// ����ID
		if(service_id==null||"".equals(service_id)){
			service_id=context.getRecord("record1").getValue("service_id");// ����ID
		}
		String service_no = context.getRecord("primary-key").getValue("service_no");// ������
		if(service_no==null||"".equals(service_no)){
			service_no=context.getRecord("record1").getValue("service_no");// ������
		}
		String service_name = context.getRecord("primary-key").getValue("service_name");// ��������
		if(service_name==null||"".equals(service_name)){
			service_name=context.getRecord("record1").getValue("service_name");// ��������
		}
		String service_targets_id = context.getRecord("primary-key").getValue("service_targets_id");// �������ID
		if(service_targets_id==null||"".equals(service_targets_id)){
			service_targets_id=context.getRecord("record1").getValue("service_targets_id");// �������ID
		}
		
		String sql = "select count(*) as num from share_ftp_service  where 1=1 ";
		if (service_id != null && !"".equals(service_id)) {
			sql += "and service_id = '" + service_id + "' ";
		}
		table.executeSelect(sql, context1, outputNode);
		String num = context1.getRecord(outputNode).getValue("num");
		if (num != null && !"0".equals(num)) {
			table.executeFunction("queryFtpService", context, inputNode,"record1");
			
			sql = "select count(*) as num from share_ftp_srv_param t1,share_ftp_service t2 where t1.ftp_service_id = t2.ftp_service_id ";
			if(service_id!=null&&!"".equals(service_id)){
				sql += "and t2.service_id = '" + service_id + "' ";
			}
			table.executeSelect(sql, context1, outputNode);
			num = context1.getRecord(outputNode).getValue("num");
			System.out.print("num===" + num);
			if (num != null && !"0".equals(num)) {
				// ��ѯ��ǰ������޸ĺ����������Ĳ���ֵ�б�
				table.executeFunction("queryParamValueById", context, inputNode,outputNode);
				DataBus db = new DataBus();
				// db.setProperty("patameter_style", param_style);
				context.addRecord("param", db);
			}
		}
		context.getRecord("record1").setValue("service_id", service_id);// ����ID
		context.getRecord("record1").setValue("service_no", service_no);// ������
		context.getRecord("record1").setValue("service_name", service_name);// ��������
		context.getRecord("record1").setValue("service_targets_id",service_targets_id);// �������ID
	}

	/**
	 * ɾ������ftp������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn40401005(ShareFtpServiceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ɾ����¼�������б� VoShareFtpServicePrimaryKey primaryKey[] =
		// context.getPrimaryKeys( inputNode );
		table.executeFunction(DELETE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * ���ظ���ķ����������滻���׽ӿڵ�������� ���ú���
	 * 
	 * @param funcName
	 *            ��������
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	protected void doService(String funcName, TxnContext context)
			throws TxnException
	{
		Method method = (Method) txnMethods.get(funcName);
		if (method == null) {
			funcName = this.getClass().getName() + "#" + funcName;
			throw new TxnErrorException(ErrorConstant.JAVA_METHOD_NOTFOUND,
					"û���ҵ�������[" + txnCode + ":" + funcName + "]�Ĵ�����");
		}

		// ִ��
		ShareFtpServiceContext appContext = new ShareFtpServiceContext(context);
		invoke(method, appContext);
	}
}
