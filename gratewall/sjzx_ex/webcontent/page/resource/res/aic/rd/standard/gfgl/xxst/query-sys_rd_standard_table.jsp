<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询实体信息列表</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// 增加实体信息
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_rd_standard_table.jsp", "增加信息实体", "modal" );
	page.addParameter( "select-key:sys_rd_standard_id", "record:sys_rd_standard_id" );
	page.addParameter( "select-key:standard_name", "record:standard_name" );
	page.addRecord();
}

// 修改实体信息
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn7000214.do", "修改实体信息", "modal" );
	page.addParameter( "record:sys_rd_standard_table_id", "primary-key:sys_rd_standard_table_id" );
	page.updateRecord();
}

// 删除代码集
function func_record_deleteRecord()
{ 
     var page = new pageDefine( "/txn7000221.ajax", "查询指标项" ); 
     page.addParameter("record:sys_rd_standard_table_id", "select-key:sys_rd_standard_table_id"); 
	 page.callAjaxService('doCallback');
	 page.callAjaxService( null, true );
}

function doCallback(errorCode, errDesc, xmlResults) 
{ 
    //被调用函数，读取使用Ajax技术得到的数据
	if ( errorCode != '000000' ) {
		alert("查询记录失败，请检查后重试");
		return;
	} 
	var codeIndex = _getXmlNodeValues(xmlResults, 'record:sys_rd_standard_table_id');
	
	if(codeIndex != ''){
		alert("请先删除代码集里的代码值！");
		return;
	}
	
	var page = new pageDefine( "/txn7000215.do", "删除实体信息" );
	page.addParameter( "record:sys_rd_standard_table_id", "primary-key:sys_rd_standard_table_id" );
	page.deleteRecord( "是否删除选中的记录" );
	
}

// 查看实体信息
function func_record_viewRecord()
{
	var page = new pageDefine( "/txn7000216.do", "查看实体信息", "modal" );
	page.addParameter( "record:sys_rd_standard_table_id", "primary-key:sys_rd_standard_table_id" );
	page.viewRecord();
}

// 查看指标项
function func_record_cloumnRecord_button()
{
    var page = new pageDefine( "/txn7000221.do", "查看指标项" );
	page.addParameter( "record:sys_rd_standard_table_id", "select-key:sys_rd_standard_table_id" );
	page.addParameter( "record:table_name", "select-key:table_name" );
	page.goPage();
}
// 查看指标项
function func_record_cloumnRecord()
{
	if (event.srcElement.tagName.toUpperCase() == 'LABEL'){
		return;
	}
	if (event.srcElement.tagName.toUpperCase() == 'INPUT'){
		return;
	}
	if (event.srcElement.tagName.toUpperCase() == 'A'){
		return;
	}
    var page = new pageDefine( "/txn7000221.do", "查看指标项" );
	page.addParameter( "record:sys_rd_standard_table_id", "select-key:sys_rd_standard_table_id" );
	page.addParameter( "record:table_name", "select-key:table_name" );
	var urlkey = document.getElementById("select-key:standard_name");
	page.addParameter("select-key:standard_name","backkey");
	page.goPage();
}

// 返回
function func_record_gobackRecord()
{
	window.location.href='/txn7000201.do';
	//goBack( "/txn7000201.do");
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询实体信息列表"/>
<freeze:errors/>

<freeze:form action="/txn7000211">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:hidden property="sys_rd_standard_id" caption="标准ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="standard_name" caption="标准名称" datatype="string" maxlength="100" style="width:95%" readonly="true"/>
      <freeze:text property="table_name" caption="信息实体名称" datatype="string" maxlength="100" style="width:95%"/>
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="查询实体信息列表" keylist="sys_rd_standard_table_id" width="95%" navbar="bottom" fixrow="false" onclick="func_record_cloumnRecord()">
      <freeze:button name="record_addRecord" caption="增加" txncode="7000213" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改" txncode="7000214" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除" txncode="7000215" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:button name="record_viewRecord" caption="查看" txncode="7000216" enablerule="1" hotkey="" align="right" onclick="func_record_viewRecord();"/>
      <freeze:button name="record_columnRecord" caption="指标项" txncode="7000221" enablerule="1" hotkey="" align="right" onclick="func_record_cloumnRecord_button()"/>
      <freeze:button name="record_gobackRecord" caption="返回"  enablerule="0"  align="right" onclick="func_record_gobackRecord()"/>
      <freeze:hidden property="sys_rd_standard_table_id" caption="信息实体ID" style="width:11%" />
      <freeze:hidden property="sys_rd_standard_id" caption="标准ID" style="width:11%" />
      <freeze:hidden property="standard_name" caption="标准名称" style="width:18%" />
      <freeze:cell property="table_name" caption="信息实体名称" style="width:15%" />
      <freeze:cell property="table_belongs" caption="所属体系" style="width:20%" />
      <freeze:cell property="memo" caption="备注" style="width:20%"  />
      <freeze:hidden property="sort" caption="排序号" style="width:10%" />
      <freeze:hidden property="timestamp" caption="时间戳" style="width:10%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
