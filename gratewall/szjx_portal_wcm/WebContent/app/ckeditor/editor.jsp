<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%
	String sLocale = (String)session.getAttribute("locale");
	if(sLocale == null || sLocale.length() <= 0){
		sLocale = "zh-cn";
	}
	sLocale = sLocale.replace('_', '-');
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title> New Document </title>
	<script language="javascript" src="ckeditor/ckeditor.js"></script>
	<style type="text/css">
		#cke_path_editor{visibility:hidden;}
		html,body{height:100%;overflow:hidden;}
	</style>
</head>
<body onload="initialize()" >
<textarea id="editor"></textarea><!--id必须有值任意、class唯一,该方法只对textarea有效-->
<script language="javascript">
<!--
	//获取初始化对象,CKEDITOR大小写敏感
	function GetEditor(){
		return CKEDITOR.instances.editor;

	}

	function GetTrueEditor(){
		return window.frames[0];
	}

	function GetTitleElement(){
		return parent.document.getElementById('DocTitle');
	}

	//内容初始化
	function initialize(){
		var initValueElId = getParameter('EditorValueElId') || '_editorValue_';
		var initValueEl = parent.document.getElementById(initValueElId);
		if(initValueEl){
			var oContent = initValueEl.value || "";
			GetEditor().setData(oContent);
		}
	}

	//接口:获取编辑器内容
	function GetHTML(){
		CKEDITOR.OfficeActiveX.UploadLocals(CKEDITOR.instances.editor);
		return CKEDITOR.instances.editor.getData(); 
	}

	//接口:向编辑器插入内容
	function SetHTML(newContent){
		CKEDITOR.instances.editor.setData(newContent);
	}

	//读取参数
	function getParameter(_sName, _sQuery){
		if(!_sName)return '';
		var query = _sQuery || location.search;
		if(!query)return '';
		var arr = query.substring(1).split('&');
		_sName = _sName.toUpperCase();
		for (var i=0,n=arr.length; i<n; i++){
			if(arr[i].toUpperCase().indexOf(_sName+'=')==0){
				return arr[i].substring(_sName.length + 1);
			}
		}
		return '';
	}

	var sEditHeight;
	var name = navigator.appName;
	switch (name){
		case "Opera":
			sEditHeight="395px";
			break
		case "Netscape":
			sEditHeight="385px";
			break
		default:
			sEditHeight="405px"
	}
	var $G_LOCALE = '<%=sLocale%>';
	sEditHeight = getParameter('height') || sEditHeight;
	sEditorConfig = getParameter('editorConfig') || "myconfig";
	CKEDITOR.replace("editor", {customConfig : sEditorConfig + ".js", height : sEditHeight, language : $G_LOCALE});

	//当编辑器第一次打开没有焦点时，避免单击插入图片按钮时，将整个编辑器区域清空
	GetEditor().on( 'instanceReady', function( e ){
		this.focus();
	} );

//-->
</script>
</body>
</html>