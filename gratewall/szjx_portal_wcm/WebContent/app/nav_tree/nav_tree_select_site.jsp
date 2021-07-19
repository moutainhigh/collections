<%
/** Title:			nav_tree_select_site.jsp
 *  Description:
 *		标准WCM V6 页面，用于“站点的选择”。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WCM
 *  Created:		2007-2-6 15:13
 *  Vesion:			1.0
 *  Last EditTime:	2007-2-6/2007-2-6
 *	Update Logs:
 *		CH@2007-2-6 created the file 
 *
 *  Parameters:
 *		see nav_tree_select_site.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>

<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.components.wcm.content.persistent.BaseChannel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channels" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSites" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.cms.auth.domain.IObjectMemberMgr" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.presentation.util.RequestHelper" %>
<%@ page import="com.trs.presentation.plugin.PluginConfig" %>
<%@ page import="com.trs.cms.auth.persistent.Role" %>
<%@ page import="com.trs.cms.auth.persistent.User" %>

<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>

<%
	int[] pSiteTypes = null;
	String sSiteTypes = currRequestHelper.getString("SiteTypes");	
	if(sSiteTypes != null && (sSiteTypes=sSiteTypes.trim()).length()>0){
		pSiteTypes = CMyString.splitToInt(sSiteTypes, ",");		
	}
	String sCurrSiteType = request.getParameter("CurrSiteType");
	int iCurrSiteType = 0;
	if(sCurrSiteType != null){
		iCurrSiteType = Integer.parseInt(sCurrSiteType);
	}
	String sSelectSiteIds = request.getParameter("SelectedSiteIds");
	String sSelectChannelIds = request.getParameter("SelectedChannelIds");
	if(sSelectSiteIds == null && sSelectChannelIds != null){
		Channels channels = Channels.findByIds(null, sSelectChannelIds);
		StringBuffer sb = new StringBuffer();
		for(int i = 0, len = channels.size(); i < len; i++){
			Channel channel = (Channel)channels.getAt(i);
			if(channel == null){
				continue;
			}
			sb.append(channel.getSiteId()).append(",");
		}
		sb.setCharAt(sb.length()-1, '\0');
		sSelectSiteIds = sb.toString();
	}
	out.clear();
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title WCMAnt:param="nav_tree_select_site.jsp.title">Navigator Tree</title>
	<link rel="stylesheet" type="text/css" href="../css/wcm-common.css">
	<link href="../js/source/wcmlib/com.trs.tree/resource/TreeNav.css" rel="stylesheet" type="text/css" />
	<link href="nav_tree.css" rel="stylesheet" type="text/css" />
</head>

<body style="margin:0;padding:0;background:#FFFFFF;">
<style type="text/css">
	.nav_tree{width:290px!important;overflow:hidden !important;}
	.TreeView{border:0px;overflow-y:auto;width:97%!important;height:100%;display:inline-block;}
</style>
<div class="nav_tree" style="width:100%;height:100%;border:0;">
	<ul class="TreeView" id="ChannelNav">
		<% 		
			writeTreeHTML(loginUser, pSiteTypes, out, currRequestHelper, iCurrSiteType);
		%>
	</ul>
</div>
	<script src="../js/easyversion/lightbase.js"></script>
	<script src="../js/easyversion/extrender.js"></script>
	<script language="javascript">
		initExtCss();
	</script>
	<script src="../js/easyversion/elementmore.js"></script>
    <script src="../js/source/wcmlib/WCMConstants.js"></script>
	<script src="../js/source/wcmlib/core/MsgCenter.js"></script>
	<script src="../js/data/locale/nav_tree.js"></script>
	<script src="../js/data/locale/tree.js"></script>
	<script src="../js/source/wcmlib/core/CMSObj.js"></script>
	<script src="../js/source/wcmlib/com.trs.tree/TreeNav.js"></script>
	<!--<script src="../js/easyversion/ajax.js"></script>-->
	<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script>
if(getParameter("IsRadioBox") == 1){
}
</script>
<link href="../css/wcm_tree.css" rel="stylesheet" type="text/css" />
<SCRIPT LANGUAGE="JavaScript">
<!--
var m_bIsLocal = false;
var m_bDebug = false;
//覆写点击节点A元素触发的事件
com.trs.tree.TreeNav.doActionOnClickA = function(_event, _elElementA){
	Event.stop(_event);
	return false;
}

com.trs.tree.TreeNav.__getSelectValue = function(_oNodeElementDiv){
	var sValue = _oNodeElementDiv.id;
	var nPos = sValue.indexOf("_");
	
	if(nPos<=0)
		return sValue;

	return sValue.substring(nPos+1);
}


//-->
</SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!--
var TreeNav = com.trs.tree.TreeNav;
//设置树的类型
//TreeNav.setType(TreeNav.TYPE_RADIO);
TreeNav.setType(getParameter("IsRadio") == 1 ? TreeNav.TYPE_RADIO:TreeNav.TYPE_CHECKBOX);

//设置记录checkbox Value在LI元素上的属性名称
TreeNav.setAttrNameRelatedValue("_objectid");

function getCheckValues(){
	return TreeNav.getCheckValues('ChannelNav', true);
}

function getCheckNames(){
	return TreeNav["SelectedNames"];
}
//-->
</SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
if( m_bDebug ){
	document.write("<a href=# onclick=\"alert(getCheckValues())\">" +
		(wcm.LANG.nav_tree_alert_20 || "测试获取的值") 
		+ "</a> || <a href=# onclick=\"alert(getCheckNames())\">" + 
		(wcm.LANG.nav_tree_alert_21 || "测试获取值的名称") 
		+ "</a><P>");
}

function expandTree(){
	//使用传入的值初始化树
	var sSelectedSiteIds = "<%=CMyString.filterForJs(CMyString.showNull(sSelectSiteIds))%>";
	if(sSelectedSiteIds != null && sSelectedSiteIds.length>0){
		var idArray = sSelectedSiteIds.split(",");
		for(var i = 0; i < idArray.length; i++){
			($('cs_' + idArray[i]) || {}).checked = true;
		}
	}
	var unSelectDisabledNoRight = getParameter("unSelectDisabledNoRight");
	if(unSelectDisabledNoRight == "1"){
		unSelectedTreeNode(sSelectedSiteIds);
	}
}

/**
*取消选中的disabled的栏目
*/
function unSelectedTreeNode(siteIds){
	if(!siteIds) return;
	var siteIds = siteIds.split(",");
	for(var i = 0; i < siteIds.length; i++){
		var inputDom = $("cs_" + siteIds[i]);
		if(inputDom && inputDom.disabled){
			inputDom.checked = false;
		}
	}
}

