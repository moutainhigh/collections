<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.common.publish.persistent.template.Template" %>
<%@ page import="com.trs.components.common.publish.persistent.template.Templates" %>
<%@ page import="com.trs.components.common.publish.PublishConstants" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishFolder" %>
<%@ page import="com.trs.webframework.xmlserver.ConvertException" %>

<%
try{
%>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof Templates)){
		throw new WCMException(CMyString.format(LocaleServer.getString("template_select_query.jsp.service", "服务(com.trs.ajaxservice.TemplateServiceProvider.query)返回的对象集合类型不为Templates，而为[{0}]，请确认。!"), new Object[]{result.getClass()}));
		//"服务(com.trs.ajaxservice.TemplateServiceProvider.query)返回的对象集合类型不为Templates，而为"+(result.getClass())+"，请确认。");
	}
	Templates objs = (Templates)result;
	
%>
<%!
	private String makeTemplateTypeName(int type) {
        switch (type) {
        case PublishConstants.TEMPLATE_TYPE_NESTED:
            return LocaleServer.getString("template_selquery.label.nested", "嵌套");
        case PublishConstants.TEMPLATE_TYPE_OUTLINE:
            return LocaleServer.getString("template_selquery.label.outline", "概览");
        case PublishConstants.TEMPLATE_TYPE_DETAIL:
            return LocaleServer.getString("template_selquery.label.detail", "细览");
        default:
            return CMyString.format(LocaleServer.getString("template_selquery.jsp.unknow", "未知[{0}]"), new int[]{type});
			//LocaleServer.getString("template_selquery.label.unknow", "未知")+"[" + type + "]";
        }
    }

	private IPublishFolder findHost(Template _template) throws ConvertException {
        IPublishFolder folder = null;
        try {
            folder = _template.getFolder();
        } catch (WCMException e) {
            throw new ConvertException(
                    ExceptionNumber.ERR_TEMPLATE_HOST_NOT_FOUND,
                    LocaleServer.getString("template_addedit_label_4","获取模板所处的站点/栏目发生异常!"), e);
        }
        return folder;
    }
%>

<table id="table_cnt" cellspacing=0 border="0" cellpadding=0 style="width:100%;border-top:1px dotted gray">
		
	<tbody>
<%
//5. 遍历生成表现
	String sType = CMyString.showNull(oMethodContext.getValue("selecttype"), "radio");
	int nNum = 0;
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			Template obj = (Template)objs.getAt(i - 1);
			if (obj == null)
				continue;
			int nRowId = obj.getId();
			boolean bIsSelected = strSelectedIds.indexOf(","+nRowId+",")!=-1;
			IPublishFolder folder = findHost(obj);
			boolean bIsVisual = obj.getPropertyAsBoolean("Visual", false);
			
%>
<%
			if(nNum%2==0){
				out.println("<tr>");
			}	
			nNum++;
%>
	<td width="50%" style="line-height:14px;" height="20" title="<%=LocaleServer.getString("template.label.tempName", "模板名称：")%> <%=CMyString.transDisplay(obj.getPropertyAsString("tempname"))%>&#13;<%=LocaleServer.getString("template.label.folder", "所属位置：")%> <%=folder.getInfo()%>&#13;<%=LocaleServer.getString("template.label.cruser", "创建者：")%> <%=obj.getPropertyAsString("crUser")%>&#13;<%=LocaleServer.getString("template.label.crtime", "创建时间：")%><%=convertDateTimeValueToString(oMethodContext,obj.getPropertyAsDateTime("crTime"))%>&#13;<%=LocaleServer.getString("template.label.tempmode", "模板类型：")%><%=bIsVisual?"可视化模板":"普通模板"%>">
		<span style="float:left;width: 13px;" class="sn"><%=(i)%>.</span>
		<input type="<%=CMyString.filterForHTMLValue(sType)%>" style="float:left;padding-left:13px;" class="chk_tempid" name="TempId" value="<%=nRowId%>" id="chk_<%=nRowId%>" _name="<%=CMyString.transDisplay(obj.getPropertyAsString("tempname"))%>"><span style="float:left;padding-left:14px;" WCMAnt:paramattr="title:template_select_list.head.floder" title="属于： <%=folder.getInfo()%>" class="flag_own_<%=obj.getFolderType()%>">&nbsp;</span>
		<span class="sp_name" _id="<%=nRowId%>" style="float:left;padding-left:15px;cursor: pointer; width: 100px; white-space:nowrap; text-overflow:ellipsis; overflow:hidden;<%=bIsVisual?"color:blue;":""%>" for="chk_<%=nRowId%>"><%=CMyString.transDisplay(obj.getPropertyAsString("tempname"))%></span>
	</td>
<%
			if(nNum%2==0){
				out.println("</tr>");
			}	
%>
<%
		}catch(Exception ex){
			ex.printStackTrace();
		}//end try
	}//end for
	if(nNum%2==1){
		out.println("<td>&nbsp;</td></tr>");
	}
	if(nNum==0){
%>
	<tr><td colspan="2" class="no_object_found" WCMAnt:param="list.NoObjectFound">不好意思, 没有找到符合条件的对象!</td></tr>
<%
	}
%>

	</tbody>
</table>
<script>
	try{
		PageContext.drawNavigator({
			Num : <%=currPager.getItemCount()%>,
			PageSize : <%=currPager.getPageSize()%>,
			PageCount : <%=currPager.getPageCount()%>,
			CurrPageIndex : <%=currPager.getCurrentPageIndex()%>
		});
	}catch(err){
		//Just skip it.
	}
</script>
<%
}catch(Throwable tx){
	tx.printStackTrace();
	throw new WCMException(LocaleServer.getString("template_select_query.jsp.label.runtimeexception", "template_query.jsp运行期异常!"), tx);
}
%>