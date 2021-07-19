package com.gwssi.dw.metadata.msurunit.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnMasterTable;
import com.gwssi.dw.metadata.msurunit.vo.GzZbJldwFlContext;

public class TxnGzZbJldwFl extends TxnMasterTable
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnGzZbJldwFl.class, GzZbJldwFlContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "gz_zb_jldw_fl";
		
	// 查询记录
	private static final String SELECT_FUNCTION = "select one gz_zb_jldw_fl";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one gz_zb_jldw_fl";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one gz_zb_jldw_fl";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one gz_zb_jldw_fl";

	
	/**
	 * 构造函数
	 */
	public TxnGzZbJldwFl()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询计量单位类别列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn301051( GzZbJldwFlContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "queryrowset", context, inputNode, outputNode );

	}

	/** 修改计量单位类别信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn301052( GzZbJldwFlContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
		setBizLog("修改计量单位类别：", context,context.getRecord(inputNode).getValue("dwlb_cn_mc"));
	}

	/** 增加计量单位类别信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn301053( GzZbJldwFlContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
		setBizLog("增加计量单位类别：", context,context.getRecord(inputNode).getValue("dwlb_cn_mc"));
	}

	/** 查询计量单位类别用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn301054( GzZbJldwFlContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
	}

	/** 删除计量单位类别信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn301055( GzZbJldwFlContext context ) throws TxnException
	{
		/**
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Recordset rs=null;
		rs=context.getRecordset(inputNode);
		int m,n;
		m=rs.size();
		String dwlbdm;
		String dwlbcnmc;
		DataBus databus;
		String totalerrormessage="";
		for(n=0;n<m;n++)
		{
			databus=rs.get(n);
			dwlbdm=databus.getValue(VoGzZbJldwFl.ITEM_DWLB_DM);
			dwlbcnmc=databus.getValue(VoGzZbJldwFl.ITEM_DWLB_CN_MC);	
			String errormessage=dwlbcnmc+"已被使用,不能删除!";
			boolean ss = isCanDelete(context,dwlbdm);
			if(ss)
			{
				table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
			}
			else
			{
				totalerrormessage=""+totalerrormessage+errormessage;
			}

		}
		if(totalerrormessage==null||totalerrormessage.length()<=0)
		{
			
		}
		else
		{
		throw new TxnDataException("",totalerrormessage);
		}
		*/
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );	
		Recordset rs = context.getRecordset("select-key");
		DataBus dataBus = null;
		DataBus countBus = null;
		GzZbJldwFlContext gzZbJldwFlContext = null;
		String dwlb_cn_mc = "";
		String count = "";
		String errorString  = "";
		String log_desc = "";
		for(int i=0; i<rs.size();i++){
			dataBus = rs.get(i);
			dwlb_cn_mc = dataBus.getValue("dwlb_cn_mc");
			gzZbJldwFlContext = new GzZbJldwFlContext();
			gzZbJldwFlContext.addRecord("select-key", dataBus);
			table.executeFunction( "selectSjfx", gzZbJldwFlContext, "record", "record" );
			countBus = gzZbJldwFlContext.getRecord("record");
			count = countBus.getValue("count");
			
			if(count!=null&&!count.equals("0")){
				errorString+=dwlb_cn_mc+";";
			}			
			log_desc+="," + dwlb_cn_mc;
		}
		
		if(!errorString.equals("")){
			errorString = errorString.substring(0,errorString.length()-1);
			throw new TxnDataException("","单位类别:" + errorString + " 已被使用，不能删除;");
		}
		// 删除记录的主键列表 VoGzDmJcdmPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		 table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
		 if(!log_desc.equals("")) log_desc = log_desc.substring(1);
		 setBizLog("删除计量单位类别：", context,log_desc);
	}
	
	
	
	
	/**
	 * 判断计量单位类别能否删除
	 * 删除条件：该计量单位类别下没有计量单位
	 * @param context
	 * @param jgid
	 * @return
	 * @throws TxnException
	 */
	private boolean isCanDelete(TxnContext context,String dwlbdm) throws TxnException
	{
		boolean iscan=false;
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		int i=0;
		try
		{
			i=table.executeFunction( "query", context, inputNode, outputNode );
		}
		catch(TxnDataException ex)
		{	
			throw new TxnDataException("","sql语句执行错误");
		}
		if(i==0)
		{
			iscan=true;
		}
		return iscan;
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
		GzZbJldwFlContext appContext = new GzZbJldwFlContext( context );
		invoke( method, appContext );
	}
}
