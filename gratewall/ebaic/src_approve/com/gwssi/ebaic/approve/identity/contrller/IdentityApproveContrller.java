package com.gwssi.ebaic.approve.identity.contrller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.gwssi.ebaic.approve.identity.service.IdentityApproveService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.rodimus.util.ParamUtil;

/**
 * 身份认证审批。
 * 
 * @author liuhailong
 */
@Controller
@RequestMapping("/approve/identity/censor")
public class IdentityApproveContrller {
	
	@Autowired
	private IdentityApproveService identityApproveService;
	
	/**
	 * <h3>加载审批图片列表</h3>
	 * 
	 * 输入：gid
	 * 
	 * 返回：[{fileId:'xxxx',thumbFileId:'xxxx',bizId:'sssss'},{fileId:'xxxx',thumbFileId:'xxxx',bizId:'sssss'}]
	 * 
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/load")
	public void load(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String gid = ParamUtil.get("gid");
		List<Map<String, Object>> list = identityApproveService.load(gid);
		response.addResponseBody(JSON.toJSONString(list));
	}
	
	/**
	 * 输入：bizId
	 * 返回：{dataType:'mbr',data:{}, hisMsg:''}
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/item")
	public void item(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String bizId = ParamUtil.get("bizId");
		String type = ParamUtil.get("type");
		Map<String, Object> item = identityApproveService.item(bizId,type);
		response.addResponseBody(JSON.toJSONString(item));
	}
	
	/**
	 * 输入：  bizId 、 state 、 msg
	 * 返回：result（如果不是success，需要提示给用户）
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/save")
	public void save(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String bizId = ParamUtil.get("bizId");
		String state = ParamUtil.get("state");
		String msg   = ParamUtil.get("msg",false);
		Map<String,Object> result = identityApproveService.save(bizId,state,msg);
		response.addResponseBody(JSON.toJSONString(result));
	}
	/**
	 * 加载身份核查用户信息
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/loadCheckUser")
    public void loadCheckUser(OptimusRequest request,OptimusResponse response) throws OptimusException{
        String gid = ParamUtil.get("gid");
        Map<String, Object> resultMap = identityApproveService.loadCheckUser(gid);
        response.addResponseBody(JSON.toJSONString(resultMap));
    }
	/**
     * 加载所选用户的审批图片列表
     * @param request
     * @param response
     * @throws OptimusException
     */
    @RequestMapping("/loadSelectUserPic")
    public void loadSelectUserPic(OptimusRequest request,OptimusResponse response) throws OptimusException{
        String userName = ParamUtil.get("userName");
        String cerNo = ParamUtil.get("cerNo");
        List<Map<String, Object>> list = identityApproveService.loadSelectUserPic(userName,cerNo);
        response.addResponseBody(JSON.toJSONString(list));
    }
    /**
     * 现场身份认证加载所选用户的审批图片列表
     * @param request
     * @param response
     * @throws OptimusException
     */
    @RequestMapping("/loadLiveUserPic")
    public void loadLiveUserPic(OptimusRequest request,OptimusResponse response) throws OptimusException{
        String userName = ParamUtil.get("userName");
        String cerNo = ParamUtil.get("cerNo");
        List<Map<String, Object>> list = identityApproveService.loadLiveUserPic(userName,cerNo);
        response.addResponseBody(JSON.toJSONString(list));
    }
}
