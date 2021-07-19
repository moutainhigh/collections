<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>增加服务查询条件信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveRecord()
{
	saveRecord( '', '保存服务条件表' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存服务条件表' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存服务条件表' );	// /txn40210001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn40210001.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加服务查询条件信息"/>
<freeze:errors/>

<freeze:form action="/txn40210003">
  <freeze:block property="record" caption="增加服务查询条件信息" width="95%">
      <freeze:button name="record_saveRecord" caption="保 存" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="保存并继续" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="保存并关闭" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="condition_id" caption="服务查询条件ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="service_id" caption="服务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="frist_connector" caption="连接符1" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="left_paren" caption="左括号" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="table_name_en" caption="表名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_name_cn" caption="表中文名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="column_name_en" caption="字段名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="column_name_cn" caption="字段中文名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="second_connector" caption="连接符2" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="param_value" caption="参数值" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="param_type" caption="参数值类型" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="right_paren" caption="右括号" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="show_order" caption="显示顺序" datatype="string" maxlength="" style="width:95%"/>
      <freeze:text property="need_input" caption="是否需要参数" datatype="string" maxlength="1" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
