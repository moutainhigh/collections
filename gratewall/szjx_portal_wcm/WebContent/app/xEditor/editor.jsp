<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ page import="com.trs.infra.util.CMyFile" %>
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
	<script language="javascript" src="ckeditor/ckeditor_source.js"></script>
	<!-- <script language="javascript" src="ckeditor/_source/plugins/bottombutton/plugin.js"></script> -->
	<style type="text/css">
		html,body
		{
			height:100%;
			overflow:hidden;
			/*IE7 BUG 高度问题*/
			padding:0px;
			margin:0px;
		}
		/*一键排版显示中文按钮样式*/
		.cke_button_key4formatCommand .cke_icon
		{
			display: none !important;
		}
		.cke_button_key4formatCommand .cke_label
		{
			display: inline !important;
		}
		/*隐藏elementsPath区域*/
		#cke_bottom_editor
		{
			display: none !important;
		}
		/*编辑器底部按钮样式*/
		.editor_Bottom
		{
			border-left:1px solid #d4d4d4;
			border-right:1px solid #d4d4d4;
			border-bottom:1px solid #d4d4d4;
			height:28px;
			line-height:28px;
		}
		.editor_bottom_button
		{
			display:block;
			float:left;
			text-align:center;
			font-size:12px;
			width:63px;
			height:24px;
			line-height:24px;
			color:#013987;
			background-color:#fff;
			margin-left:7px;
			margin-right:3px;
			margin-top:2px;
			cursor:pointer;
		}
		/*#cke_path_editor{visibility:hidden;}*/
	</style>
</head>
<body onload="initialize()" >
	<!--编辑器区域，通过id来获取节点实现编辑器界面-->
	<textarea id="editor"></textarea><!--id必须有值任意、class唯一,该方法只对textarea有效-->
	
	<!-- 底部标签区域， -->
	<div id="editor_Bottom" class="editor_Bottom"></div>
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
		//CKEDITOR.OfficeActiveX.UploadLocals(CKEDITOR.instances.editor);
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

	//初步设置编辑器默认高度
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
	sEditHeight = getParameter('height') || sEditHeight;
	
	//画出编辑器
	CKEDITOR.replace("editor",{customConfig : "sourceconfig.js",height : sEditHeight});
	
	//根据编辑器所在环境进一步确定高度
	GetEditor().on( 'instanceReady', function( e ){
		//编辑区域距离父容器的offset总高度
		var editorAreaOffsetTop = document.getElementsByTagName("table")[0].offsetTop;
		//IE7获取offsetTop的BUG
		//if(editorAreaOffsetTop < 19) {
		//	editorAreaOffsetTop = 19;
		//}
		//编辑器容器的可见高度、宽度
		var iframeWindowHeight = document.body.clientHeight;
		var iframeWindowWidth = document.body.clientHeight;
		//编辑器底部按钮的高度
		var bottomButtonHeight = document.getElementById("editor_Bottom").offsetHeight;
		/*
		*计算出编辑器区域实际的高度（该高度为整个编辑窗口高度，与replace中的参数height有区别）
		*/
		var handleHeight = iframeWindowHeight-editorAreaOffsetTop-editorAreaOffsetTop-bottomButtonHeight;
		GetEditor().resize( iframeWindowWidth, handleHeight, null, true );
		//如果不获取焦点的话，编辑器页面不会呈现
		this.focus();
	});
	/*
	//编辑器加载完成后，执行自身全屏逻辑，达到界面自适应的效果
	GetEditor().on( 'instanceReady', function( event ){
		var editor = event.editor;
		setTimeout( function(){
			// Delay bit more if editor is still not ready.
			if ( !editor.element ){
				setTimeout( arguments.callee, 100 );
				return;
			}
			event.removeListener( 'instanceReady', this.callee );
			if ( editor.name == 'editor' ){
				var command = editor.getCommand( 'maximize' );
				command.exec();
			}
		}, 0 );
		//如果不获取焦点的话，编辑器页面不会呈现
		this.focus();
	}, null, null, 9999);
	*/
//-->
</script>
</body>
</html>