package com.ye.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.bo.domain.Stock;
import com.ye.controller.base.BaseController;
import com.ye.monitor.from.JuHeWeb;
import com.ye.monitor.from.TencentWeb;


@Controller
public class LoginController extends BaseController{

	@Autowired
	private JuHeWeb juHeWeb;
	
	
	
	
	@RequestMapping(value="/login_toLogin")
	public ModelAndView toLogin() throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/admin/login");
		return mv;
	}
	
	
	//https://www.cnblogs.com/tanglc/p/3664795.html
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/login_login" ,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String LoginController() throws Exception{
		/*Map map = new HashMap();
		System.out.println("2222222");
		map.put("key","空了");
		return JSONUtils.toJSONString(map);*/
		//return "main/index";
		return "coms";
	}
	
	@RequestMapping(value="/main/{changeMenu}")
	public ModelAndView login_index(@PathVariable("changeMenu") String changeMenu){
		
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/admin/index");
		return mv;
	}
	
	
	
	
	@RequestMapping("allStockList")
	public String getAllStockByPage(){
		return "system/admin/getAllStockByPage";
	}
	
	@RequestMapping("allSMSStockList")
	public String getAllSMSStockByPage(){
		return "system/admin/getAllSMSStockByPage";
	}
	
	
	//最近关注
	@RequestMapping("recentImp")
	public String recentImp(){
		return "system/admin/recentImp";
	}
	
	@RequestMapping("mostImp")
	public String mostImp(){
		return "system/admin/mostImp";
	}
	
	@RequestMapping("imp")
	public String imp(){
		return "system/admin/imp";
	}
	
	
	@RequestMapping("recentWaitImp")
	public String waitImp(){
		return "system/admin/recentWaitImp";
	}
	@RequestMapping("longHu")
	public String longHu(){
		return "system/admin/longHu";
	}
	
	@RequestMapping("cons")
	public String consoles(){
		return "system/admin/consoles";
	}
	
	
	@RequestMapping("addStock")
	public String addStock(){
		return "system/admin/addStock";
	}
	@RequestMapping("editStock")
	public String editStock(){
		return "system/admin/editStock";
	}
	@RequestMapping("delStock")
	public String delStock(){
		return "system/admin/delStock";
	}
	
	@RequestMapping("addSMSStock")
	public String addSMSStock(){
		return "system/admin/addSMSStock";
	}
	@RequestMapping("editSMSStock")
	public String editSMSStock(){
		return "system/admin/editSMSStock";
	}
	@RequestMapping("delSMSStock")
	public String delSMSStock(){
		return "system/admin/delSMSStock";
	}
	@RequestMapping("zhangFuBang")
	public String zhangFuBang(){
		return "system/admin/zhangFuBang";
	}
	
	@RequestMapping("dieFuBang")
	public String dieFuBang(){
		return "system/admin/dieFuBang";
	}
	
	@RequestMapping("zhangSMSFuBang")
	public String zhangSMSFuBang(){
		return "system/admin/zhangSMSFuBang";
	}
	
	@RequestMapping("dieSMSFuBang")
	public String dieSMSFuBang(){
		return "system/admin/dieSMSFuBang";
	}
	
	
}
