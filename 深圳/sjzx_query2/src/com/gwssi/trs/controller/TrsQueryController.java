package com.gwssi.trs.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.trs.model.Altitemcode;
import com.gwssi.trs.service.TrsQueryService;

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
		res.addAttr("cerno", req.getParameter("cerno"));
		res.addAttr("inv", req.getParameter("inv"));
		res.addAttr("entname", req.getParameter("entname"));
		res.addPage("/page/regQuery/nzwz/nzwz_chuzixx.jsp");
		
	}
	
	@RequestMapping("/querynzwzchuzifsdata")
	public void querynzwzchuzifsdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String invid = req.getParameter("invid");
		res.addGrid( "gridpanel", trsQueryService.querynzwzchuzifsdata(invid));
	}
	
	/*
	 * 内资外资出资计划
	 */
	@RequestMapping("/querynzwzchuzijihuaxxdata")
	public void querynzwzchuzijihuaxxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String invid= req.getParameter("invid");
		res.addForm( "formpanel", trsQueryService.querynzwzchuzijihuaxx(invid));
	
	}
	
	@RequestMapping("/querynzwzchuzijihuaxxform")
	public void querynzwzchuzijihuaxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("invid", req.getParameter("invid"));
		res.addPage("/page/regQuery/nzwz/nzwz_chuzijihuaxx.jsp");
		
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
		res.addAttr("cerno", req.getParameter("cerno"));
		res.addAttr("name", req.getParameter("name"));
		res.addAttr("entname", req.getParameter("entname"));
		res.addPage("/page/regQuery/nzwz/nzwz_renyuanxx.jsp");
		
	}
	
	@RequestMapping("/queryFddbrInfo")
	public void queryFddbrInfo(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String pripid = req.getParameter("pripid");
		//res.addAttr("priPid", priPid);
		String personid = trsQueryService.queryFddbrPersonid(pripid);
		res.addAttr("personid", personid);
		res.addPage("/page/regQuery/nzwz/nzwz_renyuanxx.jsp");
	}
	
	@RequestMapping("/queryFaRenInfo")
	public void queryFaRenInfo(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String pripid = req.getParameter("pripid");
		String licFlag = req.getParameter("licFlag");
		//res.addAttr("priPid", priPid);
		String personid = trsQueryService.queryFaRenInfo(pripid,licFlag);
		res.addAttr("personid", personid);
		res.addPage("/page/regQuery/geti/geti_jinyin.jsp");
	}
	
	
	/*
	 * 内资外资股权冻结信息 
	 */
	
	@RequestMapping("/querynzwzgqdongjiexxdata")
	public void querynzwzgqdongjiexxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String recid= req.getParameter("recid");
		res.addForm( "formpanel", trsQueryService.querynzwzgqdongjiexx(recid));
	
	}
	
	@RequestMapping("/querynzwzgqdongjiexxform")
	public void querynzwzgqdongjiexxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("recid", req.getParameter("recid"));
		res.addPage("/page/regQuery/nzwz/nzwz_gq_dongjiexx.jsp");
		
	}
	
	
	/*
	 * 内资外资股权出质信息 
	 */
	
	@RequestMapping("/querynzwzgqchuzhixxdata")
	public void querynzwzgqchuzhixxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String recid= req.getParameter("recid");
		res.addForm( "formpanel", trsQueryService.querynzwzgqchuzhixx(recid));
	
	}
	
	@RequestMapping("/querynzwzgqchuzhixxform")
	public void querynzwzgqchuzhixxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("recid", req.getParameter("recid"));
		res.addPage("/page/regQuery/nzwz/nzwz_gq_chuzhixx.jsp");
		
	}
	

	/*
	 * 内资外资注销信息
	 */
	
	@RequestMapping("/querynzwzzhuxiaoxxdata")
	public void querynzwzzhuxiaoxxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {

		String recid= req.getParameter("recid");
		res.addForm( "formpanel", trsQueryService.querynzwzzhuxiaoxx(recid));
	
	}
	
	
	@RequestMapping("/querynzwzzhuxiaoxxform")
	public void querynzwzzhuxiaoxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("recid", req.getParameter("recid"));
		res.addPage("/page/regQuery/nzwz/nzwz_zhuxiaoxx.jsp");
		
	}
	
	
	/*
	 * 内资外资吊销信息
	 */
	
	@RequestMapping("/querynzwzdiaoxiaoxxdata")
	public void querynzwzdiaoxiaoxxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String recid= req.getParameter("recid");
		res.addForm( "formpanel", trsQueryService.querynzwzdiaoxiaoxx(recid));
	
	}
	
	@RequestMapping("/querynzwzdiaoxiaoxxform")
	public void querynzwzdiaoxiaoxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("recid", req.getParameter("recid"));
		res.addPage("/page/regQuery/nzwz/nzwz_diaoxiaoxx.jsp");
		
	}
	
	/*
	 * 内资外资变更信息
	 */
	@RequestMapping("/querynzwzbiangengxxdata")
	public void querynzwzbiangengxxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {

		String recid= req.getParameter("recid");
		res.addForm( "formpanel", trsQueryService.querynzwzbiangengxx(recid));
		
	}
	
	/**
	 * yzh
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	 
	//2 c7  4403010010220140626026031
	@RequestMapping("/querynzwzbiangengxxform")
	public void querynzwzbiangengxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		//String beforeAfter = req.getParameter("beforeAfter");
		res.addAttr("pripid", req.getParameter("pripid"));
		String beforeAfter = req.getParameter("beforeAfter");
		res.addAttr("beforeAfter",beforeAfter);
		String altitemcode = req.getParameter("altitemcode");
		res.addAttr("altitemcode", altitemcode);
		String regino = req.getParameter("regino");
		res.addAttr("regino",regino);
		
		if(!"3".equals(beforeAfter)){
			if(NumberUtils.isNumber(altitemcode)){
				int altitemcodeInt = Integer.valueOf(altitemcode);
				switch(altitemcodeInt){
					
					case 3:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_renyuan.jsp");
					break;
				
					case 43:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_renyuan.jsp");
						break;
					case 46:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan.jsp");
						break;
					case 47:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan.jsp");
						break;
					case 48:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan.jsp");
						break;
					case 49:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan.jsp");
						break;
					case 70:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan.jsp");
						break;
					case 73:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan.jsp");
						break;
					case 30:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_gudong.jsp");
						break;
					case 11:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_gudong.jsp");
						break;
					case 42:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_lishuqiye.jsp");
						break;
					case 15:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_lishuqiye.jsp");
						break;
					case 19:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_lishuqiye.jsp");
						break;
					case 44:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_xukexinxi.jsp");
						break;
					case 45:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_guquanbiangeng.jsp");
						break;
				}
			}else{
				switch(Altitemcode.getValue(altitemcode)){
					case A3:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan.jsp");
						break;
					case B7:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan.jsp");
						break;
					case C7:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_gudong.jsp");
						break;
					case E3:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan.jsp");
						break;
					case E7:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan.jsp");
						break;
					case E9:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_gudong.jsp");
						break;
					case F4:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan.jsp");
						break;
					case C3:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan.jsp");
						break;
					case A8:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_lishuqiye.jsp");
						break;
					case A9:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_lishuqiye.jsp");
						break;
					case D1:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_xukexinxi.jsp");
						break;
					case D2:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_gudong.jsp");
						break;
					case D6:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_lishuqiye.jsp");
						break;
					case D7:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_lishuqiye.jsp");
						break;
					case D9:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_gudong.jsp");
						break;
					/*case B2:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_lishuqiye.jsp");
						break;*/
					case B9:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_renyuan.jsp");
						break;
					case C9:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_chuzijihua.jsp");
						break;
					case G4:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_renyuan.jsp");
						break;
				}
				
			}
		}else{
			if(NumberUtils.isNumber(altitemcode)){
				int altitemcodeInt = Integer.valueOf(altitemcode);
				
					switch(altitemcodeInt){
					
						case 3:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_renyuan_compare.jsp");
							break;
						case 43:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_renyuan_compare.jsp");
							break;
						case 46:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan_compare.jsp");
							break;
						case 47:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan_compare.jsp");
							break;
						case 48:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan_compare.jsp");
							break;
						case 49:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan_compare.jsp");
							break;
						case 70:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan_compare.jsp");
							break;
						case 73:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan_compare.jsp");
							break;
						case 30:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_gudong_compare.jsp");
							break;
						case 11:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_gudong_compare.jsp");
							break;
						case 42:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_lishuqiye_compare.jsp");
							break;
						case 15:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_lishuqiye_compare.jsp");
							break;
						case 19:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_lishuqiye_compare.jsp");
							break;
						case 44:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_xukexinxi_compare.jsp");
							break;
						case 45:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_guquanbiangeng_compare.jsp");
							break;
					}
				}else{
					switch(Altitemcode.getValue(altitemcode)){
						case A3:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan_compare.jsp");
							break;
						case B7:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan_compare.jsp");
							break;
						case C7:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_gudong_compare.jsp");
							break;
						case E3:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan_compare.jsp");
							break;
						case E7:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan_compare.jsp");
							break;
						case E9:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_gudong_compare.jsp");
							break;
						case F4:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan_compare.jsp");
							break;
						case C3:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan_compare.jsp");
							break;
						case A8:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_lishuqiye_compare.jsp");
							break;
						case A9:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_lishuqiye_compare.jsp");
							break;
						case D2:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_gudong_compare.jsp");
							break;
						case D6:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_lishuqiye_compare.jsp");
							break;
						case D7:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_lishuqiye_compare.jsp");
							break;
						case D9:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_gudong_compare.jsp");
							break;
						/*case B2:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_lishuqiye_compare.jsp");
							break;*/
						case B9:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_renyuan_compare.jsp");
							break;
						case C9:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_chuzijihua_compare.jsp");
							break;
						case D1:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_xukexinxi_compare.jsp");
							break;
						case G4:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_renyuan_compare.jsp");
							break;
					}
				}
		    }
		}
	/*
	 * 投资人对比变更信息
	 */
	@RequestMapping("/querynzwzduibixxdata")
	public void querynzwzduibixxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {

		String pripid= req.getParameter("pripid");
		String beforeAfter = req.getParameter("beforeAfter");
		String altitemcode = req.getParameter("altitemcode");
		String regino = req.getParameter("regino");
		res.addGrid( "gridpanel", trsQueryService.querynzwzduibixx(pripid,beforeAfter,altitemcode,regino));
		
	}
	
	
	/*
	 * 内资外资许可信息
	 */
	@RequestMapping("/querynzwzxukexxdata")
	public void querynzwzxukexxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {

		String recid= req.getParameter("recid");
		res.addForm( "formpanel", trsQueryService.querynzwzxukexx(recid));
		
	}
	
	@RequestMapping("/querynzwzxukexxform")
	public void querynzwzxukexxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("recid", req.getParameter("recid"));
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

		String recid= req.getParameter("recid");
		res.addForm( "formpanel", trsQueryService.querynzwzqingsuanxx(recid));
		
	}
	
	@RequestMapping("/querynzwzqingsuanxxform")
	public void querynzwzqingsuanxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("recid", req.getParameter("recid"));
		res.addPage("/page/regQuery/nzwz/nzwz_qingsuanxx.jsp");
		
	}
	
	/*
	 * 内资外资成员清算信息
	 */
	@RequestMapping("/querynzwzqingsuanchengyuanxxdata")
	public void querynzwzqingsuanchengyuanxxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {

		String recid= req.getParameter("recid");
		res.addForm( "formpanel", trsQueryService.querynzwzqingsuanchengyuanxx(recid));
		
	}
	
	@RequestMapping("/querynzwzqingsuanchengyuanxxform")
	public void querynzwzqingsuanchengyuanxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("recid", req.getParameter("recid"));
		res.addPage("/page/regQuery/nzwz/nzwz_qingsuanchengyuanxx.jsp");
		
	}
	
	
	/*
	 * 内资外资财务负责信息
	 */
	
	@RequestMapping("/querynzwzcaiwufuzexxdata")
	public void querynzwzcaiwufuzexxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String recid= req.getParameter("recid");
		res.addForm( "formpanel", trsQueryService.querynzwzcaiwufuzexx(recid));
	
	}
	
	@RequestMapping("/querynzwzcaiwufuzexxform")
	public void querynzwzcaiwufuzexxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("recid", req.getParameter("recid"));
		res.addPage("/page/regQuery/nzwz/nzwz_caiwufuzexx.jsp");
		
	}
	
	/*
	 * 内资外资联络信息
	 */
	
	@RequestMapping("/querynzwzlianluoxxdata")
	public void querynzwzlianluoxxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String recid= req.getParameter("recid");
		res.addForm( "formpanel", trsQueryService.querynzwzlianluoxx(recid));
	
	}
	
	@RequestMapping("/querynzwzlianluoxxform")
	public void querynzwzlianluoxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("recid", req.getParameter("recid"));
		res.addPage("/page/regQuery/nzwz/nzwz_lianluoxx.jsp");
		
	}
	
	
	/*
	 * 集团成员信息
	 */
	
	@RequestMapping("/queryjituanchengyuanxxdata")
	public void queryjituanchengyuanxxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String grpmemid= req.getParameter("grpmemid");
		res.addForm( "formpanel", trsQueryService.queryjituanchengyuanxx(grpmemid));
	
	}
	
	@RequestMapping("/queryjituanchengyuanxxform")
	public void queryjituanchengyuanxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("grpmemid", req.getParameter("grpmemid"));
		res.addPage("/page/regQuery/jituan/jituan_chengyuanxx.jsp");
		
	}
	
	
	/*
	 * 集团变更信息
	 */
	
	@RequestMapping("/queryjituanbiangengxxdata")
	public void queryjituanbiangengxxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String recid= req.getParameter("recid");
		res.addForm( "formpanel", trsQueryService.queryjituanbiangengxx(recid));
	
	}
	
	@RequestMapping("/queryjituanbiangengxxform")
	public void queryjituanbiangengxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		//String beforeAfter = req.getParameter("beforeAfter");
		res.addAttr("pripid", req.getParameter("pripid"));
		String beforeAfter = req.getParameter("beforeAfter");
		res.addAttr("beforeAfter",beforeAfter);
		String altitemcode = req.getParameter("altitemcode");
		res.addAttr("altitemcode", altitemcode);
		String regino = req.getParameter("regino");
		res.addAttr("regino",regino);
		
		if(!"3".equals(beforeAfter)){
			if(NumberUtils.isNumber(altitemcode)){
				int altitemcodeInt = Integer.valueOf(altitemcode);
				switch(altitemcodeInt){
					
					case 3:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_renyuan.jsp");
					break;
				
					case 43:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_renyuan.jsp");
						break;
					case 46:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan.jsp");
						break;
					
					case 47:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan.jsp");
						break;
					case 48:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan.jsp");
						break;
					case 49:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan.jsp");
						break;
					case 70:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan.jsp");
						break;
					case 73:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan.jsp");
						break;
					case 30:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_gudong.jsp");
						break;
					case 11:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_gudong.jsp");
						break;
					case 42:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_lishuqiye.jsp");
						break;
					case 15:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_lishuqiye.jsp");
						break;
					case 19:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_lishuqiye.jsp");
						break;
					case 44:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_xukexinxi.jsp");
						break;
					case 45:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_guquanbiangeng.jsp");
						break;
				}
			}else{
				switch(Altitemcode.getValue(altitemcode)){
					case A3:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan.jsp");
					break;
					case B7:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan.jsp");
						break;
					case C7:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan.jsp");
						break;
					case E3:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_qingsuanzu.jsp");
						break;
					case E7:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_qingsuanzu.jsp");
						break;
					case F4:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan.jsp");
						break;
					case C3:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan.jsp");
						break;
					case A8:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_lishuqiye.jsp");
						break;
					case A9:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_lishuqiye.jsp");
						break;
					case D6:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_lishuqiye.jsp");
						break;
					case D7:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_lishuqiye.jsp");
						break;
					case D9:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_gudong.jsp");
						break;
					case B2:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_lishuqiye.jsp");
						break;
					case C9:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_chuzijiha.jsp");
						break;
					case D1:
						res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_xukexinxi.jsp");
						break;
				}
				
			}
		}else{
			if(NumberUtils.isNumber(altitemcode)){
				int altitemcodeInt = Integer.valueOf(altitemcode);
				
					switch(altitemcodeInt){
					
						case 3:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_renyuan_compare.jsp");
							break;
						case 43:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_renyuan_compare.jsp");
							break;
						case 46:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan_compare.jsp");
							break;
						case 47:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan_compare.jsp");
							break;
						case 48:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan_compare.jsp");
							break;
						case 49:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan_compare.jsp");
							break;
						case 70:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan_compare.jsp");
							break;
						case 73:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan_compare.jsp");
							break;
						case 30:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_gudong_compare.jsp");
							break;
						case 11:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_gudong_compare.jsp");
							break;
						case 42:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_lishuqiye_compare.jsp");
							break;
						case 15:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_lishuqiye_compare.jsp");
							break;
						case 19:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_lishuqiye_compare.jsp");
							break;
						case 44:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_xukexinxi_compare.jsp");
							break;
						case 45:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_guquanbiangeng_compare.jsp");
							break;
					}
				}else{
					switch(Altitemcode.getValue(altitemcode)){
						case A3:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan_compare.jsp");
							break;
						case B7:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan_compare.jsp");
							break;
						case C7:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan_compare.jsp");
							break;
						case E3:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_qingsuanzu_compare.jsp");
							break;
						case E7:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_qingsuanzu_compare.jsp");
							break;
						case F4:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan_compare.jsp");
							break;
						case C3:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_zhuyaorenyuan_compare.jsp");
							break;
						case A8:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_lishuqiye_compare.jsp");
							break;
						case A9:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_lishuqiye_compare.jsp");
							break;
						case D6:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_lishuqiye_compare.jsp");
							break;
						case D7:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_lishuqiye_compare.jsp");
							break;
						case D9:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_gudong_compare.jsp");
							break;
						case B2:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_lishuqiye_compare.jsp");
							break;
						case C9:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_chuzijiha_compare.jsp");
							break;
						case D1:
							res.addPage("/page/regQuery/nzwz/nzwz_biangengduibixx_xukexinxi_compare.jsp");
							break;
					}
				}
		    }
		}
	
	
	/*
	 * 集团注销信息
	 */
	
	@RequestMapping("/queryjituanzhuxiaoxxdata")
	public void queryjituanzhuxiaoxxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String grpcanid= req.getParameter("grpcanid");
		res.addForm( "formpanel", trsQueryService.queryjituanzhuxiaoxx(grpcanid));
	
	}
	
	@RequestMapping("/queryjituanzhuxiaoxxform")
	public void queryjituanzhuxiaoxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("grpcanid", req.getParameter("grpcanid"));
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
		String changeid= req.getParameter("changeid");
		res.addForm( "formpanel", trsQueryService.querygetibiangengxx(changeid));
	
	}
	
	@RequestMapping("/querygetibiangengxxform")
	public void querygetibiangengxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("changeid", req.getParameter("changeid"));
		res.addPage("/page/regQuery/geti/geti_biangengxx.jsp");
		
	}
	
	
	/*
	 * 个体注销信息
	 */
	
	@RequestMapping("/querygetizhuxiaoxxdata")
	public void querygetizhuxiaoxxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String canid= req.getParameter("canid");
		res.addForm( "formpanel", trsQueryService.querygetizhuxiaoxx(canid));
	
	}
	
	@RequestMapping("/querygetizhuxiaoxxform")
	public void querygetizhuxiaoxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("canid", req.getParameter("canid"));
		res.addPage("/page/regQuery/geti/geti_zhuxiaoxx.jsp");
		
	}
	
	/*
	 * 个体吊销信息
	 */
	
	@RequestMapping("/querygetidiaoxiaoxxdata")
	public void querygetidiaoxiaoxxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String revid= req.getParameter("revid");
		res.addForm( "formpanel", trsQueryService.querygetidiaoxiaoxx(revid));
	
	}
	@RequestMapping("/querygetidiaoxiaoxxform")
	public void querygetidiaoxiaoxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("revid", req.getParameter("revid"));
		res.addPage("/page/regQuery/geti/geti_diaoxiaoxx.jsp");
		
	}
	
	@RequestMapping("/queryRenYuanNames")
	@ResponseBody
	public List<Map> queryRenYuanNames(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String pripid = req.getParameter("pripid");
		String regino = req.getParameter("regino");
		String altitemcode = req.getParameter("altitemcode");
		List<Map> queryRenYuanNames = trsQueryService.queryRenYuanNames(pripid,regino,altitemcode);
		return queryRenYuanNames;
	}
	
	@RequestMapping("/queryRenYuanPersonId")
	@ResponseBody
	public void queryRenYuanPersonId(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String pripid = req.getParameter("pripid");
		String cerno = req.getParameter("cerno");
		List<Map> personIds = trsQueryService.queryRenYuanPersonId(pripid,cerno);
		if(personIds.size()>0){
			res.addAttr("personid", personIds.get(0).get("personid"));
			res.addPage("/page/regQuery/nzwz/nzwz_renyuanxx.jsp");
		}else{
			res.addAttr("personid", "");
			res.addPage("/page/regQuery/nzwz/nzwz_renyuanxx.jsp");
		}
	}
	
	@RequestMapping("/queryFuZeRenPersonId")
	@ResponseBody
	public void queryFuZeRenPersonId(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String pripid = req.getParameter("pripid");
		String regino = req.getParameter("regino");
		String bgFlag = req.getParameter("bgFlag");
		List<Map> personIds = trsQueryService.queryFuZeRenPersonId(pripid,regino,bgFlag);
		if(personIds.size()>0){
			res.addAttr("personid", personIds.get(0).get("personid"));
			res.addAttr("regino", regino);
			res.addPage("/page/regQuery/nzwz/nzwz_renyuanxx_bg.jsp");
		}else{
			res.addAttr("personid", "");
			res.addAttr("regino", regino);
			res.addPage("/page/regQuery/nzwz/nzwz_renyuanxx_bg.jsp");
		}
	}
	
	
	/*
	 * 内资外资人员变更信息 
	 */
	@RequestMapping("/querynzwzrenyuanxxdataBg")
	public void querynzwzrenyuanxxdataBg(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String personid= req.getParameter("personid");
		String regino= req.getParameter("regino");
		res.addForm( "formpanel", trsQueryService.querynzwzrenyuanxxdataBg(personid,regino));
	
	}
	
	/**
	 * 多证合一数据
	 */
	@RequestMapping("/querynzwzduozhengheyi")
	public void querynzwzduozhengheyi(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String pripid = req.getParameter("pripid");
		res.addForm( "formpanel", trsQueryService.querynzwzduozhengheyi(pripid));
	}
	
	/**
	 * 异常名录信息
	 */
	@RequestMapping("/queryyichangxxdata")
	public void queryyichangxxdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String id = req.getParameter("id");
		res.addForm("formpanel", trsQueryService.queryyichangminglu(id));
	}
	
	@RequestMapping("/queryyichangxxform")
	public void queryyichangxxform(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("id", req.getParameter("id"));
		res.addPage("/page/reg/YCQueryDetail.jsp");
		
	}
	
	/**
	 * 严重违法失信信息
	 * @param req
	 * @param res
	 * @throws OptimusException 
	 */
	@RequestMapping("/queryYZWFSXData")
	public void queryYZWFSXData(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String id = req.getParameter("id");
		res.addForm("formpanel", trsQueryService.queryYZWFSX(id));
	}
	
	@RequestMapping("/queryYZWFSXForm")
	public void queryYZWFSXForm(OptimusRequest req, OptimusResponse res) throws OptimusException {
		res.addAttr("id", req.getParameter("id"));
		res.addPage("/page/reg/YZWFSXQueryDetail.jsp");
	}
}
	