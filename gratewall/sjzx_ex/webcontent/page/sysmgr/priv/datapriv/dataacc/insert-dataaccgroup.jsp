<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>增加数据权限分组信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveRecord()
{
	saveRecord( '', '保存数据权限分组' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存数据权限分组' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存数据权限分组' );	// /txn103051.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn103051.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加数据权限分组信息"/>
<freeze:errors/>

<freeze:form action="/txn103053">
  <freeze:block property="record" caption="增加数据权限分组信息" width="95%">
      <freeze:button name="record_saveRecord" caption="保 存" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="保存并继续" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="保存并关闭" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="dataaccgrpid" caption="数据权限分组ID" datatype="string" maxlength="" style="width:95%"/>
      <freeze:text property="dataaccgrpname" caption="数据权限分组名称" datatype="string" maxlength="40" minlength="1" style="width:95%"/>
      <freeze:text property="dataaccrule" caption="角色权限认证规则" datatype="string" maxlength="1" minlength="1" style="width:95%"/>
      <freeze:text property="dataacctype" caption="数据权限分组类型" datatype="string" maxlength="1" minlength="1" style="width:95%"/>
      <freeze:text property="dataaccgrpdesc" caption="数据权限分组描述" datatype="string" maxlength="200" colspan="2" style="width:98%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
