/*
 * @Header 
 * @Revision 
 * @Date 20070307
 * 
 * =====================================================
 *  ���������Ŀ��
 * =====================================================
 */
package com.gwssi.sysmgr.choice;

import com.gwssi.common.util.CSDBConfig;
import com.gwssi.common.util.SQLConfig;
import com.gwssi.sysmgr.org.vo.VoXt_zzjg_jg;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.DataBus;

/**
 * ������ɫ�û�ѡ����
 * ʵ�ֻ�����ɫ�û���ѡ��,������ģ��ʹ��,������Ҫ�õ���ѯ����,û����ɾ�ķ���;
 * ������Ϣ���������
 * �����:  cn.gwssi.bjais.dao.JgjsyhDao
 * �޸�����:2007-03-07
 * �޸�ԭ��:�Ӵ���淶�ĽǶ��Ż�����
 * @author ��Ԫ
 * @version 1.0
 * 
 */
public class TxnJgjsyhxz extends TxnService
{
    //�����������õ��ı���,Ϊ����,����涨,��д��������ʾ����
	
   //ϵͳ��֯������
   private static final String XT_ZZJG_JG = "XT_ZZJG_JG";
   //��֯������--DAO��ѯ������
   private static final String selectFunction_ZZJG = "select orgtree list " +
   		"xt_zzjg_jg";
   
   //ϵͳ��֯�����û���
   private static final String XT_ZZJG_YH = "XT_ZZJG_YH" ;        
   //ϵͳ��֯�����û���--DAO��ѯ������
   private static final String selectFunction_ZZJGYH = "select rows for " +
   		"roleTree xt_zzjg_yh";       
   //ϵͳ��֯�����û���--����ڵ���
   private static final String inputNode_ZZJGYH = "findUserInputNode"; 
   //ϵͳ��֯�����û���--����ڵ���
   private static final String outputNode_ZZJGYH = "returnRecordsetName";  
   
   //ϵͳȨ�ޱ�
   private static final String OPERROLE = "OPERROLE" ; 
   //ϵͳȨ�ޱ�--DAO��ѯ������
   private static final String selectFunction_OPERROLE ="select roleids for " +
   		"roleTree operrole";  
   
   //�Զ���SQL,��������ɫ�û���ѯ��
	private static final String yhSQL = "808003-0001";
   
   /**
    ȡstep��init-value�����ò���������ʵ�ʵĲ����޸�
    @param context �����Ӧ�����
    @throws cn.gwssi.common.component.exception.TxnException
    */
   protected void prepare(TxnContext context) throws TxnException
   {
      
   }

    /** �������ݲ�ѯ
     * ���ף�920000 �Ĵ�����
     * ����������壺JgyhxzRowsetForm
     @param context ����������,��������û������ҵ�����
     @throws cn.gwssi.common.component.exception.TxnException
     ʵ�ֹ��̣�
     ��Ϊ�����ͽ�ɫû��ֱ��1�Զ�Ĺ�ϵ������ʵ�ֹ������£�
     1����ѯ�������õ������������ѭ�����������
     2�����������Ը��Ƶ����νڵ��������
     3�����ݻ���ID���õ��û����������û���Ϣ�������
     4��ѭ�������,�ۼ��ַ���,�õ�:�����û�����Ҫ��ɫ�ַ��ܺ������û��Ľ�ɫ�б��ַ��ܣ�
        ע����ʱ�������ַ��ܵ����ݾ��Ǹû����Ľ�ɫ���������ǽ������ز�����(a,b,c,a,a,)
     5����ѯ��ɫ���õ���ɫ�������ѭ��(a,b,c,d)
     6������ROLEID�Ƿ������������ַ�����,�����,�ͽ��ý�ɫ��Ϣ��ӵ����νڵ㣬
        �ý�ɫ���ϼ��ڵ�ID���ڻ���ID
     7������ROLEID��ģ����ѯ�õ��û�����ӵ�иý�ɫ���û��������ѭ��
     8�������û���Ϣ��ӵ����νڵ㣬���û����ϼ��ڵ�ID���ڽ�ɫID
     */
    public void txn808003( TxnContext context ) throws TxnException
    {
    	try{
            //������֯������
        	BaseTable table = TableFactory.getInstance().getTableObject( this, 
            		XT_ZZJG_JG );
            //����DAO��ѯ��������,�õ����������
            table.executeFunction( selectFunction_ZZJG, context, 
            		inputNode, outputNode );
    		Recordset rsJG = context.getRecordset(outputNode);
    		Recordset rs = new Recordset();
            DataBus insertData = null;
    	    DataBus tmp;
            String parentid;  
            if(null == rsJG){
            	rsJG = new Recordset();
            }
            //ѭ�����������,���������Ը��Ƶ����νڵ�������� 
    		for (int i = 0; i < rsJG.size(); i++) {
                //��rsJG�еõ�һ����������
    			tmp = rsJG.get(i);
    			insertData = new DataBus();
    			//�����νṹ�ڵ�ID�ĸ�ֵ
    			insertData.setValue("jgid_pk", tmp.getValue(
    					VoXt_zzjg_jg.ITEM_JGID_PK));
    			/*�����νṹ�ڵ㸸ID�ĸ�ֵ�����sjjgid_fkΪ��0�������轫ֵת��Ϊ""��
    			��Ϊ���̨���οؼ���һ���ڵ�ֻ���ǡ���ֵ*/
    			parentid = tmp.getValue(VoXt_zzjg_jg.ITEM_SJJGID_FK);
    			if( null == parentid){
    				parentid = "";
    			}
    			if (parentid.equalsIgnoreCase("0")) {
                    insertData.setValue("sjjgid_fk", "");
    			}else{
                    insertData.setValue("sjjgid_fk", tmp.getValue(
                    		VoXt_zzjg_jg.ITEM_SJJGID_FK));
                }
    			//�����νṹ�ڵ���ʾ���Ƶĸ�ֵ
    			insertData.setValue("jgmc", tmp.getValue(VoXt_zzjg_jg.ITEM_JGMC));
    			/*�����νṹ�ڵ㱸ע���ݵĸ�ֵ��ORG��ʾ�ýڵ��ǲ��ţ�USER��ʾ�ýڵ����û���
    			ROLE��ʾ�ýڵ��ǡ�Ȩ�ޡ�*/
    			insertData.setValue("type", "ORG");
    			rs.add(insertData);
    			//����˽�з���������֯��ɫ���뵱ǰ��������
    			getRoles(context,tmp.getValue(VoXt_zzjg_jg.ITEM_JGID_PK),rs);
    			insertData = null;
    	    }
            //��������ӵ�context��
    		context.remove(outputNode);
    		context.addRecord(outputNode, rs);    		
    	}catch( TxnException e ){
    		log.error(e);
            throw new TxnErrorException(
                                        ErrorConstant.DATABUS_FORMAT_ERROR,
                                        CSDBConfig
                                        .get("ggkz.jgjsyhxz.jgjsxz.error"));     		
    	}

    }

