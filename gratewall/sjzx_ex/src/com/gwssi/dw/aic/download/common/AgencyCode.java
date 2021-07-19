package com.gwssi.dw.aic.download.common;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.param.ParamHelp;

public class AgencyCode extends ParamHelp{
	
	public Recordset getAgencyName( TxnContext data ) throws TxnException{	
		
		BaseTable table = null;
		Recordset rs=null;
		try {
			//第二个参数是访问的数据表名
			table=TableFactory.getInstance().getTableObject(this, "gz_dm_jcdm_fx");
		} catch (TxnException e) {
			e.printStackTrace();
		}
		//用传递的参数组装sql从数据库中查询
		String sql ="select jgid_pk as id ,sjjgname||jgmc as name from xt_zzjg_jg ";
			try {
				table.executeRowset(sql, data, "selected-code-list");
			} catch (TxnException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				//从selected-code-list节点上取道查询结果
				rs=getParamList(data.getRecordset("selected-code-list"), "name", "id");
			} catch (TxnException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return rs;
	}
	
	public Recordset getDeptList( TxnContext data ) throws TxnException{	
		
		BaseTable table = null;
		String adm_org_id = "";
		Recordset rs=null;
		try {
			//第二个参数是访问的数据表名
			table=TableFactory.getInstance().getTableObject(this, "gz_dm_jcdm_fx");
		} catch (TxnException e) {
			e.printStackTrace();
		}
		try {
			adm_org_id=data.getString("input-data:adm_org_id");
		} catch (TxnException e) {
			e.printStackTrace();
		}
		//用传递的参数组装sql从数据库中查询
		String sql ="select jcsjfx_dm as id ,jcsjfx_mc as name from gz_dm_jcdm_fx where jc_dm_id='16111745'" +
				"And substr(jcsjfx_dm,1,4)='"+adm_org_id+"' And  length(jcsjfx_dm)>4 ";
			try {
				table.executeRowset(sql, data, "selected-code-list");
			} catch (TxnException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				//从selected-code-list节点上取道查询结果
				rs=getParamList(data.getRecordset("selected-code-list"), "name", "id");
			} catch (TxnException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return rs;
	}
	
	public Recordset getGridName( TxnContext data ) throws TxnException{	
		
		BaseTable table = null;
		String adm_dep_id = "";
		Recordset rs=null;
		try {
			//第二个参数是访问的数据表名
			table=TableFactory.getInstance().getTableObject(this, "mon_buss_grid");
		} catch (TxnException e) {
			e.printStackTrace();
		}
		try {
			adm_dep_id=data.getString("input-data:adm_dep_id");
		} catch (TxnException e) {
			e.printStackTrace();
		}
		//用传递的参数组装sql从数据库中查询
		String sql ="select grid_id as id ,grid_name as name from mon_buss_grid where adm_dep_id='"+adm_dep_id+"'";
			try {
				table.executeRowset(sql, data, "selected-code-list");
			} catch (TxnException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				//从selected-code-list节点上取道查询结果
				rs=getParamList(data.getRecordset("selected-code-list"), "name", "id");
			} catch (TxnException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return rs;
	}		
}

