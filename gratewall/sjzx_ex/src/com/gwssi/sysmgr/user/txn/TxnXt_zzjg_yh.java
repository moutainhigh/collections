/*
 * @Header @Revision @Date 20070301
 * ===================================================== 北京审计项目组
 * =====================================================
 */

package com.gwssi.sysmgr.user.txn;

import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.context.vo.VoUser;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.common.util.CSDBConfig;
import com.gwssi.common.util.Constants;
import com.gwssi.common.util.DateUtil;
import com.gwssi.common.util.MD5;
import com.gwssi.common.util.PlainSequence;
import com.gwssi.dw.dq.ldap.LDAPControl;
import com.gwssi.sysmgr.GgkzConstants;
import com.gwssi.sysmgr.user.vo.VoXt_zzjg_yh;

/**
 * @desc 用户维护后台处理类 实现用户的新建、修改、删除、获取列表等操作
 * @author adaFang
 * @version 1.0
 */
public class TxnXt_zzjg_yh extends TxnService {
    // 数据表名称
    private static final String TABLE_NAME = "xt_zzjg_yh";

    // 查询列表
    private static final String ROWSET_FUNCTION = "YHGL-select xt_zzjg_yh list";

    // 查询列表
    private static final String ROWSET_FOR_ORG_FUNCTION = "JGGL-select xt_zzjg_yh for org list";

    // 查询记录YHGL-select xt_zzjg_yh list
    private static final String SELECT_FUNCTION = "YHGL-select one xt_zzjg_yh";

    // 修改用户信息记录
    private static final String UPDATE_FUNCTION = "YHGL-update one xt_zzjg_yh";

    // 修改用户信息记录除密码
    private static final String UPDATE_WITHOUT_PSW_FUNCTION = "YHGL-update one without psw xt_zzjg_yh";

    // 增加记录
    private static final String INSERT_FUNCTION = "YHGL-insert one xt_zzjg_yh";

    // 设置用户无效记录
    private static final String STATUS_UNUSE_FUNCTION = "YHGL-update status xt_zzjg_yh";

    // 修改用户密码
    private static final String MODIFY_PASSWORD_FUNCTION = "YHGL-update password xt_zzjg_yh";

    // 根据用户名查询
    private static final String LOGIN_SELECT_FUNCTION = "LOGIN-select one xt_zzjg_yh";

    // 初始化用户密码
    private static final String UPDATE_INTE_PSW = "update password  inte xt_zzjg_yh";

    public TxnXt_zzjg_yh() {

    }

    /**
     * 查询列表（用户管理）
     * 
     * @param context
     *            交易上下文
     * @throws cn.gwssi.common.component.exception.TxnException
     */
    public void txn807001(TxnContext context) throws TxnException {
    	
            BaseTable table = TableFactory.getInstance()
                    .getTableObject(this, TABLE_NAME);
            table.executeFunction("queryYhList", context, inputNode,
                    outputNode);            
            DataBus db = null;
            String roleids = "";
            String rolenames = "";
            String jgid_fk = "";
            String jgname = "";
            TxnContext queryContext = null;
            int i = 0;
            int j = 0;
            Recordset rs = context.getRecordset(outputNode);
            if (rs != null && !rs.isEmpty()) {
                for (int k = 0; k < rs.size(); k++) {
                	db = rs.get(k);
                    roleids = db.getValue("roleids");
                    rolenames = db.getValue("rolenames");  
                    jgid_fk = db.getValue("jgid_fk"); 
                    i = roleids.indexOf(",");
                    j = rolenames.indexOf(","); 
                    if(i!=-1&&j!=-1){
		                if(roleids.substring(0,i).equals(Constants.ROLEID)&&rolenames.substring(0,j).equals(Constants.ROLENAME)){
		                	db.setValue("roleids",roleids.substring(i+1));
		                	db.setValue("rolenames",rolenames.substring(j+1));
		                } 
                    }
                    queryContext = new TxnContext();
                    queryContext.getRecord("primary-key").setValue("jgid_pk", jgid_fk);
                    callService("com.gwssi.sysmgr.org.txn.TxnXt_zzjg_jg","findJgnameByLogin",queryContext);  
                    //String jgname=userMessage.getValue(VoXt_zzjg_yh.ITEM_JGNAME);
                    jgname = queryContext.getRecord("record").getValue("jgmc");                    
                    db.setValue("jgname",jgname);
                }
            }                       
            // 增加列表序号
            PlainSequence.addIndex(context, outputNode);
            
    }

