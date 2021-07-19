<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>

<freeze:html>
<head>
<title>导出功能权限数据</title>
</head>
<freeze:body>
<freeze:title caption="导出功能权限数据"/>
<freeze:errors/>

<script language="javascript">
function saveData()
{
	var data = getFormFieldValue( "record:sqldata" );
	_save2File( data );
}
</script>

<freeze:form action="/txn980309">

	<table border='0' cellspacing='0' cellpadding='0' width="100%" height="95%">
	<tr height="90%"><td align="center">
		<freeze:edit property="record:sqldata" style="width:95%;height:100%"/>
	</td></tr>
	<tr><td align="center">
		<freeze:button name="save" caption="保 存" styleClass="menu" onclick="saveData();"/>
		&nbsp;&nbsp;
		<freeze:button name="close" caption="返 回"styleClass="menu" onclick="window.close();"/>
	</td></tr>
	</table>
  
</freeze:form>
</freeze:body>
</freeze:html>


