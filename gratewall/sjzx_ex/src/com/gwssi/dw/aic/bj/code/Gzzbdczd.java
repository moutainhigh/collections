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
		list = PrivilegeManager.getInst().getPrivilege(userId, txnCode, "ͳ���ƶ�").getPrivilegeList();
		rule = PrivilegeManager.getInst().getPrivilege(userId, txnCode, "ͳ���ƶ�").getRule();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	try{
		// ȡ���ݱ�getTableObject�ڶ��������Ƿ��ʵı�����dao�ļ�����
		table = TableFactory.getInstance().getTableObject(this, "gz_zb_dczd");
	}
	catch( TxnException e ){
	log.error( "ȡ���ݿ��ʱ����", e );
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
	
	// �ô��ݹ����Ĳ�����sql�����ݿ��ѯ
	// κǿ ע����2008-7-15
	// String sql = "select dczd_dm, dczd_mc, dczd_cc from gz_zb_dczd where dczd_dm " + rule + "(" + strJgid1 + ") order by dczd_cjm";
	// ע����ϣ����������:
	String sql = "select dczd_dm, dczd_mc, dczd_cc from gz_zb_dczd order by dczd_cjm";
	
	try{
		// ִ�в�ѯ��������ѯ�����ŵ���ʱ�ڵ�"selected-code-list"��
		table.executeRowset(sql, data, "selected-code-listed");
		Recordset rs = data.getRecordset("selected-code-listed");
		for (int i=0; i < rs.size(); i++)
		{
			DataBus db = rs.get(i);
			int dczd_cc = Integer.parseInt(db.getValue("dczd_cc"));
			String strPrefix = "";
			for (int k = 1; k < dczd_cc; k++)
			{
				strPrefix += "��";
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
	 * ��Ȩ�޶����л�ȡ���û���Ȩ��list��Ȩ��rule����ȡ�û���ĳ������ĳһ�������͵����ݷ������
	 * @param   userID  �û�ID
	 * @return  rs      �ַ�������:
	 * <tr><td>rs[0]���(�ַ�����ʽ��)Ȩ��list;</td></tr>
	 * <tr><td>rs[1]���(�ַ�����ʽ��)Ȩ��rule</td></tr>
	 */
	private String[] getPrivilegeArray(String userID,String txncode)
	{
		List list = null;
		String[] rs = new String[2];
		try 
		{
			IUserPrivilege pm = PrivilegeManager.getInst().getPrivilege(userID, txncode, "ͳ���ƶ�");
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

