package com.gwssi.share.service.txn;

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
import java.text.SimpleDateFormat;
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

import com.gwssi.common.Connect;
import com.gwssi.common.Test;
import com.gwssi.common.constant.ConstUploadFileType;
import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.constant.FileConstant;
import com.gwssi.common.constant.TaskSchedulingConstants;
import com.gwssi.common.servicejob.SrvTriggerRunner;
import com.gwssi.common.task.InterfaceData;
import com.gwssi.common.upload.UploadFileVO;
import com.gwssi.common.upload.UploadHelper;
import com.gwssi.common.util.DateUtil;
import com.gwssi.common.util.JSONUtils;
import com.gwssi.common.util.JsonDataUtil;
import com.gwssi.common.util.ParamUtil;
import com.gwssi.share.ftp.vo.VoShareSrvScheduling;
import com.gwssi.share.interfaces.txn.TxnShareInterface;
import com.gwssi.share.interfaces.vo.ShareInterfaceContext;
import com.gwssi.share.rule.vo.ShareServiceRuleContext;
import com.gwssi.share.service.vo.ShareServiceConditionContext;
import com.gwssi.share.service.vo.ShareServiceContext;
import com.gwssi.webservice.server.ServiceDAO;
import com.gwssi.webservice.server.ServiceDAOImpl;

