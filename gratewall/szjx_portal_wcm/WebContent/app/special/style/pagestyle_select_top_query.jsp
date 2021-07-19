<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../error_for_dialog.jsp"%>
<%@ page import="com.trs.components.common.publish.widget.PageStyle" %>
<%@ page import="com.trs.components.common.publish.widget.PageStyles" %>
<%@ page import="com.trs.components.common.publish.widget.StylePathHelper" %>
<%@ page import="com.trs.components.common.publish.widget.SpecialAuthServer" %>
<%@ page import="java.io.File" %>
<%@include file="../../include/list_base.jsp"%>
<%@include file="../../include/public_server.jsp"%>
<%@include file="../../include/convertor_helper.jsp"%>
<%
	boolean bCanAdd = SpecialAuthServer.hasRight(loginUser, PageStyle.createNewInstance(), SpecialAuthServer.STYLE_ADD);
	response.setHeader("bCanAdd",  String.valueOf(bCanAdd));
	PageStyles objs = (PageStyles)request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);

	//确保当前选中的风格一定要显示出来。
	MethodContext methodContext = (MethodContext)request.getAttribute(FrameworkConstants.ATTR_NAME_METHODCONTEXT);
	int nPageStyleId = methodContext.getValue("selectedids", 0);
	if(nPageStyleId != 0){
		int index = objs.indexOf(nPageStyleId) + 1;
		if(index < currPager.getFirstItemIndex() || index > currPager.getLastItemIndex()){//没有包含在objs中
			PageStyle oPageStyle = PageStyle.findById(nPageStyleId);
			if(oPageStyle != null){//复制一个新集合，并将选中的对象放在第一位
				PageStyles newObjs = PageStyles.createNewInstance(ContextHelper.getLoginUser());
				newObjs.addElement(oPageStyle, currPager.getFirstItemIndex() - 1);
				for (int i = currPager.getFirstItemIndex(); i < currPager.getLastItemIndex(); i++) {
					PageStyle obj = (PageStyle)objs.getAt(i - 1);
					if (obj == null)
						continue;
					newObjs.addElement(obj, i);					
				}
				objs = newObjs;
			}
		}
	}	
%>
<%
//5. 遍历生成表现
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			PageStyle obj = (PageStyle)objs.getAt(i - 1);
			if (obj == null)
				continue;
			int nRowId = obj.getId();

			//是否需要权限的处理
			
			//获取基本信息
			String sPageStyleName = CMyString.showNull(obj.getStyleName(),"");
			String sPageStyleDesc = CMyString.showNull(obj.getStyleDesc(),"");
			sPageStyleDesc = CMyString.filterForHTMLValue(sPageStyleDesc);
			String sCrUser = CMyString.transDisplay(obj.getPropertyAsString("CrUser"));
			String sCrTime = convertDateTimeValueToString(oMethodContext, obj.getPropertyAsDateTime("CrTime"));
			int nIsImport = obj.getIsImport();
			String sThumb = CMyString.showNull(obj.getStyleThumb(),"");
			sThumb = CMyString.filterForHTMLValue(sThumb);
			String sThumn_url = "file/read_image.jsp?FileName=" + sThumb;
			if(CMyString.isEmpty(sThumb)){
				sThumn_url = "images/zt_wt.gif";
			}
			boolean bCanDelete = SpecialAuthServer.hasRight(loginUser, obj, SpecialAuthServer.STYLE_DELETE);
			boolean bCanEdit = SpecialAuthServer.hasRight(loginUser, obj, SpecialAuthServer.STYLE_EDIT);
			boolean bCanBrowse = SpecialAuthServer.hasRight(loginUser, obj, SpecialAuthServer.STYLE_BROWSE);
			String sCssDir = "/template/style/style_css/";
			String sCssFilePath = CMyString.filterForHTMLValue(sCssDir + sPageStyleName + ".css");
%>
<div class="thumb" id="thumb_item_<%=nRowId%>" itemId="<%=nRowId%>" StyleDesc="<%=sPageStyleDesc%>">
	<div class="pic">
		<img border=0 alt="" title="<%=sPageStyleDesc%>" src="<%=sThumn_url%>" />
	</div>
	<div class="info">
		<div>
			<input type="radio" name="" value="<%=nRowId%>" cssFile="<%=sCssFilePath%>" id="cbx_<%=nRowId%>"/> 
			<label for="cbx_<%=nRowId%>"><%=CMyString.transDisplay(obj.getStyleDesc())%></label>
		</div>
		<div style="display:none;">
			<span><%= CMyString.format(LocaleServer.getString("pagestyle_query.jsp.who_and_when_create", "{0}于{1}创建"), new String[]{sCrUser,
		sCrTime+""})%></span>
		</div>
	</div>	
</div>
<%
		}catch(Exception ex){
				ex.printStackTrace();
		}
	}
%>
<%if(currPager.getPageCount() > 1){%>
<div class="morePageStyle" WCMAnt:param="pagestyle_query.jsp.more">更多...</div>
<%}%>