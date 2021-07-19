package com.gwssi.dw.aic.bj.gjcx.fhcx.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.dw.aic.bj.gjcx.fhcx.vo.VRegBusEntQueContext;

public class TxnVRegBusEntQue extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnVRegBusEntQue.class, VRegBusEntQueContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "v_reg_bus_ent_que";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select v_reg_bus_ent_que list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "${mod.function.select}";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "${mod.function.update}";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "${mod.function.insert}";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "${mod.function.delete}";
	
	/**
	 * ���캯��
	 */
	public TxnVRegBusEntQue()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ�����б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60220001( VRegBusEntQueContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		SqlStatement sql = table.getSqlStatement("queryEntList", context,context.getRecord(inputNode));
		if(sql == null)
			return;
		//String[] s= sql.getSqlStmt().toArray();
		//log.debug(s[0]);
		table.executeFunction( "queryEntList", context, inputNode, outputNode );
	}
	
	/** ѡ����뼯
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60220002( VRegBusEntQueContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow( context, outputNode, -1 );
		table.executeFunction( "queryJcdmfx", context, inputNode, outputNode );
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
		VRegBusEntQueContext appContext = new VRegBusEntQueContext( context );
		invoke( method, appContext );
	}
}
