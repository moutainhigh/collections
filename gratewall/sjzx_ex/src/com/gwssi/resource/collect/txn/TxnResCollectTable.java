package com.gwssi.resource.collect.txn;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.jdbc.OracleConnection;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.ds.ConnectFactory;
import cn.gwssi.common.dao.ds.source.DBController;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.dao.resource.PublicResource;
import cn.gwssi.common.dao.resource.code.CodeMap;
import cn.gwssi.common.txn.TxnService;
import cn.gwssi.dw.rd.metadata.code.Constants;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.constant.ConstUploadFileType;
import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.constant.FileConstant;
import com.gwssi.common.upload.UploadFileVO;
import com.gwssi.common.upload.UploadHelper;
import com.gwssi.common.util.CalendarUtil;
import com.gwssi.common.util.JsonDataUtil;
import com.gwssi.common.util.UuidGenerator;
import com.gwssi.resource.collect.vo.ResCollectTableContext;

public class TxnResCollectTable extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap		txnMethods				= getAllMethod(
																TxnResCollectTable.class,
																ResCollectTableContext.class);

	// ���ݱ�����
	private static final String	TABLE_NAME				= "res_collect_table";

	// ���ݱ�����
	private static final String	TABLE_ITEM_NAME			= "res_collect_dataitem";

	// ��ѯ�б�
	private static final String	ROWSET_FUNCTION			= "select res_collect_table list";

	// ��ѯ�б�
	private static final String	ROWSET_ITEM_FUNCTION	= "select res_collect_dataitem list";

	// ��ѯ��¼
	private static final String	SELECT_FUNCTION			= "select one res_collect_table";

	// �޸ļ�¼
	private static final String	UPDATE_FUNCTION			= "update one res_collect_table";

	// ���Ӽ�¼
	private static final String	INSERT_FUNCTION			= "insert one res_collect_table";

	// ɾ����¼
	private static final String	DELETE_FUNCTION			= "delete one res_collect_table";

	/**
	 * ���캯��
	 */
	public TxnResCollectTable()
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
	 * ��ѯ��
	 * @param context
	 * @throws TxnException
	 */
	public void txn20209003(ResCollectTableContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ����ɼ���Դjson����
		ResCollectTableContext sourceContext = new ResCollectTableContext();		
		sourceContext.getRecord(inputNode).setValue("codetype", "��Դ����_�ɼ���Դ");
		sourceContext.getRecord(inputNode).setValue("column", "cj_ly");
		Attribute.setPageRow(sourceContext, outputNode, -1);
		table.executeFunction("getInfoBycjly", sourceContext, inputNode,
				outputNode);
		Recordset sourceRs = sourceContext.getRecordset("record");
		context.setValue("colSource", JsonDataUtil.getJsonByRecordSet(sourceRs));
		// ���������������json����
		// ���������� json����
		ResCollectTableContext targetContext = new ResCollectTableContext();
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
		// ���������json����
		ResCollectTableContext typeContext = new ResCollectTableContext();
		typeContext.getRecord(inputNode).setValue("codetype", "��Դ����_������");
		typeContext.getRecord(inputNode).setValue("column", "table_type");
		Attribute.setPageRow(typeContext, outputNode, -1);
		table.executeFunction("getInfoBytabType", typeContext, inputNode,
				outputNode);
		Recordset typeRs = typeContext.getRecordset("record");
		context.setValue("tabType", JsonDataUtil.getJsonByRecordSet(typeRs));
		// �������ɲɼ���״̬json����
		ResCollectTableContext stateContext = new ResCollectTableContext();
		stateContext.getRecord(inputNode).setValue("codetype", "��Դ����_����״̬");
		stateContext.getRecord(inputNode).setValue("column", "if_creat");
		Attribute.setPageRow(stateContext, outputNode, -1);
		table.executeFunction("getInfoBycrtState", stateContext, inputNode,
				outputNode);
		Recordset stateRs = stateContext.getRecordset("record");
		context.setValue("crtState", JsonDataUtil.getJsonByRecordSet(stateRs));
	}
	/**
	 * 
	 * txn20201000(������ݱ������Ƿ�����ʹ��)    

	 * @param context
	 * @throws TxnException        
	 * void       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public void txn20201000(ResCollectTableContext context) throws TxnException
	{
		
		
		String table_name_en = context.getRecord("primary-key").getValue("table_name_en");//���ݱ�����
		String collect_table_id = context.getRecord("primary-key").getValue("collect_table_id");//���ݱ�ID
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME);
		StringBuffer sql= new StringBuffer();
		sql.append("select count(*) as name_nums from res_collect_table t where t.is_markup='"+ExConstant.IS_MARKUP_Y+"'");
		if(table_name_en!=null&&!table_name_en.equals("")){
			sql.append(" and t.table_name_en='"+table_name_en.toUpperCase()+"'");
		}
		if(collect_table_id!=null&&!collect_table_id.equals("")){
			sql.append(" and t.collect_table_id != '"+collect_table_id+"'");
		}
		//System.out.println("��ѯ���ݱ������Ƿ���ʹ��"+sql.toString());
		table.executeRowset( sql.toString(), context, outputNode);
	
	}

	/**
	 * ��ѯ�ɼ����ݱ���Ϣ�б�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn20201001(ResCollectTableContext context) throws TxnException
	{
		// System.out.println("txn20201001");
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String create_time = context.getRecord("select-key").getValue(
				"created_time");
		/*if (StringUtils.isNotBlank(create_time)) {
			String[] ctime = DateUtil.getDateRegionByDatePicker(create_time,
					true);
			context.getRecord("select-key").setValue("created_time_start",
					ctime[0]);
			context.getRecord("select-key").setValue("created_time_end",
					ctime[1]);
			context.getRecord("select-key").remove("created_time");
		}*/
		//System.out.println(context);
		table.executeFunction("queryCollectTableList", context, inputNode,
				outputNode);
		
		callService("20209002", context);
	
	}

	/**
	 * �޸Ĳɼ����ݱ���Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn20201002(ResCollectTableContext context) throws TxnException
	{
		
		
		BaseTable table = TableFactory.getInstance().getTableObject(this,TABLE_NAME);
		String collect_table_id=context.getRecord("primary-key").getValue("collect_table_id");
		//System.out.println("collect_table_id="+collect_table_id);
		context.getRecord("record").setValue("collect_table_id",collect_table_id);//���ݱ�ID
		 String table_name_en=context.getRecord("record").getValue("table_name_en");
		 if(table_name_en!=null&&!"".equals(table_name_en)){
			 table_name_en=table_name_en.toUpperCase();
			 context.getRecord("record").setValue("table_name_en",table_name_en);// ��Ӣ������
		 }
		context.getRecord("record").setValue("last_modify_time",CalendarUtil.getCurrentDateTime());//����޸�ʱ��
		context.getRecord("record").setValue("last_modify_id",context.getRecord("oper-data").getValue("userID"));//����޸���ID
		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
		
		
		

		String jsonString = context.getRecord("record").getValue("jsondata");
		if (StringUtils.isNotBlank(jsonString)) {
			// �õ�json��
			JSONArray json = JsonDataUtil.getJsonArray(jsonString, "data");
			System.out.println("------------------------------\n" + json);
			
			//ɾ������
			String deleteSql ="delete from res_collect_dataitem t where t.collect_table_id='"+collect_table_id+"' and t.dataitem_state  = '0'";
			table.executeUpdate(deleteSql);
			/*BaseTable table_dataitem = TableFactory.getInstance().getTableObject(
					this, "res_collect_dataitem");*/
			for (int i = 0; i < json.size(); i++) {
				JSONObject object = (JSONObject) json.get(i);
				String dataitem_name_en = object.getString("dataitem_name_en");
				String dataitem_name_cn = object.getString("dataitem_name_cn");
				String dataitem_type = object.getString("dataitem_type");
				String dataitem_long = object.getString("dataitem_long");
				String is_key = object.getString("is_key");
				String code_table = object.getString("code_table");
				
				DataBus db = new DataBus();
				db.setValue("dataitem_name_en", dataitem_name_en);
				db.setValue("dataitem_name_cn", dataitem_name_cn);
				db.setValue("dataitem_type", dataitem_type);
				db.setValue("dataitem_long", dataitem_long);
				db.setValue("is_key", is_key);
				db.setValue("code_table", code_table);
				db.setValue("collect_table_id", collect_table_id);
				db.setValue("dataitem_long_desc", "");
				TxnContext txnContext = new TxnContext();
				txnContext.addRecord("record", db);
				txnContext.addRecord("oper-data", context.getRecord("oper-data"));
				
				callService("20202003", txnContext);
				/*table_dataitem.executeFunction("insertTableCondition", context, db,
						outputNode);*/
			}
		}
		if(!context.getRecord("record").isEmpty()){
			context.remove("record");
		}
		this.callService("20201004", context);
	}

	/**
	 * ���Ӳɼ����ݱ���Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn20201003(ResCollectTableContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,TABLE_NAME);
		 String collect_table_id = context.getRecord("record").getValue("collect_table_id");
		 String table_name_en=context.getRecord("record").getValue("table_name_en");
		 if(table_name_en!=null&&!"".equals(table_name_en)){
			 table_name_en=table_name_en.toUpperCase();
			 context.getRecord("record").setValue("table_name_en",table_name_en);// ��Ӣ������
		 }
		if(collect_table_id==null||"".equals(collect_table_id)){
			String id = UuidGenerator.getUUID();
			context.getRecord("record").setValue("collect_table_id", id);//���ݱ�ID
			context.getRecord("record").setValue("created_time",CalendarUtil.getCurrentDateTime());//����ʱ��
			context.getRecord("record").setValue("creator_id",context.getRecord("oper-data").getValue("userID"));//������ID
			context.getRecord("record").setValue("is_markup",ExConstant.IS_MARKUP_Y);// ���볣��  ��Ч���
			context.getRecord("record").setValue("cj_ly",CollectConstants.TYPE_CJLY_OUT);// ���볣�� �ɼ���Դ
			context.getRecord("record").setValue("if_creat",CollectConstants.TYPE_IF_CREAT_NO);// ���볣�� �ɼ���δ���� ���ݱ�
			
			table.executeFunction(INSERT_FUNCTION, context, inputNode, outputNode);
		}else{
			context.getRecord("record").setValue("last_modify_time",CalendarUtil.getCurrentDateTime());//����޸�ʱ��
			context.getRecord("record").setValue("last_modify_id",context.getRecord("oper-data").getValue("userID"));//����޸���ID
			table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
		}
		
		this.callService("20201004", context);
		System.out.println("txn20201003="+context);
	}

	/**
	 * ��ѯ�ɼ����ݱ���Ϣ�������޸�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn20201004(ResCollectTableContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,TABLE_NAME);
		String collect_table_id=context.getRecord("primary-key").getValue("collect_table_id");
		if(collect_table_id==null||"".equals(collect_table_id)){
			context.getRecord("primary-key").setValue("collect_table_id",context.getRecord("record").getValue("collect_table_id"));
		}
		
		collect_table_id = context.getRecord("primary-key").getValue("collect_table_id");
		StringBuffer sql = new StringBuffer();
		
		sql.append("select t1.*,t1.created_time as cretime,nvl(t1.last_modify_time,t1.created_time)as modtime, yh1.yhxm as crename,nvl(yh2.yhxm,yh1.yhxm) as modname from res_collect_table t1,xt_zzjg_yh_new yh1,xt_zzjg_yh_new yh2");
		sql.append(" where  t1.creator_id = yh1.yhid_pk(+)  and t1.last_modify_id = yh2.yhid_pk(+)  and t1.collect_table_id = '");
		sql.append(collect_table_id);
		sql.append("'");
		table.executeRowset(sql.toString(), context, "record");
		
		
		//context.getRecord("attribute-node").setValue("record_page-row", "2000");
		Attribute.setPageRow(context, "dataItem", -1);
		table.executeFunction("getItemByTableId", context, inputNode,"dataItem");//�������б�
		Recordset rs = context.getRecordset("dataItem");
		if(rs!=null&&rs.size()>0){
			context.remove("dataItem");
			int i=1;
			while(rs.hasNext()){
				DataBus temp = (DataBus)rs.next();
				temp.setValue("index", i+"");
				context.addRecord("dataItem", temp);
				i++;
			}
		}
		
		table.executeFunction("getItemByTableIdNoCreateTable", context, inputNode,"dataItemTemp");//δ���������  ��   �������б�
	
		rs = context.getRecordset("dataItemTemp");
		String jsonData = "";
		for(int ii=0; ii<rs.size(); ii++){
			DataBus db = new DataBus();
			db = rs.get(ii);
			jsonData += "{\"collect_dataitem_id\": \""+db.getValue("collect_dataitem_id")
				+"\", \"dataitem_name_en\": \""+db.getValue("dataitem_name_en")
				+"\", \"dataitem_name_cn\": \""+db.getValue("dataitem_name_cn")
				+"\", \"dataitem_type\": \""+db.getValue("dataitem_type")
				+"\", \"dataitem_long\": \""+db.getValue("dataitem_long")
				+"\", \"is_key\": \""+db.getValue("is_key")
				+"\", \"code_table\": \""+db.getValue("code_table")
				+"\", \"is_code_table\": \""+db.getValue("is_code_table")
				+"\"}";
			jsonData += ", ";
		}
		if(StringUtils.isNotBlank(jsonData)){
			jsonData = jsonData.substring(0, jsonData.lastIndexOf(","));
			jsonData = "{data:[" + jsonData + "]}";
		}
		context.getRecord("record").setProperty("jsondata", jsonData);

	}

	/**
	 * ɾ���ɼ����ݱ���Ϣ����Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn20201005(ResCollectTableContext context) throws TxnException
	{
		
		//BaseTable table2 = TableFactory.getInstance().getTableObject(this,TABLE_NAME);
		//table2.executeFunction("deleteTableItem", context, inputNode, outputNode);//ɾ���������б�
		
		BaseTable table = TableFactory.getInstance().getTableObject(this,TABLE_NAME);//ɾ�����ݱ�
		table.executeFunction("deleteTable", context, inputNode, outputNode);
		
		
	}

	/**
	 * ��ѯ�ɼ����ݱ���Ϣ�����ڲ鿴
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn20201006(ResCollectTableContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,TABLE_NAME);
		
		String collect_table_id = context.getRecord("primary-key").getValue("collect_table_id");
		StringBuffer sql = new StringBuffer();
		
		sql.append("select t1.*,t1.created_time as cretime,nvl(t1.last_modify_time,t1.created_time)as modtime, yh1.yhxm as crename,nvl(yh2.yhxm,yh1.yhxm) as modname from res_collect_table t1,xt_zzjg_yh_new yh1,xt_zzjg_yh_new yh2");
		sql.append(" where  t1.creator_id = yh1.yhid_pk(+)  and t1.last_modify_id = yh2.yhid_pk(+)  and t1.collect_table_id = '");
		sql.append(collect_table_id);
		sql.append("'");
		table.executeRowset(sql.toString(), context, outputNode);
	
		table.executeFunction("getItemByTableId", context, inputNode,"dataItem");//������
	}
	
	/**
	 * ��ѯ�ɼ����ݱ���Ϣ����������
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn20201007(ResCollectTableContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,TABLE_NAME);
	
		table.executeFunction("getItemByTableId", context, inputNode,"dataItem");//�������б�

	}
	
	/**
	 * �ڲɼ������ɲɼ����ݱ�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn20201008(ResCollectTableContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,TABLE_NAME);
	
		table.executeFunction("creatTable", context, inputNode,"dataItem");//�������ݱ�

	}
	
	 /* �ڲɼ���              �޸�   �ɼ����ݱ�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn20201016(ResCollectTableContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,TABLE_NAME);
	
		table.executeFunction("alterTable", context, inputNode,"dataItem");//�޸Ĳɼ����ݱ�

	}
	
	
	
	
	
	/**
	 * 
	 * txn20201009(���ɼ������ݱ������Ƿ�����ʹ��)    

	 * @param context
	 * @throws TxnException        
	 * void       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public void txn20201009(ResCollectTableContext context) throws TxnException
	{
       
		
       String table_name_en = context.getRecord("primary-key").getValue("table_name_en");//���ݱ�����
	
		StringBuffer sql= new StringBuffer();
		//sql.append("select count(*) as name_nums from user_tables  where table_name='"+table_name_en.toUpperCase()+"'");
		//System.out.println("��ѯ�ɼ����Ƿ���ĳ���Ƶ����ݱ�sql========"+sql.toString());
		ConnectFactory cf = ConnectFactory.getInstance();
		DBController dbcon = cf.getConnection(ExConstant.DATA_SOURCE_COLLECT);
		OracleConnection conn = (OracleConnection) dbcon.getConnection();
	
		ResultSet rsTable = null;
		ResultSet rsTableDate = null;
		String count = "0";
		Boolean tableExit = true;
		try {
			conn.setRemarksReporting(true);
			sql.append("select count(*) as name_nums from "+table_name_en.toUpperCase());
			System.out.println("��ѯ�ɼ������ݱ����Ƿ�������====="+sql.toString());
			try{
				rsTable = conn.createStatement().executeQuery( "explain PLAN for ("+sql.toString()+")");
			
			}
			catch(SQLException e){
				e.printStackTrace();
				tableExit = false;
			}
			System.out.println("tableExit="+tableExit);
			if(tableExit){
				
				rsTableDate = conn.createStatement().executeQuery(sql.toString());
				if (rsTableDate.next()) {
					count = rsTableDate.getString("name_nums");//���ݱ������Ѵ��������ݱ�����������
					System.out.println("�ɼ������ݱ�����������====="+count);
				}
				if(count.equals("0")){
					count = "-1";//���ݱ������Ѵ��ڵ������ݱ���û������
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbcon.closeResultSet(rsTable);
			dbcon.closeResultSet(rsTableDate);
		}
		context.getRecord("record").setValue("name_nums",count);
	}
	/**
	 * 
	 * txn20201010(����excel)    

	 * @param context
	 * @throws DBException 
	 * @throws TxnException        
	 * void       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public void txn20201010(ResCollectTableContext context) throws TxnException, DBException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,TABLE_NAME);
		UploadFileVO fileVO1 = new UploadFileVO();
        fileVO1.setFileStatus(FileConstant.UPLOAD_FILESTATUS_SINGLE);
        UploadFileVO vo = UploadHelper.saveFile(context, fileVO1,ConstUploadFileType.RES_TBL);
        //��������Ϣ���ݵ�inputNode
        context.getRecord(inputNode).setValue("fj_fk",vo.getReturnId());
        context.getRecord(inputNode).setValue("fjmc",vo.getReturnName());
		table.executeFunction("importExcel", context, inputNode,outputNode);//����excel
	}
	/** 
	 * У�����ݱ��Ƿ񱻲ɼ���������
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn20201011( ResCollectTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "queryCollectTableUse", context, inputNode, outputNode );
	}
	
	/** 
	 * ͬ����ṹ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn20201012( ResCollectTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		
		//���ݿ����
		Recordset objectRs = new Recordset();
		Recordset columnRs = new Recordset();
		
		DataBus upDataSource = new DataBus();
		upDataSource.setValue("db_name", ExConstant.DATA_SOURCE_SynchroTable);
		upDataSource.setValue("sync_flag", "1");
		//upDataSource.setValue("sys_rd_data_source_id", sys_rd_data_source_id);
		
		DataBus inputData = new DataBus();
		inputData.setValue("db_name", ExConstant.DATA_SOURCE_SynchroTable);
		inputData.setValue("table_type", "TABLE");
		TxnContext tableCtx = new TxnContext();
		tableCtx.addRecord("select-key", inputData);
		//��ȡ����Ϣ
		log.debug("getTable---->begin:");
		callService("80003107",tableCtx);
		Recordset tableRs = tableCtx.getRecordset("recordTable");
		if(tableRs!=null && tableRs.size()>0){
			for(int i=0;i<tableRs.size();i++){
				DataBus tableBus = (DataBus)tableRs.get(i);
				DataBus tableData = new DataBus();

				String tableName = tableBus.getValue("table_name");
				String tableSchema = tableBus.getValue("table_schema");
				String remarks = tableBus.getValue("remarks");
				String tableCnName = "";
				//��ע
				if(remarks != null && remarks !="null" && !"".equals(remarks) && remarks.indexOf("'")<0){
					boolean flag = isMessyCode(remarks);
					if(flag==false){
						if(remarks.length() > 2000){
							remarks = remarks.substring(0, 2000);
						}
						if(remarks.length() > 30){
							remarks = remarks.substring(0, 30);
						}else{
							tableCnName = remarks;
						}
					}else{
						remarks = "???";
						tableCnName = "";
					}
				}else{
					remarks = "";
				}
				//��ȡ��������Ϣ
				String tb_pk_name = "";
				String tb_pk_columns = "";
				StringBuffer pkColumns = new StringBuffer();
				TxnContext pkCtx = new TxnContext();
				DataBus pkInputData = new DataBus();
				pkInputData.setValue("db_name", ExConstant.DATA_SOURCE_SynchroTable);
				pkInputData.setValue("table_name", tableName);
				pkCtx.addRecord("select-key", pkInputData);
				callService("80003110",pkCtx);
				Recordset pkRs = pkCtx.getRecordset("recordPk");
				if(pkRs !=null && pkRs.size()>0){
					for(int p=0;p<pkRs.size();p++){
						DataBus pkBus = (DataBus)pkRs.get(p);
						tb_pk_name = pkBus.getValue("pk_name");
						tb_pk_columns = pkBus.getValue("column_name");
						if(tb_pk_columns != null && !"".equals(tb_pk_columns)){
							pkColumns.append(",").append(tb_pk_columns);
						}
					}
				}
				if(pkColumns != null && pkColumns.length()>0){
					tb_pk_columns = pkColumns.toString().substring(1);
				}
				
				String tableId = UuidGenerator.getUUID();
				tableData.put("collect_table_id", tableId);
				//tableData.put("sys_rd_data_source_id", sys_rd_data_source_id);
				tableData.put("table_name_en", tableName);
				tableData.put("table_name_cn", tableCnName);
				tableData.put("table_type", CollectConstants.TYPE_TABLE_YW);//ҵ���
				//tableData.put("object_schema", tableSchema);
				//tableData.put("cur_record_count", "");
				tableData.put("table_desc", remarks);
				tableData.put("table_status", CollectConstants.TYPE_QY);//��״̬ ����
				tableData.put("is_markup", ExConstant.IS_MARKUP_Y);//��Ч���
				tableData.put("creator_id", context.getRecord("oper-data").getValue("userID"));//������ID
				tableData.put("created_time", CalendarUtil.getCurrentDateTime());//����ʱ��
				tableData.put("cj_ly", CollectConstants.TYPE_CJLY_IN);//�ɼ���Դ �ڲ��ɼ���
				tableData.put("if_creat", CollectConstants.TYPE_IF_CREAT_YES);//�ɼ����Ƿ����ɲɼ���
				//tableData.put("data_object_type",Constants.DB_OBJECT_TABLE);
				//tableData.put("timestamp", curDateTime);
				//tableData.put("object_script", "");
				tableData.put("tb_pk_name", tb_pk_name);
				tableData.put("tb_pk_columns", tb_pk_columns);
				tableData.put("tb_index_name", "");
				tableData.put("tb_index_columns", "");
				
				objectRs.add(tableData);
				//��ȡ�ֶ���Ϣ
				TxnContext columnCtx = new TxnContext();
				inputData.setValue("table_name", tableName);
				columnCtx.addRecord("select-key", inputData);
				callService("80003108",columnCtx);
				Recordset colsRs = columnCtx.getRecordset("recordColumn");
				if(colsRs != null && colsRs.size()>0){
					for(int j=0;j<colsRs.size();j++){
						DataBus columnBus = (DataBus)colsRs.get(j);
						DataBus columnData = new DataBus();
						String columnName = columnBus.getValue("column_name");
						String columnSize = columnBus.getValue("column_size");
						String isNullable = columnBus.getValue("is_nullable");
						String nullable = columnBus.getValue("nullable");
						String dataType = columnBus.getValue("data_type");
						String columnRemarks = columnBus.getValue("remarks");
						String defaultValue = columnBus.getValue("defaultValue");
						//�ֶ�˵��
						String columnCnName = "";
						
						if(columnRemarks != null && columnRemarks !="null" && !"".equals(columnRemarks) && columnRemarks.indexOf("'")<0){
							boolean flag = isMessyCode(columnRemarks);
							if(flag==false){
								if(columnRemarks.length() > 2000){
									columnRemarks = columnRemarks.substring(0, 2000);
								}
								if(columnRemarks.length() > 30){
									columnCnName = columnRemarks.substring(0, 30);
								}else{
									columnCnName = columnRemarks;
								}
							}else{
								columnRemarks = "???";
								columnCnName = "";
							}
						}else{
							columnRemarks = "";
						}

						//�ֶγ���
						int size=0;
						if(columnSize!=null && !"".equals(columnSize)){
							size = Integer.parseInt(columnSize);
						}
						//�ֶ�����
						int iColType=0;
						if(dataType!=null && !"".equals(dataType)){
							iColType = Integer.parseInt(dataType);
						}
						String columnType = changeColumnType(iColType);
						
						columnData.put("collect_dataitem_id", UuidGenerator.getUUID());
						columnData.put("collect_table_id", tableId);
						//columnData.put("sys_rd_data_source_id", sys_rd_data_source_id);
						//columnData.put("unclaim_tab_code", tableName);
						columnData.put("dataitem_name_en", columnName);
						columnData.put("dataitem_name_cn", columnCnName);
						columnData.put("dataitem_type", columnType);
						columnData.put("dataitem_long", size);
						columnData.put("is_key", isNullable.equalsIgnoreCase("NO") ? Constants.COLUMN_IS_PK:Constants.COLUMN_NOT_PK);
						columnData.put("is_code_table", CollectConstants.IS_CODE_TABLE_N);//���Ǵ����
						columnData.put("code_table", "");//��Ӧ�����
						columnData.put("dataitem_long_desc", columnRemarks);
						columnData.put("is_markup", ExConstant.IS_MARKUP_Y);//��Ч���
						columnData.put("creator_id", context.getRecord("oper-data").getValue("userID"));//������ID
						columnData.put("created_time", CalendarUtil.getCurrentDateTime());//����ʱ��
						columnData.put("creator_id", context.getRecord("oper-data").getValue("userID"));//������ID
						columnData.put("created_time", CalendarUtil.getCurrentDateTime());//����ʱ��
						columnData.put("last_modify_id", "");//������ID
						columnData.put("last_modify_time", "");//����ʱ��
						//columnData.put("is_null", nullable);
						columnRs.add(columnData);
					}
				}
			}
			this.callService("20201001", context);
		}
			/**
			 * ����δ������Դ��Ϣ
			 */
			log.debug("updateUnclaim---->begin:");
	    	//DataBus deleteData = new DataBus();
	    	//deleteData.put("collect_table_id", tableId);
	        if(objectRs!=null && objectRs.size()>0){
	        	//ɾ��
	        	TxnContext ctx = (TxnContext) context.clone();
	        	BaseTable unclaimTable = TableFactory.getInstance().getTableObject(this, "res_collect_table");
	        //	unclaimTable.executeFunction("deleteByDataSource", ctx, deleteData, outputNode);
	        	for(int i=0;i<objectRs.size();i++){
	        		DataBus inputDb = (DataBus)objectRs.get(i);
	        		unclaimTable.executeFunction("insertBySync", ctx, inputDb, outputNode);
	        	}
	        }

	        if(columnRs!=null && columnRs.size()>0){
	        	BaseTable unclaimColumn = TableFactory.getInstance().getTableObject(this, "res_collect_dataitem");
	        	//unclaimColumn.executeFunction("deleteColumnByDataSource", context, deleteData, outputNode);
	        	for(int i=0;i<columnRs.size();i++){
	        		DataBus columnDb = (DataBus)columnRs.get(i);
	        		unclaimColumn.executeFunction("insert one res_collect_dataitem", context, columnDb, outputNode);
	        	}
	        }
	        /**
	         * ��ȡ���ݽṹ�����¼
	       
	        log.debug("updateChange---->begin:");
	        TxnContext changeCtx = new TxnContext();
	        changeCtx.addRecord("select-key", upDataSource);
	        callService("8000110",changeCtx);
	          */
	        log.debug("sync---->end!");
	}
	
	/**
	 * �ж��ַ����Ƿ��������
	 * @param str
	 * @return
	 */
	public static boolean isMessyCode(String str) { 
	     for (int i = 0; i < str.length(); i++) { 
	        char c = str.charAt(i); 
	        // ����Unicode������ĳ���ַ���ת��ʱ������ڸ��ַ�����û�ж�Ӧ�ı��룬��õ�0x3f�����ʺ��ַ�?�� 
	        //�������ַ�����Unicode����ת��ʱ�����������������ڸ��ַ�����û�б�ʶ�κε��ַ�����õ��Ľ����0xfffd 
	        //System.out.println("--- " + (int) c); 
	        if ((int) c == 0xfffd) { 
	         // �������� 
	         //System.out.println("�������� " + (int) c); 
	         return true; 
	        } 
	     } 
	     return false;    
	}
	
	/**
	 * ת����������
	 * @param iColType
	 * @return
	 */
	public String changeColumnType(int iColType) {
		String colType;
		switch (iColType) {
		case 1:
			// char
			colType = CollectConstants.TYPE_CHAR;
			break;
		case 12:
		case -1: {
			// varchar
			colType = CollectConstants.TYPE_VARCHAR2;
		}
			break;
		case 2:
		case 3:
		case 6:
		case 7:
		case 8: {
			// Number(N,s)
			colType = Constants.COL_DATATYPE_DECIMAL;
		}
			break;
		case 4:
		case 5:
		case -5:
		case -6: {
			// int
			colType = CollectConstants.TYPE_INT;
		}
			break;
		case 91:
		case 92:
		case 93: {
			// date
			colType = CollectConstants.TYPE_DATE;

		}
			break;
		case 2004:
		case 2005:
		case -2:
		case -3:
		case -4: {
			// int
			colType = Constants.COL_DATATYPE_BINARY;
		}
			break;
		case 16:
			colType = Constants.COL_DATATYPE_BOOLEAN;
			break;
		default:
			colType = Constants.COL_DATATYPE_OTHER;
		}
		return colType;
	}
	
	/**
	 * ��ѯ�ɼ����ݱ���Ϣ�������޸�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn20201013(ResCollectTableContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,TABLE_NAME);
		String collect_table_id=context.getRecord("primary-key").getValue("collect_table_id");
		if(collect_table_id==null||"".equals(collect_table_id)){
			context.getRecord("primary-key").setValue("collect_table_id",context.getRecord("record").getValue("collect_table_id"));
		}
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
	
		table.executeFunction("getItemByTableId", context, inputNode,"dataItem");//�������б�

	}

	
	
	
	/**
	 * �����ڶ���ת����������
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn20201014(ResCollectTableContext context) throws TxnException
	{
		String collect_table_id=context.getRecord("primary-key").getValue("collect_table_id");
		if(StringUtils.isBlank(collect_table_id)){
			context.getRecord("primary-key").setValue("collect_table_id",context.getRecord("record").getValue("collect_table_id"));
		}
		String jsonString = context.getRecord("record").getValue("jsondata");
		if (StringUtils.isNotBlank(jsonString)) {
			// �õ�json��
			JSONArray json = JsonDataUtil.getJsonArray(jsonString, "data");
			//System.out.println("------------------------------\n" + json);
			/*BaseTable table_dataitem = TableFactory.getInstance().getTableObject(
					this, "res_collect_dataitem");*/
			for (int i = 0; i < json.size(); i++) {
				JSONObject object = (JSONObject) json.get(i);
				String dataitem_name_en = object.getString("dataitem_name_en");
				String dataitem_name_cn = object.getString("dataitem_name_cn");
				String dataitem_type = object.getString("dataitem_type");
				String dataitem_long = object.getString("dataitem_long");
				String is_key = object.getString("is_key");
				String code_table = object.getString("code_table");
				
				DataBus db = new DataBus();
				db.setValue("dataitem_name_en", dataitem_name_en);
				db.setValue("dataitem_name_cn", dataitem_name_cn);
				db.setValue("dataitem_type", dataitem_type);
				db.setValue("dataitem_long", dataitem_long);
				db.setValue("is_key", is_key);
				db.setValue("code_table", code_table);
				db.setValue("collect_table_id", collect_table_id);
				db.setValue("dataitem_long_desc", "");
				TxnContext txnContext = new TxnContext();
				txnContext.addRecord("record", db);
				txnContext.addRecord("oper-data", context.getRecord("oper-data"));
				
				callService("20202003", txnContext);
				/*table_dataitem.executeFunction("insertTableCondition", context, db,
						outputNode);*/
			}
		}
		BaseTable table = TableFactory.getInstance().getTableObject(this,TABLE_NAME);
		
		collect_table_id = context.getRecord("primary-key").getValue("collect_table_id");
		StringBuffer sql = new StringBuffer();
		
		context.remove("record");
		
		sql.append("select t1.*,t1.created_time as cretime,t1.last_modify_time as modtime, yh1.yhxm as crename,yh2.yhxm as modname from res_collect_table t1,xt_zzjg_yh_new yh1,xt_zzjg_yh_new yh2");
		sql.append(" where  t1.creator_id = yh1.yhid_pk(+)  and t1.last_modify_id = yh2.yhid_pk(+)  and t1.collect_table_id = '");
		sql.append(collect_table_id);
		sql.append("'");
		table.executeRowset(sql.toString(), context, outputNode);
		
		
		//System.out.println("================\n" + context);
		
		//context.getRecord("attribute-node").setValue("record_page-row", "2000");
		Attribute.setPageRow(context, "dataItem", 5);
		table.executeFunction("getItemByTableIdNoCreateTable", context, inputNode,"dataItem");//�������б�
		Recordset rs = context.getRecordset("dataItem");
		if(rs!=null&&rs.size()>0){
			context.remove("dataItem");
			int i=1;
			while(rs.hasNext()){
				DataBus temp = (DataBus)rs.next();
				temp.setValue("index", i+"");
				context.addRecord("dataItem", temp);
				i++;
			}
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
		ResCollectTableContext appContext = new ResCollectTableContext(context);
		invoke(method, appContext);
	}
}
