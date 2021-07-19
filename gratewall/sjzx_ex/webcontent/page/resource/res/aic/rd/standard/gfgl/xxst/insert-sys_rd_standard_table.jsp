<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="750" height="350">
<head>
<title>增加实体信息信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveRecord()
{
	saveRecord( '', '保存实体信息' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存实体信息' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存实体信息' );	// /txn7000211.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn7000211.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加实体信息信息"/>
<freeze:errors/>

<freeze:form action="/txn7000213">
  <freeze:block property="record" caption="增加实体信息信息" width="95%">
      <freeze:button name="record_saveRecord" caption="保 存" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="保存并继续" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="保存并关闭" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_rd_standard_id" caption="标准ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="standard_name" caption="规范名称" readonly="true" datatype="string" maxlength="100"colspan="2" style="width:95%"/>
      <freeze:text property="table_name" caption="实体信息名称" datatype="string" maxlength="100" style="width:95%" notnull="true"/>
      <freeze:text property="table_belongs" caption="所属体系" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:textarea property="memo" caption="备注" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      <freeze:hidden property="sort" caption="排序号" datatype="string" maxlength="" style="width:95%"/>
      
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
