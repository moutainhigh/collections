<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>增加主题信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveRecord()
{
	saveRecord( '', '保存业务主题定义' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存业务主题定义' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存业务主题定义' );	// /txn30401001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn30401001.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加系统信息"/>
<freeze:errors/>

<freeze:form action="/txn30401003">
  <freeze:block property="record" caption="增加系统信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="sys_name" caption="主题名称" datatype="string" maxlength="100" minlength="1" style="width:95%"/>
      <freeze:hidden property="sys_order" caption="检索顺序" datatype="number" maxlength="5" minlength="1" style="width:95%"/>
      <freeze:textarea property="sys_simple" caption="备注" colspan="2" rows="4" maxlength="1000" style="width:98%" />
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
