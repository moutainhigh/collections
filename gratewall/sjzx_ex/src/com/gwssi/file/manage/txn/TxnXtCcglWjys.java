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
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnXtCcglWjys.class, XtCcglWjysContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "xt_ccgl_wjys";
	//�ļ�����
	private static final String WJLB_TABLE = "xt_ccgl_wjlb";
	
	// �ļ�ӳ�������
    private static final String WJYS_TABLE = "xt_ccgl_wjys";
    
    // ��������¼��ѯ
    private static final String SELECT_WJYS_PK = "select one xt_ccgl_wjys by pk";

    // ��ѯ��¼
    private static final String SELECT_WJLB_FUNCTION = "select one xt_ccgl_wjlb";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select xt_ccgl_wjys list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one xt_ccgl_wjys";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one xt_ccgl_wjys";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one xt_ccgl_wjys";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one xt_ccgl_wjys";
	
	/**
	 * ���캯��
	 */
	public TxnXtCcglWjys()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ�ļ�ӳ���б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn604060101( XtCcglWjysContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoXtCcglWjysSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoXtCcglWjys result[] = context.getXtCcglWjyss( outputNode );
	}
	
	/** �޸��ļ�ӳ����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn604060102( XtCcglWjysContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoXtCcglWjys xt_ccgl_wjys = context.getXtCcglWjys( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** �����ļ�ӳ����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn604060103( XtCcglWjysContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoXtCcglWjys xt_ccgl_wjys = context.getXtCcglWjys( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ�ļ�ӳ�������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn604060104( XtCcglWjysContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoXtCcglWjysPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoXtCcglWjys result = context.getXtCcglWjys( outputNode );
	}
	
	/** ɾ���ļ�ӳ����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn604060105( XtCcglWjysContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoXtCcglWjysPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	
	/**
     * �����ļ� 604050200
     * 
     * @param context
     *            ����������
     * @throws cn.gwssi.common.component.exception.TxnException
     * @throws OperationLogException
     */
	public void txn604050209( XtCcglWjysContext context ) throws TxnException
	{	
		InputStream in = null; // ������
        OutputStream out = null; // �����

        BaseTable table = TableFactory.getInstance().getTableObject(this,
                WJYS_TABLE);
        // ��ȡ�ļ��洢�Ĵ洢·����Ϣ
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

        // ���ݲ�ѯ������ø�����Ϣ
        DataBus outDb = context.getRecord(outputNode);
        String cclbbh_pk = outDb.getValue(VoXtCcglWjys.ITEM_CCLBBH_PK);//�洢�����
        String wjmc = outDb.getValue(VoXtCcglWjys.ITEM_WJMC);
        String wybs = outDb.getValue(VoXtCcglWjys.ITEM_WYBS);
        String cclj = outDb.getValue(VoXtCcglWjys.ITEM_CCLJ);
        log.debug("�ļ����� �ļ����ƣ�  " + wjmc);

        // �����ļ�����������ֵ
        DataBus db = new DataBus();
        db.put(VoXtCcglWjlb.ITEM_CCLBBH_PK, cclbbh_pk);

       /* BaseTable table1 = TableFactory.getInstance().getTableObject(this,
                WJLB_TABLE);

        // �����ļ��������ֵ��ѯ�ļ���𣬻�ȡ����Ŀ¼
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

        // �����ļ�����Ŀ¼����װ�ļ��洢ȫ·��
        /*String root = context.getRecord("cclb").getValue(
                VoXtCcglWjlb.ITEM_CCGML);*/
        String root = getPathByCCLB(cclbbh_pk);
        String fullPath = root + FileConstant.PATH_SEPERATOR + cclj
                + FileConstant.PATH_SEPERATOR + wybs;

        // ��ȡϵͳ���ö��������ļ�������ʱ·��
        Config config = Config.getInstance();
        String tmpFile = config.getDownloadFilePath() + wybs;
        
        try {
            in = new FileInputStream(new File(fullPath));
            out = new FileOutputStream(new File(tmpFile));

            // ����һ���ļ���ϵͳ��ʱ������
            exchangeStream(in, out);
            log.debug("fullPath="+fullPath);
            log.debug("tmpFile="+tmpFile);
            // ��Ӹ���������Ϣ
            context.getConttrolData().addDownloadFile(wjmc, tmpFile);

        } catch (IOException ioe) {
            log.error("�ļ����ش��� " + ioe);
            /*throw new TxnErrorException(ErrorConstant.FILE_DOWNLOAD_ERROR,
                    BJAISConfig.get("ggkz.ccgl.wjys.io.downloaderror"));*/
        } finally {
            try {
                // �ر�������
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
     * getPathByCCLB(���ݴ洢����ȡ��Ŀ¼��Ϣ)    
     * TODO(����������������������� �C ��ѡ)    
     * TODO(�����������������ִ������ �C ��ѡ)    
     * TODO(�����������������ʹ�÷��� �C ��ѡ)    
     * TODO(�����������������ע������ �C ��ѡ)    
     * @param cclb
     * @return        
     * String       
     * @Exception �쳣����    
     * @since  CodingExample��Ver(���뷶���鿴) 1.1
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
     * ������������ ������ ���������
     * 
     * @param input
     *            ����������
     * @param output
     *            ���������
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
	 * ���ظ���ķ����������滻���׽ӿڵ��������
	 * ���ú���
	 * @param funcName ��������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void doService( String funcName,
			TxnContext context ) throws TxnException
	{
		Method method = (Method)txnMethods.get( funcName );
		if( method == null ){
			funcName = this.getClass().getName() + "#" + funcName;
			throw new TxnErrorException( ErrorConstant.JAVA_METHOD_NOTFOUND,
					"û���ҵ�������[" + txnCode + ":" + funcName + "]�Ĵ�����"
			);
		}
		
		// ִ��
		XtCcglWjysContext appContext = new XtCcglWjysContext( context );
		invoke( method, appContext );
	}
}
