<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>

<!------- WCM IMPORTS BEGIN ------------>
<%@ page import=" com.trs.cms.auth.domain.AuthServer"%>
<%@ page import=" com.trs.cms.auth.persistent.Group"%>
<%@ page import=" com.trs.cms.auth.persistent.Groups"%>
<%@ page import=" com.trs.cms.auth.persistent.Role"%>
<%@ page import=" com.trs.cms.auth.persistent.User"%>
<%@ page import=" com.trs.cms.content.CMSObj"%>
<%@ page import=" com.trs.components.wcm.content.persistent.BaseChannel"%>
<%@ page import=" com.trs.components.wcm.content.persistent.Channel"%>
<%@ page import=" com.trs.components.wcm.content.persistent.Channels"%>
<%@ page import=" com.trs.components.wcm.content.persistent.WebSite"%>
<%@ page import=" com.trs.components.wcm.individuation.IndividuationMgr"%>
<%@ page import=" com.trs.infra.common.WCMRightTypes"%>
<%@ page import=" com.trs.infra.common.WCMTypes"%>
<%@ page import=" com.trs.infra.util.CMyString"%>
<%@ page import=" com.trs.presentation.util.RequestHelper"%>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%!
	Channel m_oCurrChannel = null;
	CMSObj m_oViewer = null;
	int m_nRightIndex = -1;
	RequestHelper currRequestHelper = null;
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title WCMAnt:param="nav_tree_select_individual.jsp.title">资源树</title>
<style>
	body{
		height:100%;
		width:100%;
		padding:0;
		background:transparent;
		margin:0;
		border:0;
	}
</style>
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../js/data/locale/nav_tree.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.tree/TreeNav.js"></script>
<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
<SCRIPT LANGUAGE="JavaScript">
<!--
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
	return TreeNav.getCheckValues('divTreeRegion', true);
}

function getCheckNames(){
	return TreeNav["SelectedNames"];
}

TreeNav.observe("onload", function(){
	var SelectedChannelIds = getParameter("SelectedChannelIds");
	if(!SelectedChannelIds) return;
	SelectedChannelIds = SelectedChannelIds.split(","); 
	for(var i = 0; i < SelectedChannelIds.length; i++){
		try{
			$('cc_' + SelectedChannelIds[i]).checked = true;
		}catch(error){}
	}
	var CurrChannelId = getParameter("CurrChannelId");
	if(!CurrChannelId) return;
	try{
		Element.addClassName('ac_' + CurrChannelId, "Selected");
	}catch(error){}
});

var isDebug = false;
if(isDebug){
	document.write("<button type='button' onclick='alert(getCheckValues());'>getCheckValues</button>");
	document.write("<button type='button' onclick='alert(getCheckNames());'>getCheckNames</button>");
}
//-->
</SCRIPT>

<div class="TreeView" id="divTreeRegion">
	<div title="<%=LocaleServer.getString("nav_tree.label.individualChnl", "我定制的栏目")%>" id="i_3" classPre="SiteType3" OnlyNode="true">
		<a href="#" name="ai_3" WCMAnt:param="nav_tree_select_individual.jsp.individualChnl">我定制的栏目</a>
	</div>
	<ul id="i_3_children" SiteType="3">
		<div style="display:none;"></div>
		<%
			//init member fields start
			this.currRequestHelper = currRequestHelper;
			int nChannelId = currRequestHelper.getInt("CurrChannelId", 0);
			if (nChannelId > 0) {
				m_oCurrChannel = Channel.findById(nChannelId);
				if (m_oCurrChannel == null)
					throw new WCMException(CMyString.format(LocaleServer.getString("nav_tree_select_individual.jsp.channel_not_exists", "指定栏目[{0}]不存在！"), new int[]{nChannelId}));
			}
			m_oViewer = loginUser;
			m_nRightIndex =  currRequestHelper.getInt("RightIndex", WCMRightTypes.OBJ_ACCESS);
			//init member fields end

			String channelIds = new IndividuationMgr().getObjectIdsAsString(WCMTypes.OBJ_CHANNEL);
			writeHTMLFromObjects(Channels.findByIds(loginUser, channelIds), out);
		%>
	</ul>
