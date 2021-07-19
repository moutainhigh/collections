<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="cn.gwssi.common.web.util.RequestUtil,cn.gwssi.common.web.context.UserContext"%>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%
	UserContext user = (new RequestUtil(request)).getUserContext();
	String rootPath = request.getContextPath();
	String layoutPath = rootPath + user.getLayoutPath();
%>
<html>
<head>
<title>�������г��������ල����ίԱ��</title>
<script type="text/javascript" src="<%=rootPath%>/module/layout/layout-weiqiang/main/all-min.js"></script>
<script language="javascript">
	// ����ҳ��ı���
	document.title = "�������г��������ල����ίԱ��";
	
	// menuId Ҫ��λ�Ľڵ��ţ�xurl �˵���ҳ�棻mainPage ��ҳ���ַ
	function showSubMenu_callBack( menuId, xurl, mainPage )
	{
		// �������˵�ҳ��
		_showMenuPage( xurl );
		
		// ��������ҳ
		_showFunctionPage( mainPage );
	}
	
	
	// �˳�����Ӧ����
	function onBeforeUnloadHandler(){
		// ǩ��
/* 		var b = (event.screenX - window.screenLeft > document.documentElement.scrollWidth-20) && (event.clientY < 0);
		if( b || event.altKey ){
			var addr = _browse.contextPath + '/freeze.main?txn-code=logout';
			var page = new pageDefine( addr, "�û�ǩ��" );
			page.callAjaxService( );
		} */
		var b = (event.screenX - window.screenLeft > document.documentElement.scrollWidth-50) && (event.clientY < 0);
		if( b || event.altKey ){
			$.ajax({ 
				url : "<%=request.getContextPath()%>/logout.jsp", 
				async: false,
				success : function(json) {
				 // alert("ͬ������");
				}
			});
		}
	}
	
	window.onbeforeunload = onBeforeUnloadHandler;
	
    function popupWindow(){
    	var page = new pageDefine("/txn60800007.ajax");
    	page.callAjaxService("popWindowCallBack");
    }
     
    function popWindowCallBack(errCode,errDesc,xmlResults){
    	 if(errCode=='000000'){
    	 	var countnumber = _getXmlNodeValues( xmlResults, "/context/record/countnumber" );
    	 	if(parseInt(countnumber) > 0){
    	 	   // var oPopup =  new pageDefine("/txn60800006.do", "","_blank", 400, 300); 
    	 	    //oPopup.goPage();
    	 	    //window.open("<%=request.getContextPath()%>/txn60800006.do","_blank","height=300,width=500,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no"); 
    	 	}
    	 }
    }
    
    //window.onload = popupWindow;


</script>
</head>

<frameset rows="114,*" cols="*" name="rowset" frameborder="no" border="0" framespacing="0">
  <frame src="<%=layoutPath%>/main/top.jsp" name="topFrame" scrolling="no" noresize="noresize" id="topFrame" />
  <frameset cols="0,0,100%" name="listset" frameborder="no" border="0" framespacing="0">
    <frame src="<%=layoutPath%>/main/left.html" name="code" scrolling="auto" noresize="noresize" id="code" />
	<frame src="<%=layoutPath%>/main/switch.html" name="swit" scrolling="no" noresize="noresize" id="swit" />
    <frame src="<%=rootPath%>/txn53000009.do" name="main" scrolling="auto" noresize="noresize" id="main" />
  </frameset>
</frameset>
<noframes><body>
</body>
</noframes>
</html>
