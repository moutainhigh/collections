package cn.gwssi.analysis.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gwssi.analysis.service.AnalysisService;
import cn.gwssi.analysis.service.BusinessService;
import cn.gwssi.resource.DateUtil;
import cn.gwssi.resource.FreemarkerUtil;
import cn.gwssi.resource.SortUtil;

import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

@Controller
@RequestMapping("/analysis")
public class BusinessController {
	public static Logger log = Logger.getLogger(BusinessController.class);
	
	@Autowired
	private BusinessService businessService;

	@RequestMapping("/businessSum")
	@ResponseBody
	public List businessSum(OptimusRequest req, OptimusResponse res)
			throws OptimusException {
		String transdt = req.getParameter("transdt");
		 String measure = req.getParameter("measure");
		 String regorg = req.getParameter("regorg");
		 String accepttype = req.getParameter("accepttype");
 		 Map params = new HashMap();
		 
 		 if(transdt!=null){
 			 params.put("transdt", transdt);
 		 }
 		 
 		if(measure!=null){
			 params.put("measure", measure);
		 }
 		
 		if(regorg!=null){
			 params.put("regorg", regorg);
		 }
 		
 		if(accepttype!=null){
			 params.put("accepttype", accepttype);
		 }
 		
	     List funcList = businessService.selectBusinessSum(params);
		 // Map rootNode = new HashMap();
		 return funcList;// funcList;
	}
	
	

	@RequestMapping("/businessSecondSum")
	@ResponseBody
	public List businessSecondSum(OptimusRequest req, OptimusResponse res)
			throws OptimusException {
		String transdt = req.getParameter("transdt");
		// String measure = req.getParameter("measure");
		 String regorgCN = req.getParameter("regorgCN");
		 String regorg = req.getParameter("regorg");
		 String accepttype = req.getParameter("accepttype");
 		 Map params = new HashMap();
		 
 		 if(transdt!=null){
 			 params.put("transdt", transdt);
 		 }
 		 
 		/*if(measure!=null){
			 params.put("measure", measure);
		 }*/
 		
 		if(regorgCN!=null){
			 params.put("regorgCN", regorgCN);
		 }
 		
 		if(regorg!=null){
			 params.put("regorg", regorg);
		 }
 		
 		if(accepttype!=null){
			 params.put("accepttype", accepttype);
		 }
	     List funcList = businessService.businessSecondSum(params);
		 // Map rootNode = new HashMap();
		 return funcList;// funcList;
	}
}
