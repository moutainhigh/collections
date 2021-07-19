<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../error_for_dialog.jsp"%>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="com.trs.components.common.publish.widget.Widget" %>
<%@ page import="com.trs.components.common.publish.widget.Widgets" %>
<%@ page import="com.trs.components.common.publish.widget.SpecialAuthServer"%>
<%@ page import="com.trs.webframework.FrameworkConstants" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@include file="../../include/public_server.jsp"%>

<%!
	private String convertDateTimeValueToString(JSPRequestProcessor processor, CMyDateTime _dtValue) {
		String sDateTimeFormat = processor.getParam("DateTimeFormat");
		if (sDateTimeFormat == null) {
			sDateTimeFormat = CMyDateTime.DEF_DATETIME_FORMAT_PRG;
		}
		String sDtValue = _dtValue.toString(sDateTimeFormat);
		return CMyString.showNull(sDtValue);
	}

	private String mapFile(String _sFileName){
		if(CMyString.isEmpty(_sFileName) || _sFileName.equals("0") || _sFileName.equalsIgnoreCase("none.gif")){
			return "images/list/none.gif";
		}else{
			return "/webpic/" + _sFileName.substring(0,8) +"/" + _sFileName.substring(0,10) +"/" +_sFileName;
		}
	}
%>
<%
	JSPRequestProcessor processor = new JSPRequestProcessor(request,response);
	String sServiceId = "wcm61_widget",sMethodName="query";
	Widgets objs = (Widgets)processor.excute(sServiceId, sMethodName);

	int nWidgetCategoryLevel = processor.getParam("WidgetCategoryLevel", -1);

	// 2.构造分页参数
	int nPageSize = -1, nPageIndex = 1;
	nPageSize = processor.getParam(FrameworkConstants.ATTR_NAME_PAGESIZE, 20);
	nPageIndex = processor.getParam(FrameworkConstants.ATTR_NAME_CURRPAGE, 1);

	CPager currPager = new CPager(nPageSize);
	currPager.setCurrentPageIndex(nPageIndex);
	currPager.setItemCount(objs.size());

	out.clear();
%>
<!doctype html>
<html>
<head>
	<meta charset="utf-8" />
	<title> 资源列表 </title>
	<style type="text/css">
		*{margin:0px;padding:0px;}
		html,body{width:100%;height:100%;overflow:hidden;}
		.widget-box{
			width:100%;
			height:100%;
			overflow:auto;
		}
		.desc{
			font-size:12px;
			text-align:center;
		}
		.thumb{
			float:left;
			margin-top:10px;
			margin-left:3px;
			border:1px solid gray;
			overflow:hidden;
			cursor:move;
		}
		.thumb .img-box{
			width:172px;
			height:112px;
			line-height:112px;
			overflow:hidden;
			vertical-align:middle;
			text-align:center;
		}		
		.thumb .desc{
			margin-top:2px;
		}
		.WidgetCategory{
			font-size:12px;
			margin-top:5px;
			zoom:1;
		}
		.WidgetCategory .header{
			height:30px;
			line-height:30px;
			padding-left:5px;
			background:#DEE6E9;
			font-weight:bold;
			overflow:hidden;
			cursor:pointer;
		}
		.WidgetCategory .body{
			overflow:hidden;
			padding:2px;
		}

		.header .tools{
			display:inline-block;
			width:16px;
			height:15px;
			background:url(../images/design/tools.gif) -14px -255px no-repeat;
			margin-right:5px;
			vertical-align:middle;
		}
		.header .desc{
			display:inline-block;
			line-height:30px;
			vertical-align:middle;
		}

		.ext-ie6 .hideBody .header .tools{
			display:inline;
		}

		.hideBody .header .tools{
			background:url(../images/design/tools.gif) -14px -240px no-repeat;
		}

		.hideBody .body{
			display:none;
		}
	</style>
	<script src="../../js/easyversion/lightbase.js"></script>
	<script src="../../js/easyversion/extrender.js"></script>
	<script src="../../js/easyversion/elementmore.js"></script>
	<script src="../../js/source/wcmlib/Observable.js"></script>

	<script language="javascript" type="text/javascript" src="../../js/source/wcmlib/WCMConstants.js"></script>
	<script language="javascript" type="text/javascript" src="../../js/easyversion/ajax.js"></script>
	<script language="javascript" type="text/javascript" src="../../js/easyversion/basicdatahelper.js"></script>
	<script language="javascript" type="text/javascript" src="../../js/easyversion/web2frameadapter.js"></script>
	<script src="../drag.js"></script>
	<script src="widget_mini_query.js"></script>
</head>
<body unselectable="on" onselectstart="return false;">
<div style="" class="widget-box" id="widget-box">

<%
	//文字列表#1,图片列表#2,栏目导航#3,单篇文章#4,其他#1218
	String sWidgetCategorys = ConfigServer.getServer().getSysConfigValue("WIDGET_CATEGORYS", "");
	if(!CMyString.isEmpty(sWidgetCategorys)){
		String sItemSplit = ",";
		String sValueSplit = "#";
		String[] aWidgetCategorys = sWidgetCategorys.split(sItemSplit);
		String sWidgetCategoryName = "";
		int nWidgetCategoryId = 0;

		int currIndex = 0;
		for (int j = 0; j < aWidgetCategorys.length; j++) {
			String[] aWidgetCategory = aWidgetCategorys[j].split(sValueSplit);
			sWidgetCategoryName = aWidgetCategory[0];
			nWidgetCategoryId = Integer.parseInt(aWidgetCategory[1]);

			if(nWidgetCategoryId < nWidgetCategoryLevel) continue;
			currIndex++;
%>
<div class="WidgetCategory <%=(currIndex==1)?"":"hideBody"%>">
<div class="header"><span class="tools"></span><span class="desc"><%=CMyString.transDisplay(sWidgetCategoryName)%></span></div>
<div class="body">
<%
//5. 遍历生成表现
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			Widget obj = (Widget)objs.getAt(i - 1);
			if (obj == null)
				continue;
			int nCurrWidgetCategory = obj.getPropertyAsInt("WidgetCategory", 0);
			if(nCurrWidgetCategory != nWidgetCategoryId)
				continue;
			int nRowId = obj.getId();
			String sWidgetName = CMyString.transDisplay(obj.getWname());
			String sCrUser = CMyString.transDisplay(obj.getPropertyAsString("cruser"));
			String sCrTime = convertDateTimeValueToString(processor, obj.getPropertyAsDateTime("CrTime"));
			String sWidgetPic = CMyString.transDisplay(obj.getWidgetpic());
			String sPicFileName = mapFile(sWidgetPic);
			if(CMyString.isEmpty(sWidgetPic)){
				sPicFileName = "../images/list/none.gif";
			}
%>

<div class="thumb" id="thumb_<%=nRowId%>" itemId="<%=nRowId%>" onmouseover="imageMouseOver(event, this);" onmouseout="imageMouseOut(event, this);" onmousedown="imageMouseDown(event, this);" unselectable="on" onselectstart="return false;">
	<div class="img-box" unselectable="on"><img src="<%=sPicFileName%>" onload="resizeIfNeed(this);" border="0" alt="" unselectable="on"></div>
	<div class="desc" unselectable="on"><%=sWidgetName%></div>
</div>

<%
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
%>
</div>
</div>
<%
		}
	}
%>

<%
// 如果没有
if(objs.size()==0){
%>
	<div class="no_object_found" WCMAnt:param="layout_query.jsp.obj_notfound">
		不好意思，没有找到符合条件的对象！
	</div>
<%
}
%>
</body>
</html>