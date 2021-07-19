<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	language="java" errorPage=""%>
<%@page import="com.trs.components.wcm.content.persistent.BaseChannel"%>
<%@page import="com.trs.DreamFactory"%>
<%@page import="com.trs.cms.auth.domain.IObjectMemberMgr"%>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel"%>
<%@ page import="com.trs.components.wcm.content.persistent.Document"%>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@ page import="com.trs.infra.common.WCMException"%>
<%@ page import="com.trs.ajaxservice.WebSiteServiceProvider"%>
<%@ page import="com.trs.cms.auth.domain.AuthServer"%>
<%@ page import="com.trs.presentation.util.LoginHelper"%>
<%@ page import="com.trs.cms.auth.persistent.User"%>
<%@ page import="com.trs.infra.util.ExceptionNumber"%>
<%@ page import="com.trs.infra.common.WCMException"%>
<%@ page import="com.trs.cms.auth.persistent.RightValue"%>
<%@ page import="com.trs.cms.content.CMSObj"%>
<%
	request.setCharacterEncoding("ISO-8859-1");
	LoginHelper currLoginHelper = new LoginHelper(request, application);
	if (!currLoginHelper.checkLogin()) {
		throw new WCMException(ExceptionNumber.ERR_USER_NOTLOGIN,
				"用户未登录或登录超时！");
	}
	User loginUser = currLoginHelper.getLoginUser();
	String sObjectDesc = CMyString.getStr(request
			.getParameter("objectdesc"));

	String sSiteType = request.getParameter("sitetypeid");
	if (sSiteType != null) {
		String sSiteTypeDesc = "";
		try{
			sSiteTypeDesc = WebSiteServiceProvider.findSiteTypeDesc(Integer.parseInt(sSiteType));
		}catch(Throwable t){
			//never
		}
		String sSiteHostDescHtml = "&nbsp;";
		sSiteHostDescHtml += "<a href='#' class=\"navigation_website\" onclick=\"WebSites.loadWebSites();\"  title='"
				+ sSiteTypeDesc + "'>";
		sSiteHostDescHtml += getDescString(sSiteTypeDesc);
		sSiteHostDescHtml += "</a>";
		out.print(sSiteHostDescHtml);
		return;
	}

	String sSiteId = request.getParameter("siteid");
	String sChnlId = request.getParameter("channelid");
	String sDocId = request.getParameter("documentid");

	Channel currChnl = null;
	WebSite currSite = null;
	try {
		int docId = 0;
		int chnlId = 0;
		int siteId = 0;
		if (!CMyString.isEmpty(sDocId)
				&& (docId = Integer.parseInt(sDocId)) != 0) {
			currChnl = Document.findById(docId).getChannel();
		} else if (!CMyString.isEmpty(sChnlId)
				&& (chnlId = Integer.parseInt(sChnlId)) != 0) {
			currChnl = Channel.findById(chnlId);
		} else if (!CMyString.isEmpty(sSiteId)
				&& (siteId = Integer.parseInt(sSiteId)) != 0) {
			currSite = WebSite.findById(siteId);
		}

		if (currChnl != null) {
			if (!"true".equals(request.getParameter("NotTraceSite"))) {
				out.print(getNavHtml(currChnl, sObjectDesc, loginUser));
			} else {
				out.print(getNavWithoutSiteTrace(currChnl, sObjectDesc,
						loginUser));
			}
		} else if (currSite != null) {
			String sSiteDescHtml = "<span class='navigation_channel'><a id='navigation_site_entity' _siteid='"
					+ CMyString.filterForHTMLValue(String.valueOf(currSite.getId()))
					+ "' href='#' title='"
					+ CMyString.filterForHTMLValue(currSite.getDesc()) + "' onclick=\"trace('";
			sSiteDescHtml += CMyString.filterForJs(String.valueOf(currSite.getId()));
			sSiteDescHtml += "', true,'";
			sSiteDescHtml += CMyString.filterForJs(getRightValue(currSite, loginUser));
			sSiteDescHtml += "'); return false\" class='navigation_channel_link_current'>";
			sSiteDescHtml += CMyString.transDisplay(getDescString(currSite.getDesc()));
			sSiteDescHtml += "</a></span>";
			if (!CMyString.isEmpty(sObjectDesc)) {
				sSiteDescHtml += "<span class=\"navigation_channel_desc\" title='"
						+ CMyString.filterForHTMLValue(sObjectDesc) + "'> :&nbsp;";
				sSiteDescHtml += CMyString.transDisplay(getDescString(sObjectDesc));
				sSiteDescHtml += "</span>";
			}
			out.print(sSiteDescHtml);
		}
	} catch (Throwable ex) {
		out.print(ex);
	}
