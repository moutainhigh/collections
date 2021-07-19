package com.gwssi.dw.aic.download.txn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.HashMap;

import jxl.read.biff.BiffException;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.dw.aic.download.vo.DownloadCompareContext;

public class TxnDownloadCompare extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnDownloadCompare.class, DownloadCompareContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "download_compare";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select download_compare list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one download_compare";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one download_compare";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one download_compare";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one download_compare";
	
	/**
	 * 构造函数
	 */
	public TxnDownloadCompare()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询比对下载列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60500001( DownloadCompareContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoDownloadCompareSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoDownloadCompare result[] = context.getDownloadCompares( outputNode );
	}
	
	/** 修改比对下载信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60500002( DownloadCompareContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoDownloadCompare download_compare = context.getDownloadCompare( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加比对下载信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60500003( DownloadCompareContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoDownloadCompare download_compare = context.getDownloadCompare( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询比对下载用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60500004( DownloadCompareContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoDownloadComparePrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoDownloadCompare result = context.getDownloadCompare( outputNode );
	}
	
	/** 删除比对下载信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60500005( DownloadCompareContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );		
		// 删除过程
		// 1. 循环反查数据库数据
		Recordset rs = context.getRecordset(inputNode);
		for (int i=0; i < rs.size(); i++){
			TxnContext txnContext = new TxnContext();
			txnContext.getRecord(inputNode).setValue("download_compare_id", 
					rs.get(i).getValue("download_compare_id"));
			table.executeFunction(ROWSET_FUNCTION, txnContext, inputNode, "record");
			if (txnContext.getRecordset("record").size() == 0){
				continue;
			}
			// 2. 先删除创建的临时表
			// 2.1 查询表是否存在
			// 2.2 删除表
			TxnContext tc0 = new TxnContext();
			tc0.getRecord("select-key").setValue("table_name", 
					txnContext.getRecord("record").getValue("table_name"));
			int count_tc0 = table.executeFunction("queryTableIsExists", tc0, "select-key", "record");
			if (count_tc0 > 0){
				TxnContext tc1 = new TxnContext();
				tc1.getRecord("select-key").setValue("table_name", 
						txnContext.getRecord("record").getValue("table_name"));			
				table.executeFunction("deleteTempTable", tc1, "select-key", "record");
			}
			TxnContext tc00 = new TxnContext();
			tc00.getRecord("select-key").setValue("table_name", 
					txnContext.getRecord("record").getValue("table_name") + "_LS");
			int count_tc00 = table.executeFunction("queryTableIsExists", tc00, "select-key", "record");
			if (count_tc00 > 0){
				TxnContext tc10 = new TxnContext();
				tc10.getRecord("select-key").setValue("table_name", 
						txnContext.getRecord("record").getValue("table_name") + "_LS");			
				table.executeFunction("deleteTempTable", tc10, "select-key", "record");
			}
			
			// 3. 删除download_columns中的字段记录
			TxnContext tc2 = new TxnContext();
			tc2.getRecord("select-key").setValue("table_no", 
					txnContext.getRecord("record").getValue("table_no"));
			table.executeFunction("deleteDataInColumnTable", tc2, "select-key", "record");
		}
		
		// 3. 删除download_compare中的记录
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/**
	 * 上传文件
	 * @param context
	 * @throws TxnException
	 */
	public void txn60500006( DownloadCompareContext context ) throws TxnException
	{
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String filePath = context.getRecord("record").getValue("uploadFileInput");
		String tableName = context.getRecord("record").getValue("table_name");
		System.out.println(">>>>filePath::"+filePath);
		try {
			if (filePath.endsWith(".txt") || filePath.endsWith(".TXT")){
				destroyUploadTxtFile(filePath);
			}else{
				destroyUploadFile(filePath);
			}
		} catch (BiffException e) {
			e.printStackTrace();
			throw new TxnDataException("999999", "文件解析出错!"); 
		} catch (IOException e) {
			e.printStackTrace();
			throw new TxnDataException("999999", "文件解析出错!");
		}
		
		String table_no = com.gwssi.common.util.UuidGenerator.getUUID();
		String download_compare_id = com.gwssi.common.util.UuidGenerator.getUUID();
		TxnContext txnContext = new TxnContext();
		DataBus db = txnContext.getRecord("record");
		db.setValue("download_compare_id", download_compare_id);
		db.setValue("table_no", table_no);
		db.setValue("table_name", tableName.toUpperCase());
		db.setValue("table_name_cn", "比对表(" + tableName + ")");
		db.setValue("create_user", context.getRecord("oper-data").getValue("userID"));
		db.setValue("create_date", com.gwssi.common.util.DateUtil.getYMDTime());
		db.setValue("create_time", com.gwssi.common.util.DateUtil.getHHmmssTime());
		db.setValue("gen_table_name", tableName.toUpperCase() + "_RS");
		db.setValue("remark", "");
		db.setValue("download_cn_list", "");
		db.setValue("download_en_list", "");
		db.setValue("bak1", "");
		db.setValue("bak2", "");
		db.setValue("bak3", "");
		db.setValue("bak4", "");
		db.setValue("bak5", "");
		System.out.println("txnContext:" + txnContext);
		table.executeFunction(INSERT_FUNCTION, txnContext, "record", "record");
		context.getRecord("record").setValue("table_no", table_no);
		context.getRecord("record").setValue("download_compare_id", download_compare_id);
	}
	
	/**
	 * 处理修改备注信息
	 * @param context
	 * @throws TxnException
	 */
	public void txn60500007( DownloadCompareContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoDownloadComparePrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( "updateRemark", context, inputNode, outputNode );
		// 查询到的记录内容 VoDownloadCompare result = context.getDownloadCompare( outputNode );
	}
	
	/**
	 * 查询比对下载信息
	 * @param context
	 * @throws TxnException
	 */
	public void txn60500008( DownloadCompareContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoDownloadCompareSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( "queryResult", context, inputNode, outputNode );
		// 查询到的记录集 VoDownloadCompare result[] = context.getDownloadCompares( outputNode );
	}
	
	/**
	 * 处理上传的文件
	 * @return
	 * @throws IOException 
	 * @throws BiffException 
	 * @throws TxnDataException 
	 */
	private boolean destroyUploadFile(String filePath) throws BiffException, IOException, TxnDataException{
		File file = new File(filePath);
		jxl.Workbook xls = jxl.Workbook.getWorkbook(file);
		jxl.Sheet[] sheetArray = xls.getSheets();		
		
		// 1. 读取xls的sheet信息，只遍历每一个sheet页的第一行数据
		// 1.1 判断列数目是否一致
		// 1.2 非空sheet页的第一行文字(Trim)是否一致
		int[] columnNumberArray = new int[sheetArray.length]; // 存储每一sheet列数量的数组
 		String[] columnTitleArray = new String[sheetArray.length]; // 存储title串
 		int defaultcolumnNumber = 0;
		String defaultTitleText = "";
		int defaultSheetNumber = 0;
 		for (int i = 0 ; i < sheetArray.length; i++){
 			// 当sheet页的数据不止一行的时候
			if (sheetArray[i].getRows() > 0){
				columnNumberArray[i] = sheetArray[i].getColumns();
				columnTitleArray[i] = "";
				jxl.Cell[] titleArray  = sheetArray[i].getRow(0);
				for (int j= 0; j < titleArray.length; j ++){
					columnTitleArray[i] += titleArray[j].getContents().trim() + ",";
				}
				System.out.println("第" + i + "个sheet的title：" + columnTitleArray[i]);
				// 存储第一个sheet的列数和title信息
				if (defaultcolumnNumber == 0 && defaultTitleText.equals("")){
					defaultcolumnNumber = columnNumberArray[i];
					defaultTitleText = columnTitleArray[i];
					defaultSheetNumber = i;
				}
			}else{
				columnNumberArray[i] = 0;
				columnTitleArray[i] = "";
			}
		}
		
		for (int i = 0; i < columnNumberArray.length; i++){
			if ( columnNumberArray[i] != 0 && !columnTitleArray[i].equals("")){
				if ( defaultcolumnNumber != columnNumberArray[i] 
				    || !columnTitleArray[i].equals(defaultTitleText) ){
					throw new TxnDataException("999999", 
							"第" + (i + 1) + "个sheet页的表头和第" + (defaultSheetNumber + 1)
							+ "个sheet页的表头不一致！无法通过校验！");
				}
			}
		}
		
		return true;
	}
	
	/**
	 * 
	 * @param filePath
	 * @return
	 * @throws BiffException
	 * @throws IOException
	 * @throws TxnDataException
	 */
	private boolean destroyUploadTxtFile(String filePath) throws BiffException, IOException, TxnDataException{
		File file = new File(filePath);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		String line = reader.readLine();
		int defaultLength = 0;
		String regex = "(\")?\\s*,\\s*(\")?|\\t";
		int i = 0;
		while ( line != null ){
			if(!line.equals("")){
				if (defaultLength == 0){
					defaultLength = line.split(regex).length ;
					System.out.println("defaultLength:" + defaultLength);
				}
				// 比较每一行的列是否一致
				if (defaultLength != line.split(regex).length){
					throw new TxnDataException("999999", 
							"第" + (i + 1) + "行的数据个数与首行的数据个数不一致！无法通过校验！");
				}
			}
			line = reader.readLine();
			i ++;
		}
		
		return true;
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
		DownloadCompareContext appContext = new DownloadCompareContext( context );
		invoke( method, appContext );
	}
}
