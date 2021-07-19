<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询基础代码列表</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// 增加基础代码
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_rd_standard_codedata.jsp", "增加基础代码", "modal" );
	page.addParameter( "select-key:sys_rd_standar_codeindex", "record:sys_rd_standar_codeindex" );
	page.addRecord();
}

// 修改基础代码
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn7000414.do", "修改基础代码", "modal" );
	page.addParameter( "record:id", "primary-key:id" );
	page.updateRecord();
}

// 删除基础代码
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn7000415.do", "删除基础代码" );
	page.addParameter( "record:id", "primary-key:id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 返回代码集
function func_record_gobackRecord()
{
	window.location.href='/txn7000401.do';
	//goBack( "/txn7000401.do");
}
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	//alert(window.location.pathname);
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询基础代码列表"/>
<freeze:errors/>

<freeze:form action="/txn7000411">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:text property="sys_rd_standar_codeindex" caption="代码集标识符" maxlength="10" datatype="string" readonly='true' style="width:95%"/>
      <freeze:text property="codeindex_name" caption="代码集名称" datatype="string" readonly='true' style="width:95%"/>
      <freeze:text property="sys_rd_standard_codevalue" caption="代码值" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="sys_rd_standard_codename" caption="代码内容" datatype="string" maxlength="10" style="width:95%"/>
     
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="查询基础代码列表" keylist="id" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="增加基础代码" txncode="7000413" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改基础代码" txncode="7000414" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除基础代码" txncode="7000415" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
       <freeze:button name="record_gobackRecord" caption="返回"  enablerule="0" hotkey="" align="right" onclick="func_record_gobackRecord();"/>
      <freeze:hidden property="id" caption="代码值ID" style="width:34%" />
      <freeze:hidden property="sys_rd_standar_codeindex" caption="代码集标识符" style="width:30%" />
      <freeze:cell property="sys_rd_standard_codevalue" caption="代码值" style="width:30%" />
      <freeze:cell property="sys_rd_standard_codename" caption="代码内容" style="width:20%"  />
      <freeze:cell property="description" caption="说明" style="width:20%"  />
      
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
