package com.gwssi.dw.metadata.datadict.code;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.param.ParamHelp;

public class BgqCode extends ParamHelp
{
	public Recordset getCodeList( TxnContext data ) throws TxnException{

		BaseTable table = null;
		try{
		   table = TableFactory.getInstance().getTableObject(this, "gz_dm_bgq");
		}
		catch( TxnException e ){
		log.error( "取数据库表时错误", e );
		   return null;
		}			
		String bgqb = "";
		String sql = "";
		try{
//			取传递过来的参数，默认放在input-data上，可以有多个参数
			bgqb = data.getString("input-data:bgqb");
		}
		catch(Exception e){
			log.error( e );
		}
//		用传递过来的参数组sql从数据库查询
		if(bgqb==null||bgqb.equals("")){
			sql = "select bgq_dm, bgq_cn_mc from gz_dm_bgq where 1=1 order by bgq_dm desc";
		}
		else{
		    sql = "select bgq_dm, bgq_cn_mc from gz_dm_bgq where bgqb_dm='" + bgqb + "' order by bgq_dm desc";
		}
		try{
//		执行查询函数，查询结果存放到临时节点"selected-code-list"上
		    table.executeRowset(sql,data,"selected-code-listed");
		}
		catch( TxnException e ){
		    log.error( e );
		}
		   return getParamList(data.getRecordset("selected-code-listed"), "bgq_cn_mc", "bgq_dm");
	}	
	
}

