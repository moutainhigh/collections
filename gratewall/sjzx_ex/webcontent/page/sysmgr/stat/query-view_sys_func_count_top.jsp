<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询功能使用统计列表</title>
<style type="text/css">
#navbar{
	display:none;
}
#frame_record{
	border-collapse:collapse;
}
.FixedTitleRow{
     background-color: #7fbfd6;
     color: #000000;
}
</style>
</head>

<script language="javascript">
// 禁止焦点自动获得，以防止用户选择滚动条的时候自动弹回。
function _setFocus(){
	return;
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
}

function resetAllValue(){
	document.getElementById("select-key:query_date_from").value = "";
	document.getElementById("select-key:query_date_to").value = "";
}

function submitValue(){
	var query_date_from = document.getElementById("select-key:query_date_from").value;
	var query_date_to = document.getElementById("select-key:query_date_to").value;
	var rgExp = /^\d\d\d\d-\d\d-\d\d$/;
	if(query_date_from && !query_date_from.match(rgExp)){
		alert("【执行日期自】格式错误");
		return false;
	}
	if(query_date_to && !query_date_to.match(rgExp)){
		alert("【执行日期至】格式错误");
		return false;
	}
	
	document.forms[0].submit();
}

function funcViewDetail(){
  		var index = getSelectedRowid("record");
  		var funcName = getFormFieldText("record:func_name",index);
  		var dateFrom = getFormFieldValue("select-key:query_date_from");
  		var dateTo = getFormFieldValue("select-key:query_date_to");
  		var param = '?select-key:func_name='+funcName;
  		if(dateFrom != ''){
  			param += '&select-key:query_date_from='+dateFrom;
  		}
  		if(dateTo != ''){
  			param += '&select-key:query_date_to='+dateTo;
  		}
  		var page = new pageDefine( '/txn60900002.do'+param, "子功能统计" );
  		var win = window.frames('ajlist');
  		document.getElementById("ajlist").style.display = "block";
	 	page.goPage( null, win );
}
_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption=""/>
<freeze:errors/>

<freeze:form action="/txn60900001">
  <freeze:block theme="query" property="select-key" caption="按功能统计" width="95%">
      <freeze:datebox property="query_date_from" caption="执行日期自" datatype="string" maxlength="10" style="width:90%"/>
      <freeze:datebox property="query_date_to" caption="至" datatype="string" maxlength="10" style="width:90%"/>
  </freeze:block>
  <br/>
  <freeze:grid property="record" caption="功能大类使用情况统计" width="95%" navbar="bottom" headstyle="FixedTitleRow" checkbox="false" fixrow="false" onclick="funcViewDetail()" >
  	<freeze:cell property="@rowid" caption="序号" align="middle" style="width:6%"/>
    <freeze:cell property="func_name" caption="功能大类" style="width:15%"/>
  	<freeze:cell property="querytimes" caption="使用次数" value="" style="width:15%"/>
  </freeze:grid>
  <br/>
  <table align="center" width="95%"><tr><td><iframe name='ajlist' scrolling=no frameborder=0 width=100% style="display:none"></iframe></td></tr></table>
</freeze:form>
</freeze:body>
</freeze:html>
