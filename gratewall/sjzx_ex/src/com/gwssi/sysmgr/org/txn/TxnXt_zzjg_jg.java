/*
 * @Header @Revision @Date 20070301
 * ===================================================== ���������Ŀ��
 * =====================================================
 */

package com.gwssi.sysmgr.org.txn;

import org.apache.log4j.Logger;

import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.component.logger.TxnLogger;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.common.util.Constants;
import com.gwssi.sysmgr.GgkzConstants;
import com.gwssi.sysmgr.org.vo.VoXt_zzjg_jg;
import com.gwssi.sysmgr.user.vo.VoXt_zzjg_yh;

/**
 * @desc ϵͳ��֯��������ά����̨�����࣬ ʵ�ֻ������½����޸ġ�ɾ������ȡ�б�Ȳ���
 * @author adaFang
 * @version 1.0
 */
public class TxnXt_zzjg_jg extends TxnService
{

	private Logger	log	= TxnLogger.getLogger(TxnXt_zzjg_jg.class.getName());

	// ���ݱ�����
	private static final String	TABLE_NAME	= "xt_zzjg_jg";

	// ��ѯ��״�б�
	private static final String	ROWSET_ORGTREE_FUNCTION	= "select orgtree list xt_zzjg_jg";

	// ��ѯ����������¼
	private static final String	SELECT_ONEORG_FUNCTION	= "select one xt_zzjg_jg";

	// �޸ĵ���������¼
	private static final String	UPDATE_ONEORG_FUNCTION	= "update one xt_zzjg_jg";

	// ���ӵ���������¼
	private static final String	INSERT_ONEORG_FUNCTION	= "insert one xt_zzjg_jg";

	// ע��������¼
	private static final String	OFFUSE_ONEORG_FUNCTION	= "offuse one xt_zzjg_jg";

	// ���Ҹ�������¼
	private static final String	SELECT_PARENT_FUNCTION	= "select parent xt_zzjg_jg";
	
	//�ϼ�����
	private  String  ORG_NAME	= "";

	public TxnXt_zzjg_jg()
	{

	}

	/**
	 * ��ѯ��״�����б�
	 * 
	 * @param context
	 *            ����������
	 * @throws cn.gwssi.common.component.exception.TxnException
	 */
	public void txn806001(TxnContext context) throws TxnException
	{
			String selectedid = context.getRecord(inputNode).getValue(
					"selectedid");

			BaseTable table = TableFactory.getInstance().getTableObject(this,
					TABLE_NAME);
			table.executeFunction(ROWSET_ORGTREE_FUNCTION, context, inputNode,
					outputNode);

//			Recordset rs = context.getRecordset(outputNode);

			// ת�����������ϼ�����idֵ��0��Ϊ��ֵ������ҳ��չʾ��Ҫ
//			for (int i = 0; rs != null && (!rs.isEmpty()) && i < rs.size(); i++) {
//				DataBus temp = rs.get(i);
//				String sjjg = temp.getValue(VoXt_zzjg_jg.ITEM_SJJGID_FK);
//				if (Constants.ROOT_SJJG_ID.equals(sjjg)) {
//					temp.setValue(VoXt_zzjg_jg.ITEM_SJJGID_FK, "");
//				}
//			}

			// ����ѡ��ֵ������ҳ��ѡ�л��������ʾ
			DataBus db = new DataBus();
			db.setValue("selectedId", selectedid);
			context.addRecord("selectedNode", db);
	}

