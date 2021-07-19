package com.gwssi.dw.metadata.msurunit.code;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.txn.param.ParamHelp;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.context.Recordset;

public class Jldw extends ParamHelp{
		public Recordset getJldw( TxnContext data ) throws TxnException{
		BaseTable table = null;
		try{
//		取数据表，getTableObject第二个参数是访问的表名（dao文件名）
		table = TableFactory.getInstance().getTableObject(this, "gz_zb_jldw");
		}
		catch( TxnException e ){
		log.error( "取数据库表时错误", e );
		return null;
		}
		
		String dwlb_dm = null;
		try{
//			取传递过来的参数，默认放在input-data上，可以有多个参数
			dwlb_dm = data.getString("input-data:dwlb_dm");
		}
		catch(Exception e){
			log.error( e );
		}
//		用传递过来的参数组sql从数据库查询
		String sql = "select jldw_dm, jldw_cn_mc from gz_zb_jldw where dwlb_dm='" + dwlb_dm + "'";
		try{
//		执行查询函数，查询结果存放到临时节点"selected-code-list"上
		table.executeRowset(sql,data,"selected-code-listed");
		}
		catch( TxnException e ){
		log.error( e );
		}

		return getParamList(data.getRecordset("selected-code-listed"), "jldw_cn_mc", "jldw_dm");
		}
		
    public Recordset getJldwByFlDm( TxnContext data ) throws TxnException{
    	
			BaseTable table = null;
			try{
//			取数据表，getTableObject第二个参数是访问的表名（dao文件名）
			   table = TableFactory.getInstance().getTableObject(this, "gz_zb_jldw");
			}
			catch( TxnException e ){
			log.error( "取数据库表时错误", e );
			   return null;
			}			
			String dwlb_dm = "";
			String sql = "";
			try{
//				取传递过来的参数，默认放在input-data上，可以有多个参数
				dwlb_dm = data.getString("input-data:dwlb_dm");
			}
			catch(Exception e){
				log.error( e );
			}
			
//			用传递过来的参数组sql从数据库查询
			if(dwlb_dm==null||dwlb_dm.equals("")){
				sql = "select jldw_dm, jldw_cn_mc from gz_zb_jldw where 1=1";
			}
			else{
			    sql = "select jldw_dm, jldw_cn_mc from gz_zb_jldw where dwlb_dm='" + dwlb_dm + "'";
			}
			try{
//			执行查询函数，查询结果存放到临时节点"selected-code-list"上
			table.executeRowset(sql,data,"selected-code-listed");
			}
			catch( TxnException e ){
			log.error( e );
			}

			return getParamList(data.getRecordset("selected-code-listed"), "jldw_cn_mc", "jldw_dm");
			}		
    public Recordset getJldwlb( TxnContext data ) throws TxnException{
    	
		BaseTable table = null;
		try{
//		取数据表，getTableObject第二个参数是访问的表名（dao文件名）
		   table = TableFactory.getInstance().getTableObject(this, "gz_zb_jldw_fl");
		}
		catch( TxnException e ){
		log.error( "取数据库表时错误", e );
		   return null;
		}			
		
		try{
//		执行查询函数，查询结果存放到临时节点"selected-code-list"上
		table.executeRowset("select dwlb_dm,dwlb_cn_mc from gz_zb_jldw_fl order by dwlb_dm",data,"selected-code-listed");
		}
		catch( TxnException e ){
		log.error( e );
		}
		return getParamList(data.getRecordset("selected-code-listed"), "dwlb_cn_mc", "dwlb_dm");
		}		
	    public Recordset getCc( TxnContext data ) throws TxnException{
	    	
			BaseTable table = null;
			try{
//			取数据表，getTableObject第二个参数是访问的表名（dao文件名）
			   table = TableFactory.getInstance().getTableObject(this, "gz_dm_xzqh");
			}
			catch( TxnException e ){
			log.error( "取数据库表时错误", e );
			   return null;
			}			
			
			try{
//			执行查询函数，查询结果存放到临时节点"selected-code-list"上
			table.executeRowset("select distinct(xzqh_cc) from gz_dm_xzqh",data,"selected-code-listed");
			}
			catch( TxnException e ){
			log.error( e );
			}
			return getParamList(data.getRecordset("selected-code-listed"), "xzqh_cc", "xzqh_cc");
			}
	    public Recordset getDczdCc( TxnContext data ) throws TxnException{
	    	
			BaseTable table = null;
			try{
//			取数据表，getTableObject第二个参数是访问的表名（dao文件名）
			   table = TableFactory.getInstance().getTableObject(this, "gz_zb_dczd");
			}
			catch( TxnException e ){
			log.error( "取数据库表时错误", e );
			   return null;
			}			
			try{
//			执行查询函数，查询结果存放到临时节点"selected-code-list"上
			table.executeRowset("select distinct(dczd_cc) from gz_zb_dczd",data,"selected-code-listed");
			}
			catch( TxnException e ){
			log.error( e );
			}
			return getParamList(data.getRecordset("selected-code-listed"), "dczd_cc", "dczd_cc");
			}
	    public Recordset getOneDczd( TxnContext data ) throws TxnException{
		    	
				BaseTable table = null;
				try{
//				取数据表，getTableObject第二个参数是访问的表名（dao文件名）
				   table = TableFactory.getInstance().getTableObject(this, "gz_zb_dczd");
				}
				catch( TxnException e ){
				log.error( "取数据库表时错误", e );
				   return null;
				}			
				try{
//				执行查询函数，查询结果存放到临时节点"selected-code-list"上
				table.executeRowset("select dczd_dm,dczd_mc from gz_zb_dczd where right(dczd_dm,2)='00' order by dczd_dm",data,"selected-code-listed");
				}
				catch( TxnException e ){
				log.error( e );
				}
				return getParamList(data.getRecordset("selected-code-listed"), "dczd_mc", "dczd_dm");
				}
	    	
}
