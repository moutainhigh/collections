<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询业务表列表</title>
</head>

<script language="javascript">

// 增加业务表
function func_record_addRecord()
{
	var page = new pageDefine( "<%=request.getContextPath()%>/dw/metadata/datadict/ywbyydy/insert-sys_table_semantic.jsp", "增加业务表", "modal" );
	page.addRecord();
}

// 修改业务表
function func_record_updateRecord(index)
{
	
    document.getElementsByName("record:_flag")[index].checked = false;
	var table_no = getFormFieldValue("record:table_no", index);	
	var page = new pageDefine( "/txn30402004.do", "修改业务表", "modal" );
	page.addValue( table_no, "primary-key:table_no" );
	page.updateRecord();
			
}

// 删除业务表
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn30402005.do", "删除业务表" );
	page.addParameter( "record:table_no", "primary-key:table_no" );
	page.addParameter( "record:table_name", "primary-key:table_name" );
	page.deleteRecord( "是否删除选中的记录" );
}
function getParameter()
{
	var sys_id = getFormFieldValue("select-key:sys_id");
	if (sys_id != "")
	{
		var parameter = 'sys_id=' + sys_id;
		return parameter;
	}
}
// 请在这里添加，页面加载完成后的用户初始化操作 
function __userInitPage()
{
	var operationSpan = document.getElementsByName("span_record:operation");
	for (var i=0; i < operationSpan.length; i++){
		operationSpan[i].innerHTML = "<a onclick='func_record_updateRecord("+i+");' href='#'>修改</a>";
	}
	
	var node = top.menu.lookupItem("30412000");
	if (node){
		top.menu.setSelectedNode(node);
	}
	node = null;
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
	var table_no = getFormFieldText("record:table_no",index);  
	var page = new pageDefine("/txn30403001.do", "详细信息");	
	page.addValue( sys_id, "select-key:sys_id" );
	page.addValue( table_no, "select-key:table_no" );
	page.goPage();
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询业务表列表"/>
<freeze:errors/>

<freeze:form action="/txn30402001">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:browsebox property="sys_id" caption="业务主题" valueset="业务系统" data="code" show="name" maxlength="2" style="width:95%"/>
      <freeze:browsebox property="table_name_cn" caption="业务表中文名" valueset="业务表代码对照表" show="name" data="name" style="width:95%" parameter="getParameter()"/>
      <freeze:text property="table_name" caption="业务表名" datatype="string" maxlength="100" style="width:95%"/>
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="业务表列表" keylist="table_no" width="95%" navbar="bottom" rowselect="false" fixrow="fasle"  onclick="funcQueryInfo();">
      <freeze:button name="record_addRecord" caption="增加" txncode="30402003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除" txncode="30402005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:hidden property="table_no" caption="业务表编码" style="width:15%" />
      <freeze:hidden property="sys_id" caption="业务主题"/>
      <freeze:cell property="sys_id" caption="业务主题" style="width:20%" valueset="业务系统"/>
      <freeze:cell property="table_name_cn" caption="业务表中文名" style="width:17%" />
      <freeze:cell property="table_name" caption="业务表名" style="width:18%" />
      <freeze:cell property="demo" caption="备注" style="width:15%" />   
      <freeze:hidden property="table_order" caption="检索顺序" style="width:10%" />   
      <freeze:cell property="downloadflag" caption="是否可下载" valueset="布尔型数" style="width:10%" />
      <freeze:cell property="operation" caption="操作" align="center" style="width:5%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