    /**
     * 查询列表用于单个机构内维护人员信息（机构管理）
     * 
     * @param context
     *            交易上下文
     * @throws cn.gwssi.common.component.exception.TxnException
     */
    public void txn807011(TxnContext context) throws TxnException {
//        try {
            context.remove(outputNode);
            BaseTable table = TableFactory.getInstance()
                    .getTableObject(this, TABLE_NAME);
            table.executeFunction(ROWSET_FOR_ORG_FUNCTION, context, inputNode,
                                  outputNode);
            DataBus db = null;
            String roleids = "";
            String rolenames = "";
            String jgid_fk = "";
            String jgname = "";
            TxnContext queryContext = null;            
            int i = 0;
            int j = 0;
            Recordset rs = context.getRecordset(outputNode);
            if (rs != null && !rs.isEmpty()) {
                for (int k = 0; k < rs.size(); k++) {
                	db = rs.get(k);
                    roleids = db.getValue("roleids");
                    rolenames = db.getValue("rolenames");  
                    jgid_fk = db.getValue("jgid_fk"); 
                    i = roleids.indexOf(",");
                    j = rolenames.indexOf(","); 
                    if(i!=-1&&j!=-1){
		                if(roleids.substring(0,i).equals(Constants.ROLEID)&&rolenames.substring(0,j).equals(Constants.ROLENAME)){
		                	db.setValue("roleids",roleids.substring(i+1));
		                	db.setValue("rolenames",rolenames.substring(j+1));
		                } 
                    }
                    queryContext = new TxnContext();
                    queryContext.getRecord("primary-key").setValue("jgid_pk", jgid_fk);
                    callService("com.gwssi.sysmgr.org.txn.TxnXt_zzjg_jg","findJgnameByLogin",queryContext);  
                    //String jgname=userMessage.getValue(VoXt_zzjg_yh.ITEM_JGNAME);
                    jgname = queryContext.getRecord("record").getValue("jgmc");                    
                    db.setValue("jgname",jgname);                    
                }
            }              
            // 增加列表序号
            PlainSequence.addIndex(context, outputNode);
//        } catch (Exception e) {
//            CSDBException.throwCSDBException(e);
//        }
    }
    
    /**
     * 修改记录（用户管理and机构管理）
     * 
     * @param context
     *            交易上下文
     * @throws cn.gwssi.common.component.exception.TxnException
     */
    public void txn807002(TxnContext context) throws TxnException {
//        try {
            String yhmm = context.getRecord(inputNode)
                    .getValue(VoXt_zzjg_yh.ITEM_YHMM);
            BaseTable table = TableFactory.getInstance()
                    .getTableObject(this, TABLE_NAME);
            // 输入的用户密码为空时,不更新密码
            String yhid_pk = context.getRecord(inputNode).getValue(VoXt_zzjg_yh.ITEM_YHID_PK);
            String yhxm = context.getRecord(inputNode).getValue(VoXt_zzjg_yh.ITEM_YHXM);
        	String roleIds = context.getRecord(inputNode).getValue("roleids");
        	StringBuffer groupIds = new StringBuffer();
        	setGroupIds(table,groupIds,roleIds);
        	/* modified by jufeng 
        	boolean flag = addUserToCognos(groupIds,yhid_pk,yhxm);
        	if(!flag){
        		throw new TxnDataException("","修改cognos用户失败!");
        	}
        	*/
        	context.getRecord(inputNode).setValue("roleids", Constants.ROLEID + "," + roleIds);
        	context.getRecord(inputNode).setValue("rolenames", Constants.ROLENAME + "," + context.getRecord(inputNode).getValue("rolenames"));  
        	//context.getRecord(inputNode).setValue("zyzz", groupIds.toString());           
            if (yhmm == null || ("".equals(yhmm.trim()))) {
                log.debug("更新密码外的用户基本信息");
                table.executeFunction(UPDATE_WITHOUT_PSW_FUNCTION, context,
                                      inputNode, outputNode);
            } else {
                // 输入的用户密码非空时更新所有信息
            	context.getRecord(inputNode).setValue("mmxgrq", DateUtil.getYMDTime());
                table.executeFunction(UPDATE_FUNCTION, context, inputNode,
                                      outputNode);
            }
            setBizLog("修改用户：", context,context.getRecord("record").getValue("yhxm"));
//        } catch (Exception e) {
//            CSDBException.throwCSDBException(e);
//        }
            
//    	try{
//    		PrivilegeManager.getInst().init("统计制度");
//    	}
//    	catch(Exception e){
//    		e.printStackTrace();
//    	}
    }

