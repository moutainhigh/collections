<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="450">
<head>
<title>变更详情</title>
</head>

<script language="javascript">

// 打 印
function func_record_printDocument()
{
	print(document);
}

// 返 回
function func_record_goBackNoUpdate()
{
	goBackNoUpdate();	
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="变更详情"/>
<freeze:errors/>

<freeze:form action="/txn80002306">
<freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_rd_change_id" caption="主键" style="width:95%"/>
  </freeze:frame>

  <freeze:block  property="record" caption="变更详情" width="95%">
      <freeze:hidden property="sys_rd_change_id" caption="表主键" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="sys_rd_data_source_id" caption="数据源ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:cell property="db_name" caption="数据源名称" datatype="string"  style="width:95%" />
      <freeze:cell property="db_username" caption="用户名称" datatype="string"  style="width:95%" />
      <freeze:cell property="table_name" caption="物理表" datatype="string"  style="width:95%" />
      <freeze:cell property="table_name_cn" caption="物理表中文名" datatype="string"  style="width:95%" />
      <freeze:cell property="column_name" caption="字段" datatype="string"  style="width:95%" />
      <freeze:cell property="column_name_cn" caption="字段中文名" datatype="string"  style="width:95%" />
      <freeze:cell property="change_item" caption="变更类型" valueset="变更类型" colspan="2" datatype="string"  style="width:95%" />
      <freeze:cell property="change_before" caption="变更前内容" valueset="字段数据类型" datatype="string"  style="width:95%" />
      <freeze:cell property="change_after" caption="变更后内容" valueset="字段数据类型" datatype="string"  style="width:95%" />
      <freeze:cell property="change_oprater" caption="变更人" datatype="string"  style="width:95%" />
      <freeze:cell property="change_time" caption="变更时间" datatype="string"  style="width:95%" />
      <freeze:cell property="change_reason" caption="变更原因"  valueset="变更原因" colspan="2"  style="width:98%" />
      <freeze:hidden property="change_result" caption="处理结果" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="timestamp" caption="时间戳" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

  <p align="center" class="print-menu">
  <!--  <input type="button" name="record_printDocument" value="打 印" class="menu" onclick="func_record_printDocument();" style='width:60px' >-->
  <table cellspacing="0" cellpadding="0" class="button_table">
	<tr>
		<td class="btn_left"></td>
		<td>
 			<input type="button" name="record_goBackNoUpdate" value="关闭" class="menu" onclick="func_record_goBackNoUpdate();" style='width:60px' >
  		</td>
		<td class="btn_right"></td>
	</tr>
 </table>
  </p>
</freeze:form>
</freeze:body>
</freeze:html>
