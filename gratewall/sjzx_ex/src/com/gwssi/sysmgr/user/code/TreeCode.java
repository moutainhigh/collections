package com.gwssi.sysmgr.user.code;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.param.ParamHelp;

/**
 * 
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
public class TreeCode extends ParamHelp
{
	private static final String OPERROLE_TABLE_NAME="operrole";
	private static final String YH_TABLE_NAME="xt_zzjg_yh";
	
	/**
	 * ȡ��ɫ���ձ�
	 * @param data
	 * @return
	 */
	public Recordset getOperRoleList (TxnContext data){
		BaseTable table = null;
		String type = null;
		Recordset rs=null;
		try {
			//�ڶ��������Ƿ��ʵ����ݱ���
			table=TableFactory.getInstance().getTableObject(this, OPERROLE_TABLE_NAME);
		} catch (TxnException e) {
			log.error("ȡ��ʱ�Ĵ���"+e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		try {
//			//ȡ��type��ֵ
//			type=data.getString("input-data:type");
//		} catch (TxnException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		log.debug("type=================="+type);
		//�ô��ݵĲ�����װsql�����ݿ��в�ѯ
		String sql;

			 sql ="SELECT ROLEID ,ROLENAME FROM OPERROLE_new WHERE ROLEID<>102  ORDER BY regdate";
		log.debug("TreeCode=================="+sql);
			try {
				//��ѯ�ṹ�ŵ���ʱ�ڵ㡰selected-code-list����
				table.executeRowset(sql, data, "selected-code-list");
			} catch (TxnException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				//��selected-code-list�ڵ���ȡ����ѯ���
				rs=getParamList(data.getRecordset("selected-code-list"), "rolename", "roleid");
			} catch (TxnException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return rs;
		
	}
	/**
	 * ȡ�û����ձ�
	 * @param data
	 * @return
	 */
	public Recordset getYhList (TxnContext data){
		BaseTable table = null;
		String jgid_fk = null;
		Recordset rs=null;
		try {
			//�ڶ��������Ƿ��ʵ����ݱ���
			table=TableFactory.getInstance().getTableObject(this, YH_TABLE_NAME);
		} catch (TxnException e) {
			log.error("ȡtree��ʱ�Ĵ���"+e);
			e.printStackTrace();
		}
		try {
			//ȡ��type��ֵ
			jgid_fk=data.getString("input-data:jgid_fk");
		} catch (TxnException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("type=================="+jgid_fk);
		//�ô��ݵĲ�����װsql�����ݿ��в�ѯ
		String sql ="select yhid_pk ,yhxm from xt_zzjg_yh_new where jgid_fk='"+jgid_fk+"' and roletype<>'1' and sfyx='0'" ;
			try {
				//��ѯ�ṹ�ŵ���ʱ�ڵ㡰selected-code-list����
				table.executeRowset(sql, data, "selected-code-list");
			} catch (TxnException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				//��selected-code-list�ڵ���ȡ����ѯ���
				rs=getParamList(data.getRecordset("selected-code-list"), "yhxm", "yhid_pk");
			} catch (TxnException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return rs;
	}
}