	/**
	 * �޸ĵ���������¼
	 * 
	 * @param context
	 *            ����������
	 * @throws cn.gwssi.common.component.exception.TxnException
	 */
	public void txn806002(TxnContext context) throws TxnException
	{
			// ����Ĭ�ϸ�����ֵ
//			setRootSJJGToDefault(context);

			// ��ȡ��ǰ�����ĸ������Լ�����idֵ������У������������Ƿ�ѭ������
			String orgid = context.getRecord(inputNode).getValue(
					VoXt_zzjg_jg.ITEM_JGID_PK);

//			String parentid = context.getRecord(inputNode).getValue(
//					VoXt_zzjg_jg.ITEM_SJJGID_FK);
			//����name
			String jgname=context.getRecord(inputNode).getValue(VoXt_zzjg_jg.ITEM_JGMC);
			
			// �жϵ����������¼�λ��ʱ���Ƿ�����¼�����
//			if (checkNotSubOrg(context, orgid, parentid)) {
				
				
			//�����ϼ������жϷ���
				BaseTable table = TableFactory.getInstance().getTableObject(
						this, TABLE_NAME);
				table.executeFunction(UPDATE_ONEORG_FUNCTION, context,
						inputNode, outputNode);
				// �����û���������������
				//�ѻ���id���ͻ������Ʒŵ�databus
				DataBus updateJgname = new DataBus();
				updateJgname.setValue(VoXt_zzjg_jg.ITEM_JGMC, jgname);
				updateJgname.setValue(VoXt_zzjg_jg.ITEM_JGID_PK, orgid);
				context.addRecord("updateJgname", updateJgname);
				log.debug("	"+context);
				callService("com.gwssi.sysmgr.user.txn.TxnXt_zzjg_yh","updateJgname", context);
				setBizLog("�޸Ļ�����", context,context.getRecord("record").getValue("jgmc"));
//			} else {
//				// �����¼�����ʱ�����޸ģ��׳��쳣
//				/**
//				 * ��ʱ��ErrorConstant.ACTION_TXN_INVALID��ΪTXN_OTHER_ERROR
//				 */
//				throw new TxnDataException("","���õ��ϼ�����Ϊ��ǰ��������������,�޷���������λ��!");
//			}
	}

	/**
	 * ���ӵ���������¼
	 * 
	 * @param context
	 *            ����������
	 * @throws cn.gwssi.common.component.exception.TxnException
	 */
	public void txn806003(TxnContext context) throws TxnException{

		BaseTable table = TableFactory.getInstance().getTableObject(
				this, TABLE_NAME);
		table.executeFunction(INSERT_ONEORG_FUNCTION, context,
				inputNode, outputNode);
		DataBus db = new DataBus();
		db.setValue(VoXt_zzjg_jg.ITEM_JGID_PK, context.getRecord(
				inputNode).getValue(VoXt_zzjg_jg.ITEM_JGID_PK));
		context.setValue(outputNode, db);
		setBizLog("���ӻ�����", context,context.getRecord("record").getValue("jgmc"));
	}


	/**
	 * ��ѯ����������¼
	 * 
	 * @param context
	 *            ����������
	 * @throws cn.gwssi.common.component.exception.TxnException
	 */
	public void txn806004(TxnContext context) throws TxnException
	{
//		try {
			BaseTable table = TableFactory.getInstance().getTableObject(this,
					TABLE_NAME);
			table.executeFunction(SELECT_ONEORG_FUNCTION, context, inputNode,
					outputNode);

			// ת�����������ϼ�����idֵ��0��Ϊ��ֵ������ҳ��չʾ��Ҫ
//			setRootSJJGToNULL(context);

			DataBus db = new DataBus();
			db.setValue(VoXt_zzjg_jg.ITEM_SJJGID_FK, context.getRecord(
					inputNode).getValue(VoXt_zzjg_jg.ITEM_JGID_PK));
			String sjjgid_fk = context.getRecord(outputNode).getValue("sjjgid_fk");		
			if(sjjgid_fk==null||sjjgid_fk.equals("")){
				context.getRecord("record").setValue("sjjgname", "�����й��̾�");
			}else{
				getJgmc(sjjgid_fk,ORG_NAME);
				context.getRecord("record").setValue("sjjgname", "�����й��̾�" + getORG_NAME());				
			}
			context.setValue("select-key", db);
			// ������������ˢ��
			context.setValue("refresh", "true");

//		} catch (Exception e) {
//			CSDBException.throwCSDBException(e);
//		}
	}

