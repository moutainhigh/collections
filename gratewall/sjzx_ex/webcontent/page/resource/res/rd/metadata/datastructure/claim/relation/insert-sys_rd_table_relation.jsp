<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>增加表关系信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveRecord()
{
	saveRecord( '', '保存表关系' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存表关系' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存表关系' );	// /txn80002601.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn80002601.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加表关系信息"/>
<freeze:errors/>

<freeze:form action="/txn80002603">
  <freeze:block property="record" caption="增加表关系信息" width="95%">
      <freeze:button name="record_saveRecord" caption="保 存" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="保存并继续" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="保存并关闭" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="sys_rd_table_relation_id" caption="关联ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:text property="sys_rd_table_id" caption="主表ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="table_code" caption="主表代码" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_name" caption="主表中文名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_fk" caption="主表字段" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_fk_name" caption="主表字段名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="relation_table_code" caption="关联表代码" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="relation_table_name" caption="关联表中文名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="relation_table_fk" caption="关联表字段" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="ralation_table_fk_name" caption="关联表字段中文名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="sys_rd_system_id" caption="所属主题ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="sys_rd_data_source_id" caption="所属数据源ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="table_relation_type" caption="关联关系类型" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:textarea property="remarks" caption="注释" colspan="2" rows="4" maxlength="1000" style="width:98%"/>
      <freeze:text property="timestamp" caption="时间戳" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
