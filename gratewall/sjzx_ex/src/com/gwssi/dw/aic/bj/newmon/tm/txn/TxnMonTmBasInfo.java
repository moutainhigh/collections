package com.gwssi.dw.aic.bj.newmon.tm.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.dw.aic.bj.newmon.tm.vo.MonTmBasInfoContext;

public class TxnMonTmBasInfo extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnMonTmBasInfo.class, MonTmBasInfoContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "mon_tm_bas_info";
	
	/**
	 * ���캯��
	 */
	public TxnMonTmBasInfo()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/**
	 * �̱�����Ϣ��ѯ
	 * TxnMonTmBasInfo:txn60160001 
	 * @creater - caiwd
	 * @creatertime - Nov 17, 2008
	 * @param context
	 * @throws TxnException
	 * @returnType void
	 */
	public void txn60160001( MonTmBasInfoContext context ) throws TxnException
	{
		//String inputTopBraSig = context.getString("select-key:top_bra_sig");
		String inputTopBraSig = context.getRecord(inputNode).getValue("top_bra_sig");
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "queryMonTmBasInfo_List", context, inputNode, outputNode );
		Recordset rs = context.getRecordset(outputNode);
		if("0".equals(inputTopBraSig)||"".equals(inputTopBraSig)){
			for(int i=0;i<rs.size();i++){
				DataBus dataBus = rs.get(i);
				dataBus.setValue("top_bra_sig", "0");
			}
		}else if("1".equals(inputTopBraSig)){
			for(int i=0;i<rs.size();i++){
				DataBus dataBus = rs.get(i);
				dataBus.setValue("top_bra_sig", "1");
			}
		}else if("2".equals(inputTopBraSig)){
			for(int i=0;i<rs.size();i++){
				DataBus dataBus = rs.get(i);
				dataBus.setValue("top_bra_sig", "2");
			}
		}else{
			for(int i=0;i<rs.size();i++){
				DataBus dataBus = rs.get(i);
				String top_bra_sig = dataBus.getString("top_bra_sig");
				if(null==top_bra_sig ||"".equals(top_bra_sig)){
					dataBus.setValue("top_bra_sig", "0");
				}
			}
		}
		
	}
	
	/**
	 * �鿴�̱�����ϸ��Ϣ
	 * TxnMonTmBasInfo:txn60160002 
	 * @creater - caiwd
	 * @creatertime - Nov 18, 2008
	 * @param context
	 * @throws TxnException
	 * @returnType void
	 */
	public void txn60160002( MonTmBasInfoContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "viewMonTmBasInfo_Detail", context, inputNode, outputNode );
		String tm_reg_id = context.getRecord(inputNode).getValue("tm_reg_id");
		/*����ͨ��reg_bus_ent_id�����޸�
		
		String ent_title = context.getString("record:ent_title");
		context.getRecord(outputNode).setValue(
				"ent_title",
				((null == ent_title || "".equals(ent_title)) ? context
						.getString("record:tm_reger") : ent_title));
		if(null!= context.getString("record:tm_reger") || !"".equals(context.getString("record:tm_reger"))){
			int resNo = table.executeFunction("queryRegBusEntForTM_One", context, outputNode, "select-key1");
			if(resNo < 1){
				context.getRecord("select-key1").setValue("reg_bus_ent_id", "");
			}
		}else{
			context.getRecord("select-key1").setValue("reg_bus_ent_id", "");
		}
		*/
		if(null != tm_reg_id && !"".equals(tm_reg_id)){
			table.executeFunction( "queryMonTmEntRlt_List", context, "select-key", "ent-rlt" );
		}
		
	}
	
	/**
	 * ��ѯ��������Ϊ�����̱����б�
	 * TxnMonTmBasInfo:txn60160003 
	 * @creater - caiwd
	 * @creatertime - Nov 18, 2008
	 * @param context
	 * @throws TxnException
	 * @returnType void
	 */
	public void txn60160003( MonTmBasInfoContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "queryRegMonTmBasInfo_List", context, inputNode, outputNode );
		Recordset rs = context.getRecordset(outputNode);
		for(int i=0;i<rs.size();i++){
			String temp = rs.get(i).getString("top_bra_sig");
			if(!"2".equals(temp) && !"1".equals(temp)){
				temp = "0";
			}
			rs.get(i).setValue("top_bra_sig", temp);
			rs.get(i).setValue("temp_top_bra_sig", temp);
		}
		
	}
	
	/**
	 * �鿴��������Ϊ�����̱�����ϸ��Ϣ
	 * TxnMonTmBasInfo:txn60160004 
	 * @creater - caiwd
	 * @creatertime - Nov 18, 2008
	 * @param context
	 * @throws TxnException
	 * @returnType void
	 */
	public void txn60160004( MonTmBasInfoContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "viewMonTmBasInfo_Detail", context, inputNode, outputNode );
		
		String ent_title = context.getString("record:ent_title");
		context.getRecord(outputNode).setValue("ent_title", ((null==ent_title || "".equals(ent_title))? context.getString("record:tm_reger"):ent_title));
		String tm_reg_id = context.getString("select-key:tm_reg_id");	
		if(null != tm_reg_id && !"".equals(tm_reg_id)){
			table.executeFunction( "queryMonTmEntRlt_List", context, "select-key", "ent-rlt" );
		}
	}
	
		
	/**
	 * ���ظ���ķ����������滻���׽ӿڵ��������
	 * ���ú���
	 * @param funcName ��������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void doService( String funcName,
			TxnContext context ) throws TxnException
	{
		Method method = (Method)txnMethods.get( funcName );
		if( method == null ){
			funcName = this.getClass().getName() + "#" + funcName;
			throw new TxnErrorException( ErrorConstant.JAVA_METHOD_NOTFOUND,
					"û���ҵ�������[" + txnCode + ":" + funcName + "]�Ĵ�����"
			);
		}
		
		// ִ��
		MonTmBasInfoContext appContext = new MonTmBasInfoContext( context );
		invoke( method, appContext );
	}
}
