<%
/** Title:			channels_get.jsp
 *  Description:
 *		标准WCM5.2 页面，用于“获取栏目列表”。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WCM
 *  Created:		2004-12-11 16:12
 *  Vesion:			1.0
 *  Last EditTime:	2004-12-11/2004-12-11
 *	Update Logs:
 *		CH@2004-12-11 created the file 
 *
 *  Parameters:
 *		see channels_get.jsp
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.cms.auth.persistent.Groups" %>
<%@ page import="com.trs.infra.persistent.ObjToXmlConverter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.service.IGroupService" %>
<%@ page import="com.trs.service.ServiceHelper" %>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化（获取数据）
	int nParentId = currRequestHelper.getInt("ParentId", 0);		
	String strGroupName = CMyString.showNull(currRequestHelper.getString("GroupName"));
	String sXMLInfo = "";
	Group prtGroup = Group.findById(nParentId);
	if(nParentId > 0 && prtGroup == null) {
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取ID为["+nParentId+"]的组织失败！");
	}
	

//5.权限校验

//6.业务代码
	IGroupService currGroupService = ServiceHelper.createGroupService();
	Groups currGroups = currGroupService.getChildren(prtGroup);
	Group currGroup = null;
	for(int i=0; i<currGroups.size(); i++) {
		currGroup = (Group)currGroups.getAt(i);
		if(currGroup.getName().equals(strGroupName)) {
			break;
		}
		currGroup = null;
	}

	if(currGroup != null){
		ObjToXmlConverter aXMLConvert = new ObjToXmlConverter();
		sXMLInfo = aXMLConvert.toXMLString(currGroup, null, "GroupId,GName");
	}
	

//7.结束
	out.clear();
%>
<%=sXMLInfo%>