	/**
	 * ɾ��������¼
	 * 
	 * @param context
	 *            ����������
	 * @throws cn.gwssi.common.component.exception.TxnException
	 */
	public void txn806005(TxnContext context) throws TxnException
	{
//		try {
			String jgid = context.getRecord(inputNode).getValue(
					VoXt_zzjg_jg.ITEM_JGID_PK);
			
//			�ж��������Ƿ������˸�����¼
//			int i=0;
//			BaseTable table1 = TableFactory.getInstance().getTableObject(this,"WJG_WX");
//			String sql = "select count(0) as countjgid from WJG_WX where DM_BM='"+jgid+"'";
//			log.debug("sql:"+sql);
//			table1.executeRowset(sql, context, "result");
			DataBus db_wx = context.getRecord("result");
//			String countJGID = db_wx.getValue("countjgid");
//			
//			log.debug("countJGID:"+countJGID);
//			if(!countJGID.equals("0")){
//				throw new TxnDataException("","���׹����������˸û������޷�ɾ��������");
//			}
			
			// ����ɾ�����������»���״̬
			if (isCanDelete(context, jgid)) {
				
				context.getRecord(inputNode).put(VoXt_zzjg_jg.ITEM_SFYX,
						Constants.status_offuse);
				BaseTable table = TableFactory.getInstance().getTableObject(
						this, TABLE_NAME);
				// ���û���״̬Ϊͣ��
				table.executeFunction(OFFUSE_ONEORG_FUNCTION, context,
						inputNode, outputNode);
                //ɾ���������û�
				table.executeFunction("deleteAllUser", context,
						inputNode, outputNode);				
				setBizLog("ע��������", context,context.getRecord(outputNode).getValue("jgmc"));
			} else {
//				throw new CSDBException(
////						ErrorConstant.ACTION_TXN_INVALID,
//						ErrorConstant.TXN_OTHER_ERROR,
////						CSDBConfig.get("ggkz.jggl.delete.subexisterror"));
				throw new TxnDataException("","����������Ա���¼��������޷�ɾ������,����ɾ�������¼�������ע�������ڵ���Ա!");
			}

//		} catch (Exception e) {
//			CSDBException.throwCSDBException(e);
//		}
	}

	/**
	 * ��ѯ����������¼��ˢ��ҳ��
	 * 
	 * @param context
	 *            ����������
	 * @throws cn.gwssi.common.component.exception.TxnException
	 */
	public void txn806006(TxnContext context) throws TxnException
	{
//		try {
			DataBus db = new DataBus();
			db.setValue(VoXt_zzjg_jg.ITEM_JGID_PK, context.getRecord(inputNode)
					.getValue(VoXt_zzjg_jg.ITEM_JGID_PK));
			context.setValue("select-key", db);
			// ������������ˢ��
			context.setValue("refresh", "true");

//		} catch (Exception e) {
//			CSDBException.throwCSDBException(e);
//		}
	}
	/**
	 * ����ǰ��ѯ�ϼ�����
	 */
	public void txn806007(TxnContext context) throws TxnException{
		
		String jgid_pk = context.getRecord("record").getValue("jgid_pk");
		if(jgid_pk==null||jgid_pk.equals("")){
			context.getRecord("record").setValue("sjjgname", "�����й��̾�");
		}else{

		}
	}
	/**
	 * �жϻ����ܷ�ɾ��
	 * ɾ���������û���û��������������û���û�
	 * @param context
	 * @param jgid
	 * @return
	 * @throws TxnException
	 */
	private boolean isCanDelete(TxnContext context, String jgid)
			throws TxnException
	{
		boolean iscan = false;
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// �ж�����Ļ���IDֵ�Ƿ�Ϊ��
		if (jgid == null || jgid.length() <= 0)
//			throw new TxnErrorException(
////					ErrorConstant.SQL_DATA_INVALID,
//					ErrorConstant.TXN_OTHER_ERROR,
//					CSDBConfig.get("ggkz.jggl.select.jgidnullerror"));
		{
			throw new TxnDataException("","��������ֵΪ��!");
		}
		// ���ݻ���id����sql����ѯ������������
//		String sql1 = SQLConfig.get("806000-0001", jgid);
		String sql1 = "select xt_zzjg_jg.* from xt_zzjg_jg where sjjgid_fk='"+jgid+"' and sfyx='0'";
		// ���ݻ���id����sql����ѯ������������Ա
//		String sql2 = SQLConfig.get("806000-0002", jgid);
		String sql2="select xt_zzjg_yh_new.* from xt_zzjg_yh_new where jgid_fk='"+jgid+"' and sfyx='0'";
		int i, j;
		try {
			i = table.executeRowset(sql1, context, "result");
			if (i == 0) {
				iscan = true;
			}
		} catch (TxnDataException ex) {
			if (ex.getErrCode().compareTo(
					"SQL000"
			) == 0) {
				// �ռ�¼������������
				iscan = true;
			} else {
				throw ex;
			}
		}
		try {
			j = table.executeRowset(sql2, context, "result");
			// ���¼������һ���������Ա
			if (iscan && j == 0) {
				iscan = true;
			} else {
				iscan = false;
			}
		} catch (TxnDataException ex) {

			if (ex.getErrCode().compareTo(
					"SQL000"
			) == 0) {
				// �����ﴦ��ռ�¼�쳣
				iscan = iscan && true;
			} else {
				iscan = false;
				throw ex;
			}
		}

		return iscan;
	}

