<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>增加共享ftp服务信息</title>
</head>

<script language="javascript">
var svrId = "<%= request.getParameter("svrId") %>";

// 保 存
function func_record_saveRecord()
{
	saveRecord( '', '保存共享ftp服务' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存共享ftp服务' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存共享ftp服务' );	// /txn40401001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn40401001.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	setFormFieldValue("record:service_id",svrId);
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加共享ftp服务信息"/>
<freeze:errors/>

<freeze:form action="/txn40401003">
  <freeze:block property="record" caption="增加共享ftp服务信息" width="95%">
      <freeze:button name="record_saveRecord" caption="保 存" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="保存并继续" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="保存并关闭" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="ftp_service_id" caption="FTP服务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="service_id" caption="服务ID" datatype="string" maxlength="32" style="width:95%"  />
      <freeze:text property="datasource_id" caption="数据源ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="srv_scheduling_id" caption="服务调度ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="file_name" caption="文件名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="file_type" caption="文件类型" datatype="string" maxlength="20" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
