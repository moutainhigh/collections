<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>修改报告操作情况信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存日志报告使用情况' );	// /txn620200201.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn620200201.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改报告操作情况信息"/>
<freeze:errors/>

<freeze:form action="/txn620200202">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="log_report_use_id" caption="日志报告操作id" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改报告操作情况信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="log_report_use_id" caption="日志报告操作id" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="log_report_create_id" caption="日志报告生成id" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="browser_person" caption="浏览人" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="browser_date" caption="浏览日期" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="operate" caption="操作" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:textarea property="filename" caption="文件名" colspan="2" rows="4" maxlength="1000" style="width:98%"/>
      <freeze:textarea property="path" caption="路径" colspan="2" rows="4" maxlength="4000" style="width:98%"/>
      <freeze:text property="timestamp" caption="时间戳" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
