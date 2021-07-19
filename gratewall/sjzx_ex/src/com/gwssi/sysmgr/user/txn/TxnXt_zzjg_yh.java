/*
 * @Header @Revision @Date 20070301
 * ===================================================== ���������Ŀ��
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
 * @desc �û�ά����̨������ ʵ���û����½����޸ġ�ɾ������ȡ�б�Ȳ���
 * @author adaFang
 * @version 1.0
 */
public class TxnXt_zzjg_yh extends TxnService {
    // ���ݱ�����
    private static final String TABLE_NAME = "xt_zzjg_yh";

    // ��ѯ�б�
    private static final String ROWSET_FUNCTION = "YHGL-select xt_zzjg_yh list";

    // ��ѯ�б�
    private static final String ROWSET_FOR_ORG_FUNCTION = "JGGL-select xt_zzjg_yh for org list";

    // ��ѯ��¼YHGL-select xt_zzjg_yh list
    private static final String SELECT_FUNCTION = "YHGL-select one xt_zzjg_yh";

    // �޸��û���Ϣ��¼
    private static final String UPDATE_FUNCTION = "YHGL-update one xt_zzjg_yh";

    // �޸��û���Ϣ��¼������
    private static final String UPDATE_WITHOUT_PSW_FUNCTION = "YHGL-update one without psw xt_zzjg_yh";

    // ���Ӽ�¼
    private static final String INSERT_FUNCTION = "YHGL-insert one xt_zzjg_yh";

    // �����û���Ч��¼
    private static final String STATUS_UNUSE_FUNCTION = "YHGL-update status xt_zzjg_yh";

    // �޸��û�����
    private static final String MODIFY_PASSWORD_FUNCTION = "YHGL-update password xt_zzjg_yh";

    // �����û�����ѯ
    private static final String LOGIN_SELECT_FUNCTION = "LOGIN-select one xt_zzjg_yh";

    // ��ʼ���û�����
    private static final String UPDATE_INTE_PSW = "update password  inte xt_zzjg_yh";

    public TxnXt_zzjg_yh() {

    }

    /**
     * ��ѯ�б��û�����
     * 
     * @param context
     *            ����������
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
            // �����б����
            PlainSequence.addIndex(context, outputNode);
            
    }

    /**
     * ��ѯ�б����ڵ���������ά����Ա��Ϣ����������
     * 
     * @param context
     *            ����������
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
            // �����б����
            PlainSequence.addIndex(context, outputNode);
//        } catch (Exception e) {
//            CSDBException.throwCSDBException(e);
//        }
    }
    
    /**
     * �޸ļ�¼���û�����and��������
     * 
     * @param context
     *            ����������
     * @throws cn.gwssi.common.component.exception.TxnException
     */
    public void txn807002(TxnContext context) throws TxnException {
//        try {
            String yhmm = context.getRecord(inputNode)
                    .getValue(VoXt_zzjg_yh.ITEM_YHMM);
            BaseTable table = TableFactory.getInstance()
                    .getTableObject(this, TABLE_NAME);
            // ������û�����Ϊ��ʱ,����������
            String yhid_pk = context.getRecord(inputNode).getValue(VoXt_zzjg_yh.ITEM_YHID_PK);
            String yhxm = context.getRecord(inputNode).getValue(VoXt_zzjg_yh.ITEM_YHXM);
        	String roleIds = context.getRecord(inputNode).getValue("roleids");
        	StringBuffer groupIds = new StringBuffer();
        	setGroupIds(table,groupIds,roleIds);
        	/* modified by jufeng 
        	boolean flag = addUserToCognos(groupIds,yhid_pk,yhxm);
        	if(!flag){
        		throw new TxnDataException("","�޸�cognos�û�ʧ��!");
        	}
        	*/
        	context.getRecord(inputNode).setValue("roleids", Constants.ROLEID + "," + roleIds);
        	context.getRecord(inputNode).setValue("rolenames", Constants.ROLENAME + "," + context.getRecord(inputNode).getValue("rolenames"));  
        	//context.getRecord(inputNode).setValue("zyzz", groupIds.toString());           
            if (yhmm == null || ("".equals(yhmm.trim()))) {
                log.debug("������������û�������Ϣ");
                table.executeFunction(UPDATE_WITHOUT_PSW_FUNCTION, context,
                                      inputNode, outputNode);
            } else {
                // ������û�����ǿ�ʱ����������Ϣ
            	context.getRecord(inputNode).setValue("mmxgrq", DateUtil.getYMDTime());
                table.executeFunction(UPDATE_FUNCTION, context, inputNode,
                                      outputNode);
            }
            setBizLog("�޸��û���", context,context.getRecord("record").getValue("yhxm"));
//        } catch (Exception e) {
//            CSDBException.throwCSDBException(e);
//        }
            
//    	try{
//    		PrivilegeManager.getInst().init("ͳ���ƶ�");
//    	}
//    	catch(Exception e){
//    		e.printStackTrace();
//    	}
    }

