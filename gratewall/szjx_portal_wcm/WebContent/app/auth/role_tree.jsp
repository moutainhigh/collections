<%
/** Title:			role_tree.jsp
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
 *		see role_tree.jsp
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.persistent.Role" %>
<%@ page import="com.trs.cms.auth.persistent.Roles" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.DebugTimer" %>
<%@ page import="com.trs.presentation.common.PageViewConstants" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.service.IUserService" %>
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
	int nRoleOperType		= currRequestHelper.getInt("OperType", PageViewConstants.TREE_OPERATION_DOCUMENT);
	int nRoleTreeType		= currRequestHelper.getInt("TreeType", PageViewConstants.TREE_TYPE_NORMAL);
	String sObjTreeName = CMyString.filterForJs(CMyString.transDisplay(currRequestHelper.getString("ObjTreeName")));
	String strDisabledRoles = CMyString.filterForJs(CMyString.transDisplay(currRequestHelper.getString("DisabledRoles")));
	String sRoleIds = CMyString.filterForJs(CMyString.transDisplay(currRequestHelper.getString("RoleIds")));

	boolean isShowAll = currRequestHelper.getBoolean("ShowAll", false);

	boolean bFilterRight = currRequestHelper.getBoolean("FilterRight", false);
/*
	int	nRoleId		= currRequestHelper.getInt("RoleId", 0);			
	Role currRole		= null;
	if(nRoleId>0){
		currRole = Role.findById(nRoleId);
		if(currRole == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "指定的角色["+nRoleId+"]没有找到！");
		}
	}
*/	
	

//5.权限校验

//6.业务代码
	
	
	
//7.结束
	out.clear();
%>

var nRoleTreeType	= <%=nRoleTreeType%>;
var nRoleOperType	= <%=nRoleOperType%>;
var sRoleRootURL	= "<%=PageViewConstants.getOperRootURL(nRoleOperType)%>";	
var sRoleNodeURL	= "<%=PageViewConstants.getOperNodeURL(nRoleOperType)%>";	

function getRoleRootHref(_nTreeType){
	if(nRoleOperType == -1)return "";

	return sRoleRootURL;
}

function getRoleNodeHref( _nGroupId,  _nTreeType){
	if(nRoleOperType == -1)return "";

	return sRoleNodeURL + "?RoleId="+_nGroupId;
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

document.write("<div style=\"overflow:auto;height:100%;width:100%;\">");
var <%=CMyString.filterForJs(sObjTreeName)%>	= new CTRSTree("<%=sRoleIds%>",null,"","<%=CMyString.filterForJs(strDisabledRoles)%>");
<%=CMyString.filterForJs(sObjTreeName)%>.nType	= nRoleTreeType;

<%=CMyString.filterForJs(sObjTreeName)%>.addRootItem("<%=LocaleServer.getString("auth.label.rolelist", "角色列表")%>", "0", "", getRoleRootHref(), false);				
	<%=toTreeNavJavascript(loginUser, sObjTreeName, isShowAll ,bFilterRight)%>
<%=CMyString.filterForJs(sObjTreeName)%>.draw();
document.write("</div>");

//自动定位
window.onload = autoLocate;

<%
aTimer.stop();
if(IS_DEBUG)System.out.println(CMyString.format(LocaleServer.getString("group_tree.jsp.createtime", "构造用户[{0}]用户组树所用时间为[{1}]毫秒！"), new Object[]{loginUser.getName(),String.valueOf(aTimer.getTime())}));
%>

<%!
	public String toTreeNavJavascript(User _currUser, String _sObjTreeName, boolean _isShowAll, boolean _bFilterRight)throws WCMException {
		IUserService aUserService = (IUserService)DreamFactory.createObjectById("IUserService");
        WCMFilter filter	= new WCMFilter("", "", "ROLEID ASC");
		if(!_isShowAll) {
			filter.setWhere("VIEWABLE=1");
		}
		Roles currRoles		= Roles.openWCMObjs(_currUser, filter);
		Role currRole = null;
        String sResult = "";
		String sCrUserName="";
        for (int i = 0; i < currRoles.size(); i++) {
            currRole = (Role) currRoles.getAt(i);
            if (currRole == null)
                continue;
			if(currRole.isSystemRole()){
				continue;
			}
			sCrUserName = currRole.getCrUserName();

			//需要过滤没有权限的角色
			if(_bFilterRight){
				if(_currUser.isAdministrator() || (aUserService.isManagerOfRole(_currUser) && sCrUserName.equalsIgnoreCase(_currUser.getName()))){

					sResult += _sObjTreeName + ".addItem(\"" + CMyString.filterForJs(CMyString.transDisplay(currRole.getName()))
							+ "\", " + currRole.getId() + ", null, getRoleNodeHref("
							+ currRole.getId() + ", nRoleTreeType), \"0\", false);\n";    
				}
			}else{
				sResult += _sObjTreeName + ".addItem(\"" + CMyString.filterForJs(CMyString.transDisplay(currRole.getName()))
							+ "\", " + currRole.getId() + ", null, getRoleNodeHref("
							+ currRole.getId() + ", nRoleTreeType), \"0\", false);\n";
			}
            
        }
        return sResult;
    }

%>