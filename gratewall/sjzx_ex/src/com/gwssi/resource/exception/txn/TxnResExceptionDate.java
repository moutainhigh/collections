package com.gwssi.resource.exception.txn;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.util.DateUtil;
import com.gwssi.common.util.JsonDataUtil;
import com.gwssi.common.util.UuidGenerator;
import com.gwssi.resource.exception.vo.ResExceptionDateContext;

public class TxnResExceptionDate extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap		txnMethods		= getAllMethod(
														TxnResExceptionDate.class,
														ResExceptionDateContext.class);

	// ���ݱ�����
	private static final String	TABLE_NAME		= "res_exception_date";

	// ��ѯ�б�
	private static final String	ROWSET_FUNCTION	= "select res_exception_date list";

	// ��ѯ��¼
	private static final String	SELECT_FUNCTION	= "select one res_exception_date";

	// �޸ļ�¼
	private static final String	UPDATE_FUNCTION	= "update one res_exception_date";

	// ���Ӽ�¼
	private static final String	INSERT_FUNCTION	= "insert one res_exception_date";

	// ɾ����¼
	private static final String	DELETE_FUNCTION	= "delete one res_exception_date";

	/**
	 * ���캯��
	 */
	public TxnResExceptionDate()
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
	 * ��ѯ���µ����������¼��������������ʾ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 * @throws ParseException
	 */
	public void txn205009(ResExceptionDateContext context) throws TxnException,
			ParseException
	{
		//System.out.println(context);
		BaseTable table = TableFactory.getInstance().getTableObject(this, TABLE_NAME);
		// ��ѯ��¼������ VoResExceptionDateSelectKey selectKey = context.getSelectKey(
		// inputNode );
		
		ResExceptionDateContext context1 = new ResExceptionDateContext();
		context1.addRecord("select-key", context.getRecord("select-key"));
		callService("205001", context1);
		Recordset rs = context1.getRecordset(outputNode);
		List holidayList = new ArrayList();
		for(int ii=0; ii<rs.size(); ii++){
			DataBus db = rs.get(ii);
			Map mapTmp = new HashMap();
			mapTmp.put("date", db.getValue("exception_date"));
			mapTmp.put("desc", db.getValue("exception_desc"));
			holidayList.add(mapTmp);
		}
		String date_str=JsonDataUtil.toJSONString(holidayList);
		//System.out.println(date_str);
		context1.remove(outputNode);
		
		context.getRecord("attribute-node").setValue("record_page-row", "10000");
		table.executeFunction("getTaskByMonth", context, inputNode, outputNode);
		rs = context.getRecordset(outputNode);
		List taskList = new ArrayList();
		for(int ii=0; ii<rs.size(); ii++){
			DataBus db = rs.get(ii);
			Map mapTmp = new HashMap();
			mapTmp.put("title", db.getValue("task_name"));
			mapTmp.put("start", db.getValue("ndate") + " " + db.get("start_time"));
			mapTmp.put("end", db.getValue("ndate") + " " + db.get("end_time"));
			mapTmp.put("id", db.getValue("collect_task_id"));
			mapTmp.put("allDay", false);
			taskList.add(mapTmp);
		}
		String obj_str=JsonDataUtil.toJSONString(taskList);
		//System.out.println(obj_str);
		context.remove("record");
		
		context.setValue("task_list_str", obj_str);
		context.setValue("holiday_str", date_str);
		//context1.setValue("holiday_str", date_str);
		//context1.setValue("task_list_str", obj_str);
		context1 = null;
	}

	/**
	 * 
	 * txn205001 ��ʱ��ε���ʽ��ѯ���е������¼
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public void txn205001(ResExceptionDateContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoResExceptionDateSelectKey selectKey = context.getSelectKey(
		context.getRecord("attribute-node").setValue("record_page-row", "365");
		table.executeFunction("getAllSvrDate", context, inputNode, outputNode);
		//callService("205009", context);
	}

	/**
	 * �޸�����������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn205002(ResExceptionDateContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// �޸ļ�¼������ VoResExceptionDate res_exception_date =
		// context.getResExceptionDate( inputNode );
		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * ��������������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 * @throws ParseException
	 */
	public void txn205003(ResExceptionDateContext context) throws TxnException,
			ParseException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ���Ӽ�¼������ VoResExceptionDate res_exception_date =
		// context.getResExceptionDate( inputNode );
		context.getRecord("record").setProperty("exception_type", "1");
		context.getRecord("record").setProperty("is_markup",
				ExConstant.IS_MARKUP_Y);
		context.getRecord("record").setProperty("created_time",
				DateUtil.getYMDHMSTime());
		context.getRecord("record").setProperty("last_modify_time",
				DateUtil.getYMDHMSTime());
		context.getRecord("record").setProperty("last_modify_id",
				context.getRecord("record").getValue("creator_id"));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date exception_date = sdf.parse(context.getRecord("record").getValue(
				"exception_date"));
		if (exception_date.getDay() == 0 || exception_date.getDay() == 6) {
			context.getRecord("record").setValue("exception_type", "2");
		}

		table.executeFunction(INSERT_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * ��ѯ�������������޸�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn205004(ResExceptionDateContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoResExceptionDatePrimaryKey primaryKey =
		// context.getPrimaryKey( inputNode );
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		// ��ѯ���ļ�¼���� VoResExceptionDate result = context.getResExceptionDate(
		// outputNode );
	}

	/**
	 * ɾ������������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn205005(ResExceptionDateContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ɾ����¼�������б� VoResExceptionDatePrimaryKey primaryKey[] =
		// context.getPrimaryKeys( inputNode );
		table.executeFunction(DELETE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 
	 * txn205006 ͨ������txn205003����������������ڼ�¼ ����������֮ǰ������Ѿ�����ͬ���ڵ����ݣ���ɾ������֤ÿ�ζ���������
	 * ÿ��ֻ��һ����¼����������֮ǰ������Ѿ�����ͬ���ڵ����ݣ���ɾ�� ��֤ÿ�ζ��������ݣ���ÿ��ֻ��һ����¼
	 * 
	 * @param context
	 * @throws ParseException
	 * @throws TxnException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public void txn205006(ResExceptionDateContext context) throws TxnException,
			ParseException
	{
		String start_date_str = context.getRecord("record").getValue(
				"exception_date");
		String end_date_str = context.getRecord("record").getValue("end_date");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date start_date = sdf.parse(start_date_str);
		Date end_Date = sdf.parse(end_date_str);
		Calendar ca = Calendar.getInstance();
		context.getRecord("record").setProperty("exception_id",
				UuidGenerator.getUUID());
		while (start_date.compareTo(end_Date) <= 0) {
			TxnContext tmpDB = context;
			ca.setTime(start_date);
			ca.add(Calendar.DATE, 1);
			tmpDB.getRecord("record").setValue("exception_date",
					sdf.format(start_date));
			TxnContext tmpDB1 = context;
			callService("205008", tmpDB1);
			tmpDB.getRecord("record").setValue("exception_date",
					sdf.format(start_date));
			callService("205003", tmpDB);
			tmpDB = null;
			start_date = ca.getTime();
		}
	}

	/**
	 * txn205007 �������ڲ���ɾ�����⣬ԭ����ÿ��ֻ��һ�����ݱ�ɾ������Ϊͬһ��ֻ��һ�������¼
	 * 
	 * @param context
	 * @throws TxnException
	 * @throws ParseException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public void txn205007(ResExceptionDateContext context) throws TxnException,
			ParseException
	{
		// BaseTable table = TableFactory.getInstance().getTableObject( this,
		// TABLE_NAME );
		String start_date_str = context.getRecord("record").getValue(
				"exception_date");
		String end_date_str = context.getRecord("record").getValue("end_date");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date start_date = sdf.parse(start_date_str);
		Date end_Date = sdf.parse(end_date_str);
		Calendar ca = Calendar.getInstance();
		while (start_date.compareTo(end_Date) <= 0) {
			TxnContext tmpDB = context;
			ca.setTime(start_date);
			ca.add(Calendar.DATE, 1);
			tmpDB.getRecord("record").setValue("exception_date",
					sdf.format(start_date));
			callService("205008", tmpDB);
			tmpDB = null;
			start_date = ca.getTime();
		}
		// table.executeFunction( "", context, inputNode, outputNode );
	}

	/**
	 * 
	 * txn205008 ɾ����������
	 * 
	 * @param context
	 * @throws TxnException
	 * @throws ParseException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public void txn205008(ResExceptionDateContext context) throws TxnException,
			ParseException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		TxnContext tmpdb = new TxnContext();
		tmpdb = context;
		String flag = checkServDate(context.getRecord("record").getValue(
				"exception_date"));
		if (flag.equals(ExConstant.IS_EXCEPTION_Y)) {
			table.executeFunction("getOneByDate", tmpdb, inputNode, outputNode);
			table.executeFunction("deleteByDate", context, inputNode, outputNode);
			table.executeFunction("updateByDeleteDate", tmpdb, inputNode, outputNode);
		}
	}

	/**
	 * 
	 * txn205010 ���û�ͣ����������
	 * 
	 * @param context
	 * @throws TxnException
	 * @throws ParseException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public void txn205010(ResExceptionDateContext context) throws TxnException,
			ParseException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("pauseAllDate", context, inputNode, outputNode);
	}

	/**
	 * 
	 * checkServDate ���ָ�������Ƿ�������
	 * 
	 * @param date ָ������ ���� "2013-03-15"
	 * @return "2000" ָ������������, "2001" ָ�����ڲ�������
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public String checkServDate(String date) throws TxnException,
			ParseException
	{

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date2 = sdf.parse(date);
		TxnContext context = new TxnContext();

		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) as num from res_exception_date");
		sql.append(" where is_markup = '").append(ExConstant.IS_MARKUP_Y)
				.append("'");
		sql.append(" and exception_date = '");
		sql.append(sdf.format(date2)).append("'");
		table.executeSelect(sql.toString(), context, "record");

		String num = context.getRecord("record").getValue("num");
		int count = Integer.parseInt(num);

		if (count > 0) {
			return ExConstant.IS_EXCEPTION_Y;
		} else {
			return ExConstant.IS_EXCEPTION_N;
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
		ResExceptionDateContext appContext = new ResExceptionDateContext(
				context);
		invoke(method, appContext);
	}
}
