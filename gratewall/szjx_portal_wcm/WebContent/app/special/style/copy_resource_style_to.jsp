<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../error_for_dialog.jsp"%>
<!-- WCM IMPORTS @ BEGIN -->
<%@ page  import = "com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page  import = "java.util.HashMap" %>
<%@ page  import="com.trs.infra.util.CMyString" %>
<%@ page  import="com.trs.components.common.publish.widget.PageStyles" %>
<%@ page  import="com.trs.components.common.publish.widget.PageStyle" %>
<%@ page  import="com.trs.components.common.publish.widget.ResourceStyle" %>
<!-- WCM IMPORTS @ END -->
<!--引用公共的登录控制页面 BEGIN-->
<%@include file="../../include/public_server.jsp"%>
<!--引用公共的登录控制页面 END-->
<%!boolean IS_DEBUG = false;%>
<%
	//获取当前用户的权限，以便于控制“快速通道”中显示的入口
	//权限的校验
	//获取参数
	int nSourceResourceStyleId = currRequestHelper.getInt("SourceResourceStyleId",0);
	int nPageStyleId = currRequestHelper.getInt("PageStyleId",0);
	
	// 获取资源分类
	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	HashMap parameters = new HashMap();
	parameters.put("ObjectId", String.valueOf(nSourceResourceStyleId));
	String sServiceId = "wcm61_resourcestyle";
	String sMethodName = "findById";
	ResourceStyle oResourceStyle = ( ResourceStyle ) processor.excute(sServiceId,sMethodName,parameters);
	if(oResourceStyle==null){
		throw new WCMException(CMyString.format(LocaleServer.getString("copy_resource_style_to.jsp.fail2get_page_content", "获取页面风格[Id={0}]失败！"), new int[]{nSourceResourceStyleId}));
	}
	
	//获取对象属性
	String sPageStyleName = oResourceStyle.getStyleName();//页面风格名称
	String sPageStyleDesc = oResourceStyle.getStyleDesc();

	//获取系统中所有的页面风格集合
	processor.reset();
	parameters = new HashMap();
	sServiceId = "wcm61_pagestyle";
	sMethodName = "query";
	PageStyles allPageStyles = ( PageStyles ) processor.excute(sServiceId,sMethodName,parameters);

%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<title WCMAnt:param="copy_resource_style_to.jsp.title">选择页面风格</title>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script src="../../js/easyversion/lightbase.js"></script>
	<script src="../../js/easyversion/extrender.js"></script>
	<script src="../../js/easyversion/elementmore.js"></script>
	<style type="text/css">
		html,body{
			padding:0px;
			width:0px;
			width:100%;
			height:100%;
			overflow-x:hidden;
			overflow-y:auto;
		}
		.name_td_left{
			font-family:"宋体";
			font-size:12px;
			font-weight:bold;
			text-align:left;
			width:66px;
		}
		.note_span_right{
			font-family:"宋体";
			font-size:12px;
			padding-left:5px;
		}
		.red_span{
			font-family:"宋体";
			font-size:12px;
			color:red;
		}
		.tr_unselected_bg{
			background-color:#FFFFFF;
			font-family:"宋体";
			font-size:12px;
		}
		.head_td_bg{
			background-color:#DCF4FF;
			font-family:"宋体";
			font-size:14px;
		}
		.td_bg{
			background-color:#DCF4FF;
			font-family:"宋体";
		}
		.tr_selected_bg{
			background-color:#FFFFCC;
			font-family:"宋体";
			font-size:12px;
		}
		.operations{
			font-size:12px;
			height: 25px;
			line-height: 25px;
			margin-top : 10px;
		}
		.repeatnamearea{
			border:1px solid #acddfd;
		}
		.ext-ie .repeatnamearea{
			width:100%;
		}
		.selectarea{
			border:1px solid #acddfd;
			margin-top:5px;
			overflow:hidden;
		}
		.ext-ie .selectarea{
			width:100%;
		}
	</style>
	<SCRIPT LANGUAGE="JavaScript">
		//绘画操作按钮
		window.m_cbCfg = {
			btns : [
				{
					text : '确定',
					cmd : function(){
						//判断是否选择了页面风格
						var aSelectedElIds = getSelectedIds();
						if(aSelectedElIds.length == 0){
							top.window.Ext.Msg.alert('您没有选择页面风格！');
							return false;
						}
						var copyInfo={
							SourceResourceStyleId : <%=nSourceResourceStyleId%>,
							TargetPageStyleIds : aSelectedElIds.join(","),
							copyMode : getRadioValue('StyleCopyMode')
						};
						//调用父窗口
						top.window.wcm.CrashBoarder.get(window).notify(copyInfo);
						return false;
					}
				},{
					text : '取消',
					extraCls : 'wcm-btn-close'
				}
			]
		}
		//单击一行
		function doClick(_nPageStyleId, event){
			_nPageStyleId = _nPageStyleId || 0;
			var currTr = document.getElementById("tr_"+_nPageStyleId);
			if(currTr){
				var srcElement = event.srcElement || event.target;
				//选中checkbox
				var currCheckBox = document.getElementById("checkbox_"+_nPageStyleId);
				if(currCheckBox){
					if(srcElement && srcElement != currCheckBox)
						currCheckBox.checked = !currCheckBox.checked;
				}
				//设置选中样式
				if(Element.hasClassName(currTr, "tr_selected_bg")){
					Element.removeClassName(currTr, "tr_selected_bg");
					Element.addClassName(currTr, "tr_unselected_bg");
				}else{
					Element.removeClassName(currTr, "tr_unselected_bg");
					Element.addClassName(currTr, "tr_selected_bg");
				}
			}
		}
		function getSelectedIds(){
			var aSelectedIds = new Array();
			var checkEls = document.getElementsByTagName("input");
			for(var k = 0; k < checkEls.length; k++){
				if(checkEls[k].type != 'checkbox')
					continue;
				if(!checkEls[k].checked)
					continue;
				else{
					aSelectedIds.push(checkEls[k].value);
				}
			}
			return aSelectedIds;

		}
		function getRadioValue(_sRadioName){
			var radios = document.getElementsByName(_sRadioName);
			for (var i = 0; i < radios.length; i++){
				if(radios[i].checked) {
					return radios[i].value;
				}
			}
			return null;
		}
		function selectall(){
			var checkEls = document.getElementsByName("checkbox_input");
			var bSelectAll = true;
			for(var i=0; i < checkEls.length; i++){
				if(checkEls[i].checked){
					bSelectAll = false;
					break;
				}
			}
			if(bSelectAll){
				for(var k=0; k < checkEls.length; k++){
					var checkel = checkEls[k];
					if(checkel && !checkel.checked)
						checkel.click();
				}
			}else{
				for(var k=0; k < checkEls.length; k++){
					var checkel = checkEls[k];
					if(checkel && checkel.checked)
						checkel.click();
				}
			}
		}
	</SCRIPT>
