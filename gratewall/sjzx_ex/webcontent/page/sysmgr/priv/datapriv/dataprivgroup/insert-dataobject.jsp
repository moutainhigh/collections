<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>增加数据权限类型管理信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveRecord()
{
	saveRecord( '', '保存数据权限类型管理' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存数据权限类型管理' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存数据权限类型管理' );	// /txn103011.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn103011.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加数据权限类型管理信息"/>
<freeze:errors/>

<freeze:form action="/txn103013">
  <freeze:block property="record" caption="增加数据权限类型管理信息" width="95%">
      <freeze:button name="record_saveRecord" caption="保 存" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="保存并继续" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="保存并关闭" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="objectid" caption="数据权限类型代码" datatype="string" maxlength="" style="width:95%"/>
      <freeze:textarea property="objectsource" caption="数据权限来源" colspan="2" rows="4" maxlength="500" minlength="1" style="width:98%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
