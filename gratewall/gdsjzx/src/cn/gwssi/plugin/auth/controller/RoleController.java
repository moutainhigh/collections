package cn.gwssi.plugin.auth.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.gwssi.plugin.auth.service.FuncService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import cn.gwssi.plugin.auth.model.TPtJsBO;
import cn.gwssi.plugin.auth.service.RoleService;
import com.gwssi.optimus.util.TreeUtil;

@Controller
@RequestMapping("/auth")
public class RoleController {
    
    @Autowired
    private RoleService roleService;
    @Autowired
    private FuncService authService;
    
    @RequestMapping("/queryRole")
    public void queryRole(OptimusRequest req, OptimusResponse res) 
            throws OptimusException {
        Map params = req.getForm("formpanel");
        List list = roleService.queryRole(params);
        res.addGrid("roleGrid", list);
    }
    
    @RequestMapping("/initRole")
    public void initRole(OptimusRequest req, OptimusResponse res) 
            throws OptimusException {
        List list = authService.queryFuncTree();
        Map form = new HashMap();
        form.put("funcTree", list);
        res.addForm("formpanel", form);
    }
    
    @RequestMapping("/saveRole")
    public void saveRole(OptimusRequest req, OptimusResponse res) 
            throws OptimusException {
        TPtJsBO jsBO = req.getForm( "formpanel", TPtJsBO.class);
        String funcIdsStr = (String)req.getAttr("funcIdsStr");
        List<String> funcIds = new ArrayList<String>();
        if(StringUtils.isEmpty(funcIdsStr)){
        }else{
        	funcIds = Arrays.asList(funcIdsStr.split(","));
        }
        roleService.saveRole(jsBO, funcIds);
    }
    
    @RequestMapping("/roleDetail")
    public void roleDetail(OptimusRequest req, OptimusResponse res) 
            throws OptimusException {
        String jsId = req.getParameter("jsId");
        Map jsMap = roleService.getRole(jsId);
        res.addForm("formpanel", jsMap);
    }
    
    @RequestMapping("/queryAuthFunc")
    public void queryAuthFunc(OptimusRequest req, OptimusResponse res) 
            throws OptimusException {
        String jsId = req.getParameter("jsId");
        List authFuncList = roleService.queryAuthFunc(jsId);
        List allFucnList = authService.queryFuncTree();
        TreeUtil.makeCheckedTree(allFucnList, authFuncList, "gnId");
        res.addTree("funcTree", allFucnList);
    }
    
    @RequestMapping("/deleteRole")
    public void deleteRole(OptimusRequest req, OptimusResponse res) 
            throws OptimusException {
        List list = req.getGrid("roleGrid", TPtJsBO.class);
        roleService.deleteRole(list);
        res.addAttr("back", "success");
    }
}
