package com.gwssi.comselect.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gwssi.comselect.model.EntSelectQueryBo;
import com.gwssi.comselect.service.ComSelectService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

@Controller
@RequestMapping("/comselect")
public class ComSelectController extends BaseController{
	
	@Autowired
	private ComSelectService comSelectService;
	
	/**
	 *  解析json串 并查询结果呢
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("toQuery")
	public void toTest(OptimusRequest req, OptimusResponse resp) throws OptimusException {
	//	String jsonstr=req.getHttpRequest().getParameter("mydata");
		String jsonstr=(String) req.getAttr("mydata");
		//System.out.println("后台json："+jsonstr);
		EntSelectQueryBo bo = JSON.parseObject(jsonstr, EntSelectQueryBo.class);
		resp.addGrid("gridpanel", comSelectService.queryPageQuery(bo));
		/*System.out.println("to_test-------"+bo);*/
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
			String jsonstr=request.getParameter("mydata");
//		String jsonstr=(String) req.getAttr("mydata");
//		System.out.println("后台json："+jsonstr);
		long t1=new Date().getTime();
		EntSelectQueryBo bo = JSON.parseObject(jsonstr, EntSelectQueryBo.class);
		List<Map> list=	comSelectService.queryExcelEntQuery(bo);//需要重写  不能一次返回所有的，只返回前5000条
		XSSFWorkbook xs=new XSSFWorkbook();
		   XSSFSheet sheet=xs.createSheet("企业查询结果");
		   XSSFRow row=sheet.createRow((short)0);
		   sheet.createFreezePane( 0, 1, 0, 1 );
		   row.createCell((short)0).setCellValue("基本信息id");   
		   row.createCell((short)1).setCellValue("注册号");
		   row.createCell((short)4).setCellValue("成立日期");
		   row.createCell((short)5).setCellValue("核准日期");
		   row.createCell((short)6).setCellValue("认缴资本总额");
		   row.createCell((short)7).setCellValue("币种");
		   row.createCell((short)8).setCellValue("行业类别");
		   row.createCell((short)2).setCellValue("企业名称");
		   row.createCell((short)15).setCellValue("经营范围");
		  
		   row.createCell((short)10).setCellValue("地址");
		   row.createCell((short)11).setCellValue("管辖区域");
		   row.createCell((short)12).setCellValue("所属监管所");
		   row.createCell((short)3).setCellValue("主体类型");
		   row.createCell((short)13).setCellValue("注册状态");
		   row.createCell((short)14).setCellValue("年报年底");
		   row.createCell((short)9).setCellValue("行业代码");
		  
		   String is_spec;
		   String is_city;
		   String is_rewa;
		   String is_wo;
		   String is_valied;
		   for(int i=0;i<list.size();i++){
			   XSSFRow rowdata=sheet.createRow((short)(i+1));
			   Map map =list.get(i);
			   rowdata.createCell((short)0).setCellValue(this.getMapValue("pripid", map));   
			   rowdata.createCell((short)1).setCellValue(this.getMapValue("regno", map));
			   rowdata.createCell((short)4).setCellValue(this.getMapValue("estdate", map));
			   rowdata.createCell((short)5).setCellValue(this.getMapValue("apprdate", map));
			   rowdata.createCell((short)6).setCellValue(this.getMapValue("reccap", map));
			   rowdata.createCell((short)7).setCellValue(this.getMapValue("regcapcur", map));
			   rowdata.createCell((short)8).setCellValue(this.getMapValue("industryphy", map));
			   rowdata.createCell((short)2).setCellValue(this.getMapValue("entname", map));
			   rowdata.createCell((short)15).setCellValue(this.getMapValue("opscope", map));
			  
			   rowdata.createCell((short)10).setCellValue(this.getMapValue("dom", map));
			   rowdata.createCell((short)11).setCellValue(this.getMapValue("regorg", map));
			   rowdata.createCell((short)12).setCellValue(this.getMapValue("adminbrancode", map));
			   rowdata.createCell((short)3).setCellValue(this.getMapValue("enttype", map));
			   rowdata.createCell((short)13).setCellValue(this.getMapValue("regstate", map));
			   rowdata.createCell((short)14).setCellValue(this.getMapValue("year", map));
			   rowdata.createCell((short)9).setCellValue(this.getMapValue("industryco", map));
		   }
	/*	  for(int i=0;i<16;i++){
	       sheet.autoSizeColumn((short)i); //调整第i列宽度
		  }*/
			long t2=new Date().getTime();
			System.out.println("写入excel共耗时："+(t2-t1));
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

        JSONObject json = (JSONObject) JSON.toJSON(bo);  
       
		request.setAttribute("bo", json.toJSONString());
		
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
}	