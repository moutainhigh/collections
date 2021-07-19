<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSites" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	WebSites objs = (WebSites)request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
%>
<%
//5. 遍历生成表现
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			WebSite obj = (WebSite)objs.getAt(i - 1);
			if (obj == null)
				continue;
			int nRowId = obj.getId();
			boolean bIsSelected = strSelectedIds.indexOf(","+nRowId+",")!=-1;
			String sRightValue = getRightValue(obj, loginUser).toString();
			String sCrUser = CMyString.transDisplay(obj.getPropertyAsString("cruser"));
			String sCrTime = convertDateTimeValueToString(oMethodContext, obj.getPropertyAsDateTime("CrTime"));
			String sSiteName = CMyString.transDisplay(obj.getPropertyAsString("sitename"));
			String sSiteDesc = CMyString.transDisplay(obj.getPropertyAsString("sitedesc"));
%>
    <div class="thumb_item<%=(bIsSelected)?" thumb_item_active":""%>" id="thumb_item_<%=nRowId%>" itemId="<%=nRowId%>" right="<%=sRightValue%>" title="<%=LocaleServer.getString("website.label.websiteId", "编号")%>:&nbsp;<%=nRowId%>&#13;<%=LocaleServer.getString("website.label.sitename", "唯一标识")%>:&nbsp;<%=sSiteName%>&#13;<%=LocaleServer.getString("website.label.cruser", "创建者")%>:&nbsp;<%=sCrUser%>&#13;<%=LocaleServer.getString("website.label.crtime", "创建时间")%>:&nbsp;<%=sCrTime%>">
        <div class="thumb" id="thumb_<%=nRowId%>" contextmenu="1"></div> 
        <div class="attrs" id="thumb_attrs_<%=nRowId%>">
            <input id="cbx_<%=nRowId%>" type="checkbox"<%=(bIsSelected)?" checked":""%>/>
            <span id="desc_<%=nRowId%>" bind="thumb_edit_template"><%=sSiteDesc%></span>
        </div>
        <div class="editable" id="thumb_edit_<%=nRowId%>"></div>
    </div>
<%
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
%>
<%!
    private RightValue getRightValue(WebSite site, User loginUser) throws WCMException {
		try {
			if (loginUser.isAdministrator()) {
				return RightValue.getAdministratorRightValue();
			}
			return getRightValueByMember(site, loginUser);
		} catch (Exception e) {
			throw new WCMException(CMyString.format(LocaleServer.getString("website_thumb_query.jsp.fail2create_right_info", "构造[{0}]权限信息失败!"), new Object[]{site}),e);
		}
	}
%>
<script>
	try{
		myThumbList.init({
			SelectedIds : '<%=sOrigSelectedIds%>',
			RecordNum : <%=currPager.getItemCount()%>
		});
	}catch(err){
		//Just skip it.
	}
</script>