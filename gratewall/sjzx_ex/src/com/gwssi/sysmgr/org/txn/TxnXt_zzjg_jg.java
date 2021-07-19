/*
 * @Header @Revision @Date 20070301
 * ===================================================== 北京审计项目组
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
 * @desc 系统组织机构机构维护后台处理类， 实现机构的新建、修改、删除、获取列表等操作
 * @author adaFang
 * @version 1.0
 */
public class TxnXt_zzjg_jg extends TxnService
{

	private Logger	log	= TxnLogger.getLogger(TxnXt_zzjg_jg.class.getName());

	// 数据表名称
	private static final String	TABLE_NAME	= "xt_zzjg_jg";

	// 查询树状列表
	private static final String	ROWSET_ORGTREE_FUNCTION	= "select orgtree list xt_zzjg_jg";

	// 查询单条机构记录
	private static final String	SELECT_ONEORG_FUNCTION	= "select one xt_zzjg_jg";

	// 修改单条机构记录
	private static final String	UPDATE_ONEORG_FUNCTION	= "update one xt_zzjg_jg";

	// 增加单条机构记录
	private static final String	INSERT_ONEORG_FUNCTION	= "insert one xt_zzjg_jg";

	// 注销机构记录
	private static final String	OFFUSE_ONEORG_FUNCTION	= "offuse one xt_zzjg_jg";

	// 查找父机构记录
	private static final String	SELECT_PARENT_FUNCTION	= "select parent xt_zzjg_jg";
	
	//上级机构
	private  String  ORG_NAME	= "";

	public TxnXt_zzjg_jg()
	{

	}

	/**
	 * 查询树状机构列表
	 * 
	 * @param context
	 *            交易上下文
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

			// 转换根机构的上级机构id值从0改为空值，满足页面展示需要
//			for (int i = 0; rs != null && (!rs.isEmpty()) && i < rs.size(); i++) {
//				DataBus temp = rs.get(i);
//				String sjjg = temp.getValue(VoXt_zzjg_jg.ITEM_SJJGID_FK);
//				if (Constants.ROOT_SJJG_ID.equals(sjjg)) {
//					temp.setValue(VoXt_zzjg_jg.ITEM_SJJGID_FK, "");
//				}
//			}

			// 设置选中值，满足页面选中机构标红显示
			DataBus db = new DataBus();
			db.setValue("selectedId", selectedid);
			context.addRecord("selectedNode", db);
	}

	/**
	 * 修改单条机构记录
	 * 
	 * @param context
	 *            交易上下文
	 * @throws cn.gwssi.common.component.exception.TxnException
	 */
	public void txn806002(TxnContext context) throws TxnException
	{
			// 设置默认根机构值
//			setRootSJJGToDefault(context);

			// 获取当前机构的根机构以及机构id值，用于校验根机构设置是否循环调用
			String orgid = context.getRecord(inputNode).getValue(
					VoXt_zzjg_jg.ITEM_JGID_PK);

//			String parentid = context.getRecord(inputNode).getValue(
//					VoXt_zzjg_jg.ITEM_SJJGID_FK);
			//机构name
			String jgname=context.getRecord(inputNode).getValue(VoXt_zzjg_jg.ITEM_JGMC);
			
			// 判断调整机构上下级位置时，是否包含下级机构
//			if (checkNotSubOrg(context, orgid, parentid)) {
				
				
			//加上上级机构判断方法
				BaseTable table = TableFactory.getInstance().getTableObject(
						this, TABLE_NAME);
				table.executeFunction(UPDATE_ONEORG_FUNCTION, context,
						inputNode, outputNode);
				// 更新用户的所属机构名称
				//把机构id，和机构名称放到databus
				DataBus updateJgname = new DataBus();
				updateJgname.setValue(VoXt_zzjg_jg.ITEM_JGMC, jgname);
				updateJgname.setValue(VoXt_zzjg_jg.ITEM_JGID_PK, orgid);
				context.addRecord("updateJgname", updateJgname);
				log.debug("	"+context);
				callService("com.gwssi.sysmgr.user.txn.TxnXt_zzjg_yh","updateJgname", context);
				setBizLog("修改机构：", context,context.getRecord("record").getValue("jgmc"));
//			} else {
//				// 存在下级机构时不做修改，抛出异常
//				/**
//				 * 临时把ErrorConstant.ACTION_TXN_INVALID改为TXN_OTHER_ERROR
//				 */
//				throw new TxnDataException("","设置的上级机构为当前机构的下属部门,无法调整机构位置!");
//			}
	}

