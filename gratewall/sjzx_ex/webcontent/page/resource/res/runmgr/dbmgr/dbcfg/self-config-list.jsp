<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>灵活配置列表</title>
<style type="text/css">
.activerow {
	cursor:auto;
}
#navbar{
	display:none;
}
</style>

</head>

<script language="javascript">

// 增加服务配置
function func_record_addRecord()
{
	var page = new pageDefine( "self-config.jsp", "增加视图配置");
	var win = window.frames('ajlist');
  	document.getElementById("ajlist").style.display = "block";
	page.goPage( null, win );
	clickFlag = 0;
}
function funcViewDetail(index)
{
    var configId = getFormFieldValue("record:sys_db_config_id",index);
	var page = new pageDefine( "self-config.jsp", "修改视图配置");
    var win = window.frames('ajlist');
  	document.getElementById("ajlist").style.display = "block";
	page.addValue("update", "update");	
	page.addValue(configId, "configId");
	page.goPage( null, win );
}

function func_record_deleteRecord(){
    var configIds = getFormFieldValues( "record:sys_db_config_id" );
    var configNames = getFormFieldValues( "record:config_name" );
    var table_ids = getFormFieldValues( "record:permit_column" );
	var page = new pageDefine( "txn52103017.do", "删除灵活配置信息" );
	page.addParameter( "select-key:sys_db_user_id", "record:sys_db_user_id" );
	page.addParameter( "select-key:login_name", "record:login_name" );
	page.addParameter("select-key:user_type","record:user_type");
	page.addParameter("select-key:grant_table","record:grant_table");
	page.addValue(configIds,"record:sys_db_config_id");
	page.addValue(configNames,"record:config_name");
	page.addValue(table_ids,"record:old_table");
	page.deleteRecord( "是否删除选中的记录?" );
}
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{	
    var operationSpan = document.getElementsByName("span_record:oper");
	for (var i=0; i < operationSpan.length; i++){
	    operationSpan[i].innerHTML += "<a onclick='funcViewDetail(" + i + ");' href='#'>修改</a>";
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="灵活配置列表"/>
<freeze:errors/>

<freeze:form action="/txn52103011">
  <freeze:frame property="select-key" width="95%">
      <freeze:hidden property="sys_db_user_id" caption="sys_db_user_id"/>
      <freeze:hidden property="login_name" caption="login_name"/>
      <freeze:hidden property="user_name" caption="user_name"/>
      <freeze:hidden property="user_type" caption="user_type"/>
      <freeze:hidden property="grant_table" caption="grant_table"/>
  </freeze:frame>
  <freeze:grid property="record" caption="灵活配置列表" keylist="sys_db_config_id" checkbox="true" navbar="bottom" multiselect="true" width="95%" fixrow="false" rowselect="false">
      <freeze:button name="record_addRecord" caption="增加"  enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除"  enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:button name="record_returnRecord" caption="返回"  enablerule="0" align="right" onclick="goBack();"/>
      <freeze:hidden property="sys_db_config_id" caption="视图配置编码" />
      <freeze:hidden property="sys_db_user_id" caption="用户编码" />
      <freeze:hidden property="config_name" caption="config_name" />
      <freeze:hidden property="permit_column" caption="permit_column" />
      <freeze:cell property="@rowid" caption="序号" style="width:6%" align="center"/>
      <freeze:cell property="config_name" caption="配置名称" style="width:10%" align="left" />
      <freeze:cell property="alias_column" caption="授权表" style="width:14%" align="left" />
      <freeze:cell property="query_sql" caption="视图查询sql" style="width:66%" align="left" />
      <freeze:cell property="oper" caption="操作" style="width:4%" align="center"/>
  </freeze:grid>
  <br>
  <iframe name='ajlist' scrolling='no' frameborder='0' align="center" width='95%' style="display:none"></iframe>
</freeze:form>
</freeze:body>
</freeze:html>
