<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询功能使用统计列表</title>
<style type="text/css">
#navbar{
	display:none;
}
#frame_record{
	border-collapse:collapse;
}
</style>
</head>

<script language="javascript">
// 禁止焦点自动获得，以防止用户选择滚动条的时候自动弹回。
function _setFocus(){
	return;
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption=""/>
<freeze:errors/>

<freeze:form action="/txn60900001">
  <freeze:frame property="select-key" caption=""  width="100%" >
	<freeze:hidden property="func_name" caption="企业(机构)ID" style="width:95%"/>
	<freeze:hidden property="query_date_from" caption="企业类别：" style="width:95%" />
	<freeze:hidden property="query_date_to" caption="企业名称：" style="width:95%"/>
  </freeze:frame>
  <freeze:grid property="record" caption="各区县分局使用情况统计" width="95%" navbar="bottom" checkbox="false" fixrow="false" >
  	<freeze:cell property="@rowid" caption="序号" align="middle" style="width:6%"/>
    <freeze:cell property="sjjgname" caption="区县局" style="width:15%"/>
  	<freeze:cell property="querytimes" caption="使用次数" value="" style="width:15%"/>
  </freeze:grid>
</freeze:form>
</freeze:body>
</freeze:html>
