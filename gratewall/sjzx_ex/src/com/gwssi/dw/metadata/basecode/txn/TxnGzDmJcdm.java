package com.gwssi.dw.metadata.basecode.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnMasterTable;

import com.gwssi.dw.metadata.basecode.vo.GzDmJcdmContext;

public class TxnGzDmJcdm extends TxnMasterTable
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnGzDmJcdm.class, GzDmJcdmContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "gz_dm_jcdm";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select gz_dm_jcdm list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one gz_dm_jcdm";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one gz_dm_jcdm";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one gz_dm_jcdm";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one gz_dm_jcdm";

	// 明细表
	private static final String detailTables[][] = {
		
		// 仅仅是为了处理最后一个 [,]；可以删除
		{ null, null }
	};
	
	/**
	 * 构造函数
	 */
	public TxnGzDmJcdm()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询基础代码列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn301011( GzDmJcdmContext context ) throws TxnException
	{
		DataBus db = context.getRecord(inputNode);
		String jc_dm_dm = db.getValue("jc_dm_dm");
		if(jc_dm_dm != null && !jc_dm_dm.equals("")){
			jc_dm_dm = jc_dm_dm.toUpperCase();
			db.setValue("jc_dm_dm", jc_dm_dm);
		}
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoGzDmJcdmSelectKey selectKey = context.getSelectKey( inputNode );
		//loadMasterTable( table, ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoGzDmJcdm result[] = context.getGzDmJcdms( outputNode );
		//table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		table.executeFunction( "selectSystemCodeTable", context, inputNode, outputNode );
		// 从表
//		for( int ii=0; ii<detailTables.length; ii++ ){
//			String detail[] = detailTables[ii];
//			if( detail[0] == null || detail[0].length() == 0 ){
//				continue;
//			}
//			
//			try{
//				loadDetailTable( detail[1], context, inputNode, detail[0] );
//			}
//			catch( Exception e ){
//				log.warn( "加载明细表[" + detail[0] + "]错误", e );
//			}
//		}
	}

	
	/** 查询基础代码列表_概览
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn301016( GzDmJcdmContext context ) throws TxnException
	{
		Attribute.setPageRow(context, outputNode, 5);
	    callService("301011", context);
	}
	/** 修改基础代码信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn301012( GzDmJcdmContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		
		DataBus dataBus = context.getRecord("record");
		String jc_dm_id = dataBus.getValue("jc_dm_id");
		String dm = dataBus.getValue("jc_dm_dm");
		String jc_dm_mc = dataBus.getValue("jc_dm_mc");
		
		GzDmJcdmContext oldContext  = new GzDmJcdmContext();
		DataBus oldDb = oldContext.getRecord("primary-key");
		oldDb.setValue("jc_dm_id", jc_dm_id);	
		table.executeFunction( SELECT_FUNCTION, oldContext, "primary-key", "record" );
		String oldDm = oldContext.getRecord("record").getValue("jc_dm_dm");
		if(!dm.equals(oldDm)){
			table.executeFunction( "verifyJcdmdm", context, "record", "record" );
			DataBus queryDataBus = context.getRecord("record");
			String count = queryDataBus.getValue("count");
			if(count!=null&&!count.equals("0")){
				throw new TxnDataException("","基础代码代码："+dm+"已存在，请输入其他代码！");
			}
		}
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
		setBizLog("修改基础代码：", context,dm + "-" + jc_dm_mc);
	}

	/** 增加基础代码信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn301013( GzDmJcdmContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "verifyJcdmdm", context, "record", "record" );
		DataBus dataBus = context.getRecord("record");		
		String dm = dataBus.getValue("jc_dm_dm");
		
		if(dm != null && !dm.equals("")){
			dm = dm.toUpperCase();
			dataBus.setValue("jc_dm_dm", dm);
		}
		String jc_dm_mc = dataBus.getValue("jc_dm_mc");
		String count = dataBus.getValue("count");
		if(count!=null&&!count.equals("0")){
			throw new TxnDataException("","基础代码代码："+dm+"已存在，不能增加！");
		}
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
		setBizLog("增加基础代码：", context,dm + "-" + jc_dm_mc);
	}

	/** 查询基础代码用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn301014( GzDmJcdmContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
	}

	/** 删除基础代码信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn301015( GzDmJcdmContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );	
		Recordset rs = context.getRecordset("select-key");
		DataBus dataBus = null;
		DataBus countBus = null;
		GzDmJcdmContext gzDmJcdmContext = null;
		String jc_dm_mc = "";
		String jc_dm_dm = "";
		String count = "";
		String errorString  = "";	
		String log_desc = "";
		for(int i=0; i<rs.size();i++){
			dataBus = rs.get(i);
			jc_dm_mc = dataBus.getValue("jc_dm_mc");
			jc_dm_dm = dataBus.getValue("jc_dm_dm");
			gzDmJcdmContext = new GzDmJcdmContext();
			gzDmJcdmContext.addRecord("select-key", dataBus);
			table.executeFunction( "selectSjfx", gzDmJcdmContext, "record", "record" );
			countBus = gzDmJcdmContext.getRecord("record");
			count = countBus.getValue("count");
			if(count!=null&&!count.equals("0")){
				errorString+=jc_dm_mc+";";
			}	
			log_desc+="," + jc_dm_dm + "-" +jc_dm_mc;
		}
		if(!errorString.equals("")){
			errorString = errorString.substring(0,errorString.length()-1);
			throw new TxnDataException("","基础代码:" + errorString + " 已被使用，不能删除;");
		}
		if(!log_desc.equals("")) log_desc = log_desc.substring(1);
		// 删除记录的主键列表 VoGzDmJcdmPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		 table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
		 setBizLog("删除基础代码：", context,log_desc);
	}
	/**
	 * 记录日志
	 * @param type
	 * @param context
	 */
    private void setBizLog (String type,TxnContext context,String jgmc){
    	
    	context.getRecord("biz_log").setValue("desc", type + jgmc);
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
		GzDmJcdmContext appContext = new GzDmJcdmContext( context );
		invoke( method, appContext );
	}
}
