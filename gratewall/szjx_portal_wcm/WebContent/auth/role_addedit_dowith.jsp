<%
/** Title:			role_addedit_dowith.jsp
 *  Description:
 *		WCM5.2 处理角色添加修改页面
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
 *		see role_addedit_dowith.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.persistent.Role" %>
<%@ page import="com.trs.cms.content.WCMObjHelper" %>
<%@ page import="com.trs.service.IRoleService" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	int nRoleId  = currRequestHelper.getInt("RoleId", 0);
	Role currRole = null;
	if(nRoleId>0){
		currRole = Role.findById(nRoleId);
		if(currRole == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取ID为["+nRoleId+"]的角色失败！");
		}
	}else{//nRoleId==0 create a new group
		currRole = Role.createNewInstance();
	}
//5.权限校验

//6.业务代码
	try{
		currRole = (Role)WCMObjHelper.toWCMObj(currRequestHelper.getString("ObjectXML"), loginUser, nRoleId, Role.class);
	} catch(Exception ex){
		throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, "保存角色时因属性值不正确而失败中止！", ex);
	}
	if(nRoleId == 1){
		currRole.setRemoveable(false);
	} else if(nRoleId == 2){
		currRole.setRemoveable(false);
		currRole.setViewable(false);
	}
	IRoleService currRoleService = (IRoleService)DreamFactory.createObjectById("IRoleService");
	currRoleService.save(currRole);
//7.结束
	out.clear();
%>

<HTML>
<HEAD>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <TITLE>TRS WCM 5.2 处理角色添加修改页面:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
    <LINK href="../style/style.css" rel="stylesheet" type="text/css">
</HEAD>

<BODY>
<SCRIPT LANGUAGE="JavaScript">
var arReturn = new Array();
arReturn[0] = true;
arReturn[1] = "<%=currRole.getId()%>";
arReturn[2] = "<%=currRole.getId()%>";

window.returnValue = arReturn;
window.close();
</SCRIPT>
</BODY>
</HTML>