<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<freeze:html>
<freeze:include href="/script/selectTree.js" />
<head>
	<title>实现树形机构用户选择</title>
</head>
<%
String jgid = "3535fd267cbe4fad95e251e04f77e694";
%>
<freeze:body>
<script language="javascript">
//选择机构用户全部(多选)
function JGYH_CHECK_ALL(){
	var jgid_pk=document.getElementById("record:jgid_pk").value;
	selectJGYH('check','record:jgid_pk','record:jgmc',jgid_pk);
}
//选择机构用户(多选)
function JGYH_CHECK_ONE(){
	var jgid_pk=document.getElementById("record:jgid_pk").value;
	selectJGYH('check','record:jgid_pk','record:jgmc',jgid_pk,'<%=jgid%>');
}
//选择机构用户(单选)
function JGYH_TREE(){
	selectJGYH('tree','record:jgid_pk','record:jgmc');
}
</script>
	<freeze:title caption="实现树形机构用户选择" />
	<freeze:errors />
	<br><br><br><br>
	<freeze:form action="/txn808002.do" styleId="form1">
		<freeze:block property="record" caption="单一项目成员信息" width="95%">
			<freeze:button name="ok" caption="选择机构用户全部(多选)" onclick="JGYH_CHECK_ALL();" />
			<br>
			<input type="button" name="ok1" value="选择机构用户(多选)" onclick="JGYH_CHECK_ONE();">
			<freeze:button name="ok" caption="选择机构用户(单选)" onclick="JGYH_TREE();" />
			<br><br><br><br><br>
			<freeze:text property="jgid_pk" caption="ID" style="width:90%" maxlength="1000" datatype="string" notnull="true" colspan="2" />
			<freeze:text property="jgmc" caption="NAME" style="width:90%" maxlength="1000" datatype="string" notnull="true" colspan="2" />
		</freeze:block>
	</freeze:form>
</freeze:body>
</freeze:html>
