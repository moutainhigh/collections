package com.gwssi.log.collectlog.txn;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.dao.resource.PublicResource;
import cn.gwssi.common.dao.resource.code.CodeMap;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.common.log.LogUtil;
import com.gwssi.common.util.DateUtil;
import com.gwssi.common.util.JsonDataUtil;
import com.gwssi.common.util.UuidGenerator;
import com.gwssi.common.util.ValueSetCodeUtil;
import com.gwssi.log.collectlog.dao.CollectLogVo;
import com.gwssi.log.collectlog.vo.CollectJoumalContext;
import com.gwssi.log.sharelog.dao.ShareLogVo;
import com.gwssi.log.sharelog.vo.ShareLogContext;
import com.gwssi.resource.svrobj.vo.ResDataSourceContext;
import com.gwssi.share.service.vo.ShareServiceContext;

public class TxnCollectJoumal extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap		txnMethods		= getAllMethod(
														TxnCollectJoumal.class,
														CollectJoumalContext.class);

	// ���ݱ�����
	private static final String	TABLE_NAME		= "collect_joumal";

	// ��ѯ�б�
	private static final String	ROWSET_FUNCTION	= "select collect_joumal list";

	// ��ѯ��¼
	private static final String	SELECT_FUNCTION	= "${mod.function.select}";

	// �޸ļ�¼
	private static final String	UPDATE_FUNCTION	= "${mod.function.update}";

	// ���Ӽ�¼
	private static final String	INSERT_FUNCTION	= "${mod.function.insert}";

	// ɾ����¼
	private static final String	DELETE_FUNCTION	= "${mod.function.delete}";

	/**
	 * ���캯��
	 */
	public TxnCollectJoumal()
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
	 * ��ѯ�ɼ���־�б�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn6011001(CollectJoumalContext context) throws TxnException
	{

		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoCollectJoumalSelectKey selectKey = context.getSelectKey(
		// inputNode );
		table.executeFunction("queryCollectLogList", context, inputNode,
				outputNode);
		// ��ѯ���ļ�¼�� VoCollectJoumal result[] = context.getCollectJoumals(
		// outputNode );
		ResourceBundle code = ResourceBundle.getBundle("share_error");
		// System.out.println("context="+context);
		Recordset resultList = context.getRecordset(outputNode);
		for (int i = 0; i < resultList.size(); i++) {
			String val = ValueSetCodeUtil.getPropertiesByKey("share_error",
					resultList.get(i).getValue("return_codes"));
			resultList.get(i).setValue("return_codes", val);
		}
	}

	/**
	 * �޸Ĳɼ���־��Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn6011002(CollectJoumalContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// �޸ļ�¼������ VoCollectJoumal collect_joumal = context.getCollectJoumal(
		// inputNode );
		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * ���Ӳɼ���־��Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn6011003(CollectJoumalContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ���Ӽ�¼������ VoCollectJoumal collect_joumal = context.getCollectJoumal(
		// inputNode );
		table.executeFunction(INSERT_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * ��ѯ�ɼ���־�����޸�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn6011004(CollectJoumalContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoCollectJoumalPrimaryKey primaryKey = context.getPrimaryKey(
		// inputNode );
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		// ��ѯ���ļ�¼���� VoCollectJoumal result = context.getCollectJoumal(
		// outputNode );
	}

	/**
	 * ɾ���ɼ���־��Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn6011005(CollectJoumalContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ɾ����¼�������б� VoCollectJoumalPrimaryKey primaryKey[] =
		// context.getPrimaryKeys( inputNode );
		table.executeFunction(DELETE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * ��ת���ɼ���־��Ϣҳ��
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn6011006(CollectJoumalContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ɾ����¼�������б� VoCollectJoumalPrimaryKey primaryKey[] =
		// context.getPrimaryKeys( inputNode );
		table
				.executeFunction("queryCollectLog", context, inputNode,
						outputNode);

		ResourceBundle code = ResourceBundle.getBundle("share_error");
		//System.out.println("context=" + context);
		Recordset resultList = context.getRecordset(outputNode);
		for (int i = 0; i < resultList.size(); i++) {
			String val = ValueSetCodeUtil.getPropertiesByKey("share_error",
					resultList.get(i).getValue("return_codes"));
			resultList.get(i).setValue("return_codes", val);
		}
	}

	/**
	 * ��ѯ�б�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 * @throws IOException
	 */
	public void txn6011010(CollectJoumalContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		CollectJoumalContext targetContext = new CollectJoumalContext();
		Attribute.setPageRow(targetContext, outputNode, -1);
		table.executeFunction("getInfoByTarget", targetContext, inputNode,
				outputNode);
		System.out.println(targetContext);
		Recordset targetRs = targetContext.getRecordset("record");
		CodeMap codeMap = PublicResource.getCodeFactory();
		Recordset rs = codeMap.lookup(targetContext, "��Դ����_�����������");
		if (!rs.isEmpty()) {
			String[] keys = new String[rs.size()];
			String[] values = new String[rs.size()];
			for (int i = 0; i < rs.size(); i++) {
				DataBus db = rs.get(i);
				keys[i] = db.getValue("codename");
				values[i] = db.getValue("codevalue");
			}
			String groupValue = JsonDataUtil.getJsonGroupByRecordSet(targetRs,
					"service_targets_type", keys, values);
			context.setValue("svrTarget", groupValue);
		}

	}

	public void txn6011011(CollectJoumalContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoShareServiceSelectKey selectKey = context.getSelectKey(
		// inputNode );
		String create_time = context.getRecord("select-key").getValue(
				"created_time");

		// System.out.println("����TxnCollectjoumal.java��ֵΪ"+create_time);

		if (StringUtils.isNotBlank(create_time)) {
			String[] ctime = com.gwssi.common.util.DateUtil
					.getDateRegionByDatePicker(create_time, true);
			context.getRecord("select-key").setValue("created_time_start",
					ctime[0]);
			context.getRecord("select-key").setValue("created_time_end",
					ctime[1]);
			context.getRecord("select-key").remove("created_time");
		}
		table.executeFunction("queryShareServiceListOrder", context, inputNode,
				outputNode);
		// ��ѯ���ļ�¼�� VoShareService result[] = context.getShareServices(
		// outputNode );
	}

	/**
	 * 
	 * txn6011013�ɼ���־�鵵
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public void txn6011013(CollectJoumalContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoShareServiceSelectKey selectKey = context.getSelectKey(
		// inputNode );
		String time_start = context.getRecord("select-key").getValue(
				"created_time_start");
		String time_end = context.getRecord("select-key").getValue(
				"created_time_end");


		int count = table.executeFunction("updateCollectLog", context,
				inputNode, outputNode);
		context.getRecord("record").setValue("count", String.valueOf(count));

	}
	/**
	 * ��ѯ��־�б�(�ɼ�+����)  add by LXB 2014-01-24
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn6011020(CollectJoumalContext context) throws TxnException
	{
		//System.out.println("begin6011020:"+context);
		String start_time = context.getRecord("select-key").getValue("created_time");
		
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		
		//������� json����
		List typelist=new ArrayList();
		Map map =new HashMap();
		map.put("title", "�ɼ���־");
		map.put("key", "c");		
		typelist.add(map);
		Map map1 =new HashMap();
		map1.put("title", "������־");
		map1.put("key", "s");		
		typelist.add(map1);
		context.setValue("svrType", JsonDataUtil.toJSONString(typelist));
		
		//����״̬ json����
		List statuslist=new ArrayList();
		Map map2 =new HashMap();
		map2.put("title", "�ɹ�");
		map2.put("key", "1");		
		statuslist.add(map2);
		Map map3 =new HashMap();
		map3.put("title", "ʧ��");
		map3.put("key", "0");		
		statuslist.add(map3);
		context.setValue("svrStatus", JsonDataUtil.toJSONString(statuslist));
		
		// ���������� json����
		CollectJoumalContext svrTarcontext = new CollectJoumalContext();
		svrTarcontext.getRecord(inputNode).setValue("table_name",
				"res_service_targets");
		svrTarcontext.getRecord(inputNode).setValue("col_name",
				"service_targets_id");
		svrTarcontext.getRecord(inputNode).setValue("col_title",
				"service_targets_name");
		Attribute.setPageRow(svrTarcontext, outputNode, -1);
		table.executeFunction("getTargetInfo", svrTarcontext, inputNode,
				outputNode);
		Recordset targetRs = svrTarcontext.getRecordset("record");
		CodeMap codeMap = PublicResource.getCodeFactory();
		Recordset rs = codeMap.lookup(svrTarcontext, "��Դ����_�����������");
		if (!rs.isEmpty()) {
			String[] keys = new String[rs.size()];
			String[] values = new String[rs.size()];
			for (int i = 0; i < rs.size(); i++) {
				DataBus db = rs.get(i);
				keys[i] = db.getValue("codename");
				values[i] = db.getValue("codevalue");
			}
			String groupValue = JsonDataUtil.getJsonGroupByRecordSet(targetRs,
					"service_targets_type", keys, values);
			//System.out.println("group----"+groupValue);
			context.setValue("svrTarget", groupValue);
		}
		
		if(StringUtils.isBlank(start_time)){
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal=Calendar.getInstance();
			Date date=cal.getTime();
			String to= df.format(date)+" 00:00";
			cal.add(Calendar.DATE, -1); 
			date=cal.getTime();
			String from= df.format(date)+" 00:00";
			
			start_time=from+" �� "+to;
			context.getRecord("select-key").setValue("created_time",
					start_time);
			//System.out.println(start_time);
		}
		

		
		table.executeFunction("queryLogData", context, inputNode, outputNode);
		//System.out.println("end6011020:"+context);
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
		CollectJoumalContext appContext = new CollectJoumalContext(context);
		invoke(method, appContext);
	}

}
