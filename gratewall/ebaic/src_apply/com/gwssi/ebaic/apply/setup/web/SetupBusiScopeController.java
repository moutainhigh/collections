package com.gwssi.ebaic.apply.setup.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.ebaic.apply.setup.service.SetupBusiScopeService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.rodimus.util.ParamUtil;
import com.gwssi.torch.util.JSON;

/**
 * 经营范围。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Controller
@RequestMapping("/apply/setup/scope")
public class SetupBusiScopeController {

	@Autowired
	SetupBusiScopeService setupBusiScopeService;
	
	/**
	 * @param requeest
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/tags")
	public void tags(OptimusRequest requeest,OptimusResponse response) throws OptimusException{
		String gid = ParamUtil.get("gid");
		Map<String,Object> ret = setupBusiScopeService.getTags(gid);
		response.addResponseBody(JSON.toJSON(ret));
	}
	
	/**
	 * @param requeest
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/load")
	public void load(OptimusRequest requeest,OptimusResponse response) throws OptimusException{
		String gid = ParamUtil.get("gid");
		Map<String,Object> ret = setupBusiScopeService.load(gid);
		response.addResponseBody(JSON.toJSON(ret));
	}
	
	/**
	 * @param requeest
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/save")
	public void save(OptimusRequest requeest,OptimusResponse response) throws OptimusException{
		String gid = ParamUtil.get("gid");
		String scopeJson = ParamUtil.get("scopeJson");
		String businessScope = ParamUtil.get("businessScope",false);
		
		setupBusiScopeService.save(gid,scopeJson,businessScope);
		response.addAttr("result", "success");
	}
	
	/**
	 * @param requeest
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/compareScope")
	public void compareScope(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String customScopeVal = (String)request.getAttr("customScopeVal");
		List<Map<String, Object>> list = setupBusiScopeService.compareScope(customScopeVal);
		response.addAttr("result", list);
	}
	
	@RequestMapping("/mainScope")
	@ResponseBody
	public Map<String, Object> mainScope(OptimusRequest request,OptimusResponse response) throws OptimusException{
		List<Map<String, Object>> list = setupBusiScopeService.getMainScope();
		Map<String,Object> map = new HashMap<String,Object>();
		if (list.size() > 0) {
			map = createDicData(list);
		} else {
			List<Map<String,Object>> listmatchNull = new ArrayList<Map<String,Object>>();
			map = createDicData(listmatchNull);
		}
		return map;
	}
	
	private Map<String,Object> createDicData(List<Map<String,Object>> list) {
		Map<String,Object> json = new HashMap<String,Object>();
		Map<String,Object> data = new HashMap<String,Object>();
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		data.put("data", list);
		result.add(data);
		json.put("data", result);
		return json;
	}
}
