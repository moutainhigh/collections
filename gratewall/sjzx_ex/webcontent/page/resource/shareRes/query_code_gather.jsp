<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html>
<head>
<title>代码集查询</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存测试' );	// /txn555551.do
}

// 返 回
function func_record_goBack()
{
	window.close();
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查看业务代码集"/>
<freeze:errors/>

<freeze:form action="/txn20301026">

  <freeze:grid property="record" caption="查询业务代码集列表"  width="95%" checkbox="false" navbar="bottom" fixrow="false" >
			
			<freeze:cell property="jcsjfx_dm" caption="代码" style="width:20%" align="center"/>
			<freeze:cell property="jcsjfx_mc" caption="代码值" style="width:75%" align="left"/>
			
		</freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
