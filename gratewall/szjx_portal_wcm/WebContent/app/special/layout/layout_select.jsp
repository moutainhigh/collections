<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../../include/error_for_dialog.jsp"%>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="com.trs.components.common.publish.widget.Layouts" %>
<%@ page import="com.trs.components.common.publish.widget.Layout" %>
<%@ page import="com.trs.components.common.publish.widget.ILayoutGenerator" %>
<%@ page import="com.trs.DreamFactory" %>

<%@include file="../../include/public_server.jsp"%>
<%@include file="layout_css_generator.jsp"%>
<%
	// 1. 获取系统中所有布局对象
	JSPRequestProcessor processor = new JSPRequestProcessor(request,response);
	String sServiceId = "wcm61_layout",sMethodName="query";
	Layouts oLayouts = (Layouts)processor.excute(sServiceId, sMethodName);
	ILayoutGenerator oLayoutGenerator = (ILayoutGenerator) DreamFactory
			.createObjectById("ILayoutGenerator");
	//out.clear();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict/dtd">
<html>
 <head>
  <title  WCMAnt:param="layout_select.jsp.title">布局选择页面</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="Author" content="CH">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <link href="layout_select.css" rel="stylesheet" type="text/css" />
  	<script src="../../js/easyversion/lightbase.js"></script>
	<script src="../../js/easyversion/extrender.js"></script>
	<script src="../../js/easyversion/elementmore.js"></script>
	<script src="../js/adapter4Top.js"></script>
	<style type="text/css">
	<%
		for(int i=0;i<oLayouts.size();i++)
			out.println(getCssOfLayout((Layout)oLayouts.getAt(i)));
	%>
  </style>
 <script language="javascript">
	<!--
		window.m_cbCfg = {
			btns : [
				{
					text : '确定',
					cmd : function(){
						var id = getCheckValue();
						if(id==0){
							Ext.Msg.alert("没有选择布局！");
							return false;
						}
						this.hide();
						this.notify({id:id,content:$("content_"+id).innerHTML});
					}
				},
				{
					extraCls : 'wcm-btn-close',
					text : '取消'
				}
			]
		};		

		function getCheckValue(){
			var radios = document.getElementsByTagName("input");
			for(var i=0;i<radios.length;i++){
				if(radios[i].checked){
					return radios[i].value;
				}
			}
			return 0;
		}
	//-->
	</script>
 </head>

 <body>
 <div class="layout_list">
	  <div class="list_head" WCMAnt:param="layout_select.jsp.please_select_layout-type">
		请选择您需要的布局类型，然后点击“确定”进行添加
			
	  </div>
	  <div class="list_body">
		<%for(int i=0;i<oLayouts.size();i++){
				Layout currLayout = (Layout)oLayouts.getAt(i);
				int nRatioType = currLayout.getRatioType();
				int nId = currLayout.getId();
				String sRatio = currLayout.getRatio();
				String sName =currLayout.getName();

				// 构造title信息
				String sTitle=CMyString.format(LocaleServer.getString("layout_select.jsp.serial_num", "编号：[{0}]&#13!"), new int[]{nId});
				sTitle+=CMyString.format(LocaleServer.getString("layout_select.jsp.collumn", "列数：[{0}]&#13!"), new int[]{currLayout.getColumns()});
					sTitle+=LocaleServer.getString("layout_select.jsp.ratio_type", "比例类型：");
				if(nRatioType==Layout.RATIO_TYPE_FIXED){
					sTitle+=LocaleServer.getString("layout_select.jsp.fixed_ratio", "固定比&#13;");
				}else{
					sTitle+= LocaleServer.getString("layout_select.jsp.percentage_ratio", "百分比&#13;");
				}
				sTitle+=CMyString.format(LocaleServer.getString("layout_select.jsp.ratio_value", "比例值：[{0}]"), new String[]{sRatio});
			%>
			<label for="layout_<%=nId%>"><div class="thumb" title="<%=CMyString.filterForHTMLValue(sTitle)%>" id="thumb_<%=nId%>">
				<div class="preview <%=(nRatioType==Layout.RATIO_TYPE_FIXED)?"fixed":"percentage"%>" >
					<%=currLayout.getHtmlContent()%>
				</div>
				<!-- 这里需要根据布局的设置，重新生成布局的html，需要内部添加css样式的，否则新建的布局在添加到页面中以后，列会变成行 -->
				<div id="content_<%=nId%>" style="display:none;">
					<%=oLayoutGenerator.generateHtml(currLayout, true, false)%>
				</div>
				<div class="ratio">
					[ <%=CMyString.transDisplay(sRatio)%> ]
				</div>
				<div class="name">
					<input type="radio" name="layout" value="<%=nId%>" id="layout_<%=nId%>"/>
					<%=CMyString.isEmpty(sName)?(CMyString.format(LocaleServer.getString("layout_select.jsp.layout", "布局[{0}]"), new int[]{nId})):CMyString.transDisplay(sName)%>
				</div>
			</div>
			</label>
		<%
		}
		%>
	  </div>
  </div>
 </body>
</html>