function findElement(source, idPrefix){
	while(source && !source.id.startsWith(idPrefix)){
		source = source.parentNode;
	}
	return source;
}

function selectAllChildren(_checkBoxObj){
	var isChecked = _checkBoxObj.checked;
	var treeRootDom = findElement(_checkBoxObj, "r_");
	if(!treeRootDom) return;
	var ulDomObj = getNextHTMLSibling(treeRootDom);
	if(!ulDomObj)return;
	var inputArray = ulDomObj.getElementsByTagName("input");
	for(var i = 0; i < inputArray.length; i++){
		if(inputArray[i].checked != isChecked){
			inputArray[i].click();
		}
	}
}

function getNextHTMLSibling(domNode){
	if(domNode == null) return null;
	var tempNode = domNode.nextSibling;
	while(tempNode && tempNode.nodeType != 1){
		tempNode = tempNode.nextSibling;
	}
	return ((tempNode == null)||(tempNode.parentNode != domNode.parentNode)) ? null : tempNode;
}

com.trs.tree.TreeNav.observe("onload", expandTree);
com.trs.tree.TreeNav.makeGetChildrenHTMLAction = function(){
	return null;
};
//-->
</SCRIPT>
<!--WCM-1763 | 提供一个系统工具，可以对WCM导航树中的某些节点设置为显示或不显示-->
<script language="javascript">
<!--
	Event.observe(window, 'load', function(){
		var CustomizeInfo = null;
		var topWin = $MsgCenter.getActualTop() || top;
		if(topWin && topWin.CustomizeInfo){
			CustomizeInfo = topWin.CustomizeInfo;
		}else if(topWin.topHandler && topWin.topHandler.CustomizeInfo){
			CustomizeInfo = topWin.topHandler.CustomizeInfo;
		}
		if(!CustomizeInfo){
			var oHelper = new com.trs.web2frame.BasicDataHelper();
			oHelper.JspRequest("../individuation/systemindividuation_get.jsp",{},false, 
				function(transport,json){
					if(!json){
						return;
					}
					
					var adiv=document.getElementsByTagName("div");
					for(var i=0;i<adiv.length;i++){

						if(adiv[i].id=="r_0" && json["TEXTLIB"]=="false"){
							document.getElementById("r_0").style.display = "none"; 
						}else if(adiv[i].id=="r_1" && json["PICTURELIB"]=="false"){
							document.getElementById("r_1").style.display = "none";
						}else if(adiv[i].id=="r_2" && json["VIDEOLIB"]=="false"){
							document.getElementById("r_2").style.display = "none";
						}else if(adiv[i].id=="r_4" && json["RESOURCESLIB"]=="false"){
							document.getElementById("r_4").style.display = "none";
						}
					}
				}
			);
		}else{
			var adiv=document.getElementsByTagName("div");
			if(!CustomizeInfo["textLib"]){
				return;
			}
			for(var i=0;i<adiv.length;i++){
				if(adiv[i].id=="r_0" && CustomizeInfo["textLib"].paramValue=="false"){
					document.getElementById("r_0").style.display = "none";
				}else if(adiv[i].id=="r_1" && CustomizeInfo["pictureLib"].paramValue=="false"){
					document.getElementById("r_1").style.display = "none";
				}else if(adiv[i].id=="r_2" && CustomizeInfo["videoLib"].paramValue=="false"){
					document.getElementById("r_2").style.display = "none";
				}else if(adiv[i].id=="r_4" && CustomizeInfo["resourcesLib"].paramValue=="false"){
					document.getElementById("r_4").style.display = "none";
				}
			}
		}
	});

