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
		log.error( "ȡ���ݿ��ʱ����", e );
		   return null;
		}			
		String bgqb = "";
		String sql = "";
		try{
//			ȡ���ݹ����Ĳ�����Ĭ�Ϸ���input-data�ϣ������ж������
			bgqb = data.getString("input-data:bgqb");
		}
		catch(Exception e){
			log.error( e );
		}
//		�ô��ݹ����Ĳ�����sql�����ݿ��ѯ
		if(bgqb==null||bgqb.equals("")){
			sql = "select bgq_dm, bgq_cn_mc from gz_dm_bgq where 1=1 order by bgq_dm desc";
		}
		else{
		    sql = "select bgq_dm, bgq_cn_mc from gz_dm_bgq where bgqb_dm='" + bgqb + "' order by bgq_dm desc";
		}
		try{
//		ִ�в�ѯ��������ѯ�����ŵ���ʱ�ڵ�"selected-code-list"��
		    table.executeRowset(sql,data,"selected-code-listed");
		}
		catch( TxnException e ){
		    log.error( e );
		}
		   return getParamList(data.getRecordset("selected-code-listed"), "bgq_cn_mc", "bgq_dm");
	}	
	
}

