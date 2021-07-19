package com.gwssi.file.demo.txn;

import java.lang.reflect.Method;
import java.util.HashMap;



import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.common.constant.ConstUploadFileType;
import com.gwssi.common.constant.FileConstant;
import com.gwssi.common.upload.UploadFileVO;
import com.gwssi.common.upload.UploadHelper;
import com.gwssi.file.demo.vo.VoZwTzglJbxx;
import com.gwssi.file.demo.vo.ZwTzglJbxxContext;

public class TxnZwTzglJbxx extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnZwTzglJbxx.class, ZwTzglJbxxContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "zw_tzgl_jbxx";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select zw_tzgl_jbxx list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one zw_tzgl_jbxx";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one zw_tzgl_jbxx";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one zw_tzgl_jbxx";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one zw_tzgl_jbxx";
	
	/**
	 * 构造函数
	 */
	public TxnZwTzglJbxx()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询通知管理列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn315001001( ZwTzglJbxxContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoZwTzglJbxxSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoZwTzglJbxx result[] = context.getZwTzglJbxxs( outputNode );
	}
	
	/** 修改通知管理信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn315001002( ZwTzglJbxxContext context ) throws TxnException
	{
		System.out.println(context);
		//获取页面上的会议材料和被删除的会议材料及各自的ID
        String delIDs = context.getRecord(inputNode).getValue(
                VoZwTzglJbxx.ITEM_DELIDS);
        String delNAMEs = context.getRecord(inputNode).getValue(
        		VoZwTzglJbxx.ITEM_DELNAMES);
        String hyclid = context.getRecord(inputNode).getValue(
        		VoZwTzglJbxx.ITEM_FJ_FK);
        String hycl = context.getRecord(inputNode).getValue(VoZwTzglJbxx.ITEM_FJMC);
        
        //生成一个UploadFileVO对象，保存政务管理类型的多附件
        UploadFileVO fileVO = new UploadFileVO();
        fileVO.setRecordName("record:fjmc");
        fileVO.setDeleteId(delIDs);//页面保存的被删除附件Id值
        fileVO.setDeleteName(delNAMEs);// 页面保存的被删除附件name值
        fileVO.setOriginId(hyclid);// 更新附件前业务数据库表附件id字段存储的值
        fileVO.setOriginName(hycl);// 更新附件前业务数据库表附件name字段存储的值
        fileVO.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);//多附件
        UploadFileVO vo = UploadHelper.updateFile(context, fileVO,
                ConstUploadFileType.SHARECORD);//附件类型为政务管理

        //将附件信息传递到inputNode
        context.getRecord(inputNode).setValue(VoZwTzglJbxx.ITEM_FJ_FK,
                vo.getReturnId());
        context.getRecord(inputNode).setValue(VoZwTzglJbxx.ITEM_FJMC,
                vo.getReturnName());
        
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoZwTzglJbxx zw_tzgl_jbxx = context.getZwTzglJbxx( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加通知管理信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn315001003( ZwTzglJbxxContext context ) throws TxnException
	{
		
		UploadFileVO fileVO1 = new UploadFileVO();
        //fileVO1.setRecordName("record:fjmc");
        fileVO1.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);

        UploadFileVO vo = UploadHelper.saveFile(context, fileVO1,
                ConstUploadFileType.SHARECORD);
        
        //将附件信息传递到inputNode
        context.getRecord(inputNode).setValue("fj_fk",
                vo.getReturnId());
        context.getRecord(inputNode).setValue("fjmc",
                vo.getReturnName());
        BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoZwTzglJbxx zw_tzgl_jbxx = context.getZwTzglJbxx( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询通知管理用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn315001004( ZwTzglJbxxContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoZwTzglJbxxPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoZwTzglJbxx result = context.getZwTzglJbxx( outputNode );
		
		//从输出节点中获取会议材料的ID
        String fjids = context.getRecord(outputNode).getValue(
                VoZwTzglJbxx.ITEM_FJ_FK);

        //从输出节点中获取会议材料的名称
        String filenames = context.getRecord(outputNode).getValue(
        		VoZwTzglJbxx.ITEM_FJMC);

        //调用接口将获取的文件信息一一传回context
        UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
	}
	
	/** 删除通知管理信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn315001005( ZwTzglJbxxContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		BaseTable tabletmp = TableFactory.getInstance().getTableObject(this, TABLE_NAME);
		// 删除记录的主键列表 VoZwTzglJbxxPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		Recordset rstz = context.getRecordset("primary-key");
        for (int i = 0; i < rstz.size(); i++) {
            DataBus dbtmp = rstz.get(i);

            //删除附件
            tabletmp.executeFunction(SELECT_FUNCTION, context, dbtmp, "outputNodetmp");
            String fjids = context.getRecord("outputNodetmp").getValue(
            		VoZwTzglJbxx.ITEM_FJ_FK);
            UploadHelper.deleteFile(context, fjids, ConstUploadFileType.SHARECORD);

        }
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询通知管理用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn315001006( ZwTzglJbxxContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoZwTzglJbxxPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoZwTzglJbxx result = context.getZwTzglJbxx( outputNode );
		
		//从输出节点中获取会议材料的ID
        String fjids = context.getRecord(outputNode).getValue(
                VoZwTzglJbxx.ITEM_FJ_FK);

        //从输出节点中获取会议材料的名称
        String filenames = context.getRecord(outputNode).getValue(
        		VoZwTzglJbxx.ITEM_FJMC);

        //调用接口将获取的文件信息一一传回context
        UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
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
		ZwTzglJbxxContext appContext = new ZwTzglJbxxContext( context );
		invoke( method, appContext );
	}
}
