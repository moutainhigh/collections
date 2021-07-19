package com.gwssi.standard.txn;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;




import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.constant.ConstUploadFileType;
import com.gwssi.common.constant.FileConstant;
import com.gwssi.common.task.ZwtJsonCreat;
import com.gwssi.common.upload.UploadFileVO;
import com.gwssi.common.upload.UploadHelper;
import com.gwssi.common.util.DateUtil;
import com.gwssi.common.util.JsonDataUtil;
import com.gwssi.file.demo.vo.VoZwTzglJbxx;
import com.gwssi.file.demo.vo.ZwTzglJbxxContext;

import com.gwssi.standard.vo.CommonStandardContext;
import com.gwssi.standard.vo.VoCommonStandard;

public class TxnCommonStandard extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnCommonStandard.class, CommonStandardContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "common_standard";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select common_standard list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one common_standard";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one common_standard";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one common_standard";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one common_standard";
	
	/**
	 * ���캯��
	 */
	public TxnCommonStandard()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	public void txn603903( CommonStandardContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		//�������ݹ淶����json����
		CommonStandardContext standardContext = new CommonStandardContext();
		standardContext.getRecord(inputNode).setValue("table_name",
				"common_standard");
		standardContext.getRecord(inputNode).setValue("col_name",
				"standard_id");
		standardContext.getRecord(inputNode).setValue("col_title",
				"standard_name");
		Attribute.setPageRow(standardContext, outputNode, -1);
		table.executeFunction("getInfoBydataId", standardContext, inputNode,
				outputNode);

		Recordset standardRs = standardContext.getRecordset("record");
		context.setValue("staName",
				JsonDataUtil.getJsonByRecordSet(standardRs));
	}
	public void txn603906( CommonStandardContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		//�������ݹ淶����json����
		CommonStandardContext standardContext = new CommonStandardContext();
		standardContext.getRecord(inputNode).setValue("table_name",
				"common_standard");
		standardContext.getRecord(inputNode).setValue("col_name",
				"standard_id");
		standardContext.getRecord(inputNode).setValue("col_title",
				"standard_name");
		Attribute.setPageRow(standardContext, outputNode, -1);
		table.executeFunction("getInfoBytechId", standardContext, inputNode,
				outputNode);

		Recordset standardRs = standardContext.getRecordset("record");
		context.setValue("staName",
				JsonDataUtil.getJsonByRecordSet(standardRs));
	}
	public void txn603909( CommonStandardContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		//�������ݹ淶����json����
		CommonStandardContext standardContext = new CommonStandardContext();
		standardContext.getRecord(inputNode).setValue("table_name",
				"common_standard");
		standardContext.getRecord(inputNode).setValue("col_name",
				"standard_id");
		standardContext.getRecord(inputNode).setValue("col_title",
				"standard_name");
		Attribute.setPageRow(standardContext, outputNode, -1);
		table.executeFunction("getInfoByrenameId", standardContext, inputNode,
				outputNode);

		Recordset standardRs = standardContext.getRecordset("record");
		context.setValue("staName",
				JsonDataUtil.getJsonByRecordSet(standardRs));
	}
	/** ��ѯ�����б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn603001( CommonStandardContext context ) throws TxnException
	{
        //System.out.println("603001ֵΪ"+context);
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoCommonStandardSelectKey selectKey = context.getSelectKey( inputNode );
		String create_time = context.getRecord("select-key").getValue(
				"enable_time");
		if (StringUtils.isNotBlank(create_time)) {
			String[] ctime = DateUtil.getDateRegionByDatePicker(create_time,
					false);
			context.getRecord("select-key").setValue("created_time_start",
					ctime[0]);
			context.getRecord("select-key").setValue("created_time_end",
					ctime[1]);
			context.getRecord("select-key").remove("created_time");
		}
		table.executeFunction( "queryDataStandard", context, inputNode, outputNode );
		callService("603903", context);
		// ��ѯ���ļ�¼�� VoCommonStandard result[] = context.getCommonStandards( outputNode );
	}
	
	/** �޸Ĺ�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn603002( CommonStandardContext context ) throws TxnException
	{ 
		String userId = context.getRecord("oper-data").getValue("userID");
		context.getRecord("record").setValue("last_modify_id", userId);
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new java.util.Date());
		context.getRecord("record").setValue("last_modify_time", datetime);
		
		   String delIDs = context.getRecord(inputNode).getValue(
	                VoCommonStandard.ITEM_DELIDS);
	        String delNAMEs = context.getRecord(inputNode).getValue(
	        		VoCommonStandard.ITEM_DELNAMES);
		 String hyclid = context.getRecord(inputNode).getValue(
				 VoCommonStandard.ITEM_FJ_FK);
	        String hycl = context.getRecord(inputNode).getValue(VoCommonStandard.ITEM_FJMC);
	       
	        
	
	        
        //����һ��UploadFileVO���󣬱�������������͵Ķ฽��
        UploadFileVO fileVO = new UploadFileVO();
        fileVO.setRecordName("record:fjmc");
        fileVO.setDeleteId(delIDs);//ҳ�汣��ı�ɾ������Idֵ
        fileVO.setDeleteName(delNAMEs);// ҳ�汣��ı�ɾ������nameֵ
        fileVO.setOriginId(hyclid);// ���¸���ǰҵ�����ݿ����id�ֶδ洢��ֵ
        fileVO.setOriginName(hycl);// ���¸���ǰҵ�����ݿ����name�ֶδ洢��ֵ
        fileVO.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);//�฽��
        
        UploadFileVO vo = UploadHelper.updateFile(context, fileVO,
                ConstUploadFileType.REPORT);//��������Ϊ�������
        
        //��������Ϣ���ݵ�inputNode
        context.getRecord(inputNode).setValue(VoCommonStandard.ITEM_FJ_FK,
                vo.getReturnId());
        context.getRecord(inputNode).setValue(VoCommonStandard.ITEM_FJMC,
                vo.getReturnName());
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoCommonStandard common_standard = context.getCommonStandard( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ���ӹ�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn603003( CommonStandardContext context ) throws TxnException
	{
		
		
		String userId = context.getRecord("oper-data").getValue("userID");
		context.getRecord("record").setValue("creator_id", userId);
		context.getRecord("record").setValue("last_modify_id", userId);
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new java.util.Date());
		context.getRecord("record").setValue("created_time", datetime);
		context.getRecord("record").setValue("last_modify_time", datetime);
		
		
		UploadFileVO fileVO1 = new UploadFileVO();
        //fileVO1.setRecordName("record:fjmc");
        fileVO1.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);

        UploadFileVO vo = UploadHelper.saveFile(context, fileVO1,
                ConstUploadFileType.REPORT);
		 //��������Ϣ���ݵ�inputNode
        context.getRecord(inputNode).setValue("fj_fk",
                vo.getReturnId());
        context.getRecord(inputNode).setValue("fjmc",
                vo.getReturnName());
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoCommonStandard common_standard = context.getCommonStandard( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ���������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn603004( CommonStandardContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoCommonStandardPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoCommonStandard result = context.getCommonStandard( outputNode );
		//������ڵ��л�ȡ������ϵ�ID
        String fjids = context.getRecord(outputNode).getValue(
                VoCommonStandard.ITEM_FJ_FK);

        //������ڵ��л�ȡ������ϵ�����
        String filenames = context.getRecord(outputNode).getValue(
        		VoCommonStandard.ITEM_FJMC);

        //���ýӿڽ���ȡ���ļ���Ϣһһ����context
        UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
	}
	
	/** ɾ��������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn603005( CommonStandardContext context ) throws TxnException
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
            		VoCommonStandard.ITEM_FJ_FK);
            UploadHelper.deleteFile(context, fjids, ConstUploadFileType.REPORT);

        }
		
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
		
	
	
	/** ��ѯ���������б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn603006(CommonStandardContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoCommonStandardSelectKey selectKey = context.getSelectKey(
		// inputNode );
		String create_time = context.getRecord("select-key").getValue(
				"enable_time");
		if (StringUtils.isNotBlank(create_time)) {
			String[] ctime = DateUtil.getDateRegionByDatePicker(create_time,
					false);
			context.getRecord("select-key").setValue("created_time_start",
					ctime[0]);
			context.getRecord("select-key").setValue("created_time_end",
					ctime[1]);
			context.getRecord("select-key").remove("created_time");
		}

		table.executeFunction("queryTechStandard", context, inputNode,
				outputNode);
		callService("603905", context);
		// ��ѯ���ļ�¼�� VoCommonStandard result[] = context.getCommonStandards(
		// outputNode );
	}
	
	/** �޸ļ���������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn603007( CommonStandardContext context ) throws TxnException
	{
		String userId = context.getRecord("oper-data").getValue("userID");
		context.getRecord("record").setValue("last_modify_id", userId);
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new java.util.Date());
		context.getRecord("record").setValue("last_modify_time", datetime);
		
		  String delIDs = context.getRecord(inputNode).getValue(
	                VoCommonStandard.ITEM_DELIDS);
	        String delNAMEs = context.getRecord(inputNode).getValue(
	        		VoCommonStandard.ITEM_DELNAMES);
		 String hyclid = context.getRecord(inputNode).getValue(
				 VoCommonStandard.ITEM_FJ_FK);
	        String hycl = context.getRecord(inputNode).getValue(VoCommonStandard.ITEM_FJMC);
      //����һ��UploadFileVO���󣬱�������������͵Ķ฽��
      UploadFileVO fileVO = new UploadFileVO();
      fileVO.setRecordName("record:fjmc");
      fileVO.setDeleteId(delIDs);//ҳ�汣��ı�ɾ������Idֵ
      fileVO.setDeleteName(delNAMEs);// ҳ�汣��ı�ɾ������nameֵ
      fileVO.setOriginId(hyclid);// ���¸���ǰҵ�����ݿ����id�ֶδ洢��ֵ
      fileVO.setOriginName(hycl);// ���¸���ǰҵ�����ݿ����name�ֶδ洢��ֵ
      fileVO.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);//�฽��
      UploadFileVO vo = UploadHelper.updateFile(context, fileVO,
              ConstUploadFileType.REPORT);//��������Ϊ�������

      //��������Ϣ���ݵ�inputNode
      context.getRecord(inputNode).setValue(VoCommonStandard.ITEM_FJ_FK,
              vo.getReturnId());
      context.getRecord(inputNode).setValue(VoCommonStandard.ITEM_FJMC,
              vo.getReturnName());
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoCommonStandard common_standard = context.getCommonStandard( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ���Ӽ���������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn603008( CommonStandardContext context ) throws TxnException
	{
		
		String userId = context.getRecord("oper-data").getValue("userID");
		context.getRecord("record").setValue("creator_id", userId);
		context.getRecord("record").setValue("last_modify_id", userId);
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new java.util.Date());
		context.getRecord("record").setValue("created_time", datetime);
		context.getRecord("record").setValue("last_modify_time", datetime);
		
		UploadFileVO fileVO1 = new UploadFileVO();
        //fileVO1.setRecordName("record:fjmc");
        fileVO1.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);

        UploadFileVO vo = UploadHelper.saveFile(context, fileVO1,
                ConstUploadFileType.REPORT);
		 //��������Ϣ���ݵ�inputNode
        context.getRecord(inputNode).setValue("fj_fk",
                vo.getReturnId());
        context.getRecord(inputNode).setValue("fjmc",
                vo.getReturnName());
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoCommonStandard common_standard = context.getCommonStandard( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ�������������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn603009( CommonStandardContext context ) throws TxnException
	{	
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoCommonStandardPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoCommonStandard result = context.getCommonStandard( outputNode );
		 String fjids = context.getRecord(outputNode).getValue(
	                VoCommonStandard.ITEM_FJ_FK);

	        //������ڵ��л�ȡ������ϵ�����
	        String filenames = context.getRecord(outputNode).getValue(
	        		VoCommonStandard.ITEM_FJMC);

	        //���ýӿڽ���ȡ���ļ���Ϣһһ����context
	        UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
	}
	
	/** ɾ������������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn603010( CommonStandardContext context ) throws TxnException
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
            		VoCommonStandard.ITEM_FJ_FK);
            UploadHelper.deleteFile(context, fjids, ConstUploadFileType.REPORT);

        }
		
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ���������б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn603011( CommonStandardContext context ) throws TxnException
	{
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoCommonStandardSelectKey selectKey = context.getSelectKey( inputNode );
		String create_time = context.getRecord("select-key").getValue(
				"enable_time");
		if (StringUtils.isNotBlank(create_time)) {
			String[] ctime = DateUtil.getDateRegionByDatePicker(create_time,
					false);
			context.getRecord("select-key").setValue("created_time_start",
					ctime[0]);
			context.getRecord("select-key").setValue("created_time_end",
					ctime[1]);
			context.getRecord("select-key").remove("created_time");
		}
		table.executeFunction( "queryRenameStandard", context, inputNode, outputNode );
		callService("603908", context);
		// ��ѯ���ļ�¼�� VoCommonStandard result[] = context.getCommonStandards( outputNode );
	}
	
	/** �޸�����������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn603012( CommonStandardContext context ) throws TxnException
	{
		String userId = context.getRecord("oper-data").getValue("userID");
		context.getRecord("record").setValue("last_modify_id", userId);
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new java.util.Date());
		context.getRecord("record").setValue("last_modify_time", datetime);
		
		  String delIDs = context.getRecord(inputNode).getValue(
	                VoCommonStandard.ITEM_DELIDS);
	        String delNAMEs = context.getRecord(inputNode).getValue(
	        		VoCommonStandard.ITEM_DELNAMES);
		 String hyclid = context.getRecord(inputNode).getValue(
				 VoCommonStandard.ITEM_FJ_FK);
	        String hycl = context.getRecord(inputNode).getValue(VoCommonStandard.ITEM_FJMC);
      //����һ��UploadFileVO���󣬱�������������͵Ķ฽��
      UploadFileVO fileVO = new UploadFileVO();
      fileVO.setRecordName("record:fjmc");
      fileVO.setDeleteId(delIDs);//ҳ�汣��ı�ɾ������Idֵ
      fileVO.setDeleteName(delNAMEs);// ҳ�汣��ı�ɾ������nameֵ
      fileVO.setOriginId(hyclid);// ���¸���ǰҵ�����ݿ����id�ֶδ洢��ֵ
      fileVO.setOriginName(hycl);// ���¸���ǰҵ�����ݿ����name�ֶδ洢��ֵ
      fileVO.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);//�฽��
      UploadFileVO vo = UploadHelper.updateFile(context, fileVO,
              ConstUploadFileType.REPORT);//��������Ϊ�������

      //��������Ϣ���ݵ�inputNode
      context.getRecord(inputNode).setValue(VoCommonStandard.ITEM_FJ_FK,
              vo.getReturnId());
      context.getRecord(inputNode).setValue(VoCommonStandard.ITEM_FJMC,
              vo.getReturnName());
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoCommonStandard common_standard = context.getCommonStandard( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��������������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn603013( CommonStandardContext context ) throws TxnException
	{
		
		String userId = context.getRecord("oper-data").getValue("userID");
		context.getRecord("record").setValue("creator_id", userId);
		context.getRecord("record").setValue("last_modify_id", userId);
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new java.util.Date());
		context.getRecord("record").setValue("created_time", datetime);
		context.getRecord("record").setValue("last_modify_time", datetime);
		
		UploadFileVO fileVO1 = new UploadFileVO();
        //fileVO1.setRecordName("record:fjmc");
        fileVO1.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);

        UploadFileVO vo = UploadHelper.saveFile(context, fileVO1,
                ConstUploadFileType.REPORT);
		 //��������Ϣ���ݵ�inputNode
        context.getRecord(inputNode).setValue("fj_fk",
                vo.getReturnId());
        context.getRecord(inputNode).setValue("fjmc",
                vo.getReturnName());
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoCommonStandard common_standard = context.getCommonStandard( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ�������������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn603014( CommonStandardContext context ) throws TxnException
	{	
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoCommonStandardPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoCommonStandard result = context.getCommonStandard( outputNode );
		 String fjids = context.getRecord(outputNode).getValue(
	                VoCommonStandard.ITEM_FJ_FK);

	        //������ڵ��л�ȡ������ϵ�����
	        String filenames = context.getRecord(outputNode).getValue(
	        		VoCommonStandard.ITEM_FJMC);

	        //���ýӿڽ���ȡ���ļ���Ϣһһ����context
	        UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
	}
	
	/** ɾ������������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn603015( CommonStandardContext context ) throws TxnException
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
            		VoCommonStandard.ITEM_FJ_FK);
            UploadHelper.deleteFile(context, fjids, ConstUploadFileType.REPORT);

        }
		
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	
	/**
	 * 
	 * txn603111(Ϊ�˲�ѯ����ZWT��ͼ���λ��д����)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param context
	 * @throws TxnException        
	 * void       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public void txn603111( CommonStandardContext context ) throws TxnException
	{
//		System.out.println("txn603111Start==============");
//		
//		String sqlRight = "select nnm as COUNTNUM,interface_name as TITLE from ( select count(*) nnm , t3.interface_name " +
//		"from share_log t ,share_service t2,share_interface t3 where t.service_id=t2.service_id  and t2.interface_id=t3.interface_id " +
//		"and t.log_type='02' group by t3.interface_name order by nnm desc) where rownum<21";
//		
//		String sqlCenter1 = "select t3.*,t2.name as LEFTLINKNAME from  (select t.business_topics_id as id,  t.service_targets_id as p_id,  t.topics_name as name," +
//				"'topic' as type  from res_business_topics t, res_service_targets t2  where t.service_targets_id = t2.service_targets_id and " +
//				"t.business_topics_id in  (select distinct t3.business_topics_id from res_share_table t3)  order by t.show_order) t1, " +
//				" (  select t.service_targets_id as id,  '0' as p_id,   t.service_targets_name as name, 'object' as type from res_service_targets t " +
//				"where t.is_markup = 'Y' and is_share = 'Y'  and t.service_targets_id in (select distinct t2.service_targets_id  " +
//				"from res_business_topics t2 where t2.business_topics_id in (select distinct t3.business_topics_id from res_share_table t3)) " +
//				" order by show_order) t2, (select t.share_table_id as id, t.business_topics_id as p_id, t.table_name_cn as name, 'table' as type " +
//				"from res_share_table t, res_business_topics t2 where t.business_topics_id = t2.business_topics_id and t.table_name_cn is not null " +
//				"and t.share_table_id in ('AJGL002','AJGL005','AJGLT001','GJJT001','GJJT005','GJJT007','GTDJT001','GTDJT002','GTDJT003','NJYZT001'," +
//				"'QYDJT001','QYDJT002','QYDJT003','QYDJT004','QYDJT005','QYDJT006','QYDJT008','QYDJT009','WGGLJCT001','WGGLJCT002','WGGLJCT006') " +
//				"order by t2.show_order, t.show_order) t3 where     t2.id=t1.p_id and t1.id=t3.p_id  ";
//		System.out.println("sqlRight=============="+sqlRight);
//		System.out.println("sqlCenter1=============="+sqlCenter1);
//		ZwtJsonCreat zwt = new ZwtJsonCreat();
//		HashMap leftMap = new HashMap();
//		HashMap rightMap = new HashMap();
//		HashMap centerMap = new HashMap();
//		
//		try {
//		 	 List zwtInterface = zwt.getZwtLeft();
//			 leftMap = zwt.getLeftMap(zwtInterface);
//			 zwtInterface = zwt.getZwtBySql(sqlRight);
//			 rightMap = zwt.getRightMap(zwtInterface);
//			 zwtInterface = zwt.getZwtBySql(sqlCenter1);
//			 centerMap = zwt.getCenterMap(zwtInterface);
//		} catch (DBException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String resultString = zwt.getZwtJsonString(leftMap, rightMap, centerMap);
//		System.out.println(resultString);
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
		CommonStandardContext appContext = new CommonStandardContext( context );
		invoke( method, appContext );
	}
}
