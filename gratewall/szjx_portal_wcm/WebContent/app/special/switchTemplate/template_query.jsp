<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
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
<%@include file="../../include/list_base.jsp"%>
<%@include file="../../include/public_server.jsp"%>
<%@include file="../../include/convertor_helper.jsp"%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof Templates)){
		throw new WCMException(CMyString.format(LocaleServer.getString("template_query.type","服务(com.trs.ajaxservice.TemplateServiceProvider.query)返回的对象集合类型不为Templates，而为{0}，请确认。"),new Object[]{result.getClass()}));
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
            return CMyString.format(LocaleServer.getString("template_query.label.unknow", "未知[{0}]"),new int[]{type});
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
		<td width="40" WCMAnt:param="list.selectall" id="selAll" class="selAll">序号</td>
		<td width="50" WCMAnt:param="template_query.type">类型</td>
		<td width="100%"><span WCMAnt:param="template_query.name">模板名称</span></td>
		<td width="100" WCMAnt:param="template_query.located">所属位置</td>
		<td width="80" WCMAnt:param="template_query.newestmodifu">最近修改人</td>
		<td width="80" WCMAnt:param="template_query.modifytime">修改时间</td>
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
			boolean bHasViewRight = AuthServer.hasRight(rightValue,24);//rightValue.isHasRight(24);
			IPublishFolder folder = findHost(obj);
			int folderId = folder.getId();
			int folderType = folder.getType();
			int nTemplateType = obj.getPropertyAsInt("TempType",0);
			// 排除嵌套模板
			if(nTemplateType == PublishConstants.TEMPLATE_TYPE_NESTED)continue;
			
			String sTempName = obj.getPropertyAsString("tempname");
			String sRowClassName = i%2==0?"grid_row_even":"grid_row_odd";
			String sLastUser = obj.getPropertyAsString("lastModifiedUser");
			if(CMyString.isEmpty(sLastUser)){
				sLastUser = obj.getPropertyAsString("Cruser");
			}
			sLastUser = CMyString.transDisplay(sLastUser);
			CMyDateTime oLastTime = obj.getPropertyAsDateTime("lastModifiedTime").isNull() ? obj.getPropertyAsDateTime("CrTime"):obj.getPropertyAsDateTime("lastModifiedTime");
			boolean bIsVisual = obj.getPropertyAsBoolean("Visual", false);
			//去掉所有的非可视化模板和可视化细览模板
			if( !bIsVisual || PublishConstants.TEMPLATE_TYPE_DETAIL == nTemplateType) {
				
				continue;
			}
%>
	<tr id="tr_<%=nRowId%>" rowid="<%=nRowId%>" itemId="<%=nRowId%>" class="grid_row" right="<%=sRightValue%>" rootId="<%=nRootId%>" folderId="<%=folderId%>" folderType="<%=folderType%>" tempType="<%=obj.getType()%>" VisualAble="<%=bIsVisual%>">
		<td style="text-align:center;"><input type="radio" id="cbx_<%=nRowId%>" class="grid_checkbox" name="RowId" value="<%=nRowId%>" tempType="<%=obj.getType()%>"/></td>
		<td id="TempType_<%=nRowId%>">
		<%=makeTemplateTypeName(nTemplateType)%></td>

		<td id="tempname_<%=nRowId%>" style="font-size:14px;text-align:left;padding-left:10px;" >
			<%=CMyString.transDisplay(sTempName)%><span class="<%=bIsVisual ? "icon_visual" : ""%>" title="<%=bIsVisual ? LocaleServer.getString("template_query.viewable","可视化模板类型" ): ""%>"></span></td>
		
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
</table>
<script>
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
	throw new WCMException(LocaleServer.getString("template_query.excep","template_query.jsp运行期异常!"), tx);
}
%>