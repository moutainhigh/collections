<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>增加术语信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveRecord()
{
	saveRecord( '', '保存术语定义管理' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存术语定义管理' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存术语定义管理' );	// /txn7000101.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn7000101.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加术语信息"/>
<freeze:errors/>

<freeze:form action="/txn7000103">
  <freeze:block property="record" caption="增加术语信息" width="95%">
      <freeze:button name="record_saveRecord" caption="保 存" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="保存并继续" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="保存并关闭" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="standard_term_cn" caption="术语名称" datatype="string" maxlength="64" style="width:95%" notnull="true"/>
      <freeze:text property="standard_term_en" caption="英文名称" datatype="string" maxlength="64" style="width:95%"/>
      <freeze:textarea property="standard_term_definition" caption="术语定义" colspan="2" rows="4" maxlength="1024" style="width:98%"/>
      <freeze:textarea property="memo" caption="备注" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
