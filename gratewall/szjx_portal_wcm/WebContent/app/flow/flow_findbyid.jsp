<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.cms.process.definition.Flow" %>
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

<%!private RightValue getRightValue(Flow flow, MethodContext context,
            User loginUser) throws WCMException {
        RightValue rightValue = null;
        if (loginUser.isAdministrator()
                || loginUser.getName().equals(flow.getCrUserName())) {
            rightValue = new RightValue(RightValue.VALUE_ADMINISTRATOR);
        } else {
            rightValue = (RightValue) context.getContextCacheData("OwnerValue");
            if (rightValue == null) {
                // 从缓存获取
                CMSObj owner = (CMSObj) context.getContextCacheData("Owner");

                // 如果不存在直接从Flow上获取
                if (owner == null) {
                    owner = (CMSObj) flow.getOwner();
                }

                // 构造权限值
                if (owner != null && !(owner instanceof WCMSystemObject)) {
                    if (owner instanceof Channel) {
                        owner = ((Channel) owner).getSite();
                    }
                    rightValue = getRightValueByMember(owner, loginUser);
                } else {
                    rightValue = new RightValue(0);
                }
                context.putContextCacheData("OwnerValue", rightValue);

            }

        }
        return rightValue;
    }%>
<%
    Flow obj = (Flow) request
                .getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
        String sFlowName = obj.getName();
        String sCrUserName = obj.getCrUserName();
        String sCrTime = obj.getCrTime()
                .toString("yyyy-MM-dd HH:mm:ss");
        String sFlowDesc = obj.getDesc();
        boolean bFromChnl = oMethodContext.getValue("FromChnl", false);
        RightValue rightValue = getRightValue(obj, oMethodContext,
                loginUser);
        boolean bEditable = rightValue.isHasRight(42);
        String sEditable = (!bFromChnl && bEditable) ? "editable"
                : "readonly";
%>
<!--//TODO type findbyid here-->

<div class="attribute_row doctitle <%=sEditable%> main_attr">
	<span class="wcm_attr_value" _fieldName="flowName" title="<%=CMyString.filterForHTMLValue(sFlowName)%>" _fieldValue="<%=CMyString.filterForHTMLValue(sFlowName)%>" validation="type:'string',required:'',max_len:'50',desc:'工作流名称'" validation_desc="工作流名称"  WCMAnt:paramattr="validation_desc:flow_findbyid.jsp.flowName"><%=CMyString.filterForHTMLValue(sFlowName)%></span>
</div>
<div class="attribute_row_sep"></div>
<div class="attribute_row descinfo readonly">
	<span class="wcm_attr_value" title="<%=LocaleServer.getString("flow.label.flowId", "工作流编号")%>:&nbsp;<%=obj.getId()%>&#13;<%=LocaleServer.getString("flow.label.cruser", "创建者")%>:&nbsp;<%=sCrUserName%>&#13;<%=LocaleServer.getString("flow.label.crtime", "创建时间")%>:&nbsp;<%=sCrTime%>"><span WCMAnt:param="flow_findbyid.jsp.user">用户</span><span class="value"><%=CMyString.filterForHTMLValue(sCrUserName)%></span><span WCMAnt:param="flow_findbyid.jsp.create">创建于</span><span class="value"><%=sCrTime%></span></span>
</div>
<div class="attribute_row_sep"></div>
<div class="attribute_row <%=sEditable%>">
	<span class="wcm_attr_name" WCMAnt:param="flow_findbyid.jsp.desc">描述:</span>
	<span class="wcm_attr_value" _fieldName="flowDesc" _fieldValue="<%=CMyString.filterForHTMLValue(sFlowDesc)%>" validation="type:'string',max_len:'200',desc:'工作流描述'"  validation_desc="工作流描述" WCMAnt:paramattr="validation_desc:flow_findbyid.jsp.flowdesc"><%=CMyString.filterForHTMLValue(sFlowDesc)%></span>
</div>
<%
    } catch (Throwable tx) {
        tx.printStackTrace();
        throw new WCMException(LocaleServer.getString("flow_findbyid.jsp.label.defaultchannel", "workflow_findbyid.jsp运行期异常!"),tx);
    }
%>









　

   
  
   
 
