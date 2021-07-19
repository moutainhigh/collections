<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询功能大小类对应列表</title>
</head>

<script language="javascript">

// 增加功能大小类对应
function func_record_addRecord()
{
	var page = new pageDefine( "insert-view_func_name_log.jsp", "增加功能大小类对应", "modal" );
	page.addRecord();
}

// 修改功能大小类对应
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn620100404.do", "修改功能大小类对应", "modal" );
	page.updateRecord();
}

// 删除功能大小类对应
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn620100405.do", "删除功能大小类对应" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询功能大小类对应列表"/>
<freeze:errors/>

<freeze:form action="/txn620100401">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>

  <freeze:grid property="record" caption="查询功能大小类对应列表" keylist="second_func_name_fk" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="增加功能大小类对应" txncode="620100403" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改功能大小类对应" txncode="620100404" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除功能大小类对应" txncode="620100405" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="first_func_name" caption="功能大类" style="width:34%" />
      <freeze:cell property="second_func_name" caption="功能小类" style="width:66%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
