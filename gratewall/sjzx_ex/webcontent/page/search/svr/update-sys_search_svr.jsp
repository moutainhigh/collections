<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="800" height="550" >
<head>
<title>修改检索服务信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存检索服务' );	// /txn50010201.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn50010201.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改检索服务信息"/>
<freeze:errors/>

<freeze:form action="/txn50010202">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_search_svr_id" caption="检索服务ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改检索服务信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_search_svr_id" caption="检索服务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="svr_name" caption="服务名称" datatype="string" maxlength="200" colspan="2" style="width:98%"/>
      <freeze:text property="svr_db" caption="检索数据库" datatype="string" maxlength="4000" style="width:98%"/>
      <freeze:textarea property="svr_query" caption="检索查询串" colspan="2" rows="4" maxlength="4000" style="width:98%"/>
      <freeze:textarea property="svr_template" caption="检索服务模板" colspan="8" rows="4" maxlength="4000" style="width:98%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
