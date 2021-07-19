package com.gwssi.report.ebusiness;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.report.controller.CognosController;
import com.gwssi.report.service.CognosService;
import com.gwssi.report.service.EBusinessService;

@Controller
@RequestMapping("buscl")
public class EBussinessChuLiController {
	private static Logger log = Logger.getLogger(EBussinessChuLiController.class);
	
	@Autowired
	private EBusinessService ebusinessService;
	
	@RequestMapping("/ebus")
	public void getQueryByDate(String dateBegin,String dateEnd) {
		
	}
}
