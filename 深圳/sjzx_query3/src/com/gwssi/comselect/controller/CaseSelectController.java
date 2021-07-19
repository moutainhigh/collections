package com.gwssi.comselect.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gwssi.comselect.model.CaseSelectQueryBo;
import com.gwssi.comselect.model.EntSelectQueryBo;
import com.gwssi.comselect.service.CaseSelectService;
import com.gwssi.comselect.service.ComSelectService;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.util.ExcelUtil;

@Controller
@RequestMapping("/caseselect")
public class CaseSelectController {

	@Autowired
	private CaseSelectService CaseSelectService;
	
	/**
	 * 跳转列表页面
	 */
	@RequestMapping("toPage")
	public void toPage(OptimusRequest req, OptimusResponse resp,CaseSelectQueryBo bo) throws OptimusException {
		HttpServletRequest request=req.getHttpRequest();
		
		String caseFiDate = request.getParameter("caseFiDate");
		String cloCaseDate = request.getParameter("cloCaseDate");
		
		bo.setCaseFiDate(caseFiDate);
		bo.setCloCaseDate(cloCaseDate);
		
		JSONObject json = (JSONObject) JSON.toJSON(bo);  
	       
		request.setAttribute("bo", json.toJSONString());
			
		resp.addPage("/page/comselect/com_caseQuery_list.jsp");//为List列表页面发送标识 用以识别列表页面发送哪种.do 2为案件查询 
	}
	
	/**
	 * list列表的查询.do方法
	 */
	@RequestMapping("toQuery")
	public void toTest(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String jsonstr=(String) req.getAttr("mydata");
		CaseSelectQueryBo bo = JSON.parseObject(jsonstr, CaseSelectQueryBo.class);
		resp.addGrid("gridpanel", CaseSelectService.queryPageQuery(bo,req.getHttpRequest()));
	}
	
	/**
	 * 获取代码集
	 */
	@ResponseBody
	@RequestMapping("code_value")
	public void getCode_value(OptimusRequest req, OptimusResponse resp) throws OptimusException{
		HttpServletRequest request=req.getHttpRequest();
		String type=request.getParameter("type");
		String param=request.getParameter("param");
		
		if(StringUtils.isEmpty(param)){
			resp.addTree(type ,CaseSelectService.queryCode_value(type));
		}else{
			resp.addTree(type ,CaseSelectService.queryCode_value(type,param));
		}
		
	}
	
	/**
	 * 导出excel结果集
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws OptimusException
	 */
	@RequestMapping("/exportExcel")
	public void ExcelOut(HttpServletRequest request,HttpServletResponse response) throws IOException, OptimusException{
		
			String jsonstr=request.getParameter("mydata");
			
			CaseSelectQueryBo bo = JSON.parseObject(jsonstr, CaseSelectQueryBo.class);
			List<Map> caseExcelData = CaseSelectService.queryCaseExcel(bo,request);
			String excelTitle = "案件查询结果";//excel标题
			
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
			ArrayList<String> code2StrKeys = new ArrayList<String>();
			code2StrKeys.add("partytype");
			
			//创建代码转字符串参数集合
			ArrayList<Map<String, String>> code2String = new ArrayList<Map<String,String>>();
			HashMap<String, String> a1 = new HashMap<String, String>();
			a1.put("0", "自然人");
			code2String.add(a1);
			HashMap<String, String> a2 = new HashMap<String, String>();
			a2.put("1", "法人或其他组织");
			code2String.add(a2);
			
			//导出excel
			ExcelUtil.ExcelOut(caseExcelData, excelTitle, displayInfo, rowDataKeys, excelFreezeParam,code2StrKeys,code2String, response);
	}
	
	
	private static String getDetail_datasourcekey(){
		Properties properties = ConfigManager.getProperties("optimus");
		
		String key= properties.getProperty("regDetail.datasourcekey");


		return key;
	}
}
