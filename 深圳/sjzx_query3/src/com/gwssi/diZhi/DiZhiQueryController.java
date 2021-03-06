package com.gwssi.diZhi;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.diZhi.serivce.DiZhiQuerySerivce;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

@Controller
@RequestMapping("diZhiQuery")
public class DiZhiQueryController {
	
	@Autowired
	private  DiZhiQuerySerivce diZhiQueryService;
	
	
	@RequestMapping("/queryUserRolesByUserId")
	public void queryUserRolesByUserId(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		List<String> roleCodes  = diZhiQueryService.queryListUserRolesByUserId();
		if(roleCodes !=null && roleCodes.size() >0 && roleCodes.contains("ZHCX_DZBG_EXPORT")){
			System.out.println("success  ----"+"ZHCX_DZBG_EXPORT");
			resp.addAttr("msg", "success");
		}else{
			System.out.println("fail  ----"+"");
			resp.addAttr("msg", "fail");
		}
		//resp.addResponseBody(lstParams.toString());
	}


	@ResponseBody
	@RequestMapping("code_value")
	public void getCode_value(OptimusRequest req, OptimusResponse resp) throws OptimusException{
		resp.addTree(null ,diZhiQueryService.queryCode_value());
		
	}

	
	//
	@RequestMapping("/queryDiZhiList")
	public void caseQueryList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		Map<String, String> form = req.getForm("SpQyQueryListPanel");
		String flag = req.getParameter("flag");
		if (flag != null) {
			String count = diZhiQueryService.getListCount(form);
			resp.addAttr("count", count);
		} else {
			List<Map> lstParams = diZhiQueryService.getList(form, req.getHttpRequest());
			// System.out.println(lstParams.toString());
			resp.addGrid("SpQyQueryListGrid", lstParams);
		}
	}
	
	
	
		///????????????
	/*	@RequestMapping("/exportExcel")
		public void exportExcel(OptimusRequest req, OptimusResponse resp) throws OptimusException, IOException {
			
			
			String entname = req.getParameter("entname");
			if(entname==null) {
				entname = (String) req.getAttr("entname");
			}
			String unif = req.getParameter("unif");
			if(unif==null) {
				unif = (String) req.getAttr("unif");
			}
			String bgQianSelect = req.getParameter("bgQianSelect");
			if(bgQianSelect==null) {
				bgQianSelect = (String) req.getAttr("bgQianSelect");
			}
			String altBefore = req.getParameter("altBefore");
			if(altBefore==null) {
				altBefore = (String) req.getAttr("altBefore");
			}
			String bgHouSelect = req.getParameter("bgHouSelect");
			if(bgHouSelect==null) {
				bgHouSelect = (String) req.getAttr("bgHouSelect");
			}
			String altAfter = req.getParameter("altAfter");
			if(altAfter==null) {
				altAfter = (String) req.getAttr("altAfter");
			}
			
			String estdate_begin = req.getParameter("estdate_begin");
			if(estdate_begin==null) {
				estdate_begin = (String) req.getAttr("estdate_begin");
			}
			String estdate_end = req.getParameter("estdate_end");
			if(estdate_end==null) {
				estdate_end = (String) req.getAttr("estdate_end");
			}
			
			
			String bgdate_begin = req.getParameter("bgdate_begin");
			if(bgdate_begin==null) {
				bgdate_begin = (String) req.getAttr("bgdate_begin");
			}
			
			String bgdate_end = req.getParameter("bgdate_end");
			if(bgdate_end==null) {
				bgdate_end = (String) req.getAttr("bgdate_end");
			}
			
			
			String isZaiRuYiChang = req.getParameter("isZaiRuYiChang");
			if(isZaiRuYiChang==null) {
				isZaiRuYiChang = (String) req.getAttr("isZaiRuYiChang");
			}
			
			String yiChangType = req.getParameter("yiChangType");
			if(yiChangType==null) {
				yiChangType = (String) req.getAttr("yiChangType");
			}
			
			
			
			Map<String,String> params =new HashMap<String,String>();
			params.put("entname", entname);
			params.put("unif", unif);
			params.put("bgQianSelect", bgQianSelect);
			params.put("altBefore", altBefore);
			params.put("bgHouSelect", bgHouSelect);
			params.put("altAfter", altAfter);
			params.put("estdate_begin", estdate_begin);
			params.put("estdate_end", estdate_end);
			params.put("bgdate_begin", bgdate_begin);
			params.put("bgdate_end", bgdate_end);
			params.put("isZaiRuYiChang", isZaiRuYiChang);
			params.put("yiChangType", yiChangType);
			
			
		    String i  = diZhiQueryService.exportExcelCount(params, req.getHttpRequest());
		    System.out.println("======???????????? " +i );
		    resp.addAttr("msg", i);
		}*/
		
		
		
		//?????????????????????excel?????????????????????????????????
		@RequestMapping("/exportExcelDowns")
		public void createExcelDownloadFile(OptimusRequest req, OptimusResponse resp) throws OptimusException, IOException {
			String entname = req.getParameter("entname");
			if(entname==null) {
				entname = (String) req.getAttr("entname");
			}
			String unif = req.getParameter("unif");
			if(unif==null) {
				unif = (String) req.getAttr("unif");
			}
			String bgQianSelect = req.getParameter("bgQianSelect");
			if(bgQianSelect==null) {
				bgQianSelect = (String) req.getAttr("bgQianSelect");
			}
			String altBefore = req.getParameter("altBefore");
			if(altBefore==null) {
				altBefore = (String) req.getAttr("altBefore");
			}
			String bgHouSelect = req.getParameter("bgHouSelect");
			if(bgHouSelect==null) {
				bgHouSelect = (String) req.getAttr("bgHouSelect");
			}
			String altAfter = req.getParameter("altAfter");
			if(altAfter==null) {
				altAfter = (String) req.getAttr("altAfter");
			}
			
			String estdate_begin = req.getParameter("estdate_begin");
			if(estdate_begin==null) {
				estdate_begin = (String) req.getAttr("estdate_begin");
			}
			String estdate_end = req.getParameter("estdate_end");
			if(estdate_end==null) {
				estdate_end = (String) req.getAttr("estdate_end");
			}
			
			
			String bgdate_begin = req.getParameter("bgdate_begin");
			if(bgdate_begin==null) {
				bgdate_begin = (String) req.getAttr("bgdate_begin");
			}
			
			String bgdate_end = req.getParameter("bgdate_end");
			if(bgdate_end==null) {
				bgdate_end = (String) req.getAttr("bgdate_end");
			}
			
			
			String isZaiRuYiChang = req.getParameter("isZaiRuYiChang");
			if(isZaiRuYiChang==null) {
				isZaiRuYiChang = (String) req.getAttr("isZaiRuYiChang");
			}
			
			String yiChangType = req.getParameter("yiChangType");
			if(yiChangType==null) {
				yiChangType = (String) req.getAttr("yiChangType");
			}
			
			
			
			Map<String,String> params =new HashMap<String,String>();
			params.put("entname", entname);
			params.put("unif", unif);
			params.put("bgQianSelect", bgQianSelect);
			params.put("altBefore", altBefore);
			params.put("bgHouSelect", bgHouSelect);
			params.put("altAfter", altAfter);
			params.put("estdate_begin", estdate_begin);
			params.put("estdate_end", estdate_end);
			params.put("bgdate_begin", bgdate_begin);
			params.put("bgdate_end", bgdate_end);
			params.put("isZaiRuYiChang", isZaiRuYiChang);
			params.put("yiChangType", yiChangType);
			
			
			
			
		   List<Map> list = diZhiQueryService.exportExcel(params, req.getHttpRequest());
			// excel ????????????
			Map<String,Object> sortedMap = new LinkedHashMap<String, Object>();
			sortedMap.put("unif", "????????????????????????");
			sortedMap.put("entname", "??????????????????");
			sortedMap.put("tel", "??????");
			sortedMap.put("persname", "?????????????????????????????????");
			sortedMap.put("dom", "?????????????????????");
			sortedMap.put("msType", "????????????????????????");
			sortedMap.put("oldCodeName", "????????????");
			
			
			Workbook xs=new SXSSFWorkbook(1000);
			   final Sheet sheet=xs.createSheet("??????????????????????????????");
			   Row row=sheet.createRow((int)0);
			   sheet.createFreezePane( 0, 1, 0, 1 );
			   
			   sheet.setColumnWidth(0, 25*256);
			   sheet.setColumnWidth(1, 44*256);
			   sheet.setColumnWidth(2, 30*256);
			   sheet.setColumnWidth(3, 15*256);
			   sheet.setColumnWidth(4, 20*256);
			   sheet.setColumnWidth(5, 20*256);
			   sheet.setColumnWidth(6, 70*256);
			   
			   row.createCell((int)0).setCellValue("????????????????????????");
			   row.createCell((int)1).setCellValue("??????????????????");
			   row.createCell((int)3).setCellValue("??????");
			   row.createCell((int)2).setCellValue("?????????????????????????????????");
			   row.createCell((int)4).setCellValue("?????????????????????");
			   row.createCell((int)5).setCellValue("????????????????????????");
			   row.createCell((int)6).setCellValue("????????????");
			   
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
			   
			   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HHmmss");//??????????????????
			   String dowmFileRight =  df.format(new Date());
			   
			   resp.getHttpResponse().reset();
			   resp.getHttpResponse().setContentType("application/x-download;charset=GBK");

			   String downFileName = "????????????????????????????????????" + dowmFileRight+ ".xlsx";

			   resp.getHttpResponse().setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(downFileName, "UTF-8"));
			
			  
			   OutputStream out=resp.getHttpResponse().getOutputStream();
			   xs.write(out);
			   out.flush();
			   out.close();
		}
		
		
		
		/**
		 * ??????map????????????
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
