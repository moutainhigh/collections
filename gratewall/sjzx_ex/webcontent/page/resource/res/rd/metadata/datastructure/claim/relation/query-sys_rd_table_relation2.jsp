<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询表关系列表</title>
</head>

<script language="javascript">


// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询表关系列表"/>
<freeze:errors/>

<freeze:form action="/txn80002607">
 
  <freeze:grid property="record" caption="查询表关系列表" keylist="sys_rd_table_relation_id" width="95%" navbar="bottom" fixrow="false">
     
      <freeze:hidden property="sys_rd_table_relation_id" caption="关联ID" style="width:12%" />
      <freeze:hidden property="sys_rd_table_id" caption="主表ID" style="width:12%" />
      <freeze:hidden property="table_code" caption="主表代码" style="width:20%" />
      <freeze:cell property="table_name" caption="主表中文名称" style="width:20%" />
      <freeze:hidden property="table_fk" caption="主表字段" style="width:20%" />
      <freeze:cell property="table_fk_name" caption="主表字段名称" style="width:20%" />
      <freeze:hidden property="relation_table_code" caption="关联表代码" style="width:20%" />
      <freeze:cell property="relation_table_name" caption="关联表中文名称" style="width:20%" />
      <freeze:hidden property="relation_table_fk" caption="关联表字段" style="width:20%" />
      <freeze:cell property="ralation_table_fk_name" caption="关联表字段中文名称" style="width:20%" />
      <freeze:hidden property="sys_rd_system_id" caption="主表所属主题ID" style="width:12%" />
      <freeze:hidden property="sys_rd_data_source_id" caption="主表所属数据源ID" style="width:12%" />
       <freeze:hidden property="ref_sys_rd_system_id" caption="关联表所属主题ID" style="width:12%" />
      <freeze:hidden property="ref_sys_rd_data_source_id" caption="关联表所属数据源ID" style="width:12%" />
      <freeze:cell property="table_relation_type" caption="关联关系类型" style="width:10%" />    
      <freeze:cell property="remarks" caption="注释" style="width:20%" visible="false" />
      <freeze:hidden property="timestamp" caption="时间戳" style="width:12%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
