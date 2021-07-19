package com.gwssi.system.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.ebaic.apply.setup.service.SetupMemberService;
import com.gwssi.ebaic.apply.util.SetMemberPositionUtil;
import com.gwssi.ebaic.common.util.SubmitUtil;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.ParamUtil;
import com.gwssi.system.service.DmjService;


/**
 * 自定义查询代码集
 * 
 * @author chy
 * 
 */
@Controller
@RequestMapping("/dmj")
@Deprecated
public class DmjController {

	@Autowired
	private DmjService dmjService;
	@Autowired
	SetupMemberService setupMemberService;
	
	/**
	 * 新的json格式
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map createDicData(List<Map> list) {
		Map json = new HashMap();
		Map data = new HashMap();
		List<Map> result = new ArrayList<Map>();
		data.put("data", list);
		result.add(data);
		json.put("data", result);
		return json;
	}
	
	

	/**
	 * 
	 * @desc 前台把需要显示的“区代码”传过来，就可以按配置的代码显示相应的下拉框
	 * domDistrict格式:   110108,110107,110106     为空表示全部加载
	 * @return Map
	 * @throws OptimusException
	 */
	@RequestMapping("/getDomDistrict")
	@ResponseBody
	@SuppressWarnings({ "rawtypes" })
	public Map getDomDistrict(String domDistrict) throws OptimusException {
		List<Map> list = dmjService.getDomDistrict(domDistrict);
		Map json = createDicData(list);
		return json;
	}
	
	/**
	 * 查询省市自治区
	 * 
	 * @author liuhailong
	 * @return Map
	 * @throws OptimusException
	 */
	@RequestMapping("/queryDomProvinces")
	@ResponseBody
	@SuppressWarnings({ "rawtypes" })
	public Map queryDomProvinces() throws OptimusException {
		List<Map> list = dmjService.queryDomProvince();
		Map json = createDicData(list);
		return json;
	}
	
	/**
	 * 查询地级市
	 * 
	 * @author liuhailong
	 * @return Map
	 * @throws OptimusException
	 */
	@RequestMapping("/queryDomCities")
	@ResponseBody
	@SuppressWarnings({ "rawtypes"})
	public Map queryDomCities(String provinceZipCode) throws OptimusException {
		List<Map> list = dmjService.queryDomCities(provinceZipCode);
		Map json = createDicData(list);
		return json;
	}
	
	
	/**
	 * 查询住所产权类型
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/getDomOwnType")
	@ResponseBody
	@SuppressWarnings({ "rawtypes" })
	public Map getDomOwnType() throws OptimusException {
		List<Map> list = dmjService.getDomOwnType();
		Map json = createDicData(list);
		return json;
	}
	
	/**
	 * 查询内资类型的国别
	 * @param invType
	 * @return
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/queryContry")
	@ResponseBody
	public Map queryContry(String invType) throws OptimusException {
		List<Map> list = dmjService.queryContry(invType);
		Map map = createDicData(list);
		return map;
	}
	
	/**
	 * 查询证件类型
	 * @param invType
	 * @return
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/queryCerType")
	@ResponseBody
	public Map queryCerType(String invType,String invCount) throws OptimusException {
		List<Map> list = dmjService.queryCerType(invType,invCount);
		Map map = createDicData(list);
		return map;
	}
	
	/**
	 * 查询内资类型的非自然人类型
	 * 
	 * @author liuhailong
	 * @return Map
	 * @throws OptimusException
	 */
	@RequestMapping("/getIvtTypes")
	@ResponseBody
	@SuppressWarnings({ "rawtypes" })
	public Map queryInvestorInnerType() throws OptimusException {
		List<Map> list = dmjService.queryInvestorInnerType();
		Map json = createDicData(list);
		return json;
	}
	
	/**
	 * 查询外资类型的非自然人类型
	 * 
	 * @author dongweina
	 * @return Map
	 * @throws OptimusException
	 */
	@RequestMapping("/getForeignTypes")
	@ResponseBody
	@SuppressWarnings({ "rawtypes" })
	public Map queryInvestorForeignType() throws OptimusException {
		List<Map> list = dmjService.queryInvestorForeignType();
		Map json = createDicData(list);
		return json;
	}
	
	/**
	 *
	 * 查询企业自然投资人。 用于企业联系人,财务负责人添加
	 * 
	 * @param gid
	 * @return
	 * @throws OptimusException
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/queryInvestorsForEntId")
	@ResponseBody
	public Map queryInvestorsForEntId(String gid,String except) throws OptimusException,
			UnsupportedEncodingException {
		List<Map> list = dmjService.queryInvestorsForEntId(gid,except);
		Map map = new HashMap();
		if (list.size() > 0) {
			map = createDicData(list);
		} else {
			List<Map> listmatchNull = new ArrayList<Map>();
			map = createDicData(listmatchNull);
//			Map empty = new HashMap();
//			listmatchNull.add(empty);
//			temp.put("invstors", listmatchNull);
//			map.put("data", temp);
		}
		return map;
	}

	/**
	 * 主要人员页面查询股东姓名
	 * @param request
	 * @param response
	 * @throws OptimusException 
	 */
	@RequestMapping("/queryInvesterNames")
	@SuppressWarnings("rawtypes")
	@ResponseBody
	public Map queryInvesterNames(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String gid = ParamUtil.get("gid");
		String positionType = ParamUtil.get("mbrFlag");
		List<Map> list = dmjService.queryInvesterNames(gid,positionType);
		Map json = createDicData(list);
		return json;
	}
	
	
	/**
	 * 查询经营范围标准用语分类维护  用于经营范围标准用语维护中分类查询
	 * yzh
	 * @return Map
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/querySortStandardMaintain")
	public Map querySortStandardMaintain()throws OptimusException{
		
		List<Map> list = dmjService.querySortStandardMaintain();
		Map json = createDicData(list);
		return json;
	}
	
	
	/**
	 * 查询法人和申请人姓名
	 * @param request
	 * @param response
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/queryPostReceiver")
	@SuppressWarnings("rawtypes")
	@ResponseBody
	public Map queryPostReceiver(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String gid = ParamUtil.get("gid");
		List<Map> list = dmjService.queryPostReceiver(gid);
		Map json = createDicData(list);
		return json;
	}
	
	
	/**
	 * 查询改企业中可担任法定代表人的人选
	 * yzh
	 * @return Map
	 * @throws OptimusException
	 */
	@RequestMapping("/queryLegalDelegated")
	@SuppressWarnings("rawtypes")
	@ResponseBody
	public Map queryLegalDelegated(OptimusRequest request)throws OptimusException{
		String gid = ParamUtil.get("gid");
		String isBoardValue = ParamUtil.get("isBoardValue");
		List<Map> list = dmjService.queryLegalDelegated(gid,isBoardValue);
		Map json = createDicData(list);
		return json;
	}
	
	
//	public Map beiJingCompanyDataReturn(OptimusRequest request){
//		
//	}
//	
	
	@RequestMapping("/deleteLicense")
	@ResponseBody
	public void deleteLicense(OptimusResponse resp,OptimusRequest req){
		String delId = ParamUtil.get("delId");
		try{
			dmjService.deleteLicense(delId);
		}catch(Exception e){
			throw new RuntimeException("删除部门信息失败!");
		}
	}
	
	
}

