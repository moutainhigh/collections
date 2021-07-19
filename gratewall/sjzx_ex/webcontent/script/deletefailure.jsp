<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>

<freeze:html layout="">
<head>
<title>删除记录时错误</title>
</head>

<script language="javascript">

 var tmp = page_getSavedActionData(window.parent);
 if(tmp != null) {
	 tmp.successNumber = 0;
 }	

// 隐藏提示信息
window.parent._hideProcessHintWindow();

// 恢复所有按钮
window.parent.checkAllMenuItem();
</script>

<body bgcolor="#ffffff">
<p>删除记录时错误</p>
</body>
</freeze:html>
