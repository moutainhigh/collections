<%
/** Title:			user_addedit_dowith.jsp
 *  Description:
 *		WCM5.2 处理用户创建修改页面。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			wsw
 *  Created:		2004-12-16 10:36:04
 *  Vesion:			1.0
 *  Last EditTime:	2004-12-16 / 2004-12-16	
 *	Update Logs:
 *		wsw@2004-12-16 产生
 *
 *  Parameters:
 *		see user_addedit_dowith.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.cms.content.WCMObjHelper" %>
<%@ page import="com.trs.service.IGroupService" %>
<%@ page import="com.trs.service.IUserService" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	int nUserId   = currRequestHelper.getInt("UserId",  0);
	int nGroupId  = currRequestHelper.getInt("GroupId", 0);
	User  currUser  = null;
	Group currGroup = null;
	if(nUserId>0){
		currUser = User.findById(nUserId);
		if(currUser == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取ID为["+nUserId+"]的用户失败！");
		}
	}else{//create new user
		currUser = User.createNewInstance();
	}
	if(nGroupId>0){//create user in group management
		currGroup = Group.findById(nGroupId);
		if(currGroup == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取ID为["+nGroupId+"]的组织失败！");
		}
	}
//5.权限校验

//6.业务代码
	try{
		currUser = (User)WCMObjHelper.toWCMObj(currRequestHelper.getString("ObjectXML"), loginUser, nUserId, User.class);
	} catch(Exception ex){
		throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, "保存用户时因属性值不正确而失败中止！", ex);
	}

	
	IUserService currUserService = (IUserService)DreamFactory.createObjectById("IUserService");
	try{
	currUserService.save(currUser,currGroup);
	} catch(Exception ex){
		throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, "保存用户失败中止！", ex);
	}
	
	//如果是组下面创建，开通帐号
	
		if(currGroup!= null){
			try{
				currUserService.enable(currUser);
			} catch(Exception ex){
				throw new WCMException(ExceptionNumber.ERR_PROPERTY_NOTALLOW_EDIT , "开通帐号失败中止!", ex);
			}
		}
	
	
	
//7.结束
	out.clear();
%>

<HTML>
<HEAD>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <TITLE>TRS WCM 5.2 用户创建修改页面:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
    <LINK href="../style/style.css" rel="stylesheet" type="text/css">
</HEAD>

<BODY>
	<%if(IS_DEBUG){%>
		<a href="../wcm_use/page_config_reload.jsp">重新装载当前页面的配置信息</a><BR>
	<%}%>
<SCRIPT LANGUAGE="JavaScript">
window.returnValue = true;
window.close();
</SCRIPT>
</BODY>
</HTML>