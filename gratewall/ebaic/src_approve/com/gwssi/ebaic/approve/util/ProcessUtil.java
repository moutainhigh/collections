package com.gwssi.ebaic.approve.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.gwssi.ebaic.common.util.EbaicConsts;
import com.gwssi.ebaic.domain.BeWkEntBO;
import com.gwssi.ebaic.domain.BeWkReqBO;
import com.gwssi.ebaic.domain.BeWkReqprocessBO;
import com.gwssi.ebaic.domain.SysmgrUser;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.persistence.jdbc.StoredProcParam;
import com.gwssi.optimus.util.DateUtil;
import com.gwssi.rodimus.dao.ApproveDaoUtil;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.sms.SmsUtil;
import com.gwssi.rodimus.sms.domain.SmsBusiType;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.torch.util.UUIDUtil;
/**
 * 辅助审查、核准  审核工具类
 * 
 * @author xupeng
 *
 */
public class ProcessUtil {
    private ProcessUtil() {};
    
    /**
     * 核准通过
     * @param gid
     * @param operationType
     * @param curStep
     * @param nextStep
     */
    public static void approveOperation(IPersistenceDAO approveDao,IPersistenceDAO ebaicDao,String gid) {
        if(StringUtils.isBlank(gid)) {
            throw new EBaicException("业务流水号不能为空!");
        }
        BeWkReqBO beWkReqBO = ApproveDaoUtil.getInstance().queryForRowBo("select * from be_wk_requisition t where t.gid=?", BeWkReqBO.class, gid);
        BeWkEntBO beWkEntBO = ApproveDaoUtil.getInstance().queryForRowBo("select * from be_wk_ent t where t.gid=?", BeWkEntBO.class, gid);
        
        if(beWkReqBO==null) {//找不到申请单，退出。
            throw new EBaicException("数据异常：找不到申请单数据!");
        }
        if(beWkEntBO==null) {//找不到企业信息
            throw new EBaicException("数据异常：找不到企业数据!");
        }
        String operationType = beWkReqBO.getOperationType();
        if(StringUtils.isBlank(operationType)) {//不能确定业务类型
            throw new EBaicException("不能确定业务类型!");
        }
        if ((EbaicConsts.SQYW_PTSL).equals(operationType)) {//普通设立
            savePTSL(approveDao,ebaicDao,beWkEntBO,beWkReqBO);
        }else{
        	throw new EBaicException("业务类型（"+operationType+"）不支持，目前仅支持普通设立（10）业务!");
        }
    }
     
