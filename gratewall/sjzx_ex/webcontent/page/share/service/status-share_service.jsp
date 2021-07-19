<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-status.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>修改记录的状态</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存共享服务的服务表' );	// /txn4020000b.do
}

// 返 回
function func_record_goBackNoUpdate()
{
	goBackNoUpdate();	// /txn4020000b.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var statusFieldName = 'record:status';
	var status = getFormFieldValue( statusFieldName );
	status = (status == '1') ? '0' : '1';
	setFormFieldValue( statusFieldName, 0, status );
	setFormFieldReadonly( statusFieldName, 0, true );
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改记录的状态"/>
<freeze:errors/>

<freeze:form action="/txn4020000b">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="service_id" caption="服务ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改记录的状态" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBackNoUpdate" caption="返 回" hotkey="CLOSE" onclick="func_record_goBackNoUpdate();"/>
      <freeze:hidden property="service_id" caption="服务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="is_markup" caption="有效标记" datatype="string" maxlength="1" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
