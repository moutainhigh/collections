package cn.gwssi.app.bizlog.txn;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;

/**
 * 业务日志查询
 * 分类信息：module
 * 相关类:  cn.gwssi.app.bizlog.dao.BizlogDao
 * @author Administrator
 */
public class TxnBizlog extends TxnService
{
   // 数据表名称
   private static final String TABLE_NAME = "biz_log";
   
   /**
    取step中init-value的配置参数，根据实际的参数修改
    @param context 请求和应答参数
    @throws cn.gwssi.common.component.exception.TxnException
    */
   protected void prepare(TxnContext context) throws TxnException
   {
      
   }

    /** 查询业务日志列表
     * 交易：981211 的处理函数
     * 输入输出定义：BizlogRowsetForm
     @param context 交易上下文
     @throws cn.gwssi.common.component.exception.TxnException
     */
    public void txn981211( TxnContext context ) throws TxnException
    {
        BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
       table.executeFunction( "select biz_log list", context, inputNode, outputNode );
       /**
        * 修改页面上的日期和时间格式
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

    /** 查询业务日志
     * 交易：981214 的处理函数
     * 输入输出定义：BizlogUpdateForm
     @param context 交易上下文
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
        System.out.println("优化属性数据");
        table.executeFunction( "deleteSjSxsj", context, inputNode, outputNode );
        System.out.println("优化数据");
        table.executeFunction( "deleteSjZbsj", context, inputNode, outputNode );
        System.out.println("优化索引");
        table.executeFunction( "deleteSjSy", context, inputNode, outputNode );
        System.out.println("优化完成");
    }
    
    /** 用户日志查询
     * 交易：981216 的处理函数
     * 输入输出定义：UserBizlogRowsetForm
     @param context 交易上下文
     @throws cn.gwssi.common.component.exception.TxnException
     */
    public void txn981216( TxnContext context ) throws TxnException
    {
        BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
        table.executeFunction( "select user biz_log list", context, inputNode, outputNode );
    }
}

