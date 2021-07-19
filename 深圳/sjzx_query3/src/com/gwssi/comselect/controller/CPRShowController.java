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

import com.gwssi.comselect.service.CPRShowService;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.trs.service.MarketEntityService;
import com.gwssi.util.CacheUtile;
import com.gwssi.util.CPRSelectCacheUtile;
import com.gwssi.util.FreemarkerUtil;
import com.gwssi.util.OtherSelectCacheUtile;

@Controller
@RequestMapping("/CPRShow")
public class CPRShowController extends BaseController{
	
	@Resource
	private CPRShowService cprShowService;	
	
	/**
	 * 12315查询列表
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/CPRQueryList")
	public void CPRQueryList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		Map<String, String> form = req.getForm("CPRQueryListPanel");
			String flag = req.getParameter("flag");
			if(flag !=null){
				String count= cprShowService.getCPRListCount(form);
				resp.addAttr("count", count);
			}else {
				List<Map> lstParams= cprShowService.getCPRList(form,req.getHttpRequest());
				//System.out.println(lstParams.toString());
				resp.addGrid("CPRQueryListGrid",lstParams);
			}
	}

	/**
	 * 信息件信息
	 * @param req
	 * @param res
	 * @return
	 * @throws OptimusException
	 */
	@ResponseBody
	@RequestMapping("CPRInfoWare")
	public Map<String,Object> CPRInfoWareQuery(OptimusRequest req, OptimusResponse res) throws OptimusException {
	
		String infowareid=req.getParameter("infowareid");//获取主体身份代码
		int flag = Integer.parseInt(req.getParameter("flag")==null?"999":"".equals(req.getParameter("flag").trim())?"999":req.getParameter("flag").trim());		
		String type=req.getParameter("type");//企业类型
		Map<String,String> params =new HashMap<String,String>();
		params.put("infowareid", infowareid);		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<Map> list =null;
		if(type.equals("CPR")){
			list = cprShowService.queryCPRInfoWare(params);
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
			returnMap.put("entname", dataMap.get("CPRname"));	
			returnMap.put("regno", dataMap.get("regno"));
			returnMap.put("enttype", dataMap.get("enttype"));
			try {
				Map<String,Object> datasMap = new HashMap<String,Object>();
				Map<String,Object> sortMap =CPRSelectCacheUtile.getLinkedHashMap(type);
				for (Map.Entry<String, Object> entry : sortMap.entrySet()) {
					sortMap.put(entry.getKey(), dataMap.get(entry.getValue()));
				}  
				datasMap.put("weaponMap", sortMap); 
				returnString = FreemarkerUtil.returnString("cpr.ftl", datasMap);
				sortMap.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		returnMap.put("returnString", returnString);
		return returnMap;
	}
	
	/**
	 * 涉及主体信息
	 * @param req
	 * @param res
	 * @return
	 * @throws OptimusException
	 */
	@ResponseBody
	@RequestMapping("CPRInvolvedMain")
	public Map<String,Object> CPRInvolvedMainQuery(OptimusRequest req, OptimusResponse res) throws OptimusException {
	
		String invmaiid=req.getParameter("invmaiid");//获取主体身份代码
		int flag = Integer.parseInt(req.getParameter("flag")==null?"999":"".equals(req.getParameter("flag").trim())?"999":req.getParameter("flag").trim());		
		String economicproperty=req.getParameter("economicproperty");//企业类型
		Map<String,String> params =new HashMap<String,String>();
		params.put("invmaiid", invmaiid);	
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<Map> list =null;
		list = cprShowService.CPRInvolvedMainQuery(params);
		System.out.println(list.toString());
		if(list!=null && list.size()>0){
			dataMap = list.get(0);
		}
		else{
			dataMap.put("agentid",null);
			dataMap.put("invname",null);
			dataMap.put("ebusiness",null);
			dataMap.put("websitetype",null);
			dataMap.put("website",null);
			dataMap.put("compregno",null);
			
			dataMap.put("generalinfo",null);
			dataMap.put("supdepcode",null);
			dataMap.put("supdepname",null);
			dataMap.put("modifiedby",null);
			dataMap.put("remshotype",null);
			dataMap.put("lasmodtime",null);
			
			dataMap.put("pttype",null);
			dataMap.put("enttype",null);
			dataMap.put("addr",null);
			dataMap.put("contact",null);
			
			dataMap.put("tel",null);
			dataMap.put("tradetype",null);
			dataMap.put("stretowncode",null);
			dataMap.put("division",null);
		}
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		String returnString = "";
		if(dataMap!=null && dataMap.size()>0){
			returnMap.put("entname", dataMap.get("entname"));	
			returnMap.put("regno", dataMap.get("regno"));
			returnMap.put("enttype", dataMap.get("enttype"));
			try {
				Map<String,Object> datasMap = new HashMap<String,Object>();
				Map<String,Object> sortMap =CPRSelectCacheUtile.getLinkedHashMap("involvedMain");
				for (Map.Entry<String, Object> entry : sortMap.entrySet()) {
					sortMap.put(entry.getKey(), dataMap.get(entry.getValue()));
				}  
				datasMap.put("weaponMap", sortMap); 
				returnString = FreemarkerUtil.returnString("cpr.ftl", datasMap);
				sortMap.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		returnMap.put("returnString", returnString);
		return returnMap;
	}
	
	/**
	 * 涉及客体信息
	 * @param req
	 * @param res
	 * @return
	 * @throws OptimusException
	 */
	@ResponseBody
	@RequestMapping("CPRInvolvedObject")
	public Map<String,Object> CPRInvolvedObjectQuery(OptimusRequest req, OptimusResponse res) throws OptimusException {
	
		String invobjid=req.getParameter("invobjid");//获取主体身份代码
		int flag = Integer.parseInt(req.getParameter("flag")==null?"999":"".equals(req.getParameter("flag").trim())?"999":req.getParameter("flag").trim());		
		String economicproperty=req.getParameter("economicproperty");//企业类型
		Map<String,String> params =new HashMap<String,String>();
		params.put("invobjid", invobjid);	
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<Map> list =null;
		list = cprShowService.CPRInvolvedObjectQuery(params);
		System.out.println(list.toString());
		if(list!=null && list.size()>0){
			dataMap = list.get(0);
		}else{
			dataMap.put("invobjid",null);
			dataMap.put("invobjtype",null);
			dataMap.put("exporttype",null);
			dataMap.put("standard",null);
			dataMap.put("quantity",null);
			
			dataMap.put("brandname",null);
			dataMap.put("mdsename",null);
			dataMap.put("invoam",null);
			dataMap.put("measureunit",null);
		}
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		String returnString = "";
		if(dataMap!=null && dataMap.size()>0){
			returnMap.put("entname", dataMap.get("entname"));	
			returnMap.put("regno", dataMap.get("regno"));
			returnMap.put("enttype", dataMap.get("enttype"));
			try {
				Map<String,Object> datasMap = new HashMap<String,Object>();
				Map<String,Object> sortMap =CPRSelectCacheUtile.getLinkedHashMap("involvedObject");
				for (Map.Entry<String, Object> entry : sortMap.entrySet()) {
					sortMap.put(entry.getKey(), dataMap.get(entry.getValue()));
				}  
				datasMap.put("weaponMap", sortMap); 
				returnString = FreemarkerUtil.returnString("cpr.ftl", datasMap);
				sortMap.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		returnMap.put("returnString", returnString);
		return returnMap;
	}
	
	/**
	 * 信息提供方信息
	 * @param req
	 * @param res
	 * @return
	 * @throws OptimusException
	 */
	@ResponseBody
	@RequestMapping("CPRInfoProvider")
	public Map<String,Object> CPRInfoProviderQuery(OptimusRequest req, OptimusResponse res) throws OptimusException {
	
		String infproid=req.getParameter("infproid");//获取主体身份代码
		int flag = Integer.parseInt(req.getParameter("flag")==null?"999":"".equals(req.getParameter("flag").trim())?"999":req.getParameter("flag").trim());		
		String economicproperty=req.getParameter("economicproperty");//企业类型
		Map<String,String> params =new HashMap<String,String>();
		params.put("infproid", infproid);	
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<Map> list =null;
		list = cprShowService.CPRInfoProviderQuery(params);
		System.out.println(list.toString());
		if(list!=null && list.size()>0){
			dataMap = list.get(0);
		}else{
	        dataMap.put("agentid",null);
			dataMap.put("division",null);
			dataMap.put("mobile",null);
			dataMap.put("fax",null);
			dataMap.put("pertype",null);
			dataMap.put("peride",null);
			dataMap.put("enttype",null);
			
			dataMap.put("revetype",null);
			
			dataMap.put("addr",null);
			dataMap.put("persname",null);
			dataMap.put("landtel",null);
			dataMap.put("workunit",null);
			dataMap.put("age",null);
			
			dataMap.put("handisign",null);
			dataMap.put("email",null);
			dataMap.put("cerno",null);
			dataMap.put("certype",null);
		}
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		String returnString = "";
		if(dataMap!=null && dataMap.size()>0){
			returnMap.put("entname", dataMap.get("entname"));	
			returnMap.put("regno", dataMap.get("regno"));
			returnMap.put("enttype", dataMap.get("enttype"));
			try {
				Map<String,Object> datasMap = new HashMap<String,Object>();
				Map<String,Object> sortMap =CPRSelectCacheUtile.getLinkedHashMap("infoProvider");
				for (Map.Entry<String, Object> entry : sortMap.entrySet()) {
					sortMap.put(entry.getKey(), dataMap.get(entry.getValue()));
				}  
				datasMap.put("weaponMap", sortMap); 
				returnString = FreemarkerUtil.returnString("cpr.ftl", datasMap);
				sortMap.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		returnMap.put("returnString", returnString);
		return returnMap;
	}
	
	/**
	 * 分流信息
	 * @param req
	 * @param res
	 * @return
	 * @throws OptimusException
	 */
	@ResponseBody
	@RequestMapping("CPRDispatch")
	public Map<String,Object> CPRDispatchQuery(OptimusRequest req, OptimusResponse res) throws OptimusException {
	
		String infowareid=req.getParameter("infowareid");//获取主体身份代码
		int flag = Integer.parseInt(req.getParameter("flag")==null?"999":"".equals(req.getParameter("flag").trim())?"999":req.getParameter("flag").trim());		
		String economicproperty=req.getParameter("economicproperty");//企业类型
		Map<String,String> params =new HashMap<String,String>();
		params.put("infowareid", infowareid);	
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<Map> list =null;
		list = cprShowService.CPRDispatchQuery(params);
		System.out.println(list.toString());
		if(list!=null && list.size()>0){
			dataMap = list.get(0);
		}else{
			dataMap.put("dispatchid",null);
			dataMap.put("dispatchtime",null);
			dataMap.put("dispatchtype",null);
			dataMap.put("dispatchuserid",null);
			dataMap.put("dispatchusername",null);
			dataMap.put("evegrade",null);
			
			dataMap.put("modifiedby",null);
			dataMap.put("lasmodtime",null);
			
		}
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		String returnString = "";
		if(dataMap!=null && dataMap.size()>0){
			returnMap.put("entname", dataMap.get("entname"));	
			returnMap.put("regno", dataMap.get("regno"));
			returnMap.put("enttype", dataMap.get("enttype"));
			try {
				Map<String,Object> datasMap = new HashMap<String,Object>();
				Map<String,Object> sortMap =CPRSelectCacheUtile.getLinkedHashMap("dispatch");
				for (Map.Entry<String, Object> entry : sortMap.entrySet()) {
					sortMap.put(entry.getKey(), dataMap.get(entry.getValue()));
				}  
				datasMap.put("weaponMap", sortMap); 
				returnString = FreemarkerUtil.returnString("cpr.ftl", datasMap);
				sortMap.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		returnMap.put("returnString", returnString);
		return returnMap;
	}
	
	@RequestMapping("/CPRInvestigationQueryList")
	public void CPRInvestigationQueryList(OptimusRequest req, OptimusResponse res)throws OptimusException{
 		String infowareid = req.getParameter("infowareid");//获取主体身份代码
		int flag = Integer.parseInt(req.getParameter("flag"));
		Map<String,String> params = new HashMap<String,String>();
		params.put("infowareid", infowareid);
		List<Map> lstParams= cprShowService.CPRInvestigationQueryList(params);
//		System.out.println(lstParams.toString());
		res.addGrid("CPRInvestigation", lstParams);
	}
	
	/**
	 * 调查信息liebiao
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@ResponseBody
	@RequestMapping("/CPRInvestigationDetail")
	public void CPRInvestigationQuery(OptimusRequest req, OptimusResponse res) throws OptimusException {
	
		String investigationid = req.getParameter("investigationid");
		if(investigationid==null){
			throw new OptimusException("获取参数失败");
		}
		/*List<Map> lstParams= cprShowService.CPRInvestigationDetailQuery(investigationid);
		System.out.println(lstParams.toString();
		res.addForm("formpanel", lstParams);*/
		
		res.addForm("formpanel", cprShowService.CPRInvestigationDetailQuery(investigationid));
	}
	
	/**
	 * 调查信息qingxinag
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/CPRInvestigationQueryDetail")
	public void CPRInvestigationDetail(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("investigationid", req.getParameter("investigationid"));
		res.addPage("/page/comselect/cpr/CPRInvestigationQueryDetail.jsp");
		
	}
	
	/**
	 * 调解信息
	 * @param req
	 * @param res
	 * @return
	 * @throws OptimusException
	 */
	@ResponseBody
	@RequestMapping("CPRMediation")
	public Map<String,Object> CPRMediationQuery(OptimusRequest req, OptimusResponse res) throws OptimusException {
	
		String feedbackid=req.getParameter("feedbackid");//获取主体身份代码
		int flag = Integer.parseInt(req.getParameter("flag")==null?"999":"".equals(req.getParameter("flag").trim())?"999":req.getParameter("flag").trim());		
		String economicproperty=req.getParameter("economicproperty");//企业类型
		Map<String,String> params1 =new HashMap<String,String>();
		params1.put("feedbackid", feedbackid);	
		Map<String,Object> dataMap1 = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<Map> list1 =null;
		
		list1 = cprShowService.CPRFeedbackQuery(params1);
		System.out.println(list1.toString());
		if(list1!=null && list1.size()>0){
			dataMap1 = list1.get(0);
		}
		Map<String,String> params =new HashMap<String,String>();
		List<Map> list =null;
		if(dataMap1!=null && dataMap1.size()>0){
			if (dataMap1.get("mediationid")!=null) {
				if (dataMap1.get("mediationid").toString()!=null || !dataMap1.get("mediationid").equals("")) {
					params.put("mediationid", dataMap1.get("mediationid").toString());
					list = cprShowService.CPRMediationQuery(params);
					System.out.println(list.toString());
				}
			}
		}
		
		if (list != null && list.size()>0) {
			dataMap = list.get(0);
		}else{
			dataMap.put("mediationid",null);
			dataMap.put("govconfiscation",null);
			dataMap.put("difficult",null);
			dataMap.put("mainbodyqualify",null);
			dataMap.put("conciliationfirst",null);
	 
			dataMap.put("supportlawsuit",null);
			dataMap.put("officialletter",null);
			dataMap.put("satisfied",null);
			dataMap.put("pennant",null);
			dataMap.put("thankletter",null);
			dataMap.put("mediareport",null);
			
			dataMap.put("disam",null);
			dataMap.put("tortype",null);
			dataMap.put("douameam",null);
			dataMap.put("redecolos",null);
			dataMap.put("cheatsign",null);
			dataMap.put("defobl",null);
			
			dataMap.put("spiameam",null);
			dataMap.put("intno",null);
			dataMap.put("intenddate",null);
			dataMap.put("mediate_result",null);
		}
	
		Map<String,Object> returnMap = new HashMap<String,Object>();
		String returnString = "";
		if(dataMap!=null && dataMap.size()>0){
			returnMap.put("entname", dataMap.get("entname"));	
			returnMap.put("regno", dataMap.get("regno"));
			returnMap.put("enttype", dataMap.get("enttype"));
			try {
				Map<String,Object> datasMap = new HashMap<String,Object>();
				Map<String,Object> sortMap =CPRSelectCacheUtile.getLinkedHashMap("mediation");
				for (Map.Entry<String, Object> entry : sortMap.entrySet()) {
					sortMap.put(entry.getKey(), dataMap.get(entry.getValue()));
				}  
				datasMap.put("weaponMap", sortMap); 
				returnString = FreemarkerUtil.returnString("cpr.ftl", datasMap);
				sortMap.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		returnMap.put("returnString", returnString);
		return returnMap;
	}
	

	/**
	 * 反馈信息
	 * @param req
	 * @param res
	 * @return
	 * @throws OptimusException
	 */
	@ResponseBody
	@RequestMapping("CPRFeedback")
	public Map<String,Object> CPRFeedbackQuery(OptimusRequest req, OptimusResponse res) throws OptimusException {
	
		String feedbackid=req.getParameter("feedbackid");//获取主体身份代码
		int flag = Integer.parseInt(req.getParameter("flag")==null?"999":"".equals(req.getParameter("flag").trim())?"999":req.getParameter("flag").trim());		
		String economicproperty=req.getParameter("economicproperty");//企业类型
		Map<String,String> params =new HashMap<String,String>();
		params.put("feedbackid", feedbackid);	
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<Map> list =null;
		list = cprShowService.CPRFeedbackQuery(params);
		System.out.println(list.toString());
		if(list!=null && list.size()>0){
			dataMap = list.get(0);
		}else{
			dataMap.put("feedbackid",null);
			dataMap.put("feedbacktime",null);
			dataMap.put("emeprogress",null);
			dataMap.put("handletype",null);
			dataMap.put("replytype",null);
			dataMap.put("handleadvice",null);
	  
	 
			dataMap.put("transferorgan",null);
			dataMap.put("feeuseid",null);
			dataMap.put("feedepcode",null);
			dataMap.put("feedepname",null);
			dataMap.put("putoncase",null);
			dataMap.put("replied",null);
			
			dataMap.put("infowaretype",null);
			dataMap.put("executecar",null);
			dataMap.put("executeperson",null);
			dataMap.put("modifiedby",null);
			dataMap.put("lasmodtime",null);
			dataMap.put("handdep",null);
			
			dataMap.put("aidunittype",null);
			dataMap.put("feeregper",null);
			dataMap.put("acctype",null);
			dataMap.put("tran",null);
		}
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		String returnString = "";
		if(dataMap!=null && dataMap.size()>0){
			returnMap.put("entname", dataMap.get("entname"));	
			returnMap.put("regno", dataMap.get("regno"));
			returnMap.put("enttype", dataMap.get("enttype"));
			try {
				Map<String,Object> datasMap = new HashMap<String,Object>();
				Map<String,Object> sortMap =CPRSelectCacheUtile.getLinkedHashMap("feedback");
				for (Map.Entry<String, Object> entry : sortMap.entrySet()) {
					sortMap.put(entry.getKey(), dataMap.get(entry.getValue()));
				}  
				datasMap.put("weaponMap", sortMap); 
				returnString = FreemarkerUtil.returnString("cpr.ftl", datasMap);
				sortMap.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		returnMap.put("returnString", returnString);
		return returnMap;
	}
}
