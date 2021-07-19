<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="750" height="450">
<head>
<title>认领变更</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	
	saveAndExit( '', '保存变更记录表' );	// /txn80002301.do
	
	
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn80002301.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="认领变更"/>
<freeze:errors/>

<freeze:form action="/txn80002302">
 

  <freeze:block property="record" caption="认领变更" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <!-- 拼凑所在位置 -->
      <freeze:text property="db_name" caption="数据源名称" datatype="string"  colspan="2" style="width:98%" readonly="true"/>
      <freeze:text property="table_name" caption="物理表" datatype="string"  style="width:95%" readonly="true"/>
      <freeze:text property="table_name_cn" caption="物理表中文名" datatype="string"  style="width:95%" readonly="true"/>
      <freeze:text property="column_name" caption="字段" datatype="string"  style="width:95%" readonly="true"/>
      <freeze:text property="column_name_cn" caption="字段中文名" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:select property="change_item" caption="变更类型"  colspan="2"  valueset="变更类型" readonly="true" style="width:98%" />
     <freeze:if test="${record.change_item==5}"> 
      <freeze:select property="change_before" caption="变更前内容" valueset="字段数据类型"  style="width:95%" readonly="true"/>
      <freeze:select property="change_after" caption="变更后内容" valueset="字段数据类型"  style="width:95%" readonly="true"/>
     </freeze:if>
     <freeze:if test="${record.change_item!=5}"> 
      <freeze:text property="change_before" caption="变更前内容"  style="width:95%" readonly="true"/>
      <freeze:text property="change_after" caption="变更后内容"   style="width:95%" readonly="true"/>
     </freeze:if>
      <freeze:text property="change_oprater" caption="变更人" datatype="string" maxlength="100" style="width:95%" readonly="true"/>
      <freeze:text property="change_time" caption="变更时间" datatype="string" maxlength="32" style="width:95%" readonly="true"/>
      <freeze:select property="change_reason" caption="变更原因"  valueset="变更原因"  colspan="2" notnull="true" style="width:98%"/>
      
      <freeze:hidden property="sys_rd_table_id" caption="已认领表ID" style="width:10%" />
      <freeze:hidden property="sys_rd_change_id" caption="表主键" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="sys_rd_data_source_id" caption="数据源ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="db_username" caption="用户名称" datatype="string" maxlength="100" style="width:95%"/>
      
      <freeze:hidden property="change_result" caption="处理结果" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="timestamp" caption="时间戳" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
