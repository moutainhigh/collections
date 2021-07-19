package com.gwssi.dw.metadata.basecode.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnMasterTable;

import com.gwssi.dw.metadata.basecode.vo.GzDmJcdmContext;
import com.gwssi.dw.metadata.basecode.vo.GzDmJcdmFxContext;

public class TxnGzDmJcdmFx extends TxnMasterTable
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnGzDmJcdmFx.class, GzDmJcdmFxContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "gz_dm_jcdm_fx";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select gz_dm_jcdm_fx list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one gz_dm_jcdm_fx";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one gz_dm_jcdm_fx";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one gz_dm_jcdm_fx";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one gz_dm_jcdm_fx";

	// ��ϸ��
	private static final String detailTables[][] = {
		
		// ������Ϊ�˴������һ�� [,]������ɾ��
		{ null, null }
	};
	
	/**
	 * ���캯��
	 */
	public TxnGzDmJcdmFx()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ������������б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn3010101( GzDmJcdmFxContext context ) throws TxnException
	{
		Attribute.setPageRow(context, outputNode, 20);
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoGzDmJcdmFxSelectKey selectKey = context.getSelectKey( inputNode );
		//loadMasterTable( table, ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoGzDmJcdmFx result[] = context.getGzDmJcdmFxs( outputNode );
		//ģ����ѯ
		//table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		//��ģ����ѯ
		table.executeFunction( "rightQuery", context, inputNode, outputNode );
		// �ӱ�
//		for( int ii=0; ii<detailTables.length; ii++ ){
//			String detail[] = detailTables[ii];
//			if( detail[0] == null || detail[0].length() == 0 ){
//				continue;
//			}
//			
//			try{
//				loadDetailTable( detail[1], context, inputNode, detail[0] );
//			}
//			catch( Exception e ){
//				log.warn( "������ϸ��[" + detail[0] + "]����", e );
//			}
//		}
	}

	/** �޸Ļ������������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn3010102( GzDmJcdmFxContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );	
		DataBus dataBus = context.getRecord("record");
		String dm = dataBus.getValue("jcsjfx_dm");
		String jc_dm_id = dataBus.getValue("jc_dm_id");
		String jcsjfx_id = dataBus.getValue("jcsjfx_id");
		String jcsjfx_mc = dataBus.getValue("jcsjfx_mc");
		
		GzDmJcdmFxContext oldContext  = new GzDmJcdmFxContext();
		DataBus oldDb = oldContext.getRecord("primary-key");
		oldDb.setValue("jcsjfx_id", jcsjfx_id);		
		table.executeFunction( SELECT_FUNCTION, oldContext, "primary-key", "record" );
		String oldDm = oldContext.getRecord("record").getValue("jcsjfx_dm");
		if(!dm.equals(oldDm)){
			GzDmJcdmFxContext countContext = new GzDmJcdmFxContext();		
			DataBus countDb = countContext.getRecord("record");
			countDb.setValue("jcsjfx_dm", dm);
			countDb.setValue("jc_dm_id", jc_dm_id);
			table.executeFunction( "verifyJcsjfxdm", countContext, "record", "record" );
			DataBus queryCountDb = countContext.getRecord("record");
			String count = queryCountDb.getValue("count");
							
			if(count!=null&&!count.equals("0")){
				throw new TxnDataException("","�������ݷ�����룺"+dm+"�Ѵ����ڸ÷����£��������������룡");
			}
		}
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
		setBizLog("�޸Ļ���������", context,jcsjfx_mc);
	}

	/** ���ӻ������������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn3010103( GzDmJcdmFxContext context ) throws TxnException
	{
		
	    BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
	    
		table.executeFunction( "verifyJcsjfxdm", context, "record", "record" );
		DataBus dataBus = context.getRecord("record");
		String dm = dataBus.getValue("jcsjfx_dm");
		String jcsjfx_mc = dataBus.getValue("jcsjfx_mc");
		String count = dataBus.getValue("count");
		if(count!=null&&!count.equals("0")){
			throw new TxnDataException("","�������ݷ�����룺"+dm+"�Ѵ����ڸ÷����£��������ӣ�");
		}	  
		
	    DataBus old_dataBus = context.getRecord("record");
		String old_fzx_id = old_dataBus.getValue("jcsjfx_fjd");
		table.executeFunction( "selectSx", context, "record", "record" );
		DataBus sxData=context.getRecord("record");
		String xssx=sxData.getValue("xssx");
		if(xssx==null||xssx.equals("")){
	        sxData.setValue("xssx", "1");
		}
		else{
			sxData.setValue("xssx", xssx);
		}
		if(old_fzx_id.equals("")){
			table.executeFunction(INSERT_FUNCTION, context, "record", "record" );				
		}else{				
			GzDmJcdmFxContext old_context = new GzDmJcdmFxContext();
			DataBus old_db = old_context.getRecord("primary-key");
			old_db.setValue("jcsjfx_id", old_fzx_id);
			table.executeFunction(SELECT_FUNCTION, old_context, "primary-key", "record");
			DataBus old_db_record = old_context.getRecord("record");
			old_db_record.setValue("sfmx", "0");
			table.executeFunction(UPDATE_FUNCTION, old_context,inputNode, outputNode );
			table.executeFunction(INSERT_FUNCTION, context, "record", "record"); 
		}
		setBizLog("���ӻ���������", context,jcsjfx_mc);
	}

	/** ��ѯ����������������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn3010104( GzDmJcdmFxContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoGzDmJcdmFxPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoGzDmJcdmFx result = context.getGzDmJcdmFx( outputNode );
	}

	/** ɾ���������������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn3010105( GzDmJcdmFxContext context ) throws TxnException{
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );			
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );	
		DataBus data = context.getRecord(outputNode);
		
		/*String jcsjfx_id = data.getValue("jcsjfx_id");
		String jcsjfx_mc = "";
		//������������Ƿ�ָ������
		GzDmJcdmFxContext queryContext = new GzDmJcdmFxContext();			
		DataBus queryDb = queryContext.getRecord("record");
		queryDb.setValue("jcsjfx_id", jcsjfx_id);			
		table.executeFunction( "selectZb", queryContext, inputNode, outputNode );
		String zbCount = queryContext.getRecord(outputNode).getValue("count");
		if(zbCount!=null&&!zbCount.equals("0")){
			jcsjfx_mc = data.getValue("jcsjfx_mc");
			throw new TxnDataException("999999","��������:"+jcsjfx_mc+"�ѱ�ָ������,����ɾ��!");
		}*/
		
		//������������Ƿ񱻷�������
		/*GzDmJcdmFxContext queryFzContext = new GzDmJcdmFxContext();			
		DataBus queryFzDb = queryFzContext.getRecord("record");
		queryFzDb.setValue("jcsjfx_id", jcsjfx_id);			
		table.executeFunction( "selectFz", queryContext, inputNode, outputNode );
		String fzCount = queryContext.getRecord(outputNode).getValue("count");
		if(fzCount!=null&&!fzCount.equals("0")){
			jcsjfx_mc = data.getValue("jcsjfx_mc");
			throw new TxnDataException("999999","��������:"+jcsjfx_mc+"�ѱ���������,����ɾ��!");
		}*/		
		