    /**
     * 增加记录（用户管理and机构管理）
     * 
     * @param context
     *            交易上下文
     * @throws cn.gwssi.common.component.exception.TxnException
     */
    public void txn807003(TxnContext context) throws TxnException {
    	
            BaseTable table = TableFactory.getInstance()
                    .getTableObject(this, TABLE_NAME);
            String yhzh = context.getRecord(inputNode)
                    .getValue(VoXt_zzjg_yh.ITEM_YHZH);
            context.getRecord(inputNode).setValue("mmxgrq", DateUtil.getYMDTime());
            // 判断用户帐号（登录名）和用户编号是否已经存在
            if (isYhzhExist(context, yhzh)) {
                // 帐号或编号存在，返回
                throw new TxnDataException("","输入的用户帐号已存在!");
            } else {
            	String roleIds = context.getRecord(inputNode).getValue("roleids");
                StringBuffer groupIds = new StringBuffer();
                setGroupIds(table,groupIds,roleIds);
                
                //context.getRecord(inputNode).setValue("zyzz", groupIds.toString());
            	context.getRecord(inputNode).setValue("roleids", Constants.ROLEID + "," + roleIds);
            	context.getRecord(inputNode).setValue("rolenames", Constants.ROLENAME + "," + context.getRecord(inputNode).getValue("rolenames"));
                table.executeFunction(INSERT_FUNCTION, context, inputNode,
                                      outputNode);
                String yhid_pk = context.getRecord(outputNode).getValue(VoXt_zzjg_yh.ITEM_YHID_PK);
                String yhxm = context.getRecord(outputNode).getValue(VoXt_zzjg_yh.ITEM_YHXM);
                /* jufeng 20120428
            	boolean flag = addUserToCognos(groupIds,yhid_pk,yhxm);
            	if(!flag){
            		throw new TxnDataException("","增加cognos用户失败!");
            	}*/
                
                setBizLog("增加用户：", context,context.getRecord("record").getValue("yhxm"));
            }

    }
    
    private boolean addUserToCognos(StringBuffer groupIds,String yhid_pk, String yhxm){
		LDAPControl control = new LDAPControl();
		if(groupIds.length()>0){
			return control.insertOrUpdateUserWithGroups(yhid_pk,yhxm,groupIds.toString().split(","));
		}else{
			return control.insertOrUpdateUserWithGroups(yhid_pk,yhxm,new String[0]);			
		}
    }
    
	private void setGroupIds(BaseTable table, StringBuffer groupIds, String roleIds) throws TxnException
	{
//		roleIds = roleIds.trim();
//		if (roleIds.endsWith(",")){
//			roleIds = roleIds.substring(0, roleIds.length() - 1);
//		}
//		if ( roleIds.startsWith(";")){
//			roleIds = roleIds.substring(1);
//		}
//		
//		roleIds = roleIds.replaceAll(",", "','");
//		roleIds = "'" + roleIds + "'";
		int count = 0;
		TxnContext context = new TxnContext();
		try {
			String sql = "select funclist  from operrole_new  " +
			" where roleid in (" + roleIds +") and status='1'" ;
			count = table.executeRowset(sql, context, "funclists");
		} catch (TxnException e) {
			log.debug("没有记录");
		}
		if(count>0){
			Recordset rs = context.getRecordset("funclists");
			for(int i=0;i<rs.size();i++){
				DataBus db = rs.get(i);
				String funclist = db.getValue("funclist");
				String[] funcinfo = funclist.split(";");
				for(int j=0;funcinfo!=null&&j<funcinfo.length;j++){
					String func = funcinfo[j];
					if(func.startsWith("413")){
						if(groupIds.length()>0){
							groupIds.append(",");
						}
						groupIds.append(Integer.parseInt(func.substring(func.length()-2,func.length())));
					}
				}
			}
		}
	}

