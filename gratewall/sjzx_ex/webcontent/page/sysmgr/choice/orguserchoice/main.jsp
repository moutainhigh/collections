<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<freeze:html>
<freeze:include href="/script/selectTree.js" />
<head>
	<title>ʵ�����λ����û�ѡ��</title>
</head>
<%
String jgid = "3535fd267cbe4fad95e251e04f77e694";
%>
<freeze:body>
<script language="javascript">
//ѡ������û�ȫ��(��ѡ)
function JGYH_CHECK_ALL(){
	var jgid_pk=document.getElementById("record:jgid_pk").value;
	selectJGYH('check','record:jgid_pk','record:jgmc',jgid_pk);
}
//ѡ������û�(��ѡ)
function JGYH_CHECK_ONE(){
	var jgid_pk=document.getElementById("record:jgid_pk").value;
	selectJGYH('check','record:jgid_pk','record:jgmc',jgid_pk,'<%=jgid%>');
}
//ѡ������û�(��ѡ)
function JGYH_TREE(){
	selectJGYH('tree','record:jgid_pk','record:jgmc');
}
</script>
	<freeze:title caption="ʵ�����λ����û�ѡ��" />
	<freeze:errors />
	<br><br><br><br>
	<freeze:form action="/txn808002.do" styleId="form1">
		<freeze:block property="record" caption="��һ��Ŀ��Ա��Ϣ" width="95%">
			<freeze:button name="ok" caption="ѡ������û�ȫ��(��ѡ)" onclick="JGYH_CHECK_ALL();" />
			<br>
			<input type="button" name="ok1" value="ѡ������û�(��ѡ)" onclick="JGYH_CHECK_ONE();">
			<freeze:button name="ok" caption="ѡ������û�(��ѡ)" onclick="JGYH_TREE();" />
			<br><br><br><br><br>
			<freeze:text property="jgid_pk" caption="ID" style="width:90%" maxlength="1000" datatype="string" notnull="true" colspan="2" />
			<freeze:text property="jgmc" caption="NAME" style="width:90%" maxlength="1000" datatype="string" notnull="true" colspan="2" />
		</freeze:block>
	</freeze:form>
</freeze:body>
</freeze:html>
