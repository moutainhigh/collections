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
//		ȡ���ݱ�getTableObject�ڶ��������Ƿ��ʵı�����dao�ļ�����
		table = TableFactory.getInstance().getTableObject(this, "gz_zb_jldw");
		}
		catch( TxnException e ){
		log.error( "ȡ���ݿ��ʱ����", e );
		return null;
		}
		
		String dwlb_dm = null;
		try{
//			ȡ���ݹ����Ĳ�����Ĭ�Ϸ���input-data�ϣ������ж������
			dwlb_dm = data.getString("input-data:dwlb_dm");
		}
		catch(Exception e){
			log.error( e );
		}
//		�ô��ݹ����Ĳ�����sql�����ݿ��ѯ
		String sql = "select jldw_dm, jldw_cn_mc from gz_zb_jldw where dwlb_dm='" + dwlb_dm + "'";
		try{
//		ִ�в�ѯ��������ѯ�����ŵ���ʱ�ڵ�"selected-code-list"��
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
//			ȡ���ݱ�getTableObject�ڶ��������Ƿ��ʵı�����dao�ļ�����
			   table = TableFactory.getInstance().getTableObject(this, "gz_zb_jldw");
			}
			catch( TxnException e ){
			log.error( "ȡ���ݿ��ʱ����", e );
			   return null;
			}			
			String dwlb_dm = "";
			String sql = "";
			try{
//				ȡ���ݹ����Ĳ�����Ĭ�Ϸ���input-data�ϣ������ж������
				dwlb_dm = data.getString("input-data:dwlb_dm");
			}
			catch(Exception e){
				log.error( e );
			}
			
//			�ô��ݹ����Ĳ�����sql�����ݿ��ѯ
			if(dwlb_dm==null||dwlb_dm.equals("")){
				sql = "select jldw_dm, jldw_cn_mc from gz_zb_jldw where 1=1";
			}
			else{
			    sql = "select jldw_dm, jldw_cn_mc from gz_zb_jldw where dwlb_dm='" + dwlb_dm + "'";
			}
			try{
//			ִ�в�ѯ��������ѯ�����ŵ���ʱ�ڵ�"selected-code-list"��
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
//		ȡ���ݱ�getTableObject�ڶ��������Ƿ��ʵı�����dao�ļ�����
		   table = TableFactory.getInstance().getTableObject(this, "gz_zb_jldw_fl");
		}
		catch( TxnException e ){
		log.error( "ȡ���ݿ��ʱ����", e );
		   return null;
		}			
		
		try{
//		ִ�в�ѯ��������ѯ�����ŵ���ʱ�ڵ�"selected-code-list"��
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
//			ȡ���ݱ�getTableObject�ڶ��������Ƿ��ʵı�����dao�ļ�����
			   table = TableFactory.getInstance().getTableObject(this, "gz_dm_xzqh");
			}
			catch( TxnException e ){
			log.error( "ȡ���ݿ��ʱ����", e );
			   return null;
			}			
			
			try{
//			ִ�в�ѯ��������ѯ�����ŵ���ʱ�ڵ�"selected-code-list"��
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
//			ȡ���ݱ�getTableObject�ڶ��������Ƿ��ʵı�����dao�ļ�����
			   table = TableFactory.getInstance().getTableObject(this, "gz_zb_dczd");
			}
			catch( TxnException e ){
			log.error( "ȡ���ݿ��ʱ����", e );
			   return null;
			}			
			try{
//			ִ�в�ѯ��������ѯ�����ŵ���ʱ�ڵ�"selected-code-list"��
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
//				ȡ���ݱ�getTableObject�ڶ��������Ƿ��ʵı�����dao�ļ�����
				   table = TableFactory.getInstance().getTableObject(this, "gz_zb_dczd");
				}
				catch( TxnException e ){
				log.error( "ȡ���ݿ��ʱ����", e );
				   return null;
				}			
				try{
//				ִ�в�ѯ��������ѯ�����ŵ���ʱ�ڵ�"selected-code-list"��
				table.executeRowset("select dczd_dm,dczd_mc from gz_zb_dczd where right(dczd_dm,2)='00' order by dczd_dm",data,"selected-code-listed");
				}
				catch( TxnException e ){
				log.error( e );
				}
				return getParamList(data.getRecordset("selected-code-listed"), "dczd_mc", "dczd_dm");
				}
	    	
}
