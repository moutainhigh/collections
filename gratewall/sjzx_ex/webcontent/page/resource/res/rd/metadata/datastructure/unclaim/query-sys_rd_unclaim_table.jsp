<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询未认领表列表</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// 增加未认领表
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_rd_unclaim_table.jsp", "增加未认领表", "modal" );
	page.addRecord();
}

// 修改未认领表
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn80002107.do", "修改未认领表", "modal" );
	page.addParameter( "record:sys_rd_unclaim_table_id", "record:sys_rd_unclaim_table_id" );
	page.addParameter( "record:unclaim_table_code" ,"record:unclaim_table_code");
	page.addParameter( "record:db_name" ,"record:db_name");
	page.updateRecord();
}

// 删除未认领表
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn80002105.do", "删除未认领表" );
	page.addParameter( "record:sys_rd_unclaim_table_id", "primary-key:sys_rd_unclaim_table_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

function func_record_viewRecord_button()
{
	var page = new pageDefine( "/txn80002401.do", "查询未认领表字段列表");
	page.addParameter("record:sys_rd_unclaim_table_id","select-key:sys_rd_unclaim_table_id");
	page.addParameter("record:unclaim_table_code","select-key:unclaim_tab_code");
	page.goPage();	
}

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
	
	var page = new pageDefine( "/txn80002401.do", "查询未认领表字段列表");
	page.addParameter("record:sys_rd_unclaim_table_id","select-key:sys_rd_unclaim_table_id");
	page.addParameter("record:unclaim_table_code","select-key:unclaim_tab_code");
	page.goPage();	
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
<freeze:title caption="查询未认领表列表"/>
<freeze:errors/>

<freeze:form action="/txn80002101">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:select property="sys_rd_data_source_id" caption="数据源名称" valueset="取数据源"  style="width:90%"/>
      <freeze:text property="object_schema" caption="用户" datatype="string" maxlength="36" style="width:90%"/>
      <freeze:text property="unclaim_table_code" caption="物理表" datatype="string" maxlength="100" style="width:90%"/>
      <freeze:text property="unclaim_table_name" caption="说明" datatype="string" maxlength="100" style="width:90%"/>
      
  </freeze:block>
<br>
  <freeze:grid property="record" caption="查询未认领表列表" keylist="sys_rd_unclaim_table_id" width="95%" navbar="bottom" fixrow="false" onclick="func_record_viewRecord()"  multiselect="false" >
    
      <freeze:button name="record_updateRecord" caption="认领" txncode="80002107" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_viewRecord" caption="查看"  txncode="80002401" enablerule="1" hotkey="VIEW" icon="/script/button-icon/icon_view.gif" align="right" onclick="func_record_viewRecord_button();" visible="false"/>
      <freeze:hidden property="sys_rd_unclaim_table_id" caption="未认领表ID" style="width:12%" />
      <freeze:hidden property="sys_rd_data_source_id" caption="数据源ID" style="width:12%" />
      <freeze:cell property="db_name" caption="数据源名称" style="width:12%" />
      <freeze:cell property="object_schema" caption="用户" style="width:16%" />
      <freeze:cell property="unclaim_table_code" caption="物理表" style="width:20%" />
      <freeze:cell property="unclaim_table_name" caption="说明" style="width:20%" />    
      <freeze:cell property="cur_record_count" caption="数据量" style="width:10%" visible="false"/>
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>