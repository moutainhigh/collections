//package com.gwssi.common.rodimus.report.web;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.gwssi.common.rodimus.report.service.ReportManagerService;
//import com.gwssi.framework.common.result.Result;
//
//
//@Controller
//@RequestMapping("/report")
//public class ReportManagerController {
//
//	@Autowired
//	ReportManagerService reportManagerService; 
//	
//	protected static final String RESULT = "RESULT";
//	
//	@RequestMapping("/queryReportList")
//	public void queryReportList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		Map<String,Object> map = getParams(request);
//		Result list = reportManagerService.queryReportList(10,1,map,null);
//		request.setAttribute(RESULT, list);
////		request.getRequestDispatcher("/page/report/report_list.jsp").forward(request,response);
//	}
//	
//	/**
//	 * 从request中获取查询参数。
//	 * 
//	 * @param request
//	 * @return
//	 */
//	public static Map<String, Object> getParams(HttpServletRequest request){
//		Map<String, Object> params=new HashMap<String, Object>();
//		// 查询参数
//		String reportName = request.getParameter("reportName");
//		params.put("reportName", reportName);
//		request.setAttribute("reportName", reportName);
//		String reportCode = request.getParameter("reportCode");
//		params.put("reportCode", reportCode);
//		request.setAttribute("reportCode", reportCode);
//		String reportRemark = request.getParameter("reportRemark");
//		params.put("reportRemark", reportRemark);
//		request.setAttribute("reportRemark", reportRemark);
//		// 页面通过隐藏表单域传递reportCategory参数，可以只显示某一类报表
//		String reportCategory = request.getParameter("reportCategory");
//		params.put("reportCategory", reportCategory);
//		request.setAttribute("reportCategory", reportCategory);
//		// 只显示启用的报表
//		params.put("flag", "1"); 
//		return params;
//	}
//	
//}
