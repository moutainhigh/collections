package cn.gwssi.plugin.auth.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import cn.gwssi.plugin.auth.model.TPtUrlzyBO;
import cn.gwssi.plugin.auth.service.RoleService;
import cn.gwssi.plugin.auth.service.UrlService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

@Controller
@RequestMapping("/auth")
public class UrlController {
	
	@Autowired
    private UrlService urlService;
	@Autowired
	private RoleService roleService;
    
    @RequestMapping("/queryUrl")
    public void queryUser(OptimusRequest req, OptimusResponse resp) 
            throws OptimusException {
    	TPtUrlzyBO tPtUrlzyBO = req.getForm("formpanel", TPtUrlzyBO.class);
		String urlMc=null;
		if(tPtUrlzyBO!=null){
			 urlMc = tPtUrlzyBO.getUrlMc();
		}
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
        List<Map> userList = urlService.queryUrl(tPtUrlzyBO.getUrlMc());
        if(userList.isEmpty()){
        	if(!StringUtils.isNotBlank(tPtUrlzyBO.getUrlId())){
        		//生成主键
        		tPtUrlzyBO.setUrlId(UUID.randomUUID().toString().replace("-", ""));
        		urlService.saveUrl(tPtUrlzyBO);
        		back = "success";
        	}else{
        		urlService.updateUrl(tPtUrlzyBO);
        		back = "success";
        	}
        }else{
        	if(tPtUrlzyBO.getUrlId()!=null ){
        		// tPtUrlzyBO.getUrlId().equals(userList.get(0).getUrlId())
        		if(userList.size()>0){
        			for(int i=0;i<userList.size();i++){
        				if(tPtUrlzyBO.getUrlId().equals(userList.get(i).get("urlId"))){
            					urlService.updateUrl(tPtUrlzyBO);
        				}
        			}
        		}
        		back = "success";
        	}
        }
        resp.addAttr("back", back);
    }
    
    @RequestMapping("urlDetail")
	public void userDetail(OptimusRequest req, OptimusResponse resp) throws OptimusException {
    	String urlId = req.getParameter("urlId");
    	Map tPtUrlzyMap= urlService.getUrl(urlId);
		resp.addForm("formpanel", tPtUrlzyMap);
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
