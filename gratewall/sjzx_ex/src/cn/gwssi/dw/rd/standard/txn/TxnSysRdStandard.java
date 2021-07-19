package cn.gwssi.dw.rd.standard.txn;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;

import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.context.vo.VoFileInfo;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.common.util.CalendarUtil;
import com.gwssi.common.util.UploadUtil;
import com.gwssi.common.util.UuidGenerator;
import java.text.SimpleDateFormat;
import cn.gwssi.dw.rd.standard.vo.SysRdStandardContext;

public class TxnSysRdStandard extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysRdStandard.class, SysRdStandardContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "sys_rd_standard";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select sys_rd_standard list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one sys_rd_standard";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one sys_rd_standard";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one sys_rd_standard";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one sys_rd_standard";
	
	public static final String	DB_CONFIG		= "app";
	/**
	 * 构造函数
	 */
	public TxnSysRdStandard()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询规范列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000201( SysRdStandardContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoSysRdStandardSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoSysRdStandard result[] = context.getSysRdStandards( outputNode );
	}
	/** 查询规范列表，概览页面用
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000207( SysRdStandardContext context ) throws TxnException
	{
		Attribute.setPageRow(context, outputNode, 5);
		callService("7000201", context);
	}
	
	/** 修改规范信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000202( SysRdStandardContext context ) throws TxnException
	{
		System.out.println("txn7000202");
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		
		setFile(context);
		
		
		// 修改记录的内容 VoSysRdStandard sys_rd_standard = context.getSysRdStandard( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加规范信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000203( SysRdStandardContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoSysRdStandard sys_rd_standard = context.getSysRdStandard( inputNode );
		setFile(context);
		table.executeFunction("queryMaxSort", context, inputNode, "sort");//获取最大排序号
		Recordset rs = context.getRecordset("sort");
		DataBus tempSort = (DataBus)rs.get(0);
        if(tempSort.getValue("max(sort)")!=null&&!tempSort.getValue("max(sort)").equals("")){
        	int Intsort=Integer.parseInt(tempSort.getValue("max(sort)"))+1;
            String sort=Intsort+"";
        	context.getRecord("record").setValue("sort", sort);
        }else{
        	context.getRecord("record").setValue("sort","1" );
        }
		context.getRecord("record").setValue("sys_rd_standard_id",UuidGenerator.getUUID() );//添加ID
		String TimeStamp=CalendarUtil.getCurrentDateTime();//添加时间戳
		context.getRecord("record").setValue("timestamp", TimeStamp);
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询规范用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000204( SysRdStandardContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysRdStandardPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoSysRdStandard result = context.getSysRdStandard( outputNode );
	}
	
	/** 删除规范信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000205( SysRdStandardContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoSysRdStandardPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询规范用于视图
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000206( SysRdStandardContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoSysRdStandardPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 数据库监控
	 * 空交易，便于控制权限
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000001( SysRdStandardContext context ) throws TxnException
	{
		
	}
	
	/** 中间件监控
	 * 空交易，便于控制权限
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000002( SysRdStandardContext context ) throws TxnException
	{
		
	}
	
	/** 主机监控
	 * 空交易，便于控制权限
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000003( SysRdStandardContext context ) throws TxnException
	{
		
	}
	
	/** 全文检索监控
	 * 空交易，便于控制权限
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000004( SysRdStandardContext context ) throws TxnException
	{
		
	}
	
	/** 接口服务监控
	 * 空交易，便于控制权限
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000005( SysRdStandardContext context ) throws TxnException
	{
		
	}
	
	/** 警情库
	 * 空交易，便于控制权限
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000006( SysRdStandardContext context ) throws TxnException
	{
		
	}
	
	/** 监控管理
	 * 空交易，便于控制权限
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000007( SysRdStandardContext context ) throws TxnException
	{
		
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
		SysRdStandardContext appContext = new SysRdStandardContext( context );
		invoke( method, appContext );
	}
	
	/*
	 * 处理上传文件
	 */
	private void setFile(SysRdStandardContext context)
	{
		String rootPath = java.util.ResourceBundle.getBundle(DB_CONFIG).getString("docFilePath");
		rootPath=rootPath+"Specification/";
		 File file = new File(rootPath);
		  //判断文件夹是否存在,如果不存在则创建文件夹
		  if (!file.exists()) {
		   file.mkdir();
		  }
		  file=null;
		VoFileInfo[] files = context.getConttrolData().getUploadFileList();
		for(int i=0;i<files.length;i++){
			//得到上传时的文件名
			String localName = files[i].getLocalFileName();
			//得到文件在服务器上的绝对路径和文件名
			String serverName = files[i].getOriFileName();
			String timestamp = CalendarUtil.getCurrentDateTime();
			String filename = timestamp.replaceAll("-", "").replaceAll(":", "").replaceAll(" ","")+".doc";
			try {
				InputStream in = new FileInputStream(localName);
				OutputStream out = new FileOutputStream(new File(rootPath+""+filename));
			//将文件流输出到指定上传目录
				try {
					UploadUtil.exchangeStream(in,out);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block 
				
				e.printStackTrace();
			}
			context.getRecord("record").setValue("file_path", rootPath+filename);

		}
	}
}