public class TxnShareService extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap		txnMethods				= getAllMethod(
																TxnShareService.class,
																ShareServiceContext.class);

	// 数据表名称
	private static final String	TABLE_NAME				= "share_service";
	private static final String	TABLE_NAME_SHARE		= "sys_notice_info";

	// 查询列表
	private static final String	ROWSET_FUNCTION			= "select share_service list";

	// 查询记录
	private static final String	SELECT_FUNCTION			= "select one share_service";

	// 修改记录
	private static final String	UPDATE_FUNCTION			= "update one share_service";

	// 增加记录
	private static final String	INSERT_FUNCTION			= "insert one share_service";

	// 删除记录
	private static final String	DELETE_FUNCTION			= "delete one share_service";

	static String				NEED_INPUT_PARAMETER	= "\uFF08\u53C2\u6570\u503C\uFF09";

	/**
	 * 构造函数
	 */
	public TxnShareService()
	{

	}

	/**
	 * 初始化函数
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{

	}

	/**
	 * 查询服务表列表
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 * @throws IOException
	 */
	public void txn40200001(ShareServiceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 查询记录的条件 VoShareServiceSelectKey selectKey = context.getSelectKey(
		// inputNode );
		
		/**获取查询条件内容**/
		this.callService("40209001", context);
		/*
		 * String create_time = context.getRecord("select-key").getValue(
		 * "created_time"); if (StringUtils.isNotBlank(create_time)) { String[]
		 * ctime = DateUtil.getDateRegionByDatePicker(create_time, true);
		 * context.getRecord("select-key").setValue("created_time_start",
		 * ctime[0]);
		 * context.getRecord("select-key").setValue("created_time_end",
		 * ctime[1]); context.getRecord("select-key").remove("created_time"); }
		 */
		
		/**获取服务内容**/
		table.executeFunction("queryShareServiceList", context, inputNode,
				outputNode);
		// 查询到的记录集 VoShareService result[] = context.getShareServices(
		// outputNode );
	}

	/**
	 * 修改服务表信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn40200002(ShareServiceContext context) throws TxnException
	{
		String userId = context.getRecord("oper-data").getValue("userID");
		context.getRecord("record").setValue("last_modify_id", userId);
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new java.util.Date());
		context.getRecord("record").setValue("last_modify_time", datetime);

		// 附件部分
		// 获取页面上的会议材料和被删除的会议材料及各自的ID
		String delIDs = context.getRecord(inputNode).getValue("delIDs");
		String delNAMEs = context.getRecord(inputNode).getValue("delNAMEs");
		String hyclid = context.getRecord(inputNode).getValue("fj_fk");
		String hycl = context.getRecord(inputNode).getValue("fjmc");

		// 生成一个UploadFileVO对象，保存政务管理类型的多附件
		UploadFileVO fileVO = new UploadFileVO();
		fileVO.setRecordName("record:fjmc");
		fileVO.setDeleteId(delIDs);// 页面保存的被删除附件Id值
		fileVO.setDeleteName(delNAMEs);// 页面保存的被删除附件name值
		fileVO.setOriginId(hyclid);// 更新附件前业务数据库表附件id字段存储的值
		fileVO.setOriginName(hycl);// 更新附件前业务数据库表附件name字段存储的值
		fileVO.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);// 多附件
		UploadFileVO vo = UploadHelper.updateFile(context, fileVO,
				ConstUploadFileType.SHARECORD);// 附件类型为FTP

		// 将附件信息传递到inputNode
		context.getRecord(inputNode).setValue("fj_fk", vo.getReturnId());
		context.getRecord(inputNode).setValue("fjmc", vo.getReturnName());

		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 修改记录的内容 VoShareService share_service = context.getShareService(
		// inputNode );
		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 增加服务表信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 * @throws IOException
	 */
	public void txn40200003(ShareServiceContext context) throws TxnException,
			IOException
	{
		String userId = context.getRecord("oper-data").getValue("userID");
		context.getRecord("record").setValue("creator_id", userId);
		context.getRecord("record").setValue("last_modify_id", userId);
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new java.util.Date());
		context.getRecord("record").setValue("created_time", datetime);
		context.getRecord("record").setValue("last_modify_time", datetime);

		// 附件部分
		UploadFileVO fileVO1 = new UploadFileVO();
		fileVO1.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);
		UploadFileVO vo = UploadHelper.saveFile(context, fileVO1,
				ConstUploadFileType.SHARECORD);
		// 将附件信息传递到inputNode
		context.getRecord(inputNode).setValue("fj_fk", vo.getReturnId());
		context.getRecord(inputNode).setValue("fjmc", vo.getReturnName());
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 增加记录的内容 VoShareService share_service = context.getShareService(
		// inputNode );
		//System.out.println("新增---"+context);
		table.executeFunction(INSERT_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 查询服务表用于修改
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 * @throws IOException
	 */

	public void txn40200004(ShareServiceContext context) throws TxnException,
			IOException
	{
		// System.out.println("txn40200004="+context);
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 查询记录的主键 VoShareServicePrimaryKey primaryKey = context.getPrimaryKey(
		// inputNode );
		
		String service_id = context.getRecord("primary-key").getValue("service_id");
		StringBuffer sql = new StringBuffer();
		
		sql.append("select t1.*,t1.created_time as cretime,t1.last_modify_time as modtime, yh1.yhxm as crename,yh2.yhxm as modname from share_service t1,xt_zzjg_yh_new yh1,xt_zzjg_yh_new yh2");
		sql.append(" where  t1.creator_id = yh1.yhid_pk(+)  and t1.last_modify_id = yh2.yhid_pk(+)  and t1.service_id = '");
		sql.append(service_id);
		sql.append("'");
		table.executeRowset(sql.toString(), context, outputNode);
		
		// 查询到的记录内容 VoShareService result = context.getShareService( outputNode
		// );
		Map<String, String> map = getOhterColumn(context.getRecord(outputNode)
				.getValue("service_id"));
		context.getRecord(outputNode)
				.setValue("sql", map.get("sql").toString());
		context.getRecord(outputNode).setValue("column_name_en",
				map.get("column_name_en").toString());
		context.getRecord(outputNode).setValue("column_name_cn",
				map.get("column_name_cn").toString());
		context.getRecord(outputNode).setValue("column_no",
				map.get("column_no").toString());
		context.getRecord(outputNode).setValue("jsoncolumns",
				map.get("jsoncolumns").toString());

		// 从输出节点中获取附件的ID
		String fjids = context.getRecord(outputNode).getValue("fj_fk");

		// 从输出节点中获取附件的名称
		String filenames = context.getRecord(outputNode).getValue("fjmc");

		// 调用接口将获取的文件信息一一传回context
		UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");

		// System.out.println(context);
	}

	@SuppressWarnings("resource")
	public Map<String, String> getOhterColumn(String svrId) throws IOException,
			FileNotFoundException
	{
		// 开始读文件
		String svrPath = ExConstant.SHARE_CONFIG;
		File file = new File(svrPath + File.separator + svrId + ".dat");
		// Map<String, Map<String, String>> colMap = new HashMap<String,
		// Map<String, String>>();
		Map<String, String> tmpMap = null;
		// 判断文件是否存在
		if (file.exists()) {
			tmpMap = new HashMap<String, String>();
			InputStreamReader read = new InputStreamReader(new FileInputStream(
					file), "UTF-8");
			BufferedReader reader = new BufferedReader(read);
			String line = reader.readLine();
			while (line != null) {
				String[] cols = line.split("###");
				tmpMap.put("service_id", cols[0]);
				tmpMap.put("column_no", cols[1]);
				tmpMap.put("column_name_cn", cols[2]);
				tmpMap.put("sql", cols[3]);
				tmpMap.put("column_name_en", cols[4]);
				tmpMap.put("jsoncolumns", cols[5]);
				line = reader.readLine();
			}
		}
		// 读文件结束

		return tmpMap;
	}

	public void setOhterColumn(Map<String, String> data, String svrId)
			throws IOException
	{
		// if(StringUtils.isNotBlank(svrNo)){
		String svrPath = ExConstant.SHARE_CONFIG;
		// String svrId = data.get("service_id");
		if (StringUtils.isNotBlank(svrId)) {
			File file = new File(svrPath + File.separator + svrId + ".dat");
			//System.out.println(svrPath + File.separator + svrId + ".dat");
			// if(!file.exists()){
			// file.createNewFile();
			// }
			FileOutputStream fos = new FileOutputStream(file);
			Writer out = new OutputStreamWriter(fos, "UTF-8");
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(svrId).append("###").append(
					data.get("column_no")).append("###").append(
					data.get("column_name_cn")).append("###").append(
					data.get("sql")).append("###").append(
					data.get("column_name_en")).append("###").append(
					data.get("jsoncolumns"));
			out.write(stringBuffer.toString());
			out.close();
			fos.close();
			// }
		}
	}

	/**
	 * 删除服务表信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn40200005(ShareServiceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 删除记录的主键列表 VoShareServicePrimaryKey primaryKey[] =
		// context.getPrimaryKeys( inputNode );
		table.executeFunction(DELETE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 增加服务表信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn40200006(ShareServiceContext context) throws TxnException
	{
		String userId = context.getRecord("oper-data").getValue("userID");
		context.getRecord("record").setValue("creator_id", userId);
		context.getRecord("record").setValue("last_modify_id", userId);
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new java.util.Date());
		context.getRecord("record").setValue("created_time", datetime);
		context.getRecord("record").setValue("last_modify_time", datetime);
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 增加记录的内容 VoShareService share_service = context.getShareService(
		// inputNode );
		table.executeFunction(INSERT_FUNCTION, context, inputNode, outputNode);
		//System.out.println("0006---"+context);
	}

	/**
	 * 
	 * txn40200007(根据接口ID获取数据表列表) TODO(这里描述这个方法适用条件 – 可选) TODO(这里描述这个方法的执行流程 –
	 * 可选) TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 – 可选)
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public void txn40200007(ShareServiceContext context) throws TxnException,
			FileNotFoundException, IOException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String interface_id = context.getRecord("select-key").getValue(
				"interface_id");
		String sql = "select t.table_id,t.table_name_cn,t.sql as interfacesql from share_interface t where t.interface_id='"
				+ interface_id + "'";
		table.executeRowset(sql, context, "record");
		Recordset rs = context.getRecordset("record");
		List dataList = new ArrayList();
		DataBus db0 = rs.get(0);
		String[] table_id_list = db0.get("table_id").toString().split(",");
		String[] table_name_cn_list = db0.get("table_name_cn").toString()
				.split(",");
		for (int i = 0; i < table_id_list.length; i++) {

			Map dataMap = new HashMap();
			dataMap.put("key", table_id_list[i]);
			dataMap.put("title", table_name_cn_list[i]);
			dataList.add(dataMap);
		}
		context.getRecord("record").setProperty("dataString",
				JSONUtils.toJSONString(dataList));

		// 查询表查询条件
		TxnShareInterface txnShareInterface = new TxnShareInterface();
		BaseTable table2 = TableFactory.getInstance().getTableObject(
				txnShareInterface, "share_interface");
		ShareInterfaceContext context_param = new ShareInterfaceContext();
		context_param.setValue("key", interface_id);
		table2.executeFunction("queryTableParam", context_param, inputNode,
				outputNode);
		Recordset rs_param = context_param.getRecordset("share_interface");
		// System.out.println(context_param);
		List paramList = new ArrayList();
		for (int i = 0; i < rs_param.size(); i++) {
			DataBus db = rs_param.get(i);
			Map dataMap = new HashMap();
			// System.out.println(db.getValue("frist_connector"));

			dataMap.put("logic", db.getValue("frist_connector"));
			dataMap.put("leftParen", db.getValue("left_paren"));

			Map left_table = new HashMap();
			left_table.put("id", db.getValue("table_no"));
			left_table.put("name_en", db.getValue("table_name_en"));
			left_table.put("name_cn", db.getValue("table_name_cn"));
			dataMap.put("table", left_table);

			Map left_column = new HashMap();
			left_column.put("id", db.getValue("column_no"));
			left_column.put("name_en", db.getValue("dataitem_name_en"));
			left_column.put("name_cn", db.getValue("dataitem_name_cn"));
			dataMap.put("column", left_column);

			dataMap.put("paren", db.getValue("param_type"));
			dataMap.put("param_value", db.getValue("param_value"));
			dataMap.put("rightParen", db.getValue("right_paren"));
			paramList.add(dataMap);
		}
		context.getRecord("record").setProperty("tableParam",
				JsonDataUtil.toJSONString(paramList));

		// 查询使用该接口的服务
		ShareServiceContext ssContext = new ShareServiceContext();
		ssContext.addRecord("select-key", context.getRecord("select-key"));
		table.executeFunction("queryShareServiceList", ssContext, inputNode,
				outputNode);
		rs = ssContext.getRecordset("share_service");
		List svrList = new ArrayList();
		for (int ii = 0; ii < rs.size(); ii++) {
			DataBus db = rs.get(ii);
			Map dataMap = new HashMap();
			dataMap.put("service_id", db.get("service_id"));
			dataMap.put("service_no", db.get("service_no"));
			dataMap.put("service_name", db.get("service_name"));
			svrList.add(dataMap);
		}
		// context.remove("share_service");
		context.getRecord("record").setValue("sql_one",
				JsonDataUtil.toJSONString(svrList));
		// System.out.println("\n\n"+context);
	}

	/**
	 * 
	 * txn40200008(根据数据表ID获取数据项) 
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public void txn40200008(ShareServiceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String share_table_id = context.getRecord("select-key").getValue(
				"share_table_id");
		// System.out.println(context);
		ShareServiceContext context2 = new ShareServiceContext();
		String sql = "select t.share_dataitem_id,t.dataitem_name_cn,t.code_table,t.dataitem_name_en,t.dataitem_type_r,t.share_table_id,t2.table_name_cn,t2.table_name_en,d.codename, t.dataitem_long "
				+ "from res_share_dataitem t,res_share_table t2,codedata d where  t2.table_name_cn is not null and t.dataitem_name_cn is not null and  t.share_table_id='"
				+ share_table_id
				+ "' and t2.share_table_id='"
				+ share_table_id
				+ "' "
				+ "and  t.dataitem_type=d.codevalue(+) and d.codetype='\u5B57\u6BB5\u6570\u636E\u7C7B\u578B'  order by t.dataitem_name_cn ";
		//System.out.println(sql);
		table.executeRowset(sql, context2, "record");
		Recordset rs = context2.getRecordset("record");
		List dataList = new ArrayList();

		for (int i = 0; i < rs.size(); i++) {
			DataBus db = rs.get(i);
			Map dataMap = new HashMap();
			dataMap.put("key", db.get("share_dataitem_id"));
			dataMap.put("title", db.get("dataitem_name_cn"));
			dataMap.put("dataitem_name_en", db.get("dataitem_name_en"));
			dataMap.put("share_table_id", db.get("share_table_id"));
			dataMap.put("table_name_cn", db.get("table_name_cn"));
			dataMap.put("table_name_en", db.get("table_name_en"));
			dataMap.put("codename", db.get("codename"));
			dataMap.put("dataitem_long", db.get("dataitem_long"));
			dataMap.put("code_table", db.get("code_table"));
			dataMap.put("dataitem_type", db.get("dataitem_type_r"));
			dataList.add(dataMap);
		}
		// context.getRecord("record").setProperty("dataString",
		// JSONUtils.toJSONString(dataList));
		context.setProperty("dataString", JSONUtils.toJSONString(dataList));

		// System.out.println(context);
	}

	/**
	 * 
	 * txn40200009(新增服务对象和服务条件) 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @throws IOException
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public void txn40200009(ShareServiceContext context) throws TxnException,
			IOException
	{
		String jsondata = context.getRecord("record").getValue("jsoncolumns");
		JSONArray jsoncolumns = JsonDataUtil.getJsonArray(jsondata, "columns");// 用于展示的字段
		JSONArray jsonconditions = JsonDataUtil.getJsonArray(jsondata,
				"conditions");// 先定好参数的条件
		JSONArray jsonparams = JsonDataUtil.getJsonArray(jsondata, "params");// 需要输入参数的条件
		String jsondata2 = context.getRecord("record").getValue("limit_data");
		JSONArray jsonlimit_data = JsonDataUtil.getJsonArray(jsondata2, "data");

		// String interface_id =
		// context.getRecord("record").getValue("interface_id");
		String sql = "";
		// try {
		// sql =
		// URLDecoder.decode(context.getRecord("record").getValue("sql"),"UTF-8");
		sql = context.getRecord("record").getValue("sql");
		// } catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// System.out.println("sql="+sql);
		// 从页面直接取拼好的SQL了。
		/*
		 * ShareServiceContext contextinterface = new ShareServiceContext();
		 * contextinterface
		 * .getRecord("select-key").setValue("interface_id",interface_id);
		 * callService("40200007",contextinterface);//在接口表中查询原始sql String sql =
		 * contextinterface.getRecord("record").getValue("interfacesql");
		 * 
		 * String sqlSelect=""; String sqlCondition=""; String sqlValue="";
		 */

		String column_no = "";
		String column_name_en = "";
		String column_name_cn = "";
		String column_alias = "";
		if (jsoncolumns.size() > 0) {
			JSONArray columns = jsoncolumns;
			for (int i = 0; i < columns.size(); i++) {
				JSONObject object = (JSONObject) columns.get(i);
				JSONObject table = object.getJSONObject("table");
				JSONObject cols = object.getJSONObject("column");
				// 从页面直接取拼好的SQL了。
				// sqlSelect +=
				// table.get("name_en")+"."+cols.get("name_en")+",";
				column_no += cols.get("id") + ",";
				column_name_en += cols.get("name_en") + ",";
				column_name_cn += cols.get("name_cn") + ",";
				column_alias += object.get("alias") + ",";
			}
		}
		column_no = column_no.substring(0, column_no.length() - 1);
		column_name_en = column_name_en.substring(0,
				column_name_en.length() - 1);
		column_name_cn = column_name_cn.substring(0,
				column_name_cn.length() - 1);
		context.getRecord("record").setValue("column_no", " ");
		context.getRecord("record").setValue("column_name_en", " ");
		context.getRecord("record").setValue("column_name_cn", " ");
		context.getRecord("record").setValue("column_alias", column_alias);

		// 从页面直接取拼好的SQL了。
		/*
		 * //参数部分，拼SQL同时插入share_service_condition表，表中以字段NEED_INPUT值Y代表需要调用时输入参数
		 * if (jsonparams.size()>0) { sqlValue =
		 * spliceConditionSqlFromJSONArray(jsonparams); }
		 * //条件部分，拼SQL同时插入share_service_condition表，表中以字段NEED_INPUT值N代表不需要调用时输入参数
		 * if (jsonconditions.size()>0) { sqlCondition =
		 * spliceConditionSqlFromJSONArray(jsonconditions); }
		 * 
		 * sqlSelect = sqlSelect.substring(0, sqlSelect.length()-1); sql =
		 * sql.replaceFirst("[*]", sqlSelect);
		 * 
		 * sql += " and (" + sqlCondition +") "; sql += " and (" + sqlValue +") ";
		 * System.out.println("sql="+sql);
		 */
		// 插入数据前把单引号替换成两个单引号
		// 存储在文件中
		/*
		 * if(sql.length()>4000){ if(sql.length()>8000){
		 * context.getRecord("record").setValue("sql",sql.substring(0,
		 * 3000).replaceAll("'","''"));
		 * context.getRecord("record").setValue("sql_one",sql.substring(3000,
		 * 7000).replaceAll("'","''"));
		 * context.getRecord("record").setValue("sql_two"
		 * ,sql.substring(7000).replaceAll("'","''")); } else{
		 * context.getRecord("record").setValue("sql",sql.substring(0,
		 * 3000).replaceAll("'","''"));
		 * context.getRecord("record").setValue("sql_one"
		 * ,sql.substring(3000).replaceAll("'","''")); } } else{
		 * context.getRecord("record").setValue("sql",sql.replaceAll("'","''")); }
		 */
		context.getRecord("record").setValue("sql", " ");
		context.getRecord("record").setValue("sql_one", " ");
		context.getRecord("record").setValue("sql_two", " ");
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		// 新增前重新生成一次serviceNO防止时间太久重复
		boolean notFirst = true;
		ShareServiceContext context_NO = new ShareServiceContext();
		try {
			callService("40200012", context_NO);
		} catch (TxnException e) {
			notFirst = false;
		}
		if (notFirst) {
			context.getRecord("record").setValue("service_no",
					context_NO.getValue("service_no"));
		}
		context.getRecord("record").setValue("old_service_no",
				context.getRecord("record").getValue("service_no"));

		context.getRecord("record").setValue("jsoncolumns", " ");

		// 拼SQL然后调用txn40200003，存储到服务表
		callService("40200003", context);
		String service_id = context.getRecord("record").getValue("service_id");
		//System.out.println("svrId = " + service_id);
		Map<String, String> tmpMap = new HashMap<String, String>();
		tmpMap.put("service_id", service_id);
		tmpMap.put("column_no", column_no);
		tmpMap.put("column_name_cn", column_name_cn);
		tmpMap.put("sql", sql);
		tmpMap.put("column_name_en", column_name_en);
		tmpMap.put("jsoncolumns", jsondata);
		setOhterColumn(tmpMap, service_id);
		// 存储到条件表
		if (jsonconditions.size() > 0) {
			JSONArray columns = jsonconditions;
			insertConditions(columns, service_id, "N");
		}
		if (jsonparams.size() > 0) {
			JSONArray columns = jsonparams;
			insertConditions(columns, service_id, "Y");
		}
		// 存储到限制表
		if (jsonlimit_data.size() > 0) {
			insertShareServiceRule(jsonlimit_data, service_id);
		}

	}

	/**
	 * 
	 * txn40200010(修改服务对象和服务条件) TODO(这里描述这个方法适用条件 – 可选) TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选) TODO(这里描述这个方法的使用方法 – 可选)
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @throws IOException
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public void txn40200010(ShareServiceContext context) throws TxnException,
			IOException
	{
		String jsondata = context.getRecord("record").getValue("jsoncolumns");
		JSONArray jsoncolumns = JsonDataUtil.getJsonArray(jsondata, "columns");// 用于展示的字段
		JSONArray jsonconditions = JsonDataUtil.getJsonArray(jsondata,
				"conditions");// 先定好参数的条件
		JSONArray jsonparams = JsonDataUtil.getJsonArray(jsondata, "params");// 需要输入参数的条件

		String jsondata2 = context.getRecord("record").getValue("limit_data");
		JSONArray jsonlimit_data = JsonDataUtil.getJsonArray(jsondata2, "data");

		// String interface_id =
		// context.getRecord("record").getValue("interface_id");
		String sql = "";
		// try {
		// sql =
		// URLDecoder.decode(context.getRecord("record").getValue("sql"),"UTF-8");
		sql = context.getRecord("record").getValue("sql");
		// } catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// System.out.println("sql="+sql);
		// 从页面直接取拼好的SQL了。
		/*
		 * ShareServiceContext contextinterface = new ShareServiceContext();
		 * contextinterface
		 * .getRecord("select-key").setValue("interface_id",interface_id);
		 * callService("40200007",contextinterface);//在接口表中查询原始sql String sql =
		 * contextinterface.getRecord("record").getValue("interfacesql");
		 * System.out.println("sql="+sql);
		 * 
		 * String sqlSelect=""; String sqlCondition=""; String sqlValue="";
		 */

		String column_no = "";
		String column_name_en = "";
		String column_name_cn = "";
		String column_alias = "";
		if (jsoncolumns.size() > 0) {
			JSONArray columns = jsoncolumns;
			for (int i = 0; i < columns.size(); i++) {
				JSONObject object = (JSONObject) columns.get(i);
				JSONObject table = object.getJSONObject("table");
				JSONObject cols = object.getJSONObject("column");
				// sqlSelect +=
				// table.get("name_en")+"."+cols.get("name_en")+",";
				column_no += cols.get("id") + ",";
				column_name_en += cols.get("name_en") + ",";
				column_name_cn += cols.get("name_cn") + ",";
				column_alias += object.get("alias") + ",";
			}
		}
		column_no = column_no.substring(0, column_no.length() - 1);
		column_name_en = column_name_en.substring(0,
				column_name_en.length() - 1);
		column_name_cn = column_name_cn.substring(0,
				column_name_cn.length() - 1);
		context.getRecord("record").setValue("column_no", " ");
		context.getRecord("record").setValue("column_name_en", " ");
		context.getRecord("record").setValue("column_name_cn", " ");
		context.getRecord("record").setValue("column_alias", column_alias);

		// 从页面直接取拼好的SQL了。
		/*
		 * //参数部分，拼SQL同时插入share_service_condition表，表中以字段NEED_INPUT值Y代表需要调用时输入参数
		 * if (jsonparams.size()>0) { sqlValue =
		 * spliceConditionSqlFromJSONArray(jsonparams); }
		 * //条件部分，拼SQL同时插入share_service_condition表，表中以字段NEED_INPUT值N代表不需要调用时输入参数
		 * if (jsonconditions.size()>0) { sqlCondition =
		 * spliceConditionSqlFromJSONArray(jsonconditions); } sqlSelect =
		 * sqlSelect.substring(0, sqlSelect.length()-1);
		 * System.out.println(sqlSelect); System.out.println(sqlCondition);
		 * System.out.println(sqlValue); sql = sql.replaceFirst("[*]",
		 * sqlSelect);
		 * 
		 * sql += " and (" + sqlCondition +") "; sql += " and (" + sqlValue +") ";
		 * System.out.println("sql="+sql);
		 */

		// 插入数据前把单引号替换成两个单引号
		/*
		 * if(sql.length()>4000){ if(sql.length()>8000){
		 * context.getRecord("record").setValue("sql",sql.substring(0,
		 * 3000).replaceAll("'","''"));
		 * context.getRecord("record").setValue("sql_one",sql.substring(3000,
		 * 7000).replaceAll("'","''"));
		 * context.getRecord("record").setValue("sql_two"
		 * ,sql.substring(7000).replaceAll("'","''")); } else{
		 * context.getRecord("record").setValue("sql",sql.substring(0,
		 * 3000).replaceAll("'","''"));
		 * context.getRecord("record").setValue("sql_one"
		 * ,sql.substring(3000).replaceAll("'","''")); } } else{
		 * context.getRecord("record").setValue("sql",sql.replaceAll("'","''")); }
		 */

		context.getRecord("record").setValue("sql", " ");
		context.getRecord("record").setValue("sql_one", " ");
		context.getRecord("record").setValue("sql_two", " ");
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String service_id = context.getRecord("record").getValue("service_id");

		// 循环存储到条件表
		ShareServiceConditionContext contextCondition = new ShareServiceConditionContext();
		contextCondition.getRecord("select-key").setValue("service_id",
				service_id);
		callService("40210006", contextCondition);// 修改之前先删除
		if (jsonconditions.size() > 0) {
			JSONArray columns = jsonconditions;
			insertConditions(columns, service_id, "N");
		}
		if (jsonparams.size() > 0) {
			JSONArray columns = jsonparams;
			insertConditions(columns, service_id, "Y");
		}

		// 存储到限制表
		ShareServiceRuleContext contextRule = new ShareServiceRuleContext();
		contextRule.getRecord("primary-key").setValue("service_id", service_id);

		
		if (jsonlimit_data.size() > 0) {
			//记录新规则
			insertShareServiceRule(jsonlimit_data, service_id);
		}else{
			//删除旧规则
			ShareServiceRuleContext rulecontext = new ShareServiceRuleContext();
			DataBus dbPrimary = new DataBus();
			dbPrimary.setProperty("service_id", service_id);
			rulecontext.addRecord("primary-key", dbPrimary);
			callService("403007", rulecontext);//根据service_id删除旧的规则
		}

		Map<String, String> tmpMap = new HashMap<String, String>();
		tmpMap.put("service_no", context.getRecord("record").getValue(
				"service_no"));
		tmpMap.put("column_no", column_no);
		tmpMap.put("column_name_cn", column_name_cn);
		tmpMap.put("sql", sql);
		tmpMap.put("column_name_en", column_name_en);
		tmpMap.put("jsoncolumns", jsondata);
		tmpMap.put("service_id", service_id);
		setOhterColumn(tmpMap, context.getRecord("record").getValue(
				"service_id"));
		context.getRecord("record").setValue("jsoncolumns", " ");
		// 拼SQL然后调用txn40200002，更新到服务表
		callService("40200002", context);
	}

	public void insertShareServiceRule(JSONArray jsonlimit_data,
			String service_id) throws TxnException
	{
		ShareServiceRuleContext rulecontext = new ShareServiceRuleContext();
		DataBus dbPrimary = new DataBus();
		dbPrimary.setProperty("service_id", service_id);
		rulecontext.addRecord("primary-key", dbPrimary);
		callService("403007", rulecontext);//根据service_id删除旧的规则
		for (int i = 0; i < jsonlimit_data.size(); i++) {
			JSONObject object = (JSONObject) jsonlimit_data.get(i);
			ShareServiceRuleContext contextRule = new ShareServiceRuleContext();
			DataBus db = contextRule.getRecord("record");
			db.setValue("service_id", service_id);

			db.setValue("week", ((JSONObject) object.get("week")).get("value")
					.toString());
			String dateStr = object.getString("datesStr");
			
			/*String stime = ((JSONObject) object.get("datesStr")).get("stime").toString();
			String etime = ((JSONObject) object.get("datesStr")).getString("etime");*/
			//db.setValue("start_time", object.get("start_time").toString());
			//db.setValue("end_time", object.get("end_time").toString());
			db.setValue("times_day", object.get("times_day").toString());
			db.setValue("count_dat", object.get("count_dat").toString());
			db.setValue("total_count_day", object.get("total_count_day")
					.toString());
			Map<String, String[]> limit = splitDate(dateStr);
			String[] start = limit.get("start");
			String[] end = limit.get("end");
			for(int j=0; j<start.length; j++){
				db.setValue("start_time", start[j]);
				db.setValue("end_time", end[j]);
				callService("403003", contextRule);
			}
		}
	}

	/**
	 * 将前台传来的时间段组装成数据库对应
	 * 起始、结束两个字段对应的数组
	 * @param dateStr
	 * @return
	 */
	private Map<String, String[]> splitDate(String dateStr){
		Map<String, String[]> dateMap = new HashMap<String, String[]>();
		if(StringUtils.isNotBlank(dateStr)){
			String[] limit = dateStr.split(",");
			String[] start = new String[limit.length];
			String[] end = new String[limit.length];
			for(int i=0; i<limit.length; i++){
				String[] tmp = limit[i].split("-");
				if(tmp.length == 2){
					start[i] = tmp[0];
					end[i] = tmp[1];
				}
			}
			dateMap.put("start", start);
			dateMap.put("end", end);
		}else{
			dateMap.put("start", new String[]{});
			dateMap.put("end", new String[]{});
		}
		
		return dateMap;
	}
	
	private void insertConditions(JSONArray columns, String service_id,
			String NEED_INPUT) throws TxnException
	{
		for (int i = 0; i < columns.size(); i++) {
			ShareServiceConditionContext contextCondition = new ShareServiceConditionContext();
			DataBus db = contextCondition.getRecord("record");
			db.setValue("need_input", NEED_INPUT);
			db.setValue("service_id", service_id);

			JSONObject object = (JSONObject) columns.get(i);
			db.setValue("frist_connector", ((JSONObject) object.get("logic"))
					.get("value").toString());
			db.setValue("left_paren", object.get("leftParen").toString());
			db.setValue("table_name_en", ((JSONObject) object.get("table"))
					.get("name_en").toString());
			db.setValue("table_name_cn", ((JSONObject) object.get("table"))
					.get("name_cn").toString());
			db.setValue("column_name_en", ((JSONObject) object.get("column"))
					.get("name_en").toString());
			db.setValue("column_name_cn", ((JSONObject) object.get("column"))
					.get("name_cn").toString());
			db.setValue("second_connector", ((JSONObject) object.get("paren"))
					.get("value").toString());
			db.setValue("param_value", ((JSONObject) object.get("param_value"))
					.get("value").toString());
			db.setValue("param_type", ((JSONObject) object.get("column")).get(
					"col_type").toString());
			db.setValue("right_paren", object.get("rightParen").toString());
			db.setValue("show_order", i + 1 + "");

			callService("40210003", contextCondition);

		}
	}

	private String spliceConditionSqlFromJSONArray(JSONArray columns)
	{
		String sql = "";
		for (int i = 0; i < columns.size(); i++) {

			JSONObject object = (JSONObject) columns.get(i);
			sql += " " + object.get("logic") + " ";
			sql += (object.get("leftParen") == null || object.get("leftParen")
					.toString().equals("")) ? " " : object.get("leftParen");
			sql += " " + ((JSONObject) object.get("table")).get("name_en")
					+ "." + ((JSONObject) object.get("column")).get("name_en")
					+ " " + object.get("paren");
			sql += "'";

			if (object.get("param_value").equals(NEED_INPUT_PARAMETER)) {
				sql += "（参数" + (i + 1) + "）";
			} else {
				sql += object.get("param_value");
			}
			sql += "'";
			sql += (object.get("rightParen") == null || object
					.get("rightParen").toString().equals("")) ? " " : object
					.get("rightParen");
		}
		return sql;
	}

	/**
	 * 
	 * txn40200011(设置标志位逻辑删除) TODO(这里描述这个方法适用条件 – 可选) TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 – 可选)
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public void txn40200011(ShareServiceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("setIsmarkupAsDelete", context, inputNode,
				outputNode);
		String type = context.getRecord(inputNode).getValue("service_type");
		if (type != null && "FTP".equals(type)) {
			// 删除ftp共享服务信息
			table.executeFunction("deleteFtpService", context, inputNode,
					outputNode);
		}
	}

	/**
	 * 
	 * txn40200012(获取服务编号) TODO(这里描述这个方法适用条件 – 可选) TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 – 可选)
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public void txn40200012(ShareServiceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("getMaxService_no", context, inputNode,
				outputNode);
		// System.out.println(context);
		String service_no = context.getValue("snum");
		service_no = "service" + (Integer.parseInt(service_no) + 1);
		context.setProperty("service_no", service_no);

	}

	/**
	 * 
	 * txn40200013(修改启用停用) TODO(这里描述这个方法适用条件 – 可选) TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 – 可选)
	 * 
	 * @param context
	 * @throws Exception
	 * @throws TxnException
	 *             void
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public void txn40200013(ShareServiceContext context) throws Exception
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// System.out.println(context);
		ServiceDAO daoTable = new ServiceDAOImpl(); // 操作数据表Dao
		for (int i = 0; i < context.getRecordset("primary-key").size(); i++) {
			String sql = "update share_service t set t.service_state='";
			String service_id = context.getRecordset("primary-key").get(i)
					.getValue("service_id");
			String service_state = context.getRecordset("primary-key").get(i)
					.getValue("service_state");

			String service_type = context.getRecordset("primary-key").get(i)
					.getValue("service_type");
			if (service_state.equals("N")) {

				if (service_type != null && "FTP".equals(service_type)) {
					// 停用时 删除触发器 及SHARE_SRV_SCHEDULING 的IS_MARKUP 为无效

					String srv_scheduling_id = null;

					String sqlScheduling = "select srv_scheduling_id from share_srv_scheduling where service_id = '"
							+ service_id + "'";

					Map tablepMap = daoTable.queryService(sqlScheduling);// 获取任务调度ID
					if (tablepMap != null && !tablepMap.isEmpty()) {
						srv_scheduling_id = (String) tablepMap
								.get("SRV_SCHEDULING_ID");
						if (srv_scheduling_id != null
								&& !"".equals(srv_scheduling_id)) {
							VoShareSrvScheduling vo = new VoShareSrvScheduling();
							vo.setService_id(service_id);
							SrvTriggerRunner.removeFromScheduler(vo);
						}
					}

					// 更新任务调度表
					sqlScheduling = "update share_srv_scheduling  set is_markup = '"
							+ ExConstant.SERVICE_STATE_N
							+ "' where service_id = '" + service_id + "'";
					//System.out.println(sqlScheduling);
					table.executeUpdate(sqlScheduling);
				}

				sql += ExConstant.SERVICE_STATE_N + "' ";
			} else {
				sql += ExConstant.SERVICE_STATE_Y + "' ";

				if (service_type != null && "FTP".equals(service_type)) {

					// 启用时 启动触发器 及SHARE_SRV_SCHEDULING 的IS_MARKUP 为有效

					String sqlScheduling = "select * from share_srv_scheduling where service_id = '"
							+ service_id + "'";

					Map tablepMap = daoTable.queryService(sqlScheduling);// 获取任务调度ID
					if (tablepMap != null && !tablepMap.isEmpty()) {
						VoShareSrvScheduling vo = new VoShareSrvScheduling();
						
						if(null==context.getRecord("record").getValue("scheduling_type")){
							//共享服务列表中的启用按钮
							//System.out.println("-----"+tablepMap);
							ParamUtil.mapToBean(tablepMap, vo, false);
						}else{
							//配置调度规则后自动启用调度
							vo.setScheduling_type(context.getRecord("record")
									.getValue("scheduling_type"));// 计划任务类型
							vo.setScheduling_day(context.getRecord("record")
									.getValue("scheduling_day"));// 计划任务日期
							vo.setStart_time(context.getRecord("record").getValue(
									"start_time"));// 计划任务开始时间
							vo.setEnd_time(context.getRecord("record").getValue(
									"end_time"));// 计划任务结束时间
							vo.setScheduling_week(context.getRecord("record")
									.getValue("scheduling_week"));// 计划任务周天
							vo.setScheduling_count(context.getRecord("record")
									.getValue("scheduling_count"));// 计划任务执行次数
							vo.setInterval_time(context.getRecord("record")
									.getValue("interval_time"));// 计划任务执行 间隔
							vo.setJob_class_name(TaskSchedulingConstants.SRV_JOB_CLASS_NAME);// 触发调用的类名

							vo.setSrv_scheduling_id((String) tablepMap
									.get("SRV_SCHEDULING_ID"));
							vo.setService_id(service_id);
						}
						
						
						
						SrvTriggerRunner.addToScheduler(vo);

						// 更新任务调度表
						sqlScheduling = "update share_srv_scheduling  set is_markup = '"
								+ ExConstant.SERVICE_STATE_Y
								+ "' where service_id = '" + service_id + "'";
						//System.out.println(sqlScheduling);
						table.executeUpdate(sqlScheduling);
					}

				}
			}
			sql += " where t.service_id='" + service_id + "'";
			table.executeUpdate(sql);
			// System.out.println(sql);

		}

	}

	/**
	 * 
	 * txn40200014(查看) TODO(这里描述这个方法适用条件 – 可选) TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 – 可选)
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public void txn40200014(ShareServiceContext context) throws TxnException,
			FileNotFoundException, IOException
	{

		//System.out.println("txn402000014=" + context);
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 查询记录的主键 VoShareServicePrimaryKey primaryKey = context.getPrimaryKey(
		// inputNode );
		
		
		String service_id = context.getRecord("primary-key").getValue("service_id");
		StringBuffer sql = new StringBuffer();
		
		sql.append("select t1.*,t1.created_time as cretime,t1.last_modify_time as modtime, yh1.yhxm as crename,yh2.yhxm as modname from share_service t1,xt_zzjg_yh_new yh1,xt_zzjg_yh_new yh2");
		sql.append(" where  t1.creator_id = yh1.yhid_pk(+)  and t1.last_modify_id = yh2.yhid_pk(+)  and t1.service_id = '");
		sql.append(service_id);
		sql.append("'");
		table.executeRowset(sql.toString(), context, outputNode);
		
		
		
		
		// 查询到的记录内容 VoShareService result = context.getShareService( outputNode
		// );
		//System.out.println("txn40200014=="+context);
		Map<String, String> map = getOhterColumn(context.getRecord(outputNode)
				.getValue("service_id"));
		
		if(map!=null){
			context.getRecord(outputNode)
					.setValue("sql", map.get("sql"));
			context.getRecord(outputNode).setValue("column_name_en",
					map.get("column_name_en").toString());
			context.getRecord(outputNode).setValue("column_name_cn",
					map.get("column_name_cn").toString());
			context.getRecord(outputNode).setValue("column_no",
					map.get("column_no").toString());
			context.getRecord(outputNode).setValue("jsoncolumns",
					map.get("jsoncolumns").toString());	
		}
		// 从输出节点中获取附件的ID
		String fjids = context.getRecord(outputNode).getValue("fj_fk");

		// 从输出节点中获取附件的名称
		String filenames = context.getRecord(outputNode).getValue("fjmc");

		// 调用接口将获取的文件信息一一传回context
		UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
	//System.out.println("14---"+context);
		// System.out.println(context);
	}

	public void txn40200015(ShareServiceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		ShareServiceContext stateContext = new ShareServiceContext();
		// 构造服务状态json数据
		stateContext.getRecord(inputNode).setValue("codetype", "资源管理_归档服务状态");
		stateContext.getRecord(inputNode).setValue("column", "service_state");
		Attribute.setPageRow(stateContext, outputNode, -1);
		table.executeFunction("getInfoBysvrState", stateContext, inputNode,
				outputNode);
		Recordset stateRs = stateContext.getRecordset("record");
		context.setValue("svrState", JsonDataUtil.getJsonByRecordSet(stateRs));
		// 构造服务类型 json数据
		ShareServiceContext typeContext = new ShareServiceContext();
		typeContext.getRecord(inputNode).setValue("codetype", "资源管理_数据源类型");
		typeContext.getRecord(inputNode).setValue("column", "service_type");
		Attribute.setPageRow(typeContext, outputNode, -1);
		table.executeFunction("getInfoBysvrState", typeContext, inputNode,
				outputNode);
		Recordset typeRs = typeContext.getRecordset("record");
		//System.out.println("svrType---"+JsonDataUtil.getJsonByRecordSet(typeRs));
		context.setValue("svrType", JsonDataUtil.getJsonByRecordSet(typeRs));
		// 构造服务对象 json数据
		ShareServiceContext targetContext = new ShareServiceContext();
		targetContext.getRecord(inputNode).setValue("table_name",
				"res_service_targets");
		targetContext.getRecord(inputNode).setValue("col_name",
				"service_targets_id");
		targetContext.getRecord(inputNode).setValue("col_title",
				"service_targets_name");
		Attribute.setPageRow(targetContext, outputNode, -1);
		table.executeFunction("getInfoByTarget", targetContext, inputNode,
				outputNode);
		Recordset targetRs = targetContext.getRecordset("record");
		CodeMap codeMap = PublicResource.getCodeFactory();
		Recordset rs = codeMap.lookup(targetContext, "资源管理_服务对象类型");
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
			//System.out.println("group---"+groupValue);
			context.setValue("svrTarget", groupValue);
		}

		// 构造基础接口json数据
		ShareServiceContext interfaceContext = new ShareServiceContext();
		/*
		 * interfaceContext.getRecord(inputNode).setValue("table_name",
		 * "share_interface");
		 * interfaceContext.getRecord(inputNode).setValue("col_name",
		 * "interface_id");
		 * interfaceContext.getRecord(inputNode).setValue("col_title",
		 * "interface_name");
		 */
		Attribute.setPageRow(interfaceContext, outputNode, -1);
		table.executeFunction("getInfoByService", interfaceContext, inputNode,
				outputNode);
		
		Recordset interfaceRs = interfaceContext.getRecordset("record");

		context.setValue("pinyinInterface", JsonDataUtil
				.getPYJsonByRecordSet(interfaceRs));
		//context.setValue("svrInterface", JsonDataUtil.getJsonByRecordSet(interfaceRs));

	}

	public void txn40200025(ShareServiceContext context) throws TxnException,
			FileNotFoundException, IOException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		//System.out.println("txn40200025="+context);
		callService("40200014", context);
		String sqlStmt = "select min(t.log_date) as mintime, max(t.log_date) as maxtime from share_log_statistics t where t.service_id='"+context.getPrimaryKey().getValue("service_id")+"'";
		//System.out.println(sqlStmt);
		table.executeSelect(sqlStmt, context, outputNode);
		
		
		
		System.out.println("txn40200025=end==="+context);
	}
	
	public void txn40200026(ShareServiceContext context) throws TxnException,
	FileNotFoundException, IOException
	{
		//共享服务统计数据 add by dwn 20140108
		BaseTable table_share = TableFactory.getInstance().getTableObject(this,TABLE_NAME_SHARE);
		String service_name = context.getRecord("primary-key").getValue("service_name");//服务名称
		Attribute.setPageRow(context, "share_grap", -1);
		table_share.executeFunction("getShareInterfaceStatById", context, inputNode, "share_grap");

		Recordset share_rs = context.getRecordset("share_grap");

		if (!share_rs.isEmpty()) {
			String stime_str = "";
			String snum_str = "";
			String scount_str = "";
			List slist_info = new ArrayList();
			Map sall_map=new HashMap();
			for (int i = 0; i < share_rs.size(); i++) {
				DataBus db = share_rs.get(i);
				stime_str += stime_str == "" ? "\"" + db.getValue("log_date")
						+ "\"" : ",\"" + db.getValue("log_date") + "\"";
				snum_str += snum_str == "" ? "\"" + db.getValue("sum_num") + "\""
						: ",\"" + db.getValue("sum_num") + "\"";
				scount_str += scount_str == "" ? "\"" + db.getValue("sum_count")
						+ "\"" : ",\"" + db.getValue("sum_count") + "\"";
				String[] info = new String[] {
						db.getValue("sum_num").toString(),
						db.getValue("sum_count").toString(),
						db.getValue("avg_time").toString(),
						db.getValue("error_num").toString() };
				sall_map.put(db.getValue("log_date"), info);
			}
			slist_info.add(sall_map);
	
			context.getRecord("grap_data").setValue("stime_str", stime_str);
			context.getRecord("grap_data").setValue("sstat_num_str", snum_str);
			context.getRecord("grap_data").setValue("sstat_count_str", scount_str);
			context.getRecord("grap_data").setValue("service_name", service_name);
			context.getRecord("grap_data").setValue("sstat_info_str",JSONUtils.toJSONString(slist_info));
	
		}

		//
		BaseTable table = TableFactory.getInstance().getTableObject(this,TABLE_NAME);
		
		callService("40200014", context);
		String sqlStmt = "select min(t.log_date) as mintime, max(t.log_date) as maxtime from share_log_statistics t where t.service_id='"+context.getPrimaryKey().getValue("service_id")+"'";
		//System.out.println(sqlStmt);
		table.executeSelect(sqlStmt, context, outputNode);
		
	}

	/**
	 * 
	 * txn40200016(导出服务的配置) TODO(这里描述这个方法适用条件 – 可选) TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 – 可选)
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public void txn40200016(ShareServiceContext context) throws TxnException,
			FileNotFoundException, IOException
	{
		// logger.debug("txn40200016========>start="+context);
		//System.out.println("txn40200016");

		context.getRecord("record2").setValue("interface_id",
				context.getRecord("record").getValue("interface_id"));
		context.getRecord("record2").setValue("service_targets_id",
				context.getRecord("record").getValue("service_targets_id"));
		context.getRecord("record2").setValue("service_type",
				context.getRecord("record").getValue("service_type"));
		context.getRecord("record2").setValue("service_state",
				context.getRecord("record").getValue("service_state"));

		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 查询记录的主键 VoShareServicePrimaryKey primaryKey = context.getPrimaryKey(
		// inputNode );
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		// 查询到的记录内容 VoShareService result = context.getShareService( outputNode
		// );
		//System.out.println("00016--"+context);
		// logger.debug("txn40200016========>1");
		Map<String, String> map = getOhterColumn(context.getRecord(outputNode)
				.getValue("service_id"));
		context.getRecord("record2").setValue("sql", map.get("sql").toString());
		context.getRecord("record2").setValue("column_name_en",
				map.get("column_name_en").toString());
		context.getRecord("record2").setValue("column_name_cn",
				map.get("column_name_cn").toString());
		context.getRecord("record2").setValue("column_no",
				map.get("column_no").toString());
		context.getRecord("record2").setValue("jsoncolumns",
				map.get("jsoncolumns").toString());

		String service_targets_id = context.getRecord(outputNode).getValue(
				"service_targets_id");
		String sql = "select t.service_targets_name,t.service_targets_no,t.service_password as service_password from res_service_targets t where t.service_targets_id='"
				+ service_targets_id + "'";
		//System.out.println("00016---"+sql);
		table.executeSelect(sql, context, "record3");
		//System.out.println("00016--over");
		/*
		 * String interface_id =
		 * context.getRecord(outputNode).getValue("interface_id"); sql = "select
		 * t.table_id,t.table_name_cn from share_interface t where
		 * t.interface_id='" +interface_id+"'"; table.executeSelect(sql,
		 * context, "record4"); String[] table_id =
		 * context.getRecord("record4").getValue("table_id").split(",");
		 * 
		 * for(int i=0;i<table_id.length;i++){
		 * 
		 * ShareServiceContext context2 = new ShareServiceContext(); String
		 * sql2= "select
		 * t.share_dataitem_id,t.dataitem_name_cn,t.code_table,t.dataitem_name_en,t.dataitem_type_r,t.share_table_id,t2.table_name_cn,t2.table_name_en,d.codename,
		 * t.dataitem_long " + "from res_share_dataitem t,res_share_table
		 * t2,codedata d where t2.table_name_cn is not null and
		 * t.dataitem_name_cn is not null and t.share_table_id='"
		 * +table_id[i]+"' and t2.share_table_id='"+table_id[i]+"' " + "and
		 * t.dataitem_type=d.codevalue(+) and
		 * d.codetype='\u5B57\u6BB5\u6570\u636E\u7C7B\u578B' order by
		 * t.show_order " ; System.out.println(sql2); table.executeRowset(sql2,
		 * context, table_id[i]); table.executeRowset(sql2, context2, "record");
		 * Recordset rs = context2.getRecordset("record"); List dataList = new
		 * ArrayList(); for (int j = 0; j < rs.size(); j++) { DataBus db =
		 * rs.get(i);
		 * 
		 * 
		 * Map dataMap = new HashMap(); dataMap.put("title",
		 * db.get("dataitem_name_cn")); dataMap.put("dataitem_name_en",
		 * db.get("dataitem_name_en")); dataMap.put("dataitem_long",
		 * db.get("dataitem_long")); dataMap.put("dataitem_type",
		 * db.get("dataitem_type_r")); dataList.add(dataMap); }
		 * context.getRecord("record5").setValue("dataitem",JSONUtils.toJSONString
		 * (dataList) ); }
		 */

		//System.out.println(context);
	}

	/**
	 * 查询服务表列表
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 * @throws IOException
	 */
	public void txn40200201(ShareServiceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 查询记录的条件 VoShareServiceSelectKey selectKey = context.getSelectKey(
		// inputNode );
		String create_time = context.getRecord("select-key").getValue(
				"created_time");
		if (StringUtils.isNotBlank(create_time)) {
			String[] ctime = DateUtil.getDateRegionByDatePicker(create_time,
					true);
			context.getRecord("select-key").setValue("created_time_start",
					ctime[0]);
			context.getRecord("select-key").setValue("created_time_end",
					ctime[1]);
			context.getRecord("select-key").remove("created_time");
		}
		table.executeFunction("queryShareServiceListOrder", context, inputNode,
				outputNode);
		// 查询到的记录集 VoShareService result[] = context.getShareServices(
		// outputNode );
	}

	/**
	 * 查询服务表列表 add by dwn 2012-04-03webservice。
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void txn40200101(ShareServiceContext context) throws TxnException,
			FileNotFoundException, IOException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 查询记录的条件 VoShareServiceSelectKey selectKey = context.getSelectKey(
		// inputNode );
		// log.debug("txn40200101-context:"+context);
		// table.executeFunction( "queryServiceNo", context, inputNode,
		// outputNode );
		table.executeFunction("queryServiceNo_tmp", context, inputNode,
				outputNode);

		// 查询到的记录集 VoShareService result[] = context.getShareServices(
		// outputNode );
	}

	/**
	 * 
	 * txn40201111 批量测试
	 * 
	 * @param context
	 * @throws TxnException
	 * @throws FileNotFoundException
	 * @throws IOException
	 *             void
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public void txn40201111(ShareServiceContext context) throws TxnException,
			FileNotFoundException, IOException
	{
		Test test = new Test();
		String zts = test.testWeb();
		context.getRecord("record").setValue("zts", zts);

	}

	/**
	 * 
	 * txn40201112 单条测试
	 * 
	 * @param context
	 * @throws TxnException
	 * @throws FileNotFoundException
	 * @throws IOException
	 *             void
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public void txn40201112(ShareServiceContext context) throws TxnException,
			FileNotFoundException, IOException
	{
		Test test = new Test();
		String zts = test.testWeb1();
		context.getRecord("record").setValue("zts", zts);

	}

	/**
	 * 
	 * txn40201113 数据源连接测试
	 * 
	 * @param context
	 * @throws TxnException
	 * @throws FileNotFoundException
	 * @throws IOException
	 *             void
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public void txn40201113(ShareServiceContext context) throws TxnException,
			FileNotFoundException, IOException
	{
		Connect con = new Connect();
		// Test con = new Test();
		String zts = con.selectRs();
		context.getRecord("record").setValue("zts", zts + "");

	}
	
	/**
	 * 
	 * txn40201113 接口信息表格
	 * 
	 * @param context
	 * @throws TxnException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public void txn40201201(ShareServiceContext context) throws TxnException,
			FileNotFoundException, IOException
	{
		InterfaceData interdata= new InterfaceData();
		String dataStr =interdata.getShare_Interface();
		//System.out.println(dataStr);
		interdata.writeFile("D:/Eclipse/workspace/bjgs_exchange_fb/page/share/service/subgrid/grid_data.json", dataStr);
		String subdataStr =interdata.getShare_Table();
		//System.out.println(subdataStr);
		interdata.writeFile("D:/Eclipse/workspace/bjgs_exchange_fb/page/share/service/subgrid/subgrid_data.js", "var subgrid_data="+subdataStr+";");
		
	}
	/*
	 * public void txn40200102(ShareServiceContext context) throws IOException,
	 * TxnException{ String service_no =
	 * context.getRecord("select-key").getValue("service_no");
	 * if(StringUtils.isNotBlank(service_no)){ DataBus db = new DataBus(); Map
	 * dataMap = new HashMap(); dataMap = getOhterColumn(service_no);
	 * db.setValue("Squall", dataMap.get("sql").toString());
	 * db.setValue("column_name_en", dataMap.get("column_name_en").toString());
	 * db.setValue("column_name_cn", dataMap.get("column_name_cn").toString());
	 * db.setValue("column_no", dataMap.get("column_no").toString());
	 * db.setValue("jsoncolumns", dataMap.get("jsoncolumns").toString());
	 * context.addRecord("record", db); } }
	 */
	/**
	 * 重载父类的方法，用于替换交易接口的输入变量 调用函数
	 * 
	 * @param funcName
	 *            方法名称
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	protected void doService(String funcName, TxnContext context)
			throws TxnException
	{
		Method method = (Method) txnMethods.get(funcName);
		if (method == null) {
			funcName = this.getClass().getName() + "#" + funcName;
			throw new TxnErrorException(ErrorConstant.JAVA_METHOD_NOTFOUND,
					"没有找到交易码[" + txnCode + ":" + funcName + "]的处理函数");
		}

		// 执行
		ShareServiceContext appContext = new ShareServiceContext(context);
		invoke(method, appContext);
	}
}
