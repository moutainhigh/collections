<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="800" height="400">
<head>
<title>查询采集数据库列表</title>
</head>

<script type="text/javascript">


// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:errors/>

<freeze:form action="/txn30300007">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>

  <freeze:grid property="record" caption="查询ETL采集任务" keylist="workflow_name" width="95%" checkbox="false" rowselect="false">
      <freeze:cell property="tno" caption="序号" style="width:8%" align="center"/>
      <freeze:cell property="instance_name" caption="子任务名称" style="width:50%" align="center"/>
      <freeze:cell property="remark" caption="任务描述" style="width:42%" />
  </freeze:grid>
  <table width="95%" align="center"><tr><td align="center">
  	<div class="btn_cancel" onclick="window.close();"></div>
  </td></tr></table>
<Br/>
</freeze:form>
</freeze:body>
</freeze:html>
