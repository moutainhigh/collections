package com.gwssi.ebaic.approve.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.gwssi.ebaic.domain.SysmgrUser;
import com.gwssi.optimus.util.DateUtil;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.StringUtil;

/**
 * 任务分配工具类。
 * 
 * @author sgz
 *
 */
public class AssignUtil {
    
	/**分配或领取。
	 * 
     * @param type
     * @param userId
     * @param reqIdList
     * @param type  0-分配，1-领取
     */
    public static void saveAssign(String curStep,String userId , List<String> reqIdList,String type) {
        // 0. 参数校验
        if(StringUtil.isBlank(curStep)||(!"10".equals(curStep)&&!"12".equals(curStep))){
            throw new EBaicException("curStep参数传递错误，只能是10或者12。");
        }
        if(StringUtil.isBlank(userId)){
            throw new EBaicException("请选择审核人员。");
        }
        if(reqIdList==null || reqIdList.isEmpty()){
            throw new EBaicException("请选择待分配业务。");
        }
        
        // 1. 取得审核人员信息
        SysmgrUser censor = ApproveUserUtil.getById(userId);
        if(censor==null){
            throw new EBaicException(String.format("未找到指定编号(%s)的审核人员。",userId));
        }
        
        // 2. 批量更新多条 be_wk_req 记录
        StringBuffer reqSql = new StringBuffer();
        String processNotion = "";
        if("10".equals(curStep)) {
            reqSql.append("update be_wk_requisition set CENSOR_USER_ID=?, CENSOR_No = ?,censor_name= ?,cur_step = '10',state = '15',timestamp=? where state ='2' and (cur_step = '10' or cur_step is null) and reg_org = ?");
            if("0".equals(type)) {
                processNotion = "分配给辅助审查人员"+censor.getUserName()+"。";
            }else {
                processNotion = "辅助审查人员"+censor.getUserName()+"领取了该笔辅助审查任务";
            }
        }
        if("12".equals(curStep)) {
            reqSql.append("update be_wk_requisition set approve_user_id=?, approve_no= ?,approve_name = ?,state = '15',timestamp=? where state in('2','3') and cur_step = '12' and reg_org = ?");
            if("0".equals(type)) {
                processNotion = "分配给核准人员"+censor.getUserName()+"。";
            }else {
                processNotion = "核准人员"+censor.getUserName()+"领取了该笔核准任务";
            }
        }
        List<Object> params = new ArrayList<Object>();
        params.add(censor.getUserId());
        params.add(censor.getStaffNo());
        params.add(censor.getUserName());
        params.add(DateUtil.getCurrentDate());
        String regOrg = censor.getOrgCodeFk();
        params.add(regOrg);
        
        reqSql.append(" and requisition_id in ( ");
        boolean isFirstReqId = true;
        for(String reqId : reqIdList){
            reqId = reqId.replaceAll("'", "");
            if(!isFirstReqId){
                reqSql.append(",");
            }else{
                isFirstReqId = false;
            }
            reqSql.append("'").append(reqId).append("'");
            //辅助审查，核准环节分配或者领取环节向be_wk_reqprocess表插入记录
            String insertSql = "insert into be_wk_reqprocess(reqprocess_id,requisition_id,process_date,user_id,user_name,"
                    + "process_step,pro_end_date,state,reg_org,gid,timestamp,process_notion) values(sys_guid(),?,sysdate,?,?,?,sysdate,?,?,?,sysdate,?)";
            DaoUtil.getInstance().execute(insertSql,reqId,userId,censor.getUserName(),curStep,"15",regOrg,reqId,processNotion);
        }
        reqSql.append(" ) ");
        
        DaoUtil.getInstance().execute(reqSql.toString(), params);
    }
    
