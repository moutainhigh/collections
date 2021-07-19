package com.gwssi.ebaic.approve.util;

import com.gwssi.ebaic.domain.SysmgrUser;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.StringUtil;

/**
 * 审核权限。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class ApproveAuthUtil {

	  /**
	    * 判断当前环节是审查还是核准
	    * @param gid
	    * @return
	    */
	    public static boolean canApproved(String gid) {
	        if(StringUtil.isBlank(gid)){
	            throw new EBaicException("业务流水号不能为空!");
	        }
	        SysmgrUser sysmgrUser=ApproveUserUtil.getLoginUser();
			if(null == sysmgrUser){
				throw new EBaicException("登录超时，请重新登录。");
			}
	        boolean canApprove = ApproveAuthUtil.canApproved(sysmgrUser.getUserId(), gid);
	        return canApprove;
	    }
	    
	/**
	 * 是否可以直接核准。
	 * 
	 * @param userId
	 * @param gid
	 * @return
	 */
	public static boolean canApproved(String userId,String gid){
		// 当前业务状态为12，而且当前用户可以访问核准菜单，则可以执行核准操作。
		String curStep = DaoUtil.getInstance().queryForOneString("select r.cur_step from be_wk_requisition r where r.gid = ?", gid);
		if("12".equals(curStep)){
			return true;
		}
		//判断当前用户是否有直核权限，如果有返回true，以后实现。。。。
		return false;
	}
}