//		String fjd = data.getValue("jcsjfx_fjd");			
//		if(fjd!=null&&!fjd.equals("")){
//			table.executeFunction( "selectFjd", context, "record", "record" );
//			DataBus countData=context.getRecord("record");
//			String count=countData.getValue("count");
//			//��������һ���ӽڵ㣬�޸Ľڵ��Ƿ���ϸֵ
//			if(count.equals("1")){
//				
//				GzDmJcdmFxContext up_context = new GzDmJcdmFxContext();
//				DataBus dataBus = up_context.getRecord("primary-key");
//				dataBus.setValue("jcsjfx_id", fjd);
//									
//				table.executeFunction(SELECT_FUNCTION, up_context, "primary-key", "record");				
//				DataBus old_db_record = up_context.getRecord("record");
//				old_db_record.setValue("sfmx", "1");
//				table.executeFunction(UPDATE_FUNCTION, up_context,"record", outputNode );
//			}
//		} 
		Recordset rs = context.getRecordset("select-key");
		DataBus dataBus = null;
		String log_desc = "";
		String fx_mc = "";
		for(int i=0; i<rs.size();i++){
			dataBus = rs.get(i);
			fx_mc = dataBus.getValue("jcsjfx_mc");	
			log_desc+="," + fx_mc;
		}
		if(!log_desc.equals("")) log_desc = log_desc.substring(1);		
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );	
		setBizLog("ɾ������������", context,log_desc);
	}
	
	public void txn3010106( GzDmJcdmFxContext context ) throws TxnException
	{
		 
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow( context, outputNode, -1 );
		table.executeFunction( "getFirstNode", context, inputNode, outputNode );
		// System.out.println(context);
		Recordset rs = context.getRecordset("record");
		for (int i=0; i < rs.size(); i++)
		{
			DataBus db = rs.get(i);
			db.setValue("jcsjfx_mc", db.getValue("jcsjfx_mc")+"("+db.getValue("jcsjfx_dm")+")");
		}
		try {
			setExpand(context);
		} catch (Exception e) {
			e.printStackTrace();
		}		

	}
	
	/** ��ȡ�ڶ��㼰�Ժ�ڵ�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn3010107( GzDmJcdmFxContext context ) throws TxnException
	{
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );	
		Attribute.setPageRow( context, outputNode, -1 );
		table.executeFunction( "getSecondNode", context, inputNode, outputNode );
		Recordset rs = context.getRecordset("record");
		for (int i=0; i < rs.size(); i++)
		{
			DataBus db = rs.get(i);
			db.setValue("jcsjfx_mc", db.getValue("jcsjfx_mc")+"("+db.getValue("jcsjfx_dm")+")");
		}
		setExpand(context);
	}
	
	public void txn3010108( GzDmJcdmFxContext context ) throws TxnException
	{
//		System.out.println("************===="+context);
//		 DataBus ztDataBus=context.getRecord("select-key");
//		 String sy_fl_zt=ztDataBus.getValue("sy_fl_zt");
//		 if(sy_fl_zt.equals("0"))
//			 
//		 {
//			 throw new TxnDataException("","��ͣ�õĽڵ㲻�ܽ��д˲�����");
//			 
//		 }else {
//			 
//			
//		 }
	}
	
	private void setExpand( GzDmJcdmFxContext context ){
		Recordset rs = null;
		try {
			rs = context.getRecordset("record");
		} catch (TxnException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < rs.size(); i ++)
		{
			DataBus dataBus = rs.get(i);
			String sf_mx = dataBus.getValue("sfmx");
			
			if (sf_mx.equals("0")){
				
				//dataBus.setValue("expand", "false");
			}
			else
			{
				dataBus.setValue("expand", "true");
			}
		}
	}	

	/**
	 * ���������ڵ����ʾ˳��
	 * 1���õ�����λ�õĻ��������������ʾ��š�
	 * 2����������������ͬ�Ļ������ݼ�¼��ʾ��š�
	 * @param context
	 * @throws TxnException
	 */
	public void txn3010110( GzDmJcdmFxContext context ) throws TxnException
	{		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		//������
		DataBus dataBus = context.getRecord("select-key");
		String first_jcsjfx_nm = dataBus.getValue("first_jcsjfx_nm");
		String first_xssx = dataBus.getValue("first_xssx");
		String last_jcsjfx_nm = dataBus.getValue("last_jcsjfx_nm");
		String last_xssx = dataBus.getValue("last_xssx");
        
		DataBus db = new DataBus();
		db.setValue("jcsjfx_id", first_jcsjfx_nm);
		context.addRecord("primary-key", db);
		table.executeFunction(SELECT_FUNCTION, context, "primary-key", "record");
		db = context.getRecord("record");
		db.setValue("xssx",last_xssx);

		table.executeFunction(UPDATE_FUNCTION, context, "record", "record");

		db = context.getRecord("primary-key");
		db.setValue("jcsjfx_id", last_jcsjfx_nm);
		table.executeFunction(SELECT_FUNCTION, context, "primary-key", "record");
		db = context.getRecord("record");
		db.setValue("xssx",first_xssx);
		table.executeFunction(UPDATE_FUNCTION, context, "record", "record");	
	}
	
	
	/** ģ����ѯ����������Ϣ������ָ�����Ĳ�ѯ�������ݽڵ�
	 *  �Ի����������ڵ���ʾ���ƽ���ģ��ƥ���ѯ
	 *  1�����ݲ�ѯ�������ڵ�·��
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn3010109( GzDmJcdmFxContext context ) throws TxnException
	{	
		//System.out.println(context);
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );		
		Attribute.setPageRow( context, outputNode, -1 );
		table.executeFunction( "getSerchNode", context, inputNode, outputNode );
		Recordset rs = context.getRecordset("record");

		for(int i =0;i<rs.size();i++)
		{
			DataBus db = rs.get(i);
			String jcsjfx_fjd = db.getValue("jcsjfx_fjd");
			String jcsjfx_lj ="/"+ db.getValue("jcsjfx_id");
			while (jcsjfx_fjd!=null&&!jcsjfx_fjd.equals("")) {
				GzDmJcdmFxContext new_Context = new GzDmJcdmFxContext();
				DataBus newDB = new DataBus();
				newDB.setValue("jcsjfx_id", jcsjfx_fjd);
				new_Context.addRecord("primary-key", newDB);
				table.executeFunction(SELECT_FUNCTION, new_Context,"primary-key", "record");
				Recordset TempRs = new_Context.getRecordset("record");
				jcsjfx_fjd = "";
				String temp_jcsjfx_id = "";
				for(int j =0;j<TempRs.size();j++)
				{
					DataBus tempDb = TempRs.get(j);
					jcsjfx_fjd = tempDb.getValue("jcsjfx_fjd");
					temp_jcsjfx_id = tempDb.getValue("jcsjfx_id");
				}
				if(temp_jcsjfx_id!=null&&!temp_jcsjfx_id.equals("")){
					jcsjfx_lj = "/"+temp_jcsjfx_id + jcsjfx_lj;
				}
			}
			db.setValue("jcsjfx_lj", jcsjfx_lj);
		}
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
		GzDmJcdmFxContext appContext = new GzDmJcdmFxContext( context );
		invoke( method, appContext );
	}
}