//-->
</script>
</body>
</html>
<%!
    public void writeTreeHTML(User _loginUser, int[] _pSiteTypes,
            JspWriter out, RequestHelper currRequestHelper, int iCurrSiteType) throws Exception {        
        int[] pSiteTypes = { 0, 1, 2, 4 };
//        String[] pSiteTypeNames = { "文字库", "图片库", "视频库" };
        if (_pSiteTypes != null && _pSiteTypes.length>0) {
            pSiteTypes = _pSiteTypes;
        }

        //modified by hxj on 20080417 处理其他站点类型
		boolean isCheckBox = !currRequestHelper.getBoolean("IsRadio", false);
        for (int i = 0; i < pSiteTypes.length; i++) {
            int nSiteType = pSiteTypes[i];
            out.println("<div title=\"" + getDesc(nSiteType)
                    + "\" id=\"r_" + nSiteType + "\" classPre=\"SiteType"
                    + nSiteType + "\" OnlyNode=\"true\">");
            out.println("<a href=#>");
			if(isCheckBox){
				out.println("<input id=\"siteType_"+i+"\" type='checkbox' onclick='selectAllChildren(this);event.cancelBubble=true;'/>"
					    +"<label for=\"siteType_"+i+"\">");
			}
			out.println(getDesc(nSiteType)+"</label></a>");
            out.println("</div>");
            
//            WebSites sites = getWebSites(_loginUser, nSiteType);
			//TODO 暂时注释掉
            //if(sites == null || sites.isEmpty())continue;
            
            out.println("<ul style='display:" + (iCurrSiteType==nSiteType?"":"none") + "'>");
            writeSiteHTMLWithPlugin(_loginUser, nSiteType, out, currRequestHelper);
            out.println("</ul>");                        
        }
    }

	private String getDesc(int siteType){
		String desc = LocaleServer.getString("nav_tree.label.textWarehouse", "文字库");
		switch(siteType){
			case 1:
				desc = LocaleServer.getString("nav_tree.label.picWarehouse", "图片库");
				break;
			case 2:
				desc = LocaleServer.getString("nav_tree.label.videoWarehouse", "视频库");
				break;
			case 4:
				desc = LocaleServer.getString("nav_tree.label.resourceWarehouse", "资源库");
				break;
		}
		return desc;
	}

	private void writeSiteHTMLWithPlugin(User loginUser, int _nSiteType, 
			JspWriter out, RequestHelper currRequestHelper)throws Exception {
        switch (_nSiteType) {
			case 1:
				if(!PluginConfig.isStartPhoto()){
					out.println("<font color='red'>" + 
						LocaleServer.getString("nav_tree.label.notBuyPicComponent", "您没有购买图片选件！") 
						+ "</font>");
					return;
				}
				break;
			case 2:
				if(!PluginConfig.isStartVideo()){
					out.println("<font color='red'>" + 
						LocaleServer.getString("nav_tree.label.notBuyVideoComponent", "您没有购买流媒体选件！") 
						+ "</font>");
					return;
				}
				break;        
        }
        WebSites sites = getWebSites(loginUser, _nSiteType);
		writeHTMLFromObjects(loginUser, out, sites, currRequestHelper);
	}

    private WebSites getWebSites(User loginUser, int _nSiteType)
            throws WCMException {
        String sWhere = "Status>=0 and SiteType=?";
        WCMFilter filter = new WCMFilter("", sWhere, "SiteOrder Desc");
        filter.addSearchValues(0, _nSiteType);

        WebSites sites = WebSites.openWCMObjs(loginUser, filter);

        IObjectMemberMgr objectMemberMgr = (IObjectMemberMgr) DreamFactory
                .createObjectById("IObjectMemberMgr");

        // 5.权限过滤
        for (int i = (sites.size() - 1); i >= 0; i--) {
            WebSite site = (WebSite) sites.getAt(i);
            if (site == null)
                continue;

            if (!hasSuperRight(site, loginUser)
                    && !objectMemberMgr.isVisible(loginUser, WebSite.OBJ_TYPE, site.getId())) {
                sites.removeAt(i, false);
                continue;
            }
        }
        return sites;
    }

    private boolean hasSuperRight(BaseChannel host, CMSObj _aUser)
            throws WCMException {
        switch (_aUser.getWCMType()) {
        case User.OBJ_TYPE:
            User user = (User) _aUser;
            if (user.isAdministrator())
                return true;
            if (host != null
                    && user.getName().equalsIgnoreCase(host.getCrUserName()))
                return true;
            break;
        case Role.OBJ_TYPE:
            Role role = (Role) _aUser;
            if (role.isAdministrators())
                return true;
        default:
            break;
        }
        return false;
    }

    private void writeWebSiteHTML(User loginUser, JspWriter out, WebSite host,
            RequestHelper currRequestHelper) throws Exception {
        String sDivId = (host.isSite() ? "s" : "c") + "_" + host.getId();
        out.println("<div title=\""
                + CMyString.filterForHTMLValue(host.getDispDesc())
                + "\" classPre=\""
                + getClassPre(host)
                + "\""//
                + getExtraHTMLAttribute(loginUser, host, currRequestHelper)
                + " id=\"" + sDivId + "\">"
                + getNodeInnerHTML(currRequestHelper, host, sDivId) + "</div>");
    }

    /**
     * @param _siteOrChannel
     * @return
     */
    private String getClassPre(BaseChannel _siteOrChannel) {
        if (_siteOrChannel.isSite())
            return "site";
        Channel channel = (Channel) _siteOrChannel;
        if (channel.isNormType())
            return "channel";
        return "channel" + channel.getType();
    }

    private String getNodeInnerHTML(RequestHelper currRequestHelper,
            BaseChannel host, String divId) throws Exception {
        if (currRequestHelper.getBoolean("FromSelect", false)) {
            return "<label For=\"c" + divId + "\" style=\"cursor:hand\">"
                    + CMyString.transDisplay(host.getDispDesc()) + "</label>";
        }
        return "<a href=\"#\" " + getExtraAttrOfA(host) + ">"
                + CMyString.transDisplay(host.getDispDesc()) + "</a>";
    }

    private String getExtraAttrOfA(BaseChannel host) {
        String sTitle = CMyString.format(LocaleServer.getString("nav_tree_select_individual.jsp.stitle", "编号：{0}\n创建者：{1}\n创建时间：{2}"), new Object[]{String.valueOf(host.getId()),CMyString.filterForHTMLValue(host.getName()),CMyString.filterForHTMLValue(host.getCrUserName()),host.getCrTime()});
        return " title=\"" + sTitle + "\" ";
    }

