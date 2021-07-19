<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-view.jsp --%>
<freeze:html width="750" height="250">
<head>
<title>查看信息实体信息</title>
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
<freeze:title caption="查看信息实体信息"/>
<freeze:errors/>

<freeze:form action="/txn7000216">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_rd_standard_table_id" caption="主键" style="width:95%"/>
  </freeze:frame>

  <freeze:block   property="record" caption="查看信息实体信息" width="95%" columns="1">
      <freeze:cell property="sys_rd_standard_table_id" caption="主键" datatype="string" style="width:95%" visible="false"/>
      <freeze:hidden property="sys_rd_standard_id" caption="规范主键" style="width:95%"/>
      <freeze:cell property="standard_name" caption="规范名称" style="width:95%"/>
      <!--<freeze:cell property="table_code" caption="标准表名" style="width:95%"/>-->
      <freeze:cell property="table_name" caption="信息实体名称" style="width:95%"/>
      <freeze:cell property="table_belongs" caption="所属体系" style="width:95%"/>
      <freeze:cell property="memo" caption="备注" style="width:95%"/>
      <!--<freeze:cell property="sort" caption="排序" style="width:95%"/>-->
      <!--<freeze:cell property="timestamp" caption="时间戳" style="width:95%"/>-->
  </freeze:block>
  <p align="center" class="print-menu">
  <!--   <input type="button" name="record_printDocument" value="打 印" class="menu" onclick="func_record_printDocument();" style='width:60px' >-->
  <table cellspacing="0" cellpadding="0" class="button_table"><tr><td class="btn_left"></td><td>
  <input type="button" name="record_goBackNoUpdate" value="返 回" class="menu" onclick="func_record_goBackNoUpdate();" style='width:60px' >
  </td><td class="btn_right"></td></tr></table>
  </p>
  <!--   <p align="center" class="print-hide">&nbsp;</p>-->

</freeze:form>
</freeze:body>
</freeze:html>
