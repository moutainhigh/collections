package com.gwssi.comselect.controller;


import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.comselect.service.FoodLicSelectService;

@Controller
@RequestMapping("/FoodLicController")
public class FoodLicController{

	@Resource
	private FoodLicSelectService foodLicSelectService;
	/**
	 * 投资人查询列表
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/FoodLicQueryList")
	public void FoodLicQueryList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		Map<String, String> form = req.getForm("FoodLicQueryListPanel");
		List<Map> lstParams= foodLicSelectService.getList(form,req.getHttpRequest());
		resp.addGrid("FoodLicQueryListGrid",lstParams);

	}
	
	
	/*
	 * EXCEL导出
	 */
	@RequestMapping("/exportExcel")
	public void exportExcel(OptimusRequest req, OptimusResponse resp) throws OptimusException, IOException {
		String entname = req.getParameter("entname");
		String licno = req.getParameter("licno");
		String supunitbysup = req.getParameter("supunitbysup");
		String supdepbysup = req.getParameter("supdepbysup");
		String newflag = req.getParameter("newflag");
		String lictype = req.getParameter("lictype");
		String street = req.getParameter("street");
		String ztyt = req.getParameter("ztyt");
		String jyxm = req.getParameter("jyxm");
		
		List<Map> list = foodLicSelectService.exportExcel(entname,licno,supunitbysup,supdepbysup,newflag,lictype,street,ztyt,jyxm,req.getHttpRequest());
		System.out.println(list.toString());
		// excel 导出部分
		Map<String,Object> sortedMap = new LinkedHashMap<String, Object>();
		sortedMap.put("entname", "单位名称");
		sortedMap.put("licno", "许可证号");
		sortedMap.put("frname", "法定代表人（负责人）");
		sortedMap.put("validDate", "有效期限");
		sortedMap.put("lictypeCn", "许可证类型");
		sortedMap.put("supunitbysupCn", "管辖区域");
		sortedMap.put("supdepbysupCn", "所属工商所");
		sortedMap.put("mainFormatName", "主体业态");
		sortedMap.put("engageProject", "经营项目");
		sortedMap.put("dom", "地址");
		
		Workbook xs=new SXSSFWorkbook(1000);
		   final Sheet sheet=xs.createSheet("企业查询结果");
		   Row row=sheet.createRow((int)0);
		   sheet.createFreezePane( 0, 1, 0, 1 );
		   
		   sheet.setColumnWidth(0, 35*256);
		   sheet.setColumnWidth(1, 25*256);
		   sheet.setColumnWidth(2, 10*256);
		   sheet.setColumnWidth(3, 15*256);
		   sheet.setColumnWidth(4, 15*256);
		   sheet.setColumnWidth(5, 10*256);
		   sheet.setColumnWidth(6, 10*256);
		   sheet.setColumnWidth(7, 40*256);
		   sheet.setColumnWidth(8, 60*256);
		   sheet.setColumnWidth(9, 90*256);
		   
		   row.createCell((int)0).setCellValue("单位名称");
		   row.createCell((int)1).setCellValue("许可证号");
		   row.createCell((int)2).setCellValue("法定代表人（负责人）");
		   row.createCell((int)3).setCellValue("有效期限");
		   row.createCell((int)4).setCellValue("许可证类型");
		   row.createCell((int)5).setCellValue("管辖区域");
		   row.createCell((int)6).setCellValue("所属工商所");
		   row.createCell((int)7).setCellValue("主体业态");
		   row.createCell((int)8).setCellValue("经营项目");
		   row.createCell((int)9).setCellValue("地址");
		   
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

		   String downFileName = "食品许可证查询结果" + dowmFileRight+ ".xlsx";

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
