<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:include href="/script/lib/jquery162.js"/>
<freeze:html width="650" height="350">
<head>
<title>查询已认领列表</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// 增加已认领
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_rd_table.jsp", "增加已认领", "modal" );
	page.addRecord();
}

// 修改已认领
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn80002204.do", "修改已认领", "modal" );
	page.addParameter( "record:sys_rd_table_id", "primary-key:sys_rd_table_id" );
	page.updateRecord();
}

// 删除已认领
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn80002205.do", "删除已认领" );
	page.addParameter( "record:sys_rd_table_id", "primary-key:sys_rd_table_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 查询已认领
function func_record_viewRecord()
{
	var page = new pageDefine( "/txn80002206.do", "查看已认领", "modal" );
	page.addParameter( "record:sys_rd_table_id", "primary-key:sys_rd_table_id" );
	page.updateRecord();
}

// 字段管理
function func_record_columnRecord()
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
	var page = new pageDefine( "/txn80002501.do", "查看已认领" );
	page.addParameter( "record:sys_rd_table_id", "select-key:sys_rd_table_id" );
	page.addParameter( "record:table_code", "select-key:table_code" );
	page.addParameter( "record:table_name", "select-key:table_name" );
	page.updateRecord();
}

// 字段管理
function func_record_columnRecord_button()
{
	
	var page = new pageDefine( "/txn80002501.do", "查看已认领" );
	page.addParameter( "record:sys_rd_table_id", "select-key:sys_rd_table_id" );
	page.addParameter( "record:table_code", "select-key:table_code" );
	page.addParameter( "record:table_name", "select-key:table_name" );
	page.updateRecord();
}

// 维护表间关系
function func_record_editRelation()
{
	
	var page = new pageDefine( "/txn80002606.do", "维护表间关系" );
	page.addParameter( "record:sys_rd_table_id", "select-key:sys_rd_table_id" );
	page.addParameter( "record:sys_rd_data_source_id", "select-key:sys_rd_data_source_id" );
	page.addParameter( "record:sys_rd_system_id", "select-key:sys_rd_system_id" );
	page.addParameter( "record:table_name", "select-key:table_name" );
    page.addParameter( "record:table_code", "select-key:table_code" );
	page.goPage();
}


// 返 回
function func_record_goBack()
{
	goBack();	// 
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
<freeze:title caption="查询已认领表"/>
<freeze:errors/>

<freeze:form action="/txn80002208">
   <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:select property="sys_rd_data_source_id" caption="数据源名称" show="name" valueset="取数据源"  style="width:90%"  />
      <freeze:select property="sys_rd_system_id" caption="主题名称" valueset="业务主题"   style="width:90%"/>
      <freeze:text property="table_code" caption="物理表" datatype="string" maxlength="100" style="width:90%"/>
      <freeze:text property="table_name" caption="物理表中文名" datatype="string" maxlength="100" style="width:90%"/>
      <freeze:select property="table_type" caption="表类型" valueset="表类型" colspan="2" style="width:36.5%"/>
      
  </freeze:block>
<br />
  <freeze:grid property="record" caption="查询已认领列表" keylist="sys_rd_table_id" width="95%" navbar="bottom"  fixrow="false" multiselect="false"  >
      <freeze:button name="record_addRecord" caption="增加已认领" txncode="80002203" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();" visible="false"/>
      <freeze:button name="record_updateRecord" caption="修改" txncode="80002204" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();" visible="false"/>
      <freeze:button name="record_deleteRecord" caption="删除" txncode="80002205" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();" visible="false"/>
      <freeze:button name="record_viewRecord" caption="查看" txncode="80002206" enablerule="1" hotkey="" align="right" onclick="func_record_viewRecord();" visible="false"/>
      <freeze:button name="record_viewRecord" caption="字段管理" txncode="80002207" enablerule="1" hotkey="" align="right" onclick="func_record_columnRecord_button();" visible="false"/>
      <freeze:button name="record_editRelation" caption="维护表关系" txncode="80002606" enablerule="1" hotkey="" align="right" onclick="func_record_editRelation();"/>
      <freeze:button name="record_goBack" caption="返 回"  hotkey="CLOSE" align="right" onclick="func_record_goBack();" visible="false"/>
      
      
      <freeze:cell property="table_code" caption="物理表" style="width:24%" ondblclick="func_record_columnRecord();"  />
      <freeze:link property="table_name" caption="物理表中文名" style="width:20%" ondblclick="func_record_columnRecord();"/>
      <freeze:cell property="table_type" caption="表类型" valueset="表类型" style="width:10%" ondblclick="func_record_columnRecord();" />
      <freeze:cell property="sys_rd_system_id" caption="主题名称" valueset="业务主题" style="width:16%" ondblclick="func_record_columnRecord();" />
      
      <freeze:cell property="claim_operator" caption="认领人" style="width:12%" />
      <freeze:cell property="claim_date" caption="认领日期" style="width:10%" />
      
      <freeze:hidden property="table_no" caption="数据表编号" style="width:12%" />
      <freeze:hidden property="table_sql" caption="数据表sql" style="width:20%"  />
      <freeze:hidden property="table_sort" caption="排序字段" style="width:20%" />
      <freeze:hidden property="table_dist" caption="区县字段" style="width:20%" />
      <freeze:hidden property="table_time" caption="时间字段" style="width:20%" />
      <freeze:hidden property="parent_table" caption="父表名" style="width:20%" />
      <freeze:hidden property="parent_pk" caption="父表主键名" style="width:20%" />
      <freeze:hidden property="table_fk" caption="与父表关联列名" style="width:20%" />
      <freeze:hidden property="first_record_count" caption="初期数据量" style="width:10%" />
      <freeze:hidden property="last_record_count" caption="最后一次同步数据量" style="width:10%" />
      <freeze:hidden property="sys_rd_table_id" caption="数据表ID" style="width:10%" />
      <freeze:hidden property="sys_rd_system_id" caption="业务主体ID" style="width:12%" />
      <freeze:hidden property="sys_no" caption="业务主体编号" style="width:12%" />
      <freeze:hidden property="sys_rd_data_source_id" caption="数据源ID" style="width:12%" />
      <freeze:hidden property="table_primary_key" caption="表主键" style="width:20%" />
      <freeze:hidden property="table_index" caption="表索引" style="width:20%" />
      <freeze:hidden property="table_use" caption="用途" style="width:20%" />
      <freeze:hidden property="gen_code_column" caption="总局代码字段" style="width:12%" />
      <freeze:hidden property="prov_code_column" caption="省局代码字段" style="width:12%" />
      <freeze:hidden property="city_code_column" caption="市局代码字段" style="width:12%" />
      <freeze:hidden property="content" caption="代码字段内容" style="width:12%" />
      <freeze:hidden property="changed_status" caption="变化状态" style="width:10%" />
      <freeze:hidden property="object_schema" caption="表模式" style="width:16%" />
      <freeze:hidden property="memo" caption="备注" style="width:20%"  />
      <freeze:hidden property="is_query" caption="是否可查询" style="width:10%" />
      <freeze:hidden property="is_trans" caption="是否可共享" style="width:10%" />
      <freeze:hidden property="is_download" caption="是否可下载" style="width:10%" />
      <freeze:hidden property="sort" caption="排序" style="width:10%" />
      <freeze:hidden property="timestamp" caption="时间戳" style="width:12%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
