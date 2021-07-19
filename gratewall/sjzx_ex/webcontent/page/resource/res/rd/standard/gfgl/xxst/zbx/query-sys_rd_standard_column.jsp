<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询指标项列表</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// 增加指标项
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_rd_standard_column.jsp", "增加指标项", "modal" );
	page.addParameter( "select-key:sys_rd_standard_table_id", "record:sys_rd_standard_table_id" );
	page.addRecord();
}

// 修改指标项
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn7000224.do", "修改指标项", "modal" );
	page.addParameter( "record:sys_rd_standard_column_id", "primary-key:sys_rd_standard_column_id" );
	page.updateRecord();
}

// 删除指标项
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn7000225.do", "删除指标项" );
	page.addParameter( "record:sys_rd_standard_column_id", "primary-key:sys_rd_standard_column_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 查看指标项
function func_record_viewRecord()
{
	var page = new pageDefine( "/txn7000226.do", "查看指标项", "modal" );
	page.addParameter( "record:sys_rd_standard_column_id", "primary-key:sys_rd_standard_column_id" );
	page.viewRecord();
}

//返回
function func_record_gobackRecord()
{
	window.location.href='/txn7000211.do?select-key:standard_name='+getvl("backkey");
	//goBack('/txn7000211.do');
}

//获取url中的值
function getvl(name) {
	var reg = new RegExp("(^|\\?|&)"+ name +"=([^&]*)(\\s|&|$)", "i");
	if (reg.test(location.href)) return unescape(RegExp.$2.replace(/\+/g, " "));
		return "";
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询指标项列表"/>
<freeze:errors/>

<freeze:form action="/txn7000221">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:hidden property="sys_rd_standard_table_id" caption="信息实体ID" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_name" caption="信息实体名称" datatype="string" maxlength="100" style="width:95%" readonly="true"/>
      <freeze:text property="cn_name" caption="指标项名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="column_name" caption="字段名称" datatype="string" maxlength="100" style="width:95%"/>
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="查询指标项列表" keylist="sys_rd_standard_column_id" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="增加指标项" txncode="7000223" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改指标项" txncode="7000224" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除指标项" txncode="7000225" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
       <freeze:button name="record_viewRecord" caption="查看指标项" txncode="7000226" enablerule="1"  align="right" onclick="func_record_viewRecord();"/>
      <freeze:button name="record_gobackRecord" caption="返回"  enablerule="0"  align="right" onclick="func_record_gobackRecord();"/>
      <freeze:hidden property="sys_rd_standard_column_id" caption="指标项ID" style="width:11%" />
      <freeze:hidden property="sys_rd_standard_table_id" caption="信息实体ID" style="width:11%" />
      <freeze:cell property="data_element_identifier" caption="数据元标识符" valueset="数据元标识符" style="width:9%" />
      <freeze:cell property="cn_name" caption="指标项名称" style="width:15%" />
      <freeze:cell property="column_name" caption="字段名称" style="width:19%" />
      <freeze:cell property="column_type" caption="数据类型" valueset="字段数据类型" style="width:10%" />
      <freeze:cell property="column_format" caption="格式" style="width:10%" />
      <freeze:cell property="code_identifier" caption="代码标识符" style="width:10%" />
      <freeze:hidden property="memo" caption="备注" style="width:20%" visible="false" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
