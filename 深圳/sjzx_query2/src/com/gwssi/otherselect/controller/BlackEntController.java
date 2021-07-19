package com.gwssi.otherselect.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.otherselect.service.BlackEntService;
import com.gwssi.trs.service.MarketEntityService;
import com.gwssi.util.CacheUtile;
import com.gwssi.util.FreemarkerUtil;
import com.gwssi.util.OtherSelectCacheUtile;

@Controller
@RequestMapping("/blackent")
public class BlackEntController extends BaseController{
	
	
	@Autowired
	private BlackEntService blackEntService;
	/**
	 * 黑牌企业基本信息
	 * @param req
	 * @param res
	 * @return
	 * @throws OptimusException
	 */
	@ResponseBody
	@RequestMapping("jbxx")
	public Map<String,Object> jbxx(OptimusRequest req, OptimusResponse res) throws OptimusException {
	
		String priPid=req.getParameter("priPid");//获取主体身份代码
		String cerno=req.getParameter("cerno");//获取主体身份代码
		int flag = Integer.parseInt(req.getParameter("flag")==null?"999":"".equals(req.getParameter("flag").trim())?"999":req.getParameter("flag").trim());		
		String type=req.getParameter("type");//企业类型
		Map<String,String> params =new HashMap<String,String>();
		params.put("priPid", priPid);		
		params.put("cerno", cerno);		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<Map> list =null;
		if(type.equals("blackent")){
			list = blackEntService.queryList(params);
		}else if(type.equals("YR")){
			list = blackEntService.queryYR(params);
		}
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
				Map<String,Object> sortMap =OtherSelectCacheUtile.getLinkedHashMap(type);
				for (Map.Entry<String, Object> entry : sortMap.entrySet()) {
					sortMap.put(entry.getKey(), dataMap.get(entry.getValue()));
				}  
				datasMap.put("weaponMap", sortMap); 
				returnString = FreemarkerUtil.returnString(ConfigManager.getProperty("freemarkerSFileName"), datasMap);
				sortMap.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		returnMap.put("returnString", returnString);
		return returnMap;
	}
	
	/**
	 * 黑牌企业吊销信息
	 * @param req
	 * @param res
	 * @return
	 * @throws OptimusException
	 */
	@ResponseBody
	@RequestMapping("diaoxiaoxx")
	public Map<String,Object> diaoxiaoxx(OptimusRequest req, OptimusResponse res) throws OptimusException {
	
		String priPid=req.getParameter("priPid");//获取主体身份代码
		int flag = Integer.parseInt(req.getParameter("flag")==null?"999":"".equals(req.getParameter("flag").trim())?"999":req.getParameter("flag").trim());		
		String economicproperty=req.getParameter("economicproperty");//企业类型
		Map<String,String> params =new HashMap<String,String>();
		params.put("priPid", priPid);		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<Map> list =null;
		list = blackEntService.queryDiaoxiao(params);
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
				Map<String,Object> sortMap =OtherSelectCacheUtile.getLinkedHashMap("diaoxiao");
				for (Map.Entry<String, Object> entry : sortMap.entrySet()) {
					sortMap.put(entry.getKey(), dataMap.get(entry.getValue()));
				}  
				datasMap.put("weaponMap", sortMap); 
				returnString = FreemarkerUtil.returnString(ConfigManager.getProperty("freemarkerSFileName"), datasMap);
				sortMap.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		returnMap.put("returnString", returnString);
		return returnMap;
	}
	
	@RequestMapping("/detail")
	public void detailedInfo(OptimusRequest req, OptimusResponse res)throws OptimusException{
		String priPid = req.getParameter("priPid");//获取主体身份代码
		int flag = Integer.parseInt(req.getParameter("flag"));
		Map<String,String> params = new HashMap<String,String>();
		List<Map> list = null;
		params.put("priPid", priPid);
		switch (flag) {
		case 1: //主要人员信息
			res.addGrid("jbxxGrid", blackEntService.queryRenyuanxx(params));
			break;
		case 2: //投资人信息
			res.addGrid("jbxxGrid", blackEntService.queryTouzirenxx(params));
			break;
		case 3: //主要人员信息（一人）
			res.addGrid("jbxxGrid", blackEntService.queryYRRenyuanxx(params));
			break;
		case 4: //投资人信息（一人）
			res.addGrid("jbxxGrid", blackEntService.queryYRTouzirenxx(params));
			break;
		default:
			break;
		}
	}
	
	/*
	 * 黑牌企业主要人员信息 
	 */
	@RequestMapping("/blackentrenyuanxxdata")
	public void blackentrenyuanxxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String id = req.getParameter("id");
		res.addForm( "formpanel", blackEntService.queryrenyuanxx(id));
	
	}
	
	@RequestMapping("/blackentrenyuanxxform")
	public void blackentrenyuanxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("id", req.getParameter("id"));
		res.addPage("/page/otherselect/blackent/renyuanxxQuery.jsp");
		
	}
	
	/*
	 * 黑牌企业投资人信息 
	 */
	@RequestMapping("/blackenttouzirenxxdata")
	public void blackenttouzirenxxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String id = req.getParameter("id");
		Object obj = blackEntService.querytouzirenxx(id) ; 
		res.addForm( "formpanel",obj);
	
	}
	
	@RequestMapping("/blackenttouzirenxxform")
	public void blackenttouzirenxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("id", req.getParameter("id"));
		res.addPage("/page/otherselect/blackent/touzirenxxQuery.jsp");
		
	}
	
	/*
	 * 一人企业主要人员信息 
	 */
	@RequestMapping("/yrrenyuanxxdata")
	public void yrrenyuanxxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String id = req.getParameter("id");
		res.addForm( "formpanel", blackEntService.queryyrrenyuanxx(id));
	
	}
	
	@RequestMapping("/yrrenyuanxxform")
	public void yrrenyuanxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("id", req.getParameter("id"));
		res.addPage("/page/otherselect/yiren/renyuanxxQuery.jsp");
		
	}
	
	/*
	 * 一人企业投资人信息 
	 */
	@RequestMapping("/yrtouzirenxxdata")
	public void yrtouzirenxxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String id = req.getParameter("id");
		res.addForm( "formpanel", blackEntService.queryyrtouzirenxx(id));
	
	}
	
	@RequestMapping("/yrtouzirenxxform")
	public void yrtouzirenxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("id", req.getParameter("id"));
		res.addPage("/page/otherselect/yiren/touzirenxxQuery.jsp");
		
	}
}
