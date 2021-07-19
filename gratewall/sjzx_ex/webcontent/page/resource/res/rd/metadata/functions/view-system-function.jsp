<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<head>
<title>函数认领情况列表</title>
</head>

<script language="javascript">
function func_export() 
{   
    var page = new pageDefine("/txn8000272.do", "导出Excel");
    page.addParameter("select-key:object_type","select-key:object_type");
    page.addParameter("record:data_source_id","select-key:data_source_id");
	page.downFile();	
}
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var operationSpan = document.getElementsByName("span_record:operation");
	var number=1;
	for (var i=0; i < operationSpan.length; i++){
		operationSpan[i].style.textAlign = "center";
		operationSpan[i].innerHTML = number++;
	}
}

_browse.execute( __userInitPage );
</script>

<freeze:body>
<freeze:title caption="函数认领情况列表"/>
<freeze:errors/>

<freeze:form action="/txn80004000">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%" columns="1">
      <freeze:hidden property="object_type" caption="数据库对象类型" datatype="string" style="width:95%"/>
      <freeze:text property="db_name" caption="数据源名称" datatype="string" maxlength="16" style="width:50%"/>
  </freeze:block>

  <freeze:grid property="record" caption="函数认领情况列表" nowrap="true" keylist="sys_rd_data_source_id" width="95%" navbar="bottom" fixrow="false" checkbox="false">
      <freeze:cell property="operation" caption="序号" style="width:3%"/>
      <freeze:cell property="db_name" caption="数据源名称" style="width:20%" />
      <freeze:cell property="total" caption="函数总数" style="width:20%" />
      <freeze:cell property="wrl" caption="未认领数" style="width:20%" />
      <freeze:cell property="yrl" caption="已认领数" style="width:20%" />
      <freeze:hidden property="sys_svrname" caption="导出详情" value="导出" style="width:12%" onclick="func_export();" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
