<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>增加业务表信息</title>
</head>

<script language="javascript">
function getParameter()
{
	var parameter = 'txncode='+_top.menu.selectedNode.id+'_30402003';
	return parameter;
}

// 保 存
function func_record_saveRecord()
{
	saveRecord( '', '保存业务表定义' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存业务表定义' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存业务表定义' );	// /txn30402001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn30402001.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	document.getElementById('label:record:downloadflag').innerHTML='是否可下载：';
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加业务表信息"/>
<freeze:errors/>

<freeze:form action="/txn30402003">
  <freeze:block property="record" caption="增加业务表信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:select property="sys_id" caption="业务主题" valueset="业务系统" notnull="true" style="width:95%" show="name" data="code"/>
      <freeze:hidden property="table_order" caption="检索顺序" datatype="number" maxlength="5" minlength="1" style="width:95%"/>
      <freeze:text property="table_name" caption="业务表名" datatype="string" maxlength="60" minlength="1" style="width:95%"/>
      <freeze:text property="table_name_cn" caption="业务表中文名" datatype="string" maxlength="100" minlength="1" style="width:95%"/>
      <freeze:radio property="downloadflag" caption="是否可下载" valueset="布尔型数" notnull="true" value="0" style="width:95%" show="name" data="code"/>
      <freeze:textarea property="demo" caption="备注" colspan="2" rows="4" maxlength="1000" style="width:98%" />
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
