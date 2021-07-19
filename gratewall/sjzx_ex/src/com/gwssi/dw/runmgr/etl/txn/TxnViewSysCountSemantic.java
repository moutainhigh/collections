package com.gwssi.dw.runmgr.etl.txn;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.database.DBOperation;
import com.gwssi.common.database.DBOperationFactory;
import com.gwssi.common.util.UuidGenerator;
import com.gwssi.dw.runmgr.etl.vo.ViewSysCountSemanticContext;

public class TxnViewSysCountSemantic extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnViewSysCountSemantic.class, ViewSysCountSemanticContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "view_sys_count_semantic";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "${mod.function.select}";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "${mod.function.update}";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "${mod.function.insert}";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "${mod.function.delete}";
	
	
	private static Map CLASS_SORT_MAP = new HashMap();
	private static Map CLASS_STATE_MAP = new HashMap();
	
	static{
		DBOperation operation = DBOperationFactory.createTimeOutOperation();
		try {
			List codeList = operation.select("select f.jcsjfx_dm, f.jcsjfx_mc from gz_dm_jcdm_fx f, gz_dm_jcdm t where t.jc_dm_dm='CA91' and f.jc_dm_id=t.jc_dm_id");
			for(int j = 0; j < codeList.size(); j++){
				Map m = (Map)codeList.get(j);
				CLASS_SORT_MAP.put(m.get("JCSJFX_DM"), m.get("JCSJFX_MC"));
			}
			
			codeList = operation.select("select f.jcsjfx_dm, f.jcsjfx_mc from gz_dm_jcdm_fx f, gz_dm_jcdm t where t.jc_dm_dm='DFCA12' and f.jc_dm_id=t.jc_dm_id");
			for(int j = 0; j < codeList.size(); j++){
				Map m = (Map)codeList.get(j);
				CLASS_STATE_MAP.put(m.get("JCSJFX_DM"), m.get("JCSJFX_MC"));
			}
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ���캯��
	 */
	public TxnViewSysCountSemantic()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ��������ѯ�б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn81200001( ViewSysCountSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		//Attribute.setPageRow(context, outputNode, -1);
		String countDate = context.getRecord(inputNode).getValue("count_date");
		if(countDate == null){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			context.getRecord(inputNode).setValue("count_date", format.format(new java.util.Date()));
		}
		
		table.executeFunction( "queryCountList", context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoViewSysCountSemantic result[] = context.getViewSysCountSemantics( outputNode );
		setCodeDesc(context);
	}
	
	/**
	 * ��ѯ��ʷ������ѯ�б�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn81200002( ViewSysCountSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow(context, outputNode, -1);
		String year = context.getRecord(inputNode).getValue("year");
		if(year != null){
			String month = context.getRecord(inputNode).getValue("month");
			context.getRecord(inputNode).setValue("count_year_from", year + "-" + month + "-01");
			context.getRecord(inputNode).setValue("count_year_to", year + "-" + month + "-31");
		}else{
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -30);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String from = format.format(cal.getTime());
			String to = format.format(new Date());
			context.getRecord(inputNode).setValue("count_year_from", from);
			context.getRecord(inputNode).setValue("count_year_to", to);
		}
		if(context.getRecord(inputNode).getValue("show_full") == null){
			context.getRecord(inputNode).setValue("show_full", ""+0);
		}
		
		// �޸ļ�¼������ VoViewSysCountSemantic view_sys_count_semantic = context.getViewSysCountSemantic( inputNode );
		table.executeFunction( "queryHisCountList", context, inputNode, outputNode );
		setCodeDesc(context);
	}
	
	/** ��ȡ���ݱ�Ԥ����Ϣ�б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn81200003( ViewSysCountSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoViewSysCountSemantic view_sys_count_semantic = context.getViewSysCountSemantic( inputNode );
		table.executeFunction( "queryIncreValue", context, inputNode, outputNode );
//		Recordset rs = context.getRecordset(outputNode);
//		while(rs.hasNext()){
//			DataBus db = (DataBus)rs.next();
//			db.setValue("con1", "<");
//			db.setValue("con2", "<");
//		}
		setCodeDesc(context);
	}
	
	/** ��ѯԤ�����������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn81200004( ViewSysCountSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoViewSysCountSemanticPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( "querySysCountIncre", context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoViewSysCountSemantic result = context.getViewSysCountSemantic( outputNode );
	}
	
	/** �޸ģ�����Ԥ��������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn81200005( ViewSysCountSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoViewSysCountSemanticPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		//table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
		StringBuffer sql = new StringBuffer();
		String valueId = context.getRecord(inputNode).getValue("value_id");
		String tableClassId = context.getRecord(inputNode).getValue("table_class_id");
		String minValue = context.getRecord(inputNode).getValue("min_value");
		String maxValue = context.getRecord(inputNode).getValue("max_value");
		String inOutSign = context.getRecord(inputNode).getValue("in_out_sign");
		if(inOutSign == null || inOutSign.trim().equals("")){
			inOutSign = "0";
		}
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
		String person = context.getOperData().getValue("oper-name");
		if(valueId.trim().equals("")){
			sql.append("INSERT INTO SYS_INCRE_VALUE_SEMANTIC (VALUE_ID,TABLE_CLASS_ID,MIN_VALUE,MAX_VALUE,IN_OUT_SIGN,UPDATE_DATE,UPDATE_PERSON) VALUES('")
			.append(UuidGenerator.getUUID()).append("','")
			.append(tableClassId).append("',")
			.append(minValue).append(",")
			.append(maxValue).append(",'")
			.append(inOutSign).append("','")
			.append(date).append("','") 
			.append(person).append("')");
			//System.out.println("sql:>>>"+sql);
			table.executeUpdate(sql.toString());
		}else{
			sql = new StringBuffer();
			sql.append("UPDATE SYS_INCRE_VALUE_SEMANTIC SET MIN_VALUE=")
			.append(minValue).append(", MAX_VALUE=")
			.append(maxValue).append(",UPDATE_DATE='")
			.append(date).append("',UPDATE_PERSON='")
			.append(person).append("',")
			.append("in_out_sign='").append(inOutSign).append("'")
			.append(" WHERE VALUE_ID='").append(valueId).append("'");
			//System.out.println("sql:>>>"+sql);
			table.executeUpdate(sql.toString());
		}
	}
	
	private void setCodeDesc(ViewSysCountSemanticContext context){
		try {
			Recordset rs = context.getRecordset(outputNode);
			while(rs.hasNext()){
				DataBus db = (DataBus)rs.next();
				String sort = db.getValue("class_sort");
				if(sort != null && !sort.trim().equals("")){
					db.setValue("class_sort", (String)CLASS_SORT_MAP.get(sort.toUpperCase()));
				}
				String state = db.getValue("class_state");
				if(state != null && !state.trim().equals("")){
					db.setValue("class_state", (String)CLASS_STATE_MAP.get(state.toUpperCase()));
				}
			}
		} catch (TxnException e) {
			e.printStackTrace();
		}
	}
	
	/** ������������ѯ�б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn81200006( ViewSysCountSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow(context, outputNode, -1);
		String countDate = context.getRecord(inputNode).getValue("count_date");
		if(countDate == null){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			context.getRecord(inputNode).setValue("count_date", format.format(new java.util.Date()));
		}
		
		table.executeFunction( "queryCountList", context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoViewSysCountSemantic result[] = context.getViewSysCountSemantics( outputNode );
		setCodeDesc(context);
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
		ViewSysCountSemanticContext appContext = new ViewSysCountSemanticContext( context );
		invoke( method, appContext );
	}
}
