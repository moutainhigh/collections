package com.gwssi.report.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.report.service.ReportDistributeService;

/**
 * @author wuyincheng ,Oct 14, 2016
 */
@Controller
@RequestMapping("/reportDistribute")
public class ReportDistributeController {
	
	private static  Logger log=Logger.getLogger(ReportDistributeController.class);

	@Autowired
	private ReportDistributeService service;
	

	@SuppressWarnings("rawtypes")
	@RequestMapping("/getInfosByReportId")
	public List<Map> getYannualReportPageInfo(OptimusRequest req, OptimusResponse res) {
		
		
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getAllUpperDept")
	public void getAllUpperDept(OptimusRequest req, OptimusResponse res){
		List<Map> list=service.getAllUpperDept();
		try {
			res.addGrid("grid", list, null);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getAllDept")
	public void getAllDept(OptimusRequest req, OptimusResponse res){
		String upperdept=req.getParameter("upperdept");
		List<Map> list=service.getAllDept(upperdept);
		try {
			res.addGrid("grid", list, null);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getReportDeptTree")
	public void getReportDeptTree(OptimusRequest req, OptimusResponse res){
		String reportId = req.getParameter("reportId");
		List list=service.getDeptsTree(reportId);
		res.addTree("funcTree", list);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("/updateDeptsTree")
	public void updateDeptsTree(OptimusRequest req, OptimusResponse res){
		String reportId = req.getParameter("reportId");
		String deptIds = req.getParameter("deptIds");
		service.updateDeptsTree(reportId, deptIds);
	}	
	
	
}
