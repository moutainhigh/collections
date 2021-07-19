<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>认领表</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存未认领表' );	// /txn80002101.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn80002101.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="认领表"/>
<freeze:errors/>

<freeze:form action="/txn80002202">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_rd_unclaim_table_id" caption="未认领表ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="认领表" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      
      <freeze:hidden  property="sys_rd_unclaim_table_id" caption="物理表" style="width:75%"/>
        <freeze:hidden property="unclaim_table_code" caption="物理表" style="width:75%"/>
        <freeze:hidden property="object_schema" caption="模式" style="width:75%"/>
        <freeze:hidden property="sys_rd_data_source_id" caption="数据源ID" style="width:75%"/>
        
        <freeze:cell property="unclaim_table_code" caption="物理表"  colspan="2" style="width:75%"/>
        <freeze:text property="sys_rd_system_id" caption="主题名称"  style="width:95%"/>
        
        <!-- <freeze:select property="sys_rd_system_id" caption="系统名称" notnull="true" onchange="fun_Changed(1);" valueset="业务系统代码集" style="width:95%"/>-->
        <freeze:text property="cur_record_count" caption="数据量" readonly="true" style="width:95%"/>
        <freeze:text property="tb_pk_columns" caption="主键" readonly="true" style="width:95%"/> 
        <freeze:text property="tb_index_columns" caption="索引" readonly="true" style="width:95%"/>
        <freeze:text property="table_type" caption="表类型" valueset="表类型" notnull="true" colspan="2" style="width:95%"/> 
        <!--<freeze:radio property="table_type" caption="表类型" valueset="表类型" notnull="true" colspan="2" onchange="funTBChanged();" value="1"/>-->
        <!--  4 -->
        <freeze:text property="table_name" caption="物理表中文名" notnull="true" colspan="2" style="width:98%"/>
        <freeze:text property="parent_table" caption="主表"  style="width:98%"/>
        <!--  5 -->
        <!-- <freeze:browsebox property="parent_table" caption="主表" valueset="根据数据源下表的代码" style="width:98%" colspan="2" parameter="getParameter()" onchange="fun_Changed(2);"/>-->
        
        <freeze:text property="parent_pk" caption="主表主键"   style="width:95%"/>
        <freeze:text property="table_fk" caption="外键"  style="width:95%" />
        <!--  6 -->
        <!-- <freeze:browsebox property="parent_pk" caption="主表主键" valueset="JC_COLUMN_CODELIST_BY_RLTAB" show="code" multiple="true"  parameter="getZBPKParameter()" style="width:95%"/>
        <freeze:browsebox property="table_fk" caption="外键" show="code" valueset="根据表主键查字段列表" parameter="getZdParameter(100)" style="width:95%" /> -->     
        <!--  7
        <freeze:browsebox property="gen_code_column" show="code" valueset="根据表主键查字段列表" caption="总局代码字段" style="width:95%" parameter="getZdParameter(36)"/>
        <freeze:browsebox property="prov_code_column" show="code" valueset="根据表主键查字段列表" caption="省局代码字段" style="width:95%" parameter="getZdParameter(36)"/>-->
        <!--  8 
        <freeze:browsebox property="city_code_column" valueset="根据表主键查字段列表" show="code" caption="市局代码字段" style="width:95%" parameter="getZdParameter(36)"/>
        <freeze:browsebox property="content" valueset="根据表主键查字段列表" show="code" caption="代码内容字段" style="width:95%" parameter="getZdParameter(2000)"/> -->
        <!-- 9 -->
   		<freeze:textarea property="table_use" caption="用途" colspan="2" maxlength="1000" style="width:98%"/>
     	<freeze:textarea property="remark" caption="备注" colspan="2"  maxlength="2000" style="width:98%" />
      <!--  
      <freeze:hidden property="sys_rd_unclaim_table_id" caption="物理表" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="sys_rd_data_source_id" caption="数据源ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="unclaim_table_code" caption="未认领表" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="unclaim_table_name" caption="未认领表名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="object_schema" caption="对象模式" datatype="string" maxlength="36" style="width:95%"/>
      <freeze:text property="tb_index_name" caption="索引名称" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:textarea property="tb_index_columns" caption="索引字段" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      <freeze:text property="tb_pk_name" caption="主键名" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:textarea property="tb_pk_columns" caption="主键字段" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      <freeze:text property="cur_record_count" caption="当前记录数量" datatype="string" maxlength="" style="width:95%"/>
      <freeze:textarea property="remark" caption="备注" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      <freeze:text property="data_object_type" caption="数据对象类型" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="timestamp" caption="时间戳" datatype="string" maxlength="32" style="width:95%"/>
      -->
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
