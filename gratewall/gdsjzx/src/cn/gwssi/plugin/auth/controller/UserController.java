package cn.gwssi.plugin.auth.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import cn.gwssi.plugin.auth.model.TPtYhBO;
import cn.gwssi.plugin.auth.service.RoleService;
import cn.gwssi.plugin.auth.service.UserService;
import com.gwssi.optimus.util.DateUtil;
import com.gwssi.optimus.util.MD5Util;
import com.gwssi.optimus.util.TreeUtil;


@Controller
@RequestMapping("/auth")
public class UserController {
	
	@Autowired
    private UserService userService;
	@Autowired
	private RoleService roleService;
    
    @RequestMapping("/queryUser")
    public void queryUser(OptimusRequest req, OptimusResponse resp) 
            throws OptimusException {
    	Map params = req.getForm("formpanel");
        List<Map> userList = userService.queryUser(params);
        resp.addGrid("userGrid", userList);
    }
    
    
    @RequestMapping("/queryUnAuthRole")
    public void queryUnAuthRole(OptimusRequest req, OptimusResponse resp) 
            throws OptimusException {
        String userId = (String)req.getParameter("userId");
        List list = userService.queryUnAuthRole(userId);
        resp.addGrid("roleGrid", list);
    }
    
    @RequestMapping("/saveUser")
    public void saveUser(OptimusRequest req, OptimusResponse resp) 
            throws OptimusException {
    	Map<String,String> map = req.getForm("formpanel"); 
        String roles = map.get("jsId");
        List<String> roleList = null;
        if(StringUtils.isNotBlank(roles))
            roleList = Arrays.asList(roles.split(","));
    	TPtYhBO yhBO = req.getForm("formpanel", TPtYhBO.class);
        String back = "fail";
        Map oldYhBO = userService.queryUserByLoginName(yhBO.getLoginName());
        yhBO.setZjjgname(userService.queryzzjgName(yhBO.getZjjgid()));
        if(StringUtils.isBlank(yhBO.getUserId())){
            if(oldYhBO==null){
                yhBO.setUserPwd(MD5Util.MD5Encode(yhBO.getUserPwd()));
                yhBO.setCreateTime(DateUtil.getCurrentDate());
                userService.saveUser(yhBO);
                if(roleList!=null)
                    userService.saveAuthRole(yhBO.getUserId(), roleList);
                back = "success";
            }
        }else{
            if(oldYhBO==null || oldYhBO.get("userId").equals(yhBO.getUserId())){
                userService.updateUser(yhBO);
                userService.deleteAuthRole(yhBO.getUserId());
                userService.saveAuthRole(yhBO.getUserId(), roleList);
                back = "success";
            }
        }
        resp.addAttr("back", back);
    }
    
    @RequestMapping("userDetail")
	public void userDetail(OptimusRequest req, OptimusResponse resp) throws OptimusException {
    	String userId = req.getParameter("userId");
		Map<String,String> map = userService.getUser(userId);
		resp.addForm("formpanel", map);
	}
    
    @RequestMapping("roleList")
	public void roleList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
    	String userId = req.getParameter("userId");
    	List userRoleList = userService.queryUserRole(userId);
    	List roleAllList = roleService.queryRoleTree();
    	TreeUtil.makeCheckedTree(roleAllList, userRoleList, "jsId");
		resp.addTree("js", roleAllList);
	}
    
    @RequestMapping("zyRoleList")
	public void zyRoleList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
    	String userId = req.getParameter("userId");
    	List jsAllList = userService.queryRole(userId);
		resp.addTree("zyJs", jsAllList);
	}
    
    @RequestMapping("zzjgList")
	public void zzjgList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		List jsAllList = userService.queryZzjgList(null);
		resp.addTree("zzJg", jsAllList);
	}
    
    @RequestMapping("zzjgCheckList")
	public void zzjgCheckList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
    	String userId = req.getParameter("userId");
		List jsAllList = userService.queryzzjgCheckList(userId);
		resp.addTree("zjjg", jsAllList);
	}
    
    @RequestMapping("/deleteUser")
    public void deleteUser(OptimusRequest req, OptimusResponse resp) 
            throws OptimusException {
    	List<Map<String, String>> list = req.getGrid("userGrid");
    	for(Map<String, String> map : list){
			String userId = (String) map.get("userId");
			userService.deleteUser(userId);
		}
		resp.addAttr("back", "success");
    }
}
