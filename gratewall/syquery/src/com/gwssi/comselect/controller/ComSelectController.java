package com.gwssi.comselect.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;






import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gwssi.application.log.aspect.LogUtil;
import com.gwssi.application.model.SmSysIntegrationBO;
import com.gwssi.comselect.model.EntSelectQueryBo;
import com.gwssi.comselect.service.ComSelectAuthService;
import com.gwssi.comselect.service.ComSelectService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;
import com.gwssi.optimus.plugin.auth.service.AuthService;
import com.gwssi.optimus.util.StringUtil;
import com.gwssi.util.ExcelUtil;

import edu.emory.mathcs.backport.java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequestMapping("/comselect")
public class ComSelectController extends BaseController{
	
	@Autowired
	private ComSelectService comSelectService;
	@Autowired
	private ComSelectAuthService comSelectAuthService;
	
	/**
	 *  解析json串 并查询结果呢
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("toQuery")
	public void toTest(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String jsonstr = "";
		String cntFlag =req.getHttpRequest().getParameter("cntFlag");
		EntSelectQueryBo bo = null;

		if(!"1".equals(cntFlag)){
			jsonstr = (String) req.getAttr("mydata");
			bo = JSON.parseObject(jsonstr, EntSelectQueryBo.class);
			Map<String, Object> queryPageMap = comSelectService.queryPageQuery(bo,req.getHttpRequest());
			List<Map> data = (List<Map>)queryPageMap.get("data");
			resp.addGrid("gridpanel", data);
		}else{
			jsonstr = req.getHttpRequest().getParameter("mydata");
			bo = JSON.parseObject(jsonstr, EntSelectQueryBo.class);
			Map<String, Object> countMap = comSelectService.queryPageQuery(bo,null);
			String object = (String)countMap.get("count");
			resp.addResponseBody(object);
		}
	}
	
	
	
	/**
	 * 导出结果集
	 *  		测试方法 测试导出excel
	 * @param request
	 * @param model
	 * @param response
	 * @throws IOException
	 * @throws OptimusException 
	 */
	@RequestMapping("/exportExcel")
	public void ExcelOut(HttpServletRequest request,HttpServletResponse response) throws IOException, OptimusException{
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		String id = user.getUserId();
		String username  = user.getUserName();
		
		
		
		long s1=new Date().getTime();
		Map<String,Object> sortedMap = new LinkedHashMap<String, Object>();
		String jsonstr=request.getParameter("mydata");
		String select = request.getParameter("select");
		JSONObject aa = JSON.parseObject(select);
		final JSONObject res = aa.getJSONObject("data");
		if(!"".equals(res.get("entname"))){
			sortedMap.put("entname", res.get("entname"));
		}
		if(!"".equals(res.get("regno"))){
			sortedMap.put("regno", res.get("regno"));
		}
		if(!"".equals(res.get("enttype"))){
			sortedMap.put("enttype", res.get("enttype"));
		}
		if(!"".equals(res.get("dom"))){
			sortedMap.put("dom", res.get("dom"));
		}
		if(!"".equals(res.get("reccap"))){
			sortedMap.put("reccap", res.get("reccap"));
		}
		if(!"".equals(res.get("regcapcur"))){
			sortedMap.put("regcapcur", res.get("regcapcur"));
		}
		if(!"".equals(res.get("regstate"))){
			sortedMap.put("regstate", res.get("regstate"));
		}
		if(!"".equals(res.get("industryphy"))){
			sortedMap.put("industryphy", res.get("industryphy"));
		}
		if(!"".equals(res.get("industryco"))){
			sortedMap.put("industryco", res.get("industryco"));
		}
		if(!"".equals(res.get("regorg"))){
			sortedMap.put("regorg", res.get("regorg"));
		}
		if(!"".equals(res.get("opscope"))){
			sortedMap.put("opscope", res.get("opscope"));
		}
		if(!"".equals(res.get("estdate"))){
			sortedMap.put("estdate", res.get("estdate"));
		}
		if(!"".equals(res.get("apprdate"))){
			sortedMap.put("apprdate", res.get("apprdate"));
		}
		if(!"".equals(res.get("lerep"))){
			sortedMap.put("lerep", res.get("lerep"));
		}
		if(!"".equals(res.get("opfyears"))){
			sortedMap.put("opfyears", res.get("opfyears"));
		}
		
		EntSelectQueryBo bo = JSON.parseObject(jsonstr, EntSelectQueryBo.class);
		long t1=new Date().getTime();
		List<Map> list=	comSelectService.queryExcelEntQuery(bo,res,id,request);//需要重写  不能一次返回所有的，只返回前5000条
		long t2=new Date().getTime();
		System.out.println("执行SQL时间："+(t2-t1));
		//创建excel冻结窗口参数
		int[] excelFreezeParam = {0,1,0,1};
		
		//ExcelUtil.ExcelOut(list, excelTitle, displayInfo, rowDataKeys,excelFreezeParam,null,null, response);
		Workbook xs=new SXSSFWorkbook(1000);
		   final Sheet sheet=xs.createSheet("企业查询结果");
		   Row row=sheet.createRow((int)0);
		   sheet.createFreezePane( 0, 1, 0, 1 );//冻结窗口
		   //row.createCell((short)0).setCellValue("基本信息id");
//		   sheet.trackAllColumnsForAutoSizing();
		   
		   //sheet.autoSizeColumn((short)0);
		   int numCell = 0;
		   for (Map.Entry<String, Object> entry : sortedMap.entrySet()) {			   
			   if(!"".equals((String)entry.getValue())){
				   row.createCell((int)numCell).setCellValue((String)entry.getValue());
				   numCell++;
			   }
		   }

		   long t3=new Date().getTime();
		   for(int i=0;i<list.size();i++){
			   Row rowdata=sheet.createRow((int)(i+1));
			   Map map =list.get(i);
			   int numRow = 0;
			   for (Map.Entry<String, Object> entry : sortedMap.entrySet()) {
				   if(!"".equals((String)entry.getValue())){
					   rowdata.createCell((short)numRow).setCellValue(this.getMapValue(entry.getKey(), map));
					   numRow++;
				   }
			   }
		   }
//		   for(int i=0;i<16;i++){
//			   sheet.autoSizeColumn((short)i,true); //调整第i列宽度
//		   }
		   long t4=new Date().getTime();
		   System.out.println("循环时间"+(t4-t3));
		   
		   //多线程创建XSSFRow对象 以及填充数据
		  /*final AtomicInteger count = new AtomicInteger(1);
		   ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
		   Iterator<Map> it = list.iterator();
		   while(it.hasNext()){
				final Map map = it.next();
				
				fixedThreadPool.execute(new Runnable() { 
					@Override
					public void run() {
						XSSFRow rowdata=sheet.createRow(count.get());
						int numRow = 0;
						for (Map.Entry<String, Object> entry : res.entrySet()) {
						   if(!"".equals((String)entry.getValue())){
							   rowdata.createCell((short)numRow).setCellValue(getMapValue(entry.getKey(), map));
							   numRow++;
						   }
						}
					}
				});
		   }*/
		   
		   
		   
		   
			//long t2=new Date().getTime();
			//System.out.println("写入excel共耗时："+(t2-t1));
		   //以下为excel导出部分
		   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HHmmss");//设置日期格式
		   String dowmFileRight =  df.format(new Date());
		   
		   response.reset();
		   response.setContentType("application/x-download;charset=GBK");

		   String downFileName = "企业查询结果" + dowmFileRight+ ".xlsx";

		   response.setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(downFileName, "UTF-8"));
		
		  
		   OutputStream out=response.getOutputStream();
		   xs.write(out);
		   out.flush();
		   out.close();
		   long s2=new Date().getTime();
		   System.out.println("执行SQL时间："+(s2-s1));
	}
	
	/**
	 * 处理map中的空值
	 * @param key
	 * @param map
	 * @return
	 */
	private String getMapValue(String key,Map map){
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
	
	
	/**
	 * 页面跳转
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("topange")
	public void toPage(OptimusRequest req, OptimusResponse resp,EntSelectQueryBo bo) throws OptimusException {
	HttpServletRequest request=req.getHttpRequest();
	/*		EntSelectQueryBo bo =new EntSelectQueryBo();//查询条件bo类
		String estdate_start=request.getParameter("estdate_start");
		String estdate_end =request.getParameter("estdate_end");
		String apprdate_start =request.getParameter("apprdate_start");
		String apprdate_end =request.getParameter("apprdate_end");
		String reccap_start =request.getParameter("reccap_start");
		String reccap_end =request.getParameter("reccap_end");
		String regcapcur =request.getParameter("regcapcur");
		String industryphy =request.getParameter("industryphy");
		
		String[] entname_term=request.getParameterValues("entname_term");
		String[] entname =request.getParameterValues("entname");
		
		String[] opscope_term =request.getParameterValues("opscope_term");
		String[] opscope =request.getParameterValues("opscope");
		
		String[] dom_term= request.getParameterValues("dom_term");
		String[] dom=request.getParameterValues("dom");
		
		String[] regorg=request.getParameterValues("regorg");
		String[] adminbrancode =request.getParameterValues("adminbrancode");
		String[] gongzuowangge =request.getParameterValues("gongzuowangge");
		String[] danyuanwangge =request.getParameterValues("danyuanwangge");
		
		String enttype_radio =request.getParameter("enttype_radio");//主体类型是大类还是小类
		String [] enttype =request.getParameterValues("enttype");
		
		String[] regstate = request.getParameterValues("regstate");
		
		String year =request.getParameter("year");
		
		bo.setEstdate_start(estdate_start);
		bo.setEstdate_end(estdate_end);
		bo.setApprdate_start(apprdate_start);
		bo.setApprdate_end(apprdate_end);
		bo.setReccap_start(reccap_start);
		bo.setReccap_end(reccap_end);
		bo.setRegcapcur(regcapcur);
		bo.setIndustryphy(industryphy);
		
		bo.setEntname_term(entname_term);
		bo.setEntname(entname);
		bo.setOpscope_term(opscope_term);
		bo.setOpscope(opscope);
		bo.setDom_term(dom_term);
		bo.setDom(dom);
		
		bo.setRegorg(regorg);
		bo.setAdminbrancode(adminbrancode);
		bo.setGongzuowangge(gongzuowangge);
		bo.setDanyuanwangge(danyuanwangge);
		bo.setEnttype_radio(enttype_radio);
		bo.setRegstate(regstate);
		bo.setYear(year);*/

		String estdate_start =request.getParameter("estdate_start");
		String estdate_end =request.getParameter("estdate_end");
		String apprdate_start =request.getParameter("apprdate_start");
		String apprdate_end =request.getParameter("apprdate_end");
		bo.setEstdate_start(estdate_start);
		bo.setEstdate_end(estdate_end);
		bo.setApprdate_start(apprdate_start);
		bo.setApprdate_end(apprdate_end);
		
//		Map map =comSelectAuthService.findRegorg();
//		if(map.get("codeindexCode")==null){
//			return;
//		}else{
//			String regno =(String) map.get("codeindexCode");
//			
//			String isadmin=(String)map.get("istoporg");
//			if("Y".equalsIgnoreCase(isadmin)){
//				
//			}else{
//				String[] str={regno};
//				
//				bo.setRegorg(str);	
//			}
//			//
//		}
        JSONObject json = (JSONObject) JSON.toJSON(bo);  
        
        String boJson = json.toJSONString();       
		request.setAttribute("bo", boJson);
		
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		if(user != null){
			String userId = user.getUserId();	
			List<Map> roleIdListByLoginName = new AuthService().getRoleIdByUsersRole(userId);
			
			ArrayList<String> roleCodes = new ArrayList<String>();
			for(Map roleMap : roleIdListByLoginName){
				String role_code = (String)roleMap.get("roleCode");
				roleCodes.add(role_code);
			}
			session.setAttribute("roleIdList", roleCodes);//将该用户角色List放入session
			
			if(roleCodes.contains("EXCELROLE")){
				req.getHttpRequest().setAttribute("excelFlag", "1");//1为可以看见导出按钮的用户 且不控制导出数量
				resp.addPage("/page/comselect/com_query_list.jsp");
				return;
			}else if(roleCodes.contains("ZHCX_SHCX")){
				req.getHttpRequest().setAttribute("excelFlag", "2");//1为可以看见导出按钮的用户 但是控制导出数量5000条
				resp.addPage("/page/comselect/com_query_list.jsp");
				return;
			}
		}
		
		resp.addPage("/page/comselect/com_query_list.jsp");
	}
	
	/**
	 * 获取代码集
	 * @param req
	 * @param resp
	 * @return 
	 * @throws OptimusException 
	 */
	@ResponseBody
	@RequestMapping("code_value")
	public void getCode_value(OptimusRequest req, OptimusResponse resp) throws OptimusException{
		HttpServletRequest request=req.getHttpRequest();
		String type=request.getParameter("type");
		String parm=request.getParameter("parm");
		if(StringUtils.isEmpty(parm)){
			resp.addTree(type ,comSelectService.queryCode_value(type));
		}else{
			resp.addTree(type ,comSelectService.queryCode_value(type,parm));
		}
		
	}
	
	/**
	 * Json串转码
	 * @param req
	 * @param resp
	 * @return 
	 * @throws OptimusException 
	 */
	@ResponseBody
	@RequestMapping("codetype")
	public EntSelectQueryBo codetype(OptimusRequest req, OptimusResponse resp) throws OptimusException{
		HttpServletRequest request=req.getHttpRequest();
		String jsonstr=request.getParameter("data");
		EntSelectQueryBo bo = JSON.parseObject(jsonstr, EntSelectQueryBo.class);
		String[] regorg = new String[50];
		String[] enttype = new String[1000];
		String[] regstate = new String[10];
		List<Map<String, Object>> a1 = comSelectService.queryCode_value("regcapcur", bo.getRegcapcur());
		if(a1.size()!= 0&&a1.size()>0){
			bo.setRegcapcur((String)a1.get(0).get("text"));
		}
		List<Map<String, Object>> a2 = comSelectService.queryCode_value("industryphy", bo.getIndustryphy());
		if(a2.size()!= 0&&a2.size()>0){
			bo.setIndustryphy((String)a2.get(0).get("text"));
		}
		if(bo.getRegorg() != null){
		for(int i=0; i<bo.getRegorg().length;i++){
			List<Map<String, Object>> a3 = comSelectService.queryCode_value("regorg", bo.getRegorg()[i]);
			if(a3!=null&&a3.size()>0){
				regorg[i] = (String)a3.get(0).get("text");
			}
		}
		}
		bo.setRegorg(regorg);
		if(bo.getEnttype() != null){
		for(int i=0; i<bo.getEnttype().length;i++){
			List<Map<String, Object>> a4 = comSelectService.queryCode_value("enttype_code", bo.getEnttype()[i]);
			if(a4!=null&&a4.size()>0){
				enttype[i] = (String)a4.get(0).get("text");
			}
		}
		bo.setEnttype(enttype);
		}
		
		if(bo.getRegstate() != null){
		for(int i=0; i<bo.getRegstate().length;i++){
			List<Map<String, Object>> a5 = comSelectService.queryCode_value("regstate", bo.getRegstate()[i]);
			if(a5!=null&&a5.size()>0){
				regstate[i] = (String)a5.get(0).get("text");
			}
		}
		bo.setRegstate(regstate);
		}
		return bo;
	}
	
	
	
	/**
	 * 综合查询 查询
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("query")
	public void getQueryPage(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
/*		HttpServletRequest request=req.getHttpRequest();
		EntSelectQueryBo bo =new EntSelectQueryBo();//查询条件bo类
		String estdate_start=request.getParameter("estdate_start");
		String estdate_end =request.getParameter("estdate_end");
		String apprdate_start =request.getParameter("apprdate_start");
		String apprdate_end =request.getParameter("apprdate_end");
		String reccap_start =request.getParameter("reccap_start");
		String reccap_end =request.getParameter("reccap_end");
		String regcapcur =request.getParameter("regcapcur");
		String industryphy =request.getParameter("industryphy");
		
		String[] entname_term=request.getParameterValues("entname_term");
		String[] entname =request.getParameterValues("entname");
		
		String[] opscope_term =request.getParameterValues("opscope_term");
		String[] opscope =request.getParameterValues("opscope");
		
		String[] dom_term= request.getParameterValues("dom_term");
		String[] dom=request.getParameterValues("dom");
		
		String[] regorg=request.getParameterValues("regorg");
		String[] adminbrancode =request.getParameterValues("adminbrancode");
		String[] gongzuowangge =request.getParameterValues("gongzuowangge");
		String[] danyuanwangge =request.getParameterValues("danyuanwangge");
		
		String enttype_radio =request.getParameter("enttype_radio");//主体类型是大类还是小类
		String [] enttype =request.getParameterValues("enttype");
		
		String[] regstate = request.getParameterValues("regstate");
		
		String year =request.getParameter("year");
		
		bo.setEstdate_start(estdate_start);
		bo.setEstdate_end(estdate_end);
		bo.setApprdate_start(apprdate_start);
		bo.setApprdate_end(apprdate_end);
		bo.setReccap_start(reccap_start);
		bo.setReccap_end(reccap_end);
		bo.setRegcapcur(regcapcur);
		bo.setIndustryphy(industryphy);
		
		bo.setEntname_term(entname_term);
		bo.setEntname(entname);
		bo.setOpscope_term(opscope_term);
		bo.setOpscope(opscope);
		bo.setDom_term(dom_term);
		bo.setDom(dom);
		
		bo.setRegorg(regorg);
		bo.setAdminbrancode(adminbrancode);
		bo.setGongzuowangge(gongzuowangge);
		bo.setDanyuanwangge(danyuanwangge);
		bo.setEnttype_radio(enttype_radio);
		bo.setRegstate(regstate);
		bo.setYear(year);
		
		System.out.println(bo.toString());*/
		

		/*bo.setEstdate_start(estdate_start);
		bo.setEstdate_end(estdate_end);*/
	}
	
	@RequestMapping("queryEntytype")
	public void queryEntType(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String radiovalue =(String) req.getAttr("radiovalue");
		 List<Map<String, Object>> entypetree =null;
		 entypetree  = comSelectService.queryEntTypeTree(radiovalue);
        Map rootNode = new HashMap();
        rootNode.put("name", "全部");
        rootNode.put("id", "all");
        rootNode.put("open", true);
        entypetree.add(rootNode);
        resp.addTree("enttpeTree", entypetree);

		
	}
	
	@RequestMapping("queryCount")
	public void queryCount(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String  count = comSelectService.queryCount();
		resp.addResponseBody(count);
	}
	
}	