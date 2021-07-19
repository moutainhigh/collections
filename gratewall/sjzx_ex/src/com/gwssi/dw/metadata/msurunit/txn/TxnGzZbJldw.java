package com.gwssi.dw.metadata.msurunit.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.dw.metadata.msurunit.vo.GzZbJldwContext;
import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.DataBus;

public class TxnGzZbJldw extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnGzZbJldw.class, GzZbJldwContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "gz_zb_jldw";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one gz_zb_jldw";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one gz_zb_jldw";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one gz_zb_jldw";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one gz_zb_jldw";
	
	/**
	 * ���캯��
	 */
	public TxnGzZbJldw()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ������λ�б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn301061( GzZbJldwContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "queryrowset", context, inputNode, outputNode );
	}
	
	/** �޸ļ�����λ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn301062( GzZbJldwContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
		setBizLog("�޸ļ�����λ��", context,context.getRecord(inputNode).getValue("jldw_cn_mc"));
	}
	
	/** ���Ӽ�����λ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn301063( GzZbJldwContext context ) throws TxnException
	{	
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
		setBizLog("���Ӽ�����λ��", context,context.getRecord(inputNode).getValue("jldw_cn_mc"));
	}
	
	/** ��ѯ������λ�����޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn301064( GzZbJldwContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ɾ��������λ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn301065( GzZbJldwContext context ) throws TxnException
	{
		/**
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Recordset rs=null;
		rs=context.getRecordset(inputNode);
		int m,n;
		m=rs.size();
		String jldwdm;
		String jldwcnmc;
		DataBus databus;
		String totalerrormessage="";
		for(n=0;n<m;n++)
		{
			databus=rs.get(n);
			jldwdm=databus.getValue(VoGzZbJldw.ITEM_JLDW_DM);
			jldwcnmc=databus.getValue(VoGzZbJldw.ITEM_JLDW_CN_MC);	
			String errormessage=jldwcnmc+"�ѱ�ʹ��,����ɾ��!";
			if(isCanDelete(context,jldwdm))
			{
				table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
			}
			else
			{
				totalerrormessage=""+totalerrormessage+errormessage;
			
			}

		}
		if(totalerrormessage==null||totalerrormessage.length()<=0)
		{
			
		}
		else
		{
		throw new TxnDataException("",totalerrormessage);
		}
		*/
		


		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );	
		Recordset rs = context.getRecordset("select-key");
		DataBus dataBus = null;
		DataBus countBus = null;
		GzZbJldwContext gzZbJldwContext = null;
		String jldw_cn_mc = "";
		String count = "";
		String errorString  = "";	
		String log_desc = "";
		for(int i=0; i<rs.size();i++){
			dataBus = rs.get(i);
			jldw_cn_mc = dataBus.getValue("jldw_cn_mc");
			gzZbJldwContext = new GzZbJldwContext();
			gzZbJldwContext.addRecord("select-key", dataBus);
			table.executeFunction( "selectSjfx", gzZbJldwContext, "record", "record" );
			countBus = gzZbJldwContext.getRecord("record");
			count = countBus.getValue("count");
			
			if(count!=null&&!count.equals("0")){
				errorString+=jldw_cn_mc+";";
			}			
			log_desc+="," + jldw_cn_mc;
		}
		if(!errorString.equals("")){
			errorString = errorString.substring(0,errorString.length()-1);
			throw new TxnDataException("","������λ:" + errorString + " �ѱ�ʹ�ã�����ɾ��;");
		}
		// ɾ����¼�������б� VoGzDmJcdmPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		 table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );	
		 if(!log_desc.equals("")) log_desc = log_desc.substring(1);
		 setBizLog("ɾ��������λ��", context,log_desc);
	}
		
	
	
	/**
	 * �жϼ�����λ�ܷ�ɾ��
	 * ɾ���������ü�����λû�б�ʹ��
	 * @param context
	 * @param jgid
	 * @return
	 * @throws TxnException
	 */
	private boolean isCanDelete(TxnContext context,String jldwdm) throws TxnException
	{
		boolean iscan=false;
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		int i,m,n;
		//��ѯѡ�еļ�����λ��ָ������Ƿ��й���
		m=table.executeFunction("iscandelete", context, inputNode, outputNode );
		System.out.println(m+"m is here");
		//��ѯѡ�еļ�����λ��ָ�����Թ��������Ƿ��й���
		n=table.executeFunction("iscandelete2", context, inputNode, outputNode );
		System.out.println(n+"n is here");
		i=m+n;
		System.out.println(i+"i is here");
		if(i==0)
		{
			iscan=true;
		}
		return iscan;			
	}
	
	/**
	 * ��¼��־
	 * @param type
	 * @param context
	 */
    private void setBizLog (String type,TxnContext context,String jgmc){
    	
    	context.getRecord("biz_log").setValue("desc", type + jgmc);
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
		GzZbJldwContext appContext = new GzZbJldwContext( context );
		invoke( method, appContext );
	}
}