	/**
	 * 增加单条机构记录
	 * 
	 * @param context
	 *            交易上下文
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
		setBizLog("增加机构：", context,context.getRecord("record").getValue("jgmc"));
	}


	/**
	 * 查询单条机构记录
	 * 
	 * @param context
	 *            交易上下文
	 * @throws cn.gwssi.common.component.exception.TxnException
	 */
	public void txn806004(TxnContext context) throws TxnException
	{
//		try {
			BaseTable table = TableFactory.getInstance().getTableObject(this,
					TABLE_NAME);
			table.executeFunction(SELECT_ONEORG_FUNCTION, context, inputNode,
					outputNode);

			// 转换根机构的上级机构id值从0改为空值，满足页面展示需要
//			setRootSJJGToNULL(context);

			DataBus db = new DataBus();
			db.setValue(VoXt_zzjg_jg.ITEM_SJJGID_FK, context.getRecord(
					inputNode).getValue(VoXt_zzjg_jg.ITEM_JGID_PK));
			String sjjgid_fk = context.getRecord(outputNode).getValue("sjjgid_fk");		
			if(sjjgid_fk==null||sjjgid_fk.equals("")){
				context.getRecord("record").setValue("sjjgname", "北京市工商局");
			}else{
				getJgmc(sjjgid_fk,ORG_NAME);
				context.getRecord("record").setValue("sjjgname", "北京市工商局" + getORG_NAME());				
			}
			context.setValue("select-key", db);
			// 设置左侧机构树刷新
			context.setValue("refresh", "true");

//		} catch (Exception e) {
//			CSDBException.throwCSDBException(e);
//		}
	}

	/**
	 * 删除机构记录
	 * 
	 * @param context
	 *            交易上下文
	 * @throws cn.gwssi.common.component.exception.TxnException
	 */
	public void txn806005(TxnContext context) throws TxnException
	{
//		try {
			String jgid = context.getRecord(inputNode).getValue(
					VoXt_zzjg_jg.ITEM_JGID_PK);
			
//			判断文献中是否引用了该条记录
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
//				throw new TxnDataException("","文献管理中引用了该机构，无法删除机构！");
//			}
			
			// 满足删除条件，更新机构状态
			if (isCanDelete(context, jgid)) {
				
				context.getRecord(inputNode).put(VoXt_zzjg_jg.ITEM_SFYX,
						Constants.status_offuse);
				BaseTable table = TableFactory.getInstance().getTableObject(
						this, TABLE_NAME);
				// 设置机构状态为停用
				table.executeFunction(OFFUSE_ONEORG_FUNCTION, context,
						inputNode, outputNode);
                //删除机构下用户
				table.executeFunction("deleteAllUser", context,
						inputNode, outputNode);				
				setBizLog("注销机构：", context,context.getRecord(outputNode).getValue("jgmc"));
			} else {
//				throw new CSDBException(
////						ErrorConstant.ACTION_TXN_INVALID,
//						ErrorConstant.TXN_OTHER_ERROR,
////						CSDBConfig.get("ggkz.jggl.delete.subexisterror"));
				throw new TxnDataException("","机构存在人员或下级机构，无法删除机构,请先删除所有下级机构及注销机构内的人员!");
			}

//		} catch (Exception e) {
//			CSDBException.throwCSDBException(e);
//		}
	}

