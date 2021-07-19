package com.gwssi.ebaic.apply.setup.web;

import java.util.Map;	

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.gwssi.ebaic.apply.setup.service.SetupMemberService;
import com.gwssi.ebaic.apply.util.SetMemberPositionUtil;
import com.gwssi.ebaic.common.util.SubmitUtil;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.ParamUtil;

/**
 * 主要人员  保存、下一步。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Controller
@RequestMapping("/apply/setup/member")
public class SetupMemberController {
	@Autowired
	SetupMemberService setupMemberService;
	
	/**
	 * 主要人员页签  保存和下一步 执行规则 判断是否需要授权
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/runRule")
	public void runRule(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String gid = ParamUtil.get("gid");
		String entId = ParamUtil.get("entId");
		//String entMob = ParamUtil.get("entMob");
		String entMob = "";
		
		String isBoard = ParamUtil.get("isBoardValue");
		String isSuped = ParamUtil.get("isSupedValue");
		//String isNext = ParamUtil.get("isNext");
		//执行规则
		
		
		//保存时 设置主要人员表法定代表人标记  以及法定代表人表中更新数据
		setupMemberService.runRule(gid,isBoard,isSuped,entId,entMob);
		
		//保存时  更新be_wk_requisition时间戳
		SubmitUtil.updateTimeStamp("be_wk_requisition", "gid", gid);
		
		// 当前帐号是否为业务法人
		/*if(!LeRepUtil.isLeRep(gid)){
			String authType = LeRepUtil.getAuthType(gid);
			if( StringUtils.isNotBlank(authType) && "1".equals(isNext)){
				//已经点过保存按钮选择了授权类型，再点下一步时直接跳转
				response.addAttr("nextUrl", "");
			}else{
				//检查是否具有法人授权
				if(!LeRepUtil.hasAuth(gid)){
					response.addAttr("nextUrl",ConfigUtil.get("submit.toleRep"));
				}else{
					response.addAttr("nextUrl", "");
				}
			}
			
		}else{
			response.addAttr("nextUrl", "");
		}*/
		
		
	}
	
	/**
	 * 设置主要人员  法定代表人
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/setPosition")
	public void setupPosition(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String entmemberId = ParamUtil.get("entmemberId");
		String setType = ParamUtil.get("setType");
		String gid = ParamUtil.get("gid");
		setupMemberService.setupPosition(entmemberId,setType,gid);
	}
	
	
	/**
	 * 查询是否设立董事会，是否设立监事会。
	 * 
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/setting")
	public void setting(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String gid = ParamUtil.get("gid");
		Map<String,Object> setting = setupMemberService.querySetting(gid);
		response.addResponseBody(JSON.toJSONString(setting));
	}
	
	/**
	 * 设置是否设立董事会。
	 * 
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/setSettingBoard")
	public void setSettingBoard(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String gid = ParamUtil.get("gid");
		String isBoard = ParamUtil.get("isBoard");
		setupMemberService.setSettingBoard(gid,isBoard);
		response.addAttr("result", "success");
	}
	
	/**
	 * 设置是否设立监事会。
	 * 
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/setSettingSusped")
	public void setSettingSusped(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String gid = ParamUtil.get("gid");
		String isSusped = ParamUtil.get("isSusped");
		setupMemberService.setSettingSusped(gid,isSusped);
		response.addAttr("result", "success");
	}
	
	/**
	 * 删除主要人员
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/delMbr")
	public void delMbr(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String entmemberId = ParamUtil.get("entmemberId");
		String positionType = ParamUtil.get("positionType");
		String gid = ParamUtil.get("gid");
		setupMemberService.delMbr(entmemberId,positionType,gid);
	}
	/**
	 * 查询单个主要人员或者单个股东的信息
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/queryEntMemberOrInvester")
	public void queryEntMemberOrInvester(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String id = (String)request.getAttr("id");
		Map<String, Object> ret = setupMemberService.queryEntMemberOrInvester(id);
		response.addAttr("result",ret);
	}
	
	/**
	 * 查询Job数据。
	 * 
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/job")
	public void job(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String entmemberId = ParamUtil.get("entmemberId");
		String positionType = ParamUtil.get("positionType");
		Map<String, Object> ret = setupMemberService.job(entmemberId,positionType);
		response.addAttr("job",ret);
	}
	
	
	/**
	 * 法定代表人数据回显
	 * yzh
	 * @return Map
	 * @throws OptimusException
	 */
	@RequestMapping("/reDeployLegalDelegate")
	public void reDeployLegalDelegate(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String gid = ParamUtil.get("gid");
		Map<String, Object> ret = setupMemberService.reDeployLegalDelegate(gid);
		response.addAttr("data", ret);
	}
	/**
	 * 添加或编辑经理信息时，检测董事是否有兼任经理的，若有返回该董事信息。
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/isManager")
	public void isManager(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String gid = ParamUtil.get("gid");
		Map<String, Object> res = setupMemberService.queryManagered(gid);
		response.addAttr("result", res);
	}
	/**
	 * 删除经理时，初始化董事‘是否兼任经理’字段为‘0’不兼任。
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/initBoard")
	public void initBoard(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String gid = ParamUtil.get("gid");
		setupMemberService.updateIsManager(gid);
	}
	
	/**
	 * 董事里取消是否兼任经理选择时 将库中主要人员表里的经理数据删除
	 */
	@RequestMapping("/deleteDirector")
	public void deleteDirector(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String entMemberId = ParamUtil.get("entMemberId");
		setupMemberService.deleteDirector(entMemberId);
	}
	
	/**
	 * 查询该企业有无经理
	 */
	@RequestMapping("/queryDirector")
	public void queryDirector(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String gid = ParamUtil.get("gid");
		Integer directorNum = setupMemberService.queryDirector(gid);
		response.addResponseBody(directorNum.toString());
	}
	
	@RequestMapping("/storeLegalRep")
	public void storeLegalRep(OptimusResponse resp,OptimusRequest req) throws OptimusException{
		String gid = ParamUtil.get("gid");
		String entId = ParamUtil.get("entId");
		//String entMob = ParamUtil.get("entMob");
		String entMob = "";
		
		try {
			SetMemberPositionUtil.setLegal(entId, gid, entMob);
			SubmitUtil.updateTimeStamp("be_wk_requisition", "gid", gid);
		} catch (Exception e) {
			throw new EBaicException("法定代表人保存失败!");
		}
		
		//dmjService.storeLegalRep(gid);
	}
}
