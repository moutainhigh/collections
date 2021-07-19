<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>增加ftp服务参数信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveRecord()
{
	saveRecord( '', '保存ftp服务参数' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存ftp服务参数' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存ftp服务参数' );	// /txn40402001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn40402001.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加ftp服务参数信息"/>
<freeze:errors/>

<freeze:form action="/txn40402003">
  <freeze:block property="record" caption="增加ftp服务参数信息" width="95%">
      <freeze:button name="record_saveRecord" caption="保 存" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="保存并继续" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="保存并关闭" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="srv_param_id" caption="参数值ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:text property="ftp_service_id" caption="FTP服务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="param_value_type" caption="代码表String INT BOOLEAN" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="patameter_name" caption="参数名" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="patameter_value" caption="参数值" datatype="string" maxlength="50" style="width:95%"/>
      <freeze:text property="style" caption="格式" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="showorder" caption="顺序字段" datatype="string" maxlength="" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
