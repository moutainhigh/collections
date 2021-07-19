<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<freeze:include href="/script/selectTree.js"/>
<head>
<title>实现树形机构用户选择</title>
</head>

<%
String jgid = "2e78412970e648cc807fe9da55988f43";
%>

<freeze:body>
<freeze:title caption="实现树形机构角色选择"/>
<freeze:errors/>

</freeze:body>
<br><br><br><br>
<freeze:form action="/txn808002.do" styleId="form1">
 <freeze:block property="record" caption="单一项目成员信息" width="95%" >	
 <freeze:button name="ok" caption="选择机构角色用户(多选)" onclick="selectJGJSYH('check','record:jgid_pk','record:jgmc');"/><br>
<input type="button" name="ok1" value="选择机构角色用户(多选)" onclick="javaScript:selectJGJSYH('check','record:jgid_pk','record:jgmc','<%=jgid%>');">	 
 	 <freeze:button name="ok" caption="选择机构角色用户(单选)" onclick="selectJGJSYH('tree','record:jgid_pk','record:jgmc');"/><br>
 	<br><br><br><br>

<freeze:text property="jgid_pk" caption="ID" style="width:90%" maxlength="1000" datatype="string" notnull="true" colspan="2"/>		
<freeze:text property="jgmc" caption="NAME" style="width:90%" maxlength="1000" datatype="string" notnull="true" colspan="2"/>		
  </freeze:block>
</freeze:form> 	
</freeze:html>
