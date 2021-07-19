package cn.gwssi.plugin.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import cn.gwssi.plugin.auth.service.AuthService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.optimus.core.common.Constants;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.core.web.event.WebContext;
import cn.gwssi.plugin.auth.OptimusAuthManager;
import cn.gwssi.plugin.auth.model.User;
import com.gwssi.optimus.util.MD5Util;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
	@Autowired
	private AuthService authService;

	@RequestMapping("login")
	@ResponseBody
	public Map login(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		// Map<String,String> map = req.getForm("formpanel");
		// String dlm = map.get("dlm");
		// String mm = map.get("mm");
		String dlm = req.getParameter("dlm").trim();
		String mm = req.getParameter("mm").trim();
		Map map = new HashMap();
		mm = MD5Util.MD5Encode(mm);
		if (StringUtils.isBlank(dlm)) {
			map.put("back", "fail");
			map.put("msg", "用户名不能为空");
			return map;
		}
		List<Map> yhBO= authService.login(dlm, mm);
		boolean isSA = false;
		if (null == yhBO) {
			map.put("back", "fail");
			map.put("msg", "用户名或密码错误");
			return map;
		}
		if (OptimusAuthManager.USER_TYPE_NORMAL.equals(yhBO.get(0).get("usertype"))) {
			map.put("back", "fail");
			map.put("msg", "普通用户不能登录后台");
			return map;
		}
		HttpSession session = WebContext.getHttpSession();
		session.setAttribute(OptimusAuthManager.LOGIN_NAME, dlm);
		String url = "/page/sjbd/index.jsp";
		List<Map> list = authService.getRoleIdListByLoginName(dlm);
		List urlList = authService.getDefaultUrl(dlm);
		if (urlList.size() > 0) {
			Map urlMap = (Map) urlList.get(0);
			url = (String) urlMap.get("defUrl");
		}
		List<String> roleList = new ArrayList<String>();
		for (Map mapRole : list) {
			String role = (String) mapRole.get("jsId");
			roleList.add(role);
			if ("superadmin".equals(role)) {
				isSA = true;
			}
		}
		User user = new User();
		//[{createTime=000000014979AAAC, idCard=null, sex=1, userPwd=202CB962AC59075B964B07152D234B70, countryCity=null, facilityId=null, userType=null, telphone=null, orgId=null, pwdQuestion=null, address=null, zyJsId=superadmin, email=null, zipCode=null, userId=superadmin, pwdAnswer=null, userName=superadmin, yxBj=1, cerNo=null, cerType=null, loginName=superadmin, mobile=null}]
		user.setUserId((String) yhBO.get(0).get("userId"));
		user.setLoginName((String) yhBO.get(0).get("userName"));
		user.setRoleIdList(roleList);
		// SystemUser user = new SystemUser();
		// user.setUserId(dlm);
		// user.setRoleIdList(roleList);
		session.setAttribute(OptimusAuthManager.USER, user);
		session.setAttribute(OptimusAuthManager.SUPERADMIN, isSA);
		map.put("url", url);
		map.put("back", "success");
		return map;
	}

	@RequestMapping("/getLoginName")
	@ResponseBody
	public Map<String, String> username(HttpSession session) throws OptimusException {
		Map<String, String> map = new HashMap<String, String>();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		if (null != user) {
			String loginName = user.getLoginName();
			map.put("loginName", loginName);
		}
		return map;
	}

	@RequestMapping("/queryFuncTree")
	@ResponseBody
	public void queryFuncTree(OptimusRequest req, OptimusResponse res)
			throws OptimusException {
		HttpSession session = req.getHttpRequest().getSession();
		String dlm = (String) session
				.getAttribute(OptimusAuthManager.LOGIN_NAME);
		boolean flag = false;
		if (Constants.SECURITY_AUTHCHECK) {
			if (session.getAttribute(OptimusAuthManager.SUPERADMIN) != null)
				flag = (Boolean) session
						.getAttribute(OptimusAuthManager.SUPERADMIN);
		}
		List funcList = authService.queryMenu(dlm, flag);
		Map rootNode = new HashMap();
		rootNode.put("name", "菜单树");
		rootNode.put("id", "0");
		funcList.add(rootNode);
		res.addTree("funcTree", funcList);
	}

	//[{id=af7786ac117d47d3bf9b6d5659f048ab, frameurl=null, name=权限管理, pid=9ffbb04414b4432a9652aa04d1442228}, {id=8369b87f82c94a3c812277d008acde05, frameurl=/page/name/main.html, name=名称调整, pid=e8a72550d6ea4c91b6adf2c513e7fd5a}, {id=feeb129f21e84ed4bedcf20f08962e69, frameurl=/page/dictionary/dictionary_list.html, name=代码表管理, pid=9ffbb04414b4432a9652aa04d1442228}, {id=e8a72550d6ea4c91b6adf2c513e7fd5a, frameurl=index, name=名称登记, pid=0}, {id=443df7c2e672466a960b3ee35bd6304f, frameurl=/page/name/main.html, name=名称设立, pid=e8a72550d6ea4c91b6adf2c513e7fd5a}, {id=38e323489db14a7d91f074561c06a4ff, frameurl=/page/auth/func_list.html, name=功能管理, pid=af7786ac117d47d3bf9b6d5659f048ab}, {id=931467464af24bd9a063394d84df32d9, frameurl=/page/report/bbgl_rcgl.jsp, name=gg, pid=0}, {id=86a9e57f576146669714bfe15adad703, frameurl=/page/report/bbgl_rcgl.jsp, name=gg1, pid=0}, {id=01a55cf34dd44ac18a57e6d0443320e8, frameurl=/page/auth/role_list.html, name=角色管理, pid=af7786ac117d47d3bf9b6d5659f048ab}, {id=9ffbb04414b4432a9652aa04d1442228, frameurl=null, name=系统管理, pid=0}, {id=24ceaa9dbe71402e82d52971a96a8a4d, frameurl=/page/auth/user_list.html, name=用户管理, pid=af7786ac117d47d3bf9b6d5659f048ab}, {id=d788ce25aa3b43d6a274dd7c73e22fcd, frameurl=/page/auth/white_list.html, name=白名单管理, pid=af7786ac117d47d3bf9b6d5659f048ab}, {id=8cbb3220223345f2aeeb5e0560acf040, frameurl=da, name=www, pid=0}, {id=bab307729a614cf5b8ccf472b69f4c76, frameurl=da, name=www1, pid=0}, {id=d1de6e177b334436811b1640962e2d6a, frameurl=da, name=www2, pid=0}, {id=6c36c67439674f6395aa7fdce6caa50b, frameurl=/page/sjbd/index.jsp, name=主页, pid=0}, {id=e2700cd81ac7443c950a31874b180a14, frameurl=/page/sjbd/index.jsp, name=主页, pid=0}, {id=0, name=菜单树}]
	@RequestMapping("quit")
	public void quit(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		HttpSession session = WebContext.getHttpSession();
		session.removeAttribute(OptimusAuthManager.LOGIN_NAME);
		System.err.println(OptimusAuthManager.USER+"OptimusAuthManager.USER");
		session.removeAttribute(OptimusAuthManager.USER);
		session.removeAttribute(OptimusAuthManager.SUPERADMIN);
	}


	/**获取用户可见菜单
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/getFunTreeByUser")
	@ResponseBody
	public List getFunTreeByUser(OptimusRequest req, OptimusResponse res)
			throws OptimusException {
		HttpSession session = req.getHttpRequest().getSession();
		String dlm = (String) session
				.getAttribute(OptimusAuthManager.LOGIN_NAME);
		boolean flag = false;
		if (Constants.SECURITY_AUTHCHECK) {
			if (session.getAttribute(OptimusAuthManager.SUPERADMIN) != null)
				flag = (Boolean) session
						.getAttribute(OptimusAuthManager.SUPERADMIN);
		}
		List funcList = authService.queryMenu(dlm, flag);
		Map rootNode = new HashMap();
		return funcList;
	}





	/**获取用户可见模块菜单
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/getMenuByUser")
	@ResponseBody
	public List getMenuByUser(OptimusRequest req, OptimusResponse res)
			throws OptimusException {
		String id=req.getParameter("id");
		HttpSession session = req.getHttpRequest().getSession();
		String dlm = (String) session
				.getAttribute(OptimusAuthManager.LOGIN_NAME);
		boolean flag = false;
		if (Constants.SECURITY_AUTHCHECK) {
			if (session.getAttribute(OptimusAuthManager.SUPERADMIN) != null)
				flag = (Boolean) session
						.getAttribute(OptimusAuthManager.SUPERADMIN);
		}
		List funcList = authService.queryAppMenu(dlm, flag,id);
		Map rootNode = new HashMap();
		return funcList;
	}
}

