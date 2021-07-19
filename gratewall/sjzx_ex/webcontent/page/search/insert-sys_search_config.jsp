<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>增加全文检索信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveRecord()
{
	saveRecord( '', '保存全文检索' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存全文检索' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存全文检索' );	// /txn50030101.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn50030101.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加全文检索信息"/>
<freeze:errors/>

<freeze:form action="/txn50030103">
  <freeze:block property="record" caption="增加全文检索信息" width="95%">
      <freeze:button name="record_saveRecord" caption="保 存" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="保存并继续" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="保存并关闭" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="sys_search_config_id" caption="搜索配置ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:text property="sys_svr_user_id" caption="用户ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:textarea property="permit_subject" caption="授权主题" colspan="2" rows="4" maxlength="4000" style="width:98%"/>
      <freeze:text property="create_by" caption="创建人" datatype="string" maxlength="10" minlength="1" style="width:95%"/>
      <freeze:text property="create_date" caption="创建日期" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:text property="config_order" caption="配置顺序" datatype="string" maxlength="" minlength="1" style="width:95%"/>
      <freeze:text property="is_pause" caption="是否暂停" datatype="string" maxlength="2" minlength="1" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
