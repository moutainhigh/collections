<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>

<freeze:html layout="">
<head>
<title>ɾ����¼�ɹ�</title>
</head>

<script language="javascript">
// �޸���־��Ϣ
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
	
 	// �򿪴��ڵ�����
 	var win = window.parent;
	
	// ȡ�������ݱ�־
	var addr = _addHrefParameter( nextPage, 'inner-flag:back-flag', 'true' );

 	// �򿪴��ڵ�����
	addr = _addHrefParameter( addr, 'inner-flag:open-type', win.openWindowType );
	
	// ����ȫ·��
	addr = _browse.resolveURL( addr );
	
 	// ת������
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
<p>ɾ����¼ʱ�ɹ�</p>
</body>
</freeze:html>
