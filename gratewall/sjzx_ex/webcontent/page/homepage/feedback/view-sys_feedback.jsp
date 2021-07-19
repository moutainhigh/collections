<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-view.jsp --%>
<freeze:html width="750" height="350">
<head>
<title>查看意见反馈信息</title>
</head>

<script language="javascript">

// 打 印
function func_record_printDocument()
{
	print(document);
}

// 返 回
function func_record_goBackNoUpdate()
{
	goBackNoUpdate();	// /txn711001.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body styleClass='body-div'>
<freeze:title caption="查看意见反馈信息"/>
<freeze:errors/>

<freeze:form action="/txn711006">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_feedback_id" caption="意见反馈ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block theme="print" property="record" caption="查看意见反馈信息" width="95%">
      <freeze:cell property="sys_feedback_id" caption="意见反馈ID" style="width:95%"/>
      <freeze:cell property="content" caption="意见反馈内容" colspan="2" datatype="text" style="width:98%"/>
      <freeze:cell property="publish_date" caption="发布时间" style="width:95%"/>
      <freeze:cell property="author" caption="发布人" style="width:95%"/>
      <freeze:cell property="status" caption="有效标志" datatype="string" style="width:95%" visible="false"/>
      <freeze:cell property="description" caption="处理结果" colspan="2" style="width:98%"/>
  </freeze:block>
  <p align="center" class="hide">
  <input type="button" name="record_printDocument" value="打 印" class="menu" onclick="func_record_printDocument();" style='width:60px' >
  <input type="button" name="record_goBackNoUpdate" value="返 回" class="menu" onclick="func_record_goBackNoUpdate();" style='width:60px' >
  </p>

</freeze:form>
</freeze:body>
</freeze:html>
