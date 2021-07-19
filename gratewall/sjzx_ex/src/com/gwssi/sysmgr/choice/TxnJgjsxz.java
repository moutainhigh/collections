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
import com.gwssi.common.util.Constants;
import com.gwssi.sysmgr.org.vo.VoXt_zzjg_jg;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;


/**
 * ������ɫѡ����
 * ʵ�ֻ�����ɫ��ѡ��,������ģ��ʹ��,������Ҫ�õ���ѯ����,û����ɾ�ķ���;
 * ������Ϣ���������
 * �����:  cn.gwssi.bjais.dao.JgjsDao
 * �޸�����:2007-03-07
 * �޸�ԭ��:�Ӵ���淶�ĽǶ��Ż�����
 * @author ��Ԫ
 * @version 1.0
 * 
 */
public class TxnJgjsxz extends TxnService
{
    //�����������õ��ı���,Ϊ����,����涨,��д��������ʾ����
	
    //ϵͳ��֯������	
    private static final String XT_ZZJG_JG = "XT_ZZJG_JG";
    //��֯������--DAO��ѯ������
    private static final String selectFunction_ZZJG = "select orgtree list " +
    		"xt_zzjg_jg";   		                                            
    //��֯������--����ڵ���
    private static final String inputNode_ZZJG = "jgjsxz";
   
    //ϵͳ��֯�����û���
    private static final String XT_ZZJG_YH = "XT_ZZJG_YH" ;        
    //ϵͳ��֯�����û���--DAO��ѯ������
    private static final String selectFunction_ZZJGYH = "select rows for " +
    		"roleTree xt_zzjg_yh";       
    //ϵͳ��֯�����û���--����ڵ���
    private static final String inputNode_ZZJGYH = "findUserInputNode";
    //ϵͳ��֯�����û���--����ڵ���
    private static final String outNode_ZZJGYH = "rolefind";
    
    //ϵͳȨ�ޱ�
    private static final String OPERROLE = "OPERROLE" ; 
    //ϵͳȨ�ޱ�--DAO��ѯ������
    private static final String selectFunction_OPERROLE ="select roleids for " +
    		"roleTree operrole";       
    //ϵͳȨ�ޱ�--����ڵ���
    private static final String inputNode_OPERROLE = "roleinput";
    //ϵͳȨ�ޱ�--����ڵ���
    private static final String outputNode_OPERROLE = "role";
    
    //������Ϣ��������
    private static final String errorMsg = "ggkz.jgjsxz.jgjsxz.error";
    
    
    
    
   /**
    ȡstep��init-value�����ò���������ʵ�ʵĲ����޸�
    @param context �����Ӧ�����
    @throws cn.gwssi.common.component.exception.TxnException
    */
   protected void prepare(TxnContext context) throws TxnException
   {
      
   }

