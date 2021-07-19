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
    PageStyles objs = (PageStyles)request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
    String sHostRightValue = SpecialAuthServer.getRightValue(loginUser, PageStyle.class).toString();
    response.setHeader("HostRightValue",  sHostRightValue);
    boolean bCanAdd = SpecialAuthServer.hasRight(loginUser, new PageStyle(), SpecialAuthServer.STYLE_ADD);
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
            String sRightValue = SpecialAuthServer.getRightValue(loginUser,obj).toString();
            //获取基本信息
            String sPageStyleName = CMyString.showNull(obj.getStyleName(),"");
            sPageStyleName = CMyString.filterForHTMLValue(sPageStyleName);
            String sPageStyleDesc = CMyString.showNull(obj.getStyleDesc(),"");
            sPageStyleDesc = CMyString.filterForHTMLValue(sPageStyleDesc);
            String sCrUser = CMyString.transDisplay(obj.getPropertyAsString("CrUser"));
            String sCrTime = convertDateTimeValueToString(oMethodContext, obj.getPropertyAsDateTime("CrTime"));
            int nIsImport = obj.getIsImport();
            String sThumb = CMyString.showNull(obj.getStyleThumb(),"");
            sThumb = CMyString.filterForHTMLValue(sThumb);
            String sThumn_url = "../file/read_image.jsp?FileName=" + sThumb;
            if(CMyString.isEmpty(sThumb)){
                sThumn_url = "./images/zt_wt.gif";
            }
            boolean bCanDelete = SpecialAuthServer.hasRight(loginUser, obj, SpecialAuthServer.STYLE_DELETE);
            boolean bCanEdit = SpecialAuthServer.hasRight(loginUser, obj, SpecialAuthServer.STYLE_EDIT);
            boolean bCanBrowse = SpecialAuthServer.hasRight(loginUser, obj, SpecialAuthServer.STYLE_BROWSE);
%>
<div class="thumb" id="thumb_item_<%=nRowId%>" rightValue="<%=sRightValue%>" itemId="<%=nRowId%>" StyleDesc="<%=sPageStyleDesc%>" bCanAdd="<%=bCanAdd%>" bCanEdit="<%=bCanEdit%>">
    <div class="pic"><img border=0 alt="" <%=bCanEdit ? LocaleServer.getString("pagestyle_query.jsp.label.click_here2_alter_style_thumb","title=\'点击此处对风格的缩略图进行修改\'") : ""%> src="<%=sThumn_url%>" <%=bCanEdit ? "onclick='uploadStyleThumb(" + nRowId + ")'" : ""%>/></div>
    <div class="info">
        <div title='<%=LocaleServer.getString("pagestyle_query.jsp.label.zh_name","中文名称：")%> <%=sPageStyleDesc%>&#13 <%=LocaleServer.getString("pagestyle_query.jsp.label.en_name","英文名称：")%> <%=sPageStyleName%>'>
            <input type="checkbox" name="" value="" id="cbx_<%=nRowId%>"/> <label for="cbx_<%=nRowId%>"> <span class="nameclass"><%=CMyString.truncateStr(sPageStyleDesc,30)%></span></label>
        </div>
        <div>
            <span><%= CMyString.format(LocaleServer.getString("pagestyle_query.jsp.who_and_when_create", "{0}于{1}创建"), new String[]{CMyString.truncateStr(sCrUser,13),sCrTime+""})%></span>
        </div>
        <div class="cmds">
            <li class="edit <%=bCanEdit ? "" : "disableCls"%>" cmd="edit" WCMAnt:param="pagestyle_query.jsp.alter">修改</li>
            <li class="export <%=bCanBrowse ? "" : "disableCls"%>" cmd="export" WCMAnt:param="pagestyle_query.jsp.export">导出</li>
            <li class="delete <%=bCanDelete ? "" : "disableCls"%>" cmd="delete" WCMAnt:param="pagestyle_query.jsp.delete">删除</li>
            <li class="more <%=(bCanAdd || bCanEdit)? "" : "disableCls"%>" cmd="moreCmds" WCMAnt:param="pagestyle_query.jsp.moreCmds">更多操作</li>

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
        var bCanAdd = '<%=bCanAdd%>';
        setAddRight(bCanAdd);
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