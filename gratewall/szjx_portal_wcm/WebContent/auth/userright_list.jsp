<%
/** Title:			useright_list.jsp
 *  Description:
 *		WCM5.2 用户权限列表查看页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			wsw
 *  Created:		2006-03-02 
 *  Vesion:			1.0
 *  Last EditTime:	2006-05-11
 *	History			Who			What
 *	2006-03-02		wenyh		create.
 *	2006-05-11		wenyh		modify:分站点查看
 *
 *  Parameters:
 *		see useright_list.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>

<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.cms.auth.persistent.Role" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSites" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.service.IChannelService" %>
<%@ page import="com.trs.cms.auth.persistent.RightDef" %>
<%@ page import="com.trs.cms.auth.persistent.RightDefs" %>
<%@ page import="com.trs.cms.auth.persistent.Right" %>
<%@ page import="com.trs.cms.auth.persistent.Rights" %>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>

<%
	int nSiteId = currRequestHelper.getInt("SiteId", 0);
	int nFlag = currRequestHelper.getInt("Flag", 0);
	
	if((nSiteId == 0) && (nFlag == 0)){
		throw  new WCMException("参数有误");//shoul be nerver happened.
	}
	
	int nTabIndex = currRequestHelper.getInt("RighttypeTabIndex", 0);
	WebSite currSite = null;
	WebSites currSites = null;
	if(nFlag == 0){
		currSite = WebSite.findById(nSiteId);
		if(currSite == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,"没有找到[Id="+nSiteId+"]的站点!");
		}
	}else{
		WCMFilter siteFilter = new WCMFilter("","Status=0","SiteId");
		currSites = WebSites.openWCMObjs(loginUser,siteFilter);
		if(currSites.isEmpty()){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,"系统中没有找到有效站点!");
		}
		if(nSiteId > 0){
			currSite = (WebSite)currSites.getById(nSiteId);
		}else{
			currSite = (WebSite)currSites.getAt(0);
			nSiteId = currSite.getId();
		}
		if(currSite == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,"没有获取到系统中的站点数据!");
		}		
	}
	

	int nUserId = currRequestHelper.getInt("UserId", 0);

	int nOperId = currRequestHelper.getInt("OperId", 0);
	int nOperType = currRequestHelper.getInt("OperType", 0);

	String sOperDesc = getOperDesc(nOperId,nOperType);		
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2
权限查看::::::::::::::::::::::::::::::::::::::::::::::::::::::::::..</TITLE>
<base target="_self">
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<script src="../js/CTRSSimpleTab.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/CTRSButton.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/CTRSAction.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/CTRSHashtable.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/CTRSRequestParam.js"></script>
<script src="./UserRightViewer.js"></script>
<script>
function onViewBySite(_oSelect){
	var siteId = parseInt(_oSelect.options[_oSelect.selectedIndex].value);
	var oTRSAction = new CTRSAction("userright_list.jsp");
	oTRSAction.setParameter("OperId",<%=nOperId%>);
	oTRSAction.setParameter("OperType",<%=nOperType%>);
	oTRSAction.setParameter("SiteId",siteId);
	oTRSAction.setParameter("Flag",1);
	oTRSAction.setParameter("RighttypeTabIndex",TRSSimpleTab.nCurrIndex);
	oTRSAction.doAction();	
}
</script>
<script>
<%
	WCMFilter rightDefFilter = new WCMFilter("", "", "RIGHTINDEX");
	RightDefs rightDefs = RightDefs.openWCMObjs(loginUser, rightDefFilter);

	RightDef rightdef = null;	
	for(int i=0; i<rightDefs.size(); i++){
		rightdef = (RightDef)rightDefs.getAt(i);
		if(rightdef != null){		
%>								
			RightViewer.addRightDef("<%=CMyString.filterForJs(rightdef.getName())%>",<%=rightdef.getIndex()%>);			
<%
		}
	}
%>

<%
	WCMFilter rightFilter = new WCMFilter("","rightvalue>0 and ObjId="+nSiteId+" AND ObjType=103 AND OprId="+nOperId+" AND OprType="+nOperType,"");
	Rights  rightsOnSite = Rights.openWCMObjs(loginUser,rightFilter);
	Right right = null;		
	for(int i=0; i<rightsOnSite.size(); i++){
		right = (Right)rightsOnSite.getAt(i);
		if(right != null){
%>
			RightViewer.addRight("<%=getRightHost(right)%>","<%=right.getValue()%>","SITE");
<%
		}
	}
%>

<%
	rightFilter = new WCMFilter("","rightvalue>0 and ObjType=101 AND OprId="+nOperId+" AND OprType="+nOperType+" AND exists(select channelid from wcmchannel where siteid="+nSiteId+" and wcmright.objid=wcmchannel.channelid)","");
	Rights  rightsOnChannel = Rights.openWCMObjs(loginUser,rightFilter);		
	for(int i=0; i<rightsOnChannel.size(); i++){
		right = (Right)rightsOnChannel.getAt(i);
		if(right != null){
%>
			RightViewer.addRight("<%=getRightHost(right)%>","<%=right.getValue()%>","CHANNEL");
<%
		}
	}
%>
</script>

</HEAD>

<BODY>
<TABLE width="100%" height="90%" border="0" cellpadding="0"
	cellspacing="0">
	<TR>
		<TD align="center" valign="top" class="tanchu_content_td">
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">			
			<TR>
				<TD>
				<TABLE width="100%" height="100%" border="0" cellpadding="0"
					cellspacing="0">					
					<TR>
						<TD height="25"><!--开始标签列表--> <script>					
					TRSSimpleTab.nCurrIndex = 0;
					
					TRSSimpleTab.addItem("<%=LocaleServer.getString("auth.label.operatesite", "站点类操作权限")%>");	
					TRSSimpleTab.addItem("<%=LocaleServer.getString("auth.label.operatechannel", "频道类操作权限")%>");
					TRSSimpleTab.addItem("<%=LocaleServer.getString("auth.label.operatetemplate", "模板类操作权限")%>");
					TRSSimpleTab.addItem("<%=LocaleServer.getString("auth.label.operatedocument", "文档类操作权限")%>");	
					
					TRSSimpleTab.draw();					
				</script> <!--结束标签列表--></TD>
					</TR>
				</TABLE>
				</TD>
			</TR>
			<TR>
				<TD bgcolor="#FFFFFF"
					style="BORDER-left: #a6a6a6 1px solid; BORDER-right: #a6a6a6 1px solid; BORDER-bottom: #a6a6a6 1px solid;font-size:14px"
					align=left valign="middle"><br><%=sOperDesc%>@<%=currSite.getDesc()%>&nbsp;&nbsp;&nbsp;
					<%if(currSites != null && currSites.size()>1){%>
						查看在<span style="border:1px solid #333333;width:1"><select id="siteChoice" onChange="onViewBySite(this)" style="margin:-2;background:#eeeeee;">
						<option value="0">请选择</option>
						<%for(int i=0; i < currSites.size(); i++){
							WebSite site = (WebSite)currSites.getAt(i);
							if(site != null && site.getId() != currSite.getId()){
						%>
							<option value="<%=site.getId()%>"><%=site%></option>
						<%
								}
							}
						%>
						</select></span>的权限
					<%
						}
					%>
					</TD>
			</TR>
			<TR>
				<TD bgcolor="#FFFFFF"
					style="BORDER-left: #a6a6a6 1px solid; BORDER-right: #a6a6a6 1px solid; BORDER-bottom: #a6a6a6 1px solid"
					align=center valign="top"><BR>				
				<div id="id_TRSSimpleTab0" style="display:inline;"></div>
				<div id="id_TRSSimpleTab1" style="display:none"></div>
				<div id="id_TRSSimpleTab2" style="display:none"></div>
				<div id="id_TRSSimpleTab3" style="display:none"></div>
				<!-- display rights by used checkbox -->
				<!--
				<script>RightViewer.drawRightTabs("id_TRSSimpleTab0",<%=WCMRightTypes.SITE_MIN_INDEX%>,<%=WCMRightTypes.SITE_MAX_INDEX%>);</script>				
				<script>RightViewer.drawRightTabs("id_TRSSimpleTab1",<%=WCMRightTypes.CHNL_MIN_INDEX%>,<%=WCMRightTypes.CHNL_MAX_INDEX%>);</script>	
				<script>RightViewer.drawRightTabs("id_TRSSimpleTab2",<%=WCMRightTypes.TEMPLATE_MIN_INDEX%>,<%=WCMRightTypes.TEMPLATE_MAX_INDEX%>);</script>
				<script>RightViewer.drawRightTabs("id_TRSSimpleTab3",<%=WCMRightTypes.DOC_MIN_INDEX%>,<%=WCMRightTypes.DOC_MAX_INDEX%>);</script>				
				-->
				<!-- display rights by used another form -->
				<script>RightViewer.displayRightsOnTab(<%=WCMRightTypes.SITE_MIN_INDEX%>,<%=WCMRightTypes.SITE_MAX_INDEX%>,"SITE","id_TRSSimpleTab0");</script>
				<script>RightViewer.displayRightsOnTab(<%=WCMRightTypes.CHNL_MIN_INDEX%>,<%=WCMRightTypes.CHNL_MAX_INDEX%>,"CHANNEL","id_TRSSimpleTab1");</script>
				<script>RightViewer.displayRightsOnTab(<%=WCMRightTypes.TEMPLATE_MIN_INDEX%>,<%=WCMRightTypes.TEMPLATE_MAX_INDEX%>,"CHANNEL","id_TRSSimpleTab2");</script>
				<script>RightViewer.displayRightsOnTab(<%=WCMRightTypes.DOC_MIN_INDEX%>,<%=WCMRightTypes.DOC_MAX_INDEX%>,"CHANNEL","id_TRSSimpleTab3");</script>				
				<!-- -->				
				</TD>
			</TR>			
		</TABLE>
		</TD>
	</TR>
	<TR>
		<TD align=center><script>				
			//定义一个TYPE_ROMANTIC_BUTTON按钮
			var oTRSButtons = new CTRSButtons();
			
			oTRSButtons.cellSpacing = "0";
			oTRSButtons.nType = TYPE_ROMANTIC_BUTTON;

			oTRSButtons.addTRSButton("返    回", "window.close();");
			
			oTRSButtons.draw();
		</script></TD>
	</TR>
</TABLE>
<script>
	TRSSimpleTab.onClickItem(<%=nTabIndex%>);
</script>
</BODY>
</HTML>
<%!
	private CMSObj getRightHost(Right _right) throws WCMException{		
		return (CMSObj)CMSObj.findById(_right.getObjType(),_right.getObjId());
	}

	private String getOperDesc(int _nOperId,int _nOperType) throws WCMException{
		switch(_nOperType){
			case User.OBJ_TYPE : {
				User currUser = User.findById(_nOperId);
				if(currUser == null) throw new WCMException("没有找到[id="+_nOperId+"]的用户!");
				return "&nbsp;&nbsp;<img src=\"../images/icon_user.gif\">&nbsp;&nbsp;"+currUser;
			}
			case Group.OBJ_TYPE : {
				Group currGroup = Group.findById(_nOperId);
				if(currGroup == null) throw new WCMException("没有找到[id="+_nOperId+"]的组织!");
				return "&nbsp;&nbsp;<img src=\"../images/icon_group.gif\">&nbsp;&nbsp;"+currGroup;
			}case Role.OBJ_TYPE : {
				Role currRole = Role.findById(_nOperId);
				if(currRole == null) throw new WCMException("没有找到[id="+_nOperId+"]的角色!");
				return "&nbsp;&nbsp;<img src=\"../images/icon_role.gif\">&nbsp;&nbsp;"+currRole;
			}
			default : {
				throw new WCMException("无效的操作者类型!");
			}
		}
	}
%>