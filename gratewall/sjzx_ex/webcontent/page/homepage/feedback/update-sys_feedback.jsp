<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>修改意见反馈信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存意见反馈' );	// /txn711001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn711001.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改意见反馈信息"/>
<freeze:errors/>

<freeze:form action="/txn711002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_feedback_id" caption="意见反馈ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改意见反馈信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_feedback_id" caption="意见反馈ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:textarea property="content" caption="意见反馈内容" colspan="2" rows="4" maxlength="4000" style="width:98%"/>
      <freeze:text property="publish_date" caption="发布时间" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="author" caption="发布人" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="status" caption="有效标志" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="description" caption="处理结果" datatype="string" maxlength="200" colspan="2" style="width:98%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
