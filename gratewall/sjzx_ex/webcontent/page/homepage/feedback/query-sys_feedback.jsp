<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询意见反馈列表</title>
</head>

<script language="javascript">

// 增加意见反馈
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_feedback.jsp", "增加意见反馈", "modal" );
	page.addRecord();
}

// 修改意见反馈
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn711004.do", "修改意见反馈", "modal" );
	page.addParameter( "record:sys_feedback_id", "primary-key:sys_feedback_id" );
	page.updateRecord();
}

// 删除意见反馈
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn711005.do", "删除意见反馈" );
	page.addParameter( "record:sys_feedback_id", "primary-key:sys_feedback_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 打印列表
function func_record_printPage()
{
	var page = new pageDefine( "/txn711001.do", "打印列表", "printWindow" );
	page.printPage( 2 );
}

// 查看内容
function func_record_viewRecord()
{
	var page = new pageDefine( "/txn711006.do", "查看内容", "modal" );
	page.addParameter( "record:sys_feedback_id", "primary-key:sys_feedback_id" );
	page.viewRecord();
}

// 修改日志
function func_record_viewHistoryLog()
{
	var page = new pageDefine( "/txn711007.do", "查询意见反馈的修改日志", "modal" );
	page.addParameter( "record:sys_feedback_id", "primary-key:sys_feedback_id" );
	page.goPage( );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询意见反馈列表"/>
<freeze:errors/>

<freeze:form action="/txn711001">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>

  <freeze:grid property="record" caption="查询意见反馈列表" keylist="sys_feedback_id" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="增加意见反馈" txncode="711003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改意见反馈" txncode="711004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除意见反馈" txncode="711005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:button name="record_printPage" caption="打印列表" txncode="711001" enablerule="0" hotkey="PRINT" align="right" onclick="func_record_printPage();"/>
      <freeze:button name="record_viewRecord" caption="查看内容" txncode="711006" enablerule="1" hotkey="VIEW" align="right" onclick="func_record_viewRecord();"/>
      <freeze:button name="record_viewHistoryLog" caption="修改日志" txncode="711007" enablerule="1" align="right" onclick="func_record_viewHistoryLog();"/>
      <freeze:cell property="sys_feedback_id" caption="意见反馈ID" style="width:19%" />
      <freeze:cell property="content" caption="意见反馈内容" style="width:20%" visible="false" />
      <freeze:cell property="publish_date" caption="发布时间" style="width:17%" />
      <freeze:cell property="author" caption="发布人" style="width:32%" />
      <freeze:cell property="status" caption="有效标志" style="width:10%" visible="false"/>
      <freeze:cell property="description" caption="处理结果" style="width:32%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
