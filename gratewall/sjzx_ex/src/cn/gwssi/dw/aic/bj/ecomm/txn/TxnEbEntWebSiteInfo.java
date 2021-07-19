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
	 * 业务类提供的所有方法
	 */
	private static HashMap		txnMethods		= getAllMethod(
														TxnEbEntWebSiteInfo.class,
														EbEntWebSiteInfoContext.class);

	// 数据表名称
	private static final String	TABLE_NAME		= "eb_ent_web_site_info";

	// 查询列表
	private static final String	ROWSET_FUNCTION	= "select eb_ent_web_site_info list";

	// 查询记录
	private static final String	SELECT_FUNCTION	= "select one eb_ent_web_site_info";

	// 修改记录
	private static final String	UPDATE_FUNCTION	= "update one eb_ent_web_site_info";

	// 增加记录
	private static final String	INSERT_FUNCTION	= "insert one eb_ent_web_site_info";

	// 删除记录
	private static final String	DELETE_FUNCTION	= "delete one eb_ent_web_site_info";

	private static Map			CLASS_WZLX_MAP	= new HashMap();						// 网站类型代码集

	private static Map			CLASS_JYMS_MAP	= new HashMap();						// 经营模式

	private static Map			CLASS_ZYNR_MAP	= new HashMap();						// 电子商务互动的主要内容

	private static Map			CLASS_ZYJF_MAP	= new HashMap();						// 主要纠纷

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
					.select("select codevalue,codename from codedata  where codetype ='电子商务经营模式'");
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
	 * 构造函数
	 */
	public TxnEbEntWebSiteInfo()
	{

	}

	/**
	 * 初始化函数
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{

	}

	/**
	 * 查询电子商务列表
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn6026001(EbEntWebSiteInfoContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 查询记录的条件 VoEbEntWebSiteInfoSelectKey selectKey = context.getSelectKey(
		// inputNode );
		table.executeFunction(ROWSET_FUNCTION, context, inputNode, outputNode);
		// 查询到的记录集 VoEbEntWebSiteInfo result[] = context.getEbEntWebSiteInfos(
		// outputNode );
	}

	/**
	 * 修改电子商务信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn6026002(EbEntWebSiteInfoContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 修改记录的内容 VoEbEntWebSiteInfo eb_ent_web_site_info =
		// context.getEbEntWebSiteInfo( inputNode );
		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 增加电子商务信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn6026003(EbEntWebSiteInfoContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 增加记录的内容 VoEbEntWebSiteInfo eb_ent_web_site_info =
		// context.getEbEntWebSiteInfo( inputNode );
		table.executeFunction(INSERT_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 查询电子商务用于修改
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn6026004(EbEntWebSiteInfoContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 查询记录的主键 VoEbEntWebSiteInfoPrimaryKey primaryKey =
		// context.getPrimaryKey( inputNode );
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		// 查询到的记录内容 VoEbEntWebSiteInfo result = context.getEbEntWebSiteInfo(
		// outputNode );
	}

	/**
	 * 删除电子商务信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn6026005(EbEntWebSiteInfoContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 删除记录的主键列表 VoEbEntWebSiteInfoPrimaryKey primaryKey[] =
		// context.getPrimaryKeys( inputNode );
		table.executeFunction(DELETE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 查询电子商务列表
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn6026006(EbEntWebSiteInfoContext context) throws TxnException
	{

		// 查询记录的条件 VoEbEntWebSiteInfoSelectKey selectKey = context.getSelectKey(
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

	// 跳转到网站信息框架页面
	public void txn6026007(EbEntWebSiteInfoContext context) throws TxnException
	{
		// System.out.println(">>>>>txn60121205");
	}

	/**
	 * 网站判定信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn6026008(EbEntWebSiteInfoContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 查询记录的条件 VoEbEntWebSiteInfoSelectKey selectKey = context.getSelectKey(
		// inputNode );
		table.executeFunction("queryWebJudgeInfo", context, inputNode,
				outputNode);
		setCodeDesc(context);
		// 查询到的记录集 VoEbEntWebSiteInfo result[] = context.getEbEntWebSiteInfos(
		// outputNode );
	}

	private void setCodeDesc(EbEntWebSiteInfoContext context)
	{

		try {
			Recordset rs = context.getRecordset(outputNode);
			while (rs.hasNext()) {
				DataBus db = (DataBus) rs.next();
				String webTypes = db.getValue("web_type");// 网站类型
				System.out.println("webTypes=" + webTypes);
				if (webTypes != null && !webTypes.trim().equals("")) {
					db.setValue("web_type", getStr(webTypes, CLASS_WZLX_MAP));
				}

				String bussModes = db.getValue("buss_mode");// 经营模式
				if (bussModes != null && !bussModes.trim().equals("")) {
					db.setValue("buss_mode", getStr(bussModes, CLASS_JYMS_MAP));
				}

				String webContents = db.getValue("web_content");// 主要内容
				if (webContents != null && !webContents.trim().equals("")) {
					db.setValue("web_content", getStr(webContents,
							CLASS_ZYNR_MAP));
				}

				String custDisputes = db.getValue("cust_dispute");// 主要纠纷
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
	 * 重载父类的方法，用于替换交易接口的输入变量 调用函数
	 * 
	 * @param funcName
	 *            方法名称
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	protected void doService(String funcName, TxnContext context)
			throws TxnException
	{
		Method method = (Method) txnMethods.get(funcName);
		if (method == null) {
			funcName = this.getClass().getName() + "#" + funcName;
			throw new TxnErrorException(ErrorConstant.JAVA_METHOD_NOTFOUND,
					"没有找到交易码[" + txnCode + ":" + funcName + "]的处理函数");
		}

		// 执行
		EbEntWebSiteInfoContext appContext = new EbEntWebSiteInfoContext(
				context);
		invoke(method, appContext);
	}
}
