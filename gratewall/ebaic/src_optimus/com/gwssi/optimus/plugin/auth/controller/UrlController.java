package com.gwssi.optimus.plugin.auth.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.plugin.auth.model.TPtUrlzyBO;
import com.gwssi.optimus.plugin.auth.service.UrlService;

@Controller
@RequestMapping("/auth")
public class UrlController {
	
	@Autowired
    private UrlService urlService;
//	@Autowired
//	private RoleService roleService;
    
    @RequestMapping("/queryUrl")
    public void queryUser(OptimusRequest req, OptimusResponse resp) 
            throws OptimusException {
    	TPtUrlzyBO tPtUrlzyBO = req.getForm("formpanel", TPtUrlzyBO.class);
    	String urlMc = tPtUrlzyBO.getUrlMc();
        @SuppressWarnings("rawtypes")
		List urlList = urlService.queryUrl(urlMc);
        resp.addGrid("urlGrid", urlList);
    }
    
    @RequestMapping("/saveUrl")
    public void saveUser(OptimusRequest req, OptimusResponse resp) 
            throws OptimusException {
    	TPtUrlzyBO tPtUrlzyBO = req.getForm( "formpanel", TPtUrlzyBO.class);
    	tPtUrlzyBO.setLx("2");
    	String url = tPtUrlzyBO.getUrl();
    	PathMatcher pathMatcher = new AntPathMatcher();
    	if(pathMatcher.isPattern(url)){
    		tPtUrlzyBO.setSfjqlj("0");
    	}else{
    		tPtUrlzyBO.setSfjqlj("1");
    	}
    	String back = "fail";
        List<TPtUrlzyBO> userList = urlService.queryUrl(tPtUrlzyBO.getUrlMc());
        if(userList.isEmpty()){
        	if(tPtUrlzyBO.getUrlId().isEmpty()){
        		urlService.saveUrl(tPtUrlzyBO);
        		back = "success";
        	}else{
        		urlService.updateUrl(tPtUrlzyBO);
        		back = "success";
        	}
        }else{
        	if(tPtUrlzyBO.getUrlId()!=null && tPtUrlzyBO.getUrlId().equals(userList.get(0).getUrlId())){
        		urlService.updateUrl(tPtUrlzyBO);
        		back = "success";
        	}
        }
        resp.addAttr("back", back);
    }
    
    @RequestMapping("urlDetail")
	public void userDetail(OptimusRequest req, OptimusResponse resp) throws OptimusException {
    	String urlId = req.getParameter("urlId");
    	TPtUrlzyBO tPtUrlzyBO = urlService.getUrl(urlId);
		resp.addForm("formpanel", tPtUrlzyBO);
	}
    
    @RequestMapping("/deleteUrl")
    public void deleteUser(OptimusRequest req, OptimusResponse resp) 
            throws OptimusException {
    	List<Map<String, String>> list = req.getGrid("urlGrid");
    	for(Map<String, String> map : list){
			String urlId = (String) map.get("urlId");
			TPtUrlzyBO tPtUrlzyBO = new TPtUrlzyBO();
			tPtUrlzyBO.setUrlId(urlId);
			urlService.deleteUrl(tPtUrlzyBO);
		}
		resp.addAttr("back", "success");
    }
}