</head>
<body style="padding:0px;margin:0px;">
<form id="frm" style="padding:0px;margin:0px;">
<div class="repeatnamearea">
	<table border="0" cellspacing="0" cellpadding="0" width="100%" class="operations" style="margin:0px;">
	<tbody>
		<tr>
			<td class="td_bg" WCMAnt:param="copy_resource_style_to.jsp.please_sel_style_rename">请选择风格重名后的处理方式：</td>
		</tr>
		<tr>
			<td height="30px">
				<span><input type="radio" name="StyleCopyMode" value="1"></span>
				<span WCMAnt:param="copy_resource_style_to.jsp.auto_cover">自动覆盖</span>
				<span><input type="radio" name="StyleCopyMode" value="3"></span>
				<span WCMAnt:param="copy_resource_style_to.jsp.auto_rename">自动更名</span>
				<span><input type="radio" name="StyleCopyMode" value="2" checked></span>
				<span WCMAnt:param="copy_resource_style_to.jsp.skip">跳过</span>
			</td>
		</tr>
	</tbody>
	</table>
</div>
<span class="operations" style="margin:0px;padding-left:10px;" WCMAnt:param="copy_resource_style_to.jsp.please_sle_target_style">请选择目标风格: </span>
<div class="selectarea"> 
	<table border="0" cellspacing="0" cellpadding="0" width="100%" style="height:25px;">
		<tr>
			<td width="40px" align="center" class="head_td_bg"><span onclick="selectall();return false;" style="cursor:pointer;" WCMAnt:param="copy_resource_style_to.jsp.sel_all">全选</span></td>
			<td align="center" class="head_td_bg" WCMAnt:param="copy_resource_style_to.jsp.zh_name">中文名</td>
			<td width="100px" align="center" class="head_td_bg" WCMAnt:param="copy_resource_style_to.jsp.en_name">英文名</td>
		</tr>
	</table>
	<table border="0" cellspacing="1" cellpadding="0" width="100%" style="background-color:#e6e6e6">
		<!--
		<%if(nPageStyleId != 0){%>
			<tr onclick="doClick(0, event)" class="tr_unselected_bg" id="tr_0">
				<td width="40px" align="center" ><input type="checkbox" name="checkbox_input" value="0" id="checkbox_0"/></td>
				<td align="center">系统默认风格</td>
				<td width="100px" align="center">default</td>
			</tr>
		<%}%>
		-->
		<%
			for(int i =0;i<allPageStyles.size();i++){
				PageStyle aPageStyle = (PageStyle)allPageStyles.getAt(i);
				if(aPageStyle==null){
					continue;
				}
				if(aPageStyle.getId() == nPageStyleId){//排除自己所属的页面风格
					continue;
				}
		%>
		<tr onclick="doClick(<%=aPageStyle.getId()%>, event)" class="tr_unselected_bg" id="tr_<%=aPageStyle.getId()%>">
			<td width="40px" align="center" ><input type="checkbox" name="checkbox_input" value="<%=aPageStyle.getId()%>" id="checkbox_<%=aPageStyle.getId()%>"/></td>
			<td align="center" title="<%=CMyString.filterForHTMLValue(CMyString.showNull(aPageStyle.getStyleDesc()))%>"><%=CMyString.filterForHTMLValue(CMyString.truncateStr(CMyString.showNull(aPageStyle.getStyleDesc()),30))%></td>
			<td width="100px" align="center" title="<%=CMyString.filterForHTMLValue(CMyString.showNull(aPageStyle.getStyleName()))%>"><%=CMyString.filterForHTMLValue(CMyString.truncateStr(CMyString.showNull(aPageStyle.getStyleName()),30))%></td>
		</tr>
		<%
			}
		%>
	</table>
</div>
<form>
</body>
</html>