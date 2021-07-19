<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询未认领表字段列表</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/FusionCharts/jquery-1.4.2.min.js"></script>
<script language="javascript">

// 增加未认领表字段
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_rd_unclaim_column.jsp", "增加未认领表字段", "modal" );
	page.addRecord();
}

// 修改未认领表字段
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn80002404.do", "修改未认领表字段", "modal" );
	page.addParameter( "record:sys_rd_unclaim_column_id", "primary-key:sys_rd_unclaim_column_id" );
	page.updateRecord();
}

// 删除未认领表字段
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn80002405.do", "删除未认领表字段" );
	page.addParameter( "record:sys_rd_unclaim_column_id", "primary-key:sys_rd_unclaim_column_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 返 回
function func_record_goBack()
{
	goBack();	// 
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var operationSpan = document.getElementsByName("span_record:operation");
	var number=1;
	for (var i=0; i < operationSpan.length; i++){
		operationSpan[i].style.textAlign = "center";
		operationSpan[i].innerHTML = number++;
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询未认领表字段列表"/>
<freeze:errors/>

<freeze:form action="/txn80002401">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:hidden property="sys_rd_unclaim_table_id" caption="物理表ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="unclaim_tab_code" caption="物理表" datatype="string" maxlength="32" style="width:39%" readonly="true" colspan="2"/>
      <freeze:text property="unclaim_column_code" caption="字段名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:select property="unclaim_column_type" caption="字段类型" valueset="字段数据类型"  style="width:95%"/>
  </freeze:block>
<BR>
  <freeze:grid property="record" caption="查询未认领表字段列表" keylist="sys_rd_unclaim_column_id" width="95%" rowselect="false" checkbox="false" navbar="bottom" fixrow="false">
      <freeze:button name="record_goBack" caption="返 回"  hotkey="CLOSE" align="right" onclick="func_record_goBack();"/>
      <freeze:button name="record_addRecord" caption="增加未认领表字段" txncode="80002403" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改未认领表字段" txncode="80002404" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除未认领表字段" txncode="80002405" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:hidden property="sys_rd_unclaim_column_id" caption="表主键" style="width:10%" />
      <freeze:hidden property="sys_rd_unclaim_table_id" caption="物理表ID" style="width:12%" />
      <freeze:hidden property="sys_rd_data_source_id" caption="数据源ID" style="width:12%" />
      <freeze:hidden property="unclaim_tab_code" caption="未认领表名" style="width:20%" />
      <freeze:cell property="@rowid" caption="序号" align="center" style="width:5%" />
      <freeze:cell property="unclaim_column_code" caption="字段名称" style="width:20%" />
      <freeze:cell property="unclaim_column_name" caption="字段中文名" style="width:20%" />
      <freeze:cell property="unclaim_column_type" caption="字段类型" valueset="字段数据类型" style="width:10%" />
      <freeze:cell property="unclaim_column_length" caption="字段长度" style="width:10%" />
      <freeze:hidden property="is_primary_key" caption="是否主键" style="width:10%" />
      <freeze:hidden property="is_index" caption="是否索引" style="width:10%" />
      <freeze:hidden property="is_null" caption="是否允许为空" style="width:10%" />
      <freeze:hidden property="default_value" caption="默认值" style="width:20%" />
      <freeze:hidden property="remarks" caption="备注" style="width:20%"  />
      <freeze:hidden property="timestamp" caption="时间戳" style="width:12%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
