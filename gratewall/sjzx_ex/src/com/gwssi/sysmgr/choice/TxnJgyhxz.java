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
import com.gwssi.sysmgr.user.vo.VoXt_zzjg_yh;

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
 * �����û�ѡ����
 * ʵ�ֻ����û���ѡ��,������ģ��ʹ��,������Ҫ�õ���ѯ����,û����ɾ�ķ���;
 * ������Ϣ���������
 * �����:  cn.gwssi.bjais.dao.JgyhDao
 * �޸�����:2007-03-07
 * �޸�ԭ��:�Ӵ���淶�ĽǶ��Ż�����
 * @author ��Ԫ
 * @version 1.0
 * 
 */
public class TxnJgyhxz extends TxnService
{
    //�����������õ��ı���,Ϊ����,����涨,��д��������ʾ����
	
    //ϵͳ��֯������
	private static final String XT_ZZJG_JG = "XT_ZZJG_JG";
    //��֯������--DAO��ѯ������
	private static final String selectFunction_ZZJG = "select orgtree list xt_zzjg_jg";
	   
	//ϵͳ��֯�����û���
	private static final String XT_ZZJG_YH = "XT_ZZJG_YH";        
	//ϵͳ��֯�����û���--DAO��ѯ������
	private static final String selectFunction_ZZJGYH = "select xt_zzjg_yh list for jgyhxz";       
	//ϵͳ��֯�����û���--����ڵ���
	private static final String outputNode_ZZJGYH = "yhlist";  
   
   /**
    ȡstep��init-value�����ò���������ʵ�ʵĲ����޸�
    @param context �����Ӧ�����
    @throws cn.gwssi.common.component.exception.TxnException
    */
   protected void prepare(TxnContext context) throws TxnException
   {
      
   }

   /** �������ݲ�ѯ
    * ���ף�808002 �Ĵ�����
    * ����������壺JgyhxzRowsetForm
    @param context ����������,��������û������ҵ�����
    @throws cn.gwssi.common.component.exception.TxnException
    ʵ�ֹ��̣�
    ��Ϊ�����ͽ�ɫû��ֱ��1�Զ�Ĺ�ϵ������ʵ�ֹ������£�
    1����ѯ�������õ������������ѭ�����������
    2�����������Ը��Ƶ����νڵ��������
    3�����ݻ���ID���õ��û����������û���Ϣ�������
    4����Ϊ�������û���һ�Զ༴�������ӹ�ϵ����������ֱ�Ӳ�ѯ�û���
      �õ���������û���¼�Ļ���ID��Ϊ���û��ڵ�ĸ�ID����
    */
    public void txn808002( TxnContext context ) throws TxnException
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
    		for (int i = 0; i < rsJG.size(); i++) {
    		    //�ӻ���������еõ���������
    			tmp = rsJG.get(i);
    			insertData = new DataBus();
    			parentid = tmp.getValue(VoXt_zzjg_jg.ITEM_SJJGID_FK);
    			/*�����νṹ�ڵ㸸ID�ĸ�ֵ�����sjjgid_fkΪConstants.ROOT_SJJG_ID��
    			���轫ֵת��Ϊ""����Ϊ���̨���οؼ���һ���ڵ�ֻ����""ֵ*/
    			if (parentid.equalsIgnoreCase(Constants.ROOT_SJJG_ID)) {
              	    insertData.setValue("sjjgid_fk", "");
    			}else{
    		        insertData.setValue("sjjgid_fk", 
    		        		 tmp.getValue(VoXt_zzjg_jg.ITEM_SJJGID_FK));
    		    }
    		    //�����νṹ�ڵ�ID�ĸ�ֵ
    			insertData.setValue("jgid_pk", 
    					tmp.getValue(VoXt_zzjg_jg.ITEM_JGID_PK));
    			//�����νṹ�ڵ���ʾ���Ƶĸ�ֵ
    			insertData.setValue("jgmc", tmp.getValue(VoXt_zzjg_jg.ITEM_JGMC));
    		    /*�����νṹ�ڵ㱸ע���ݵĸ�ֵ��ORG��ʾ�ýڵ��ǲ��ţ�USER��ʾ�ýڵ����û���
    			ROLE��ʾ�ýڵ���"Ȩ��"*/
    			insertData.setValue("type", "ORG");
    			rs.add(insertData);
    			insertData = null;
    		}

            /*���²����ǽ��û���ӵ������У���Ϊ�������û���һ�Զ༴�������ӹ�ϵ��
    		��������ֱ�Ӳ�ѯ�û����õ���������û���¼�Ļ���ID��Ϊ���û��ڵ�ĸ�ID����*/ 
            BaseTable tableyh = TableFactory.getInstance().getTableObject( this, 
            		XT_ZZJG_YH );
            tableyh.executeFunction( selectFunction_ZZJGYH, context, inputNode,
            		outputNode_ZZJGYH );
            Recordset rsJGYH = context.getRecordset(outputNode_ZZJGYH);
    		/*ѭ�������û�,���û���Ϣ��ӵ����ͽ������,
            ע��,�˴��û���Ϣ�Ĳ������Ծ��Ǹ��û��ڵ�ĸ�ID*/
            for (int j = 0; j < rsJGYH.size(); j++) {
    		    //�ӽ�����еõ�һ���û�����
            	tmp = rsJGYH.get(j);
    			insertData = new DataBus();
    			/*�����νṹ�ڵ㸸ID�ĸ�ֵ��ע�⣺��Ϊ�������û���1�Զ�Ĺ�ϵ��
    			�˴��û��ڵ�ĸ�ID���Ǹ��û���������ID*/
    		    insertData.setValue("sjjgid_fk", 
    		    		tmp.getValue(VoXt_zzjg_yh.ITEM_JGID_FK) );
    		    //�����νṹ�ڵ�ID�ĸ�ֵ
    			insertData.setValue("jgid_pk", 
    					tmp.getValue(VoXt_zzjg_yh.ITEM_YHID_PK) );
    			//�����νṹ�ڵ���ʾ���Ƶĸ�ֵ
    			insertData.setValue("jgmc", 
    					tmp.getValue(VoXt_zzjg_yh.ITEM_YHXM) );
    			/*�����νṹ�ڵ㱸ע���ݵĸ�ֵ��ORG��ʾ�ýڵ��ǲ��ţ�USER��ʾ�ýڵ����û���
    			ROLE��ʾ�ýڵ���Ȩ��*/
    			insertData.setValue("type", "USER");
    			rs.add(insertData);
    			insertData = null;
    		}
            //��������ӵ�context��
    		context.remove(outputNode);
    		context.addRecord(outputNode, rs);    		
    	}catch ( TxnException e ){
    		log.error(e);
            throw new TxnErrorException(
                                        ErrorConstant.DATABUS_FORMAT_ERROR,"�����û�ѡ��ʧ��");     		
    	}

    }
}

