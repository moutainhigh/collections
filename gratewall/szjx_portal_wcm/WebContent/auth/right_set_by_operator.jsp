<%
/** Title:			right_set_by_operator.jsp
 *  Description:
 *		WCM5.2 设置权限页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			wenyh
 *  Created:		2006-04-30 13:34
 *  Vesion:			1.0
 *  Last EditTime:	2006-04-30 / 2006-04-30
 *	Update Logs:
 *	History				Who				What
 *	2006-07-26			wenyh			设置站点类权限时,不显示添加频道操作
 *
 *  Parameters:
 *		see right_set_by_operator.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.persistent.Rights" %>
<%@ page import="com.trs.cms.auth.domain.IRightHost" %>
<%@ page import="com.trs.cms.auth.domain.IRightMgr" %>
<%@ page import="com.trs.cms.auth.domain.RightHostFactory" %>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.cms.auth.persistent.Right" %>
<%@ page import="com.trs.cms.auth.persistent.RightDef" %>
<%@ page import="com.trs.cms.auth.persistent.RightDefs" %>
<%@ page import="com.trs.cms.auth.persistent.Role" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.content.persistent.BaseChannel" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.components.wcm.resource.Statuses" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%
//4.初始化(获取数据)
	int nOperId = currRequestHelper.getInt("OperId", 0);
	int nOperType = currRequestHelper.getInt("OperType", 0);
	
	StringBuffer sbWhere = new StringBuffer(128);
	sbWhere.append("OprId=").append(nOperId).append(" AND ");
	sbWhere.append("OprType=").append(nOperType).append(" AND ");
	sbWhere.append("RightValue>=").append(0).append(" AND (");
	sbWhere.append("ObjType=").append(WebSite.OBJ_TYPE).append(" OR ");
	sbWhere.append("ObjType=").append(Channel.OBJ_TYPE).append(")");	

	WCMFilter rightFilter = new WCMFilter("",sbWhere.toString(),"ObjType desc,ObjId asc");
	Rights currRights = new Rights(loginUser);
	currRights.open(rightFilter);
	
	WCMFilter filter = new WCMFilter("", "", "RIGHTINDEX");
	RightDefs currRightDefs = RightDefs.openWCMObjs(loginUser, filter);

//7.结束
	out.clear();
%>
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 <%=LocaleServer.getString("auth.label.rightset", "权限设置")%>::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<base target="_self">
<LINK href="../style/style.css" rel="stylesheet" type="text/css">


<script src="../js/CTRSHTMLElement.js"></script>
<script src="../js/CTRSHTMLTr.js"></script>  

<script src="../js/CTRSSimpleTab.js"></script>

<script src="../js/CTRSArray.js"></script>

<SCRIPT LANGUAGE="JavaScript" src="../js/CWCMObj.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/CWCMObjHelper.js"></SCRIPT>

<SCRIPT LANGUAGE="JavaScript" src="../js/CTRSButton.js"></script>  

<script src="../js/CTRSHashtable.js"></script>
<script src="../js/CTRSRequestParam.js"></script>
<script src="../js/CTRSAction.js"></script>
<script src="./UserRightHelper.js"></script>
<script src="../js/prototype.js"></script>
<script>

<%
	//初始化权限索引
	RightDef currRightDef = null;
	for(int i=0; i<currRightDefs.size(); i++){
		currRightDef = (RightDef)currRightDefs.getAt(i);
%>
	RightSetHelper.addRightDef("<%=CMyString.filterForJs(currRightDef.getName())%>", "<%=CMyString.filterForJs(CMyString.showNull(currRightDef.getDesc()))%>",<%=currRightDef.getIndex()%>);
<%
	}
%>

<%
//初始化权限数据
	Right currRight = null;
	CMSObj oOperator = null;
	for(int i=0; i<currRights.size(); i++){
		currRight = (Right)currRights.getAt(i);
		if(currRight == null)continue;

		oOperator = currRight.getOperator();
		if(oOperator == null) continue;

		
%>
	RightSetHelper.addRight(<%=currRight.getObjType()%>, <%=currRight.getObjId()%>, "<%=CMyString.filterForJs(getOperTarget(currRight.getObjType(),currRight.getObjId()))%>", "<%=currRight.getValue()%>", <%=currRight.getId()%>);	
<%
	}
%>

	function init(){				
		try{
			RightSetHelper.draw(<%=WCMRightTypes.SITE_MIN_INDEX%>, <%=WCMRightTypes.SITE_MAX_INDEX%>,"id_TRSSimpleTab0");
			RightSetHelper.draw(<%=WCMRightTypes.CHNL_MIN_INDEX%>, <%=WCMRightTypes.CHNL_MAX_INDEX%>, "id_TRSSimpleTab1");
			RightSetHelper.draw(<%=WCMRightTypes.TEMPLATE_MIN_INDEX %>, <%=WCMRightTypes.TEMPLATE_MAX_INDEX %>, "id_TRSSimpleTab2");
			RightSetHelper.draw(<%=WCMRightTypes.DOC_MIN_INDEX %>, <%=WCMRightTypes.DOC_MAX_INDEX %>, "id_TRSSimpleTab3");
		}catch(e){
			alert(e.description);
		}		
	}
	
	function addChannelResource(){
		if(TRSSimpleTab.nCurrIndex == 0){
			return false;
		}

		var nObjType = <%=Channel.OBJ_TYPE%>;
		var sChannelIds = RightSetHelper.getIdArray(nObjType).join(",");
		
		//select channels.
		var oTRSAction = new CTRSAction("../channel/channel_select.jsp");
		oTRSAction.setParameter("TreeType", 1);
		oTRSAction.setParameter("ChannelIds", sChannelIds);
		oTRSAction.setParameter("DisabledChannels", "0");
		oTRSAction.setParameter("ChannelTypes", "<%=Channel.TYPE_NORM%>,<%=Channel.TYPE_TOP_NEWS%>,<%=Channel.TYPE_TOP_PICS%>,<%=Channel.TYPE_INFOVIEW%>");
		oTRSAction.setParameter("TreeWidth", "492");
		
		var sResult = oTRSAction.doDialogAction(500, 600);

		if(!sResult) return;		

		//get channels info.
		var retriveChnlAction = new CTRSAction("../channel/channels_get_by_ids.jsp");		
		retriveChnlAction.setParameter("ChannelIds",sResult);

		var chnlsXml = retriveChnlAction.doXMLHttpAction();		
		var chnls = WCMObjHelper.fromXMLString(chnlsXml);
		
		//refresh: remove the channel not selected.
		RightSetHelper.refreshRights(nObjType,sResult.split(","));

		var chnl = null;
		var nObjId = 0;	

		for(var i=0; i<chnls.length; i++){
			chnl = chnls[i];
			if(chnl == null) continue;
			 nObjId = chnl.getProperty("CHANNELID");
			var objDesc = chnl.getProperty("CHNLDESC") + "[频道-" + nObjId + "]";
			RightSetHelper.putResource(nObjType,nObjId,objDesc);
		}
		
		if(RightSetHelper.bUpdate) init();
	}

	function addSiteResource(){		
		var nObjType = <%=WebSite.OBJ_TYPE%>;
		var sSiteIds = RightSetHelper.getIdArray(nObjType).join(",");
		
		//select sites.
		var selectSiteAction = new CTRSAction("../channel/site_select.jsp");
		selectSiteAction.setParameter("SiteIds",sSiteIds);
		var sResult = selectSiteAction.doDialogAction(500,600);				
		if(!sResult) return;
		
		//get sites info.
		var siteAction = new CTRSAction("../channel/sites_get_by_ids.jsp");
		siteAction.setParameter("SiteIds",sResult);
		
		var sitesXml = siteAction.doXMLHttpAction();
		var sites = WCMObjHelper.fromXMLString(sitesXml);		

		//refresh: remove the site not selected.
		RightSetHelper.refreshRights(nObjType,sResult.split(","));
		
		var site = null;
		var nObjId = 0;
		for(var i=0; i<sites.length; i++){
			site = sites[i];
			if(site == null) continue;
			nObjId = site.getProperty("SITEID");
			var objDesc = site.getProperty("SITEDESC") + "[站点-" + nObjId + "]";
			RightSetHelper.putResource(nObjType,nObjId,objDesc);
			
		}
		
		if(RightSetHelper.bUpdate) init();		
	}
	
	window.onload = init;
</script>

<script>
//覆盖删除的通知函数，以便刷新页面
UserRightHelper.prototype.notifyRemove = function(eleOnEvent){
	init();
}

function onOK(){	
	var frmAction = document.frmAction;
	if(frmAction == null){
		CTRSAction_alert("没有定义");
	}
	
	frmAction.RightsXML.value = RightSetHelper.toXmlInfo();
	
	frmAction.submit();
}

function onInOutSiteTab(){
	var spanAddChannel = $("ChannelAddSpan");
	if($("id_TRSSimpleTab0").style.display == "none"){
		spanAddChannel.style.display = "inline";
	}else{
		spanAddChannel.style.display = "none";
	}	
}
</script>
</HEAD>

<BODY>
<form name="frmAction" method="post" action="right_set_by_operator_dowith.jsp">
	<INPUT TYPE="hidden" name="OperType" value="<%=nOperType%>">
	<INPUT TYPE="hidden" name="OperId" value="<%=nOperId%>">
	<INPUT TYPE="hidden" name="RightsXML" value="">
</form>

<!--~== Dialog Head TABLE ==~-->
	<script src="../js/CWCMDialogHead.js"></script>
	<script>WCMDialogHead.draw("<%=LocaleServer.getString("auth.label.rightset", "权限设置")%>");</script>
<!--~- END Dialog Head TABLE -~-->

<TABLE width="100%" height="90%" border="0" cellpadding="0" cellspacing="0">
<!--~--- ROW5 ---~-->
<TR>
  <TD align="center" valign="top" class="tanchu_content_td">
  <!--~== TABLE4 ==~-->
  <TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
  <!--~--- ROW6 ---~-->
  <TR>
    <TD>
		
    </TD>
  </TR>
  <!--~- END ROW6 -~-->
  <!--~-- ROW10 --~-->
  <TR>
    <TD>
		<!--~== TABLE1 ==~-->
		<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
		<!--~--- ROW1 ---~-->
		<TR>
		  <TD height="25">
				<!--开始标签列表-->
				<script>					
					TRSSimpleTab.nCurrIndex = 0;
					TRSSimpleTab.addItem("<%=LocaleServer.getString("auth.label.operatesite", "站点类操作权限")%>");
					TRSSimpleTab.addItem("<%=LocaleServer.getString("auth.label.operatechannel", "频道类操作权限")%>");
					TRSSimpleTab.addItem("<%=LocaleServer.getString("auth.label.operatetemplate", "模板类操作权限")%>");				
					TRSSimpleTab.addItem("<%=LocaleServer.getString("auth.label.operatedocument", "文档类操作权限")%>");
					TRSSimpleTab.draw();
				</script>
				<!--结束标签列表-->	
		  </TD>
		</TR>
		<!--~- END ROW1 -~-->
		<TR>
		  <TD  bgcolor="#FFFFFF" style="BORDER-left: #a6a6a6 1px solid; BORDER-right: #a6a6a6 1px solid; BORDER-bottom: #a6a6a6 1px solid" align=left valign="middle">
			<%=getOperDesc(nOperId,nOperType)%>&nbsp;&nbsp;&nbsp;&nbsp;<span type="text" onclick="addSiteResource()" style="cursor:hand">添加站点</span><span type="text" onclick="addChannelResource()" style="cursor:hand;display:none" id="ChannelAddSpan">&nbsp;&nbsp;&nbsp;&nbsp;添加频道</span><br>			
			</TD>
		</TR>
		<!--~-- ROW10 --~-->
		<TR>
		  <TD  bgcolor="#FFFFFF" style="BORDER-left: #a6a6a6 1px solid; BORDER-right: #a6a6a6 1px solid; BORDER-bottom: #a6a6a6 1px solid" align=center valign="top">
			
			<BR>		  
				<div id="id_TRSSimpleTab0" style="display:inline;" onPropertyChange="onInOutSiteTab();">
					
				</div>				
				<div id="id_TRSSimpleTab1" style="display:none">
					
				</div>
				<div id="id_TRSSimpleTab2" style="display:none">
					
				</div>			
				<div id="id_TRSSimpleTab3" style="display:none">
					
				</div>			
						
				<BR>
		  </TD>
		</TR>
		<!--~ END ROW10 ~-->
		</TABLE>
		<!--~ END TABLE1 ~-->
		
    </TD>
  </TR>
  <!--~ END ROW10 ~-->
  <!--~-- ROW18 --~-->
  <TR>
    <TD align=center height="10">&nbsp;</TD>
  </TR>
  <TR>
    <TD align=center>
		<SCRIPT SRC="../js/CTRSButton.js"></SCRIPT>
		<script>
			//定义一个TYPE_ROMANTIC_BUTTON按钮
			var oTRSButtons = new CTRSButtons();
			
			oTRSButtons.cellSpacing = "0";
			oTRSButtons.nType = TYPE_ROMANTIC_BUTTON;

			oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.confirm", "确认")%>", "onOK()");
			oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.cancel", "取消")%>", "window.close();");
			
			oTRSButtons.draw();
		</script>
    </TD>
  </TR>
  <!--~ END ROW18 ~-->
  </TABLE>
  <!--~ END TABLE4 ~-->
  </TD>
</TR>
<!--~- END ROW5 -~-->
</TABLE>
<!--~ END TABLE1 ~-->
</BODY>
</HTML>
<%!		

	public String getOperatorDesc(CMSObj _rightHost)throws WCMException{
		switch(_rightHost.getWCMType()){
			case User.OBJ_TYPE:
				return ((User)_rightHost).getName() ;
			case Group.OBJ_TYPE:
				return ((Group)_rightHost).getName();
			case Role.OBJ_TYPE:
				return ((Role)_rightHost).getName();
			default:
				return "";
		}
	}

	private String getOperTarget(int _nObjType,int _nObjId)throws WCMException{
		return ""+ CMSObj.findById(_nObjType,_nObjId);
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