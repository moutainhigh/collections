<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询转换策略列表</title>
</head>

<script language="javascript">
// 查询Session信息
function func_viewDetail(){
	var page = new pageDefine( "/txn501040003.do", "查看日志详细信息", "modal" );
	page.addParameter( "record:workflow_run_id", "select-key:workflow_run_id" );
	page.addParameter( "record:mapping_name", "select-key:mapping_name" );
	page.addParameter( "select-key:rep_foldername", "select-key:rep_foldername" );
	page.addParameter( "select-key:wf_name", "select-key:wf_name" );
	page.addParameter( "select-key:dbuser", "select-key:dbuser" );	
	page.goPage();
}

// 返回
function func_record_goBack()
{
	goBack();
}
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询转换策略列表"/>
<freeze:errors/>

<freeze:form action="/txn501040002">
  <freeze:frame property="select-key" caption="" width="95%">
      <freeze:hidden property="workflow_id" caption="workflow_id" style="width:55%"/> 
      <freeze:hidden property="dbuser" caption="数据库用户" style="width:55%"/> 
  </freeze:frame>

  <freeze:grid property="record" caption="转换策略列表" keylist="workflow_id" width="95%" navbar="bottom" fixrow="false" multiselect="false">
      <freeze:button name="record_dispatch" caption="明细" txncode="501040003" enablerule="1" hotkey="UPDATE" align="right" onclick="func_viewDetail();"/>
      <freeze:hidden property="workflow_id" caption="ID" style="width:10%"/>    
      <freeze:cell property="mapping_name" caption="转换策略" align="center" style="width:20%" />
      <freeze:hidden property="src_success_rows" caption="源成功记录数" style="width:15%" />
      <freeze:hidden property="src_failed_rows" caption="源失败记录数" style="width:15%" />
      <freeze:hidden property="targ_success_rows" caption="目标成功记录数" style="width:15%" />
      <freeze:hidden property="targ_failed_rows" caption="目标失败记录数" style="width:20%" />
      <freeze:hidden property="total_trans_errs" caption="转换条数" style="width:20%" />
      <freeze:hidden property="first_error_msg" caption="出错信息" style="width:50%" />
      <freeze:cell property="start_time" caption="开始时间" align="center" style="width:30%" />
      <freeze:cell property="end_time" caption="结束时间" align="center" style="width:30%" />
      <freeze:cell property="status" caption="状态" align="center" />
      <freeze:hidden property="workflow_run_id" caption="workflow_run_id" style="width:20%" />
  </freeze:grid>
  <div style="text-align:center">
  	<input type="button" value="返回" onclick="func_record_goBack();" class="menu" style="width:60px;" />
  </div>
</freeze:form>
</freeze:body>
</freeze:html>
