<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>修改代码信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	var jc_dm_dm = getFormFieldValue("record:jc_dm_dm");
	var jc_dm_mc = getFormFieldValue("record:jc_dm_mc");
	
	if(/[\'@$!`~/?#,;^&\*\.\\]+/.test(jc_dm_dm))  {
		alert("基础代码中不应含有特殊字符"); 
		return;
	}
	if(/[\'@$!`~/?#,;^&\*\.\\]+/.test(jc_dm_mc))  {
		alert("基础代码名称中不应含有特殊字符"); 
		return;
	}
	saveAndExit( '', '保存基础代码管理' );	// /txn301011.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn301011.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}
function   checkMe()       
{   
 var loc = getFormFieldValue("record:jc_dm_mc");
  if(/[\'@$!`~/?#,;^&\*\.\\]+/.test(loc))  {
    alert("基础代码名称中不应含有特殊字符"); 
    return false;
  }else{
    return true;
  }
}
_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改代码信息"/>
<freeze:errors/>

<freeze:form action="/txn301012">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="jc_dm_id" caption="代码ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" captionWidth="0.5" caption="修改代码信息" width="95%" >
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="jc_dm_id" caption="代码ID" datatype="string" maxlength="36" style="width:95%"/>
      <freeze:text property="jc_dm_dm" caption="代码" datatype="string" maxlength="36" colspan="2" minlength="1" style="width:95%"/>
      <freeze:text property="jc_dm_mc" caption="代码名称" validator="checkMe()" datatype="string" maxlength="255" colspan="2" minlength="1" style="width:98%"/>
      <freeze:select property="jc_dm_bzly" caption="代码标准来源" valueset="基础代码标准来源" notnull="true" style="width:95%"/>
      <freeze:textarea property="jc_dm_ms" caption="代码描述" colspan="2" rows="4" maxlength="32700" style="width:98%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
