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
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnDownloadCompare.class, DownloadCompareContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "download_compare";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select download_compare list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one download_compare";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one download_compare";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one download_compare";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one download_compare";
	
	/**
	 * ���캯��
	 */
	public TxnDownloadCompare()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ�ȶ������б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60500001( DownloadCompareContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoDownloadCompareSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoDownloadCompare result[] = context.getDownloadCompares( outputNode );
	}
	
	/** �޸ıȶ�������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60500002( DownloadCompareContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoDownloadCompare download_compare = context.getDownloadCompare( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ���ӱȶ�������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60500003( DownloadCompareContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoDownloadCompare download_compare = context.getDownloadCompare( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ�ȶ����������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60500004( DownloadCompareContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoDownloadComparePrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoDownloadCompare result = context.getDownloadCompare( outputNode );
	}
	
	/** ɾ���ȶ�������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60500005( DownloadCompareContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );		
		// ɾ������
		// 1. ѭ���������ݿ�����
		Recordset rs = context.getRecordset(inputNode);
		for (int i=0; i < rs.size(); i++){
			TxnContext txnContext = new TxnContext();
			txnContext.getRecord(inputNode).setValue("download_compare_id", 
					rs.get(i).getValue("download_compare_id"));
			table.executeFunction(ROWSET_FUNCTION, txnContext, inputNode, "record");
			if (txnContext.getRecordset("record").size() == 0){
				continue;
			}
			// 2. ��ɾ����������ʱ��
			// 2.1 ��ѯ���Ƿ����
			// 2.2 ɾ����
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
			
			// 3. ɾ��download_columns�е��ֶμ�¼
			TxnContext tc2 = new TxnContext();
			tc2.getRecord("select-key").setValue("table_no", 
					txnContext.getRecord("record").getValue("table_no"));
			table.executeFunction("deleteDataInColumnTable", tc2, "select-key", "record");
		}
		
		// 3. ɾ��download_compare�еļ�¼
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/**
	 * �ϴ��ļ�
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
			throw new TxnDataException("999999", "�ļ���������!"); 
		} catch (IOException e) {
			e.printStackTrace();
			throw new TxnDataException("999999", "�ļ���������!");
		}
		
		String table_no = com.gwssi.common.util.UuidGenerator.getUUID();
		String download_compare_id = com.gwssi.common.util.UuidGenerator.getUUID();
		TxnContext txnContext = new TxnContext();
		DataBus db = txnContext.getRecord("record");
		db.setValue("download_compare_id", download_compare_id);
		db.setValue("table_no", table_no);
		db.setValue("table_name", tableName.toUpperCase());
		db.setValue("table_name_cn", "�ȶԱ�(" + tableName + ")");
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
	 * �����޸ı�ע��Ϣ
	 * @param context
	 * @throws TxnException
	 */
	public void txn60500007( DownloadCompareContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoDownloadComparePrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( "updateRemark", context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoDownloadCompare result = context.getDownloadCompare( outputNode );
	}
	
	/**
	 * ��ѯ�ȶ�������Ϣ
	 * @param context
	 * @throws TxnException
	 */
	public void txn60500008( DownloadCompareContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoDownloadCompareSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( "queryResult", context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoDownloadCompare result[] = context.getDownloadCompares( outputNode );
	}
	
	/**
	 * �����ϴ����ļ�
	 * @return
	 * @throws IOException 
	 * @throws BiffException 
	 * @throws TxnDataException 
	 */
	private boolean destroyUploadFile(String filePath) throws BiffException, IOException, TxnDataException{
		File file = new File(filePath);
		jxl.Workbook xls = jxl.Workbook.getWorkbook(file);
		jxl.Sheet[] sheetArray = xls.getSheets();		
		
		// 1. ��ȡxls��sheet��Ϣ��ֻ����ÿһ��sheetҳ�ĵ�һ������
		// 1.1 �ж�����Ŀ�Ƿ�һ��
		// 1.2 �ǿ�sheetҳ�ĵ�һ������(Trim)�Ƿ�һ��
		int[] columnNumberArray = new int[sheetArray.length]; // �洢ÿһsheet������������
 		String[] columnTitleArray = new String[sheetArray.length]; // �洢title��
 		int defaultcolumnNumber = 0;
		String defaultTitleText = "";
		int defaultSheetNumber = 0;
 		for (int i = 0 ; i < sheetArray.length; i++){
 			// ��sheetҳ�����ݲ�ֹһ�е�ʱ��
			if (sheetArray[i].getRows() > 0){
				columnNumberArray[i] = sheetArray[i].getColumns();
				columnTitleArray[i] = "";
				jxl.Cell[] titleArray  = sheetArray[i].getRow(0);
				for (int j= 0; j < titleArray.length; j ++){
					columnTitleArray[i] += titleArray[j].getContents().trim() + ",";
				}
				System.out.println("��" + i + "��sheet��title��" + columnTitleArray[i]);
				// �洢��һ��sheet��������title��Ϣ
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
							"��" + (i + 1) + "��sheetҳ�ı�ͷ�͵�" + (defaultSheetNumber + 1)
							+ "��sheetҳ�ı�ͷ��һ�£��޷�ͨ��У�飡");
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
				// �Ƚ�ÿһ�е����Ƿ�һ��
				if (defaultLength != line.split(regex).length){
					throw new TxnDataException("999999", 
							"��" + (i + 1) + "�е����ݸ��������е����ݸ�����һ�£��޷�ͨ��У�飡");
				}
			}
			line = reader.readLine();
			i ++;
		}
		
		return true;
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
		DownloadCompareContext appContext = new DownloadCompareContext( context );
		invoke( method, appContext );
	}
}
