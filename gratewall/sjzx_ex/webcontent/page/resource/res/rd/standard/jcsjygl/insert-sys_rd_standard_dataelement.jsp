<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="950" height="350">
<head>
<title>增加基础数据元信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveRecord()
{
	saveRecord( '', '保存基础数据元管理' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存基础数据元管理' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存基础数据元管理' );	// /txn7000301.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn7000301.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加基础数据元信息"/>
<freeze:errors/>

<freeze:form action="/txn7000303">
  <freeze:block property="record" caption="增加基础数据元信息" width="95%">
      <freeze:button name="record_saveRecord" caption="保 存" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="保存并继续" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="保存并关闭" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="identifier" caption="标识符" datatype="string" maxlength="8" style="width:95%" notnull="true"/>
      <freeze:select property="standard_category" caption="规范类型" valueset="规范类型" style="width:95%" notnull="true"/>   
      <freeze:text property="cn_name" caption="中文名称" colspan="2"  maxlength="256" style="width:98%" notnull="true"/>
      <freeze:text property="en_name" caption="英文名称" colspan="2"  maxlength="256" style="width:98%"/>
      <freeze:text property="column_nane" caption="字段名称" datatype="string" maxlength="64" style="width:95%"/>
      <freeze:select property="data_type" caption="数据类型" valueset="字段数据类型" style="width:95%" notnull="true"/>
      <freeze:text property="data_length" caption="数据长度" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="data_format" caption="数据格式" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="value_domain" caption="值域" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="jc_standar_codeindex" caption="基础代码集标识符" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="unit" caption="计量单位" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="synonyms" caption="同义词" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="version" caption="版本" datatype="string" maxlength="8" style="width:95%"/>
      <freeze:textarea property="representation" caption="表示" colspan="2" rows="4" maxlength="4000" style="width:98%"/>
      <freeze:textarea property="memo" caption="备注" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
