package com.report.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping(value = { "/", "/index" })
	public String doIndex() {
		return "index";
	}
	
	
	
	@RequestMapping(value = { "data01"})
	public String doXiaofei() {
		return "data/data01";
	}
}
