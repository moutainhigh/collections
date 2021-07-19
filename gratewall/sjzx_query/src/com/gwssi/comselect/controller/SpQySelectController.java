package com.gwssi.comselect.controller;


import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.comselect.service.SpQySelectService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.trs.controller.MarketEntityController;
import com.gwssi.util.FreemarkerUtil;
import com.gwssi.util.NBSelectCacheUtile;

@Controller
@RequestMapping("/SpQySelect")
public class SpQySelectController{
	private static  Logger log=Logger.getLogger(SpQySelectController.class);

	@Resource
	private SpQySelectService spQySelectService;
	
	/**
	 * 获取代码集
	 */
	@ResponseBody
	@RequestMapping("code_value")
	public void getCode_value(OptimusRequest req, OptimusResponse resp) throws OptimusException{
		HttpServletRequest request=req.getHttpRequest();
		//String type=request.getParameter("type");
		//String param=request.getParameter("param");
		String type="";
		String param = "";
		if ((String)req.getAttr("type")==null) {
			type=req.getParameter("type");
		}else{
			type=(String)req.getAttr("type"); //关联字段
		}
		
		if ((String)req.getAttr("param")==null) {
			param=req.getParameter("param");
		}else{
			param=(String)req.getAttr("param"); //关联字段
		}
		
		
		
		if(StringUtils.isEmpty(param)){
			resp.addTree(type ,spQySelectService.queryCode_value(type));
		}else{
			resp.addTree(type ,spQySelectService.queryCode_value(type,param));
		}
		
	}

