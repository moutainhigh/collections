package com.gwssi.dw.runmgr.etl.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.dw.runmgr.etl.vo.EtlTableCountContext;

public class TxnLoginSessionHs extends TxnService
{
	
	// 数据表名称
	private static final String TABLE_NAME = "login_session_hs";

	
	/**
	 * 构造函数
	 */
	public TxnLoginSessionHs()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询历史登录用户信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn81100001( TxnContext context ) throws TxnException
	{
		Attribute.setPageRow(context, outputNode, 30);
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "queryLoginSessionHsList", context, inputNode, outputNode );
//		System.out.println("---------->"+context);
        Recordset rs = context.getRecordset(outputNode);
        while(rs.hasNext()){
        	DataBus db = (DataBus) rs.next();
        	String jgid = db.getValue("jgid_pk");
        	String rolenames = db.getValue("rolenames"); 
        	if(rolenames!=null&&!"".equals(rolenames)){
        		String[] roleArr = rolenames.split(",");
        		StringBuffer strBuf = new StringBuffer();
        		for(int i=0;i<roleArr.length;i++){
        			if(strBuf.length()>0){
        				strBuf.append(",");
        			}
        			if(!"user".equalsIgnoreCase(roleArr[i])){
        				strBuf.append(roleArr[i]);
        			}
        		}
        		db.setValue("rolenames",strBuf.toString());
        	}
        	TxnContext queryContext = new TxnContext();
            queryContext.getRecord("primary-key").setValue("jgid_pk", jgid);
            callService("com.gwssi.sysmgr.org.txn.TxnXt_zzjg_jg","findJgnameByLogin",queryContext);  
            //String jgname=userMessage.getValue(VoXt_zzjg_yh.ITEM_JGNAME);
            String jgname = queryContext.getRecord("record").getValue("jgmc");
            db.setValue("jgname",jgname);
        }
	}
	
	/** 用户访问统计
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn81100002( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "statLoginSessionHsList", context, inputNode, outputNode );        
	}	
	
	/** 使用情况统计
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn81100003( TxnContext context ) throws TxnException
	{
		Attribute.setPageRow(context, outputNode, -1);
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "statUseList", context, inputNode, outputNode );        
	}	
	
	/** 更新当前登录用户登录时间，由top.jsp发ajax请求调用
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn81100004( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "updateLoginSession", context, inputNode, outputNode );        
	}
}
