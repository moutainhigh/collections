package com.gwssi.yaoxie;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.yaoxie.service.BeiAnDeailService;

@Controller
@RequestMapping("beiAn")
public class BeiAnDetailController {

	@Autowired
	private BeiAnDeailService beianService;
	
	
	

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0 || str.equals("")
				|| str.matches("\\s*");
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	
	@RequestMapping("getDetail")
	public void getDetail(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
	/*	String pripid = req.getParameter("pripid");
		String endid = req.getParameter("endid");
		String regno = req.getParameter("regno");
		String certno = req.getParameter("certno");*/
		
		
		/*if(isNotEmpty(endid)){
			endid = endid.trim();
		}
		if(isNotEmpty(certno)){
			certno = certno.trim();
		}*/
		
		
		
		String id = req.getParameter("id");
		
		if(isNotEmpty(id)){
			id = id.trim();
			List<Map> res = beianService.queryDetail(id);
			resp.addAttr("data", res);
		}else {
			resp.addAttr("data", null);
		}
	}
	
	
	
	@RequestMapping("queryYaoJianSysMainDetail")
	public void getYaoJianZhuTiDetail(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		String id = req.getParameter("id");
		
		if(isNotEmpty(id)){
			id = id.trim();
			List<Map> res = beianService.queryYaoJianZhuTiDetail(id);
			resp.addAttr("data", res);
		}else {
			resp.addAttr("data", null);
		}
	}
	
	
	
	

}
