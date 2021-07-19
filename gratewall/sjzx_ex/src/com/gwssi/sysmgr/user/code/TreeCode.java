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
	 * 取角色对照表
	 * @param data
	 * @return
	 */
	public Recordset getOperRoleList (TxnContext data){
		BaseTable table = null;
		String type = null;
		Recordset rs=null;
		try {
			//第二个参数是访问的数据表名
			table=TableFactory.getInstance().getTableObject(this, OPERROLE_TABLE_NAME);
		} catch (TxnException e) {
			log.error("取表时的错误"+e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		try {
//			//取得type的值
//			type=data.getString("input-data:type");
//		} catch (TxnException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		log.debug("type=================="+type);
		//用传递的参数组装sql从数据库中查询
		String sql;

			 sql ="SELECT ROLEID ,ROLENAME FROM OPERROLE_new WHERE ROLEID<>102  ORDER BY regdate";
		log.debug("TreeCode=================="+sql);
			try {
				//查询结构放到临时节点“selected-code-list”上
				table.executeRowset(sql, data, "selected-code-list");
			} catch (TxnException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				//从selected-code-list节点上取道查询结果
				rs=getParamList(data.getRecordset("selected-code-list"), "rolename", "roleid");
			} catch (TxnException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return rs;
		
	}
	/**
	 * 取用户对照表
	 * @param data
	 * @return
	 */
	public Recordset getYhList (TxnContext data){
		BaseTable table = null;
		String jgid_fk = null;
		Recordset rs=null;
		try {
			//第二个参数是访问的数据表名
			table=TableFactory.getInstance().getTableObject(this, YH_TABLE_NAME);
		} catch (TxnException e) {
			log.error("取tree表时的错误"+e);
			e.printStackTrace();
		}
		try {
			//取得type的值
			jgid_fk=data.getString("input-data:jgid_fk");
		} catch (TxnException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("type=================="+jgid_fk);
		//用传递的参数组装sql从数据库中查询
		String sql ="select yhid_pk ,yhxm from xt_zzjg_yh_new where jgid_fk='"+jgid_fk+"' and roletype<>'1' and sfyx='0'" ;
			try {
				//查询结构放到临时节点“selected-code-list”上
				table.executeRowset(sql, data, "selected-code-list");
			} catch (TxnException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				//从selected-code-list节点上取道查询结果
				rs=getParamList(data.getRecordset("selected-code-list"), "yhxm", "yhid_pk");
			} catch (TxnException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return rs;
	}
}