	/**
	 * 年报查询列表
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/SpQyQueryList")
	public void SpQyQueryList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		Map<String, String> form = req.getForm("SpQyQueryListPanel");
		String flag = req.getParameter("flag");
		if(flag !=null){
			String count= spQySelectService.getSpQyListCount(form);//总条数
			resp.addAttr("count", count);
		}else {
			List<Map> lstParams= spQySelectService.getSpQyList(form,req.getHttpRequest());//查询前100条信息 
			System.out.println(lstParams.toString());
			resp.addGrid("SpQyQueryListGrid",lstParams);
		}
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/SPQyQueryDetail")
	public void SPQyQueryDetail(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String pripid = "";
		if ((String)req.getAttr("pripid")==null) {
			pripid=req.getParameter("pripid");
		}else{
			pripid=(String)req.getAttr("pripid"); //关联字段
		}
		List<Map> lstParams= spQySelectService.getSpQyDetail(pripid);//查询前100条信息 
		System.out.println(lstParams.toString());
		resp.addGrid("SPLicQueryListGrid",lstParams);
	}
	
	/**
	 * 年报查询列表
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/queryNBDetailList")
	public void queryNBDetailList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		//String pripid = (String)req.getAttr("pripid");
		String pripid = req.getParameter("pripid");
		String type = req.getParameter("type");
		List<Map> lstParams=null;
		if (type !=null && "1".equals(type)) {
			lstParams = spQySelectService.getQYList(pripid,req.getHttpRequest());
		}
		if (type !=null && "0".equals(type)) {
			lstParams = spQySelectService.getGTList(pripid,req.getHttpRequest());
		}
		System.out.println();
		log.info("年报【年度信息】查询的结果集:"+lstParams.toString());
		//resp.addGrid("NBQueryDetailGrid",lstParams);
		resp.addAttr("msg", lstParams);
		//resp.addResponseBody(lstParams.toString());
	}
	
	/**
	 * 年报查询列表
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/queryNBDetailReg")
	public void queryNBDetailReg(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		//String pripid = (String)req.getAttr("pripid");
		String pripid = req.getParameter("pripid");
		String type = req.getParameter("type");
		List<Map> lstParams=null;
		if (type !=null && "1".equals(type)) {
			lstParams = spQySelectService.getQYList(pripid,req.getHttpRequest());
		}
		if (type !=null && "0".equals(type)) {
	    	lstParams = spQySelectService.getGTList(pripid,req.getHttpRequest());
		}
		System.out.println();
		log.info("年报【年度信息】查询的结果集:"+lstParams.toString());
		resp.addGrid("NBQueryDetailGrid",lstParams);
		//resp.addAttr("msg", lstParams);
		//resp.addResponseBody(lstParams.toString());
	}
	/**
	 * 年报信息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/NBQYGTInfoQueryInfo")
	public void NBQYInfoQueryGrid(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String id ="";
		if ((String)req.getAttr("id")==null) {
			id=req.getParameter("id");
		}else{
			 id=(String)req.getAttr("id"); //关联字段
		}
		String type ="";
		if ((String)req.getAttr("type")==null) {
			type=req.getParameter("type");
		}else{
			type=(String)req.getAttr("type"); //企业或个体判断区分
		}
		int entityNo =0;
		if (req.getAttr("entityNo")==null) {
			entityNo=Integer.parseInt(req.getParameter("entityNo"));
		}else{
			entityNo=Integer.parseInt((String)req.getAttr("entityNo"));//标识
		}
		
	/*	int flag = Integer.parseInt(req.getParameter("flag")==null?"999":"".equals(req.getParameter("flag").trim())?"999":req.getParameter("flag").trim());*/		
	   	Map<String,String> params =new HashMap<String,String>();
		params.put("id", id);		
		if("qy".equals(type)){ //企业年报
			switch (entityNo) {
				case 1: // 基本信息
					List<Map> lstParams = spQySelectService.queryNBQYInfo(params,entityNo);
					if(lstParams !=null && lstParams.size()>0){
						log.info("企业年报【基本信息查询】查询的结果集:"+lstParams.get(0).toString());
						res.addForm("qyjbxxPanel", lstParams.get(0));
					}else{
						res.addForm("qyjbxxPanel", null);
					}
					
					break;
				case 2: // 资产状况信息
					List<Map> lstParamszc = spQySelectService.queryNBQYInfo(params,entityNo);
					if(lstParamszc !=null && lstParamszc.size()>0){
						log.info("企业年报【资产状况信息】查询的结果集:"+lstParamszc.get(0).toString());
						res.addForm("qyzczkPanel", lstParamszc.get(0));
					}else{
						res.addForm("qyzczkPanel", null);
					}
					
				//	res.addForm("qyzczkPanel", spQySelectService.queryNBQYInfo(params,entityNo).get(0));
					break;
				case 3: // 股东及出资
					res.addGrid("qyczxxGrid",  spQySelectService.queryNBQYInfo(params,entityNo));
					break;
				case 4: // 股权变更信息
				//	log.info("企业年报【股权变更信息查询】查询的结果集:"+spQySelectService.queryNBQYInfo(params,entityNo).toString());
					res.addGrid("qygqbgGrid", spQySelectService.queryNBQYInfo(params,entityNo));
					break;
				case 5: // 对外投资信息
				//	log.info("企业年报【对外投资信息】查询的结果集:"+spQySelectService.queryNBQYInfo(params,entityNo).toString());
					res.addGrid("qydwtzGrid", spQySelectService.queryNBQYInfo(params,entityNo));
					break;
				case 6: // 对外提供保证担保
				//	log.info("企业年报【对外提供保证担保】查询的结果集:"+spQySelectService.queryNBQYInfo(params,entityNo).toString());
					res.addGrid("qydwdbGrid", spQySelectService.queryNBQYInfo(params,entityNo));
					break;
				case 7: // 网站或网店
					List<Map> lstParamsWD = spQySelectService.queryNBQYInfo(params,entityNo);
					if(lstParamsWD !=null && lstParamsWD.size()>0){
						log.info("企业年报【 网站或网店】查询的结果集:"+lstParamsWD.get(0).toString());
						res.addForm("qywdxxPanel", lstParamsWD.get(0));
					}else{
						res.addForm("qywdxxPanel", null);
					}
					
					//res.addForm("qywdxxPanel", spQySelectService.queryNBQYInfo(params,entityNo).get(0));
					break;
				case 8: // 党建信息
					List<Map> lstParamsDJ = spQySelectService.queryNBQYInfo(params,entityNo);
					if(lstParamsDJ !=null && lstParamsDJ.size()>0){
						log.info("企业年报【党建信息】查询的结果集:"+lstParamsDJ.get(0).toString());
						res.addForm("qydjxxPanel", lstParamsDJ.get(0));
					}else{
						res.addForm("qydjxxPanel", null);
					}
					//res.addForm("qydjxxPanel", spQySelectService.queryNBQYInfo(params,entityNo).get(0));
					break;
				case 9: //修改信息
				//	log.info("企业年报【修改信息】查询的结果集:"+spQySelectService.queryNBQYInfo(params,entityNo).toString());
					res.addGrid("qyxgxxGrid", spQySelectService.queryNBQYInfo(params,entityNo));
					break;
				default:
					break;
		}
		}else if("gt".equals(type)){ //个体年报
				switch (entityNo) {
				case 1: // 基本信息
					List<Map> lstParams = spQySelectService.queryNBGTInfo(params,entityNo);
					if(lstParams !=null && lstParams.size()>0){
						log.info("个体年报【基本信息查询】查询的结果集:"+lstParams.get(0).toString());
						res.addForm("gtjbxxPanel", lstParams.get(0));
					}else{
						res.addForm("gtjbxxPanel", null);
					}
				//	res.addForm("gtjbxxPanel", spQySelectService.queryNBGTInfo(params,entityNo));
					break;
				case 2: // 生产经营情况
					List<Map> lstParamsZC = spQySelectService.queryNBGTInfo(params,entityNo);
					if(lstParamsZC !=null && lstParamsZC.size()>0){
						log.info("个体年报【生产经营情况查询】查询的结果集:"+lstParamsZC.get(0).toString());
						res.addForm("gtzczkPanel", lstParamsZC.get(0));
					}else{
						res.addForm("gtzczkPanel", null);
					}
				//	res.addForm("gtzczkPanel", spQySelectService.queryNBGTInfo(params,entityNo));
					break;
				case 3: // 行政许可信息
	         	//	log.info("企业年报【行政许可信息】查询的结果集:"+spQySelectService.queryNBGTInfo(params,entityNo).toString());
					res.addGrid("gtxzxkGrid", spQySelectService.queryNBGTInfo(params,entityNo));
					break;
				case 4: // 网站或网店
					List<Map> lstParamsWZ = spQySelectService.queryNBGTInfo(params,entityNo);
					if(lstParamsWZ !=null && lstParamsWZ.size()>0){
						log.info("个体年报【网站或网店查询】查询的结果集:"+lstParamsWZ.get(0).toString());
						res.addForm("gtwdxxPanel", lstParamsWZ.get(0));
					}else{
						res.addForm("gtwdxxPanel", null);
					}
					
				//	res.addForm("gtwdxxPanel", spQySelectService.queryNBGTInfo(params,entityNo).get(0));
					break;
				case 5: // 党建信息
					List<Map> lstParamsDJ = spQySelectService.queryNBGTInfo(params,entityNo);
					if(lstParamsDJ !=null && lstParamsDJ.size()>0){
						log.info("个体年报【党建信息查询】查询的结果集:"+lstParamsDJ.get(0).toString());
						res.addForm("gtdjxxPanel", lstParamsDJ.get(0));
					}else{
						res.addForm("gtdjxxPanel", null);
					}
				//	res.addForm("gtdjxxPanel", spQySelectService.queryNBGTInfo(params,entityNo).get(0));
					break;
				case 6: // 修改信息
				//	log.info("企业年报【修改信息】查询的结果集:"+spQySelectService.queryNBGTInfo(params,entityNo).toString());
					res.addGrid("gtxgxxGrid", spQySelectService.queryNBGTInfo(params,entityNo));
					break;
				default:
					break;
				}
		}else {
			this.log.error("年报类型错误。！！！！！"+"当前年报类型："+type);
		}
	}

	
	/**
	 * 年报信息
	 * @param req
	 * @param res
	 * @return
	 * @throws OptimusException
	 */
	@ResponseBody
	@RequestMapping("NBQYGTInfoQuery")
	public Map<String,Object> NBQYInfoQuery(OptimusRequest req, OptimusResponse res) throws OptimusException {
	
		String id=req.getParameter("id");//关联字段
	/*	int flag = Integer.parseInt(req.getParameter("flag")==null?"999":"".equals(req.getParameter("flag").trim())?"999":req.getParameter("flag").trim());*/		
		String type=req.getParameter("type");//企业或个体判断区分
		int entityNo=Integer.parseInt(req.getParameter("entityNo"));//标识
	   	Map<String,String> params =new HashMap<String,String>();
		params.put("id", id);		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<Map> list =null;
		if("qy".equals(type)){ //企业年报
			switch (entityNo) {
				case 1: // 基本信息
					list = spQySelectService.queryNBQYInfo(params,entityNo);
					break;
				case 2: // 资产状况信息
					list = spQySelectService.queryNBQYInfo(params,entityNo);
					break;
				case 7: // 网站或网店
					list = spQySelectService.queryNBQYInfo(params,entityNo);
					break;
				case 8: // 党建信息
					list = spQySelectService.queryNBQYInfo(params,entityNo);
					break;
				default:
					break;
		   }
		}else if("gt".equals(type)){ //个体年报
				switch (entityNo) {
				case 1: // 基本信息
					list = spQySelectService.queryNBGTInfo(params,entityNo);
					break;
				case 2: // 生产经营情况
					list = spQySelectService.queryNBGTInfo(params,entityNo);
					break;
				case 4: // 网站或网店
					list = spQySelectService.queryNBGTInfo(params,entityNo);
					break;
				case 5: // 党建信息
					list = spQySelectService.queryNBGTInfo(params,entityNo);
					break;
				default:
					break;
				}
		}else {
			this.log.error("年报类型错误。！！！！！"+"当前年报类型："+type);
		}
		
		if(list!=null && list.size()>0){
			dataMap = list.get(0);
		}else{
			if("qy".equals(type)){ //企业年报
				switch (entityNo) {
				case 2: // 资产状况信息
					dataMap.put("assgro",null);
					dataMap.put("liagro",null);
					dataMap.put("vendinc",null);
					dataMap.put("maibusinc",null);
					dataMap.put("progro",null);
					dataMap.put("netinc",null);
					
					dataMap.put("ratgro",null);
					dataMap.put("totequ",null);
					dataMap.put("ifassgro",null);
					dataMap.put("ifliagro",null);
					dataMap.put("ifvendinc",null);
					dataMap.put("ifmaibusinc",null);
					dataMap.put("ifprogro",null);
					
					dataMap.put("ifnetinc",null);
					dataMap.put("ifratgro",null);
					dataMap.put("iftotequ",null);
					break;
				case 7: // 网站或网店
					dataMap.put("webtype",null);
					dataMap.put("websitname",null);
					dataMap.put("domain",null);
					break;
				case 8: // 党建信息
					dataMap.put("numparm",null);
					dataMap.put("parins",null);
					dataMap.put("resparmsign",null);
					dataMap.put("resparsecsign",null);
					break;
				default:
					break;
			}
			}else if("gt".equals(type)){ //个体年报
					switch (entityNo) {
					case 2: // 生产经营情况
						dataMap.put("vendinc",null);
						dataMap.put("ratgro",null);
						dataMap.put("ifvendinc",null);
						dataMap.put("ifratgro",null);
						break;
					case 4: // 网站或网店
						dataMap.put("webtype",null);
						dataMap.put("websitname",null);
						dataMap.put("domain",null);
						break;
					case 5: // 党建信息
						dataMap.put("numparm",null);
						dataMap.put("parins",null);
						dataMap.put("resparmsign",null);
						dataMap.put("resparsecsign",null);
						break;
					default:
						break;
					}
			}
		}
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		String returnString = "";
		if(dataMap!=null && dataMap.size()>0){
		/*	returnMap.put("entname", dataMap.get("CPRname"));	
			returnMap.put("regno", dataMap.get("regno"));
			returnMap.put("enttype", dataMap.get("enttype"));*/
			try {
				Map<String,Object> datasMap = new HashMap<String,Object>();
				Map<String,Object> sortMap =NBSelectCacheUtile.getLinkedHashMap(type,entityNo);
				for (Map.Entry<String, Object> entry : sortMap.entrySet()) {
					sortMap.put(entry.getKey(), dataMap.get(entry.getValue()));
				}  
				datasMap.put("weaponMap", sortMap); 
				returnString = FreemarkerUtil.returnString("nb.ftl", datasMap);
				sortMap.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		returnMap.put("returnString", returnString);
		return returnMap;
	}
	
	/**
	 * 对外提供保证担保详情信息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/getDWDBQueryDetail")
	public void getDWDBQueryDetail(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String ancheid = req.getParameter("ancheid");
		if(ancheid==null){
			throw new OptimusException("获取参数失败");
		}
		resp.addForm("NBQYDWDBDetialPanel", spQySelectService.getDWDBQueryById(ancheid));
	}
	/**
	 * 对外提供保证担保详情信息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/getCZXXQueryDetail")
	public void getCZXXQueryDetail(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String ancheid = req.getParameter("ancheid");
		if(ancheid==null){
			throw new OptimusException("获取参数失败");
		}
		resp.addForm("NBQYCZXXDetialPanel", spQySelectService.getCZXXQueryById(ancheid));
	}
	
	/**
	 * 工商联络员信息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/getContactDetailById")
	public void getContactDetailById(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String id = req.getParameter("id");
		if(id==null){
			throw new OptimusException("获取参数失败");
		}
		resp.addForm("queryContactDetialPanel", spQySelectService.getContactQueryById(id));
	}
	
	/**
	 * 获取当前用户的user_id 获取用户的角色，然后返回到前台判断是否有权限  是否公示？
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/queryUserRolesByUserId")
	public void queryUserRolesByUserId(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		List<String> roleCodes  = spQySelectService.queryListUserRolesByUserId();
		if(roleCodes !=null && roleCodes.size() >0 && roleCodes.contains("ZHCX_SPQY_EXPORT")){
			System.out.println("success  ----"+"ZHCX_SPQY_EXPORT");
			resp.addAttr("msg", "success");
		}else{
			System.out.println("fail  ----"+"");
			resp.addAttr("msg", "fail");
		}
		//resp.addResponseBody(lstParams.toString());
	}
	
	/**
	 * 食品企业查询导出
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 * @throws IOException 
	 */
	@RequestMapping("/exportExcel")
	public void exportExcel(OptimusRequest req, OptimusResponse resp) throws OptimusException, IOException {
		String entname = req.getParameter("entname");
		String regorg = req.getParameter("regorg");
		String regno = req.getParameter("regno");
		String adminbrancode = req.getParameter("adminbrancode");
		String SpQymark = req.getParameter("SpQymark");
		String estdate_begin = req.getParameter("estdate_begin");
		String estdate_end = req.getParameter("estdate_end");
	 	Map<String,String> params =new HashMap<String,String>();
			params.put("entname", entname);
			params.put("regorg", regorg);
			params.put("regno", regno);
			params.put("adminbrancode", adminbrancode);
			params.put("SpQymark", SpQymark);
			params.put("estdate_begin", estdate_begin);
			params.put("estdate_end", estdate_end);
		List<Map> list = spQySelectService.exportExcel(params, req.getHttpRequest());
		// excel 导出部分
		Map<String,Object> sortedMap = new LinkedHashMap<String, Object>();
		sortedMap.put("regno", "统一社会信用代码/注册号");
		sortedMap.put("entname", "商事主体名称");
		sortedMap.put("enttypeCn", "商事主体类型");
		sortedMap.put("estdate", "成立日期");
		sortedMap.put("dom", "地址");
		sortedMap.put("regstateCn", "商事主体状态");
		
		
		Workbook xs=new SXSSFWorkbook(1000);
		   final Sheet sheet=xs.createSheet("未年报查询结果");
		   Row row=sheet.createRow((int)0);
		   sheet.createFreezePane( 0, 1, 0, 1 );
		   
		   sheet.setColumnWidth(0, 25*256);
		   sheet.setColumnWidth(1, 44*256);
		   sheet.setColumnWidth(2, 30*256);
		   sheet.setColumnWidth(3, 20*256);
		   sheet.setColumnWidth(4, 70*256);
		   sheet.setColumnWidth(5, 15*256);
		   
		   row.createCell((int)0).setCellValue("统一社会信用代码/注册号");
		   row.createCell((int)1).setCellValue("商事主体名称");
		   row.createCell((int)2).setCellValue("商事主体类型");
		   row.createCell((int)3).setCellValue("成立日期");
		   row.createCell((int)4).setCellValue("地址");
		   row.createCell((int)5).setCellValue("商事主体状态");
		   
		   for(int i=0;i<list.size();i++){
			   Row rowdata=sheet.createRow((int)(i+1));
			   Map map =list.get(i);
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

		   String downFileName = "食品企业查询结果" + dowmFileRight+ ".xlsx";

		   resp.getHttpResponse().setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(downFileName, "UTF-8"));
		
		  
		   OutputStream out=resp.getHttpResponse().getOutputStream();
		   xs.write(out);
		   out.flush();
		   out.close();
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
	
}