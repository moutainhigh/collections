<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询术语列表</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// 增加术语
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_rd_standard_term.jsp", "增加术语", "modal" );
	page.addRecord();
}

// 修改术语
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn7000104.do", "修改术语", "modal" );
	page.addParameter( "record:sys_rd_standar_term_id", "primary-key:sys_rd_standar_term_id" );
	page.updateRecord();
}

// 删除术语
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn7000105.do", "删除术语" );
	page.addParameter( "record:sys_rd_standar_term_id", "primary-key:sys_rd_standar_term_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 删除术语
function func_record_viewRecord()
{
	var page = new pageDefine( "/txn7000106.do", "查看术语", "modal" );
	page.addParameter( "record:sys_rd_standar_term_id", "primary-key:sys_rd_standar_term_id" );
	page.viewRecord( );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{

}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询术语列表"/>
<freeze:errors/>

<freeze:form action="/txn7000101">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:text property="standard_term_cn" caption="术语名称" datatype="string" maxlength="32" style="width:90%"/>
       <freeze:text property="standard_term_en" caption="术语英文" datatype="string" maxlength="32" style="width:90%"/>
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="查询术语列表" keylist="sys_rd_standar_term_id" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="增加术语" txncode="7000103" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改术语" txncode="7000104" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除术语" txncode="7000105" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:button name="record_viewRecord" caption="查看术语" txncode="7000106" enablerule="1" hotkey="" align="right" onclick="func_record_viewRecord();"/>
      <freeze:hidden property="sys_rd_standar_term_id" caption="术语ID" style="width:20%" />
      <freeze:cell property="standard_term_cn" caption="术语名称" style="width:20%" />
      <freeze:cell property="standard_term_en" caption="英文名称" style="width:20%" />
      <freeze:cell property="standard_term_definition" caption="术语定义" style="width:60%"  />
      <freeze:hidden property="memo" caption="备注" style="width:15%"  />
      
      
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
