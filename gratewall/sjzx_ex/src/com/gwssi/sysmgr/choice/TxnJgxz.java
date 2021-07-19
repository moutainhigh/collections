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
 * ����ѡ����
 * ʵ�ֻ�����ѡ��,������ģ��ʹ��,������Ҫ�õ���ѯ����,û����ɾ�ķ���;
 * ������Ϣ���������
 * �����:  cn.gwssi.bjais.dao.JgDao
 * �޸�����:2007-03-07
 * �޸�ԭ��:�Ӵ���淶�ĽǶ��Ż�����
 * @author ��Ԫ
 * @version 1.0
 * 
 */
public class TxnJgxz extends TxnService
{
    //�����������õ��ı���,Ϊ����,����涨,��д��������ʾ����
	
    //ϵͳ��֯������	
    private static final String XT_ZZJG_JG = "XT_ZZJG_JG";
    //��֯������--DAO��ѯ������
    private static final String selectFunction_ZZJG = "select orgtree list xt_zzjg_jg";   		                                            
  
   /**
    ȡstep��init-value�����ò���������ʵ�ʵĲ����޸�
    @param context �����Ӧ�����
    @throws cn.gwssi.common.component.exception.TxnException
    */
   protected void prepare(TxnContext context) throws TxnException
   {
      
   }


    /** ʵ�����λ���ѡ��
     * ���ף�808001 �Ĵ�����
     * ����������壺JgxzRowsetForm,��������û������ҵ�����
     @param context ����������
     @throws cn.gwssi.common.component.exception.TxnException
     */
    public void txn808001( TxnContext context ) throws TxnException
    {
    	try{
            //������֯������
        	BaseTable table = TableFactory.getInstance().getTableObject( this, 
        			XT_ZZJG_JG );
        	//����DAO��ѯ��������,�õ����������
            table.executeFunction( selectFunction_ZZJG, context, inputNode, 
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
    		    //�ӻ���������еõ�һ����������
    	    	tmp = rsJG.get(i);
    			insertData = new DataBus();
    			parentid = tmp.getValue(VoXt_zzjg_jg.ITEM_SJJGID_FK);
    			if( null == parentid ){
    				parentid = "";
    			}
                /*�����νṹ�ڵ㸸ID�ĸ�ֵ�����sjjgid_fkΪConstants.ROOT_SJJG_ID��
                  ���轫ֵת��Ϊ""����Ϊ���̨���οؼ���һ���ڵ�ֻ����""ֵ*/
    			if (parentid.equals(Constants.ROOT_SJJG_ID)) {
    		        insertData.setValue("sjjgid_fk","");
    			}else{
    		        insertData.setValue("sjjgid_fk", tmp.getValue(
    		        		VoXt_zzjg_jg.ITEM_SJJGID_FK));
    		    }
    			//�����νṹ�ڵ�ID�ĸ�ֵ
    			insertData.setValue("jgid_pk", tmp.getValue(
    					VoXt_zzjg_jg.ITEM_JGID_PK));
    			//�����νṹ�ڵ���ʾ���Ƶĸ�ֵ
    			insertData.setValue("jgmc", tmp.getValue(VoXt_zzjg_jg.ITEM_JGMC));
    			/*�����νṹ�ڵ㱸ע���ݵĸ�ֵ��ORG��ʾ�ýڵ��ǲ��ţ�USER��ʾ�ýڵ����û���ROLE��ʾ�ýڵ���Ȩ��*/
    			insertData.setValue("type", "ORG");
    			insertData.setValue("jgjc", tmp.getValue("jgjc"));
    			insertData.setValue("sjjgname", tmp.getValue("sjjgname"));
    		    rs.add(insertData);
    			//�������ݶ���,���������ظ�
    		    insertData = null;
        	}
    	    //��������ӵ�context��
    	    context.remove(outputNode);
    	    context.addRecord(outputNode, rs);   
    	}catch(TxnException e){
    		log.error(e);
            throw new TxnErrorException(
                                        ErrorConstant.DATABUS_FORMAT_ERROR,"����ѡ��ʧ��");
    	}

   }
}

