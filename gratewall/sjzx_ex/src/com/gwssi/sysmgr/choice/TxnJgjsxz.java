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
 * 机构角色选择类
 * 实现机构角色的选择,供其它模块使用,此类主要用到查询方法,没有增删改方法;
 * 分类信息：公共组件
 * 相关类:  cn.gwssi.bjais.dao.JgjsDao
 * 修改日期:2007-03-07
 * 修改原因:从代码规范的角度优化代码
 * @author 马元
 * @version 1.0
 * 
 */
public class TxnJgjsxz extends TxnService
{
    //以下是类中用到的变量,为区分,本类规定,大写变量都表示表名
	
    //系统组织机构表	
    private static final String XT_ZZJG_JG = "XT_ZZJG_JG";
    //组织机构表--DAO查询方法名
    private static final String selectFunction_ZZJG = "select orgtree list " +
    		"xt_zzjg_jg";   		                                            
    //组织机构表--输入节点名
    private static final String inputNode_ZZJG = "jgjsxz";
   
    //系统组织机构用户表
    private static final String XT_ZZJG_YH = "XT_ZZJG_YH" ;        
    //系统组织机构用户表--DAO查询方法名
    private static final String selectFunction_ZZJGYH = "select rows for " +
    		"roleTree xt_zzjg_yh";       
    //系统组织机构用户表--输入节点名
    private static final String inputNode_ZZJGYH = "findUserInputNode";
    //系统组织机构用户表--输出节点名
    private static final String outNode_ZZJGYH = "rolefind";
    
    //系统权限表
    private static final String OPERROLE = "OPERROLE" ; 
    //系统权限表--DAO查询方法名
    private static final String selectFunction_OPERROLE ="select roleids for " +
    		"roleTree operrole";       
    //系统权限表--输入节点名
    private static final String inputNode_OPERROLE = "roleinput";
    //系统权限表--输出节点名
    private static final String outputNode_OPERROLE = "role";
    
    //错误消息变量定义
    private static final String errorMsg = "ggkz.jgjsxz.jgjsxz.error";
    
    
    
    
   /**
    取step中init-value的配置参数，根据实际的参数修改
    @param context 请求和应答参数
    @throws cn.gwssi.common.component.exception.TxnException
    */
   protected void prepare(TxnContext context) throws TxnException
   {
      
   }