	/**
     * 查询记录用于修改（用户管理and机构管理）
     * 
     * @param context
     *            交易上下文
     * @throws cn.gwssi.common.component.exception.TxnException
     */
    public void txn807004(TxnContext context) throws TxnException {
            BaseTable table = TableFactory.getInstance()
                    .getTableObject(this, TABLE_NAME);
            try {
                table.executeFunction(SELECT_FUNCTION, context, inputNode,
                                      outputNode);
                String roleids = context.getRecord(outputNode).getValue("roleids");
                String rolenames = context.getRecord(outputNode).getValue("rolenames");
                int i = roleids.indexOf(",");
                int j = rolenames.indexOf(",");
                if(roleids.substring(0,i).equals(Constants.ROLEID)&&rolenames.substring(0,j).equals(Constants.ROLENAME)){
                	context.getRecord(outputNode).setValue("roleids",roleids.substring(i+1));
                	context.getRecord(outputNode).setValue("rolenames",rolenames.substring(j+1));
                }
            } catch (TxnDataException ex) {
                log.error(ex);
                // 若空记录异常抛出异常空信息提示
                if (ex.getErrCode().compareTo(
                		"SQL000"
                ) == 0) {
                    throw new TxnDataException("","用户记录不存在!");
                } else {
                    throw ex;
                }
            }

    }
    /**
     * 删除记录(软删除,设置用户有效状态为无效)
     * 
     * @param context
     *            交易上下文
     * @throws cn.gwssi.common.component.exception.TxnException
     */
    public void txn807005(TxnContext context) throws TxnException {
//        try {
            Recordset rs = context.getRecordset(inputNode);
            String yhxm = "";
            if (rs != null && !rs.isEmpty()) {
                for (int i = 0; i < rs.size(); i++) {
                    rs.get(i).setValue(VoXt_zzjg_yh.ITEM_SFYX,
                                       Constants.status_offuse);
                    log.debug("删除用户纪录：" + rs.get(i));
                    yhxm+= "," + rs.get(i).getValue("yhxm");
                }
            }
            if(!yhxm.equals("")) yhxm = yhxm.substring(1);
            context.getRecord(inputNode).setValue(VoXt_zzjg_yh.ITEM_SFYX,
                                                  Constants.status_offuse);
            BaseTable table = TableFactory.getInstance()
                    .getTableObject(this, TABLE_NAME);
            table.executeFunction(STATUS_UNUSE_FUNCTION, context, inputNode,
                                  outputNode);
            setBizLog("停用用户：", context,yhxm);
//        } catch (Exception e) {
//            CSDBException.throwCSDBException(e);
//        }
    }
    /**
     * 启用用户(设置用户有效状态为有效效)
     * 
     * @param context
     *            交易上下文
     * @throws cn.gwssi.common.component.exception.TxnException
     */
    public void txn807008(TxnContext context) throws TxnException {
//        try {
            Recordset rs = context.getRecordset(inputNode);
            String yhxm = "";
            if (rs != null && !rs.isEmpty()) {
                for (int i = 0; i < rs.size(); i++) {
                    rs.get(i).setValue(VoXt_zzjg_yh.ITEM_SFYX,
                                       Constants.status_inuse);
                    log.debug("启用用户纪录：" + rs.get(i));
                    yhxm+= "," + rs.get(i).getValue("yhxm");
                }
            }
            if(!yhxm.equals("")) yhxm = yhxm.substring(1);
            context.getRecord(inputNode).setValue(VoXt_zzjg_yh.ITEM_SFYX,
                                                  Constants.status_inuse);
            BaseTable table = TableFactory.getInstance()
                    .getTableObject(this, TABLE_NAME);
            table.executeFunction(STATUS_UNUSE_FUNCTION, context, inputNode,
                                  outputNode);
            setBizLog("启用用户：", context,yhxm);
//        } catch (Exception e) {
//            CSDBException.throwCSDBException(e);
//        }
    }    
    /**
     * 修改密码
     * 
     * @param context
     *            交易上下文
     * @throws cn.gwssi.common.component.exception.TxnException
     */
    public void txn807006(TxnContext context) throws TxnException {

    	VoUser operData = context.getOperData();
            //获取登录帐号
            String dlzh = operData.getUserName();
            // 创建数据载体。
            DataBus data = new DataBus();
            // 设置属性。
            data.setValue(VoXt_zzjg_yh.ITEM_YHZH, dlzh);
            // 存入传输总线对象。
            context.setValue("queryobj", data);
            // 获取数据模型对象。名称需要与DAO对象文件名称匹配，区分大小写。
        	String yhzh = context.getValue(Constants.LOGIN_NAME);
            String sql="select * from xt_zzjg_yh_new where sfyx ='0' and upper(yhzh)='"+dlzh.toUpperCase()+"'";
            BaseTable xt_yh = TableFactory.getInstance().getTableObject(this,
                                                                        TABLE_NAME);
            try {
                int num = xt_yh.executeRowset(sql, context, "execResult");
                // 动态调用方法。查询当前用户信息
                // 参数说明：方法名称，上下文对象，数据载体，数据载体（返回）。
                //table.executeFunction("validateUser", context, "queryobj",
                //                      "execResult");
            } catch (TxnDataException e) {
                // 出错退出。
                if (e.getErrCode().compareTo(
                		"SQL000"
                ) == 0)
                    throw new TxnDataException("","用户记录不存在!");
                throw e;
            }
            // 读取数据载体。
            DataBus execResult = context.getRecord("execResult");
            String dlmm = execResult.getValue(VoXt_zzjg_yh.ITEM_YHMM);
            
            DataBus userObj = context.getRecord(inputNode);
            //设置密码修改日期
            userObj.setValue("mmxgrq", DateUtil.getYMDTime());
            log.debug("页面获取的密码数据："+userObj);
            String jmm = MD5.md5(userObj.getValue("jmm").toUpperCase()).toUpperCase();
            String yhmm = MD5.md5(userObj.getValue("yhmm").toUpperCase()).toUpperCase();
            String qrmm = MD5.md5(userObj.getValue("qrmm").toUpperCase()).toUpperCase();
            if (!yhmm.equalsIgnoreCase(qrmm)) {
                throw new TxnDataException("","新密码和确认密码不一样!");
            }
            if (!jmm.equalsIgnoreCase(dlmm)) {
                throw new TxnDataException("","原密码输入错误！");
            }
            execResult.setValue(VoXt_zzjg_yh.ITEM_YHMM, yhmm);
            BaseTable table = TableFactory.getInstance()
                    .getTableObject(this, TABLE_NAME);
            table.executeFunction(MODIFY_PASSWORD_FUNCTION, context,
                                  execResult, outputNode);

    }
    /**
     * 初始化密码
     * 
     * @param context
     *            交易上下文
     * @throws cn.gwssi.common.component.exception.TxnException
     */
    public void txn807007(TxnContext context) throws TxnException {
//        try {
    		//处理多行记录
            Recordset rs = context.getRecordset(inputNode);
            /**
             * #系统默认用户名密码
				bjais.default.username=system
				bjais.default.password=manager
				bjais.default.initpassword=222222
             */
            String INIT_PASSWORD=null;
            try {
            	INIT_PASSWORD = ResourceBundle.getBundle("app").getString("userDefaultPwd");
			} catch (Exception e) {
				log.error("未能读取系统的初始化用户密码");
			}
            if (INIT_PASSWORD == null || INIT_PASSWORD.length() <= 0){
                INIT_PASSWORD = "222222";
            }
            log.info("初始化用户密码源码：" + INIT_PASSWORD);
            INIT_PASSWORD = MD5.md5(INIT_PASSWORD.toUpperCase());
            String yhxm = "";
            if (rs != null && !rs.isEmpty()) {
                for (int i = 0; i < rs.size(); i++) {
                    rs.get(i).setValue(VoXt_zzjg_yh.ITEM_YHMM,
                                       INIT_PASSWORD.toUpperCase());
                    rs.get(i).setValue("mmxgrq", DateUtil.getYMDTime());
                    log.debug("初始化用户密码：" + rs.get(i));
                    yhxm+= "," + rs.get(i).getValue("yhxm");
                }
            }
            if(!yhxm.equals("")) yhxm = yhxm.substring(1);
            //处理一行记录
            //设置密码修改日期
            //context.getRecord(inputNode).setValue("mmxgrq", DateUtil.getYMDTime());
            BaseTable table = TableFactory.getInstance()
                    .getTableObject(this, TABLE_NAME);
            table.executeFunction(UPDATE_INTE_PSW, context, inputNode,
                                  outputNode);
            
            setBizLog("初始化用户密码：", context,yhxm);
//        } catch (Exception e) {
//            CSDBException.throwCSDBException(e);
//        }
    }
    
