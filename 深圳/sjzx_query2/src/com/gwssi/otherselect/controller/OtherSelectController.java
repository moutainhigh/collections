package com.gwssi.otherselect.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.optimus.core.common.ThreadLocalManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.otherselect.service.OtherSelectService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.gwssi.application.home.controller.HomeController;
import com.gwssi.comselect.model.EntSelectQueryBo;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.otherselect.service.OtherSelectService;
import com.gwssi.trs.JsonUtil;

@Controller
@RequestMapping("/otherselect")
public class OtherSelectController{
	private static Logger logger = Logger.getLogger(OtherSelectController.class);	
	
	@Resource
	private OtherSelectService otherSelectService;
	
	/**
	 * 失信被执行人查询列表
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/SXQueryList")
	public void SXQueryList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		/*Map<String, String> form = req.getForm("SXQueryListPanel");
		String flag = req.getParameter("flag");
		if(flag !=null){
			String count= otherSelectService.getSXListCount(form);
			resp.addAttr("count", count);
		}else {
			List<Map> lstParams= otherSelectService.getSXList(form,req.getHttpRequest());
			//System.out.println(lstParams.toString());
				resp.addGrid("SXQueryListGrid",lstParams);
		}*/
		
		
		String str = req.getParameter("postData");
		Map<String, String> jsonstr =req.getForm("SXQueryListPanel");
		String flag = req.getParameter("flag");
		Map localMap1 = (Map) JSON.parse(str);
		List localList = (List)localMap1.get("data");
		Map mapDataPage =   null;
		int pageIndex = 1;
		int pageSize = 10;
		if(localList.size()>3) {
			mapDataPage = (Map) localList.get(3);
			pageIndex  =  (Integer) mapDataPage.get("data");
		}
		if(localList.size()>3) {
			mapDataPage = (Map) localList.get(3);
			pageIndex  =  (Integer) mapDataPage.get("data");
		}
		 
