package com.gwssi.trs.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.trs.Conts;
import com.gwssi.trs.PageDataUtil;

import com.gwssi.trs.service.CaseQueryService;


@Controller
@RequestMapping("/casequery")
public class CaseQueryController {
		
	private static Logger log = Logger.getLogger(CaseQueryController.class);
	
	@Autowired
	private CaseQueryService caseQueryService;
	  
	
	/**
	 * 案件移送信息 
	 */
	@RequestMapping("/queryanjianyisongxxdata")
	public void queryanjianyisongxxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String casetranid= req.getParameter("casetranid");
		res.addForm( "formpanel", caseQueryService.queryanjianyisongxx(casetranid));
	
	}
	
	@RequestMapping("/queryanjianyisongxxform")
	public void queryanjianyisongxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("casetranid", req.getParameter("casetranid"));
		res.addPage("/page/caseQuery/common/anjianyisongxx.jsp");
		
	}
	
	
	/**
	 * 案件案源信息
	 */
	@RequestMapping("/queryanyuanxxdata")
	public void queryanyuanxxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String casesrcid= req.getParameter("casesrcid");
		res.addForm( "formpanel", caseQueryService.queryanyuanxx(casesrcid));
	
	}
	
	@RequestMapping("/queryanyuanxxform")
	public void queryanyuanxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("casesrcid", req.getParameter("casesrcid"));
		res.addPage("/page/caseQuery/common/anyuanxx.jsp");
		
	}
	
	/**
	 * 案件当事人信息 
	 */
	
	@RequestMapping("/querydangshirenxxdata")
	public void querynzwzrenyuanxxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String casepartyid= req.getParameter("casepartyid");
		res.addForm( "formpanel", caseQueryService.querydangshirenxx(casepartyid));
	
	}
	
	@RequestMapping("/querydangshirenxxform")
	public void querydangshirenxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("casepartyid", req.getParameter("casepartyid"));
		res.addPage("/page/caseQuery/common/dangshirenxx.jsp");
		
	}
	
	
	/**
	 * 案件违法行为及处罚信息
	 */
	
	@RequestMapping("/queryweifaxxdata")
	public void queryweifaxxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String illegactid= req.getParameter("illegactid");
		res.addForm( "formpanel", caseQueryService.queryweifaxx(illegactid));
	
	}
	
	@RequestMapping("/queryweifaxxform")
	public void queryweifaxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("illegactid", req.getParameter("illegactid"));
		res.addPage("/page/caseQuery/common/weifaxx.jsp");
		
	}
		
}
	