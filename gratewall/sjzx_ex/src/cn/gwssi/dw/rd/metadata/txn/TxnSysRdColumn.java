package cn.gwssi.dw.rd.metadata.txn;

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
import cn.gwssi.dw.rd.metadata.vo.SysRdColumnContext;

public class TxnSysRdColumn extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysRdColumn.class, SysRdColumnContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "sys_rd_column";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select sys_rd_column list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one sys_rd_column";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one sys_rd_column";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one sys_rd_column";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one sys_rd_column";
	
	/**
	 * ���캯��
	 */
	public TxnSysRdColumn()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ��������ֶα��б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn80002501( SysRdColumnContext context ) throws TxnException
	{
		
		DataBus db = context.getRecord(inputNode);
		String column_code = db.getValue("column_code");
		if(column_code!=null && !"".equals(column_code)){
			column_code = column_code.toUpperCase();
			db.setValue("column_code", column_code);
		}
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysRdColumnSelectKey selectKey = context.getSelectKey( inputNode );
		String show_all = context.getRecord(inputNode).getValue("show_all");
		if(show_all!=null&&!show_all.equals("")){
			Attribute.setPageRow(context, outputNode, -1);
		}
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		Recordset rs = context.getRecordset(outputNode);
		//ҳ����Ϊ����ʾ�Ƿ�����һ�η�ת������ʱ����һ�Ρ�
		for(int i=0;i<rs.size();i++)
		{
			if(rs.get(i).getValue("use_type").equals("0"))
				rs.get(i).setValue("use_type","1");
			else if(rs.get(i).getValue("use_type").equals("1"))
				rs.get(i).setValue("use_type","0");
		}
		

		// ��ѯ���ļ�¼�� VoSysRdColumn result[] = context.getSysRdColumns( outputNode );
	}
	
	/** ��ѯ��������ֶα��б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn80003501( SysRdColumnContext context ) throws TxnException
	{
		Attribute.setPageRow(context, outputNode, 10);
		callService("80002501", context);
	}
	
	/** ��ѯ��������ֶα��б��������ýӿڵ�������
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn80002507( SysRdColumnContext context ) throws TxnException
	{
		
		
		/*String column_code = db.getValue("column_code");
		if(column_code!=null && !"".equals(column_code)){
			column_code = column_code.toUpperCase();
			db.setValue("column_code", column_code);
		}*/
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction( "queryColumnType", context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoSysRdColumn result[] = context.getSysRdColumns( outputNode );
	}
	/** �޸���������ֶα���Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn80002502( SysRdColumnContext context ) throws TxnException
	{
		DataBus db = context.getRecord("record");
		//ҳ����Ϊ����ʾ�Ƿ�����һ�η�ת������ʱ����һ�Ρ�
		if(db.getValue("use_type").equals("0"))
			db.setValue("use_type","1");
			else if(db.getValue("use_type").equals("1"))
			db.setValue("use_type","0");
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoSysRdColumn sys_rd_column = context.getSysRdColumn( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ������������ֶα���Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn80002503( SysRdColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoSysRdColumn sys_rd_column = context.getSysRdColumn( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ��������ֶα������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn80002504( SysRdColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysRdColumnPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoSysRdColumn result = context.getSysRdColumn( outputNode );
	}
	
	/** ɾ����������ֶα���Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn80002505( SysRdColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoSysRdColumnPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ��������ֶα����ڲ鿴
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn80002506( SysRdColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysRdColumnPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoSysRdColumn result = context.getSysRdColumn( outputNode );
	}
		
	/** ��ѯ����Ԫ�����б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn8000601( SysRdColumnContext context ) throws TxnException
	{
		DataBus db = context.getRecord(inputNode);
		String column_code = db.getValue("column_code");
		if(column_code!=null && !"".equals(column_code)){
			column_code = column_code.toUpperCase();
			db.setValue("column_code", column_code);
		}
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String column_name = context.getRecord(inputNode).getValue("column_name");
		if(column_name !=null && !"".equals(column_name)){
			table.executeFunction( "queryDistinctColumnListByName", context, inputNode, outputNode );
		}else{
			table.executeFunction( "queryDistinctColumnListByCode", context, inputNode, outputNode );
		}
		Recordset rs = context.getRecordset(outputNode);
		for(int i=0;i<rs.size();i++){
			DataBus temp = (DataBus)rs.get(i);
			TxnContext ctx = new TxnContext();
			if(column_name!=null&&!column_name.equals("")){
				table.executeFunction( "queryColumnListByName", ctx, temp, "ListDB" );
			}else{
				table.executeFunction( "queryColumnListByCode", ctx, temp, "ListDB" );
			}
			Recordset rst = ctx.getRecordset("ListDB");
			String column_n = "";
			String column_cn = "";
			String content = "";
			StringBuffer codeBuffer = new StringBuffer();
			StringBuffer contentBuffer = new StringBuffer();
			for(int j=0;j<rst.size();j++){
				DataBus ListTemp = (DataBus)rst.get(j);
				if(column_name !=null && !"".equals(column_name)){
					column_n = ListTemp.getValue("column_name");
					if(ListTemp.getValue("column_code")!=null && !"".equals(ListTemp.getValue("column_code")) && codeBuffer.toString().indexOf(ListTemp.getValue("column_code")) < 0){
						codeBuffer.append(";");
						codeBuffer.append(ListTemp.getValue("column_code"));
					}
					contentBuffer.append(";");
					contentBuffer.append(ListTemp.getValue("table_name"));
					contentBuffer.append("(");
		            contentBuffer.append(ListTemp.getValue("table_code"));
					contentBuffer.append(")");
				}else{
					column_n = ListTemp.getValue("column_code");
					if(ListTemp.getValue("column_name")!=null && !"".equals(ListTemp.getValue("column_name")) && codeBuffer.toString().indexOf(ListTemp.getValue("column_name")) < 0){
						codeBuffer.append(";");
						codeBuffer.append(ListTemp.getValue("column_name"));
					}
					contentBuffer.append(";");
					contentBuffer.append(ListTemp.getValue("table_name"));
					contentBuffer.append("(");
					contentBuffer.append(ListTemp.getValue("table_code"));
					contentBuffer.append(")");
					}
				}
				column_cn = codeBuffer.toString();
			    content = contentBuffer.toString();
			if(column_cn !=null && !"".equals(column_cn)){
				column_cn = codeBuffer.toString().substring(1);
				column_cn = column_cn.replaceAll("\r", "");
				column_cn = column_cn.replaceAll("\n", "");
			}
			if(content !=null && !"".equals(content)){
				content = contentBuffer.toString().substring(1);
				content = content.replaceAll("\r", "");
				content = content.replaceAll("\n", "");
			}

			if(column_name !=null && !"".equals(column_name)){
				temp.setValue("column_code", column_cn);
				temp.setValue("column_name", column_n);
				temp.setValue("content", content);
			}else{
				temp.setValue("column_code", column_n);
				temp.setValue("column_name", column_cn );
				temp.setValue("content", content);
			}
			
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
		SysRdColumnContext appContext = new SysRdColumnContext( context );
		invoke( method, appContext );
	}
}