/*
    private String getExtraHTMLAttribute(User loginUser, BaseChannel host,
            RequestHelper currRequestHelper) throws WCMException {
        //return " SDisabled=\"true\" ";
		return " ";
    }
*/

    private String getExtraHTMLAttribute(User loginUser, WebSite host,
            RequestHelper currRequestHelper) throws WCMException {
        if (hasRight(loginUser, host, currRequestHelper))
            return "";

        return " SDisabled=\"true\" ";
    }


    private boolean hasRight(User loginUser, WebSite host,
            RequestHelper currRequestHelper) throws WCMException {

        if (loginUser.isAdministrator())
            return true;

        int nRightIndex = currRequestHelper.getInt("RightIndex",
                WCMRightTypes.OBJ_ACCESS);
        return AuthServer.hasRight(loginUser, host, nRightIndex);
    }

    /**
     * @param loginUser
     * @param out
     * @param objects
     * @throws Exception
     * @throws WCMException
     */
    private void writeHTMLFromObjects(User loginUser, JspWriter out,
            WebSites objects, RequestHelper currRequestHelper) throws Exception {
        for (int i = 0, nSize = objects.size(); i < nSize; i++) {
            WebSite host = (WebSite) objects.getAt(i);
            if (host == null)
                continue;

			if(currRequestHelper.getInt("SelectMobile", 0) == 1 && !host.isMobile()){
				continue;
			}

            writeWebSiteHTML(loginUser, out, host, currRequestHelper);
        }
    }

%>