<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>

<freeze:html layout="">
<head>
<title>ɾ����¼ʱ����</title>
</head>

<script language="javascript">

 var tmp = page_getSavedActionData(window.parent);
 if(tmp != null) {
	 tmp.successNumber = 0;
 }	

// ������ʾ��Ϣ
window.parent._hideProcessHintWindow();

// �ָ����а�ť
window.parent.checkAllMenuItem();
</script>

<body bgcolor="#ffffff">
<p>ɾ����¼ʱ����</p>
</body>
</freeze:html>
