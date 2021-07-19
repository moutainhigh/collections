package com.gwssi.ebaic.approve.manager.contrller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.ebaic.approve.manager.service.ManagerService;

@Controller
@RequestMapping("/approve/manager")
public class ManagerContrller {
	protected final static Logger logger = Logger.getLogger(ManagerContrller.class);
	
	@Autowired
	protected ManagerService managerService;
	
	
	
}
