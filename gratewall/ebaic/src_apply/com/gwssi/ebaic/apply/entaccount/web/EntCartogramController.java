package com.gwssi.ebaic.apply.entaccount.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.ebaic.apply.entaccount.service.EntCartogramService;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

/*
 * author yzh
 */
@RequestMapping("/apply/entCartogram")
@Controller
public class EntCartogramController {
	
	@Autowired
	protected EntCartogramService entCartogramService;
	
	/**
	 * 企业投资信息饼图  投资了哪些企业 投资额分别是多少
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryUnInversorMsg")
	public void queryUnInversorMsg(OptimusRequest request,OptimusResponse response){
		
	}
}
