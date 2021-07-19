package cn.gwssi.plugin.auth.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.gwssi.plugin.auth.service.OrgService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

@Controller
@RequestMapping("/org")
public class OrgController {
    
    @Autowired
    private OrgService orgService;
    
    @RequestMapping("/queryOrgTree")
    public void queryFuncTree(OptimusRequest req, OptimusResponse res) 
            throws OptimusException {
        List funcList = orgService.queryOrgTree();
        Map rootNode = new HashMap();
        rootNode.put("name", "广东省工商行政管理局");
        rootNode.put("id", "440000");
        funcList.add(rootNode);
        res.addTree("funcTree", funcList);
    }
}
