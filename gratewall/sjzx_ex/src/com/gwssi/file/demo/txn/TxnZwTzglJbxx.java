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
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnZwTzglJbxx.class, ZwTzglJbxxContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "zw_tzgl_jbxx";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select zw_tzgl_jbxx list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one zw_tzgl_jbxx";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one zw_tzgl_jbxx";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one zw_tzgl_jbxx";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one zw_tzgl_jbxx";
	
	/**
	 * ���캯��
	 */
	public TxnZwTzglJbxx()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ֪ͨ�����б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn315001001( ZwTzglJbxxContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoZwTzglJbxxSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoZwTzglJbxx result[] = context.getZwTzglJbxxs( outputNode );
	}
	
	/** �޸�֪ͨ������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn315001002( ZwTzglJbxxContext context ) throws TxnException
	{
		System.out.println(context);
		//��ȡҳ���ϵĻ�����Ϻͱ�ɾ���Ļ�����ϼ����Ե�ID
        String delIDs = context.getRecord(inputNode).getValue(
                VoZwTzglJbxx.ITEM_DELIDS);
        String delNAMEs = context.getRecord(inputNode).getValue(
        		VoZwTzglJbxx.ITEM_DELNAMES);
        String hyclid = context.getRecord(inputNode).getValue(
        		VoZwTzglJbxx.ITEM_FJ_FK);
        String hycl = context.getRecord(inputNode).getValue(VoZwTzglJbxx.ITEM_FJMC);
        
        //����һ��UploadFileVO���󣬱�������������͵Ķ฽��
        UploadFileVO fileVO = new UploadFileVO();
        fileVO.setRecordName("record:fjmc");
        fileVO.setDeleteId(delIDs);//ҳ�汣��ı�ɾ������Idֵ
        fileVO.setDeleteName(delNAMEs);// ҳ�汣��ı�ɾ������nameֵ
        fileVO.setOriginId(hyclid);// ���¸���ǰҵ�����ݿ����id�ֶδ洢��ֵ
        fileVO.setOriginName(hycl);// ���¸���ǰҵ�����ݿ����name�ֶδ洢��ֵ
        fileVO.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);//�฽��
        UploadFileVO vo = UploadHelper.updateFile(context, fileVO,
                ConstUploadFileType.SHARECORD);//��������Ϊ�������

        //��������Ϣ���ݵ�inputNode
        context.getRecord(inputNode).setValue(VoZwTzglJbxx.ITEM_FJ_FK,
                vo.getReturnId());
        context.getRecord(inputNode).setValue(VoZwTzglJbxx.ITEM_FJMC,
                vo.getReturnName());
        
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoZwTzglJbxx zw_tzgl_jbxx = context.getZwTzglJbxx( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ����֪ͨ������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn315001003( ZwTzglJbxxContext context ) throws TxnException
	{
		
		UploadFileVO fileVO1 = new UploadFileVO();
        //fileVO1.setRecordName("record:fjmc");
        fileVO1.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);

        UploadFileVO vo = UploadHelper.saveFile(context, fileVO1,
                ConstUploadFileType.SHARECORD);
        
        //��������Ϣ���ݵ�inputNode
        context.getRecord(inputNode).setValue("fj_fk",
                vo.getReturnId());
        context.getRecord(inputNode).setValue("fjmc",
                vo.getReturnName());
        BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoZwTzglJbxx zw_tzgl_jbxx = context.getZwTzglJbxx( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ֪ͨ���������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn315001004( ZwTzglJbxxContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoZwTzglJbxxPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoZwTzglJbxx result = context.getZwTzglJbxx( outputNode );
		
		//������ڵ��л�ȡ������ϵ�ID
        String fjids = context.getRecord(outputNode).getValue(
                VoZwTzglJbxx.ITEM_FJ_FK);

        //������ڵ��л�ȡ������ϵ�����
        String filenames = context.getRecord(outputNode).getValue(
        		VoZwTzglJbxx.ITEM_FJMC);

        //���ýӿڽ���ȡ���ļ���Ϣһһ����context
        UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
	}
	
	/** ɾ��֪ͨ������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn315001005( ZwTzglJbxxContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		BaseTable tabletmp = TableFactory.getInstance().getTableObject(this, TABLE_NAME);
		// ɾ����¼�������б� VoZwTzglJbxxPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		Recordset rstz = context.getRecordset("primary-key");
        for (int i = 0; i < rstz.size(); i++) {
            DataBus dbtmp = rstz.get(i);

            //ɾ������
            tabletmp.executeFunction(SELECT_FUNCTION, context, dbtmp, "outputNodetmp");
            String fjids = context.getRecord("outputNodetmp").getValue(
            		VoZwTzglJbxx.ITEM_FJ_FK);
            UploadHelper.deleteFile(context, fjids, ConstUploadFileType.SHARECORD);

        }
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ֪ͨ���������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn315001006( ZwTzglJbxxContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoZwTzglJbxxPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoZwTzglJbxx result = context.getZwTzglJbxx( outputNode );
		
		//������ڵ��л�ȡ������ϵ�ID
        String fjids = context.getRecord(outputNode).getValue(
                VoZwTzglJbxx.ITEM_FJ_FK);

        //������ڵ��л�ȡ������ϵ�����
        String filenames = context.getRecord(outputNode).getValue(
        		VoZwTzglJbxx.ITEM_FJMC);

        //���ýӿڽ���ȡ���ļ���Ϣһһ����context
        UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
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
		ZwTzglJbxxContext appContext = new ZwTzglJbxxContext( context );
		invoke( method, appContext );
	}
}
