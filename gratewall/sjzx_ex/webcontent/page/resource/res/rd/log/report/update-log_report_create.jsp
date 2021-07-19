<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>修改系统使用情况报告信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存日志报告生成' );	// /txn620200101.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn620200101.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改系统使用情况报告信息"/>
<freeze:errors/>

<freeze:form action="/txn620200102" enctype="multipart/form-data">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="log_report_create_id" caption="日志报告id" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改系统使用情况报告信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="log_report_create_id" caption="日志报告id" datatype="string" maxlength="32" style="width:95%"/>
   	  <freeze:file property="uploadfile" caption="文件名" colspan="2" accept="*.doc,*.docx" style="width:98%"/>
      <freeze:hidden property="report_type" caption="报告类型" colspan="2" valueset="报告类型" style="width:95%" />
      <freeze:hidden property="report_name" caption="报告名称"  maxlength="1000" style="width:98%"/>
      <freeze:hidden property="create_date" caption="建立日期" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:hidden property="last_mender" caption="最后修改者" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="publish_date" caption="发布日期" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:hidden property="publish_person" caption="发布人" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="state" caption="状态" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="operate" caption="操作" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="filename" caption="文件名"  maxlength="1000" style="width:98%"/>
      <freeze:hidden property="path" caption="路径"  maxlength="4000" style="width:98%"/>
      <freeze:hidden property="timestamp" caption="时间戳" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
