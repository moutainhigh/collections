<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<head>
<title>查询已认领存储过程列表</title>
</head>

<script language="javascript">
function func_go_view()
{
	var page = new pageDefine( "/txn80005201.do", "存储过程管理" );
	page.addParameter("record:sys_rd_data_source_id","select-key:sys_rd_data_source_id");
	page.addParameter("record:db_name","select-key:db_name");
	page.addParameter("record:object_type","select-key:object_type");
	page.goPage();
}

function func_export() 
{   
    var page = new pageDefine("/txn8000276.do", "导出Excel");
    page.addParameter("select-key:object_type","select-key:object_type");
    page.addParameter("record:jc_sys_name","select-key:jc_sys_name");
	page.downFile();	
}
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( __userInitPage );
</script>

<freeze:body>
<freeze:title caption="已认领存储过程列表"/>
<freeze:errors/>

<freeze:form action="/txn80005200">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%" columns="1">
      <freeze:hidden property="object_type" caption="数据库对象类型" datatype="string" style="width:95%"/>
      <freeze:text property="db_name" caption="数据源名称" datatype="string" maxlength="100" style="width:50%"/>
  </freeze:block>

  <freeze:grid property="record" caption="存储过程认领情况列表" nowrap="true" keylist="sys_rd_data_source_id" width="95%" navbar="bottom" fixrow="false" multiselect="false">
  <freeze:button name="go_view" caption="存储过程管理" txncode="80005201" enablerule="1" align="right" onclick="func_go_view();"/>
  	  <freeze:hidden property="sys_rd_data_source_id" caption="数据源ID" style="width:30%" />
  	  <freeze:hidden property="object_type" caption="数据库对象类型" style="width:5%" />
      <freeze:cell property="db_name" caption="数据源名称" style="width:30%" />
      <freeze:cell property="cnt" caption="已认领数量" style="width:30%" />
      <!--freeze:link property="sys_svrname" caption="导出详情" value="导出" style="width:30%" onclick="func_export();" /-->
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
