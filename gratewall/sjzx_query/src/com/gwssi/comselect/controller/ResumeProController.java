package com.gwssi.comselect.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gwssi.comselect.model.EntSelectQueryBo;
import com.gwssi.comselect.model.ResumeQueryBo;
import com.gwssi.comselect.service.ComSelectService;
import com.gwssi.comselect.service.ResumeProService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.util.ExcelUtil;

@Controller
@RequestMapping("/rusumePro")
public class ResumeProController extends BaseController{
		
	@Autowired
	private ResumeProService resumeProService;
	
	/**
	 *  解析json串 并查询结果呢
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("toQuery")
	public void toTest(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String jsonstr=(String) req.getAttr("mydata");
		ResumeQueryBo bo = JSON.parseObject(jsonstr, ResumeQueryBo.class);
		resp.addGrid("gridpanel", resumeProService.queryPageQuery(bo));
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
			resp.addTree(type ,resumeProService.queryCode_value(type));
		}else{
			resp.addTree(type ,resumeProService.queryCode_value(type,parm));
		}
		
	}
	
	
	/**
	 * 页面跳转
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("toPage")
	public void toPage(OptimusRequest req, OptimusResponse resp,ResumeQueryBo bo) throws OptimusException {
		
		HttpServletRequest request=req.getHttpRequest();
		String regTime =request.getParameter("regTime");
		bo.setRegTime(regTime);
		
	    JSONObject json = (JSONObject) JSON.toJSON(bo);  
	   
		request.setAttribute("bo", json.toJSONString());
		
		resp.addPage("/page/comselect/com_resumePro_list.jsp");
	}	
	
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws OptimusException
	 */
	@RequestMapping("/exportExcel")
	public void ExcelOut(HttpServletRequest request,HttpServletResponse response) throws IOException, OptimusException{
		
		String jsonstr=request.getParameter("mydata");
		long t1=new Date().getTime();
		ResumeQueryBo bo = JSON.parseObject(jsonstr, ResumeQueryBo.class);
		List<Map> list=	resumeProService.queryCaseExcel(bo);
		
		//excel标题名
		String excelTitle = "12315查询结果";
		//excel展示数据列名
		ArrayList<String> displayInfo = new ArrayList<String>();
		//keys
		ArrayList<String> rowDataKeys = new ArrayList<String>();
		
		displayInfo.add("登记编号");
		displayInfo.add("信息来源");
		displayInfo.add("登记部门");
		displayInfo.add("受理登记人");
		displayInfo.add("登记时间");
		
		rowDataKeys.add("regino");
		rowDataKeys.add("infoori");
		rowDataKeys.add("regdep");
		rowDataKeys.add("accregper");
		rowDataKeys.add("regtime");
		
		//冻结窗口
		int[] excelFreezeParam = {0,1,0,1};//第一行
		
		//导出excel
		ExcelUtil.ExcelOut(list, excelTitle, displayInfo, rowDataKeys, excelFreezeParam,null,null, response);
		
		long t2=new Date().getTime();
		System.out.println("写入excel共耗时："+(t2-t1));
	}
}
