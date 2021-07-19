<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../error_for_dialog.jsp"%>
<%@ page import="com.trs.components.common.publish.widget.ContentStyles" %>
<%@ page import="com.trs.components.common.publish.widget.ContentStyle" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@include file="../../include/public_server.jsp"%>
<%
	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	ContentStyles oContentStyles = (ContentStyles) processor.excute("wcm61_contentstyle", "query");
%>

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title> New Document </title>
</head>
<link href="sys_style_select.css" rel="stylesheet" type="text/css" />
<script src="../../js/easyversion/lightbase.js"></script>
<script src="../../js/easyversion/extrender.js"></script>
<script src="../../js/easyversion/elementmore.js"></script>
<script src="../js/adapter4Top.js"></script>
<body>
<div class="rstyle_box" id="rstyle_box">
	<%
		for (int i = 0; i < oContentStyles.size(); i++){
			try{
				ContentStyle obj = (ContentStyle)oContentStyles.getAt(i);
				if (obj == null)
					continue;
				int nRowId = obj.getId();
				String sStyleName = obj.getPropertyAsString("STYLENAME");
				//是否需要权限的处理
				String sCrUser = obj.getPropertyAsString("cruser");
				String sCrTime = obj.getPropertyAsString("CrTime");
				String sStyleThumb = CMyString.transDisplay(obj.getStyleThumb());
				String sPicFileName = mapFile(sStyleThumb);
				if(CMyString.isEmpty(sStyleThumb)){
					sPicFileName = "../images/none_small.gif";
				}
	%>
	<div class="thumb" id="thumb_item_<%=nRowId%>" itemId="<%=nRowId%>" title="<%=LocaleServer.getString("content_style_select.jsp.label.Id","编号:")%>&nbsp;<%=nRowId%>&#13;<%=LocaleServer.getString("metaviewdata_query.jsp.label.widget_style_name","资源风格名称:")%>&nbsp;<%=CMyString.filterForHTMLValue(sStyleName)%>&#13;<%=LocaleServer.getString("content_style_select.jsp.label.cruser","创建者:")%>&nbsp;<%=CMyString.filterForHTMLValue(sCrUser)%>&#13;<%=LocaleServer.getString("metaviewdata_query.jsp.label.crtime","创建时间:")%>&nbsp;<%=CMyString.filterForHTMLValue(sCrTime)%>">
		<div class="pic"><img src="<%=CMyString.filterForHTMLValue(sPicFileName)%>" border="0" alt=""></div>
		<div class="desc">
			<input type="checkbox" name="Style" style="border:0px;" value="<%=nRowId%>" id="cbx_<%=nRowId%>" /> <label for="cbx_<%=nRowId%>"><%=CMyString.transDisplay(sStyleName)%></label>
		</div>
	</div>
	<%
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	%>
</div>
<div class="selectAllBox" id="selectAllBox"><span class="selectSp" id="selectSp"><input type="checkbox" name="SelAll" id="SelAll" value="" style="border:none;"/> <label for="SelAll" WCMAnt:param="content_style_select.jsp.select_all">全选</label></span></div>
</body>
<script language="javascript">
<!--
	window.m_cbCfg = {
		btns : [
			{
				text : '确定',
				id : 'btnSave',
				cmd : function(){
					var selectedIds = buildData();
					if(!selectedIds){
						return false;
					}
					var params = {
						selectedStyleIds : buildData()
					};
					top.window.wcm.CrashBoarder.get(window).notify(params);
					return false;
				}
			},
			{
				extraCls : 'wcm-btn-close',
				text : '取消'
			}
		]
	};	
	function buildData(){
		var els = document.getElementsByName('Style');
		var selectedStyleIds = [];
		for(var i=0; i<els.length;i++){
			if(els[i].checked){
				selectedStyleIds.push(els[i].value);
			}
		}
		//校验
		if(selectedStyleIds.length == 0){
			top.Ext.Msg.alert("请选择需要导出的风格!");
			return false;
		}
		//返回数据
		return selectedStyleIds.join(",");
	}
		//全选按钮处理
	Event.observe('selectSp', 'click', function(event){
		var ev = window.event || event;
		var dom = Event.element(ev);
		var parentDom = dom.parentNode;
		if(!Element.hasClassName(dom, 'selectSp') && !Element.hasClassName(parentDom, 'selectSp')){
			return;	
		}
		var doms = document.getElementsByName('Style');
		for (var i = 0,nLen = doms.length; i < nLen; i++){
			doms[i].checked = $('SelAll').checked;
		}
	});
//-->
</script>
</html>
<%!
	private String mapFile(String _sFileName){
		if(CMyString.isEmpty(_sFileName) || _sFileName.equals("0") || _sFileName.equalsIgnoreCase("none.gif")){
			return "../images/list/none.gif";
		}else{
			return "/webpic/" + _sFileName.substring(0,8) +"/" + _sFileName.substring(0,10) +"/" +_sFileName;
		}
	}
%>