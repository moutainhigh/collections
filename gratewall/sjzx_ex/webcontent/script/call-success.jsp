<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>

<freeze:html layout="">
<head>
<title>调用服务成功</title>
</head>

<script language="javascript">
var	nextPage = '<%=request.getParameter("inner-flag:service-success-page")%>';
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
	
	goBackWithUpdate( nextPage, window.parent );
}
else{
	window.parent._hideProcessHintWindow();
	window.parent.deleteSuccess();
}
</script>

<body bgcolor="#ffffff">
<p>调用服务执行成功</p>
</body>
</freeze:html>
