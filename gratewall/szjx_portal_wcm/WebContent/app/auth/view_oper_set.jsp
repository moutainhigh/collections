<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/errorforAuth.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor"%>

<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.domain.GroupMgr" %>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.cms.auth.persistent.Groups" %>
<%@ page import="com.trs.cms.auth.persistent.ObjectMember" %>
<%@ page import="com.trs.cms.auth.persistent.ObjectMembers" %>
<%@ page import="com.trs.cms.auth.persistent.Role" %>
<%@ page import="com.trs.cms.auth.persistent.Users" %>
<%@ page import="com.trs.cms.content.CMSObj" %>

<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.infra.persistent.BaseObj" %>
<%@ page import="com.trs.infra.util.CMyString" %>

<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.content.persistent.BaseChannel" %>

<%@ page import="com.trs.service.IUserService" %>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="java.util.List" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%
	//初始化(获取数据)
	int nObjType = currRequestHelper.getInt("ObjType", 0);
	int nObjId = currRequestHelper.getInt("ObjId", 0);
	boolean bFilterRight = currRequestHelper.getBoolean("FilterRight", false);
	//String sObjIds = request.getParameter("ObjId");
	if(nObjId == 0)
		throw new WCMException(CMyString.format(LocaleServer.getString("view_oper_set.auth.label.noviewperssion", "新建模式不允许查看/设置可访问可操作成员!Type:[{0}] Id:[{1}]"), new String[]{String.valueOf(nObjType), Integer.toString(nObjId)}));

	//权限校验
	BaseChannel baseChannel = null;
    switch (nObjType) {
        case Channel.OBJ_TYPE:
            baseChannel = Channel.findById(nObjId);
			/*if (!AuthServer.hasRight(loginUser, baseChannel, WCMRightTypes.CHNL_SETRIGHT)) {
				throw new WCMException("您没有权限维护当前对象的成员！对象信息：[" + baseChannel + "]");
			}*/
            break;
        case WebSite.OBJ_TYPE:
            baseChannel = WebSite.findById(nObjId);
			/*if (!AuthServer.hasRight(loginUser, baseChannel, WCMRightTypes.Site_SETRIGHT)) {
				throw new WCMException("您没有权限维护当前对象的成员！对象信息：[" + baseChannel + "]");
			}*/
            break;
        default:
            throw new WCMException(CMyString.format(LocaleServer.getString("view_oper_set.jsp.objnotfound_a", "不支持您指定的对象类型！[ObjType={0}]"), new int[]{nObjType}));
    }
	if (baseChannel == null)
		throw new WCMException(CMyString.format(LocaleServer.getString("view_oper_set.jsp.objnotfound", "您指定的对象不存在！[ObjType={0}, ObjId={1}]"), new int[]{nObjType,nObjId}));
	
	List children = baseChannel.getChildren(loginUser);
	boolean bHasChildren = children != null && children.size()>0;

	//获取对当前对象可见和可操作的成员
	JSPRequestProcessor processor = new JSPRequestProcessor(request,response);
	String sServiceId = "wcm61_objectmember",sMethodName="queryMembersOfObject";
	ObjectMembers objMembers = (ObjectMembers)processor.excute(sServiceId, sMethodName);

	//结束
	out.clear();
%>
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE><%=LocaleServer.getString("auth.label.viewoperset", "可访问可操作设置")%>::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<base target="_self">
<link href="style.css" rel="stylesheet" type="text/css" />
<link href="auth_index.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/resource/widget.css" rel="stylesheet" type="text/css" />
<style>
	html, body{
		width:100%;
		height:100%;
	}
	.contentTd{
		border-width:0px 1px;
		border-color:#9d9d9d;
		border-style:solid;
		width:100%;
		height:100%;
	}
	.no_object_found{font-size:14px;color:gray;height:100px;line-height:100px;font-style:italic;padding-left:15px;margin-top:30px;background:white;text-align:center;}

	.headdesc{overflow:hidden;text-overflow:ellipsis;white-space:nowrap;}
	.ext-ie8 .wcm-btn{display:inline;}
</style>
<script type="text/javascript" src="../js/data/locale/wcm52.js"></script>
<script src="../js/wcm52/CTRSHTMLElement.js"></script>
<script src="../js/wcm52/CTRSHTMLTr.js"></script>  
<script src="../js/wcm52/CTRSSimpleTab.js"></script>
<script src="../js/wcm52/CTRSArray.js"></script>