</div>
<%!
	private void writeHTMLFromObjects(Channels channels, JspWriter out) throws Exception {
		for(int i = 0, size = channels.size(); i < size; i++){
			Channel channel = (Channel)channels.getAt(i);
			if(channel == null) continue;
			writeSingleNodeHTML(channel, out);
		}
	}


    private void writeSingleNodeHTML(BaseChannel host, JspWriter out)
            throws Exception {
        String sDivId = "c_" + host.getId();
        String siteType = " SiteType='" + this.getSiteType(host) + "' ";
        out.println("<div title='"
                + CMyString.filterForHTMLValue(host.getDispDesc())
                + "' classPre='" + getClassPre(host) + "' "
                + siteType + getExtraHTMLAttribute(host) + " id='" + sDivId
                + "'>" + getNodeInnerHTML(host, sDivId) + "</div>");
    }

    private String getSiteType(BaseChannel host) throws WCMException {
        if (!host.isSite()) {
            host = WebSite.findById(host.getSiteId());
        }
        Number siteType = (Number) ((WebSite) host).getProperty("SiteType");
        return siteType.toString();
    }

    private String getClassPre(BaseChannel host) {
        if (host.isSite())
            return "site";
        Channel channel = (Channel) host;
        if (channel.isNormType())
            return "channel";
        return "channel" + channel.getType();
    }

    private String getExtraHTMLAttribute(BaseChannel host) throws WCMException {
        if (host.isSite())
            return " OnlyNode=\"true\" ";

        if (hasRight((Channel) host))
            return "";

        return " SDisabled=\"true\" ";
    }

    private String getNodeInnerHTML(BaseChannel host, String divId)
            throws Exception {
		return "<a href=\"#\"  name=\"a" + divId + "\" "
				+ getExtraAttrOfA(host) + ">" + "<label For=\"c" + divId
				+ "\" style=\"cursor:hand\">"
				+ CMyString.transDisplay(host.getDispDesc()) + "</label>"
				+ "</a>";
    }

    private String getExtraAttrOfA(BaseChannel host) {
        String sTitle = CMyString.format(LocaleServer.getString("nav_tree_select_individual.jsp.stitle", "编号：{0}\n创建者：{1}\n创建时间：{2}"), new Object[]{String.valueOf(host.getId()),CMyString.filterForHTMLValue(host.getName()),CMyString.filterForHTMLValue(host.getCrUserName()),host.getCrTime()});
        return " title=\"" + sTitle + "\" ";
    }

   private boolean hasRight(Channel _channel) throws WCMException {
        boolean bExcludeSelf = currRequestHelper.getBoolean("ExcludeSelf", false);
        if (m_oCurrChannel != null && bExcludeSelf
                && _channel.getId() == m_oCurrChannel.getId())
            return false;

        String sExcludeChannelIds = currRequestHelper.getString("ExcludeChannelIds");
        if (sExcludeChannelIds != null
                && sExcludeChannelIds.length() > 0
                && ("," + sExcludeChannelIds + ",").indexOf(","
                        + _channel.getId() + ",") >= 0) {
            return false;
        }

        boolean bExcludeVirtual = currRequestHelper.getBoolean("ExcludeVirtual", false);
        if (bExcludeVirtual && _channel.isVirtual())
            return false;

        boolean bIsLink = (_channel.getType() == Channel.TYPE_LINK);
        boolean bExcludeLink = currRequestHelper.getBoolean("ExcludeLink", false);
        if (bExcludeLink && bIsLink) {// 链接
            return false;
        }

        boolean bIsHostTop = _channel.getType() == Channel.TYPE_TOP_NEWS
                || _channel.getType() == Channel.TYPE_TOP_PICS;
        boolean bExcludeTop = currRequestHelper.getBoolean("ExcludeTop", false);
        if (bExcludeTop && bIsHostTop) {// 图片或头条
            return false;
        }

        boolean bExcludeOnlySearch = currRequestHelper.getBoolean("ExcludeOnlySearch", false);
        if (bExcludeOnlySearch && _channel.isVirtual() && !bIsHostTop) {// 仅检索
            return false;
        }

        boolean bExcludeParent = currRequestHelper.getBoolean("ExcludeParent", false);
        if (m_oCurrChannel != null && bExcludeParent
                && _channel.getId() == m_oCurrChannel.getParentId())
            return false;

        if (hasSuperRight(_channel, this.m_oViewer))
            return true;

        return hasRight(_channel, m_nRightIndex);
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

    private boolean hasRight(BaseChannel _oSiteOrChannel, int nRightIndex)
            throws WCMException {
        return AuthServer.hasRight(m_oViewer, _oSiteOrChannel, nRightIndex);
    }
%>