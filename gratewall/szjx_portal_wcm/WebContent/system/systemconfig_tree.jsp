<%
/** Title:			workertype_tree.jsp
 *  Description:
 *		标准WCM5.2 页面，用于“角色导航结构”。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WCM
 *  Created:		2004-12-19 16:12
 *  Vesion:			1.0
 *  Last EditTime:	2004-12-19/2004-12-19
 *	Update Logs:
 *		CH@2004-12-19 created the file 
 *		niuzhao@2005-12-21 屏蔽了所有系统配置信息的查看页面，见Bug 2176
 *  Parameters:
 *		see workertype_tree.jsp
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
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
	int nOperType		= currRequestHelper.getInt("OperType", PageViewConstants.TREE_OPERATION_DOCUMENT);
	int nTreeType		= currRequestHelper.getInt("TreeType", PageViewConstants.TREE_TYPE_NORMAL);
	String sObjTreeName = currRequestHelper.getString("ObjTreeName");
	String sRootItemName = currRequestHelper.getString("RootItemName");


//5.权限校验

//6.业务代码
	
	
	
//7.结束
	out.clear();
%>

var nTreeType	= <%=nTreeType%>;
var nOperType	= <%=nOperType%>;
var sRootURL	= "<%=PageViewConstants.getOperRootURL(nOperType)%>";	
var sNodeURL	= "<%=PageViewConstants.getOperNodeURL(nOperType)%>";	

function getRootHref(){
	if(nOperType == -1)return "";

	return sRootURL;
}

function getNodeHref( _nTypeId){
	if(nOperType == -1)return "";

	return sNodeURL + "?ConfigType="+_nTypeId;
}

document.write("<div style=\"overflow:auto;height:100%;width:194;\">");
<%=toTreeNavJavascript(sObjTreeName,sRootItemName)%>
document.write("</div>");
<%
aTimer.stop();
if(IS_DEBUG)System.out.println("构造用户["+loginUser.getName()+"]JobWorker类型树所用时间为["+aTimer.getTime()+"]毫秒！");
%>

<%!
	public String toTreeNavJavascript(String _sObjTreeName, String _sRootItemName)throws WCMException {
		String sResult = "";
		sResult += "var " + _sObjTreeName + " = new CTRSTree();\n";
		sResult += _sObjTreeName + ".nType = nTreeType;\n";
		sResult += _sObjTreeName + ".addRootItem(\"" + _sRootItemName + "\",\"S2\",\"" + _sRootItemName
				+ "\",\"\",false);\n";
		ConfigServer currConfigServer = ConfigServer.createInstance();
		int[] arConfigTypeIds = currConfigServer.getTypeIds();
		String[] arConfigTypeNames = currConfigServer.getTypeNames();
		for(int i=0;i<arConfigTypeIds.length;i++) {
			sResult += _sObjTreeName + ".addItem(\"" + arConfigTypeNames[i]
				+ "\","	+ "\"S2_"+(i+1) + "\",\"类型[" + arConfigTypeNames[i]
				+ "]\",getNodeHref(" + arConfigTypeIds[i] + "),\"S2\",false);\n";
		}
		sResult += _sObjTreeName + ".draw();\n";
		return sResult;
	}

%>