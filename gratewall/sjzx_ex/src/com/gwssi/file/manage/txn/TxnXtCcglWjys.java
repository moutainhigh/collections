package com.gwssi.file.manage.txn;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.Config;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.constant.FileConstant;
import com.gwssi.file.manage.vo.VoXtCcglWjlb;
import com.gwssi.file.manage.vo.VoXtCcglWjys;
import com.gwssi.file.manage.vo.XtCcglWjysContext;

public class TxnXtCcglWjys extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnXtCcglWjys.class, XtCcglWjysContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "xt_ccgl_wjys";
	//文件类别表
	private static final String WJLB_TABLE = "xt_ccgl_wjlb";
	
	// 文件映射表名称
    private static final String WJYS_TABLE = "xt_ccgl_wjys";
    
    // 主键单记录查询
    private static final String SELECT_WJYS_PK = "select one xt_ccgl_wjys by pk";

    // 查询记录
    private static final String SELECT_WJLB_FUNCTION = "select one xt_ccgl_wjlb";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select xt_ccgl_wjys list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one xt_ccgl_wjys";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one xt_ccgl_wjys";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one xt_ccgl_wjys";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one xt_ccgl_wjys";
	
	/**
	 * 构造函数
	 */
	public TxnXtCcglWjys()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询文件映射列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn604060101( XtCcglWjysContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoXtCcglWjysSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoXtCcglWjys result[] = context.getXtCcglWjyss( outputNode );
	}
	
	/** 修改文件映射信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn604060102( XtCcglWjysContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoXtCcglWjys xt_ccgl_wjys = context.getXtCcglWjys( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加文件映射信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn604060103( XtCcglWjysContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoXtCcglWjys xt_ccgl_wjys = context.getXtCcglWjys( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询文件映射用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn604060104( XtCcglWjysContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoXtCcglWjysPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoXtCcglWjys result = context.getXtCcglWjys( outputNode );
	}
	
	/** 删除文件映射信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn604060105( XtCcglWjysContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoXtCcglWjysPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	
	/**
     * 下载文件 604050200
     * 
     * @param context
     *            交易上下文
     * @throws cn.gwssi.common.component.exception.TxnException
     * @throws OperationLogException
     */
	public void txn604050209( XtCcglWjysContext context ) throws TxnException
	{	
		InputStream in = null; // 输入流
        OutputStream out = null; // 输出流

        BaseTable table = TableFactory.getInstance().getTableObject(this,
                WJYS_TABLE);
        // 获取文件存储的存储路径信息
        try {
            table.executeFunction(SELECT_WJYS_PK, context, inputNode,
                    outputNode);
        } catch (TxnDataException ex) {
            log.error(ex);
            /*if (ex.getErrCode().compareTo(ErrorConstant.ECODE_SQL_NOTFOUND) == 0) {
                throw new TxnErrorException(ErrorConstant.SQL_SELECT_NOROW,
                        BJAISConfig
                                .get("ggkz.ccgl.wjys.select.ysnullerror"));
            } else {
                throw ex;
            }*/
        }

        // 根据查询结果设置附件信息
        DataBus outDb = context.getRecord(outputNode);
        String cclbbh_pk = outDb.getValue(VoXtCcglWjys.ITEM_CCLBBH_PK);//存储类别编号
        String wjmc = outDb.getValue(VoXtCcglWjys.ITEM_WJMC);
        String wybs = outDb.getValue(VoXtCcglWjys.ITEM_WYBS);
        String cclj = outDb.getValue(VoXtCcglWjys.ITEM_CCLJ);
        log.debug("文件下载 文件名称：  " + wjmc);

        // 设置文件类别对象主键值
        DataBus db = new DataBus();
        db.put(VoXtCcglWjlb.ITEM_CCLBBH_PK, cclbbh_pk);

       /* BaseTable table1 = TableFactory.getInstance().getTableObject(this,
                WJLB_TABLE);

        // 根据文件类别主键值查询文件类别，获取类别根目录
        try {
            table1.executeFunction(SELECT_WJLB_FUNCTION, context, db, "cclb");
        } catch (TxnDataException ex) {
            log.error(ex);
            if (ex.getErrCode().compareTo(ErrorConstant.ECODE_SQL_NOTFOUND) == 0) {
                throw new TxnErrorException(ErrorConstant.SQL_SELECT_NOROW,
                        BJAISConfig
                                .get("ggkz.ccgl.wjys.select.ysnullerror"));
            } else {
                throw ex;
            }
        }*/

        // 根据文件类别根目录，组装文件存储全路径
        /*String root = context.getRecord("cclb").getValue(
                VoXtCcglWjlb.ITEM_CCGML);*/
        String root = getPathByCCLB(cclbbh_pk);
        String fullPath = root + FileConstant.PATH_SEPERATOR + cclj
                + FileConstant.PATH_SEPERATOR + wybs;

        // 获取系统配置对象，设置文件下载临时路径
        Config config = Config.getInstance();
        String tmpFile = config.getDownloadFilePath() + wybs;
        
        try {
            in = new FileInputStream(new File(fullPath));
            out = new FileOutputStream(new File(tmpFile));

            // 复制一份文件到系统临时下载区
            exchangeStream(in, out);
            log.debug("fullPath="+fullPath);
            log.debug("tmpFile="+tmpFile);
            // 添加附件下载信息
            context.getConttrolData().addDownloadFile(wjmc, tmpFile);

        } catch (IOException ioe) {
            log.error("文件下载错误 " + ioe);
            /*throw new TxnErrorException(ErrorConstant.FILE_DOWNLOAD_ERROR,
                    BJAISConfig.get("ggkz.ccgl.wjys.io.downloaderror"));*/
        } finally {
            try {
                // 关闭数据流
                if (in != null)
                    in.close();
                if (out != null)
                    out.close();
            } catch (IOException ioe) {
                log.error(ioe);
                /*throw new TxnErrorException(ErrorConstant.FILE_CLOSE_ERROR,
                        BJAISConfig.get("ggkz.ccgl.wjys.io.closeerror"));*/
            }
        }
	}
	
	/**
     * 
     * getPathByCCLB(根据存储类别获取根目录信息)    
     * TODO(这里描述这个方法适用条件 – 可选)    
     * TODO(这里描述这个方法的执行流程 – 可选)    
     * TODO(这里描述这个方法的使用方法 – 可选)    
     * TODO(这里描述这个方法的注意事项 – 可选)    
     * @param cclb
     * @return        
     * String       
     * @Exception 异常对象    
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public String getPathByCCLB(String cclb){
    	String rootPath = "";
    	
    	if("07".equals(cclb)){
    		rootPath = ExConstant.FILE_UPLOAD;
    	}else if ("02".equals(cclb)){
    		rootPath = ExConstant.COLLECT_RECORD;
    	}else if("01".equals(cclb)){
    		rootPath = ExConstant.SHARE_RECORD;
    	}else if("03".equals(cclb)){
    		rootPath = ExConstant.REPORT;
    	}else if("05".equals(cclb)){
    		rootPath = ExConstant.COLLECT_XML;
    	}else if("04".equals(cclb)){
    		rootPath = ExConstant.SHARE_XML;
    	}else if("06".equals(cclb)){
    		rootPath = ExConstant.RES_TBL_RECORD;
    	}else if("08".equals(cclb)){
    		rootPath = ExConstant.FILE_FTP;
    	}else if("10".equals(cclb)){
    		rootPath = ExConstant.FILE_DATABASE;
    	}else if("09".equals(cclb)){
    		rootPath = ExConstant.SHARE_CONFIG;
    	}else if("12".equals(cclb)){
    		rootPath = ExConstant.SERVICE_TARGET;
    	}else {
    		rootPath = "";
    	}
    	
    	return rootPath;
    }
	
	/**
     * 将数据输入流 交换到 数据输出流
     * 
     * @param input
     *            数据输入流
     * @param output
     *            数据输出流
     * @throws IOException
     */
    private void exchangeStream(InputStream input, OutputStream output)
            throws IOException {
        int readLen = 0;
        byte[] readBytes = new byte[8 * 1024];
        while ((readLen = input.read(readBytes)) > 0) {
            output.write(readBytes, 0, readLen);
        }
        output.flush();
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
		XtCcglWjysContext appContext = new XtCcglWjysContext( context );
		invoke( method, appContext );
	}
}
