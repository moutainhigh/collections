<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="850" height="350">
<head>
<title>未认领存储过程</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">
function func_record_addRecord()
{
	var page = new pageDefine( "/txn80005102.do", "存储过程", "modal", 800, 500 );
	page.addParameter("record:sys_rd_unclaim_table_id","select-key:sys_rd_unclaim_table_id ");
	//var page = new pageDefine( "../../../ysjgl/xtstgl/insert-unclaim_view.jsp", "存储过程", "modal", 800, 500 );
	//page.callback = rlCallback;
	//page.updateRecord(null, _gridRecord);
	page.updateRecord();
}
function rlCallback(successNumber, responses, requests, nodeName){
	if(successNumber>0){
		_gridRecord.deleteSelectedRow();
	}
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
	var page = new pageDefine( "/txn80005104.do", "查看未存储过程信息","modal");
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


_browse.execute( __userInitPage );
</script>
<freeze:body>
<freeze:title caption="存储过程列表"/>
<freeze:errors/>

<freeze:form action="/txn80005101">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
 	  <freeze:select property="sys_rd_data_source_id" caption="数据源名称" valueset="取数据源"  style="width:90%"/>
      <freeze:text property="object_schema" caption="用户" datatype="string" maxlength="50" style="width:90%"/>
      <freeze:text property="unclaim_table_code" caption="存储过程名称" datatype="string" maxlength="200" style="width:36.5%" colspan="2" />
  </freeze:block>
  <br />
  <freeze:grid property="record" caption="存储过程列表" keylist="sys_rd_unclaim_table_id" width="95%" navbar="bottom" fixrow="false" onclick="func_record_viewRecord()" multiselect="false">
      <freeze:button name="record_addRecord" caption="认领" txncode="80005103" enablerule="1" hotkey="ADD" icon="/script/button-icon/icon_add.gif" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_viewRecord" caption="查看" txncode="80005104" enablerule="1" hotkey="VIEW" icon="/script/button-icon/icon_view.gif" align="right" onclick="func_record_viewRecord();" visible="false"/>
      <freeze:cell property="sys_rd_unclaim_table_id" caption="主键" style="width:10%" visible="false"/>
      <freeze:cell property="db_name" caption="数据源名称" style="width:10%"/>
      <freeze:cell property="object_schema" caption="用户" style="width:10%"/>
      <freeze:cell property="unclaim_table_code" caption="存储过程名称" style="width:15%" />
      <freeze:cell property="remark" caption="存储过程用途" style="width:15%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
