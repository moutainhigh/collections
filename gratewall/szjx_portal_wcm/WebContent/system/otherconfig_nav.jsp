<%
/** Title:			otherconfig_nav.jsp
 *  Description:
 *		标准WCM5.2 页面，用于“文档管理”。
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
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentLinkType" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentLinkTypes" %>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%
if (request.getProtocol().equals("HTTP/1.0"))
	response.setHeader("Pragma", "cache");
if (request.getProtocol().equals("HTTP/1.1"))
	response.setHeader("Cache-Control", "cache");
%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化（获取数据）
String sRightValue = ( loginUser.isAdministrator())?"1":"0";
//7.结束
	out.clear();
%>
<html>
<head>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<LINK HREF="../style/rightclickmenu.css"  rel="stylesheet" type="text/css">
<script src="../js/CTRSRightClickMenu.js"></script>
<script LANGUAGE="JavaScript" src="../js/CTRSRightMenu_res_sysattribute.js"></script>
</head>
<BODY style="margin:5px" oncontextmenu="return false;" >
<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" >
	<TR >
	<TD class="navigation_content_table" MenuIndex="0" RightValue="<%=sRightValue%>">
	
	<!--WCM 导航树开始-->
	<script src="../js/CTRSTree_res_system.js"></script>
	<script src="../js/CTRSTree.js"></script>
	<script src="../js/CWCMTreeDepends.js"></script>
	<script src="../js/CWCMObj.js"></script>
	
	
	<script>
		var oWCMObj = new CWCMObj();
		oWCMObj.setProperty("MENUINDEX",0);
		oWCMObj.setProperty("RIGHTVALUE","<%=sRightValue%>");
		var oTRSTree = new CTRSTree();
		oTRSTree.addRootItem("属性配置", "S1", "属性配置","",false,oWCMObj);
	<%	if(AuthServer.hasRight(loginUser,null,WCMRightTypes.SYS_DOCSOURCE)){%>
		oTRSTree.addItem("文档来源", "S1_1", "文档来源", "../system/source_list.jsp", "S1",false,oWCMObj);
	<%	}
		if(AuthServer.hasRight(loginUser,null,WCMRightTypes.SYS_DOCSTATUS)){%>
		oTRSTree.addItem("文档状态", "S1_2", "文档状态", "../system/status_list.jsp", "S1",false,oWCMObj);
	<%	}
		if(AuthServer.hasRight(loginUser,null,WCMRightTypes.SYS_WCMBEAN)){%>
		oTRSTree.addItem("发布组件管理", "S1_3", "发布组件管理", "../publish/wcmtagbean_list.jsp", "S1",false,oWCMObj);
	<%	}
		if(AuthServer.hasRight(loginUser,null,WCMRightTypes.SYS_EVENTTYPE)){%>
		oTRSTree.addItem("日程事件类型", "S1_4", "日程事件类型", "../cooperation/eventtype_list.jsp", "S1",false,oWCMObj);
	<%	}
		if(AuthServer.hasRight(loginUser,null,WCMRightTypes.SYS_SECURITY)){%>
		oTRSTree.addItem("安全级别", "S1_5", "安全级别", "../document/security_list.jsp", "S1",false,oWCMObj);
	<%	}
		if(AuthServer.hasRight(loginUser,null,WCMRightTypes.SYS_RIGHTDEF)){%>
		oTRSTree.addItem("权限管理", "S1_6", "权限管理", "../auth/rightdef_list.jsp", "S1",false,oWCMObj);
	<%	}
		if(AuthServer.hasRight(loginUser,null,WCMRightTypes.SYS_OPERTYPE)){%>
		oTRSTree.addItem("操作类型", "S1_7", "操作类型", "../stat/opertype_list.jsp", "S1",false,oWCMObj);
	<%	}%>
	<%if(AuthServer.hasRight(loginUser,null,WCMRightTypes.SYS_CONTENTLINK)){%>
		oTRSTree.addRootItem("热词管理", "HW", "热词管理","../contentlink/contentlinktype_list.jsp",false,oWCMObj);
		<%
			ContentLinkTypes types = new ContentLinkTypes(loginUser);
			types.open(null);			
			ContentLinkType type = null;
			for(int i=0;i<types.size();i++){
				type = (ContentLinkType)types.getAt(i);
				if(type == null) continue;
		%>
			oTRSTree.addItem("<%=type.getTypeName()%>", "HW_<%=i%>", "<%=type.getTypeDesc()%>", "../contentlink/contentlink_list.jsp?LinkTypeId=<%=type.getId()%>", "HW",false,oWCMObj);
		<%
			}
		%>
	<%}%>
		oTRSTree.draw();
	</script>
	<!--WCM 导航树结束-->
	<script>
	var sRightValue = <%=sRightValue%>;
	function getRightValue(_nObjType, _nObjId, _sRightIndexes){
		return sRightValue;
	}

	function setAttrRight(){
		var oTRSAction = new CTRSAction("../auth/right_set.jsp");
		oTRSAction.setParameter("ObjId", 1);
		oTRSAction.setParameter("ObjType",1);
		oTRSAction.doDialogAction(800,500);
		return;
	}
	</script>
	</TD>
	</TR>
</TABLE>
</body>
</html>