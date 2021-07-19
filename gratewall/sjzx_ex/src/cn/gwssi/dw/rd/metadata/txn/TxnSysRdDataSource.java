package cn.gwssi.dw.rd.metadata.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.component.Config;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.context.vo.VoUser;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.ds.ConnectFactory;
import cn.gwssi.common.dao.ds.source.DBController;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;
import cn.gwssi.dw.rd.metadata.code.Constants;

import com.gwssi.common.util.CalendarUtil;
import com.gwssi.common.util.UuidGenerator;

public class TxnSysRdDataSource extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysRdDataSource.class, TxnContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "sys_rd_data_source";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select sys_rd_data_source list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one sys_rd_data_source";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one sys_rd_data_source";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one sys_rd_data_source";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one sys_rd_data_source";
	
	/**
	 * 构造函数
	 */
	public TxnSysRdDataSource()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/**
	 * 查询数据源列表
	 * @param context
	 * @throws TxnException
	 */
	public void txn8000101(TxnContext context) throws TxnException {
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
	}
	
	/**
	 * 修改数据源信息
	 * @param context
	 * @throws TxnException
	 */
	public void txn8000102(TxnContext context) throws TxnException {
		String dbName = context.getRecord(inputNode).getString("db_name");
		DataBus db = this.getDBConfigData(dbName);
		if (db == null)
			throw new TxnDataException("", "未找到数据源[" + dbName + "]");

		//String DBUrl = db.getString("db-url") == null ? "" : db
				//.getString("db-url");

		DataBus inputData = context.getRecord(inputNode);

		inputData.setValue("db_schema", db.getValue("db-username")==null?"":db.getValue("db-username"));
		inputData.setValue("db_type", db.getValue("db-type")==null?"":db.getValue("db-type"));
		inputData.setValue("db_server", db.getValue("db-server")==null?"":db.getValue("db-server"));
		inputData.setValue("db_username", db.getValue("db-username")==null?"":db.getValue("db-username"));
		inputData.setValue("db_password", db.getValue("db-password")==null?"":db.getValue("db-password"));
		inputData.setValue("jndi_name", db.getValue("jndi-name")==null?"":db.getValue("jndi-name"));
		//inputData.setValue("db_url", db.getString("db-url")==null?"":db.getString("db-url"));
		inputData.setValue("db_driver", db.getValue("db-driver")==null?"":db.getValue("db-driver"));
		inputData.setValue("value_class", db.getValue("value-class")==null?"":db.getValue("value-class"));
		inputData.setValue("db_isolation", db.getValue("db_isolation")==null?"":db.getValue("db_isolation"));
		inputData.setValue("db_transaction", db.getValue("db-transaction")==null?"":db.getValue("db-transaction"));
		inputData.setValue("sync_table", db.getValue("sync-table")==null?"":db.getValue("sync-table"));
		inputData.setValue("merge_flag", db.getValue("merge-flag")==null?"":db.getValue("merge-flag"));
		
		//创建人
		VoUser user = context.getOperData();
		String userName = user.getOperName();
		inputData.setValue("creator", userName);
		//时间戳
		inputData.setValue("timestamp", CalendarUtil.getCurrentDateTime());
		//同步标记置0
		inputData.setValue("sync_flag", "0");
		//System.out.print(inputData);
		BaseTable table = TableFactory.getInstance().getTableObject(this, TABLE_NAME);
		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
	}
	
	/**
	 * 增加数据源信息
	 * @param context
	 * @throws TxnException
	 */
	public void txn8000103(TxnContext context) throws TxnException {
		String dbName = context.getRecord(inputNode).getString("db_name");
		DataBus db = this.getDBConfigData(dbName);
		if (db == null)
			throw new TxnDataException("", "未找到数据源[" + dbName + "]");

		String DBUrl = db.getValue("db-url") == null ? "" : db
				.getValue("db-url");

		DataBus inputData = context.getRecord(inputNode);
		
		inputData.setValue("db_schema", db.getValue("db-username")==null?"":db.getValue("db-username"));
		inputData.setValue("db_type", db.getValue("db-type")==null?"":db.getValue("db-type"));
		inputData.setValue("db_server", db.getValue("db-server")==null?"":db.getValue("db-server"));
		inputData.setValue("db_username", db.getValue("db-username")==null?"":db.getValue("db-username"));
		inputData.setValue("db_password", db.getValue("db-password")==null?"":db.getValue("db-password"));
		inputData.setValue("jndi_name", db.getValue("jndi-name")==null?"":db.getValue("jndi-name"));
		inputData.setValue("db_url", DBUrl==null?"":DBUrl);
		inputData.setValue("db_driver", db.getValue("db-driver")==null?"":db.getValue("db-driver"));
		inputData.setValue("value_class", db.getValue("value-class")==null?"":db.getValue("value-class"));
		inputData.setValue("db_isolation", db.getValue("db_isolation")==null?"":db.getValue("db_isolation"));
		inputData.setValue("db_transaction", db.getValue("db-transaction")==null?"":db.getValue("db-transaction"));
		inputData.setValue("sync_table", db.getValue("sync-table")==null?"":db.getValue("sync-table"));
		inputData.setValue("merge_flag", db.getValue("merge-flag")==null?"":db.getValue("merge-flag"));
		//创建人
		VoUser user = context.getOperData();
		String userName = user.getOperName();
		inputData.setValue("creator", userName);
		//时间戳
		inputData.setValue("timestamp", CalendarUtil.getCurrentDateTime());
		//同步标记置0
		inputData.setValue("sync_flag", "0");
		//System.out.println(inputData);
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/**
	 * 查询数据源用于修改
	 * @param context
	 * @throws TxnException
	 */
	public void txn8000104(TxnContext context) throws TxnException {
		BaseTable table = TableFactory.getInstance().getTableObject(this, TABLE_NAME);
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
	}
	
	/**
	 * 删除数据源信息
	 * @param context
	 * @throws TxnException
	 */
	public void txn8000105(TxnContext context) throws TxnException {
		BaseTable table = TableFactory.getInstance().getTableObject(this, TABLE_NAME);
		table.executeFunction(DELETE_FUNCTION, context, inputNode, outputNode);
	}
	
	/**
	 * 测试数据源连接
	 * @param context
	 * @throws TxnException
	 */
	public void txn8000106(TxnContext context) throws TxnException {
		String db_name = context.getRecord(inputNode).getString("db_name");
		ConnectFactory cf = cn.gwssi.common.dao.ds.ConnectFactory.getInstance();
		DBController conn = cf.getConnection(db_name);
	}
	
	/**
	 * 同步数据源
	 * @param context
	 * @throws TxnException
	 */
	public void txn8000107(TxnContext context) throws TxnException {
		
		//清空回收站  add by dwn20120702
		BaseTable table1 = TableFactory.getInstance().getTableObject(this, TABLE_NAME);
    	table1.executeFunction("pugreRecyclebin", context, inputNode, outputNode);

		String db_name = context.getRecord(inputNode).getValue("db_name");
		String sys_rd_data_source_id = context.getRecord(inputNode).getValue("sys_rd_data_source_id");
		String curDate = CalendarUtil.getCurrentDate();
		String curDateTime = CalendarUtil.getCurrentDateTime();
		//数据库对象
		Recordset objectRs = new Recordset();
		Recordset columnRs = new Recordset();
		
		DataBus upDataSource = new DataBus();
		upDataSource.setValue("db_name", db_name);
		upDataSource.setValue("sync_date", curDate);
		upDataSource.setValue("sync_flag", "1");
		upDataSource.setValue("sys_rd_data_source_id", sys_rd_data_source_id);
		
		DataBus inputData = new DataBus();
		inputData.setValue("db_name", db_name);
		inputData.setValue("table_type", "TABLE");
		TxnContext tableCtx = new TxnContext();
		tableCtx.addRecord("select-key", inputData);
		//获取表信息
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
				//表备注
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
				//获取表主键信息
				String tb_pk_name = "";
				String tb_pk_columns = "";
				StringBuffer pkColumns = new StringBuffer();
				TxnContext pkCtx = new TxnContext();
				DataBus pkInputData = new DataBus();
				pkInputData.setValue("db_name", db_name);
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
				tableData.put("sys_rd_unclaim_table_id", tableId);
				tableData.put("sys_rd_data_source_id", sys_rd_data_source_id);
				tableData.put("unclaim_table_code", tableName);
				tableData.put("unclaim_table_name", tableCnName);
				tableData.put("object_schema", tableSchema);
				tableData.put("cur_record_count", "");
				tableData.put("remark", remarks);
				tableData.put("data_object_type",Constants.DB_OBJECT_TABLE);
				tableData.put("timestamp", curDateTime);
				tableData.put("object_script", "");
				tableData.put("tb_pk_name", tb_pk_name);
				tableData.put("tb_pk_columns", tb_pk_columns);
				tableData.put("tb_index_name", "");
				tableData.put("tb_index_columns", "");
				objectRs.add(tableData);
				//获取字段信息
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
						//字段说明
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

						//字段长度
						int size=0;
						if(columnSize!=null && !"".equals(columnSize)){
							size = Integer.parseInt(columnSize);
						}
						//字段类型
						int iColType=0;
						if(dataType!=null && !"".equals(dataType)){
							iColType = Integer.parseInt(dataType);
						}
						String columnType = changeColumnType(iColType);
						
						columnData.put("sys_rd_unclaim_column_id", UuidGenerator.getUUID());
						columnData.put("sys_rd_unclaim_table_id", tableId);
						columnData.put("sys_rd_data_source_id", sys_rd_data_source_id);
						columnData.put("unclaim_tab_code", tableName);
						columnData.put("unclaim_column_code", columnName);
						columnData.put("timestamp", curDateTime);
						columnData.put("default_value", defaultValue);
						columnData.put("remarks", columnRemarks);
						columnData.put("unclaim_column_name", columnCnName);
						columnData.put("unclaim_column_length", size);
						columnData.put("is_index", isNullable.equalsIgnoreCase("NO") ? Constants.COLUMN_IS_PK:Constants.COLUMN_NOT_PK);
						columnData.put("is_primary_key", isNullable.equalsIgnoreCase("NO") ? Constants.COLUMN_IS_PK:Constants.COLUMN_NOT_PK);
						columnData.put("is_null", nullable);
						columnData.put("unclaim_column_type", columnType);
						columnRs.add(columnData);
					}
				}
			}
		}
		//获取视图信息
		log.debug("getView---->begin:");
		DataBus viewInputData = new DataBus();
        viewInputData.setValue("db_name", db_name);
		viewInputData.setValue("table_type", "VIEW");
		TxnContext viewCtx = new TxnContext();
		viewCtx.addRecord("select-key", viewInputData);
		callService("80003107",viewCtx);
		Recordset viewRs = viewCtx.getRecordset("recordTable");
		if(viewRs != null && viewRs.size()>0){
			for(int k=0;k<viewRs.size();k++){
				DataBus viewBus = (DataBus)viewRs.get(k);
				DataBus viewData = new DataBus();
				String viewName = viewBus.getValue("table_name");
				String viewSchema = viewBus.getValue("table_schema");
				viewData.put("sys_rd_unclaim_table_id", UuidGenerator.getUUID());
				viewData.put("sys_rd_data_source_id", sys_rd_data_source_id);
				viewData.put("unclaim_table_code", viewName);
				viewData.put("unclaim_table_name", "");
				viewData.put("object_schema", viewSchema);
				viewData.put("cur_record_count", "");
				viewData.put("remark", "");
				viewData.put("data_object_type",Constants.DB_OBJECT_VIEW);
				viewData.put("timestamp", curDateTime);
				viewData.put("object_script", "");
				viewData.put("tb_pk_name", "");
				viewData.put("tb_pk_columns", "");
				viewData.put("tb_index_name", "");
				viewData.put("tb_index_columns", "");
				objectRs.add(viewData);
			}
		}
		//获取函数信息
		log.debug("getFunction---->begin:");
		DataBus functionInputData = new DataBus();
		functionInputData.setValue("db_name", db_name);
		functionInputData.setValue("procedure_type", "2");
		TxnContext functionCtx = new TxnContext();
		functionCtx.addRecord("select-key", functionInputData);
		callService("80003109",functionCtx);
		Recordset functionRs = functionCtx.getRecordset("recordFunction");
		if(functionRs != null && functionRs.size()>0){
			for(int l=0;l<functionRs.size();l++){
				DataBus functionBus = (DataBus)functionRs.get(l);
				DataBus functionData = new DataBus();
				String functionName = functionBus.getValue("procedure_name");
				String functionSchema = functionBus.getValue("table_schema");
				functionData.put("sys_rd_unclaim_table_id", UuidGenerator.getUUID());
				functionData.put("sys_rd_data_source_id", sys_rd_data_source_id);
				functionData.put("unclaim_table_code", functionName);
				functionData.put("unclaim_table_name", "");
				functionData.put("object_schema", functionSchema);
				functionData.put("cur_record_count", "");
				functionData.put("remark", "");
				functionData.put("data_object_type",Constants.DB_OBJECT_FUNCTION);
				functionData.put("timestamp", curDateTime);
				functionData.put("object_script", "");
				functionData.put("tb_pk_name", "");
				functionData.put("tb_pk_columns", "");
				functionData.put("tb_index_name", "");
				functionData.put("tb_index_columns", "");
				objectRs.add(functionData);
			}
		}
		//获取存储过程信息
		log.debug("getProcedure---->begin:");
		DataBus procedureInputData = new DataBus();
		procedureInputData.setValue("db_name", db_name);
		procedureInputData.setValue("procedure_type", "1");
		TxnContext procedureCtx = new TxnContext();
		procedureCtx.addRecord("select-key", procedureInputData);
		callService("80003109",procedureCtx);
		Recordset procedureRs = procedureCtx.getRecordset("recordProcedure");
		if(procedureRs != null && procedureRs.size()>0){
			for(int m=0;m<procedureRs.size();m++){
				DataBus procedureBus = (DataBus)procedureRs.get(m);
				DataBus procedureData = new DataBus();
				String procedureName = procedureBus.getValue("procedure_name");
				String procedureSchema = procedureBus.getValue("table_schema");
				procedureData.put("sys_rd_unclaim_table_id", UuidGenerator.getUUID());
				procedureData.put("sys_rd_data_source_id", sys_rd_data_source_id);
				procedureData.put("unclaim_table_code", procedureName);
				procedureData.put("unclaim_table_name", "");
				procedureData.put("object_schema", procedureSchema);
				procedureData.put("cur_record_count", "");
				procedureData.put("remark", "");
				procedureData.put("data_object_type",Constants.DB_OBJECT_PROCEDURES);
				procedureData.put("timestamp", curDateTime);
				procedureData.put("object_script", "");
				procedureData.put("tb_pk_name", "");
				procedureData.put("tb_pk_columns", "");
				procedureData.put("tb_index_name", "");
				procedureData.put("tb_index_columns", "");
				objectRs.add(procedureData);
			}
		}
		/**
		 * 更新未认领资源信息
		 */
		log.debug("updateUnclaim---->begin:");
    	DataBus deleteData = new DataBus();
    	deleteData.put("sys_rd_data_source_id", sys_rd_data_source_id);
        if(objectRs!=null && objectRs.size()>0){
        	//修改数据源同步时间
        	BaseTable table = TableFactory.getInstance().getTableObject(this, TABLE_NAME);
        	table.executeFunction("updateSyncDate", context, upDataSource, outputNode);
        	//删除
        	TxnContext ctx = (TxnContext) context.clone();
        	BaseTable unclaimTable = TableFactory.getInstance().getTableObject(this, "sys_rd_unclaim_table");
        	unclaimTable.executeFunction("deleteByDataSource", ctx, deleteData, outputNode);
        	for(int i=0;i<objectRs.size();i++){
        		DataBus inputDb = (DataBus)objectRs.get(i);
        		unclaimTable.executeFunction("insertBySync", ctx, inputDb, outputNode);
        	}
        }

        if(columnRs!=null && columnRs.size()>0){
        	BaseTable unclaimColumn = TableFactory.getInstance().getTableObject(this, "sys_rd_unclaim_column");
        	unclaimColumn.executeFunction("deleteColumnByDataSource", context, deleteData, outputNode);
        	for(int i=0;i<columnRs.size();i++){
        		DataBus columnDb = (DataBus)columnRs.get(i);
        		unclaimColumn.executeFunction("insert one sys_rd_unclaim_column", context, columnDb, outputNode);
        	}
        }
        /**
         * 获取数据结构变更记录
         */
        log.debug("updateChange---->begin:");
        TxnContext changeCtx = new TxnContext();
        changeCtx.addRecord("select-key", upDataSource);
        callService("8000110",changeCtx);
        log.debug("sync---->end!");
	}
	
	/**
	 * 获取数据结构变更记录
	 * @param context
	 * @throws TxnException
	 */
	public void txn8000110(TxnContext context) throws TxnException{
		DataBus db = context.getRecord("select-key");
		String sys_rd_data_source_id = db.getValue("sys_rd_data_source_id");
		String db_name = db.getValue("db_name");
		String nowDate = CalendarUtil.getCurrentDate();
		String curDateTime = CalendarUtil.getCurrentDateTime();
		//变更记录
		Recordset changeRs = new Recordset();
		BaseTable dataSourceTable = TableFactory.getInstance().getTableObject(this, TABLE_NAME);
		//根据数据源取已认领表信息
		DataBus inputData = new DataBus();
		inputData.setValue("sys_rd_data_source_id", sys_rd_data_source_id);
		inputData.setValue("db_name", db_name);
		TxnContext claimCtx = new TxnContext();
		Attribute.setPageRow(claimCtx, "claimTableRecord", -1);
		dataSourceTable.executeFunction("selectClaimTableList", claimCtx, inputData, "claimTableRecord");
		Recordset claimTableRs = claimCtx.getRecordset("claimTableRecord");
		if(claimTableRs != null && claimTableRs.size()>0){
			for(int i=0;i<claimTableRs.size();i++){
				DataBus outputData = (DataBus)claimTableRs.get(i);
				String tableCode = outputData.getValue("table_code");
				String tableName = outputData.getValue("table_name");
				String tablePk = outputData.getValue("table_primary_key");
				String tableSchema = outputData.getValue("object_schema");
				//根据数据源及表名取未认领表信息
				inputData.setValue("unclaim_table_code", tableCode);
				inputData.setValue("object_schema", tableSchema);
				TxnContext unclaimCtx = new TxnContext();
				int unclaimTableNum = dataSourceTable.executeFunction("selectUnclaimTableContent", unclaimCtx, inputData, "unclaimTableRecord");
				DataBus unclaimTableDb = new DataBus();
				if(unclaimTableNum != 0){
					unclaimTableDb = (DataBus)unclaimCtx.getRecordset("unclaimTableRecord").get(0);
				}
				if(unclaimTableDb == null || unclaimTableDb.isEmpty()){
					//源表删除
					DataBus changeBus = new DataBus();
					changeBus.put("sys_rd_change_id", UuidGenerator.getUUID());
					changeBus.put("sys_rd_data_source_id", sys_rd_data_source_id);
					changeBus.put("db_name", db_name);
					changeBus.put("db_username", tableSchema);
					changeBus.put("table_name", tableCode);
					changeBus.put("table_name_cn", tableName);
					changeBus.put("column_name", "");
					changeBus.put("column_name_cn", "");
					changeBus.put("change_item", Constants.COMPARE_STATUS_TAB_DELETE);
					changeBus.put("change_before", "");
					changeBus.put("change_after", "");
					changeBus.put("change_result", Constants.CHANGE_STATE_NO);
					changeBus.put("change_oprater", "");
					changeBus.put("change_time", nowDate);
					changeBus.put("change_reason", "");
					changeBus.put("timestamp", curDateTime);
					changeRs.add(changeBus);
				}else{
					String unclaimTablePk = unclaimTableDb.getValue("tb_pk_columns");
					if(!tablePk.equals(unclaimTablePk)){
						//表主键变更
						DataBus changeBus = new DataBus();
						changeBus.put("sys_rd_change_id", UuidGenerator.getUUID());
						changeBus.put("sys_rd_data_source_id", sys_rd_data_source_id);
						changeBus.put("db_name", db_name);
						changeBus.put("db_username", tableSchema);
						changeBus.put("table_name", tableCode);
						changeBus.put("table_name_cn", tableName);
						changeBus.put("column_name", "");
						changeBus.put("column_name_cn", "");
						changeBus.put("change_item", Constants.COMPARE_STATUS_PK_CHANGED);
						changeBus.put("change_before", tablePk);
						changeBus.put("change_after", unclaimTablePk);
						changeBus.put("change_result", Constants.CHANGE_STATE_NO);
						changeBus.put("change_oprater", "");
						changeBus.put("change_time", nowDate);
						changeBus.put("change_reason", "");
						changeBus.put("timestamp", curDateTime);
						changeRs.add(changeBus);
					}
					//取已认领表字段信息
					inputData.setValue("table_code", tableCode);
					TxnContext claimColumnCtx = new TxnContext();
					Attribute.setPageRow(claimColumnCtx, "claimColumnRecord", -1);
					dataSourceTable.executeFunction("selectClaimColumnList", claimColumnCtx, inputData, "claimColumnRecord");
					Recordset claimColumnRs = claimColumnCtx.getRecordset("claimColumnRecord");
					if(claimColumnRs != null && claimColumnRs.size()>0){
						for(int j=0;j<claimColumnRs.size();j++){
							DataBus claimColumnBus = (DataBus)claimColumnRs.get(j);
							String columnCode = claimColumnBus.getValue("column_code");
							String columnName = claimColumnBus.getValue("column_name");
							String columnType = claimColumnBus.getValue("column_type");
							String columnLength = claimColumnBus.getValue("column_length");
							//取未认领表字段信息
							inputData.setValue("unclaim_tab_code", tableCode);
							inputData.setValue("unclaim_column_code", columnCode);
							TxnContext unclaimColumnCtx = new TxnContext();
							int unclaimColumnNum = dataSourceTable.executeFunction("selectUnclaimColumnContent", unclaimColumnCtx, inputData, "unclaimColumnRecord");
							DataBus unclaimColumnBus = new DataBus();
							if(unclaimColumnNum != 0){
								unclaimColumnBus = (DataBus)unclaimColumnCtx.getRecordset("unclaimColumnRecord").get(0);
							}
							if(unclaimColumnBus == null || unclaimColumnBus.isEmpty()){
								//源字段删除
								DataBus changeBus = new DataBus();
								changeBus.put("sys_rd_change_id", UuidGenerator.getUUID());
								changeBus.put("sys_rd_data_source_id", sys_rd_data_source_id);
								changeBus.put("db_name", db_name);
								changeBus.put("db_username", tableSchema);
								changeBus.put("table_name", tableCode);
								changeBus.put("table_name_cn", tableName);
								changeBus.put("column_name", columnCode);
								changeBus.put("column_name_cn", columnName);
								changeBus.put("change_item", Constants.COMPARE_STATUS_COL_DELETE);
								changeBus.put("change_before", "");
								changeBus.put("change_after", "");
								changeBus.put("change_result", Constants.CHANGE_STATE_NO);
								changeBus.put("change_oprater", "");
								changeBus.put("change_time", nowDate);
								changeBus.put("change_reason", "");
								changeBus.put("timestamp", curDateTime);
								changeRs.add(changeBus);
							}else{
								String unclaimColumnType = unclaimColumnBus.getValue("unclaim_column_type");
								String unclaimColumnLength = unclaimColumnBus.getValue("unclaim_column_length");
								if(!columnType.equals(unclaimColumnType)){
									//字段类型变更
									DataBus changeBus = new DataBus();
									changeBus.put("sys_rd_change_id", UuidGenerator.getUUID());
									changeBus.put("sys_rd_data_source_id", sys_rd_data_source_id);
									changeBus.put("db_name", db_name);
									changeBus.put("db_username", tableSchema);
									changeBus.put("table_name", tableCode);
									changeBus.put("table_name_cn", tableName);
									changeBus.put("column_name", columnCode);
									changeBus.put("column_name_cn", columnName);
									changeBus.put("change_item", Constants.COMPARE_STATUS_OBJ_CHANGED);
									changeBus.put("change_before", columnType);
									changeBus.put("change_after", unclaimColumnType);
									changeBus.put("change_result", Constants.CHANGE_STATE_NO);
									changeBus.put("change_oprater", "");
									changeBus.put("change_time", nowDate);
									changeBus.put("change_reason", "");
									changeBus.put("timestamp", curDateTime);
									changeRs.add(changeBus);
								}
								if(!columnLength.equals(unclaimColumnLength)){
									//字段长度变更
									DataBus changeBus = new DataBus();
									changeBus.put("sys_rd_change_id", UuidGenerator.getUUID());
									changeBus.put("sys_rd_data_source_id", sys_rd_data_source_id);
									changeBus.put("db_name", db_name);
									changeBus.put("db_username", tableSchema);
									changeBus.put("table_name", tableCode);
									changeBus.put("table_name_cn", tableName);
									changeBus.put("column_name", columnCode);
									changeBus.put("column_name_cn", columnName);
									changeBus.put("change_item", Constants.COMPARE_STATUS_LEN_CHANGED);
									changeBus.put("change_before", columnLength);
									changeBus.put("change_after", unclaimColumnLength);
									changeBus.put("change_result", Constants.CHANGE_STATE_NO);
									changeBus.put("change_oprater", "");
									changeBus.put("change_time", nowDate);
									changeBus.put("change_reason", "");
									changeBus.put("timestamp", curDateTime);
									changeRs.add(changeBus);
								}
							}
						}
					}
					//源表字段增加
					TxnContext columnAddCtx = new TxnContext();
					Attribute.setPageRow(columnAddCtx, "columnAddRecord", -1);
					dataSourceTable.executeFunction("selectColumnAddList", columnAddCtx, inputData, "columnAddRecord");
					Recordset columnAddRs = columnAddCtx.getRecordset("columnAddRecord");
					if(columnAddRs != null && columnAddRs.size()>0){
						for(int k=0;k<columnAddRs.size();k++){
							DataBus columnAddBus = (DataBus)columnAddRs.get(k);
							String unclaimColumnCode = columnAddBus.getValue("unclaim_column_code");
							String unclaimColumnName = columnAddBus.getValue("unclaim_column_name");
							DataBus changeBus = new DataBus();
							changeBus.put("sys_rd_change_id", UuidGenerator.getUUID());
							changeBus.put("sys_rd_data_source_id", sys_rd_data_source_id);
							changeBus.put("db_name", db_name);
							changeBus.put("db_username", tableSchema);
							changeBus.put("table_name", tableCode);
							changeBus.put("table_name_cn", tableName);
							changeBus.put("column_name", unclaimColumnCode);
							changeBus.put("column_name_cn", unclaimColumnName);
							changeBus.put("change_item", Constants.COMPARE_STATUS_COL_ADD);
							changeBus.put("change_before", "");
							changeBus.put("change_after", "");
							changeBus.put("change_result", Constants.CHANGE_STATE_NO);
							changeBus.put("change_oprater", "");
							changeBus.put("change_time", nowDate);
							changeBus.put("change_reason", "");
							changeBus.put("timestamp", curDateTime);
							changeRs.add(changeBus);
						}
					}
				}
			}
		}
		//处理变更记录
		if(changeRs != null && changeRs.size()>0){
			//删除未处理变更记录
			BaseTable changeTable = TableFactory.getInstance().getTableObject(this, "sys_rd_change");
			changeTable.executeUpdate("deleteChangeByDataSource", inputData);
			//插入最新变更记录
			for(int l=0;l<changeRs.size();l++){
				DataBus changeBus = (DataBus)changeRs.get(l);
				changeTable.executeUpdate("insert one sys_rd_change", changeBus);
			}
		}
	}
	/**
	 * 定时同步数据源
	 * @param context
	 * @throws TxnException
	 */
	public void txn8000109(TxnContext context) throws TxnException {
		
	}
	
	/**
	 * 查询数据源是否存在
	 * @param context
	 * @throws TxnException
	 */
	public void txn8000108(TxnContext context) throws TxnException {
		BaseTable table = TableFactory.getInstance().getTableObject(this, TABLE_NAME);
		String operType = context.getRecord(inputNode).getString("oper_type");
		if(operType.equals("update")){
			table.executeFunction("selectDataSourceExsitForUpdate", context, inputNode, outputNode);
		}else{
			table.executeFunction("selectDataSourceExsit", context, inputNode, outputNode);
		}
	}

	
	/**
	 * 获取连接配置信息
	 */
	private DataBus getDBConfigData(String db_name) throws TxnException {
		DataBus db = null;
		DataBus[] dbs = Config.getInstance().getJdbcConfig();
		for (int i = 0; i < dbs.length; i++) {
			String dbName = dbs[i].getString("db-name");
			if (db_name.equalsIgnoreCase(dbName.toLowerCase())) {
				return dbs[i];
			}
		}
		return db;
	}
	
	/**
	 * 转化数据类型
	 * @param iColType
	 * @return
	 */
	public String changeColumnType(int iColType) {
		String colType;
		switch (iColType) {
		case 1:
			// char
			colType = Constants.COL_DATATYPE_CHAR;
			break;
		case 12:
		case -1: {
			// varchar
			colType = Constants.COL_DATATYPE_VARCHAR;
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
			colType = Constants.COL_DATATYPE_NUMBER;
		}
			break;
		case 91:
		case 92:
		case 93: {
			// date
			colType = Constants.COL_DATATYPE_DATE;

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
	 * 判断字符串是否存在乱码
	 * @param str
	 * @return
	 */
	public static boolean isMessyCode(String str) { 
	     for (int i = 0; i < str.length(); i++) { 
	        char c = str.charAt(i); 
	        // 当从Unicode编码向某个字符集转换时，如果在该字符集中没有对应的编码，则得到0x3f（即问号字符?） 
	        //从其他字符集向Unicode编码转换时，如果这个二进制数在该字符集中没有标识任何的字符，则得到的结果是0xfffd 
	        //System.out.println("--- " + (int) c); 
	        if ((int) c == 0xfffd) { 
	         // 存在乱码 
	         //System.out.println("存在乱码 " + (int) c); 
	         return true; 
	        } 
	     } 
	     return false;    
	}
			
	/**
	 * 重载父类的方法，用于替换交易接口的输入变量
	 * 调用函数
	 * @param funcName 方法名称
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void doService( String funcName,
			TxnContext context ) throws TxnException
	{
		Method method = (Method)txnMethods.get( funcName );
		if( method == null ){
			funcName = this.getClass().getName() + "#" + funcName;
			throw new TxnErrorException( ErrorConstant.JAVA_METHOD_NOTFOUND,
					"没有找到交易码[" + txnCode + ":" + funcName + "]的处理函数"
			);
		}
		
		// 执行
		TxnContext appContext = new TxnContext( context );
		invoke( method, appContext );
		//SysSystemSemanticContext appContext = new SysSystemSemanticContext( context );
		//invoke( method, appContext );
	}
}
