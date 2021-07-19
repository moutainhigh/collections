<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="350">
<head>
<title>增加用户信息</title>
</head>

<body class="body-main"><div class="body-div">
<script language="javascript">

function func_record_goBack(){
  goBack( "/txn807001.do" );
}
</script>

用户信息保存成功!<br>

点击返回用户管理列表<br>

<freeze:button name="返回列表" property="返回列表" onclick="func_record_goBack()"></freeze:button>


</div></body>
</freeze:html>