    /** ��ѯ������ɫ��˽�з���,ʵ�ֽ�ĳһ���������µ���֯��ɫ���뵱ǰ�����������
     * ���״��룺getRoles 
     * ����������壺String , Recordset
     @param String ����ID ,���ݴ�ֵ�õ��û����µ���֯��ɫ��Ϣ
     @param Recordset ��������� ,���õ�����֯��ɫ��Ϣ���뵽�����������
     @throws cn.gwssi.common.component.exception.TxnException
     ʵ�ֹ��̣�
     1�����ݻ���ID���õ��û����������û���Ϣ�����;
     2��ѭ�������,�ۼ��ַ���,�õ�:�����û�����Ҫ��ɫ�ַ��ܺ������û��Ľ�ɫ�б��ַ���;
        ע����ʱ�������ַ��ܵ����ݾ��Ǹû����Ľ�ɫ���������ǽ������ز�����
     3����ѯ��ɫ���õ���ɫ�������ѭ��
     4������ROLEID�Ƿ������������ַ�����,�����,�ͽ��ý�ɫ��Ϣ��ӵ����νڵ㣬
        �ý�ɫ���ϼ��ڵ�ID���ڻ���ID
     */    
    private void getRoles( TxnContext context,String jgid,Recordset rs ) 
    throws TxnException
    {    
        //1.���ݻ���ID���õ��û����������û���Ϣ�����
		DataBus db = new DataBus();
		db.setValue("jgid_fk", jgid);
		context.setValue("findUserInputNode", db);
        BaseTable table = TableFactory.getInstance().getTableObject( this, 
        		XT_ZZJG_YH );
        table.executeFunction( selectFunction_ZZJGYH, context, inputNode_ZZJGYH,
        		outputNode );
        Recordset rsJGYH = context.getRecordset(outputNode);
        DataBus insertData = null;
	    DataBus tmp;    
		String roleIDS = ",";
		String roleID = "";
		String roleName = "";
		if( null == rsJGYH ){
			rsJGYH = new Recordset();
		}
    	//2.ѭ����¼,�ۼ��ַ���,�õ�:�����û���MROLE�ַ��ܺ������û���ROLES�ַ���
		for (int i = 0; i < rsJGYH.size(); i++) {
			//�ӽ�����еõ�һ�������û�����
		    tmp = rsJGYH.get(i);
			insertData = new DataBus();
			//�ۼ���Ҫ��ɫ,��Ϊ��ѯ����֮һ
			//mainRole = mainRole + tmp.getValue("mainrole") +  ",";
			//�ۼӽ�ɫID,��Ϊ��ѯ����֮һ
			roleIDS = roleIDS + tmp.getValue("mainrole") +  "," + 
			tmp.getValue("roleids") + ",";          
	    }
    	//3.ѭ��role��ļ�¼
		//����Ȩ�ޱ�
        table = TableFactory.getInstance().getTableObject( this, OPERROLE );
        //����DAOȨ�ޱ��ѯ����
        table.executeFunction( selectFunction_OPERROLE, context, 
        		inputNode, outputNode );
        Recordset rsOPERROLE = context.getRecordset(outputNode);
        if( null == rsOPERROLE ){
        	rsOPERROLE = new Recordset();
        }
        //ѭ��Ȩ�޽����,���ý�ɫ��Ϣ��ӵ����ͽڵ�
        for (int j=0; j < rsOPERROLE.size(); j++){
        	tmp = rsOPERROLE.get(j);
        	insertData = new DataBus();
        	roleID = tmp.getValue("roleid");
        	roleName = tmp.getValue("rolename");
	        /*4.���ҵ�ǰroleID�Ƿ�������ɫ�ַ��ܻ��ɫID�ַ�����,����,��ʾ�û����µ��û�ӵ��
      	    �ý�ɫ,���û���ӵ�иý�ɫ,�ͽ��ý�ɫ��Ϣ��ӵ����νڵ㣬
      	    �ý�ɫ���ϼ��ڵ�ID���ڻ���ID*/
      	    if(null == roleIDS ){
      		    roleIDS = "";
    	    }
      	    //����ɫ��Ϣ��ӵ����ͽ������
            if ( (roleIDS.indexOf("," + roleID + ",")) > -1 ){
        		insertData.setValue("jgid_pk", jgid + ":" + tmp.getValue("roleid"));
        		insertData.setValue("jgmc", tmp.getValue("rolename"));
        		insertData.setValue("sjjgid_fk",jgid);
         		insertData.setValue("type", "ROLE"); 
        		rs.add(insertData);
        	}
			//���÷���,���û����뵱ǰ��������
			getUsers(context,jgid,roleID,rs);
			insertData = null;
        }
            
      }

