<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<freeze:include href="/script/selectTree.js"/>
<head>
<title>ʵ�����λ����û�ѡ��</title>
</head>


<%
String jgid = "";
%>

<freeze:body>
<freeze:title caption="ʵ�����λ�����ɫѡ��"/>
<freeze:errors/>

</freeze:body>
<br><br><br><br>
<freeze:form action="/txn808002.do" styleId="form1">
 <freeze:block property="record" caption="��һ��Ŀ��Ա��Ϣ" width="95%" >	
 <freeze:button name="ok" caption="ѡ�������ɫ(��ѡ)" onclick="selectJGJS('check','record:jgid_pk','record:jgmc');"/><br>
 <input type="button" name="ok1" value="ѡ�������ɫ(��ѡ)" onclick="javaScript:selectJGJS('check','record:jgid_pk','record:jgmc');">	 
 	 <freeze:button name="ok" caption="ѡ�������ɫ(��ѡ)" onclick="selectJGJS('tree','record:jgid_pk','record:jgmc');"/><br>
 	<br><br><br><br>

<freeze:text property="jgid_pk" caption="ID" style="width:90%" maxlength="1000" datatype="string" notnull="true" colspan="2"/>		
<freeze:text property="jgmc" caption="NAME" style="width:90%" maxlength="1000" datatype="string" notnull="true" colspan="2"/>		
  </freeze:block>
</freeze:form> 	
</freeze:html>
