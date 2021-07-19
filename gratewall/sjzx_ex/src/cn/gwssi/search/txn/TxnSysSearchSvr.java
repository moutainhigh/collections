package cn.gwssi.search.txn;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;
import cn.gwssi.search.vo.SysSearchSvrContext;

import com.gwssi.common.util.UuidGenerator;

public class TxnSysSearchSvr extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysSearchSvr.class, SysSearchSvrContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "sys_search_svr";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select sys_search_svr list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one sys_search_svr";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one sys_search_svr";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one sys_search_svr";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one sys_search_svr";
	
	/**
	 * 构造函数
	 */
	public TxnSysSearchSvr()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询检索服务列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn50010201( SysSearchSvrContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoSysSearchSvrSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoSysSearchSvr result[] = context.getSysSearchSvrs( outputNode );
	}
	
	/** 修改检索服务信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn50010202( SysSearchSvrContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoSysSearchSvr sys_search_svr = context.getSysSearchSvr( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加检索服务信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn50010203( SysSearchSvrContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		BaseTable tableParam = TableFactory.getInstance().getTableObject( this, "sys_search_svr_param" );
	    DataBus db = context.getRecord("record");
	    DataBus db2 = new DataBus();
	    
	    TxnContext ctx = new TxnContext();
	    
	    String sys_search_svr_id = UuidGenerator.getUUID();
	    
	    String svr_name = db.getValue("svr_name");
	    String svr_db = db.getValue("svr_db");
	    String svr_template = db.getValue("svr_template");
	    String svr_param_str = db.getValue("svr_param_str");
	    
	    db2.setValue("sys_search_svr_id", sys_search_svr_id);
	    db2.setValue("svr_name", svr_name);
	 
	    db2.setValue("svr_db", svr_db);
	    db2.setValue("svr_template", svr_template);
	    ArrayList list = new ArrayList();
	    ArrayList list2 = new ArrayList();
	    String[] a = svr_param_str.split(";");
	    if(a.length>0){
	    	  TxnContext ctx2 = new TxnContext();
	    	  tableParam.executeSelect("SELECT MAX(sort_order) as sort_order FROM sys_search_svr_param", ctx2, "max-order");
	 		   String max = ctx2.getRecord("max-order").getValue("sort_order");
	 		   if(max.trim().equals(""))
	 			  max = "0";
	 		   int count = Integer.parseInt(max) + 1 ;
	 		   
	    	// table.executeBatch(sqlStmt)
	 	    
	 	    
	 	    SqlStatement[] sqlStmt = new SqlStatement[a.length];
	 	    
	 		for (int j = 0; j < a.length; j++) {
	 			SqlStatement ss = new SqlStatement();
	 			String [] b = a[j].split(",");
	 			StringBuffer  sql =new StringBuffer( " insert into sys_search_svr_param " +
	 					"(SYS_SEARCH_SVR_PARAM_ID, SYS_SEARCH_SVR_ID, COLUMN_NAME, COLUMN_TYPE, INDEX_TYPE, WEIGHT, DB_NAME,SORT_ORDER) values");
	 			sql.append(" ( '")
	 			   .append(UuidGenerator.getUUID())
	 			   .append("','")
	 			   .append(sys_search_svr_id)
	 			   .append("','")
	 			   .append(b[0])
	 			   .append("','")
	 			   .append(b[1])
	 			   .append("','")
	 			   .append(b[2])
	 			   .append("','")
	 			   .append(b[3])
	 			   .append("','")
	 			   .append(svr_db)
	 			   .append("','")
	 			   .append( Integer.toString(count++) )
	 			   .append("' )");	
	 			ss.addSqlStmt(sql.toString());
	 			sqlStmt[j] = ss;
	 			
	 			//拼查询串
	 			if(b[2]!=null&&b[2].equals("1")&&b[1]!=null&&!b[1].equals("DATE")){
	 				
	 				//==1时需要全文检索 ,==0时只是显示
	 				//日期型数据经过试验不能加入检索字段
	 				String t = b[0]+"/"+b[3];
	 				list.add(t);
	 			}
	 			list2.add(b[0]);
	 			
	 		}
	 		tableParam.executeBatch(sqlStmt);
	    }
	    StringBuffer queryStr = new StringBuffer();
	    
	    if(list.size()>0){
	    	for(int j=0; j<list.size();j++){
	    		if(j==0){
	    			queryStr.append( list.get(j) );
	    		}else{
	    			queryStr.append(",").append( list.get(j) );
	    		}
	    	}
	    }
	    StringBuffer columns = new StringBuffer();
	    
	    if(list2.size()>0){
	    	for(int j=0; j<list2.size();j++){
	    		if(j==0){
	    			columns.append( list2.get(j) );
	    		}else{
	    			columns.append(",").append( list2.get(j) );
	    		}
	    	}
	    }
	    db2.setValue("svr_query", queryStr.toString());
	    db2.setValue("columns", columns.toString());
	    ctx.addRecord("record", db2);
	    table.executeFunction( INSERT_FUNCTION, ctx, inputNode, outputNode );
	    
		// 增加记录的内容 VoSysSearchSvr sys_search_svr = context.getSysSearchSvr( inputNode );
		
		//table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询检索服务用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn50010204( SysSearchSvrContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysSearchSvrPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoSysSearchSvr result = context.getSysSearchSvr( outputNode );
	}
	
	/** 删除检索服务信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn50010205( SysSearchSvrContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoSysSearchSvrPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
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
		SysSearchSvrContext appContext = new SysSearchSvrContext( context );
		invoke( method, appContext );
	}
}
