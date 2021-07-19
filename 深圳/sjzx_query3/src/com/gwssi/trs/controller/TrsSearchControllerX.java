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
@RequestMapping("/trsSearchxiao")
public class TrsSearchControllerX {
    
    @Autowired
    private TrsSearchService trsSearchServicexiao;

    
   
    /**
     * 获取数据库的key -value
     * @param req
     * @param resp
     * @throws OptimusException
     */
    @ResponseBody
	@RequestMapping("/queryxiao")
	public Map findKeyValueDcDmDbtypeBO(OptimusRequest req, OptimusResponse resp) 
	        throws OptimusException {
			Map<String,String> map= new HashMap<String,String>();
		 	List<Map<String, Object>> funcList =null;
		 	List<Map> list=trsSearchServicexiao.querySearchCountBo();
		 	
		 	StringBuffer returnString = new StringBuffer();
		 	
		 	for(int i=0; i<list.size();i++){
		 		Map<String,Object> map1=list.get(i);
		 		if(i==(list.size()-1)){
		 			returnString.append(map1.get("searchText"));
		 		}else{
		 			returnString.append(map1.get("searchText")).append(",");	
		 		}
		 	
		 	}
		 	System.out.println("searchText"+returnString);
	
	        map.put("returnString", returnString.toString());
	        System.out.println(map.size()+"map");
	        return  map;
	}	
    
    @ResponseBody
	@RequestMapping("/countDualxiao")
	public void SearchCount(OptimusRequest req, OptimusResponse resp) 
	        throws OptimusException {
    	String queryKeyWord = req.getParameter("queryKeyWord");
    	trsSearchServicexiao.doSearchConntDual(queryKeyWord);
    	
    	
    	
	}	
}