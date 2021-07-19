<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-historylog.jsp --%>
<freeze:html width="750" height="350">
<head>
<title>意见反馈的修改日志</title>
</head>

<script language="javascript">

// 返 回
function func_record_goBackNoUpdate()
{
	goBackNoUpdate();	// /txn711001.do
}

// 设置修改过的字段
function _setModifyField()
{
	// 取TABLE
	var	table = document.getElementsByName( "record" );
	if( table.length < 1 ){
		return;
	}
	
	// 取每列的内容，进行比较
	var	rows = table[0].rows;
	for( var ii=1; ii<rows.length-1; ii++ ){
		var cells1 = rows[ii].cells;
		var cells2 = rows[ii+1].cells;
		for( var jj=0; jj<cells1.length-2; jj++ ){
			var ct = cells2[jj].innerText;
			if( cells1[jj].innerText != ct && ct != '' ){
				cells1[jj].style.color = "red";
			}
		}
	}
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	_setModifyField();
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="意见反馈的修改日志"/>
<freeze:errors/>

<freeze:form action="/txn711007">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_feedback_id" caption="意见反馈ID" style="width:95%"/>
  </freeze:frame>

  <freeze:grid property="record" caption="意见反馈的修改日志" keylist="h_record_id_" checkbox="false" width="95%" navbar="bottom" fixrow="true">
      <freeze:cell property="sys_feedback_id" caption="意见反馈ID" style="width:19%" />
      <freeze:cell property="content" caption="意见反馈内容" style="width:20%" visible="false" />
      <freeze:cell property="publish_date" caption="发布时间" style="width:17%" />
      <freeze:cell property="author" caption="发布人" style="width:32%" />
      <freeze:cell property="status" caption="有效标志" style="width:10%" visible="false"/>
      <freeze:cell property="description" caption="处理结果" style="width:32%" />
      <freeze:cell property="h_record_id_" caption="流水号" style="width:14%" visible="false" />
      <freeze:cell property="h_back_time_" caption="修改时间" style="width:16%" />
      <freeze:cell property="h_back_user_" caption="修改人" style="width:14%" />
  </freeze:grid>
  
  <p align="center">
  <input type="button" name="record_goBackNoUpdate" value="返 回" class="menu" onclick="func_record_goBackNoUpdate();" style='width:60px' >
  </p>

</freeze:form>
</freeze:body>
</freeze:html>
