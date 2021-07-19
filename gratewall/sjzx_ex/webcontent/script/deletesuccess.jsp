<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>

<freeze:html layout="">
<head>
<title>删除记录成功</title>
</head>

<script language="javascript">
// 修改日志信息
_browse.updateLogger( window, false );

var	nextPage = '<%=request.getParameter("inner-flag:delete-success-page")%>';
if( nextPage.indexOf('@') == 0 ){
	if( nextPage.indexOf('(') < 0 ){
		nextPage = nextPage + "()";
	}

	window.parent._hideProcessHintWindow();
	eval( "window.parent." + nextPage.substring(1) );
}
else if( nextPage != "null" ){
	var previewFlag = '<%=request.getParameter("inner-flag:preview-flag")%>';
	if( previewFlag == 'true' ){
		if( nextPage.indexOf('?') > 0 ){
			nextPage = nextPage + "&inner-flag:preview-flag=true";
		}
		else{
			nextPage = nextPage + "?inner-flag:preview-flag=true";
		}
	}
	
 	// 打开窗口的类型
 	var win = window.parent;
	
	// 取缓存数据标志
	var addr = _addHrefParameter( nextPage, 'inner-flag:back-flag', 'true' );

 	// 打开窗口的类型
	addr = _addHrefParameter( addr, 'inner-flag:open-type', win.openWindowType );
	
	// 生成全路径
	addr = _browse.resolveURL( addr );
	
 	// 转换编码
 	addr = page_setSubmitData( win, addr );
 	
 	if( win.openWindowType == 'modal' ){
	 	var aObject = win.document.getElementById( '@modalRefresh' );
	 	if( aObject == null ){
	 		aObject = win.document.createElement( "A" );
			aObject.id = '@modalRefresh';
			aObject.style.display = 'none';
			win._browse.appendElement( 'body', aObject );
	 	}
	 	
	 	aObject.href = addr;
	 	aObject.click( );
	}
	else{
		//console.log(addr);
		addr = addr.replace(/attribute-node:record_record-number/ig, 'x');
		//console.log(addr);
		//win.location = nextPage;
		win.location = addr;
	}
}
else{
	window.parent._hideProcessHintWindow();
	window.parent.deleteSuccess();
}
</script>

<body bgcolor="#ffffff">
<p>删除记录时成功</p>
</body>
</freeze:html>