	/**
	 * 查询单条机构记录不刷新页面
	 * 
	 * @param context
	 *            交易上下文
	 * @throws cn.gwssi.common.component.exception.TxnException
	 */
	public void txn806006(TxnContext context) throws TxnException
	{
//		try {
			DataBus db = new DataBus();
			db.setValue(VoXt_zzjg_jg.ITEM_JGID_PK, context.getRecord(inputNode)
					.getValue(VoXt_zzjg_jg.ITEM_JGID_PK));
			context.setValue("select-key", db);
			// 设置左侧机构树刷新
			context.setValue("refresh", "true");

//		} catch (Exception e) {
//			CSDBException.throwCSDBException(e);
//		}
	}
	/**
	 * 增加前查询上级机构
	 */
	public void txn806007(TxnContext context) throws TxnException{
		
		String jgid_pk = context.getRecord("record").getValue("jgid_pk");
		if(jgid_pk==null||jgid_pk.equals("")){
			context.getRecord("record").setValue("sjjgname", "北京市工商局");
		}else{

		}
	}
	/**
	 * 判断机构能否删除
	 * 删除条件：该机构没有下属机构并且没有用户
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
		// 判断输入的机构ID值是否为空
		if (jgid == null || jgid.length() <= 0)
//			throw new TxnErrorException(
////					ErrorConstant.SQL_DATA_INVALID,
//					ErrorConstant.TXN_OTHER_ERROR,
//					CSDBConfig.get("ggkz.jggl.select.jgidnullerror"));
		{
			throw new TxnDataException("","机构主键值为空!");
		}
		// 根据机构id配置sql语句查询机构下属机构
//		String sql1 = SQLConfig.get("806000-0001", jgid);
		String sql1 = "select xt_zzjg_jg.* from xt_zzjg_jg where sjjgid_fk='"+jgid+"' and sfyx='0'";
		// 根据机构id配置sql语句查询机构下所属人员
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
				// 空记录，无下属机构
				iscan = true;
			} else {
				throw ex;
			}
		}
		try {
			j = table.executeRowset(sql2, context, "result");
			// 无下级机构且机构下无人员
			if (iscan && j == 0) {
				iscan = true;
			} else {
				iscan = false;
			}
		} catch (TxnDataException ex) {

			if (ex.getErrCode().compareTo(
					"SQL000"
			) == 0) {
				// 在这里处理空记录异常
				iscan = iscan && true;
			} else {
				iscan = false;
				throw ex;
			}
		}

		return iscan;
	}

	/**
	 * 查询单条机构记录
	 * 
	 * @param context
	 *            交易上下文
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
		log.debug("机构主键值： " + jgid);

		// 根据主键值查询机构信息
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction(SELECT_PARENT_FUNCTION, context,
				GgkzConstants.ORG_INFO, GgkzConstants.ORG_INFO);

		// 根据上级机构id值循环处理，获取根机构信息
		do {
			table.executeFunction(SELECT_PARENT_FUNCTION, context,
					GgkzConstants.ROOT_ORG_INFO, GgkzConstants.ROOT_ORG_INFO);
			jgid = context.getRecord(GgkzConstants.ROOT_ORG_INFO).getValue(
					VoXt_zzjg_jg.ITEM_SJJGID_FK);
			// 当前机构不是根机构
			if (!Constants.ROOT_SJJG_ID.equals(jgid.trim())) {
				context.getRecord(GgkzConstants.ROOT_ORG_INFO).setValue(
						VoXt_zzjg_jg.ITEM_JGID_PK, jgid);
			}
		} while (!Constants.ROOT_SJJG_ID.equals(jgid.trim()));
	}

//	/**
//	 * 为满足工作流组织机构展示时，根机构父结点id为固定值 烽火台展示必须为空值，做的页面与数据库值转换
//	 * 根机构的上级机构id在页面展示为空值，在数据库存储为Constants维护的固定值。 本方法将页面的空值转换为固定值
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
//			log.debug("转换根机构: " + sjjg);
//		}
//	}
//
//	/**
//	 * 为满足工作流组织机构展示时，根机构父结点id为固定值 烽火台展示必须为空值，做的页面与数据库值转换 本方法将数据库的固定值转换为空值
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
	 * 检查设置的上级机构是否为当前机构的下属机构
	 * 
	 * @param context
	 *            烽火台数据结构
	 * @param orgId
	 *            当前机构id
	 * @param parentId
	 *            用户设置的上级机构id
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
		// 循环查询设置的上级机构的所有层级的上级机构，并与当前机构对比。根机构为""
		while (!Constants.ROOT_SJJG_ID.equals(org_tmp.trim())) {

			// 根据设置的父机构查询机构记录
			table.executeFunction(SELECT_PARENT_FUNCTION, context,
					GgkzConstants.ORG_INFO, GgkzConstants.ORG_INFO);
			org_tmp = context.getRecord(GgkzConstants.ORG_INFO).getValue(
					VoXt_zzjg_jg.ITEM_SJJGID_FK);
			// 上级机构不为根机构，且与当前机构相等，则返回false
			if ((!Constants.ROOT_ORG_ID.equals(org_tmp.trim()))
					&& (org_tmp.equals(orgId))) {
				notSub = false;
				break;
			}
			// 如果不为根机构继续往上找
			if (!Constants.ROOT_SJJG_ID.equals(org_tmp.trim())) {
				context.getRecord(GgkzConstants.ORG_INFO).setValue(
						VoXt_zzjg_jg.ITEM_JGID_PK, org_tmp);
			}
		}
		context.remove(GgkzConstants.ORG_INFO);
		return notSub;
	}

	/*
	 * 根据机构编号查询是否存在有效机构信息
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
			log.debug("ECODE_SQL_NOTFOUND的错误代码号："+ex.getErrCode());
			if (ex.getErrCode().compareTo(
					"SQL000"   //该版本中没有ECODE_SQL_NOTFOUND
			) == 0) {
				// 数据库无数据
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
	 * 获得上级机构名称
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
     * 登录时获得机构名称
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
//                // 数据库无数据
//                context.clear();
//            } else {
//                throw ex;
//            }
//        }
    	String jgid_pk = context.getRecord("primary-key").getValue("jgid_pk");
		getJgmc(jgid_pk,ORG_NAME);
		if(getORG_NAME()!=null&&!getORG_NAME().equals("")){
		     context.getRecord("record").setValue("jgmc", "北京市工商局" + getORG_NAME());    
		}else{
			context.getRecord("record").setValue("jgmc", "北京市工商局" + getORG_NAME());
		}
    }	
	/**
	 * 记录日志
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