<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CWCMObj.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CWCMObjHelper.js"></SCRIPT>

<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSButton.js"></script>  

<script src="../js/wcm52/CTRSHashtable.js"></script>
<script src="../js/wcm52/CTRSRequestParam.js"></script>
<script src="../js/wcm52/CTRSAction.js"></script>


<script>
//global variable definition.
var m_nObjType = <%=nObjType%>;
var m_nObjId = <%=nObjId%>;
var m_bHasChildren = <%=bHasChildren%>;
var loginUserId = <%=loginUser.getId()%>;
var loginUserName = '<%=CMyString.filterForJs(loginUser.getName())%>';
var needRefresh = false;
var OBJ_WEBSITE = 103;
var OBJ_CHANNEL = 101;
var TYPE_WCMOBJ_USER	= 204;
var TYPE_WCMOBJ_GROUP	= 201;
var TYPE_WCMOBJ_ROLE	= 203;
var WEBSITE_SETRIGHT = 7;
var CHANNEL_SETRIGHT = 55;

</script>
</HEAD>

<BODY style="overflow:auto;background:transparent;">
<form name="frmAction" method="post">
	<INPUT TYPE="hidden" name="ObjType" id="ObjType" value="<%=nObjType%>">
	<INPUT TYPE="hidden" name="ObjId" id="ObjId" value="<%=nObjId%>">
	<INPUT TYPE="hidden" name="ResetChildrenRights" id="ResetChildrenRights" value="0">
	<INPUT TYPE="hidden" name="ObjectMembersXML" id="ObjectMembersXML" value="">
</form>
<table id="table_body" class="wcm_table_layout" border="0" cellpadding="0" cellspacing="0">
<tr>
	<td valign="top" bgcolor="#fff" style="height:29px;">
		<div class="pageinfo">
			<div class="pageinfo_left"></div>
			<div class="pageinfo_right"></div>
			<div style="float:right" id="toggleModeBox"></div>	
			<div style="float:right; display:none;" id="btnBox">
			<script>		
				oButtons = new CTRSButtons("bt_table_noborder");
				oButtons.addTRSButton("<%=LocaleServer.getString("auth.button.adduser", "添加用户")%>", "addUser();", "../images/auth/icon_user.gif","<%=LocaleServer.getString("auth.tip.adduser", "添加用户")%>");
				oButtons.addTRSButton("<%=LocaleServer.getString("auth.button.addgroup", "添加用户组")%>", "addGroup();", "../images/auth/icon_group.gif","<%=LocaleServer.getString("auth.tip.addgroup", "添加用户组")%>");
				oButtons.addTRSButton("<%=LocaleServer.getString("auth.button.addrole", "添加角色")%>", "addRole();", "../images/auth/icon_role.gif","<%=LocaleServer.getString("auth.tip.addrole", "添加角色")%>");
				oButtons.addTRSButton("<%=LocaleServer.getString("auth.button.dropall", "全部删除")%>", "removeAll();", "../images/auth/button_delete.gif","<%=LocaleServer.getString("auth.tip.dropall", "删除全部设置")%>");
				oButtons.addTRSButton("<%=LocaleServer.getString("auth.button.saveright", "保存修改")%>", "onSave();", "../images/auth/save.png","<%=LocaleServer.getString("auth.tip.saveright", "保存修改")%>");
				oButtons.draw();
				delete oButtons;
			</script>	
			</div>
			<TABLE style="float:left;height:29px;"><TR VALIGN='MIDDLE'><TD id="hostInfoTd">&nbsp;<%=baseChannel%></TD></TR></TABLE>
		</div>	
	</td>
	<td></td>
</tr>
	<td vAlign="top" bgColor="#f5f5f5" class="contentTd">
	<div style="overflow-y:auto;height:100%;">
		<div id="content"></div>
	</div>
	</td>
</table>
<div id="list_navigator" style="position:absolute;bottom:0px;right:60px;">

