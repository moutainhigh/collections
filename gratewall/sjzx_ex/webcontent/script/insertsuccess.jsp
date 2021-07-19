<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>

<freeze:html layout="">
<head>
<title>删除记录成功</title>
</head>



<body bgcolor="#ffffff" onunload="window.returnValue='<%=request.getParameter("nextPage")%>';">

<script type="text/javascript">
// 修改日志信息
_browse.updateLogger( window, false );
window.close();
</script>

</body>
</freeze:html>
