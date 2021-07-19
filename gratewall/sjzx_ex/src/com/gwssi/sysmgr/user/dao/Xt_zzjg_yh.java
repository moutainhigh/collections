package com.gwssi.sysmgr.user.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.context.vo.VoUser;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

import com.gwssi.sysmgr.org.vo.VoXt_zzjg_jg;

public class Xt_zzjg_yh extends BaseTable
{
	public Xt_zzjg_yh()
	{

	}

	/**
	 * 注册用户自定义的SQL语句
	 */
	protected void register()
	{
		registerSQLFunction("updateJgname", DaoFunction.SQL_UPDATE,
				"更新用户所属机构名称");
		registerSQLFunction("queryYhList", DaoFunction.SQL_ROWSET, "查询用户列表");
	}

	/**
	 * 执行SQL语句前的处理
	 */
	public void prepareExecuteStmt(DaoFunction func, TxnContext request,
			DataBus[] inputData, String outputNode) throws TxnException
	{

	}

	/**
	 * 执行完SQL语句后的处理
	 */
	public void afterExecuteStmt(DaoFunction func, TxnContext request,
			DataBus[] inputData, String outputNode) throws TxnException
	{

	}

	/**
	 * 
	 * 判断有没有指定权限
	 */
	public static boolean operHasRole(String roleList, String role)
	{
		if (roleList == null || roleList.equals("")) {
			return false;
		}
		String[] roleArray = roleList.split(";");
		for (int i = 0; i < roleArray.length; i++) {
			if (roleArray[i].equals(role)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 查询用户
	 * 
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement queryYhList(TxnContext request, DataBus inputData)
	{
		VoUser operData = request.getOperData();
		String roleList = operData.getRoleList();
		// 1681600是系统管理，101是system
		boolean operHasSystem = operHasRole(roleList, "101");
		boolean operHasSystemOperate = operHasRole(roleList, "1681600");

		DataBus db = request.getRecord("select-key");
		String yhzh = db.getValue("yhzh");
		String yhxm = db.getValue("yhxm");
		String jgid_fk = db.getValue("jgid_fk");
		String sfyx = db.getValue("sfyx");
		String rolenames = db.getValue("rolenames");
		SqlStatement stmt = new SqlStatement();
		String sql = " select  yhid_pk,jgid_fk,yhzh,yhmm,yhxm,b.sfyx,b.plxh,b.last_login_time,sfz,zw,zyzz,gzdh,lxdh,qtlxfs,dzyx,b.bz,jzjgid,roleids,mainrole,yhbh,jgname,jzjgname,rolenames,mainrolename,roletype,mmxgrq,b.maxline,a.jgid_pk,a.sjjgid_fk,a.sjjgname,a.jgmc,a.jgjc from xt_zzjg_jg a, xt_zzjg_yh_new b where b.jgid_fk = a.jgid_pk ";

		if (sfyx != null && !sfyx.equals("")) {
			sql += " and b.sfyx = '" + sfyx + "' ";
		}
		if (yhzh != null && !yhzh.equals("")) {
			sql += " and upper(yhzh) like '%" + yhzh.toUpperCase() + "%' ";
		}
		if (yhxm != null && !yhxm.equals("")) {
			sql += " and yhxm like '%" + yhxm + "%' ";
		}
		if (jgid_fk != null && !jgid_fk.equals("")) {
			sql += " and b.jgid_fk like '%" + jgid_fk + "' ";
		}
		if (rolenames != null && !rolenames.equals("")) {
			sql += " and rolenames like '%" + rolenames + "%' ";
		}
		// //如果没有system权限而且 有用户授权的权限，只查用户所在部门的用户
		// if(!operHasSystem && operHasSystemOperate){
		// String orgCode=operData.getOrgCode();
		// sql+= " and jgid_fk =  '"+orgCode+"' ";
		// }
		String t_sql = sql;

		stmt.setCountStmt("select count(*) from (" + sql + ")");
		t_sql += " order by plxh,yhxm ";
		// System.out.println("sql="+sql);
		stmt.addSqlStmt(t_sql);
		return stmt;
	}

	/**
	 * XXX:更新所属机构名称 对于多记录的查询语句，需要生成两个语句：查询语句和取记录数量的语句 对于其它的语句，只需要生成一个语句
	 * 
	 * @param request
	 *            交易的上下文
	 * @param inputData
	 *            生成语句的输入节点
	 * @return
	 */
	public SqlStatement updateJgname(TxnContext request, DataBus inputData)
	{
		DataBus db = request.getRecord("updateJgname");
		String jgid = db.getValue(VoXt_zzjg_jg.ITEM_JGID_PK);
		String jgname = db.getValue(VoXt_zzjg_jg.ITEM_JGMC);

		SqlStatement stmt = new SqlStatement();
		String sql = "update xt_zzjg_yh_new set JGNAME='" + jgname
				+ "' where JGID_FK='" + jgid + "' and SFYX='0'";
		stmt.addSqlStmt(sql);
		// stmt.setCountStmt( "select count(*) from department" );
		return stmt;
	}

}
