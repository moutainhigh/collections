<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-view.jsp --%>
<freeze:html width="750" height="300">
<head>
<title>查看指标项信息</title>
<style>
body{
background:#ffffff;
}
</style>
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

_browse.execute( __userInitPage );
</script>
<freeze:body styleClass='body-div'>
<freeze:title caption="查看指标项信息"/>
<freeze:errors/>

<freeze:form action="/txn7000226">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_rd_standard_column_id" caption="主键" style="width:95%"/>
  </freeze:frame>

  <freeze:block  property="record" caption="查看指标项信息" width="95%">
      <freeze:hidden property="sys_rd_standard_column_id" caption="主键" datatype="string" style="width:95%"/>
      <freeze:hidden property="sys_rd_standard_table_id" caption="标准表主键" style="width:95%"/>
      <freeze:cell property="cn_name" caption="指标项名称" style="width:95%"/>
      <freeze:cell property="column_name" caption="字段名" style="width:95%"/>
      <freeze:cell property="column_type" caption="数据类型" valueset="字段数据类型" style="width:95%"/>
      <freeze:cell property="column_format" caption="格式" style="width:95%"/>
      <freeze:cell property="code_identifier" caption="代码标识符" valueset="基础代码集对照" style="width:95%"/>
       <freeze:cell property="data_element_identifier" caption="数据元标识符" valueset="数据元标识符" style="width:95%"/>
      <freeze:cell property="memo" caption="备注" style="width:95%"/>
  </freeze:block>
  <p align="center" class="print-menu">
  <!--   <input type="button" name="record_printDocument" value="打 印" class="menu" onclick="func_record_printDocument();" style='width:60px' >-->
  <table cellspacing="0" cellpadding="0" class="button_table">
	<tr>
		<td class="btn_left"></td>
		<td>
  			<input type="button" name="record_goBackNoUpdate" value="返 回" class="menu" onclick="func_record_goBackNoUpdate();" style='width:60px' >
  		</td>
		<td class="btn_right"></td>
	</tr>
  </table>
  </p>
  <!--  <p align="center" class="print-hide">&nbsp;</p>-->

</freeze:form>
</freeze:body>
</freeze:html>
