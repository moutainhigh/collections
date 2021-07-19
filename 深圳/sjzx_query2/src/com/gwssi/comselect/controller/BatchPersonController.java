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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.application.log.aspect.LogUtil;
import com.gwssi.comselect.service.BatchPersonService;
import com.gwssi.comselect.utils.ReadExcellUtils;
import com.gwssi.comselect.utils.ReadTxtUtil;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.util.ExcelUtil;
import com.gwssi.util.PeopleExcelUtil;

/**
 * 人员查询
 * @author ye
 * @param <E>
 */
@Controller
@RequestMapping("/readTxt")
public class BatchPersonController<E> extends BaseService {
	private static  Logger log=Logger.getLogger(BatchPersonController.class);
	private List<?> params = new ArrayList<E>();
	
	
	@Autowired
	private BatchPersonService bpService;
	
	public List<?> getParams() {
		return params;
	}

	public void setParams(List<?> params) {
		this.params = params;
	}



	/**
	 *  读取上传的txt文件 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unused", "deprecation" })
	@RequestMapping("/text")
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
						log.info("请选择文件");
						return;
					}
					String ext = ReadExcellUtils.getExt(item.getName());
					fileName = UUID.randomUUID().toString() + ext;
					// 上传文件的目录
					File savedFile = new File(dirPath, fileName);
					item.write(savedFile); // 保存文件到磁盘 目录之中
					List queryParams = ReadTxtUtil.readTxtFile(dirPath + "/"+ fileName);// 得到当前读到的txt内容
					request.getSession().setAttribute("queryParams", queryParams);
					//this.setParams(queryParams);//放入内存缓存之中
					//去数据据中查询
					resp.addAttr("msg", "上传成功");
					//System.out.println("====> " + queryParams);
					log.info("上传成功");
					log.info("当前用户上传的文件解析结果： " + queryParams);
					savedFile.delete();
				}
			}
		}
		
	}
	
	
	
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/list")
	public void getPersonList(OptimusRequest req, OptimusResponse resp)	throws OptimusException, Exception {
		 List params = (List) req.getHttpRequest().getSession().getAttribute("queryParams");
		 List list  =	bpService.getPersonListMap(params,req.getHttpRequest());
		 req.getHttpRequest().getSession().setAttribute("totalList",list);
	     //this.setListTotal(list);
	     resp.addGrid("PersonQueryListGrid", list);
	}
	
	
	@RequestMapping("/getStatus")
	public void getQueryPersonStatus(OptimusRequest req, OptimusResponse resp)	throws OptimusException, Exception {
		 resp.addAttr("infos", "0");
	}
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/personDown")
    public void download(OptimusRequest req, OptimusResponse resp) throws IOException, OptimusException{
		String excelTitle = "人员查询结果";//excel标题
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
	/*	List params = this.getParams();//
		
		System.out.println(params);
		List<Map> caseExcelData =  bpService.getPersonListMap(params);*/
		
		//List  lists = this.getListTotal();
		
		 List lists = (List) req.getHttpRequest().getSession().getAttribute("totalList");
		
		//展示列名
		ArrayList<String> displayInfo = new ArrayList<String>();
		displayInfo.add("");
		displayInfo.add("身份证号码");
		displayInfo.add("统一社会信用代码");
		displayInfo.add("企业名称");
		displayInfo.add("姓名");
		displayInfo.add("职务");
		displayInfo.add("企业类型");
		
		displayInfo.add("注册资本");
		displayInfo.add("出资额及比例（或职务）");
		displayInfo.add("成立日期");
		displayInfo.add("企业状态");
		displayInfo.add("注吊销时间");
		displayInfo.add("登记机关");
		displayInfo.add("地址");
		
		
		//keys集合
		ArrayList<String> rowDataKeys = new ArrayList<String>();
		rowDataKeys.add("");
		rowDataKeys.add("cerno");
		rowDataKeys.add("unifsocicrediden");
		rowDataKeys.add("entname");
		rowDataKeys.add("persname");
		rowDataKeys.add("perflag");
		
		rowDataKeys.add("enttype");
		rowDataKeys.add("regcap");
		rowDataKeys.add("memo");
		rowDataKeys.add("estdate");
		rowDataKeys.add("entstatus");
		rowDataKeys.add("apprdate");
		rowDataKeys.add("regorg");
		rowDataKeys.add("dom");
		
		//excel冻结窗口参数
		int[] excelFreezeParam = {0,1,0,1};//冻结第一行
		
		
		if(lists!=null&&lists.size()>0){
			LogUtil.insertLog("人员查询_导出", "", "", req.getHttpRequest(), dao);
			//PeopleExcelUtil.PeopleExcelOut(lists, excelTitle, displayInfo, rowDataKeys, excelFreezeParam,null,null, resp.getHttpResponse());
			PeopleExcelUtil.PeopleExcelOut(lists, excelTitle, displayInfo, rowDataKeys, excelFreezeParam,null,null, resp.getHttpResponse());
		}else{
			resp.addResponseBody("当前暂没有数据导出，请先进行查询再导出！");
		}
		
	}
	
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/PersonQueryList")
	public void PersonQueryList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		Map<String, String> form = req.getForm("PersonQueryListPanel");
		String flag = req.getParameter("flag");
		if(flag !=null){
			String count= bpService.getPersonListCount(form);
			resp.addAttr("count", count);
		}else {
			List<Map> lstParams= bpService.getPersonList(form,req.getHttpRequest());
			//System.out.println(lstParams.toString());
			resp.addGrid("LegalPensonQueryListGrid",lstParams);
			
			req.getHttpRequest().getSession().setAttribute("totalList",lstParams);
		}
	}
	
	/**
	 * 未年报导出角色
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/QueryBatchPerson")
	public void QueryBatchPerson(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		List<String> roleCodes  = bpService.queryListUserRolesByUserId();
		log.info("人员批量查询角色:"+roleCodes.toString());
		if(roleCodes !=null && roleCodes.size() >0 && roleCodes.contains("ZHCX_PER")){
			System.out.println("success  ----"+"ZHCX_PER");
			resp.addAttr("msg", "success");
		}else{
			System.out.println("fail  ----"+"");
			resp.addAttr("msg", "fail");
		}
		//resp.addResponseBody(lstParams.toString());
	}
	/*
	 * 历史人员变更查询
	 */
	@RequestMapping("/queryBGDetail")
	public void queryBGDetail(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String id = req.getParameter("id");
		String cerno = req.getParameter("cerno");
		String flag = req.getParameter("flag");
		String bgtype = req.getParameter("bgtype");

		if("1".equals(bgtype)){ //变更前
			List<Map> res = bpService.getBGDetall(id, cerno, flag, bgtype);
			resp.addGrid("jbxxGridBefore", res, null);
		}
		else if("2".equals(bgtype)){ //变更后
			List<Map> res = bpService.getBGDetall(id, cerno, flag, bgtype);
			resp.addGrid("jbxxGridAfter", res, null);
		}else{
			resp.addGrid("jbxxGridBefore", null, null);
			resp.addGrid("jbxxGridAfter", null, null);
		}
		
	}
	
	
	
	public static boolean isEmpty(String str) {
		return null == str || str.length() == 0 || "".equals(str)
				|| str.matches("\\s*");
	}
}