    /** ���η�ʽ����֯������ɫ���ݲ�ѯ
     * ���״��룺808004 
     * ����������壺JgjsxzRowsetForm
     @param context ����������,��������û������ҵ�����
     @throws cn.gwssi.common.component.exception.TxnException
     ʵ�ֹ��̣�
     ��Ϊ�����ͽ�ɫû��ֱ��1�Զ�Ĺ�ϵ������ʵ�ֹ������£�
     1����ѯ�������õ������������ѭ�����������
     2�����������Ը��Ƶ����νڵ��������
     3�����ݻ���ID���õ��û����������û���Ϣ�������
     4��ѭ�������,�ۼ��ַ���,�õ�:�����û�����Ҫ��ɫ�ַ��ܺ������û��Ľ�ɫ�б��ַ��ܣ�
        ע����ʱ�������ַ��ܵ����ݾ��Ǹû����Ľ�ɫ���������ǽ������ز�����
     5����ѯ��ɫ���õ���ɫ�������ѭ��
     6������ROLEID�Ƿ������������ַ�����,�����,�ͽ��ý�ɫ��Ϣ��ӵ����νڵ㣬
        �ý�ɫ���ϼ��ڵ�ID���ڻ���ID
     */
    public void txn808004( TxnContext context ) throws TxnException
    {
    	try{
            //������֯������
        	BaseTable table = TableFactory.getInstance().getTableObject( this, 
            		XT_ZZJG_JG );
            //����DAO��ѯ��������,�õ����������
        	table.executeFunction( selectFunction_ZZJG, context, inputNode_ZZJG, 
            		outputNode );
    		Recordset rsJG = context.getRecordset(outputNode);
    		Recordset rs = new Recordset();
            DataBus insertData = null;
    		DataBus tmp;
            String parentid;
            if( null == rsJG ){
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
    			parentid = tmp.getValue(VoXt_zzjg_jg.ITEM_SJJGID_FK);
    			/*�����νṹ�ڵ㸸ID�ĸ�ֵ�����sjjgid_fkΪConstants.ROOT_SJJG_ID��
    			���轫ֵת��Ϊ""����Ϊ���̨���οؼ���һ���ڵ�ֻ����""ֵ*/
    			if( null == parentid){
    				parentid = "";
    			}
    			if (parentid.equalsIgnoreCase(Constants.ROOT_SJJG_ID)) {
    		        insertData.setValue("sjjgid_fk", "");
    			}else{
    		  		insertData.setValue("sjjgid_fk", tmp.getValue(
    		  				VoXt_zzjg_jg.ITEM_SJJGID_FK));
    		     }
    	        //�����νṹ�ڵ���ʾ���Ƶĸ�ֵ
    			insertData.setValue("jgmc", tmp.getValue(VoXt_zzjg_jg.ITEM_JGMC));
    			/*�����νṹ�ڵ㱸ע���ݵĸ�ֵ��ORG��ʾ�ýڵ��ǲ��ţ�USER��ʾ�ýڵ���
    			�û���ROLE��ʾ�ýڵ��ǡ�Ȩ�ޡ�*/
    			insertData.setValue("type", "ORG");
    			rs.add(insertData);
    			//���÷��������û��������µ���֯��ɫ���뵱ǰ�����������
    			getRoles(context,tmp.getValue(VoXt_zzjg_jg.ITEM_JGID_PK),rs);
    			insertData = null;
    		}
    		//��������ӵ�context��
    		context.remove(outputNode);
    		context.addRecord(outputNode, rs);    		
    	}catch(TxnException e){
    		log.error(e);
            throw new TxnErrorException(
                                        ErrorConstant.DATABUS_FORMAT_ERROR,
                                        CSDBConfig
                                        .get(errorMsg));    		
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
        context.setValue(inputNode_ZZJGYH, db);
        BaseTable table = TableFactory.getInstance().getTableObject( this, 
        		XT_ZZJG_YH );
        table.executeFunction( selectFunction_ZZJGYH, context, inputNode_ZZJGYH, 
        		outNode_ZZJGYH );
        Recordset rsJGYH = context.getRecordset(outNode_ZZJGYH);
        DataBus insertData = null;
        DataBus tmp; 
        //������Ҫ��ɫ,��ֵ��Ϊ��ѯ����֮һ
        String mainRole = ",";
        //������ɫID�б�,��ֵ��Ϊ��ѯ����֮һ
        String roleIDS = ",";
        //������ɫID,��ֵ���뵽��������
		String roleID = "";
		//������ɫ����,��ֵ���뵽��������
		String roleName = "";		
		if(null == rsJGYH){
			rsJGYH = new Recordset();
		}
    	//2.ѭ����¼,�ۼ��ַ���,�õ�:�����û���MROLE�ַ��ܺ������û���ROLES�ַ���		
		for (int i = 0; i < rsJGYH.size(); i++) {
		    //�õ��û�������е�һ�������û�����
			tmp = rsJGYH.get(i);
			insertData = new DataBus();
			//�ۼ���Ҫ��ɫ,��Ϊ��ѯ����֮һ
			mainRole = mainRole + tmp.getValue("mainrole") + ",";
			//�ۼӽ�ɫID�ַ���,��Ϊ��ѯ����֮һ
			roleIDS = roleIDS + tmp.getValue("roleids") + ",";          
		}
	    //3.ѭ��role��ļ�¼
		//����Ȩ�ޱ�
	    table = TableFactory.getInstance().getTableObject( this, OPERROLE );
	    //����Ȩ�ޱ�
        table.executeFunction( selectFunction_OPERROLE, context, 
        		inputNode_OPERROLE, outputNode_OPERROLE );
        Recordset rsOPERROLE = context.getRecordset(outputNode_OPERROLE);
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
      	    if(null == mainRole ){
      	    	mainRole = "";
      	    }
      	    if(null == roleIDS ){
      		    roleIDS = "";
    	    }
      	    //����ɫ��Ϣ��ӵ����ͽ������
	        if ( (mainRole.indexOf("," + roleID + ",")) > -1 ){
	      	    insertData.setValue("jgid_pk", tmp.getValue("roleid"));
      		    insertData.setValue("jgmc", tmp.getValue("rolename"));
      		    insertData.setValue("sjjgid_fk",jgid);
                insertData.setValue("type", "ROLE");
	            rs.add(insertData);
	        }else if ( (roleIDS.indexOf("," + roleID + ",")) > -1 ){
		        insertData.setValue("jgid_pk", tmp.getValue("roleid"));
      		    insertData.setValue("jgmc", tmp.getValue("rolename"));
      		    insertData.setValue("sjjgid_fk",jgid);
        	    insertData.setValue("type", "ROLE");
	            rs.add(insertData);
	      }	
	    }           
   }
}

