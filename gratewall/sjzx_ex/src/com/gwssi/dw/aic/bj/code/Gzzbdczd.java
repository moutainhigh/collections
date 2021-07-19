package com.gwssi.dw.aic.bj.code;

import java.util.List;

import com.gwssi.sysmgr.priv.datapriv.IUserPrivilege;
import com.gwssi.sysmgr.priv.datapriv.PrivilegeManager;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.param.ParamHelp;

public class Gzzbdczd extends ParamHelp
{
	public Recordset getGzzbdczd( TxnContext data ) throws TxnException{		BaseTable table = null;
	String txnCode = data.getString("input-data:txncode");
	String userId = data.getRecord("oper-data").getValue("userID");
	System.out.println(txnCode);
	List list = null;
	String rule = "";
	try {
		list = PrivilegeManager.getInst().getPrivilege(userId, txnCode, "统计制度").getPrivilegeList();
		rule = PrivilegeManager.getInst().getPrivilege(userId, txnCode, "统计制度").getRule();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	try{
		// 取数据表，getTableObject第二个参数是访问的表名（dao文件名）
		table = TableFactory.getInstance().getTableObject(this, "gz_zb_dczd");
	}
	catch( TxnException e ){
	log.error( "取数据库表时错误", e );
	return null;
	}
	
	String strJgid = "";
	for (int j = 0; j < list.size(); j ++)
	{
		strJgid += "'";
		strJgid += list.get(j).toString();
		strJgid += "'";
		strJgid += ",";
	}
	
	int strLength = strJgid.length();
	String strJgid1 = "";
	if (list.size() > 0){
		strJgid1 = strJgid.substring(0, strLength-1);
	}
	else{
		strJgid1 = "''";
	}
	
	// 用传递过来的参数组sql从数据库查询
	// 魏强 注释于2008-7-15
	// String sql = "select dczd_dm, dczd_mc, dczd_cc from gz_zb_dczd where dczd_dm " + rule + "(" + strJgid1 + ") order by dczd_cjm";
	// 注释完毕，新语句如下:
	String sql = "select dczd_dm, dczd_mc, dczd_cc from gz_zb_dczd order by dczd_cjm";
	
	try{
		// 执行查询函数，查询结果存放到临时节点"selected-code-list"上
		table.executeRowset(sql, data, "selected-code-listed");
		Recordset rs = data.getRecordset("selected-code-listed");
		for (int i=0; i < rs.size(); i++)
		{
			DataBus db = rs.get(i);
			int dczd_cc = Integer.parseInt(db.getValue("dczd_cc"));
			String strPrefix = "";
			for (int k = 1; k < dczd_cc; k++)
			{
				strPrefix += "　";
			}
			String dczd_mc = db.getValue("dczd_mc");
			dczd_mc = strPrefix + dczd_mc;
			db.setValue("dczd_mc", dczd_mc);
		}
	}
	catch( TxnException e ){
	log.error( e );
	}

	return getParamList(data.getRecordset("selected-code-listed"), "dczd_mc", "dczd_dm");}
	/**
	 * 从权限对象中获取该用户的权限list与权限rule，获取用户在某功能下某一数据类型的数据访问许可
	 * @param   userID  用户ID
	 * @return  rs      字符串数组:
	 * <tr><td>rs[0]存放(字符串形式的)权限list;</td></tr>
	 * <tr><td>rs[1]存放(字符串形式的)权限rule</td></tr>
	 */
	private String[] getPrivilegeArray(String userID,String txncode)
	{
		List list = null;
		String[] rs = new String[2];
		try 
		{
			IUserPrivilege pm = PrivilegeManager.getInst().getPrivilege(userID, txncode, "统计制度");
			list = pm.getPrivilegeList();
			rs[1] = pm.getRule();
		}
		catch(Exception e)
		{
			log.error( e );
		}
		StringBuffer strZdid = new StringBuffer();
		for (int j = 0; j < list.size(); j ++)
		{
			strZdid.append("'");
			strZdid.append(list.get(j));
			strZdid.append("'");
			strZdid.append(",");
		}
		int strLength = strZdid.length();
		if (list.size() > 0)
		{
			rs[0] = strZdid.substring(0, strLength-1);
		}
		else
		{
			rs[0] = "''";
		}
		return rs;
	}	 
}

