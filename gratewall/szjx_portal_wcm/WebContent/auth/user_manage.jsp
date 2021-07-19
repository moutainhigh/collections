<%
/** Title:			user_manager.jsp
 *  Description:
 *		WCM5.2 用户的状态和在组织中是否为管理员的管理页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WSW
 *  Created:		2004/12/15
 *  Vesion:			1.0
 *  Last EditTime:	
 *	Update Logs:
 *
 *  Parameters:
 *		see user_manager.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.cms.auth.persistent.Users" %>
<%@ page import="com.trs.service.IGroupService" %>
<%@ page import="com.trs.service.IUserService" %>
<!------- WCM IMPORTS END ------------>


<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%!
	private final static int SET_GROUPADMIN	   = 1;
	private final static int CANCEL_GROUPADMIN = 2;
	private final static int START_ACCOUNT	   = 3;
	private final static int STOP_ACCOUNT	   = 4;
%>
<%
//4.初始化（获取数据）
	int nGroupId  = currRequestHelper.getInt("GroupId", 0);
	int nOperType = currRequestHelper.getInt("OperType", 0);
	Group currGroup = null;
	Users currUsers = null;
	if(nGroupId>0){
		currGroup = Group.findById(nGroupId);
		if(currGroup==null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取ID为["+nGroupId+"]的组织失败！");
		}
	}
	currUsers = Users.findByIds(loginUser, currRequestHelper.getString("UserIds"));
	if(currUsers==null){
		throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, "获取用户集合失败，操作中止！");
	}
//5.权限校验
	if(nGroupId>0){
		if(!(AuthServer.hasRight(loginUser,currGroup) || loginUser.isAdministrator())){
			throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, "对不起，您没有权限进行此项操作！");
		}
	}else{
		if(!loginUser.isAdministrator()){
			throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, "对不起，您没有权限进行此项操作！");
		}
	}
//6.业务代码
	IGroupService currGroupService = (IGroupService)DreamFactory.createObjectById("IGroupService");
	IUserService  currUserService  = (IUserService)DreamFactory.createObjectById("IUserService");
	switch(nOperType){
		case SET_GROUPADMIN:		//指定为组管理员
			if(nGroupId>0)
				setGroupAdmin(currGroupService, currGroup, currUsers);
			break;
		case CANCEL_GROUPADMIN:		//取消组管理身份
			if(nGroupId>0)
				cancelGroupAdmin(currGroupService, currGroup, currUsers);
			break;
		case START_ACCOUNT:			//开通账号
			enableUser(currUserService, currUsers);
			break;
		case STOP_ACCOUNT:			//停用账户
			disableUser(currUserService, currUsers);
			break;
		default:
			throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "无效的操作类型！");
	}
//7.结束
%>

<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TRS WCM 5.2 用户状态管理::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
</head>
<BODY topmargin="0" leftmargin="0">
	<%if(IS_DEBUG){%>
		<a href="../wcm_use/page_config_reload.jsp">重新装载当前页面的配置信息</a><BR>
	<%}%>
<SCRIPT LANGUAGE="JavaScript">
<% if (nOperType == CANCEL_GROUPADMIN && !AuthServer.hasRight(loginUser,currGroup) ){

%>
	window.returnValue = false;

<%}else{%>

	window.returnValue = true;
<%}%>
	window.close();
</SCRIPT>
</BODY>
</HTML>

<%!
	private void setGroupAdmin(IGroupService _groupService, Group _group, Users _users) throws WCMException{
		if(_groupService==null){
			throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "传入IGroupService为空！");
		}
		if(_group==null){
			throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "传入组织为空！");
		}
		if(_users==null){
			throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "传入用户集合为空！");
		}
		User currUser = null;
		for(int i=0; i<_users.size(); i++){
			try{
				currUser = (User)_users.getAt(i);
				_groupService.addManager(currUser, _group);
			}catch(WCMException e){
				throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, "对第["+i+"]个用户操作时出现异常！操作中止！", e);
			}
		}
	}

	private void cancelGroupAdmin(IGroupService _groupService, Group _group, Users _users) throws WCMException{
		if(_groupService==null){
			throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "传入IGroupService为空！");
		}
		if(_group==null){
			throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "传入组织为空！");
		}
		if(_users==null){
			throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "传入用户集合为空！");
		}
		User currUser = null;
		for(int i=0; i<_users.size(); i++){
			try{
				currUser = (User)_users.getAt(i);
				_groupService.removeManager(currUser, _group);
			}catch(WCMException e){
				throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, "对第["+i+"]个用户操作时出现异常！操作中止！", e);
			}
		}
	}

	private void enableUser(IUserService _userService, Users _users) throws WCMException{
		if(_userService==null){
			throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "传入IUserService为空！");
		}
		if(_users==null){
			throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "传入用户集合为空！");
		}
		User currUser = null;
		for(int i=0; i<_users.size(); i++){
			try{
				currUser = (User)_users.getAt(i);
				_userService.enable(currUser);
			}catch(WCMException e){
				throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, "对第["+i+"]个用户操作时出现异常！操作中止！", e);
			}
		}
	}

	private void disableUser(IUserService _userService, Users _users) throws WCMException{
		if(_userService==null){
			throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "传入IUserService为空！");
		}
		if(_users==null){
			throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "传入用户集合为空！");
		}
		User currUser = null;
		for(int i=0; i<_users.size(); i++){
			try{
				currUser = (User)_users.getAt(i);
				_userService.disable(currUser);
			}catch(WCMException e){
				throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, "对第["+i+"]个用户操作时出现异常！操作中止！", e);
			}
		}
	}
%>