package cn.gwssi.dw.rd.metadata.txn;

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
import cn.gwssi.dw.rd.metadata.vo.SysRdTableRelationContext;

import com.gwssi.common.util.CalendarUtil;
import com.gwssi.common.util.UuidGenerator;

public class TxnSysRdTableRelation extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysRdTableRelation.class, SysRdTableRelationContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "sys_rd_table_relation";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select sys_rd_table_relation list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one sys_rd_table_relation";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one sys_rd_table_relation";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one sys_rd_table_relation";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one sys_rd_table_relation";
	
	/**
	 * 构造函数
	 */
	public TxnSysRdTableRelation()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询表关系列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80002601( SysRdTableRelationContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoSysRdTableRelationSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoSysRdTableRelation result[] = context.getSysRdTableRelations( outputNode );
	}
	
	/** 修改表关系信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80002602( SysRdTableRelationContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoSysRdTableRelation sys_rd_table_relation = context.getSysRdTableRelation( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加表关系信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80002603( SysRdTableRelationContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String sys_rd_table_id =   context.getRecord("record").getValue("sys_rd_table_id");
		String table_fk = context.getRecord("record").getValue("table_fk");
		String ref_sys_rd_table_id = context.getRecord("record").getValue("ref_sys_rd_table_id");
		String relation_table_fk  = context.getRecord("record").getValue("relation_table_fk");
		TxnContext context2 = new TxnContext();
		DataBus db1 = new DataBus();
		db1.setValue("sys_rd_table_id", sys_rd_table_id);
		db1.setValue("table_fk", table_fk);
		db1.setValue("ref_sys_rd_table_id", ref_sys_rd_table_id);
		db1.setValue("relation_table_fk", relation_table_fk);
		context2.addRecord("select-key", db1);
		callService("80002601", context2);
		Recordset rs2 = context2.getRecordset("record");
		if(rs2!=null&&rs2.size()>0){
			 throw new TxnErrorException("999999:未知的错误", "表关系已经存在！");
		}
		
	    context.getRecord("record").setValue("sys_rd_table_relation_id",UuidGenerator.getUUID());
	    context.getRecord("record").setValue("timestamp",CalendarUtil.getCurrentDateTime());
	    context.getRecord("record").setValue("table_relation_type","");
	    context.getRecord("record").setValue("sys_rd_system_id","");
	    context.getRecord("record").setValue("ref_sys_rd_system_id","");
	    
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询表关系用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80002604( SysRdTableRelationContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysRdTableRelationPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoSysRdTableRelation result = context.getSysRdTableRelation( outputNode );
	}
	
	/** 删除表关系信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80002605( SysRdTableRelationContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		//System.out.println("删除表关系 \n"+context);
		// 删除记录的主键列表 VoSysRdTableRelationPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/**
	 * 修改表关系信息
	 * @param context
	 * @throws TxnException
	 */
	public void txn80002606( SysRdTableRelationContext context ) throws TxnException
	{
		 
		DataBus db1 = context.getRecord("select-key");
		DataBus db = new DataBus(); 
		String sys_rd_table_id = db1.getValue("sys_rd_table_id");
		db.setValue("sys_rd_table_id",db1.getValue("sys_rd_table_id"));
		db.setValue("sys_rd_system_id",db1.getValue("sys_rd_system_id"));
		db.setValue("sys_rd_data_source_id",db1.getValue("sys_rd_data_source_id"));
		db.setValue("table_name",db1.getValue("table_name"));
		db.setValue("table_code",db1.getValue("table_code"));
		
		DataBus db2 = new DataBus(); 		 
		db2.setValue("sys_rd_table_id",db1.getValue("sys_rd_table_id"));
		
		//查询表关联信息列表
		
		DataBus  db_record3 = new DataBus();
			
	   //根据主表信息查询主表字段信息
		TxnContext context2 = new TxnContext();
		context2.addRecord("select-key", db2);
	    Attribute.setPageRow(context2, "record", -1);
	    callService("80002501", context2);
	        
		Recordset rs1 = context2.getRecordset("record");
		StringBuffer sb1 = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		if(rs1!=null&&rs1.size()>0){
			for(int j=0; j<rs1.size(); j++){
				DataBus db3  = rs1.get(j);
					if(j==0){
						sb1.append(db3.getValue("column_name"));
						sb2.append(db3.getValue("column_code"));
					}else{
						sb1.append(","+db3.getValue("column_name"));
						sb2.append(","+db3.getValue("column_code"));
					} 
			}
				
		}
		db_record3.setValue("table_fk_str", sb1.toString());
		db_record3.setValue("table_fk_name_str", sb2.toString());
		
		
		//查询数据源信息
		TxnContext context3 = new TxnContext();
		callService("8000101", context3);
		Recordset rs3 = context3.getRecordset("record");
		StringBuffer sb3 = new StringBuffer();
		StringBuffer sb4 = new StringBuffer();
			if(rs3!=null&&rs3.size()>0){
				for(int j=0; j<rs3.size(); j++){
					DataBus db3  = rs3.get(j);
					if(j==0){
						sb3.append(db3.getValue("sys_rd_data_source_id"));
						sb4.append(db3.getValue("db_name"));
					}else{
						sb3.append(","+db3.getValue("sys_rd_data_source_id"));
						sb4.append(","+db3.getValue("db_name"));
					}
				    
				}
			}
			
		db_record3.setValue("ds_id_str", sb3.toString());
		db_record3.setValue("ds_name_str", sb4.toString());
		db_record3.setValue("sys_rd_table_id",db1.getValue("sys_rd_table_id"));
		db_record3.setValue("sys_rd_system_id",db1.getValue("sys_rd_system_id"));
		db_record3.setValue("sys_rd_data_source_id",db1.getValue("sys_rd_data_source_id"));
		db_record3.setValue("table_name",db1.getValue("table_name"));
		db_record3.setValue("table_code",db1.getValue("table_code"));
		
		context.addRecord("record2",db_record3);
		
		TxnContext context4 = new TxnContext();
		DataBus db4 = new DataBus();
		db4.setValue("sys_rd_table_id", sys_rd_table_id);
		context4.addRecord("select-key", db4);
		
		Attribute.setPageRow(context4, "record", -1);
		callService("80002601", context4);
		Recordset rs2 = context4.getRecordset("record");
		Recordset rs4 = new Recordset();
		if(rs2!=null&&rs2.size()>0){
			for(int j=0; j<rs2.size(); j++){
				DataBus db5 = 	rs2.get(j);
				rs4.add(db5);
			}
		}	
		if(rs4!=null&&rs4.size()>0){
			for(int j=0; j<rs4.size(); j++){
				DataBus db6 = rs4.get(j);
				db6.setValue("no", String.valueOf(j+1));
				db6.setValue("table_fk_name", db6.getValue("table_fk")+"("+db6.getValue("table_fk_name")+")");
				db6.setValue("relation_table_name", db6.getValue("relation_table_code")+"("+db6.getValue("relation_table_name")+")");
				db6.setValue("relation_table_fk_name", db6.getValue("relation_table_fk")+"("+db6.getValue("relation_table_fk_name")+")");
			}
		}
		context.addRecord("record", rs4);
		
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
		SysRdTableRelationContext appContext = new SysRdTableRelationContext( context );
		invoke( method, appContext );
	}
}
