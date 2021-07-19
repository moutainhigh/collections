<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询已认领表字段表列表</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script language="javascript">

// 增加已认领表字段表
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_rd_column.jsp", "增加已认领表字段表", "modal" );
	page.addRecord();
}

// 修改已认领表字段表
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn80002504.do", "修改已认领表字段表", "modal" );
	page.addParameter( "record:sys_rd_column_id", "primary-key:sys_rd_column_id" );
	page.updateRecord();
}

// 删除已认领表字段表
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn80002505.do", "删除已认领表字段表" );
	page.addParameter( "record:sys_rd_column_id", "primary-key:sys_rd_column_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 查询已认领表字段表
function func_record_viewRecord()
{
	if (event.srcElement.tagName.toUpperCase() == 'INPUT'){
		return;
	}
	if (event.srcElement.tagName.toUpperCase() == 'LABEL'){
		return;
	}
	if (event.srcElement.tagName.toUpperCase() == 'A'){
		return;
	}
	var page = new pageDefine( "/txn80002506.do", "查看已认领表字段表", "modal" );
	page.addParameter( "record:sys_rd_column_id", "primary-key:sys_rd_column_id" );
	page.updateRecord();
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn80002501.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	$(".radioNew").each(function(index){
		$($(this).prev()[0]).css("display","");
		$($(this).prev()[0]).css("margin-left","-1000");
		$(this).click(function(){
			$(".radioNew").css("background-position-y","bottom");
			$(this).prev()[0].click();
			if($(this).prev()[0].checked){
				$(this).css("background-position-y","top");
			}
		});
		if($(this).prev()[0].checked){
			$(this).css("background-position-y","top");
		}
	});
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询已认领表字段表列表"/>
<freeze:errors/>

<freeze:form action="/txn80002501">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
  	 <freeze:hidden property="sys_rd_table_id" caption="已认领表id" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_code" caption="物理表" datatype="string" maxlength="100" style="width:95%" readonly="true"/>
      <freeze:text property="table_name" caption="物理表中文名" datatype="string" maxlength="100" style="width:95%" readonly="true"/>
      <freeze:text property="column_code" caption="字段名" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="column_name" caption="字段中文名" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:select property="column_type" caption="字段类型" valueset="字段数据类型"  style="width:95%"/>
      <freeze:browsebox property="column_codeindex" caption="系统代码集名称" show ="mix" valueset="取系统代码集" style="width:95%"/>
  </freeze:block>
	<br />
  <freeze:grid property="record" caption="字段列表" keylist="sys_rd_column_id" width="95%" navbar="bottom" fixrow="false" multiselect="false" onclick="func_record_viewRecord();">
    <freeze:button name="record_deleteRecord" caption="删除" txncode="80002505" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();" visible="false"/>
      <freeze:button name="record_viewRecord" caption="查看" txncode="80002206" enablerule="1" hotkey="" align="right" onclick="func_record_viewRecord();" visible="false"/>
      <freeze:button name="record_addRecord" caption="增加已认领表字段表" txncode="80002503" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改" txncode="80002504" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_goBack" caption="返回" hotkey="CLOSE" align="right" onclick="func_record_goBack();"/>
     
      <freeze:cell property="column_code" caption="字段名" style="width:20%" />
      <freeze:cell property="column_name" caption="字段中文名" style="width:15%" />
      <freeze:cell property="column_type" caption="字段类型" valueset="字段数据类型" style="width:10%" />
      <freeze:cell property="column_length" caption="字段长度" style="width:8%" />
      <freeze:cell property="column_codeindex" caption="系统代码集名称" style="width:15%" />
      
      <freeze:cell property="use_type" caption="是否有效" valueset="布尔型数" style="width:8%" />
      
      <freeze:hidden property="sys_rd_column_id" caption="字段ID" style="width:10%" />
      <freeze:hidden property="sys_rd_table_id" caption="物理表ID" style="width:12%" />
      <freeze:hidden property="sys_rd_data_source_id" caption="数据源ID" style="width:12%" />
      <freeze:hidden property="table_code"  caption="物理表名" style="width:20%" />
      <freeze:hidden property="alia_name" caption="字段别名" style="width:20%" />
      <freeze:hidden property="code_name" caption="系统代码名" style="width:20%" />
      <freeze:hidden property="standard_status" caption="合标状态" style="width:10%" />
      <freeze:hidden property="is_extra_col" caption="是否为必查字段" style="width:10%" />
      <freeze:hidden property="sys_column_type" caption="字段类型" style="width:10%" />
      <freeze:hidden property="jc_data_element" caption="基础数据元" style="width:10%" />
      <freeze:hidden property="jc_data_index" caption="基础代码集" style="width:10%" />
      <freeze:hidden property="domain_value" caption="值域" style="width:10%" />
      <freeze:hidden property="unit" caption="计量单位" style="width:12%" />
      <freeze:hidden property="claim_operator" caption="认领人" style="width:12%" />
      <freeze:hidden property="claim_date" caption="认领时间" style="width:10%" />
      <freeze:hidden property="default_value" caption="缺省值" style="width:16%" />
      <freeze:hidden property="is_null" caption="是否为空" style="width:10%" />
      <freeze:hidden property="is_primary_key" caption="是否主键" style="width:10%" />
      <freeze:hidden property="is_index" caption="是否索引" style="width:10%" />
      <freeze:hidden property="changed_status" caption="字段变化状态" style="width:10%" />
      <freeze:hidden property="sync_sign" caption="同步标识串" style="width:12%" />
      <freeze:hidden property="description" caption="说明" style="width:20%" visible="false" />
      <freeze:hidden property="column_level" caption="字段级别" style="width:10%" />
      <freeze:hidden property="basic_flag" caption="基础字段标识" style="width:16%" />
      <freeze:hidden property="data_from" caption="数据来源" style="width:16%" />
      <freeze:hidden property="data_from_column" caption="数据来源字段" style="width:16%" />
      <freeze:hidden property="transform_desp" caption="数据转换描述" style="width:20%" />
      <freeze:hidden property="sort" caption="排序" style="width:10%" />
      <freeze:hidden property="timestamp" caption="时间戳" style="width:12%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
