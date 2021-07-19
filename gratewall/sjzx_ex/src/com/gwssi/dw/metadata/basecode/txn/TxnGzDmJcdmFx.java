package com.gwssi.dw.metadata.basecode.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.Attribute;
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

import com.gwssi.dw.metadata.basecode.vo.GzDmJcdmContext;
import com.gwssi.dw.metadata.basecode.vo.GzDmJcdmFxContext;

public class TxnGzDmJcdmFx extends TxnMasterTable
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnGzDmJcdmFx.class, GzDmJcdmFxContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "gz_dm_jcdm_fx";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select gz_dm_jcdm_fx list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one gz_dm_jcdm_fx";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one gz_dm_jcdm_fx";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one gz_dm_jcdm_fx";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one gz_dm_jcdm_fx";

	// 明细表
	private static final String detailTables[][] = {
		
		// 仅仅是为了处理最后一个 [,]；可以删除
		{ null, null }
	};
	
	/**
	 * 构造函数
	 */
	public TxnGzDmJcdmFx()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询基础代码分项列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn3010101( GzDmJcdmFxContext context ) throws TxnException
	{
		Attribute.setPageRow(context, outputNode, 20);
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoGzDmJcdmFxSelectKey selectKey = context.getSelectKey( inputNode );
		//loadMasterTable( table, ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoGzDmJcdmFx result[] = context.getGzDmJcdmFxs( outputNode );
		//模糊查询
		//table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		//右模糊查询
		table.executeFunction( "rightQuery", context, inputNode, outputNode );
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

	/** 修改基础代码分项信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn3010102( GzDmJcdmFxContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );	
		DataBus dataBus = context.getRecord("record");
		String dm = dataBus.getValue("jcsjfx_dm");
		String jc_dm_id = dataBus.getValue("jc_dm_id");
		String jcsjfx_id = dataBus.getValue("jcsjfx_id");
		String jcsjfx_mc = dataBus.getValue("jcsjfx_mc");
		
		GzDmJcdmFxContext oldContext  = new GzDmJcdmFxContext();
		DataBus oldDb = oldContext.getRecord("primary-key");
		oldDb.setValue("jcsjfx_id", jcsjfx_id);		
		table.executeFunction( SELECT_FUNCTION, oldContext, "primary-key", "record" );
		String oldDm = oldContext.getRecord("record").getValue("jcsjfx_dm");
		if(!dm.equals(oldDm)){
			GzDmJcdmFxContext countContext = new GzDmJcdmFxContext();		
			DataBus countDb = countContext.getRecord("record");
			countDb.setValue("jcsjfx_dm", dm);
			countDb.setValue("jc_dm_id", jc_dm_id);
			table.executeFunction( "verifyJcsjfxdm", countContext, "record", "record" );
			DataBus queryCountDb = countContext.getRecord("record");
			String count = queryCountDb.getValue("count");
							
			if(count!=null&&!count.equals("0")){
				throw new TxnDataException("","基础数据分项代码："+dm+"已存在于该分类下，请输入其他代码！");
			}
		}
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
		setBizLog("修改基础代码分项：", context,jcsjfx_mc);
	}

	/** 增加基础代码分项信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn3010103( GzDmJcdmFxContext context ) throws TxnException
	{
		
	    BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
	    
		table.executeFunction( "verifyJcsjfxdm", context, "record", "record" );
		DataBus dataBus = context.getRecord("record");
		String dm = dataBus.getValue("jcsjfx_dm");
		String jcsjfx_mc = dataBus.getValue("jcsjfx_mc");
		String count = dataBus.getValue("count");
		if(count!=null&&!count.equals("0")){
			throw new TxnDataException("","基础数据分项代码："+dm+"已存在于该分类下，不能增加！");
		}	  
		
	    DataBus old_dataBus = context.getRecord("record");
		String old_fzx_id = old_dataBus.getValue("jcsjfx_fjd");
		table.executeFunction( "selectSx", context, "record", "record" );
		DataBus sxData=context.getRecord("record");
		String xssx=sxData.getValue("xssx");
		if(xssx==null||xssx.equals("")){
	        sxData.setValue("xssx", "1");
		}
		else{
			sxData.setValue("xssx", xssx);
		}
		if(old_fzx_id.equals("")){
			table.executeFunction(INSERT_FUNCTION, context, "record", "record" );				
		}else{				
			GzDmJcdmFxContext old_context = new GzDmJcdmFxContext();
			DataBus old_db = old_context.getRecord("primary-key");
			old_db.setValue("jcsjfx_id", old_fzx_id);
			table.executeFunction(SELECT_FUNCTION, old_context, "primary-key", "record");
			DataBus old_db_record = old_context.getRecord("record");
			old_db_record.setValue("sfmx", "0");
			table.executeFunction(UPDATE_FUNCTION, old_context,inputNode, outputNode );
			table.executeFunction(INSERT_FUNCTION, context, "record", "record"); 
		}
		setBizLog("增加基础代码分项：", context,jcsjfx_mc);
	}

	/** 查询基础代码分项用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn3010104( GzDmJcdmFxContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoGzDmJcdmFxPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoGzDmJcdmFx result = context.getGzDmJcdmFx( outputNode );
	}

	/** 删除基础代码分项信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn3010105( GzDmJcdmFxContext context ) throws TxnException{
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );			
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );	
		DataBus data = context.getRecord(outputNode);
		
		/*String jcsjfx_id = data.getValue("jcsjfx_id");
		String jcsjfx_mc = "";
		//检验基础数据是否被指标引用
		GzDmJcdmFxContext queryContext = new GzDmJcdmFxContext();			
		DataBus queryDb = queryContext.getRecord("record");
		queryDb.setValue("jcsjfx_id", jcsjfx_id);			
		table.executeFunction( "selectZb", queryContext, inputNode, outputNode );
		String zbCount = queryContext.getRecord(outputNode).getValue("count");
		if(zbCount!=null&&!zbCount.equals("0")){
			jcsjfx_mc = data.getValue("jcsjfx_mc");
			throw new TxnDataException("999999","基础代码:"+jcsjfx_mc+"已被指标引用,不能删除!");
		}*/
		
		//检验基础数据是否被分组引用
		/*GzDmJcdmFxContext queryFzContext = new GzDmJcdmFxContext();			
		DataBus queryFzDb = queryFzContext.getRecord("record");
		queryFzDb.setValue("jcsjfx_id", jcsjfx_id);			
		table.executeFunction( "selectFz", queryContext, inputNode, outputNode );
		String fzCount = queryContext.getRecord(outputNode).getValue("count");
		if(fzCount!=null&&!fzCount.equals("0")){
			jcsjfx_mc = data.getValue("jcsjfx_mc");
			throw new TxnDataException("999999","基础代码:"+jcsjfx_mc+"已被分组引用,不能删除!");
		}*/		
		
//		String fjd = data.getValue("jcsjfx_fjd");			
//		if(fjd!=null&&!fjd.equals("")){
//			table.executeFunction( "selectFjd", context, "record", "record" );
//			DataBus countData=context.getRecord("record");
//			String count=countData.getValue("count");
//			//如果是最后一个子节点，修改节点是否明细值
//			if(count.equals("1")){
//				
//				GzDmJcdmFxContext up_context = new GzDmJcdmFxContext();
//				DataBus dataBus = up_context.getRecord("primary-key");
//				dataBus.setValue("jcsjfx_id", fjd);
//									
//				table.executeFunction(SELECT_FUNCTION, up_context, "primary-key", "record");				
//				DataBus old_db_record = up_context.getRecord("record");
//				old_db_record.setValue("sfmx", "1");
//				table.executeFunction(UPDATE_FUNCTION, up_context,"record", outputNode );
//			}
//		} 
		Recordset rs = context.getRecordset("select-key");
		DataBus dataBus = null;
		String log_desc = "";
		String fx_mc = "";
		for(int i=0; i<rs.size();i++){
			dataBus = rs.get(i);
			fx_mc = dataBus.getValue("jcsjfx_mc");	
			log_desc+="," + fx_mc;
		}
		if(!log_desc.equals("")) log_desc = log_desc.substring(1);		
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );	
		setBizLog("删除基础代码分项：", context,log_desc);
	}
	
	public void txn3010106( GzDmJcdmFxContext context ) throws TxnException
	{
		 
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow( context, outputNode, -1 );
		table.executeFunction( "getFirstNode", context, inputNode, outputNode );
		// System.out.println(context);
		Recordset rs = context.getRecordset("record");
		for (int i=0; i < rs.size(); i++)
		{
			DataBus db = rs.get(i);
			db.setValue("jcsjfx_mc", db.getValue("jcsjfx_mc")+"("+db.getValue("jcsjfx_dm")+")");
		}
		try {
			setExpand(context);
		} catch (Exception e) {
			e.printStackTrace();
		}		

	}
	
	/** 读取第二层及以后节点
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn3010107( GzDmJcdmFxContext context ) throws TxnException
	{
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );	
		Attribute.setPageRow( context, outputNode, -1 );
		table.executeFunction( "getSecondNode", context, inputNode, outputNode );
		Recordset rs = context.getRecordset("record");
		for (int i=0; i < rs.size(); i++)
		{
			DataBus db = rs.get(i);
			db.setValue("jcsjfx_mc", db.getValue("jcsjfx_mc")+"("+db.getValue("jcsjfx_dm")+")");
		}
		setExpand(context);
	}
	
	public void txn3010108( GzDmJcdmFxContext context ) throws TxnException
	{
//		System.out.println("************===="+context);
//		 DataBus ztDataBus=context.getRecord("select-key");
//		 String sy_fl_zt=ztDataBus.getValue("sy_fl_zt");
//		 if(sy_fl_zt.equals("0"))
//			 
//		 {
//			 throw new TxnDataException("","已停用的节点不能进行此操作！");
//			 
//		 }else {
//			 
//			
//		 }
	}
	
	private void setExpand( GzDmJcdmFxContext context ){
		Recordset rs = null;
		try {
			rs = context.getRecordset("record");
		} catch (TxnException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < rs.size(); i ++)
		{
			DataBus dataBus = rs.get(i);
			String sf_mx = dataBus.getValue("sfmx");
			
			if (sf_mx.equals("0")){
				
				//dataBus.setValue("expand", "false");
			}
			else
			{
				dataBus.setValue("expand", "true");
			}
		}
	}	

	/**
	 * 交互两个节点的显示顺序
	 * 1、得到交换位置的基础数据内码和显示序号。
	 * 2、交换所有内码相同的基础数据记录显示序号。
	 * @param context
	 * @throws TxnException
	 */
	public void txn3010110( GzDmJcdmFxContext context ) throws TxnException
	{		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		//传内码
		DataBus dataBus = context.getRecord("select-key");
		String first_jcsjfx_nm = dataBus.getValue("first_jcsjfx_nm");
		String first_xssx = dataBus.getValue("first_xssx");
		String last_jcsjfx_nm = dataBus.getValue("last_jcsjfx_nm");
		String last_xssx = dataBus.getValue("last_xssx");
        
		DataBus db = new DataBus();
		db.setValue("jcsjfx_id", first_jcsjfx_nm);
		context.addRecord("primary-key", db);
		table.executeFunction(SELECT_FUNCTION, context, "primary-key", "record");
		db = context.getRecord("record");
		db.setValue("xssx",last_xssx);

		table.executeFunction(UPDATE_FUNCTION, context, "record", "record");

		db = context.getRecord("primary-key");
		db.setValue("jcsjfx_id", last_jcsjfx_nm);
		table.executeFunction(SELECT_FUNCTION, context, "primary-key", "record");
		db = context.getRecord("record");
		db.setValue("xssx",first_xssx);
		table.executeFunction(UPDATE_FUNCTION, context, "record", "record");	
	}
	
	
	/** 模糊查询基础数据信息，用于指标树的查询基础数据节点
	 *  对基础数据树节点显示名称进行模糊匹配查询
	 *  1、根据查询结果反算节点路径
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn3010109( GzDmJcdmFxContext context ) throws TxnException
	{	
		//System.out.println(context);
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );		
		Attribute.setPageRow( context, outputNode, -1 );
		table.executeFunction( "getSerchNode", context, inputNode, outputNode );
		Recordset rs = context.getRecordset("record");

		for(int i =0;i<rs.size();i++)
		{
			DataBus db = rs.get(i);
			String jcsjfx_fjd = db.getValue("jcsjfx_fjd");
			String jcsjfx_lj ="/"+ db.getValue("jcsjfx_id");
			while (jcsjfx_fjd!=null&&!jcsjfx_fjd.equals("")) {
				GzDmJcdmFxContext new_Context = new GzDmJcdmFxContext();
				DataBus newDB = new DataBus();
				newDB.setValue("jcsjfx_id", jcsjfx_fjd);
				new_Context.addRecord("primary-key", newDB);
				table.executeFunction(SELECT_FUNCTION, new_Context,"primary-key", "record");
				Recordset TempRs = new_Context.getRecordset("record");
				jcsjfx_fjd = "";
				String temp_jcsjfx_id = "";
				for(int j =0;j<TempRs.size();j++)
				{
					DataBus tempDb = TempRs.get(j);
					jcsjfx_fjd = tempDb.getValue("jcsjfx_fjd");
					temp_jcsjfx_id = tempDb.getValue("jcsjfx_id");
				}
				if(temp_jcsjfx_id!=null&&!temp_jcsjfx_id.equals("")){
					jcsjfx_lj = "/"+temp_jcsjfx_id + jcsjfx_lj;
				}
			}
			db.setValue("jcsjfx_lj", jcsjfx_lj);
		}
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
		GzDmJcdmFxContext appContext = new GzDmJcdmFxContext( context );
		invoke( method, appContext );
	}
}