</div>
<div id="errorInfo" style="display:none;"></div>
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/easyversion/extrender.js"></script>
<script src="../js/data/locale/auth.js"></script>
<script src="../js/data/locale/system.js"></script>
<script src="../../app/js/easyversion/ajax.js"></script>
<script src="../../app/js/easyversion/basicdatahelper.js"></script>
<script src="../../app/js/easyversion/web2frameadapter.js"></script>
<script src="../../app/js/easyversion/elementmore.js"></script>
<script src="../../app/js/easyversion/list.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanel.js"></script>
<script src="../../app/js/source/wcmlib/Observable.js"></script>
<script src="../../app/js/source/wcmlib/pagecontext/PageNav.js"></script>

<script src="../../app/js/source/wcmlib/dragdrop/dd.js"></script>
<script src="../../app/js/source/wcmlib/dragdrop/wcm-dd.js"></script>
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/dialog/Dialog.js"></script>
<script src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>
<script src="view_oper_set.js"></script>
<%
//初始化可见对象成员数据
	ObjectMember currObjMemb = null;
	CMSObj oOperator = null;
	IUserService currUserService = (IUserService)DreamFactory.createObjectById("IUserService");
	boolean bIsGroupAdmin = currUserService.isAdminOfGroup(loginUser);
	Users users = null;
	GroupMgr groupMgr = new GroupMgr();
	Groups groups = null;
	if(!loginUser.isAdministrator() && bIsGroupAdmin){
		groups = groupMgr.getOffspringGroups(loginUser,null);
		users = currUserService.getCanMgrUsers(User.USER_STATUS_REG, null);
	}
	try{
		for(int i=0; i<objMembers.size(); i++){
			currObjMemb = (ObjectMember)objMembers.getAt(i);
			if(currObjMemb == null)continue;
			int nMemberType = currObjMemb.getMemberType();
			/*if(nMemberType == 203)
				continue;*/
			int nMemberId = currObjMemb.getMemberId();
			boolean isVisible = currObjMemb.isVisible();
			boolean canOperate = currObjMemb.isDoOperation();
			
			//获取当前登录用户可操作的对象
			oOperator = (CMSObj)BaseObj.findById(nMemberType,nMemberId);

			//当前用户不是管理员，并且需要过滤没有权限的用户或组织
			if(!loginUser.isAdministrator() && bFilterRight  && (nMemberType == Group.OBJ_TYPE || nMemberType == User.OBJ_TYPE)){
				if(bIsGroupAdmin){
					oOperator = (CMSObj)getMgrMember(groups,users,nMemberType,nMemberId);
				}else{
					oOperator = null;
				}
			}

			if(oOperator == null)continue;
%>
<script language="javascript">
	wcm.ObjectMembers.add({
	'MEMBERTYPE' : <%=nMemberType%>,
	'MEMBERID' : <%=nMemberId%>, 
	'NAME' : "<%=CMyString.filterForJs(getOperatorDesc(oOperator))%>", 
	'VISIBLE' : <%=isVisible?"1":"0"%>, 
	'DOOPERATION' : <%=canOperate?"1":"0"%>,
	'OBJECTMEMBERID':<%=currObjMemb.getId()%>
});
</script>
<%		}
	}catch(WCMException e){
		e.printStackTrace();
	}
%>
</BODY>
</HTML>
<%!		
	public String getOperatorDesc(CMSObj _obj)throws WCMException{
		switch(_obj.getWCMType()){
			case User.OBJ_TYPE:
				return ((User)_obj).getName() ;
			case Group.OBJ_TYPE:
				return ((Group)_obj).getName();
			case Role.OBJ_TYPE:
				return ((Role)_obj).getName();
			default:
				return "";
		}
	}
	private Object getMgrMember(Groups _groups, Users _users, int _nMemberType, int _nMemberId) {
		try {
			switch(_nMemberType){
				case User.OBJ_TYPE:
					if(_users != null){
						User currUser = User.findById(_nMemberId);
						if(_users.indexOf(currUser) != -1){
							return currUser;
						}
						return null;
					}
					return null;
				case Group.OBJ_TYPE:
					if(_groups != null){
						Group currGroup = Group.findById(_nMemberId);
						if(_groups.indexOf(currGroup) != -1){
							return currGroup;
						}
						return null;
					}
					return null;
				default:
					return null;
			}
		} catch (WCMException e) {
			e.printStackTrace();
		}
		return null;
	}
%>