	/**
	 * ��ѯ����������¼
	 * 
	 * @param context
	 *            ����������
	 * @throws cn.gwssi.common.component.exception.TxnException
	 */
	public void txn806099(TxnContext context, String jgid) throws TxnException
	{
		VoXt_zzjg_jg jgVO = new VoXt_zzjg_jg();
		jgVO.setJgid_pk(jgid);
		VoXt_zzjg_jg rootVO = new VoXt_zzjg_jg();
		rootVO.setJgid_pk(jgid);
		context.setValue(GgkzConstants.ORG_INFO, jgVO);
		context.setValue(GgkzConstants.ROOT_ORG_INFO, rootVO);
		log.debug("��������ֵ�� " + jgid);

		// ��������ֵ��ѯ������Ϣ
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction(SELECT_PARENT_FUNCTION, context,
				GgkzConstants.ORG_INFO, GgkzConstants.ORG_INFO);

		// �����ϼ�����idֵѭ��������ȡ��������Ϣ
		do {
			table.executeFunction(SELECT_PARENT_FUNCTION, context,
					GgkzConstants.ROOT_ORG_INFO, GgkzConstants.ROOT_ORG_INFO);
			jgid = context.getRecord(GgkzConstants.ROOT_ORG_INFO).getValue(
					VoXt_zzjg_jg.ITEM_SJJGID_FK);
			// ��ǰ�������Ǹ�����
			if (!Constants.ROOT_SJJG_ID.equals(jgid.trim())) {
				context.getRecord(GgkzConstants.ROOT_ORG_INFO).setValue(
						VoXt_zzjg_jg.ITEM_JGID_PK, jgid);
			}
		} while (!Constants.ROOT_SJJG_ID.equals(jgid.trim()));
	}

//	/**
//	 * Ϊ���㹤������֯����չʾʱ�������������idΪ�̶�ֵ ���̨չʾ����Ϊ��ֵ������ҳ�������ݿ�ֵת��
//	 * ���������ϼ�����id��ҳ��չʾΪ��ֵ�������ݿ�洢ΪConstantsά���Ĺ̶�ֵ�� ��������ҳ��Ŀ�ֵת��Ϊ�̶�ֵ
//	 * 
//	 * @param context
//	 */
//	private void setRootSJJGToDefault(TxnContext context)
//	{
//
//		String sjjg = context.getRecord(inputNode).getValue(
//				VoXt_zzjg_jg.ITEM_SJJGID_FK);
//
//		if (sjjg == null || "".equals(sjjg.trim())) {
//			context.getRecord(inputNode).setValue(VoXt_zzjg_jg.ITEM_SJJGID_FK,
//					Constants.ROOT_SJJG_ID);
//			log.debug("ת��������: " + sjjg);
//		}
//	}
//
//	/**
//	 * Ϊ���㹤������֯����չʾʱ�������������idΪ�̶�ֵ ���̨չʾ����Ϊ��ֵ������ҳ�������ݿ�ֵת�� �����������ݿ�Ĺ̶�ֵת��Ϊ��ֵ
//	 * 
//	 * @param context
//	 */
//	private void setRootSJJGToNULL(TxnContext context)
//	{
//
//		String sjjg = context.getRecord(outputNode).getValue(
//				VoXt_zzjg_jg.ITEM_SJJGID_FK);
//		if (Constants.ROOT_SJJG_ID.equals(sjjg)) {
//			context.getRecord(outputNode).setValue(VoXt_zzjg_jg.ITEM_SJJGID_FK,
//					"");
//		}
//	}