    /**
     * 普通设立
     * @param beWkReqBO
     * @param beWkEntBO
     */
    @SuppressWarnings("rawtypes")
	public static void savePTSL(IPersistenceDAO approveDao,IPersistenceDAO ebaicDao,BeWkEntBO beWkEntBO,BeWkReqBO beWkReqBO){
    	
    	//0.如果核准过，那么就不在执行，直接return
    	String state = beWkReqBO.getState();
    	if("8".equals(state)){
    		return ;
    	}
    	if("9".equals(state)){
    		throw new EBaicException("核准失败：当前业务状态为已驳回。");
    	}
    	if("12".equals(state)){
    		throw new EBaicException("核准失败：当前业务状态为已终止。");
    	}
    	//如果54库cp_rs_ent 中有企业注册号，则return
    	String ebaicRegNO = beWkEntBO.getRegNo();
    	List<String> approveParams = new ArrayList<String>();
    	List<Map<String, Object>> ret = null;
    	if(StringUtil.isNotBlank(ebaicRegNO)){
    		approveParams.add(ebaicRegNO);
    		
			if(approveDao==null){
	    		approveDao = ApproveDaoUtil.getInstance().getDao();
	    	}
			String sql = "select count(*) from cp_rs_ent ent where ent.reg_no = ? ";
			ret = ApproveDaoUtil.getInstance().queryForList(approveDao,sql, approveParams);
			if(ret!=null&&!ret.isEmpty()){
    			return ;
    		}
    	}
    	
    	//1、法定代表人移动电话检验
    	String gid = beWkReqBO.getGid();
    	List<String> params1 = new ArrayList<String>();
    	if(StringUtil.isBlank(gid)){
    		throw new EBaicException("数据异常：获取业务主键失败。");
    	}
    	params1.add(gid);
    	String sql = " select l.le_rep_mob from be_wk_le_rep l where l.gid=?";
    	List<Map<String, Object>> list =DaoUtil.getInstance().queryForList(ebaicDao, sql, params1);
		
    	if(list==null||list.isEmpty()){
    		throw new EBaicException("数据异常：获取法定代表人信息失败。");
    	}
		Map<String,Object> map = list.get(0);
    	String legMobTel = StringUtil.safe2String(map.get("leRepMob"));
    	if(StringUtil.isBlank(legMobTel)){
    		throw new EBaicException("数据异常：法定代表人未录入移动电话码。");
    	}
    	
    	String entType = beWkEntBO.getEntType();
        if(StringUtil.isBlank(entType)){
        	throw new EBaicException("核准失败：不能确定企业类型。");
        }
        String regOrg = beWkReqBO.getRegOrg();
        if(StringUtil.isBlank(regOrg)){
        	throw new EBaicException("核准失败：不能确定核准机关。");
        }
        if (regOrg.length() != 6 && regOrg.length() != 9){
            throw new EBaicException("登记机关代码出错，获取注册号失败。");
        } 
        //统一社会信用代码，如果已经保存了，则不会重新生成
        String uniScid = beWkEntBO.getUniScid();
        if(StringUtils.isBlank(uniScid)) {
        	// 生成统一社会信用代码，并保存到ent表
            uniScid = getUniscid(beWkReqBO, beWkEntBO);
        }
        //注册号，如果已经保存了，则不会重新生成
        String regNo = beWkEntBO.getRegNo(); 
        if(StringUtils.isBlank(regNo)) {
            regNo = getRegNo(beWkReqBO, beWkEntBO);
            beWkEntBO.setRegNo(regNo);
			ApproveDaoUtil.getInstance().update(approveDao,beWkEntBO);
			DaoUtil.getInstance().update(ebaicDao,beWkEntBO);
        }
        //2.核准文号
        String notNo = beWkEntBO.getNameAppId();
        String accountPassWord = "";//企业账号生成默认密码
        
        List<StoredProcParam> params = new ArrayList<StoredProcParam>();
        params.add(new StoredProcParam(1, uniScid, StoredProcParam.IN, oracle.jdbc.OracleTypes.VARCHAR));
        params.add(new StoredProcParam(2, regNo, StoredProcParam.IN, oracle.jdbc.OracleTypes.VARCHAR));
        params.add(new StoredProcParam(3, notNo , StoredProcParam.IN, oracle.jdbc.OracleTypes.VARCHAR));
        params.add(new StoredProcParam(4, beWkEntBO.getGid() , StoredProcParam.IN, oracle.jdbc.OracleTypes.VARCHAR));
        params.add(new StoredProcParam(5, accountPassWord , StoredProcParam.OUT, oracle.jdbc.OracleTypes.VARCHAR));
        params.add(new StoredProcParam(6, "" , StoredProcParam.OUT, oracle.jdbc.OracleTypes.VARCHAR));
        List resultList = null;
		
		if(approveDao==null){
    		approveDao = ApproveDaoUtil.getInstance().getDao();
    	}
		resultList = ApproveDaoUtil.getInstance().callStoreProcess(approveDao, "{call proc_approve_setup(?,?,?,?,?,?)}", params);
        //给企业法人发送短信，告知生成的默认企业账号密码
        if(resultList!=null&&resultList.size()>0) {
            accountPassWord = (String) resultList.get(0);
            String entName = beWkEntBO.getEntName();
            
            Map<String,Object> smsParams = new HashMap<String,Object>();
            smsParams.put("entName", entName);
            smsParams.put("pwd", accountPassWord);
            
            if(StringUtils.isNotBlank(legMobTel)&&StringUtils.isNotBlank(accountPassWord)) {
                // 企业账号
    			SmsUtil.send(legMobTel, SmsBusiType.APPROVE_ENT_ACCOUNT, smsParams);
            }
            String linkmanMobile = getLinkmanMobile(beWkReqBO);
            if(StringUtil.isNotBlank(linkmanMobile)){
	            // 取照通知
				SmsUtil.send(linkmanMobile, SmsBusiType.APPROVE_GET_CERTICATE, smsParams);
            }
        }
    }
    public static String getLinkmanMobile(BeWkReqBO beWkReqBO) {
		if(beWkReqBO==null){
			return "";
		}
		String ret = beWkReqBO.getMobTel();
		if(StringUtil.isBlank(ret)){
			String userId = beWkReqBO.getAppUserId();
			String sql = "select y.mobile from t_pt_yh y where y.user_id = ?";
			ret = DaoUtil.getInstance().queryForOneString(sql, userId);
		}
		if(StringUtil.isBlank(ret)){
			String cerType = beWkReqBO.getCertType();
			String cerNo = beWkReqBO.getCertNo();
			String sql = "select i.mobile from sysmgr_identity i where i.cer_type = ? and i.cer_no = ?";
			ret = DaoUtil.getInstance().queryForOneString(sql, cerType, cerNo);
		}
		if(ret==null){
			ret = "";
		}
		return ret;
	}
    /**
     * 查询法定代表人信息
     * @param beWkReqBO
     * @return
     */
    public static String getBeWkLeRepMobile(String gid) {
		String sql = "select l.le_rep_mob from be_wk_le_rep l where l.gid = ?";
		String leRepMobile = DaoUtil.getInstance().queryForOneString(sql, gid);
		return leRepMobile;
	}
	/**
     * 点击审查弹出各种页签界面，第一次点击保存的时候，向be_wk_reqprocess表中插入一条记录
     * @param gid
     * @throws OptimusException
     */
    public static void generatorBeWkReqprocess(String gid) {
        if(StringUtils.isBlank(gid)) {
            throw new EBaicException("数据异常!");
        }
        SysmgrUser sysmgrUser=ApproveUserUtil.getLoginUser();
        if(null == sysmgrUser){
            throw new EBaicException("登录超时，请重新登录。");
        }
        BeWkReqprocessBO beWkReqprocessBO = (BeWkReqprocessBO) DaoUtil.getInstance().queryForRowBo("select * from be_wk_reqprocess t where t.state='16' and t.process_date is not null and t.pro_end_date is null and t.gid=?",BeWkReqprocessBO.class, gid);
        if(beWkReqprocessBO==null) {
            //String requisitionId = DaoUtil.getInstance().queryForOneString("select t.requisition_id from be_wk_requisition t where t.gid=?", gid);
            BeWkReqBO beWkReqBO = DaoUtil.getInstance().get(BeWkReqBO.class, gid);//gid和requisitionId一样
            if(beWkReqBO==null) {
                throw new EBaicException("数据异常!");
            }
            //更新bewkrequisition表中state为16（审核中）
            String currentDateStr = DateUtil.toDateStr(DateUtil.getCurrentDate());
            //DaoUtil.getInstance().execute("update be_wk_requisition t set t.state='16',t.version=nvl(version,0)+1,t.timestamp = to_date(?,'yyyy-MM-dd HH24:mi:ss') where t.requisition_id=?",currentDateStr,requisitionId);
            DaoUtil.getInstance().execute("update be_wk_requisition t set t.state='16',t.timestamp = to_date(?,'yyyy-MM-dd HH24:mi:ss') where t.requisition_id=?",currentDateStr,gid);
            beWkReqprocessBO = new BeWkReqprocessBO();
            beWkReqprocessBO.setReqprocessId(UUIDUtil.getUUID());
            beWkReqprocessBO.setProcessDate(DateUtil.getCurrentDate());
            beWkReqprocessBO.setTimestamp(DateUtil.getCurrentDate());
            beWkReqprocessBO.setRequisitionId(gid);
            beWkReqprocessBO.setRegOrg(sysmgrUser.getOrgCodeFk());
            beWkReqprocessBO.setState("16");//审核中
            beWkReqprocessBO.setProcessStep(beWkReqBO.getCurStep());
            if("10".equals(beWkReqBO.getCurStep())) {
                beWkReqprocessBO.setProcessNotion("辅助审查中。");
            }
            if("12".equals(beWkReqBO.getCurStep())) {
                beWkReqprocessBO.setProcessNotion("核准中。");
            }
            beWkReqprocessBO.setGid(gid);
            beWkReqprocessBO.setUserId(sysmgrUser.getUserId());
            beWkReqprocessBO.setUserName(sysmgrUser.getUserName());
            DaoUtil.getInstance().insert(beWkReqprocessBO);
        }
    }
	/**
	 * 根据gid获取当前process记录
	 * @param gid
	 * @return
	 * @throws OptimusException
	 */
	public static BeWkReqprocessBO findCurrentProcess(String gid){
		String sql = "select t.* from Be_Wk_Reqprocess t where t.pro_end_date is null and t.process_date is not null and t.state='16' and t.gid= ? order by t.process_date desc";
		List<BeWkReqprocessBO> list = DaoUtil.getInstance().queryForListBo(sql, BeWkReqprocessBO.class, gid);
		BeWkReqprocessBO beWkReqprocessBO = null;
		if(list!=null && list.size()>0){
			beWkReqprocessBO = list.get(0);
		}
		return beWkReqprocessBO;
	}
	/**
	 * 获取注册号
	 * @param beWkReqBO
	 * @param beWkEntBO
	 * @return
	 * @throws OptimusException 
	 */
	public static String getRegNo(BeWkReqBO beWkReqBO,BeWkEntBO beWkEntBO) {
	    // 读取注册号。
        String regNo = beWkEntBO.getRegNo();
        // 如果注册号没有，生成一个新的注册号。
        if (StringUtils.isNotBlank(regNo)) {
        	return regNo;
        }
        
        // 内资用内资注册号，外资用外资注册号
        String entType = beWkEntBO.getEntType();
        int entTypeInt = Integer.parseInt(entType);
        if(entTypeInt<5000||entTypeInt>=9000){
            regNo = getFileNo(EbaicConsts.BHMB_ZCH);
        }else{
            regNo = getFileNo(EbaicConsts.BHMB_WZZCH);
            // 外资注册号前面以4开头
            regNo=EbaicConsts.ZCH_WZ + regNo;
        }
        if (StringUtils.isBlank(regNo)||regNo.trim().length()!= 8) {
            throw new EBaicException("注册号生成异常!");
        }
        if(entTypeInt<5000||entTypeInt>=9000){
            regNo = getZch(beWkReqBO.getRegOrg(), regNo); // 生成注册号
        }else {
            // 外资用的登记机关都是市局
            regNo = getZch(EbaicConsts.BJAIC, regNo); // 生成注册号
        }
        return regNo;
	}
	// 生成注册码
   public static String getZch(String regOrg, String regNo) {
          if (StringUtil.isBlank(regNo)) {
              throw new EBaicException("获取注册号失败!");
          } 
          StringBuffer buf = new StringBuffer();
          if (regOrg.length() == 6) {
              buf.append(regOrg);
          }else if(regOrg.length() == 9) {
              buf.append(regOrg.substring(0, 6));
          }else {
              throw new EBaicException("登记机关代码出错，获取注册号失败。");
          } 
          if (regNo.length() == 8) {
              buf.append(regNo);
          }else {
              throw new EBaicException("获取注册号失败!");
          } 
          // 从递归中获取公式结束后的余数
          int m = 10;
          int t;
          int n;
          for (int i = 0; i < buf.toString().length(); i++) {
             String sub = buf.toString().substring(i, i + 1);
             int a = Integer.parseInt(sub);
             n = a + m;
             if ((n % 10) == 0) m = (10 * 2) % 11;
             else m = ((n % 10) * 2) % 11;
          }
          // 求公式（m+t)%10=1中t的值
          if (m > 1) t = 11 - m;
          else t = 1 - m;
          buf.append(t);
          if (buf.toString().length() != 15) {
              throw new EBaicException("获取注册号失败!");
          }else {
              return buf.toString();
          }
     }
    /**
	 * 获取文号数据
	 *  key ： 100 或 103 ，用于生成注册号。
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings("rawtypes")
    private static String getFileNo(String key) {
            // 查询数据，获取文号数据
            Map map = new HashMap();
            String sql = "select a.fileno_reset,a.fileno_year,a.fileno_no,a.fileno_value,a.fileno_length from sysmgr_fileno a where a.fileno_id=?";
            map = ApproveDaoUtil.getInstance().queryForRow(sql, key);
            String fileno_reset = (String) map.get("filenoReset");
            String fileno_year = (String) map.get("filenoYear");
            String fileno_no = String.valueOf((BigDecimal) map.get("filenoNo"));
            String fileno_value = (String) map.get("filenoValue");
            String fileno_length = String.valueOf((BigDecimal) map.get("filenoLength"));
            // 获得当前年
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
            String strCurrentYear = dateFormat.format(new Date());
            // 如果该模板可根据文号重设标志和年更改 则更改
            // filenoreset=0 可根据年度变化更改 1不可根据年度变化更新
            if ("1".equals(fileno_reset)) {
                String updatesql = "update sysmgr_fileno a set  a.fileno_no=? where a.fileno_id=?";
                ApproveDaoUtil.getInstance().execute(updatesql, Integer.valueOf(fileno_no) + 1,key);
            } else if (fileno_year.trim().equals(strCurrentYear)) {
                String updatesql = "update sysmgr_fileno a set  a.fileno_no=? where a.fileno_id=?";
                ApproveDaoUtil.getInstance().execute(updatesql,Integer.valueOf(fileno_no) + 1,key);
                //新的一年开始，开始号会用两次 应先增再取
                int intFileno_no_New = Integer.valueOf(fileno_no) + 1;
                fileno_no = String.valueOf(intFileno_no_New);
            } else {
                // 1)更新年
                // 2)重设为1
                String updatesql = "update sysmgr_fileno a set a.fileno_year=?, a.fileno_no=? where a.fileno_id=?";
                ApproveDaoUtil.getInstance().execute(updatesql,strCurrentYear,1,key);
                // 如果是更新年的情况，取新年的第一个记录
                Map mapNew = new HashMap();
                mapNew = DaoUtil.getInstance().queryForRow(sql,key);
                fileno_year = (String) mapNew.get("FILENO_YEAR");
                fileno_no = String.valueOf((BigDecimal) mapNew.get("FILENO_NO"));
            }
            // 补位 不够长度补0
            StringBuffer strb = new StringBuffer();
            int iFilenoLen = Integer.valueOf(fileno_length);
            int iFilenoNoLen = fileno_no.toString().length();
            for (int i = 0; i < iFilenoLen - iFilenoNoLen; i++) {
                strb.append("0");
            }
            // 模板字符串里头必须包括2个关键字 1)YYYY 2)NUM
            String strFileNo="";
            strFileNo = fileno_value.replaceAll("YYYY", fileno_year);
            strFileNo = strFileNo.replaceAll("NUM", strb + fileno_no);
            return strFileNo;
    }
    /**
	 * 生成统一社会信用代码
	 * 
	 * @return
	 * @throws OptimusException 
	 */
	public static String getUniscid(BeWkReqBO beWkReqBO,BeWkEntBO beWkEntBO) {
	    String uniScid = "";
	    if(beWkReqBO==null) {//找不到申请单，退出。
            throw new EBaicException("数据异常：找不到申请单!");
        }
        if(beWkEntBO==null) {//找不到企业信息
            throw new EBaicException("数据异常：找不到企业信息!");
        }
        String operationType = beWkReqBO.getOperationType();
        if(StringUtils.isBlank(operationType)) {//不能确定业务类型
            throw new EBaicException("数据异常!");
        }
        String entType = beWkEntBO.getEntType();
        if(StringUtils.isBlank(entType)) {//不能确定企业类型
            throw new EBaicException("数据异常：不能确定企业类型!");
        }
        //生成统一社会信用代码
        // 9 是对的
        String managerNo = "9";//第1位：登记管理部门代码，按国务院序列规则，3表示工商部门
        String typeNo = "1";//第2位：机构类别代码，1表示企业（含除集团、外国常驻代表机构外的所有企业类型）、3表示农民专业合作社
        
        if(StringUtils.isNotBlank(entType)){
            if("9100".equals(entType) || "9200".equals(entType)){
                typeNo = "3";
            }
        }
        String areaCode = beWkReqBO.getRegOrg();//第3-8位：登记管理机关行政区划码
        if(StringUtils.isNotBlank(areaCode)){
            areaCode = areaCode.substring(0, 6);
        }
        if(StringUtil.isBlank(areaCode)){
        	throw new EBaicException("数据异常：登记管理机关行政区划码为空!");
        }
        String organCode = beWkEntBO.getOrganCode();//第9-17位：主体标识码（组织机构代码）
        if(StringUtils.isNotBlank(organCode)){
            organCode = organCode.trim();
        }
//        if(StringUtil.isBlank(areaCode)){
//        	throw new EBaicException("数据异常：主体标识码（组织机构代码）为空!");
//        }
        //设立
        if(StringUtils.isNotBlank(operationType)&& (EbaicConsts.SQYW_PTSL.equals(operationType))){
            if(StringUtils.isBlank(organCode) || organCode.length()!=9){
                organCode = getOrganCodeNo();
            }
            //生成统一社会信用代码
            uniScid=getCreditCode(managerNo, typeNo, areaCode, organCode);//第18位：统一社会信用代码的校验码
            if (StringUtils.isBlank(uniScid) || uniScid.length()!=18) {
                throw new EBaicException("生成统一社会信用代码失败，请与系统管理员联系!");
            }
        }
        if (StringUtils.isNotBlank(uniScid) && uniScid.length()==18) {
            //更新组织机构代码及统一社会信用代码
            beWkEntBO.setOrganCode(organCode);
            beWkEntBO.setUniScid(uniScid);
            DaoUtil.getInstance().update(beWkEntBO);
            ApproveDaoUtil.getInstance().update(beWkEntBO);
            return uniScid;
        }else{
        	 throw new EBaicException("生成统一社会信用代码失败，请与系统管理员联系!");
        }
	}
	 /**
     * md31选法
     * @param managerNo 第1位 登记管理机关代码，工商为3或9
     * @param typeNo 第2位 1企业 2个体 3农专 4其他
     * @param areaCode  第3-8位 区域代码，分局或市局的代码 例110105
     * @param orgCode  第9-17位 代码，9位的组织机构代码号
     * @return 返回 最终的统一社会信用代码
     */
    public static String getCreditCode(String managerNo, String typeNo,
            String areaCode, String orgCode) {
        // 17位 加权因子数组
        int[] jq_nums = new int[] { 1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25,13, 8, 24, 10, 30, 28 };
        String all_code = managerNo + typeNo + areaCode + orgCode;
        int total = 0;
        for (int i = 0; i < all_code.length(); i++) {
            int num = 0;
            if (!StringUtil.isNumeric(all_code.charAt(i) + "")) {
                num = getNoByChar18(all_code.charAt(i));
            } else {
                num = Integer.parseInt(all_code.charAt(i) + "");
            }
            total += num * jq_nums[i];
        }
        if (total % 31 == 0) {
            return all_code+"0";
        }else{
            int result = 31 - (total % 31);
            return all_code + get18CharResult(result);
        }
    }
    /**
     * 获取主体标识码（组织机构代码）
     * @param jdbcTemplate
     * @return
     */
    @SuppressWarnings("rawtypes")
    public synchronized static String getOrganCodeNo() {
        String organCode="";
        String no="";
        Map map = new HashMap();
        String selectSql = "select no,code from sysmgr_org_code where no = (select max(t.no) + 1 from sysmgr_org_code t where t.is_use = '1' and use_date > trunc(sysdate - 3))";
        map = ApproveDaoUtil.getInstance().queryForRow(selectSql);
        if(map == null || map.isEmpty()){
            String sql = "select no,code from sysmgr_org_code where no = (select max(t.no) + 1 from sysmgr_org_code t where t.is_use = '1')";
            map = ApproveDaoUtil.getInstance().queryForRow(sql);
        }
        organCode = (String)map.get("code");
        no = String.valueOf(map.get("no"));
        if(no != null && !"".equals(no) && organCode != null && !"".equals(organCode)){
            //更新主体标识码库
            String updateCodeSql = "update sysmgr_org_code set is_use='1',use_date=sysdate where no="+Integer.valueOf(no);
            ApproveDaoUtil.getInstance().execute(updateCodeSql);
        }
        return organCode;
    }
    /**
     * 18位校验，根据字母获取对应的数字值
     * @param ch
     * @return
     */
    public static int getNoByChar18(char ch) {
        Map<Character,Integer> map = new HashMap<Character,Integer>();
        map.put('A', 10);
        map.put('B', 11);
        map.put('C', 12);
        map.put('D', 13);
        map.put('E', 14);
        map.put('F', 15);
        map.put('G', 16);
        map.put('H', 17);
        map.put('J', 18);
        map.put('K', 19);
        map.put('L', 20);
        map.put('M', 21);
        map.put('N', 22);
        map.put('P', 23);
        map.put('Q', 24);
        map.put('R', 25);
        map.put('T', 26);
        map.put('U', 27);
        map.put('W', 28);
        map.put('X', 29);
        map.put('Y', 30);
        return map.get(ch);
    }
    
    
    public static String get18CharResult(int result) {
        Map<Integer,String> map = new HashMap<Integer,String>();
        map.put(0, "0");
        map.put(1, "1");
        map.put(2, "2");
        map.put(3, "3");
        map.put(4, "4");
        map.put(5, "5");
        map.put(6, "6");
        map.put(7, "7");
        map.put(8, "8");
        map.put(9, "9");
        map.put(10, "A");
        map.put(11, "B");
        map.put(12, "C");
        map.put(13, "D");
        map.put(14, "E");
        map.put(15, "F");
        map.put(16, "G");
        map.put(17, "H");
        map.put(18, "J");
        map.put(19, "K");
        map.put(20, "L");
        map.put(21, "M");
        map.put(22, "N");
        map.put(23, "P");
        map.put(24, "Q");
        map.put(25, "R");
        map.put(26, "T");
        map.put(27, "U");
        map.put(28, "W");
        map.put(29, "X");
        map.put(30, "Y");
        return map.get(result);
    }
    
    /**
     * 
     * @param regOrgFk
     * @return
     */
    public static String getRegOrgByFk(String regOrgFk){
    	String orgName = "";
    	String sql = "select r.org_name from sysmgr_org r where r.org_code = ?";
    	if(StringUtils.isNotBlank(regOrgFk)){
    		orgName = ApproveDaoUtil.getInstance().queryForOneString(sql, regOrgFk);
    	}
    	return orgName;
    }
    
    /**
     * 根据代码值得到代码名称
     * @param codevalue
     * @param dmTypeName
     * @return
     */
    public static String getNameValue(String codevalue, String dmTypeName) {
        String codeName="";
        String sql = "select t.code_name from sysmgr_cvalue t where t.type_id_fk=? and t.code_value=?";
        if(StringUtils.isNotBlank(codevalue)&&StringUtils.isNotBlank(dmTypeName)) {
            codeName =ApproveDaoUtil.getInstance().queryForOneString(sql,dmTypeName, codevalue);
        }
        return codeName;
    }
    
}
