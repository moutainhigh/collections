<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../../include/error_for_dialog.jsp"%>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="com.trs.components.common.publish.widget.Layouts" %>
<%@ page import="com.trs.components.common.publish.widget.Layout" %>
<%@ page import="com.trs.components.common.publish.widget.SpecialAuthServer" %>
<%@ page import="com.trs.webframework.FrameworkConstants" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@include file="../../include/public_server.jsp"%>
<%@include file="layout_css_generator.jsp"%>
<%
	JSPRequestProcessor processor = new JSPRequestProcessor(request,response);
	String sServiceId = "wcm61_layout",sMethodName="query";
	Layouts oLayouts = (Layouts)processor.excute(sServiceId, sMethodName);
	
	// 2.构造分页参数
	int nPageSize = -1, nPageIndex = 1;
	nPageSize = processor.getParam(FrameworkConstants.ATTR_NAME_PAGESIZE, 20);
	nPageIndex = processor.getParam(FrameworkConstants.ATTR_NAME_CURRPAGE, 1);

	CPager currPager = new CPager(nPageSize);
	currPager.setCurrentPageIndex(nPageIndex);
	currPager.setItemCount(oLayouts.size());

	// 3.获取生成的样式文件
	StringBuffer sbCss = new StringBuffer();
	for(int i=0;i<oLayouts.size();i++)
		sbCss.append(getCssOfLayout((Layout)oLayouts.getAt(i))).append("\n");


	// 4.权限判断
	out.clear();
%>
<div style="height<%=220*oLayouts.size()%>px;padding:2px 5px;" class="layout-box">

<%
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		Layout currLayout = (Layout)oLayouts.getAt(i - 1);
		if(currLayout==null)
			continue;
		// 判断是否有删除权限、编辑权限
		int nId = currLayout.getId();
		String sName = currLayout.getName();
		int nRatioType = currLayout.getRatioType();
		
		// 构造title信息
		String sTitle=CMyString.format(LocaleServer.getString("layout_query.jsp.serial_num", "编号：[{0}]&#13;"), new int[]{nId});
		sTitle+=CMyString.format(LocaleServer.getString("layout_query.jsp.collumn_num", "列数：[{0}]&#13;"), new int[]{currLayout.getColumns()});
		sTitle+= LocaleServer.getString("layout_query.jsp.ratio_type","比例类型：");
		if(nRatioType==Layout.RATIO_TYPE_FIXED){
			sTitle+= LocaleServer.getString("layout_query.jsp.fixed_ratio","固定比&#13;");
		}else{
			sTitle+= LocaleServer.getString("layout_query.jsp.percentage_ratio","百分比&#13;");
		}
		sTitle+=CMyString.format(LocaleServer.getString("layout_query.jsp.percentage_value", "百分比[{0}]&#13;"), new String[]{currLayout.getRatio()});

		sName = CMyString.isEmpty(sName)?(CMyString.format(LocaleServer.getString("layout_query.jsp.layout", "布局[{0}]"), new int[]{nId})):sName;
		String sRatio = getRatioWidthPix(nRatioType,currLayout.getRatio());

%>
<div class="thumb" itemid="<%=nId%>" id="thumb_<%=nId%>" title="<%=CMyString.filterForHTMLValue(sTitle)%>">
	<div class="pic <%=(nRatioType==Layout.RATIO_TYPE_FIXED)?"fixed":"percentage"%>">
		<%=currLayout.getHtmlContent()%>
	</div>
	<div class="ratio desc">
		<%
			out.print(CMyString.format(LocaleServer.getString("layout_query.jsp.percentage", "比例：{0}"), new String[]{sRatio}));
		%>
	</div>
</div>
<%
}
%>
</div>
<%
// 如果没有布局
if(oLayouts.size()==0){
%>
	<div class="no_object_found" WCMAnt:param="layout_query.jsp.obj_notfound">
		不好意思，没有找到符合条件的对象！
	</div>
<%
}
%>
<script language="javascript">
<!--
	updateLayoutStyle('<%=CMyString.filterForJs(sbCss.toString())%>');
//-->
</script>