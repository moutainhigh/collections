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

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
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
<%!boolean IS_DEBUG = false;
  IGroupService currGroupService = (IGroupService)DreamFactory.createObjectById("IGroupService");
%>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
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
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "指定的用户组["+nGroupId+"]没有找到！");
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

function TRSTreeItem_loadChildren(oTRSTree, _sParentId, _oCurrItem){
	var	oTRSAction = new CTRSAction('group_tree_nodes_get.jsp');
	oTRSAction.setParameter('OperType', nOperType);
	oTRSAction.setParameter('TreeType', nTreeType);
	oTRSAction.setParameter('ObjTreeName', '<%=CMyString.filterForJs(sObjTreeName)%>');
	oTRSAction.setParameter('GroupId', _sParentId);

	var sResult = oTRSAction.doXMLHttpAction();
	try{
		eval(sResult);
	}catch(error){
		alert(error.message);
	}
}

document.write("<div style=\"overflow:auto;height:100%;width:<%=nTreeWidth%>;\">");
var <%=CMyString.filterForJs(sObjTreeName)%>	= new CTRSTree("<%=CMyString.filterForJs(CMyString.showNull(currRequestHelper.getString("GroupIds")))%>");
<%=CMyString.filterForJs(sObjTreeName)%>.nType	= nTreeType;
if(nOperType==<%=PageViewConstants.TREE_OPERATION_GRPUSER_SEL%>) {
	<%=CMyString.filterForJs(sObjTreeName)%>.addRootItem("用户列表", "0", "用户列表", getRootHref(), false);
}
else {
	<%=CMyString.filterForJs(sObjTreeName)%>.addRootItem("用户组列表", "0", "用户组列表", getRootHref(), false);
}
<%
	StringBuffer sbDisabledGroupIds = new StringBuffer();
%>
	<%=toTreeNavJavascript(currGroupService, null, sObjTreeName,loginUser,0, isShowAll, sbDisabledGroupIds, false)%>
<%=CMyString.filterForJs(sObjTreeName)%>.arDisabledId = [<%=sbDisabledGroupIds.toString()%>];
<%=CMyString.filterForJs(sObjTreeName)%>.draw();
document.write("</div>");

//自动定位
window.onload = autoLocate;

<%
aTimer.stop();
if(IS_DEBUG)System.out.println("构造用户["+loginUser.getName()+"]用户组树所用时间为["+aTimer.getTime()+"]毫秒！");
%>

<%!
	public String toTreeNavJavascript(IGroupService _service,
			Group _parentGroup, String _sObjTreeName, User _loginUser, int _parentId, boolean _isShowAll, StringBuffer _sbDisabledGroupIds, boolean _bParentHasRight)throws WCMException {
		Groups currGroups = _service.getChildren(_parentGroup);
		Group currGroup = null;
		String sResult = "";
		//Groups userGroups = _loginUser.getGroups();
		Groups userGroups = getGroups(_loginUser);
		for (int i = 0; i < currGroups.size(); i++) {
			currGroup = (Group) currGroups.getAt(i);
			if (currGroup == null)
				continue;
			boolean bHasRightView = _bParentHasRight || (userGroups!=null && userGroups.indexOf(currGroup)!=-1)
				|| AuthServer.hasRight(_loginUser, currGroup);
			if (_isShowAll || bHasRightView){
				sResult += _sObjTreeName + ".addItem(\"" + CMyString.filterForJs(currGroup.getName())
						+ "\", " + currGroup.getId() + ", \"用户组["
						+ PageViewUtil.toHtml(currGroup.getName()) + "]\", getNodeHref("
						+ currGroup.getId() + ", nTreeType), \""+_parentId+"\", true);\n";
				//sResult += toTreeNavJavascript(_service, currGroup, _sObjTreeName,_loginUser,currGroup.getId(),_isShowAll, _sbDisabledGroupIds, bHasRightView);
			} else if(hasChild(currGroup) && hasRight(_loginUser, currGroup, userGroups)){
				if(_sbDisabledGroupIds.length()>0){
					_sbDisabledGroupIds.append(",");
				}
				_sbDisabledGroupIds.append(currGroup.getId());
				String sTemp = "";//toTreeNavJavascript(_service, currGroup, _sObjTreeName,_loginUser,currGroup.getId(),_isShowAll, _sbDisabledGroupIds, bHasRightView);
				if(sTemp.length() > 1)
					sResult += _sObjTreeName + ".addItem(\"" + CMyString.filterForJs(currGroup.getName())
						+ "\", " + currGroup.getId() + ", \"用户组["
						+ PageViewUtil.toHtml(currGroup.getName()) + "]\", null, \""+_parentId+"\", false);\n";
				sResult += sTemp;
			}
		}

		return sResult;
	}
	
	private boolean hasChild(Group currGroup) throws WCMException{	 
		Groups currGroups = currGroupService.getChildren(currGroup);
		return currGroups.size()!=0;
	}
	
	private boolean hasRight(User _loginUser, Group currGroup, Groups _userGroups) throws WCMException{	 
		Groups currGroups = currGroupService.getChildren(currGroup);
		Group currGroupinChild = null;
		for(int i=0;i<currGroups.size();i++){  
			currGroupinChild = (Group)currGroups.getAt(i);
			if (currGroupinChild == null)
				continue;
			if(_userGroups!=null && _userGroups.indexOf(currGroupinChild)!=-1)
				return true;
			if(AuthServer.hasRight(_loginUser, currGroupinChild))
				return true;
		}
		return false;
	}
	

	/**
	*获取当前用户_loginUser所在的用户组（包含用户组的祖先组）
	*/
	private Groups getGroups(User _loginUser)throws WCMException{
		Groups userGroups = _loginUser.getGroups();
		Group currGroup = null;
		for(int i=0, length = userGroups.size() - 1; i >= 0;i--){  
			currGroup = (Group)userGroups.getAt(i);
			appendAncestorsGroup(currGroup, userGroups);
		}
		return userGroups;
	}

	/**
	*追加当前用户组_currGroup的祖先组到_userGroups中
	*/
	private Groups appendAncestorsGroup(Group _currGroup, Groups _userGroups)throws WCMException{
		while(_currGroup != null){
			_userGroups.addElement(_currGroup);
			_currGroup = _currGroup.getParent();
		}
		return _userGroups;
	}
%>