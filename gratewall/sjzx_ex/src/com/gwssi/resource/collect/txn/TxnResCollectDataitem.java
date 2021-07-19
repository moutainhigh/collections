package com.gwssi.resource.collect.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.util.CalendarUtil;
import com.gwssi.common.util.UuidGenerator;
import com.gwssi.resource.collect.vo.ResCollectDataitemContext;

public class TxnResCollectDataitem extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnResCollectDataitem.class, ResCollectDataitemContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "res_collect_dataitem";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select res_collect_dataitem list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one res_collect_dataitem";
	
	
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one res_collect_dataitem";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one res_collect_dataitem";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one res_collect_dataitem";
	
	/**
	 * 构造函数
	 */
	public TxnResCollectDataitem()
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
	 * 
	 * txn20202000(检查数据项名称是否正被使用)    

	 * @param context
	 * @throws TxnException        
	 * void       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public void txn20202000(ResCollectDataitemContext context) throws TxnException
	{
		
		String dataitem_name_en = context.getRecord("select-key").getValue("dataitem_name_en");//数据项名称
		String collect_table_id = context.getRecord("select-key").getValue("collect_table_id");//数据表ID
		String collect_dataitem_id = context.getRecord("select-key").getValue("collect_dataitem_id");//数据项ID
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME);
		System.out.println(dataitem_name_en + " == " + collect_table_id);
		StringBuffer sql= new StringBuffer();
		sql.append("select count(*) as name_nums from res_collect_dataitem t where t.is_markup='"+ExConstant.IS_MARKUP_Y+"'");
		if(StringUtils.isNotBlank(dataitem_name_en)){
			sql.append(" and t.dataitem_name_en = '"+dataitem_name_en.toUpperCase()+"'");
		}
		
		if(StringUtils.isNotBlank(collect_table_id)){
			sql.append(" and t.collect_table_id = '"+collect_table_id+"'");
		}
		if(StringUtils.isNotBlank(collect_dataitem_id)){
			sql.append(" and t.collect_dataitem_id != '"+collect_dataitem_id+"'");
		}
		System.out.println("查询数据项名称是否正被使用sql=========="+sql.toString());
		table.executeRowset( sql.toString(), context, outputNode);
	
	}
	
	
	/** 
	 * 查询采集数据项表列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn20202001( ResCollectDataitemContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 
	 * 修改采集数据项表信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn20202002( ResCollectDataitemContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		 String dataitem_name_en=context.getRecord("record").getValue("dataitem_name_en");
		 if(dataitem_name_en!=null&&!"".equals(dataitem_name_en)){
			 dataitem_name_en=dataitem_name_en.toUpperCase();
			 context.getRecord("record").setValue("dataitem_name_en",dataitem_name_en);// 表字段英文名称
		 }
		String is_code=context.getRecord("record").getValue("code_table");
		context.getRecord("record").setValue("last_modify_id",context.getRecord("oper-data").getValue("userID"));//最后修改人ID
		context.getRecord("record").setValue("last_modify_time", CalendarUtil.getCurrentDateTime());//最后修改时间
		if(is_code!=null&&is_code.equals("00")){
			context.getRecord("record").setValue("is_code_table","0");//不是代码表
		}else{
			context.getRecord("record").setValue("is_code_table","1");//是代码表
		}
		String is_key=context.getRecord("record").getValue("is_key");
		if(is_key==null||is_key.equals("")){
			context.getRecord("record").setValue("is_key","0");//不是主键
		}
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 
	 * 增加采集数据项表信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn20202003( ResCollectDataitemContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String dataitem_name_en=context.getRecord("record").getValue("dataitem_name_en");
		if(StringUtils.isNotBlank(dataitem_name_en)){
			 dataitem_name_en=dataitem_name_en.toUpperCase();
			 context.getRecord("record").setValue("dataitem_name_en",dataitem_name_en);// 表字段英文名称
		}
		String id = UuidGenerator.getUUID();
		context.getRecord("record").setValue("collect_dataitem_id", id);//数据项ID
		//context.getRecord("record").setValue("collect_table_id", context.getRecord("record").getValue("collect_table_id"));//数据表ID
		context.getRecord("record").setValue("created_time", CalendarUtil.getCurrentDateTime());//创建时间
		context.getRecord("record").setValue("creator_id",context.getRecord("oper-data").getValue("userID"));//创建人ID
		context.getRecord("record").setValue("is_markup",ExConstant.IS_MARKUP_Y);// 引入常量 有效标记
		context.getRecord("record").setValue("last_modify_id",context.getRecord("oper-data").getValue("userID"));//创建人ID
		context.getRecord("record").setValue("last_modify_time", CalendarUtil.getCurrentDateTime());//创建人ID
		context.getRecord("record").setValue("dataitem_state", "0");//物理表是否生成该字段
		
		String is_code=context.getRecord("record").getValue("code_table");
		
		if(StringUtils.isNotBlank(is_code) && !is_code.equals("00")){
			context.getRecord("record").setValue("is_code_table",CollectConstants.IS_CODE_TABLE_Y);//是代码表
		}else{
			context.getRecord("record").setValue("is_code_table",CollectConstants.IS_CODE_TABLE_N);//不是代码表
		}
		String is_key=context.getRecord("record").getValue("is_key");
		if(is_key==null||is_key.equals("")){
			context.getRecord("record").setValue("is_key","0");//不是主键
		}
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 
	 * 查询采集数据项表用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn20202004( ResCollectDataitemContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		
		context.getRecord("record").setValue("collect_table_id", context.getRecord("primary-key").getValue("collect_table_id"));//采集表ID
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		table.executeFunction("queryTableId", context, inputNode, "tableinfo");
	}
	
	/** 
	 * 删除采集数据项表信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn20202005( ResCollectDataitemContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		
		//table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
		table.executeFunction( "deleteTableItem", context, inputNode, outputNode );
	}
	/** 
	 * 查询采集数据项表用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn20202006( ResCollectDataitemContext context ) throws TxnException
	{
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		
		context.getRecord("record").setValue("collect_dataitem_id", context.getRecord("primary-key").getValue("collect_dataitem_id"));//数据项ID
		context.getRecord("record").setValue("collect_table_id", context.getRecord("primary-key").getValue("collect_table_id"));//数据表ID
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		
		table.executeFunction("queryTableId", context, inputNode, "tableinfo");//数据表信息
	}
	/** 
	 * 查询采集数据项表用于新增
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn20202007( ResCollectDataitemContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME);
		
		table.executeFunction("queryTableId", context, inputNode, "tableinfo");//数据表信息
		table.executeFunction("queryTableId", context, inputNode, outputNode);//数据项列表
	}
	
	/** 
	 * 查询采集数据项表用于新增
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn20202008( ResCollectDataitemContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME);
		String id = UuidGenerator.getUUID();
		context.getRecord("record").setValue("collect_dataitem_id", id);//数据项ID
		
		context.getRecord("record").setValue("collect_table_id", context.getRecord("record").getValue("collect_table_id"));//数据表ID
		context.getRecord("record").setValue("dataitem_name_en", context.getRecord("record").getValue("dataitem_name_en").toUpperCase());//数据项名称
		context.getRecord("record").setValue("dataitem_name_cn", context.getRecord("record").getValue("dataitem_name_cn"));//数据项中文名称
		context.getRecord("record").setValue("dataitem_type", context.getRecord("record").getValue("dataitem_type"));//数据项类型
		context.getRecord("record").setValue("dataitem_long", context.getRecord("record").getValue("dataitem_long"));//数据项长度
		context.getRecord("record").setValue("is_key", context.getRecord("record").getValue("is_key"));//是否主键
		context.getRecord("record").setValue("dataitem_long_desc", context.getRecord("record").getValue("dataitem_long_desc"));//数据项描述
		context.getRecord("record").setValue("created_time",CalendarUtil.getCurrentDateTime());//创建时间
		context.getRecord("record").setValue("creator_id",context.getRecord("oper-data").getValue("userID"));//创建人ID
		context.getRecord("record").setValue("is_markup",ExConstant.IS_MARKUP_Y);// 引入常量//有效标记
		
		//context.getRecord("record").setValue("last_modify_id",context.getRecord("oper-data").getValue("userID"));
		//context.getRecord("record").setValue("last_modify_time", CalendarUtil.getCurrentDateTime());
		
		String is_code=context.getRecord("record").getValue("code_table");
		if(is_code!=null&&is_code.equals("01")){
			context.getRecord("record").setValue("is_code_table","1");//是代码表
		}else{
			context.getRecord("record").setValue("is_code_table","0");//不是代码表
		}
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode);
	
	}
	/** 
	 * 修改采集数据项表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn20202009( ResCollectDataitemContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME);
	
		context.getRecord("record").setValue("collect_dataitem_id", context.getRecord("record").getValue("collect_dataitem_id"));//数据项ID
		context.getRecord("record").setValue("collect_table_id", context.getRecord("record").getValue("collect_table_id"));//数据表ID
		context.getRecord("record").setValue("dataitem_name_en", context.getRecord("record").getValue("dataitem_name_en"));//数据项名称
		context.getRecord("record").setValue("dataitem_name_cn", context.getRecord("record").getValue("dataitem_name_cn"));//数据项中文名称
		context.getRecord("record").setValue("dataitem_type", context.getRecord("record").getValue("dataitem_type"));//数据项类型
		context.getRecord("record").setValue("dataitem_long", context.getRecord("record").getValue("dataitem_long"));//数据项长度
		context.getRecord("record").setValue("is_key", context.getRecord("record").getValue("is_key"));//是否主键
		context.getRecord("record").setValue("dataitem_long_desc", context.getRecord("record").getValue("dataitem_long_desc"));//数据项描述
		context.getRecord("record").setValue("is_markup",ExConstant.IS_MARKUP_Y);// 引入常量//有效标记
	
		context.getRecord("record").setValue("last_modify_id",context.getRecord("oper-data").getValue("userID"));//最后修改人ID
		context.getRecord("record").setValue("last_modify_time", CalendarUtil.getCurrentDateTime());//最后修改时间
		
		String is_code=context.getRecord("record").getValue("code_table");
		if(is_code!=null&&is_code.equals("01")){
			context.getRecord("record").setValue("is_code_table","1");//是代码表
		}else{
			context.getRecord("record").setValue("is_code_table","0");//不是代码表
		}
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	
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
		ResCollectDataitemContext appContext = new ResCollectDataitemContext( context );
		invoke( method, appContext );
	}
}
