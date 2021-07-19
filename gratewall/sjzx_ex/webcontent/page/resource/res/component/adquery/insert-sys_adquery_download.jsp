<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>增加高级查询及下载信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveRecord()
{
	saveRecord( '', '保存高级查询及下载' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存高级查询及下载' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存高级查询及下载' );	// /txn6025001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn6025001.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加高级查询及下载信息"/>
<freeze:errors/>

<freeze:form action="/txn6025003">
  <freeze:block property="record" caption="增加高级查询及下载信息" width="95%">
      <freeze:button name="record_saveRecord" caption="保 存" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="保存并继续" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="保存并关闭" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_advanced_query_id" caption="主键" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="name" caption="高级查询主题名称" datatype="string" maxlength="200" colspan="2" style="width:98%"/>
      <freeze:text property="create_by" caption="创建人" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="create_date" caption="创建日期" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="last_exec_date" caption="最后执行日期" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="sys_svr_user_id" caption="用户id" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="is_mutil_download" caption="是否重复狭隘" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:text property="apply_count" caption="申请次数" datatype="string" maxlength="" style="width:95%"/>
      <freeze:text property="last_down_date" caption="最后下载时间" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
