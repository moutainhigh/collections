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
 *
 *  Parameters:
 *		see workertype_tree.jsp
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.common.job.JobWorkerType" %>
<%@ page import="com.trs.components.common.job.JobWorkerTypes" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.DebugTimer" %>
<%@ page import="com.trs.presentation.common.PageViewConstants" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
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

	int	nWorkerTypeId	= currRequestHelper.getInt("TypeId", 0);			
	JobWorkerType currType		= null;
	if(nWorkerTypeId>0){
		currType = JobWorkerType.findById(nWorkerTypeId);
		if(currType == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "指定的JobWorkerType["+nWorkerTypeId+"]没有找到！");
		}
	}
	
	

//5.权限校验

//6.业务代码
	
	
	
//7.结束
	out.clear();
%>

var nTreeType	= <%=nTreeType%>;
var nOperType	= <%=nOperType%>;
var sRootURL	= "<%=PageViewConstants.getOperRootURL(nOperType)%>";	
var sNodeURL	= "<%=PageViewConstants.getOperNodeURL(nOperType)%>";	

function getRootHref(_nTreeType){
	if(nOperType == -1)return "";

	return sRootURL;
}

function getNodeHref( _nTypeId,  _nTreeType){
	if(nOperType == -1)return "";

	return sNodeURL + "?TypeId="+_nTypeId;
}

function TRSTreeItem_loadChildren(_oTRSTree, _sParentId, _oCurrItem){
}

function locateItem(_sLocatePath, _bLocateParent, _bReloadParent){
	if(_sLocatePath == null || _sLocatePath.length<=0)
		return;

	<%=CMyString.filterForJs(sObjTreeName)%>.locate(_sLocatePath, _bLocateParent, _bReloadParent);
}

function autoLocate(){
	var sLocation = window.location.href;
	var sParams = sLocation.substring(sLocation.indexOf("?") + 1);
	if(sParams.indexOf("LocatePath=") < 0)
		return;
	var arParams = sParams.split("&");
	var sLocatePath = "";
	for(var i=0; i<arParams.length; i++){
		if(arParams[i].indexOf("LocatePath=")>=0){
			sLocatePath = arParams[i];
			sLocatePath = sLocatePath.substring(sLocatePath.indexOf("=") + 1);
		}
	}
	if(sLocatePath != null && sLocatePath.length > 0)
		locateItem(sLocatePath);
}

document.write("<div style=\"overflow:auto;height:100%;width:194;\">");
var <%=CMyString.filterForJs(sObjTreeName)%>	= new CTRSTree();
<%=CMyString.filterForJs(sObjTreeName)%>.nType	= nTreeType;

<%=CMyString.filterForJs(sObjTreeName)%>.addRootItem("计划调度", "0", "计划调度", getRootHref(), false);
	<%=toTreeNavJavascript(loginUser, sObjTreeName)%>
<%=CMyString.filterForJs(sObjTreeName)%>.draw();
document.write("</div>");

//自动定位
window.onload = autoLocate;

<%
aTimer.stop();
if(IS_DEBUG)System.out.println("构造用户["+loginUser.getName()+"]计划调度类型树所用时间为["+aTimer.getTime()+"]毫秒！");
%>

<%!
	public String toTreeNavJavascript(User _currUser, String _sObjTreeName)throws WCMException {
		WCMFilter filter	= new WCMFilter("", "", "OperId");
		JobWorkerTypes currTypes = JobWorkerTypes.openWCMObjs(_currUser, filter);
		JobWorkerType currType = null;
		String sResult = "";
		for (int i = 0; i < currTypes.size(); i++) {
			currType = (JobWorkerType) currTypes.getAt(i);
			if (currType == null || currType.getId() == 100 || currType.getId() == 1 || currType.getId() == 4 || currType.getId() == 6)
				continue;

			sResult += _sObjTreeName + ".addItem(\"" + CMyString.filterForJs(currType.getWorkerName())
					+ "\", " + currType.getId() + ", \"类型["
					+ PageViewUtil.toHtml(currType.getWorkerName()) + "]\", getNodeHref("
					+ currType.getId() + ", nTreeType), \"0\", false);\n";
		}

		return sResult;
	}

%>