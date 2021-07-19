package com.gwssi.share.trs.txn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
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
import cn.gwssi.common.dao.resource.PublicResource;
import cn.gwssi.common.dao.resource.code.CodeMap;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.util.DateUtil;
import com.gwssi.common.util.JSONUtils;
import com.gwssi.common.util.JsonDataUtil;
import com.gwssi.common.util.StringUtil;
import com.gwssi.share.rule.vo.ShareServiceRuleContext;
import com.gwssi.share.trs.vo.TrsShareServiceContext;
import com.trs.client.TRSException;

public class TxnTrsShareService extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap		txnMethods		= getAllMethod(
														TxnTrsShareService.class,
														TrsShareServiceContext.class);

	// ���ݱ�����
	private static final String	TABLE_NAME		= "trs_share_service";

	// ��ѯ�б�
	private static final String	ROWSET_FUNCTION	= "select trs_share_service list";

	// ��ѯ��¼
	private static final String	SELECT_FUNCTION	= "select one trs_share_service";

	// �޸ļ�¼
	private static final String	UPDATE_FUNCTION	= "update one trs_share_service";

	// ���Ӽ�¼
	private static final String	INSERT_FUNCTION	= "insert one trs_share_service";

	// ɾ����¼
	private static final String	DELETE_FUNCTION	= "delete one trs_share_service";

	/**
	 * ���캯��
	 */
	public TxnTrsShareService()
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
	 * ��ѯtrs�ӿ��б�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn40300011(TrsShareServiceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ���������� json����
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction("getTrsCountBySvrObjType", context, inputNode,
				outputNode);
		Recordset targetRs = context.getRecordset("record");
		// System.out.println("targetRs:"+targetRs.size());
		CodeMap codeMap = PublicResource.getCodeFactory();
		Recordset rs = codeMap.lookup(context, "��Դ����_�����������");
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

	/**
	 * ��ѯtrs�ӿ��б�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn40300001(TrsShareServiceContext context) throws TxnException
	{
		// System.out.println("txn40300001="+context);
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ���������� json����
		TrsShareServiceContext context1 = new TrsShareServiceContext();
		Attribute.setPageRow(context1, outputNode, -1);
		table.executeFunction("getTrsCountBySvrObjType", context1, inputNode,
				outputNode);
		Recordset targetRs = context1.getRecordset("record");
		// System.out.println("targetRs:"+targetRs.size());
		CodeMap codeMap = PublicResource.getCodeFactory();
		Recordset rs = codeMap.lookup(context1, "��Դ����_�����������");
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
		// �������״̬����
		Attribute.setPageRow(context1, outputNode, -1);
		table.executeFunction("getTrsCountBySvrState", context1, inputNode,
				outputNode);
		Recordset rs2 = context1.getRecordset("record");

		if (!rs2.isEmpty()) {
			String groupValue = JsonDataUtil.getJsonByRecordSet(rs2,
					"codevalue", "codename");
			context.setValue("svrState", groupValue);
			// System.out.println(groupValue);
		}
		// this.callService("40300011", context);
		// ��ѯ��¼������ VoTrsShareServiceSelectKey selectKey = context.getSelectKey(
		// inputNode );
		// Attribute.setPageRow(context, outputNode, 10);
		table.executeFunction("queryTrsShareList", context, inputNode,
				outputNode);
		// ��ѯ���ļ�¼�� VoTrsShareService result[] = context.getTrsShareServices(
		// outputNode );
	}

	/**
	 * �޸�trs�ӿ���Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 * @throws IOException
	 */
	public void txn40300002(TrsShareServiceContext context)
			throws TxnException, IOException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		DataBus oper_data = context.getRecord("oper-data");
		context.getRecord("record").setValue("last_modify_id",
				oper_data.getValue("userID"));
		context.getRecord("record").setValue("last_modify_time",
				DateUtil.getYMDHMSTime());
		// System.out.println("txn40300002=============="+context);
		// �޸ļ�¼������ VoTrsShareService trs_share_service =
		// context.getTrsShareService( inputNode );

		context = prepareCtx(context);

		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
	}

	private TrsShareServiceContext prepareCtx(TrsShareServiceContext context)
	{
		String trs_template = context.getRecord("record").getValue(
				"trs_template");

		if (StringUtils.isNotBlank(trs_template)) {
			context.getRecord("record").setValue("use_template", "T");
			if (trs_template.length() > 3500) {
				context.getRecord("record").setValue("trs_template",
						trs_template.substring(0, 3500));
				context.getRecord("record").setValue("trs_template_ex",
						trs_template.substring(3500));
			} else {
				context.getRecord("record").setValue("trs_template_ex", "");
			}
		} else {
			context.getRecord("record").setValue("use_template", "F");
			context.getRecord("record").setValue("trs_template_ex", "");
		}
		return context;
	}

	/**
	 * ����trs�ӿ���Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 * @throws IOException
	 */
	public void txn40300003(TrsShareServiceContext context)
			throws TxnException, IOException
	{
		// System.out.println("txn40300003======="+context);
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// Ĭ��ֵ
		context.getRecord("record").setValue("service_state",
				ExConstant.SERVICE_STATE_N);
		context.getRecord("record").setValue("is_markup",
				ExConstant.IS_MARKUP_Y);
		DataBus oper_data = context.getRecord("oper-data");
		context.getRecord("record").setValue("creator_id",
				oper_data.getValue("userID"));
		context.getRecord("record").setValue("created_time",
				DateUtil.getYMDHMSTime());
		context.getRecord("record").setValue("last_modify_id",
				oper_data.getValue("userID"));
		context.getRecord("record").setValue("last_modify_time",
				DateUtil.getYMDHMSTime());

		// ����ǰ��������һ��serviceNO��ֹʱ��̫���ظ�
		boolean notFirst = true;
		TrsShareServiceContext context_NO = new TrsShareServiceContext();
		try {
			callService("40300006", context_NO);
			// System.out.println(context_NO);
		} catch (TxnException e) {
			notFirst = false;
		}
		if (notFirst) {
			context.getRecord("record").setValue("trs_service_no",
					context_NO.getRecord("record").getValue("trs_service_no"));
		}
		// String trs_service_id =
		// context.getRecord("record").getValue("trs_service_id");

		context = prepareCtx(context);

		// ���Ӽ�¼������ VoTrsShareService trs_share_service =
		// context.getTrsShareService( inputNode );
		table.executeFunction(INSERT_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * ��ѯtrs�ӿ������޸�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void txn40300004(TrsShareServiceContext context)
			throws TxnException, FileNotFoundException, IOException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoTrsShareServicePrimaryKey primaryKey =
		// context.getPrimaryKey( inputNode );
	
		
		String trs_service_id = context.getRecord("primary-key").getValue("trs_service_id");
		StringBuffer sql = new StringBuffer();
		
		sql.append("select t1.*,t1.created_time as cretime,t1.last_modify_time as modtime, yh1.yhxm as crename,yh2.yhxm as modname from trs_share_service t1,xt_zzjg_yh_new yh1,xt_zzjg_yh_new yh2");
		sql.append(" where  t1.creator_id = yh1.yhid_pk(+)  and t1.last_modify_id = yh2.yhid_pk(+)  and t1.trs_service_id = '");
		sql.append(trs_service_id);
		sql.append("'");
		table.executeRowset(sql.toString(), context, outputNode);
		
		
		// ��ѯ���ļ�¼���� VoTrsShareService result = context.getTrsShareService(
		// outputNode );
		String trs_template = context.getRecord("record").getValue(
				"trs_template")
				+ context.getRecord("record").getValue("trs_template_ex");
		context.getRecord(outputNode).setValue("trs_template", trs_template);
		/*
		 * Map<String, String> map =
		 * getOhterColumn(context.getRecord(outputNode)
		 * .getValue("trs_service_id"));
		 * if(map.get("use_template").toString().equals("true")){
		 * context.getRecord(outputNode).setValue("trs_template",
		 * map.get("trs_template").toString()); }
		 */
	}

	/**
	 * ɾ��trs�ӿ���Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn40300005(TrsShareServiceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ɾ����¼�������б� VoTrsShareServicePrimaryKey primaryKey[] =
		// context.getPrimaryKeys( inputNode );
		table.executeFunction(DELETE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 
	 * txn40300006(ȡ���) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������ �C ��ѡ)
	 * TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public void txn40300006(TrsShareServiceContext context) throws TxnException
	{
		// System.out.println("txn40300006");
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("getMaxTrsService_no", context, inputNode,
				outputNode);
		//
		String trs_service_no = context.getRecord("record").getValue("snum");
		// System.out.println(service_no);
		trs_service_no = "trsService" + (Integer.parseInt(trs_service_no) + 1);
		// System.out.println(service_no);
		context.getRecord("record").setProperty("trs_service_no",
				trs_service_no);

		// System.out.println(context);
	}

	/**
	 * 
	 * txn40300007(��ѯTRS���п�) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������ �C ��ѡ)
	 * TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public void txn40300007(TrsShareServiceContext context) throws TxnException
	{
		// System.out.println("txn40300007");
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Attribute.setPageRow(context, outputNode, -1);
		String trs_db_str = java.util.ResourceBundle.getBundle("trs")
				.getString("searchDB");
		String[] trs_dbs = trs_db_str.split(",");
		List dataList = new ArrayList();
		for (int i = 0; i < trs_dbs.length; i++) {
			Map dataMap = new HashMap();
			dataMap.put("key", trs_dbs[i]);
			dataMap.put("title", trs_dbs[i]);
			dataList.add(dataMap);
		}
		context.setProperty("dataString", JsonDataUtil.toJSONString(dataList));
	}

	/**
	 * 
	 * txn40300008(�޸�����ͣ��״̬) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������ �C ��ѡ)
	 * TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public void txn40300008(TrsShareServiceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		for (int i = 0; i < context.getRecordset("primary-key").size(); i++) {
			String sql = "update trs_share_service t set t.service_state='";
			String service_id = context.getRecordset("primary-key").get(i)
					.getValue("trs_service_id");
			String service_state = context.getRecordset("primary-key").get(i)
					.getValue("service_state");
			if (service_state.equals("N")) {
				sql += ExConstant.SERVICE_STATE_N + "' ";
			} else {
				sql += ExConstant.SERVICE_STATE_Y + "' ";
			}
			sql += " where t.trs_service_id='" + service_id + "'";
			table.executeUpdate(sql);
			// System.out.println(sql);
		}
	}

	/**
	 * ����trs��������
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void txn40300010(TrsShareServiceContext context)
			throws TxnException, FileNotFoundException, IOException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		// ��ȡTRS������Ϣ
		table.executeFunction("getTrsServiceRule", context, inputNode, "rule");
		if (!context.getRecord("rule").isEmpty()) {
			Recordset rs = context.getRecordset("rule");
			Map map = new HashMap();
			List list=new ArrayList();
			for (int i = 0; i < rs.size(); i++) {
				DataBus db = rs.get(i);
				Map content = new HashMap();
				Map map_week = new HashMap();
				map_week.put("value", db.getValue("week"));
				map_week.put("name_cn", getWeekName(db.getValue("week")));
				content.put("week", map_week);
				content.put("datesStr",db.getValue("time_str"));
				content.put("times_day",db.getValue("times_day").equals("0") ? "" :db.getValue("times_day"));
				content.put("count_dat",db.getValue("count_dat").equals("0") ? "" :db.getValue("count_dat"));
				content.put("total_count_day",db.getValue("total_count_day").equals("0") ? "" :db.getValue("total_count_day"));
				list.add(content);
			}
			map.put("data", list);
			String limit_data=JSONUtils.toJsonStringByObj(map);
			context.getRecord("record").setValue("limit_data", limit_data);
		}

	}

	private String getWeekName(String weekVal)
	{
		String weekName = "";
		if (weekVal.equals("1")) {
			weekName = "����һ";
		} else if (weekVal.equals("2")) {
			weekName = "���ڶ�";
		} else if (weekVal.equals("3")) {
			weekName = "������";
		} else if (weekVal.equals("4")) {
			weekName = "������";
		} else if (weekVal.equals("5")) {
			weekName = "������";
		} else if (weekVal.equals("6")) {
			weekName = "������";
		} else {
			weekName = "������";
		}
		return weekName;
	}

	public void txn40300012(TrsShareServiceContext context)
			throws TxnException, FileNotFoundException, IOException
	{
		String jsondata2 = context.getRecord("record").getValue("limit_data");
		JSONArray jsonlimit_data = JsonDataUtil.getJsonArray(jsondata2, "data");
		String service_id = context.getRecord("record").getValue(
				"trs_service_id");

		// �洢�����Ʊ�
		ShareServiceRuleContext contextRule = new ShareServiceRuleContext();
		contextRule.getRecord("primary-key").setValue("service_id", service_id);

		// ��ɾ�ٲ�
		//if (jsonlimit_data.size() > 0) {
			this.insertShareServiceRule(jsonlimit_data, service_id);
		//}

	}

	public void insertShareServiceRule(JSONArray jsonlimit_data,
			String service_id) throws TxnException
	{
		ShareServiceRuleContext rulecontext = new ShareServiceRuleContext();
		DataBus dbPrimary = new DataBus();
		dbPrimary.setProperty("service_id", service_id);
		rulecontext.addRecord("primary-key", dbPrimary);
		callService("403007", rulecontext);
		for (int i = 0; i < jsonlimit_data.size(); i++) {
			JSONObject object = (JSONObject) jsonlimit_data.get(i);
			ShareServiceRuleContext contextRule = new ShareServiceRuleContext();
			DataBus db = contextRule.getRecord("record");
			db.setValue("service_id", service_id);
			db.setValue("week", ((JSONObject) object.get("week")).get("value").toString());
			String dateStr = object.getString("datesStr");
			db.setValue("times_day", object.get("times_day").toString());
			db.setValue("count_dat", object.get("count_dat").toString());
			db.setValue("total_count_day", object.get("total_count_day")
					.toString());
			Map<String, String[]> limit = splitDate(dateStr);
			String[] start = limit.get("start");
			String[] end = limit.get("end");
			for (int j = 0; j < start.length; j++) {
				db.setValue("start_time", start[j]);
				db.setValue("end_time", end[j]);
				callService("403003", contextRule);
			}
		}
	}

	/**
	 * ��ǰ̨������ʱ�����װ�����ݿ��Ӧ ��ʼ�����������ֶζ�Ӧ������
	 * 
	 * @param dateStr
	 * @return
	 */
	private Map<String, String[]> splitDate(String dateStr)
	{
		Map<String, String[]> dateMap = new HashMap<String, String[]>();
		if (StringUtils.isNotBlank(dateStr)) {
			String[] limit = dateStr.split(",");
			String[] start = new String[limit.length];
			String[] end = new String[limit.length];
			for (int i = 0; i < limit.length; i++) {
				String[] tmp = limit[i].split("-");
				if (tmp.length == 2) {
					start[i] = tmp[0];
					end[i] = tmp[1];
				}
			}
			dateMap.put("start", start);
			dateMap.put("end", end);
		} else {
			dateMap.put("start", new String[] {});
			dateMap.put("end", new String[] {});
		}

		return dateMap;
	}
	
	
	/**
	 * 
	 * txn40300013(�������������)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param context
	 * @throws TxnException        
	 * void       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public void txn40300013(TrsShareServiceContext context) throws TxnException,
				FileNotFoundException, IOException
	{
		context.getRecord("record2").setValue("service_targets_id",
				context.getRecord("record").getValue("service_targets_id"));
		context.getRecord("record2").setValue("service_type",
				context.getRecord("record").getValue("service_type"));
		context.getRecord("record2").setValue("service_state",
				context.getRecord("record").getValue("service_state"));

		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		//table.executeFunction("queryRule", context, inputNode, "rule");
		
		table.executeFunction("queryTrsRule", context, inputNode, "rule");
		if (!context.getRecord("rule").isEmpty()) {
			Recordset rs = context.getRecordset("rule");
			Map map = new HashMap();
			List list=new ArrayList();
			for (int i = 0; i < rs.size(); i++) {
				DataBus db = rs.get(i);
				Map content = new HashMap();
				Map map_week = new HashMap();
				map_week.put("value", db.getValue("week"));
				map_week.put("name_cn", getWeekName(db.getValue("week")));
				content.put("week", map_week);
				content.put("datesStr",db.getValue("start_time") + "-"+ db.getValue("end_time"));
				content.put("times_day",db.getValue("times_day"));
				content.put("count_dat",db.getValue("count_dat"));
				content.put("total_count_day",db.getValue("total_count_day"));
				list.add(content);
			}
			map.put("data", list);
			String limit_data=JSONUtils.toJsonStringByObj(map);
			context.getRecord("record").setValue("limit_data", limit_data);
		}

		String service_targets_id = context.getRecord(outputNode).getValue(
				"service_targets_id");
		String sql = "select t.service_targets_name,t.service_targets_no,t.service_password as service_password from res_service_targets t where t.service_targets_id='"
				+ service_targets_id + "'";
		//System.out.println("00016---"+sql);
		table.executeSelect(sql, context, "record3");
	}
	/**
	 * 
	 * getOhterColumn(ģ�����ļ���ʽ����ʹ����������������д) TODO(����������������������� �C ��ѡ)
	 * TODO(�����������������ִ������ �C ��ѡ) TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C
	 * ��ѡ)
	 * 
	 * @param svrId
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 *             Map<String,String>
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
	 */
	@SuppressWarnings("resource")
	public Map<String, String> getOhterColumn(String svrId) throws IOException,
			FileNotFoundException
	{
		// ��ʼ���ļ�
		// System.out.println(svrId);
		String svrPath = ExConstant.TRS_TEMPLATE;
		// String svrPath = "D:\\temp\\datafiles\\trs_template";
		// System.out.println("svrPath===="+svrPath);
		File file = new File(svrPath + File.separator + svrId + ".dat");
		// System.out.println(file.toString());
		// Map<String, Map<String, String>> colMap = new HashMap<String,
		// Map<String, String>>();
		Map<String, String> tmpMap = new HashMap<String, String>();
		// �ж��ļ��Ƿ����
		if (file.exists()) {
			InputStreamReader read = new InputStreamReader(new FileInputStream(
					file), "UTF-8");
			BufferedReader reader = new BufferedReader(read);
			String line = reader.readLine();
			// System.out.println("line==============>"+line);
			String tmp = "";
			while (line != null) {
				tmp += line;
				line = reader.readLine();
			}
			// System.out.println("line==============>"+line);
			if (tmp.equals("")) {
				tmpMap.put("use_template", "false");
			} else {
				tmpMap.put("trs_template", tmp);
				tmpMap.put("use_template", "true");
			}
		} else {
			tmpMap.put("use_template", "false");
		}
		// ���ļ�����

		return tmpMap;
	}

	public void setOhterColumn(Map<String, String> data, String svrId)
			throws IOException
	{
		// if(StringUtils.isNotBlank(svrNo)){
		String svrPath = ExConstant.TRS_TEMPLATE;
		// String svrId = data.get("service_id");
		if (StringUtils.isNotBlank(svrId)) {
			File file = new File(svrPath + File.separator + svrId + ".dat");
			// System.out.println(svrPath + File.separator + svrId + ".dat");
			// if(!file.exists()){
			// file.createNewFile();
			// }
			FileOutputStream fos = new FileOutputStream(file);
			Writer out = new OutputStreamWriter(fos, "UTF-8");
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(data.get("trs_template"));
			out.write(stringBuffer.toString());
			out.close();
			fos.close();
			// }
		}
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
		TrsShareServiceContext appContext = new TrsShareServiceContext(context);
		invoke(method, appContext);
	}

	public static void main(String[] args) throws TRSException
	{

		String temp = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?> <Results><#if ResultSize gt 0 ><ResultInfo>  <ResultSize>${ResultSize!}</ResultSize>  <StartIndex>${StartIndex!}</StartIndex>  <EndIndex>${EndIndex!}</EndIndex>  <UsedTime>${UsedTime!}</UsedTime>  <CurPage>${CurPage!}</CurPage>  <PageCount>${PageCount!}</PageCount>  <CollId>${CollId!}</CollId>  <Rows>${Rows!}</Rows>  <Query><![CDATA[${Query!}]]></Query></ResultInfo><ResultItems><#list results as x>  <Item>    <reg_bus_ent_id><![CDATA[${x.reg_bus_ent_id!}]]></reg_bus_ent_id>    <name><![CDATA[${x.name}]]></name>    <update_date><![CDATA[${x.update_date!}]]></update_date>    <type desc=\"����\"><![CDATA[${x.type!}]]></type>    <DetailInfo>             <dom desc=\"ס��\"><![CDATA[${x.dom!}]]></dom>                     <corp_rpt desc=\"����������/������\"><![CDATA[${x.corp_rpt!}]]></corp_rpt>                     <reg_no desc=\"ע���\"><![CDATA[${x.reg_no!}]]></reg_no>                     <name_hs desc=\"��������\"><![CDATA[${x.name_hs!}]]></name_hs>    </DetailInfo>  </Item></#list>  </ResultItems></#if></Results>";
		System.out.println(StringUtil.isXML(temp));
	}
}
