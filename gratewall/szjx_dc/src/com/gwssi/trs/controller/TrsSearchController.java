package com.gwssi.trs.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.trs.model.SearchCountBO;
import com.gwssi.trs.service.TrsSearchService;


/**
 * trs 查询关键词显示
 * @author chaihw
 *
 */
@Controller
@RequestMapping("/trsSearch")
public class TrsSearchController {
    
    @Autowired
    private TrsSearchService trsSearchService;

    
   
    /**
     * 获取数据库的key -value
     * @param req
     * @param resp
     * @throws OptimusException
     */
    @ResponseBody
	@RequestMapping("/query")
	public Map findKeyValueDcDmDbtypeBO(OptimusRequest req, OptimusResponse resp) 
	        throws OptimusException {
			Map<String,String> map= new HashMap<String,String>();
		 	List<Map<String, Object>> funcList =null;
		 	List<Map> list=trsSearchService.querySearchCountBo();
		 	
		 	StringBuffer returnString = new StringBuffer();
		 	
		 	for(int i=0; i<list.size();i++){
		 		Map<String,Object> map1=list.get(i);
		 		if(i==(list.size()-1)){
		 			returnString.append(map1.get("searchText"));
		 		}else{
		 			returnString.append(map1.get("searchText")).append(",");	
		 		}
		 	
		 	}
	
	        map.put("returnString", returnString.toString());
	        return  map;
	}	
    
    @ResponseBody
	@RequestMapping("/countDual")
	public void SearchCount(OptimusRequest req, OptimusResponse resp) 
	        throws OptimusException {
    	String queryKeyWord = req.getParameter("queryKeyWord");
    	trsSearchService.doSearchConntDual(queryKeyWord);
    	
    	
    	
	}	
}