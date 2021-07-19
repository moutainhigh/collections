package com.gwssi.sysmgr.priv.func.txn;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.iface.DaoTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

public class TxnFuncinfo extends TxnService
{
   // ���ݱ�����
   private final String TABLE_NAME = "funcinfo";

   // ��ѯ�б�
   private final String ROWSET_FUNCTION = "select funcinfo list";

   // ��ѯ��¼
   private final String SELECT_FUNCTION = "select one funcinfo";

   // �޸ļ�¼
   private final String UPDATE_FUNCTION = "update one funcinfo";

   // ���Ӽ�¼
   private final String INSERT_FUNCTION = "insert one funcinfo";

   // ɾ����¼
   private final String DELETE_FUNCTION = "delete one funcinfo";
   
   // �ƶ����ܽڵ�
   private final String MOVE_FUNCTION = "update funcinfo parentcode";
   
   private  boolean bool = false;

   public TxnFuncinfo()
   {
      
   }

   /** ��ʼ������
    @param context ����������
    @throws cn.gwssi.common.component.exception.TxnException
    */
   protected void prepare(TxnContext context) throws TxnException
   {
      
   }

   /** ��ѯ�б�
    @param context ����������
    @throws cn.gwssi.common.component.exception.TxnException
    */
	public void txn980301( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow( context, outputNode, -1 );
		table.executeFunction( "getGnqxInUse", context, inputNode, outputNode );
		//table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
	}

   /** �޸ļ�¼
    @param context ����������
    @throws cn.gwssi.common.component.exception.TxnException
    */
	public void txn980302( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}

   /** ���Ӽ�¼
    @param context ����������
    @throws cn.gwssi.common.component.exception.TxnException
    */
	public void txn980303( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}

   /** ��ѯ��¼
    @param context ����������
    @throws cn.gwssi.common.component.exception.TxnException
    */
	public void txn980304( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
	}

   /** ɾ����¼
    @param context ����������
    @throws cn.gwssi.common.component.exception.TxnException
    */
	public void txn980305( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}


	/** �ƶ��ڵ�/�޸��ϼ��ڵ�Ĺ��ܴ���
	 @param context ����������
	 @throws cn.gwssi.common.component.exception.TxnException
	 */
	public void txn980307( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( MOVE_FUNCTION, context, inputNode, outputNode );
	}

	
	/** ���������б�
    @param context ����������
    @throws cn.gwssi.common.component.exception.TxnException
    */
	public void txn980308( TxnContext context ) throws TxnException
	{
		Functxn2Htmls functxnDocument = new Functxn2Htmls();
		String result = functxnDocument.createDocument( context );
		
		// ��������
		DataBus outputData = context.getRecord( outputNode );
		outputData.setValue( "sqldata", result );
	}

	/** ����������Ϣ
	 @param context ����������
	 @throws TxnException
	 */
	public void txn980309( TxnContext context ) throws TxnException
	{
		// ����Where���
		DataBus inputData = context.getRecord( inputNode );
		String funcCode = inputData.getValue( "funccode" );
		if( funcCode == null || funcCode.length() == 0 ){
			return;
		}
		
		String whereClause = "funccode='" + funcCode + "'";
		
		// ȡ���ݱ����
		DaoTable infoTable = TableFactory.getInstance().getTableConfig( TABLE_NAME );
		DaoTable txnTable = TableFactory.getInstance().getTableConfig( "functxn" );
		
		// �������
		String s1 = infoTable.exportSqlData( whereClause );
		String s2 = txnTable.exportSqlData( whereClause );
		
		// ��������
		DataBus outputData = context.getRecord( outputNode );
		outputData.setValue( "sqldata", s1 + s2 );
	}
	/**
	 * ��ѯ����һ��ڵ�
	 * @param context
	 * @throws TxnException
	 */
	public void txn9803101( TxnContext context ) throws TxnException{
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow( context, outputNode, -1 );
		table.executeFunction( "getFirstNode", context, inputNode, outputNode );
		
		String roleid = context.getRecord("select-key").getValue("roleid");	
		String funccode = "";
		DataBus db = null;
		for(int i=0;i<context.getRecordset(outputNode).size();i++){
			db = context.getRecordset(outputNode).get(i);		
			funccode = db.getValue("funccode");
			this.valContext(roleid,funccode,table);
			if(!isBool()){ 
				db.clear();
			}
		}
	}
	/**
	 * ��ѯ�ӽڵ�
	 * @param context
	 * @throws TxnException
	 */
	public void txn9803102( TxnContext context ) throws TxnException{
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow( context, outputNode, -1 );
		table.executeFunction( "getSecondNodes", context, inputNode, outputNode );	
		String funccode = "";
		//String src = "";
		String roleid = context.getRecord("select-key").getValue("roleid");
		TxnContext valContext = null;
		TxnContext txnContext = null;
		DataBus db = null;
		Recordset contetList = context.getRecordset(outputNode);
		String roleAccIdS = "";
		for(int i=0;i<contetList.size();i++){
			
			db = context.getRecordset(outputNode).get(i);				
			funccode = db.getValue("funccode");	
			this.valContext(roleid,funccode,table);
			if(!isBool()){
				db.clear();
			}else{				
				valContext = new TxnContext(); 
				valContext.getRecord("select-key").setValue("funccode", funccode);
				table.executeFunction( "valTxn", valContext, inputNode, outputNode );
				Recordset queryList = valContext.getRecordset(outputNode);
				
				if(queryList.size()==0){						
//					src = "txn9803103.ajax?select-key:funccode="+funccode+"&select-key:roleid="+roleid;
//					contetList.get(i).setValue("src",src);	
					txnContext = new TxnContext();
					Attribute.setPageRow( txnContext, outputNode, -1 );
					txnContext.getRecord("select-key").setValue("funccode", funccode);
					table.executeFunction( "getTxnByFun", txnContext, inputNode, outputNode );	
					roleAccIdS = getRoleAccIdS(txnContext,roleid,funccode ,table);					
					contetList.get(i).setValue("id",roleAccIdS);				
					contetList.get(i).setValue("expand", "true");
				}
			}		
		}
	}
	
