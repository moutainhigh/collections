<%
/** Title:			group_tree.jsp
 *  Description:
 *		标准WCM5.2 页面，用于“用户组导航结构”。
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
 *		see group_tree.jsp
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.cms.auth.persistent.Groups" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.DebugTimer" %>
<%@ page import="com.trs.presentation.common.PageViewConstants" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.service.IGroupService" %>
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

	int	nGroupId		= currRequestHelper.getInt("GroupId", 0);			
	Group currGroup		= null;
	if(nGroupId>0){
		currGroup = Group.findById(nGroupId);
		if(currGroup == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("group_tree.obj.not.found","指定的用户组[{0}]没有找到！"),new int[]{nGroupId}));
		}
	}
	
	int nTreeWidth = currRequestHelper.getInt("TreeWidth", 194);

	boolean isShowAll = currRequestHelper.getBoolean("ShowAll", false);
	

//5.权限校验

//6.业务代码
	WCMFilter filter = new WCMFilter("", "", "");
	
	IGroupService currGroupService = (IGroupService)DreamFactory.createObjectById("IGroupService");
	//Groups currGroups	= currGroupService.getChildren(Group);

//7.结束
	out.clear();
%>

var nTreeType	= <%=nTreeType%>;
var nOperType	= <%=nOperType%>;
var sRootURL	= "<%=PageViewConstants.getOperRootURL(nOperType)%>";	
var sNodeURL	= "<%=PageViewConstants.getOperNodeURL(nOperType)%>";	

function getRootHref(_nTreeType){
	if(nOperType == -1)
		return "";

	return sRootURL;
}

function getNodeHref( _nGroupId,  _nTreeType){
	if(nOperType == -1)
		return "";

	return sNodeURL + "?GroupId="+_nGroupId;
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

document.write("<div style=\"overflow:auto;height:100%;width:<%=nTreeWidth%>;\">");
var <%=CMyString.filterForJs(sObjTreeName)%>	= new CTRSTree("<%=CMyString.filterForJs(CMyString.showNull(currRequestHelper.getString("GroupIds")))%>");
<%=CMyString.filterForJs(sObjTreeName)%>.nType	= nTreeType;
if(nOperType==<%=PageViewConstants.TREE_OPERATION_GRPUSER_SEL%>) {
	<%=CMyString.filterForJs(sObjTreeName)%>.addRootItem("<%=LocaleServer.getString("group.label.userlist1", "用户列表")%>", "0", "", getRootHref(), false);
}
else {
	<%=CMyString.filterForJs(sObjTreeName)%>.addRootItem("<%=LocaleServer.getString("group.label.grouplist", "用户组列表")%>", "0", "", getRootHref(), false);
}
	<%=toTreeNavJavascript(currGroupService, null, sObjTreeName,loginUser,0, isShowAll)%>
<%=CMyString.filterForJs(sObjTreeName)%>.draw();
document.write("</div>");

//自动定位
window.onload = autoLocate;
document.getElementById(<%=nGroupId%>).click();
<%
aTimer.stop();
if(IS_DEBUG)System.out.println(CMyString.format(LocaleServer.getString("group_tree.jsp.createtime", "构造用户[{0}]用户组树所用时间为[{1}]毫秒！"), new String[]{loginUser.getName(),String.valueOf(aTimer.getTime())}));
%>

<%!
	public String toTreeNavJavascript(IGroupService _service,
			Group _parentGroup, String _sObjTreeName, User _loginUser, int _parentId, boolean _isShowAll)throws WCMException {
		Groups currGroups = _service.getChildren(_parentGroup);
		Group currGroup = null;
		String sResult = "";
		for (int i = 0; i < currGroups.size(); i++) {
			currGroup = (Group) currGroups.getAt(i);
			if (currGroup == null)
				continue;
			if (_isShowAll || AuthServer.hasRight(_loginUser, currGroup)){
				sResult += _sObjTreeName + ".addItem(\"" + CMyString.filterForJs(currGroup.getName())
						+ "\", " + currGroup.getId() + ", null, getNodeHref("
						+ currGroup.getId() + ", nTreeType), \""+_parentId+"\", false);\n";
				sResult += toTreeNavJavascript(_service, currGroup, _sObjTreeName,_loginUser,currGroup.getId(),_isShowAll);
			} else {
				String sTemp = toTreeNavJavascript(_service, currGroup, _sObjTreeName,_loginUser,currGroup.getId(),_isShowAll);
				if(sTemp.length() > 1)
					sResult += _sObjTreeName + ".addItem(\"" + CMyString.filterForJs(currGroup.getName())
						+ "\", " + currGroup.getId() + ", null, null, \""+_parentId+"\", false);\n";
				sResult += sTemp;
			}
		}

		return sResult;
	}
%>