<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>修改资料</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	saveAndExit('保存成功','保存失败',"txn53000001.do");
}

// 保存并继续
function func_record_saveAndContinue()
{
    setFormFieldValue('record:sys_notice_state',0,'1');
	saveAndExit('保存成功','保存失败',"txn53000001.do");
}
// 返 回
function func_record_goBack()
{
	goBack();	// /txn50220001.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改资料"/>
<freeze:errors/>

<freeze:form action="/txn53000002" enctype="multipart/form-data">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_notice_id" caption="资料ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改资料" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_saveAndContinue" caption="保存并发布" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_notice_id" caption="资料ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="sys_notice_title" caption="标题" datatype="string" maxlength="50" minlength="1" style="width:95%"/>
      <freeze:textarea property="sys_notice_matter" caption="内容" colspan="2" rows="4" maxlength="300" style="width:98%"/>
      <freeze:hidden property="sys_notice_promulgator" caption="发布人" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="sys_notice_org" caption="发布单位" datatype="string" maxlength="60" style="width:95%"/>
      <freeze:hidden property="sys_notice_date" caption="发布时间" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="sys_notice_state" caption="发布状态" datatype="string" maxlength="1" value="0" style="width:95%"/>
      <freeze:file property="sys_notice_filepath" caption="修改附件" accept="*.*" style="width:95%" colspan="2"/>
      <freeze:cell property="sys_notice_filepath" caption="已有附件：" style="width:95%" colspan="2"/>
      <freeze:hidden property="old_sys_notice_filepath" caption="附件" style="width:95%" />
  </freeze:block>
  <div id="showHtml"></div>
</freeze:form>
</freeze:body>
</freeze:html>
