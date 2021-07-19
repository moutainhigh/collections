<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询抽取服务管理列表</title>
</head>

<script language="javascript">
// 查询Session信息
function func_viewDetail(){
	var page = new pageDefine( "/txn501040002.do", "查看转换策略列表", "window" );
	page.addParameter( "record:workflow_id","select-key:workflow_id");
	page.addParameter( "record:rep_foldername", "select-key:rep_foldername" );
	page.addParameter( "record:wf_name", "select-key:wf_name" );
	page.addParameter( "record:dbuser", "select-key:dbuser" );
	page.goPage();
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询抽取服务管理列表"/>
<freeze:errors/>

<freeze:form action="/txn501040001">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:select property="rep_id" caption="项目名称" valueset="ETL项目" show="name" style="width:55%"/>
  </freeze:block>

  <freeze:grid property="record" caption="抽取服务列表" keylist="workflow_id" width="95%" navbar="bottom" fixrow="false" multiselect="false">
      <freeze:button name="record_dispatch" caption="明细" txncode="501040002" enablerule="1" align="right" onclick="func_viewDetail();"/>
      <freeze:hidden property="sys_etl_wf_id" caption="ID" style="width:10%" />
      <freeze:hidden property="rep_folderid" caption="文件夹ID" style="width:15%" />
      <freeze:cell property="rep_foldername" caption="文件夹名称" align="center" style="width:15%" />
      <freeze:hidden property="workflow_id" caption="workflow_ID" style="width:15%" />
      <freeze:cell property="wf_name" caption="抽取服务名称" align="center" style="width:15%" />
      <freeze:cell property="wf_ms" caption="抽取服务描述" align="center" style="width:20%" />
      <freeze:cell property="wf_db_source" caption="源数据库" align="center" style="width:20%" />
      <freeze:cell property="wf_db_target" caption="目标数据库" align="center" style="width:20%" />
      <freeze:hidden property="wf_state" caption="状态" style="width:10%" />
      <freeze:hidden property="dbuser" caption="数据库用户" style="width:10%" />
      <freeze:hidden property="domain_name" caption="域名" style="width:10%" />
      <freeze:hidden property="server_name" caption="服务名" style="width:10%" />
  </freeze:grid>
</freeze:form>
</freeze:body>
</freeze:html>