	public void txn9803103( TxnContext context ) throws TxnException{
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow( context, outputNode, -1 );
		table.executeFunction( "getLastNode", context, inputNode, outputNode );
		Recordset list = context.getRecordset(outputNode);		
		String roleaccid = "";
		String txnname = "";
		for(int i=0;i<list.size();i++){
			roleaccid = list.get(i).getValue("roleaccid");
			txnname = list.get(i).getValue("txnname");
			list.get(i).setValue("id",roleaccid);
			list.get(i).setValue("text",txnname);
			list.get(i).setValue("expand", "true");
		}
	}
	/**
	 * �ݹ鷽����������ʾ�Ĺ���
	 * @param roleid
	 * @param funccode
	 * @param table
	 * @throws TxnException
	 */
	private void valContext( String roleid,String funccode, BaseTable table )throws TxnException{
		
		String[] funcList = null;
		TxnContext valContext = null;
		TxnContext dataContext = null;
		TxnContext funListContext = null;
		String sfSjqx = "";
		setBool(false);	
		funListContext = new TxnContext(); 
		funListContext.getRecord("select-key").setValue("roleid", roleid);				
		table.executeFunction( "getFunList", funListContext, inputNode, outputNode );
		String funcListSrc = funListContext.getRecord(outputNode).getValue("funclist");		
		if(funcListSrc==null||funcListSrc.equals("")){
			 setBool(false);
		}else{
			funcList = funcListSrc.split(";");
			valContext = new TxnContext(); 
			Attribute.setPageRow( valContext, outputNode, -1 );
			valContext.getRecord("select-key").setValue("funccode", funccode);
			table.executeFunction( "valTxn", valContext, inputNode, outputNode );
			Recordset queryList = valContext.getRecordset(outputNode);	
			if(queryList.size()==0){				
				sfSjqx = getSjqx(funccode, table);			
				if(sfSjqx!=null&&sfSjqx.equals("1")){
		    		for(int i=0;i<funcList.length;i++){
		            	if(funccode.equals(funcList[i])){
		            		setBool(true);
		            		break;
		            	}
		            } 
                }
			}else{			
		    	for(int j=0;j<queryList.size();j++){
		    		if(!isBool()){
			    		funccode = queryList.get(j).getValue("funccode"); 		
			    		valContext(roleid,funccode,table);
		    		}
		    	} 
			}
		}
	}
	/**
	 * ���һ�����ܵ����н�ɫ����Ȩ�޴���
	 * @param context
	 * @param roleid
	 * @param funccode
	 * @param table
	 * @return
	 * @throws TxnException
	 */
	private String getRoleAccIdS(TxnContext context,String roleid,String funccode,  BaseTable table)throws TxnException{
		
		String roleAccIdS = "";	
		String roleAccId = "";
		String txncode = "";
		TxnContext txnContext = null;
		Recordset txnList = context.getRecordset(outputNode);
		
		for(int j=0;j<txnList.size();j++){
			
			txncode = txnList.get(j).getValue("txncode");
			txnContext = new TxnContext();
			txnContext.getRecord("select-key").setValue("roleid", roleid);
			txnContext.getRecord("select-key").setValue("funccode", funccode);
			txnContext.getRecord("select-key").setValue("txncode", txncode);			
			table.executeFunction( "getRoleAccId", txnContext, inputNode, outputNode );			
			roleAccId = txnContext.getRecord(outputNode).getValue("roleaccid");
			if(roleAccId!=null&&!roleAccId.equals("")){				
				roleAccIdS+="," + roleAccId;
			}
		}
		if(!roleAccIdS.equals("")){
			roleAccIdS = roleAccIdS.substring(1);
		}
		return roleAccIdS;
	}
	
	/**
	 * ��ѯ�����Ƿ���Ҫ����Ȩ��
	 * @param funccode
	 * @param table
	 * @return
	 * @throws TxnException
	 */	
	private String getSjqx(String funccode,  BaseTable table)throws TxnException{
			
		TxnContext dataContext = new TxnContext();
		dataContext.getRecord("select-key").setValue("funccode", funccode);
		table.executeFunction( "getSjqx", dataContext, inputNode, outputNode );	
		String sfSjqx = dataContext.getRecord(outputNode).getValue("sf_sjqx");				
		return sfSjqx;
	}
	
	public boolean isBool(){
		return bool;
	}

	public void setBool(boolean bool){
		this.bool = bool;
	}
}
