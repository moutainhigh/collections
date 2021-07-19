<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="750" height="350">
<head>
<title>修改ftp任务信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存FTP任务表','/txn30101013.do?primary-key:collect_task_id=' + getFormFieldValue("record:collect_task_id") );	// /txn30201001.do
}

// 返 回
function func_record_goBack()
{
	//goBack();	// /txn30201001.do
	var page = new pageDefine( '/txn30101013.do?primary-key:collect_task_id=' + getFormFieldValue("record:collect_task_id"), '查询采集任务');
	page.updateRecord();
}
function getParameter(){
	//从当前页面取值，组成key=value格式的串
    var parameter = 'input-data:service_targets_id=<freeze:out  value="${record.service_targets_id}"/>';
	return parameter;
}
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改ftp任务信息"/>
<freeze:errors/>

<freeze:form action="/txn30201002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="ftp_task_id" caption="FTP任务ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改ftp任务信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="ftp_task_id" caption="FTP任务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="collect_task_id" caption="采集任务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="service_targets_id" caption="服务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="service_no" caption="任务ID：" datatype="string" readonly="true" style="width:95%"/>
      <freeze:text property="file_name_en" caption="文件名称"  readonly="true" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="file_name_cn" caption="文件中文名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:browsebox property="collect_table" caption="对应采集表" show="name" notnull="true" valueset="资源管理_服务对象对应采集表" parameter="getParameter();"  style="width:95%"/>
      <freeze:select property="collect_mode" caption="采集方式" show="name" notnull="true" valueset="资源管理_采集方式" style="width:95%"/>
      <freeze:hidden property="file_status" caption="文件状态" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:textarea property="file_description" caption="文件描述" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      <freeze:hidden property="fj_path" caption="附件path"  />
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
