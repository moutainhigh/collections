package cn.gwssi.template.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;
import cn.gwssi.template.dao.MsWordDao;
import cn.gwssi.template.vo.SysMswordContext;

import com.gwssi.common.util.UuidGenerator;

public class TxnMsWord extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnMsWord.class, SysMswordContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "sys_msword";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select sys_msword list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one sys_msword";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one sys_msword";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one sys_msword";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one sys_msword";
	
	/**
	 * 构造函数
	 */
	public TxnMsWord()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询word模板列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30200101( SysMswordContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoSysMswordSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoSysMsword result[] = context.getSysMswords( outputNode );
	}
	
	/** 修改word模板信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30200102( SysMswordContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoSysMsword sys_msword = context.getSysMsword( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加word模板信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30200103( SysMswordContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String sys_msword_id =  UuidGenerator.getUUID();
		DataBus db = context.getRecord(inputNode);
		String sys_msword_name = db.getValue("sys_msword_name");
		String sys_msword_template = db.getValue("sys_msword_template");
		String sys_msword_desp = db.getValue("sys_msword_desp");
		String sys_msword_bookmarks = db.getValue("sys_msword_bookmarks");
		MsWordDao dao = new MsWordDao();
		
		try {
			dao.addMsWord(sys_msword_id, sys_msword_name, sys_msword_template, sys_msword_bookmarks, sys_msword_desp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println("新增word模板文件  \n"+context);		
		// 增加记录的内容 VoSysMsword sys_msword = context.getSysMsword( inputNode );
		//table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询word模板用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30200104( SysMswordContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysMswordPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoSysMsword result = context.getSysMsword( outputNode );
	}
	
	/** 删除word模板信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30200105( SysMswordContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoSysMswordPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
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
		SysMswordContext appContext = new SysMswordContext( context );
		invoke( method, appContext );
	}
}
