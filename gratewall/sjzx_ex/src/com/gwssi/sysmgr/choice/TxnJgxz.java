/*
 * @Header 
 * @Revision 
 * @Date 20070307
 * 
 * =====================================================
 *  北京审计项目组
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
 * 机构选择类
 * 实现机构的选择,供其它模块使用,此类主要用到查询方法,没有增删改方法;
 * 分类信息：公共组件
 * 相关类:  cn.gwssi.bjais.dao.JgDao
 * 修改日期:2007-03-07
 * 修改原因:从代码规范的角度优化代码
 * @author 马元
 * @version 1.0
 * 
 */
public class TxnJgxz extends TxnService
{
    //以下是类中用到的变量,为区分,本类规定,大写变量都表示表名
	
    //系统组织机构表	
    private static final String XT_ZZJG_JG = "XT_ZZJG_JG";
    //组织机构表--DAO查询方法名
    private static final String selectFunction_ZZJG = "select orgtree list xt_zzjg_jg";   		                                            
  
   /**
    取step中init-value的配置参数，根据实际的参数修改
    @param context 请求和应答参数
    @throws cn.gwssi.common.component.exception.TxnException
    */
   protected void prepare(TxnContext context) throws TxnException
   {
      
   }


    /** 实现树形机构选择
     * 交易：808001 的处理函数
     * 输入输出定义：JgxzRowsetForm,本方法中没有输入业务参数
     @param context 交易上下文
     @throws cn.gwssi.common.component.exception.TxnException
     */
    public void txn808001( TxnContext context ) throws TxnException
    {
    	try{
            //声明组织机构表
        	BaseTable table = TableFactory.getInstance().getTableObject( this, 
        			XT_ZZJG_JG );
        	//调用DAO查询机构方法,得到机构结果集
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
            //循环机构结果集,将机构属性复制到树形节点的属性中
    	    for (int i = 0; i < rsJG.size(); i++) {
    		    //从机构结果集中得到一个机构对象
    	    	tmp = rsJG.get(i);
    			insertData = new DataBus();
    			parentid = tmp.getValue(VoXt_zzjg_jg.ITEM_SJJGID_FK);
    			if( null == parentid ){
    				parentid = "";
    			}
                /*对树形结构节点父ID的赋值，如果sjjgid_fk为Constants.ROOT_SJJG_ID，
                  则需将值转化为""，因为烽火台树形控件的一级节点只能是""值*/
    			if (parentid.equals(Constants.ROOT_SJJG_ID)) {
    		        insertData.setValue("sjjgid_fk","");
    			}else{
    		        insertData.setValue("sjjgid_fk", tmp.getValue(
    		        		VoXt_zzjg_jg.ITEM_SJJGID_FK));
    		    }
    			//对树形结构节点ID的赋值
    			insertData.setValue("jgid_pk", tmp.getValue(
    					VoXt_zzjg_jg.ITEM_JGID_PK));
    			//对树形结构节点显示名称的赋值
    			insertData.setValue("jgmc", tmp.getValue(VoXt_zzjg_jg.ITEM_JGMC));
    			/*对树形结构节点备注内容的赋值，ORG表示该节点是部门，USER表示该节点是用户，ROLE表示该节点是权限*/
    			insertData.setValue("type", "ORG");
    			insertData.setValue("jgjc", tmp.getValue("jgjc"));
    			insertData.setValue("sjjgname", tmp.getValue("sjjgname"));
    		    rs.add(insertData);
    			//重置数据对象,避免数据重复
    		    insertData = null;
        	}
    	    //将结果集加到context中
    	    context.remove(outputNode);
    	    context.addRecord(outputNode, rs);   
    	}catch(TxnException e){
    		log.error(e);
            throw new TxnErrorException(
                                        ErrorConstant.DATABUS_FORMAT_ERROR,"机构选择失败");
    	}

   }
}

