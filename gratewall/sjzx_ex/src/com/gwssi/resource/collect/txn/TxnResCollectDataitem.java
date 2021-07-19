package com.gwssi.resource.collect.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.util.CalendarUtil;
import com.gwssi.common.util.UuidGenerator;
import com.gwssi.resource.collect.vo.ResCollectDataitemContext;

public class TxnResCollectDataitem extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnResCollectDataitem.class, ResCollectDataitemContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "res_collect_dataitem";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select res_collect_dataitem list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one res_collect_dataitem";
	
	
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one res_collect_dataitem";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one res_collect_dataitem";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one res_collect_dataitem";
	
	/**
	 * ���캯��
	 */
	public TxnResCollectDataitem()
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
	 * 
	 * txn20202000(��������������Ƿ�����ʹ��)    

	 * @param context
	 * @throws TxnException        
	 * void       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public void txn20202000(ResCollectDataitemContext context) throws TxnException
	{
		
		String dataitem_name_en = context.getRecord("select-key").getValue("dataitem_name_en");//����������
		String collect_table_id = context.getRecord("select-key").getValue("collect_table_id");//���ݱ�ID
		String collect_dataitem_id = context.getRecord("select-key").getValue("collect_dataitem_id");//������ID
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME);
		System.out.println(dataitem_name_en + " == " + collect_table_id);
		StringBuffer sql= new StringBuffer();
		sql.append("select count(*) as name_nums from res_collect_dataitem t where t.is_markup='"+ExConstant.IS_MARKUP_Y+"'");
		if(StringUtils.isNotBlank(dataitem_name_en)){
			sql.append(" and t.dataitem_name_en = '"+dataitem_name_en.toUpperCase()+"'");
		}
		
		if(StringUtils.isNotBlank(collect_table_id)){
			sql.append(" and t.collect_table_id = '"+collect_table_id+"'");
		}
		if(StringUtils.isNotBlank(collect_dataitem_id)){
			sql.append(" and t.collect_dataitem_id != '"+collect_dataitem_id+"'");
		}
		System.out.println("��ѯ�����������Ƿ�����ʹ��sql=========="+sql.toString());
		table.executeRowset( sql.toString(), context, outputNode);
	
	}
	
	
	/** 
	 * ��ѯ�ɼ���������б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn20202001( ResCollectDataitemContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 
	 * �޸Ĳɼ����������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn20202002( ResCollectDataitemContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		 String dataitem_name_en=context.getRecord("record").getValue("dataitem_name_en");
		 if(dataitem_name_en!=null&&!"".equals(dataitem_name_en)){
			 dataitem_name_en=dataitem_name_en.toUpperCase();
			 context.getRecord("record").setValue("dataitem_name_en",dataitem_name_en);// ���ֶ�Ӣ������
		 }
		String is_code=context.getRecord("record").getValue("code_table");
		context.getRecord("record").setValue("last_modify_id",context.getRecord("oper-data").getValue("userID"));//����޸���ID
		context.getRecord("record").setValue("last_modify_time", CalendarUtil.getCurrentDateTime());//����޸�ʱ��
		if(is_code!=null&&is_code.equals("00")){
			context.getRecord("record").setValue("is_code_table","0");//���Ǵ����
		}else{
			context.getRecord("record").setValue("is_code_table","1");//�Ǵ����
		}
		String is_key=context.getRecord("record").getValue("is_key");
		if(is_key==null||is_key.equals("")){
			context.getRecord("record").setValue("is_key","0");//��������
		}
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 
	 * ���Ӳɼ����������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn20202003( ResCollectDataitemContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String dataitem_name_en=context.getRecord("record").getValue("dataitem_name_en");
		if(StringUtils.isNotBlank(dataitem_name_en)){
			 dataitem_name_en=dataitem_name_en.toUpperCase();
			 context.getRecord("record").setValue("dataitem_name_en",dataitem_name_en);// ���ֶ�Ӣ������
		}
		String id = UuidGenerator.getUUID();
		context.getRecord("record").setValue("collect_dataitem_id", id);//������ID
		//context.getRecord("record").setValue("collect_table_id", context.getRecord("record").getValue("collect_table_id"));//���ݱ�ID
		context.getRecord("record").setValue("created_time", CalendarUtil.getCurrentDateTime());//����ʱ��
		context.getRecord("record").setValue("creator_id",context.getRecord("oper-data").getValue("userID"));//������ID
		context.getRecord("record").setValue("is_markup",ExConstant.IS_MARKUP_Y);// ���볣�� ��Ч���
		context.getRecord("record").setValue("last_modify_id",context.getRecord("oper-data").getValue("userID"));//������ID
		context.getRecord("record").setValue("last_modify_time", CalendarUtil.getCurrentDateTime());//������ID
		context.getRecord("record").setValue("dataitem_state", "0");//������Ƿ����ɸ��ֶ�
		
		String is_code=context.getRecord("record").getValue("code_table");
		
		if(StringUtils.isNotBlank(is_code) && !is_code.equals("00")){
			context.getRecord("record").setValue("is_code_table",CollectConstants.IS_CODE_TABLE_Y);//�Ǵ����
		}else{
			context.getRecord("record").setValue("is_code_table",CollectConstants.IS_CODE_TABLE_N);//���Ǵ����
		}
		String is_key=context.getRecord("record").getValue("is_key");
		if(is_key==null||is_key.equals("")){
			context.getRecord("record").setValue("is_key","0");//��������
		}
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 
	 * ��ѯ�ɼ�������������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn20202004( ResCollectDataitemContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		
		context.getRecord("record").setValue("collect_table_id", context.getRecord("primary-key").getValue("collect_table_id"));//�ɼ���ID
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		table.executeFunction("queryTableId", context, inputNode, "tableinfo");
	}
	
	/** 
	 * ɾ���ɼ����������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn20202005( ResCollectDataitemContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		
		//table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
		table.executeFunction( "deleteTableItem", context, inputNode, outputNode );
	}
	/** 
	 * ��ѯ�ɼ�������������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn20202006( ResCollectDataitemContext context ) throws TxnException
	{
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		
		context.getRecord("record").setValue("collect_dataitem_id", context.getRecord("primary-key").getValue("collect_dataitem_id"));//������ID
		context.getRecord("record").setValue("collect_table_id", context.getRecord("primary-key").getValue("collect_table_id"));//���ݱ�ID
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		
		table.executeFunction("queryTableId", context, inputNode, "tableinfo");//���ݱ���Ϣ
	}
	/** 
	 * ��ѯ�ɼ����������������
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn20202007( ResCollectDataitemContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME);
		
		table.executeFunction("queryTableId", context, inputNode, "tableinfo");//���ݱ���Ϣ
		table.executeFunction("queryTableId", context, inputNode, outputNode);//�������б�
	}
	
	/** 
	 * ��ѯ�ɼ����������������
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn20202008( ResCollectDataitemContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME);
		String id = UuidGenerator.getUUID();
		context.getRecord("record").setValue("collect_dataitem_id", id);//������ID
		
		context.getRecord("record").setValue("collect_table_id", context.getRecord("record").getValue("collect_table_id"));//���ݱ�ID
		context.getRecord("record").setValue("dataitem_name_en", context.getRecord("record").getValue("dataitem_name_en").toUpperCase());//����������
		context.getRecord("record").setValue("dataitem_name_cn", context.getRecord("record").getValue("dataitem_name_cn"));//��������������
		context.getRecord("record").setValue("dataitem_type", context.getRecord("record").getValue("dataitem_type"));//����������
		context.getRecord("record").setValue("dataitem_long", context.getRecord("record").getValue("dataitem_long"));//�������
		context.getRecord("record").setValue("is_key", context.getRecord("record").getValue("is_key"));//�Ƿ�����
		context.getRecord("record").setValue("dataitem_long_desc", context.getRecord("record").getValue("dataitem_long_desc"));//����������
		context.getRecord("record").setValue("created_time",CalendarUtil.getCurrentDateTime());//����ʱ��
		context.getRecord("record").setValue("creator_id",context.getRecord("oper-data").getValue("userID"));//������ID
		context.getRecord("record").setValue("is_markup",ExConstant.IS_MARKUP_Y);// ���볣��//��Ч���
		
		//context.getRecord("record").setValue("last_modify_id",context.getRecord("oper-data").getValue("userID"));
		//context.getRecord("record").setValue("last_modify_time", CalendarUtil.getCurrentDateTime());
		
		String is_code=context.getRecord("record").getValue("code_table");
		if(is_code!=null&&is_code.equals("01")){
			context.getRecord("record").setValue("is_code_table","1");//�Ǵ����
		}else{
			context.getRecord("record").setValue("is_code_table","0");//���Ǵ����
		}
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode);
	
	}
	/** 
	 * �޸Ĳɼ��������
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn20202009( ResCollectDataitemContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME);
	
		context.getRecord("record").setValue("collect_dataitem_id", context.getRecord("record").getValue("collect_dataitem_id"));//������ID
		context.getRecord("record").setValue("collect_table_id", context.getRecord("record").getValue("collect_table_id"));//���ݱ�ID
		context.getRecord("record").setValue("dataitem_name_en", context.getRecord("record").getValue("dataitem_name_en"));//����������
		context.getRecord("record").setValue("dataitem_name_cn", context.getRecord("record").getValue("dataitem_name_cn"));//��������������
		context.getRecord("record").setValue("dataitem_type", context.getRecord("record").getValue("dataitem_type"));//����������
		context.getRecord("record").setValue("dataitem_long", context.getRecord("record").getValue("dataitem_long"));//�������
		context.getRecord("record").setValue("is_key", context.getRecord("record").getValue("is_key"));//�Ƿ�����
		context.getRecord("record").setValue("dataitem_long_desc", context.getRecord("record").getValue("dataitem_long_desc"));//����������
		context.getRecord("record").setValue("is_markup",ExConstant.IS_MARKUP_Y);// ���볣��//��Ч���
	
		context.getRecord("record").setValue("last_modify_id",context.getRecord("oper-data").getValue("userID"));//����޸���ID
		context.getRecord("record").setValue("last_modify_time", CalendarUtil.getCurrentDateTime());//����޸�ʱ��
		
		String is_code=context.getRecord("record").getValue("code_table");
		if(is_code!=null&&is_code.equals("01")){
			context.getRecord("record").setValue("is_code_table","1");//�Ǵ����
		}else{
			context.getRecord("record").setValue("is_code_table","0");//���Ǵ����
		}
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	
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
		ResCollectDataitemContext appContext = new ResCollectDataitemContext( context );
		invoke( method, appContext );
	}
}
