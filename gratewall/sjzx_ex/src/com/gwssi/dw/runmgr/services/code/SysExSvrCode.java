package com.gwssi.dw.runmgr.services.code;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.param.ParamHelp;

public class SysExSvrCode extends ParamHelp
{
	private static final String TABLE_NAME="sys_svr_service";
	public Recordset getSvrList (TxnContext data) throws TxnException{
		BaseTable table = TableFactory.getInstance().getTableObject(this, TABLE_NAME);
		String userId = data.getRecord("input-data").getValue("userbianma");
		TxnContext ctx = new TxnContext();
		
		if(userId.trim().equals("�������ʼ��")){
			DataBus db = new DataBus();
	        db.setValue("jcsjfx_dm", "��ҵ���˻�����Ϣ�������Ϣ-�ʼ��");
	        db.setValue("jcsjfx_mc", "��ҵ���˻�����Ϣ�������Ϣ-�ʼ��");
	        ctx.addRecord("record", db);
	        db = new DataBus();
	        db.setValue("jcsjfx_dm", "�ɼ����˻�����Ϣ");
	        db.setValue("jcsjfx_mc", "�ɼ����˻�����Ϣ");
	        ctx.addRecord("record", db);
	        db = new DataBus();
	        db.setValue("jcsjfx_dm", "�ɼ�������չ��Ϣ");
	        db.setValue("jcsjfx_mc", "�ɼ�������չ��Ϣ");
	        ctx.addRecord("record", db);
	        db = new DataBus();
	        db.setValue("jcsjfx_dm", "�ɼ������չ��Ϣ");
	        db.setValue("jcsjfx_mc", "�ɼ������չ��Ϣ");
	        ctx.addRecord("record", db);
	        db = new DataBus();
	        db.setValue("jcsjfx_dm", "�ɼ���������Ϣ");
	        db.setValue("jcsjfx_mc", "�ɼ���������Ϣ");
	        ctx.addRecord("record", db);
	        db = new DataBus();
	        db.setValue("jcsjfx_dm", "�ɼ�������չ��Ϣ");
	        db.setValue("jcsjfx_mc", "�ɼ�������չ��Ϣ");
	        ctx.addRecord("record", db);
	        db = new DataBus();
	        db.setValue("jcsjfx_dm", "�ɼ����������Ϣ");
	        db.setValue("jcsjfx_mc", "�ɼ����������Ϣ");
	        ctx.addRecord("record", db);
			Recordset rs = getParamList(ctx.getRecordset("record"), "jcsjfx_mc", "jcsjfx_dm");	
			return rs;
		}
        
		table.executeRowset("select s.SYS_SVR_SERVICE_ID as jcsjfx_dm, s.SVR_NAME as jcsjfx_mc from sys_svr_config t, sys_svr_service s,sys_svr_user u where u.user_name='"+userId+"' and t.sys_svr_service_id=s.sys_svr_service_id and t.sys_svr_user_id=u.sys_svr_user_id", ctx, "record" );
		Recordset rs = null;
		
		if(userId.trim().equals("��˰�û�")){
			TxnContext temp = new TxnContext();
			table.executeRowset("select distinct(t.inf_desc) as jcsjfx_dm,  t.inf_desc as jcsjfx_mc from sys_clt_log_detail t",temp,"record");
			Recordset temprs = temp.getRecordset("record");
			while(temprs.hasNext()){
				DataBus tempdb = (DataBus)temprs.next();
				ctx.addRecord("record", tempdb);
			}
			DataBus db = new DataBus();
	        db.setValue("jcsjfx_dm", "��˰���ݲɼ�����");
	        db.setValue("jcsjfx_mc", "��˰���ݲɼ�����");
	        ctx.addRecord("record", db);
		}
		rs = getParamList(ctx.getRecordset("record"), "jcsjfx_mc", "jcsjfx_dm");	
		return rs;
		
		
	}
}