    /** �������ݲ�ѯ
     * ���ף�getUsers �Ĵ�����
     * ����������壺jsyhxzRowsetForm
     @param context ����������,��������û������ҵ�����
     @throws cn.gwssi.common.component.exception.TxnException
     ʵ�ֹ��̣�
     1������ROLEID��ģ����ѯ�õ��û�����ӵ�иý�ɫ���û��������ѭ��
     2�������û���Ϣ��ӵ����νڵ㣬���û����ϼ��ڵ�ID���ڽ�ɫID
     */
      private void getUsers( TxnContext context,String jgid, String roleID,
        		Recordset rs ) throws TxnException
	  {
          DataBus tmp;
          DataBus insertData = null;
          //1.����roleid�õ��ý�ɫƥ��������û�
	      BaseTable table = TableFactory.getInstance().getTableObject( this, 
	    		  XT_ZZJG_YH);	
	      //����ROLEID��ģ����ѯ�õ��û�����ӵ�иý�ɫ���û������
		  String sqlSelect = SQLConfig.get(yhSQL,jgid,roleID);
	      Recordset returnRecordset = null;
	      try{
	          //ִ����䷵�ظ��¼�¼����
              context.remove(outputNode_ZZJGYH);
	          int size = table.executeRowset(sqlSelect, context, 
	        		  outputNode_ZZJGYH);
	          //�жϷ��ؼ�¼����
	          if(size!=0){
                  //��ȡִ�н��
	        	  returnRecordset = context.getRecordset(outputNode_ZZJGYH);
	        	  if( null == returnRecordset ){
	        	      returnRecordset = new Recordset();
	        	  }
	        	  //ѭ���û������,�����û���Ϣ��ӵ����νڵ㣬���û����ϼ��ڵ�ID���ڽ�ɫID
	        	  for(int i=0;i<returnRecordset.size();i++){
	        	      tmp = returnRecordset.get(i);
	                  insertData = new DataBus();
	        		  insertData.setValue("jgid_pk", jgid + ":" + roleID + ":" 
	        				  + tmp.getValue("yhid_pk"));
	                  insertData.setValue("jgmc", tmp.getValue("yhxm"));
	                  insertData.setValue("sjjgid_fk",jgid + ":" + roleID);
	      			  /*�����νṹ�ڵ㱸ע���ݵĸ�ֵ��ORG��ʾ�ýڵ���"����"��USER��ʾ�ýڵ���
	      			  "�û�"��ROLE��ʾ�ýڵ���"Ȩ��"*/
                      insertData.setValue("type", "USER");
	                  rs.add(insertData);
	        	 }
	         }	        	        
	      }catch(TxnException e){
	           log.error(e);
	           throw new TxnErrorException(
	                                        ErrorConstant.DATABUS_FORMAT_ERROR,
	                                        CSDBConfig
	                                        .get("ggkz.jgjsyhxz.jsyhxz.error")); 	        	
	       }		              	
    }
    
}

