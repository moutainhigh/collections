<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@page import="cn.gwssi.common.context.Recordset"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询表关系列表</title>
</head>
<%
DataBus context = (DataBus) request.getAttribute("freeze-databus");
Recordset rs = context.getRecordset("record");
if(rs!=null&&rs.size()>0){
	for(int j=0; j<rs.size(); j++){
		DataBus db2 = rs.get(j);
		db2.setValue("table_name", db2.getValue("table_code")+"("+db2.getValue("table_name")+")");
		db2.setValue("table_fk_name", db2.getValue("table_fk")+"("+db2.getValue("table_fk_name")+")");
		db2.setValue("relation_table_name", db2.getValue("relation_table_code")+"("+db2.getValue("relation_table_name")+")");
		db2.setValue("relation_table_fk_name", db2.getValue("relation_table_fk")+"("+db2.getValue("relation_table_fk_name")+")");
	}
}

%>
<script language="javascript">

// 增加表关系
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_rd_table_relation.jsp", "增加表关系", "modal" );
	page.addRecord();
}

// 修改表关系
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn80002604.do", "修改表关系", "modal" );
	page.addParameter( "record:sys_rd_table_relation_id", "primary-key:sys_rd_table_relation_id" );
	page.updateRecord();
}

// 删除表关系
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn80002605.do", "删除表关系" );
	page.addParameter( "record:sys_rd_table_relation_id", "primary-key:sys_rd_table_relation_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询表关系列表"/>
<freeze:errors/>

<freeze:form action="/txn80002601">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:text property="table_name" caption="主表名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_code" caption="主表代码" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_fk" caption="主表字段代码" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_fk_name" caption="主表字段名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="relation_table_name" caption="关联表名称" datatype="string" maxlength="100" style="width:95%"/>
       <freeze:text property="relation_table_code" caption="关联表代码" datatype="string" maxlength="100" style="width:95%"/>
       <freeze:text property="relation_table_fk" caption="关联表字段" datatype="string" maxlength="100" style="width:95%"/>
       <freeze:text property="relation_table_fk_name" caption="关联表字段名称" datatype="string" maxlength="100" style="width:95%"/>
      
  </freeze:block>
<br/>
  <freeze:grid property="record" caption="查询表关系列表" checkbox="false"  width="95%" navbar="bottom" fixrow="false">
      <freeze:hidden property="sys_rd_table_relation_id" caption="关联ID" style="width:12%" />
      <freeze:hidden property="sys_rd_table_id" caption="主表ID" style="width:12%" />
      <freeze:hidden property="table_code" caption="主表代码" style="width:20%" />
      <freeze:cell property="@rowid" caption="序号"  style="width:6%" align="center" /> 
      <freeze:cell property="table_name" caption="主表" style="width:22%" />
      <freeze:hidden property="table_fk" caption="主表字段" style="width:20%" />
      <freeze:cell property="table_fk_name" caption="主表字段" style="width:22%" />
      <freeze:hidden property="relation_table_code" caption="关联表代码" style="width:20%" />
      <freeze:cell property="relation_table_name" caption="关联表" style="width:22%" />
      <freeze:hidden property="relation_table_fk" caption="关联表字段" style="width:20%" />
      <freeze:cell property="relation_table_fk_name" caption="关联表字段" style="width:22%" />
      <freeze:hidden property="sys_rd_system_id" caption="主表所属主题ID" style="width:12%" />
      <freeze:hidden property="sys_rd_data_source_id" caption="主表所属数据源ID" style="width:12%" />
      <freeze:hidden property="ref_sys_rd_system_id" caption="关联表所属主题ID" style="width:12%" />
      <freeze:hidden property="ref_sys_rd_data_source_id" caption="关联表所属数据源ID" style="width:12%" />
      <freeze:hidden property="table_relation_type" caption="关联关系类型" style="width:10%" />    
      <freeze:hidden property="remarks" caption="注释" style="width:20%" visible="false" />
      <freeze:hidden property="timestamp" caption="时间戳" style="width:12%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