	/**
	 * ������õ��ϼ������Ƿ�Ϊ��ǰ��������������
	 * 
	 * @param context
	 *            ���̨���ݽṹ
	 * @param orgId
	 *            ��ǰ����id
	 * @param parentId
	 *            �û����õ��ϼ�����id
	 * @return
	 * @throws TxnException
	 */
	private boolean checkNotSubOrg(TxnContext context, String orgId,
			String parentId) throws TxnException
	{
		boolean notSub = true;
		String org_tmp = parentId;
		VoXt_zzjg_jg jgVO = new VoXt_zzjg_jg();
		jgVO.setJgid_pk(parentId);

		context.setValue(GgkzConstants.ORG_INFO, jgVO);
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ѭ����ѯ���õ��ϼ����������в㼶���ϼ����������뵱ǰ�����Աȡ�������Ϊ""
		while (!Constants.ROOT_SJJG_ID.equals(org_tmp.trim())) {

			// �������õĸ�������ѯ������¼
			table.executeFunction(SELECT_PARENT_FUNCTION, context,
					GgkzConstants.ORG_INFO, GgkzConstants.ORG_INFO);
			org_tmp = context.getRecord(GgkzConstants.ORG_INFO).getValue(
					VoXt_zzjg_jg.ITEM_SJJGID_FK);
			// �ϼ�������Ϊ�����������뵱ǰ������ȣ��򷵻�false
			if ((!Constants.ROOT_ORG_ID.equals(org_tmp.trim()))
					&& (org_tmp.equals(orgId))) {
				notSub = false;
				break;
			}
			// �����Ϊ����������������
			if (!Constants.ROOT_SJJG_ID.equals(org_tmp.trim())) {
				context.getRecord(GgkzConstants.ORG_INFO).setValue(
						VoXt_zzjg_jg.ITEM_JGID_PK, org_tmp);
			}
		}
		context.remove(GgkzConstants.ORG_INFO);
		return notSub;
	}

	/*
	 * ���ݻ�����Ų�ѯ�Ƿ������Ч������Ϣ
	 */
	private boolean isJgbhExist(TxnContext context, String jgbh)
			throws TxnException
	{
		boolean result = true;

//		String sql = SQLConfig.get("806000-0003", jgbh);
		String sql="select jgid_pk from xt_zzjg_jg where jgbh ='"+jgbh+"' and sfyx='0'";
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		try {
			int num = table.executeRowset(sql, context, "jg");
			if (num < 1)
				result = false;
		} catch (TxnDataException ex) {
			log.debug("ECODE_SQL_NOTFOUND�Ĵ������ţ�"+ex.getErrCode());
			if (ex.getErrCode().compareTo(
					"SQL000"   //�ð汾��û��ECODE_SQL_NOTFOUND
			) == 0) {
				// ���ݿ�������
				result = false;
			} else {
				throw ex;
			}
		}
		return result;
	}

	protected void prepare(TxnContext arg0) throws TxnException
	{
		// TODO Auto-generated method stub
		
	}
	/**
	 * ����ϼ���������
	 * @param jgid_pk
	 * @return
	 */
	private void getJgmc (String jgid_pk,String jgName)throws TxnException{
		
		TxnContext context = new TxnContext();
		context.getRecord("record").setValue("jgid_pk", jgid_pk);
		context.getRecord("record").setValue("sjjgname", jgName);
		BaseTable table = TableFactory.getInstance().getTableObject(this,TABLE_NAME);
		table.executeFunction("getSjjgName", context, "record","record");		
		String sjjgid_fk = context.getRecord("record").getValue("sjjgid_fk");
		String jgmc = context.getRecord("record").getValue("jgmc");	
		if(sjjgid_fk==null||sjjgid_fk.equals("")){
			setORG_NAME(jgmc+jgName);
		}else{
			jgmc+=getORG_NAME();
			setORG_NAME(jgmc);
			this.getJgmc(sjjgid_fk,jgmc);
		}
	}
	
    /**
     * ��¼ʱ��û�������
     */
    public void findJgnameByLogin(TxnContext context) throws TxnException {

//        try {
//    		BaseTable table = TableFactory.getInstance().getTableObject(this,
//    				TABLE_NAME);
//    		table.executeFunction(SELECT_ONEORG_FUNCTION, context,
//    				"primary-key", "record");        	
//        } catch (TxnDataException ex) {
//            if (ex.getErrCode().compareTo(
//            		"SQL000"
//            ) == 0) {
//                // ���ݿ�������
//                context.clear();
//            } else {
//                throw ex;
//            }
//        }
    	String jgid_pk = context.getRecord("primary-key").getValue("jgid_pk");
		getJgmc(jgid_pk,ORG_NAME);
		if(getORG_NAME()!=null&&!getORG_NAME().equals("")){
		     context.getRecord("record").setValue("jgmc", "�����й��̾�" + getORG_NAME());    
		}else{
			context.getRecord("record").setValue("jgmc", "�����й��̾�" + getORG_NAME());
		}
    }	
	/**
	 * ��¼��־
	 * @param type
	 * @param context
	 */
    private void setBizLog (String type,TxnContext context,String jgmc){
    	
    	context.getRecord("biz_log").setValue("desc", type + jgmc);
    }	
	public String getORG_NAME()
	{
		return ORG_NAME;
	}

	public void setORG_NAME(String org_name)
	{
		ORG_NAME = org_name;
	}
	
}
