<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="600" height="200">
<head>
<title>修改参数维护信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存参数表' );	// /txn30103001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn30103001.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="维护参数信息"/>
<freeze:errors/>

<freeze:form action="/txn30103002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="webservice_patameter_id" caption="参数ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="维护参数信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="webservice_patameter_id" caption="参数ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="webservice_task_id" caption="webservice任务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="service_targets_id" caption="服务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:select property="patameter_type" caption="参数类型" valueset="资源管理_参数类型" notnull="true" style="width:95%"/>
      <freeze:text property="patameter_name" caption="参数名" datatype="string" maxlength="100" notnull="true" style="width:95%"/>
      <freeze:textarea property="patameter_value" caption="参数值"  colspan="2" rows="2" maxlength="1000" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
