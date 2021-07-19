package com.gwssi.comselect.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.comselect.service.CaseShowService;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.trs.service.MarketEntityService;
import com.gwssi.util.CacheUtile;
import com.gwssi.util.CaseSelectCacheUtile;
import com.gwssi.util.FreemarkerUtil;
import com.gwssi.util.OtherSelectCacheUtile;

@Controller
@RequestMapping("/caseShow")
public class CaseShowController extends BaseController{
	
	@Resource
	private CaseShowService caseShowService;	
	
	/**
	 * 案件查询列表
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/caseQueryList")
	public void caseQueryList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		Map<String, String> form = req.getForm("caseQueryListPanel");
			String flag = req.getParameter("flag");
			if(flag !=null){
				String count= caseShowService.getCaseListCount(form);
				resp.addAttr("count", count);
			}else {
				List<Map> lstParams= caseShowService.getCaseList(form,req.getHttpRequest());
				//System.out.println(lstParams.toString());
					resp.addGrid("caseQueryListGrid",lstParams);
			}
	}

	/**
	 * 案件基本信息
	 * @param req
	 * @param res
	 * @return
	 * @throws OptimusException
	 */
	@ResponseBody
	@RequestMapping("caseBaseInfo")
	public Map<String,Object> caseBaseInfoQuery(OptimusRequest req, OptimusResponse res) throws OptimusException {
	
		String id=req.getParameter("id");//获取主体身份代码
		int flag = Integer.parseInt(req.getParameter("flag")==null?"999":"".equals(req.getParameter("flag").trim())?"999":req.getParameter("flag").trim());		
		String type=req.getParameter("type");//企业类型
		Map<String,String> params =new HashMap<String,String>();
		params.put("id", id);		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<Map> list =null;
		if(type.equals("case")){
			list = caseShowService.queryCaseBaseInfo(params);
		}
		/*else if(type.equals("YR")){
			list = blackEntService.queryYR(params);
		}*/
		if(list!=null && list.size()>0){
			dataMap = list.get(0);
		}
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		String returnString = "";
		if(dataMap!=null && dataMap.size()>0){
			returnMap.put("entname", dataMap.get("casename"));	
			returnMap.put("regno", dataMap.get("regno"));
			returnMap.put("enttype", dataMap.get("enttype"));
			try {
				Map<String,Object> datasMap = new HashMap<String,Object>();
				Map<String,Object> sortMap =CaseSelectCacheUtile.getLinkedHashMap(type);
				for (Map.Entry<String, Object> entry : sortMap.entrySet()) {
					sortMap.put(entry.getKey(), dataMap.get(entry.getValue()));
				}  
				datasMap.put("weaponMap", sortMap); 
				returnString = FreemarkerUtil.returnString("case.ftl", datasMap);
				sortMap.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		returnMap.put("returnString", returnString);
		return returnMap;
	}
	
	/**
	 * 违法行为及处罚信息信息
	 * @param req
	 * @param res
	 * @return
	 * @throws OptimusException
	 */
	@ResponseBody
	@RequestMapping("casePenalizeInfo")
	public Map<String,Object> casePenalizeInfoQuery(OptimusRequest req, OptimusResponse res) throws OptimusException {
	
		String id=req.getParameter("id");//获取主体身份代码
		int flag = Integer.parseInt(req.getParameter("flag")==null?"999":"".equals(req.getParameter("flag").trim())?"999":req.getParameter("flag").trim());		
		String economicproperty=req.getParameter("economicproperty");//企业类型
		Map<String,String> params =new HashMap<String,String>();
		params.put("id", id);	
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<Map> list =null;
		list = caseShowService.casePenalizeInfoQuery(params);
		System.out.println(list.toString());
		if(list!=null && list.size()>0){
			dataMap = list.get(0);
		}else{
			dataMap.put("litigantname",null);
		/*	dataMap.put("casename",null);*/
			dataMap.put("illegfact",null);
			dataMap.put("illegacttype",null);
			dataMap.put("violation",null);
			dataMap.put("sExtNodenum",null);
			
			dataMap.put("penbasis",null);
			dataMap.put("punisreason",null);
			dataMap.put("pentypecontext",null);
			dataMap.put("punishcontent",null);
			dataMap.put("caseval",null);
			dataMap.put("penam",null);
			
			dataMap.put("forfam",null);
			dataMap.put("apprcuram",null);
			dataMap.put("pendecissdate",null);
			dataMap.put("docno",null);
		}
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		String returnString = "";
		if(dataMap!=null && dataMap.size()>0){
			returnMap.put("entname", dataMap.get("entname"));	
			returnMap.put("regno", dataMap.get("regno"));
			returnMap.put("enttype", dataMap.get("enttype"));
			try {
				Map<String,Object> datasMap = new HashMap<String,Object>();
				Map<String,Object> sortMap =CaseSelectCacheUtile.getLinkedHashMap("penalize");
				for (Map.Entry<String, Object> entry : sortMap.entrySet()) {
					sortMap.put(entry.getKey(), dataMap.get(entry.getValue()));
				}  
				datasMap.put("weaponMap", sortMap); 
				returnString = FreemarkerUtil.returnString("case.ftl", datasMap);
				sortMap.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		returnMap.put("returnString", returnString);
		return returnMap;
	}
	
	/**
	 * 当事人信息
	 * @param req
	 * @param res
	 * @return
	 * @throws OptimusException
	 */
	@ResponseBody
	@RequestMapping("caseLitigantInfo")
	public Map<String,Object> caseLitigantInfoQuery(OptimusRequest req, OptimusResponse res) throws OptimusException {
	
		String id=req.getParameter("id");//获取主体身份代码
		String litiganttype=req.getParameter("litiganttype");//获取主体身份代码
		int flag = Integer.parseInt(req.getParameter("flag")==null?"999":"".equals(req.getParameter("flag").trim())?"999":req.getParameter("flag").trim());		
		String economicproperty=req.getParameter("economicproperty");//企业类型
		Map<String,String> params =new HashMap<String,String>();
		params.put("id", id);	
		params.put("litiganttype", litiganttype);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<Map> list =null;
		list = caseShowService.queryCaseLitigantInfo(params);
		if(list!=null && list.size()>0){
			dataMap = list.get(0);
		}
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		String returnString = "";
		if(dataMap!=null && dataMap.size()>0){
			returnMap.put("entname", dataMap.get("entname"));	
			returnMap.put("regno", dataMap.get("regno"));
			returnMap.put("enttype", dataMap.get("enttype"));
			try {
				Map<String,Object> datasMap = new HashMap<String,Object>();
				Map<String,Object> sortMap =CaseSelectCacheUtile.getLinkedHashMap(litiganttype);
				for (Map.Entry<String, Object> entry : sortMap.entrySet()) {
					sortMap.put(entry.getKey(), dataMap.get(entry.getValue()));
				}  
				datasMap.put("weaponMap", sortMap); 
				returnString = FreemarkerUtil.returnString("case.ftl", datasMap);
				sortMap.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		returnMap.put("returnString", returnString);
		return returnMap;
	}
	
	/**
	 * 案件源信息
	 * @param req
	 * @param res
	 * @return
	 * @throws OptimusException
	 */
	@ResponseBody
	@RequestMapping("caseSourceInfo")
	public Map<String,Object> caseSourceInfoQuery(OptimusRequest req, OptimusResponse res) throws OptimusException {
	
		String id=req.getParameter("casesourceno");//获取主体身份代码
		int flag = Integer.parseInt(req.getParameter("flag")==null?"999":"".equals(req.getParameter("flag").trim())?"999":req.getParameter("flag").trim());		
		String type=req.getParameter("type");//企业类型
		Map<String,String> params =new HashMap<String,String>();
		params.put("id", id);		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<Map> list =null;
		if(type.equals("case")){
			list = caseShowService.queryCaseSourceInfo(params);
		}
		/*else if(type.equals("YR")){
			list = blackEntService.queryYR(params);
		}*/
		if(list!=null && list.size()>0){
			dataMap = list.get(0);
		}else{
			dataMap.put("casekind",null);
		
		    dataMap.put("litiganttype",null);
			dataMap.put("litigantname",null);
			dataMap.put("litigantcerno",null);
			dataMap.put("caseinfo",null);
			dataMap.put("regtime",null);
			
		    dataMap.put("undertaker",null);
			dataMap.put("department",null);
			dataMap.put("casesourcestate",null);
			dataMap.put("casedeadline_end",null);
			dataMap.put("regcontent",null);
		
		
		    dataMap.put("registerman",null);
			dataMap.put("approvalname",null);
			dataMap.put("turned",null);
			dataMap.put("turnedclue",null);
			dataMap.put("sourcetype",null);
		
		    dataMap.put("registerman",null);
			dataMap.put("approvalname",null);
			dataMap.put("casemodel",null);
			dataMap.put("casemodel",null);
			dataMap.put("productkind",null);
			dataMap.put("circulation",null);
			dataMap.put("division",null);
		}
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		String returnString = "";
		if(dataMap!=null && dataMap.size()>0){
			returnMap.put("entname", dataMap.get("casename"));	
			returnMap.put("regno", dataMap.get("regno"));
			returnMap.put("enttype", dataMap.get("enttype"));
			try {
				Map<String,Object> datasMap = new HashMap<String,Object>();
				Map<String,Object> sortMap =CaseSelectCacheUtile.getLinkedHashMap("source");
				for (Map.Entry<String, Object> entry : sortMap.entrySet()) {
					sortMap.put(entry.getKey(), dataMap.get(entry.getValue()));
				}  
				datasMap.put("weaponMap", sortMap); 
				returnString = FreemarkerUtil.returnString("case.ftl", datasMap);
				sortMap.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		returnMap.put("returnString", returnString);
		return returnMap;
	}
	
	/**
	 * 移送信息
	 * @param req
	 * @param res
	 * @return
	 * @throws OptimusException
	 */
	@ResponseBody
	@RequestMapping("caseMoveInfo")
	public Map<String,Object> caseMoveInfoQuery(OptimusRequest req, OptimusResponse res) throws OptimusException {
	
		String id=req.getParameter("id");//获取主体身份代码
		int flag = Integer.parseInt(req.getParameter("flag")==null?"999":"".equals(req.getParameter("flag").trim())?"999":req.getParameter("flag").trim());		
		String type=req.getParameter("type");//企业类型
		Map<String,String> params =new HashMap<String,String>();
		params.put("id", id);		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<Map> list =null;
		if(type.equals("case")){
			list = caseShowService.queryCaseMoveInfo(params);
		}
		/*else if(type.equals("YR")){
			list = blackEntService.queryYR(params);
		}*/
		if(list!=null && list.size()>0){
			dataMap = list.get(0);
		}else{
			dataMap.put("receiveunit",null);
			dataMap.put("receiver",null);
			dataMap.put("remarks",null);
			dataMap.put("movename",null);
			dataMap.put("movetime",null);
			dataMap.put("poster",null);
			dataMap.put("abolish",null);
		}
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		String returnString = "";
		if(dataMap!=null && dataMap.size()>0){
			returnMap.put("entname", dataMap.get("casename"));	
			returnMap.put("regno", dataMap.get("regno"));
			returnMap.put("enttype", dataMap.get("enttype"));
			try {
				Map<String,Object> datasMap = new HashMap<String,Object>();
				Map<String,Object> sortMap =CaseSelectCacheUtile.getLinkedHashMap("move");
				for (Map.Entry<String, Object> entry : sortMap.entrySet()) {
					sortMap.put(entry.getKey(), dataMap.get(entry.getValue()));
				}  
				datasMap.put("weaponMap", sortMap); 
				returnString = FreemarkerUtil.returnString("case.ftl", datasMap);
				sortMap.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		returnMap.put("returnString", returnString);
		return returnMap;
	}
	
}
