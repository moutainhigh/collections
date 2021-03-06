package com.gwssi.taiwai;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
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

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.taiwai.serivce.TaiWaiGtService;

@Controller
@RequestMapping("taiwan")
public class TaiWaiGtController {

	
	@Autowired
	private TaiWaiGtService taiWaiGtService;
	
	
	@RequestMapping("/queryTaiWanGtList")
	public void caseQueryList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		Map<String, String> form = req.getForm("YRQueryListPanel");
		String flag = req.getParameter("flag");
		String year = form.get("year");
		String month = form.get("jiduDate");
		
		
		
		String s_str = "01-01";
		String e_str = "03-31";
		
		String begTime = null;
		String endTime = null;
		
		if(month.equals("1")) {
			begTime = year+"-"+s_str;
			endTime = year +"-"+ e_str;
		}else if(month.equals("2")) {
			s_str = "04-01";
			e_str = "06-30";
			begTime = year+"-"+s_str;
			endTime = year +"-"+ e_str;
		}else if(month.equals("3")) {
			s_str = "07-01";
			e_str = "09-30";
			begTime = year+"-"+s_str;
			endTime = year +"-"+ e_str;
		}else {
			s_str = "10-01";
			e_str = "12-30";
			begTime = year+"-"+s_str;
			endTime = year +"-"+ e_str;
		}
		
		if("all".equals(month)){
			begTime  =  year+"-"+"01-01";
			endTime = year +"-"+ "12-31";
		}
		
		if (flag != null) {
			String count = taiWaiGtService.getListCount(begTime,endTime);
			resp.addAttr("count", count);
		} else {
			List<Map> lstParams = taiWaiGtService.getList(begTime,endTime);
			// System.out.println(lstParams.toString());
			List  changeTypeList = typechage(lstParams);
			req.getHttpRequest().getSession().setAttribute("taiwaiGtSession",changeTypeList );
			resp.addGrid("YRQueryListPanel", lstParams);
		}
	} 
	
	
	
	@RequestMapping("/exportExcel")
	public void exportExcel(OptimusRequest req, OptimusResponse resp) throws OptimusException, IOException {

		List<Map> list  = (List<Map>) req.getHttpRequest().getSession().getAttribute("taiwaiGtSession");
		
		//System.out.println(list.toString());
		// excel ????????????
		Map<String,Object> sortedMap = new LinkedHashMap<String, Object>();
		sortedMap.put("traname", "?????????????????????");
		sortedMap.put("oploc", "????????????");
		sortedMap.put("compform_cn", "????????????");
		sortedMap.put("fundam", "????????????");
		sortedMap.put("opscope", "?????????????????????");
		sortedMap.put("regno", "???????????????");
		sortedMap.put("uniscid", "????????????????????????");
		sortedMap.put("estdate", "??????????????????");
		sortedMap.put("regstate_cn", "????????????");
		sortedMap.put("empnum", "????????????");
		sortedMap.put("name", "???????????????(?????????)??????");
		sortedMap.put("dom", "??????(?????????)????????????(??????)");
		sortedMap.put("tel", "????????????");
		sortedMap.put("cerno", "??????(????????????)");
		
		Workbook xs=new SXSSFWorkbook(1000);
		   final Sheet sheet=xs.createSheet("?????????????????????????????????");
		   Row row=sheet.createRow((int)0);
		   sheet.createFreezePane( 0, 1, 0, 1 );
		   
		   sheet.setColumnWidth(0, 35*256);
		   sheet.setColumnWidth(1, 25*256);
		   sheet.setColumnWidth(2, 10*256);
		   sheet.setColumnWidth(3, 15*256);
		   sheet.setColumnWidth(4, 15*256);
		   sheet.setColumnWidth(5, 30*256);
		   sheet.setColumnWidth(6, 30*256);
		   sheet.setColumnWidth(7, 20*256);
		   sheet.setColumnWidth(8, 10*256);
		   sheet.setColumnWidth(9, 10*256);
		   sheet.setColumnWidth(10,30*256);
		   sheet.setColumnWidth(11, 50*256);
		   sheet.setColumnWidth(12, 40*256);
		   sheet.setColumnWidth(13, 40*256);
		   row.createCell((int)0).setCellValue("?????????????????????");
		   row.createCell((int)1).setCellValue("????????????");
		   row.createCell((int)2).setCellValue("????????????");
		   row.createCell((int)3).setCellValue("????????????");
		   row.createCell((int)4).setCellValue("?????????????????????");
		   row.createCell((int)5).setCellValue("???????????????");
		   row.createCell((int)6).setCellValue("????????????????????????");
		   row.createCell((int)7).setCellValue("??????????????????");
		   row.createCell((int)8).setCellValue("????????????");
		   row.createCell((int)9).setCellValue("????????????");
		   row.createCell((int)10).setCellValue("???????????????(?????????)??????");
		   row.createCell((int)11).setCellValue("??????(?????????)????????????(??????)");
		   row.createCell((int)12).setCellValue("????????????");
		   row.createCell((int)13).setCellValue("??????(????????????)");
		   
		   if(list!=null&&list.size()>0) {
			   for(int i=0;i<list.size();i++){
				   Row rowdata=sheet.createRow((int)(i+1));
				   Map map =  list.get(i);
				   int numRow = 0;
				   for (Map.Entry<String, Object> entry : sortedMap.entrySet()) {
					   if(!"".equals((String)entry.getValue())){
						   rowdata.createCell((short)numRow).setCellValue(getMapValue(entry.getKey(), map));
						   numRow++;
					   }
				   }
			   }
		   }
		   
		   
		   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HHmmss");//??????????????????
		   String dowmFileRight =  df.format(new Date());
		   
		   resp.getHttpResponse().reset();
		   resp.getHttpResponse().setContentType("application/x-download;charset=GBK");

		   String downFileName = "?????????????????????????????????" + dowmFileRight+ ".xlsx";

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
	
	/*
	 * ????????????
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Map> typechage(List<Map> list){
		List<Map> changtype =new ArrayList<Map>();
		for(Map<String,Object> map1:list){
			
			Map<String,Object> newMap= new HashMap<String,Object>();
			for(String s :map1.keySet()){
				Object obj=map1.get(s);
				
				if (obj!=null&&(obj.getClass()==GregorianCalendar.class)){
					GregorianCalendar gcal =(GregorianCalendar)obj;
					String format = "yyyy-MM-dd";
					SimpleDateFormat formatter = new SimpleDateFormat(format);
					newMap.put(s,  formatter.format(gcal.getTime()).toString());
				}else{
					newMap.put(s, map1.get(s));
				}
			}
			changtype.add(newMap);
		}
		
		return  changtype;
	}
	
}
