package cn.gwssi.dw.aic.bj.ecomm.txn;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;
import cn.gwssi.dw.aic.bj.ecomm.vo.EbEntWebSiteInfoContext;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.database.DBOperation;
import com.gwssi.common.database.DBOperationFactory;

public class TxnEbEntWebSiteInfo extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap		txnMethods		= getAllMethod(
														TxnEbEntWebSiteInfo.class,
														EbEntWebSiteInfoContext.class);

	// ���ݱ�����
	private static final String	TABLE_NAME		= "eb_ent_web_site_info";

	// ��ѯ�б�
	private static final String	ROWSET_FUNCTION	= "select eb_ent_web_site_info list";

	// ��ѯ��¼
	private static final String	SELECT_FUNCTION	= "select one eb_ent_web_site_info";

	// �޸ļ�¼
	private static final String	UPDATE_FUNCTION	= "update one eb_ent_web_site_info";

	// ���Ӽ�¼
	private static final String	INSERT_FUNCTION	= "insert one eb_ent_web_site_info";

	// ɾ����¼
	private static final String	DELETE_FUNCTION	= "delete one eb_ent_web_site_info";

	private static Map			CLASS_WZLX_MAP	= new HashMap();						// ��վ���ʹ��뼯

	private static Map			CLASS_JYMS_MAP	= new HashMap();						// ��Ӫģʽ

	private static Map			CLASS_ZYNR_MAP	= new HashMap();						// �������񻥶�����Ҫ����

	private static Map			CLASS_ZYJF_MAP	= new HashMap();						// ��Ҫ����

	static {
		DBOperation operation = DBOperationFactory.createTimeOutOperation();
		try {
			List codeList = operation
					.select("select f.jcsjfx_dm, f.jcsjfx_mc from gz_dm_jcdm_fx f, gz_dm_jcdm t where t.jc_dm_dm='DFEB06' and f.jc_dm_id=t.jc_dm_id");
			for (int j = 0; j < codeList.size(); j++) {
				Map m = (Map) codeList.get(j);
				CLASS_WZLX_MAP.put(m.get("JCSJFX_DM"), m.get("JCSJFX_MC"));
			}

			// codeList = operation.select("select f.jcsjfx_dm, f.jcsjfx_mc from
			// gz_dm_jcdm_fx f, gz_dm_jcdm t where t.jc_dm_dm='DFEB03' and
			// f.jc_dm_id=t.jc_dm_id");
			codeList = operation
					.select("select codevalue,codename from codedata  where codetype ='��������Ӫģʽ'");
			for (int j = 0; j < codeList.size(); j++) {
				Map m = (Map) codeList.get(j);
				CLASS_JYMS_MAP.put(m.get("CODEVALUE"), m.get("CODENAME"));
			}

			codeList = operation
					.select("select f.jcsjfx_dm, f.jcsjfx_mc from gz_dm_jcdm_fx f, gz_dm_jcdm t where t.jc_dm_dm='DFEB10' and f.jc_dm_id=t.jc_dm_id");
			for (int j = 0; j < codeList.size(); j++) {
				Map m = (Map) codeList.get(j);
				CLASS_ZYNR_MAP.put(m.get("JCSJFX_DM"), m.get("JCSJFX_MC"));
			}

			codeList = operation
					.select("select f.jcsjfx_dm, f.jcsjfx_mc from gz_dm_jcdm_fx f, gz_dm_jcdm t where t.jc_dm_dm='DFEB09' and f.jc_dm_id=t.jc_dm_id");
			for (int j = 0; j < codeList.size(); j++) {
				Map m = (Map) codeList.get(j);
				CLASS_ZYJF_MAP.put(m.get("JCSJFX_DM"), m.get("JCSJFX_MC"));
			}
		} catch (DBException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���캯��
	 */
	public TxnEbEntWebSiteInfo()
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
	 * ��ѯ���������б�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn6026001(EbEntWebSiteInfoContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoEbEntWebSiteInfoSelectKey selectKey = context.getSelectKey(
		// inputNode );
		table.executeFunction(ROWSET_FUNCTION, context, inputNode, outputNode);
		// ��ѯ���ļ�¼�� VoEbEntWebSiteInfo result[] = context.getEbEntWebSiteInfos(
		// outputNode );
	}

	/**
	 * �޸ĵ���������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn6026002(EbEntWebSiteInfoContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// �޸ļ�¼������ VoEbEntWebSiteInfo eb_ent_web_site_info =
		// context.getEbEntWebSiteInfo( inputNode );
		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * ���ӵ���������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn6026003(EbEntWebSiteInfoContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ���Ӽ�¼������ VoEbEntWebSiteInfo eb_ent_web_site_info =
		// context.getEbEntWebSiteInfo( inputNode );
		table.executeFunction(INSERT_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * ��ѯ�������������޸�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn6026004(EbEntWebSiteInfoContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoEbEntWebSiteInfoPrimaryKey primaryKey =
		// context.getPrimaryKey( inputNode );
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		// ��ѯ���ļ�¼���� VoEbEntWebSiteInfo result = context.getEbEntWebSiteInfo(
		// outputNode );
	}

	/**
	 * ɾ������������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn6026005(EbEntWebSiteInfoContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ɾ����¼�������б� VoEbEntWebSiteInfoPrimaryKey primaryKey[] =
		// context.getPrimaryKeys( inputNode );
		table.executeFunction(DELETE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * ��ѯ���������б�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn6026006(EbEntWebSiteInfoContext context) throws TxnException
	{

		// ��ѯ��¼������ VoEbEntWebSiteInfoSelectKey selectKey = context.getSelectKey(
		// inputNode );
		DataBus db = context.getRecord("select-key");
		TxnContext context1 = new TxnContext();
		context1.addRecord("select-key", db);
		this.callService("6026106", context1);

		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("queryWebSite", context, inputNode, outputNode);

		context.addRecord("record1", context1.getRecordset("record"));
	}

	// ��ת����վ��Ϣ���ҳ��
	public void txn6026007(EbEntWebSiteInfoContext context) throws TxnException
	{
		// System.out.println(">>>>>txn60121205");
	}

	/**
	 * ��վ�ж���Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn6026008(EbEntWebSiteInfoContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoEbEntWebSiteInfoSelectKey selectKey = context.getSelectKey(
		// inputNode );
		table.executeFunction("queryWebJudgeInfo", context, inputNode,
				outputNode);
		setCodeDesc(context);
		// ��ѯ���ļ�¼�� VoEbEntWebSiteInfo result[] = context.getEbEntWebSiteInfos(
		// outputNode );
	}

	private void setCodeDesc(EbEntWebSiteInfoContext context)
	{

		try {
			Recordset rs = context.getRecordset(outputNode);
			while (rs.hasNext()) {
				DataBus db = (DataBus) rs.next();
				String webTypes = db.getValue("web_type");// ��վ����
				System.out.println("webTypes=" + webTypes);
				if (webTypes != null && !webTypes.trim().equals("")) {
					db.setValue("web_type", getStr(webTypes, CLASS_WZLX_MAP));
				}

				String bussModes = db.getValue("buss_mode");// ��Ӫģʽ
				if (bussModes != null && !bussModes.trim().equals("")) {
					db.setValue("buss_mode", getStr(bussModes, CLASS_JYMS_MAP));
				}

				String webContents = db.getValue("web_content");// ��Ҫ����
				if (webContents != null && !webContents.trim().equals("")) {
					db.setValue("web_content", getStr(webContents,
							CLASS_ZYNR_MAP));
				}

				String custDisputes = db.getValue("cust_dispute");// ��Ҫ����
				if (custDisputes != null && !custDisputes.trim().equals("")) {
					db.setValue("cust_dispute", getStr(custDisputes,
							CLASS_ZYJF_MAP));
				}
			}
		} catch (TxnException e) {
			e.printStackTrace();
		}
	}

	private String getStr(String str, Map map)
	{
		StringBuffer content1 = new StringBuffer();
		String[] strs = str.split(",");
		for (int i = 0; i < strs.length; i++) {
			String tmp = null == map.get(strs[i].toUpperCase()) ? "" : map.get(
					strs[i].toUpperCase()).toString();
			if (i != (strs.length - 1)) {
				if (tmp != null && !tmp.equals("")) {
					tmp = tmp + ",";
				}

			}

			content1.append(tmp);
		}

		return content1.toString();
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
		EbEntWebSiteInfoContext appContext = new EbEntWebSiteInfoContext(
				context);
		invoke(method, appContext);
	}
}
