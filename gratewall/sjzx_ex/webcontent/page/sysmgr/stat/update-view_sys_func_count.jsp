<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>修改功能使用统计信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存功能使用统计' );	// /txn60900001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn60900001.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改功能使用统计信息"/>
<freeze:errors/>

<freeze:form action="/txn60900002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="func_name" caption="功能模块" style="width:95%"/>
      <freeze:hidden property="sjjgid_fk" caption="机构ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改功能使用统计信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="querytimes" caption="次数" datatype="string" maxlength="" style="width:95%"/>
      <freeze:text property="query_date" caption="执行日期" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:hidden property="func_name" caption="功能模块" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="sjjgid_fk" caption="机构ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="func_index" caption="序号" datatype="string" maxlength="" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
