<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询变更记录表列表</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// 增加变更记录表
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_rd_change.jsp", "增加变更记录表", "modal" );
	page.addRecord();
}

// 修改变更记录表
function func_record_updateRecord()
{
	var result =getFormFieldValues("record:change_result");

	if(result!="1"){
		var page = new pageDefine( "/txn80002304.do", "修改变更记录表", "modal" );
		page.addParameter( "record:sys_rd_change_id", "record:sys_rd_change_id" );
		page.addParameter( "record:sys_rd_table_id", "record:sys_rd_table_id" );
		page.updateRecord();
	}else
	{
		alert("请选择【未处理】记录进行认领变更操作！");
		_formSubmit( null, '正在处理记录 ... ...');

	}
	
}

// 删除变更记录表
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn80002305.do", "删除变更记录表" );
	page.addParameter( "record:sys_rd_change_id", "primary-key:sys_rd_change_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 查看变更详情
function func_record_viewRecord()
{
	
	var page = new pageDefine( "/txn80002306.do", "变更详情", "modal" );
	page.addParameter( "record:sys_rd_change_id", "primary-key:sys_rd_change_id" );
	page.updateRecord();
}


// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	$(".radioNew").each(function(index){
		if(index<2){
			$(this).click(function(){
				$(".radioNew:lt(2)").css("background-position-y","bottom");
				$(this).css("background-position-y","top");
				$(this).prev()[0].click();
			});
			$(this).next()[0].onclick=function(){
				$(this).prev()[0].click();
				$(".radioNew:lt(2)").css("background-position-y","bottom");
				$($(".radioNew")[index]).css("background-position-y","top");
			};
		}else{
			$(this).click(function(){
				$(".radioNew:gt(1)").css("background-position-y","bottom");
				$(this).css("background-position-y","top");
				$(this).prev()[0].click();
			});
		}
	});
	for(var ii=0;ii<2;ii++){
		if($($(".radioNew")[ii]).prev()[0].checked){
			$($(".radioNew")[ii]).css("background-position-y","top");
		}
	}
	//document.getElementById('label:select-key:change_result').innerHTML='处理结果：';
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询变更记录列表"/>
<freeze:errors/>

<freeze:form action="/txn80002301">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:hidden property="sys_rd_change_id" caption="表主键" datatype="string" maxlength="32" style="width:90%"/>
      <freeze:text property="table_name" caption="物理表" datatype="string" maxlength="100" style="width:90%"/>
      <freeze:text property="column_name" caption="字段" datatype="string" maxlength="100" style="width:90%"/>
      <freeze:select property="change_item" caption="变更类型" valueset="变更类型"  style="width:90%"/>
      <freeze:radio property="change_result" caption="处理结果"  valueset="处理结果" style="width:90%"/>
  </freeze:block>
<br />
  <freeze:grid property="record" caption="查询变更记录列表" keylist="change_result" width="95%" navbar="bottom" rowselect="false" fixrow="false" multiselect="false">
      <freeze:button name="record_addRecord" caption="增加变更记录表" txncode="80002303" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();" visible="false"/>
      <freeze:button name="record_updateRecord" caption="认领变更" txncode="80002304" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_viewRecord" caption="变更详情"   txncode="80002306" enablerule="1" hotkey="VIEW" align="right" onclick="func_record_viewRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除变更记录表" txncode="80002305" enablerule="1" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      
      
      
      <freeze:cell property="db_name" caption="数据源名称" style="width:10%" />
      <freeze:cell property="table_name" caption="物理表" style="width:15%" />
      <freeze:cell property="column_name" caption="字段" style="width:15%" />
      <freeze:cell property="change_item" caption="变更类型" valueset="变更类型" style="width:10%" />
      <freeze:cell property="change_before" caption="变更前内容" valueset="字段数据类型" style="width:20%" />
      <freeze:cell property="change_after" caption="变更后内容" valueset="字段数据类型" style="width:20%" />
      <freeze:cell property="change_result" caption="处理结果" valueset="处理结果" style="width:10%" />
      
      <freeze:hidden property="sys_rd_change_id" caption="表主键" style="width:10%" />
      <freeze:hidden property="sys_rd_data_source_id" caption="数据源ID" style="width:12%" />
      <freeze:hidden property="db_username" caption="用户名称" style="width:20%" />
      <freeze:hidden property="table_name_cn" caption="物理表中文名" style="width:20%" />
      <freeze:hidden property="column_name_cn" caption="字段中文名" style="width:20%" />
      <freeze:hidden property="change_oprater" caption="变更人" style="width:20%" />
      <freeze:hidden property="change_time" caption="变更时间" style="width:12%" />
      <freeze:hidden property="change_reason" caption="变更原因" style="width:20%" />
      <freeze:hidden property="timestamp" caption="时间戳" style="width:12%" />
      
      <freeze:hidden property="sys_rd_table_id" caption="已认领表ID" style="width:10%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
