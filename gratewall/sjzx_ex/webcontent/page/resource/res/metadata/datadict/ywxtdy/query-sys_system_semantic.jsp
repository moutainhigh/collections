<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询主题列表</title>
</head>

<script language="javascript">

// 增加系统
function func_record_addRecord()
{
	var page = new pageDefine( "<%=request.getContextPath()%>/dw/metadata/datadict/ywxtdy/insert-sys_system_semantic.jsp", "增加主题", "modal" );
	page.addRecord();
}

// 修改系统
function func_record_updateRecord(index)
{
    document.getElementsByName("record:_flag")[index].checked = false;
	var page = new pageDefine( "/txn30401004.do", "修改主题", "modal" );	
	var sys_id = getFormFieldValue("record:sys_id", index);
	page.addValue( sys_id, "primary-key:sys_id" );
	page.updateRecord();
}

// 删除系统
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn30401005.do", "删除主题" );
	page.addParameter( "record:sys_id", "primary-key:sys_id" );
	page.addParameter( "record:sys_name", "primary-key:sys_name" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var operationSpan = document.getElementsByName("span_record:operation");
	for (var i=0; i < operationSpan.length; i++){
		operationSpan[i].innerHTML = "<a onclick='func_record_updateRecord("+i+");' href='#'>修改</a>";
	}	
	
}

function funcQueryInfo(){    
	if (event.srcElement.tagName.toUpperCase() == 'INPUT'){
		return;
	}
	if (event.srcElement.tagName.toUpperCase() == 'A'){
		return;
	}	
	var index = getSelectedRowid("record");
	var sys_id = getFormFieldText("record:sys_id",index);  
	var page = new pageDefine("/txn30402001.do", "详细信息");	
	page.addValue( sys_id, "select-key:sys_id" );
	page.goPage();	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询主题列表"/>
<freeze:errors/>

<freeze:form action="/txn30401001">
  <freeze:grid property="record" caption="主题列表" keylist="sys_id" width="95%" navbar="bottom" fixrow="false" rowselect="false" onclick="funcQueryInfo();" >
      <freeze:button name="record_addRecord" caption="增加" txncode="30401003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除" txncode="30401005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:hidden property="sys_id" caption="主题ID" style="width:15%" />
      <freeze:cell property="sys_name" caption="主题名称" style="width:48%" />
      <freeze:cell property="sys_simple" caption="备注" style="width:47%" />
      <freeze:hidden property="sys_order" caption="检索顺序" style="width:20%" />
      <freeze:cell property="operation" caption="操作" align="center" style="width:5%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
