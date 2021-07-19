//package com.gwssi.ebaic.approve.censor.service;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.stereotype.Service;
//
//import com.gwssi.ebaic.approve.util.ApproveUserUtil;
//import com.gwssi.ebaic.approve.util.ProcessUtil;
//import com.gwssi.ebaic.domain.SysmgrIdentityBO;
//import com.gwssi.ebaic.domain.SysmgrUser;
//import com.gwssi.ebaic.domain.TPtYhBO;
//import com.gwssi.optimus.core.exception.OptimusException;
//import com.gwssi.rodimus.dao.DaoUtil;
//import com.gwssi.rodimus.util.DateUtil;
//import com.gwssi.rodimus.util.StringUtil;
//
///**
// * 辅助审查-身份认证
// * @author xupeng
// *
// */
//@Service("approveIdentityService")
//public class ApproveIdentityService {
//	
//	/**
//	 * 根据identityId获取附件
//	 * @param identityId
//	 * @return
//	 * @throws OptimusException
//	 */
//	public List<Map<String,Object>> getIdentityUpfile(String identityId) throws OptimusException{
//		//1、验证数据
//		if(StringUtil.isBlank(identityId)){
//			throw new OptimusException("参数不能为空！");
//		}
//		//2、查询上传附件
//		String sql = "select * from sysmgr_identity_picture pic where pic.identity_id = ? order by pic.identity_id";
//		List<Map<String,Object>> resultList = DaoUtil.getInstance().queryForList(sql, identityId);
//		
//		if(resultList==null || resultList.isEmpty()){
//			throw new OptimusException("没有附件或者获取附件失败！");
//		}
//		
//		return resultList;
//	}
//	
//	
//	/**
//	 * 执行辅助审查-身份认证操作
//	 * @param identityId
//	 * @param appUserId
//	 * @param approveMsg
//	 * @param flag
//	 * @throws OptimusException
//	 */
//	public void doIdentity(String identityId,String appUserId,String approveMsg,String checkSate,String gid) throws OptimusException{
//		
//		//1、验证数据
//		if(StringUtil.isBlank(identityId) || StringUtil.isBlank(checkSate) || StringUtil.isBlank(appUserId)){
//			throw new OptimusException("数据异常！");
//		}
//		//目前只有1-认证通过 2-认证不通过两种状态
//		if(!("1".equals(checkSate)||"2".equals(checkSate))){
//			throw new OptimusException("数据异常！");
//		}
//		
//		//2、获取当前机关、用户
//		SysmgrUser user = ApproveUserUtil.getLoginUser();
//		if(user==null){
//			throw new OptimusException("获取当前用户信息失败！");
//		}
////		String approveUserId = ApproveUserUtil.getCurrentUserId();
////		String regOrg = ApproveUserUtil.getCurrentRegOrg();
////		if(StringUtil.isBlank(approveUserId) || StringUtil.isBlank(regOrg)){
////			throw new OptimusException("登录超时，请重新登录！");
//		//3、获取并判断bo
//		SysmgrIdentityBO sysmgrIdentityBO = null;
//		TPtYhBO tPtYhBO = null;
//		sysmgrIdentityBO = DaoUtil.getInstance().get(SysmgrIdentityBO.class, identityId);
//		if(sysmgrIdentityBO==null){
//			throw new OptimusException("获取身份认证信息失败！");
//		}
//		tPtYhBO = DaoUtil.getInstance().get(TPtYhBO.class, appUserId);
//		if(tPtYhBO==null){
//			throw new OptimusException("获取账号信息失败！");
//		}
//		
//		//4、根据flag(1-通过 2-审批未通过)更新sysmgr_identity表和t_pt_yh表
//		sysmgrIdentityBO.setApproveUserId(user.getUserId());
//		sysmgrIdentityBO.setApproveUserName(user.getUserName());
//		Calendar cal = DateUtil.getCurrentTime();
//		sysmgrIdentityBO.setApproveDate(cal);
//		sysmgrIdentityBO.setTimestamp(cal);
//		sysmgrIdentityBO.setApproveMsg(approveMsg);
//		sysmgrIdentityBO.setRegOrg(user.getOrgCodeFk());
//		sysmgrIdentityBO.setFlag(checkSate);
//		
//		tPtYhBO.setIdentityCheckState(checkSate);
//		
//		DaoUtil.getInstance().update(sysmgrIdentityBO);
//		DaoUtil.getInstance().update(tPtYhBO);
//		
//		//5、插入记录到process表
//		ProcessUtil.generatorBeWkReqprocess(gid);
//		
//	}
//	
//	
//	/**
//	 * 针对单张图片进行审批操作
//	 * @param pictureId
//	 * @param approveMsg
//	 */
//	public void doIdentityToPic(String pictureId,String approveMsg,String gid)throws OptimusException{
//		//1、验证参数
//		if(StringUtil.isBlank(pictureId)){
//			throw new OptimusException("参数传递错误！");
//		}
//		if(StringUtil.isNotBlank(gid)){
//			throw new OptimusException("参数传递错误！");
//		}
//		
//		//2、更新
//		String sql = "update sysmgr_identity_picture set approve_msg = ?,timestamp = ? where picture_id = ?";
//		List<Object> params = new ArrayList<Object>();
//		params.add(approveMsg);
//		params.add(DateUtil.getCurrentTime());
//		DaoUtil.getInstance().execute(sql, params);
//		
//		//3、生成记录到process表
//		ProcessUtil.generatorBeWkReqprocess(gid);
//	}
//
//}
