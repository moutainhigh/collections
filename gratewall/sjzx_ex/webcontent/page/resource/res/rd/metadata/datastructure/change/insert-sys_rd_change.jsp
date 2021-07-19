<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>增加变更记录表信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveRecord()
{
	saveRecord( '', '保存变更记录表' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存变更记录表' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存变更记录表' );	// /txn80002301.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn80002301.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加变更记录表信息"/>
<freeze:errors/>

<freeze:form action="/txn80002303">
  <freeze:block property="record" caption="增加变更记录表信息" width="95%">
      <freeze:button name="record_saveRecord" caption="保 存" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="保存并继续" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="保存并关闭" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_rd_change_id" caption="表主键" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="sys_rd_data_source_id" caption="数据源ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="db_name" caption="数据源名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="db_username" caption="用户名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_name" caption="物理表" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_name_cn" caption="物理表中文名" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="column_name" caption="字段" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="column_name_cn" caption="字段中文名" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="change_item" caption="变更类型" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="change_before" caption="变更前内容" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="change_after" caption="变更后内容" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="change_result" caption="处理结果" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="change_oprater" caption="变更人" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="change_time" caption="变更时间" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:textarea property="change_reason" caption="变更原因" colspan="2" rows="4" maxlength="1000" style="width:98%"/>
      <freeze:text property="timestamp" caption="时间戳" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
