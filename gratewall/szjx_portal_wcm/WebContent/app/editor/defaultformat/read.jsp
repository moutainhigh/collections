<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.content.persistent.DefaultFormat" %>
<%@ page import="com.trs.components.wcm.content.domain.DefaultFormatMgr" %>
<%@ page import="com.trs.components.wcm.content.domain.IDefaultFormatMgr" %>
<%@include file="../../include/public_server.jsp"%>
<%
	/*
	//获取channelId，跟据channelId获取到当前数据库存数的配置值
	String sAttributes = "";
	int nObjId = currRequestHelper.getInt("ObjId",0);
	int nObjType = currRequestHelper.getInt("ObjType",0);
	if( nObjId != 0 ) {
		int nObjectId = DefaultFormat.getObjectId(nObjType, nObjId);
		DefaultFormat defaultFormat = null;
		if (nObjectId != 0) {
			defaultFormat = DefaultFormat.findById(nObjectId);
			sAttributes = defaultFormat.getPropertyAsString("Attribute");
			//word粘贴是否开启默认排版配置
			boolean isWord = defaultFormat.getPropertyAsBoolean("FORWORD", true);
			if(isWord){
				sAttributes += "isForWord:1;";
			}else{
				sAttributes += "isForWord:0;";
			}
		}
	}
	*/
	String sAttributes = "";
	String sFor = "";//判断是否采用系统级的标志
	int nObjId = currRequestHelper.getInt("ObjId",0);
	int nObjType = currRequestHelper.getInt("ObjType",0);
	//栏目级
	if(nObjType == Channel.OBJ_TYPE) {
		Channel channel = Channel.findById(nObjId);
		IDefaultFormatMgr mgr = (IDefaultFormatMgr) DreamFactory.createObjectById("IDefaultFormatMgr");
		DefaultFormat defaultFormat = mgr.getDefaultFormat(channel);
		if(defaultFormat != null) {
			if(!defaultFormat.isStatus() && !defaultFormat.isInherit()) {
				sFor += "isInherit:0;";
				sFor += "isStatus:0;";
				if(defaultFormat.getObjType()== WebSite.OBJ_TYPE) {
					sAttributes += "isStatus:0;";
					sAttributes += "isInherit:1;";
				}
				if(defaultFormat.getObjType()== Channel.OBJ_TYPE) {
					sAttributes += "isStatus:0;";
					sAttributes += "isInherit:0;";
				}
			}else{
				sAttributes = defaultFormat.getPropertyAsString("Attribute");
				sFor = defaultFormat.getPropertyAsString("Attribute");
				//word粘贴是否开启默认排版配置
				boolean isWord = defaultFormat.getPropertyAsBoolean("FORWORD", false);
				boolean isInherit = defaultFormat.getPropertyAsBoolean("INHERIT", true);
				boolean isStatus = defaultFormat.getPropertyAsBoolean("STATUS", false);
				if(isWord){
					sAttributes += "isForWord:1;";
				}else{
					sAttributes += "isForWord:0;";
				}
				if(defaultFormat.getObjType()== WebSite.OBJ_TYPE) {
					sAttributes += "isInherit:1;";
					sAttributes += "isStatus:0;";
				}else {
					if(isInherit) {
						sAttributes += "isInherit:1;";
						sAttributes += "isStatus:0;";
					}else if(isStatus) {
						sAttributes += "isStatus:1;";
					}else {
						sAttributes += "isStatus:0;";
					}
				}
			}
		}
	}else {//站点级
		if( nObjId != 0 ) {
			int nObjectId = DefaultFormat.getObjectId(nObjType, nObjId);
			DefaultFormat defaultFormat = null;
			if (nObjectId != 0) {
				defaultFormat = DefaultFormat.findById(nObjectId);
				//word粘贴是否开启默认排版配置
				boolean isWord = defaultFormat.getPropertyAsBoolean("FORWORD", false);
				boolean isInherit = defaultFormat.getPropertyAsBoolean("INHERIT", true);
				boolean isStatus = defaultFormat.getPropertyAsBoolean("STATUS", false);
				sAttributes = defaultFormat.getPropertyAsString("Attribute");
				if(!isInherit) {
					sFor = defaultFormat.getPropertyAsString("Attribute");
					if(!isStatus) {
						sAttributes="";
					}
				}
				if(isWord){
					sAttributes += "isForWord:1;";
				}else{
					sAttributes += "isForWord:0;";
				}
				if(isInherit) {
					sAttributes += "isInherit:1;";
					sAttributes += "isStatus:0;";
				}else if(isStatus) {
					sAttributes += "isStatus:1;";
				}else {
					sAttributes += "isStatus:0;";
				}
			}
		}
	}
	//系统级
	if("".equals(sFor)) {
		String sDomain = ConfigServer.getServer().getInitProperty("WCM_PATH");
		String sPath = sDomain + "/app/editor/defaultformat/systemConfig.txt";
		if(CMyFile.fileExists(sPath)) {
			sAttributes = CMyFile.readFile(sPath);
		}
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title> 默认排版界面 </title>
	<!--AJAX-->
	<script src="../../../app/js/runtime/myext-debug.js"></script>
	<script src="../../../app/js/source/wcmlib/WCMConstants.js"></script> 
	<script src="../../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script> 
	<!--AJAX end-->

	<!--FloatPanel Outer Start-->
	<script src="../../../app/js/runtime/myext-debug.js"></script>
	<script src="../../../app/js/locale/cn.js" WCMAnt:locale="../js/locale/$locale$.js"></script>
	<script src="../../../app/js/source/wcmlib/WCMConstants.js"></script>
	<script src="../../../app/js/source/wcmlib/core/MsgCenter.js"></script>
	<script src="../../../app/js/source/wcmlib/core/CMSObj.js"></script>
	<script src="../../../app/js/source/wcmlib/core/AuthServer.js"></script>
	<script src="../../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanel.js"></script>
	<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
	<!--FloatPanel Outer End-->

	<script src="document_defaultFormat_config.js"></script>
	<script src="read.js"></script>
	<!-- 暂时这样调用 -->
	<script src="../../../app/editor/editor/plugins/key4format/lang.js"></script>
	<link rel="stylesheet" type="text/css" href="read.css"/>
</head>

<body style='width:400px;height:400px;' onload='init("<%=sAttributes%>");'>
	<fieldset>
		<legend>默认选项</legend>
		<div id="defaultSelected" class="">
			<div id="defaultSelected_checkbox_kee" class="parentBox" >
				
			</div>
			<!-- 留白 -->
			<div id="" class="" style="height:10px"></div>
			<div id="defaultSelected_checkbox_del" class="parentBox" >
				
			</div>
			<!-- 留白 -->
			<div id="" class="" style="height:10px"></div>
			<div id="defaultSelected_checkbox_add" class="parentBox" >
				
			</div>
			<!-- 留白 -->
			<div id="" class="" style="height:10px"></div>
			<!-- 产生样式文件左窗口 -->
			<div id="" class="parentBox_left">
				<!-- 留白 -->
				<div id="" class="" style="height:10px"></div>
				<div id="defaultSelected_text_inp" >
				</div>
				<!-- 留白 -->
				<div id="" class="" style="height:10px"></div>
				<div id="defaultSelected_select">
					<div id="div_addfontfamily" class="">
						
					</div>
					<!-- 留白 -->
					<div id="" class="" style="height:5px"></div>
					<div id="div_addfontsize" class="">
						
					</div>
					<!-- 留白 -->
					<div id="" class="" style="height:5px"></div>
				</div>
			</div>
			<!-- 产生样式代码右窗口 -->
			<div id="preview_box" class="parentBox_right" style="overflow:auto;">
				<!-- <iframe src="" width="250px" height="150px"></iframe> -->
			</div>
		</div>
	</fieldset>
	<hr />
	<fieldset>
		<div id="defautlFormat_forword" class="" style="height:30px">
			<input id="defautlFormat_forword_checked" type="checkbox" name="">
				<label for="defautlFormat_forword_checked">word粘贴执行默认排版</label>
		</div>

		<div id="defautlFormat_status" class="" style="height:30px">
			<label>开启默认排版功能：</label>
			<input type="radio" name="status" id="defautlFormat_status_checked">
				<label for="defautlFormat_status_checked">开启</label>
			<input type="radio" name="status" id="defautlFormat_status_inherit" checked>
				<label for="defautlFormat_status_inherit">继承</label>
			<input type="radio" name="status" id="defautlFormat_status_nochecked">
				<label for="defautlFormat_status_nochecked">关闭</label>
		</div>

		<div id="defautlFormat_inherit" class="" style="height:30px;display:none">
			<label>默认排版作用范围：</label>
			<input type="radio" name="inherit" id="defautlFormat_inherit_checked" checked>
				<label for="defautlFormat_inherit_checked">使用继承项</label>
			<input type="radio" name="inherit" id="defautlFormat_inherit_nochecked">
				<label for="defautlFormat_inherit_nochecked">使用自身项</label>
		</div>

	</fieldset>
</body>
</html>