%>
<%!
public RightValue getRightValueByMember(CMSObj obj, User user) throws WCMException {
	try {
		if (user.isAdministrator()) {
			return RightValue.getAdministratorRightValue();
		}
		RightValue oRightValue;
		if(obj instanceof BaseChannel){
			IObjectMemberMgr oObjectMemberMgr = (IObjectMemberMgr) DreamFactory.createObjectById("IObjectMemberMgr");
			if(oObjectMemberMgr.canOperate(user, obj.getWCMType(), obj.getId())){
				oRightValue = AuthServer.getRightValue(obj, user);
			}else{
				oRightValue = new RightValue();
			}
		}else{
			oRightValue = AuthServer.getRightValue(obj, user);
		}
		return oRightValue;
	} catch (Exception e) {
		throw new WCMException("构造[" + obj + "]权限信息失败!", e);
	}
}
private String getNavHtml(Channel _currChannel, String _sDesc,
			User loginUser) throws WCMException {
		StringBuffer sbHtml = new StringBuffer();

		if (_currChannel == null) {
			sbHtml.append(_sDesc);
		} else {
			Channel parent = null;
			StringBuffer sbChannelNav = new StringBuffer();

			sbChannelNav.append("<span class=\"navigation_channel\" title='"
					+ _currChannel.getDesc() + "'>");
			sbChannelNav
					.append("<a id=\"navigation_channel_entity\" href=\"#\" class=\"navigation_channel_link_current\"  onclick=\"trace('"
							+ _currChannel.getId()
							+ "',false,'"
							+ getRightValue(_currChannel, loginUser)
							+ "',"
							+ _currChannel.getType() + "); return false;\" >");
			sbChannelNav.append(getDescString(_currChannel.getDesc()));
			sbChannelNav.append("</a></span>");
			if (!CMyString.isEmpty(_sDesc)) {
				sbChannelNav
						.append("<span class=\"navigation_channel_desc\" title='"
								+ _sDesc + "'> :&nbsp;");
				sbChannelNav.append(getDescString(_sDesc));
				sbChannelNav.append("&nbsp;</span>");
			}

			parent = _currChannel.getParent();
			while (parent != null) {
				if (parent == null) {
					break;
				}
				sbChannelNav.insert(0, "</span>");
				sbChannelNav.insert(0,
						" <span style='font-size: 16px;'>&gt;</span> ");
				sbChannelNav.insert(0, "<span class=\"navigation_channel\">");
				sbChannelNav.insert(0, "</a>");
				sbChannelNav.insert(0, getDescString(parent.getDesc()));
				sbChannelNav.insert(0,
						"<a href=\"#\" class=\"navigation_channel_link\"  onclick=\"trace('"
								+ parent.getId() + "',false,'"
								+ getRightValue(parent, loginUser) + "',"
								+ parent.getType()
								+ "); return false;\" title='"
								+ parent.getDesc() + "'>");
				parent = parent.getParent();
			}
			sbChannelNav.insert(0, "</span>");
			sbChannelNav.insert(0,
					" <span style='font-size: 16px;'>&gt;</span> ");
			sbChannelNav.insert(0, "<span class=\"navigation_channel\">");
			sbChannelNav.insert(0, "</a></span>");
			sbChannelNav.insert(0, getDescString(_currChannel.getSite()
					.getDesc()));
			int nSiteId = _currChannel.getSiteId();
			sbChannelNav
					.insert(
							0,
							"<a id=\"navigation_site_entity\" _siteid='"
									+ nSiteId
									+ "' href=\"#\" class=\"navigation_channel_link\"  onclick=\"trace('"
									+ nSiteId
									+ "', true,'"
									+ getRightValue(_currChannel.getSite(),
											loginUser)
									+ "');return false;\" title='"
									+ _currChannel.getSite().getDesc() + "'>");
			sbChannelNav.insert(0, "<span class=\"navigation_channel\">");
			sbHtml.append(sbChannelNav);
		}//end if

		return sbHtml.toString();
	}//END: getNavHtml

	//wenyh@2007-08-02 added.
	//有些地方不站点上不需要链接
	private String getNavWithoutSiteTrace(Channel channel, String desc,
			User user) throws WCMException {
		if (channel == null)
			return desc;

		StringBuffer navHtml = new StringBuffer(256);
		String tempDesc = channel.getSite().getDesc();
		navHtml.append("<span class='navigation_channel'>");
		navHtml.append(getDescString(tempDesc));
		navHtml.append("</span>");
		navHtml.append("<span style='font:16px;'>&nbsp;&gt;</span>");
		Channel parent = channel.getParent();
		while (parent != null) {
			navHtml.append("<span class='navigation_channel'>");
			navHtml.append("<a href='#' class='navigation_channel_link'");
			navHtml.append(" onclick='trace(");
			navHtml.append(parent.getId()).append(",");
			navHtml.append(false).append(",");
			navHtml.append("\"").append(getRightValue(parent, user)).append(
					"\"").append(",");
			navHtml.append(parent.getType()).append(");return false'");
			tempDesc = parent.getDesc();
			navHtml.append(" title='").append(tempDesc).append("'>");
			navHtml.append(getDescString(tempDesc));
			navHtml.append("</a>");
			navHtml.append("</span>");
			navHtml.append("<span style='font:16px;'>&nbsp;&gt; </span>");
			parent = parent.getParent();
		}

		navHtml.append("<span class='navigation_channel'>");
		navHtml.append("<a href='#' class='navigation_channel_link'");
		navHtml.append(" onclick='trace(");
		navHtml.append(channel.getId()).append(",");
		navHtml.append(false).append(",");
		navHtml.append("\"").append(getRightValue(channel, user)).append("\"")
				.append(",");
		navHtml.append(channel.getType()).append(");return false'");
		tempDesc = channel.getDesc();
		navHtml.append(" title='").append(tempDesc).append("'>");
		navHtml.append(getDescString(tempDesc));
		navHtml.append("</a>");
		navHtml.append("</span>");

		return navHtml.toString();
	}

	private String getRightValue(CMSObj currObj, User _currUser)
			throws WCMException {
		String sRightValue = "";
		if (_currUser.equals(currObj.getCrUser())
				|| _currUser.isAdministrator()) {
			//如果是CrUser，则64位权限全部置为1
			sRightValue = "1111111111111111111111111111111111111111111111111111111111111111";
		} else {
			RightValue rightValue = getRightValueByMember(currObj, _currUser);
			if (rightValue != null) {
				sRightValue = rightValue.toString().toString();
			}
		}
		return sRightValue;
	}

	private String getDescString(String desc) {
		//return CMyString.truncateStr(CMyString.filterForHTMLValue(desc), 40);
		//wenyh@2007-08-13 truncate first.
		return CMyString.filterForHTMLValue(CMyString.truncateStr(desc, 18));
	}%>