    /**
     * ���Ӽ�¼���û�����and��������
     * 
     * @param context
     *            ����������
     * @throws cn.gwssi.common.component.exception.TxnException
     */
    public void txn807003(TxnContext context) throws TxnException {
    	
            BaseTable table = TableFactory.getInstance()
                    .getTableObject(this, TABLE_NAME);
            String yhzh = context.getRecord(inputNode)
                    .getValue(VoXt_zzjg_yh.ITEM_YHZH);
            context.getRecord(inputNode).setValue("mmxgrq", DateUtil.getYMDTime());
            // �ж��û��ʺţ���¼�������û�����Ƿ��Ѿ�����
            if (isYhzhExist(context, yhzh)) {
                // �ʺŻ��Ŵ��ڣ�����
                throw new TxnDataException("","������û��ʺ��Ѵ���!");
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
            		throw new TxnDataException("","����cognos�û�ʧ��!");
            	}*/
                
                setBizLog("�����û���", context,context.getRecord("record").getValue("yhxm"));
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
			log.debug("û�м�¼");
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
     * ��ѯ��¼�����޸ģ��û�����and��������
     * 
     * @param context
     *            ����������
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
                // ���ռ�¼�쳣�׳��쳣����Ϣ��ʾ
                if (ex.getErrCode().compareTo(
                		"SQL000"
                ) == 0) {
                    throw new TxnDataException("","�û���¼������!");
                } else {
                    throw ex;
                }
            }

    }
    /**
     * ɾ����¼(��ɾ��,�����û���Ч״̬Ϊ��Ч)
     * 
     * @param context
     *            ����������
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
                    log.debug("ɾ���û���¼��" + rs.get(i));
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
            setBizLog("ͣ���û���", context,yhxm);
//        } catch (Exception e) {
//            CSDBException.throwCSDBException(e);
//        }
    }
    /**
     * �����û�(�����û���Ч״̬Ϊ��ЧЧ)
     * 
     * @param context
     *            ����������
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
                    log.debug("�����û���¼��" + rs.get(i));
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
            setBizLog("�����û���", context,yhxm);
//        } catch (Exception e) {
//            CSDBException.throwCSDBException(e);
//        }
    }    
    /**
     * �޸�����
     * 
     * @param context
     *            ����������
     * @throws cn.gwssi.common.component.exception.TxnException
     */
    public void txn807006(TxnContext context) throws TxnException {

    	VoUser operData = context.getOperData();
            //��ȡ��¼�ʺ�
            String dlzh = operData.getUserName();
            // �����������塣
            DataBus data = new DataBus();
            // �������ԡ�
            data.setValue(VoXt_zzjg_yh.ITEM_YHZH, dlzh);
            // ���봫�����߶���
            context.setValue("queryobj", data);
            // ��ȡ����ģ�Ͷ���������Ҫ��DAO�����ļ�����ƥ�䣬���ִ�Сд��
        	String yhzh = context.getValue(Constants.LOGIN_NAME);
            String sql="select * from xt_zzjg_yh_new where sfyx ='0' and upper(yhzh)='"+dlzh.toUpperCase()+"'";
            BaseTable xt_yh = TableFactory.getInstance().getTableObject(this,
                                                                        TABLE_NAME);
            try {
                int num = xt_yh.executeRowset(sql, context, "execResult");
                // ��̬���÷�������ѯ��ǰ�û���Ϣ
                // ����˵�����������ƣ������Ķ����������壬�������壨���أ���
                //table.executeFunction("validateUser", context, "queryobj",
                //                      "execResult");
            } catch (TxnDataException e) {
                // �����˳���
                if (e.getErrCode().compareTo(
                		"SQL000"
                ) == 0)
                    throw new TxnDataException("","�û���¼������!");
                throw e;
            }
            // ��ȡ�������塣
            DataBus execResult = context.getRecord("execResult");
            String dlmm = execResult.getValue(VoXt_zzjg_yh.ITEM_YHMM);
            
            DataBus userObj = context.getRecord(inputNode);
            //���������޸�����
            userObj.setValue("mmxgrq", DateUtil.getYMDTime());
            log.debug("ҳ���ȡ���������ݣ�"+userObj);
            String jmm = MD5.md5(userObj.getValue("jmm").toUpperCase()).toUpperCase();
            String yhmm = MD5.md5(userObj.getValue("yhmm").toUpperCase()).toUpperCase();
            String qrmm = MD5.md5(userObj.getValue("qrmm").toUpperCase()).toUpperCase();
            if (!yhmm.equalsIgnoreCase(qrmm)) {
                throw new TxnDataException("","�������ȷ�����벻һ��!");
            }
            if (!jmm.equalsIgnoreCase(dlmm)) {
                throw new TxnDataException("","ԭ�����������");
            }
            execResult.setValue(VoXt_zzjg_yh.ITEM_YHMM, yhmm);
            BaseTable table = TableFactory.getInstance()
                    .getTableObject(this, TABLE_NAME);
            table.executeFunction(MODIFY_PASSWORD_FUNCTION, context,
                                  execResult, outputNode);

    }
    /**
     * ��ʼ������
     * 
     * @param context
     *            ����������
     * @throws cn.gwssi.common.component.exception.TxnException
     */
    public void txn807007(TxnContext context) throws TxnException {
//        try {
    		//������м�¼
            Recordset rs = context.getRecordset(inputNode);
            /**
             * #ϵͳĬ���û�������
				bjais.default.username=system
				bjais.default.password=manager
				bjais.default.initpassword=222222
             */
            String INIT_PASSWORD=null;
            try {
            	INIT_PASSWORD = ResourceBundle.getBundle("app").getString("userDefaultPwd");
			} catch (Exception e) {
				log.error("δ�ܶ�ȡϵͳ�ĳ�ʼ���û�����");
			}
            if (INIT_PASSWORD == null || INIT_PASSWORD.length() <= 0){
                INIT_PASSWORD = "222222";
            }
            log.info("��ʼ���û�����Դ�룺" + INIT_PASSWORD);
            INIT_PASSWORD = MD5.md5(INIT_PASSWORD.toUpperCase());
            String yhxm = "";
            if (rs != null && !rs.isEmpty()) {
                for (int i = 0; i < rs.size(); i++) {
                    rs.get(i).setValue(VoXt_zzjg_yh.ITEM_YHMM,
                                       INIT_PASSWORD.toUpperCase());
                    rs.get(i).setValue("mmxgrq", DateUtil.getYMDTime());
                    log.debug("��ʼ���û����룺" + rs.get(i));
                    yhxm+= "," + rs.get(i).getValue("yhxm");
                }
            }
            if(!yhxm.equals("")) yhxm = yhxm.substring(1);
            //����һ�м�¼
            //���������޸�����
            //context.getRecord(inputNode).setValue("mmxgrq", DateUtil.getYMDTime());
            BaseTable table = TableFactory.getInstance()
                    .getTableObject(this, TABLE_NAME);
            table.executeFunction(UPDATE_INTE_PSW, context, inputNode,
                                  outputNode);
            
            setBizLog("��ʼ���û����룺", context,yhxm);
//        } catch (Exception e) {
//            CSDBException.throwCSDBException(e);
//        }
    }
    
    /**
     * �����˺�
     * 
     * @param context
     *            ����������
     * @throws cn.gwssi.common.component.exception.TxnException
     */
    public void txn807009(TxnContext context) throws TxnException {
    	String yhzh = context.getRecord(inputNode).getValue(VoXt_zzjg_yh.ITEM_YHZH);
        if(isYhzhExist(context,yhzh)){
        	throw new TxnDataException("","���˺��Ѵ���!");
        }
    }  
    
    /**
     * �����˺�
     * 
     * @param context
     *            ����������
     * @throws cn.gwssi.common.component.exception.TxnException
     */
    public void txn807010(TxnContext context) throws TxnException {
    	String yhzh = context.getRecord(inputNode).getValue(VoXt_zzjg_yh.ITEM_YHZH);
        
    } 

    /**
     * ��¼
     * 
     * @param context
     *            ����������
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
                // ���ݿ�������
                context.clear();
            } else {
                throw ex;
            }
        }
    }
    /**
     * ����������������
     * @param context
     * @throws TxnException
     */
    public void updateJgname(TxnContext context) throws TxnException {
    	
    	
        BaseTable table = TableFactory.getInstance().getTableObject(this, TABLE_NAME);
        
        table.executeFunction("updateJgname", context, "updateJgname", "updateJgname");
    }
    /**
     * �ж��û��ʺź��û�����Ƿ��Ѿ�����
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
        log.debug("isYhzhExist�����е�yhzh����:"+yhzh);
        // �ж�������û��ʺź��û���ŷǿ�
        if (yhzh == null)
            return false;

        // �����û��ʺŻ��û���Ų�ѯ�û���Ϣ
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
                // ���ݿ�������
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
	 * ��¼��־
	 * @param type
	 * @param context
	 */
    private void setBizLog (String type,TxnContext context,String jgmc){
    	
    	context.getRecord("biz_log").setValue("desc", type + jgmc);
    }	
}
