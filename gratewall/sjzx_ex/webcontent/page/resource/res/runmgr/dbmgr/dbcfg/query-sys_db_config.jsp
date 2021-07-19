<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>视图配置列表</title>
<style type="text/css">
.activerow {
	cursor:auto;
}
</style>

</head>

<script language="javascript">

// 增加服务配置
function func_record_addRecord()
{
	var page = new pageDefine( "main-config.jsp", "增加视图配置");
	page.addRecord();
}
// 删除数据库共享对象
function func_record_deleteRecord(index)
{
    var config_type = getFormFieldValue("record:config_type",index);
	var page = new pageDefine( "txn52103009.do", "选择删除数据库视图" );
	page.addValue( getFormFieldValue("record:sys_db_config_id",index), "record:sys_db_config_id" );
	page.addValue( getFormFieldValue("record:sys_db_user_id",index), "record:sys_db_user_id" );
	page.addValue( getFormFieldValue("record:config_name",index), "record:config_name" );
	page.addValue( getFormFieldValue("record:login_name",index), "record:login_name" );
	page.addValue( getFormFieldValue("record:grant_table",index), "record:grant_table" );
	if(config_type=='01'){
		page.addValue( getFormFieldValue("record:table_no",index), "record:old_table" );
	}else{
		page.addValue( getFormFieldValue("record:permit_column",index), "record:old_table" );
	}
	page.addValue( getFormFieldValue("record:user_type",index), "record:user_type" );
	page.addValue( config_type,"record:config_type");
	page.deleteRecord("确认删除配置吗？");
}
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
    var operationSpan = document.getElementsByName("span_record:oper");
	for (var i=0; i < operationSpan.length; i++){
	    operationSpan[i].innerHTML += "<a onclick='func_record_deleteRecord(" + i + ");' href='#'>删除</a>&nbsp;&nbsp;<a onclick='func_viewLog(" + i + ");' href='#'>查看日志</a>";
	}
}

function func_viewLog(index){
    var login_name = getFormFieldValue("record:login_name",index);
    var config_name = getFormFieldValue("record:config_name",index);
    var user_type = getFormFieldValue("record:user_type",index);
    
	var page = new pageDefine( "/txn52103008.do", "视图日志");
	page.addValue(login_name,"select-key:login_name");
	page.addValue(config_name,"select-key:config_name");
	page.addValue(user_type,"select-key:user_type");
	page.goPage();  
}
_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="视图配置列表"/>
<freeze:errors/>

<freeze:form action="/txn52103001">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:text property="config_name" caption="配置名称："  style="width:60%"/>
      <freeze:select property="config_type" caption="配置类型："  valueset="数据库视图配置类型" style="width:60%"/>
      <freeze:select property="sys_db_user_id" caption="用户名称：" valueset="数据库共享对象代码" style="width:60%"/>
      <freeze:select property="state" caption="用户状态：" valueset="user_state" style="width:60%"/>
      <freeze:select property="sys_db_view_id" caption="视图名称：" valueset="数据库共享视图代码" style="width:60%"/>
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="视图配置列表" keylist="sys_db_config_id" checkbox="false" multiselect="false" width="95%" navbar="bottom" fixrow="false" rowselect="false">
      <freeze:button name="record_addRecord" caption="配置" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();" visible="false"/>
      <freeze:button name="record_deleteRecord" caption="删除"  enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();" visible="false"/>

      <freeze:hidden property="sys_db_config_id" caption="视图配置编码" />
      <freeze:hidden property="config_name" caption="配置名称" />
      <freeze:hidden property="sys_db_view_id" caption="视图编码" />
      <freeze:hidden property="sys_db_user_id" caption="用户编码" />
      <freeze:hidden property="user_type" caption="用户类型" />
      <freeze:hidden property="grant_table" caption="grant_table" />
      <freeze:hidden property="table_no" caption="table_no" />
      <freeze:hidden property="config_type" caption="config_type" />
      <freeze:hidden property="permit_column" caption="permit_column" />
      <freeze:cell property="@rowid" caption="序号" style="width:5%" align="center"/>
      <freeze:cell property="config_name" caption="配置名称" style="width:14%" align="left" />
      <freeze:cell property="login_name" caption="用户代码" style="width:8%" align="left" />
      <freeze:cell property="user_name" caption="用户名称" style="width:15%" align="left" />
      <freeze:cell property="state" caption="状态" valueset="user_state" style="width:8%" align="left" />
      <freeze:cell property="view_code" caption="视图代码" style="width:10%" align="left" />
      <freeze:cell property="view_name" caption="视图名称" style="width:15%" align="left" />
      <freeze:cell property="config_type" caption="配置类型" style="width:10%" valueset="数据库视图配置类型" align="left" />
      <freeze:cell property="oper" caption="操作" style="width:15%" align="center" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
