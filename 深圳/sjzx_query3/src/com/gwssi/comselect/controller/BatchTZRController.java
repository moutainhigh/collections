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
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.comselect.service.BatchTZRService;
import com.gwssi.comselect.utils.ReadExcellUtils;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.util.ExcelUtil;

@Controller
@RequestMapping("/batchtzr")
public class BatchTZRController {

	@Autowired
	private BatchTZRService batchFddbrService;

	private Map params = new HashMap();
	
	
	
	public Map getParams() {
		return params;
	}

	public void setParams(Map params) {
		this.params = params;
	}
	
	
	

	/**
	 *  读取上传的excel文件 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unused", "deprecation" })
	@RequestMapping("/bathTZRUpload")
	public void show(OptimusRequest req, OptimusResponse resp)	throws OptimusException, Exception {
		Map maps = new HashMap();
		HttpServletRequest request = req.getHttpRequest();
		
		String flag = req.getParameter("flag"); //excel文档类型
		String realPath = request.getRealPath("/");
		// 定义上传的目录
		String dirPath = realPath + "/upload";
		
		File dirFile = new File(dirPath);
		// 自动创建上传的目录
		if (!dirFile.exists())
			dirFile.mkdirs();
		// 上传操作
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
						resp.addResponseBody("请选择文件");
						return;
					}
					String ext = ReadExcellUtils.getExt(item.getName());
					fileName = UUID.randomUUID().toString() + ext;
					// 上传文件的目录
					File savedFile = new File(dirPath, fileName);
					item.write(savedFile); // 保存文件到磁盘 目录之中
					maps = ReadExcellUtils.readExcel(dirPath + "/"+ fileName,flag);// 得到当前读到的excell内容
					this.setParams(maps);
					//List list = batchFddbrService.getFDDBRList(maps);
					savedFile.delete();
				}
			}
		}
		
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	@RequestMapping("/TZRList")
	public void getList(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		
		Map queryParam = this.getParams();//得到当前的文件法定代表人名字和身份证等相当信息
		
		
		
		
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
		
		
		
		
		//查询列表的值
		List list = batchFddbrService.getFDDBRList(queryParam);
		
		
		
		if(list!=null&&list.size()>0){
			resp.addGrid("LegalPensonQueryListGrid", list);
		}else{
			resp.addAttr("msg", "无数据");
		}
		
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/tzrdown")
    public void download(HttpServletRequest request,HttpServletResponse response) throws IOException, OptimusException {    
		String excelTitle = "投资人批量查询结果";//excel标题
		Map queryParam = this.getParams();//得到当前的文件法定代表人名字和身份证等相当信息
		List<Map> caseExcelData =  batchFddbrService.getFDDBRDownList(queryParam);
		System.out.println(queryParam);
		System.out.println(">>>>>>>>>>>>");
		System.out.println(caseExcelData);
		
		
		
		//展示列名
		ArrayList<String> displayInfo = new ArrayList<String>();
		displayInfo.add("登记编号");
		displayInfo.add("登记名称");
		displayInfo.add("案值");
		displayInfo.add("案件状态");
		displayInfo.add("当事人类型");
		displayInfo.add("当事人名称");
		
		//keys集合
		ArrayList<String> rowDataKeys = new ArrayList<String>();
		rowDataKeys.add("caseno");
		rowDataKeys.add("casename");
		rowDataKeys.add("caseval");
		rowDataKeys.add("casestate");
		rowDataKeys.add("partytype");
		rowDataKeys.add("unitname");
		
		//excel冻结窗口参数
		int[] excelFreezeParam = {0,1,0,1};//冻结第一行
		
		//需要将代码转出字符串的字段key名集合
//		ArrayList<String> code2StrKeys = new ArrayList<String>();
//		code2StrKeys.add("partytype");
		
		//创建代码转字符串参数集合
//		ArrayList<Map<String, String>> code2String = new ArrayList<Map<String,String>>();
//		HashMap<String, String> a1 = new HashMap<String, String>();
//		a1.put("0", "自然人");
//		code2String.add(a1);
//		HashMap<String, String> a2 = new HashMap<String, String>();
//		a2.put("1", "法人或其他组织");
//		code2String.add(a2);
		
		ExcelUtil.ExcelOut(caseExcelData, excelTitle, displayInfo, rowDataKeys, excelFreezeParam,null,null, response);
		
	}
    
    
    
    
    
    
    
    
    
    
    
    
    
    ///常用工具类

	public static boolean isEmpty(String str) {
		return null == str || str.length() == 0 || "".equals(str)
				|| str.matches("\\s*");
	}
		
}
