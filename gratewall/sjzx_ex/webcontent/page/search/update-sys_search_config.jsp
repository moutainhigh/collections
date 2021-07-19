<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>修改全文检索信息</title>
</head>

<script language="javascript">

// 保 存
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
<freeze:title caption="修改全文检索信息"/>
<freeze:errors/>

<freeze:form action="/txn50030102">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_search_config_id" caption="搜索配置ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改全文检索信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_search_config_id" caption="搜索配置ID" datatype="string" maxlength="32" style="width:95%"/>
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
