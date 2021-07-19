<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>修改文件上传采集表信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存文件上传采集表' );	// /txn30301001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn30301001.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改文件上传采集表信息"/>
<freeze:errors/>

<freeze:form action="/txn30301002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="file_upload_task_id" caption="文件上传任务ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改文件上传采集表信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="file_upload_task_id" caption="文件上传任务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="collect_task_id" caption="采集任务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="collect_table" caption="采集表" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="collect_mode" caption="采集模式" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="file_status" caption="文件状态" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:textarea property="file_description" caption="文件描述" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      <freeze:text property="collect_file_name" caption="采集文件名称" datatype="string" maxlength="200" colspan="2" style="width:98%"/>
      <freeze:text property="collect_file_id" caption="采集文件ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:textarea property="check_result_file_name" caption="校验结果文件名称" colspan="2" rows="4" maxlength="300" style="width:98%"/>
      <freeze:text property="check_result_file_id" caption="校验结果文件ID" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