		if(flag !=null){
			String count= otherSelectService.getSXListCount(jsonstr);
			resp.addAttr("count", count);
		}else {
			String count= otherSelectService.getSXListCount(jsonstr);
			
			Map pageMap = new HashMap();
			//pageMap.put("page",1);
			pageMap.put("page",pageIndex);
			pageMap.put("pagerows",pageSize);
			//pageMap.put("pagerows",10);
			pageMap.put("totalrows",Integer.valueOf(count));
			
			
			List<Map> lstParams= otherSelectService.getSXList(jsonstr,req.getHttpRequest(),pageIndex,pageSize);
			resp.setPaginationParams(pageMap);		
			resp.addGrid("gridpanel",lstParams);
		}
	}
	
	/**
	 * 失信被执行人查询详情信息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/getQueryById")
	public void getQueryById(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String sExtSequence = req.getParameter("sExtSequence");
		if(sExtSequence==null){
			throw new OptimusException("获取参数失败");
		}
		resp.addForm("queryInfoFormPannel", otherSelectService.getSXQueryById(sExtSequence));
	}
	
	/**
	 * 黑牌企业查询详情信息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("hpqyQuery")
	public void hpqyQuery(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String str = req.getParameter("postData");
		Map<String, String> jsonstr =req.getForm("formpanel");
		String flag = req.getParameter("flag");
		Map localMap1 = (Map) JSON.parse(str);
		List localList = (List)localMap1.get("data");
		Map mapDataPage =   null;
		int pageIndex = 1;
		int pageSize = 10;
		if(localList.size()>3) {
			mapDataPage = (Map) localList.get(3);
			pageIndex  =  (Integer) mapDataPage.get("data");
		}
		 
		if(flag !=null){
			String count= otherSelectService.queryHPListCount(jsonstr);
			resp.addAttr("count", count);
		}else {
			String count= otherSelectService.queryHPListCount(jsonstr);
			
			Map pageMap = new HashMap();
			//pageMap.put("page",1);
			pageMap.put("page",pageIndex);
			pageMap.put("pagerows",pageSize);
			//pageMap.put("pagerows",10);
			pageMap.put("totalrows",Integer.valueOf(count));
			
			
			List<Map> lstParams= otherSelectService.queryHPList(jsonstr,req.getHttpRequest(),pageIndex,pageSize);
			resp.setPaginationParams(pageMap);		
			resp.addGrid("gridpanel",lstParams);
		}
	}
	
	/**
	 * 一人责任有限公司查询 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/YRQueryList")
	public void YRQueryList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		 Map<String, String> form = req.getForm("YRQueryListPanel");
		String flag = req.getParameter("flag");
		String str = req.getParameter("postData");
		Map localMap1 = (Map) JSON.parse(str);
		List localList = (List)localMap1.get("data");
		Map mapDataPage =   null;
		int pageIndex = 1;
		int pageSize = 10;
		if(localList.size()>3) {
			mapDataPage = (Map) localList.get(3);
			pageIndex  =  (Integer) mapDataPage.get("data");
		}
		
		
		if(flag !=null){
			String count= otherSelectService.getYRListCount(form);
			resp.addAttr("count", count);
		}else {
			String count= otherSelectService.getYRListCount(form);
			Map pageMap = new HashMap();
			//pageMap.put("page",1);
			pageMap.put("page",pageIndex);
			pageMap.put("pagerows",pageSize);
			//pageMap.put("pagerows",10);
			pageMap.put("totalrows",Integer.valueOf(count));
			
			
			List<Map> lstParams= otherSelectService.getYRList(form,req.getHttpRequest(),pageIndex,pageSize);
			//System.out.println(lstParams.toString());
			
			resp.setPaginationParams(pageMap);		
			resp.addGrid("gridpanel",lstParams);
			
			resp.addGrid("YRQueryListGrid",lstParams);
		}
	}
	
	
	/**
	 * 餐饮许可证信息查询列表
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/CYQueryList")
	public void CYQueryList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		Map<String, String> form = req.getForm("CYQueryListPanel");
		List<Map> lstParams= otherSelectService.getCYFWList(form,req.getHttpRequest());
		resp.addGrid("CYQueryListGrid",lstParams);
	}
	
	
	/**
	 * 食品流通许可证信息查询列表
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/SPQueryList")
	public void SPQueryList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		Map<String, String> form = req.getForm("SPQueryListPanel");
		List<Map> lstParams= otherSelectService.getSPFWList(form,req.getHttpRequest());
		resp.addGrid("SPQueryListGrid",lstParams);
	}
	
	/**
	 * 综合查询日志查询列表
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/LogQueryList")
	public void LogQueryList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		Map<String, String> form = req.getForm("LogQueryListPanel");
		List<Map> lstParams= otherSelectService.getLogList(form,req.getHttpRequest());
		resp.addGrid("LogQueryListGrid",lstParams);
	}
	
	
	/**
	 * 餐饮许可证信息查询详情信息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/getCYQueryById")
	public void getCYQueryById(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String id = req.getParameter("id");
		if(id==null){
			throw new OptimusException("获取参数失败");
		}
		resp.addForm("queryCYDetialPanel", otherSelectService.getCYQueryById(id));
	}
	
	/**
	 * 食品流通许可证信息查询详情信息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/getSPQueryById")
	public void getSPQueryById(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String id = req.getParameter("id");
		if(id==null){
			throw new OptimusException("获取参数失败");
		}
		resp.addForm("querySPDetialPanel", otherSelectService.getSPQueryById(id));
	}
	
	
	/**
	 * 综合查询日志详情
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/getLogQueryById")
	public void getLogQueryById(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String id = req.getParameter("id");
		if(id==null){
			throw new OptimusException("获取参数失败");
		}
		resp.addForm("queryLogDetialPanel", otherSelectService.getLogQueryById(id));
	}
	
	/**
	 * 综合查询系统管理角色管理列表
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/RoleQueryList")
	public void RoleQueryList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		Map<String, String> form = req.getForm("RoleQueryListPanel");
		List<Map> lstParams= otherSelectService.getRoleList(form);
		resp.addGrid("RoleQueryListGrid",lstParams);
	}
	
	/**
	 * 综合查询系统角色下人员
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/RolePersonList")
	public void RolePersonList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		Map<String, String> form = req.getForm("RolePersonListPanel");
		String roleCode = req.getParameter("roleCode");
		if(roleCode == null){
			roleCode = "";
		}
		List<Map> lstParams= otherSelectService.RolePersonList(roleCode,req.getHttpRequest(),form);
		resp.addGrid("RolePersonListGrid",lstParams);
	}
	
	
	/**
	 * 综合查询系统管理角色管理_删除人员角色信息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/deleteRolePerson")
	public void deleteRolePerson(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String userId = req.getParameter("userId");
		if(userId == null){
			userId = "";
		}
		String roleCode = req.getParameter("roleCode");
		if(roleCode == null){
			roleCode = "";
		}
		String flag = otherSelectService.deleteRolePerson(userId,roleCode);
		if("1".equals(flag)){
			resp.addResponseBody(flag);
		}
	}
	
	/**
	 * 人员角色管理 模糊查询人员账号
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/QueryUserInfo")
	public void queryUserInfo(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		//String userId = req.getParameter("userId");
		String userInfo = req.getParameter("userInfo");
		List<Map> userInfoArray = otherSelectService.queryUserInfo(userInfo);
		String string = JSONArray.toJSONString(userInfoArray);
		resp.addResponseBody(string);
	}
	
	
	/**
	 * 保存人员角色信息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/SaveUserInfo")
	public void SaveUserInfo(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		//String userId = req.getParameter("userId");
		String userList = req.getParameter("userList");
		String roleCode = req.getParameter("roleCode");
		
		if(roleCode == null){
			roleCode = "";
		}
		
		try {
			otherSelectService.saveUserInfo(userList, roleCode);
		} catch (Exception e) {
			resp.addResponseBody("0");
		}
		resp.addResponseBody("1");
	}
	
	
	/**
	 * 保存人员信息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/SaveUser")
	public void SaveUser(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		//String userId = req.getParameter("userId");
		String userList = req.getParameter("userList");
		if(userList == null || "".equals(userList)){
			resp.addResponseBody("2");
			return;
		}
		//String roleCode = req.getParameter("roleCode");
		
/*		if(roleCode == null){
 * 
			roleCode = "";
		}
*/		
		/*try {
			otherSelectService.SaveUser(userList);
		} catch (Exception e) {
			resp.addResponseBody("0");
		}
		resp.addResponseBody("1");*/
		resp.addResponseBody(otherSelectService.SaveUser(userList));
	}
	
	
	
	/**
	 * 综合查询系统角色下功能
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/RolePowerList")
	public void RolePowerList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String roleCode = req.getParameter("roleCode");
		List<Map> lstParams= otherSelectService.RolePowerList(roleCode,req.getHttpRequest());
		resp.addGrid("RolePowerListGrid",lstParams);
	}
	
	
	/**
	 * 综合查询系统管理角色管理_删除角色功能信息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/deleteRolePower")
	public void deleteRolePower(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String functionCode = req.getParameter("functionCode");
		if(functionCode == null){
			functionCode = "";
		}
		String roleCode = req.getParameter("roleCode");
		if(roleCode == null){
			roleCode = "";
		}
		String flag = otherSelectService.deleteRolePower(functionCode,roleCode);
		if("1".equals(flag)){
			resp.addResponseBody(flag);
		}
	}
	
	
	/**
	 * 查询当前角色所没有具备的功能代码
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/QueryFuncRole")
	public void QueryFuncRole(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String roleCode = req.getParameter("roleCode");
		if(roleCode == null){
			roleCode = "";
		}
		List<Map> userInfoArray = otherSelectService.QueryFuncRole(roleCode);
		String string = JSONArray.toJSONString(userInfoArray);
		resp.addResponseBody(string);
	}
	
	/**
	 * 保存当前角色新增的功能代码
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/SaveFuncRole")
	public void SaveFuncRole(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String roleCode = req.getParameter("roleCode");
		if(roleCode == null){
			roleCode = "";
		}
		String functionCode = req.getParameter("functionCode");
		if(functionCode == null){
			functionCode = "";
		}
		String flag = otherSelectService.SaveFuncRole(roleCode,functionCode);
		if("1".equals(flag)){
			resp.addResponseBody(flag);
		}
	}
	
	/**
	 * 保存角色
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/AddRole")
	public void AddRole(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String RoleCode = req.getParameter("RoleCode");
		String RoleName = req.getParameter("RoleName");
		String RoleDesc = req.getParameter("RoleDesc");
		if(RoleCode == null){
			RoleCode = "";
		}
		if(RoleName == null){
			RoleName = "";
		}
		if(RoleDesc == null){
			RoleDesc = "";
		}
		String flag = otherSelectService.AddRole(RoleCode,RoleName,RoleDesc);
		if("1".equals(flag)){
			resp.addResponseBody(flag);
		}
	}
	
	/**
	 * 联想查询部门信息
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/QueryDep")
	public void QueryDep(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String name = req.getParameter("name");
		if(name == null){
			name = "";
		}
		List<Map> userInfoArray = otherSelectService.QueryDep(name);
		String string = JSONArray.toJSONString(userInfoArray);
		resp.addResponseBody(string);
	}
	
	/**
	 * 联想查询角色信息
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/QueryRole")
	public void QueryRole(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		List<Map> userInfoArray = otherSelectService.QueryRole();
		String string = JSONArray.toJSONString(userInfoArray);
		resp.addResponseBody(string);
	}
	
	/**
	 * 综合查询系统管理删除角色(置为不可用状态)
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/deleteRole")
	public void deleteRole(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String roleCode = req.getParameter("roleCode");
		if(roleCode == null){
			roleCode = "";
		}
		resp.addResponseBody(otherSelectService.deleteRole(roleCode)); 
	}
	
	/**
	 * 联想查询部门信息
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/queryRoleInfo")
	public void queryRoleInfo(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String roleCode = req.getParameter("roleCode");
		if(roleCode == null){
			roleCode = "";
		}
		List<Map> roleInfo = otherSelectService.queryRoleInfo(roleCode);
		String string = JSONArray.toJSONString(roleInfo);
		resp.addResponseBody(string);
	}
	
	
	/**
	 * 保存当前角色新增的功能代码
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/SaveRole")
	public void SaveRole(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String roleName = req.getParameter("roleName");
		if(roleName == null){
			roleName = "";
		}
		String newRoleCode = req.getParameter("newRoleCode");
		if(newRoleCode == null){
			newRoleCode = "";
		}
		String roleDesc = req.getParameter("roleDesc");
		if(roleDesc == null){
			roleDesc = "";
		}
		String oldRoleCode = req.getParameter("oldRoleCode");
		if(oldRoleCode == null){//如果旧角色代码为空 则为新添加的角色 否则为修改角色信息
			String flag = otherSelectService.AddRole(roleName,newRoleCode,roleDesc);
			resp.addResponseBody(flag);
		}else{
			String flag = otherSelectService.EditRole(oldRoleCode,roleName,newRoleCode,roleDesc);
			resp.addResponseBody(flag);
		}
	}
	
	/**
	 * 综合查询系统 功能查询
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/PowerList")
	public void PowerList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		Map<String, String> form = req.getForm("PowerListPanel");
		String functionCode = req.getParameter("functionCode");
		if(functionCode == null){
			functionCode = "";
		}
		List<Map> lstParams= otherSelectService.PowerList(functionCode,form);
		resp.addGrid("PowerListGrid",lstParams);
	}
	
	
	/**
	 * 综合查询系统管理禁用功能(置为不可用状态)
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/banPower")
	public void banPower(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String functionCode = req.getParameter("functionCode");
		String flag = req.getParameter("flag");
		if(functionCode == null){
			return;
		}
		resp.addResponseBody(otherSelectService.banPower(functionCode,flag)); 
	}
	
	/**
	 * 年报统计
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getNbStatisticsInfo")
	public void getNbStatisticsInfo(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String code = req.getParameter("code");
		String time = req.getParameter("time");
		StringBuffer bf = new StringBuffer();
		if(!isEmpty(time)){
			String [] temp = time.split("-");
			for (int i = 0; i < temp.length; i++) {
				bf.append(temp[i]);
			}
			logger.info("得到传递的时间是： "  + bf.toString());
		}
		
		List<Map> nbStatisticsInfo = otherSelectService.getNbStatisticsInfo(code,bf.toString());
		resp.addResponseBody(JSONArray.toJSONString(nbStatisticsInfo));
		//resp.addGrid("nbStatisticsGrid",nbStatisticsInfo);
	}
	
	
	
	
	/**
	 * 年报统计
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getZhfxInfo")
	public void getZhfxInfo(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String code = req.getParameter("code");
		List<Map> zhfxInfo = otherSelectService.getZhfxInfo(code);
		resp.addResponseBody(JSONArray.toJSONString(zhfxInfo));
	}
	
	
	public static boolean isEmpty(String str) {
		return null == str || str.length() == 0 || "".equals(str)
				|| str.matches("\\s*");
	}
}


