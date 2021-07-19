<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询文件上传采集表列表</title>
</head>

<script language="javascript">
function return_parents()
{

}


function __userInitPage()
{
	window.parent.location.href='/txn30101001.do';
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body >
<freeze:title caption="查询文件上传采集表列表"/>
<freeze:errors/>

</freeze:body>
</freeze:html>
