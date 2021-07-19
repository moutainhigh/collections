<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>增加共享主题信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveRecord()
{
	saveRecord( '', '保存业务主题表' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存业务主题表' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存业务主题表' );	// /txn203011.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn203011.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加共享主题信息"/>
<freeze:errors/>

<freeze:form action="/txn203013">
  <freeze:block property="record" caption="增加共享主题信息" width="95%">
      <freeze:button name="record_saveRecord" caption="保 存" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="保存并继续" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="保存并关闭" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="business_topics_id" caption="业务主题ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:text property="service_targets_id" caption="服务对象ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="topics_name" caption="主题名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="topics_no" caption="主题编号" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:textarea property="topics_desc" caption="主题描述" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      <freeze:text property="show_order" caption="显示顺序" datatype="string" maxlength="" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
