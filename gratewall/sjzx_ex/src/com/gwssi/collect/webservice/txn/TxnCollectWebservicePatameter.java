package com.gwssi.collect.webservice.txn;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

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

import com.gwssi.collect.webservice.vo.CollectWebservicePatameterContext;
import com.gwssi.common.util.JSONUtils;
import com.gwssi.common.util.JsonDataUtil;
import com.gwssi.common.util.UuidGenerator;

public class TxnCollectWebservicePatameter extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap		txnMethods		= getAllMethod(
														TxnCollectWebservicePatameter.class,
														CollectWebservicePatameterContext.class);

	// ���ݱ�����
	private static final String	TABLE_NAME		= "collect_webservice_patameter";

	// ��ѯ�б�
	private static final String	ROWSET_FUNCTION	= "select collect_webservice_patameter list";

	// ��ѯ��¼
	private static final String	SELECT_FUNCTION	= "select one collect_webservice_patameter";

	// �޸ļ�¼
	private static final String	UPDATE_FUNCTION	= "update one collect_webservice_patameter";

	// ���Ӽ�¼
	private static final String	INSERT_FUNCTION	= "insert one collect_webservice_patameter";

	// ɾ����¼
	private static final String	DELETE_FUNCTION	= "delete one collect_webservice_patameter";

	/**
	 * ���캯��
	 */
	public TxnCollectWebservicePatameter()
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
	 * ��ѯ�������Ƿ��ظ�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30103000(CollectWebservicePatameterContext context)
			throws TxnException
	{
		String patameter_name = context.getRecord("primary-key").getValue(
				"patameter_name");// ��������
		String webservice_patameter_id = context.getRecord("primary-key")
				.getValue("webservice_patameter_id");// ����ID
		String webservice_task_id = context.getRecord("primary-key").getValue(
				"webservice_task_id");// ����ID
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		System.out.println(patameter_name + " == " + patameter_name);
		StringBuffer sql = new StringBuffer();
		sql
				.append("select count(*) as name_nums from collect_webservice_patameter t where 1=1 ");
		if (StringUtils.isNotBlank(patameter_name)) {
			sql.append(" and t.patameter_name = '"
					+ patameter_name.toLowerCase() + "'");
		}

		if (StringUtils.isNotBlank(webservice_task_id)) {
			sql.append(" and t.webservice_task_id = '" + webservice_task_id
					+ "'");
		}
		if (StringUtils.isNotBlank(webservice_patameter_id)) {
			sql.append(" and t.webservice_patameter_id != '"
					+ webservice_patameter_id + "'");
		}
		System.out.println("��ѯ���������Ƿ�����ʹ��sql==========" + sql.toString());
		table.executeRowset(sql.toString(), context, outputNode);
	}

	/**
	 * ��ѯ����ά���б�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30103001(CollectWebservicePatameterContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoCollectWebservicePatameterSelectKey selectKey =
		// context.getSelectKey( inputNode );
		table.executeFunction(ROWSET_FUNCTION, context, inputNode, outputNode);
		// ��ѯ���ļ�¼�� VoCollectWebservicePatameter result[] =
		// context.getCollectWebservicePatameters( outputNode );
	}

	/**
	 * �޸Ĳ���ά����Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30103002(CollectWebservicePatameterContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// �޸ļ�¼������ VoCollectWebservicePatameter collect_webservice_patameter =
		// context.getCollectWebservicePatameter( inputNode );

		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
	}

	public void txn30103033(CollectWebservicePatameterContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String webservice_patameter_id = context.getRecord("primary-key").getValue("webservice_patameter_id");
		if(StringUtils.isNotBlank(webservice_patameter_id)){
			context.getRecord("record").setValue("webservice_patameter_id",webservice_patameter_id );
			Attribute.setPageRow(context, outputNode, -1);		
			table.executeFunction(SELECT_FUNCTION, context, inputNode, "record");
			
			table.executeFunction("queryParamValueById", context, inputNode, outputNode);
			
			Recordset dataSet =context.getRecordset("collect_webservice_patameter");
			String dataSt="";
			List list=new ArrayList();
			if(dataSet!=null && dataSet.size()>0){
				for (int j = 0; j < dataSet.size(); j++) {
					DataBus data=dataSet.get(j);
					Map tmpMap=new HashMap();
					tmpMap.put("ws_param_value_id",data.getValue("ws_param_value_id") );
					tmpMap.put("param_value_type",data.getValue("param_value_type") );
					tmpMap.put("patameter_value",data.getValue("patameter_value") );
					tmpMap.put("style",data.getValue("style") );
					tmpMap.put("showorder",data.getValue("showorder") );
					tmpMap.put("patameter_name",data.getValue("patameter_name") );
					list.add(tmpMap);
				}
				dataSt=JSONUtils.toJSONString(list);
				context.setValue("dataSt", dataSt);
				
			}
		}
		
		//System.out.println("30103033 end:"+context);
	}


	/**
	 * ���Ӳ���ά����Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30103003(CollectWebservicePatameterContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String webservice_task_id = context.getRecord("select-key").getValue(
				"webservice_task_id");
		context.getRecord("record").setValue("webservice_task_id",
				webservice_task_id);
		String id = context.getRecord("record").getValue(
				"webservice_patameter_id");
		//System.out.println("txn30103003:"+context);
		
		if(StringUtils.isBlank(id)){
			// ���Ӳ�����Ϣ
			table.executeFunction(INSERT_FUNCTION, context, inputNode, outputNode);
			id = context.getRecord("record").getValue("webservice_patameter_id");
		}else {
			//�޸Ĳ���
			table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);

		}
		
		// ��ȡjsonֵ������ֵ��
		String jsonString = context.getRecord("record").getValue("jsondata");
		if (StringUtils.isNotBlank(jsonString)) {
			
			Recordset rs=new Recordset();
			TxnContext txnContext = new TxnContext();
			txnContext.setValue("webservice_patameter_id",id);
			JSONArray json = JsonDataUtil.getJsonArray(jsonString, "data");
			for (int i = 0; i < json.size(); i++) {
				// �����ǲ���ֵ
				JSONObject object = (JSONObject) json.get(i);

				String param_value_type = object.getString("param_value_type"); // ����
				String patameter_value_name = object
						.getString("patameter_value_name"); // ������
				String patameter_value_value = object
						.getString("patameter_value_value");// ����ֵ
				String style = object.getString("style");// ��ʽ
				String showorder = object.getString("showorder"); // ˳��
				String ws_param_value_id = object.getString("ws_param_value_id");//ֵ���� 
				

				DataBus db = new DataBus();
				db.setValue("webservice_patameter_id", id);
				db.setValue("param_value_type", param_value_type);
				db.setValue("patameter_name", patameter_value_name);
				db.setValue("patameter_value", patameter_value_value);
				db.setValue("style", style);
				db.setValue("connector", " ");
				db.setValue("showorder", showorder);
				db.setValue("ws_param_value_id",ws_param_value_id );
				
				rs.add(db);
				
				
			}
			txnContext.addRecord("record", rs);
			// ��������ֵ
			callService("30107003", txnContext);
		}
	}

	// ���Ӽ�¼������ VoCollectWebservicePatameter collect_webservice_patameter =
	// context.getCollectWebservicePatameter( inputNode );

	// String
	// id=context.getRecord("record").getValue("webservice_patameter_id");
	//		
	// System.out.print("id==="+id);
	// if(id==null||"".equals(id)){
	// id = UuidGenerator.getUUID();
	// context.getRecord("record").setValue("webservice_patameter_id",
	// id);//����ID
	// context.getRecord("primary-key").setValue("webservice_patameter_id",
	// id);//����ID
	//			  
	// context.getRecord("record").setValue("webservice_task_id",
	// context.getRecord("record").getValue("webservice_task_id"));//����ID
	// context.getRecord("record").setValue("patameter_type",
	// context.getRecord("record").getValue("patameter_type"));//��������
	// context.getRecord("record").setValue("patameter_name",
	// context.getRecord("record").getValue("patameter_name").toLowerCase());//��������
	// context.getRecord("record").setValue("patameter_value",
	// context.getRecord("record").getValue("patameter_value"));//����ֵ
	// context.getRecord("record").setValue("patameter_style",
	// context.getRecord("record").getValue("patameter_style"));//������ʽ
	// table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	// }else{
	// context.getRecord("record").setValue("webservice_patameter_id",
	// id);//��������
	// context.getRecord("record").setValue("patameter_type",
	// context.getRecord("record").getValue("patameter_type"));//��������
	// context.getRecord("record").setValue("patameter_name",
	// context.getRecord("record").getValue("patameter_name").toLowerCase());//��������
	// context.getRecord("record").setValue("patameter_value",
	// context.getRecord("record").getValue("patameter_value"));//����ֵ
	// context.getRecord("record").setValue("patameter_style",
	// context.getRecord("record").getValue("patameter_style"));//������ʽ
	// table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	// }
	//			
	// this.callService("30102008", context);
	// }

	/**
	 * ��ѯ����ά�������޸�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30103004(CollectWebservicePatameterContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoCollectWebservicePatameterPrimaryKey primaryKey =
		// context.getPrimaryKey( inputNode );
		String param_style = context.getRecord("record").getValue(
				"patameter_style");

		table.executeFunction("update_style", context, inputNode, outputNode);

		// ��ѯ��ǰ������޸ĺ����������Ĳ���ֵ�б�
		table.executeFunction("queryParamValueById", context, inputNode,
				outputNode);
		DataBus db = new DataBus();
		db.setProperty("patameter_style", param_style);
		context.addRecord("param", db);

		// ��ѯ���ļ�¼���� VoCollectWebservicePatameter result =
		// context.getCollectWebservicePatameter( outputNode );
	}

	/**
	 * ɾ������ά����Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30103005(CollectWebservicePatameterContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ɾ����¼�������б� VoCollectWebservicePatameterPrimaryKey primaryKey[] =
		// context.getPrimaryKeys( inputNode );
		table.executeFunction(DELETE_FUNCTION, context, inputNode, outputNode);
		String webservice_task_id = context.getRecord("primary-key").getValue(
				"webservice_task_id");// �ɼ����񷽷�ID
		if (webservice_task_id != null && !"".equals(webservice_task_id)) {
			context.getRecord("record").setValue("webservice_task_id",
					webservice_task_id);
		}
		this.callService("30102008", context);
	}

	public void txn30103055(CollectWebservicePatameterContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String webservice_patameter_id = context.getRecord("primary-key").getValue(
				"webservice_patameter_id");// �ɼ����񷽷�ID
		System.out.println("txn30103055 webservice_patameter_id"+webservice_patameter_id);
		table.executeFunction("delete_param", context, inputNode, outputNode);
	}

	/**
	 * ��ѯ����ά����������
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30103006(CollectWebservicePatameterContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoCollectWebservicePatameterPrimaryKey primaryKey =
		// context.getPrimaryKey( inputNode );

		String collect_task_id = context.getRecord("primary-key").getValue(
				"collect_task_id");// �ɼ�����ID
		if (collect_task_id != null && !"".equals(collect_task_id)) {
			context.getRecord("record").setValue("collect_task_id",
					collect_task_id);
		}
		String webservice_task_id = context.getRecord("primary-key").getValue(
				"webservice_task_id");// �ɼ����񷽷�ID
		if (webservice_task_id != null && !"".equals(webservice_task_id)) {
			context.getRecord("record").setValue("webservice_task_id",
					webservice_task_id);
		}
		String service_targets_id = context.getRecord("primary-key").getValue(
				"service_targets_id");// �������ID

		if (service_targets_id != null && !"".equals(service_targets_id)) {
			context.getRecord("record").setValue("service_targets_id",
					service_targets_id);
		}
		// table.executeFunction( SELECT_FUNCTION, context, inputNode,
		// outputNode );
		// ��ѯ���ļ�¼���� VoCollectWebservicePatameter result =
		// context.getCollectWebservicePatameter( outputNode );
	}

	/**
	 * ��ѯ����ά�������޸�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30103007(CollectWebservicePatameterContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoCollectWebservicePatameterPrimaryKey primaryKey =
		// context.getPrimaryKey( inputNode );

		String service_targets_id = context.getRecord("primary-key").getValue(
				"service_targets_id");// �������ID

		if (service_targets_id != null && !"".equals(service_targets_id)) {
			context.getRecord("record").setValue("service_targets_id",
					service_targets_id);
		}
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		// ��ѯ���ļ�¼���� VoCollectWebservicePatameter result =
		// context.getCollectWebservicePatameter( outputNode );
	}

	/**
	 * ��ѯ����ά�������޸�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30103008(CollectWebservicePatameterContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoCollectWebservicePatameterPrimaryKey primaryKey =
		// context.getPrimaryKey( inputNode );
		String param_style = context.getRecord("record").getValue(
				"patameter_style");

		table.executeFunction("update_param", context, inputNode, outputNode);

		// ��ѯ��ǰ������޸ĺ����������Ĳ���ֵ�б�
		table.executeFunction("queryParamValueById", context, inputNode,
				outputNode);
		DataBus db = new DataBus();
		db.setProperty("patameter_style", param_style);
		context.addRecord("param", db);

		// ��ѯ���ļ�¼���� VoCollectWebservicePatameter result =
		// context.getCollectWebservicePatameter( outputNode );
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
		CollectWebservicePatameterContext appContext = new CollectWebservicePatameterContext(
				context);
		invoke(method, appContext);
	}
}
