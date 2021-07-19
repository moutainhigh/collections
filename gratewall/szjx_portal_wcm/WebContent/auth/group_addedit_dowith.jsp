<%
/** Title:			group_addedit_dowith.jsp
 *  Description:
 *		WCM5.2 处理组织添加修改。
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
 *		see group_addedit_dowith.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.cms.content.WCMObjHelper" %>
<%@ page import="com.trs.service.IGroupService" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	int nGroupId  = currRequestHelper.getInt("GroupId", 0);
	Group currGroup = null;
	if(nGroupId>0){
		currGroup = Group.findById(nGroupId);
		if(currGroup == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取ID为["+nGroupId+"]的组织失败！");
		}
	}else{//nGroupId==0 create a new group
		currGroup = Group.createNewInstance();
	}
//5.权限校验

//6.业务代码
	boolean isAdmin = ContextHelper.getLoginUser().isAdministrator();
	try{
		currGroup = (Group)WCMObjHelper.toWCMObj(currRequestHelper.getString("ObjectXML"), loginUser, nGroupId, Group.class);
	} catch(Exception ex){
		throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, "保存组织时因属性值不正确而失败中止！", ex);
	}
	IGroupService currGroupService = (IGroupService)DreamFactory.createObjectById("IGroupService");
	currGroupService.save(currGroup);
//7.结束
	out.clear();
%>

<HTML>
<HEAD>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <TITLE>TRS WCM 5.2 处理组织添加修改::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
    <LINK href="../style/style.css" rel="stylesheet" type="text/css">
</HEAD>

<BODY>
<SCRIPT LANGUAGE="JavaScript">
var arReturn = new Array();
arReturn[0] = true;
arReturn[1] = "<%=getLocatePath(currGroup)%>";
arReturn[2] = "<%=currGroup.getId()%>";
arReturn[3] = "<%=isAdmin?"2":"1"%>";

window.returnValue = arReturn;
window.close();
</SCRIPT>
</BODY>
</HTML>
<%!
	private String getLocatePath(Group _currGroup)throws WCMException{
		if(_currGroup == null)
			return "";

		String sPath = String.valueOf(_currGroup.getId());
		Group tempGrp = _currGroup;
		int nParentId;
		while((nParentId = tempGrp.getParentId()) != 0){
			sPath = nParentId + "," + sPath;
			tempGrp = tempGrp.getParent();
			if(tempGrp == null) return sPath;
		}
		return sPath;
	}
%>