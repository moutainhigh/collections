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
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysRdStandard.class, SysRdStandardContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "sys_rd_standard";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select sys_rd_standard list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one sys_rd_standard";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one sys_rd_standard";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one sys_rd_standard";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one sys_rd_standard";
	
	public static final String	DB_CONFIG		= "app";
	/**
	 * ���캯��
	 */
	public TxnSysRdStandard()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ�淶�б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000201( SysRdStandardContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysRdStandardSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoSysRdStandard result[] = context.getSysRdStandards( outputNode );
	}
	/** ��ѯ�淶�б�����ҳ����
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000207( SysRdStandardContext context ) throws TxnException
	{
		Attribute.setPageRow(context, outputNode, 5);
		callService("7000201", context);
	}
	
	/** �޸Ĺ淶��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000202( SysRdStandardContext context ) throws TxnException
	{
		System.out.println("txn7000202");
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		
		setFile(context);
		
		
		// �޸ļ�¼������ VoSysRdStandard sys_rd_standard = context.getSysRdStandard( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ���ӹ淶��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000203( SysRdStandardContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoSysRdStandard sys_rd_standard = context.getSysRdStandard( inputNode );
		setFile(context);
		table.executeFunction("queryMaxSort", context, inputNode, "sort");//��ȡ��������
		Recordset rs = context.getRecordset("sort");
		DataBus tempSort = (DataBus)rs.get(0);
        if(tempSort.getValue("max(sort)")!=null&&!tempSort.getValue("max(sort)").equals("")){
        	int Intsort=Integer.parseInt(tempSort.getValue("max(sort)"))+1;
            String sort=Intsort+"";
        	context.getRecord("record").setValue("sort", sort);
        }else{
        	context.getRecord("record").setValue("sort","1" );
        }
		context.getRecord("record").setValue("sys_rd_standard_id",UuidGenerator.getUUID() );//���ID
		String TimeStamp=CalendarUtil.getCurrentDateTime();//���ʱ���
		context.getRecord("record").setValue("timestamp", TimeStamp);
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ�淶�����޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000204( SysRdStandardContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysRdStandardPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoSysRdStandard result = context.getSysRdStandard( outputNode );
	}
	
	/** ɾ���淶��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000205( SysRdStandardContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoSysRdStandardPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ�淶������ͼ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000206( SysRdStandardContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoSysRdStandardPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ���ݿ���
	 * �ս��ף����ڿ���Ȩ��
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000001( SysRdStandardContext context ) throws TxnException
	{
		
	}
	
	/** �м�����
	 * �ս��ף����ڿ���Ȩ��
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000002( SysRdStandardContext context ) throws TxnException
	{
		
	}
	
	/** �������
	 * �ս��ף����ڿ���Ȩ��
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000003( SysRdStandardContext context ) throws TxnException
	{
		
	}
	
	/** ȫ�ļ������
	 * �ս��ף����ڿ���Ȩ��
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000004( SysRdStandardContext context ) throws TxnException
	{
		
	}
	
	/** �ӿڷ�����
	 * �ս��ף����ڿ���Ȩ��
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000005( SysRdStandardContext context ) throws TxnException
	{
		
	}
	
	/** �����
	 * �ս��ף����ڿ���Ȩ��
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000006( SysRdStandardContext context ) throws TxnException
	{
		
	}
	
	/** ��ع���
	 * �ս��ף����ڿ���Ȩ��
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000007( SysRdStandardContext context ) throws TxnException
	{
		
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
		SysRdStandardContext appContext = new SysRdStandardContext( context );
		invoke( method, appContext );
	}
	
	/*
	 * �����ϴ��ļ�
	 */
	private void setFile(SysRdStandardContext context)
	{
		String rootPath = java.util.ResourceBundle.getBundle(DB_CONFIG).getString("docFilePath");
		rootPath=rootPath+"Specification/";
		 File file = new File(rootPath);
		  //�ж��ļ����Ƿ����,����������򴴽��ļ���
		  if (!file.exists()) {
		   file.mkdir();
		  }
		  file=null;
		VoFileInfo[] files = context.getConttrolData().getUploadFileList();
		for(int i=0;i<files.length;i++){
			//�õ��ϴ�ʱ���ļ���
			String localName = files[i].getLocalFileName();
			//�õ��ļ��ڷ������ϵľ���·�����ļ���
			String serverName = files[i].getOriFileName();
			String timestamp = CalendarUtil.getCurrentDateTime();
			String filename = timestamp.replaceAll("-", "").replaceAll(":", "").replaceAll(" ","")+".doc";
			try {
				InputStream in = new FileInputStream(localName);
				OutputStream out = new FileOutputStream(new File(rootPath+""+filename));
			//���ļ��������ָ���ϴ�Ŀ¼
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
