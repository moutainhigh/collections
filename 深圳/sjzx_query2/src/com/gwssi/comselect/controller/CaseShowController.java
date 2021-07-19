package com.gwssi.comselect.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
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
		}else{
			if (litiganttype.equals("1")) {
				dataMap.put("unitname",null);
				dataMap.put("regno",null);
				dataMap.put("lerep",null);
				dataMap.put("certype",null);
				dataMap.put("cerno",null);
				dataMap.put("dom",null);
				dataMap.put("enttype",null);
			}else{
				dataMap.put("casename",null);
				dataMap.put("name",null);
				dataMap.put("sex",null);
				dataMap.put("age",null);
				dataMap.put("occupation",null);
				
				dataMap.put("certype",null);
				dataMap.put("cerno",null);
				dataMap.put("dom",null);
				dataMap.put("tel",null);
				dataMap.put("postalcode",null);
			}
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
	
	/**基本信息（列表）
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/detail")
	public void detailedInfo(OptimusRequest req, OptimusResponse res)throws OptimusException{	
		String priPid = (String)req.getAttr("priPid");
		Map<String,String> params = new HashMap<String,String>();
		List<Map> result = null;
		params.put("priPid", priPid);
		result = caseShowService.queryList(params);		
		res.addGrid("jbxxGrid", result, null);
	}
	
	@RequestMapping("/formdetail")
	public void formdetail(OptimusRequest req, OptimusResponse res)throws OptimusException{	
		String id = req.getParameter("id");
		Map<String,String> params = new HashMap<String,String>();
		List<Map> result = null;
		params.put("id", id);
		result = caseShowService.queryListDetail(params);
		if(result!=null || result.size()>0){
			res.addForm("formpanel", result.get(0), null);
		}
		else{
			res.addForm("formpanel", null, null);
		}
	}
	
	
	/**
	 * 处罚决定书信息
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/casePenDoc")
	public void casePenDoc(OptimusRequest req, OptimusResponse res)throws OptimusException{	
		String id = req.getParameter("id");
		List<Map> result = null;
		result = caseShowService.queryPenDoc(id);
		res.addAttr("data", result, null);

	}
	
	/**
	 * 处理map中的空值
	 * @param key
	 * @param map
	 * @return
	 */
	private static String getMapValue(String key,Map map){
		if(null==map.get(key)){
			return "";
		}else{
			if(!StringUtils.isEmpty(map.get(key).toString())){
				return map.get(key).toString();
			}else{
				return "";
			}
		}
	}
	
	
	/*案件信息导出*/
	@RequestMapping("/exportExcel")
	public void exportExcel(OptimusRequest req, OptimusResponse resp)throws OptimusException, IOException{	
		String caseno = req.getParameter("caseno");
		String casename = req.getParameter("casename");
		String litigantname = req.getParameter("litigantname");
		String litigantcerno = req.getParameter("litigantcerno");
		String pendecno = req.getParameter("pendecno");
		String pendecissdate_begin = req.getParameter("pendecissdate_begin");
		String pendecissdate_end = req.getParameter("pendecissdate_end");
		String caseregistertime_begin = req.getParameter("caseregistertime_begin");
		String caseregistertime_end = req.getParameter("caseregistertime_end");
		String casestate = req.getParameter("casestate");
		
		
		Map params = new HashMap();
		params.put("caseno", caseno);
		params.put("casename", casename);
		params.put("litigantname", litigantname);
		params.put("litigantcerno", litigantcerno);
		params.put("pendecno", pendecno);
		params.put("pendecissdate_begin", pendecissdate_begin);
		params.put("pendecissdate_end", pendecissdate_end);
		params.put("caseregistertime_begin", caseregistertime_begin);
		params.put("caseregistertime_end", caseregistertime_end);
		//params.put("casestate", casestate);
		
		
		
		// excel 导出部分
		Map<String,Object> sortedMap = new LinkedHashMap<String, Object>();
		sortedMap.put("litigantcerno", "当事人证件号");
		sortedMap.put("litigantname", "当事人名称");
		sortedMap.put("pendecno", "处罚文号");
		sortedMap.put("casereason", "处罚原因");
		sortedMap.put("penbasis", "处罚依据");
		sortedMap.put("pentypecontext", "处罚结果");
		sortedMap.put("penam", "罚款金额");
		sortedMap.put("forfam", "没收金额");
		sortedMap.put("pendecissdate", "处罚日期");
		
		HttpServletRequest reqs = req.getHttpRequest();
		HttpServletResponse resps = resp.getHttpResponse();
		
		List<Map> list = caseShowService.exportExcel(params,reqs,resps);
		Workbook xs=new SXSSFWorkbook(1000);
		   final Sheet sheet=xs.createSheet("案件结果导出");
		   Row row=sheet.createRow((int)0);
		   sheet.createFreezePane( 0, 1, 0, 1 );
		   
		   sheet.setColumnWidth(0, 25*256);
		   sheet.setColumnWidth(1, 45*256);
		   sheet.setColumnWidth(2, 45*256);
		   sheet.setColumnWidth(3, 40*256);
		   sheet.setColumnWidth(4, 60*256);
		   sheet.setColumnWidth(5, 40*256);
		   sheet.setColumnWidth(6, 15*256);
		   sheet.setColumnWidth(7, 15*256);
		   sheet.setColumnWidth(8, 15*256);
		   
		   row.createCell((int)0).setCellValue("当事人证件号");
		   row.createCell((int)1).setCellValue("当事人名称");
		   row.createCell((int)2).setCellValue("处罚文号");
		   row.createCell((int)3).setCellValue("处罚原因");
		   row.createCell((int)4).setCellValue("处罚依据");
		   row.createCell((int)5).setCellValue("处罚结果");
		   row.createCell((int)6).setCellValue("罚款金额");
		   row.createCell((int)7).setCellValue("没收金额");
		   row.createCell((int)8).setCellValue("处罚日期");
		   
		   for(int i=0;i<list.size();i++){
			   Row rowdata=sheet.createRow((int)(i+1));
			   Map map =list.get(i);
			   System.out.println(map);
			   int numRow = 0;
			   for (Map.Entry<String, Object> entry : sortedMap.entrySet()) {
				   if(!"".equals((String)entry.getValue())){
					   rowdata.createCell((short)numRow).setCellValue(getMapValue(entry.getKey(), map));
					   numRow++;
				   }
			   }
		   }
		   
		   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HHmmss");//设置日期格式
		   String dowmFileRight =  df.format(new Date());
		   
		   resp.getHttpResponse().reset();
		   resp.getHttpResponse().setContentType("application/x-download;charset=GBK");

		   String downFileName = "案件结果导出" + dowmFileRight+ ".xlsx";

		   resp.getHttpResponse().setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(downFileName, "UTF-8"));
		
		   OutputStream out=resp.getHttpResponse().getOutputStream();
		   xs.write(out);
		   out.flush();
		   out.close();
	}
	
	
}
