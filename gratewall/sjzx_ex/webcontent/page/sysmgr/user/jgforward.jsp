<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="350">
<head>
<title>�����û���Ϣ</title>
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
    <freeze:hidden property="yhid_pk" caption="�û�ID" style="width:90%"/>
    <freeze:hidden property="jgid_fk" caption="����ID" style="width:90%"/>
  </freeze:frame>
�û���Ϣ����ɹ�!<br>

��������û������б�<br>

<freeze:button name="�����б�" property="�����б�" onclick="func_record_goBack()"></freeze:button>


</div></body>
</freeze:html>