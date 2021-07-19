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

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.comselect.service.BlackEntSelectService;

@Controller
@RequestMapping("/BlackEntSelect")
public class BlackEntSelectController{

	@Resource
	private BlackEntSelectService blackEntSelectService;
	
	/**
	 * 黑名单地址企业列表
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/BlackEntQueryList")
	public void BlackEntQueryList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		Map<String, String> form = req.getForm("blackEntQueryListPanel");
		List<Map> lstParams= blackEntSelectService.getList(form);
		resp.addGrid("portalAppQueryListGrid",lstParams);
	}
	
	/**
	 * 黑名单地址企业详情
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/BlackEntQueryDetail")
	public void BlackEntQueryDetail(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String id = req.getParameter("id");
		resp.addForm("blackEntEditOrAddPannel", blackEntSelectService.getListDetail(id));
	}
	
	/**
	 * 黑名单地址企业更新
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/BlackEntUpdate")
	public void BlackEntUpdate(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String id = (String)req.getAttr("id");
		Map<String, String> form = req.getForm("blackEntEditOrAddPannel");
		blackEntSelectService.updateListDetail(form,id);
		resp.addAttr("del", "success");
	}
	
	/**
	 * 黑名单地址企业增加
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/BlackEntInsert")
	public void BlackEntInsert(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		Map<String, String> form = req.getForm("blackEntEditOrAddPannel");
		blackEntSelectService.updateListInsert(form);
		resp.addAttr("del", "success");
	}
	
	/**
	 * 黑名单地址企业删除
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/BlackEntDelete")
	public void BlackEntDelete(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String id = (String)req.getAttr("id");
		blackEntSelectService.updateListDelete(id);
		resp.addAttr("del", "success");
	}
	
	/**
	 * 白名单地址企业导出
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 * @throws IOException 
	 */
	@RequestMapping("/exportExcel")
	public void exportExcel(OptimusRequest req, OptimusResponse resp) throws OptimusException, IOException {
		String address = req.getParameter("address");
		String houseCode = req.getParameter("houseCode");
		List<Map> list = blackEntSelectService.exportExcel(address,houseCode);
		// excel 导出部分
		Map<String,Object> sortedMap = new LinkedHashMap<String, Object>();
		sortedMap.put("address", "地址名称");
		sortedMap.put("houseCode", "房屋编码");
		sortedMap.put("remark", "备注");
		sortedMap.put("createName", "创建人");
		sortedMap.put("updateName", "最后修改人");
		
		Workbook xs=new SXSSFWorkbook(1000);
		   final Sheet sheet=xs.createSheet("企业查询结果");
		   Row row=sheet.createRow((int)0);
		   sheet.createFreezePane( 0, 1, 0, 1 );
		   
		   sheet.setColumnWidth(0, 25*256);
		   sheet.setColumnWidth(1, 25*256);
		   sheet.setColumnWidth(2, 25*256);
		   sheet.setColumnWidth(3, 25*256);
		   sheet.setColumnWidth(4, 25*256);
		   
		   row.createCell((int)0).setCellValue("地址名称");
		   row.createCell((int)1).setCellValue("房屋编码");
		   row.createCell((int)2).setCellValue("备注");
		   row.createCell((int)3).setCellValue("创建人");
		   row.createCell((int)4).setCellValue("最后修改人");
		   
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

		   String downFileName = "地址黑名单查询结果" + dowmFileRight+ ".xlsx";

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
