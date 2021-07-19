<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.publish.region.RegionInfo" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.RightValue" %>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.cms.content.WCMSystemObject" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.webframework.context.MethodContext" %>
<%@include file="../include/findbyid_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
    try {
%>
<%
    RegionInfo obj = (RegionInfo) request
                .getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
        String sRegionName = obj.getName();
        String sCrUserName = obj.getCrUserName();
        String sCrTime = obj.getCrTime().toString("yyyy-MM-dd HH:mm:ss");
		String sEditable = "readonly";
%>
<!--//TODO type findbyid here-->

<div class="attribute_row doctitle <%=sEditable%> main_attr">
	<span class="wcm_attr_value" _fieldName="regionName" title="<%=CMyString.filterForHTMLValue(sRegionName)%>" _fieldValue="<%=CMyString.filterForHTMLValue(sRegionName)%>" validation="type:'string',required:'',max_len:'50',desc:'导读名称'" validation_desc="导读名称"  WCMAnt:paramattr="validation_desc:region_findbyid.jsp.regionName"><%=CMyString.filterForHTMLValue(sRegionName)%></span>
</div>
<div class="attribute_row_sep"></div>
<div class="attribute_row descinfo readonly">
	<span class="wcm_attr_value" title="<%=LocaleServer.getString("region.label.regionId", "导读编号")%>:&nbsp;<%=obj.getId()%>&#13;<%=LocaleServer.getString("region.label.cruser", "创建者")%>:&nbsp;<%=sCrUserName%>&#13;<%=LocaleServer.getString("region.label.crtime", "创建时间")%>:&nbsp;<%=sCrTime%>"><span WCMAnt:param="region_findbyid.jsp.user">用户</span><span class="value"><%=CMyString.filterForHTMLValue(sCrUserName)%></span><span WCMAnt:param="region_findbyid.jsp.create">创建于</span><span class="value"><%=sCrTime%></span></span>
</div>
<%
    } catch (Throwable tx) {
        tx.printStackTrace();
        throw new WCMException(CMyString.format(LocaleServer.getString(
                "regionbyid.runtime.error", LocaleServer.getString("region_findbyid.jsp.label.exception", "{0}_findbyid.jsp运行期异常!")),
                new String[] { "region" }), tx);
    }
%>









　

   
  
   
 
