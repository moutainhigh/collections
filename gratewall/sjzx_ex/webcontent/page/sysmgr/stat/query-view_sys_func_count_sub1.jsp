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
	document.getElementById("label:record:@rowid").className = "addBoder";
	document.getElementById("label:record:func_name").className = "addBoder";
	document.getElementById("label:record:querytimes").className = "addBoder";
}

function funcViewDetail(){
	    //document.getElementById("div").style.height = 800;
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
  		var page = new pageDefine( '/txn60900003.do'+param, "子子功能统计","_blank",screen.availWidth-30,screen.availHeight-60 );
	 	page.goPage( null, window );
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption=""/>
<freeze:errors/>

<freeze:form action="/txn60900001">
  <freeze:frame property="select-key" caption=""  width="100%" >
	<freeze:hidden property="func_name" caption="企业(机构)ID" style="width:95%"/>
	<freeze:hidden property="query_date_from" caption="企业类别：" style="width:95%" />
	<freeze:hidden property="query_date_to" caption="企业名称：" style="width:95%"/>
  </freeze:frame>
  <div style="position: absolute;">
  <freeze:grid property="record" caption="子功能使用情况统计" width="100%" navbar="bottom" checkbox="false" fixrow="false" onclick="funcViewDetail()" >
  	<freeze:cell property="@rowid" caption="序号" align="middle" style="width:6%"/>
    <freeze:cell property="func_name" caption="子功能" style="width:15%"/>
  	<freeze:cell property="querytimes" caption="使用次数" value="" style="width:15%"/>
  </freeze:grid>
  </div>
</freeze:form>
</freeze:body>
</freeze:html>
