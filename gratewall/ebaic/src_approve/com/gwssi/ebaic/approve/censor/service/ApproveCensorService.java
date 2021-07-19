package com.gwssi.ebaic.approve.censor.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gwssi.ebaic.approve.util.ApproveUserUtil;
import com.gwssi.ebaic.approve.util.AssignUtil;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.indentity.IdentityCardUtil;
import com.gwssi.rodimus.indentity.domain.IdentityCardBO;
import com.gwssi.rodimus.util.StringUtil;

@Service(value="censorService")
public class ApproveCensorService extends BaseService { //受理业务包含 领取、分配、退回、受理
	
	protected final static Logger logger = Logger.getLogger(ApproveCensorService.class);
	
	/**
	 * 审查环节分配操作
	 * @param censorUserId
	 * @param reqIdList
	 * @throws OptimusException
	 */
	public void saveAssign(String censorUserId , List<String> reqIdList) throws OptimusException{
		String curStep = "10";// 环节， 10-辅助审查/受理， 12-核准
        AssignUtil.saveAssign(curStep, censorUserId, reqIdList,"0");
	}
	
	/**
	 * 审查环节领取。
	 * @param requisitionId
	 * @throws OptimusException
	 */
	public void saveAdopt(List<String> reqIdList) throws OptimusException{
		String currentUserId = ApproveUserUtil.getLoginUser().getUserId();
		String curStep = "10";// 环节， 10-辅助审查/受理， 12-核准
        AssignUtil.saveAssign(curStep, currentUserId, reqIdList,"1");
	}
	
	/**
	 * 退回修改再分配-批量分配
	 * @param censorUserList
	 * @throws OptimusException
	 */
	public void saveBackAssign(List<String> censorUserList) throws OptimusException{
		String curStep = "10";
		AssignUtil.saveBackAssign(curStep, censorUserList);
	}
	
	
	/**
	 * 根据姓名和证件号码，进行身份验证并且获取证件图片url地址
	 * @param name
	 * @param cerNo
	 * @return
	 * @throws OptimusException
	 */
	public IdentityCardBO getIdentityPic(String name,String cerNo) throws OptimusException{
		if(StringUtil.isBlank(name)){
			throw new OptimusException("姓名不允许为空！");
		}
		if(StringUtil.isBlank(cerNo)){
			throw new OptimusException("证件号码不允许为空！");
		}
		IdentityCardBO ret =  IdentityCardUtil.getIdentityCardInfo(name, cerNo);
		if(ret==null || StringUtil.isBlank(ret.getPicUrl())){
			throw new OptimusException("姓名或证件号码不正确，获取公安机关证件照失败!");
		}
		return ret;
	}
	
	
	/**
	 * 根据identityId获取到上传附件的照片路径集合
	 * @param identityId
	 * @return
	 */
	public List<Map<String,Object>> getIdentityfileUrlById(String identityId) throws OptimusException{
		if(StringUtil.isBlank(identityId)){
			throw new OptimusException("参数传递错误！");
		}
		String sql = "select * from sysmgr_identity_picture p where p.identity_id = ?";
		List<Map<String, Object>> identitylist = DaoUtil.getInstance().queryForList(sql, identityId);
		if(identitylist.isEmpty()){
			throw new OptimusException("本人没有上传附件！");
		}
		
		return identitylist;
	}
	
}
