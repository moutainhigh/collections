package com.gwssi.sysmgr.priv.func.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.sysmgr.role.vo.OperrolefunContext;

public class TxnOperrolefun extends TxnService {
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod(TxnOperrolefun.class,
			OperrolefunContext.class);

	// ���ݱ�����
	private static final String TABLE_NAME = "operrolefun";

	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select operrolefun list";

	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "${mod.function.select}";

	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "${mod.function.update}";

	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one operrolefun";

	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one operrolefun";

	/**
	 * ���캯��
	 */
	public TxnOperrolefun() {

	}

	/**
	 * ��ʼ������
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException {

	}

	/**
	 * ��ѯ��ɫ����Ȩ���б�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn103031(OperrolefunContext context) throws TxnException {
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoOperrolefunSelectKey selectKey = context.getSelectKey(
		// inputNode );
		table.executeFunction(ROWSET_FUNCTION, context, inputNode, outputNode);
		// ��ѯ���ļ�¼�� VoOperrolefun result[] = context.getOperrolefuns( outputNode
		// );
	}

	/**
	 * �޸Ľ�ɫ����Ȩ����Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn103032(OperrolefunContext context) throws TxnException {
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// �޸ļ�¼������ VoOperrolefun operrolefun = context.getOperrolefun( inputNode
		// );
		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * ���ӽ�ɫ����Ȩ����Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn103033(OperrolefunContext context) throws TxnException {
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ���Ӽ�¼������ VoOperrolefun operrolefun = context.getOperrolefun( inputNode
		// );
		table.executeFunction(INSERT_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * ��ѯ��ɫ����Ȩ�������޸�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn103034(OperrolefunContext context) throws TxnException {
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoOperrolefunPrimaryKey primaryKey = context.getPrimaryKey(
		// inputNode );
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		// ��ѯ���ļ�¼���� VoOperrolefun result = context.getOperrolefun( outputNode );
	}

	/**
	 * ɾ����ɫ����Ȩ����Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn103035(OperrolefunContext context) throws TxnException {
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ɾ����¼�������б� VoOperrolefunPrimaryKey primaryKey[] =
		// context.getPrimaryKeys( inputNode );
		table.executeFunction(DELETE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * ɾ����ɫ����Ȩ����Ϣ������ɫID
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn103036(OperrolefunContext context) throws TxnException {
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String roleid = context.getRecord(inputNode).getValue("roleid");
		String funcList = context.getRecord(inputNode).getValue("funcList");

		if (funcList == null || funcList.equals("")) {

			TxnContext select = new TxnContext();
			DataBus db = new DataBus();
			db.setValue("roleid", roleid);
			select.addRecord(inputNode, db);
			callService("com.gwssi.sysmgr.priv.func.txn.TxnOperrolefun", "txn103031",
					select);
			Recordset rs = select.getRecordset(outputNode);
			for (int i = 0; i < rs.size(); i++) {
				String roleaccid = rs.get(i).getValue("roleaccid");
				TxnContext delete = new TxnContext();
				db = new DataBus();
				db.setValue("objectid", roleaccid);
				db.setValue("dataaccdispobj", "1");
				delete.addRecord("record", db);
				callService("com.gwssi.sysmgr.priv.datapriv.txn.TxnDataaccdisp",
						"txn103047", delete);

				delete = new TxnContext();
				db = new DataBus();
				db.setValue("objectid", roleaccid);
				db.setValue("dataaccdispobj", "2");
				delete.addRecord("record", db);
				callService("com.gwssi.sysmgr.priv.datapriv.txn.TxnDataaccdisp",
						"txn103047", delete);
			}
			// ɾ����¼�������б� VoOperrolefunPrimaryKey primaryKey[] =
			// context.getPrimaryKeys( inputNode );
			table.executeFunction("����ɫɾ��", context, inputNode, outputNode);
		} else {

			funcList = "'" + funcList.replaceAll(";", "','") + "'";
			TxnContext valContext = new TxnContext();
			Attribute.setPageRow(valContext, outputNode, -1);
			valContext.getRecord("select-key").setValue("funcList", funcList);
			table.executeFunction("selectFunc", valContext, inputNode,
					outputNode);
			Recordset queryList = valContext.getRecordset(outputNode);
			TxnContext select = null;
			DataBus db = null;
			DataBus queryDb = null;
			String funcCode = "";
			TxnContext deleteContext = null;
			DataBus deleteDb = null;
			String roleaccid = "";
			TxnContext delete = null;
			for (int k = 0; k < queryList.size(); k++) {

				String roleaccidS = "";
				queryDb = queryList.get(k);
				funcCode = queryDb.getValue("funccode");
				select = new TxnContext();
				Attribute.setPageRow(select, outputNode, -1);
				db = new DataBus();
				db.setValue("roleid", roleid);
				db.setValue("txncode", funcCode);
				select.addRecord("select-key", db);
				table.executeFunction("selectOperRoleId", select, inputNode,
						outputNode);
				Recordset rs = select.getRecordset(outputNode);
				// for(int i = 0; i < rs.size(); i++){
				// roleaccid = rs.get(i).getValue("roleaccid");
				// TxnContext delete = new TxnContext();
				// db = new DataBus();
				// db.setValue("objectid", roleaccid);
				// db.setValue("dataaccdispobj", "1");
				// delete.addRecord("record", db);
				// callService("cn.gwssi.csdb.xtgl.txn.TxnDataaccdisp",
				// "txn103047", delete);
				//					
				// delete = new TxnContext();
				// db = new DataBus();
				// db.setValue("objectid", roleaccid);
				// db.setValue("dataaccdispobj", "2");
				// delete.addRecord("record", db);
				// callService("cn.gwssi.csdb.xtgl.txn.TxnDataaccdisp",
				// "txn103047", delete);
				// }
				for (int i = 0; i < rs.size(); i++) {

					roleaccid = rs.get(i).getValue("roleaccid");
					roleaccidS += "," + roleaccid;
				}
				if (roleaccidS != null && !roleaccidS.equals("")) {

					roleaccidS = roleaccidS.substring(1);
					delete = new TxnContext();
					db = new DataBus();
					db.setValue("objectid", roleaccidS);
					delete.addRecord("select-key", db);
					table.executeFunction("deleteDispByObjIdS", delete,
							inputNode, outputNode);
				}
				deleteContext = new TxnContext();
				deleteDb = new DataBus();
				deleteDb.setValue("roleid", roleid);
				deleteDb.setValue("txncode", funcCode);
				deleteContext.addRecord("select-key", deleteDb);

				table.executeFunction("deleteOperRoleFun", deleteContext,
						inputNode, outputNode);
			}
		}
	}

	/**
	 * ��ѯ��ɫ���н��״���
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn103037(OperrolefunContext context) throws TxnException {
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoOperrolefunPrimaryKey primaryKey = context.getPrimaryKey(
		// inputNode );
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction("loadRoleTxn", context, inputNode, outputNode);
		// ��ѯ���ļ�¼���� VoOperrolefun result = context.getOperrolefun( outputNode );
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
			throws TxnException {
		Method method = (Method) txnMethods.get(funcName);
		if (method == null) {
			funcName = this.getClass().getName() + "#" + funcName;
			throw new TxnErrorException(ErrorConstant.JAVA_METHOD_NOTFOUND,
					"û���ҵ�������[" + txnCode + ":" + funcName + "]�Ĵ�����");
		}

		// ִ��
		OperrolefunContext appContext = new OperrolefunContext(context);
		invoke(method, appContext);
	}
}
