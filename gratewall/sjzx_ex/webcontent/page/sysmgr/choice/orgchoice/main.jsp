<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<freeze:html>
<freeze:include href="/script/selectTree.js" />
<head>
	<title>ʵ�����λ����û�ѡ��</title>
</head>
<freeze:body>
	<freeze:title caption="ʵ�����λ����û�ѡ��" />
	<freeze:errors />
<script language="javascript">
//ѡ�����(��ѡ)
function JG_CHECK(){
	var jgid_pk=document.getElementById("record:jgid_pk").value;
	selectJG('check','record:jgid_pk','record:jgmc',jgid_pk);
}
//ѡ�����(��ѡ)
function JG_TREE(){
	selectJG('tree','record:jgid_pk','record:jgmc');
}
</script>
<br><br><br><br>
	<freeze:form action="/txn808002.do" styleId="form1">
		<freeze:block property="record" caption="��һ��Ŀ��Ա��Ϣ" width="95%">
			<freeze:button name="ok" caption="ѡ�����(��ѡ)" onclick="JG_CHECK();" />
			<br>
			<freeze:button name="ok" caption="ѡ�����(��ѡ)" onclick="JG_TREE();" />
			<br><br><br><br><br>

			<freeze:text property="jgid_pk" caption="ID" style="width:90%" maxlength="1000" datatype="string" notnull="true" colspan="2" />
			<freeze:text property="jgmc" caption="NAME" style="width:90%" maxlength="1000" datatype="string" notnull="true" colspan="2" />
		</freeze:block>
	</freeze:form>
</freeze:body>
</freeze:html>