    /**
     * 判断要分配的核准数据是否由其本人提交(一审一核)
     * @param userId
     * @param reqIdList
     */
    public static void checkAssign(String userId,List<String> reqIdList){
    	
    	// 1. 取得审核人员信息
        SysmgrUser user = ApproveUserUtil.getById(userId);
        if(user==null){
            throw new EBaicException(String.format("未找到指定编号(%s)的审核人员。",userId));
        }
    	
    	StringBuffer sql = new StringBuffer();
    	sql.append("select * from be_wk_requisition where state in('2','3') and cur_step = '12' and reg_org = ? and censor_user_id = ? and requisition_id in ( ");
    	 boolean isFirstReqId = true;
         for(String reqId : reqIdList){
             reqId = reqId.replaceAll("'", "");
             if(!isFirstReqId){
            	 sql.append(",");
             }else{
                 isFirstReqId = false;
             }
             sql.append("'").append(reqId).append("'");
         }
         sql.append(" ) ");
         List<String> paramlist = new ArrayList<String>();
         paramlist.add(user.getOrgCodeFk());
         paramlist.add(userId);
         
         List<Map<String,Object>> ret = DaoUtil.getInstance().queryForList(sql.toString(), paramlist);
         if(ret!=null && ret.size()>0){
        	 throw new EBaicException(String.format("分配数据中存在(%s)辅助审查的数据，不允许核准分配！", user.getUserName()));
         }
    }
    
    
    /**
     * 退回修改再分配或领取。
     * 
     * @param curStep
     * @param censorUserList
     * @throws EBaicException
     */
    public static void saveBackAssign(String curStep,List<String> censorUserList) throws EBaicException{
    	
    	//1、执行参数校验
    	if(StringUtil.isBlank(curStep) || (!"10".equals(curStep)&&!"12".equals(curStep))){
    		throw new EBaicException("curStep参数传递错误，只能是10或者12。");
    	}
    	if(censorUserList==null || censorUserList.isEmpty()){
    		throw new EBaicException("请选择审核人员！");
    	}
  
    	//2、配置更新be_wk_requisition表多条记录sql
    	StringBuffer reqSql = new StringBuffer();
    	
    	//10代表辅助审查退回再分配
    	if("10".equals(curStep)){
    		
    		reqSql.append("update be_wk_requisition req set CENSOR_No = ?,state = '15',timestamp=?,CENSOR_USER_ID=?,censor_name=? ");
    		/*reqSql.append(" where req.cur_step='10' and req.state='2' and req.censor_result ='12' "); 
    		reqSql.append(" and req.CENSOR_USER_ID = ?");*/
    		reqSql.append("where req.gid = ");
    		
    		reqSql.append("(select t.gid from ( ");
    		reqSql.append("select gid,user_name, user_id, sysdate from ( ");
    		reqSql.append("select  gid, user_id, user_name from( ");
    		reqSql.append("select process.*, row_number() over(partition by process.gid order by process.timestamp desc) rn ");
    		reqSql.append("from be_wk_reqprocess process where process.gid in ( ");
    		reqSql.append("select requisition.gid from be_wk_requisition requisition where requisition.censor_user_id is null and requisition.censor_name is null and requisition.censor_Result is null and requisition.censor_notion is null ");
    		reqSql.append("and requisition.state = '2' and requisition.reg_org = ? and requisition.operation_type = '10' ");
    		reqSql.append(") and process.process_step='10' and trim(process.process_result) = '12' ");
    		reqSql.append(") where rn <= 1 and user_id = ? group by gid,user_id, user_name ");
    		reqSql.append(")) t)");
    		
    	}
		//12代表核准退回再分配
    	if("12".equals(curStep)){
    	   /* reqSql.append("update be_wk_requisition set approve_no = ?,state = '15',timestamp=?");
            reqSql.append(" where cur_step='12' and state in('2','3') and approve_result='12' "); 
            reqSql.append(" and approve_user_id = ?");*/   
            
            
            reqSql.append("update be_wk_requisition req set approve_no = ?,state = '15',timestamp=?,approve_USER_ID=?,approve_name=? ");
    		/*reqSql.append(" where req.cur_step='10' and req.state='2' and req.censor_result ='12' "); 
    		reqSql.append(" and req.CENSOR_USER_ID = ?");*/
    		reqSql.append("where req.gid = ");
    		
    		reqSql.append("(select t.gid from ( ");
    		reqSql.append("select gid,user_name, user_id, sysdate from ( ");
    		reqSql.append("select  gid, user_id, user_name from( ");
    		reqSql.append("select process.*, row_number() over(partition by process.gid order by process.timestamp desc) rn ");
    		reqSql.append("from be_wk_reqprocess process where process.gid in ( ");
    		reqSql.append("select requisition.gid from be_wk_requisition requisition where requisition.approve_user_id is null and requisition.approve_name is null and requisition.approve_Result is null and requisition.approve_notion is null ");
    		reqSql.append("and requisition.state = '2' and requisition.reg_org = ? and requisition.operation_type = '10' ");
    		reqSql.append(") and process.process_step='12' and trim(process.process_result) = '12' ");
    		reqSql.append(") where rn <= 1 and user_id = ? group by gid,user_id, user_name ");
    		reqSql.append(")) t)");
            
            
            
            
	   	}
    	//3、根据userName获取审批平台用户信息、暂定循环更新be_wk_Requisition表
    	for (String userId : censorUserList) {
			SysmgrUser user = ApproveUserUtil.getById(userId);
			if(user==null){
				throw new EBaicException("获取用户失败!");
			}
			List<Object> params = new ArrayList<Object>();
			params.add(user.getStaffNo());
	        params.add(DateUtil.getCurrentDate());
	        params.add(userId);
	        params.add(user.getUserName());
	        params.add(user.getOrgCodeFk());
	        params.add(userId);
	        DaoUtil.getInstance().execute(reqSql.toString(), params);
    	}
    }
    
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    /**
     * 获取指定日期天数加1,返回String类型
     * @author xupeng
     * @return
     * @throws EBaicException 
     */
    public static String getDateAdd(String strdate) throws EBaicException{
    	if(StringUtil.isBlank(strdate)){
    		throw new EBaicException("传入日期为空！");
    	}
    	Date date = null;
    	try {
			date = sdf.parse(strdate);
		} catch (ParseException e) {
			throw new EBaicException("日期格式不正确："+e.getMessage());
		}
    	if(date==null){
    		throw new EBaicException("日期格式不正确!");
    	}
    	Calendar calendar = com.gwssi.rodimus.util.DateUtil.getCurrentTime();
    	calendar.setTime(date);
    	calendar.add(Calendar.DAY_OF_MONTH, 1);
    	String resultdate = sdf.format(calendar.getTime());
    	return resultdate;
    }
    
    /**
     * 判断查询条件中，起始时间是否小于截止时间
     * @author xupeng
     * @param startDate
     * @param endDate
     * @throws EBaicException
     */
    public static void compareDate(String startDate,String endDate) throws EBaicException{
    	// FIXME 确实是否 && 而不是 ||
    	if(StringUtil.isBlank(startDate) && StringUtil.isBlank(endDate)){
    		throw new EBaicException("传入日期为空！");
    	}
    	Date date_1 = null;
    	try {
			date_1 = sdf.parse(startDate);
		} catch (ParseException e) {
			throw new EBaicException("开始时间日期格式不正确："+e.getMessage());
		}

    	Date date_2 = null;
    	try {
			date_2 = sdf.parse(endDate);
		} catch (ParseException e) {
			throw new EBaicException("截止时间日期格式不正确："+e.getMessage());
		}
    	
    	if(date_1.after(date_2)){
    		throw new EBaicException("截止时间不能小于起始时间！");
    	}
    }
    
    
}
