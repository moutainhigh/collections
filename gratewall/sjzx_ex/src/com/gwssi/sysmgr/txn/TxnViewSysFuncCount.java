package com.gwssi.sysmgr.txn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

import com.gwssi.common.util.ExcelWrite;
import com.gwssi.sysmgr.vo.ViewSysFuncCountContext;

import javax.servlet.http.HttpServletResponse;

public class TxnViewSysFuncCount extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnViewSysFuncCount.class, ViewSysFuncCountContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "view_sys_func_count";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select view_sys_func_count list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one view_sys_func_count";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one view_sys_func_count";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one view_sys_func_count";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one view_sys_func_count";
	
	private static final String[] FUNCNAMES = {"简单查询","高级查询","统计报表","全文检索"};
	private static final String[] QUXIAN = {"北京市工商局市局机关","东城分局","西城分局","崇文分局","宣武分局","朝阳分局","丰台分局","石景山分局","海淀分局","门头沟分局","房山分局","通州分局","顺义分局","昌平分局","大兴分局","怀柔分局","平谷分局","密云分局","延庆分局","燕山分局"};
	/**
	 * 构造函数
	 */
	public TxnViewSysFuncCount()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询功能使用统计列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60900001( ViewSysFuncCountContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction( "queryDataByDate", context, inputNode, outputNode );
		
		for(int i = 0; i < FUNCNAMES.length; i++){
			boolean found = false;
			Recordset rs = context.getRecordset(outputNode);
			while(rs.hasNext()){
				DataBus db = (DataBus)rs.next();
				String func_name = db.getValue("func_name");
				if(func_name.trim().equals(FUNCNAMES[i])){
					found = true;
					break;
				}
			}
			if(!found){
				DataBus db = new DataBus();
				db.setValue("func_name", FUNCNAMES[i]);
				db.setValue("querytimes", "0");
				db.setValue("primary-key", "func_name="+FUNCNAMES[i]+" sjjgid_fk=");
				context.addRecord(outputNode, db);
			}
		}
	}
	
	/** 子功能使用统计信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60900002( ViewSysFuncCountContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction( "querySub1DataByDate", context, inputNode, outputNode );
	}
	
	/** 子子功能使用统计信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60900003( ViewSysFuncCountContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction( "querySub2DataByDate", context, inputNode, outputNode );
		List busList = new ArrayList();
		for(int i = 0; i < QUXIAN.length; i++){
			boolean found = false;
			Recordset rs = context.getRecordset(outputNode);
			while(rs.hasNext()){
				DataBus db = (DataBus)rs.next();
				String sjjgid_fk = db.getValue("sjjgname");
				if(sjjgid_fk.trim().equals(QUXIAN[i])){
					busList.add(db);
					found = true;
				}
			}
			if(!found){
				DataBus db = new DataBus();
				db.setValue("sjjgname", QUXIAN[i]);
				db.setValue("querytimes", "0");
				db.setValue("primary-key", "func_name= sjjgid_fk="+QUXIAN[i]);
				busList.add(db);
			}
		}
		context.remove(outputNode);
		for(int i = 0; i < busList.size(); i++){
			DataBus db = (DataBus)busList.get(i);
			context.addRecord(outputNode, db);
		}
	}
	
	/** 用于导出
	 * @param context 交易上下文
	 * @throws TxnException
	 * @throws IOException 
	 */
	public void txn620100208( ViewSysFuncCountContext context ) throws TxnException
	{
		
//		com.gwssi.common.util.ExcelHelper.writeExcel(out, db.toString());
//		response.setContentType("aplication/vnd.ms-excel");
//	    response.addHeader("Content-Disposition","inline; filename=" + new String(title.getBytes("GBK"),"ISO8859_1") + ".xls"); 
//		while(rs.hasNext()){
//			DataBus db = (DataBus)rs.next();
//			
//		}
	    BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction( "queryLogStatistics", context, inputNode, outputNode );
		
//		String[] Titles = {"功能大类","功能小类","所属分局","使用次数"} ;
//		String[][] Contents;
//		Recordset rs = context.getRecordset(outputNode);
//        File tempFile=new File("d:/temp/output23.xls");
//		OutputStream os=null;
//		try {
//			os = new FileOutputStream(tempFile);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Contents=new String[rs.size()][5];
//		
//		for(int i=0;i<rs.size();i++)
//		{
//			DataBus db = (DataBus)rs.get(i);
//			Contents[i][0]=db.getValue("first_func_name");
//			Contents[i][1]=db.getValue("func_name");
//			Contents[i][2]=db.getValue("sjjgname");
//			Contents[i][3]=db.getValue("querytimes");
//		}
//		ExcelWrite ew = new ExcelWrite();
//		try {
//			ew.expordExcel(os, "第一页", Titles, Contents);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	/** 用户使用日志统计
	 * @param context 交易上下文
	 * @throws TxnException
	 * @throws IOException 
	 */
	public void txn620100201( ViewSysFuncCountContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		//Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction( "queryLogStatistics", context, inputNode, outputNode );
		


		/*List busList = new ArrayList();
		for(int i = 0; i < QUXIAN.length; i++){
			boolean found = false;
			Recordset rs = context.getRecordset(outputNode);
			while(rs.hasNext()){
				DataBus db = (DataBus)rs.next();
				String sjjgid_fk = db.getValue("sjjgname");
				if(sjjgid_fk.trim().equals(QUXIAN[i])){
					busList.add(db);
					found = true;
				}
			}
			if(!found){
				DataBus db = new DataBus();
				db.setValue("sjjgname", QUXIAN[i]);
				db.setValue("querytimes", "0");
				db.setValue("primary-key", "func_name= sjjgid_fk="+QUXIAN[i]);
				busList.add(db);
			}
		}
		context.remove(outputNode);
		for(int i = 0; i < busList.size(); i++){
			DataBus db = (DataBus)busList.get(i);
			context.addRecord(outputNode, db);
		}*/
	}
	
	/** 查询功能使用统计用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60900004( ViewSysFuncCountContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoViewSysFuncCountPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoViewSysFuncCount result = context.getViewSysFuncCount( outputNode );
	}
	
	/** 删除功能使用统计信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60900005( ViewSysFuncCountContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoViewSysFuncCountPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
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
		ViewSysFuncCountContext appContext = new ViewSysFuncCountContext( context );
		invoke( method, appContext );
	}
}
