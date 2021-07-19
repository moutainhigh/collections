<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.components.common.publish.persistent.template.Template" %>
<%@ page import="com.trs.components.common.publish.persistent.template.Templates" %>
<%@ page import="com.trs.components.common.publish.PublishConstants" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishFolder" %>
<%@ page import="com.trs.webframework.xmlserver.ConvertException" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.ajaxservice.TemplateAuthHelper" %>
<%@ page import="com.trs.cms.auth.persistent.RightValue" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishFolder" %>
<%
try{
%>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof Templates)){
		throw new WCMException(CMyString.format(LocaleServer.getString("template_query.jsp.service", "服务(com.trs.ajaxservice.TemplateServiceProvider.query)返回的对象集合类型不为Templates，而为[{0}]，请确认。!"), new Object[]{result.getClass()}));
	}
	Templates objs = (Templates)result;
%>
<%!
	private String makeTemplateTypeName(int type) {
        switch (type) {
        case PublishConstants.TEMPLATE_TYPE_NESTED:
            return LocaleServer.getString("template_query.label.nested", "嵌套");
        case PublishConstants.TEMPLATE_TYPE_OUTLINE:
            return LocaleServer.getString("template_query.label.outline", "概览");
        case PublishConstants.TEMPLATE_TYPE_DETAIL:
            return LocaleServer.getString("template_query.label.detail", "细览");
		 case PublishConstants.TEMPLATE_TYPE_INFOVIEWPRINT:
            return LocaleServer.getString("template_query.label.infoviewprint", "表单打印");
        default:
            return CMyString.format(LocaleServer.getString("template_query.jsp.unknow", "未知[{0}]"), new int[]{type});
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
    public RightValue getRightValue(Template template, User loginUser,
            MethodContext context) throws WCMException {
        RightValue rightValue = null;
        if (loginUser.isAdministrator()
                || loginUser.getName().equals(template.getCrUserName())) {
            rightValue = new RightValue(RightValue.VALUE_ADMINISTRATOR);
        } else {
            IPublishFolder host = (IPublishFolder) context
                    .getContextCacheData("Host");
            rightValue = TemplateAuthHelper.makeTemplateRightValue(context,
                    loginUser, template, null);
        }
        return rightValue;
    }
%>

<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
	<tr id="grid_head" class="grid_head">
		<td onclick="wcm.Grid.selectAll();" width="50" WCMAnt:param="list.selectall" class="selAll">全选</td>
		<td WCMAnt:param="template_list.head.preview" width="40">预览</td>
<td grid_sortby="TempType" width="60"><span WCMAnt:param="template_list.head.TempType">类型</span><%=getOrderFlag("TempType", sCurrOrderBy)%></td>
<td grid_sortby="tempname"><span WCMAnt:param="template_list.head.tempname">模板名称</span><%=getOrderFlag("tempname", sCurrOrderBy)%></td>
<td width="130"><span WCMAnt:param="template_list.head.FolderId">所属位置</span></td>
<td width="80"><span WCMAnt:param="template_list.head.lastModifiedUser">最近修改人</span></td>
<td grid_sortby="lastModifiedTime" width="75"><span WCMAnt:param="template_list.head.lastModifiedTime">修改时间</span><%=getOrderFlag("lastModifiedTime", sCurrOrderBy)%></td>

	</tr>
	<tbody class="grid_body" id="grid_body">
<%
//5. 遍历生成表现
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			Template obj = (Template)objs.getAt(i - 1);
			if (obj == null)
				continue;
			int nRowId = obj.getId();
			int nRootId = obj.getRootId(); 
			boolean bIsSelected = strSelectedIds.indexOf(","+nRowId+",")!=-1;
			RightValue rightValue = getRightValue(obj, loginUser, oMethodContext);
			String sRightValue = rightValue.toString();
			boolean bCanEdit = AuthServer.hasRight(rightValue,23);//rightValue.isHasRight(23);//hasRight(loginUser,obj,23);
			boolean bCanPreview = AuthServer.hasRight(rightValue,24);//hasRight(loginUser,obj,24);
			boolean bHasViewRight = AuthServer.hasRight(rightValue,24);//rightValue.isHasRight(24);
			IPublishFolder folder = findHost(obj);
			int folderId = folder.getId();
			int folderType = folder.getType();
			String sRowClassName = i%2==0?"grid_row_even":"grid_row_odd";
			String sLastUser = obj.getPropertyAsString("lastModifiedUser");
			if(CMyString.isEmpty(sLastUser)){
				sLastUser = obj.getPropertyAsString("Cruser");
			}
			sLastUser = CMyString.transDisplay(sLastUser);
			CMyDateTime oLastTime = obj.getPropertyAsDateTime("lastModifiedTime").isNull() ? obj.getPropertyAsDateTime("CrTime"):obj.getPropertyAsDateTime("lastModifiedTime");
			boolean bIsVisual = obj.getPropertyAsBoolean("Visual", false);
%>
	<tr id="tr_<%=nRowId%>" rowid="<%=nRowId%>" class="grid_row <%=sRowClassName%> <%=(bIsSelected)?" grid_row_active":""%> <%=(bHasViewRight)?"":"grid_selectdisable_row"%>" right="<%=sRightValue%>" rootId="<%=nRootId%>" folderId="<%=folderId%>" folderType="<%=folderType%>" tempType="<%=obj.getType()%>" VisualAble="<%=bIsVisual%>">
		<td><input type="checkbox" id="cb_<%=nRowId%>" class="grid_checkbox" name="RowId" value="<%=nRowId%>" <%=(bHasViewRight)?"":"disabled"%> <%=(bIsSelected)?"checked":""%>/><span class="grid_index" id="grid_index_<%=nRowId%>"><%=i%></span></td>
		
		<td><span class="<%=(bCanPreview)?"object_preview":"objectcannot_preview"%> grid_function" grid_function="preview">&nbsp;</span></td>
<td id="TempType_<%=nRowId%>">
		<%=makeTemplateTypeName(obj.getPropertyAsInt("TempType",0))%></td>
<td id="tempname_<%=nRowId%>" style="font-size:14px;text-align:left;padding-left:10px;" >
		<a href="#" onclick="return false;" class="<%=(bCanEdit)?"object_edit":"objectcannot_edit"%>" grid_function="edit" title="[模板-<%=nRowId%>]" contextmenu="1" id="tempname_<%=nRowId%>" WCMAnt:paramattr="title:template_list.head.template"><%=CMyString.transDisplay(obj.getPropertyAsString("tempname"))%><span class="<%=bIsVisual ? "icon_visual" : ""%>" title="<%=bIsVisual ? "可视化模板类型" : ""%>"></span></a></td>
<td id="FolderId_<%=nRowId%>">
		<%=CMyString.transDisplay(folder.getInfo())%></td>
<td id="lastModifiedUser_<%=nRowId%>">
		<%=sLastUser%></td>
<td id="lastModifiedTime_<%=nRowId%>">
		<%=convertDateTimeValueToString(oMethodContext,oLastTime)%></td>

	</tr>
<%
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
%>
	</tbody>
	<tbody id="grid_NoObjectFound" style="display:none;">
		<tr><td colspan="7" class="no_object_found" WCMAnt:param="list.NoObjectFound">不好意思, 没有找到符合条件的对象!</td></tr>
	</tbody>
</table>
<script>
	wcm.Grid.init({
		OrderBy : '<%=sCurrOrderBy%>',
		RecordNum : <%=currPager.getItemCount()%>
	});
	PageContext.drawNavigator({
		Num : <%=currPager.getItemCount()%>,
		PageSize : <%=currPager.getPageSize()%>,
		PageCount : <%=currPager.getPageCount()%>,
		CurrPageIndex : <%=currPager.getCurrentPageIndex()%>
	});
</script>
<%
}catch(Throwable tx){
	tx.printStackTrace();
	throw new WCMException(LocaleServer.getString("template_query.jsp.label.runtimeexception", "template_query.jsp运行期异常!"), tx);
}
%>