    /**
     * 检验账号
     * 
     * @param context
     *            交易上下文
     * @throws cn.gwssi.common.component.exception.TxnException
     */
    public void txn807009(TxnContext context) throws TxnException {
    	String yhzh = context.getRecord(inputNode).getValue(VoXt_zzjg_yh.ITEM_YHZH);
        if(isYhzhExist(context,yhzh)){
        	throw new TxnDataException("","该账号已存在!");
        }
    }  
    
    /**
     * 检验账号
     * 
     * @param context
     *            交易上下文
     * @throws cn.gwssi.common.component.exception.TxnException
     */
    public void txn807010(TxnContext context) throws TxnException {
    	String yhzh = context.getRecord(inputNode).getValue(VoXt_zzjg_yh.ITEM_YHZH);
        
    } 

    /**
     * 登录
     * 
     * @param context
     *            交易上下文
     * @throws cn.gwssi.common.component.exception.TxnException
     */
    public void login(TxnContext context) throws TxnException {
//        VoXt_zzjg_yh yhVO = new VoXt_zzjg_yh();
//        yhVO.setYhzh(context.getValue(Constants.LOGIN_NAME).toUpperCase());
//        context.addRecord(GgkzConstants.USER_INFO, yhVO);
//        try {
//            BaseTable table = TableFactory.getInstance()
//                    .getTableObject(this, TABLE_NAME);
//            table.executeFunction(LOGIN_SELECT_FUNCTION, context,
//                                  GgkzConstants.USER_INFO,
//                                  GgkzConstants.USER_INFO);
    	String yhzh = context.getValue(Constants.LOGIN_NAME);
        String sql="select * from xt_zzjg_yh_new where sfyx ='0' and upper(yhzh)='"+yhzh.toUpperCase()+"'";
        BaseTable table = TableFactory.getInstance().getTableObject(this,
                                                                    TABLE_NAME);
        try {
            int num = table.executeRowset(sql, context, GgkzConstants.USER_INFO);
        } catch (TxnDataException ex) {
            if (ex.getErrCode().compareTo(
            		"SQL000"
            ) == 0) {
                // 数据库无数据
                context.clear();
            } else {
                throw ex;
            }
        }
    }
    /**
     * 更新所属机构名称
     * @param context
     * @throws TxnException
     */
    public void updateJgname(TxnContext context) throws TxnException {
    	
    	
        BaseTable table = TableFactory.getInstance().getTableObject(this, TABLE_NAME);
        
        table.executeFunction("updateJgname", context, "updateJgname", "updateJgname");
    }
    /**
     * 判断用户帐号和用户编号是否已经存在
     * 
     * @param context
     * @param yhzh
     * @param yhbh
     * @return
     * @throws TxnException
     */
    private boolean isYhzhExist(TxnContext context, String yhzh)
                                                                throws TxnException {
        boolean result = true;
        log.debug("isYhzhExist方法中的yhzh参数:"+yhzh);
        // 判断输入的用户帐号和用户编号非空
        if (yhzh == null)
            return false;

        // 根据用户帐号或用户编号查询用户信息
//        String sql = SQLConfig.get("807000-0001", yhzh);
        String sql="select yhid_pk from xt_zzjg_yh_new where sfyx ='0' and upper(yhzh)='"+yhzh.toUpperCase()+"'";
        BaseTable table = TableFactory.getInstance().getTableObject(this,
                                                                    TABLE_NAME);
        try {
            int num = table.executeRowset(sql, context, "wjys");
            if (num < 1) {
                result = false;
            }
        } catch (TxnDataException ex) {
            if (ex.getErrCode().compareTo(
            		"SQL000"
            		) == 0) {
                // 数据库无数据
                result = false;
            } else {
                throw ex;
            }
        }
        return result;
    }
    

	protected void prepare(TxnContext arg0) throws TxnException
	{
		// TODO Auto-generated method stub
		
	}
	/**
	 * 记录日志
	 * @param type
	 * @param context
	 */
    private void setBizLog (String type,TxnContext context,String jgmc){
    	
    	context.getRecord("biz_log").setValue("desc", type + jgmc);
    }	
}
