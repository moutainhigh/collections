<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channels" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	Channels objs = (Channels)request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
%>
<%
//5. 遍历生成表现
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			Channel obj = (Channel)objs.getAt(i - 1);
			if (obj == null)
				continue;
			int nRowId = obj.getId();
			int nChnlType = obj.getType();
			boolean bIsSelected = strSelectedIds.indexOf(","+nRowId+",")!=-1;
			String shasChildStyle = obj.getChildrenSize(null) > 0 ? "_folder" : "";
			String sRightValue = getRightValue(obj, loginUser).toString();
			String sCrUser = CMyString.transDisplay(obj.getPropertyAsString("cruser"));
			String sCrTime = convertDateTimeValueToString(oMethodContext, obj.getPropertyAsDateTime("CrTime"));
			String sChnlName = CMyString.transDisplay(obj.getPropertyAsString("chnlname"));
			String sChnlDesc = CMyString.transDisplay(obj.getPropertyAsString("chnldesc"));


%>
    <div class="thumb_item<%=(bIsSelected)?" thumb_item_active":""%>" id="thumb_item_<%=nRowId%>" itemId="<%=nRowId%>" right="<%=sRightValue%>" isVirtual="<%=obj.isVirtual()%>" chnlType="<%=nChnlType%>" title="<%=LocaleServer.getString("channel.label.websiteId", "编号")%>:&nbsp;<%=nRowId%>&#13;<%=LocaleServer.getString("channel.label.chnlname", "唯一标识")%>:&nbsp;<%=sChnlName%>&#13;<%=LocaleServer.getString("channel.label.cruser", "创建者")%>:&nbsp;<%=sCrUser%>&#13;<%=LocaleServer.getString("channel.label.crtime", "创建时间")%>:&nbsp;<%=sCrTime%>">
        <div contextmenu="1" class="thumb thumb<%=nChnlType%><%=shasChildStyle%>" id="thumb_<%=nRowId%>"></div> 
        <div class="attrs" id="thumb_attrs_<%=nRowId%>">
            <input id="cbx_<%=nRowId%>" type="checkbox"<%=(bIsSelected)?" checked":""%>/>
            <span id="desc_<%=nRowId%>" bind="thumb_edit_template"><%=sChnlDesc%></span>
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
    private RightValue getRightValue(Channel channel, User loginUser) throws WCMException {
		try {
			if (loginUser.isAdministrator() || loginUser.getName().equalsIgnoreCase(channel.getCrUserName())) {
				return RightValue.getAdministratorRightValue();
			}
			return getRightValueByMember(channel, loginUser);
		} catch (Exception e) {
			throw new WCMException(CMyString.format(LocaleServer.getString("channel_thumb_query.jsp.objnotfound", "构造[{0}]权限信息失败!"), new Object[]{channel}), e);
			//"构造[" + channel + "]权限信息失败!",);
		}
	}
%>
<script>
	try{
		myThumbList.init({
			SelectedIds : '<%=CMyString.filterForJs(sOrigSelectedIds)%>',
			RecordNum : <%=currPager.getItemCount()%>
		});
	}catch(err){
		//Just skip it.
	}
</script>