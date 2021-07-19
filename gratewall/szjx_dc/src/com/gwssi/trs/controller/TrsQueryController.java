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

import com.gwssi.trs.service.TrsQueryService;
import com.gwssi.trs.service.TrsService;

@Controller
@RequestMapping("/trsquery")
public class TrsQueryController {
		
	private static  Logger log=Logger.getLogger(TrsQueryController.class);
	
	@Autowired
	private TrsQueryService trsQueryService;
	  
	
	/*
	 * 内资外资隶属信息 
	 */
	@RequestMapping("/querynzwzlishuxxdata")
	public void querynzwzlishuxxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String brpripid= req.getParameter("brpripid");
		res.addForm( "formpanel", trsQueryService.querynzwzlishuxx(brpripid));
	
	}
	
	@RequestMapping("/querynzwzlishuxxform")
	public void querynzwzlishuxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("brpripid", req.getParameter("brpripid"));
		res.addPage("/page/regQuery/nzwz/nzwz_lishuxx.jsp");
		
	}
	
	
	/*
	 * 内资外资出资信息
	 */
	@RequestMapping("/querynzwzchuzixxdata")
	public void querynzwzchuzixxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String invid= req.getParameter("invid");
		res.addForm( "formpanel", trsQueryService.querynzwzchuzixx(invid));
	
	}
	
	@RequestMapping("/querynzwzchuzixxform")
	public void querynzwzchuzixxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("invid", req.getParameter("invid"));
		res.addPage("/page/regQuery/nzwz/nzwz_chuzixx.jsp");
		
	}
	
	/*
	 * 内资外资人员信息 
	 */
	
	@RequestMapping("/querynzwzrenyuanxxdata")
	public void querynzwzrenyuanxxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String personid= req.getParameter("personid");
		res.addForm( "formpanel", trsQueryService.querynzwzrenyuanxx(personid));
	
	}
	
	@RequestMapping("/querynzwzrenyuanxxform")
	public void querynzwzrenyuanxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("personid", req.getParameter("personid"));
		res.addPage("/page/regQuery/nzwz/nzwz_renyuanxx.jsp");
		
	}
	
	
	/*
	 * 内资外资股权冻结信息 
	 */
	
	@RequestMapping("/querynzwzgqdongjiexxdata")
	public void querynzwzgqdongjiexxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String froid= req.getParameter("froid");
		res.addForm( "formpanel", trsQueryService.querynzwzgqdongjiexx(froid));
	
	}
	
	@RequestMapping("/querynzwzgqdongjiexxform")
	public void querynzwzgqdongjiexxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("froid", req.getParameter("froid"));
		res.addPage("/page/regQuery/nzwz/nzwz_gq_dongjiexx.jsp");
		
	}
	
	
	/*
	 * 内资外资股权出质信息 
	 */
	
	@RequestMapping("/querynzwzgqchuzhixxdata")
	public void querynzwzgqchuzhixxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String imporgid= req.getParameter("imporgid");
		res.addForm( "formpanel", trsQueryService.querynzwzgqchuzhixx(imporgid));
	
	}
	
	@RequestMapping("/querynzwzgqchuzhixxform")
	public void querynzwzgqchuzhixxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("imporgid", req.getParameter("imporgid"));
		res.addPage("/page/regQuery/nzwz/nzwz_gq_chuzhixx.jsp");
		
	}
	

	/*
	 * 内资外资注销信息
	 */
	
	@RequestMapping("/querynzwzzhuxiaoxxdata")
	public void querynzwzzhuxiaoxxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {

		String pripid= req.getParameter("pripid");
		res.addForm( "formpanel", trsQueryService.querynzwzzhuxiaoxx(pripid));
	
	}
	
	
	@RequestMapping("/querynzwzzhuxiaoxxform")
	public void querynzwzzhuxiaoxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("pripid", req.getParameter("pripid"));
		res.addPage("/page/regQuery/nzwz/nzwz_zhuxiaoxx.jsp");
		
	}
	
	
	/*
	 * 内资外资吊销信息
	 */
	
	@RequestMapping("/querynzwzdiaoxiaoxxdata")
	public void querynzwzdiaoxiaoxxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String pripid= req.getParameter("pripid");
		res.addForm( "formpanel", trsQueryService.querynzwzdiaoxiaoxx(pripid));
	
	}
	
	@RequestMapping("/querynzwzdiaoxiaoxxform")
	public void querynzwzdiaoxiaoxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("pripid", req.getParameter("pripid"));
		res.addPage("/page/regQuery/nzwz/nzwz_diaoxiaoxx.jsp");
		
	}
	
	
	/*
	 * 内资外资许可信息
	 */
	@RequestMapping("/querynzwzxukexxdata")
	public void querynzwzxukexxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {

		String licid= req.getParameter("licid");
		res.addForm( "formpanel", trsQueryService.querynzwzxukexx(licid));
		
	}
	
	@RequestMapping("/querynzwzxukexxform")
	public void querynzwzxukexxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("licid", req.getParameter("licid"));
		res.addPage("/page/regQuery/nzwz/nzwz_xukexx.jsp");
		
	}
	
	
	/*
	 * 内资外资迁移信息
	 */
	@RequestMapping("/querynzwzqianyixxdata")
	public void querynzwzqianyixxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {

		String id= req.getParameter("id");
		res.addForm( "formpanel", trsQueryService.querynzwzqianyixx(id));
		
	}
	
	@RequestMapping("/querynzwzqianyixxform")
	public void querynzwzqianyixxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("id", req.getParameter("id"));
		res.addPage("/page/regQuery/nzwz/nzwz_qianyixx.jsp");
		
	}
	
	
	/*
	 * 内资外资清算信息
	 */
	@RequestMapping("/querynzwzqingsuanxxdata")
	public void querynzwzqingsuanxxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {

		String liid= req.getParameter("liid");
		res.addForm( "formpanel", trsQueryService.querynzwzqingsuanxx(liid));
		
	}
	
	@RequestMapping("/querynzwzqingsuanxxform")
	public void querynzwzqingsuanxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("liid", req.getParameter("liid"));
		res.addPage("/page/regQuery/nzwz/nzwz_qingsuanxx.jsp");
		
	}
	
	
	/*
	 * 内资外资财务负责信息
	 */
	
	@RequestMapping("/querynzwzcaiwufuzexxdata")
	public void querynzwzcaiwufuzexxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String fpid= req.getParameter("fpid");
		res.addForm( "formpanel", trsQueryService.querynzwzcaiwufuzexx(fpid));
	
	}
	
	@RequestMapping("/querynzwzcaiwufuzexxform")
	public void querynzwzcaiwufuzexxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("fpid", req.getParameter("fpid"));
		res.addPage("/page/regQuery/nzwz/nzwz_caiwufuzexx.jsp");
		
	}
	
	/*
	 * 内资外资联络信息
	 */
	
	@RequestMapping("/querynzwzlianluoxxdata")
	public void querynzwzlianluoxxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String lmid= req.getParameter("lmid");
		res.addForm( "formpanel", trsQueryService.querynzwzlianluoxx(lmid));
	
	}
	
	@RequestMapping("/querynzwzlianluoxxform")
	public void querynzwzlianluoxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("lmid", req.getParameter("lmid"));
		res.addPage("/page/regQuery/nzwz/nzwz_lianluoxx.jsp");
		
	}
	
	
	/*
	 * 集团成员信息
	 */
	
	@RequestMapping("/queryjituanchengyuanxxdata")
	public void queryjituanchengyuanxxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String memid= req.getParameter("memid");
		res.addForm( "formpanel", trsQueryService.queryjituanchengyuanxx(memid));
	
	}
	
	@RequestMapping("/queryjituanchengyuanxxform")
	public void queryjituanchengyuanxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("memid", req.getParameter("memid"));
		res.addPage("/page/regQuery/jituan/jituan_chengyuanxx.jsp");
		
	}
	
	
	/*
	 * 集团变更信息
	 */
	
	@RequestMapping("/queryjituanbiangengxxdata")
	public void queryjituanbiangengxxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String altid= req.getParameter("altid");
		res.addForm( "formpanel", trsQueryService.queryjituanbiangengxx(altid));
	
	}
	
	@RequestMapping("/queryjituanbiangengxxform")
	public void queryjituanbiangengxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("altid", req.getParameter("altid"));
		res.addPage("/page/regQuery/jituan/jituan_biangengxx.jsp");
		
	}
	
	
	/*
	 * 集团注销信息
	 */
	
	@RequestMapping("/queryjituanzhuxiaoxxdata")
	public void queryjituanzhuxiaoxxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String pripid= req.getParameter("pripid");
		res.addForm( "formpanel", trsQueryService.queryjituanzhuxiaoxx(pripid));
	
	}
	
	@RequestMapping("/queryjituanzhuxiaoxxform")
	public void queryjituanzhuxiaoxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("pripid", req.getParameter("pripid"));
		res.addPage("/page/regQuery/jituan/jituan_zhuxiaoxx.jsp");
		
	}
	
	
	/*
	 * 个体经营信息
	 */
	
	@RequestMapping("/querygetijinyinxxdata")
	public void querygetijinyinxxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String personid= req.getParameter("personid");
		res.addForm( "formpanel", trsQueryService.querygetijinyinxx(personid));
	
	}
	
	@RequestMapping("/querygetijinyinxxform")
	public void querygetijinyinxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("personid", req.getParameter("personid"));
		res.addPage("/page/regQuery/geti/geti_jinyin.jsp");
		
	}
	
	
	/*
	 * 个体许可信息
	 */
	
	@RequestMapping("/querygetixukexxdata")
	public void querygetixukexxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String licid= req.getParameter("licid");
		res.addForm( "formpanel", trsQueryService.querygetixukexx(licid));
	
	}
	
	@RequestMapping("/querygetixukexxform")
	public void querygetixukexxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("licid", req.getParameter("licid"));
		res.addPage("/page/regQuery/geti/geti_xukexx.jsp");
		
	}
	
	
	/*
	 * 个体变更信息
	 */
	
	@RequestMapping("/querygetibiangengxxdata")
	public void querygetibiangengxxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String altid= req.getParameter("altid");
		res.addForm( "formpanel", trsQueryService.querygetibiangengxx(altid));
	
	}
	
	@RequestMapping("/querygetibiangengxxform")
	public void querygetibiangengxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("altid", req.getParameter("altid"));
		res.addPage("/page/regQuery/geti/geti_biangengxx.jsp");
		
	}
	
	
	/*
	 * 个体注销信息
	 */
	
	@RequestMapping("/querygetizhuxiaoxxdata")
	public void querygetizhuxiaoxxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String pripid= req.getParameter("pripid");
		res.addForm( "formpanel", trsQueryService.querygetizhuxiaoxx(pripid));
	
	}
	
	@RequestMapping("/querygetizhuxiaoxxform")
	public void querygetizhuxiaoxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("pripid", req.getParameter("pripid"));
		res.addPage("/page/regQuery/geti/geti_zhuxiaoxx.jsp");
		
	}
	
	/*
	 * 个体吊销信息
	 */
	
	@RequestMapping("/querygetidiaoxiaoxxdata")
	public void querygetidiaoxiaoxxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String pripid= req.getParameter("pripid");
		res.addForm( "formpanel", trsQueryService.querygetidiaoxiaoxx(pripid));
	
	}
	
	@RequestMapping("/querygetidiaoxiaoxxform")
	public void querygetidiaoxiaoxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("pripid", req.getParameter("pripid"));
		res.addPage("/page/regQuery/geti/geti_diaoxiaoxx.jsp");
		
	}
		
}
	