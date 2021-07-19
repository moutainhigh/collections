<%
/** Title:			user_tree.jsp
 *  Description:
 *		标准WCM5.2 页面，用于“用户管理导航结构”。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WCM
 *  Created:		2004-12-26 16:12
 *  Vesion:			1.0
 *  Last EditTime:	2004-12-26/2004-12-26
 *	Update Logs:
 *		CH@2004-12-26 created the file 
 *
 *  Parameters:
 *		see user_tree.jsp
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.util.DebugTimer" %>
<%@ page import="com.trs.presentation.common.PageViewConstants" %>
<!------- WCM IMPORTS END ------------>
<%!boolean IS_DEBUG = false;%>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%
    DebugTimer aTimer = new DebugTimer();
	aTimer.start();
%>

<%
//TODO 权限校验

//4.初始化（获取数据）	
	

//5.权限校验

//6.业务代码	
	int nOperType = PageViewConstants.TREE_OPERATION_SYSUSER_MGR;
	
//7.结束
	out.clear();
%>

var nOperType	= <%=nOperType%>;
var sRootURL	= "<%=PageViewConstants.getOperRootURL(nOperType)%>";	
var sNodeURL	= "<%=PageViewConstants.getOperNodeURL(nOperType)%>";	

function getRootHref(){
	return sRootURL;
}

function getNodeHref( _nStatusId){
	return sNodeURL + "?StatusId="+_nStatusId;
}


document.write("<div style=\"overflow:auto;height:100%;width:194;\">");
var oUserTree	= new CTRSTree();
	oUserTree.addRootItem("所有用户", "U0", "所有用户列表", getRootHref(), false);				
		oUserTree.addItem("待开通", "U0<%=User.USER_STATUS_APPLY%>", "正在申请（新用户）", getNodeHref(<%=User.USER_STATUS_APPLY%>), "U0");
		oUserTree.addItem("已开通", "U0<%=User.USER_STATUS_REG%>", "已经注册（正常状态）", getNodeHref(<%=User.USER_STATUS_REG%>), "U0");
		oUserTree.addItem("已停用", "U0<%=User.USER_STATUS_FORBID%>", "禁止使用（用户停用）", getNodeHref(<%=User.USER_STATUS_FORBID%>), "U0");
		oUserTree.addItem("已删除", "U0<%=User.USER_STATUS_DEL%>", "已经删除（用户已删除）", getNodeHref(<%=User.USER_STATUS_DEL%>), "U0");
oUserTree.draw();
document.write("</div>");