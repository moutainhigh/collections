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
	 * ע���û��Զ����SQL���
	 */
	protected void register()
	{
		registerSQLFunction("updateJgname", DaoFunction.SQL_UPDATE,
				"�����û�������������");
		registerSQLFunction("queryYhList", DaoFunction.SQL_ROWSET, "��ѯ�û��б�");
	}

	/**
	 * ִ��SQL���ǰ�Ĵ���
	 */
	public void prepareExecuteStmt(DaoFunction func, TxnContext request,
			DataBus[] inputData, String outputNode) throws TxnException
	{

	}

	/**
	 * ִ����SQL����Ĵ���
	 */
	public void afterExecuteStmt(DaoFunction func, TxnContext request,
			DataBus[] inputData, String outputNode) throws TxnException
	{

	}

	/**
	 * 
	 * �ж���û��ָ��Ȩ��
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
	 * ��ѯ�û�
	 * 
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement queryYhList(TxnContext request, DataBus inputData)
	{
		VoUser operData = request.getOperData();
		String roleList = operData.getRoleList();
		// 1681600��ϵͳ����101��system
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
		// //���û��systemȨ�޶��� ���û���Ȩ��Ȩ�ޣ�ֻ���û����ڲ��ŵ��û�
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
	 * XXX:���������������� ���ڶ��¼�Ĳ�ѯ��䣬��Ҫ����������䣺��ѯ����ȡ��¼��������� ������������䣬ֻ��Ҫ����һ�����
	 * 
	 * @param request
	 *            ���׵�������
	 * @param inputData
	 *            ������������ڵ�
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
