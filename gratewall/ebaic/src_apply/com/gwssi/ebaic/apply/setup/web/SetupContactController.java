package com.gwssi.ebaic.apply.setup.web;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.ebaic.apply.setup.service.SetupContactService;

@Controller
@RequestMapping("/apply/setup/contact")
public class SetupContactController {

	@Autowired
	SetupContactService setupContactService;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getContactDetail")
	@ResponseBody
	public Map getContactDetail(String ivtId) throws UnsupportedEncodingException{
		
		Map map = setupContactService.getContactDetail(ivtId);
		return map;
	}
}
