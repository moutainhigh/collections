package cn.gwssi.app.bizlog.txn;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;

/**
 * ҵ����־��ѯ
 * ������Ϣ��module
 * �����:  cn.gwssi.app.bizlog.dao.BizlogDao
 * @author Administrator
 */
public class TxnBizlog extends TxnService
{
   // ���ݱ�����
   private static final String TABLE_NAME = "biz_log";
   
   /**
    ȡstep��init-value�����ò���������ʵ�ʵĲ����޸�
    @param context �����Ӧ�����
    @throws cn.gwssi.common.component.exception.TxnException
    */
   protected void prepare(TxnContext context) throws TxnException
   {
      
   }

    /** ��ѯҵ����־�б�
     * ���ף�981211 �Ĵ�����
     * ����������壺BizlogRowsetForm
     @param context ����������
     @throws cn.gwssi.common.component.exception.TxnException
     */
    public void txn981211( TxnContext context ) throws TxnException
    {
        BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
       table.executeFunction( "select biz_log list", context, inputNode, outputNode );
       /**
        * �޸�ҳ���ϵ����ں�ʱ���ʽ
        * 20070101 --> 2007-01-01
        * 175959 --> 17:59:59
        */
       Recordset rs = context.getRecordset(outputNode);
       	for(int i=0; i<rs.size(); i++){
       		DataBus dateBus = rs.get(i);
       		dateBus.setValue("regdate", formatDate(dateBus.getValue("regdate")));
       		dateBus.setValue("regtime", formatTime(dateBus.getValue("regtime")));
       	}
    }
    static String formatDate(String str){
    	if(!str.equals("")&&str.length()==8){
    		str=str.substring(0,4)+"-"+str.substring(4,6)+"-"+str.substring(6,8);
    	}
    	return str;
    }
    static String formatTime(String str){
    	if(!str.equals("")&&str.length()==6){
    		str=str.substring(0,2)+":"+str.substring(2,4)+":"+str.substring(4,6);
    	}
    	return str;       
    }

    /** ��ѯҵ����־
     * ���ף�981214 �Ĵ�����
     * ����������壺BizlogUpdateForm
     @param context ����������
     @throws cn.gwssi.common.component.exception.TxnException
     */
    public void txn981214( TxnContext context ) throws TxnException
    {
        BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
        table.executeFunction( "select one biz_log", context, inputNode, outputNode );
    }

    public void txn981219( TxnContext context ) throws TxnException
    {
        BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
        System.out.println("�Ż���������");
        table.executeFunction( "deleteSjSxsj", context, inputNode, outputNode );
        System.out.println("�Ż�����");
        table.executeFunction( "deleteSjZbsj", context, inputNode, outputNode );
        System.out.println("�Ż�����");
        table.executeFunction( "deleteSjSy", context, inputNode, outputNode );
        System.out.println("�Ż����");
    }
    
    /** �û���־��ѯ
     * ���ף�981216 �Ĵ�����
     * ����������壺UserBizlogRowsetForm
     @param context ����������
     @throws cn.gwssi.common.component.exception.TxnException
     */
    public void txn981216( TxnContext context ) throws TxnException
    {
        BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
        table.executeFunction( "select user biz_log list", context, inputNode, outputNode );
    }
}

