<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../error_for_dialog.jsp"%>
<%@ page import="com.trs.components.common.publish.widget.ContentStyle" %>
<%@ page import="com.trs.components.common.publish.widget.ContentStyles" %>
<%@ page import="com.trs.components.common.publish.widget.StylePathHelper" %>
<%@ page import="com.trs.components.common.publish.widget.SpecialAuthServer" %>
<%@ page import="java.io.File" %>
<%@include file="../../include/list_base.jsp"%>
<%@include file="../../include/public_server.jsp"%>
<%@include file="../../include/convertor_helper.jsp"%>
<%
	ContentStyles objs = (ContentStyles)request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	String sHostRightValue = SpecialAuthServer.getRightValue(loginUser, ContentStyle.class).toString();
	response.setHeader("HostRightValue",  sHostRightValue);
	boolean bCanAdd = SpecialAuthServer.hasRight(loginUser, new ContentStyle(), SpecialAuthServer.STYLE_ADD);
%>
<%
//5. 遍历生成表现
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			ContentStyle obj = (ContentStyle)objs.getAt(i - 1);
			if (obj == null)
				continue;
			int nRowId = obj.getId();

			//是否需要权限的处理
			String sRightValue = SpecialAuthServer.getRightValue(loginUser,obj).toString();
			//获取基本信息
			String sContentStyleName = CMyString.showNull(obj.getStyleName(),"");
			sContentStyleName = CMyString.filterForHTMLValue(sContentStyleName);
			String sCssFlag = CMyString.showNull(obj.getCssFlag(),"");
			sCssFlag = CMyString.filterForHTMLValue(sCssFlag);
			String sCrUser = CMyString.transDisplay(obj.getPropertyAsString("CrUser"));
			String sCrTime = convertDateTimeValueToString(oMethodContext, obj.getPropertyAsDateTime("CrTime"));
			int nPageStyleId = obj.getPageStyleId();
			String sThumb = CMyString.showNull(obj.getStyleThumb(),"");
			sThumb = CMyString.filterForHTMLValue(sThumb);
			String sThumn_url = "../file/read_image.jsp?FileName=" + sThumb;
			if(CMyString.isEmpty(sThumb)){
				sThumn_url = "./images/zt_wt.gif";
			}
			int nIsPrivate = obj.getIsPrivate();
			int nResourceContentStyleId = obj.getSrcContentStyleId();
			boolean bCanDel = SpecialAuthServer.hasRight(loginUser, obj, SpecialAuthServer.STYLE_DELETE);//nIsPrivate==1?true:false;
			boolean bCanEdit = SpecialAuthServer.hasRight(loginUser, obj, SpecialAuthServer.STYLE_EDIT);
			boolean bCanReset =(nIsPrivate==1 ||  nResourceContentStyleId <= 0 || obj.getPageStyleId() <=0) ? false : true;
			boolean bCanBrowse = SpecialAuthServer.hasRight(loginUser, obj, SpecialAuthServer.STYLE_BROWSE);
			// 如果 SrcContentStyleId 对应的对象不存在的话，
			// 执行 恢复默认 操作肯定会出错，
			// 所以，在此次再加强判断
			if(bCanReset){
				ContentStyle oSrcContentstyle = ContentStyle.findById(nResourceContentStyleId);
				if(oSrcContentstyle==null){
					bCanReset = false;
				}
			}



%>
<div class="thumb" id="thumb_item_<%=nRowId%>" itemId="<%=nRowId%>" rightValue="<%=sRightValue%>" ContentStyleName="<%=sContentStyleName%>" CssFlag="<%=sCssFlag%>" PageStyleId="<%=nPageStyleId%>"  bCanEdit="<%=bCanEdit%>" bCanReset="<%=bCanReset%>" bCanDel="<%=bCanDel%>"  bCanAdd="<%=bCanAdd%>">
	<div class="pic"><img border=0 alt="" <%=bCanEdit ? LocaleServer.getString("contentstyle_query.jsp.label.click2alter","title='点击此处对风格的缩略图进行修改'") : ""%> src="<%=sThumn_url%>" <%=bCanEdit ? "onclick='uploadStyleThumb(" + nRowId + ")'" : ""%>/></div>
	<div class="info">
		<div title="<%=CMyString.format(LocaleServer.getString("contentstyle_query.jsp.name_name", "名称：{0}&#13标识：{1}"), new String[]{sContentStyleName,sCssFlag})%>">
			<input type="checkbox" name="" value="" id="cbx_<%=nRowId%>"/> <label for="cbx_<%=nRowId%>"><span class="nameclass"><%=CMyString.truncateStr(sContentStyleName,30)%></span></label>
		</div>
		<div>
			<%=CMyString.format(LocaleServer.getString("contentstyle_query.jsp.name_crtime", "{0}于{1}创建"), new String[]{sCrUser,sCrTime+""})%>
		</div>
		<div class="cmds">
			<li class="edit <%=bCanEdit ? "" : "disableCls"%>" cmd="edit"  WCMAnt:param="contentstyle_query.jsp.alter">修改</li>
<%
	if(nPageStyleId == 0){
%>
			<li class="export <%=bCanBrowse ? "" : "disableCls"%>" cmd="export" WCMAnt:param="contentstyle_query.jsp.export" >导出</li>
<%
}	
%>
			<li class="preview <%=bCanBrowse ? "" : "disableCls"%>" cmd="preview" WCMAnt:param="contentstyle_query.jsp.preview">预览</li>
			<li class="more <%=(bCanAdd || bCanDel)? "" : "disableCls"%>" cmd="moreCmds" WCMAnt:param="contentstyle_query.jsp.more_oper">更多操作</li>
		</div>
	</div>	
</div>

<%
	}catch(Exception ex){
			ex.printStackTrace();
		}
	}
%>
<script>
	try{
		PageContext.drawNavigator({
			Num : <%=currPager.getItemCount()%>,
			PageSize : <%=currPager.getPageSize()%>,
			PageCount : <%=currPager.getPageCount()%>,
			CurrPageIndex : <%=currPager.getCurrentPageIndex()%>
		});
	}catch(err){
		alert(err.message);
		//Just skip it.
	}
</script>