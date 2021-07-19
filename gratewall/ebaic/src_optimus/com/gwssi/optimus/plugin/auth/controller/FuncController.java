package com.gwssi.optimus.plugin.auth.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.TPtGnBO;
import com.gwssi.optimus.plugin.auth.model.TPtUrlzyBO;
import com.gwssi.optimus.plugin.auth.model.TPtZjzyBO;
import com.gwssi.optimus.plugin.auth.service.FuncService;

@Controller
@RequestMapping("/auth")
@SuppressWarnings({"rawtypes","unchecked"})
public class FuncController {
    
    @Autowired
    private FuncService funcService;
    
    @Autowired
	private OptimusAuthManager optimusAuthManager;
    
    @RequestMapping("/queryFuncTree")
    public void queryFuncTree(OptimusRequest req, OptimusResponse res) 
            throws OptimusException {
        List funcList = funcService.queryFuncTree();
        Map rootNode = new HashMap();
        rootNode.put("name", "功能树");
        rootNode.put("id", "0");
        funcList.add(rootNode);
        res.addTree("funcTree", funcList);
    }
    
    @RequestMapping("/queryChildFunc")
    public void queryChildFunc(OptimusRequest req, OptimusResponse res) 
            throws OptimusException {
        String sjgnId = req.getParameter("sjgnId");
        List funcList = funcService.queryChildFunc(sjgnId);
        res.addGrid("gridpanel", funcList);
    }
    
    @RequestMapping("/deleteFunc")
    public void deleteFunc(OptimusRequest req, OptimusResponse res) 
            throws OptimusException {
        String funcIdsStr = (String)req.getAttr("funcIdsStr");
        if(StringUtils.isEmpty(funcIdsStr)){
            return;
        }
        List<String> funcIds = Arrays.asList(funcIdsStr.split(","));
        funcService.deleteFunc(funcIds);
    }
    
    @RequestMapping("/funcDetail")
    public void funcDetail(OptimusRequest req, OptimusResponse res) 
            throws OptimusException {
        String gnId = req.getParameter("gnId");
        TPtGnBO gnBO = funcService.getFuncById(gnId);
        res.addForm("formpanel", gnBO);
    }
    
    @RequestMapping("/saveFunc")
    public void saveFunc(OptimusRequest req, OptimusResponse res) 
            throws OptimusException {
        TPtGnBO gnBO = req.getForm( "formpanel", TPtGnBO.class);
        String back = funcService.saveFunc(gnBO);
        res.addAttr("back", back);
    }
    
    @RequestMapping("/queryFuncUrl")
    public void queryFuncUrl(OptimusRequest req, OptimusResponse res) 
            throws OptimusException {
        String gnId = req.getParameter("gnId");
        if(StringUtils.isEmpty(gnId)){
            return;
        }
        List urlList = funcService.queryFuncUrl(gnId);
        res.addGrid("urlGrid", urlList);
    }
    
    @RequestMapping("/funcUrlDetail")
    public void urlDetail(OptimusRequest req, OptimusResponse res) 
            throws OptimusException {
        String urlId = req.getParameter("urlId");
        TPtUrlzyBO urlBO = funcService.getFuncUrl(urlId);
        res.addForm("formpanel", urlBO);
    }
    
    @RequestMapping("/saveFuncUrl")
    public void saveFuncUrl(OptimusRequest req, OptimusResponse res) 
            throws OptimusException {
        TPtUrlzyBO urlBO = req.getForm( "formpanel", TPtUrlzyBO.class);
        urlBO.setLx("1");
        String url = urlBO.getUrl();
        PathMatcher pathMatcher = new AntPathMatcher();
    	if(pathMatcher.isPattern(url)){
    		urlBO.setSfjqlj("0");
    	}else{
    		urlBO.setSfjqlj("1");
    	}
        funcService.saveFuncUrl(urlBO); 
    }
    
    @RequestMapping("/deleteFuncUrl")
    public void deleteUrl(OptimusRequest req, OptimusResponse res) 
            throws OptimusException {
        List list = req.getGrid("urlGrid", TPtUrlzyBO.class);
        funcService.deleteUrl(list);
    }
    
    @RequestMapping("/queryFuncZj")
    public void queryFuncZj(OptimusRequest req, OptimusResponse res) 
            throws OptimusException {
        String gnId = req.getParameter("gnId");
        List funcList = funcService.queryFuncZj(gnId);
        res.addGrid("zjGrid", funcList);
    }
    
    @RequestMapping("/saveFuncZj")
    public void saveFuncZj(OptimusRequest req, OptimusResponse res) 
            throws OptimusException {
        TPtZjzyBO zjBO = req.getForm( "formpanel", TPtZjzyBO.class);
        String back = funcService.saveFuncZj(zjBO); 
        res.addAttr("back", back);
    }
    
    @RequestMapping("/funcZjDetail")
    public void funcZjDetail(OptimusRequest req, OptimusResponse res) 
            throws OptimusException {
        String zjId = req.getParameter("zjId");
        TPtZjzyBO zjBO = funcService.getFuncZj(zjId);
        res.addForm("formpanel", zjBO);
    }
    
    @RequestMapping("/deleteFuncZj")
    public void deleteFuncZj(OptimusRequest req, OptimusResponse res) 
            throws OptimusException {
        List list = req.getGrid("zjGrid", TPtZjzyBO.class);
        funcService.deleteUrl(list);
    }
    
    @RequestMapping("/publishFunc")
    public void publishFunc(OptimusRequest req, OptimusResponse res) throws OptimusException{
    	if(OptimusAuthManager.SECURITY_USE_CACHE){
    		optimusAuthManager.init();
    		res.addAttr("back", "success");
    	}else{
    		res.addAttr("back", "fail");
    	}
    }
}
