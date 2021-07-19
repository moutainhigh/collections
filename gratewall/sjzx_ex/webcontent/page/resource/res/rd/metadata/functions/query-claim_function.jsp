<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="850" height="350">
<head>
<title>已函数列表</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn80004204.do", "修改函数", "modal", 700, 600 );
	page.addParameter("record:sys_rd_claimed_view_id","primary-key:sys_rd_claimed_view_id");
	page.updateRecord();
}


// 查看内容
function func_record_viewRecord()
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
	var page = new pageDefine( "/txn80004205.do", "查看", "modal" );
	page.addParameter("record:sys_rd_claimed_view_id","primary-key:sys_rd_claimed_view_id ");
	page.viewRecord();
}

// 删除表
function func_record_deleteRecord()
{
    var page = new pageDefine( "/txn80004203.do", "删除函数" );
    page.addParameter("record:sys_rd_claimed_view_id","primary-key:sys_rd_claimed_view_id ");
    page.deleteRecord( "是否删除选中的记录" );
}

//返 回
function func_record_goBackNoUpdate()
{
	goBackNoUpdate();
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
	$(".grid-headrow td:eq(0)").css("width","30");
}


_browse.execute( __userInitPage );
</script>
<freeze:body>
<freeze:title caption="函数列表"/>
<freeze:errors/>

<freeze:form action="/txn80004201">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
  	  <freeze:hidden property="sys_rd_claimed_view_id" caption="表主键" datatype="string" style="width:95%"/>	
  	  <freeze:select property="sys_rd_data_source_id" caption="数据源名称" valueset="取数据源"  style="width:90%"/>	
      <freeze:text property="view_name" caption="函数名称" datatype="string" maxlength="100" style="width:90%"/>
  </freeze:block>
  <br />

  <freeze:grid property="record" caption="函数列表" nowrap="true" keylist="sys_rd_claimed_view_id" width="95%" navbar="bottom" fixrow="false" multiselect="false" onclick="func_record_viewRecord();">
      <freeze:button name="record_updateRecord" caption="修改" txncode="80004202" enablerule="1" hotkey="UPDATE" icon="/script/button-icon/icon_update.gif" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除" txncode="80004203" enablerule="2" hotkey="DELETE" icon="/script/button-icon/icon_delete.gif" align="right" onclick="func_record_deleteRecord();" value="" visible="false"/>
      <freeze:button name="record_viewRecord" caption="查看" txncode="80004205" enablerule="1" hotkey="VIEW" icon="/script/button-icon/icon_view.gif" align="right" onclick="func_record_viewRecord();" visible="false"/>
      
      <freeze:hidden property="sys_rd_claimed_view_id" caption="表主键"/>
      <freeze:hidden property="sys_rd_data_source_id" caption="数据源ID"/>
      <freeze:cell property="view_name" caption="函数名称" style="width:25%" />
      <freeze:cell property="view_use" caption="函数用途" style="width:35%" />
      <freeze:cell property="claim_operator" caption="认领人" valueset="用户姓名" style="width:20%" />
      <freeze:cell property="claim_date" caption="认领时间" style="width:20%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