    /** 树形方式的组织机构角色数据查询
     * 交易代码：808004 
     * 输入输出定义：JgjsxzRowsetForm
     @param context 交易上下文,本方法中没有输入业务参数
     @throws cn.gwssi.common.component.exception.TxnException
     实现过程：
     因为机构和角色没有直接1对多的关系，所以实现过程如下：
     1、查询机构，得到机构结果集，循环机构结果集
     2、将机构属性复制到树形节点的属性中
     3、根据机构ID，得到该机构下所有用户信息结果集，
     4、循环结果集,累加字符窜,得到:所有用户的主要角色字符窜和所有用户的角色列表字符窜，
        注意这时这两个字符窜的内容就是该机构的角色，下面我们进行排重操作；
     5、查询角色表，得到角色结果集，循环
     6、查找ROLEID是否在以上两个字符窜中,如果在,就将该角色信息添加到树形节点，
        该角色的上级节点ID等于机构ID
     */
    public void txn808004( TxnContext context ) throws TxnException
    {
    	try{
            //声明组织机构表
        	BaseTable table = TableFactory.getInstance().getTableObject( this, 
            		XT_ZZJG_JG );
            //调用DAO查询机构方法,得到机构结果集
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
            //循环机构结果集,将机构属性复制到树形节点的属性中         
    		for (int i = 0; i < rsJG.size(); i++) {
    		    //从rsJG中得到一个机构对象
    			tmp = rsJG.get(i);
    	        insertData = new DataBus();
    			//对树形结构节点ID的赋值
    			insertData.setValue("jgid_pk", tmp.getValue(
    					VoXt_zzjg_jg.ITEM_JGID_PK));
    			parentid = tmp.getValue(VoXt_zzjg_jg.ITEM_SJJGID_FK);
    			/*对树形结构节点父ID的赋值，如果sjjgid_fk为Constants.ROOT_SJJG_ID，
    			则需将值转化为""，因为烽火台树形控件的一级节点只能是""值*/
    			if( null == parentid){
    				parentid = "";
    			}
    			if (parentid.equalsIgnoreCase(Constants.ROOT_SJJG_ID)) {
    		        insertData.setValue("sjjgid_fk", "");
    			}else{
    		  		insertData.setValue("sjjgid_fk", tmp.getValue(
    		  				VoXt_zzjg_jg.ITEM_SJJGID_FK));
    		     }
    	        //对树形结构节点显示名称的赋值
    			insertData.setValue("jgmc", tmp.getValue(VoXt_zzjg_jg.ITEM_JGMC));
    			/*对树形结构节点备注内容的赋值，ORG表示该节点是部门，USER表示该节点是
    			用户，ROLE表示该节点是“权限”*/
    			insertData.setValue("type", "ORG");
    			rs.add(insertData);
    			//调用方法，将该机构对象下的组织角色加入当前机构结果集中
    			getRoles(context,tmp.getValue(VoXt_zzjg_jg.ITEM_JGID_PK),rs);
    			insertData = null;
    		}
    		//将结果集加到context中
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
    
    /** 查询机构角色的私有方法,实现将某一机构对象下的组织角色加入当前机构结果集中
     * 交易代码：getRoles 
     * 输入输出定义：String , Recordset
     @param String 机构ID ,根据此值得到该机构下的组织角色信息
     @param Recordset 机构结果集 ,将得到的组织角色信息加入到机构结果集中
     @throws cn.gwssi.common.component.exception.TxnException
     实现过程：
     1、根据机构ID，得到该机构下所有用户信息结果集;
     2、循环结果集,累加字符窜,得到:所有用户的主要角色字符窜和所有用户的角色列表字符窜;
        注意这时这两个字符窜的内容就是该机构的角色，下面我们进行排重操作；
     3、查询角色表，得到角色结果集，循环
     4、查找ROLEID是否在以上两个字符窜中,如果在,就将该角色信息添加到树形节点，
        该角色的上级节点ID等于机构ID
     */    
    private void getRoles( TxnContext context,String jgid,Recordset rs ) 
    throws TxnException
    { 
        //1.根据机构ID，得到该机构下所有用户信息结果集
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
        //声明主要角色,该值做为查询条件之一
        String mainRole = ",";
        //声明角色ID列表,该值做为查询条件之一
        String roleIDS = ",";
        //声明角色ID,该值加入到机构树中
		String roleID = "";
		//声明角色名称,该值加入到机构树中
		String roleName = "";		
		if(null == rsJGYH){
			rsJGYH = new Recordset();
		}
    	//2.循环记录,累加字符窜,得到:所有用户的MROLE字符窜和所有用户的ROLES字符窜		
		for (int i = 0; i < rsJGYH.size(); i++) {
		    //得到用户结果集中的一个机构用户对象
			tmp = rsJGYH.get(i);
			insertData = new DataBus();
			//累加主要角色,作为查询条件之一
			mainRole = mainRole + tmp.getValue("mainrole") + ",";
			//累加角色ID字符窜,作为查询条件之一
			roleIDS = roleIDS + tmp.getValue("roleids") + ",";          
		}
	    //3.循环role表的记录
		//声明权限表
	    table = TableFactory.getInstance().getTableObject( this, OPERROLE );
	    //调用权限表
        table.executeFunction( selectFunction_OPERROLE, context, 
        		inputNode_OPERROLE, outputNode_OPERROLE );
        Recordset rsOPERROLE = context.getRecordset(outputNode_OPERROLE);
        if( null == rsOPERROLE ){
        	rsOPERROLE = new Recordset();
        }
        //循环权限结果集,将该角色信息添加到树型节点
        for (int j=0; j < rsOPERROLE.size(); j++){
      	    tmp = rsOPERROLE.get(j);
      	    insertData = new DataBus();
      	    roleID = tmp.getValue("roleid");
      	    roleName = tmp.getValue("rolename");     	      	       	       	 
	        /*4.查找当前roleID是否在主角色字符窜或角色ID字符窜中,如在,表示该机构下的用户拥有
      	    该角色,即该机构拥有该角色,就将该角色信息添加到树形节点，
      	    该角色的上级节点ID等于机构ID*/
      	    if(null == mainRole ){
      	    	mainRole = "";
      	    }
      	    if(null == roleIDS ){
      		    roleIDS = "";
    	    }
      	    //将角色信息添加到树型结果集中
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

