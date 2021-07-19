<%
/** Title:			site_delete.jsp
 *  Description:
 *		WCM5.2 组织删除页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WSW
 *  Created:		2004/12/15
 *  Vesion:			1.0
 *  Last EditTime:	
 *	Update Logs:
 *
 *  Parameters:
 *		see site_delete.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.cms.auth.persistent.Groups" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.service.IGroupService" %>
<!------- WCM IMPORTS END ------------>


<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化（获取数据）
	String strGroupIds= CMyString.showNull(currRequestHelper.getString("GroupIds"));
	Groups currGroups = Groups.findByIds(loginUser, strGroupIds);

//5.权限校验
	boolean isAdmin = ContextHelper.getLoginUser().isAdministrator();
	int nParentId = 0;
	String strLocatePath = "0";
	if(currGroups.size() == 1) {
		Group currGroup = (Group)currGroups.getAt(0);
		nParentId = currGroup.getParentId();
		if(nParentId > 0) 
			strLocatePath = getLocatePath(currGroup.getParent(), loginUser, isAdmin);
	}
//6.业务代码
	IGroupService currGroupService = (IGroupService)DreamFactory.createObjectById("IGroupService");
	currGroupService.delete(strGroupIds);
//7.结束
%>

<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TRS WCM 5.2 组织删除页面::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
</head>
<BODY>
<SCRIPT LANGUAGE="JavaScript">
var arReturn = new Array();
arReturn[0] = true;
arReturn[1] = "<%=strLocatePath%>";
arReturn[2] = "<%=nParentId%>";
arReturn[3] = "<%=isAdmin?"2":"1"%>";

window.returnValue = arReturn;
window.close();
</SCRIPT>
</BODY>
</HTML>
<%!
	private String getLocatePath(Group _currGroup, User _loginUser, boolean _isAdmin)throws WCMException{
		if(_currGroup == null)
			return "";

		String sPath = String.valueOf(_currGroup.getId());
		Group parentGroup = getParent(_currGroup, _loginUser, _isAdmin);
		if(parentGroup == null)
			return sPath;

		return getLocatePath(parentGroup, _loginUser, _isAdmin) + "," + sPath;
	}

	private Group getParent(Group _currGroup, User _loginUser, boolean _isAdmin) throws WCMException {
		if(_isAdmin) {
			return _currGroup.getParent();
		} else {
			Group parentGroup = _currGroup.getParent();
			while(true) {
				if(parentGroup == null)
					break;
				if(AuthServer.hasRight(_loginUser,parentGroup))
					break;
				parentGroup = parentGroup.getParent();
			}
			return parentGroup;
		}
	}
%>