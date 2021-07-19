<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询表关联关系</title>
</head>

<script language="javascript">


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

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询已认领列表"/>
<freeze:errors/>

<freeze:form action="/txn8000801">
   <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:text property="db_name" caption="数据源名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_code" caption="表名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_primary_key" caption="主键" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="table_fk" caption="外键" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="parent_table" caption="主表" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="parent_pk" caption="主表主键" datatype="string" maxlength="32" style="width:95%"/>
      
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="查询表关联关系" keylist="sys_rd_table_id" width="95%" checkbox="false" navbar="bottom" fixrow="false">
      <freeze:cell property="operation" caption="序号" style="width:5%" />
      <freeze:cell property="db_name" caption="数据源名称" style="width:15%" />
      <freeze:cell property="table_code" caption="表名称" style="width:20%" />
      <freeze:cell property="table_primary_key" caption="主键" style="width:15%" />
      <freeze:cell property="table_fk" caption="外键" style="width:15%" />
      <freeze:cell property="parent_table" caption="主表" style="width:13%" />
      <freeze:cell property="parent_pk" caption="主表主键" style="width:15%" />
      
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
