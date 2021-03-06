package com.gwssi.comselect.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.gwssi.comselect.service.BatchFDDBRService;
import com.gwssi.comselect.utils.ReadExcellUtils;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.util.ExcelUtil;

import org.apache.commons.io.FileUtils;  
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;  
import org.springframework.http.HttpHeaders;  
import org.springframework.http.HttpStatus;  
import org.springframework.http.MediaType;  
import org.springframework.http.ResponseEntity;  
import org.springframework.stereotype.Component;  
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/batch")
public class BatchFDDBRController {
	private static  Logger log  =  Logger.getLogger(BatchFDDBRController.class);
	
	
	@Autowired
	private BatchFDDBRService batchFddbrService;

	private Map params = new HashMap();
	
	
	
	public Map getParams() {
		return params;
	}

	public void setParams(Map params) {
		this.params = params;
	}
	
	
	

	/**
	 *  ???????????????excel?????? 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unused", "deprecation" })
	@RequestMapping("/bathFDDBRUpload")
	public void show(OptimusRequest req, OptimusResponse resp)	throws OptimusException, Exception {
		Map maps = new HashMap();
		HttpServletRequest request = req.getHttpRequest();
		
		String flag = req.getParameter("flag"); //excel????????????
		String realPath = request.getRealPath("/");
		// ?????????????????????
		String dirPath = realPath + "/upload";
		
		File dirFile = new File(dirPath);
		// ???????????????????????????
		if (!dirFile.exists())
			dirFile.mkdirs();
		// ????????????
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		String fileName = null;
		List contents = null;
		List results = null;
		List items = upload.parseRequest(request); // 3name=null name=null
		if (null != items) {
			Iterator itr = items.iterator();
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				if (item.isFormField()) {
					continue;
				} else {
					if(isEmpty(item.getName())){
						resp.addResponseBody("???????????????");
						log.info("???????????????");
						return;
					}
					String ext = ReadExcellUtils.getExt(item.getName());
					fileName = UUID.randomUUID().toString() + ext;
					// ?????????????????????
					File savedFile = new File(dirPath, fileName);
					item.write(savedFile); // ????????????????????? ????????????
					maps = ReadExcellUtils.readExcel(dirPath + "/"+ fileName,flag);// ?????????????????????excell??????
					this.setParams(maps);
					log.info("??????????????????");
					//List list = batchFddbrService.getFDDBRList(maps);
					savedFile.delete();
				}
			}
		}
		
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	@RequestMapping("/FDDBRList")
	public void getList(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		
		Map queryParam = this.getParams();//?????????????????????????????????????????????????????????????????????
		
		
		
		
		/*HttpServletRequest request = req.getHttpRequest();
		Map<String,String> pageMap = new HashMap<String, String>();
		Map<String,Object> result = new HashMap<String,Object>();
		String postData = request.getParameter("postData");
		Map map = JSON.parseObject(postData);
		List list1 = (List)map.get("data");
		Map map0 = (Map)list1.get(0);
		Map map1 = (Map)list1.get(2);
		String pageSize = String.valueOf((Integer)map0.get("data"));
		String pageNo = String.valueOf((Integer)map1.get("data"));
		pageMap.put("pageSize",pageSize);
		pageMap.put("pageNo",pageNo);*/
		
		
		
		
		//??????????????????
		//List list = batchFddbrService.getFDDBRList(queryParam,req);
		
	/*	if(list!=null&&list.size()>0){
			resp.addGrid("LegalPensonQueryListGrid", list);
		}else{
			resp.addAttr("msg", "?????????");
		}*/
		
	}
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/fdddown")
    public void download(HttpServletRequest request,HttpServletResponse response) throws IOException, OptimusException{
		String excelTitle = "?????????????????????????????????";//excel??????
		
		
		Map queryParam = this.getParams();//?????????????????????????????????????????????????????????????????????
		List<Map> caseExcelData =  batchFddbrService.getFDDBRDownList(queryParam);
		
		
		
		
		//????????????
		ArrayList<String> displayInfo = new ArrayList<String>();
		displayInfo.add("????????????????????????/?????????");
		displayInfo.add("????????????");
		displayInfo.add("???????????????");
		displayInfo.add("?????????");
		displayInfo.add("??????????????????");
		displayInfo.add("????????????");
		displayInfo.add("????????????");
		
		
		//keys??????
		ArrayList<String> rowDataKeys = new ArrayList<String>();
		rowDataKeys.add("regno");
		rowDataKeys.add("entname");
		rowDataKeys.add("lerep");
		rowDataKeys.add("cerno");
		rowDataKeys.add("regstate");
		rowDataKeys.add("estdate");
		rowDataKeys.add("apprdate");
		
		//excel??????????????????
		int[] excelFreezeParam = {0,1,0,1};//???????????????
		
		//???????????????????????????????????????key?????????
//		ArrayList<String> code2StrKeys = new ArrayList<String>();
//		code2StrKeys.add("partytype");
		
		//????????????????????????????????????
//		ArrayList<Map<String, String>> code2String = new ArrayList<Map<String,String>>();
//		HashMap<String, String> a1 = new HashMap<String, String>();
//		a1.put("0", "?????????");
//		code2String.add(a1);
//		HashMap<String, String> a2 = new HashMap<String, String>();
//		a2.put("1", "?????????????????????");
//		code2String.add(a2);
		ExcelUtil.ExcelOut(caseExcelData, excelTitle, displayInfo, rowDataKeys, excelFreezeParam,null,null, response);
		
	}
    
    
    
    
    
    
    
    
    
    
    
    
    
    ///???????????????

	public static boolean isEmpty(String str) {
		return null == str || str.length() == 0 || "".equals(str)
				|| str.matches("\\s*");
	}
		
}
