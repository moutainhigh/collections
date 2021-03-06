<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%
	String sDomain = ConfigServer.getServer().getInitProperty("WCM_PATH");
	String sPath = sDomain + "/app/editor/defaultformat/systemConfig.txt";
	String sAttributes = "";
	if(CMyFile.fileExists(sPath)) {
		sAttributes = CMyFile.readFile(sPath);
	}
	//加载上次默认排版操作
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title> 系统— —默认排版界面 </title>
	<script src="document_defaultFormat_config.js"></script>
	<script src="systemConfig.js"></script>
	<!-- 暂时这样调用 -->
	<script src="../../../app/editor/editor/plugins/key4format/lang.js"></script>
	<link rel="stylesheet" type="text/css" href="read.css"/>
</head>
<style type="text/css">
	
</style>
<body style='width:400px;text-align:center;' onload='init("<%=sAttributes%>");'>
	<fieldset style='width:400px;font-size:"16px";font-family:"黑体";text-align:justify'>
		<br/>
		<p>　　在此页面中可以对编辑器的粘贴进行默认配置。勾选以下选项后，保存设置。</p>
		<p>　　编辑器中执行粘贴操作后，代码执行相关操作使粘贴到过达到预期效果。如需要继续对代码进行调整时，可采用<u style="color:red;">一键排版</u>来实现。</p>
	</fieldset>
	<form method=post action="systemConfig_dowith.jsp" id="frm_check_value" name="frm_check_value">
		<fieldset style='width:400px;text-align:justify;'>
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
				<div id="" class="parentBox">
					<!-- 留白 -->
					<div id="" class="" style="height:10px"></div>
					<div id="defaultSelected_text_inp" style="float:left">
					</div>
					<div id="defaultSelected_select" style="float:right;margin-left:50px;">
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
			</div>
		</fieldset>
		<hr style='width:400px;'/>
		<fieldset style='width:400px;text-align:justify;'>
			<div id="defautlFormat_forword" class="" style="height:30px;margin-left:50px;">
				<input id="defautlFormat_forword_checked" type="checkbox" name="">
					<label for="defautlFormat_forword_checked">word粘贴执行默认排版</label>
			</div>

			<div id="defautlFormat_status" class="" style="height:30px;margin-left:50px;">
				<label>开启默认排版功能：</label>
				<input type="radio" name="status" id="defautlFormat_status_checked" checked>
					<label for="defautlFormat_status_checked">开启</label>
				<input type="radio" name="status" id="defautlFormat_status_nochecked">
					<label for="defautlFormat_status_nochecked">关闭</label>
			</div>

		</fieldset>
		<div id="" class="">
			<input type="hidden" id="postData" name="postData" value="">
			<input type="hidden" id="isForWord" name="isForWord" value="">
			<input type="hidden" id="sysStatus" name="sysStatus" value="">
			<input type="button" value="保存设置" onclick="formPost();">
		</div>
	</form>
</body>
</html>
