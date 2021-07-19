<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="350">
<head>
<title>增加用户信息</title>
</head>
<script language="javascript">
var org;
function getOrgID(){
 org = document.getElementById("primary-key:jgid_fk").value;
 }
 
function func_record_goBack(){
var param = "select-key:jgid_fk="+org;
  goBack( "/txn807011.do?"+param );
}
</script>
<body class="body-main" onload="getOrgID()"><div class="body-div">

  <freeze:frame property="primary-key" width="95%">
    <freeze:hidden property="yhid_pk" caption="用户ID" style="width:90%"/>
    <freeze:hidden property="jgid_fk" caption="机构ID" style="width:90%"/>
  </freeze:frame>
用户信息保存成功!<br>

点击返回用户管理列表<br>

<freeze:button name="返回列表" property="返回列表" onclick="func_record_goBack()"></freeze:button>


</div></body>
</freeze:html>