package com.gwssi.resource.svrobj.txn;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.dao.resource.PublicResource;
import cn.gwssi.common.dao.resource.code.CodeMap;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.common.constant.ConstUploadFileType;
import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.constant.FileConstant;
import com.gwssi.common.upload.UploadFileVO;
import com.gwssi.common.upload.UploadHelper;
import com.gwssi.common.util.JsonDataUtil;
import com.gwssi.resource.svrobj.vo.ResServiceTargetsContext;

/**
 * 
 * 
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�TxnResServiceTargets �������� �����ˣ�lvhao ����ʱ�䣺2013-3-15
 * ����03:22:39 �޸��ˣ�lvhao �޸�ʱ�䣺2013-3-15 ����03:22:39 �޸ı�ע��
 * 
 * @version
 * 
 */
public class TxnResServiceTargets extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap		txnMethods		= getAllMethod(
														TxnResServiceTargets.class,
														ResServiceTargetsContext.class);

	// ���ݱ�����
	private static final String	TABLE_NAME		= "res_service_targets";

	// ��ѯ�б�
	private static final String	ROWSET_FUNCTION	= "select res_service_targets list";

	// ��ѯ��¼
	private static final String	SELECT_FUNCTION	= "select one res_service_targets";

	// �޸ļ�¼
	private static final String	UPDATE_FUNCTION	= "update one res_service_targets";

	// ���Ӽ�¼
	private static final String	INSERT_FUNCTION	= "insert one res_service_targets";

	// ɾ����¼
	private static final String	DELETE_FUNCTION	= "delete one res_service_targets";

	/**
	 * ���캯��
	 */
	public TxnResServiceTargets()
	{

	}

	/**
	 * ��ʼ������
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{

	}

	/**
	 * 
	 * txn201001(��ѯ��������б�) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������ �C ��ѡ)
	 * TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public void txn201001(ResServiceTargetsContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ���������� json����
		ResServiceTargetsContext context1 = new ResServiceTargetsContext();
		Attribute.setPageRow(context1, outputNode, -1);
		table.executeFunction("getInfoBySvrObjType", context1, inputNode, outputNode);
		Recordset targetRs = context1.getRecordset("record");
		CodeMap codeMap = PublicResource.getCodeFactory();
		Recordset rs = codeMap.lookup(context1, "��Դ����_�����������");
		if (!rs.isEmpty()) {
			String[] keys = new String[rs.size()];
			String[] values = new String[rs.size()];
			for (int i = 0; i < rs.size(); i++) {
				DataBus db = rs.get(i);
				keys[i] = db.getValue("codename");
				values[i] = db.getValue("codevalue");
			}
			String groupValue = JsonDataUtil.getJsonGroupByRecordSet(targetRs,
					"service_targets_type", keys, values);
			context.setValue("svrTarget", groupValue);
		}
		Attribute.setPageRow(context, outputNode, 10);
		table.executeFunction("queryResServiceTargetsList", context, inputNode,
				outputNode);
	}

	/**
	 * 
	 * txn201012(��ѯ��������б�)
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public void txn201012(ResServiceTargetsContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ���������� json����
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction("getInfoBySvrObjType", context, inputNode,
				outputNode);
		Recordset targetRs = context.getRecordset("record");
		CodeMap codeMap = PublicResource.getCodeFactory();
		Recordset rs = codeMap.lookup(context, "��Դ����_�����������");
		if (!rs.isEmpty()) {
			String[] keys = new String[rs.size()];
			String[] values = new String[rs.size()];
			for (int i = 0; i < rs.size(); i++) {
				DataBus db = rs.get(i);
				keys[i] = db.getValue("codename");
				values[i] = db.getValue("codevalue");
			}
			String groupValue = JsonDataUtil.getJsonGroupByRecordSet(targetRs,
					"service_targets_type", keys, values);
			context.setValue("svrTarget", groupValue);
		}

	}

	/**
	 * 
	 * txn201002(�޸ķ������) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������ �C ��ѡ)
	 * TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public void txn201002(ResServiceTargetsContext context) throws TxnException
	{
		String userId = context.getRecord("oper-data").getValue("userID");
		context.getRecord("record").setValue("last_modify_id", userId);
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new java.util.Date());
		context.getRecord("record").setValue("last_modify_time", datetime);
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// �޸ļ�¼������ VoResServiceTargets res_service_targets =
		// context.getResServiceTargets( inputNode );
		
		// ��������
		// ��ȡҳ���ϵĻ�����Ϻͱ�ɾ���Ļ�����ϼ����Ե�ID
		String delIDs = context.getRecord(inputNode).getValue("delIDs");
		String delNAMEs = context.getRecord(inputNode).getValue("delNAMEs");
		String hyclid = context.getRecord(inputNode).getValue("fj_fk");
		String hycl = context.getRecord(inputNode).getValue("fjmc");
		//System.out.println("Context=====\n" + context);
		// ����һ��UploadFileVO���󣬱�������������͵Ķ฽��
		UploadFileVO fileVO = new UploadFileVO();
		fileVO.setRecordName("record:fjmc");
		fileVO.setDeleteId(delIDs);// ҳ�汣��ı�ɾ������Idֵ
		fileVO.setDeleteName(delNAMEs);// ҳ�汣��ı�ɾ������nameֵ
		fileVO.setOriginId(hyclid);// ���¸���ǰҵ�����ݿ����id�ֶδ洢��ֵ
		fileVO.setOriginName(hycl);// ���¸���ǰҵ�����ݿ����name�ֶδ洢��ֵ
		fileVO.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);// �฽��
		UploadFileVO vo = UploadHelper.updateFile(context, fileVO,
				ConstUploadFileType.SERVICE_TARGET_PATH);// 

		// ��������Ϣ���ݵ�inputNode
		context.getRecord(inputNode).setValue("fj_fk", vo.getReturnId());
		context.getRecord(inputNode).setValue("fjmc", vo.getReturnName());
		
		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 
	 * txn201003(�����������) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������ �C ��ѡ)
	 * TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public void txn201003(ResServiceTargetsContext context) throws TxnException
	{
		String userId = context.getRecord("oper-data").getValue("userID");
		context.getRecord("record").setValue("creator_id", userId);
		context.getRecord("record").setValue("last_modify_id", userId);
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new java.util.Date());
		context.getRecord("record").setValue("created_time", datetime);
		context.getRecord("record").setValue("last_modify_time", datetime);

		// ��������
		UploadFileVO fileVO1 = new UploadFileVO();
		fileVO1.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);
		UploadFileVO vo = UploadHelper.saveFile(context, fileVO1,
				ConstUploadFileType.SERVICE_TARGET_PATH);
		// ��������Ϣ���ݵ�inputNode
		context.getRecord(inputNode).setValue("fj_fk", vo.getReturnId());
		context.getRecord(inputNode).setValue("fjmc", vo.getReturnName());
		
		ResServiceTargetsContext con = new ResServiceTargetsContext();
		DataBus selectKey = new DataBus();
		selectKey.put("service_targets_type", context.getRecord("record")
				.getValue("service_targets_type"));
		con.addRecord("select-key", selectKey);
		this.callService("201014", con);
		String show_order = con.getRecord("record").getValue("toporder");
		if (StringUtils.isBlank(show_order)) {
			context.getRecord("record").setValue("show_order", show_order);
		}
		// System.out.println("\n\n = = = " + con);
		/*
		 * ResServiceTargetsContext context2 = new ResServiceTargetsContext();
		 * context2
		 * .getRecord("select-key").setValue("service_targets_name",context
		 * .getRecord("record").getValue("service_targets_name"));
		 * context2.getRecord
		 * ("select-key").setValue("service_targets_no",context
		 * .getRecord("record").getValue("service_targets_no"));
		 * System.out.println(context2); callService("201007",context2);
		 * 
		 * System.out.println(context2.getRecord("record").getValue(
		 * "service_targets_name"));
		 * System.out.println(context2.getRecord("record"
		 * ).getValue("service_targets_no"));
		 * 
		 * System.out.println(context2);
		 */
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ���Ӽ�¼������ VoResServiceTargets res_service_targets =
		// context.getResServiceTargets( inputNode );
		table.executeFunction(INSERT_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 
	 * txn201004(��ѯ�����޸�) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������ �C ��ѡ)
	 * TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public void txn201004(ResServiceTargetsContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoResServiceTargetsPrimaryKey primaryKey =
		// context.getPrimaryKey( inputNode );
		
		
		//table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		
		
		String service_targets_id = context.getRecord("primary-key").getValue("service_targets_id");
		StringBuffer sql = new StringBuffer();
		
		
		sql.append("select t1.*,t1.created_time as cretime,t1.last_modify_time as modtime, yh1.yhxm as crename,yh2.yhxm as modname from res_service_targets t1,xt_zzjg_yh_new yh1,xt_zzjg_yh_new yh2");
		sql.append(" where  t1.creator_id = yh1.yhid_pk  and t1.last_modify_id = yh2.yhid_pk  and t1.service_targets_id = '");
		sql.append(service_targets_id);
		sql.append("'");
		table.executeRowset(sql.toString(), context, outputNode);
		// ��ѯ���ļ�¼���� VoResServiceTargets result = context.getResServiceTargets(
		// outputNode );
		// ������ڵ��л�ȡ������ID
		String fjids = context.getRecord(outputNode).getValue("fj_fk");

		// ������ڵ��л�ȡ����������
		String filenames = context.getRecord(outputNode).getValue("fjmc");

		// ���ýӿڽ���ȡ���ļ���Ϣһһ����context
		UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
	}

	/**
	 * 
	 * txn201005(ɾ���������) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������ �C ��ѡ)
	 * TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public void txn201005(ResServiceTargetsContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ɾ����¼�������б� VoResServiceTargetsPrimaryKey primaryKey[] =
		// context.getPrimaryKeys( inputNode );
		table.executeFunction(DELETE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 
	 * txn201006(ɾ��������Ϣ--���ñ�־λ����ɾ��) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������ �C
	 * ��ѡ) TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public void txn201006(ResServiceTargetsContext context) throws TxnException
	{
		// System.out.println("txn201006");

		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ɾ����¼�������б� VoResServiceTargetsPrimaryKey primaryKey[] =
		// context.getPrimaryKeys( inputNode );
		// table.executeFunction( DELETE_FUNCTION, context, inputNode,
		// outputNode );
		table.executeFunction("setIs_markToZero", context, inputNode,
				outputNode);
	}

	/**
	 * 
	 * txn201007(������������������Ƿ�����ʹ��) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������ �C
	 * ��ѡ) TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public void txn201007(ResServiceTargetsContext context) throws TxnException
	{
		// System.out.println("txn201007="+context);
		DataBus db = context.getRecord("select-key");
		String service_targets_name = db.getValue("service_targets_name");
		String service_targets_no = db.getValue("service_targets_no");
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String sql = "select count(*) as name_nums from res_service_targets t where t.is_markup='"
				+ ExConstant.IS_MARKUP_Y
				+ "' and t.service_targets_name='"
				+ service_targets_name + "'";
		// System.out.println(sql);
		table.executeRowset(sql, context, outputNode);

		sql = "select count(*) as code_nums from res_service_targets t where t.is_markup='"
				+ ExConstant.IS_MARKUP_Y
				+ "' and t.service_targets_no='"
				+ service_targets_no + "'";
		table.executeRowset(sql, context, outputNode);
		// System.out.println(sql);
		// System.out.println(context);
	}

	/**
	 * 
	 * txn201008(������Դ�鿴����������) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������ �C ��ѡ)
	 * TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public void txn201008(ResServiceTargetsContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		StringBuffer sql = new StringBuffer();
		sql.append("select t.business_topics_id as id , t.service_targets_id as p_id,t.topics_name as name,'topic' as type from res_business_topics t,res_service_targets t2 ");
		sql.append("where t.service_targets_id=t2.service_targets_id  and   t.business_topics_id in (select distinct  t3.business_topics_id from res_share_table t3) order by t.show_order ");
		// sql.append("union all ");
		// sql.append("select t.service_targets_id as id, '0' as p_id , t.service_targets_name as name from res_service_targets t where t.is_markup='Y'  order by show_order");

		table.executeRowset(sql.toString(), context, outputNode);
		sql.setLength(0);
		sql.append("select t.service_targets_id as id, '0' as p_id , t.service_targets_name as name,'object' as type from res_service_targets t where t.is_markup='Y' and is_share='Y'  and   t.service_targets_id in (select distinct  t2.service_targets_id from res_business_topics t2 where  t2.business_topics_id in (select distinct  t3.business_topics_id from res_share_table t3))  order by show_order");
		table.executeRowset(sql.toString(), context, outputNode);

		sql.setLength(0);
		sql.append("select t.share_table_id  as id,t.business_topics_id as p_id,t.table_name_cn as name,'table' as type from res_share_table t, res_business_topics t2 where t.business_topics_id=t2.business_topics_id");
		sql.append(" and t.table_name_cn is not null  order by t2.show_order,t.show_order");

		table.executeRowset(sql.toString(), context, outputNode);
		System.out.println(context);
	}

	/**
	 * 
	 * txn201009(�鿴��Ϣ) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������ �C ��ѡ)
	 * TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public void txn201009(ResServiceTargetsContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoResServiceTargetsPrimaryKey primaryKey =
		// context.getPrimaryKey( inputNode );
		//table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		
		
		String service_targets_id = context.getRecord("primary-key").getValue("service_targets_id");
		StringBuffer sql = new StringBuffer();
		
		
		sql.append("select t1.*,t1.created_time as cretime,t1.last_modify_time as modtime, yh1.yhxm as crename,yh2.yhxm as modname from res_service_targets t1,xt_zzjg_yh_new yh1,xt_zzjg_yh_new yh2");
		sql.append(" where  t1.creator_id = yh1.yhid_pk  and t1.last_modify_id = yh2.yhid_pk  and t1.service_targets_id = '");
		sql.append(service_targets_id);
		sql.append("'");
		table.executeRowset(sql.toString(), context, outputNode);
		// ��ѯ���ļ�¼���� VoResServiceTargets result = context.getResServiceTargets(
		// outputNode );
		// ������ڵ��л�ȡ������ID
		String fjids = context.getRecord(outputNode).getValue("fj_fk");

		// ������ڵ��л�ȡ����������
		String filenames = context.getRecord(outputNode).getValue("fjmc");

		// ���ýӿڽ���ȡ���ļ���Ϣһһ����context
		UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
		
	}

	/**
	 * 
	 * txn201010(�޸�����/ͣ��״̬) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������ �C ��ѡ)
	 * TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public void txn201010(ResServiceTargetsContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		System.out.println(context);

		for (int i = 0; i < context.getRecordset("primary-key").size(); i++) {
			String sql = "update res_service_targets t set t.service_status='";
			String service_targets_id = context.getRecordset("primary-key")
					.get(i).getValue("service_targets_id");
			String service_status = context.getRecordset("primary-key").get(i)
					.getValue("service_status");

			if (service_status.equals(ExConstant.SERVICE_STATE_Y)) {
				sql += ExConstant.SERVICE_STATE_N + "' ";
			} else {
				sql += ExConstant.SERVICE_STATE_Y + "' ";
			}
			sql += " where t.service_targets_id='" + service_targets_id + "'";
			table.executeUpdate(sql);
			System.out.println(sql);
		}
		
		callService("201001", context);

		// table.executeFunction( SELECT_FUNCTION, context, inputNode,
		// outputNode );
	}

	/**
	 * У���������Ƿ����� txn201001(������һ�仰�����������������) TODO(����������������������� �C ��ѡ)
	 * TODO(�����������������ִ������ �C ��ѡ) TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C
	 * ��ѡ)
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public void txn201011(ResServiceTargetsContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoResServiceTargetsSelectKey selectKey =
		// context.getSelectKey( inputNode );
		// table.executeFunction( ROWSET_FUNCTION, context, inputNode,
		// outputNode );
		table.executeFunction("queryServiceUse", context, inputNode, outputNode);
		// ��ѯ���ļ�¼�� VoResServiceTargets result[] = context.getResServiceTargetss(
		// outputNode );
	}

	/**
	 * ���ҷ��������������
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn201014(ResServiceTargetsContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoResServiceTargetsSelectKey selectKey =
		// context.getSelectKey( inputNode );
		// table.executeFunction( ROWSET_FUNCTION, context, inputNode,
		// outputNode );
		table.executeFunction("getTargetTopOrder", context, inputNode,
				outputNode);
		// ��ѯ���ļ�¼�� VoResServiceTargets result[] = context.getResServiceTargetss(
		// outputNode );
	}
	
	/**
	 * ��ȡ����������õĲɼ������Ӧ�Ĳɼ����б�
	 * @param context
	 * @throws TxnException
	 */
	public void txn201015(ResServiceTargetsContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction("getTargetCollectTable", context, inputNode,
				outputNode);
		// ��ѯ���ļ�¼�� VoResServiceTargets result[] = context.getResServiceTargetss(
		// outputNode );
	}
	
	/**
	 * ��ȡ��������������ͳ��
	 * @param context
	 * @throws TxnException
	 */
	public void txn201016(ResServiceTargetsContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction("getTargetStatistics", context, inputNode,
				outputNode);
		// ��ѯ���ļ�¼�� VoResServiceTargets result[] = context.getResServiceTargetss(
		// outputNode );
	}
	
	/**
	 * ���ظ���ķ����������滻���׽ӿڵ�������� ���ú���
	 * 
	 * @param funcName
	 *            ��������
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	protected void doService(String funcName, TxnContext context)
			throws TxnException
	{
		Method method = (Method) txnMethods.get(funcName);
		if (method == null) {
			funcName = this.getClass().getName() + "#" + funcName;
			throw new TxnErrorException(ErrorConstant.JAVA_METHOD_NOTFOUND,
					"û���ҵ�������[" + txnCode + ":" + funcName + "]�Ĵ�����");
		}

		// ִ��
		ResServiceTargetsContext appContext = new ResServiceTargetsContext(
				context);
		invoke(method, appContext);
	}
}
