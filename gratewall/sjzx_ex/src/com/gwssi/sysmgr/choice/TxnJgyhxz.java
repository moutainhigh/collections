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
 * 机构用户选择类
 * 实现机构用户的选择,供其它模块使用,此类主要用到查询方法,没有增删改方法;
 * 分类信息：公共组件
 * 相关类:  cn.gwssi.bjais.dao.JgyhDao
 * 修改日期:2007-03-07
 * 修改原因:从代码规范的角度优化代码
 * @author 马元
 * @version 1.0
 * 
 */
public class TxnJgyhxz extends TxnService
{
    //以下是类中用到的变量,为区分,本类规定,大写变量都表示表名
	
    //系统组织机构表
	private static final String XT_ZZJG_JG = "XT_ZZJG_JG";
    //组织机构表--DAO查询方法名
	private static final String selectFunction_ZZJG = "select orgtree list xt_zzjg_jg";
	   
	//系统组织机构用户表
	private static final String XT_ZZJG_YH = "XT_ZZJG_YH";        
	//系统组织机构用户表--DAO查询方法名
	private static final String selectFunction_ZZJGYH = "select xt_zzjg_yh list for jgyhxz";       
	//系统组织机构用户表--输出节点名
	private static final String outputNode_ZZJGYH = "yhlist";  
   
   /**
    取step中init-value的配置参数，根据实际的参数修改
    @param context 请求和应答参数
    @throws cn.gwssi.common.component.exception.TxnException
    */
   protected void prepare(TxnContext context) throws TxnException
   {
      
   }

   /** 树形数据查询
    * 交易：808002 的处理函数
    * 输入输出定义：JgyhxzRowsetForm
    @param context 交易上下文,本方法中没有输入业务参数
    @throws cn.gwssi.common.component.exception.TxnException
    实现过程：
    因为机构和角色没有直接1对多的关系，所以实现过程如下：
    1、查询机构，得到机构结果集，循环机构结果集
    2、将机构属性复制到树形节点的属性中
    3、根据机构ID，得到该机构下所有用户信息结果集，
    4、因为机构和用户是一对多即天生父子关系，所以我们直接查询用户表，
      得到结果，将用户记录的机构ID作为该用户节点的父ID即可
    */
    public void txn808002( TxnContext context ) throws TxnException
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
    		for (int i = 0; i < rsJG.size(); i++) {
    		    //从机构结果集中得到机构对象
    			tmp = rsJG.get(i);
    			insertData = new DataBus();
    			parentid = tmp.getValue(VoXt_zzjg_jg.ITEM_SJJGID_FK);
    			/*对树形结构节点父ID的赋值，如果sjjgid_fk为Constants.ROOT_SJJG_ID，
    			则需将值转化为""，因为烽火台树形控件的一级节点只能是""值*/
    			if (parentid.equalsIgnoreCase(Constants.ROOT_SJJG_ID)) {
              	    insertData.setValue("sjjgid_fk", "");
    			}else{
    		        insertData.setValue("sjjgid_fk", 
    		        		 tmp.getValue(VoXt_zzjg_jg.ITEM_SJJGID_FK));
    		    }
    		    //对树形结构节点ID的赋值
    			insertData.setValue("jgid_pk", 
    					tmp.getValue(VoXt_zzjg_jg.ITEM_JGID_PK));
    			//对树形结构节点显示名称的赋值
    			insertData.setValue("jgmc", tmp.getValue(VoXt_zzjg_jg.ITEM_JGMC));
    		    /*对树形结构节点备注内容的赋值，ORG表示该节点是部门，USER表示该节点是用户，
    			ROLE表示该节点是"权限"*/
    			insertData.setValue("type", "ORG");
    			rs.add(insertData);
    			insertData = null;
    		}

            /*以下操作是将用户添加到机构中，因为机构和用户是一对多即天生父子关系，
    		所以我们直接查询用户表，得到结果，将用户记录的机构ID作为该用户节点的父ID即可*/ 
            BaseTable tableyh = TableFactory.getInstance().getTableObject( this, 
            		XT_ZZJG_YH );
            tableyh.executeFunction( selectFunction_ZZJGYH, context, inputNode,
            		outputNode_ZZJGYH );
            Recordset rsJGYH = context.getRecordset(outputNode_ZZJGYH);
    		/*循环机构用户,将用户信息添加到树型结果集中,
            注意,此处用户信息的部门属性就是该用户节点的父ID*/
            for (int j = 0; j < rsJGYH.size(); j++) {
    		    //从结果集中得到一个用户对象
            	tmp = rsJGYH.get(j);
    			insertData = new DataBus();
    			/*对树形结构节点父ID的赋值，注意：因为机构和用户是1对多的关系，
    			此处用户节点的父ID就是该用户所属机构ID*/
    		    insertData.setValue("sjjgid_fk", 
    		    		tmp.getValue(VoXt_zzjg_yh.ITEM_JGID_FK) );
    		    //对树形结构节点ID的赋值
    			insertData.setValue("jgid_pk", 
    					tmp.getValue(VoXt_zzjg_yh.ITEM_YHID_PK) );
    			//对树形结构节点显示名称的赋值
    			insertData.setValue("jgmc", 
    					tmp.getValue(VoXt_zzjg_yh.ITEM_YHXM) );
    			/*对树形结构节点备注内容的赋值，ORG表示该节点是部门，USER表示该节点是用户，
    			ROLE表示该节点是权限*/
    			insertData.setValue("type", "USER");
    			rs.add(insertData);
    			insertData = null;
    		}
            //将结果集加到context中
    		context.remove(outputNode);
    		context.addRecord(outputNode, rs);    		
    	}catch ( TxnException e ){
    		log.error(e);
            throw new TxnErrorException(
                                        ErrorConstant.DATABUS_FORMAT_ERROR,"机构用户选择失败");     		
    	}

    }
}

