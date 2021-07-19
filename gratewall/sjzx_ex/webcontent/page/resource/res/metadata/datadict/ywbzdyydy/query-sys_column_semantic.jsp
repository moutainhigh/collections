<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询业务字段列表</title>
</head>

<script language="javascript">

// 增加业务字段
function func_record_addRecord()
{
	var page = new pageDefine( "<%=request.getContextPath()%>/dw/metadata/datadict/ywbzdyydy/insert-sys_column_semantic.jsp", "增加业务字段", "modal" );
	page.addRecord();
}

// 修改业务字段
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn30403004.do", "修改业务字段", "modal" );
	page.addParameter( "record:column_no", "primary-key:column_no" );
	page.updateRecord();
}

// 删除业务字段
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn30403005.do", "删除业务字段" );
	page.addParameter( "record:column_no", "primary-key:column_no" );
	page.addParameter( "record:column_name", "primary-key:column_name" );
	page.deleteRecord( "是否删除选中的记录" );
}

function getParameter()
{
	var sys_id = getFormFieldValue("select-key:sys_id");
	if (sys_id != "")
	{
		var parameter = 'sys_id=' + sys_id;
		return parameter;
	}else{
	    alert("请选择业务主题");
	    return;
	}
}

function resetTableNo()
{
	if (getFormFieldValue("select-key:table_no") == null || getFormFieldValue("select-key:table_no") == "")
	{
	}
	else{
		setFormFieldValue("select-key:table_no","")
	}
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var operationSpan = document.getElementsByName("span_record:operation");
	for (var i=0; i < operationSpan.length; i++){
		operationSpan[i].innerHTML = "<a onclick='setCurrentRowChecked(\"record\");func_record_updateRecord();' href='#'>修改</a>";
	}	
	var node = top.menu.lookupItem("30413000");
	if (node){
		top.menu.setSelectedNode(node);
	}
	node = null;	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询业务字段列表"/>
<freeze:errors/>

<freeze:form action="/txn30403001">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:browsebox property="sys_id" caption="业务主题" valueset="业务系统" show="name" style="width:95%" data="code" onchange="resetTableNo()"/>
      <freeze:browsebox property="table_no" caption="表中文名" valueset="业务表代码对照表" show="name" data="code" style="width:95%" parameter="getParameter()"/>
      <freeze:text property="column_name" caption="业务字段名" datatype="string" maxlength="60" style="width:95%"/>
      <freeze:text property="column_name_cn" caption="业务字段中文名" datatype="string" maxlength="100" style="width:95%"/>
  </freeze:block>
  <br> 
  <freeze:grid property="record" caption="业务字段列表" keylist="column_no" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="增加" txncode="30403003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除" txncode="30403005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:hidden property="column_no" caption="字段编码" style="width:8%" />
      <freeze:cell property="sys_name" caption="业务主题" style="width:12%" />
      <freeze:cell property="table_name_cn" caption="业务表" style="width:22%" />
      <freeze:cell property="column_name_cn" caption="业务字段中文名" style="width:12%" />
      <freeze:cell property="column_name" caption="业务字段名" style="width:23%" />      
      <freeze:cell property="edit_type" caption="字段类型" valueset="字段类型" style="width:10%" />
      <freeze:cell property="edit_content" caption="字段长度" style="width:10%" />
      <freeze:cell property="demo" caption="备注" style="width:10%" />
      <freeze:cell property="operation" caption="操作" align="center" style="width:5%" />
      <freeze:hidden property="sys_order" caption="检索顺序" style="width:20%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
