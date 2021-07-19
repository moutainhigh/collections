<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>增加下载设置信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveRecord()
{
	saveRecord( '', '保存下载设置' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存下载设置' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存下载设置' );	// /txn105001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn105001.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加下载设置信息"/>
<freeze:errors/>

<freeze:form action="/txn105003">
  <freeze:block property="record" caption="增加下载设置信息" width="95%">
      <freeze:button name="record_saveRecord" caption="保 存" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="保存并继续" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="保存并关闭" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="download_purv_id" caption="主键" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="agency_id" caption="机构编号" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="has_purv" caption="是否允许下载" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="max_result" caption="允许下载条数" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="last_modi_user" caption="最后修改者" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="last_modi_date" caption="最后修改日期" datatype="string" maxlength="10" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
