package com.gwssi.ebaic.approve.approval.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gwssi.ebaic.approve.util.ApproveAuthUtil;
import com.gwssi.ebaic.approve.util.ApproveUserUtil;
import com.gwssi.ebaic.approve.util.AssignUtil;
import com.gwssi.ebaic.domain.SysmgrUser;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.util.DateUtil;
import com.gwssi.rodimus.util.StringUtil;

/**
 * 核准环节分配、领取。
 * 
 * @author shigaozhan
 */
@Service(value="approvalService")
public class ApprovalService extends BaseService {
	protected final static Logger logger = Logger.getLogger(ApprovalService.class);
	
    /**
     * 核准环节任务分配。
     * 
     * @param selectedApproveUserId
     * @param requisitionIds
     */
    public void saveAssign(String selectedApproveUserId, List<String> reqIdList) {
        String curStep = "12";// 环节， 10-辅助审查/受理， 12-核准
        //判断一审一核
        AssignUtil.checkAssign(selectedApproveUserId, reqIdList);
        
        AssignUtil.saveAssign(curStep, selectedApproveUserId, reqIdList,"0");
    }
    /**
     * 核准环节 退回修改再分配。
     * 
     * @param userIdArr
     * @param requisitionIdlist
     * @throws OptimusException 
     */
    public void saveBackAgainAssign(List<String> userIdArr) throws OptimusException {
        String curStep = "12";// 环节， 10-辅助审查/受理， 12-核准
        AssignUtil.saveBackAssign(curStep, userIdArr);
    }
    /**
     * 领取。
     * 
     * @param requisitionIdlist
     * @throws OptimusException
     */
	public void saveAdopt(List<String> requisitionIdlist) throws OptimusException {
        SysmgrUser sysmgrUser=ApproveUserUtil.getLoginUser();
        if(null==sysmgrUser){
        	throw new OptimusException("登录超时，请重新登录！");
        }
        AssignUtil.saveAssign("12", sysmgrUser.getUserId(), requisitionIdlist,"1");//核准环节
	}
	
	/**
	 * 判断当前数据是否为核准环节
	 * @param gid
	 * @return
	 * @throws OptimusException
	 */
	public boolean checkIsHz(String gid) throws OptimusException{
		if(StringUtil.isBlank(gid)){
			throw new OptimusException("参数传递错误!");
		}
		
		boolean isHz = ApproveAuthUtil.canApproved(gid);
		
		if(isHz){
			return true;
		}else{
			return false;
		}	
	}
	
	
	/**
	 * 退回分配到名下的待核准数据
	 * @param gid
	 * @throws OptimusException
	 */
	public void backAssign(String gid) throws OptimusException {
		if(StringUtil.isBlank(gid)){
			throw new OptimusException("参数传递错误！");
		}
		//判断是否为核准  核准为true
		boolean isHz = ApproveAuthUtil.canApproved(gid);
		//检验工作人员是否编辑过审核内容数据
        Long count = DaoUtil.getInstance().queryForOneLong("select COUNT(T.MODIFY_ID) from be_wk_modify t,be_wk_requisition n where t.gid=n.gid and t.version=n.version and t.table_name='BE_WK_ENT' and t.before<>t.after and  t.gid=?", gid);
        if(count!=null&&count>0) {
        	//修改过
        	throw new OptimusException("修改过审核内容数据，不能执行退回操作！");
        }
        //执行退回待核准数据
        StringBuffer sql = new StringBuffer();
        SysmgrUser currentUser = ApproveUserUtil.getLoginUser();
        if(isHz) {
            sql.append("update be_wk_requisition set approve_user_id='', approve_no= '',approve_name = '',approve_notion = '',state = '2',timestamp=? where state = '15' and cur_step = '12' and gid = ?");
            //向be_wk_reqprocess插入数据
            String insertSql = "insert into be_wk_reqprocess(reqprocess_id,requisition_id,process_date,user_id,user_name,"
                    + "process_step,pro_end_date,state,reg_org,gid,timestamp,process_notion) values(sys_guid(),?,sysdate,?,?,?,sysdate,?,?,?,sysdate,?)";
            DaoUtil.getInstance().execute(insertSql,gid,currentUser.getUserId(),currentUser.getUserName(),"12","15",currentUser.getOrgCodeFk(),gid,"从‘我的任务’列表退回至核准分配/领取列表。");
        
        }else {
            sql.append("update be_wk_requisition set censor_user_id='', censor_no= '',censor_name = '',censor_notion = '',state = '2',timestamp=? where state = '15' and cur_step = '10' and gid = ?");
            //向be_wk_reqprocess插入数据
            String insertSql = "insert into be_wk_reqprocess(reqprocess_id,requisition_id,process_date,user_id,user_name,"
                    + "process_step,pro_end_date,state,reg_org,gid,timestamp,process_notion) values(sys_guid(),?,sysdate,?,?,?,sysdate,?,?,?,sysdate,?)";
            DaoUtil.getInstance().execute(insertSql,gid,currentUser.getUserId(),currentUser.getUserName(),"10","15",currentUser.getOrgCodeFk(),gid,"从‘我的任务’列表退回至辅助审查分配/领取列表。");
        }
        List<Object> paramlist = new ArrayList<Object>();
        paramlist.add(DateUtil.getCurrentTime());
        paramlist.add(gid);
        DaoUtil.getInstance().execute(sql.toString(), paramlist);
	}
	
}
