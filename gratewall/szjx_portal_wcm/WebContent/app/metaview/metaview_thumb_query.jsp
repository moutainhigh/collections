<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.components.metadata.definition.MetaViews" %>
<%
try{
%>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof MetaViews)){
		throw new WCMException(CMyString.format(LocaleServer.getString("thumb_query.type","服务(com.trs.components.metadata.service.MetaDataDefServiceProvider.query)返回的对象集合类型不为MetaViews，而为{0}，请确认。"),new Object[]{result.getClass()}));
	}
	MetaViews objs = (MetaViews)result;
	if (!loginUser.isAdministrator()) {
        throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, LocaleServer.getString("thumb_query.not.administrator","您不是管理员，没有权限管理视图列表！"));
    }
%>
<%
//5. 遍历生成表现
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			MetaView obj = (MetaView)objs.getAt(i - 1);
			if (obj == null)
				continue;
			int nRowId = obj.getId();
			boolean bIsSelected = strSelectedIds.indexOf(","+nRowId+",")!=-1;
			String sRightValue = getRightValue(obj, loginUser).toString();
			boolean bIsSingleTable = obj.isMultiTable() ? false : true;
			String sRowClassName = i%2==0?"grid_row_even":"grid_row_odd";
			String sName = CMyString.transDisplay(obj.getPropertyAsString("VIEWNAME"));
			String sDesc = CMyString.transDisplay(obj.getPropertyAsString("VIEWDESC"));

			String sCrUser = CMyString.transDisplay(obj.getPropertyAsString("cruser"));
			String sCrTime = convertDateTimeValueToString(oMethodContext, obj.getPropertyAsDateTime("CrTime"));
%>
    <div class="thumb_item<%=(bIsSelected)?" thumb_item_active":""%>" id="thumb_item_<%=nRowId%>" itemId="<%=nRowId%>" right="<%=sRightValue%>" isSingleTable="<%=bIsSingleTable%>"
	title="<%=LocaleServer.getString("metaview.label.rowId", "编号")%>: <%=nRowId%>&#13;<%=LocaleServer.getString("metaview_select.label.enfieldname", "英文名称")%>：<%=sName%>&#13;<%=LocaleServer.getString("metaview_select.label.fieldname", "名称")%>：<%=sDesc%>&#13;<%=LocaleServer.getString("metaview_select.label.cruser", "创建者")%>: <%=sCrUser%>&#13;<%=LocaleServer.getString("metaview_select.label.crtime", "创建时间")%>：<%=sCrTime%>">
        <div class="thumb" id="thumb_<%=nRowId%>"></div> 
        <div class="attrs" id="thumb_attrs_<%=nRowId%>">
            <input id="cbx_<%=nRowId%>" type="checkbox"<%=(bIsSelected)?" checked":""%>/>
            <span id="desc_<%=nRowId%>" bind="thumb_edit_template"><%=sDesc%></span>
        </div>
        <div class="editable" id="thumb_edit_<%=nRowId%>"></div>
    </div>
<%
		}catch(Exception ex){
			ex.printStackTrace();
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
<%
}catch(Throwable tx){
	tx.printStackTrace();
	throw new WCMException(LocaleServer.getString("thumb_query.runExce","metaview_thumb_query.jsp运行期异常!"), tx);
}
%>