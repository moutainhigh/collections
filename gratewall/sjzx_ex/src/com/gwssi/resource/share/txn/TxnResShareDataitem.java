package com.gwssi.resource.share.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.resource.share.vo.ResShareDataitemContext;

public class TxnResShareDataitem extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap		txnMethods		= getAllMethod(
														TxnResShareDataitem.class,
														ResShareDataitemContext.class);

	// ���ݱ�����
	private static final String	TABLE_NAME		= "res_share_dataitem";

	// ��ѯ�б�
	private static final String	ROWSET_FUNCTION	= "select res_share_dataitem list";

	// ��ѯ��¼
	private static final String	SELECT_FUNCTION	= "select one res_share_dataitem";

	// �޸ļ�¼
	private static final String	UPDATE_FUNCTION	= "update one res_share_dataitem";

	// ���Ӽ�¼
	private static final String	INSERT_FUNCTION	= "insert one res_share_dataitem";

	// ɾ����¼
	private static final String	DELETE_FUNCTION	= "delete one res_share_dataitem";

	/**
	 * ���캯��
	 */
	public TxnResShareDataitem()
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
	 * ��ѯ������������Ϣ�б�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn20301021(ResShareDataitemContext context)
			throws TxnException
	{
		System.out.println(context);

		String share_table_id = context.getRecord("select-key").getValue(
				"share_table_id");
		String sql = "select t1.service_targets_name,t2.topics_name,t.table_name_en,t.table_name_cn "
				+ "from res_share_table t,res_business_topics t2,res_service_targets t1 "
				+ "where   t1.service_targets_id=t2.service_targets_id and t2.business_topics_id=t.business_topics_id "
				+ "and t.share_table_id='" + share_table_id + "' ";

		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeRowset(sql, context, "fatherRecord");

		table.executeFunction(ROWSET_FUNCTION, context, inputNode, outputNode);
		// ��ѯ���ļ�¼�� VoResShareDataitem result[] = context.getResShareDataitems(
		// outputNode );

	}

	/**
	 * �޸Ĺ�����������Ϣ��Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn20301022(ResShareDataitemContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// �޸ļ�¼������ VoResShareDataitem res_share_dataitem =
		// context.getResShareDataitem( inputNode );
		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * ���ӹ�����������Ϣ��Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn20301023(ResShareDataitemContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ���Ӽ�¼������ VoResShareDataitem res_share_dataitem =
		// context.getResShareDataitem( inputNode );
		table.executeFunction(INSERT_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * ��ѯ������������Ϣ�����޸�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn20301024(ResShareDataitemContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoResShareDataitemPrimaryKey primaryKey =
		// context.getPrimaryKey( inputNode );
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		// ��ѯ���ļ�¼���� VoResShareDataitem result = context.getResShareDataitem(
		// outputNode );
	}

	/**
	 * ɾ��������������Ϣ��Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn20301025(ResShareDataitemContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ɾ����¼�������б� VoResShareDataitemPrimaryKey primaryKey[] =
		// context.getPrimaryKeys( inputNode );
		table.executeFunction(DELETE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * ��ת�����뼯�鿴ҳ��
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn20301026(ResShareDataitemContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,TABLE_NAME);
		table.executeFunction("queryCodeMusterById", context, inputNode,
				outputNode);
		
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
		ResShareDataitemContext appContext = new ResShareDataitemContext(
				context);
		invoke(method, appContext);
	}
}
