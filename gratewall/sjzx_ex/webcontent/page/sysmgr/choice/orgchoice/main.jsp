<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<freeze:html>
<freeze:include href="/script/selectTree.js" />
<head>
	<title>实现树形机构用户选择</title>
</head>
<freeze:body>
	<freeze:title caption="实现树形机构用户选择" />
	<freeze:errors />
<script language="javascript">
//选择机构(多选)
function JG_CHECK(){
	var jgid_pk=document.getElementById("record:jgid_pk").value;
	selectJG('check','record:jgid_pk','record:jgmc',jgid_pk);
}
//选择机构(单选)
function JG_TREE(){
	selectJG('tree','record:jgid_pk','record:jgmc');
}
</script>
<br><br><br><br>
	<freeze:form action="/txn808002.do" styleId="form1">
		<freeze:block property="record" caption="单一项目成员信息" width="95%">
			<freeze:button name="ok" caption="选择机构(多选)" onclick="JG_CHECK();" />
			<br>
			<freeze:button name="ok" caption="选择机构(单选)" onclick="JG_TREE();" />
			<br><br><br><br><br>

			<freeze:text property="jgid_pk" caption="ID" style="width:90%" maxlength="1000" datatype="string" notnull="true" colspan="2" />
			<freeze:text property="jgmc" caption="NAME" style="width:90%" maxlength="1000" datatype="string" notnull="true" colspan="2" />
		</freeze:block>
	</freeze:form>
</freeze:body>
</freeze:html>
