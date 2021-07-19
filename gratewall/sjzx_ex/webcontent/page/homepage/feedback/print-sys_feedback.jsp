<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-print.jsp --%>
<freeze:html width="750" height="400">
<head>
<title>打印意见反馈信息</title>
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
<freeze:title caption="打印意见反馈信息"/>
<freeze:errors/>

<!-- 用户自定义的函数 -->
<script language="javascript">
function prepareHead( totalRow, pageRow, totalPage, currentPage )
{
	document.write( '<table border="0" width="100%"><tr><td>打印测试程序的 [headscript] 属性</td>' );
	document.write( '<td>　</td>' );
	document.write( '<td align="right">第 ' + currentPage + ' 页，总共 ' + totalPage + ' 页</td></tr></table>' );
}

function prepareTail( totalRow, pageRow, totalPage, currentPage )
{
	document.write( '<table border="0" width="100%"><tr><td>打印测试程序的 [tailscript] 属性</td>' );
	document.write( '<td>　</td>' );
	document.write( '<td align="right">第 ' + currentPage + ' 页，总共 ' + totalPage + ' 页</td></tr></table>' );
}
</script>

<freeze:form action="/txn711001">
  <p align="center">
  <freeze:print property="record" fixrow="true" pageRows="24" caption="<font size='5'><b>打印测试程序的[caption]属性</b></font>" note="这是一个打印测试表的 [note] 属性" headscript="prepareHead" tailscript="prepareTail" align="center" width="95%">
      <freeze:cell property="sys_feedback_id" caption="意见反馈ID" style="width:19%" />
      <freeze:cell property="content" caption="意见反馈内容" style="width:20%" visible="false" />
      <freeze:cell property="publish_date" caption="发布时间" style="width:17%" />
      <freeze:cell property="author" caption="发布人" style="width:32%" />
      <freeze:cell property="status" caption="有效标志" style="width:10%" visible="false"/>
      <freeze:cell property="description" caption="处理结果" style="width:32%" />
  </freeze:print></p>
  <p align="center" class="hide">
  <input type="button" name="record_printDocument" value="打 印" class="menu" onclick="func_record_printDocument();" style='width:60px' >
  <input type="button" name="record_goBackNoUpdate" value="返 回" class="menu" onclick="func_record_goBackNoUpdate();" style='width:60px' >
  </p>
  <p align="center" class="hide">&nbsp;</p>
  
</freeze:form>
</freeze:body>
</freeze:html>
